package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Business;
import com.fline.form.vo.ItemCount;

import java.util.List;
import java.util.Map;

public interface BusinessAccessService extends AbstractNamespaceAccessService<Business> {
	
	List<Map<String, Object>> cumulativeTempItemRequest();
	int counts();
	
	List<Map<String, Object>> serviceItemCount();
	
	List<Map<String, Object>> dayItemCount();
	
	List<Map<String, Object>> monthItemCount();
	
	List<Map<String, Object>> weekItemCount(Map<String,Object> param);
	
	List<Map<String, Object>> dayTempCount();
	
	List<Map<String, Object>> monthCerNoCount();
	
	Pagination<Business>findTempCount(Map<String, Object> parameter,Ordering ord,Pagination<Business> page);
	
	long getTempSum(Map<String, Object> parameter);
	
	int deleteTest();
	
	Pagination<ItemCount> findItemCount(Map<String, Object> parameter,Ordering ord,Pagination<ItemCount> page);

	long getItemSum(Map<String, Object> parameter);
	
	List<Map<String, Object>> deptItemCount(int type,int city);
	
	List<Map<String, Object>> cityItemCount(int type);
	
	List<Map<String, Object>> deptItemCountByDay();
	
	List<Map<String, Object>> dayItemCountChange(int days);
	
	long dayItemCountChangeSum(int days);
	
	List<Map<String, Object>> implementedItem();

	Business findByCode(String code);
}
