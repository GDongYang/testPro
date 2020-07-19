package com.fline.form.access.service.impl;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Catalog;
import com.fline.form.access.service.CatalogAccessService;

public class CatalogAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Catalog> implements
        CatalogAccessService {

	@Override
	public void authorize(Catalog catalog) {
		getIbatisDataAccessObject().update(namespace, "authorize", catalog);
	}

	@Override
	public List<Catalog> findByCondition(Map<String, Object> params) {
		return this.find("findByCondition", params);
	}

	@Override
	public long getSequence() {
		return getIbatisDataAccessObject().sequence("Catalog");
	}
	

}