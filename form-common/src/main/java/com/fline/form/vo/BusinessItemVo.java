package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;


public class BusinessItemVo implements Serializable{

	private static final long serialVersionUID = 7321178796242240971L;

	private int status;

	private String cerno;

	private long deptId;
	
	private long accountId; // 账号id
	
	private String accountName;//账号名称
	
	private String deptName;//部门名称
	
	private String businessCode;
	
	private long certTempId;
	
	private String certCode;
	
	private String certName;
	
	private long timeConsuming;
	
	private String code;
	
	private Date createDate;
	
	private String message;
	
    private String materialCode;

    private String materialName;

    private String itemInnerCode;

    private String itemName;

	public long getTimeConsuming() {
		return timeConsuming;
	}

	public void setTimeConsuming(long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    public String getItemInnerCode() {
        return itemInnerCode;
    }

    public void setItemInnerCode(String itemInnerCode) {
        this.itemInnerCode = itemInnerCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
