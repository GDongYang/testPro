package com.fline.form.mgmt.service;

import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.SealLog;

public interface SealLogMgmtService {
	Pagination<SealLog> findPagination(Map<String, Object> param,
			Ordering order, Pagination<SealLog> page);

	SealLog create(SealLog sealLog);
	
//	SealLog create(long deptId, long sealId, String itemName, String certName
//			, String cerNo ,long businessItemId);

}
