package com.fline.form.vo;

public class ResponseResult<T> {
	
	public static final int SUCCESS_CODE = 0;

    public static final int ERROR_CODE = -1;

	private static final String SUCCESS_MSG = "操作成功";
	
	private int returnCode;
	
	private String returnMessage;

	private T data;
	
	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private ResponseResult(){}

	private ResponseResult(int returnCode, String returnMessage) {
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
	}

	private ResponseResult(int returnCode, String returnMessage, T data) {
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.data = data;
	}

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<T>(SUCCESS_CODE, SUCCESS_MSG, null);
    }

	public static <T> ResponseResult<T> success(T data) {
		return new ResponseResult<T>(SUCCESS_CODE, SUCCESS_MSG, data);
	}
	
	public static <T> ResponseResult<T> error(String returnMessage) {
		return new ResponseResult<T>(ERROR_CODE, returnMessage);
	}
}
