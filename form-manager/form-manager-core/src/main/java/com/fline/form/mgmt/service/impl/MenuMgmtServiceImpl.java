package com.fline.form.mgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.aip.platform.model.type.IdentityUser;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Menu;
import com.fline.form.access.service.MenuAccessService;
import com.fline.form.action.vo.TreeNode;
import com.fline.form.mgmt.service.MenuMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
@Service("menuMgmtService")
public class MenuMgmtServiceImpl implements MenuMgmtService {
	@Resource
	private UserSessionManagementService userSessionManagementService;
	@Resource
	private MenuAccessService menuAccessService;
	
	@Override
	public void update(Menu menu) {
		menuAccessService.update(menu);
	}

	@Override
	public void remove(Menu menu) {
		menuAccessService.remove(menu);
	}

	@Override
	public Menu create(Menu menu) {
		return menuAccessService.create(menu);
	}

	@Override
	public Menu findById(long id) {
		return menuAccessService.findById(id);
	}

	@Override
	public List<Menu> findMenus() {
		List<Menu> menus = null;
		IdentityUser iuser = userSessionManagementService.findByContext();

		menus = menuAccessService.findRoleMenu(iuser.getId());

		List<Menu> result = getChildMenus(0, menus);
		return result;
	}
	
	@Override
	public List<TreeNode> findMenuTree() {
		List<Menu> menus = null;
		menus =  menuAccessService.findMenuTree();
		List<TreeNode> result = getChildMenuTree(0, menus);
		return result;
	}
	
	private List<TreeNode> getChildMenuTree(long parentId, List<Menu> menus) {
		List<TreeNode> result = null;

		for (Menu reg : menus) {
			if (Long.parseLong(reg.getParentId()) == parentId) {
				if (result == null) {
					result = new ArrayList<TreeNode>();
				}
				TreeNode node = new TreeNode();
				/*node.setId(reg.getId());
				node.setText(reg.getName());
				node.setModel(reg);
				node.setNodeType(TreeNode.NODE_TYPE_REG);
				node.setIcon(reg.getIcon());
//				node.getState().put("checked", true);
				node.setNodes(getChildMenuTree(reg.getId(), menus));*/
				
				node.setId(reg.getId());
				node.setpId(reg.getParentId());
				node.setIcon(reg.getIcon());
				node.setName(reg.getName());
				node.setChildren(getChildMenuTree(reg.getId(), menus));
				result.add(node);
			}
		}

		return result;
	}
	
	private List<Menu> getChildMenus(long parentId, List<Menu> menus) {
		List<Menu> result = null;
		if(menus != null){
			for (Menu reg : menus) {
				if (Long.parseLong(reg.getParentId()) == parentId ) {
					if (result == null) {
						result = new ArrayList<Menu>();
					}
					result.add(reg);
					reg.setChildren(getChildMenus(reg.getId(), menus));
				}
			}
		}

		return result;
	}

	@Override
	public Pagination<Menu> findPagination(Map<String, Object> param, Ordering order, Pagination<Menu> page) {
		return null;
	}

	@Override
	public List<Menu> findMenuByRole(long roleId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("roleId", roleId);
		return menuAccessService.find("findByRole", parameter);
	}

}
