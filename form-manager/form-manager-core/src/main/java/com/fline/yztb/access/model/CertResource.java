package com.fline.yztb.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.form.vo.CertResourceVo;



/**
 * 
 * @author wangn
 * 2019-4-28
 * r_cert_resource表实体类
 *
 */
public class CertResource extends LifecycleModel {


	private static final long serialVersionUID = -2847483355754667350L;
	@Column(name = "模板表id", column = "tempId")
	private int tempId;
	@Column(name = "模板表编码", column = "tempCode")
	private String tempCode;
	@Column(name = "资源编码", column = "resourceCode")
	private String resourceCode;
	@Column(name = "资源类型", column = "recourceType")
	private int resourceType;
	@Column(name = "字段名称", column = "fieldName")
	private String fieldName;
	@Column(name = "字段英文名", column = "fieldCode")
	private String fieldCode;
	@Column(name = "字段路径", column = "fieldPath")
	private String fieldPath;
	private String packageId;//业务对象id
	private String dataElementId;//数据元id
	
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

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
	public String getTempCode() {
		return tempCode;
	}
	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
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
	
	public CertResourceVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
	}
}
