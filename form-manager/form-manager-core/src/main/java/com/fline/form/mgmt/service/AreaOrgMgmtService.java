package com.fline.form.mgmt.service;

import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.AreaOrg;

public interface AreaOrgMgmtService {
	Pagination<AreaOrg> findPagination(Map<String, Object> param,
			Ordering order, Pagination<AreaOrg> page);

	void update(AreaOrg areaOrg);

	void remove(AreaOrg areaOrg);

	AreaOrg create(AreaOrg areaOrg);

	AreaOrg findById(long id);

}
