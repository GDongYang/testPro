package com.fline.form.access.model;

import java.util.List;

import com.fline.form.mapper.Model2VoConverter;
import org.springframework.beans.BeanUtils;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.vo.MaterialVo;

public class Material extends LifecycleModel {

	
	private static final long serialVersionUID = 9140795138834215040L;

	/**
	 * situationId
	 */
	private Integer situationId;
	
	private int type;				//申请表=3 , 证明=1，不需要显示的材料=2 
	
	private List<String> tempIds;
	
	private List<CertTemp> temps;
	
	private Integer isMust;			//1. 必要   2.非必要  3.容缺后补
	
	private Integer needUpload;		//1:需要上传,2:不需要上传

	public Integer getSituationId() {
		return situationId;
	}

	public void setSituationId(Integer situationId) {
		this.situationId = situationId;
	}

	public MaterialVo toVo(){
        return Model2VoConverter.INSTANCE.toVo(this);
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

	public List<String> getTempIds() {
		return tempIds;
	}

	public void setTempIds(List<String> tempIds) {
		this.tempIds = tempIds;
	}

	public Integer getIsMust() {
		return isMust;
	}

	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}

	public List<CertTemp> getTemps() {
		return temps;
	}

	public void setTemps(List<CertTemp> temps) {
		this.temps = temps;
	}

	public Integer getNeedUpload() {
		return needUpload;
	}

	public void setNeedUpload(Integer needUpload) {
		this.needUpload = needUpload;
	}
	
	

}
