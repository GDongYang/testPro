package com.fline.form.access.model;

import java.util.List;

import com.fline.form.mapper.Model2VoConverter;
import org.springframework.beans.BeanUtils;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.vo.SituationVo;

public class Situation extends LifecycleModel {

	private static final long serialVersionUID = 6932591179961959151L;

	/**
	 * 事项innerid
	 */
	private Integer itemId;
	
	private List<String> materialIds;
	
	private List<Material> materialList;
	
	private Integer type;
	
	private Integer confirm;//是否需要定时模糊匹配 
	
	private String describe;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public SituationVo toVo(){
        return Model2VoConverter.INSTANCE.toVo(this);
	}

    public List<String> getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(List<String> materialIds) {
        this.materialIds = materialIds;
    }

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	public Integer getConfirm() {
		return confirm;
	}

	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
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
