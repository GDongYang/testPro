package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.form.vo.PositionVo;
import org.springframework.beans.BeanUtils;

@Table(name = "职务表", tableName = "C_POSITION")
public class Position extends LifecycleModel {

	private static final long serialVersionUID = 7849583959337928778L;
	@Column(name = "是否激活", column = "ACTIVE")
	private boolean active;
	
	@Column(name = "部门ID", column = "DEPARTMENT_ID")
	private long departmentId;
	
	private String departmentName;
	
	private int type;//0-普通岗位；1-综合办理岗位
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public PositionVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
	}
}
