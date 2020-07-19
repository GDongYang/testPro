package com.fline.yztb.vo;

import java.io.Serializable;

/**
 * 
 * @author xuhuan-pc
 *
 */
public class FormPropertyVo implements Serializable {

	private static final long serialVersionUID = 5543344880696017769L;
	
	private String name;				//属性名称
	
	private String cnName;				//属性中文名称
	
	private String value;				//属性值
	
	private String valueName;			//属性值的中文名称
	
	private String formCode;			//表单编码
	
	private String formName;            //表单名称
	
	private int formVersion;			//表单版本
	
	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int getFormVersion() {
		return formVersion;
	}

	public void setFormVersion(int formVersion) {
		this.formVersion = formVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
