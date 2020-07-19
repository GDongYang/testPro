package com.fline.form.mgmt.service.impl;

import com.feixian.aip.platform.access.common.service.impl.AbstractDataAccessServiceImpl;
import com.feixian.aip.platform.model.type.IdentityUser;
import com.feixian.aip.platform.usercontext.util.UserContextThreadLocal;
import com.feixian.aip.platform.usercontext.vo.UserContext;
import com.feixian.aip.platform.usersession.support.UserSessionAuthorizationException;
import com.feixian.aip.platform.usersession.vo.UserSessionContext;
import com.feixian.tp.cache.Cache;
import com.feixian.tp.common.encrypt.PasswordEncoder;
import com.feixian.tp.common.util.Assertion;
import com.feixian.tp.common.vo.Endpoint;
import com.feixian.tp.common.vo.MapParameter;
import com.feixian.tp.dao.DataAccessObjectConst;
import com.fline.form.access.model.User;
import com.fline.form.access.model.UserSession;
import com.fline.form.access.service.UserSessionAccessService;
import com.fline.form.constant.Contants;
import com.fline.form.constant.UserSessionConst;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.fline.form.util.JwtUtil;
import com.fline.form.util.UserSessionCookieUtil;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class UserSessionManagementServiceImpl extends AbstractDataAccessServiceImpl implements UserSessionManagementService {

	private Log logger = LogFactory.getLog(UserSessionManagementServiceImpl.class);

    private static final String USERCONTEXT_PREFIX = "UserContext_";

    private PasswordEncoder passwordEncoder;

    private Cache cache;

    private UserSessionAccessService userSessionAccessService;

    private int expireIntervalMinutes = 30;

    @Override
    public UserSessionContext login(String username, String password) throws UserSessionAuthorizationException {
        return this.login(username, password, 0, null, null);
    }

    @Override
    public void logout() {
        long userSessionId = JwtUtil.getUserSessionId();
        if (userSessionId > 0) {
            UserSession userSession = userSessionAccessService.findById(userSessionId);
            if (null == userSession) {
                return;
            }
            userSession.setActive(false);
            userSession.setUpdateDate(new Date());
            userSessionAccessService.update(userSession);
            this.removeFromCache(String.valueOf(userSessionId));
            UserSessionCookieUtil.removeCookie(UserSessionConst.TOKEN);
        }
    }

    @Override
    public IdentityUser findByContext() {
        String sessionKey = String.valueOf(JwtUtil.getUserSessionId());
        return findByContext(sessionKey);
    }

    private UserSessionContext login(String username, String password, int parentId, Endpoint remoteEndpoint, Endpoint localEndpoint) throws UserSessionAuthorizationException {
        Assertion.notEmpty(username, "username not found");
        User user = loadUserByUsername(username, password, parentId);
        return saveUserSession(user, remoteEndpoint, localEndpoint);
    }

    private UserSessionContext saveUserSession(User user, Endpoint remoteEndpoint, Endpoint localEndpoint) {
    	Date date = new Date();
        UserContext userContext = new UserContext();
        userContext.setUserId(user.getId());
        userContext.setUser(user);
        userContext.setOperateDate(date);
        userContext.setHostAddress(UserSessionCookieUtil.getRemoteIpAddr());
        userContext.setHostName(UserSessionCookieUtil.getRemoteHostName());
        UserSession userSession = this.createUserSession(user.getId(), date, remoteEndpoint, localEndpoint);
        userContext.setUserSessionId(userSession.getId());
        UserSessionContext userSessionContext = new UserSessionContext();
        userSessionContext.setUserContext(userContext);
        userSessionContext.setUserSession(userSession);
        UserContextThreadLocal.setUserContext(userContext);
        this.putIntoCache(String.valueOf(userSession.getId()), userContext);

        String userSessionId = String.valueOf(userSession.getId());
        String token = JwtUtil.generateToken(userSessionId, user.getUsername());
        UserSessionCookieUtil.addCookie(UserSessionConst.TOKEN, token);
        cache.remove(Contants.KEY_USER_PRIVILEGE + user.getId());
        return userSessionContext;
    }

    private User loadUserByUsername(String username, String password, int parentId) {
    	User user = this.findUserByUsername(username, parentId);
        Assertion.notNull(user, "用户不存在");
        Assertion.isTrue(user.getPassword().equals(password)||user.getPassword().equals(encodePassword(password)), "密码错误!");
        Assertion.isTrue(user.getActive(),"用户无效");
        return user;
    }

    private static String asString(Endpoint endpoint) {
        if (null == endpoint) {
            return null;
        }
        return endpoint.toString();
    }

    private UserSession createUserSession(long userId, Date loginDate, Endpoint remoteEndpoint, Endpoint localEndpoint) {
    	UserSession userSession = new UserSession();
        userSession.setUserId(userId);
        userSession.setActive(true);
        userSession.setRemoteEndPoint(asString(remoteEndpoint));
        userSession.setServerEndPoint(asString(localEndpoint));
        userSession.setLoginDate(loginDate);
        userSession.setExpireDate(DateUtils.addMinutes(userSession.getLoginDate(), getExpireIntervalMinutes()));
        return this.getIbatisDataAccessObject().create(UserSession.class.getSimpleName(), userSession);
    }

    private UserContext getFromCache(String key) {
        Object Object = cache.get(generateKey(key));
        if (null != Object && Object instanceof UserContext) {
            return (UserContext) Object;
        }
        UserSession userSession = userSessionAccessService.findById(asLong(key, 0));
        if (null == userSession) {
            return null;
        }
        UserContext userContext = new UserContext();
        userContext.setUserId(userSession.getUserId());
        userContext.setUser(findUserByUserId(userSession.getUserId()));
        if(userContext.getUser() instanceof User) {
//            loadPrivilege((User) userContext.getUser());
        }
        userContext.setUserSessionId(userSession.getId());
        this.putIntoCache(key, userContext);
        return userContext;
    }

    private User findUserByUsername(String username, int parentId) {
        MapParameter parameters = new MapParameter();
        parameters.put(User.ATTRIBUTE_USERNAME, username);
        parameters.put(User.ATTRIBUTE_PARENTID, parentId);
        return this.getIbatisDataAccessObject().findOne(User.class.getSimpleName(), DataAccessObjectConst.STATEMENT_FIND, parameters);
    }

    private User findUserByUserId(long userId) {
        return this.getIbatisDataAccessObject().findById(User.class.getSimpleName(), userId);
    }

    private void putIntoCache(String key, UserContext userContext) {
        cache.put(generateKey(key), getExpireIntervalMinutes(), userContext);
    }

    private void removeFromCache(String key) {
        cache.remove(generateKey(key));
    }

    private String generateKey(String key) {
        return USERCONTEXT_PREFIX + key;
    }

    private String encodePassword(String password) {
    	String s = passwordEncoder.encodePassword(password, null);
    	System.out.println(s);
        return s;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public int getExpireIntervalMinutes() {
        return expireIntervalMinutes;
    }

    public void setExpireIntervalMinutes(int expireIntervalMinutes) {
        this.expireIntervalMinutes = expireIntervalMinutes;
    }
    
	public void setUserSessionAccessService(
			UserSessionAccessService UserSessionAccessService) {
		this.userSessionAccessService = UserSessionAccessService;
	}

	private IdentityUser findByContext(String sessionKey) {
        UserContext tmpUserContext = this.getFromCache(sessionKey);
        if (null != tmpUserContext) {
            return tmpUserContext.getUser();
        }
        return null;
    }

}