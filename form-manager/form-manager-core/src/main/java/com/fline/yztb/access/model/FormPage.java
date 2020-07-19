package com.fline.yztb.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.yztb.vo.FormPageVo;

@Table(name = "业务表单", tableName = "c_form_page")
public class FormPage extends LifecycleModel implements Comparable<FormPage>  {

	private static final long serialVersionUID = 5001584889338197507L;

	@Column(name = "目录表code", column = "catalogCode")
	private String catalogCode;

	@Column(name = "部门ID", column = "departmentId")
	private Integer departmentId;
	
	private String departmentName;
	
	private Integer type;

	@Column(name = "是否可用", column = "active")
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
	
	 public int compareTo(FormPage obj) {
	     return this.getCode().compareTo(obj.getCode());
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

    public String getCheckFunction() {
        return checkFunction;
    }

    public void setCheckFunction(String checkFunction) {
        this.checkFunction = checkFunction;
    }

    public FormPageVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
    }
}
