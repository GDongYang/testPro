package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "角色表", tableName = "C_ROLE")
public class Role extends LifecycleModel {

	private static final long serialVersionUID = -4007832776881705018L;
	@Column(name = "角色描述", column = "DESCRIPTION")
	private String description;
	
	@Column(name = "状态", column = "ACTIVE")
	private boolean active;
	
	private int level;//级别

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}