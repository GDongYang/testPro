package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.BusinessItem;

public interface BusinessItemMgmtService {
	Pagination<BusinessItem> findPagination(Map<String, Object> param,
			Ordering order, Pagination<BusinessItem> page);

	void update(BusinessItem businessItem);

	void remove(BusinessItem businessItem);

	BusinessItem create(BusinessItem businessItem);

	BusinessItem findById(long id);
	
	BusinessItem findByCodeAndID(String certCode, String businessCode);
	
	List<BusinessItem> findByBusinessId(long businessId);

}