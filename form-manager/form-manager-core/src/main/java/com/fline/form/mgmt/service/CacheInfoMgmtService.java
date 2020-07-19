package com.fline.form.mgmt.service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CacheInfo;

import java.util.Map;

public interface CacheInfoMgmtService {
	Pagination<CacheInfo> findPagination(Map<String, Object> param,
			Ordering order, Pagination<CacheInfo> page);

    void refreshCache(String code);

    void refreshAll();

}
