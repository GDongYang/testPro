package com.fline.form.access.model;

import java.util.Date;

import com.feixian.aip.platform.model.type.IdentityUserSession;
import com.feixian.tp.model.LifecycleModel;

public class UserSession extends LifecycleModel implements IdentityUserSession{

	private static final long serialVersionUID = 8877953177422527589L;
	
	public static final String ATTRIBUTE_USERID = "userId";

    public static final String Column_ACTIVE = "ACTIVE";

    public static final String Column_LOGINDATE = "LOGINDATE";

    public static final String Column_EXPIREDDATE = "EXPIREDDATE";

    private long userId;

    private Boolean active;

    private String remoteEndPoint;

    private String serverEndPoint;

    private Date loginDate;

    private Date expireDate;

    private String sessionId;

    private String guid;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRemoteEndPoint() {
        return remoteEndPoint;
    }

    public void setRemoteEndPoint(String remoteEndPoint) {
        this.remoteEndPoint = remoteEndPoint;
    }

    public String getServerEndPoint() {
        return serverEndPoint;
    }

    public void setServerEndPoint(String serverEndPoint) {
        this.serverEndPoint = serverEndPoint;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
