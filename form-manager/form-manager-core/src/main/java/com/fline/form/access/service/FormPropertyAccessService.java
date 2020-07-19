package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.yztb.access.model.FormProperty;
import com.fline.yztb.vo.FormPropertyVo;

public interface FormPropertyAccessService extends AbstractNamespaceAccessService<FormProperty> {
	
	List<FormProperty> findByForm(String formCode, long formVersion);
	
	void removeByForm(String formCode, long formVersion);

    List<FormPropertyVo> findByMaxVersion();

    FormProperty findByFormAndName(String formCode, long formVersion, String name);
    
    List<FormPropertyVo> findVoListByFormInfo(String formCode,long formVersion);
}
