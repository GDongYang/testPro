package com.fline.form.vo;

import java.util.List;
import java.util.Map;

import com.fline.form.access.model.Business;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.User;

public class DataCollectionParam {

	private String busiCode;
	
	private long departmentId;
	
	private String item;
	
	private String itemCode;
	
	private String cerNo;
	
	private List<CertTemp> certs;
	
	private CertTemp cert;
	
	private long askDeptId;
	
	private String applicantUnit;
	
	private String applicantUser;
	
	private String otherParam;
	
	private String cerName;

	private String departmentCode;

	private User user;
	
	private String catalogCode;

	private Business business;
	
	private String otherCerNo;
	
	private String otherCerName;

	private String regNo;

	private String entName;

	private String uniscid;
	
	private String itemInnerCode;
	
	private int type;//one-1;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private Map<String,Object> sealInfo;
	
	public Map<String,Object> getSealInfo() {
		return sealInfo;
	}

	public void setSealInfo(Map<String,Object> sealInfo) {
		this.sealInfo = sealInfo;
	}

	public String getOtherCerNo() {
		return otherCerNo;
	}

	public void setOtherCerNo(String otherCerNo) {
		this.otherCerNo = otherCerNo;
	}

	public String getOtherCerName() {
		return otherCerName;
	}

	public void setOtherCerName(String otherCerName) {
		this.otherCerName = otherCerName;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public List<CertTemp> getCerts() {
		return certs;
	}

	public void setCerts(List<CertTemp> certs) {
		this.certs = certs;
	}

	public CertTemp getCert() {
		return cert;
	}

	public void setCert(CertTemp cert) {
		this.cert = cert;
	}

	public long getAskDeptId() {
		return askDeptId;
	}

	public void setAskDeptId(long askDeptId) {
		this.askDeptId = askDeptId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}

	public String getCerName() {
		return cerName;
	}

	public void setCerName(String cerName) {
		this.cerName = cerName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getUniscid() {
		return uniscid;
	}

	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}

	public String getItemInnerCode() {
		return itemInnerCode;
	}

	public void setItemInnerCode(String itemInnerCode) {
		this.itemInnerCode = itemInnerCode;
	}
	
}
