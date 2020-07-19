package com.fline.request;

import com.fline.yztb.mgmt.service.impl.FdpClientMgmtServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRequest {
	private static Log logger = LogFactory.getLog(TestRequest.class);

	public static void main(String[] args) throws InterruptedException {
		TestRequest testRequest=new TestRequest();
		testRequest.runMethod();
	}
	public void runMethod() throws InterruptedException {
		int THREAD_NUM=1;
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
		CountDownLatch lactch = new CountDownLatch(THREAD_NUM);
		System.out.printf("begin");
		//开启多个线程，每个线程调用1000次
		for (int i = 0; i < THREAD_NUM; i++) {
			pool.execute(new TaskRun1(1,lactch));
		}
		lactch.await();
		System.out.printf("finish");
	}
	private  class TaskRun1 implements  Runnable{
		private int rumcount;
		private CountDownLatch latch;

		public TaskRun1(int rumcount,CountDownLatch latch) {
			this.rumcount = rumcount;
			this.latch=latch;
		}

		@Override
		public void run() {
			for (int i = 0; i < rumcount; i++) {
				getData();
				logger.info(Thread.currentThread().getName()+"  has finish "+i);
			}
			latch.countDown();
		}
	}


	private static void getData() {
		String busiCode = UUID.randomUUID().toString();
		StringBuilder sb = new StringBuilder();
		sb.append("&busiCode=" + busiCode);
		sb.append("&cerNo=331004198712221629");
		try {
			sb.append("&cerName=" + URLEncoder.encode("郭文仪", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("&itemCode=27fae164-2d8a-413b-a5cb-bdbc26e9ea34");
		sb.append("&username=tz_acc_0060");
		sb.append("&secret=e10adc3949ba59abbe56e057f20f883e");
		sb.append("&applicantUnit=测试单位");
		sb.append("&applicantUser=测试人员");
//		sb.append("&certCode=tz_gaj_cer_074");
		try {
			sb.append("&otherName=" + URLEncoder.encode("吴永海", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("&otherCerNo=331022198408190031");
//		String result = sendPost("http://127.0.0.1:8000/yztb/rest/certificate", "busiCode=lkuytrxxxxdd123132sxcv&cerNo=330625193606201430&itemCode=tz_it_00055_abcdefgasdas&certCode=tz_gaj_cer_052");
	//String result = sendPost("http://172.26.24.109/yztb/rest/certificate/power3", sb.toString());
//		String result = sendPost("http://127.0.0.1:8000/yztb/rest/certificate/power3", sb.toString());
	//	String result = sendPost("http://127.0.0.1:8000/yztb/rest/certificate/power3", sb.toString());
		String result = sendPost("http://172.26.24.109/yztb/rest/certificate/power", sb.toString());
		logger.info(result);
	}

	private static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			String username = "tz_acc_0024";
			String secret = "e10adc3949ba59abbe56e057f20f883e";
			String nonce = UUID.randomUUID().toString();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String created = sdf.format(new Date());
			String passwdDigest = MD5(nonce + "_" + created + "_" + secret);
			
			conn.setRequestProperty("Username", username);
			conn.setRequestProperty("PasswdDigest", passwdDigest);
			conn.setRequestProperty("Nonce", nonce);
			conn.setRequestProperty("Created", created);
			conn.setRequestProperty("ApplicantUnit", URLEncoder.encode("测试单位", "UTF-8"));
			conn.setRequestProperty("ApplicantUser", URLEncoder.encode("测试人员", "UTF-8"));
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
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

}
