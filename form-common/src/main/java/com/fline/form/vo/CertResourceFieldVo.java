package com.fline.form.vo;

import java.io.Serializable;

/**
 * 
 * @author zhaoxz
 * 2019-5-7
 * r_cert_resource表实体类
 *
 */
public class CertResourceFieldVo implements Serializable  {

	private static final long serialVersionUID = -6313219649512437083L;

	private int resourceType;
//	@Column(name = "字段名称", column = "fieldName")
	private String fieldName;
//	@Column(name = "字段英文名", column = "fieldCode")
	private String fieldCode;
//	@Column(name = "字段路径", column = "fieldPath")
	private String fieldPath;
	private String packageId;//业务对象id
	private String dataElementId;//数据元id

	public int getResourceType() {
		return resourceType;
	}
	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldPath() {
		return fieldPath;
	}
	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getDataElementId() {
		return dataElementId;
	}
	public void setDataElementId(String dataElementId) {
		this.dataElementId = dataElementId;
	}


}
