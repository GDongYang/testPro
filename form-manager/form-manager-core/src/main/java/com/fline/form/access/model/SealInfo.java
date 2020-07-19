package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.form.vo.SealInfoVo;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class SealInfo extends LifecycleModel {

	
	private static final long serialVersionUID = -2295332624415214914L;

	private String username;

	private String project;
	
	private Integer departmentId;

	private String departmentName;

	private Date createTime;

	@Column(name = "是否可见", column = "active")
	private String visible;
	
	@Column(name = "印章图片内容",column = "image")
	private byte[] image;
	
	public SealInfoVo toVo(){
        return Model2VoConverter.INSTANCE.toVo(this);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
    
    
}
