package com.fline.request;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fline.yztb.access.model.Business;
import com.fline.yztb.mgmt.service.BusinessMgmtService;

public class Test {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-server-index.xml");
		BusinessMgmtService businessMgmtService = (BusinessMgmtService) context.getBean("businessMgmtService");
		Business business = businessMgmtService.findById(2554);
		System.out.println(business.getAccessIP());
	}

}
