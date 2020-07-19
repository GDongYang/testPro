package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.FormInfoMgmtService;
import com.fline.form.vo.FormInfoVo;

public class FormInfoAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private FormInfoMgmtService formInfoMgmtService;
	private FormInfoVo formInfoVo;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private String queryDate;
	private String certcode;
	private String certNum;
	private String startDate;
	private String endDate;
	private String formCode;

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
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

	public String getCertcode() {
		return certcode;
	}

	public void setCertcode(String certcode) {
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

	public void setFormInfoMgmtService(FormInfoMgmtService formInfoMgmtService) {
		this.formInfoMgmtService = formInfoMgmtService;
	}

	public FormInfoVo getFormInfoVo() {
		return formInfoVo;
	}

	public void setFormInfoVo(FormInfoVo formInfoVo) {
		this.formInfoVo = formInfoVo;
	}
	
	public String findPage() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}

		if (Detect.notEmpty(certcode)) {
			param.put("certTempCode", certcode);
		}
		if (Detect.notEmpty(certNum)) {
			param.put("certNum", certNum);
		}
		if (Detect.notEmpty(formCode)) {
			param.put("formCode", formCode);
		}
		dataMap = formInfoMgmtService.findPagination(param);
		return SUCCESS;
	}
	
}
