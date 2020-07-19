package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class AreaOrg extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * areaCode
	 */
	private String areaCode;

	/**
	 * orgCode
	 */
	private String orgCode;

	public String getAreaCode() {
		return areaCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


}
