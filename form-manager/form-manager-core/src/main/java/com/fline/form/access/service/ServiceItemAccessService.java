package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ServiceItem;

public interface ServiceItemAccessService extends
		AbstractNamespaceAccessService<ServiceItem> {
	public Pagination<ServiceItem> findPaginationTable(
			Map<String, Object> param, Ordering order,
			Pagination<ServiceItem> page);

	public void createPositionServiceItem(Map<String, Object> param);

	public void createServiceItemTemp(Map<String, Object> param);

	public void deletePositionServiceItem(Map<String, Object> param);

	public void deleteServiceItemTemp(Map<String, Object> param);

	public List<String> findRTemp(Map<String, Object> param);

	public List<String> findPositionId(Map<String, Object> param);

	public List<String> findPositionName(Map<String, Object> param);

	public ServiceItem findServiceItemCount(Map<String, Object> param);

	List<ServiceItem> findServiceItemByPositionId(long positionId);

	public void createNanWeiServiceItem(Map<String, Object> param);

	public ServiceItem findByCode(String code);
}
