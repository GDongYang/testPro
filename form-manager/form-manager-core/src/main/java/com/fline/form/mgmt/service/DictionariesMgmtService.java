package com.fline.form.mgmt.service;

import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Dictionaries;

public interface DictionariesMgmtService {
	Pagination<Dictionaries> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Dictionaries> page);

	void update(Dictionaries dictionaries);

	void remove(Dictionaries dictionaries);

	Dictionaries create(Dictionaries dictionaries);

	Dictionaries findById(long id);

}
