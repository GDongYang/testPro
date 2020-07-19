package com.fline.yztb.mgmt.service.impl.vo;

import java.io.Serializable;
import java.util.List;

import com.fline.yztb.access.model.CertResource;
import com.fline.yztb.vo.FormPropertyVo;
import io.swagger.annotations.ApiParam;

public class FormPageParamVo implements Serializable {

    private static final long serialVersionUID = 3119479588353615023L;

    private long id;
    private String content;
    private String htmlContent;
    private List<CertResource> certResources;
    private byte[] image;
    @ApiParam("app页面内容")
    private String appContent;
    private byte[] appImage;
    private String onlineContent;
    private byte[] onlineImage;
    private String offlineContent;
    private byte[] offlineImage;
    private String terminalContent;			//自助终端页面内容
    private byte[] terminalImage;			//自助终端图片
    private int postType;
    private int payType;
    private List<Integer> items;

    private List<FormPropertyVo> properties;

    public String getTerminalContent() {
        return terminalContent;
    }

    public void setTerminalContent(String terminalContent) {
        this.terminalContent = terminalContent;
    }

    public byte[] getTerminalImage() {
        return terminalImage;
    }

    public void setTerminalImage(byte[] terminalImage) {
        this.terminalImage = terminalImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public List<CertResource> getCertResources() {
        return certResources;
    }

    public void setCertResources(List<CertResource> certResources) {
        this.certResources = certResources;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAppContent() {
        return appContent;
    }

    public void setAppContent(String appContent) {
        this.appContent = appContent;
    }

    public String getOnlineContent() {
        return onlineContent;
    }

    public void setOnlineContent(String onlineContent) {
        this.onlineContent = onlineContent;
    }

    public String getOfflineContent() {
        return offlineContent;
    }

    public void setOfflineContent(String offlineContent) {
        this.offlineContent = offlineContent;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public byte[] getAppImage() {
        return appImage;
    }

    public void setAppImage(byte[] appImage) {
        this.appImage = appImage;
    }

    public byte[] getOnlineImage() {
        return onlineImage;
    }

    public void setOnlineImage(byte[] onlineImage) {
        this.onlineImage = onlineImage;
    }

    public byte[] getOfflineImage() {
        return offlineImage;
    }

    public void setOfflineImage(byte[] offlineImage) {
        this.offlineImage = offlineImage;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public List<FormPropertyVo> getProperties() {
        return properties;
    }

    public void setProperties(List<FormPropertyVo> properties) {
        this.properties = properties;
    }

}
