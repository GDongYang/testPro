package com.fline.form.mgmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fline.form.mgmt.service.Cacheable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.access.service.ServiceAccountAccessService;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.ServiceAccountMgmtService;
import com.fline.form.util.MD5Util;
import com.fline.form.vo.BusinessContext;
@Service("serviceAccountMgmtService")
public class ServiceAccountMgmtServiceImpl implements ServiceAccountMgmtService, Cacheable {

	private Log logger = LogFactory.getLog(ServiceAccountMgmtServiceImpl.class);
	@Resource
	private ServiceAccountAccessService serviceAccountAccessService;
	@Resource
	private DataCacheService dataCacheService;

	private static final ThreadLocal<BusinessContext> logonContext = new ThreadLocal<BusinessContext>();

	public BusinessContext getCurrentContext() {
		return logonContext.get();
	}

	@Override
	public Pagination<ServiceAccount> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ServiceAccount> page) {
		return serviceAccountAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(ServiceAccount serviceAccount) {
		serviceAccountAccessService.update(serviceAccount);

		//将业务账号添加到redis缓存中
		dataCacheService.setServiceAccount(serviceAccount.getCode(),serviceAccount);
	}

	@Override
	public void remove(ServiceAccount serviceAccount) {
		serviceAccountAccessService.remove(serviceAccount);
		//从redis中删除实体
		dataCacheService.removeRedis(KeyConstant.YZTB_SERVICE_ACCOUNT,serviceAccount.getCode());
	}

	@Override
	public ServiceAccount create(ServiceAccount serviceAccount) {
		ServiceAccount account= serviceAccountAccessService.create(serviceAccount);
		return account;
	}

	@Override
	public ServiceAccount findById(long id) {
		return serviceAccountAccessService.findById(id);
	}

	public void passWordReset(String id, String password) {
		serviceAccountAccessService.passWordReset(id, password);

		ServiceAccount account= findById(Long.parseLong(id));
		//将业务账号添加到redis缓存中
		dataCacheService.setServiceAccount(account.getCode(),account);

	}

	public List<ServiceAccount> findList(Map<String, Object> param) {
		return serviceAccountAccessService.find(param);
	}

	@Override
	public int login(String accountCode, String passwdDigest, String nonce, String created, 
			String applicantUnit, String applicantUser) {
		if (accountCode == null || accountCode.equals("")) {
			return -1;
		}
		ServiceAccount account = serviceAccountAccessService.findByCode(accountCode);
		if (account==null) {
			return -1;
		}
		String passwdString = MD5Util.MD5(nonce + "_" + created + "_" + account.getPassword());
		if (!passwdDigest.equalsIgnoreCase(passwdString)) {
			return -2;
		}
		if ((applicantUnit == null || applicantUnit.equals("")
				|| applicantUser == null || applicantUser.equals("") )) {
			return -3;
		}
		
		logger.info("Service Account login success:" + account.getCode());
		
		BusinessContext context = new BusinessContext();
		context.setAccount(account);
		context.setApplicantUnit(applicantUnit);
		context.setApplicantUser(applicantUser);
		logonContext.set(context);
		return 1;
	}
	
	@Override
	public int login(String accountCode, String secret, String applicantUnit, String applicantUser) {
		if (accountCode == null || accountCode.equals("")) {
			return -1;
		}
		ServiceAccount account = serviceAccountAccessService.findByCode(accountCode);
		if (account==null) {
			return -1;
		}
		if(!account.getPassword().equals(secret)) {
			return -2;
		}
		if ((applicantUnit == null || applicantUnit.equals("") 
				|| applicantUser == null || applicantUser.equals("") )) {
			return -3;
		}
		
		logger.info("Service Account login success:" + account.getCode());
		
		BusinessContext context = new BusinessContext();
		context.setAccount(account);
		context.setApplicantUnit(applicantUnit);
		context.setApplicantUser(applicantUser);
		logonContext.set(context);
		return 1;
	}
	
	public void updateIP(long id, String ipaddress) {
		serviceAccountAccessService.updateIP(id, ipaddress);

		ServiceAccount account= findById(id);
		//将业务账号添加到redis缓存中
		dataCacheService.setServiceAccount(account.getCode(),account);
	}

	@Override
	public void logout() {
		logonContext.remove();
	}

	@Override
	public void refreshCache() {
	    dataCacheService.clearServiceAccount();
	    dataCacheService.setServiceAccounts(serviceAccountAccessService.findAllVo());
    }

	@Override
	public void createToCache(ServiceAccount serviceAccount) {
		//将业务账号添加到redis缓存中
		dataCacheService.setServiceAccount(serviceAccount.getCode(),serviceAccount);
	}

}
