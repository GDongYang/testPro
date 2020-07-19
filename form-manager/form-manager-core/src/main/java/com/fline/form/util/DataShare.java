package com.fline.form.util;

import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.Contants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataShare {

	private static Log logger = LogFactory.getLog(DataShare.class);

//	private static final String SERVER_URL = "http://10.49.132.13";
//	private static final long TIME_OFFSET = 3*60*1000;
//	private static ReadWriteLock lock = new ReentrantReadWriteLock();


	private static String getHzSecret() {
		String requestUrl = "http://10.54.19.90:8085/ESBWeb/servlets/33.1111.zj.appkeyAndrequestToken.SynReq@1.0";
		String result = HttpRequest.sendPostRequest(requestUrl,null);
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getInt("result_code") == 200) {
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			JSONObject data = jsonArray.getJSONObject(0);
			return data.getString("requestSecret");
		}
		return null;
	}
	private static String getZjsSecret(int type) {
		BasicDataSource basicDs = new BasicDataSource();
        basicDs.setDriverClassName("com.mysql.jdbc.Driver");
        basicDs.setUrl("jdbc:mysql://172.23.2.240:8080/fline_yztb?useUnicode=true&characterEncoding=UTF8");
        basicDs.setUsername("liangyc");
        basicDs.setPassword("Liangyc123!@#");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDs);
        String sql = "SELECT REQUEST_SECRET from c_secret2 WHERE KEY_TYPE=4";
        if(type==1){
        	sql = "SELECT REQUEST_SECRET from c_secret WHERE KEY_TYPE=2";
        }
        Map<String, Object> set = jdbcTemplate.queryForMap(sql);
        String setret = (String) set.get("REQUEST_SECRET");
        try {
			basicDs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return setret;
	}

	public static List<Map<String,Object>> getDatas(String url, String params, String additional) {
		return null;
	}
	public static List<Map<String, Object>> getDataFromDb(String sql) {
		BasicDataSource basicDs = new BasicDataSource();
		basicDs.setDriverClassName("com.mysql.jdbc.Driver");
		basicDs.setUrl("jdbc:mysql://59.202.39.73:3306/yztb?useUnicode=true&characterEncoding=UTF8");
		basicDs.setUsername("fdp");
		basicDs.setPassword("fdp!@#");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDs);
		
		List<Map<String, Object>> ml = jdbcTemplate.queryForList(sql);

		try {
			basicDs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ml;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getDatas(String url, Map<String,String> params, String additional) {
		logger.info("【getDatas begin】url:" + url + ",params:" + params);
		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
		long requestTime = System.currentTimeMillis();
		String type = params.get("type");
		String appSecret = "";
		String appKey = Contants.APP_KEY;	//绍兴共享的key
		if("1".equals(type)){
			appSecret = getZjsSecret(1);
			params.remove("type");
			appKey = Contants.APP_KEY2;		//绍兴证照库的key
		}else{
			appSecret = getZjsSecret(2);	
		}
		String sign = MD5.MD5Encode(appKey + appSecret + requestTime);
		logger.info("appKey is: " + appKey);
		logger.info("requestTime is: " + requestTime);
		logger.info("sign is:" + sign );
		logger.info("additional is: " + additional);
		String result = "";
		try {
			Map<String,String> requestParams = new HashMap<>();
			requestParams.put("appKey", appKey);
			requestParams.put("sign", sign);
			requestParams.put("requestTime", requestTime + "");
			//requestParams.put("additional", additional);
			requestParams.putAll(params);
			result = HttpRequest.post(url,requestParams);
		} catch (Exception e1) {
			throw new RuntimeException(Contants.DATA_SHARE_ERROR + "请求超时");
		}
        logger.info("【getDatas result】" + result);
		if(Detect.notEmpty(result)) {
			result = result.replace("null", "-");
			JSONObject json = JSONObject.fromObject(result);
			if(!"00".equals(json.getString("code"))) {
				throw new RuntimeException(Contants.DATA_SHARE_ERROR + json.getString("msg"));
			}
			if(json.getInt("dataCount") > 0) {
				JSONArray datas = null;
				try {
					datas = json.getJSONArray("datas");
				} catch (Exception e) {
					try {
						String dataStr = json.getString("datas");
						datas = JSONArray.fromObject(dataStr);
					} catch (Exception e1) {
						String dataStr = json.getString("datas");
						resultMap.add(JSONObject.fromObject(dataStr));
					}
				}
				if(Detect.notEmpty(datas)) {
					resultMap.addAll(datas);
				}
			}
		}
		logger.info("【getDatas end】url:" + url + ",params:" + params);
		return resultMap;
	}
	
	public static void main(String[] args) {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("fsfz","330522198908252119");
			params.put("fxm","吴振杰");
			params.put("msfz","330501198804052428");
			params.put("mxm","金莉");
			getDatas("http://10.54.19.90:8085/ESBWeb/servlets/33.1111.zj.birthInfo.SynReq@1.0",
					params,"");
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			Map<String ,String> params = new HashMap<>();
//			params.put("cardId","330219194803272364");
//			params.put("name","吴玉娥");
//			params.put("userName","1");
//			params.put("userCode","1");
//			params.put("idCard","1");
//			params.put("orgCode","1");
//			params.put("orgName","1");
//			params.put("bizCode","1");
//			params.put("bizName","1");
//			String result = HttpRequest.post("http://10.54.19.90:8085/ESBWeb/servlets/33.1111.zjhz.smzx.mzjqueryShjzData.SynReq@1.0",
//					params);
//			System.out.println(URLDecoder.decode(result,"utf-8"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
