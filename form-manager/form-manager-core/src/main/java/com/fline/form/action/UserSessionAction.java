package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.User;
import com.fline.form.mgmt.service.SecurityCodeMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;

import java.util.HashMap;
import java.util.Map;

public class UserSessionAction extends AbstractAction {

	private static final long serialVersionUID = -1766083959991364651L;

	private String username;
	private String password;
	private String returnUrl;
	private int parentId;
	private String code;

	private String redirectPage;

	private User user;

	private String authCode;

	private String certInfo;

	private String signature;

	private String name;
	
	private String cardNo;

	private String codeId;
	
    private SecurityCodeMgmtService securityCodeMgmtService;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCertInfo() {
		return certInfo;
	}

	public void setCertInfo(String certInfo) {
		this.certInfo = certInfo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    private UserSessionManagementService userSessionManagementService;

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	public String login() {
		String securityCode = securityCodeMgmtService.getFromCache(codeId);
		if (!Detect.notEmpty(securityCode)) {
			resultMap.put("result", 0);
			resultMap.put("msg", "无法猎取验证码!");
			return SUCCESS;
		} else {
			if (!Detect.notEmpty(code)) {
				resultMap.put("result", 0);
				resultMap.put("msg", "验证码为空 !");
				return SUCCESS;
			} else {
				if (!code.equalsIgnoreCase(securityCode)) {
					resultMap.put("result", 0);
					resultMap.put("msg", "验证码错误,请重新填写验证码 !");
					return SUCCESS;
				}
			}
		}

		String returnString = null;

		if (!(Detect.notEmpty(username)) || !(Detect.notEmpty(password))) {
			resultMap.put("result", 0);
			resultMap.put("msg", "用户名或密码为空 !");
			return SUCCESS;
		}

		try {
			this.userSessionManagementService.login(username, password);
			resultMap.put("result", 1);
		} catch (Throwable e) {
			returnString = e.getMessage();
			if (null == returnString) {
				returnString = "error";
			}
			resultMap.put("result", 0);
			resultMap.put("msg", returnString);
		}

		return SUCCESS;
	}

	public String logout() {
		this.userSessionManagementService.logout();
		redirectPage = "url:login.jsp";
		return "loginSuccess";
	}

	public String loadCurrentUser() {
		try {
			user = (User) this.userSessionManagementService.findByContext();
			/*
			 * user = new User(); user.setId(iuser.getId());
			 * user.setName(iuser.getName());
			 * user.setUsername(iuser.getUsername());
			 */
			// user.setPrivileges(this.userSessionManagementService.findPrivilegesByContext());
		} catch (Exception e) {
			return "currentUser";
		}
		return "currentUser";
	}

	public void setUserSessionManagementService(
			UserSessionManagementService userSessionManagementService) {
		this.userSessionManagementService = userSessionManagementService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public void setSecurityCodeMgmtService(SecurityCodeMgmtService securityCodeMgmtService) {
        this.securityCodeMgmtService = securityCodeMgmtService;
    }
}
