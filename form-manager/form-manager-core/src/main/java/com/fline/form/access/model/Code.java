package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "编码表", tableName = "C_CODE")
public class Code  extends LifecycleModel{

	private static final long serialVersionUID = 6097312574701726399L;
	@Column(name = "字段名", column = "FIELD_NAME")
	private String fieldName;
	@Column(name = "字段中文名", column = "FIELD_CN")
	private String fieldCN;

	// 内容
	private String contents;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldCN() {
		return fieldCN;
	}
	public void setFieldCN(String fieldCN) {
		this.fieldCN = fieldCN;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

}
