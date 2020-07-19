package com.fline.form.util;

import java.io.Serializable;

public class HttpClientResult implements Serializable {

	private static final long serialVersionUID = 4982154719394529136L;

	/**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

	public HttpClientResult(int scInternalServerError) {
		code = scInternalServerError;
	}

	public HttpClientResult(int statusCode, String content2) {
		code = statusCode;
		content = content2;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
