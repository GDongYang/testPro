package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author panym
 * @createDate 2019年4月28日上午11:00:21
 */
public class DepartmentVo implements Serializable{
	
	private static final long serialVersionUID = 2527891232875743683L;

	private long id;				//id
	
	private Date updateTime;		//更新时间
	
	private String name;			//部门名称
	
	private String active;			//1-激活;0-未激活
	
	private String code;			//部门code
	
	private String isLeaf;			//1-是;0-不是
	
	private String orgcoding;		//组织code

	private String parentId;		//父部门的id号
	
	private long version;			//版本号
	
	private Date created;			//创建日期
	
	private Integer orgtype;			//部门类型

    private String areaCode;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getOrgcoding() {
		return orgcoding;
	}
	public void setOrgcoding(String orgcoding) {
		this.orgcoding = orgcoding;
	}
	public Integer getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(Integer orgtype) {
		this.orgtype = orgtype;
	}

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
