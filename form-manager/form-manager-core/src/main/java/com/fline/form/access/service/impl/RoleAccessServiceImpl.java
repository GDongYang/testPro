package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Role;
import com.fline.form.access.service.RoleAccessService;

public class RoleAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Role> implements RoleAccessService {
	
	public void saveRoleMenu(long roleId, long menuId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		param.put("menuId", menuId);
		getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Role.saveRoleMenu", param);
	}
	
	public void removeRoleMenuByRole(long roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		getIbatisDataAccessObject().remove(namespace,"removeRoleMenuByRole", param);
	}
	
	public void removeRoleMenuByMenu(long menuId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuId", menuId);
		getIbatisDataAccessObject().remove(namespace,"removeRoleMenuByMenu", param);
	}
	
	public void removeUserRoleByRole(long roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		getIbatisDataAccessObject().remove(namespace,"removeUserRoleByRole", param);
	}
	
	public boolean isExists(long id,String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("name", name);
		return count("isExists", param) > 0;
	}

	@Override
	public List<Role> findByUserRole(long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", id);
		return getIbatisDataAccessObject().find(namespace, "findByUserRole", param);
	}

	@Override
	public void removeRoleDepartByRole(long roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		getIbatisDataAccessObject().remove(namespace,"removeRoleDepartByRole", param);
		
	}

	@Override
	public void saveRoleDepart(long roleId, long departId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		param.put("departId", departId);
		getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Role.saveRoleDepart", param);
		
	}

    @Override
    public void saveRoleDepartAll(Map<String, Object> params) {
        getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Role.saveRoleDepartAll", params);
    }

	@Override
	public List<Long> findDeptIdsByRole(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
            .queryForList("Role.findDeptIdsByRole", params);
	}

	@Override
	public void removeDeptIds(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Role.removeDeptIds", params);		
	}

}
