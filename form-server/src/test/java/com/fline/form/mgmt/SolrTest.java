package com.fline.form.mgmt;

import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.vo.CertTempVo;
import com.fline.form.vo.RequestLog;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrTest {

    @Autowired
    private FdpClientMgmtService fdpClientMgmtService;

    @Test
    public void test() {
        RequestLog requestLog = new RequestLog();
        requestLog.setCerName("1");
        requestLog.setCerNo("cerNo");
        requestLog.setItemInnerCode("itemInnerCode");
        requestLog.setRequestName("ceshi");
        requestLog.setRequestCode("certCode");
        requestLog.setDataSource("一证通办");
        requestLog.setRequestTime(new Date());
        fdpClientMgmtService.saveRequestLog(requestLog);
    }
}
