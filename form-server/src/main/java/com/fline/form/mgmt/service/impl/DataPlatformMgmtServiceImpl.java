package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.util.JsonUtil;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DataPlatformMgmtService;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.FormPropertyVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.tests.props.PropShadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataPlatformMgmtServiceImpl implements DataPlatformMgmtService {

    private Log logger = LogFactory.getLog(DataPlatformMgmtServiceImpl.class);

    @Value("${aic.queryData.url}")
    private String queryDataUrl;
    @Value("${aic.queryData.tenantUserCode}")
    private String tenantUserCode;
    @Value("${aic.queryData.password}")
    private String password;
    @Autowired
    private DataCacheService dataCacheService;

    @Override
    public String sendRequest(String formCode, String interfaceType, Map<String, Object> requestParams) {
        FormPageVo formPage = dataCacheService.getFormPage(formCode);
        List<FormPropertyVo> properties = formPage.getProperties();
        if(!Detect.notEmpty(properties)) {
            throw new BaseException("未配置业务接口");
        }
        Optional<FormPropertyVo> propertyVoOptional = properties.stream().filter((property) -> interfaceType.equals(property.getName())).findAny();
        if(propertyVoOptional.isPresent()) {
            String interfaceId = propertyVoOptional.get().getValue();
            return sendRequest(interfaceId, requestParams);
        } else {
            throw new BaseException("未配置业务接口");
        }
    }

    @Override
    public String sendRequest(String interfaceId, Map<String, Object> requestParams) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("tenantUserCode", tenantUserCode);
            params.put("password", password);
            params.put("resourceCode", interfaceId);
            params.put("parameter", JsonUtil.marshal(requestParams));
            Map<String, String> headers = new HashMap<>();
            headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);
            logger.info("sendRequest params:" + params);
            HttpClientResult httpClientResult = HttpClientUtil.doPost(queryDataUrl, headers, params);
            String content = httpClientResult.getContent();
            logger.info("sendRequest result:" + content);
            JSONObject jsonObject = JSON.parseObject(content);
            if(jsonObject.getIntValue("code") == 600) {
                return jsonObject.getString("data");
            } else {
                throw new RuntimeException(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            throw new RuntimeException("请求失败：" + e.getMessage());
        }
    }
}
