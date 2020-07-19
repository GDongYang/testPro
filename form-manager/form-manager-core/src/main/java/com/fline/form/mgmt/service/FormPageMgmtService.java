package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.yztb.access.model.FormPage;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;

public interface FormPageMgmtService {
	
	Pagination<FormPage> findPagination(Map<String, Object> param, Ordering order, Pagination<FormPage> page);

	void update(FormPage formPage);

	void remove(FormPage formPage);

	FormPage create(FormPage formPage);

	FormPage findById(long id);
	
	FormPage findByCode(String code);
	
	List<FormPage> findAll();
	
	FormPage findImagesById(long id);
	
	Map<String, Object> loadFormProperty(String formCode, long formVersion);
	
	void saveFormPageDef(FormPageParamVo formParam, boolean onlyContent);
	
	/**
	 * @Description: 将表单active置1;增加版本号;将表单中content发布到服务器上
	 * @param id 表单id号
	 * @return String 发布的结果信息
	 */
	String updateActive(long id);//发布
	
	/**
	 * @Description: 刷新单个表单的缓存信息 
	 * @param formPage 指定表单信息
	 */
	String saveToCache(Map<String, Object> params);
	
	List<FormPage> findList(Map<String, Object> params);
	
	String copyFormsToOtherDepts(Map<String, Object> params);


    void saveFormTemp(String formCode, String[] tempCodes);
}
