package com.game.utils;

/**
 * 用来去除html标签等操作
 * 
 * @author zhangzhen
 * @time 2018年11月30日
 */
public class HTMLUtil {
	/**
	 * 将hmtl的内容转换成无HTML代码
	 * 
	 * @param htmlContent
	 * @return
	 */
	public static String parseToNoHTMLTagContent(String htmlContent) {
		return htmlContent.replaceAll("<[^>]*>", "");
	}

	/**
	 * 判断是否有含有html标签
	 * 
	 * @param content
	 * @return 若为true，则表示含有html标签 若为false，则表示不含有html标签
	 */
	public static boolean containHTMLTag(String content) {
		return parseToNoHTMLTagContent(content).length() - content.length() < 0 ? true
				: false;
	}

}
