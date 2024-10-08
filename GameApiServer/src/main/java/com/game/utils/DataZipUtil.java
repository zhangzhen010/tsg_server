package com.game.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * 数据压缩工具
 * 
 * @author zhangzhen
 * @date 2017年8月24日
 * @version 1.0
 */
public class DataZipUtil {

	protected static Logger log = LogManager.getLogger(DataZipUtil.class);

	private static final String Version = "20180712";
	/**
	 * 压缩阈值20k
	 */
	private static final int ZIPLIMIT = 20 * 1024;

	private static String dataKey() {
		return "#" + Version + "#";
	}

	/**
	 * 数据编码
	 * 
	 * @param encodeString
	 * @return
	 */
	public static String dataEncode(String encodeString) {
		return dataEncode(encodeString, ZIPLIMIT);
	}

	private static String dataEncode(String encodeString, int clen) {
		if (encodeString.length() > clen && !encodeString.startsWith(dataKey())) {
			try {
				return dataKey() + CodeUtils.encodeBase64(ZipUtil.compress(encodeString));
			} catch (IOException e) {
				log.error(e, e);
			}
			return encodeString;
		} else {
			return encodeString;
		}
	}

	/**
	 * 数据解码
	 * 
	 * @param decodeString
	 * @return
	 * @throws Exception
	 */
	public static String dataDecode(String decodeString) throws Exception {
		if (decodeString.startsWith(dataKey())) {
			String parseString = decodeString.replaceFirst(dataKey(), "");
			return ZipUtil.uncompress(CodeUtils.decodeBase64(parseString));
		} else {
			return decodeString;
		}
	}

	/**
	 * 数据编码
	 * @param encodeData
	 * @return
	 */
	public static byte[] dataEncode(byte[] encodeData) {
		try {
			return ZipUtil.compress(encodeData);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 数据解码
	 * @param decodeData
	 * @return
	 * @throws Exception
	 */
	public static byte[] dataDecode(byte[] decodeData) throws Exception {
		return ZipUtil.uncompress(decodeData);
	}
}
