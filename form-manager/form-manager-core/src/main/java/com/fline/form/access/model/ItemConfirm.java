package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;

public class ItemConfirm extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * createTime
	 */
	private Date createTime;

	/**
	 * itemId
	 */
	private Long itemId;

	/**
	 * itemName
	 */
	private String itemName;

	/**
	 * itemInnerCode
	 */
	private String itemInnerCode;

	/**
	 * status
	 */
	private Integer status;//0不同意，1同意

	/**
	 * deptId
	 */
	private Long deptId;

	/**
	 * deptName
	 */
	private String deptName;

	public Date getCreateTime() {
		return createTime;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemInnerCode() {
		return itemInnerCode;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemInnerCode(String itemInnerCode) {
		this.itemInnerCode = itemInnerCode;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
