package com.fline.form.vo.ycsl;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

public class CallInfo {

    @Element(name = "CALLER", required = false)
    private String caller;
    @Element(name = "CALLTIME", required = false)
    private String calltime;
    @Element(name = "CALLBACK_URL", required = false)
    private String callbackUrl;

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
