package com.fline.form.mgmt.service.impl;

import com.alibaba.gov.api.client.AtgBusClient;
import com.alibaba.gov.api.client.DefaultAtgBusClient;
import com.alibaba.gov.api.domain.AtgBusSecretKey;
import com.alibaba.gov.api.request.AtgBizComAlibabaGovEvaluationQueryPublishurlRequest;
import com.alibaba.gov.api.response.AtgBizComAlibabaGovEvaluationQueryPublishurlResponse;
import com.fline.form.mgmt.service.AtgBizMgmtService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.vo.AreaOrgVo;
import com.fline.form.vo.DepartmentVo;
import com.fline.yztb.vo.ItemVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtgBizMgmtServiceImpl implements AtgBizMgmtService {

    private Log logger = LogFactory.getLog(AtgBizMgmtServiceImpl.class);

    @Autowired
    private DataCacheService dataCacheService;

    @Value("${atg-bus-opt.appId}")
    private String appId;
    @Value("${atg-bus-opt.appKey}")
    private String appKey;
    @Value("${atg-bus-opt.appSecret}")
    private String appSecret;
    @Value("${atg-bus-opt.gateway-url}")
    private String gatewayUrl;

    private AtgBusClient atgBusClient;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void init() {
        //初始化网关地址、秘钥信息
        List<AtgBusSecretKey> secretKeys = new ArrayList<>();
        AtgBusSecretKey atgBusSecretKey = new AtgBusSecretKey(appKey, appSecret);
        secretKeys.add(atgBusSecretKey);
        //初始化客户端
        atgBusClient = new DefaultAtgBusClient(gatewayUrl, appId, secretKeys);
    }

    @Override
    public String evaluationUrl(String projectId, String itemCode, String certType, String cerNo, String cerName) {
        try {
            ItemVo item = dataCacheService.getItem(itemCode);
            DepartmentVo dept = dataCacheService.getDepartment(Long.parseLong(item.getDepartmentId()));
            AreaOrgVo areaOrg = dataCacheService.getAreaOrg(dept.getAreaCode());

            AtgBizComAlibabaGovEvaluationQueryPublishurlRequest request = new AtgBizComAlibabaGovEvaluationQueryPublishurlRequest();
            request.setAffairId(projectId);//办件统一赋码ID
            request.setAffairStatus(1L);//办件状态 1收件 2办结
            request.setAffairTime(LocalDateTime.now().format(dtf));//收件时间/办结时间
            request.setAffairType(2L);//办件类型  1即办件  2承诺件
            request.setAreaCode(dept.getAreaCode());
            request.setAreaName(areaOrg.getName());
            request.setChannel("APP_ONLINE");//评价渠道，默认为二维码，若是PC端评价需要传PC_ONLINE，手机端APP_ONLINE
            request.setDepartCode(item.getDepartmentCode());
            request.setDepartName(item.getDeptName());
            request.setDepartIdentityCode(item.getDepartmentCode());
            request.setIdentityType(certType);//证件类型编码
            request.setMatterId(itemCode);//事项编码
            request.setOperatorName(cerName);
            request.setUserIdentityNum(cerNo);//用户类型为个人时传用户身份证号，用户类型为法人时传社会统一信用码
            request.setUserType("31".equals(certType) ? "person" : "legal");//用户类型 person个人 legal法人

            AtgBizComAlibabaGovEvaluationQueryPublishurlResponse response = atgBusClient.execute(request);
            if(response.getSuccess()) {
                String data = response.getData();
                logger.info("获取评价页面成功：" + data);
                return data;
            } else {
                throw new RuntimeException(response.getErrorMsg());
            }
        } catch (Exception e) {
            logger.info("获取评价页面失败：" + e.getMessage());
        }
        return null;
    }
}
