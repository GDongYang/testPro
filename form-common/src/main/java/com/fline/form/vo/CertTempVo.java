package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @author panym
 * @createDate 2019年4月28日上午10:56:05
 */
public class CertTempVo implements Serializable {
	
	private static final long serialVersionUID = -8930246375720413635L;

	private long id;				//id

	private String code;			//模板code

	private String name;			//模板名称

	private String catalogCode;		//类别编码

	private long version;			//版本号

	private Integer departmentId;	//请求部门Id
	
	private Integer catalogDeptId;	//使用的部门ID

	private String deptName;		//请求部门名称
	
	private String catalogDeptName;	//使用部门的名称

	private long type;				//1-个人;2-企业

	private String deptCode;		//部门编码
	
	private String dataCode;

	private int active;				//1-已发布;0-未发布

	private Date createTime;		//创建日期

	private String content;			//模板内容

	private String dataSource;
	
	private int dataType;
	
	private String cerNoParam;
	
	private String cerNameParam;
	
	private String otherParams;		//额外参数
	
	private String requestHandler;	//请求处理的方法
	
	private String resultHandler;			//结果处理方法

	private String notice;

	private Map<Integer, List<TempAttachmentVo>> attachmentMap;

	private Set<String[]> resources;//resources[0]=resourceCode,resources[1]=resourceType
	
	private String appContent;				//app端content
	
	private String onlineContent;			//政务服务网content
	
	private String offlineContent;			//一窗content
	
	private String packageId;				//业务ID号
	
	private String interfaceId;				//接口ID号
	
	private int postType;					//快递类型  0:不需要快递 1:单向快递 2:双向快递
	
	private int payType;					//缴费类型 0:不需要缴费 1:需要缴费


	public String getCode() {
		return code;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public Set<String[]> getResources() {
		return resources;
	}
	public void setResources(Set<String[]> resources) {
		this.resources = resources;
	}

    public Map<Integer, List<TempAttachmentVo>> getAttachmentMap() {
        return attachmentMap;
    }

    public void setAttachmentMap(Map<Integer, List<TempAttachmentVo>> attachmentMap) {
        this.attachmentMap = attachmentMap;
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
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getOtherParams() {
        return otherParams;
    }

    public void setOtherParams(String otherParams) {
        this.otherParams = otherParams;
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
    
}
