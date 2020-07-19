package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Role;

public interface RoleAccessService extends AbstractNamespaceAccessService<Role> {
	
	void saveRoleMenu(long roleId, long menuId);
	
	void removeRoleMenuByRole(long roleId);
	
	void removeRoleMenuByMenu(long menuId);
	
	boolean isExists(long id,String name);

	List<Role> findByUserRole(long id);
	
	void removeUserRoleByRole(long roleId);

	void removeRoleDepartByRole(long roleId);

	void saveRoleDepart(long roleId, long departId);
	
	/**
	 * @Description :部门权限设置-（sql批量增加）
	 * @author : shaowei
	 * @param roleId
	 * @param list
	 */
	void saveRoleDepartAll(Map<String, Object> params);
	
	/**
	 * @Description: 根据角色Id获取所有关联的部门Id 
	 * @param params roleId 角色Id
	 * @return List<Long> 关联的部门Id
	 */
	List<Long> findDeptIdsByRole(Map<String,Object> params);
	
	/**
	 * @Description: 删除指定角色的部分部门列表
	 * @param params  deptIds : 需要删除的部门ID列表 roleId : 角色Id
	 * @return void
	 */
	void removeDeptIds(Map<String, Object> params);

}
