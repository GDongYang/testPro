package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.DataMgmtService;
import com.fline.form.mgmt.service.PostInfoMgmtService;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.vo.PersonInfo;
import com.fline.form.vo.PostInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostInfoMgmtServiceImpl implements PostInfoMgmtService {

    private Log logger = LogFactory.getLog(PostInfoMgmtServiceImpl.class);

    @Value("${zjzwfw.kdsd.url}")
    private String serverUrl;
    @Value("${zjzwfw.kdsd.appKey}")
    private String appKey;
    @Value("${zjzwfw.kdsd.appSecret}")
    private String appSecret;
    @Value("${zjzwfw.kdsd.version}")
    private String version;
    @Autowired
    private DataMgmtService dataMgmtService;

    @Override
    public JSONArray getPostInfo(String userId) {
        try {
            String url = "/api/address/user/" + userId + "/list";
            JSONObject jsonObject = sendRequest(url, null);
            if(jsonObject.getIntValue("state") == 200) {
                return jsonObject.getJSONArray("content");
            } else {
                throw new RuntimeException(jsonObject.getString("content"));
            }
        } catch (Exception e) {
            logger.error("获取邮寄地址失败：" + e.getMessage());
        }
        return null;
    }

    @Override
    public long addPostInfo(String formBusiCode, PostInfo postInfo) {
        try {
            PersonInfo personInfo = dataMgmtService.getPersonInfo(formBusiCode);
            String url = "/api/address/user/" + personInfo.getUserId() + "/create";
            Map<String, Object> params = new HashMap<>(2);
            params.put("userType", 1);//1-个人用户 2-法人用户
            params.put("addressInfo", JSON.toJSONString(postInfo));
            JSONObject jsonObject = sendRequest(url, params);
            if(jsonObject.getIntValue("state") == 200) {
                return jsonObject.getJSONObject("content").getLong("id");
            } else {
                throw new RuntimeException(jsonObject.getString("content"));
            }
        } catch (Exception e) {
            logger.error("新增邮寄地址失败：" + e.getMessage());
            throw new RuntimeException("新增邮寄地址失败：" + e.getMessage());
        }
    }

    @Override
    public long updatePostInfo(String formBusiCode, PostInfo postInfo) {
        try {
            PersonInfo personInfo = dataMgmtService.getPersonInfo(formBusiCode);
            String url = "/api/address/user/" + personInfo.getUserId() + "/update/" + postInfo.getId();
            Map<String, Object> params = new HashMap<>(1);
            params.put("addressInfo", JSON.toJSONString(postInfo));
            JSONObject jsonObject = sendRequest(url, params);
            if(jsonObject.getIntValue("state") == 200) {
                return jsonObject.getJSONObject("content").getLong("id");
            } else {
                throw new RuntimeException(jsonObject.getString("content"));
            }
        } catch (Exception e) {
            logger.error("修改邮寄地址失败：" + e.getMessage());
            throw new RuntimeException("修改邮寄地址失败：" + e.getMessage());
        }
    }

    @Override
    public long deletePostInfo(String formBusiCode, String id) {
        try {
            PersonInfo personInfo = dataMgmtService.getPersonInfo(formBusiCode);
            String url = "/api/address/user/" + personInfo.getUserId() + "/delete/" + id;
            JSONObject jsonObject = sendRequest(url, null);
            if(jsonObject.getIntValue("state") == 200) {
                return jsonObject.getJSONObject("content").getLong("id");
            } else {
                throw new RuntimeException(jsonObject.getString("content"));
            }
        } catch (Exception e) {
            logger.error("删除邮寄地址失败：" + e.getMessage());
            throw new RuntimeException("删除邮寄地址失败：" + e.getMessage());
        }
    }

    private JSONObject sendRequest(String url, Map<String, Object> apiParams) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> params = new HashMap<>(4);
        //系统参数
        params.put("appKey", appKey);
        params.put("version", version);
        params.put("timestamp", sdf.format(new Date()));
        //API参数
        if(Detect.notEmpty(apiParams)) {
            params.putAll(apiParams);
        }
        String stringToSign = sortParams(params) + appSecret;
        String sign = generate(stringToSign, null);
        params.put("sign", sign);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);
        HttpClientResult httpClientResult = HttpClientUtil.doPost(serverUrl + url, headers, params);
        String content = httpClientResult.getContent();
        return JSON.parseObject(content);
    }

    private String sortParams(Map<String, Object> params) {
        // 删掉sign参数
        params.remove("sign");
        StringBuilder stringToSign = new StringBuilder();
        List<String> keyList = new ArrayList<String>();
        for (String key : params.keySet()) {
            String value = params.get(key) + "";
            // 将值为空的参数排除
            if (value != null && !"".equals(value)) {
                keyList.add(key);
            }
        }
        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        return r;// char值小的排前边
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        //将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            stringToSign.append(key);
            stringToSign.append(params.get(key));
        }
        return stringToSign.toString();
    }

    //对“生成签名的字符串”进行MD5，并进行BASE64操作，根据请求编码得到签名
    private String generate(String stringToSign, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "utf-8";
        }
        String sign = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            sign = base64Encoder.encode(md5.digest(stringToSign.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }
}
