package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;
import com.fline.form.annotation.Column;
import com.fline.form.annotation.Table;

@Table(name = "模板明细表", tableName = "C_TEMP_DETIL")
public class TempAttachment extends LifecycleModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2867272809078165845L;


	@Column(name = "签章Y坐标", column = "coordinatey")
	private String coordinatey;

	@Column(name = "签章X坐标", column = "coordinatex")
	private String coordinatex;
	
	@Column(name = "关键字", column = "keyWord")
	private String keyWord;

	@Column(name = "是否有效", column = "type")
	private int type;

	@Column(name = "宽", column = "width")
	private Double width;
	
	@Column(name = "高", column = "height")
	private Double height;
	
	@Column(name = "模板id", column = "tempId")
	private long tempId;
	
	private byte[] content;

	public String getCoordinatey() {
		return coordinatey;
	}

	public void setCoordinatey(String coordinatey) {
		this.coordinatey = coordinatey;
	}

	public String getCoordinatex() {
		return coordinatex;
	}

	public void setCoordinatex(String coordinatex) {
		this.coordinatex = coordinatex;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}


}
