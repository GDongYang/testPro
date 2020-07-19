package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.BusinessItem;
import com.fline.form.access.service.BusinessItemAccessService;

public class BusinessItemAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<BusinessItem> implements
		BusinessItemAccessService {

	@Override
	public BusinessItem findByCode(String code) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("code", code);
		return findOne(parameter);
	}

	@Override
	public BusinessItem findByCodeAndID(String certCode, String businessCode) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("certCode", certCode);
		parameter.put("businessCode", businessCode);
		List<BusinessItem> list = find("findByCodeAndID", parameter);
		if(Detect.notEmpty(list) && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<BusinessItem> findByBusinessId(long businessId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("businessId", businessId);
		return find("findByBusinessId",parameter);
	}
	
	@Override
	public int deleteTest() {
		Map<String,Object> params = new HashMap<String,Object>();
		return remove("deleteTest",params);
	}
	
	

}
