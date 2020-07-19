package com.fline.form.vo;

import java.io.Serializable;

public class ItemCount implements Serializable {
	
	private static final long serialVersionUID = -7659595118049973627L;

	private String deptName;
	
	private String itemName;
	
	private long number;
	
	private String area;
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

}
