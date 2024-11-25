package com.farm.sfile.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 文件下载工具
 * 
 * @author macpl
 *
 */
public class DownloadUtils {
	private static final Logger log = Logger.getLogger(DownloadUtils.class);

	public static String getFileNameByUTF8(String name) {
		try {
			return URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(")
					.replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
					.replaceAll("%26", "\\&");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			return name.replaceAll("\\+", "%20").replaceAll("%28", "\\(").replaceAll("%29", "\\)")
					.replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#").replaceAll("%26", "\\&");
		}
	}

	/**
	 * 下载组件-普通下载
	 * 
	 * @param file
	 * @param filename
	 * @param response
	 */
	public static void downloadFile(File file, String filename, HttpServletResponse response) {
		if (!file.exists()) {
			throw new RuntimeException("the file is not exist!");
		}
		filename = filename.replaceAll(" ", "").replaceAll(";", "").replaceAll(",", "").replaceAll("=", "")
				.replaceAll("&", "");
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-length", file.length() + "");
		try {
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + new String(filename.getBytes("gbk"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] b = new byte[4096];
			int length;
			while ((length = is.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			if (e.getClass().getName().indexOf("ClientAbortException") >= 0) {
				// 在下载图片时端口连接（有可能是客户端断开）
			} else {
				log.error(e.getMessage());
			}
		} finally {
			// 这里主要关闭。
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				// 在下载图片时端口连接（有可能是客户端断开）
				// log.error(e.getMessage());
			}
		}
	}

	/**
	 * <h2>使用断点续传技术提供输出流</h2>
	 * <p>
	 * 处理普通的或带有断点续传参数的下载请求，并按照请求方式提供相应的输出流写出。请传入相应的参数并执行该方法以开始传输。
	 * </p>
	 * 
	 * @author (开源项目中摘抄)
	 * @param request
	 * @param response
	 * @param file
	 * @param filefname
	 * @param contentType
	 * @return true 的话表示第一次请求 false 表示后面多次请求
	 */
	public static void rangeStreamDownloadFile(HttpServletRequest request, HttpServletResponse response, File file,
			String filefname, String contentType) {
		long fileLength = file.length();// 文件总大小
		long startOffset = 0; // 起始偏移量
		boolean hasEnd = false;// 请求区间是否存在结束标识
		long endOffset = 0; // 结束偏移量
		long contentLength = 0; // 响应体长度
		String rangeBytes = "";// 请求中的Range参数
		// 设置请求头，基于kiftd文件系统推荐使用application/octet-stream
		if (StringUtils.isNotBlank(contentType)) {
			response.setContentType(contentType);
		}
		// 设置文件信息
		response.setCharacterEncoding("UTF-8");
		if (request.getHeader("User-Agent").toLowerCase().indexOf("safari") >= 0) {
			// safari浏览器
			response.setHeader("Content-Disposition",
					"attachment; filename=\""
							+ new String(filefname.getBytes(Charset.forName("UTF-8")), Charset.forName("ISO-8859-1"))
							+ "\"; filename*=utf-8''" + getFileNameByUTF8(filefname));
		} else {
			// 普通浏览器
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getFileNameByUTF8(filefname)
					+ "\"; filename*=utf-8''" + getFileNameByUTF8(filefname));
		}
		// 设置支持断点续传功能
		response.setHeader("Accept-Ranges", "bytes");
		// 针对具备断点续传性质的请求进行解析
		if (request.getHeader("Range") != null && request.getHeader("Range").startsWith("bytes=")) {
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
			if (rangeBytes.endsWith("-")) {
				// 解析请求参数范围为仅有起始偏移量而无结束偏移量的情况
				startOffset = Long.parseLong(rangeBytes.substring(0, rangeBytes.indexOf('-')).trim());
				// 仅具备起始偏移量时，例如文件长为13，请求为5-，则响应体长度为8
				contentLength = fileLength - startOffset;
			} else {
				hasEnd = true;
				startOffset = Long.parseLong(rangeBytes.substring(0, rangeBytes.indexOf('-')).trim());
				endOffset = Long.parseLong(rangeBytes.substring(rangeBytes.indexOf('-') + 1).trim());
				// 具备起始偏移量与结束偏移量时，例如0-9，则响应体长度为10个字节
				contentLength = endOffset - startOffset + 1;
			}
		} else { // 从开始进行下载
			// 首次访问（普通文件下载）
			contentLength = fileLength; // 客户端要求全文下载
		}
		response.setHeader("Content-Length", "" + contentLength);// 设置请求体长度
		if (startOffset != 0) {
			// 设置Content-Range，格式为“bytes 起始偏移-结束偏移/文件的总大小”
			String contentRange;
			if (!hasEnd) {
				contentRange = new StringBuffer("bytes ").append("" + startOffset).append("-")
						.append("" + (fileLength - 1)).append("/").append("" + fileLength).toString();
				response.setHeader("Content-Range", contentRange);
			} else {
				contentRange = new StringBuffer("bytes ").append(rangeBytes).append("/").append("" + fileLength)
						.toString();
			}
			response.setHeader("Content-Range", contentRange);
		} else {
			// 下载首次访问有
		}
		// 写出缓冲
		byte[] buf = new byte[4096];
		// 读取文件并写处至输出流
		BufferedOutputStream out = null;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			out = new BufferedOutputStream(response.getOutputStream());
			raf.seek(startOffset);
			if (!hasEnd) {
				// 无结束偏移量时，将其从起始偏移量开始写到文件整体结束，如果从头开始下载，起始偏移量为0
				int n = 0;
				while ((n = raf.read(buf)) != -1) {
					out.write(buf, 0, n);
				}
			} else {
				// 有结束偏移量时，将其从起始偏移量开始写至指定偏移量结束。
				int n = 0;
				long readLength = 0;// 写出量，用于确定结束位置
				while (readLength < contentLength) {
					n = raf.read(buf);
					readLength += n;
					out.write(buf, 0, n);
				}
			}
			out.flush();
			out.close();
		} catch (IOException ex) {
			// 针对任何IO异常忽略，传输失败不处理
			// 在下载图片时端口连接（有可能是客户端断开）
			// log.error(ex.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 断点续传，用于ios/mp3/mp4
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void sendVideoFile(HttpServletRequest request, HttpServletResponse response, File file,
			String filefname, String contentType) throws FileNotFoundException, IOException {
		long fileLength = file.length();// 文件总大小
		long startOffset = 0; // 起始偏移量
		boolean hasEnd = false;// 请求区间是否存在结束标识
		long endOffset = 0; // 结束偏移量
		long contentLength = 0; // 响应体长度
		String rangeBytes = "";// 请求中的Range参数
		// 设置请求头，基于kiftd文件系统推荐使用application/octet-stream
		if (StringUtils.isNotBlank(contentType)) {
			response.setContentType(contentType);
		}
		// 设置支持断点续传功能
		response.setHeader("Accept-Ranges", "bytes");
		// 针对具备断点续传性质的请求进行解析
		if (request.getHeader("Range") != null && request.getHeader("Range").startsWith("bytes=")) {
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
			if (rangeBytes.endsWith("-")) {
				// 解析请求参数范围为仅有起始偏移量而无结束偏移量的情况
				startOffset = Long.parseLong(rangeBytes.substring(0, rangeBytes.indexOf('-')).trim());
				// 仅具备起始偏移量时，例如文件长为13，请求为5-，则响应体长度为8
				contentLength = fileLength - startOffset;
			} else {
				hasEnd = true;
				startOffset = Long.parseLong(rangeBytes.substring(0, rangeBytes.indexOf('-')).trim());
				endOffset = Long.parseLong(rangeBytes.substring(rangeBytes.indexOf('-') + 1).trim());
				// 具备起始偏移量与结束偏移量时，例如0-9，则响应体长度为10个字节
				contentLength = endOffset - startOffset + 1;
			}
		} else { // 从开始进行下载
			// 首次访问（普通文件下载）
			contentLength = fileLength; // 客户端要求全文下载
		}
		response.setHeader("Content-Length", "" + contentLength);// 设置请求体长度
		if (startOffset != 0) {
			// 设置Content-Range，格式为“bytes 起始偏移-结束偏移/文件的总大小”
			String contentRange;
			if (!hasEnd) {
				contentRange = new StringBuffer("bytes ").append("" + startOffset).append("-")
						.append("" + (fileLength - 1)).append("/").append("" + fileLength).toString();
				response.setHeader("Content-Range", contentRange);
			} else {
				contentRange = new StringBuffer("bytes ").append(rangeBytes).append("/").append("" + fileLength)
						.toString();
			}
			response.setHeader("Content-Range", contentRange);
		} else {
			// 设置Content-Range，格式为“bytes 起始偏移-结束偏移/文件的总大小”
			String contentRange;
			if (!hasEnd) {
				contentRange = new StringBuffer("bytes ").append("" + startOffset).append("-")
						.append("" + (fileLength - 1)).append("/").append("" + fileLength).toString();
				response.setHeader("Content-Range", contentRange);
			} else {
				contentRange = new StringBuffer("bytes ").append(rangeBytes).append("/").append("" + fileLength)
						.toString();
			}
			response.setHeader("Content-Range", contentRange);
		}
		// 写出缓冲
		byte[] buf = new byte[4096];
		// 读取文件并写处至输出流
		BufferedOutputStream out = null;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			out = new BufferedOutputStream(response.getOutputStream());
			raf.seek(startOffset);
			if (!hasEnd) {
				// 无结束偏移量时，将其从起始偏移量开始写到文件整体结束，如果从头开始下载，起始偏移量为0
				int n = 0;
				while ((n = raf.read(buf)) != -1) {
					out.write(buf, 0, n);
				}
			} else {
				// 有结束偏移量时，将其从起始偏移量开始写至指定偏移量结束。
				int n = 0;
				long readLength = 0;// 写出量，用于确定结束位置
				while (readLength < contentLength) {
					n = raf.read(buf);
					readLength += n;
					out.write(buf, 0, n);
				}
			}
			out.flush();
			out.close();
		} catch (IOException ex) {
			// 针对任何IO异常忽略，传输失败不处理
			if (StringUtils.isNotBlank(ex.getMessage()) && ex.getMessage().indexOf("Connection reset by peer") < 0) {
				log.error(ex.getMessage());
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
