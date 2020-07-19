package com.fline.form.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fline.form.constant.UserSessionConst;
import com.fline.form.mgmt.service.ServiceAccountMgmtService;
import com.fline.form.util.JwtUtil;
import com.fline.form.util.SpringUtil;

public class CheckLoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if(checkRequestURIIntNotFilterList(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		int restRet = checkAccountURI(request);
		if (restRet == 1) {
			filterChain.doFilter(request, response);
			return;
		}
		else if (restRet == -1) {
			response.sendError(401, "E000001");
			return;
		}
		else if(restRet == -2){
			response.sendError(401, "E000002");
			return;
		}
		else if(restRet == -3){
			response.sendError(401, "E000006");
			return;
		}
		
		if (checkToken(request)) {
			filterChain.doFilter(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/login.jsp?returnUrl=" + request.getRequestURL());
		}

	}

    private boolean checkToken(HttpServletRequest request) {
        Cookie[] requestCookie = request.getCookies();
        if (requestCookie == null) {
            return false;
        }
        String token = "";
        for (Cookie c:requestCookie) {
            if(UserSessionConst.TOKEN.equals(c.getName())) {
                token = c.getValue();
            }
        }
        return JwtUtil.verify(token);
    }

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request)  {
	  String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
	  return uri.equals("/login.jsp")||uri.equals("/loginFline.jsp")||uri.equals("/loginAdmin.jsp")||
			  uri.equals("/reg.jsp")||uri.equals("/ssoLogin.action")||
			  uri.contains("/login.jsp?returnUrl") ||uri.equals("/loginCard.jsp")||uri.equals("/yc/app/indexYc.html")||
			  uri.endsWith(".js")||
			  uri.endsWith(".css")||
			  uri.endsWith(".swf")||
			  uri.endsWith(".ttf")||
			  uri.endsWith(".woff")||
			  uri.endsWith(".png")||
			  uri.endsWith(".gif")||
			  uri.endsWith(".jpg")||
			  uri.endsWith(".map")||
			  uri.endsWith("loadCurrentUser.action")||
			  uri.endsWith("login.action")||
			  uri.endsWith("logout.action")||
			  uri.endsWith("authCode.action")||
			  uri.endsWith("getCodeGenerate.action")||
			  uri.contains("/resources/") ||
              uri.contains("tempInfoAction") ||
              uri.contains("userAction") ||
              uri.contains("demo4.html") ||
              uri.contains("driveCardInfoChange.html") ||
              uri.contains("replenishmentNumberPlate.html") ||
              uri.contains("driveCardChange.html") ||
              uri.contains("synchron");
	}
	
	private int checkAccountURI(HttpServletRequest request)  {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		if (uri.contains("/rest/") && !uri.contains("/excel/") && !uri.contains("v2/api-docs") &&
                !uri.contains("swagger") && !uri.contains("/formEditor/")) {
			String askIP = request.getRemoteAddr();
			//判断ip是否在白名单

			String account = request.getHeader("Username");
			String passwdDigest = request.getHeader("PasswdDigest");
			String nonce = request.getHeader("Nonce");
			String created = request.getHeader("Created");
			String applicantUnit = "";
			String applicantUser = "";
			try {
				if (request.getHeader("ApplicantUnit") != null) {
					applicantUnit = URLDecoder.decode(request.getHeader("ApplicantUnit"), "UTF-8");
					applicantUser = URLDecoder.decode(request.getHeader("ApplicantUser"), "UTF-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ServiceAccountMgmtService serviceAccountMgmtService = (ServiceAccountMgmtService)SpringUtil.getBean("serviceAccountMgmtService");
			return serviceAccountMgmtService.login(account, passwdDigest, nonce, created, applicantUnit, applicantUser);
		}
		return 0;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
