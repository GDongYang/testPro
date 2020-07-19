package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.yztb.access.model.FormPage;
import com.fline.form.mgmt.service.FormPageMgmtService;
import com.fline.form.util.Base64Util;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormPageAction extends AbstractAction implements ModelDriven<FormPage> {
	
	private static final long serialVersionUID = -8245847850255885658L;

	private FormPageMgmtService formPageMgmtService;
	
	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;
	
	private String sort;
	
	private String order;
	
	private String departId;

	private FormPage formPage;
	
	private int isActive;
	
	private List<Long> formPageIds;
	
	private List<Long> deptIds;

	private String[] tempCodes;
	
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setFormPageMgmtService(FormPageMgmtService formPageMgmtService) {
		this.formPageMgmtService = formPageMgmtService;
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

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	public void setFormPageIds(List<Long> formPageIds) {
		this.formPageIds = formPageIds;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

    public String[] getTempCodes() {
        return tempCodes;
    }

    public void setTempCodes(String[] tempCodes) {
        this.tempCodes = tempCodes;
    }

    public String save() {
		try {
			formPageMgmtService.create(formPage);
			dataMap.put("msg", "新增成功!");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "新增失败!");
		}
		return SUCCESS;
	}

	public String update() {
		try {
			formPageMgmtService.update(formPage);
			dataMap.put("msg", "修改成功!");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "修改失败!");
		}
		return SUCCESS;
	}

	public String remove() {
		try {
			formPageMgmtService.remove(formPage);
			dataMap.put("msg", "删除成功!");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "删除失败!");
		}
		
		return SUCCESS;
	}
	public String findAll(){
		List<FormPage> listTemp=formPageMgmtService.findAll();
		dataMap.put("result", listTemp);
		return "findAll";
	}
	public String findById() {
		FormPage formPage1 = formPageMgmtService.findById(formPage.getId());
		dataMap.put("formPage", formPage1);
		return SUCCESS;
	}
	
	public String findImagesById() {
		FormPage formPage1 = formPageMgmtService.findImagesById(formPage.getId());
		if (null != formPage1.getAppImage()) {
			dataMap.put("appImage", Base64Util.encode(formPage1.getAppImage()));
		}
		if (null != formPage1.getOnlineImage()) {
			dataMap.put("onlineImage", Base64Util.encode(formPage1.getOnlineImage()));
		}
		if (null != formPage1.getOfflineImage()) {
			dataMap.put("offlineImage", Base64Util.encode(formPage1.getOfflineImage()));
		}
		dataMap.put("formPage", formPage1);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<FormPage> page = new Pagination<FormPage>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("departmentId", departId);
		param.put("nameLike", formPage.getNameLike());
		if(isActive == 1) {//表示需要增加active条件查询
			param.put("active", formPage.isActive());
		}
		Ordering order = new Ordering();
		order.addDesc(sort);
		Pagination<FormPage> pageData = formPageMgmtService.findPagination(param, order, page);
		dataMap.put("page", pageData);
		return SUCCESS;
	}
	
	public String findAllPage() {
		Pagination<FormPage> page = new Pagination<FormPage>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("active", 1);
		Ordering order = new Ordering();
		order.addDesc(sort);
		Pagination<FormPage> pageData = formPageMgmtService.findPagination(param, order, page);
		dataMap.put("page", pageData);
		return SUCCESS;
	}
	
	public String updateActive() {
		String msg = formPageMgmtService.updateActive(formPage.getId());
		dataMap.put("msg", msg);
		return SUCCESS;
	}
	
	public String saveToCache() {
		Map<String, Object> params = new HashMap<>();
		params.put("formPageId", formPage.getId());
	    String msg = formPageMgmtService.saveToCache(params);
	    dataMap.put("msg", msg);
		return SUCCESS;
	}
	public String findList() {
		Map<String,Object> params = new HashMap<>();
		if(isActive == 1) {//表示需要增加active条件查询
			params.put("active", formPage.isActive());
		}
		if(formPage.getDepartmentId() != null && formPage.getDepartmentId() != 0) {
			params.put("deptId", formPage.getDepartmentId());
		}
		List<FormPage> result = formPageMgmtService.findList(params);
		dataMap.put("result", result);
		return SUCCESS;
	}

	/**
	 * @Description: 复制表单到其他的部门
	 * @return String
	 */
	public String copyFormsToOtherDept() {
		Map<String, Object> params = new HashMap<>();
		params.put("formPageIds",formPageIds );
		params.put("deptIds", deptIds);
		String result = formPageMgmtService.copyFormsToOtherDepts(params);
		dataMap.put("msg", result);
		return SUCCESS; 
	}

	public String saveFormTemp() {
        try {
            formPageMgmtService.saveFormTemp(formPage.getCode(), tempCodes);
            dataMap.put("code", 1);
            dataMap.put("msg", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("code", -1);
            dataMap.put("msg", "失败");
        }
        return SUCCESS;
    }
	
	@Override
	public FormPage getModel() {
		if (formPage == null) {
			formPage = new FormPage();
		}
		return formPage;
	}
	
	
}
