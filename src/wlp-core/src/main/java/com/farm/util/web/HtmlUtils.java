package com.farm.util.web;


import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.farm.util.web.FarmHtmlUtils;

public class HtmlUtils {

	/**
	 * html 標簽转义
	 * 
	 * @param source
	 * @return
	 */
	public static String htmlEncode(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}




	/**
	 * 删除Html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String HtmlRemoveTag(String html) {
		return FarmHtmlUtils.HtmlRemoveTag(html);// 返回文本字符串
	}


	/**
	 * 判断知识中是否包含目录标题
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isHaveKnowMenu(String text) {
		Document document = Jsoup.parseBodyFragment(text);
		Elements eles = document.getElementsByTag("H1");
		eles.addAll(document.getElementsByTag("H2"));
		eles.addAll(document.getElementsByTag("H3"));
		eles.addAll(document.getElementsByTag("H4"));
		return eles.size() > 0;
	}

}
