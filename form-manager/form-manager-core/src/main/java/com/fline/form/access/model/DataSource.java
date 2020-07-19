package com.fline.form.access.model;

import com.feixian.tp.model.NamespaceModel;

public class DataSource extends NamespaceModel{
	
	private static final long serialVersionUID = -3643252576744423491L;
	
	private int type;//1-部门；2-证件
	
	private String parentCode;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
