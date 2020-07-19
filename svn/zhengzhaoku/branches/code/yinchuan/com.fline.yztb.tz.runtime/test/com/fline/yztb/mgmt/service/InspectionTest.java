package com.fline.yztb.mgmt.service;

import com.fline.yztb.quartz.InspectionJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class InspectionTest {

    @Autowired
    private InspectionJobService inspectionJobService;

    @Test
    public void test() {
        inspectionJobService.morning();
    }
}
