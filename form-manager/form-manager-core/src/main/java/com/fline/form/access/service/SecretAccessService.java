package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Secret;
import com.fline.form.vo.SecretVo;

public interface SecretAccessService extends AbstractNamespaceAccessService<Secret> {
	
	List<SecretVo> findAllVo();

	boolean isExists(String appKey);
}
