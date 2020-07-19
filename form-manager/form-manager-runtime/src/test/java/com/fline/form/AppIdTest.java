package com.fline.form;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppIdTest {

    private static String createUrl = "http://oc.zjzwfw.gov.cn/atgaffcore/distributeConf/create";
    private static String pageUrl = "http://oc.zjzwfw.gov.cn/atgaffcore/distributeConf/page";
    private static String appId = "2088000577";
    private static String sid = "a7b4c23d4f41c27939e10c1370972c94";
    private static String updateSql = "update appId set status = ? where matterCode = ?";

    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);
        headers.put("Cookie", "sid=" + sid);
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);

        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from appId ");
        for (Map<String, Object> map : list) {
            String matterCode = map.get("matterCode") + "";
            params.put("matterCode", matterCode);
            try {
                HttpClientResult httpClientResult = HttpClientUtil.doPost(createUrl, headers, params);
                String content = httpClientResult.getContent();
                System.out.println(content);
                JSONObject jsonObject = JSON.parseObject(content);
                if(jsonObject.getBooleanValue("success")) {
                    jdbcTemplate.update(updateSql, "true", matterCode);
                } else {
                    Map<String, Object> pageParams = new HashMap<>();
                    pageParams.put("current", 1);
                    pageParams.put("pageSize", 10);
                    pageParams.put("matterCode", matterCode);
                    HttpClientResult pageResult = HttpClientUtil.doGet(pageUrl, headers, pageParams);
                    String pageContent = pageResult.getContent();
                    JSONObject pageObj = JSON.parseObject(pageContent);
                    JSONObject object = pageObj.getJSONObject("data").getJSONArray("list").getJSONObject(0);
                    jdbcTemplate.update(updateSql, object.getString("appId"), matterCode);
                }
            } catch (Exception e) {
                jdbcTemplate.update(updateSql, e.getMessage(), matterCode);
            }
        }
    }

    private static JdbcTemplate getJdbcTemplate() {
        BasicDataSource basicDs = new BasicDataSource();
        basicDs.setDriverClassName("com.mysql.jdbc.Driver");
        basicDs.setUrl("jdbc:mysql://121.40.214.176/form_center_tz?useUnicode=true&characterEncoding=UTF8");
        basicDs.setUsername("fline");
        basicDs.setPassword("000000");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDs);
        return jdbcTemplate;
    }
}
