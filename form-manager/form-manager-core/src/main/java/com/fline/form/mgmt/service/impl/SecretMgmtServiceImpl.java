package com.fline.form.mgmt.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Secret;
import com.fline.form.access.service.SecretAccessService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.SecretMgmtService;

@Service("secretMgmtService")
public class SecretMgmtServiceImpl implements SecretMgmtService {

	@Resource
	private SecretAccessService secretAccessService;
	@Resource
	private DataCacheService dataCacheService;
	@Override
	public Pagination<Secret> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Secret> page) {
		return secretAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Secret secret) {
		secretAccessService.update(secret);
		dataCacheService.clearSecret();
		dataCacheService.setSecrets(secretAccessService.findAllVo());
	}

	@Override
	public void remove(Secret secret) {
		secretAccessService.remove(secret);
	}

	@Override
	public Secret create(Secret secret) {
		secret = secretAccessService.create(secret);
		dataCacheService.setSecret(secret);
		return secret;
	}

	@Override
	public Secret findById(long id) {
		return secretAccessService.findById(id);
	}

	@Override
	public boolean isExists(String appKey) {
		return  secretAccessService.isExists(appKey);
	}

}
