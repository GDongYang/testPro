package com.fline.form.type;

public enum  ApplySourceType {

    APP("app"),
    PC("pc"),
    TERMINAL("terminal"),
    OFFLINE("offline")
    ;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ApplySourceType(String name) {
        this.name = name;
    }
}
