package com.fline.form.mgmt.service.impl.vo;

import java.io.Serializable;

public class MaterialTempVo implements Serializable {

    private String name;

    private String code;

    private Integer type;

    private String tips;

    private Long situationId;

    private String situationCode;

    private String situationName;

    private Integer situationType;

    private String situationDescribe;

    private long itemId;

    private String tempCode;

    private Integer isMust;

    private Integer needUpload;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSituationId() {
        return situationId;
    }

    public void setSituationId(Long situationId) {
        this.situationId = situationId;
    }

    public String getSituationCode() {
        return situationCode;
    }

    public void setSituationCode(String situationCode) {
        this.situationCode = situationCode;
    }

    public String getSituationName() {
        return situationName;
    }

    public void setSituationName(String situationName) {
        this.situationName = situationName;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public Integer getSituationType() {
        return situationType;
    }

    public void setSituationType(Integer situationType) {
        this.situationType = situationType;
    }

    public String getSituationDescribe() {
        return situationDescribe;
    }

    public void setSituationDescribe(String situationDescribe) {
        this.situationDescribe = situationDescribe;
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
}
