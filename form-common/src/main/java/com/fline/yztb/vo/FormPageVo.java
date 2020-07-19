package com.fline.yztb.vo;

import java.io.Serializable;
import java.util.List;

public class FormPageVo implements Serializable{

	private static final long serialVersionUID = -2354848874308257058L;

	private String name;

	private String code;

    private long version;

	private String catalogCode;

	private Integer departmentId;
	
	private String departmentName;
	
	private Integer type;

	private boolean active;

	private String notice;
	
	private String appContent;				//app端content
	
	private byte[] appImage;
	
	private String onlineContent;			//政务服务网content
	
	private byte[] onlineImage;
	
	private String offlineContent;			//一窗content
	
	private byte[] offlineImage;

    private String terminalContent;			//自助终端页面内容

    private byte[] terminalImage;			//自助终端图片

    private String packageId;				//业务ID号
	
	private String interfaceId;				//接口ID号
	
	private int postType;					//快递类型  0:不需要快递 1:单向快递 2:双向快递
	
	private int payType;					//缴费类型 0:不需要缴费 1:需要缴费
	
	private String formUrl;

	private String checkFunction;

    private List<FormPropertyVo> properties;

    private List<String> tempCodes;
	
	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getAppContent() {
		return appContent;
	}

	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	public byte[] getAppImage() {
		return appImage;
	}

	public void setAppImage(byte[] appImage) {
		this.appImage = appImage;
	}

	public String getOnlineContent() {
		return onlineContent;
	}

	public void setOnlineContent(String onlineContent) {
		this.onlineContent = onlineContent;
	}

	public byte[] getOnlineImage() {
		return onlineImage;
	}

	public void setOnlineImage(byte[] onlineImage) {
		this.onlineImage = onlineImage;
	}

	public String getOfflineContent() {
		return offlineContent;
	}

	public void setOfflineContent(String offlineContent) {
		this.offlineContent = offlineContent;
	}

	public byte[] getOfflineImage() {
		return offlineImage;
	}

	public void setOfflineImage(byte[] offlineImage) {
		this.offlineImage = offlineImage;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<FormPropertyVo> getProperties() {
        return properties;
    }

    public void setProperties(List<FormPropertyVo> properties) {
        this.properties = properties;
    }

    public String getTerminalContent() {
        return terminalContent;
    }

    public void setTerminalContent(String terminalContent) {
        this.terminalContent = terminalContent;
    }

    public byte[] getTerminalImage() {
        return terminalImage;
    }

    public void setTerminalImage(byte[] terminalImage) {
        this.terminalImage = terminalImage;
    }

    public List<String> getTempCodes() {
        return tempCodes;
    }

    public void setTempCodes(List<String> tempCodes) {
        this.tempCodes = tempCodes;
    }

    public String getCheckFunction() {
        return checkFunction;
    }

    public void setCheckFunction(String checkFunction) {
        this.checkFunction = checkFunction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
