package com.fline.form.vo;

import java.io.Serializable;

/**
 * @author zhaoxz
 * @date2019年5月6日上午10:54:30
 * @Description:    盖章信息 
 */
public class TempAttachmentVo  implements Serializable{

	private static final long serialVersionUID = 7022466583873977223L;
	
	private long id; //证件模板id

    private String code; //印章编码
	
	private String keyWord; //盖章关键字
	
	private String coordinatex; //盖章x坐标
	
	private String coordinatey; //盖章y坐标
	private int type;
	private Double width;
	
	private Double height;
	
	private long tempId;//模板id

    public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCoordinatex() {
		return coordinatex;
	}

	public void setCoordinatex(String coordinatex) {
		this.coordinatex = coordinatex;
	}

	public String getCoordinatey() {
		return coordinatey;
	}

	public void setCoordinatey(String coordinatey) {
		this.coordinatey = coordinatey;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public long getTempId() {
		return tempId;
	}

	public void setTempId(long tempId) {
		this.tempId = tempId;
	}
    
}

