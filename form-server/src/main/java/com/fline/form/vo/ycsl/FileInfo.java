package com.fline.form.vo.ycsl;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "FILEINFO")
public class FileInfo {

    @Element(name = "FILENAME", required = false)
    private String fileName;

    @Element(name = "FILEURL", required = false)
    private String fileUrl;

    @Element(name = "FILEPWD", required = false)
    private String filePwd;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFilePwd() {
        return filePwd;
    }

    public void setFilePwd(String filePwd) {
        this.filePwd = filePwd;
    }
}
