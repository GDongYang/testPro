package com.fline.form.access.model;


import java.util.Date;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "审批事项表", tableName = "C_APPROVE_ITEM")
public class ApproveItem extends LifecycleModel {

	private static final long serialVersionUID = 6097312574701726399L;
	@Column(name = "是否激活", column = "ACTIVE")
	private String active;
	@Column(name = "部门ID", column = "DEPARTMENT_ID")
	private String departmentId;
	//部门名称
	private String departmentName;
	//岗位Id
	private String positionId;
	//岗位名称
	private String positionName;
	
	private String certTempId;
	
	private String unid;
	
	private String areaCode;
	
	private String area;
	
	private Date versionDate;
	
	private String counterpart;
	
	public String getUnid() {
		return unid;
	}
	public void setUnid(String unid) {
		this.unid = unid;
	}
	public String getCertTempId() {
		return certTempId;
	}
	public void setCertTempId(String certTempId) {
		this.certTempId = certTempId;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Date getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
	public String getCounterpart() {
		return counterpart;
	}
	public void setCounterpart(String counterpart) {
		this.counterpart = counterpart;
	}
}
