package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Menu;
import com.fline.form.action.vo.TreeNode;

public interface MenuMgmtService {
	Pagination<Menu> findPagination(Map<String, Object> param, Ordering order,
			Pagination<Menu> page);

	void update(Menu menu);

	void remove(Menu menu);

	Menu create(Menu menu);

	Menu findById(long id);
	
	List<Menu> findMenuByRole(long roleId);
	
	List<Menu> findMenus();
	
	public List<TreeNode> findMenuTree();

}
