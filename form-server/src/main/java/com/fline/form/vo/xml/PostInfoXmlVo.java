package com.fline.form.vo.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2020/4/13.
 */
@XmlRootElement(name = "RECORDS")
public class PostInfoXmlVo {
    private PostInfoXmlNodeVo RECORD;

    public PostInfoXmlVo() {
    }

    public PostInfoXmlVo(PostInfoXmlNodeVo RECORD) {
        this.RECORD = RECORD;
    }

    public PostInfoXmlNodeVo getRECORD() {
        return RECORD;
    }

    public void setRECORD(PostInfoXmlNodeVo RECORD) {
        this.RECORD = RECORD;
    }
}
