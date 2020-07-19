package com.fline.form.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fline.form.constant.ProjectNodeEnum;

public abstract class ProjectEvent {

    /**
     * 申报号
     */
    protected String projid;

    protected int status = 1;

    protected String errorMsg;

    protected String attName;

    protected String attPath;

    @JSONField(serialize = false)
    public abstract ProjectNodeEnum getNode();

    public String getProjid() {
        return projid;
    }

    @JSONField(name = "PROJID")
    public void setProjid(String projid) {
        this.projid = projid;
    }

    @JSONField(serialize = false)
    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }
    @JSONField(serialize = false)
    public String getAttPath() {
        return attPath;
    }

    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }
    @JSONField(serialize = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    @JSONField(serialize = false)
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
