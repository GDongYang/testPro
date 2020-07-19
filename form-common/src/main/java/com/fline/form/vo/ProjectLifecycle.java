package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

public class ProjectLifecycle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4354962028848723187L;

	/**
	 * 办件流水号
	 */
    private String projectId;

	/**
	 * 办件节点名称
	 */
	private String nodeName;

	/**
	 * 办结节点
	 */
	private int node;

	/**
	 * 事项编码
	 */
    private String itemCode;

	/**
	 * 唯一是识别码
	 */
    private String formBusiCode;

	/**
	 * 申请人
	 */
    private String applyerName;

	/**
	 * 证件号
	 */
    private String applyerCardNumber;

	/**
	 * 创建时间
	 */
    private Date createTime;

	/**
	 * 内容
	 */
    private String content;

    private String attachment;

    private int status = 1;

    private String errorMsg;
    
    private String itemName;

    private String sendData;

    private String responseData;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
}
