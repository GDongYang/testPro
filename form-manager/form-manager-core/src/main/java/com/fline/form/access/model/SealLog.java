package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class SealLog extends LifecycleModel{
	
	private static final long serialVersionUID = 3181058415950629375L;

	private long deptId;//部门id
	
	private long sealId;//印章id
	
	private String itemName;//事项名称
	
	private String certName;//证件名称
	
	private String cerNo;//查询身份证号
	
	private String busiCode;//业务事项编码
	
	private String username;//操作人
	
	private String sealName;
	
	private String deptName;
	
	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public SealLog() {};
	
	public SealLog(long deptId, long sealId, String itemName, String certName, 
			String cerNo, String busiCode, String username) {
		this.deptId = deptId;
		this.sealId = sealId;
		this.itemName = itemName;
		this.certName = certName;
		this.cerNo = cerNo;
		this.busiCode = busiCode;
		this.username = username;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public long getSealId() {
		return sealId;
	}

	public void setSealId(long sealId) {
		this.sealId = sealId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
