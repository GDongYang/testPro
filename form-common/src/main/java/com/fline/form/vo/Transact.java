package com.fline.form.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fline.form.constant.ProjectNodeEnum;

import java.io.Serializable;

public class Transact extends ProjectEvent implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 2279052210349430961L;

	/**
	 * 办理人员姓名
	 */
	@JSONField(name = "TRANSACT_USER")
	private String transactUser;

	/**
	 * 办结人所属部门名称    办结人所属部门名称。使用浙江政务服务网统一用户平台中的部门简称。
	 */
	@JSONField(name = "HANDER_DEPTNAME")
	private String handerDeptName;

	/**
	 * 办结人所属部门编码   如果是窗口延伸类或多级联办的办件必填。该编码由浙江政务服务网统一用户平台提供。
	 */
	@JSONField(name = "HANDER_DEPTID")
	private String handerDeptId;

	/**
	 * 办结人所属部门的所在行政区划编码
	 */
	@JSONField(name = "AREACODE")
	private String areaCode;

	/**
	 * 办结日期  时间格式 yyyy-mm-ddhh24:mi:ss
	 */
	@JSONField(name = "TRANSACT_TIME")
	private String transactTime;

	/**
	 * 办理结果办结、转报办结、作废办结、不予受理、退件
	 */
	@JSONField(name = "TRANSACT_RESULT")
	private String transactResult;

	/**
	 * 办理结果描述  准予许可、不予许可等
	 */
	@JSONField(name = "TRANSACT_DESCRIBE")
	private String transactDescribe;

	/**
	 * 结果编号
	 */
	@JSONField(name = "RESULT_CODE")
	private String resultCode;

	/**
	 * 备注
	 */
	@JSONField(name = "REMARK")
	private String remark;

	/**
	 * 所属系统  用于区分不同业务系统报送的数据，标识由省级平台分配
	 */
	@JSONField(name = "BELONGSYSTEM")
	private String belongSystem;

	/**
	 * 备用字段
	 */
	@JSONField(name = "EXTEND")
	private String extend;

	/**
	 * 同步状态  数据产生时间
	 */
	@JSONField(name = "CREATE_TIME")
	private String createTime;

	/**
	 * 插入：I，更新：U，删除：D，已同步：S
	 */
	@JSONField(name = "SYNC_STATUS")
	private String syncStatus;

	/**
	 * 版本号 默认值=1，如果有信息变更，则版本号递增
	 */
	@JSONField(name = "DATAVERSION")
	private Integer dataVersion;
	
	/**
	 * 业务编号.
	 */
	@JSONField(name = "ywbh")
	private String ywbh;
	
	public String getYwbh() {
		return ywbh;
	}

	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}

	public String getTransactUser() {
		return transactUser;
	}

	public String getHanderDeptName() {
		return handerDeptName;
	}

	public String getHanderDeptId() {
		return handerDeptId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getTransactTime() {
		return transactTime;
	}

	public String getTransactResult() {
		return transactResult;
	}

	public String getTransactDescribe() {
		return transactDescribe;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getRemark() {
		return remark;
	}

	public String getBelongSystem() {
		return belongSystem;
	}

	public String getExtend() {
		return extend;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public Integer getDataVersion() {
		return dataVersion;
	}

	public void setTransactUser(String transactUser) {
		this.transactUser = transactUser;
	}

	public void setHanderDeptName(String handerDeptName) {
		this.handerDeptName = handerDeptName;
	}

	public void setHanderDeptId(String handerDeptId) {
		this.handerDeptId = handerDeptId;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setTransactTime(String transactTime) {
		this.transactTime = transactTime;
	}

	public void setTransactResult(String transactResult) {
		this.transactResult = transactResult;
	}

	public void setTransactDescribe(String transactDescribe) {
		this.transactDescribe = transactDescribe;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setBelongSystem(String belongSystem) {
		this.belongSystem = belongSystem;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setDataVersion(Integer dataVersion) {
		this.dataVersion = dataVersion;
	}


    @Override
    public ProjectNodeEnum getNode() {
        return ProjectNodeEnum.FINISH;
    }
}
