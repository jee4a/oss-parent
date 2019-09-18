package com.jee4a.oss.framework.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	/**
	 * 定义编码格式 UTF-8
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * 定义编码格式 GBK
	 */
	public static final String GBK = "GBK";

	private static final String URL_PARAM_CONNECT_FLAG = "&";

	private static final String EMPTY = "";

	private static int maxTotal = 200;

	private static int maxPerRoute = 20;
	
	private static CloseableHttpClient httpClient ;

	static {
		 PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		 cm.setMaxTotal(maxTotal);
		 cm.setDefaultMaxPerRoute(maxPerRoute);
		 httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 * POST方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String post(String url, Map<String, Object> params, String enc) {
		CloseableHttpResponse response = null;
		String result = EMPTY;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			if (params != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : params.keySet()) {
					paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), enc));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
			} else {
				logger.error("statusCode = " + response.getStatusLine().getStatusCode());
				logger.error("response = " + EntityUtils.toString(response.getEntity()));
			}
		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
		} finally {
			try {
				if (response!=null) {
					response.close();
				}
			} catch (IOException e) {
				 logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
 
 
	public static String postJson(String url, String json, String enc) {
		CloseableHttpResponse response = null;
		String result = EMPTY;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), enc));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
			} else {
				logger.error("statusCode = " + response.getStatusLine().getStatusCode());
				logger.error("response = " + EntityUtils.toString(response.getEntity()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
 

	/**
	 * GET方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	 
	public static String get(String url, Map<String, Object> params, String enc) {
		CloseableHttpResponse response = null;
		String result = EMPTY;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);
		strtTotalURL.append(url);
		try {
			if (params != null && params.keySet().size() != 0) {
	    		if (strtTotalURL.indexOf("?") == -1) {
	    		    strtTotalURL.append("?").append(getUrl(params, enc));
	    		} else {
	    			strtTotalURL.append("&").append(getUrl(params, enc));
	    		}
			}
			URIBuilder builder = new URIBuilder(strtTotalURL.toString());
			URI uri = builder.build();
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), enc));
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	            result = sb.toString();
			} else {
				logger.error("statusCode = " + response.getStatusLine().getStatusCode());
				logger.error("response = " + EntityUtils.toString(response.getEntity()));
			}
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(), e);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	 
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param file
	 * @param params
	 * @param enc
	 * @return
	 */
	public static boolean download(String url, File file, Map<String, Object> params, String enc) {
		StringBuffer strtTotalURL = new StringBuffer(url);
		if (params != null) {
			if (strtTotalURL.indexOf("?") == -1) {
				strtTotalURL.append("?").append(getUrl(params, enc));
			} else {
				strtTotalURL.append("&").append(getUrl(params, enc));
			}
		}
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(strtTotalURL.toString());
			httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			// 执行getMethod
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream =  response.getEntity().getContent();
				fileOutputStream = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(b)) > 0) {
					fileOutputStream.write(b, 0, len);
				}
				return true;
			} else {
				logger.error("statusCode = " + response.getStatusLine().getStatusCode());
			}
		}  catch (IOException e) {
			logger.error(e.getMessage(), e);
		}  finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e2) {
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (Exception e2) {
				}
			}
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return false;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param file
	 * @return
	 */
	public static boolean download(String url, File file) {
		return download(url, file, null, null);
	}

	public static void main(String[] args) {
		download("http://www.cnpps.org/attachement/jpg/site15/20140922/bc305bae4037158a06ea62.jpg", new File("d:/123.jpg"));
	}

	public static HttpClient getClient() {
		return httpClient;
	}

	/**
	 * 据Map生成URL字符串
	 * 
	 * @param map
	 *            Map
	 * @param valueEnc
	 *            URL编码
	 * @return URL
	 */
	private static String getUrl(Map<String, Object> map, String valueEnc) {

		if (null == map || map.keySet().size() == 0) {
			return (EMPTY);
		}
		StringBuffer url = new StringBuffer();
		for (String key : map.keySet()) {
			Object val = map.get(key);
			if (val == null) {
				continue;
			}
			try {
				val = URLEncoder.encode(val.toString(), valueEnc);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			url.append(key).append("=").append(val).append(URL_PARAM_CONNECT_FLAG);
		}
		String strURL = url.toString();
		if (strURL.endsWith(URL_PARAM_CONNECT_FLAG)) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return strURL;
	}
 
}
