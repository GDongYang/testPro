package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ApproveItem;
import com.fline.form.access.model.CountryDo;

public interface ApproveItemAccessService extends
		AbstractNamespaceAccessService<ApproveItem> {
	public Pagination<ApproveItem> findPaginationTable(
			Map<String, Object> param, Ordering order,
			Pagination<ApproveItem> page);

	Integer findCountrycount();

	public void createPositionApproveItem(Map<String, Object> param);

	public void createApproveItemTemp(Map<String, Object> param);

	public void deletePositionApproveItem(Map<String, Object> param);

	public void deleteApproveItemTemp(Map<String, Object> param);

	List<CountryDo> findCountry(Map<String, Object> param);

	public List<String> findRTemp(Map<String, Object> param);

	public List<String> findPositionId(Map<String, Object> param);

	public List<String> findPositionName(Map<String, Object> param);

	public ApproveItem findApproveItemCount(Map<String, Object> param);

	List<ApproveItem> findApproveItemByPositionId(long positionId);

	public void createNanWeiApproveItem(Map<String, Object> param);

	public ApproveItem findByCode(String code);

}
