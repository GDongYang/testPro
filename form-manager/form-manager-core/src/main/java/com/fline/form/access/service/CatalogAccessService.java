package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Catalog;

public interface CatalogAccessService extends AbstractNamespaceAccessService<Catalog> {

	void authorize(Catalog catalog);

	List<Catalog> findByCondition(Map<String, Object> params);

	long getSequence();
}
