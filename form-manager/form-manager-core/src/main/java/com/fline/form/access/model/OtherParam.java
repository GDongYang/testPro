package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class OtherParam extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	private String certCode;

	private String paramName;

	private String paramField;

	public String getCertCode() {
		return certCode;
	}

	public String getParamName() {
		return paramName;
	}

	public String getParamField() {
		return paramField;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setParamField(String paramField) {
		this.paramField = paramField;
	}


}
