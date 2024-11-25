package com.farm.util.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 转义工具集合
 * 
 * @author Administrator
 * 
 */
public class FarmFormatUnits {
	/**
	 * 为文件设置大小并加上单位
	 * 
	 * @param fileLength
	 *            文件大小（b）
	 * @return
	 */
	public static String getFileLengthAndUnit(int fileLength) {
		String unit = "b";
		Integer length = fileLength;
		if ((Integer) fileLength / 1024 > 0) {
			length = (Integer) fileLength / 1024;
			unit = "kb";
		}
		if ((Integer) fileLength / 1024 / 1024 > 0) {
			length = (Integer) fileLength / 1024 / 1024;
			unit = "mb";
		}
		return length + unit;
	}

	/**
	 * 按照指定长度切割字符串
	 * 
	 * @param inputString
	 *            需要切割的源字符串
	 * @param length
	 *            指定的长度
	 * @return
	 */
	public static List<String> SplitStringByLen(String inputString, int length) {
		List<String> divList = new ArrayList<>();
		int remainder = (inputString.length()) % length;
		// 一共要分割成几段
		int number = (int) Math.floor((inputString.length()) / length);
		for (int index = 0; index < number; index++) {
			String childStr = inputString.substring(index * length, (index + 1) * length);
			divList.add(childStr);
		}
		if (remainder > 0) {
			String cStr = inputString.substring(number * length, inputString.length());
			divList.add(cStr);
		}
		return divList;
	}

	/**
	 * 将tags序列字符串转化为tag的list序列
	 * 
	 * @param ids
	 * @return
	 */
	public static List<String> parseTags(String tags) {
		if (tags == null || StringUtils.isBlank(tags)) {
			return new ArrayList<String>();
		}
		String[] markdot = formatTags(tags).split(",");
		List<String> list_ = new ArrayList<String>();
		for (int i = 0; i < markdot.length; i++) {
			String temp = markdot[i];
			if (temp != null && !temp.equals("") && !temp.equals(" "))
				list_.add(temp.trim());
		}
		return list_;
	}

	/**
	 * 格式化标签，转义分隔符
	 * 
	 * @param tags
	 * @return
	 */
	public static String formatTags(String tags) {
		if (StringUtils.isBlank(tags)) {
			return null;
		}
		return tags.replaceAll("，", ",").replaceAll(" ", ",").replaceAll("、", ",").replaceAll(";", ",").replaceAll("；",
				",");
	}

