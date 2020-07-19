package com.fline.form.mgmt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.vo.CertificateResult;
import com.fline.form.vo.PersonInfo;

import java.util.List;
import java.util.Map;

public interface DataMgmtService {

    String getPhoto(String formBusiCode);

    Map<String, Object> check(String formBusiCode, String otherParam);
    
    PersonInfo getPersonInfo(String formBusiCode);

    List<CertificateResult> findCertificateData(String formBusiCode, String situationCode, String otherParam, String formData);

    JSONArray getSingleData(String certCode, String cerNo, String cerName, String itemCode, String otherParam);

}
