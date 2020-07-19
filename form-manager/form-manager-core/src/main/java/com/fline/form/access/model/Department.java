package com.fline.form.access.model;


import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.form.vo.DepartmentVo;

@Table(name = "部门表", tableName = "C_DEPARTMENT")
public class Department extends LifecycleModel{

	private static final long serialVersionUID = 25404992233720601L;
	
	@Column(name = "是否激活", column = "ACTIVE")
	private String active;
	
	@Column(name = "父节点ID", column = "PARENT_ID")
	private String parentId;
	
	@Column(name = "是否为叶子节点", column = "IS_LEAF")
	private String isLeaf;
	
	@Column(name = "ip白名单", column = "IPADDRESS")
	private String ipAddress;
	
	@Column(name = "回调地址", column = "RETURNADDRESS")
	private String returnAddress;
	
	@Column(name = "部门编码", column = "ORGCODING")
	private String orgcoding;
	
	private String oid;
	
	private String fullName;
	
	private int orgtype;
	
	private String level;
	
	private String uncode;
	
	private String uniquecoding;
	
	private String poid;
	
	public String getOrgcoding() {
		return orgcoding;
	}

	public void setOrgcoding(String orgcoding) {
		this.orgcoding = orgcoding;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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
	
	public void setId(String id){
		try{
			this.id = Long.parseLong(id);
			
		}catch (NumberFormatException e) {
			this.id = 0;
		}
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(int orgtype) {
		this.orgtype = orgtype;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUncode() {
		return uncode;
	}

	public void setUncode(String uncode) {
		this.uncode = uncode;
	}

	public String getUniquecoding() {
		return uniquecoding;
	}

	public void setUniquecoding(String uniquecoding) {
		this.uniquecoding = uniquecoding;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}
	
	public DepartmentVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
	}

}