	/**
	 * 替换可能存在的utf8中的 4字节字符
	 * 
	 * @param input
	 *            输入字符串
	 * @param replacement
	 *            替换为的字符串
	 * @return 替换后的utf8字符串
	 */
	public static String replaceUtf8Mb4Char(String text, String replacement) {
		try {
			String input = text;
			if (input == null) {
				return text;
			}
			StringBuilder sb = new StringBuilder(input.length());
			byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
			char[] chars = input.toCharArray();
			int charIdx = 0;
			for (int i = 0; i < bytes.length; i++) {
				byte b = bytes[i];
				// four bytes
				if ((b & 0XF0) == 0XF0) {
					sb.append(replacement);
					// utf-8四字节字符unicode后变为2个字符， 故字符下标多加1
					charIdx += 2;
					i += 3;
					continue;
				} else if ((b & 0XE0) == 0XE0) {
					// three bytes
					// forward 2 byte
					i += 2;
				} else if ((b & 0XC0) == 0XC0) {
					i += 1;
				}
				sb.append(chars[charIdx]);
				charIdx++;
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return text;
		}
	}

	/**
	 * 将tags序列字符串转化为tag的list序列(用于全文检索)
	 * 
	 * @param tagtext
	 *            标签字符串
	 * @param innerWord
	 *            查询关键字
	 * @return
	 */
	public static List<Map<String, Object>> parseSearchTags(String tagtext, String innerWord) {
		String tagStr = FarmHtmlUtils.HtmlRemoveTag(tagtext);
		List<String> tags = parseTags(tagStr);
		List<Map<String, Object>> styleTags = new ArrayList<>();
		if (StringUtils.isNotBlank(innerWord)) {
			List<String> userTags = parseTags(innerWord.toUpperCase().replace("TAG:", "").replace("ALL:", "")
					.replace("TYPE:", "").replace("AUTHOR:", "").replace("TITLE:", ""));
			// 标签查询,迭代知识标签
			for (String tag : tags) {
				Map<String, Object> node = new HashMap<>();
				{
					node.put("TEXT", tag);
					node.put("ISMARK", false);
				}
				if (userTags.contains(tag.toUpperCase())) {
					node.put("ISMARK", true);
				}
				styleTags.add(node);
			}
		}
		return styleTags;
	}

	/**
	 * 去掉日期时间的格式（去掉'-'、'：'、'空格'）
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String getReFormateTime14(String timeStr) {
		timeStr = timeStr.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "") + "00000000000000";
		return timeStr.substring(0, 14);
	}

	/**
	 * 格式化时间
	 * 
	 * @param timeStr08_14
	 *            yyyyMMddHHmmss
	 * @param isshowCurrentDay
	 *            是否当天显示为“今天”
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormateTime(String timeStr08_14, boolean isshowCurrentDay) {
		if (timeStr08_14 == null || timeStr08_14.trim().length() <= 0) {
			return null;
		}
		int tlength = timeStr08_14.length();
		timeStr08_14 = timeStr08_14 + "00000000";
		String yyyy = timeStr08_14.substring(0, 4);
		String MM = timeStr08_14.substring(4, 6);
		String dd = timeStr08_14.substring(6, 8);
		String HH = timeStr08_14.substring(8, 10);
		String mm = timeStr08_14.substring(10, 12);
		String ss = timeStr08_14.substring(12, 14);
		String returnData = null;
		if (tlength == 8 && returnData == null) {
			returnData = yyyy + "-" + MM + "-" + dd;
		}
		if (tlength == 10 && returnData == null) {
			returnData = yyyy + "-" + MM + "-" + dd + " " + HH;
		}
		if (tlength == 12 && returnData == null) {
			returnData = yyyy + "-" + MM + "-" + dd + " " + HH + ":" + mm;
		}
		if (returnData == null) {
			returnData = yyyy + "-" + MM + "-" + dd + " " + HH + ":" + mm + ":" + ss;
		}
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentday = _sdf.format(new Date());
		return isshowCurrentDay ? returnData.replace(currentday, "今天") : returnData;
	}

	/**
	 * 时间去格式
	 * 
	 * @param timeStr
	 *            格式化的時間yyyy-MM-dd HH:mm:ss
	 * @param length
	 *            最后截取时间的长度
	 * @return yyyyMMddHHmmss
	 */
	public static String reFormateTime(String timeStr, int length) {
		if (timeStr == null || timeStr.trim().length() <= 0) {
			return null;
		}
		timeStr = timeStr.replace(":", "").replace("-", "").replace(" ", "");
		if (timeStr.length() > length) {
			timeStr = timeStr.substring(0, length);
		}
		return timeStr;

	}

	/**
	 * kindedit用于修改字符串时，需要转义为该格式
	 * 
	 * @param text
	 * @return
	 */
	public static String getKindEditorEditText(String text) {
		text = text.replaceAll("&nbsp;", "&amp;nbsp;");
		return text;
	}

	/**
	 * 拼接sql的in子句
	 * 
	 * @param popTypeIds
	 * @return
	 */
	public static String getSqlInStrs(List<String> popTypeIds) {
		StringBuffer inStr = new StringBuffer();
		for (String id : popTypeIds) {
			if (inStr.length() == 0) {
				inStr.append("'" + id + "'");
			} else {
				inStr.append(",'" + id + "'");
			}
		}
		if (inStr.length() == 0) {
			inStr.append("'NOID'");
		}
		return inStr.toString();
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 * 
	 * @param inputString
	 *            原始字符串
	 * @param length
	 *            指定长度
	 * @return
	 */
	public static List<String> getStrList(String inputString, int length) {
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		return getStrList(inputString, length, size);
	}

	/**
	 * 把原始字符串分割成指定长度的字符串列表
	 * 
	 * @param inputString
	 *            原始字符串
	 * @param length
	 *            指定长度
	 * @param size
	 *            指定列表大小
	 * @return
	 */
	private static List<String> getStrList(String inputString, int length, int size) {
		List<String> list = new ArrayList<String>();
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length, (index + 1) * length);
			list.add(childStr);
		}
		return list;
	}

	public static Map<String, String> getDicMap(String dicstr) {
		String[] mapStr = dicstr.trim().split(",");
		Map<String, String> dictionary = new HashMap<String, String>();
		for (String keyValue : mapStr) {
			String[] node = keyValue.split(":");
			try {
				dictionary.put(node[0], node[1]);
			} catch (Exception e) {
				throw new RuntimeException(e + ",请检查字典项：'" + keyValue + "'");
			}
		}
		return dictionary;
	}

	/**
	 * 分割字符串，如果开始位置大于字符串长度，返回空
	 * 
	 * @param str
	 *            原始字符串
	 * @param f
	 *            开始位置
	 * @param t
	 *            结束位置
	 * @return
	 */
	private static String substring(String str, int f, int t) {
		if (f > str.length())
			return null;
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}
}
