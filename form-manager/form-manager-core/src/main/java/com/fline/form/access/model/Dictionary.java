package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class Dictionary extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 对应字段
	 */
	private String field;

	/**
	 * 类型
	 */
	private Integer type;

	public String getField() {
		return field;
	}

	public Integer getType() {
		return type;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setType(Integer type) {
		this.type = type;
	}


}
