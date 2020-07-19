package com.fline.form.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.constant.Constant;
import com.fline.form.mgmt.service.impl.FormInfoContext;
import com.fline.yztb.vo.FormPageVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FtlTestController {
    @RequestMapping("/view/{modelname}")
    public String hello(Map<String,Object> map,@PathVariable(value = "modelname") String modelname){
        String path ="E:\\workspace\\fline_work\\js\\a.txt";
        String formData = readFileContent(path);
        createFormPdf(map, formData);
        return "/app/"+modelname;
    }

    public  String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    public Map<String, Object> createFormPdf(Map<String, Object> dataMap, String formDataStr) {
        JSONObject formData = JSON.parseObject(formDataStr);
        JSONArray form1 = formData.getJSONArray("form1");
        JSONObject formInfo = FormInfoContext.get();
            for (int i = 0; i < form1.size(); i++) {
                JSONObject jsonObject = form1.getJSONObject(i);
                String name = jsonObject.getString("name");
                Object value = jsonObject.containsKey("value_cn") ? jsonObject.get("value_cn") : jsonObject.get("value");
                dataMap.put(name, value);
            }
            if(formData.size()>1){
                formData.remove("form1");
                int num =2;
                for(int i =0;i<formData.size();i++){
                    JSONArray form = formData.getJSONArray("form"+num);
                    if(form==null){
                        continue;
                    }
                    JSONObject jsonObject = form.getJSONObject(0);
                    String name = "form"+num;
                    JSONArray autotable = jsonObject.getJSONArray("autotable");

                    List<Map<String, Object>> dataList = new ArrayList<>();
                    for(int j=0;j<autotable.size();j++){
                        JSONArray jsonArray = autotable.getJSONArray(j);
                        Map<String, Object> autoMap = new HashMap<>();
                        for(int o=0;o<jsonArray.size();o++){
                            JSONObject autotableOBJ = jsonArray.getJSONObject(o);
                            String autoname = autotableOBJ.getString("name");
                            Object value =null;
                            if(autotableOBJ.containsKey("value_cn") && autotableOBJ.get("value_cn").toString().split(",").length>0){
                                value = autotableOBJ.get("value");
                            }else {
                                value = autotableOBJ.containsKey("value_cn") ? autotableOBJ.get("value_cn") : autotableOBJ.get("value");
                            }
                            autoMap.put(autoname,value);
                        }
                        dataList.add(autoMap);
                    }
                    dataMap.put(name, dataList);
                    num++;
                }
            }
        String jsonString= JSON.toJSONString(dataMap);
        return dataMap;
    }
}
