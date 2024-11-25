package com.farm.sfile.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;

public class EncodeUtils {
	private static final int BYTE_SIZE = 8;
	private static final String CODE_UTF8 = "UTF-8";
	private static final String CODE_UTF16 = "UTF-16";// Unicode
	private static final String CODE_UTF16LE = "UTF-16LE";// Unicode big endian
	private static final String CODE_GBK = "GBK"; // ABSU

	/**getEncode已經沒有問題了（此方法未驗證，不要貿然使用,但是如果getEncode方法不好用时可以一试）
	 * @param file
	 * @return
	 */
	@Deprecated
	public static String getEncode_back(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		BufferedInputStream bis = null;
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else
				if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					// 单独出现BF以下的，也算是GBK
					if (0x80 <= read && read <= 0xBF)
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80 -
							// 0xBF),也可能在GB编码内
							continue;
						else
							break;
						// 也有可能出错，但是几率较小
					} else if (0xE0 <= read && read <= 0xEF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
				System.out.println(loc + " " + Integer.toHexString(read));
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return charset;
	}

	/**
	 * 通过文件全名称获取编码集名称
	 */
	public static String getEncode(String fullFileName) {
		try {

			String encode = codeString(new File(fullFileName));
			if (encode.equals("Unicode")) {
				return "Unicode";
			} else {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fullFileName));
				try {
					return getEncode(bis, CODE_GBK);
				} finally {
					bis.close();
				}
			}
		} catch (Exception e) {
			return CODE_GBK;
		}
	}

	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 *            :file
	 * @return 文件编码格式
	 * @throws Exception
	 */
	private static String codeString(File fileName) throws Exception {
		FileInputStream fistrea = new FileInputStream(fileName);
		BufferedInputStream bin = new BufferedInputStream(fistrea);
		String code = null;
		try {
			int p = (bin.read() << 8) + bin.read();
			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}
			IOUtils.closeQuietly(bin);
		} finally {
			bin.close();
			fistrea.close();
		}
		return code;
	}

	/**
	 * 通过文件缓存流获取编码集名称，文件流必须为未曾
	 *
	 * @param bis
	 *            文件流
	 */
	private static String getEncode(BufferedInputStream bis, String defaultEncoding) throws Exception {
		bis.mark(0);
		String encodeType;
		byte[] head = new byte[3];
		bis.read(head);
		if (head[0] == -1 && head[1] == -2 && head[2] == (byte) 0x41) {
			encodeType = CODE_UTF16;
		} else if (head[0] == -2 && head[1] == -1 && head[2] == 0) {
			// encodeType = "Unicode";
			encodeType = CODE_UTF16LE;
		} else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
			// 带BOM的UTF8 (CODE_UTF8_BOM)
			encodeType = CODE_UTF8;
		} else {
			if (isUTF8(bis)) {
				encodeType = CODE_UTF8;
			} else {
				encodeType = defaultEncoding;
			}
		}

		return encodeType;
	}

	/**
	 * 是否是无BOM的UTF8格式，不判断常规场景，只区分无BOM UTF8和GBK
	 */
	private static boolean isUTF8(BufferedInputStream bis) throws Exception {
		bis.reset();

		// 读取第一个字节
		int code = bis.read();
		do {
			BitSet bitSet = convert2BitSet(code);
			// 判断是否为单字节
			if (bitSet.get(0)) {// 多字节时，再读取N个字节
				if (!checkMultiByte(bis, bitSet)) {// 未检测通过,直接返回
					return false;
				}
			}
			code = bis.read();
		} while (code != -1);
		return true;
	}

	/**
	 * 检测多字节，判断是否为utf8，已经读取了一个字节
	 */
	private static boolean checkMultiByte(BufferedInputStream bis, BitSet bitSet) throws Exception {
		int count = getCountOfSequential(bitSet);
		byte[] bytes = new byte[count - 1];// 已经读取了一个字节，不能再读取
		bis.read(bytes);
		for (byte b : bytes) {
			if (!checkUtf8Byte(b)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测bitSet中从开始有多少个连续的1
	 */
	private static int getCountOfSequential(BitSet bitSet) {
		int count = 0;
		for (int i = 0; i < BYTE_SIZE; i++) {
			if (bitSet.get(i)) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * 检测单字节，判断是否为utf8
	 */
	private static boolean checkUtf8Byte(byte b) throws Exception {
		BitSet bitSet = convert2BitSet(b);
		return bitSet.get(0) && !bitSet.get(1);
	}

	/**
	 * 将整形转为BitSet
	 */
	private static BitSet convert2BitSet(int code) {
		BitSet bitSet = new BitSet(BYTE_SIZE);

		for (int i = 0; i < BYTE_SIZE; i++) {
			int tmp3 = code >> (BYTE_SIZE - i - 1);
			int tmp2 = 0x1 & tmp3;
			if (tmp2 == 1) {
				bitSet.set(i);
			}
		}
		return bitSet;
	}
}