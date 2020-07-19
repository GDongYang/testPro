package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.Constant;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.TokenMgmtService;
import com.fline.form.mgmt.service.YcslMgmtService;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.vo.DepartmentVo;
import com.fline.form.vo.ycsl.*;
import com.fline.yztb.vo.ItemVo;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class YcslMgmtServiceImpl implements YcslMgmtService {

    private Logger logger = LoggerFactory.getLogger(YcslMgmtServiceImpl.class);

    @Value("${ycsl.belongSystem}")
    private String belongSystem;
    @Value("${ycsl.appkey}")
    private String appkey;
    @Value("${ycsl.appSecret}")
    private String appSecret;
    @Value("${ycsl.submitUrl}")
    private String submitUrl;
    @Value("${ycsl.tokenUrl}")
    private String tokenUrl;
    @Value("${file.serverUrl}")
    private String fileServerUrl;
    @Autowired
    private FdpClientMgmtService fdpClientMgmtService;
    @Autowired
    private TokenMgmtService tokenMgmtService;

    @Override
    public String sendProject(JSONObject formInfo, ItemVo item, DepartmentVo dept, String formInfoStr, String attrInfoStr, String postInfoStr) {
        String formBusiCode = formInfo.getString("FORM_BUSINESS_CODE");
        String applyName = formInfo.getString("APPLY_NAME");
        String applyCardType = formInfo.getString("APPLY_CARD_TYPE");
        String applyCardNumber = formInfo.getString("APPLY_CARD_NUMBER");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());

        //String applyUid = formInfo.getString("APPLICANT_UID");
        //String applyMobile = formInfo.getString("APPLY_MOBILE");
        String applyUid = "123";
        JSONObject formJsonObject = JSON.parseObject(formInfoStr);
        String applyMobile = formJsonObject.getString("applyMobile");

        //申报基本信息
        BaseInfo baseInfo = new BaseInfo();
        CallInfo callInfo = new CallInfo();
        callInfo.setCaller("台州市自助终端机");
        callInfo.setCalltime(time);
        baseInfo.setCallInfo(callInfo);

        //企业
        if("51".equals(applyCardType)) {
            baseInfo.setApplyType("1");
            baseInfo.setApplyCardtype("营业执照号");
            baseInfo.setLegalman(formInfo.getString("LEGAL_MAN"));
            baseInfo.setXkFrSfzh("");//法人证件号码
        }
        //个人
        else {
            baseInfo.setApplyType("0");
            baseInfo.setApplyCardtype("身份证");
        }
        baseInfo.setServicecode(item.getInnerCode());
        baseInfo.setServiceDeptid(dept.getCode());
        baseInfo.setServiceversion("1");//事项版本
        baseInfo.setServicename(item.getName());
        baseInfo.setProjectname(item.getName());
        baseInfo.setInfotype("即办件");
        baseInfo.setBusType("0");
        baseInfo.setApplyname(applyName);
        baseInfo.setApplyCardnumber(applyCardNumber);
        baseInfo.setContactman(applyName);
        baseInfo.setContactmanCardtype("身份证");
        baseInfo.setContactmanCardnumber(applyCardNumber);
        baseInfo.setTelphone(Detect.notEmpty(applyMobile) ? applyMobile : "13757187216");
        baseInfo.setDeptid(dept.getCode());
        baseInfo.setDeptname(dept.getName());
        baseInfo.setSsOrgcode(dept.getCode());//实施机构统一社会信用代码
        baseInfo.setReceiveUseid(applyUid);
        baseInfo.setReceiveName(applyName);
        baseInfo.setApplyfrom("1");//申报来源：1.pc端
        baseInfo.setApproveType("01");//审批类型:01.普通办件
        baseInfo.setApplyPropertiy("99");//项目性质:99.其他
        baseInfo.setReceivetime(time);
        baseInfo.setAreacode(dept.getAreaCode());
        baseInfo.setDatastate("1");
        baseInfo.setBelongsystem(belongSystem);
        baseInfo.setCreateTime(time);
        baseInfo.setSyncStatus("I");
        baseInfo.setDataversion("1");
        baseInfo.setIntype("2");//办件接入类型:2.申报机

        //申报材料信息
        AttrInfoList attrInfoList = new AttrInfoList();
        if (Detect.notEmpty(attrInfoStr)) {
            JSONArray attrArray = JSON.parseArray(attrInfoStr);
            for (int ii = 0; ii < attrArray.size(); ii++) {
                JSONObject attrObj = attrArray.getJSONObject(ii);
                if(attrObj.isEmpty()) {
                    continue;
                }
                String materialName = attrObj.getString("materialName");
                String materialCode = attrObj.getString("materialCode");
                String fileName = attrObj.getString("certName");
                String certFile = attrObj.getString("certFile");

                AttrInfo attrInfo = new AttrInfo();
                attrInfo.setUnid(UUID.randomUUID().toString().replace("-", ""));
                attrInfo.setAttrName(materialName);
                attrInfo.setAttrId(materialCode);
                attrInfo.setIsTake("1");
                attrInfo.setTakeTime(time);
                attrInfo.setBelongsystem(belongSystem);
                attrInfo.setCreate_time(time);
                attrInfo.setSync_status("I");
                attrInfo.setDataVersion("1");

                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(fileName);
                fileInfo.setFilePwd("123");
                String fileUrl = fileServerUrl + item.getInnerCode() + "/" + formBusiCode + "/" + fileName;
                fileInfo.setFileUrl(fileUrl);

                String fileKey = Constant.FDP_FILE_KEY + "/" + item.getInnerCode() + "/" + formBusiCode + "/" + fileName;
                byte[] bs = this.fdpClientMgmtService.readFile(fileKey);
                if(Detect.notEmpty(bs)){
                    attrInfo.setTakeType("电子证照库");
                } else if (Detect.notEmpty(certFile)) {
                    try {
                        fdpClientMgmtService.writeFile(fileKey, null, new BASE64Decoder().decodeBuffer(certFile));
                    } catch (IOException e) {
                        throw new RuntimeException("附件保存失败");
                    }
                    attrInfo.setTakeType("附件上传");
                } else {
                    continue;
                }
                attrInfo.setFiles(Arrays.asList(fileInfo));
                attrInfoList.add(attrInfo);
            }
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);

        Map<String, Object> params = new HashMap<>();
        params.put("requestKey", appkey);
        params.put("baseInfoXml", toXmlString(baseInfo));
        params.put("attrXml", toXmlString(attrInfoList));
        params.put("token", tokenMgmtService.getToken());
        logger.info("submit ycsl :" + JSON.toJSONString(params));
        try {
            HttpClientResult httpClientResult = HttpClientUtil.doPost(submitUrl, headers, params);
            String content = httpClientResult.getContent();
            logger.info("ycsl result:" + content);
            JSONObject jsonObject = JSON.parseObject(content);
            if(!"01".equals(jsonObject.getString("result"))) {
                throw new RuntimeException("提交一窗失败:" + jsonObject.getString("resultmsg"));
            }
            return jsonObject.getString("projid");
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            tokenMgmtService.decrReadCount();
        }
    }

    private String toXmlString(Object obj) {
        Format format = new Format("<?xml version=\"1.0\" encoding= \"UTF-8\" ?>");
        //持久化
        Persister persister = new Persister(format);
        try {
            OutputStream outputStream = new ByteArrayOutputStream();
            persister.write(obj, outputStream, "utf-8");
            String s = outputStream.toString();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("转换xml失败");
        }
    }

}
