package com.fline.form.access.service;

import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Code;

public interface CodeAccessService extends AbstractNamespaceAccessService<Code> {
	String findContents(Map<String,Object> map);
}
