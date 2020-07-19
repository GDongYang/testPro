package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "RECORDS")
public class BaseInfoXmlVo {
    private  BaseInfoXmlNodeVo RECORD;

    public BaseInfoXmlVo() {
    }

    public BaseInfoXmlVo(BaseInfoXmlNodeVo RECORD) {
        this.RECORD = RECORD;
    }

    public BaseInfoXmlNodeVo getRECORD() {
        return RECORD;
    }
    @XmlElement
    public void setRECORD(BaseInfoXmlNodeVo RECORD) {
        this.RECORD = RECORD;
    }
}
