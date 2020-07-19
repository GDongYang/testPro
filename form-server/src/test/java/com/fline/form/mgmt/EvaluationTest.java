package com.fline.form.mgmt;

import com.fline.form.mgmt.service.AtgBizMgmtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluationTest {

    @Autowired
    private AtgBizMgmtService atgBizMgmtService;

    @Test
    public void test() {
        String data = atgBizMgmtService.evaluationUrl("331000191223529067437", "6536e84f-cc33-4dcd-89ee-265e396d5694", "51", "91330103704789206U", "杭州广鸿贸易有限公司");
        System.out.println(data);
    }
}
