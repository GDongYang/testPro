package com.fline.form.mgmt.service.impl;

import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.SecurityCodeMgmtService;
import com.fline.form.util.SecurityCode;
import com.fline.form.util.SecurityImage;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("securityCodeMgmtService")
public class SecurityCodeMgmtServiceImpl implements SecurityCodeMgmtService {

    private final int expireTime = 60 * 5;//5分钟过期

    @Resource
    private DataCacheService dataCacheService;

    @Override
    public String getFromCache(String uuid) {
        String securityCode = dataCacheService.getSecurityCode(uuid);
        dataCacheService.delSecurityCode(uuid);
        return securityCode;
    }

    @Override
    public Map<String, Object> generateCode(){
        // 如果开启Hard模式，可以不区分大小写
        String securityCode = SecurityCode.getSecurityCode(5, SecurityCode.SecurityCodeLevel.Hard, false).toLowerCase();
        // 获取默认难度和长度的验证码
        ByteArrayInputStream imageStream = SecurityImage.getImageAsInputStream(securityCode);

        String uuid = UUID.randomUUID().toString().replace("-","");
        dataCacheService.setSecurityCode(uuid, securityCode, expireTime);

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("imgBase64", new BASE64Encoder().encodeBuffer(IOUtils.toByteArray(imageStream)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("codeId", uuid);
        return result;
    }


}
