package com.fline.form.vo;

public class PersonInfo {

    private String cerName;

    private String cerNo;

    private String mobile;

    private String itemCode;

    private String userId;

    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public PersonInfo() {
    }

    public PersonInfo(String cerName, String cerNo, String mobile, String itemCode, String userId) {
        this.cerName = cerName;
        this.cerNo = cerNo;
        this.mobile = mobile;
        this.itemCode = itemCode;
        this.userId = userId;
    }


    public PersonInfo(String cerName, String cerNo, String mobile, String itemCode, String userId, String areaCode) {
        this.cerName = cerName;
        this.cerNo = cerNo;
        this.mobile = mobile;
        this.itemCode = itemCode;
        this.userId = userId;
        this.areaCode=areaCode;
    }

    public String getCerName() {
        return cerName;
    }

    public void setCerName(String cerName) {
        this.cerName = cerName;
    }

    public String getCerNo() {
        return cerNo;
    }

    public void setCerNo(String cerNo) {
        this.cerNo = cerNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
