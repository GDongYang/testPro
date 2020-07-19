package com.fline.form.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {
    //事项库调用地址
    private static final String url="http://172.31.84.143:31001/item/api/info/getItemInfo/";
    @Autowired
    private ItemMgmtService itemMgmtService;

    @GetMapping("/situation/{formBusiCode}")
    public ResponseResult<Map<String, Object>> findSituations(@PathVariable String formBusiCode) {
        Map<String, Object> situations = itemMgmtService.findSituations(formBusiCode);
        return ResponseResult.success(situations);
    }

    /**
     *请求事项库接口 获取事项库事项信息
     * @param itemCode 表单中心的事项编码
     * @throws Exception
     * @author mh
     * @return
     */

    @GetMapping("/getItemInfo/{itemCode}")
    public ResponseResult getItemInfo(@PathVariable String itemCode){
        JSONArray data=null;
        try {
            HttpClientResult result = HttpClientUtil.doGet(url + itemCode);
            JSONObject jsonObject = JSON.parseObject(result.getContent());
            data = JSON.parseArray(jsonObject.get("data").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success(data);
    }
}
