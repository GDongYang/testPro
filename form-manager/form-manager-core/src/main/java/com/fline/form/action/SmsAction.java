package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.SmsMgmtService;
import com.fline.form.vo.SmsVo;

public class SmsAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4683341782034419490L;
	
	private Integer pageNum;

	private Integer pageSize;

	private Map<String, Object> dataMap;

	private SmsVo smsVo;
	@Autowired
	private SmsMgmtService smsMgmtService;

	private String startDate;

	private String endDate;

	private String formCode;

	private String itemCode;

	private String phone;

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getFormCode() {
		return formCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public SmsVo getSmsVo() {
		return smsVo;
	}

	public void setSmsVo(SmsVo smsVo) {
		this.smsVo = smsVo;
	}

	public String findPage() {
		Map<String, Object> param = new HashMap<String, Object>();
		// Ordering order = new Ordering();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);

		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}

		if (Detect.notEmpty(formCode)) {
			param.put("formCode", formCode);
		}
		if (Detect.notEmpty(itemCode)) {
			param.put("itemCode", itemCode);
		}
		if (Detect.notEmpty(phone)) {
			param.put("phone", phone);
		}
		dataMap = smsMgmtService.findPage(param);
		return SUCCESS;
	}

}
