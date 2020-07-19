package com.fline.form.controller;

import com.fline.form.constant.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/form")
public class FormController {

    @Value("${sso.puser-url}")
    private String puserUrl;
    @Value("${sso.uuser-url}")
    private String uuserUrl;

    @RequestMapping("/app")
    public void gotoApp(String dUserType, String dQlInnerCode, HttpServletResponse response) throws IOException {
        //puser：个人用户;uuser：法人用户
        String ssoUrl = ("puser".equals(dUserType) ? puserUrl : uuserUrl) + (Constant.APP + dQlInnerCode);
        response.sendRedirect(ssoUrl);
    }

}
