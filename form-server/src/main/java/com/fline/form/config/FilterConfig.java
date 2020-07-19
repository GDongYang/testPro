package com.fline.form.config;

import com.alipay.atgbusmng.api.domain.AtgBusSecretKey;
import com.alipay.atgbusmng.api.filter.http.AtgBusSignFilter;
import com.fline.form.filter.AtgBusSignAdapterFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AtgBusSignAdapterFilter> filterRegistrationBean(List<AtgBusSecretKey> secretKeys) {
        AtgBusSignFilter atgBusSignFilter = new AtgBusSignFilter();
        atgBusSignFilter.setAtgBusSecretKeys(secretKeys);
        atgBusSignFilter.setSignUriRegex("^/egov/.*$");// 配置需要签名的过滤器正则表达式

        AtgBusSignAdapterFilter atgBusSignAdapterFilter = new AtgBusSignAdapterFilter();
        atgBusSignAdapterFilter.setAtgBusSignFilter(atgBusSignFilter);

        FilterRegistrationBean<AtgBusSignAdapterFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setFilter(atgBusSignAdapterFilter);
        filterReg.addUrlPatterns("/*");
        return filterReg;
    }
}
