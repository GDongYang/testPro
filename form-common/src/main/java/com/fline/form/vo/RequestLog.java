package com.fline.form.vo;

import java.io.Serializable;
import java.util.Date;

public class RequestLog implements Serializable {

    private String requestCode;//接口编码

    private String requestName;//接口名称

    private String dataSource;//接口来源

    private String requestData;//请求参数

    private String responseData;//返回结果

    private Date requestTime;//请求时间

    private int status = 1;//状态

    private String cerNo;//身份证号码

    private String cerName;//姓名

    private String itemCode;//事项编码

    private String itemInnerCode;//事项内部编码

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemInnerCode() {
        return itemInnerCode;
    }

    public void setItemInnerCode(String itemInnerCode) {
        this.itemInnerCode = itemInnerCode;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
