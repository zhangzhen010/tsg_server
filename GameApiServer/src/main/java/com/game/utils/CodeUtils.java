package com.game.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CodeUtils {
	/**
	 * Logger for this class
	 */
	private static Logger log = LogManager.getLogger(CodeUtils.class);

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String decodeBase64(String b64string) throws Exception {
		return new String(Base64.getDecoder().decode(b64string.getBytes()), "utf-8");
	}

	public static String encodeBase64(String stringsrc) {
		try {
			return new String(Base64.getEncoder().encode(stringsrc.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
			return null;
		}
	}

	public static String Md5(String s) {
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	public static String generateSignature_OPPO(String data, String data1) {
		try {
			byte[] byteHMAC = (byte[]) null;
			try {
				Mac mac = Mac.getInstance("HmacSHA1");
				SecretKeySpec spec = null;
				spec = new SecretKeySpec(data.getBytes(), "HmacSHA1");
				mac.init(spec);
				byteHMAC = mac.doFinal(data1.getBytes());
			} catch (InvalidKeyException e) {
				log.error(e, e);
			} catch (NoSuchAlgorithmException e) {
				log.error(e, e);
			}
			return URLEncoder.encode(String.valueOf(base64Encode_OPPO(byteHMAC)), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static char[] base64Encode_OPPO(byte[] data) {
		final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;
			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText 被签名的字符串
	 * @param encryptKey  密钥
	 * @return 返回被加密后的字符串
	 * @throws Exception
	 */
	public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		byte[] digest = mac.doFinal(text);
		StringBuilder sBuilder = bytesToHexString(digest);
		return sBuilder.toString();
	}

	/**
	 * 转换成Hex
	 * 
	 * @param bytesArray
	 */
	public static StringBuilder bytesToHexString(byte[] bytesArray) {
		if (bytesArray == null) {
			return null;
		}
		StringBuilder sBuilder = new StringBuilder();
		for (byte b : bytesArray) {
			String hv = String.format("%02x", b);
			sBuilder.append(hv);
		}
		return sBuilder;
	}

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptData 被签名的字符串
	 * @param encryptKey  密钥
	 * @return 返回被加密后的字符串
	 * @throws Exception
	 */
	public static String hmacSHA1Encrypt(byte[] encryptData, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		// 完成 Mac 操作
		byte[] digest = mac.doFinal(encryptData);
		StringBuilder sBuilder = bytesToHexString(digest);
		return sBuilder.toString();
	}

	public static String HMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 加密解密
	 * @param bytes
	 * @return
	 */
	public static byte[] encodeOrDecode(byte[] bytes) {
		int length = bytes.length;
		int strCount = 0;
		String _pstr = "HOjslfYI181WE73Rd8fogJhlHGotitGTGZHHash41";
		int pLength = _pstr.length();
		for (int i = 0; i < length; ++i) {
			if (strCount >= pLength) {
				strCount = 0;
			}
			bytes[i] ^= (byte) _pstr.charAt(strCount++);
		}
		return bytes;
	}


	/**
	 * HmacSHA256签名
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String createHmacSha256(String data, String key) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(rawHmac);
		} catch (Exception e) {
			log.error("创建HmacSHA256签名异常：", e);
			return "创建HmacSHA256签名异常：" + e.getMessage();
		}
	}

}
