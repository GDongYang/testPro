package com.fline.form.access.service.impl;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Code;
import com.fline.form.access.service.CodeAccessService;

public class CodeAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Code> implements CodeAccessService {

	@Override
	public String findContents(Map<String, Object> map) {
		List<Code> list = find("findContents",map);
		if(list==null) return null;
		else  return list.get(0).getContents();
	}

	
}