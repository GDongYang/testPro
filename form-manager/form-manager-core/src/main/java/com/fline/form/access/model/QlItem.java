/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:QlItem.java
 * Package Name:com.fline.yztb.access.model
 * Date:2019年8月12日上午9:25:16
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;

/**
 * ClassName:QlItem 
 * Function: 权力事项表. 
 * Reason: TODO ADD REASON. 
 * Date: 2019年8月12日 上午9:25:16
 * 
 * @author 邵炜
 * @version
 * @see
 */
public class QlItem extends LifecycleModel {

	/**
	 */
	private static final long serialVersionUID = 237502223524012946L;

	/**
	 * qlItemName:权力事项名称.
	 */
	private String qlItemName;

	/**
	 * qlLeadDept:实施或牵头的处（科）室名称
	 */
	private String qlLeadDept;

	/**
	 * qlInnerCode:内部编码
	 */
	private String qlInnerCode;

	/**
	 * qlOugUId:部门编码
	 */
	private String qlOugUId;

	/**
	 * qlKind:事项类型
	 */
	private String qlKind;

	
	/**
	 * qlMainitemId:主项ID
	 */
	private String qlMainitemId;
	
	
	/**
	 * qlSubitemId:子项ID.
	 */
	private String qlSubitemId;

	/**
	 * qlAreaCode:区域编码
	 */
	private String qlAreaCode;

	/**
	 * materialInfo:材料信息
	 */
	private String materialInfo;
	
	/**
	 * tongTime:入库时间
	 */
	private Date tongTime;
	
	private Long departmentId;
	
	public String getQlItemName() {
		return qlItemName;
	}

	public void setQlItemName(String qlItemName) {
		this.qlItemName = qlItemName;
	}

	public String getQlLeadDept() {
		return qlLeadDept;
	}

	public void setQlLeadDept(String qlLeadDept) {
		this.qlLeadDept = qlLeadDept;
	}

	public String getQlInnerCode() {
		return qlInnerCode;
	}

	public void setQlInnerCode(String qlInnerCode) {
		this.qlInnerCode = qlInnerCode;
	}

	public String getQlOugUId() {
		return qlOugUId;
	}

	public void setQlOugUId(String qlOugUId) {
		this.qlOugUId = qlOugUId;
	}

	public String getQlKind() {
		return qlKind;
	}

	public void setQlKind(String qlKind) {
		this.qlKind = qlKind;
	}

	public String getQlAreaCode() {
		return qlAreaCode;
	}

	public void setQlAreaCode(String qlAreaCode) {
		this.qlAreaCode = qlAreaCode;
	}

	public String getMaterialInfo() {
		return materialInfo;
	}

	public void setMaterialInfo(String materialInfo) {
		this.materialInfo = materialInfo;
	}

	public Date getTongTime() {
		return tongTime;
	}

	public void setTongTime(Date tongTime) {
		this.tongTime = tongTime;
	}

	public String getQlMainitemId() {
		return qlMainitemId;
	}

	public void setQlMainitemId(String qlMainitemId) {
		this.qlMainitemId = qlMainitemId;
	}

	public String getQlSubitemId() {
		return qlSubitemId;
	}

	public void setQlSubitemId(String qlSubitemId) {
		this.qlSubitemId = qlSubitemId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
}
