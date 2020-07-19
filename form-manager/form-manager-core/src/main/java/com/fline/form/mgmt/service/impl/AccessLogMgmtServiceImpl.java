package com.fline.form.mgmt.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.AccessLog;
import com.fline.form.access.service.AccessLogAccessService;
import com.fline.form.mgmt.service.AccessLogMgmtService;

@Service("accessLogMgmtService")
public class AccessLogMgmtServiceImpl implements AccessLogMgmtService {

	@Resource
	private AccessLogAccessService accessLogAccessService;

	@Override
	public Pagination<AccessLog> findPagination(Map<String, Object> param,
			Ordering order, Pagination<AccessLog> page) {
		return accessLogAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(AccessLog accessLog) {
		accessLogAccessService.update(accessLog);
	}

	@Override
	public void remove(AccessLog accessLog) {
		accessLogAccessService.remove(accessLog);
	}

	@Override
	public AccessLog create(AccessLog accessLog) {
		return accessLogAccessService.create(accessLog);
	}

	@Override
	public AccessLog findById(long id) {
		return accessLogAccessService.findById(id);
	}

}
