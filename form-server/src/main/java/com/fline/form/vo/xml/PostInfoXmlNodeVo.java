package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "RECORD")
public class PostInfoXmlNodeVo {
    private String PRODUCTS="";
    private String TAKE_POSTNO="";
    private String POST_UNITNAME="";
    private String SEND_UNITNAME="";
    private String SEND_USER="";
    private String SEND_PHONE="";
    private String RECEIVE_NAME="";
    private String RECEIVE_PHONE="";
    private String RECEIVE_ADDR="";
    private String RECEIVE_POST="";
    private String REMARK="";
    private String BELONGSYSTEM="";
    private String AREACODE="";
    private String EXTEND="";
    private String CREATE_TIME="";
    private String SYNC_STATUS="";
    private String DATAVERSION="";

    public PostInfoXmlNodeVo() {
    }

    public PostInfoXmlNodeVo(String PRODUCTS, String TAKE_POSTNO, String POST_UNITNAME, String SEND_UNITNAME, String SEND_USER, String SEND_PHONE, String RECEIVE_NAME, String RECEIVE_PHONE, String RECEIVE_ADDR, String RECEIVE_POST, String REMARK, String BELONGSYSTEM, String AREACODE, String EXTEND, String CREATE_TIME, String SYNC_STATUS, String DATAVERSION) {
        this.PRODUCTS = PRODUCTS;
        this.TAKE_POSTNO = TAKE_POSTNO;
        this.POST_UNITNAME = POST_UNITNAME;
        this.SEND_UNITNAME = SEND_UNITNAME;
        this.SEND_USER = SEND_USER;
        this.SEND_PHONE = SEND_PHONE;
        this.RECEIVE_NAME = RECEIVE_NAME;
        this.RECEIVE_PHONE = RECEIVE_PHONE;
        this.RECEIVE_ADDR = RECEIVE_ADDR;
        this.RECEIVE_POST = RECEIVE_POST;
        this.REMARK = REMARK;
        this.BELONGSYSTEM = BELONGSYSTEM;
        this.AREACODE = AREACODE;
        this.EXTEND = EXTEND;
        this.CREATE_TIME = CREATE_TIME;
        this.SYNC_STATUS = SYNC_STATUS;
        this.DATAVERSION = DATAVERSION;
    }

    public String getPRODUCTS() {
        return PRODUCTS;
    }

    public void setPRODUCTS(String PRODUCTS) {
        this.PRODUCTS = PRODUCTS;
    }

    public String getTAKE_POSTNO() {
        return TAKE_POSTNO;
    }

    public void setTAKE_POSTNO(String TAKE_POSTNO) {
        this.TAKE_POSTNO = TAKE_POSTNO;
    }

    public String getPOST_UNITNAME() {
        return POST_UNITNAME;
    }

    public void setPOST_UNITNAME(String POST_UNITNAME) {
        this.POST_UNITNAME = POST_UNITNAME;
    }

    public String getSEND_UNITNAME() {
        return SEND_UNITNAME;
    }

    public void setSEND_UNITNAME(String SEND_UNITNAME) {
        this.SEND_UNITNAME = SEND_UNITNAME;
    }

    public String getSEND_USER() {
        return SEND_USER;
    }

    public void setSEND_USER(String SEND_USER) {
        this.SEND_USER = SEND_USER;
    }

    public String getSEND_PHONE() {
        return SEND_PHONE;
    }

    public void setSEND_PHONE(String SEND_PHONE) {
        this.SEND_PHONE = SEND_PHONE;
    }

    public String getRECEIVE_NAME() {
        return RECEIVE_NAME;
    }

    public void setRECEIVE_NAME(String RECEIVE_NAME) {
        this.RECEIVE_NAME = RECEIVE_NAME;
    }

    public String getRECEIVE_PHONE() {
        return RECEIVE_PHONE;
    }

    public void setRECEIVE_PHONE(String RECEIVE_PHONE) {
        this.RECEIVE_PHONE = RECEIVE_PHONE;
    }

    public String getRECEIVE_ADDR() {
        return RECEIVE_ADDR;
    }

    public void setRECEIVE_ADDR(String RECEIVE_ADDR) {
        this.RECEIVE_ADDR = RECEIVE_ADDR;
    }

    public String getRECEIVE_POST() {
        return RECEIVE_POST;
    }

    public void setRECEIVE_POST(String RECEIVE_POST) {
        this.RECEIVE_POST = RECEIVE_POST;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getBELONGSYSTEM() {
        return BELONGSYSTEM;
    }

    public void setBELONGSYSTEM(String BELONGSYSTEM) {
        this.BELONGSYSTEM = BELONGSYSTEM;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getEXTEND() {
        return EXTEND;
    }

    public void setEXTEND(String EXTEND) {
        this.EXTEND = EXTEND;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getSYNC_STATUS() {
        return SYNC_STATUS;
    }

    public void setSYNC_STATUS(String SYNC_STATUS) {
        this.SYNC_STATUS = SYNC_STATUS;
    }

    public String getDATAVERSION() {
        return DATAVERSION;
    }

    public void setDATAVERSION(String DATAVERSION) {
        this.DATAVERSION = DATAVERSION;
    }
}
