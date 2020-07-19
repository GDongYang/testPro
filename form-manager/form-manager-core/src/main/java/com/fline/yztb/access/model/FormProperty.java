package com.fline.yztb.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Table;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.form.mapper.Vo2ModelConverter;
import com.fline.yztb.vo.FormPropertyVo;
import org.springframework.beans.BeanUtils;

@Table(name = "业务表单属性", tableName = "c_form_property")
public class FormProperty extends LifecycleModel {

	private static final long serialVersionUID = -7601011383891526976L;

	public final static String PACKAGE = "package";
	public final static String ITEM = "item";
	public final static String INTERFACE_VERIFY = "interface_verify";
	public final static String INTERFACE_SUBMIT = "interface_submit";
	public final static String INTERFACE_LOADDATA = "interface_loaddata";
    public final static String DEPT_ID = "deptId";
    public final static String POST_TYPE = "postType";
    public final static String PAY_TYPE = "payType";
	
	private String cnName;				//属性中文名称
	
	private String value;				//属性值
	
	private String valueName;			//属性值的中文名称
	
	private String formCode;			//表单编码
	
	private String formName;            //表单名称
	
	private long formVersion;			//表单版本
	
	public FormProperty() {

	}

	public FormProperty(FormPropertyVo vo) {
        BeanUtils.copyProperties(vo, this);
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public long getFormVersion() {
		return formVersion;
	}

	public void setFormVersion(long formVersion) {
		this.formVersion = formVersion;
	}
	
	public FormPropertyVo toVo() {
        return Model2VoConverter.INSTANCE.toVo(this);
    }

}
