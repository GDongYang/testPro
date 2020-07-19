package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.OtherParam;
import com.fline.form.access.service.OtherParamAccessService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherParamAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<OtherParam> implements
        OtherParamAccessService {

    @Override
    public List<OtherParam> findByCert(String certCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("certCode", certCode);
        return find(param);
    }

}
