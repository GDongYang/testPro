package com.fline.form.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.UserSessionConst;
import com.opensymphony.xwork2.ActionContext;

public abstract class UserSessionCookieUtil extends Detect {

	public static void addCookie(String name, String value) {
		Cookie cookie = null;
		if (null != ActionContext.getContext() && null != ServletActionContext.getResponse()) {
			if (null != ActionContext.getContext() && null != ServletActionContext.getRequest()) {
				Cookie[] cookies = ServletActionContext.getRequest().getCookies();
				cookie = getCookie(name, cookies);
			}
			if (null == cookie) {
				cookie = new Cookie(name, value);

			} else {
				cookie.setValue(value);
			}
			cookie.setPath("/");
			ServletActionContext.getResponse().addCookie(cookie);

		}
	}
	
	public static void removeCookie(String name)
	{
		Cookie cookie = null;
		if (null != ActionContext.getContext() && null != ServletActionContext.getResponse()) {
			if (null != ActionContext.getContext() && null != ServletActionContext.getRequest()) {
				Cookie[] cookies = ServletActionContext.getRequest().getCookies();
				cookie = getCookie(name, cookies);
			}
			if (null != cookie) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				ServletActionContext.getResponse().addCookie(cookie);
			} 
		}
	}
	
	public static String getCookieValue(String name) {
		return getCookieValue(name, getCookies());
	}

	public static String getCookieValue(String name, Cookie[] cookies) {
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (name.equalsIgnoreCase(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static Cookie getCookie(String name, Cookie[] cookies) {
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (name.equalsIgnoreCase(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	public static Cookie[] getCookies() {
		Cookie[] cookies = null;
		if (null != ActionContext.getContext() && null != ServletActionContext.getRequest()) {
			cookies = ServletActionContext.getRequest().getCookies();
		}
		return cookies;
	}

	public static long getUserSessionIdCookieValue() {
		String value = getCookieValue(UserSessionConst.USER_SESSION_ID);
		return asLong(value, 0);
	}

	public static String getRemoteHostName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return (request.getRemoteHost() == null) ? request.getRemoteAddr() : request.getRemoteHost();
	}

	public static String getRemoteIpAddr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
