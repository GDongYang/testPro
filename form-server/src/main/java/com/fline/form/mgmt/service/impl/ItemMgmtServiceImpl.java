package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.form.vo.SituationVo;
import com.fline.yztb.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemMgmtServiceImpl implements ItemMgmtService {

    @Autowired
    private DataCacheService dataCacheService;

    private String needSignItems = "B2BF9731E9D76D726D1112213D993E54";

    @Override
    public Map<String, Object> findSituations(String formBusiCode) {
        JSONObject formInfo = FormInfoContext.get();
        String itemCode = formInfo.getString("ITEM_CODE");
        ItemVo item = dataCacheService.getItem(itemCode);
        Map<String, Object> mainInfo = new HashMap<>();
        mainInfo.put("title", item.getName());//标题
        mainInfo.put("notice", item.getNotice());//温馨提示
        //获取情形
        List<Map<String, Object>> situations = new ArrayList<>();
        Map<String, SituationVo> situationMap = item.getSituationMap();
        if(Detect.notEmpty(situationMap)) {
            for (String s : situationMap.keySet()) {
                SituationVo situationVo = situationMap.get(s);
                Map<String, Object> situation = new HashMap<>();
                situation.put("name", situationVo.getName());
                situation.put("code", s);
                // situation.put("id", situationVo.getId());
                situation.put("type", situationVo.getType());
                situation.put("describe", situationVo.getDescribe());
                situations.add(situation);
            }
            //情形根据id排序
            situations.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
                Long num1 = o1.get("id") != null ? (Long) o1.get("id") : 0;
                Long num2 = o2.get("id") != null ? (Long) o2.get("id") : 0;
                return num1.compareTo(num2);
            });
        }
        mainInfo.put("situations", situations);
        mainInfo.put("applyName", formInfo.getString("APPLY_NAME"));
        mainInfo.put("applyCardNumber", formInfo.getString("APPLY_CARD_NUMBER"));
        mainInfo.put("applyCardType", formInfo.getString("APPLY_CARD_TYPE"));
        if(needSignItems.contains(itemCode)) {
            mainInfo.put("needSign", 1);
        } else {
            mainInfo.put("needSign", 0);
        }
        return mainInfo;
    }
}
