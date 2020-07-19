package com.fline.request;

import com.lowagie.text.pdf.codec.Base64;
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

import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestBusiness {
	

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
	
	
	public static void testCertificate(String cerno,String cername) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://10.48.133.10/yztb/rest/certificate");
		
		String username = "tz_acc_0043";
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
			httppost.addHeader("ApplicantUnit",URLEncoder.encode("guotu 分局", "UTF-8"));
			httppost.addHeader("ApplicantUser",URLEncoder.encode("测试1", "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("busiCode", nonce));
		formparams.add(new BasicNameValuePair("cerNo", cerno));
		formparams.add(new BasicNameValuePair("cerName", cername));
		formparams.add(new BasicNameValuePair("itemCode", "51f59cd4-6f9b-4752-a09c-171564ad2d22"));
		

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
	
	public static void testCertificatePower() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://127.0.0.1:8000/yztb/rest/certificate/power");
		
		String username = "tz_acc_0050";
		String secret = "e10adc3949ba59abbe56e057f20f883e";
		
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("busiCode", UUID.randomUUID().toString()));
		formparams.add(new BasicNameValuePair("cerNo", "332603197311181112"));
		formparams.add(new BasicNameValuePair("cerName", "章明学"));
		formparams.add(new BasicNameValuePair("itemCode", "51f59cd4-6f9b-4752-a09c-171564ad2d22"));
		
		formparams.add(new BasicNameValuePair("username",username));
		formparams.add(new BasicNameValuePair("secret", secret));
		try {
			formparams.add(new BasicNameValuePair("applicantUnit",URLEncoder.encode("测试单位", "UTF-8")));
			formparams.add(new BasicNameValuePair("applicantUser",URLEncoder.encode("测试1", "UTF-8")));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

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
	
	public static void testCertificateFile() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://10.48.133.10/yztb/rest/certificate/file");
		
		String username = "tz_gaj_acc_0029";
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
			httppost.addHeader("ApplicantUnit",URLEncoder.encode("台州市公安局", "UTF-8"));
			httppost.addHeader("ApplicantUser",URLEncoder.encode("台州市公安局自助机", "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("busiCode", nonce));
		formparams.add(new BasicNameValuePair("cerNo", "332603197311181112"));
		formparams.add(new BasicNameValuePair("cerName", "章明学"));
		formparams.add(new BasicNameValuePair("itemCode", "ac5e1bb1-debb-4b0a-95e5-60669e768f89"));
		formparams.add(new BasicNameValuePair("certCode", "tz_rsj_cer_073"));
		

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

	

	private static String getFileBase64(String outputPath) {
		File destFile = new File(outputPath);
		InputStream in = null;
		String certFile = null;
		try {
			in = new FileInputStream(destFile);
			byte[] bytes = new byte[in.available()];
			int length = in.read(bytes);
			certFile = Base64.encodeBytes(bytes, 0, length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return certFile;
	}

	public static void main(String[] args) {
		//testCertificate("332603197311181112","章明学");
		testCertificatePower();
		//testCertificateFile();
	}


}
