package com.fline.form.mgmt.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 大数据操作接口
 * 
 * @author Administrator
 *
 */
public interface FdpClientMgmtService {

	boolean writeFile(String key, Map<String, String> labels, byte[] content);

	byte[] readFile(String key);

	//将object对象存入solr
	boolean insertDataIntoSolr(String tableName, Object data);
	
	JSONArray queryDataFromSolr(String sql);
	
	//写入表单业务数据
	boolean createFormInfo(Map<String, Object> data);
	
	JSONObject loadFormInfo(String formBusiCode);
	
	boolean saveFormData(Map<String, Object> data);
	
	JSONObject loadFormData(String formBusiCode);
	
	boolean savePayment(Object data);
	
	JSONArray queryPayment(String sql);

    boolean saveCallbackInfo(Object data);

    String queryCallbackInfo(String generatedUnicode);
    
    String finishCallbackInfo(String generatedUnicode);

    boolean saveProjectInfo(Object data);
    
    String queryProjectInfo(String projectId);

    boolean saveProjectLifecycle(Object data);

	boolean insertSmsIntoSolr(String tableName, Map<String, Object> param);
	
	String queryProjectInfoByfCode(String formBusiCode);
	
	String queryPayInfo(String noticeno, String ywcode);
	
	public boolean savePayResult(Object data);
	
	boolean savePayInfo(Object data);
	
	String queryRefundInfo(String noticeno, String ywcode);

	boolean saveQuestionFeedbackVo(Object data);

    boolean saveRequestLog(Object data);

    JSONObject loadTempFormData(String projId);

	boolean tempSaveFormData(Map<String, Object> formData);
}
