package com.fline.form.mgmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.FormData;
import com.fline.form.access.service.FormDataAccessService;
import com.fline.form.mgmt.service.FormDataMgmtService;
@Service("FormDataMgmtService")
public class FormDataMgmtServiceImpl implements FormDataMgmtService {

	@Resource
	private FormDataAccessService formDataAccessService;
	
	@Override
	public FormData create(FormData formData) {
		return this.formDataAccessService.create(formData);
	}

	@Override
	public void remove(FormData formData) {
		this.formDataAccessService.remove(formData);
	}

	@Override
	public List<FormData> findAll() {
		return this.formDataAccessService.findAll();
	}

	@Override
	public FormData findById(long id) {
		return this.formDataAccessService.findById(id);
	}

	@Override
	public void update(FormData formData) {
		this.formDataAccessService.update(formData);
	}

	@Override
	public Pagination<FormData> findPagination(Map<String, Object> param, Ordering order, Pagination<FormData> page) {
		return this.formDataAccessService.findPagination(param, order,page);
	}

}
