package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.aip.platform.model.type.IdentityUser;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Role;
import com.fline.form.mgmt.service.RoleMgmtService;
import com.fline.form.mgmt.service.UserMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleAction extends AbstractAction implements ModelDriven<Role> {

	private static final long serialVersionUID = 4416846047893610014L;

	private RoleMgmtService roleMgmtService;
	private UserSessionManagementService userSessionManagementService;
	private UserMgmtService userMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;

	private Role role;
	
	private long[] menuIds;
	
	private long[] userIds;
	
	private List<Long> departIds;
	
	private List<Long> showDeptIds;
	
	private Long jobDeptId;
	
	private String jobStr;
	
	private Integer jobLevel;

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setRoleMgmtService(RoleMgmtService roleMgmtService) {
		this.roleMgmtService = roleMgmtService;
	}
	
	public void setUserSessionManagementService(UserSessionManagementService userSessionManagementService) {
		this.userSessionManagementService = userSessionManagementService;
	}
	
	public void setUserMgmtService(UserMgmtService userMgmtService) {
		this.userMgmtService = userMgmtService;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public long[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(long[] menuIds) {
		this.menuIds = menuIds;
	}
	
	public long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(long[] userIds) {
		this.userIds = userIds;
	}


	public void setShowDeptIds(List<Long> showDeptIds) {
		this.showDeptIds = showDeptIds;
	}

	public void setJobStr(String jobStr) {
		this.jobStr = jobStr;
	}

	public void setDepartIds(List<Long> departIds) {
		this.departIds = departIds;
	}

	public void setJobDeptId(Long jobDeptId) {
		this.jobDeptId = jobDeptId;
	}

	public void setJobLevel(Integer jobLevel) {
		this.jobLevel = jobLevel;
	}

	/***
	 * 新增角色
	 * @return
	 */
	public String save() {
		try {
			IdentityUser user = userSessionManagementService.findByContext();
			role.setCreator(String.valueOf(user.getId()));
			role.setUpdater(String.valueOf(user.getId()));
			roleMgmtService.create(role);
			dataMap.put("msg", "新增成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "新增失败!");
		}
		return SUCCESS;
	}

	/**
	 * 修改角色
	 * @return
	 */
	public String update() {
		try {
			IdentityUser user = userSessionManagementService.findByContext();
			Role r = roleMgmtService.findById(role.getId());
			r.setName(role.getName());
			r.setDescription(role.getDescription());
			r.setActive(role.isActive());
			r.setUpdater(String.valueOf(user.getId()));
			r.setLevel(role.getLevel());
			roleMgmtService.update(r);
			dataMap.put("msg", "修改成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "修改失败!");
		}
		return SUCCESS;
	}

	/**
	 * 删除角色
	 * @return
	 */
	public String remove() {
		roleMgmtService.remove(role);
		return SUCCESS;
	}

	/**
	 * 根据角色id查询
	 * @return
	 */
	public String findById() {
		Role role1 = roleMgmtService.findById(role.getId());
		dataMap.put("role", role1);
		return SUCCESS;
	}

	/**
	 * 分页查询
	 * @return
	 */
	public String findPage() {
		Pagination<Role> page = new Pagination<Role>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nameLike", role.getName());
		Ordering order = new Ordering();
		order.addDesc("CREATED");	//根据创建日期显示
		order.addDesc("ID");		//根据ID降序显示
		Pagination<Role> pageData = roleMgmtService.findPagination(param,
				order, page);
		
		dataMap.put("total", pageData.getCount());
		dataMap.put("rows",pageData.getItems());
		return SUCCESS;
	}
	
	/**
	 * 查询所有已激活的角色
	 * @return
	 */
	public String findAll() {
		dataMap.put("datas", roleMgmtService.findAll());
		return SUCCESS;
	}
	
	public String findList() {
		dataMap.put("datas", roleMgmtService.findList());
		return SUCCESS;
	}
	
	/**
	 * 设置菜单权限
	 * @return
	 */
	public String saveMenu() {
		roleMgmtService.assignMenu(role.getId(), menuIds);
		return SUCCESS;
	}
	
	/**
	 * 设置部门权限
	 * @return
	 */
	public String saveDepart() {
		roleMgmtService.assignDepart(role.getId(),departIds);
		return SUCCESS;
	}
	
	/**
	 * @Description :部门权限设置-（sql批量增加）
	 * @author : shaowei
	 * @return
	 */
	public String saveDepartAll(){
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", role.getId());
		params.put("departIds",departIds);
		params.put("showDeptIds", showDeptIds);
		params.put("jobDeptId", jobDeptId);
		params.put("jobStr",jobStr);
		params.put("jobLevel", jobLevel);
	    roleMgmtService.assignDepartAll(params);
	    return SUCCESS;
	}
	
	/**
	 * 角色名称是否存在
	 * @return
	 */
	public String isExists() {
		boolean flag = roleMgmtService.isExists(role.getId(), role.getName());
		if(flag) {
			dataMap.put("valid", false);
		}else {
			dataMap.put("valid", true);
		}
		return SUCCESS;
	}
	
	/** 分配角色 */
	public String saveUserRole() {
		userMgmtService.assignUser(role.getId(), userIds);
		return SUCCESS;
	}
	
	/**
	 * @Description: 清空用户所有关联的部门信息 
	 * @return String
	 */
	public String cleaerAllDepts() {
		String result = "清空成功!";
		try {
			roleMgmtService.removeRoleDepartByRole(role.getId());
		}catch (Exception e) {
			result = "清空失败!请重试";
		}
		dataMap.put("msg", result);
		return SUCCESS;
	}

	@Override
	public Role getModel() {
		if (role == null) {
			role = new Role();
		}
		return role;
	}

}
