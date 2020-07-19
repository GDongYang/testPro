package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Menu;

public interface MenuAccessService extends AbstractNamespaceAccessService<Menu> {
	public List<Menu> findMenuTree();
	
	List<Menu> findRoleMenu(long userId);
}
