package com.fline.form.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Menu;
import com.fline.form.action.vo.TreeNode;
import com.fline.form.mgmt.service.MenuMgmtService;
import com.fline.form.mgmt.service.RoleMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class MenuAction extends AbstractAction implements ModelDriven<Menu> {

	private static final long serialVersionUID = 1327428986498467629L;

	private MenuMgmtService menuMgmtService;
	
	private RoleMgmtService roleMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;
	
	private long roleId;

	private Menu menu;
	private List<Menu> menuList;
	private List<TreeNode> menuTree;
	private Pagination<Menu> menuPage;
	private String result;
	public String getResult() {
		return result;
	}

	public void setRoleMgmtService(RoleMgmtService roleMgmtService) {
		this.roleMgmtService = roleMgmtService;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<TreeNode> getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(List<TreeNode> menuTree) {
		this.menuTree = menuTree;
	}

	public Pagination<Menu> getMenuPage() {
		return menuPage;
	}

	public void setMenuPage(Pagination<Menu> menuPage) {
		this.menuPage = menuPage;
	}

	public Map<String, Object> getDataMap() {

		return dataMap;

	}

	public void setDataMap(Map<String, Object> dataMap) {

		this.dataMap = dataMap;

	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String save() {
		menuMgmtService.create(menu);
		result = "新增成功！";
		return "saveResult";
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public void setMenuMgmtService(MenuMgmtService menuMgmtService) {
		this.menuMgmtService = menuMgmtService;
	}

	public String update() {
		menuMgmtService.update(menu);
		result = "修改成功！";
		return "updateResult";
	}

	public String remove() {
		menuMgmtService.remove(menu);
		roleMgmtService.removeRoleMenuByMenu(menu.getId());
		result = "删除成功！";
		return "removeResult";
	}

	public String findById() {
		Menu menu1 = menuMgmtService.findById(menu.getId());
		dataMap.put("menu", menu1);
		return "findById";
	}

	public String findPage() {
		Pagination<Menu> page = new Pagination<Menu>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		Ordering order = new Ordering();
		// order.addDesc("CREATED");
		menuPage = menuMgmtService.findPagination(param,
				order, page);
		//dataMap.put("items", pageData);
		
		
		//dataMap.put("total", menuPage.getCount());
		//dataMap.put("rows",menuPage.getItems());
		return SUCCESS;
	}

	@Override
	public Menu getModel() {
		if (menu == null) {
			menu = new Menu();
		}
		return menu;
	}
	
	public String findMenuList(){
		menuList = menuMgmtService.findMenus();
		return "menuList";
	}
	
	public String findMenuTree(){
		TreeNode node = new TreeNode();
		node.setId(0L);
		node.setName("一证通办");
		node.setChildren(menuMgmtService.findMenuTree());
		menuTree = new ArrayList<TreeNode>();
		menuTree.add(node);
		return "menuTree";
	}
	
	public String findMenuByRole() {
		menuList = menuMgmtService.findMenuByRole(roleId);
		return "menuList";
	}
}
