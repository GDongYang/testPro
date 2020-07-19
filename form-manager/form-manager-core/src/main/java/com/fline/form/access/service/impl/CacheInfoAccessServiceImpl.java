package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.CacheInfo;
import com.fline.form.access.service.CacheInfoAccessService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CacheInfoAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<CacheInfo> implements
        CacheInfoAccessService {

    @Override
    public void updateRefreshTime(String code, Date time) {
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        param.put("time", time);
        param.put("status", CacheInfo.CACHED);
        update("updateRefreshTime", param);
    }

    @Override
    public void autoUpdateStatus(String code, int status) {
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        param.put("status", status);
        update("updateStatus", param);
    }

}
