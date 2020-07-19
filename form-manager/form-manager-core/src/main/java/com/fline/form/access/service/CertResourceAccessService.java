package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.yztb.access.model.CertResource;

/**
 * 
 * @author wangn
 * 2019-4-28
 * r_cert_resource表Service层接口
 *
 */
public interface CertResourceAccessService extends AbstractNamespaceAccessService<CertResource> {
	
	void removeByCert(String code);

	List<Map<String, Object>> findByGroup();
	
	List<Map<String, Object>> findByMap(Map<String,Object> param);
}
