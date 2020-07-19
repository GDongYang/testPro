package com.fline.form.access.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.yztb.access.model.CertResource;
import com.fline.form.access.service.CertResourceAccessService;

/**
 * 
 * @author wangn
 * 2019-4-28
 * r_cert_resource表Service层实现类
 *
 */
public class CertResourceAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<CertResource> implements
        CertResourceAccessService {

	@Override
	public void removeByCert(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tempCode", code);
		remove("removeByCert", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByGroup() {
	    return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findByGroup");
    }

	@Override
	public List<Map<String, Object>> findByMap(Map<String, Object> param) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findByMap",param);
	}

	
}
