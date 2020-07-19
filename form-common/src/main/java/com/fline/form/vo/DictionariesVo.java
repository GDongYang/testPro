/**
 * Project Name:com.fline.yztb.vo
 * File Name:DictionariesVo.java
 * Package Name:com.fline.yztb.vo
 * Date:2019年6月28日下午8:23:45
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.vo;

import java.io.Serializable;

/**
 * ClassName:DictionariesVo 
 * Function: 交管字典码. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年6月28日 下午8:23:45
 * @author   邵炜
 * @version  
 * @see 	 
 */
public class DictionariesVo implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -3897818939918130534L;

	// 编码
	private String code;
	
	// 解释
	private String explain;
	
	// 对应字段
	private String filed;
	
	// 种类
	private String template;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
}

