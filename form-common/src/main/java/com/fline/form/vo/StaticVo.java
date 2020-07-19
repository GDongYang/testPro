package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class StaticVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6293746243345147039L;

	/**
	 * 状态 status
	 */
	@JSONField(name = "status")
	private Integer status;

	/**
	 * 证件号码 cerno
	 */
	@JSONField(name = "cerno")
	private String cerNo;

	/**
	 * 业务ID businessid
	 */
	@JSONField(name = "businessid")
	private String businessId;

	/**
	 * 业务编码
	 */
	@JSONField(name = "businesscode")
	private String businessCode;

	/**
	 * 
	 */
	@JSONField(name = "certtempid")
	private Long certtempId;

	/**
	 * 证件编码 certcode
	 */
	@JSONField(name = "certcode")
	private String certCode;

	/**
	 * 证件名称 certname
	 */
	@JSONField(name = "certname")
	private String certName;

	/**
	 * 耗时 timeconsuming
	 */
	@JSONField(name = "timeconsuming")
	private Long timeConsuming;

	/**
	 * 编码 code
	 */
	@JSONField(name = "code")
	private String code;

	/**
	 * 创建时间 createdate
	 */
	@JSONField(name = "createdate")
	private Date createDate;

	/**
	 * 返回信息
	 */
	@JSONField(name = "message")
	private String message;

	/**
	 * 请求参数 requestparam
	 */
	@JSONField(name = "requestparam")
	private String requestParam;

	/**
	 * 事项名 itemName
	 */
	@JSONField(name = "itemName")
	private String itemName;

	/**
	 * 账户类型 accountName
	 */
	@JSONField(name = "accountName")
	private String accountName;

	private Integer count;

	public Integer getStatus() {
		return status;
	}

	public String getCerNo() {
		return cerNo;
	}

	public String getBusinessId() {
		return businessId;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public Long getCerttempId() {
		return certtempId;
	}

	public String getCertCode() {
		return certCode;
	}

	public String getCertName() {
		return certName;
	}

	public Long getTimeConsuming() {
		return timeConsuming;
	}

	public String getCode() {
		return code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getMessage() {
		return message;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public String getItemName() {
		return itemName;
	}

	public String getAccountName() {
		return accountName;
	}

	public Integer getCount() {
		return count;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setCerttempId(Long certtempId) {
		this.certtempId = certtempId;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public void setTimeConsuming(Long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "StaticVo [status=" + status + ", cerNo=" + cerNo + ", businessId=" + businessId + ", businessCode="
				+ businessCode + ", certtempId=" + certtempId + ", certCode=" + certCode + ", certName=" + certName
				+ ", timeConsuming=" + timeConsuming + ", code=" + code + ", createDate=" + createDate + ", message="
				+ message + ", requestParam=" + requestParam + ", itemName=" + itemName + ", accountName=" + accountName
				+ ", count=" + count + "]";
	}

}
