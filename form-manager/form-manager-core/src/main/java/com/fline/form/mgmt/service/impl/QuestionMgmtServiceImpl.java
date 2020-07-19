package com.fline.form.mgmt.service.impl;


import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Question;
import com.fline.form.access.service.CertTempAccessService;
import com.fline.form.access.service.QuestionAccessService;
import com.fline.form.access.service.SealInfoAccessService;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.mgmt.service.QuestionMgmtService;


/**
 * 
 * @author wangn
 * 2019-04-28
 * c_question表mgmt层实现类  
 */

@Service("questionMgmtService")
public class QuestionMgmtServiceImpl implements QuestionMgmtService {
	@Resource
	private SealInfoAccessService sealInfoAccessService;
	@Resource
	private CertTempAccessService certTempAccessService;
	@Resource
	private QuestionAccessService questionAccessService;
	@Resource
	private DepartmentAccessService departmentAccessService;
	
	/**
	 * find查找方法
	 */
	@Override
	public Pagination<Question> findPagination(Map<String, Object> param, Ordering order, Pagination<Question> page) {
		Pagination<Question> pageData = questionAccessService.findPagination(param, order, page);
		if(pageData.getItems()!=null){
			int i=0;
			for(Question questions :pageData.getItems()){
				Long departmentId = questions.getDepartmentId();
				Department department = departmentAccessService.findById(departmentId);
				pageData.getItems().get(i).setDepartment(department.getName());
				
				
				Long certTempId = questions.getCertTempId();
				CertTemp certTemp = certTempAccessService.findById(certTempId);
				pageData.getItems().get(i).setCertTemp(certTemp.getName());
				i++;
				
			}
		}
		
		return pageData;
	}
	
	

	/**
	 * 修改数据
	 */
	@Override
	public void update(Question question) {
		questionAccessService.update(question);
	}

	/**
	 * 删除数据
	 */
	@Override
	public void remove(Question question) {
		questionAccessService.remove(question);
	}
	
	/**
	 * 添加数据
	 */
	@Override
	public void create(Question question) {
		questionAccessService.create(question);
	}

	

}
