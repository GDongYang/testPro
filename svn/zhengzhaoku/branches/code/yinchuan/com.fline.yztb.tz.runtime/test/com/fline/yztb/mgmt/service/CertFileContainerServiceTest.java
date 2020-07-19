package com.fline.yztb.mgmt.service;

import com.fline.yztb.access.model.CertFileContainer;
import com.fline.yztb.quartz.BusinessJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * className:RsaServiceTest
 * package:com.fline.yztb.mgmt.service
 * Description:
 *
 * @Date:2019/11/13 12:30
 * @Author:Zhuwm
 */
@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CertFileContainerServiceTest {
    @Autowired
    private CertFileContainerMgmtService certFileContainerMgmtService;

    @Test
    public void save(){
        CertFileContainer certFileContainer=new CertFileContainer();
        certFileContainer.setName("33333");
        certFileContainer.setCertCode("tz_cert037");
        certFileContainer.setCertFileKey("333");
        certFileContainer.setCertType(3333);
        certFileContainer.setSaveDir("3333");
        certFileContainer.setLeftCount(3333);
        certFileContainer.setVersion(3333);
        CertFileContainer certFileContainer1=certFileContainerMgmtService.createOrUpdate(certFileContainer);
        System.out.println(certFileContainer1);
    }

    @Test
    public void find(){
        List<CertFileContainer>certFileContainers=certFileContainerMgmtService.findCountNotZero();
        System.out.println(certFileContainers);
    }
    @Test
    public void syncTest(){
        new BusinessJobService().syncCertFileFromFdp();
    }
}
