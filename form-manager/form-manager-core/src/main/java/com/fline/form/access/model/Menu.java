package com.fline.form.access.model;

import java.util.List;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "菜单表", tableName = "C_MENU")
public class Menu extends LifecycleModel {

	private static final long serialVersionUID = -4480673179608849672L;

	@Column(name = "菜单图标", column = "ICON")
	private String icon;

	@Column(name = "菜单地址", column = "LOCATION")
	private String location;

	@Column(name = "顺序", column = "ORDINAL")
	private String ordinal;

	@Column(name = "父菜单id", column = "PARENT_ID")
	private String parentId;

	@Column(name = "参数", column = "PARAMETER")
	private String parameter;

	@Column(name = "是否可见", column = "VISIBLE")
	private String visible;

	@Column(name = "菜单类型", column = "TYPE")
	private String type;
	private List<Menu> children;
	
	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
