/**
 * Project Name:com.fline.yztb.vo
 * File Name:DictionaryVo.java
 * Package Name:com.fline.yztb.vo
 * Date:2019年7月13日下午4:20:18
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.vo;

import java.io.Serializable;

/**
 * ClassName:DictionaryVo 
 * Function: 字典缓存类 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年7月13日 下午4:20:18
 * @author   邵炜
 * @version  
 * @see 	 
 */
public class DictionaryVo implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String name;
	
	private String field;
	
	private String memo;
	
	private Integer type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}

