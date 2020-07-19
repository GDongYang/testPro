package com.fline.form.vo;

import java.util.List;

public class ItemSituationVo {

    private String innerCode;

    private List<String> situationCodes;

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public List<String> getSituationCodes() {
        return situationCodes;
    }

    public void setSituationCodes(List<String> situationCodes) {
        this.situationCodes = situationCodes;
    }
}
