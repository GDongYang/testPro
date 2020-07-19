package com.fline.form.mgmt.service;


import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Question;

/**
 * 
 * @author wangn
 * 2019-04-28
 * c_question表mgmt层接口  
 */
public interface QuestionMgmtService {
	/**
	 * find方法查找
	 * @param param
	 * @param order
	 * @param page
	 * @return
	 */
	Pagination<Question> findPagination(Map<String, Object> param, Ordering order,
			Pagination<Question> page);
	/**
	 * 修改数据
	 * @param question
	 */
	void update(Question question);
	/**
	 * 删除数据
	 * @param question
	 */
	void remove(Question question);
	/**
	 * 添加数据
	 * @param sealInfo
	 */
	void create(Question sealInfo);
}
