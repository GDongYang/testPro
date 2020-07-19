package com.fline.form.access.model;

import com.feixian.tp.model.NamespaceModel;

public class DataElement extends NamespaceModel {

	private static final long serialVersionUID = 1L;

	/**
	 * field
	 */
	private String field;

	/**
	 * fieldname
	 */
	private String fieldname;

	/**
	 * dataelementid
	 */
	private String dataelementid;

	/**
	 * dataformat
	 */
	private String dataformat;

	/**
	 * formCode
	 */
	private String formCode;

	public String getField() {
		return field;
	}

	public String getFieldname() {
		return fieldname;
	}

	public String getDataelementid() {
		return dataelementid;
	}

	public String getDataformat() {
		return dataformat;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public void setDataelementid(String dataelementid) {
		this.dataelementid = dataelementid;
	}

	public void setDataformat(String dataformat) {
		this.dataformat = dataformat;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}


}
