package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.yztb.vo.ItemVo;
import org.springframework.beans.BeanUtils;

@Table(name = "事项表", tableName = "C_ITEM")
public class Item extends LifecycleModel {

	private static final long serialVersionUID = 6097312574701726399L;
	@Column(name = "是否激活", column = "ACTIVE")
	private String active;
	@Column(name = "部门ID", column = "DEPARTMENT_ID")
	private String departmentId;
	
	private String departmentName;		// 部门名称
	
	private String positionId;			// 岗位Id

	private String positionName;		// 岗位名称

	private String certTempId;
	
	private Integer type;				// 事项类型

	private String parent;				// 父级事项名
	
	private String innerCode;
	
	private Integer executable;			//是否可以一证通办1:可以0:不可以
	
	private String area;
	
	private String areaCode;
	
	private String ougUid;				
	
	private Integer deptClassify;
	
	private String formCode;			//表单编码
	
	private String affaitType;			//办件类型 00-即办件、01-承诺件、99-其他

    private String bizType;

    private String projectNature;

    private String approveType;			//01 普遍办件 02 绿色通道 03联合会审 04并联审批 99 其他
    
    private Integer handleFrequency;	//
    
    private String serviceId;
    
    private String notice;				//温馨提示
    
    private String theme;				//主题分类
    
    private String alias;				//别名
    
    private Integer orderNum;			//排序
    
    private Integer starLevel;			//星级服务
    
    private String departmentCode;		//部门code 数据库中没有此字段 为转Vo而建

    private Integer confirmStatus;

    private byte[] formImg;

    private Integer hasImg;

    public Integer getHasImg() {
        return hasImg;
    }

    public void setHasImg(Integer hasImg) {
        this.hasImg = hasImg;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getCertTempId() {
		return certTempId;
	}

	public void setCertTempId(String certTempId) {
		this.certTempId = certTempId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOugUid() {
		return ougUid;
	}

	public void setOugUid(String ougUid) {
		this.ougUid = ougUid;
	}
	
	public Integer getExecutable() {
		return executable;
	}

	public void setExecutable(Integer executable) {
		this.executable = executable;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getAffaitType() {
		return affaitType;
	}

	public void setAffaitType(String affaitType) {
		this.affaitType = affaitType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getProjectNature() {
		return projectNature;
	}

	public void setProjectNature(String projectNature) {
		this.projectNature = projectNature;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getDeptClassify() {
		return deptClassify;
	}

	public void setDeptClassify(Integer deptClassify) {
		this.deptClassify = deptClassify;
	}

	public Integer getHandleFrequency() {
		return handleFrequency;
	}

	public void setHandleFrequency(Integer handleFrequency) {
		this.handleFrequency = handleFrequency;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

    public byte[] getFormImg() {
        return formImg;
    }

    public void setFormImg(byte[] formImg) {
        this.formImg = formImg;
    }

    public ItemVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
    }
}
