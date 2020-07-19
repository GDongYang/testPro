package com.fline.form.aop;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.User;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;

@Component
public class DeptPrivilegeAdvice implements MethodInterceptor {

	@Resource
	private UserSessionManagementService userSessionManagementService;
	@Resource
	private DepartmentMgmtService departmentMgmtService;
	
	private String adminUser = "admin,adminMax";
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		User user = (User) userSessionManagementService.findByContext();
		if (adminUser.contains(user.getUsername())) {
			return invocation.proceed();
		}
		List<Long> deptIds = departmentMgmtService.findDeptsByUser(user.getId());
		if(!Detect.notEmpty(deptIds)) {
			return null;
		}
		HashMap<String, Object> param = (HashMap<String, Object>) invocation.getArguments()[0];
		param.put("deptIds", deptIds);
		return invocation.proceed();
	}
	
}