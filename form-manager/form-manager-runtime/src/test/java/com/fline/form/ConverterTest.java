package com.fline.form;

import com.fline.form.access.model.Item;
import com.fline.form.mapper.Model2VoConverter;
import com.fline.yztb.vo.ItemVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConverterTest {

    @Test
    public void test() {
        Item item = new Item();
        item.setDepartmentName("cessss");
        ItemVo itemVo = Model2VoConverter.INSTANCE.toVo(item);
        System.out.println(itemVo.getDeptName());
    }
}
