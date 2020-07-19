package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ApproveItem;
import com.fline.form.access.model.CountryDo;
import com.fline.form.access.service.ApproveItemAccessService;

public class ApproveItemAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<ApproveItem> implements
        ApproveItemAccessService {

	@Override
	public Pagination<ApproveItem> findPaginationTable(
			Map<String, Object> param, Ordering order,
			Pagination<ApproveItem> page) {
		return this.findPagination("findTable", param, order, page);
	}

	@Override
	public void createPositionApproveItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert("ApproveItem.createPositionApproveItem", param);
	}

	@Override
	public void createApproveItemTemp(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert("ApproveItem.createApproveItemTemp", param);
	}

	@Override
	public List<String> findRTemp(Map<String, Object> param) {
		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList("ApproveItem.findRTemp", param);
		return listStr;
	}

	@Override
	public void deletePositionApproveItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.update("ApproveItem.deletePositionApproveItem", param);
	}

	@Override
	public void deleteApproveItemTemp(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.update("ApproveItem.deleteApproveItemTemp", param);
	}

	@Override
	public List<String> findPositionName(Map<String, Object> param) {
		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList("ApproveItem.findPositionName", param);
		return listStr;
	}

	@Override
	public List<String> findPositionId(Map<String, Object> param) {
		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList("ApproveItem.findPositionId", param);
		return listStr;
	}

	@Override
	public ApproveItem findApproveItemCount(Map<String, Object> param) {
		ApproveItem itemCount = findOne("findApproveItemCount", param);
		return itemCount;
	}

	@Override
	public List<ApproveItem> findApproveItemByPositionId(long positionId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("positionId", positionId);
		return this.find("findApproveItemByPositionId", param);
	}

	@Override
	public void createNanWeiApproveItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert("ApproveItem.createNanWeiApproveItem", param);
	}

	@Override
	public ApproveItem findByCode(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		return findOne("findByCode", param);
	}

	@Override
	public List<CountryDo> findCountry(Map<String, Object> param) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.queryForList("ApproveItem.findCountry", param);
	}

	@Override
	public Integer findCountrycount() {

		return (Integer) this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForObject("ApproveItem.findCountrycount");
	}

}
