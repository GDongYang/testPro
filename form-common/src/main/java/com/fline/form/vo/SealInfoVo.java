package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author panym
 * @createDate 2019年4月28日上午11:09:32
 */
public class SealInfoVo implements Serializable{
	
	private static final long serialVersionUID = 5244039839009243306L;

	private long id;				//id

	private String code;			//印章code
	
	private String name;			//印章名称
	
	private String username;		//印章结果
	
	private String project;			//部门路径···
	
	private Integer departmentId;	//部门ID
	
	private int active;				//1-可见;0-不可见
	
	private Date createTime;		//创建日期
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
