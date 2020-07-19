package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.form.vo.ServiceAccountVo;

@Table(name = "服务帐号表", tableName = "C_SERVICE_ACCOUNT")
public class ServiceAccount extends LifecycleModel {

	private static final long serialVersionUID = -3843021574836054077L;

	@Column(name = "是否激活", column = "ACTIVE")
	private boolean active;

	@Column(name = "密码", column = "PASSWORD")
	private String password;
	
	@Column(name = "IP地址列表以、间隔", column = "IPADDRESS")
	private String ipaddress;

	@Column(name = "用户名", column = "USERNAME")
	private String username;

	@Column(name = "职务表主键ID", column = "POSITION_ID")
	private long positionId;
	
	@Column(name = "部门表主键ID", column = "DEPARTMENT_ID")
	private long departmentId;
	
	private String departmentName;
	
	private String positionName;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}


    public ServiceAccountVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
    }
}
