package com.fline.form.constant;

public enum ProjectNodeEnum {
    RECEIVE(0, "收件"),
    ACCEPT(1, "受理"),
    FINISH(2, "办结")
    ;

    private int value;

    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ProjectNodeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
