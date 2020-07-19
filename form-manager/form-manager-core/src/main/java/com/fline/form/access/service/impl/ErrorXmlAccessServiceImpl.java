package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.ErrorXml;
import com.fline.form.access.service.ErrorXmlAccessService;

public class ErrorXmlAccessServiceImpl extends AbstractNamespaceAccessServiceImpl<ErrorXml> implements ErrorXmlAccessService {

	@Override
	public void createByRepeat() {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("ErrorXml.createByRepeat");
	}


}
