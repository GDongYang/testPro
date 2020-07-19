package com.fline.request;

import com.fline.yztb.mgmt.service.TempInfoService;
import com.fline.yztb.util.DataShare;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataShare {
	
	@Resource
	private TempInfoService tempInfoService;
	
	@Test
	public void testYztb() {
		String busiCode = UUID.randomUUID().toString();
		StringBuilder sb = new StringBuilder();
		sb.append("&busiCode=" + busiCode);
		sb.append("&cerNo=331003199505312171");
		try {
			sb.append("&cerName=" + URLEncoder.encode("叶震宇", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("&itemCode=7add61f3-1aff-488d-9079-7e348b468e01");
		sb.append("&username=tz_acc_0051");
		sb.append("&secret=e10adc3949ba59abbe56e057f20f883e");
		sb.append("&applicantUnit=测试单位");
		sb.append("&applicantUser=测试人员");
		List<Map<String, Object>> listMap = DataShare.getDatas("http://10.49.133.164/gateway/api/001008010010161/windowSystem/83QeYeA4fbf0vaE8.htm",sb.toString(),"");

		System.out.println("result");
	}
	
	private static void testHx() {
		Map<String,String> param = new HashMap<>();
		param.put("certCode","332603197311181112");
	    param.put("personName","章明学");
	    param.put("personType","2");
	    param.put("bizDept","001008010010074");
	    param.put("itemCode","其他-02491-006");   
	    param.put("areaCode","331002");
		param.put("itemId","234423");
		String requestJson = JSONObject.toJSONString (param);
		String encryptRequestJson = HxDesUtil.encDES (requestJson);
		System.out.println(encryptRequestJson);
		try {
			DataShare.getDatas("http://10.49.132.13/gateway/api/001008010010019/dataSharing/9G556V5f4a487v83.htm",
					"&message=" + URLEncoder.encode(encryptRequestJson, "UTF-8"),"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private static void testRedBlack() {
		Map<String,String> param = new HashMap<>();
		param.put("itemCode","02");
		param.put("certCode","332603197311181112");                                                    
		param.put("partyName","章明学");
		param.put("partyType","2");
		param.put("bizDept","001008010010025");
		String requestJson = JSONObject.toJSONString (param);
		String encryptRequestJson = HxDesUtil.encDES (requestJson);
		System.out.println(encryptRequestJson);
		try {
			DataShare.getDatas("http://10.49.132.13/gateway/api/001008010010019/dataSharing/cJ11eMf4w9xHb0Q0.htm",
					"&message=" + URLEncoder.encode(encryptRequestJson, "UTF-8"),"");
//			String result = HttpRequest.sendPostRequest("http://10.49.132.9/interfaceServer/getRedBlack.json",
//					"message=" + URLEncoder.encode(encryptRequestJson, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testFankui() {
		Map<String,String> param = new HashMap<>();
		param.put("entityName","阿里巴巴");
		param.put("uniCode","1000116400");
		//param.put("itemCode","1234");
		//param.put("itemName","关于滴滴出行乱收费");
		//param.put("itemUid","shjds726gsad");
		param.put("type","已惩戒");
		//param.put("result","罚款10万元");
		//param.put("amount","100000");
		//param.put("basis","中华人名共和国刑法第297条");
		param.put("district","331021");
		param.put("dept","001008010010016");
		param.put("itemId","234423");
		param.put("disciplinaryTime","2018-03-03");
		String requestJson = JSONObject.toJSONString (param);
		String encryptRequestJson = HxDesUtil.encDES (requestJson);
		System.out.println(encryptRequestJson);
		try {
			List<Map<String,Object>> datas = DataShare.getDatas("http://10.49.132.13/gateway/api/001008010010019/dataSharing/eWPm2b9k4KjaB4y9.htm",
					"&message=" + URLEncoder.encode(encryptRequestJson, "UTF-8"),"");
			if(datas != null && datas.size() > 0) {
				System.out.println("联合奖惩反馈结果 :" + datas.get(0).get("msg"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("联合奖惩反馈结果 :" + "失败");
		}
	}

	@Test
	public void test() {
	StringBuilder sb=new StringBuilder();
	sb.append("&HTBH=2018331004XS0036372");
	sb.append("&szcs=台州市");
		List<Map<String,Object>> datas=DataShare.getDatas("http://10.49.133.164/gateway/api/001008010010001/dataSharing/x9qXe4795k599IK4.htm",
				sb.toString() ,"");


		System.out.println(datas);
	}
	

	
}
