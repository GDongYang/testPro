package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "RECORD")
public class BaseInfoXmlNodeVo {
    private String PROJID="";
    private String ADDRESS="";
    private String APPLY_CARDNUMBER="";
    private String APPLY_CARDTYPE="";
    private String APPLYNAME="";
    private String APPLY_PROPERTIY="";
    private String APPLYFROM="";
    private String APPROVE_TYPE="";
    private String AREACODE="";
    private String BELONGSYSTEM="";
    private String BUS_TYPE="";
    private String CONTACTMAN_CARDNUMBER="";
    private String CONTACTMAN="";
    private String CONTACTMAN_CARDTYPE="";
    private String CREATE_TIME="";
    private String DATASTATE="";
    private String DATAVERSION="";
    private String DEPTID="";
    private String DEPTNAME="";
    private String INTYPE="";
    private String INFOTYPE="";
    private String MEMO="";
    private String PROJECTNAME="";
    private String RECEIVE_NAME="";
    private String RECEIVETIME="";
    private String RECEIVE_USEID="";
    private String SERVICECODE="";
    private String SERVICECODE_ID="";
    private String SERVICE_DEPTID="";
    private String SERVICENAME="";
    private String SERVICEVERSION="";
    private String SS_ORGCODE="";
    private String SYNC_STATUS="";
    private String TELPHONE="";
    private String LEGALMAN="";
    private String POSTCODE="";
    private String BELONGTO="";

    public String getBELONGTO() {
        return BELONGTO;
    }

    public void setBELONGTO(String BELONGTO) {
        this.BELONGTO = BELONGTO;
    }



    public String getPOSTCODE() {
        return POSTCODE;
    }

    public void setPOSTCODE(String POSTCODE) {
        this.POSTCODE = POSTCODE;
    }

    public String getLEGALMAN() {
        return LEGALMAN;
    }

    public void setLEGALMAN(String LEGALMAN) {
        this.LEGALMAN = LEGALMAN;
    }

    public String getPROJID() {
        return PROJID;
    }
    @XmlElement
    public void setPROJID(String PROJID) {
        this.PROJID = PROJID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }
    @XmlElement
    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getAPPLY_CARDNUMBER() {
        return APPLY_CARDNUMBER;
    }
    @XmlElement
    public void setAPPLY_CARDNUMBER(String APPLY_CARDNUMBER) {
        this.APPLY_CARDNUMBER = APPLY_CARDNUMBER;
    }

    public String getAPPLY_CARDTYPE() {
        return APPLY_CARDTYPE;
    }
    @XmlElement
    public void setAPPLY_CARDTYPE(String APPLY_CARDTYPE) {
        this.APPLY_CARDTYPE = APPLY_CARDTYPE;
    }

    public String getAPPLYNAME() {
        return APPLYNAME;
    }
    @XmlElement
    public void setAPPLYNAME(String APPLYNAME) {
        this.APPLYNAME = APPLYNAME;
    }

    public String getAPPLY_PROPERTIY() {
        return APPLY_PROPERTIY;
    }
    @XmlElement
    public void setAPPLY_PROPERTIY(String APPLY_PROPERTIY) {
        this.APPLY_PROPERTIY = APPLY_PROPERTIY;
    }

    public String getAPPLYFROM() {
        return APPLYFROM;
    }
    @XmlElement
    public void setAPPLYFROM(String APPLYFROM) {
        this.APPLYFROM = APPLYFROM;
    }

    public String getAPPROVE_TYPE() {
        return APPROVE_TYPE;
    }
    @XmlElement
    public void setAPPROVE_TYPE(String APPROVE_TYPE) {
        this.APPROVE_TYPE = APPROVE_TYPE;
    }

    public String getAREACODE() {
        return AREACODE;
    }
    @XmlElement
    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getBELONGSYSTEM() {
        return BELONGSYSTEM;
    }
    @XmlElement
    public void setBELONGSYSTEM(String BELONGSYSTEM) {
        this.BELONGSYSTEM = BELONGSYSTEM;
    }

    public String getBUS_TYPE() {
        return BUS_TYPE;
    }
    @XmlElement
    public void setBUS_TYPE(String BUS_TYPE) {
        this.BUS_TYPE = BUS_TYPE;
    }

    public String getCONTACTMAN_CARDNUMBER() {
        return CONTACTMAN_CARDNUMBER;
    }
    @XmlElement
    public void setCONTACTMAN_CARDNUMBER(String CONTACTMAN_CARDNUMBER) {
        this.CONTACTMAN_CARDNUMBER = CONTACTMAN_CARDNUMBER;
    }

