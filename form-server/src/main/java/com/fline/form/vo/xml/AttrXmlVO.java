package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "RECORDS")
public class AttrXmlVO {
    private List<AttrXmlNodeVo> RECORD;

    public AttrXmlVO() {

    }

    public AttrXmlVO(List<AttrXmlNodeVo> RECORD) {
        this.RECORD = RECORD;
    }

    public List<AttrXmlNodeVo> getRECORD() {
        return RECORD;
    }
    @XmlElement
    public void setRECORD(List<AttrXmlNodeVo> RECORD) {
        this.RECORD = RECORD;
    }
}
