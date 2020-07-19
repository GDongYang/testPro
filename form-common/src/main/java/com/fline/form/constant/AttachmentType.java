/**
 * 
 */
package com.fline.form.constant;

/**
 * @author zhaoxz
 * @date2019年5月13日下午4:15:40
 * @Description:    TODO(用一句话描述该文件做什么) 
 */
public enum AttachmentType {
	SEAL(1,"sealInfo"),
	QR_CODE(2,"qrCode"),
	WATERMARK(3,"watermark"),
	PHOTO(4,"photo"),
	ICON(5,"images");

    private int code;
    private String value;

	private  AttachmentType(int code,String value) {
        this.code = code;
        this.value = value;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public static String getValueByCode(int code){
		for (AttachmentType at : AttachmentType.values()) {
			if (at.getCode()==code)
				return at.getValue();
		}
		return null;
	}
	  
}
