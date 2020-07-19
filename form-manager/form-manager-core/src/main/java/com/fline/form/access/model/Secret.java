package com.fline.form.access.model;

import java.util.Date;

import com.fline.form.mapper.Model2VoConverter;
import org.springframework.beans.BeanUtils;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.vo.SecretVo;

public class Secret extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属平台
	 */
	private String platform;

	/**
	 * 应用KEY
	 */
	private String appKey;

	/**
	 * 应用密钥
	 */
	private String appSecret;

	/**
	 * 刷新密钥
	 */
	private String requestSecret;

	/**
	 * 请求密钥
	 */
	private String refreshSecret;

	/**
	 * refreshSecretEndTime
	 */
	private Long refreshSecretEndTime;

	/**
	 * requestSecretEndTime
	 */
	private Long requestSecretEndTime;

	/**
	 * updateTime
	 */
	private Date updateTime;

	public String getPlatform() {
		return platform;
	}

	public String getAppKey() {
		return appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getRequestSecret() {
		return requestSecret;
	}

	public String getRefreshSecret() {
		return refreshSecret;
	}

	public Long getRefreshSecretEndTime() {
		return refreshSecretEndTime;
	}

	public Long getRequestSecretEndTime() {
		return requestSecretEndTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setRequestSecret(String requestSecret) {
		this.requestSecret = requestSecret;
	}

	public void setRefreshSecret(String refreshSecret) {
		this.refreshSecret = refreshSecret;
	}

	public void setRefreshSecretEndTime(Long refreshSecretEndTime) {
		this.refreshSecretEndTime = refreshSecretEndTime;
	}

	public void setRequestSecretEndTime(Long requestSecretEndTime) {
		this.requestSecretEndTime = requestSecretEndTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public SecretVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
	}

}
