package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.User;
import com.fline.form.access.service.UserAccessService;
import com.fline.form.mgmt.service.UserMgmtService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service("userMgmtService")
public class UserMgmtServiceImpl implements UserMgmtService {
	private Log logger = LogFactory.getLog(UserMgmtServiceImpl.class);
	@Resource
	private UserAccessService userAccessService;

	@Override
	public Pagination<User> findPagination(Map<String, Object> param,
			Ordering order, Pagination<User> page) {
		return userAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(User user) {
		userAccessService.update(user);
	}

	@Override
	public void remove(User user) {
		userAccessService.remove(user);
	}

	@Override
	public User create(User user,long roleId) {
		userAccessService.create(user);
		//saveUserRole(user.getId(),198L);
		return user;
	}

	@Override
	public User findById(long id) {
		return userAccessService.findById(id);
	}

	@Override
	public List<User> find(Map<String, Object> param) {
		return userAccessService.find(param);
	}

	@Override
	public void authorize(User user) {
		userAccessService.authorize(user);
	}

	@Override
	public void passWordReset(String id, String password) {
		userAccessService.passWordReset(id, password);
	}

	@Override
	public void removeUserRole(long userId) {
		userAccessService.removeUserRole(userId);
	}

	@Override
	public void saveUserRole(long userId, long roleId) {
		userAccessService.saveUserRole(userId, roleId);
	}

	@Override
	public void updateCertNo(long id, String certNo) {
		userAccessService.updateCertNo(id, certNo);
		
	}


	public List<User> findByRoleId(long roleId) {
		return userAccessService.findByRoleId(roleId);
	}
	
	public List<User> findAll() {
		return userAccessService.findAll();
	}
	
	public void removeURByRole(long roleId) {
		userAccessService.removeURByRole(roleId);
	}
	
	public List<User> findList(Map<String,Object> parameter) {
		//return userAccessService.findList(parameter);
		return null;
	}
	
	public int assignPosition(long position, long[] ids) {
		return userAccessService.assignPosition(position, ids);
	}
	
	public int removePosition(long position) {
		return userAccessService.removePosition(position);
	}

	/**
	 * 分配用户
	 */
	@Override
	public void assignUser(long roleId, long[] userIds) {
		removeURByRole(roleId);
		if(userIds != null && userIds.length > 0) {
			for(long userId : userIds) {
				saveUserRole(userId, roleId);
			}
		}
	}
	
	/**
	 * 分配角色
	 */
	@Override
	public void assignRole(long userId, long[] roleIds) {
		removeUserRole(userId);
		if (roleIds != null && roleIds.length > 0) {
			for (long roleId : roleIds) {
				saveUserRole(userId, roleId);
			}
		}
	}
	
}
