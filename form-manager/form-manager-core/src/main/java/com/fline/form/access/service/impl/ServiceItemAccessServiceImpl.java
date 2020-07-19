package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ServiceItem;
import com.fline.form.access.service.ServiceItemAccessService;

public class ServiceItemAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<ServiceItem> implements
		ServiceItemAccessService {

	@Override
	public Pagination<ServiceItem> findPaginationTable(
			Map<String, Object> param, Ordering order,
			Pagination<ServiceItem> page) {
		return this.findPagination("findTable", param, order, page);
	}

	@Override
	public void createPositionServiceItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert("ServiceItem.createPositionServiceItem", param);
	}

	@Override
	public void createServiceItemTemp(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert("ServiceItem.createServiceItemTemp", param);
	}

	@Override
	public List<String> findRTemp(Map<String, Object> param) {
		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList("ServiceItem.findRTemp", param);
		return listStr;
	}

	@Override
	public void deletePositionServiceItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.update("ServiceItem.deletePositionServiceItem", param);
	}

	@Override
	public void deleteServiceItemTemp(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.update("ServiceItem.deleteServiceItemTemp", param);
	}

	@Override
	public List<String> findPositionName(Map<String, Object> param) {
		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList("ServiceItem.findPositionName", param);
		return listStr;
	}

	@Override
	public List<String> findPositionId(Map<String, Object> param) {
		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList("ServiceItem.findPositionId", param);
		return listStr;
	}

	@Override
	public ServiceItem findServiceItemCount(Map<String, Object> param) {
		ServiceItem itemCount = findOne("findServiceItemCount", param);
		return itemCount;
	}

	@Override
	public List<ServiceItem> findServiceItemByPositionId(long positionId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("positionId", positionId);
		return this.find("findServiceItemByPositionId", param);
	}

	@Override
	public void createNanWeiServiceItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert("ServiceItem.createNanWeiServiceItem", param);
	}

	@Override
	public ServiceItem findByCode(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		return findOne("findByCode", param);
	}

}
