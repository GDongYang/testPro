package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.TokenMgmtService;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenMgmtServiceImpl implements TokenMgmtService {

    private Logger logger = LoggerFactory.getLogger(TokenMgmtServiceImpl.class);

    @Value("${ycsl.appkey}")
    private String appkey;
    @Value("${ycsl.appSecret}")
    private String appSecret;
    @Value("${ycsl.tokenUrl}")
    private String tokenUrl;

    private final int expireTime = 20*60;//20分钟

    @Autowired
    private DataCacheService dataCacheService;

    @Override
    public String getToken() {
        //获取token
        String token = dataCacheService.getToken();
        //token缓存有效期50分钟，如果为空则是过期，需要重新获取
        if(!Detect.notEmpty(token)) {
            //获取锁
            String requestId = UUID.randomUUID().toString();
            lock(requestId);
            try {
                //再次从缓存获取token，是否其他线程已重新请求
                token = dataCacheService.getToken();
                if(!Detect.notEmpty(token)) {
                    //获取读锁，当没有使用token进行请求时才可以重新获取token
                    readLock();
                    //重新请求获取token
                    token = requestToken();
                    setToken(token);
                }
            } finally {
                //释放锁
                unlock(requestId);
            }
        }
        incrReadCount();//使用数量加1
        return token;
    }


    @Override
    public void decrReadCount() {
        dataCacheService.decr(KeyConstant.YZTB_TOKEN_READ_COUNT);
    }

    private void incrReadCount() {
        dataCacheService.incr(KeyConstant.YZTB_TOKEN_READ_COUNT);
    }

    private void lock(String requestId) {
        //最大阻塞时间90秒
        long endTime = System.currentTimeMillis() + 90000;
        while (!dataCacheService.setTokenLock(requestId)) {
            if(System.currentTimeMillis() > endTime) {
                throw new RuntimeException("获取Token锁超时");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void unlock(String requestId) {
        dataCacheService.delTokenLock(requestId);
    }

    private void readLock() {
        //最大阻塞时间40秒
        long endTime = System.currentTimeMillis() + 40000;
        while (dataCacheService.getTokenReadCount() > 0) {
            if(System.currentTimeMillis() > endTime) {
                dataCacheService.delTokenReadCount();
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setToken(String token) {
        dataCacheService.setToken(token, expireTime);
    }

    private String requestToken() {
        logger.info("ycsl getToken begin");
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);

        Map<String, Object> params = new HashMap<>();
        long requestTime = System.currentTimeMillis();
        params.put("requestKey", appkey);
        params.put("sign", MD5Util.MD5(appkey + appSecret + requestTime).toLowerCase());
        params.put("requestTime", requestTime);
        try {
            HttpClientResult httpClientResult = HttpClientUtil.doGet(tokenUrl, headers, params);
            String content = httpClientResult.getContent();
            logger.info("ycsl getToken result:" + content);
            JSONObject jsonObject = JSON.parseObject(content);
            if(!"00".equals(jsonObject.getString("result"))) {
                throw new RuntimeException(jsonObject.getString("resultmsg"));
            } else {
                return jsonObject.getString("resultmsg");
            }
        } catch (Exception e) {
            logger.error("ycsl getToken error:" + e.getMessage(), e);
            throw new RuntimeException("获取token失败");
        }
    }

}
