package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fline.form.mgmt.service.TempInfoService;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TempInfoServiceImpl implements TempInfoService {
	private Log logger = LogFactory.getLog(TempInfoServiceImpl.class);
	
    @Value("${form-server-url}")
    private String formServerUrl;

    @Override
    public String createFormByPersonal(String serviceCode, String userId, String sfId, String sfName, String mobile, String applyForm) {
    	 Map<String, Object> formReq = new HashMap<>();
         formReq.put("service_code", serviceCode);
         formReq.put("applyer_name", sfName);
         formReq.put("applyer_cert_no", sfId);
         formReq.put("applicant_uid", userId);
         formReq.put("applyer_cert_type", "31");//证件类型，31身份证,51营业执照
         formReq.put("applyForm", applyForm);//1-PC端,2-移动端，9-一窗
         formReq.put("busType", "0");//业务类型
         formReq.put("applyer_mobile", mobile);//联系电话
         String formUrl ="";
         try {
         	 String url = formServerUrl + "/form/createForm/item/" + serviceCode;
         	
             Map<String, String> headers = new HashMap<>();
             headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_JSON);
 			 HttpClientResult result = HttpClientUtil.doPost(url, headers, formReq);
             JSONObject jsonObject = JSONObject.parseObject(result.getContent());
             jsonObject = jsonObject.getJSONObject("data");
             formUrl = jsonObject.getString("formUrl");
             logger.info("创建表单成功：" + formUrl);
         } catch (Exception e) {
        	 logger.error("创建表单异常：" + e.getMessage());
             e.printStackTrace();
         }
         return formUrl;
    }

    @Override
    public String createFormByLegalMan(String serviceCode, String userId, String sfId, String sfName
            , String mobile, String applyForm, String legalMan, String companyType, String companyAddress
            , String attnName, String attnIDNo, String companyRegNumber) {
        Map<String, Object> formReq = new HashMap<>();
        formReq.put("service_code", serviceCode);
        formReq.put("applyer_name", sfName);
        formReq.put("applyer_cert_no", sfId);
        formReq.put("applicant_uid", userId);
        formReq.put("applyer_cert_type", "51");//证件类型，31身份证,51营业执照
        formReq.put("applyForm", applyForm);//1-PC端,2-移动端，9-一窗
        formReq.put("busType", "0");//业务类型
        formReq.put("applyer_mobile", mobile);//经办人联系电话
        formReq.put("attnName", attnName);//经办人姓名
        formReq.put("attnIDNo", attnIDNo);//经办人身份证号
        formReq.put("legalMan", legalMan);//法人姓名
        formReq.put("companyRegNumber", companyRegNumber);//工商注册号
        formReq.put("companyType", companyType);
        formReq.put("companyAddress", companyAddress);
        String formUrl ="";
        try {
            String url = formServerUrl + "/form/createForm/item/" + serviceCode;

            Map<String, String> headers = new HashMap<>();
            headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_JSON);
            HttpClientResult result = HttpClientUtil.doPost(url, headers, formReq);
            JSONObject jsonObject = JSONObject.parseObject(result.getContent());
            jsonObject = jsonObject.getJSONObject("data");
            formUrl = jsonObject.getString("formUrl");
            logger.info("创建表单成功：" + formUrl);
        } catch (Exception e) {
            logger.error("创建表单异常：" + e.getMessage());
            e.printStackTrace();
        }
        return formUrl;
    }

}