    public String getCONTACTMAN() {
        return CONTACTMAN;
    }
    @XmlElement
    public void setCONTACTMAN(String CONTACTMAN) {
        this.CONTACTMAN = CONTACTMAN;
    }

    public String getCONTACTMAN_CARDTYPE() {
        return CONTACTMAN_CARDTYPE;
    }
    @XmlElement
    public void setCONTACTMAN_CARDTYPE(String CONTACTMAN_CARDTYPE) {
        this.CONTACTMAN_CARDTYPE = CONTACTMAN_CARDTYPE;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }
    @XmlElement
    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getDATASTATE() {
        return DATASTATE;
    }
    @XmlElement
    public void setDATASTATE(String DATASTATE) {
        this.DATASTATE = DATASTATE;
    }

    public String getDATAVERSION() {
        return DATAVERSION;
    }
    @XmlElement
    public void setDATAVERSION(String DATAVERSION) {
        this.DATAVERSION = DATAVERSION;
    }

    public String getDEPTID() {
        return DEPTID;
    }
    @XmlElement
    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
    }

    public String getDEPTNAME() {
        return DEPTNAME;
    }
    @XmlElement
    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME;
    }

    public String getINTYPE() {
        return INTYPE;
    }
    @XmlElement
    public void setINTYPE(String INTYPE) {
        this.INTYPE = INTYPE;
    }

    public String getINFOTYPE() {
        return INFOTYPE;
    }
    @XmlElement
    public void setINFOTYPE(String INFOTYPE) {
        this.INFOTYPE = INFOTYPE;
    }

    public String getMEMO() {
        return MEMO;
    }
    @XmlElement
    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }

    public String getPROJECTNAME() {
        return PROJECTNAME;
    }
    @XmlElement
    public void setPROJECTNAME(String PROJECTNAME) {
        this.PROJECTNAME = PROJECTNAME;
    }

    public String getRECEIVE_NAME() {
        return RECEIVE_NAME;
    }
    @XmlElement
    public void setRECEIVE_NAME(String RECEIVE_NAME) {
        this.RECEIVE_NAME = RECEIVE_NAME;
    }

    public String getRECEIVETIME() {
        return RECEIVETIME;
    }
    @XmlElement
    public void setRECEIVETIME(String RECEIVETIME) {
        this.RECEIVETIME = RECEIVETIME;
    }

    public String getRECEIVE_USEID() {
        return RECEIVE_USEID;
    }
    @XmlElement
    public void setRECEIVE_USEID(String RECEIVE_USEID) {
        this.RECEIVE_USEID = RECEIVE_USEID;
    }

    public String getSERVICECODE() {
        return SERVICECODE;
    }
    @XmlElement
    public void setSERVICECODE(String SERVICECODE) {
        this.SERVICECODE = SERVICECODE;
    }

    public String getSERVICECODE_ID() {
        return SERVICECODE_ID;
    }
    @XmlElement
    public void setSERVICECODE_ID(String SERVICECODE_ID) {
        this.SERVICECODE_ID = SERVICECODE_ID;
    }

    public String getSERVICE_DEPTID() {
        return SERVICE_DEPTID;
    }
    @XmlElement
    public void setSERVICE_DEPTID(String SERVICE_DEPTID) {
        this.SERVICE_DEPTID = SERVICE_DEPTID;
    }

    public String getSERVICENAME() {
        return SERVICENAME;
    }
    @XmlElement
    public void setSERVICENAME(String SERVICENAME) {
        this.SERVICENAME = SERVICENAME;
    }

    public String getSERVICEVERSION() {
        return SERVICEVERSION;
    }
    @XmlElement
    public void setSERVICEVERSION(String SERVICEVERSION) {
        this.SERVICEVERSION = SERVICEVERSION;
    }

    public String getSS_ORGCODE() {
        return SS_ORGCODE;
    }
    @XmlElement
    public void setSS_ORGCODE(String SS_ORGCODE) {
        this.SS_ORGCODE = SS_ORGCODE;
    }

    public String getSYNC_STATUS() {
        return SYNC_STATUS;
    }
    @XmlElement
    public void setSYNC_STATUS(String SYNC_STATUS) {
        this.SYNC_STATUS = SYNC_STATUS;
    }

    public String getTELPHONE() {
        return TELPHONE;
    }
    @XmlElement
    public void setTELPHONE(String TELPHONE) {
        this.TELPHONE = TELPHONE;
    }
}
