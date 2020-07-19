package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.User;
import com.fline.form.access.service.UserAccessService;


public class UserAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<User> implements
UserAccessService {
	@Override
	public void authorize(User user) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id", user.getId());
		if(!user.getActive()){
			param.put("active", true);
		}else{
			param.put("active", false);
		}
		getIbatisDataAccessObject().update(namespace, "authorize", param);
	}

	@Override
	public void passWordReset(String id, String password) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id", id);
		param.put("passWord", password);
		getIbatisDataAccessObject().update(namespace, "passWordReset", param);
		
	}

	@Override
	public void removeUserRole(long userId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		getIbatisDataAccessObject().update(namespace, "removeUserRole", param);
		
	}

	@Override
	public void saveUserRole(long userId, long roleId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("roleId", roleId);
		getIbatisDataAccessObject().update(namespace, "saveUserRole", param);
	}

	@Override
	public void updateCertNo(long id, String certNo) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id", id);
		param.put("certNo", certNo);
		getIbatisDataAccessObject().update(namespace, "updateCertNo", param);
		
	}
	
	@Override
	public int assignPosition(long position, long[] ids) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("position", position);
		param.put("ids", ids);
		return getIbatisDataAccessObject().update(namespace, "assignPosition", param);
		
	}
	
	@Override
	public int removePosition(long position) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("position", position);
		return getIbatisDataAccessObject().update(namespace, "removePosition", param);
		
	}
	
	public List<User> findByRoleId(long roleId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("roleId", roleId);
		return getIbatisDataAccessObject().find(namespace,"findByRoleId",param);
	}
	
	@Override
	public void removeURByRole(long roleId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("roleId", roleId);
		getIbatisDataAccessObject().remove(namespace, "removeURByRole", param);
	}
	

}