package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ProjectInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6142358405038927838L;

	/**
	 * 办件流水号 projectId
	 */
    private String projectId;

	/**
	 * 事项编码 itemCode
	 */
    private String itemCode;

	/**
	 * 唯一业务流水号 formBusiCode
	 */
    private String formBusiCode;

	/**
	 * 申请人姓名 applyerName
	 */
    private String applyerName;

	/**
	 * 申请人证件号 applyerCardNumber
	 */
    private String applyerCardNumber;

	/**
	 * 创建时间 createTime
	 */
    private Date createTime;

	/**
	 * 发送数据 sendData
	 */
    private String sendData;

	/**
	 * 响应数据 responseData
	 */
    private String responseData;

	/**
	 * 状态 status
	 */
    private int status;

	/**
	 * 支付链接 payUrl
	 */
    private String payUrl;

	private Integer count;
	
	private String itemName;

	@JSONField(serialize = false)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getFormBusiCode() {
        return formBusiCode;
    }

    public void setFormBusiCode(String formBusiCode) {
        this.formBusiCode = formBusiCode;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getApplyerCardNumber() {
        return applyerCardNumber;
    }

    public void setApplyerCardNumber(String applyerCardNumber) {
        this.applyerCardNumber = applyerCardNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    @JSONField(serialize = false)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
