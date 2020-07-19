package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.SealInfo;

public interface SealInfoAccessService extends AbstractNamespaceAccessService<SealInfo> {
	public SealInfo findByName(String name);
	
	void createCertSeal(Map<String,Object> map);
	void createSealHistory (SealInfo seif);
	
	void updateActive (SealInfo seif);
	
	void removeCertSeal(long id);
	
	List<Map<String,Object>> findCertSeal(String code);

	SealInfo findByCertArea(long certId,String areaCode);
	
	List<SealInfo> findByDepartmentId(long deptId);
	
	//更新图片
	void updateImage(Map<String,Object> params);
}
