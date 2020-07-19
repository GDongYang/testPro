package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.BusinessItem;

public interface BusinessItemAccessService extends AbstractNamespaceAccessService<BusinessItem> {

	BusinessItem findByCode(String code);
	
	BusinessItem findByCodeAndID(String certCode,String businessCode);
	
	List<BusinessItem> findByBusinessId(long businessId);
	
	int deleteTest();
	
}