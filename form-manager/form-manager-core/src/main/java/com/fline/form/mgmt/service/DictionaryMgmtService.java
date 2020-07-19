package com.fline.form.mgmt.service;

import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Dictionary;

public interface DictionaryMgmtService {
	Pagination<Dictionary> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Dictionary> page);

	void update(Dictionary dictionary);

	void remove(Dictionary dictionary);

	Dictionary create(Dictionary dictionary);

	Dictionary findById(long id);

}
