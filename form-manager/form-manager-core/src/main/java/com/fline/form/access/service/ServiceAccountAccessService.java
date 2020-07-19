package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.vo.ServiceAccountVo;

import java.util.List;

public interface ServiceAccountAccessService extends
		AbstractNamespaceAccessService<ServiceAccount> {

	void passWordReset(String id, String password);
	
	ServiceAccount findByCode(String code);
	
	void updateIP(long id, String ipaddress);

    List<ServiceAccountVo> findAllVo();
}
