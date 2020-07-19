package com.fline.form.mgmt.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.SealLog;
import com.fline.form.access.service.SealLogAccessService;
import com.fline.form.mgmt.service.SealLogMgmtService;
@Service("sealLogMgmtService")
public class SealLogMgmtServiceImpl implements SealLogMgmtService {
	@Resource
	private SealLogAccessService sealLogAccessService;
	
	@Override
	public Pagination<SealLog> findPagination(Map<String, Object> param,
			Ordering order, Pagination<SealLog> page) {
		return sealLogAccessService.findPagination(param, order, page);
	}

	@Override
	public SealLog create(SealLog sealLog) {
		return sealLogAccessService.create(sealLog);
	}
	

}
