package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class FormInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -560904484256801157L;

	/**
	 * APPLY_MOBILE 联系方式
	 */
	@JSONField(name = "apply_mobile")
	private String applyMobile;

	/**
	 * FORM_URL 表单url
	 */
	@JSONField(name = "form_url")
	private String formUrl;

	/**
	 * APPLY_NAME 申请人姓名
	 */
	@JSONField(name = "apply_name")
	private String applyName;

	/**
	 * APPLICANT_UID 申请人唯一标识
	 */
	@JSONField(name = "applicant_uid")
	private String applicantUid;

	/**
	 * APPLY_CARD_TYPE 申请人证件类型
	 */
	@JSONField(name = "apply_card_type")
	private String applyCardType;

	/**
	 * APPLY_CARD_NUMBER 申请人证件号码
	 */
	@JSONField(name = "apply_card_number")
	private String applyCardNumber;

	/**
	 * FORM_CREATED 表单创建日期
	 */
	@JSONField(name = "form_created")
	private Date formCreated;

	/**
	 * FORM_CODE 表单编码
	 */
	@JSONField(name = "form_code")
	private String formCode;

	/**
	 * ITEM_CODE 事项编码
	 */
	@JSONField(name = "item_code")
	private String itemCode;

	/**
	 * FORM_CREATOR 表单创建人
	 */
	@JSONField(name = "formCreator")
	private String formCreator;

	/**
	 * FORM_BUSINESS_CODE 表单业务流水号
	 */
	@JSONField(name = "form_business_code")
	private String formBusinessCode;

	/**
	 * APPLY_SCENA 场景编码
	 */
	@JSONField(name = "apply_scena")
	private String applyScena;

	/**
	 * FORM_VERSION
	 */
	@JSONField(name = "form_version")
	private String formVersion;

	/**
	 * DEPT_ID 表单版本
	 */
	@JSONField(name = "dept_id")
	private String deptId;

	/**
	 * AREA_CODE 部门id
	 */
	@JSONField(name = "area_code")
	private String areaCode;

	/**
	 * DEPT_CODE 地区编码
	 */
	@JSONField(name = "dept_code")
	private String deptCode;

	/**
	 * FORM_DIGEST 部门编码
	 */
	@JSONField(name = "form_digest")
	private String formDigest;

	private String itemName;

    @JSONField(name = "APPLY_SOURCE")
	private String applySource;
	
	private Integer count;


	public String getFormUrl() {
		return formUrl;
	}

	public String getApplyName() {
		return applyName;
	}

	public String getApplicantUid() {
		return applicantUid;
	}

	public String getApplyCardType() {
		return applyCardType;
	}

	public String getApplyCardNumber() {
		return applyCardNumber;
	}

	public Date getFormCreated() {
		return formCreated;
	}

	public String getFormCode() {
		return formCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getFormCreator() {
		return formCreator;
	}

	public String getFormBusinessCode() {
		return formBusinessCode;
	}

	public String getApplyScena() {
		return applyScena;
	}

	public String getFormVersion() {
		return formVersion;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getFormDigest() {
		return formDigest;
	}

	public Integer getCount() {
		return count;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public void setApplicantUid(String applicantUid) {
		this.applicantUid = applicantUid;
	}

	public void setApplyCardType(String applyCardType) {
		this.applyCardType = applyCardType;
	}

	public void setApplyCardNumber(String applyCardNumber) {
		this.applyCardNumber = applyCardNumber;
	}

	public void setFormCreated(Date formCreated) {
		this.formCreated = formCreated;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setFormCreator(String formCreator) {
		this.formCreator = formCreator;
	}

	public void setFormBusinessCode(String formBusinessCode) {
		this.formBusinessCode = formBusinessCode;
	}

	public void setApplyScena(String applyScena) {
		this.applyScena = applyScena;
	}

	public void setFormVersion(String formVersion) {
		this.formVersion = formVersion;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setFormDigest(String formDigest) {
		this.formDigest = formDigest;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getApplyMobile() {
		return applyMobile;
	}

	public void setApplyMobile(String applyMobile) {
		this.applyMobile = applyMobile;
	}

    public String getApplySource() {
        return applySource;
    }

    public void setApplySource(String applySource) {
        this.applySource = applySource;
    }

    @Override
	public String toString() {
		return "FormInfoVo [applyMobile=" + applyMobile + ", formUrl=" + formUrl + ", applyName=" + applyName
				+ ", applicantUid=" + applicantUid + ", applyCardType=" + applyCardType + ", applyCardNumber="
				+ applyCardNumber + ", formCreated=" + formCreated + ", formCode=" + formCode + ", itemCode=" + itemCode
				+ ", formCreator=" + formCreator + ", formBusinessCode=" + formBusinessCode + ", applyScena="
				+ applyScena + ", formVersion=" + formVersion + ", deptId=" + deptId + ", areaCode=" + areaCode
				+ ", deptCode=" + deptCode + ", formDigest=" + formDigest + ", count=" + count + "]";
	}

	@JSONField(serialize = false)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}
