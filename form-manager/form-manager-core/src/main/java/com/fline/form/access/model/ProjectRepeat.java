package com.fline.form.access.model;

import com.feixian.tp.model.NamespaceModel;

import java.util.Date;

public class ProjectRepeat extends NamespaceModel {

	private static final long serialVersionUID = 1L;



	/**
	 * 办件流水号
	 */
	private String projectId;

	/**
	 * 业务系统状态
	 */
	private Integer sysStatus;

	/**
	 * 中台状态
	 */
	private Integer ztStatus;

	/**
	 * 业务系统返回结果
	 */
	private String sysMsg;

	/**
	 * 中台返回结果
	 */
	private String ztMsg;

	/**
	 * 重推次数
	 */
	private Integer repeatNum = 0;

	/**
	 * 请求业务系统数据
	 */
	private String sendData;

    /**
     * 请求业务系统地址
     */
	private String sendUrl;

	/**
	 * createTime
	 */
	private Date createTime;

	/**
	 * updateTime
	 */
	private Date updateTime;

	/**
	 * aliSendData
	 */
	private String aliSendData;

	private Integer repeatMaxNum = 5;

	public String getProjectId() {
		return projectId;
	}

	public Integer getSysStatus() {
		return sysStatus;
	}

	public Integer getZtStatus() {
		return ztStatus;
	}

	public String getSysMsg() {
		return sysMsg;
	}

	public String getZtMsg() {
		return ztMsg;
	}

	public Integer getRepeatNum() {
		return repeatNum;
	}

	public String getSendData() {
		return sendData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getAliSendData() {
		return aliSendData;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}

	public void setZtStatus(Integer ztStatus) {
		this.ztStatus = ztStatus;
	}

	public void setSysMsg(String sysMsg) {
		this.sysMsg = sysMsg;
	}

	public void setZtMsg(String ztMsg) {
		this.ztMsg = ztMsg;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}

	public void setSendData(String sendData) {
		this.sendData = sendData;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setAliSendData(String aliSendData) {
		this.aliSendData = aliSendData;
	}

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public Integer getRepeatMaxNum() {
        return repeatMaxNum;
    }

    public void setRepeatMaxNum(Integer repeatMaxNum) {
        this.repeatMaxNum = repeatMaxNum;
    }
}
