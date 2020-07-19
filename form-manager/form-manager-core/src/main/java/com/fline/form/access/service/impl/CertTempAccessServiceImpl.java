package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.service.CertTempAccessService;
import com.fline.form.vo.CertTempVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CertTempAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<CertTemp> implements
		CertTempAccessService {

	@Override
	public List<CertTemp> findByInnerCode(String innerCode) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("innerCode", innerCode);
		return find("findByInnerCode", param);
	}


	@Override
	public CertTemp findByCode(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", code);
        List<CertTemp> list = find("findByCode", param);
        return Detect.notEmpty(list) ? list.get(0) : null;
	}

	@Override
	public List<CertTemp> findCropCertTempList(){
		Map<String, Object> param = new HashMap<String, Object>();
		return find("findCropCertTempList", param);
	}

	@Override
	public void updateActive(long id,int active) {
		Map<String,Object> param = new HashMap<>();
		param.put("id",id);
		param.put("active",active);
		update("updateActive",param);
	}


	@Override
	public List<CertTemp> findByIds(String[] ids) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ids", ids);
		return find("findByIds",param);
	}
	
	@Override
	public CertTemp loadFormPageDef(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		CertTemp result = this.findOne("loadCertContent", params);
		return result;
	}

	@Override
	public void saveFormPageDef(long id, String content, String htmlContent, byte[] image) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("content", content);
		params.put("htmlContent", htmlContent);
		params.put("image", image);
		update("updateContent", params);
	}

	@Override
	public void createCertSeal(Map<String,Object> params) {
		 this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("CertTemp.createCertSeal", params);
	}


	@Override
	public List<Map<String,Object>> findRSeal(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("CertTemp.findRSeal", params);
	}


	@Override
	public void insertHistory(CertTemp certTemp) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("CertTemp.insertHistory", certTemp);
	}


	@Override
	public void deleteCertSeal(Map<String, Object> params) {
		remove("deleteCertSeal",params);
	}

	@Override
	public List<CertTempVo> findAllVo() {
	    return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
    }


	@Override
	public long createHighVersion(CertTemp highVersionCert) {
		return (long) this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("CertTemp.createHighVersion", highVersionCert);
	}


	@Override
	public List<CertTemp> findRelateVersion(Map<String, Object> params) {
		return find("findRelateVersion",params);
	}


	@Override
	public void copyToRelations(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("CertTemp.copyToRelations", params);		
	}


	@Override
	public List<CertTemp> findHistotyVersion(Map<String, Object> params) {
		return find("findHistoryVersion",params);
	}


	@Override
	public void createRmaterialTempByKey(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("CertTemp.createRmaterialTempByKey", params);
	}


	@Override
	public void updateRmaterialTempByNewId(Map<String, Object> params) {
		update("updateRmaterialTempByNewId", params);
	}


	@Override
	public List<CertTemp> findAllActive() {
		Map<String, Object> params = new HashMap<>(); 
		return find("findAllActive",params);
	}


	@Override
	public List<CertTemp> findByMaterial(Map<String, Object> params) {
		return  find("findByMaterial",params);
	}


	@Override
	public void removeRelationByTempId(Map<String, Object> params) {
		remove("removeRelationByTempId", params);
	}


	@Override
	public List<CertTemp> findAllByType(Map<String, Object> params) {
		return find("findAllByType",params);
	}


	@Override
	public List<CertTemp> findTempList(Map<String, Object> params) {
		return find(params);
	}


	@Override
	public void createTempList(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("CertTemp.createTempList", params);
	}

    @Override
    public List<String> findByForm(String formCode) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("formCode", formCode);
        return (List<String>)this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("CertTemp.findByForm", param);
    }
	
}
