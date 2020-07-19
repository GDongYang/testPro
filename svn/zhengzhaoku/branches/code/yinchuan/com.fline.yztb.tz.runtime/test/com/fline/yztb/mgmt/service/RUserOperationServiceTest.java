package com.fline.yztb.mgmt.service;

import com.fline.yztb.access.model.CRsa;
import com.fline.yztb.access.model.UserOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class RUserOperationServiceTest {
    @Autowired
    private UserOperationMgmtService userOperationMgmtService;
    @Test
    public void saveTest() throws ParseException {
        UserOperation userOperation=new UserOperation();
        userOperation.setUserId(222L);
        userOperation.setMenuId(333L);
        userOperation.setCreateDate(new Date());
        userOperation.setOperateType("0123");
        userOperationMgmtService.create(userOperation);
        System.out.printf("aaaa");

    }
    @Test
    public void selectTest(){
        List<UserOperation>userOperations=userOperationMgmtService.findByUserId(222L);
        System.out.printf("aaaa");
    }
    @Test
    public void batchInsertTest(){
        List<UserOperation>userOperations=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UserOperation userOperation=new UserOperation();
            userOperation.setUserId(222L+i);
            userOperation.setMenuId(333L+i);
            userOperation.setCreateDate(new Date());
            userOperation.setOperateType("01");
            userOperations.add(userOperation);
        }
        userOperationMgmtService.insertBatch(userOperations);
    }

}
