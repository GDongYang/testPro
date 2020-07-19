package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.User;

public interface UserAccessService extends AbstractNamespaceAccessService<User>{
	
	void authorize(User user);

	void passWordReset(String id, String password);

	void removeUserRole(long userId);

	void saveUserRole(long userId, long roleId);

	void updateCertNo(long id, String certNo);
	
	List<User> findByRoleId(long roleId);
	
	void removeURByRole(long roleId);
	
	int assignPosition(long position, long[] ids);
	
	int removePosition(long position);
}
