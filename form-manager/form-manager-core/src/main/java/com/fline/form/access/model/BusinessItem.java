package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;

public class BusinessItem extends LifecycleModel {

	private static final long serialVersionUID = -1951013192824084196L;

	@Column(name = "STATUS", column = "STATUS")
	private long status;

	@Column(name = "CERNO", column = "CERNO")
	private String cerno;

	@Column(name = "BUSINESS_ID", column = "BUSINESS_ID")
	private long businessId;
	
	@Column(name = "证件模板", column = "CERT_TEMP_ID")
	private long certTempId;
	
	@Column(name = "模板code", column="CERT_TEMP_CODE")
	private String certCode;
	
	@Column(name = "模板名称", column="CERT_NAME")
	private String certName;
	
	private long timeConsuming;
	
	public long getTimeConsuming() {
		return timeConsuming;
	}

	public void setTimeConsuming(long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
	}

	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	public long getCertTempId() {
		return certTempId;
	}

	public void setCertTempId(long certTempId) {
		this.certTempId = certTempId;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}
	
	
}
