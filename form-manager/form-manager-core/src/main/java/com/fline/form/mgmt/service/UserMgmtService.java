package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.User;

public interface UserMgmtService {
	Pagination<User> findPagination(Map<String, Object> param, Ordering order,
			Pagination<User> page);

	void update(User user);

	void remove(User user);

	User create(User user,long roleId);

	User findById(long id);
	
	List<User> find(Map<String, Object> param);
	
	void authorize(User user);

	void passWordReset(String id, String password);

	void removeUserRole(long userId);

	void saveUserRole(long userId, long roleId);
	
	void updateCertNo(long id, String certNo);
	
	List<User> findByRoleId(long roleId);
	
	List<User> findAll();
	
	void removeURByRole(long roleId);
	
	List<User> findList(Map<String,Object> parameter);
	
	int assignPosition(long position, long[] ids);
	
	int removePosition(long position);

	void assignUser(long roleId, long[] userIds);
	
	void assignRole(long userId, long[] roleIds);
}
