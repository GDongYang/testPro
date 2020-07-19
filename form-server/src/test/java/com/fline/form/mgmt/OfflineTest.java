package com.fline.form.mgmt;

import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

public class OfflineTest {

    public static void main(String[] args) throws Exception {
        OfflineTest test = new OfflineTest();
        test.getUrl();
    }

    public void getUrl() throws Exception {
        String username = "xxx";
        String password = "xxxxx";
        long timestamp = System.currentTimeMillis();

        Map<String, String> headers = new HashMap<>();
        headers.put("username", username);
        headers.put("timestamp", timestamp + "");

        Map<String, Object> params = new HashMap<>();
        params.put("itemInnerCode", "adc3c241-0ff2-4630-ac0e-61b5e7580fee");
        params.put("cerNo", "xxxxx");
        params.put("cerName", "xxxx");
        params.put("projectId", "12345678");

        Map<String, Object> signParams = new HashMap<>();
        signParams.putAll(params);
        signParams.putAll(headers);
        signParams.put("password", password);

        StringBuilder sb = new StringBuilder();
        signParams.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((entry) -> {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        });
        String signature = MD5Util.MD5(sb.toString());
        headers.put("signature", signature);
        HttpClientResult result = HttpClientUtil.doGet("http://10.49.7.236:30014/formCenter/rest/form/offline/formUrl", headers, params);
        System.out.println("result="+result.getContent());

    }
}
