package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "目录表", tableName = "C_CATALOG")
public class Catalog extends LifecycleModel {

	private static final long serialVersionUID = -1546452382217904654L;

	@Column(name = "是否激活", column = "ACTIVE")
	private boolean active;

	@Column(name = "父ID", column = "PARENT_ID")
	private long parentId;

	@Column(name = "是否叶子节点", column = "ISLEAF")
	private boolean isLeaf;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public boolean isIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isleaf) {
		this.isLeaf = isleaf;
	}

}
