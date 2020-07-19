package com.fline.form.access.model;

import com.feixian.tp.model.NamespaceModel;

import java.util.Date;

public class CacheInfo extends NamespaceModel {

	private static final long serialVersionUID = 1L;

	public static final int UNCACHED = 0;

    public static final int CACHED = 1;

    public static final int IN_CACHE = 2;
    
    public static final int ERROR = 3;

	/**
	 * 刷新时间
	 */
	private Date refreshTime;

	/**
	 * 状态
	 */
	private Integer status;


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }
}
