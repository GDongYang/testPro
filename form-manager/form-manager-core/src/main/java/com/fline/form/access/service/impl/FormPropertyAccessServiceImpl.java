package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.yztb.access.model.FormProperty;
import com.fline.form.access.service.FormPropertyAccessService;
import com.fline.yztb.vo.FormPropertyVo;

public class FormPropertyAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<FormProperty> implements
		FormPropertyAccessService {

	@Override
	public List<FormProperty> findByForm(String formCode, long formVersion) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("formCode", formCode);
        param.put("formVersion", formVersion);
        return find(param);
	}

	@Override
	public void removeByForm(String formCode, long formVersion) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("formCode", formCode);
        param.put("formVersion", formVersion);
        remove("removeByForm", param);
	}

	@Override
	public List<FormPropertyVo> findByMaxVersion() {
	    return getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findByMaxVersion");
    }

    @Override
    public FormProperty findByFormAndName(String formCode, long formVersion, String name) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("formCode", formCode);
        param.put("formVersion", formVersion);
        param.put("name", name);
        return findOne(param);
    }

	@Override
	public List<FormPropertyVo> findVoListByFormInfo(String formCode, long formVersion) {
		Map<String, Object> param = new HashMap<>();
		param.put("formCode", formCode);
		param.put("formVersion", formVersion);
		return getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findVoListByFormInfo",param);
	}
	

}
