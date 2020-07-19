package com.fline.form.mgmt.service.impl;


import java.util.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import com.fline.form.mgmt.service.*;
import com.fline.form.mgmt.service.TempInfoService;
import com.fline.form.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import com.feixian.tp.common.util.Detect;


@Service("tempInfoService")
public class TempInfoServiceImpl implements TempInfoService {
	private Log logger = LogFactory.getLog(TempInfoServiceImpl.class);

	private final int threadSize = 20;

	private final ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);


	

	@Resource
	private FormMgmtService formMgmtService;
	@Resource
	private FdpClientMgmtService fdpClientMgmtService;





	@Override
	public Map<String, Object> loadTempFrom(String projId, String formBusiCode){
		if(!Detect.notEmpty(projId)) {
			PersonInfo person = getPersonInfo(formBusiCode);
			projId = person.getItemCode()+";"+person.getAreaCode()+";"+person.getCerNo();
		}
		return formMgmtService.loadTempFrom(projId);
	}

	@Override
	public String tempSaveFromData(String formBusiCode, String projId, String formInfo, String attrInfo) {
		String result = formMgmtService.tempSaveFromData(formBusiCode,projId,formInfo,attrInfo);
		return result;
	}

	@Override
	public PersonInfo getPersonInfo(String formBusiCode) {
		JSONObject formInfo = fdpClientMgmtService.loadFormInfo(formBusiCode);
		//System.out.println(formInfo);
		int i=0;
		while(formInfo==null && i<50) {
			formInfo = fdpClientMgmtService.loadFormInfo(formBusiCode);
			i++;
			//System.err.println(formInfo);
		}
		String cerName = formInfo.getString("APPLY_NAME");
		String cerNo = formInfo.getString("APPLY_CARD_NUMBER");
		String mobile = formInfo.getString("APPLY_MOBILE");
		String userId = formInfo.getString("APPLICANT_UID");
		String itemCode = formInfo.getString("ITEM_CODE");
		String areaCode = formInfo.getString("AREA_CODE");
		// 判断是否为法人，法人则取经办人的身份证和姓名
		String attnName = formInfo.getString("ATTN_NAME");
		String attnIdNo = formInfo.getString("ATTN_IDNO");
		if(!Detect.notEmpty(attnIdNo)) {
			attnIdNo = formInfo.getString("ATTN");
		}
		if(Detect.notEmpty(attnName) && Detect.notEmpty(attnIdNo)) {
			if(Detect.notEmpty(attnName) && attnName.contains("[")) {
				attnName = attnName.substring(2,attnName.length()-2);
			}
			if(Detect.notEmpty(attnIdNo) && attnIdNo.contains("[")) {
				attnIdNo = attnIdNo.substring(2,attnIdNo.length()-2);
			}
			cerName = attnName;
			cerNo = attnIdNo;
		}
		PersonInfo person=new PersonInfo(cerName, cerNo, mobile, itemCode, userId,areaCode);
//		person.setCerName(cerName);
//		person.setCerNo(cerNo);
//		return new PersonInfo(cerName, cerNo, mobile, itemCode, userId,areaCode);
		return  person;
	}


}
