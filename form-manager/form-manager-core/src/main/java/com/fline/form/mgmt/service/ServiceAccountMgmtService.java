package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.vo.BusinessContext;

public interface ServiceAccountMgmtService {
	Pagination<ServiceAccount> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ServiceAccount> page);
	BusinessContext getCurrentContext();

	void update(ServiceAccount serviceAccount);

	void remove(ServiceAccount serviceAccount);

	ServiceAccount create(ServiceAccount serviceAccount);

	ServiceAccount findById(long id);
	
	void passWordReset(String id, String password);
	
	List<ServiceAccount> findList(Map<String,Object> param);
	
	int login(String accountCode, String passwdDigest, String nonce, String created, 
			String applicantUnit, String applicantUser);
	
	void logout();
	
	void updateIP(long id, String ipaddress);
	
	int login(String accountCode, String secret, String applicantUnit, String applicantUser);
	
	void createToCache(ServiceAccount serviceAccount);

}
