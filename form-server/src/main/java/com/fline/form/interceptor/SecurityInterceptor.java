package com.fline.form.interceptor;

import com.feixian.tp.common.util.Detect;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.util.MD5Util;
import com.fline.form.vo.ServiceAccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SecurityInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Autowired
    private DataCacheService dataCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String signature = request.getHeader("signature");
        String timestamp = request.getHeader("timestamp");
        String username = request.getHeader("username");
        logger.info("签名校验开始，username:{}", username);

        if(!Detect.notEmpty(signature)) {
            throw new BaseException("签名为空!");
        }
        ServiceAccountVo account = dataCacheService.getServiceAccount(username);
        if(account == null) {
            throw new BaseException("username不存在!");
        }
        String password = account.getPassword();

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String[]> signParams = new HashMap<>(parameterMap);
        signParams.put("username", new String[]{username});
        signParams.put("password", new String[]{password});
        signParams.put("timestamp", new String[]{timestamp});
        StringBuilder sb = new StringBuilder();
        signParams.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {
            sb.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");
        });
        String md5 = MD5Util.MD5(sb.toString());
        if(!signature.equals(md5)) {
            throw new BaseException("签名错误！");
        }
        return true;
    }
}
