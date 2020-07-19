package com.fline.yztb.mgmt.service;

import com.fline.yztb.access.model.CRsa;
import com.fline.yztb.access.service.RSaAccessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class RsaServiceTest {
    @Autowired
    private RSaMgmtService rSaMgmtService;
    @Test
    public void saveTest() throws ParseException {
        CRsa cRsa=new CRsa();
        cRsa.setPriKey("kkkkk");
        cRsa.setPubKey("wwwww");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        cRsa.setCreateDate(sdf.parse("2017-01-02"));
        cRsa.setExpireDate(sdf.parse("2019-11-23"));
        rSaMgmtService.create(cRsa);
        System.out.println(cRsa.getId());
    }
    @Test
    public void selectTest(){
        CRsa cRsa=rSaMgmtService.findLatest();
        System.out.println(cRsa);
    }

}
