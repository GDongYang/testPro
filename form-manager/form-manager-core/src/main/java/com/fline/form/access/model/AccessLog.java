package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "系统日志表", tableName = "C_ACCESSLOG")
public class AccessLog extends LifecycleModel {

	private static final long serialVersionUID = -1033175944963816687L;

	@Column(name = "资源名称", column = "RES_NAME")
	private String resName;

	@Column(name = "资源编码", column = "RES_CODE")
	private String resCode;

	@Column(name = "用户ID", column = "USER_ID")
	private long userId;

	@Column(name = "访问时间", column = "ACCESS_DATE")
	private Date accessDate;

	@Column(name = "服务器地址", column = "SERVER_ENDPOINT")
	private String serverEndpoint;

	@Column(name = "客户端地址", column = "REMOTE_ENDPOINT")
	private String remoteEndpoint;

	@Column(name = "唯一编码", column = "GUID")
	private String guid;

	@Column(name = "访问类型", column = "ACCESS_TYPE")
	private int accessType;
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getServerEndpoint() {
		return serverEndpoint;
	}

	public void setServerEndpoint(String serverEndpoint) {
		this.serverEndpoint = serverEndpoint;
	}

	public String getRemoteEndpoint() {
		return remoteEndpoint;
	}

	public void setRemoteEndpoint(String remoteEndpoint) {
		this.remoteEndpoint = remoteEndpoint;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

}
