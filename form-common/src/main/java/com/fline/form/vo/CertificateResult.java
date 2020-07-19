package com.fline.form.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 证件返回数据封装
 */
public class CertificateResult implements Serializable {

    @JsonIgnore
    private String busiItemCode;

    private String certCode;

    private String certName;//文件名称

    private String msg;

    private int code;//0无数据；1成功；-1失败；3返回文件URL为空

    private List<Map<String, Object>> certData;//结构化数据

    private String certFile;//文件内容

    private long timeConsuming;//耗时

    private Integer isMust;//材料是否必要

    private String materialCode;//材料编码

    private String materialName;//材料名称

    private Integer materialType;//材料类型

    private String materialTips;//材料提示

    private String requestId;//调用省平台接口标识

    private Integer needUpload;//是否需要上传文件

    public String getMaterialTips() {
        return materialTips;
    }

    public void setMaterialTips(String materialTips) {
        this.materialTips = materialTips;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    public String getBusiItemCode() {
        return busiItemCode;
    }

    public void setBusiItemCode(String busiItemCode) {
        this.busiItemCode = busiItemCode;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Map<String, Object>> getCertData() {
        return certData;
    }

    public void setCertData(List<Map<String, Object>> certData) {
        this.certData = certData;
    }

    public String getCertFile() {
        return certFile;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public long getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getNeedUpload() {
        return needUpload;
    }

    public void setNeedUpload(Integer needUpload) {
        this.needUpload = needUpload;
    }
}
