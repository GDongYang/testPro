package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Menu;
import com.fline.form.access.service.MenuAccessService;

public class MenuAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Menu> implements MenuAccessService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> findMenuTree() {
		List<Menu> m = this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Menu.findAllTree");
		return m;
	}

	@Override
	public List<Menu> findRoleMenu(long userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return find("findRoleMenu", param);
	}

}