package com.fline.form.controller;

import com.alibaba.fastjson.JSONObject;
import com.commnetsoft.proxy.SsoClient;
import com.commnetsoft.proxy.model.CallResult;
import com.commnetsoft.proxy.model.UserInfo;
import com.commnetsoft.proxy.util.ConfigHelper;
import com.fline.form.mgmt.service.LegalSsoMgmtService;
import com.fline.form.mgmt.service.TempInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;

@Controller
public class SsoLoginController {

    private Log logger = LogFactory.getLog(SsoLoginController.class);
    private String contentType = "text/html;charset=utf-8";

    @Autowired
    private TempInfoService tempInfoService;
    @Autowired
    private LegalSsoMgmtService legalSsoMgmtService;

    @RequestMapping("/ssoLogin.action")
    public void personLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("ssoLogin begin");
        response.setContentType(contentType);

        PrintWriter out = response.getWriter();
        String ticket = request.getParameter("ticket");//获取票据
        String sp = request.getParameter("sp");//具体事项页面地址 如http://aa.com/item?id=3232

        SsoClient client = SsoClient.getInstance();
        //登录认证
        CallResult cr = client.login(request, ticket);
        logger.info("单点登录，错误码："+cr.getResult()+"，错误信息："+cr.getErrmsg()+"。 ");
        if("0".equals(cr.getResult())){//认证成功登录系统
            logger.info("ssoLogin valid success");
            UserInfo user = client.getUser(request);
            logger.info("ssoLogin user info :" + user.getLoginname() + " " + user.getUsername());

            String userId = user.getUserid();
            String userName = user.getUsername();
            String cname = user.getCompanyname();
            String idtype = user.getIdtype();
            String idnum = user.getIdnum();
            String mobile = user.getMobile();

            if(StringUtils.isEmpty(sp)){//跳转到首页
                System.out.println("*****index");
                response.sendRedirect("app/error.html");
            }
            else {//跳转到具体事项页面
                String url;
                if (sp.startsWith("app:")) {
                    url = tempInfoService.createFormByPersonal(sp, userId, idnum, userName, mobile,"2");
                }
                else {
                    try {
                        userName = URLEncoder.encode(userName, "UTF-8");
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        cname = URLEncoder.encode(cname, "UTF-8");
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        idtype = URLEncoder.encode(idtype, "UTF-8");
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                    url = sp + "?userId=" + userId + "&loginName=" + user.getLoginname()  + "&idnum=" + idnum
                            + "&username=" + userName + "&companyname=" + cname + "&mobile=" + mobile + "&idtype=" + idtype;
                }
                System.out.println(url);
                response.sendRedirect(url);
            }
        }else{//认证失败
            out.print("登录失败，错误码："+cr.getResult()+"，错误信息："+cr.getErrmsg()+"。 ");
            String url = ConfigHelper.getProperty("url");
            String servicecode = ConfigHelper.getProperty("servicecode");
            url = url+"usp.do?action=ssoLogin&servicecode="+servicecode;
            out.print(" <a href='"+url+"'>重新登录</a>");
        }
        //说明:因函数返回类型为void类型,即可不用返回，直接输出；
        out.close();
        out.flush();
    }

    @RequestMapping("/servlet/legalSso")
    public void legalLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        // 跳转Url
        String redirectUrl = "";

        // 从Request请求的参数中获取ssotoken
        String ssotoken = request.getParameter("ssotoken");

        // 取具体办事事项地址（若此项有值，成功登录后请跳转此地址到具体事项，否则跳转系统首页）
        String gotoUrl = request.getQueryString();
        if (null != gotoUrl && !gotoUrl.trim().equals("")) {
            // 清理事项地址前的“goto=”标识
            gotoUrl = gotoUrl.substring(5);
        }

        // 验证令牌并获取用户的登录信息
        JSONObject jsonObj = legalSsoMgmtService.doQuery(ssotoken);
        int errCode = jsonObj.getIntValue("errCode");

        // errCode = 0 表示认证成功
        if (0 == errCode) {
            // 验证成功
            String info = jsonObj.getString("info");
            JSONObject legalInfo = JSONObject.parseObject(info);

            // 企业名称
            String companyName = legalInfo.get("CompanyName").toString();

            // 工商注册号
            String regNumber = null;
            Object regNumberObj = legalInfo.get("CompanyRegNumber");
            if (null != regNumberObj) {
                regNumber = regNumberObj.toString();
            }

            // 统一社会信用代码
            String uniscid = null;
            Object uniscidObj = legalInfo.get("uniscid");
            if (null != uniscidObj) {
                uniscid = uniscidObj.toString();
            }

            // 用户id，政务服务网账号唯一标识
            String userid = null;
            Object useridObj = legalInfo.get("userId");
            if (null != useridObj) {
                userid = useridObj.toString();
            }

            // 经办人手机号
            String mobile = null;
            Object mobileObj = legalInfo.get("attnPhone");
            if (null != mobileObj) {
                mobile = mobileObj.toString();
            }

            //经办人证件类型
            String idtype = null;
            Object idtypeObj = legalInfo.get("attnIDType");
            if (null != idtypeObj) {
                idtype = idtypeObj.toString();
            }

            String idnum = StringUtils.isEmpty(uniscid) ? regNumber : uniscid;

            String companyLegRep = null;
            Object companyLegRepObj = legalInfo.get("CompanyLegRep");
            if (null != companyLegRepObj) {
                companyLegRep = companyLegRepObj.toString();
            }

            String companyType = null;
            Object companyTypeObj = legalInfo.get("CompanyType");
            if (null != companyTypeObj) {
                companyType = companyTypeObj.toString();
            }

            String companyAddress = null;
            Object companyAddressObj = legalInfo.get("CompanyAddress");
            if (null != companyAddressObj) {
                companyAddress = companyAddressObj.toString();
            }

            String attnName = null;
            Object attnNameObj = legalInfo.get("attnName");
            if (null != attnNameObj) {
                attnName = attnNameObj.toString();
            }

            String attnIDNo = null;
            Object attnIDNoObj = legalInfo.get("attnIDNo");
            if (null != attnIDNoObj) {
                attnIDNo = attnIDNoObj.toString();
            }

            if(gotoUrl.startsWith("app:")) {
                redirectUrl = tempInfoService.createFormByLegalMan(gotoUrl, userid, idnum, companyName, mobile,"2", companyLegRep, companyType, companyAddress, attnName, attnIDNo, regNumber);
            }
        } else {
            // 验证失败，跳转登录失败的页面
            redirectUrl = "app/error.html";
        }
        logger.info("redirectUrl:" + redirectUrl);
        response.setHeader("refresh", "1;URL=" + redirectUrl);
    }
}
