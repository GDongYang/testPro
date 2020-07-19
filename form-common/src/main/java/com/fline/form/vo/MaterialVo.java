/**
 * 
 */
package com.fline.form.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author zhaoxz
 * @date2019年5月21日下午1:42:11
 * @Description:    TODO(用一句话描述该文件做什么) 
 */
public class MaterialVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8772200750560534108L;
	
	private String name;
	
	private String code;

	private int type;

	private Integer isMust;

	private String tips;
	
	private Integer needUpload;
	
	private List<String> tempCodeList;
	

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public List<String> getTempCodeList() {
		return tempCodeList;
	}

	public void setTempCodeList(List<String> tempCodeList) {
		this.tempCodeList = tempCodeList;
	}

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getNeedUpload() {
		return needUpload;
	}

	public void setNeedUpload(Integer needUpload) {
		this.needUpload = needUpload;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialVo that = (MaterialVo) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
