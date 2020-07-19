package com.fline.form.mgmt.service;

import java.io.InputStream;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Opinion;

public interface OpinionMgmtService {
	Pagination<Opinion> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Opinion> page);

	void update(Opinion opinion);

	void remove(Opinion opinion);

	void create(Opinion opinion);

	Opinion findById(long id);
	
	InputStream getExcelFile(Map<String,Object> param);

}
