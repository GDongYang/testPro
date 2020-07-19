package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.Constant;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DataCollectionMgmtService;
import com.fline.form.mgmt.service.DataMgmtService;
import com.fline.form.mgmt.service.DataPlatformMgmtService;
import com.fline.form.vo.*;
import com.fline.yztb.vo.ItemVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataMgmtServiceImpl implements DataMgmtService {

    private Log logger = LogFactory.getLog(DataMgmtServiceImpl.class);

    @Value("${yztb.dataShare.username}")
    private String username;
    @Value("${yztb.dataShare.secret}")
    private String secret;
    @Value("${yztb.dataShare.applicantUnit}")
    private String applicantUnit;

    @Autowired
    private DataPlatformMgmtService dataPlatformMgmtService;
    @Autowired
    private DataCacheService dataCacheService;
    @Autowired
    private DataCollectionMgmtService dataCollectionMgmtService;

    @Override
    public String getPhoto(String formBusiCode) {
        logger.info("getPhoto begin:" + formBusiCode);
        PersonInfo personInfo = getPersonInfo(formBusiCode);
        Map<String, Object> params = new HashMap<>();
        params.put("sfid", personInfo.getCerNo());
        params.put("name", personInfo.getCerName());
        ItemVo itemVo = dataCacheService.getItem(personInfo.getItemCode());
        params.put("itemCode", itemVo.getCode());
        String result = dataPlatformMgmtService.sendRequest(Constant.PHOTO_CODE, params);
        JSONObject resultObj = JSON.parseObject(result);
        if(resultObj.getIntValue("returnCode") == ResponseResult.SUCCESS_CODE) {
            JSONObject certData = resultObj.getJSONObject("data").getJSONArray("certData").getJSONObject(0);
            return certData.getString("photo");
        } else {
            throw new RuntimeException(resultObj.getString("returnMessage"));
        }
    }

    @Override
    public Map<String, Object> check(String formBusiCode, String otherParam) {
        logger.info("check begin:" + formBusiCode);
        JSONObject formInfo = FormInfoContext.get();
        String cerName = formInfo.getString("APPLY_NAME");
        String cerNo = formInfo.getString("APPLY_CARD_NUMBER");
        String formCode = formInfo.getString("FORM_CODE");

        Map<String, Object> params = new HashMap<>();
        params.put("sfId", cerNo);
        params.put("name", cerName);
        if(Detect.notEmpty(otherParam)) {
            JSONObject json = JSON.parseObject(otherParam);
            params.putAll(json);
        }
        String result = dataPlatformMgmtService.sendRequest(formCode, Constant.INTERFACE_VERIFY, params);
        return JSON.parseObject(result);
    }
    
	@Override
	public PersonInfo getPersonInfo(String formBusiCode) {
		//JSONObject formInfo = fdpClientMgmtService.loadFormInfo(formBusiCode);
        JSONObject formInfo = FormInfoContext.get();
        String cerName = formInfo.getString("APPLY_NAME");
		String cerNo = formInfo.getString("APPLY_CARD_NUMBER");
		String mobile = formInfo.getString("APPLY_MOBILE");
        String userId = formInfo.getString("APPLICANT_UID");
        String itemCode = formInfo.getString("ITEM_CODE");
		return new PersonInfo(cerName, cerNo, mobile, itemCode, userId);
	}

	@Override
    public List<CertificateResult> findCertificateData(String formBusiCode, String situationCode, String otherParam, String formData) {
        logger.info("findCertificateData begin:" + formBusiCode);
        PersonInfo personInfo = getPersonInfo(formBusiCode);
        ItemCertificateVo itemCertificateVo = new ItemCertificateVo();
        itemCertificateVo.setCerName(personInfo.getCerName());
        itemCertificateVo.setCerNo(personInfo.getCerNo());
        itemCertificateVo.setOtherParam(otherParam);
        itemCertificateVo.setBusiCode(formBusiCode);
        itemCertificateVo.setItems(new ArrayList<>());
        itemCertificateVo.setFormData(formData);
        ItemSituationVo itemSituationVo = new ItemSituationVo();
        itemSituationVo.setInnerCode(personInfo.getItemCode());
        if (Detect.notEmpty(situationCode)) {
            itemSituationVo.setSituationCodes(Arrays.asList(situationCode.split(",")));
        }
        itemCertificateVo.getItems().add(itemSituationVo);
        return dataCollectionMgmtService.findDataByItems2Jpg(itemCertificateVo);
    }

    @Override
    public JSONArray getSingleData(String certCode, String cerNo, String cerName, String itemCode, String otherParam) {
        return dataCollectionMgmtService.getSingleData(certCode, cerNo, cerName, itemCode, otherParam);
    }

}
