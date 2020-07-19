package com.fline.yztb.mgmt.service;

import com.feixian.tp.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MemcachedTest {

    @Autowired
    private Cache cache;

    @Test
    public void put() {
        cache.put("test","测试1");
        //aaa
    }

    @Test
    public void get() {
        System.out.println(cache.get("test"));
    }
}
