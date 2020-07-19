package com.fline.form.access.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.NoticeUser;
import com.fline.form.access.service.NoticeUserAccessService;

import java.util.List;

public class NoticeUserAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<NoticeUser> implements
NoticeUserAccessService {

    @Override
    public List<String> findMobiles () {
        return getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("NoticeUser.findMobiles");
    }

}
