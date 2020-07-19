package com.fline.form.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.annotation.IgnoreExpire;
import com.fline.form.constant.Constant;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.impl.FormInfoContext;
import com.fline.form.mgmt.service.impl.FormMgmtServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class FormBizInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(FormBizInterceptor.class);

    @Autowired
    private FdpClientMgmtService fdpClientMgmtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("request uri:{}, parameter:{}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()));
        Map<String, String> pathVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if(pathVars.containsKey(Constant.FORM_BUSI_CODE)) {
            //获取表单信息
            String formBusiCode = pathVars.get(Constant.FORM_BUSI_CODE);
            MDC.put(Constant.FORM_BIZ_INFO, "formBizCode[" + formBusiCode + "]" );
            JSONObject formInfo = fdpClientMgmtService.loadFormInfo(formBusiCode);
            if (formInfo == null) {
                throw new BaseException("加载表单数据失败:无法获取表单信息");
            }
            //有IgnoreExpire注解不需要判断是否过期
            if(handler instanceof HandlerMethod) {
                HandlerMethod h = (HandlerMethod)handler;
                IgnoreExpire expireAnnotation = h.getMethodAnnotation(IgnoreExpire.class);
                if (expireAnnotation == null) {
                    // 检查授权, 先比较创建时间，误差在1小时以内就可以通过
                    Date formCreated = formInfo.getDate("FORM_CREATED");
                    if (isExpire(formCreated)) {
                        throw new BaseException("加载表单数据失败:时间超出一小时");
                    }
                }
            }
            FormInfoContext.set(formInfo);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        FormInfoContext.remove();
        MDC.remove(Constant.FORM_BIZ_INFO);
    }

    /**
     * 校验是否过期，过期时间一个小时
     * @param formCreated
     * @return
     */
    private boolean isExpire(Date formCreated) {
        LocalDateTime formCreatedTime = formCreated.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime expireTime = formCreatedTime.plusHours(1);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expireTime);
    }
}
