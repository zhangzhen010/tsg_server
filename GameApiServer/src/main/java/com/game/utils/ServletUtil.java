package com.game.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * http请求工具
 * 
 * @author zhangzhen
 * @date 2017年8月15日
 * @version 1.0
 */
public class ServletUtil {

	private static Logger log = LogManager.getLogger(ServletUtil.class);

	/**
	 * http请求
	 * 
	 * @param url      地址
	 * @param methName 接口名
	 * @param params   参数
	 */
	public static String httpPostSendJson(String url, String methName, HashMap<String, Object> params) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			URL urls = new URL(url + methName);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json");
			out = new BufferedOutputStream(connection.getOutputStream());
			StringBuffer buf = new StringBuffer();
			log.info(JSON.toJSONString(params));
			buf.append(JSON.toJSONString(params));
			out.write(buf.toString().getBytes());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {

				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (Exception e) {
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * http请求
	 * 
	 * @param url    地址
	 * @param params 参数
	 */
	public static String httpPostSendJson(String url, HashMap<String, Object> params) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			URL urls = new URL(url);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json");
			out = new BufferedOutputStream(connection.getOutputStream());
			StringBuffer buf = new StringBuffer();
			buf.append(JSON.toJSONString(params));
			out.write(buf.toString().getBytes());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.info(result);
			}
			return null;
		} catch (Exception e) {
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * http请求
	 * 
	 * @param url      地址
	 * @param methName 接口名
	 * @param params   参数
	 */
	public static String httpPostSend(String url, String methName, List<Parames> params) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			Iterator<Parames> entryItr = params.iterator();
			URL urls = new URL(url + methName);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			out = new BufferedOutputStream(connection.getOutputStream());
			StringBuffer buf = new StringBuffer();
			while (entryItr.hasNext()) {
				Parames en = entryItr.next();
				buf.append(URLEncoder.encode(en.getCanshuName(), "utf-8"));
				buf.append(("=" + URLEncoder.encode(en.getCanshuValue(), "utf-8")));
				if (entryItr.hasNext()) {
					buf.append("&");
				}
			}
			out.write(buf.toString().getBytes());
			// System.out.println("请求参数为:" + buf.toString());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (SocketTimeoutException socketTimeOut) {
			log.error("content out！", socketTimeOut);
			return null;
		} catch (Exception e) {
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * http请求
	 * 
	 * @param url      地址
	 * @param methName 接口名
	 * @param data   参数
	 */
	public static String httpPostSend(String url, String methName, String data) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			URL urls = new URL(url + methName);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			out = new BufferedOutputStream(connection.getOutputStream());

			out.write(data.toString().getBytes());
			// System.out.println("请求参数为:" + buf.toString());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (SocketTimeoutException socketTimeOut) {
			log.error("content out！", socketTimeOut);
			return null;
		} catch (Exception e) {
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * http请求 授权
	 * 
	 * @param url      地址
	 * @param methName 接口名
	 * @param params   参数
	 */
	public static String httpPostOAuthSend(String url, String methName, List<Parames> params) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			Iterator<Parames> entryItr = params.iterator();
			URL urls = new URL(url + methName);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", "OAuth");
			out = new BufferedOutputStream(connection.getOutputStream());
			StringBuffer buf = new StringBuffer();
			while (entryItr.hasNext()) {
				Parames en = entryItr.next();
				buf.append(URLEncoder.encode(en.getCanshuName(), "utf-8"));
				buf.append(("=" + URLEncoder.encode(en.getCanshuValue(), "utf-8")));
				if (entryItr.hasNext()) {
					buf.append("&");
				}
			}
			out.write(buf.toString().getBytes());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (Exception e) {
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * httpget
	 * 
	 * @param url
	 * @param methName
	 * @param parames
	 * @return
	 */
	public static String httpGetSend(String url, String methName, List<Parames> parames) {
		InputStreamReader in = null;
		HttpURLConnection connection = null;
		try {
			String result;
			Iterator<Parames> entryItr = parames.iterator();
			StringBuffer buf = new StringBuffer();
			while (entryItr.hasNext()) {
				Parames en = entryItr.next();
				buf.append(en.getCanshuName());
				buf.append(("=" + en.getCanshuValue()));
				if (entryItr.hasNext()) {
					buf.append("&");
				}
			}
			URL urls = new URL(url + methName + "?" + buf.toString());
			connection = (HttpURLConnection) urls.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * httpsget
	 * 
	 * @param url
	 * @param methName
	 * @param parames
	 * @return
	 */
	public static String httpsGetSend(String url, String methName, List<Parames> parames) {
		InputStreamReader in = null;
		HttpsURLConnection connection = null;
		try {
			String result;
			Iterator<Parames> entryItr = parames.iterator();
			StringBuffer buf = new StringBuffer();
			while (entryItr.hasNext()) {
				Parames en = entryItr.next();
				buf.append(en.getCanshuName());
				buf.append(("=" + en.getCanshuValue()));
				if (entryItr.hasNext()) {
					buf.append("&");
				}
			}
			URL urls = new URL(url + methName + "?" + buf.toString());
			log.error(url + methName + "?" + buf.toString());
			connection = (HttpsURLConnection) urls.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {

				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 发送日志到后台，为了效率，不需要返回，只管发送，post，json
	 * 
	 * @param url      地址
	 * @param dataJson 参数
	 */
	public static String logSend(String url, String dataJson) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			URL urls = new URL(url);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json");
			out = new BufferedOutputStream(connection.getOutputStream());
			StringBuffer buf = new StringBuffer();
			buf.append(dataJson);
			out.write(buf.toString().getBytes());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * http请求
	 * 
	 * @param url      地址
	 * @param params   参数
	 */
	public static String httpPostSendJson(String url, Map<String, Object> params) {
		InputStreamReader in = null;
		BufferedOutputStream out = null;
		HttpURLConnection connection = null;
		try {
			String result;
			URL urls = new URL(url);
			connection = (HttpURLConnection) urls.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json");
			out = new BufferedOutputStream(connection.getOutputStream());
			StringBuffer buf = new StringBuffer();
			buf.append(JSON.toJSONString(params));
			out.write(buf.toString().getBytes());
			out.flush();
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				return result;
			} else {
				log.error("http code:" + response_code);
				in = new InputStreamReader(connection.getErrorStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				log.error(result);
			}
			return null;
		} catch (Exception e) {
			log.error("发送异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static JSONObject httpsPostSendJson(String url, Map<String, String> headMap, Map<String, Object> paraMap) {
		HttpPost httpReq = new HttpPost(url);
		// 创建无需SSL验证的httpClient实例.
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getIgnoeSSLClient();
			httpReq.addHeader("Content-Type", "application/json;charset=UTF-8");
			for (Entry<String, String> headEntry : headMap.entrySet()) {
				httpReq.addHeader(headEntry.getKey(), headEntry.getValue());
			}
			String paraJson = JSON.toJSONString(paraMap);
			httpReq.setEntity(new StringEntity(paraJson));
			JSONObject responseParamPairs = new JSONObject();
			HttpResponse resp = httpclient.execute(httpReq);
			if (null != resp && 200 == resp.getStatusLine().getStatusCode()) {
				responseParamPairs = JSON.parseObject(EntityUtils.toString(resp.getEntity()));
			}
			return responseParamPairs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONObject httpsPostSendString(String url, Map<String, String> paramaters) {
		HttpPost httpReq = new HttpPost(url);
		// 创建无需SSL验证的httpClient实例.
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getIgnoeSSLClient();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (paramaters != null) {
				List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
				BasicNameValuePair bnv;
				for (Map.Entry<String, String> entry : paramaters.entrySet()) {
					bnv = new BasicNameValuePair(entry.getKey(), entry.getValue());
					paramPairs.add(bnv);

				}
				httpReq.setEntity(new UrlEncodedFormEntity(paramPairs, "UTF-8"));
			}
			JSONObject responseParamPairs = new JSONObject();
			HttpResponse resp = httpclient.execute(httpReq);
			if (null != resp && 200 == resp.getStatusLine().getStatusCode()) {
				responseParamPairs = JSON.parseObject(EntityUtils.toString(resp.getEntity()));
			}
			return responseParamPairs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String httpsPostSend(String url, Map<String, Object> paramaters) {
		HttpPost httpReq = new HttpPost(url);
		// 创建无需SSL验证的httpClient实例.
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getIgnoeSSLClient();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (paramaters != null) {
				List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
				BasicNameValuePair bnv;
				for (Map.Entry<String, Object> entry : paramaters.entrySet()) {
					String v;
					if (entry.getValue() instanceof String) {
						v = (String) entry.getValue();
					} else {
						v = String.valueOf(entry.getValue());
					}
					bnv = new BasicNameValuePair(entry.getKey(), v);
					paramPairs.add(bnv);

				}
				httpReq.setEntity(new UrlEncodedFormEntity(paramPairs, "UTF-8"));
			}
			HttpResponse resp = httpclient.execute(httpReq);
			if (null != resp && 200 == resp.getStatusLine().getStatusCode()) {
				return EntityUtils.toString(resp.getEntity());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 创建跳过SSL验证的httpClient实例
	 * 
	 */
	private static CloseableHttpClient getIgnoeSSLClient() throws Exception {
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				return true;
			}
		}).build();
		// 创建httpClient
		CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext)
				.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		return client;
	}

	/**
	 * 获取get请求参数列表
	 * 
	 * @param queryString
	 * @param enc
	 * @return
	 */
	public static Map<String, String[]> getParamsMap(String queryString, String enc) {
		Map<String, String[]> paramsMap = new HashMap<String, String[]>();
		if (queryString != null && queryString.length() > 0) {
			int ampersandIndex, lastAmpersandIndex = 0;
			String subStr, param, value;
			String[] paramPair, values, newValues;
			do {
				ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0) {
					subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
					lastAmpersandIndex = ampersandIndex;
				} else {
					subStr = queryString.substring(lastAmpersandIndex);
				}
				paramPair = subStr.split("=");
				param = paramPair[0];
				value = paramPair.length == 1 ? "" : paramPair[1];
				try {
					value = URLDecoder.decode(value, enc);
				} catch (UnsupportedEncodingException ignored) {
				}
				if (paramsMap.containsKey(param)) {
					values = paramsMap.get(param);
					int len = values.length;
					newValues = new String[len + 1];
					System.arraycopy(values, 0, newValues, 0, len);
					newValues[len] = value;
				} else {
					newValues = new String[] { value };
				}
				paramsMap.put(param, newValues);
			} while (ampersandIndex > 0);
		}
		return paramsMap;
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl    请求地址
	 * @param outputStr     提交的数据
	 * @return 返回微信服务器响应的信息
	 * @throws Exception
	 */
	public static String httpsRequestPost(String requestUrl, String outputStr) throws Exception {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new FsTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
			throw new RuntimeException("链接异常" + ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
			throw new RuntimeException("https请求异常" + e);
		}
	}

	static class FsTrustManager implements X509TrustManager {

		// 检查客户端证书
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		// 检查服务器端证书
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		// 返回受信任的X509证书数组
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public static JSONObject httpsPostSendJson(String url, Map<String, String> headMap, String json) {
		HttpPost httpReq = new HttpPost(url);
		// 创建无需SSL验证的httpClient实例.
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getIgnoeSSLClient();
			httpReq.addHeader("Content-Type", "application/json;charset=UTF-8");
			for (Entry<String, String> headEntry : headMap.entrySet()) {
				httpReq.addHeader(headEntry.getKey(), headEntry.getValue());
			}
			String paraJson = json;
			httpReq.setEntity(new StringEntity(paraJson));
			JSONObject responseParamPairs = new JSONObject();
			HttpResponse resp = httpclient.execute(httpReq);
			if (null != resp && 200 == resp.getStatusLine().getStatusCode()) {
				responseParamPairs = JSON.parseObject(EntityUtils.toString(resp.getEntity()));
				System.out.println("res" + resp);
			}
			return responseParamPairs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
