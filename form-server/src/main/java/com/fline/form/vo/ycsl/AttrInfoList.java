package com.fline.form.vo.ycsl;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "RECORDS")
public class AttrInfoList {

    @ElementList(inline = true)
    private List<AttrInfo> attrInfos;

    public AttrInfoList() {
        this.attrInfos = new ArrayList<>();
    }

    public boolean add(AttrInfo attrInfo) {
        return this.attrInfos.add(attrInfo);
    }
}
