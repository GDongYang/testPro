package com.fline.form.mgmt.service;


import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.yztb.access.model.CertResource;

/**
 * 
 * @author wangn
 * 2019-4-28
 * r_cert_resource表mgmt层接口
 *
 */
public interface CertResourceMgmtService {
	
	/**
	 * find方法查找
	 * @param param
	 * @param order
	 * @param page
	 * @return
	 */
	Pagination<CertResource> findPagination(Map<String, Object> param, Ordering order,
			Pagination<CertResource> page);

	/**
	 * 修改数据
	 * @param certResource
	 */
	void update(CertResource certResource);
	
	/**
	 * 删除数据
	 * @param certResource
	 */
	void remove(CertResource certResource);

	/**
	 * 添加数据
	 * @param certResource
	 */
	void create(CertResource certResource);

	/**
	 * 
	 * @Description: 刷新单个数据缓存
	 * @param certResource     
	 */
	void createToCache(List<CertResource> certResources);

    void cacheByForm(long formId);
}
