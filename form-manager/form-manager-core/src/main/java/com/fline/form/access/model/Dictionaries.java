package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class Dictionaries extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * explain
	 */
	private String explain;

	/**
	 * 对应字段
	 */
	private String filed;

	/**
	 * 接口模板
	 */
	private String template;
	

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
