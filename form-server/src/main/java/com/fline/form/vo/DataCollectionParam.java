package com.fline.form.vo;

import java.util.Map;

public class DataCollectionParam {

	private String busiCode;
	
	private long departmentId;
	
	private String item;
	
	private String itemCode;
	
	private String cerNo;
	
	private BusinessVo businessVo;
	
	private long askDeptId;
	
	private String applicantUnit;
	
	private String applicantUser;
	
	private String otherParam;
	
	private String cerName;

	private String departmentCode;
	
	private String itemInnerCode;
	
	private int type;//one-1;

    private MaterialVo materialVo;

    private CertTempVo certTempVo;

    private boolean needFile;

    private String catalogCode;

    private boolean pdf2Jpg;

    private String formData;

    public boolean isPdf2Jpg() {
        return pdf2Jpg;
    }

    public void setPdf2Jpg(boolean pdf2Jpg) {
        this.pdf2Jpg = pdf2Jpg;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public boolean isNeedFile() {
        return needFile;
    }

    public void setNeedFile(boolean needFile) {
        this.needFile = needFile;
    }

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

	public String getItemInnerCode() {
		return itemInnerCode;
	}

	public void setItemInnerCode(String itemInnerCode) {
		this.itemInnerCode = itemInnerCode;
	}

	public BusinessVo getBusinessVo() {
		return businessVo;
	}

	public void setBusinessVo(BusinessVo businessVo) {
		this.businessVo = businessVo;
	}

    public MaterialVo getMaterialVo() {
        return materialVo;
    }

    public void setMaterialVo(MaterialVo materialVo) {
        this.materialVo = materialVo;
    }

    public CertTempVo getCertTempVo() {
        return certTempVo;
    }

    public void setCertTempVo(CertTempVo certTempVo) {
        this.certTempVo = certTempVo;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }
}
