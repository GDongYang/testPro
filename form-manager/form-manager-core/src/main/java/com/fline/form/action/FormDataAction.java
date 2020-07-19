package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.FormData;
import com.fline.form.mgmt.service.FormDataMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class FormDataAction extends AbstractAction implements ModelDriven<FormData> {
	private static final long serialVersionUID = 1L;
	private FormData formData;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	@Resource
	private FormDataMgmtService formDataMgmtService;
	private int pageNum;
	private int pageSize;
	
	public FormData getFormData() {
		return formData;
	}
	public void setFormData(FormData formData) {
		this.formData = formData;
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String findPage() {
		Pagination<FormData> page= new Pagination<FormData>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		page = formDataMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	public String create() {
		formDataMgmtService.create(formData);
		return SUCCESS;
	}
	
	public String update() {
		formDataMgmtService.update(formData);
		return SUCCESS;
	}
	
	public String remove() {
		formDataMgmtService.remove(formData);
		return SUCCESS;
	}
	
	public String findById() {
		FormData data = formDataMgmtService.findById(formData.getId());
		dataMap.put("list", data);
		return SUCCESS;
	}
	
	@Override
	public FormData getModel() {
		if(formData == null) {
			formData= new FormData();
		}
		return formData;
	}
}
