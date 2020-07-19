package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.OtherParam;

import java.util.List;

public interface OtherParamAccessService extends AbstractNamespaceAccessService<OtherParam> {

    List<OtherParam> findByCert(String certCode);

}
