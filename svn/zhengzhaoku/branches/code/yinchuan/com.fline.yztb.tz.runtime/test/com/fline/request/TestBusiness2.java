package com.fline.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestBusiness2 {
	

	public static String MD5(String s){
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
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
            e.printStackTrace();
            return null;
        }
    } 
	
	
	public static void testCertificateFile() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://10.48.133.10/yztb/rest/certificate/file");
		
		String username = "tz_acc_0040";
		String secret = "e10adc3949ba59abbe56e057f20f883e";
		String nonce = UUID.randomUUID().toString();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String created = sdf.format(new Date());
		String passwdDigest = MD5(nonce + "_" + created + "_" + secret);
		
		httppost.addHeader("Username", username);
		httppost.addHeader("PasswdDigest", passwdDigest);
		httppost.addHeader("Nonce", nonce);
		httppost.addHeader("Created", created);
		try {
			httppost.addHeader("ApplicantUnit",URLEncoder.encode("台州市行政服务中心", "UTF-8"));
			httppost.addHeader("ApplicantUser",URLEncoder.encode("台州市行政服务中心自助机", "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("busiCode", nonce));
		formparams.add(new BasicNameValuePair("cerNo", "330511195402144214"));
		formparams.add(new BasicNameValuePair("cerName", ""));
		formparams.add(new BasicNameValuePair("itemCode", "8b13bcbe-36a8-4411-95c1-45e996264460"));
		formparams.add(new BasicNameValuePair("certCode", "tz_mzj_cer_068"));
		

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpClient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					System.out.println("--------------------------------------");
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
	}

	public static void main(String[] args) {
		testCertificateFile();
	}


}
