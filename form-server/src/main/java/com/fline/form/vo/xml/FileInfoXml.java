package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "FILEINFO")
public class FileInfoXml {
    private String FILENAME="";
    private String FILEPWD="";
    private String FILEURL="";

    public FileInfoXml(String FILENAME, String FILEPWD, String FILEURL) {
        this.FILENAME = FILENAME;
        this.FILEPWD = FILEPWD;
        this.FILEURL = FILEURL;
    }

    public FileInfoXml() {
    }

    public String getFILENAME() {
        return FILENAME;
    }
    @XmlElement
    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public String getFILEPWD() {
        return FILEPWD;
    }
    @XmlElement
    public void setFILEPWD(String FILEPWD) {
        this.FILEPWD = FILEPWD;
    }

    public String getFILEURL() {
        return FILEURL;
    }
    @XmlElement
    public void setFILEURL(String FILEURL) {
        this.FILEURL = FILEURL;
    }

    @Override
    public String toString() {
        return "FILEINFOXMLVO{" +
                "FILENAME='" + FILENAME + '\'' +
                ", FILEPWD='" + FILEPWD + '\'' +
                ", FILEURL='" + FILEURL + '\'' +
                '}';
    }
}

