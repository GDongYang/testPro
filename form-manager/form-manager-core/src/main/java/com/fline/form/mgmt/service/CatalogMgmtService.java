package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Catalog;
import com.fline.form.action.vo.TreeNode;

public interface CatalogMgmtService {
	Pagination<Catalog> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Catalog> page);

	void update(Catalog catalog);

	void remove(Catalog catalog);

	Catalog create(Catalog catalog);

	Catalog findById(long id);

	List<TreeNode> findTree(Map<String, Object> map, long parentId);

	void authorize(Catalog catalog);

	long sequence();

}
