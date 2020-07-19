package com.fline.form.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import com.feixian.aip.platform.model.type.IdentityUser;
import com.fline.form.access.model.AccessLog;
import com.fline.form.mgmt.service.AccessLogMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;

@Component
public class AccessLogAdvice implements MethodBeforeAdvice {
	
	@Resource
	private UserSessionManagementService userSessionManagementService;
	@Resource
	private AccessLogMgmtService accessLogMgmtService;

	@Override
	public void before(Method method, Object[] args, Object service)
			throws Throwable {

		IdentityUser iuser = userSessionManagementService.findByContext();
		//判断iuser是否为null，如果为null，则从ThredLocal里取 是否存在 serviceAccount
		AccessLog accessLog = new AccessLog();
		accessLog.setAccessDate(new Date());
		accessLog.setCreator(iuser.getUsername());
		accessLog.setResName(service.getClass()+"."+method.getName());
		accessLog.setUserId(iuser.getId());
		accessLog.setGuid(UUID.randomUUID().toString());
		accessLogMgmtService.create(accessLog);
		/*System.out.println("iuser" + iuser);
		System.out.println("before()" + method.getName());
		System.out.println("service" + service.getClass());*/
	}

}
