package com.fline.form.mgmt.service;

import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Secret;

public interface SecretMgmtService {
	Pagination<Secret> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Secret> page);

	void update(Secret secret);

	void remove(Secret secret);

	Secret create(Secret secret);

	Secret findById(long id);

	boolean isExists(String appKey);

}
