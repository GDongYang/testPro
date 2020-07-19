package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ServiceItem;

public interface ServiceItemMgmtService {
	Pagination<ServiceItem> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ServiceItem> page);

	// void update(ServiceItem item,String[] positions,String[] certTempS);

	// void remove(ServiceItem item);

	// ServiceItem create(ServiceItem item,String[] positions,String[]
	// certTempS);

	ServiceItem findById(long id);

	List<String> findRTemp(String memo);

	List<String> findPositionId(String id);

	String findPositionName(String id);

	Pagination<ServiceItem> findPaginationTable(Map<String, Object> param,
			Ordering order, Pagination<ServiceItem> page);

	ServiceItem findServiceItemCount(long positionId, String itemCode);

	List<ServiceItem> findServiceItemByPositionId(long positionId);

	ServiceItem findByCode(String code);

	ServiceItem save(ServiceItem item);

	void updateAssociatedCerts(ServiceItem item, String[] positions,
			String[] certTempS);

}
