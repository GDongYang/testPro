package com.fline.form.vo;


import java.io.Serializable;

public class AreaOrgVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
