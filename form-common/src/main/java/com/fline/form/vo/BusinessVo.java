package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author panym
 * @createDate 2019年4月28日上午10:48:59
 */
public class BusinessVo implements Serializable {
	
	private static final long serialVersionUID = 8511644527968352376L;

	private String code;			//编码
	
	private Integer status; // 状态

	private String cerno; 			//身份证号
	
	private long accountId; // 账号id
	
	private String accountName;		//账号名称
 
	private Long departmentId; // 部门id
	
	private String departmentName;		//部门名称
	
	private Date createDate;		//创建日期
	
	private String itemCode;		//事项内部code
	
	private String itemName;		//事项名称

    private String situationCode;   //事项情形编码
	
	private String certCode;		//证件模板code
	
	private String cerName;			//姓名

	private Integer type; // 1-页面;2-接口;3-终端机;4-定期巡检

	private String accessIP;		//访问的ip号

	private Long userId; // 用户id
	
	private String itemInnerCode; 	//事项内部编码
	
	private Date askDate;

	private String otherParam;	//额外参数
	
	private String applicantUnit;
	
	private String applicantUser;

	private Integer count;

	private boolean needFile;
	
	private boolean pdf2Jpg;

	private String itemBaseCode;//事项权利基本码

    public String getItemBaseCode() {
        return itemBaseCode;
    }

    public void setItemBaseCode(String itemBaseCode) {
        this.itemBaseCode = itemBaseCode;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSituationCode() {
		return situationCode;
	}

	public void setSituationCode(String situationCode) {
		this.situationCode = situationCode;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	public String getCerName() {
		return cerName;
	}
	public void setCerName(String cerName) {
		this.cerName = cerName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getAccessIP() {
		return accessIP;
	}
	public void setAccessIP(String accessIP) {
		this.accessIP = accessIP;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getItemInnerCode() {
		return itemInnerCode;
	}

	public void setItemInnerCode(String itemInnerCode) {
		this.itemInnerCode = itemInnerCode;
	}

	public Date getAskDate() {
		return askDate;
	}

	public void setAskDate(Date askDate) {
		this.askDate = askDate;
	}

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public boolean isNeedFile() {
		return needFile;
	}

	public void setNeedFile(boolean needFile) {
		this.needFile = needFile;
	}

	@JSONField(serialize = false)
	public boolean getPdf2Jpg() {
		return pdf2Jpg;
	}

	public void setPdf2Jpg(boolean pdf2Jpg) {
		this.pdf2Jpg = pdf2Jpg;
	}

}
