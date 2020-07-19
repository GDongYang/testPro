package com.fline.form.access.model;

import com.feixian.aip.platform.model.type.IdentityUser;
import com.feixian.tp.model.LifecycleModel;

public class User extends LifecycleModel implements IdentityUser {

	private static final long serialVersionUID = -8919033005937685296L;

	public static final String USERNAME_ADMIN = "admin";

	public static final String USERNAME_INTEGRATION = "integration";

	public static final String ATTRIBUTE_USER_WORKPOSITION = "workPosition";

	public static final String ATTRIBUTE_USERNAME = "username";

	public static final String ATTRIBUTE_PARENTID = "parentId";

	public static final String ATTRIBUTE_USERLOCATION_USERID = "userId";

	public static final String ATTRIBUTE_USERLOCATION_LOCATIONID = "locationId";

	public static final String ATTRIBUTE_USERLOCATION_LOCATIONIDS = "locationIds";

	public static final String ATTRIBUTE_USERGROUP_GROUPNAME = "groupName";

	public static final String ATTRIBUTE_USERORGANIZATION_USERID = "userId";

	public static final String ATTRIBUTE_USERORGANIZATION_ORGANIZATIONID = "organizationId";

	public static final String ATTRIBUTE_USERORGANIZATION_ORGANIZATIONNAME = "organizationName";

	public static final String ATTRIBUTE_USER_MOBILEPHONE = "mobilePhone";

	public static final short SSO_INSERT = 1;

	public static final short SSO_UPDATE = 2;

	public static final short SSO_DELETE = 3;

	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	/** 账号状态 */
	private boolean active;
	/** 性别 */
	private int sex;
	/** 移动电话 */
	private String mobilePhone;
	/** 办公电话 */
	private String officePhone;
	/** Email */
	private String email;
	/** 工号 */
	private String workNo;
	/** 所属单位 */
	private long orgId;
	private String orgName;
	/** 手机虚拟号 */
	private String virtualMobilePhone;
	/** 传真 */
	private String fax;
	/** 岗位*/
	private long positionId;
	
	/** 用户唯一识别码 */
	private String userId;
	
	private long rCount;
	
	private String orgCode;
	
	private String cardNo;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	private String certNo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getVirtualMobilePhone() {
		return virtualMobilePhone;
	}

	public void setVirtualMobilePhone(String virtualMobilePhone) {
		this.virtualMobilePhone = virtualMobilePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public long getrCount() {
		return rCount;
	}

	public void setrCount(long rCount) {
		this.rCount = rCount;
	}

}
