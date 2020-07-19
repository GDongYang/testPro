	package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Business;
import com.fline.form.vo.ItemCount;
import com.fline.form.vo.ResponseResult;

public interface BusinessMgmtService {
	Pagination<Business> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Business> page);

	void update(Business business);

	void remove(Business business);

	ResponseResult create(Business business);

	ResponseResult createFile(Business business);

	Business findById(long id);
	
	List<Map<String, Object>> cumulativeTempItemRequest();
	
	int count();
	
	List<Map<String, Object>> serviceItemCount(Map<String, Object> params);

	List<Map<String, Object>> dayItemCount();
	
	List<Map<String, Object>> monthItemCount();
	
	List<Map<String, Object>> weekItemCount(Map<String,Object> param);
	
	List<Map<String, Object>> dayTempCount();
	
	List<Map<String, Object>> monthCerNoCount();
	
	List<Map<String, Object>> deptItemCountByDay(Map<String, Object>params);//获取省级下事项饼图
	
	Map<String, Object> dayItemCountChange(Map<String,Object> params);//获取指定天数内的数据 作为折线图
	
	Map<String, Object> deptItemCount(Map<String, Object>params);//部门排名图
	
	List<Map<String, Object>> cityItemCount(Map<String,Object> params);//市区排名图
	
	Pagination<Business> findTempCount(Map<String, Object> param,
			Ordering order, Pagination<Business> page);
	
	long getTempSum(Map<String, Object> parameter);
	
	String deleteTest();
	
	Pagination<ItemCount> findItemCount(Map<String, Object> parameter,Ordering ord,Pagination<ItemCount> page);

	long getItemSum(Map<String, Object> parameter);
	
	Map<String, Object> findItemCountBySolr(Map<String, Object> parameter);

	Map<String, Object> findBusinessMonitor(Map<String, Object> parameter);

	Map<String, Object> findStaticFromSolr(Map<String, Object> param);
	
	Map<String, Object> findBySql(String sql);

	Map<String, Object> sqlSearch(Map<String, Object> param);

	void lock(String requestId);
	
	void unLock(String requestId);
	
	Map<String, Object> itemCountRank(Map<String, Object> params);
	
	Map<String, Object> findDeptItemCountByCode(Map<String, Object> params);
	
	Map<String, Object> findItemCountByDept(Map<String, Object> params);

}