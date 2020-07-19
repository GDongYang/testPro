package com.fline.yztb.mgmt.service;

import com.fline.yztb.vo.WzmItemCount;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.fline.yztb.access.model.Business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BusinessMgmtServiceTest {
	
	@Autowired
	BusinessMgmtService businessMgmtService;


	@Test
	public void testFindById() throws Exception {
		Business business = businessMgmtService.findById(251720);
		Assert.assertNotNull(business);
		System.out.println(business.getName());
	}
	@Test
	public void testgetWzmItemCount() throws Exception{
	 Map<String,Object>maparam=businessMgmtService.wzmItemCount();
		System.out.println(maparam);
	}

}
