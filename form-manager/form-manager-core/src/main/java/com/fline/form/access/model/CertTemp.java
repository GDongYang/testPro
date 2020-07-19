package com.fline.form.access.model;

import java.util.Date;

import com.fline.form.mapper.Model2VoConverter;
import org.springframework.beans.BeanUtils;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;
import com.fline.form.vo.CertTempVo;

@Table(name = "证件模板表", tableName = "C_CERT_TEMP")
public class CertTemp extends LifecycleModel implements Comparable<CertTemp>  {

	private static final long serialVersionUID = 2607053951012365749L;

	@Column(name = "模板", column = "code")
	private String code;
	
//	@Column(name = "序号", column = "SERIAL")
//	private String serial;
	
	@Column(name = "部门ID", column = "departmentId")
	private Integer departmentId;
	
	private Integer catalogDeptId;			//使用的部门ID
	
	private String catalogCode;
	
	private long tempCount;
	
	private long type;	//1证照；2证照（无模板）；3申请表
	
	private String dataCode;
	
	private String deptName;				//请求部门的名称
	
	private String catalogDeptName;			//使用部门的名称
	
	private Date createTime;				//1-PDF;2-FTL;3-申请表
	
	private int feedback;					//1-公共数据共享平台；2-其他
	
	private String deptCode;
	
	private String dataSource;
	
	private int dataType;
	
	private String content;
	
	private String htmlContent;				//html信息

	private int active;
	
	private byte[] image;
	
	private String keywords; 				// 材料和模板关联的 模糊关键字
	
	private String cerNoParam;
	
	private String cerNameParam;
	
	private String requestHandler;			//请求处理的方法
	
	private String resultHandler;			//结果处理的方法
	
	private String notice;
	
	private String otherParams;
	
	private String appContent;				//app端content
	
	private String onlineContent;			//政务服务网content
	
	private String offlineContent;			//一窗content
	
	private String packageId;				//业务ID号
	
	private String interfaceId;				//接口ID号
	
	private int postType;					//快递类型  0:不需要快递 1:单向快递 2:双向快递
	
	private int payType;					//缴费类型 0:不需要缴费 1:需要缴费
	
	
	public int getActive() {
		return active;
	}
	
	public void setActive(int active) {
		this.active = active;
	}
	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public int getFeedback() {
		return feedback;
	}

	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public long getTempCount() {
		return tempCount;
	}

	public void setTempCount(long tempCount) {
		this.tempCount = tempCount;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	 public int compareTo(CertTemp obj) {
	     return this.getCode().compareTo(obj.getCode());
    }  
	 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getCerNoParam() {
		return cerNoParam;
	}

	public void setCerNoParam(String cerNoParam) {
		this.cerNoParam = cerNoParam;
	}

	public String getCerNameParam() {
		return cerNameParam;
	}

	public void setCerNameParam(String cerNameParam) {
		this.cerNameParam = cerNameParam;
	}

	public String getResultHandler() {
		return resultHandler;
	}

	public void setResultHandler(String resultHandler) {
		this.resultHandler = resultHandler;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getOnlineContent() {
		return onlineContent;
	}

	public void setOnlineContent(String onlineContent) {
		this.onlineContent = onlineContent;
	}

	public String getOfflineContent() {
		return offlineContent;
	}

	public void setOfflineContent(String offlineContent) {
		this.offlineContent = offlineContent;
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

	public String getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(String otherParams) {
		this.otherParams = otherParams;
	}

	public Integer getCatalogDeptId() {
		return catalogDeptId;
	}

	public void setCatalogDeptId(Integer catalogDeptId) {
		this.catalogDeptId = catalogDeptId;
	}

	public String getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(String requestHandler) {
		this.requestHandler = requestHandler;
	}

	public String getCatalogDeptName() {
		return catalogDeptName;
	}

	public void setCatalogDeptName(String catalogDeptName) {
		this.catalogDeptName = catalogDeptName;
	}

	public CertTempVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
	}
}
