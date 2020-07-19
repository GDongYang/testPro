package com.fline.form.util;

import com.feixian.tp.common.util.Detect;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataShareBak {
	
	private static final String SERVER_URL = "http://10.49.132.13";
	private static final String APP_KEY = "";
	private static final String APP_SECRET = "";
	private static final long TIME_OFFSET = 3*60*1000;
	private static String requestSecret;
	private static String refreshSecret;
	private static long refreshSecretEndTime = 0;
	private static long requestSecretEndTime = 0;
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private static String refreshTokenByKey() {
		long requestTime = System.currentTimeMillis();
		if(requestSecretEndTime - TIME_OFFSET > requestTime) {
			return requestSecret;
		}
		String sign = MD5.MD5Encode(APP_KEY+APP_SECRET+requestTime);
		String param = "appKey=" + APP_KEY + "&sign=" + sign + "&requestTime=" + requestTime;
		String result = HttpRequest.sendPostRequest(SERVER_URL+"/gateway/app/refreshTokenByKey.htm",param);
		System.out.println("【refreshTokenByKey】:"+result);
		if(Detect.notEmpty(result)) {
			JSONObject json = JSONObject.fromObject(result);
			JSONObject datas = json.getJSONObject("datas");
			if(datas != null) {
				refreshSecret = datas.getString("refreshSecret");
				refreshSecretEndTime = datas.getLong("refreshSecretEndTime");
				requestSecret = datas.getString("requestSecret");
				requestSecretEndTime = datas.getLong("requestSecretEndTime");
			}
		}
		return requestSecret;
	}
	
	private static String refreshTokenBySec() {
		long requestTime = System.currentTimeMillis();
		if(requestSecretEndTime - TIME_OFFSET > requestTime) {
			return requestSecret;
		}
		String sign = MD5.MD5Encode(APP_KEY+refreshSecret+requestTime);
		String param = "appKey=" + APP_KEY + "&sign=" + sign + "&requestTime=" + requestTime;
		String result = HttpRequest.sendPostRequest(SERVER_URL+"/gateway/app/refreshTokenBySec.htm",param);
		System.out.println("refreshTokenBySec" + result);
		if(Detect.notEmpty(result)) {
			JSONObject json = JSONObject.fromObject(result);
			JSONObject datas = json.getJSONObject("datas");
			if(datas != null) {
				refreshSecret = datas.getString("refreshSecret");
				refreshSecretEndTime = datas.getLong("refreshSecretEndTime");
				requestSecret = datas.getString("requestSecret");
				requestSecretEndTime = datas.getLong("requestSecretEndTime");
			}
		}
		return requestSecret;
	}
	
	private static String getSecret() {
		System.out.println("【getSecret】：" +  "requestSecretEndTime="+requestSecretEndTime + "，currentTime="+System.currentTimeMillis());
		if(requestSecretEndTime -TIME_OFFSET > System.currentTimeMillis()) {
			return requestSecret;
		}
		String result = "";
		lock.writeLock().lock();
		try {
			if(refreshSecretEndTime - TIME_OFFSET > System.currentTimeMillis()) {
				result = refreshTokenBySec();
			}
			result = refreshTokenByKey();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getDatas(String url, String params) {
		List<Map<String,Object>> resultMap = new ArrayList<>();
		long requestTime = System.currentTimeMillis();
		String sign = MD5.MD5Encode(APP_KEY + getSecret() + requestTime);
		String result = "";
		lock.readLock().lock();
		try {
			String rquestParams = "appKey=" + APP_KEY + "&sign=" + sign + "&requestTime=" + requestTime + params;
			result = HttpRequest.sendPostRequest(url, rquestParams);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		System.out.println("【getDatas】："+result);
		if(Detect.notEmpty(result)) {
			result = result.replace("null", "\"\"");
			JSONObject json = JSONObject.fromObject(result);
			if(!"00".equals(json.getString("code"))) {
				throw new RuntimeException(json.getString("msg"));
			}
			if(json.getInt("dataCount") > 0) {
				JSONArray datas;
				try {
					datas = json.getJSONArray("datas");
				} catch (Exception e) {
					String dataStr = json.getString("datas");
					datas = JSONArray.fromObject(dataStr);
				}
				if(Detect.notEmpty(datas)) {
					resultMap.addAll(datas);
				}
			}
		}
		return resultMap;
	}
	
	public static void main(String[] args) {
		String url = "";
		String params = "";
		getDatas(url, params);
	}

}
