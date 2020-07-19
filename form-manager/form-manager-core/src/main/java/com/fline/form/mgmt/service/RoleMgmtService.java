package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Role;

public interface RoleMgmtService {
	Pagination<Role> findPagination(Map<String, Object> param, Ordering order,
			Pagination<Role> page);

	void update(Role role);

	void remove(Role role);

	Role create(Role role);

	Role findById(long id);
	
	void saveRoleMenu(long roleId, long menuId);
	
	void removeRoleMenuByRole(long roleId);
	
	void removeRoleMenuByMenu(long menuId);
	
	boolean isExists(long id,String name);

	List<Role> findAll();

	List<Role> findByUserRole(long id);

	void removeRoleDepartByRole(long id);

	void saveRoleDepart(long id, long departId);
	
	void assignMenu(long roleId,long[] menuIds);
	
	void assignDepart(long roleId,List<Long> departIds);
	
	/**
	 * @Description :部门权限设置-（sql批量增加）
	 * @author : shaowei
	 * @param roleId
	 * @param departIds
	 */
	void assignDepartAll(Map<String, Object> params);
	
	List<Role> findList();
}
