package com.fline.form.vo.ycsl;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "RECORD")
public class AttrInfo {

    @Element(name = "UNID", required = false)
    private String unid;

    @Element(name = "ATTRNAME", required = false)
    private String attrName;

    @Element(name = "SORTID", required = false)
    private String sortId;

    @Element(name = "TAKETYPE", required = false)
    private String takeType;

    @Element(name = "ISTAKE", required = false)
    private String isTake;

    @Element(name = "AMOUNT", required = false)
    private String amount;

    @Element(name = "TAKETIME", required = false)
    private String takeTime;

    @Element(name = "MEMO", required = false)
    private String memo;

    @Element(name = "BELONGSYSTEM", required = false)
    private String belongsystem;

    @Element(name = "AREACODE", required = false)
    private String areaCode;

    @Element(name = "EXTEND", required = false)
    private String extend;

    @Element(name = "DATAVERSION", required = false)
    private String dataVersion;

    @Element(name = "SYNC_STATUS", required = false)
    private String sync_status;

    @Element(name = "CREATE_TIME", required = false)
    private String create_time;

    @Element(name = "ATTRID", required = false)
    private String attrId;

    @ElementList(name = "FILES")
    private List<FileInfo> files;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getTakeType() {
        return takeType;
    }

    public void setTakeType(String takeType) {
        this.takeType = takeType;
    }

    public String getIsTake() {
        return isTake;
    }

    public void setIsTake(String isTake) {
        this.isTake = isTake;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBelongsystem() {
        return belongsystem;
    }

    public void setBelongsystem(String belongsystem) {
        this.belongsystem = belongsystem;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getSync_status() {
        return sync_status;
    }

    public void setSync_status(String sync_status) {
        this.sync_status = sync_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
}
