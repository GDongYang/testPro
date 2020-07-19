package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Business;
import com.fline.form.access.service.BusinessAccessService;
import com.fline.form.vo.ItemCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Business> implements
		BusinessAccessService {

	@Override
	public List<Map<String, Object>> cumulativeTempItemRequest() {
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.cumulativeTempItemRequest");
		return ListMapObj;
	}

	@Override
	public int counts() {
		return (int) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForObject("Business.count");
	}
	@Override
	public List<Map<String, Object>> serviceItemCount(){
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.serviceItemCount");
		return ListMapObj;
	}

	@Override
	public List<Map<String, Object>> dayItemCount() {
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.dayItemCount");
		return ListMapObj;
	}

	@Override
	public List<Map<String, Object>> monthItemCount() {
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.monthItemCount");
		return ListMapObj;
	}
	
	@Override
	public List<Map<String, Object>> monthCerNoCount(){
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.monthCerNoCount");
		return ListMapObj;
	}
	
	@Override
	public List<Map<String, Object>> weekItemCount(Map<String, Object> param) {
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.weekItemCount",param);
		return ListMapObj;
	}

	@Override
	public List<Map<String, Object>> dayTempCount() {
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.dayTempCount");
		return ListMapObj;
	}

	@Override
	public Pagination<Business> findTempCount(Map<String, Object> parameter,Ordering ord,Pagination<Business> page) {
		return this.findPagination("findTempCount", parameter, ord, page);
	}
	
	@Override
	public long getTempSum(Map<String, Object> parameter) {
		return count("findTempCount_sum", parameter);
	}
	
	@Override
	public int deleteTest() {
		Map<String,Object> params = new HashMap<String,Object>();
		return remove("deleteTest",params);
	}
	
	@Override
	public Pagination<ItemCount> findItemCount(Map<String, Object> parameter,Ordering ord,Pagination<ItemCount> page) {
		return this.getIbatisDataAccessObject().findPagination("Business.findItemCount", parameter, ord, page);
	}
	
	@Override
	public long getItemSum(Map<String, Object> parameter) {
		return count("findItemCount_sum", parameter);
	}
	
	@Override
	public List<Map<String, Object>> deptItemCount(int type,int city){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("type", type);
		param.put("city", city);
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.deptItemCount",param);
		return ListMapObj;
	}
	
	@Override
	public List<Map<String, Object>> cityItemCount(int type){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("type", type);
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.cityItemCount",param);
		return ListMapObj;
	}
	
	@Override
	public List<Map<String, Object>> deptItemCountByDay(){
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.deptItemCountByDay");
		return ListMapObj;
	}
	
	@Override
	public List<Map<String, Object>> dayItemCountChange(int days){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("days", days);
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.dayItemCountChange",param);
		return ListMapObj;
	}
	
	@Override
	public long dayItemCountChangeSum(int days) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("days", days);
		return count("dayItemCountChange_sum",param);
	}
	
	@Override
	public List<Map<String, Object>> implementedItem(){
		List<Map<String, Object>> ListMapObj = (List<Map<String, Object>>) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Business.implementedItem");
		return ListMapObj;
	}

	@Override
	public Business findByCode(String code) {
		Map<String, Object> param = new HashMap<>();
		param.put("code", code);
		List<Business> list = find(param);
		return Detect.notEmpty(list) ? list.get(0) : null;
	}

}
