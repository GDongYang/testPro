package com.fline.yztb.vo;

import com.fline.form.vo.SituationVo;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author panym
 * @createDate 2019年4月28日上午11:03:27
 */
public class ItemVo implements Serializable{

    private static final long serialVersionUID = 7672555616825709257L;

    private long id;					//id

    private String name;				//事项名称

    private String innerCode;			//内部编码

    private String code; 				//事项code

    private String active;				//1-:已发布;0-:未发布

    private String departmentId;		//部门id

    private String deptName;		//部门名称

    private Integer type;				//事项类型

    private Integer executable;			//是否可以一证通办1:可以0:不可以

    private String formCode;			//表单Code

    private String affaitType;			//办件类型 00-即办件、01-承诺件、99-其他

    private String bizType;

    private String projectNature;

    private String approveType;			//01 普遍办件 02 绿色通道 03联合会审 04并联审批 99 其他

    private String serviceId;

    private String notice;//温馨提示

    private String theme;//主题分类

    private String departmentCode;

    private String parent;

    private String alias;

    private Integer starLevel;

    private String areaCode;

    private String memo;

    private String themeName;

    private Map<String, SituationVo> situationMap;

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getProjectNature() {
        return projectNature;
    }

    public void setProjectNature(String projectNature) {
        this.projectNature = projectNature;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getAffaitType() {
        return affaitType;
    }

    public void setAffaitType(String affaitType) {
        this.affaitType = affaitType;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getActive() {
        return active;
    }
    public void setActive(String active) {
        this.active = active;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getInnerCode() {
        return innerCode;
    }
    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }
    public Integer getExecutable() {
        return executable;
    }
    public void setExecutable(Integer executable) {
        this.executable = executable;
    }
    public Map<String, SituationVo> getSituationMap() {
        return situationMap;
    }
    public void setSituationMap(Map<String, SituationVo> situationMap) {
        this.situationMap = situationMap;
    }
    public String getFormCode() {
        return formCode;
    }
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
