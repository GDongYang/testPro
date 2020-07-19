package com.fline.form.vo;

import com.fline.form.access.model.Business;
import com.fline.form.access.model.ServiceAccount;

public class BusinessContext {

	private ServiceAccount account;
	
	private Business business;
	
	private String item;
	
	private String applicantUser;
	
	private String applicantUnit;

	public ServiceAccount getAccount() {
		return account;
	}

	public void setAccount(ServiceAccount account) {
		this.account = account;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public String getApplicantUser() {
		return applicantUser;
	}

	public void setApplicantUser(String applicantUser) {
		this.applicantUser = applicantUser;
	}

	public String getApplicantUnit() {
		return applicantUnit;
	}

	public void setApplicantUnit(String applicantUnit) {
		this.applicantUnit = applicantUnit;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
}
