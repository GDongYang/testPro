package com.fline.form.mgmt.service;

import com.feixian.aip.platform.model.type.IdentityUser;
import com.feixian.aip.platform.model.type.IdentityUserSession;
import com.feixian.aip.platform.usercontext.vo.UserContext;
import com.feixian.aip.platform.usersession.support.UserSessionAuthorizationException;
import com.feixian.aip.platform.usersession.vo.UserSessionContext;

public interface UserSessionManagementService {
	
	UserSessionContext login(String username, String password) throws UserSessionAuthorizationException;
	
	void logout();

	IdentityUser findByContext();
	
}
