package com.fline.form.vo;

/**
 * 返回码和返回描述封装类
 */
public enum ResultCode {
    NO_DATA(0, "无数据"),
    OK(1, "成功"),
    FAIL(-1, "失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
