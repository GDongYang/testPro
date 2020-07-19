package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Secret;
import com.fline.form.access.service.SecretAccessService;
import com.fline.form.vo.SecretVo;

public class SecretAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<Secret> implements
        SecretAccessService {

	@Override
	public List<SecretVo> findAllVo() {
		 return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
	                .queryForList(namespace + ".findAllVo");
	}

	@Override
	public boolean isExists(String appKey) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appKey", appKey);
		return count("isExists", param) > 0;
	}

}
