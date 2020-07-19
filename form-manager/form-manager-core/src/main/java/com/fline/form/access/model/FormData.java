package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;

/**
 * 表单数据
 * @author 邵炜
 *
 */
public class FormData extends LifecycleModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973332209717704377L;

	private String businessCode;
	
	private String itemCode;
	
	private String cerNo;
	
	private String data;
	
	private Date createTime;

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
