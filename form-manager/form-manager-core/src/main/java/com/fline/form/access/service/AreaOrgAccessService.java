package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.AreaOrg;
import com.fline.form.vo.AreaOrgVo;

import java.util.List;

public interface AreaOrgAccessService extends AbstractNamespaceAccessService<AreaOrg> {

    List<AreaOrgVo> findAllVo();
}
