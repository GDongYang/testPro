package com.fline.form.mgmt.service;

import java.util.Map;

import com.fline.form.vo.ResponseResult;

public interface FormMgmtService {

	Map<String, Object> createFormByItem(String serviceCode, String formReq);

	Map<String, Object> loadFormData(String formBusiCode);

    String saveFormData(String formBusiCode, String formInfo, String postInfo, String attrInfo);

    String submitFormData(String formBusiCode, String formInfo, String attrInfo, String postInfo);

    Map<String, Object> submitYcsl(String formBusiCode, String formInfoStr, String attrInfo, String postInfo);

    String getTerminalFormUrl(String itemCode, String personInfo, String areaCode);

    String getOfflineFormUrl(String itemInnerCode, String cerNo, String cerName, String formBusiCode);

    void submitOfflineForm(String formBusiCode, String formInfoStr);

    Map<String, Object> loadOfflineFormData(String formBusiCode);

    Map<String,Object> loadTempFrom(String projId);

    Map<String,Object> createFormByIdNum(String serviceCode, String bType, String idNum);

    String tempSaveFromData(String formBusiCode, String projId, String formInfo, String attrInfo);

    String uploadOss(String base64,String path);
}
