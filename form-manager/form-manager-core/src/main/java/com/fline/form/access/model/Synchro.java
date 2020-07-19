package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;

/**
 * 存放同步的时间和同步的表名
 * @author 邵炜
 *
 */
public class Synchro extends LifecycleModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 687876991356248701L;

	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 同步状态
	 */
	private Integer state; 
	
	/**
	 * 处理时间.
	 */
	private Long takeTime;
	
	
	/**
	 * 操作时间.
	 */
	private Date updateTime;
	
	/**
	 * 同步条件.
	 */
	private String term;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	
}
