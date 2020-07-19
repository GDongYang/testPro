package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ApproveItem;
import com.fline.form.action.vo.TreeNode;

public interface ApproveItemMgmtService {
	Pagination<ApproveItem> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ApproveItem> page);

	// void update(ApproveItem item,String[] positions,String[] certTempS);

	// void remove(ApproveItem item);

	// ApproveItem create(ApproveItem item,String[] positions,String[]
	// certTempS);

	ApproveItem findById(long id);

	List<String> findRTemp(String memo);

	List<String> findPositionId(String id);

	String findPositionName(String id);

	Pagination<ApproveItem> findPaginationTable(Map<String, Object> param,
			Ordering order, Pagination<ApproveItem> page);

	ApproveItem findApproveItemCount(long positionId, String itemCode);

	List<ApproveItem> findApproveItemByPositionId(long positionId);

	ApproveItem findByCode(String code);

	ApproveItem save(ApproveItem item);

	void updateAssociatedCerts(ApproveItem item, String[] positions,
			String[] certTempS);

	TreeNode findCountry(int page, int pageSize);

}
