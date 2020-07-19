package com.fline.form.config;

import com.fline.form.interceptor.FormBizInterceptor;
import com.fline.form.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("classpath:form-interceptor-path.properties")
@Import({FormBizInterceptor.class, SecurityInterceptor.class})
@Configuration
public class DefaultMvcConfig implements WebMvcConfigurer {

    @Value("#{'${form-biz.paths}'.split(',')}")
    private String[] bizPaths;
    @Value("#{'${form-security.path}'.split(',')}")
    private String[] securityPaths;

    @Autowired
    private FormBizInterceptor formBizInterceptor;
    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor).addPathPatterns(securityPaths).order(1);
        registry.addInterceptor(formBizInterceptor).addPathPatterns(bizPaths).order(2);
    }
}
