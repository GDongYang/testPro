package com.fline.form.action.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode implements Serializable {
	
	private static final long serialVersionUID = -2565027446390459927L;

	public static final String NODE_TYPE_REG = "reg";
	
	public static final String NODE_TYPE_JURIS = "juris";
	
	public static final String NODE_TYPE_ORG = "org";
	
	public static final String NODE_TYPE_USER = "user";

	private long id;
	
	private String text;
	
	private String icon;
	
	private List<TreeNode> nodes;
	
	private String nodeType;
	
	private Object model;
	
	private String pId;
	private String name;
	private List<TreeNode> children;
	
	private Map<String, Boolean> state;
	private String code;

	

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<TreeNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public String getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public Object getModel() {
		return model;
	}
	
	public void setModel(Object model) {
		this.model = model;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Map<String, Boolean> getState() {
		if (state == null) {
			state = new HashMap<String, Boolean>(4);
		}
		return state;
	}
	
	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
