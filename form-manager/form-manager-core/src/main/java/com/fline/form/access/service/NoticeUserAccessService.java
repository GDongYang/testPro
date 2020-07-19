package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.NoticeUser;

import java.util.List;

public interface NoticeUserAccessService extends AbstractNamespaceAccessService<NoticeUser> {

    List<String> findMobiles ();
}
