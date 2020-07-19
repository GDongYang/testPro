package com.fline.form.vo;

import java.util.List;

public class ItemCertificateVo {

    private String cerNo;

    private String cerName;

    private String otherParam;

    private String busiCode;

    List<ItemSituationVo> items;

    private String formData;

    public String getCerNo() {
        return cerNo;
    }

    public void setCerNo(String cerNo) {
        this.cerNo = cerNo;
    }

    public String getCerName() {
        return cerName;
    }

    public void setCerName(String cerName) {
        this.cerName = cerName;
    }

    public List<ItemSituationVo> getItems() {
        return items;
    }

    public void setItems(List<ItemSituationVo> items) {
        this.items = items;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }
}
