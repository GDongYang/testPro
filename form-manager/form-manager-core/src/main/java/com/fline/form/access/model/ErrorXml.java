package com.fline.form.access.model;

import java.util.Date;

import com.feixian.tp.model.LifecycleModel;

/**
 * 存放无法解析的事项内部编码
 * @author 邵炜
 *
 */
public class ErrorXml extends LifecycleModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1742647172064615419L;

	/**
	 * xml无法解析的内部编码
	 */
	private String innerCode;
	
	/**
	 * 是否已经解决
	 */
	private Integer isSolve;
	
	
	/**
	 * 入库时间
	 */
	private Date createDate;
	
	
	/**
	 * 解决时间
	 */
	private Date solveDate;
	
	/**
	 * 错误类型 1.为xml无法解析 2.为重复绑定模板
	 */
	private Integer errorType;


	public String getInnerCode() {
		return innerCode;
	}


	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}


	public Integer getIsSolve() {
		return isSolve;
	}


	public void setIsSolve(Integer isSolve) {
		this.isSolve = isSolve;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getSolveDate() {
		return solveDate;
	}


	public void setSolveDate(Date solveDate) {
		this.solveDate = solveDate;
	}


	public Integer getErrorType() {
		return errorType;
	}


	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}
	
	
	
}

