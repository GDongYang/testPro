package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.FormData;

public interface FormDataMgmtService {

	/**
	  * @Description : 增加
	  * @author : shaowei
	  * @return
	  */
	FormData create(FormData formData);
	
	/**
	  * @Description : 删除
	  * @author : shaowei
	  * @return
	  */
	void remove(FormData formData);
	
	/**
	  * @Description : 查找所有
	  * @author : shaowei
	  * @return
	  */
	List<FormData> findAll();
	
	/**
	  * @Description : 根据id查找
	  * @author : shaowei
	  * @return
	  */
	FormData findById(long id);
	
	/**
	  * @Description : 更新
	  * @author : shaowei
	  * @return
	  */
	void update(FormData formData);
	
	/**
	  * @Description : 分页查询
	  * @author : shaowei
	  * @return
	  */
	Pagination<FormData> findPagination(Map<String, Object> param, Ordering order,
			Pagination<FormData> page);
}
