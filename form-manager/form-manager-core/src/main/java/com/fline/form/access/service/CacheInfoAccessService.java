package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.CacheInfo;

import java.util.Date;

public interface CacheInfoAccessService extends AbstractNamespaceAccessService<CacheInfo> {

    void updateRefreshTime(String code, Date time);

    void autoUpdateStatus(String code, int status);

}
