package com.fline.form.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClientUtil {

	// 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";
    
    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 30000;
    
    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 30000;
    
    public static String CONTENT_TYPE_KEY = "Content-Type";
    
    public static final String CONTENT_TYPE_URLFORM = "application/x-www-form-urlencoded";
    
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
      * 发送get请求；不带请求头和请求参数
     * 
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url) throws Exception {
        return doGet(url, null, null);
    }
    
    /**
     * 发送get请求；带请求参数
     * 
     * @param url 请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, Object> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     * 
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Entry<String, Object>> entrySet = params.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue() == null? null : entry.getValue().toString());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);
        
        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     * 
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url) throws Exception {
        return doPost(url, null, null);
    }
    
    /**
     * 发送post请求；带请求参数
     * 
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url, Map<String, Object> params) throws Exception {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求；带请求头和请求参数
     * 
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        packageHeader(headers, httpPost);
        // 封装请求参数
        String contentType = headers.get(CONTENT_TYPE_KEY);
        packageParam(params, contentType, httpPost);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * Description: 封装请求头
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     * 
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(Map<String, Object> params, String contentType, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
        	if (CONTENT_TYPE_JSON.equals(contentType)) {
        		StringEntity entity = new StringEntity(JSON.toJSONString(params), ENCODING);
            	entity.setContentEncoding(ENCODING);
            	entity.setContentType(contentType);
            	httpMethod.setEntity(entity);
        	}
        	else {
        		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        		Set<Entry<String, Object>> entrySet = params.entrySet();
        		for (Entry<String, Object> entry : entrySet) {
        			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()==null? null : entry.getValue().toString()));
        		}
        		// 设置到请求的http对象中
        		httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        	}
        }
    }
    
    /**
     * Description: 获得响应结果
     * 
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
            CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);

        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * Description: 释放资源
     * 
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }
    
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
	public static String getYztb(String idnum) {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://172.31.84.143:30123/ycyztb/rest/certificate/file");
		//http://127.0.0.1:8003/yztb/rest/certificate/file
		//http://172.31.84.143:30123/ycyztb/rest/certificate/file
		String username = "tz_acc_0086";
		String secret = "e10adc3949ba59abbe56e057f20f883e";
		String nonce = UUID.randomUUID().toString();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String created = sdf.format(new Date());
		String passwdDigest = MD5.MD5Encode(nonce + "_" + created + "_" + secret);

		httppost.addHeader("Username", username);
		httppost.addHeader("PasswdDigest", passwdDigest);
		httppost.addHeader("Nonce", nonce);
		httppost.addHeader("Created", created);
		try {
			httppost.addHeader("ApplicantUnit", URLEncoder.encode("台州市", "UTF-8"));
			httppost.addHeader("ApplicantUser", URLEncoder.encode("台州市掌上办对接账号", "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("busiCode", nonce));
		formparams.add(new BasicNameValuePair("cerNo", idnum));
		formparams.add(new BasicNameValuePair("cerName", ""));
		formparams.add(new BasicNameValuePair("itemCode",
				"ac5e1bb1-debb-4b0a-95e5-60669e768f89"));
		formparams.add(new BasicNameValuePair("certCode", "tz_gaj_cer_074"));

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpClient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String sendGetYbtx(String formId, String cerNo) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(
					"http://10.75.23.46:8443/tabshare/api/v2/application-form/" + formId
							+ "/img");
			/** 第一种添加参数的形式 */
			/*
			 * uriBuilder.addParameter("name", "root");
			 * uriBuilder.addParameter("password", "123456");
			 */
			/** 第二种添加参数的形式 */
			// 创建参数队列
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("formId", formId));
			formparams.add(new BasicNameValuePair("cerNo", cerNo));
			uriBuilder.setParameters(formparams);

			// 根据带参数的URI对象构建GET请求对象
			HttpGet httpGet = new HttpGet(uriBuilder.build());

			String appId = "test_app_2";
			String appPriKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMi29wM97rpnt6frbpiYs8OIvVQlQUVp12fYneONfiiCPPZNXlSoOMY7FI1dqFVnhZlss8rW7CBkiiVI8PVZ5DxKi+MbHDxVXkkPFkuWYcq+WALfagOMUJtR3M5njP0X4Vi9CE1TAGgdUXEk+kYJQFSWW9n3ILx1KrdYl85Z7EopAgMBAAECgYEAs5z/yRfoBUmBUTRe4RFtdKJuAtnf4hUIHTb8e8CH0ApUMXkk3A851zLRfnv8gojERnq/HdpcrdQqx5jRgE3z7eQ6j2g+1BRLiS1g9teHDLGV+kj55eVTIhXLZkbRymaC6/2GwjlCXFJCZ4JtFdhUg9hLC1/wmOiMy1UJeg/P/jECQQD7zB/mK8zl0YON+42y2fLjPkBu0Cv4pzrAUsUo08Qqw9mZfYJ+AaoF5NH1WmXSqi3vbd1lauaiTAU7TwaUmbOlAkEAzBCTQK4nef4t2Rh0Fxvv3HNscXblSn1FPSse/s83ltVQKxzCCmZHuYlc1laST9UrTly8KGgg30o3qBhlrDJlNQJBAM+QTRtoL9ejBlccbopor5gz0NmIMTcgY4X2tSAasTKvj8i/dbp5lLaXEZy3kAhA0Oz2G/NmVAilsgpZ8oq8ySECQH6EaEdZsi/4XwSSHKeXModDKKDQTih4skzSR01Du/tQFXwlQEiiUSW+/EgHNH86crcK171Vkcvef9NADQlN1WECQQCM8eT8nsbElgk+DWJnZxx/wfmiF2jENJlSMdBIoRPqrefZH6Q1O03NddlchlLvIYJLm+uFWz07bLFS13OwWlDK";
			String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String token = null;
			try {
				token = EncryptionUtil.encrypt(appId + "," + today + ",1", appPriKey);
			} catch (Exception e) {
				e.printStackTrace();
			}

			httpGet.addHeader("token", token);
			httpGet.addHeader("validmode", "api");
			httpGet.addHeader("appId", appId);

			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				result = Base64Util.getBase64FromInputStream(entity.getContent());
			}
		} catch (ClientProtocolException e) {
			System.err.println("Http协议出现问题");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("解析错误");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.err.println("URI解析异常");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO异常");
			e.printStackTrace();
		} finally {
			// 释放连接
			if (null != response) {
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					System.err.println("释放连接出错");
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
