package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.AreaOrg;
import com.fline.form.access.service.AreaOrgAccessService;
import com.fline.form.vo.AreaOrgVo;

import java.util.List;

public class AreaOrgAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<AreaOrg> implements
AreaOrgAccessService {

    @Override
    public List<AreaOrgVo> findAllVo() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList(namespace + ".findAllVo");
    }

}
