package com.fline.form.vo;

import java.io.Serializable;

public class RedisCacheParamVo implements Serializable {

	private static final long serialVersionUID = 815637602281480750L;

	private int interFaceId;// 对应的接口表的id
	private String paramName;
	private String paramPosition;
	private String paramType;
	private String required;// 是否必填
	private String defaults;// 默认值

	public int getInterFaceId() {
		return interFaceId;
	}

	public void setInterFaceId(int interFaceId) {
		this.interFaceId = interFaceId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamPosition() {
		return paramPosition;
	}

	public void setParamPosition(String paramPosition) {
		this.paramPosition = paramPosition;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getDefaults() {
		return defaults;
	}

	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}

}
