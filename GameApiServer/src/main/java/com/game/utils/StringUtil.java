package com.game.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class StringUtil {

	/**
	 * 空字符串
	 */
	public static String EMPTY_VALUE = "";

	private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 字符串比较
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareString(String str1, String str2) {
		if (str1 == null || str2 == null) {
			if (str1 == str2) {
				return true;
			}
		} else if (str1.equals(str2)) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串比较(忽略大小写)
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareStringIgnoreCase(String str1, String str2) {
		if (str1 == null || str2 == null) {
			if (str1 == str2) {
				return true;
			}
		} else if (str1.equalsIgnoreCase(str2)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否null或者是否为空字符
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmptyOrNull(String string) {
        return string == null || string.isEmpty();
	}

	/**
	 * 分解字符串,没有识别正则表达式的功能，而且返回的是List
	 * 
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public static List<String> split(String source, String delimiter) {
		ArrayList<String> list = new ArrayList<String>();
		if (source == null || source.length() == 0) {
			return list;
		}
		if (delimiter == null || delimiter.length() == 0) {
			list.add(source);
			return list;
		}
		int fromIndex = 0;
		int delimiterLength = delimiter.length();

		do {
			int index = source.indexOf(delimiter, fromIndex);
			if (index == fromIndex) {
				list.add("");
			} else if (index == -1) {
				list.add(source.substring(fromIndex, source.length()));
				break;
			} else {
				list.add(source.substring(fromIndex, index));
			}
			fromIndex = index + delimiterLength;
		} while (true);

		return list;
	}

	public static Map<String, String> decodeHttpQueryNoDecode(String httpQuery) throws UnsupportedEncodingException {
		Map<String, String> map = new TreeMap<String, String>();
		int index = httpQuery.indexOf("?");
		if (index != -1) {
			httpQuery = httpQuery.substring(index + 1, httpQuery.length());
		}
		for (String s : httpQuery.split("&")) {
			String pair[] = s.split("=");
			map.put(pair[0], pair[1]);
		}
		return map;
	}

	public static String buildHttpQueryNoEncode(Map<String, Object> data) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		Map<String, Object> tempMap = new TreeMap<>();
		tempMap.putAll(data);
		for (Map.Entry<String, Object> pair : tempMap.entrySet()) {
			builder.append(pair.getKey() + "=" + pair.getValue() + "&");
		}
		return builder.substring(0, builder.length() - 1);
	}

	/**
	 * 默认16 位随机字符串
	 *
	 * @return
	 */
	public static String generateNoncestr() {
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		for (int i = 0; i < 16; i++) {
			sb.append(chars.charAt(rd.nextInt(chars.length() - 1)));
		}
		return sb.toString();
	}

}
