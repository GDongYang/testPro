package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.FormDataSolrMgmtService;
import com.fline.form.vo.FormDataSolrVo;

public class FormDataSolrAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private FormDataSolrMgmtService formDataSolrMgmtService;
	private FormDataSolrVo formDataSolrVo;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private String queryDate;
	private String certcode[];
	private String certNum;
	private String dataBusinessCode;
	private String startDate;
	private String endDate;
	private String formBusinessCode;

	public String getFormBusinessCode() {
		return formBusinessCode;
	}

	public void setFormBusinessCode(String formBusinessCode) {
		this.formBusinessCode = formBusinessCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDataBusinessCode() {
		return dataBusinessCode;
	}

	public void setDataBusinessCode(String dataBusinessCode) {
		this.dataBusinessCode = dataBusinessCode;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String[] getCertcode() {
		return certcode;
	}

	public void setCertcode(String[] certcode) {
		this.certcode = certcode;
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

	public void setFormDataSolrMgmtService(FormDataSolrMgmtService formDataSolrMgmtService) {
		this.formDataSolrMgmtService = formDataSolrMgmtService;
	}

	public FormDataSolrVo getFormDataSolrVo() {
		return formDataSolrVo;
	}

	public void setFormDataSolrVo(FormDataSolrVo formDataSolrVo) {
		this.formDataSolrVo = formDataSolrVo;
	}
	
	public String findPage() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		if (certcode != null && certcode.length > 0) {
			if (!"".equals(certcode[0])) {
				param.put("certTempCode", certcode[0]);
			}
		}
		if (Detect.notEmpty(formBusinessCode)) {
			param.put("formBusinessCode", formBusinessCode);
		}
		if (Detect.notEmpty(certNum)) {
			param.put("certNum", certNum);
		}
		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}
		dataMap = formDataSolrMgmtService.findPagination(param);
		return SUCCESS;
	}
	
	public String findByBusinessCode() {
		Map<String, Object> param = new HashMap<>();
		if (Detect.notEmpty(formBusinessCode)) {
			param.put("formBusinessCode", formBusinessCode);
		}
		dataMap = formDataSolrMgmtService.findPagination(param);
		return SUCCESS;
	}

}
