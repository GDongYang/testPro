package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

public class SendMsgInfo implements Serializable {
	
	private static final long serialVersionUID = 600331800501507646L;

	private String projectId;		//办件号
	
	private String cerNo;			//身份证号
	
	private String ztMsg;			//中台响应
	
	private String requestData;		//请求中台数据
	
	private String serviceCode;		//服务编号
	
	private String msgType;			//消息种类 受理、办结
	
	private Date createTime;		//消息创建日期

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getZtMsg() {
		return ztMsg;
	}

	public void setZtMsg(String ztMsg) {
		this.ztMsg = ztMsg;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
