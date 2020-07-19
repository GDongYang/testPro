package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class FormDataSolrVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7726818391669748970L;

	/**
	 * 数据创建日期 DATA_CREATED
	 */
	@JSONField(name = "data_created")
	private Date dataCreated;

	/**
	 * 表单编码 FORM_CODE
	 */
	@JSONField(name = "form_code")
	private String formCode;

	/**
	 * 事项编码 ITEM_CODE
	 */
	@JSONField(name = "item_code")
	private String itemCode;

	/**
	 * 表单数据 FORM_CONTENT
	 */
	@JSONField(name = "form_content")
	private String formContent;

	/**
	 * 数据摘要 DATA_DIGEST
	 */
	@JSONField(name = "data_digest")
	private String dataDigest;

	/**
	 * 表单流水号 FORM_BUSINESS_CODE
	 */
	@JSONField(name = "form_business_code")
	private String formBusinessCode;

	/**
	 * 数据流水号 DATA_BUSINESS_CODE
	 */
	@JSONField(name = "data_business_code")
	private String dataBusinessCode;

	/**
	 * 证件类型 APPLY_CARD_TYPE
	 */
	@JSONField(name = "apply_card_type")
	private String applyCardType;

	@JSONField(name = "card_type")
	private String cardType;

	/**
	 * 证件号码 APPLY_CARD_NUMBER
	 */
	@JSONField(name = "apply_card_number")
	private String applyCardNumber;

	/**
	 * 身份证号码 SFZHM
	 */
	@JSONField(name = "sfzhm")
	private String sfzhm;

	@JSONField(name = "card_number")
	private String cardNumber;

	public Date getDataCreated() {
		return dataCreated;
	}

	public String getFormCode() {
		return formCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getFormContent() {
		return formContent;
	}

	public String getDataDigest() {
		return dataDigest;
	}

	public String getFormBusinessCode() {
		return formBusinessCode;
	}

	public String getDataBusinessCode() {
		return dataBusinessCode;
	}

	public String getApplyCardType() {
		return applyCardType;
	}

	public String getCardType() {
		return cardType;
	}

	public String getApplyCardNumber() {
		return applyCardNumber;
	}

	public String getSfzhm() {
		return sfzhm;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setDataCreated(Date dataCreated) {
		this.dataCreated = dataCreated;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}

	public void setDataDigest(String dataDigest) {
		this.dataDigest = dataDigest;
	}

	public void setFormBusinessCode(String formBusinessCode) {
		this.formBusinessCode = formBusinessCode;
	}

	public void setDataBusinessCode(String dataBusinessCode) {
		this.dataBusinessCode = dataBusinessCode;
	}

	public void setApplyCardType(String applyCardType) {
		this.applyCardType = applyCardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setApplyCardNumber(String applyCardNumber) {
		this.applyCardNumber = applyCardNumber;
	}

	public void setSfzhm(String sfzhm) {
		this.sfzhm = sfzhm;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "FormDataSolrVo [dataCreated=" + dataCreated + ", formCode=" + formCode + ", itemCode=" + itemCode
				+ ", formContent=" + formContent + ", dataDigest=" + dataDigest + ", formBusinessCode="
				+ formBusinessCode + ", dataBusinessCode=" + dataBusinessCode + ", applyCardType=" + applyCardType
				+ ", cardType=" + cardType + ", applyCardNumber=" + applyCardNumber + ", sfzhm=" + sfzhm
				+ ", cardNumber=" + cardNumber + "]";
	}

}
