package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.DataElement;
import com.fline.form.access.service.DataElementAccessService;

import java.util.HashMap;
import java.util.Map;

public class DataElementAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<DataElement> implements
        DataElementAccessService {

    @Override
    public void removeByFormCode(String formCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("formCode", formCode);
        remove("removeByFormCode", params);
    }

}
