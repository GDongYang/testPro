/**
 * 
 */
package com.fline.form.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoxz
 * @date2019年5月21日下午1:42:11
 * @Description:    TODO(用一句话描述该文件做什么) 
 */
public class SituationVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -652934622502007390L;

	private Long id;

	private String name; //名称
	
	private String code;

	private Integer type;

	private String describe;
	
	private List<MaterialVo> materialList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<MaterialVo> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialVo> materialList) {
		this.materialList = materialList;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
