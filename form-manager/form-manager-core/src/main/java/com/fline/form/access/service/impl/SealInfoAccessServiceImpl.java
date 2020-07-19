package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.service.SealInfoAccessService;

public class SealInfoAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<SealInfo> implements
        SealInfoAccessService {

	@Override
	public SealInfo findByName(String name) {
		Map<String,Object> param = new LinkedHashMap<String,Object>();
		param.put("name", name);
		List<SealInfo> list =  this.getIbatisDataAccessObject().find("SealInfo.findByName", param);
		return Detect.notEmpty(list) ? list.get(0) : null;
	}
	
	@Override
	public void createSealHistory(SealInfo seif) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("SealInfo.createSealHistory", seif);
	}
	
	
	@Override
	public void updateActive(SealInfo seif) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("SealInfo.updateActive", seif);
	}
	
	
	
	
	@Override
	public void createCertSeal(Map<String,Object> map) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("SealInfo.createCertSeal", map);
	}
	
	@Override
	public void removeCertSeal(long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		remove("removeCertSeal", map);
	}
	
	
	
	@Override
	public List<Map<String,Object>> findCertSeal(String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		return this.getIbatisDataAccessObject().find("SealInfo.findCertSeal", map);
	}
	
	@Override
	public SealInfo findByCertArea(long certId,String areaCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("certId", certId);
		map.put("areaCode", areaCode);
		return findOne("findByCertArea", map);
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.access.service.SealInfoAccessService#findByDepartmentId(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealInfo> findByDepartmentId(long deptId) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList(namespace+".findByDepartmentId", deptId);
	}

	@Override
	public void updateImage(Map<String, Object> params) {
		update("updateImage",params);
	}

}
