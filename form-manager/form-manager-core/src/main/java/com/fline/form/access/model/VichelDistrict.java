package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class VichelDistrict extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * deptCode
	 */
	private String deptCode;

	/**
	 * deptName
	 */
	private String deptName;

	/**
	 * deptFullName
	 */
	private String deptFullName;

	/**
	 * sealName
	 */
	private String sealName;

	/**
	 * license
	 */
	private String license;

	/**
	 * deptLevel
	 */
	private String deptLevel;

	/**
	 * address
	 */
	private String address;

	/**
	 * parentCode
	 */
	private String parentCode;

	public String getDeptCode() {
		return deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDeptFullName() {
		return deptFullName;
	}

	public String getSealName() {
		return sealName;
	}

	public String getLicense() {
		return license;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public String getAddress() {
		return address;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDeptFullName(String deptFullName) {
		this.deptFullName = deptFullName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}


}
