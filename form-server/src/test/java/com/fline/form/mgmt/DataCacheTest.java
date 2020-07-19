package com.fline.form.mgmt;

import com.fline.form.mgmt.service.DataCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataCacheTest {

    @Autowired
    private DataCacheService dataCacheService;

    @Test
    public void testItem() throws Exception {
//        ItemVo item = dataCacheService.getItem("fb065d50-4194-4279-9446-69af77dde316");
//        System.out.println(item.getName());
    }
}
