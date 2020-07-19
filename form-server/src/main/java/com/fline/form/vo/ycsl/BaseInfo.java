package com.fline.form.vo.ycsl;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "RECORD")
public class BaseInfo {

    @Element(name = "CALLINFO")
    private CallInfo callInfo;
    @Element(name = "APPLY_TYPE", required = false)
    private String applyType;
    @Element(name = "SERVICECODE", required = false)
    private String servicecode;
    @Element(name = "SERVICE_DEPTID", required = false)
    private String serviceDeptid;
    @Element(name = "BUS_MODE", required = false)
    private String busMode;
    @Element(name = "BUS_MODE_DESC", required = false)
    private String busModeDesc;
    @Element(name = "SERVICEVERSION", required = false)
    private String serviceversion;
    @Element(name = "SERVICENAME", required = false)
    private String servicename;
    @Element(name = "PROJECTNAME", required = false)
    private String projectname;
    @Element(name = "HANDLESTATE", required = false)
    private String handlestate;
    @Element(name = "INFOTYPE", required = false)
    private String infotype;
    @Element(name = "BUS_TYPE", required = false)
    private String busType;
    @Element(name = "REL_BUS_ID", required = false)
    private String relBusId;
    @Element(name = "APPLYNAME", required = false)
    private String applyname;
    @Element(name = "APPLY_CARDTYPE", required = false)
    private String applyCardtype;
    @Element(name = "APPLY_CARDNUMBER", required = false)
    private String applyCardnumber;
    @Element(name = "CONTACTMAN", required = false)
    private String contactman;
    @Element(name = "CONTACTMAN_CARDTYPE", required = false)
    private String contactmanCardtype;
    @Element(name = "CONTACTMAN_CARDNUMBER", required = false)
    private String contactmanCardnumber;
    @Element(name = "TELPHONE", required = false)
    private String telphone;
    @Element(name = "POSTCODE", required = false)
    private String postcode;
    @Element(name = "ADDRESS", required = false)
    private String address;
    @Element(name = "LEGALMAN", required = false)
    private String legalman;
    @Element(name = "XK_FR_SFZH", required = false)
    private String xkFrSfzh;
    @Element(name = "DEPTID", required = false)
    private String deptid;
    @Element(name = "DEPTNAME", required = false)
    private String deptname;
    @Element(name = "APPLYFROM", required = false)
    private String applyfrom;
    @Element(name = "APPROVE_TYPE", required = false)
    private String approveType;
    @Element(name = "APPLY_PROPERTIY", required = false)
    private String applyPropertiy;
    @Element(name = "RECEIVETIME", required = false)
    private String receivetime;
    @Element(name = "BELONGTO", required = false)
    private String belongto;
    @Element(name = "AREACODE", required = false)
    private String areacode;
    @Element(name = "DATASTATE", required = false)
    private String datastate;
    @Element(name = "BELONGSYSTEM", required = false)
    private String belongsystem;
    @Element(name = "EXTEND", required = false)
    private String extend;
    @Element(name = "DATAVERSION", required = false)
    private String dataversion;
    @Element(name = "SYNC_STATUS", required = false)
    private String syncStatus;
    @Element(name = "RECEIVE_USEID", required = false)
    private String receiveUseid;
    @Element(name = "RECEIVE_NAME", required = false)
    private String receiveName;
    @Element(name = "CREATE_TIME", required = false)
    private String createTime;
    @Element(name = "SS_ORGCODE", required = false)
    private String ssOrgcode;
    @Element(name = "MEMO", required = false)
    private String memo;
    @Element(name = "INTYPE", required = false)
    private String intype;

    public CallInfo getCallInfo() {
        return callInfo;
    }

    public void setCallInfo(CallInfo callInfo) {
        this.callInfo = callInfo;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getServiceDeptid() {
        return serviceDeptid;
    }

    public void setServiceDeptid(String serviceDeptid) {
        this.serviceDeptid = serviceDeptid;
    }

    public String getBusMode() {
        return busMode;
    }

    public void setBusMode(String busMode) {
        this.busMode = busMode;
    }

    public String getBusModeDesc() {
        return busModeDesc;
    }

    public void setBusModeDesc(String busModeDesc) {
        this.busModeDesc = busModeDesc;
    }

    public String getServiceversion() {
        return serviceversion;
    }

    public void setServiceversion(String serviceversion) {
        this.serviceversion = serviceversion;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getHandlestate() {
        return handlestate;
    }

    public void setHandlestate(String handlestate) {
        this.handlestate = handlestate;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getRelBusId() {
        return relBusId;
    }

    public void setRelBusId(String relBusId) {
        this.relBusId = relBusId;
    }

    public String getApplyname() {
        return applyname;
    }

    public void setApplyname(String applyname) {
        this.applyname = applyname;
    }

    public String getApplyCardtype() {
        return applyCardtype;
    }

    public void setApplyCardtype(String applyCardtype) {
        this.applyCardtype = applyCardtype;
    }

    public String getApplyCardnumber() {
        return applyCardnumber;
    }

    public void setApplyCardnumber(String applyCardnumber) {
        this.applyCardnumber = applyCardnumber;
    }

    public String getContactman() {
        return contactman;
    }

    public void setContactman(String contactman) {
        this.contactman = contactman;
    }

    public String getContactmanCardtype() {
        return contactmanCardtype;
    }

    public void setContactmanCardtype(String contactmanCardtype) {
        this.contactmanCardtype = contactmanCardtype;
    }

    public String getContactmanCardnumber() {
        return contactmanCardnumber;
    }

    public void setContactmanCardnumber(String contactmanCardnumber) {
        this.contactmanCardnumber = contactmanCardnumber;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalman() {
        return legalman;
    }

    public void setLegalman(String legalman) {
        this.legalman = legalman;
    }

    public String getXkFrSfzh() {
        return xkFrSfzh;
    }

    public void setXkFrSfzh(String xkFrSfzh) {
        this.xkFrSfzh = xkFrSfzh;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getApplyfrom() {
        return applyfrom;
    }

    public void setApplyfrom(String applyfrom) {
        this.applyfrom = applyfrom;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApplyPropertiy() {
        return applyPropertiy;
    }

    public void setApplyPropertiy(String applyPropertiy) {
        this.applyPropertiy = applyPropertiy;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getDatastate() {
        return datastate;
    }

    public void setDatastate(String datastate) {
        this.datastate = datastate;
    }

    public String getBelongsystem() {
        return belongsystem;
    }

    public void setBelongsystem(String belongsystem) {
        this.belongsystem = belongsystem;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getDataversion() {
        return dataversion;
    }

    public void setDataversion(String dataversion) {
        this.dataversion = dataversion;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getReceiveUseid() {
        return receiveUseid;
    }

    public void setReceiveUseid(String receiveUseid) {
        this.receiveUseid = receiveUseid;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSsOrgcode() {
        return ssOrgcode;
    }

    public void setSsOrgcode(String ssOrgcode) {
        this.ssOrgcode = ssOrgcode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIntype() {
        return intype;
    }

    public void setIntype(String intype) {
        this.intype = intype;
    }
}
