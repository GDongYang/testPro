package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SmsVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -956278212020379807L;

	/**
	 * 手机号码
	 */
	@JSONField(name = "phone")
	private String phone;

	/**
	 * 内容 content
	 */
	@JSONField(name = "content")
	private String content;

	/**
	 * 验证码 checkedcode
	 */
	@JSONField(name = "checkedcode")
	private String checkedCode;

	/**
	 * 创建时间 create_date
	 */
	@JSONField(name = "create_date")
	private Date createDate;

	/**
	 * 事项编码 item_code
	 */
	@JSONField(name = "item_code")
	private String itemCode;

	/**
	 * 表单编码 form_code
	 */
	@JSONField(name = "form_code")
	private String formCode;

	/**
	 * 状态 status
	 */
	@JSONField(name = "status")
	private String status;

	public String getPhone() {
		return phone;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getFormCode() {
		return formCode;
	}

	public String getStatus() {
		return status;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckedCode() {
		return checkedCode;
	}

	public void setCheckedCode(String checkedCode) {
		this.checkedCode = checkedCode;
	}

	@Override
	public String toString() {
		return "SmsVo [phone=" + phone + ", content=" + content + ", checkedCode=" + checkedCode + ", createDate="
				+ createDate + ", itemCode=" + itemCode + ", formCode=" + formCode + ", status=" + status + "]";
	}

}
