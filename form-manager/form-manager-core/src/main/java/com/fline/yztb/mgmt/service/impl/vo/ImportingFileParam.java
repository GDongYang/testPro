package com.fline.yztb.mgmt.service.impl.vo;

import java.io.Serializable;

public class ImportingFileParam implements Serializable {

	private static final long serialVersionUID = -5546635695038032557L;

	private int templateId;
	private String fileName;
	private byte[] fileContent;
	private String fileSuffixame;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileSuffixame() {
		return fileSuffixame;
	}

	public void setFileSuffixame(String fileSuffixame) {
		this.fileSuffixame = fileSuffixame;
	}

}
