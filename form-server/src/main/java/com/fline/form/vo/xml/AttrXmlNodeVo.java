package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "RECORD")
public class AttrXmlNodeVo {
    private String UNID="";
    private String ATTRNAME="";
    private String ATTRID="";
    private String SORTID="";
    private String TAKETYPE="";
    private String ISTAKE="";
    private String AMOUNT="";
    private String TAKETIME="";
    private String MEMO="";
    private String Areacode="";
    private String EXTEND="";
    private String CREATE_TIME="";
    private String DATAVERSION="";
    private String SYNC_STATUS="";
    private String BELONGSYSTEM="";


    private List<FileInfoXml> fileInfoXmls;

    public AttrXmlNodeVo() {

    }

    public AttrXmlNodeVo(String UNID, String ATTRNAME, String ATTRID, String SORTID, String TAKETYPE, String ISTAKE, String AMOUNT, String TAKETIME, String MEMO, String Areacode, String EXTEND, String CREATE_TIME, String DATAVERSION, String SYNC_STATUS, String BELONGSYSTEM, List<FileInfoXml> fileInfoXmls) {
        this.UNID = UNID;
        this.ATTRNAME = ATTRNAME;
        this.ATTRID = ATTRID;
        this.SORTID = SORTID;
        this.TAKETYPE = TAKETYPE;
        this.ISTAKE = ISTAKE;
        this.AMOUNT = AMOUNT;
        this.TAKETIME = TAKETIME;
        this.MEMO = MEMO;
        this.Areacode = Areacode;
        this.EXTEND = EXTEND;
        this.CREATE_TIME = CREATE_TIME;
        this.DATAVERSION = DATAVERSION;
        this.SYNC_STATUS = SYNC_STATUS;
        this.BELONGSYSTEM = BELONGSYSTEM;
        this.fileInfoXmls = fileInfoXmls;
    }

    public String getUNID() {
        return UNID;
    }
    @XmlElement
    public void setUNID(String UNID) {
        this.UNID = UNID;
    }

    public String getATTRNAME() {
        return ATTRNAME;
    }
    @XmlElement
    public void setATTRNAME(String ATTRNAME) {
        this.ATTRNAME = ATTRNAME;
    }

    public String getATTRID() {
        return ATTRID;
    }
    @XmlElement
    public void setATTRID(String ATTRID) {
        this.ATTRID = ATTRID;
    }

    public String getSORTID() {
        return SORTID;
    }
    @XmlElement
    public void setSORTID(String SORTID) {
        this.SORTID = SORTID;
    }

    public String getTAKETYPE() {
        return TAKETYPE;
    }
    @XmlElement
    public void setTAKETYPE(String TAKETYPE) {
        this.TAKETYPE = TAKETYPE;
    }

    public String getISTAKE() {
        return ISTAKE;
    }
    @XmlElement
    public void setISTAKE(String ISTAKE) {
        this.ISTAKE = ISTAKE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }
    @XmlElement
    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getTAKETIME() {
        return TAKETIME;
    }
    @XmlElement
    public void setTAKETIME(String TAKETIME) {
        this.TAKETIME = TAKETIME;
    }

    public String getMEMO() {
        return MEMO;
    }
    @XmlElement
    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }

    public String getAreacode() {
        return Areacode;
    }
    @XmlElement
    public void setAreacode(String Areacode) {
        Areacode = Areacode;
    }

    public String getEXTEND() {
        return EXTEND;
    }
    @XmlElement
    public void setEXTEND(String EXTEND) {
        this.EXTEND = EXTEND;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }
    @XmlElement
    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getDATAVERSION() {
        return DATAVERSION;
    }
    @XmlElement
    public void setDATAVERSION(String DATAVERSION) {
        this.DATAVERSION = DATAVERSION;
    }

    public String getSYNC_STATUS() {
        return SYNC_STATUS;
    }
    @XmlElement
    public void setSYNC_STATUS(String SYNC_STATUS) {
        this.SYNC_STATUS = SYNC_STATUS;
    }

    public String getBELONGSYSTEM() {
        return BELONGSYSTEM;
    }
    @XmlElement
    public void setBELONGSYSTEM(String BELONGSYSTEM) {
        this.BELONGSYSTEM = BELONGSYSTEM;
    }

    public List<FileInfoXml> getFileInfoXmls() {
        return fileInfoXmls;
    }
    @XmlElementWrapper(name = "FILES")
    @XmlElement(name = "FILEINFO")
    public void setFileInfoXmls(List<FileInfoXml> fileInfoXmls) {
        this.fileInfoXmls = fileInfoXmls;
    }

}
