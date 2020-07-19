package com.fline.form.vo;

import java.io.Serializable;

public class VichelDistrictVo implements Serializable{
	
	private static final long serialVersionUID = 5500687327190866761L;
	
	private long id;
	private String deptCode;			//部门编码
	private String deptName;			//部门名称
	private String deptFullName;		//部门全称
	private String sealName;			//
	private String license;				//牌照
	private String deptLevel;			//部门层级
	private String address;				//地址
	private String parentCode;			//父级部门编码
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptFullName() {
		return deptFullName;
	}
	public void setDeptFullName(String deptFullName) {
		this.deptFullName = deptFullName;
	}
	public String getSealName() {
		return sealName;
	}
	public void setSealName(String sealName) {
		this.sealName = sealName;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	
	

}
