package com.fline.form.vo;

import java.io.Serializable;

/**
 * 
 * @author panym
 * @createDate 2019年4月28日下午1:45:40
 */
public class ServiceAccountVo implements Serializable{
	
	private static final long serialVersionUID = 1324509409821430274L;

	private long id;				//id
	
	private String name;			//用户类型名
	
	private String code;			//用户code
	
	private String password;		//用户密码(加密后)

	private boolean active;			//1-激活;0-未激活
	
	private String ipaddress;		//ip白名单

	private String username;		//用户名

	private long positionId;		//职务id
	
	private long departmentId;		//部门id
	
	private String departmentName;	//部门名称
	
	private String positionName;	//岗位名称
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getPositionId() {
		return positionId;
	}
	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	
}
