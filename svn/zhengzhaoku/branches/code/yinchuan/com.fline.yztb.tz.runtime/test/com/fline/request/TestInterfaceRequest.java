package com.fline.request;

import com.fline.yztb.util.DataShare;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestInterfaceRequest {
	private static Log logger = LogFactory.getLog(TestInterfaceRequest.class);


	@Test
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
		sb.append("&paperid=332623196906010911" );
//		sb.append("&qydm=331004");
		sb.append("&custname=邵宣豪");
		//List<Map<String, Object>> result = DataShare.getDatas("http://10.49.132.13/gateway/api/001008010010161/dataSharing/U4ubjJuda99312s2.htm",sb.toString(),"");

		//String result = sendPost("http://127.0.0.1:8000/yztb/rest/interface/dataget", sb.toString());
		//String result = sendPost("http://172.26.24.109/yztb/rest/interface/dataget", sb.toString());
		String result=sendPost("http://10.48.144.229/open/dataShare/LoanmortgageContract", sb.toString());
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
