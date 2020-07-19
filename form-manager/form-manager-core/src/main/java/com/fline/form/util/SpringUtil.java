
package com.fline.form.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import com.fline.form.constant.GlobalConstants;



public class SpringUtil implements ApplicationContextAware, ServletContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        SpringUtil.applicationContext = arg0;
    }
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    private ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext sc) {
        this.servletContext=sc;
        String path = sc.getRealPath("");
        GlobalConstants.REAL_PATH = path;
        System.out.println(path);
    }
}
