package com.fline.form.mgmt.service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.AccessLog;

import java.util.Map;

public interface AccessLogMgmtService {
	Pagination<AccessLog> findPagination(Map<String, Object> param,
			Ordering order, Pagination<AccessLog> page);

	void update(AccessLog accessLog);

	void remove(AccessLog accessLog);

	AccessLog create(AccessLog accessLog);

	AccessLog findById(long id);

}
