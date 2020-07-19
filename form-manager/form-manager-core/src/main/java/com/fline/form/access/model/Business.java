package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;

public class Business extends LifecycleModel {

	private static final long serialVersionUID = -5567204431175221142L;
	
	private long accountId;
	
	private Long departmentId;
	
	private String cerno; 
	
	private String itemCode;
	
	private String itemInnerCode;
	
	private long status;
	
	private String departmentName;
	
	private String ItemName;
	
	private int certTempCount;
	
	private String accountName;
	
	private String certCode;
	
	private String applicantUnit;
	
	private String applicantUser;
	
	private Date askDate;
	
	private String cerName;// 姓名

	private String power;
	
	private String permissionCode;
	
	private int type;//1-页面;2-接口;3-终端机;4-定期巡检
	
	private String accessIP;
	
	private long userId;
	
	private String userName;
	
	private String busiDate;
	
	private String zhId;
	
	private String otherCerNo;

	private String otherCerName;
	
	@Column(name="请求参数",column="requestParam")
	private String requestParam;	//请求参数

	public String getOtherCerName() {
		return otherCerName;
	}

	public void setOtherCerName(String otherCerName) {
		this.otherCerName = otherCerName;
	}

	public String getOtherCerNo() {
		return otherCerNo;
	}

	public void setOtherCerNo(String otherCerNo) {
		this.otherCerNo = otherCerNo;
	}

	public String getZhId() {
		return zhId;
	}

	public void setZhId(String zhId) {
		this.zhId = zhId;
	}

	public String getBusiDate() {
		return busiDate;
	}

	public void setBusiDate(String busiDate) {
		this.busiDate = busiDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccessIP() {
		return accessIP;
	}

	public void setAccessIP(String accessIP) {
		this.accessIP = accessIP;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCertTempCount() {
		return certTempCount;
	}

	public void setCertTempCount(int certTempCount) {
		this.certTempCount = certTempCount;
	}

	public String getApplicantUnit() {
		return applicantUnit;
	}

	public void setApplicantUnit(String applicantUnit) {
		this.applicantUnit = applicantUnit;
	}

	public String getApplicantUser() {
		return applicantUser;
	}

	public void setApplicantUser(String applicantUser) {
		this.applicantUser = applicantUser;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public Date getAskDate() {
		return askDate;
	}

	public void setAskDate(Date askDate) {
		this.askDate = askDate;
	}

	public String getCerName() {
		return cerName;
	}

	public void setCerName(String cerName) {
		this.cerName = cerName;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getItemInnerCode() {
		return itemInnerCode;
	}

	public void setItemInnerCode(String itemInnerCode) {
		this.itemInnerCode = itemInnerCode;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	
	
	
}
