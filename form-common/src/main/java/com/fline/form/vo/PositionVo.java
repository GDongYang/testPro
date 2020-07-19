package com.fline.form.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author panym
 * @createDate 2019年4月28日上午11:06:44
 */
public class PositionVo implements Serializable{
	
	private static final long serialVersionUID = 5721788218521216310L;

	private long id;					//id
	
	private String name;				//岗位名称
	
	private String code;				//岗位code
	
	private boolean active;				//1-:已激活;0-:未激活
	
	private long departmentId;			//部门id
	
	private String departmentName;		//部门名称
	
	private int type;					//0-普通岗位；1-综合办理岗位
	
	private List<String> itemCodeList;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getItemCodeList() {
		return itemCodeList;
	}
	public void setItemCodeList(List<String> itemCodeList) {
		this.itemCodeList = itemCodeList;
	}
	
}
