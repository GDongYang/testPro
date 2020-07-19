package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.atgbusmng.api.domain.StuffInfoVO;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Entry;
import com.fline.form.constant.Constant;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.*;
import com.fline.form.type.ApplySourceType;
import com.fline.form.util.*;
import com.fline.form.vo.*;
import com.fline.form.vo.xml.*;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;
import com.itextpdf.io.codec.Base64;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import com.fline.form.util.HttpClientUtil;

@Service
public class FormMgmtServiceImpl implements FormMgmtService {

    private Logger logger = LoggerFactory.getLogger(FormMgmtServiceImpl.class);

    private static final Integer ERROR_CODE = 1;
    private static final Integer SUCCESS_CODE = 0;

    //事项库调用地址
    private static final String url="http://172.31.84.143:31001/item/api/info/getItemInfo/";


    @Value("${form.rootUrl}")
    private String formRootUrl;
    @Value("${aic.dataService.url}")
    private String dataServiceUrl;
    @Value("${ga.serverUrl}")
    private String gaServerUrl;

    @Value("${yinchuan.serverUrl}")
    private String ycServerUrl;
    @Value("${yinchuan.apId}")
    private String apId;
    @Value("${yinchuan.secretKey}")
    private String secretKey;

    @Autowired
    private DataCacheService dataCacheService;
    @Autowired
    private FdpClientMgmtService fdpClientMgmtService;
    @Autowired
    private PostInfoMgmtService postInfoMgmtService;
    @Autowired
    private AliProjectMgmtService aliProjectMgmtService;
    @Autowired
    private DataCollectionMgmtService dataCollectionMgmtService;
    @Autowired
    private YcslMgmtService ycslMgmtService;
    @Autowired
    private CertificateFileMgmtService certificateFileMgmtService;


    @Override
    public Map<String, Object> createFormByIdNum(String serviceCode, String bType, String idNum) {
        //发送请求获取用户信息
        String planEncryption = idNum + bType + apId + secretKey;
        //md5 32位加密获取参数mac
        String mac = MD5Util.encryption(planEncryption);
        Map<String, String> conentType = new HashMap<>();
        conentType.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_JSON);

        Map<String, Object> params = new HashMap<>();
        params.put("bcardNO", idNum);
        params.put("bType", bType);
        params.put("apId", apId);
        params.put("mac", mac);
        //发送请求获取用户信息
        HttpClientResult httpClientResult = null;
        try {
            httpClientResult = HttpClientUtil.doPost(ycServerUrl, conentType, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("获取到用户信息：" + httpClientResult.getContent());
        JSONObject jsonObject = JSON.parseObject(httpClientResult.getContent());
        Integer code = (Integer) jsonObject.get("code");
        System.out.println("code:" + code);
        if (code.equals(ERROR_CODE)) {
            System.out.println("进来抛出异常了");
            throw new BaseException("校验用户信息失败");
        }
        String contentBase64 = (String) jsonObject.get("data");
        try {
            String content = new String(new BASE64Decoder().decodeBuffer(contentBase64));
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<String, Object>();
    }

    @Override
    public String tempSaveFromData(String formBusiCode, String projId, String formInfo, String attrInfo) {
        JSONObject fi = fdpClientMgmtService.loadFormInfo(formBusiCode);
        String newDataBusiCode = UUID.randomUUID().toString().replaceAll("-", "");

        Map<String, Object> formData = new HashMap<String, Object>();
        String itemCode = fi.getString("ITEM_CODE");
        String areaCode = fi.getString("AREA_CODE");
        String sfzh = fi.getString("APPLY_CARD_NUMBER");
        if (!Detect.notEmpty(projId)) {
            projId = itemCode + ";" + areaCode + ";" + sfzh;
        }
        //附件格式处理
        if (Detect.notEmpty(attrInfo)) {
            JSONArray attrArray = JSON.parseArray(attrInfo);
            List<StuffInfoVO> suffInfoList = new ArrayList<StuffInfoVO>();
            for (int ii = 0; ii < attrArray.size(); ii++) {
                JSONObject attrObj = attrArray.getJSONObject(ii);
                String materialName = attrObj.getString("materialName");
                String materialCode = attrObj.getString("materialCode");
                String materialSignature = attrObj.getString("signature");
                String fileName = attrObj.getString("certName");
                String certFile = attrObj.getString("certFile");
                if (attrObj.containsKey("certUrl")) {
                    certFile = attrObj.getString("certUrl");
                }

                StuffInfoVO stuffInfoVO = new StuffInfoVO();
                stuffInfoVO.setStuffName(materialName);
                stuffInfoVO.setStuffType("2");//材料类型 （2电子
                stuffInfoVO.setStuffUniId(materialCode);
                stuffInfoVO.setFetchMode("02");//收取类型
                stuffInfoVO.setStuffNum(1L);//材料数量
                stuffInfoVO.setAttachName(fileName);
                stuffInfoVO.setMemo(materialSignature);//证照签名信息
                stuffInfoVO.setAttachPath(certFile);
                //TODO 文件存入oss
                suffInfoList.add(stuffInfoVO);
            }
            if (suffInfoList.size() > 0) {
                formData.put("FILE_CONTENT", JSONArray.toJSONString(suffInfoList));
            }
        }
        formData.put("FORM_BUSINESS_CODE", projId);
        formData.put("ITEM_CODE", itemCode);
        formData.put("FORM_CODE", fi.getString("FORM_CODE"));
        formData.put("DATA_BUSINESS_CODE", newDataBusiCode);
        formData.put("SFZHM", sfzh);
        formData.put("FORM_CONTNENT", formInfo);
        formData.put("DATA_CREATED", new Date());
        boolean res = fdpClientMgmtService.tempSaveFormData(formData);
        if (!res) {
            logger.error("创建表单失败：保存数据失败！");
            throw new BaseException("未获取到暂存数据");
        } else {
            logger.info("保存表单数据成功：" + formBusiCode);
            return "保存数据成功";
        }
    }

    @Override
    public Map<String, Object> createFormByItem(String serviceCode, String formReq) {
        logger.info("【createFormByItem begin】serviceCode:{},formReq:{}", serviceCode, formReq);
        String[] codes = serviceCode.split(":");
        String applySource = codes[0];
        String itemCode = codes[1];
        JSONObject jsonObj = JSON.parseObject(formReq);
        String certType = jsonObj.getString("applyer_cert_type");
        // 1. 获取事项信息
        ItemVo item = dataCacheService.getItem(itemCode);
        if (item == null) {
            throw new BaseException("创建表单失败:事项编码错误");
        }
        // 2. 获取表单信息
        String formCode;
        if (Constant.COMMON_FORM_CODE.equals(item.getFormCode())) {
            formCode = Constant.PERSON_TYPE.equals(certType) ? Constant.COMMON_PERSON_FORM_CODE : Constant.COMMON_COMPANY_FORM_CODE;
        } else {
            formCode = item.getFormCode();
        }
        FormPageVo form = dataCacheService.getFormPage(formCode);
        if (form == null) {
            throw new BaseException("创建表单失败:无法获取表单信息");
        }
        // 3.生成表单流水号
        String formBusiCode = UUID.randomUUID().toString().replaceAll("-", "");
        // 4. 保存业务信息（保存原始数据）

		String formUrl = formRootUrl + applySource + "/" + formCode + "_"
				+ form.getVersion() + ".html";
        Map<String, Object> formInfo = new HashMap<>();
        formInfo.put("FORM_BUSINESS_CODE", formBusiCode);
        formInfo.put("FORM_CODE", formCode);
        formInfo.put("ITEM_CODE", item.getInnerCode());
        formInfo.put("SERVICE_ID", item.getServiceId());
        formInfo.put("APPLY_SCENA", item.getInnerCode());
        formInfo.put("FORM_URL", formUrl);
        formInfo.put("APPLY_SOURCE", applySource);//应用来源
        formInfo.put("FORM_CREATOR", jsonObj.getString("applyer_name"));
        formInfo.put("APPLY_NAME", jsonObj.getString("applyer_name"));
        formInfo.put("APPLY_CARD_NUMBER", jsonObj.getString("applyer_cert_no"));
        formInfo.put("APPLICANT_UID", jsonObj.getString("applicant_uid"));
        formInfo.put("APPLY_CARD_TYPE", certType);
        formInfo.put("APPLY_MOBILE", jsonObj.getString("applyer_mobile"));
        formInfo.put("LEGAL_MAN", jsonObj.getString("legalMan"));
        formInfo.put("COMPANY_TYPE", jsonObj.getString("companyType"));
        formInfo.put("COMPANY_ADDRESS", jsonObj.getString("companyAddress"));
        formInfo.put("ATTN_NAME", jsonObj.getString("attnName"));
        formInfo.put("ATTN_ID_NO", jsonObj.getString("attnIDNo"));
        formInfo.put("COMPANY_REG_NUMBER", jsonObj.getString("companyRegNumber"));
        formInfo.put("FORM_CREATED", new Date());
        formInfo.put("FORM_VERSION", form.getVersion());
        formInfo.put("FORM_DIGEST", formReq);
        boolean result = fdpClientMgmtService.createFormInfo(formInfo);
        // 5.返回表单地址 + 流水号
        if (!result) {
            throw new BaseException("创建表单失败：保存数据失败！");
        } else {
            logger.info("创建表单成功：" + formBusiCode + ", url：" + formUrl);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("formBusiCode", formBusiCode);
//			formUrl = "http://218.95.136.135:50000/form-yinchuan/yc/app/indexYc.html";
            resultMap.put("formUrl", formUrl + "?formBusiCode=" + formBusiCode);
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> loadFormData(String formBusiCode) {
        logger.info("loadFormData :" + formBusiCode);
        // 获取表单信息
        JSONObject formInfo = FormInfoContext.get();
        // 获取表单数据
        Map<String, Object> result = new HashMap<>();
        String applyCardNumber = formInfo.getString("APPLY_CARD_NUMBER");
        String applyName = formInfo.getString("APPLY_NAME");
        String itemCode = formInfo.getString("ITEM_CODE");
        String formCode = formInfo.getString("FORM_CODE");
        String applyCardType = formInfo.getString("APPLY_CARD_TYPE");
        List<Map<String, Object>> formData = collectData(formCode, applyCardNumber, applyName, itemCode);
        result.put("formData", formData);
        logger.info("开始加载表单键值对数据---------------------" + formData.size());
        for (int i = 0; i < formData.size(); i++) {
            Map<String, Object> stringObjectMap = formData.get(i);
            Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
            for (Map.Entry e : entries) {
                logger.info("表单键值对" + (String) e.getKey() + ":");
            }
        }

        // 用户基本信息
        Map<String, Object> personInfo = new HashMap<>();
        personInfo.put("APPLY_NAME", applyName);
        personInfo.put("APPLY_CARD_NUMBER", applyCardNumber);
        personInfo.put("APPLY_MOBILE", formInfo.getString("APPLY_MOBILE"));
        personInfo.put("ITEM_CODE", itemCode);
        //获取用户类型 1 为个人 2为企业
        String form_digest = formInfo.getString("FORM_DIGEST");
        JSONObject obj= JSON.parseObject(form_digest);
        personInfo.put("BUS_TYPE", obj.get("busType"));
        //统一信用代码
        personInfo.put("UNISCID", obj.get("uniscid"));
        ItemVo item = dataCacheService.getItem(itemCode);
        personInfo.put("ITEM_NAME", item.getName());
        if (Constant.COMPANY_TYPE.equals(applyCardType)) {
            personInfo.put("LEGAL_MAN", formInfo.getString("LEGAL_MAN"));
            personInfo.put("ATTN_NAME", formInfo.getString("ATTN_NAME"));
            personInfo.put("ATTN_ID_NO", formInfo.getString("ATTN_ID_NO"));
            personInfo.put("COMPANY_ADDRESS", formInfo.getString("COMPANY_ADDRESS"));
        }
        result.put("personInfo", personInfo);
        // 获取邮寄地址
//        String userId = formInfo.getString("APPLICANT_UID");
//        JSONArray postInfo = postInfoMgmtService.getPostInfo(userId);
//        result.put("postInfo", postInfo);
        // 前端校验函数
        FormPageVo formPage = dataCacheService.getFormPage(item.getFormCode());
        String checkFunction = formPage.getCheckFunction();
        result.put("checkFunction", Detect.notEmpty(checkFunction) ? checkFunction.split(",") : Collections.emptyList());
        return result;
    }

    @Override
    public String saveFormData(String formBusiCode, String formInfo, String postInfo, String attrInfo) {
        // 1. 获取表单信息
        JSONObject formInfoJson = FormInfoContext.get();
        Map<String, Object> formData = new HashMap<>();
        formData.put("itemCode", formInfoJson.getString("ITEM_CODE"));
        formData.put("formCode", formInfoJson.getString("FORM_CODE"));
        formData.put("formBusiCode", formBusiCode);
        formData.put("applyerCardNumber", formInfoJson.getString("APPLY_CARD_NUMBER"));
        formData.put("applyerName", formInfoJson.getString("APPLY_NAME"));
        Map<String, Object> formDataMap = new HashMap<>();
        formDataMap.put("formInfo", formInfo);
        formDataMap.put("attrInfo", attrInfo);
        formDataMap.put("postInfo", postInfo);
        formData.put("formContent", JSON.toJSONString(formDataMap));
        formData.put("dataCreated", new Date());
        boolean result = fdpClientMgmtService.saveFormData(formData);
        if (!result) {
            throw new BaseException("保存表单数据失败");
        } else {
            logger.info("保存表单数据成功：" + formBusiCode);
            return formBusiCode;
        }
    }

    @Override
    public String submitFormData(String formBusiCode, String formInfoStr, String attrInfo, String postInfo) {
        logger.info("submitFormData begin:" + formBusiCode);
        //保存表单信息
        saveFormData(formBusiCode, formInfoStr, postInfo, attrInfo);
        // 1. send project data
        JSONObject formInfo = FormInfoContext.get();
        String itemCode = formInfo.getString("ITEM_CODE");
        ItemVo item = dataCacheService.getItem(itemCode);
        DepartmentVo dept = dataCacheService.getDepartment(Long.parseLong(item.getDepartmentId()));

        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(attrInfo);
            if(Detect.notEmpty(list)){
                String postfix =".jpg";
                for (Map<String, Object> map : list) {
                    if (map.get("certFile") != null) {
                        String base64 = map.get("certFile").toString();
                        base64 = base64.replace(" ","+");
                        OSSClientUtil OSSClientUtil = new OSSClientUtil();
                        String ossKey = OSSClientUtil.uploadImg2OssBase64(base64, postfix, OSSClientUtil.ROOT + OSSClientUtil.APP);
                        String url = OSSClientUtil.getUrl(OSSClientUtil.ROOT + OSSClientUtil.APP + ossKey);
                       /* byte[] bytes = Base64.decode(base64);
                        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                        BufferedImage bi = ImageIO.read(bais);
                        String uuid = UUID.randomUUID().toString();
                        String filePath = System.getProperty("user.dir") + File.separator
                                + "download" + File.separator + uuid + ".jpg";
                        File file = new File(filePath);
                        if (!file.getParentFile().exists()) {
                            file.mkdirs();
                        }
                        ImageIO.write(bi, "jpg", file);
                        map.put("url", "http://172.31.84.188:18087/form-yinchuan/rest/file/pic?busiCode=" + uuid);*/
                        map.put("url", url);
                    }else{
                        StringBuilder sb = new StringBuilder();
                        sb.append(Constant.FDP_FILE_KEY)
                               // .append("/").append(map.get("materialCode").toString())
                                .append("/").append(formBusiCode)
                                .append("/").append(map.get("certName").toString());
                        byte[] bytes = fdpClientMgmtService.readFile(sb.toString());
                        String url ="";
                        if(bytes.length!=0){
                            byte[] bytesOSS = PDFbox.pdf2Jpg(bytes);
                            String encode = new BASE64Encoder().encode(bytesOSS);
                            OSSClientUtil OSSClientUtil = new OSSClientUtil();
                            //String postfix =map.get("certName").toString().substring(map.get("certName").toString().lastIndexOf(".",map.get("certName").toString().length()));
                            String ossKey = OSSClientUtil.uploadImg2OssBase64(encode,postfix,OSSClientUtil.ROOT + OSSClientUtil.APP);
                            url = OSSClientUtil.getUrl(OSSClientUtil.ROOT + OSSClientUtil.APP + ossKey);
                        }
                        map.put("url", url);
                    }
                }

                attrInfo = JSON.toJSONString(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        String busType = "0";
        String applyName = formInfo.getString("APPLY_NAME");
        String applicantUid = formInfo.getString("APPLICANT_UID");
        String applyCardType = formInfo.getString("APPLY_CARD_TYPE");
        String applyMobile = formInfo.getString("APPLY_MOBILE");
        String applyCardNumber = formInfo.getString("APPLY_CARD_NUMBER");
        String relBusId = null;
        String deptId = item.getDepartmentId();
        String areaCode = dept.getAreaCode();
        String serviceCodeId = Detect.notEmpty(item.getParent()) ? item.getParent() : itemCode;
        String applyForm = "2";


        //提交的信息转换格式
//        String formStr = "[" +  formInfoStr + "]";
//        if(formStr.startsWith("\ufeff")){
//            formStr = formStr.substring(1);
//        }
//        formStr = StringEscapeUtils.unescapeJava(formStr);

        //事项库基本信息转化xml，baseInfoXml
        BaseInfoXmlNodeVo baseInfoXml = new BaseInfoXmlNodeVo();
        String sxmc = "";
        Map<String, Object> dataParam = new HashMap<>();
        HttpClientResult result1 = null;
        try {
            result1 = HttpClientUtil.doGet(url + itemCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = JSON.parseObject(result1.getContent());
        JSONArray data=null;
        data = JSON.parseArray(jsonObject1.get("data").toString());
        JSONArray info = JSON.parseObject(data.get(0).toString()).getJSONArray("info");

        for(int i = 0;i<info.size();i++){
            Map<String,String> infoMap = (Map<String, String>) info.get(i);
            Iterator<String> iter = infoMap.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                String value = infoMap.get(key).toString();
                System.out.println(key+" "+value);
                baseInfoXml.setSERVICEVERSION("1");
                baseInfoXml.setDATAVERSION("1");
                baseInfoXml.setDEPTNAME("市场服务二室");
                baseInfoXml.setDEPTID("58f3588cf9ae4a8cbfe56bac4803b4d8");
                baseInfoXml.setSERVICECODE_ID("0f861034eddc446db50d39a19033b413");
                baseInfoXml.setBUS_TYPE("0");
                baseInfoXml.setAPPLYNAME(applyName);
                baseInfoXml.setAPPLY_CARDTYPE("身份证");
                baseInfoXml.setPROJID(RandomsUtil.getProjid());
                baseInfoXml.setAPPLY_CARDNUMBER(applyCardNumber);
                baseInfoXml.setSERVICENAME(item.getName());
                baseInfoXml.setSERVICECODE(itemCode);
                baseInfoXml.setCONTACTMAN_CARDTYPE("身份证");
                baseInfoXml.setCONTACTMAN_CARDNUMBER(applyCardNumber);
                baseInfoXml.setTELPHONE(applyMobile);
                baseInfoXml.setCONTACTMAN(applyName);
                if ("办件类型".equals(key)) {
                    baseInfoXml.setINFOTYPE(value);
                }

                /*if ("fddbr".equals(mapList.get("name"))) {
                    baseInfoXml.setLEGALMAN(mapList.get("value"));
                }
                if ("yb".equals(mapList.get("name"))) {
                    baseInfoXml.setPOSTCODE(mapList.get("value"));
                }
                if ("xmbh".equals(mapList.get("name"))) {
                    baseInfoXml.setBELONGTO(mapList.get("value"));
                }
                if ("xxdz".equals(mapList.get("name"))) {
                    baseInfoXml.setADDRESS(mapList.get("value"));
                }*/

            }
        }


        /*JSONObject jsonForm = JSON.parseObject(formInfoStr);
        String value = jsonForm.getString("form1");
        Map<String, Object> dataParam = new HashMap<>();
        List<Map<String, String>> listObjectSec = JSONArray.parseObject(value, List.class);
        BaseInfoXmlNodeVo baseInfoXml = new BaseInfoXmlNodeVo();
//        BaseInfoXmlVo baseInfoXml1 = new BaseInfoXmlVo();
        String xm = "";
        String sxmc = "";
        String sfzh ="";
        for (Map<String, String> mapList : listObjectSec) {
//            for (Map.Entry entry : mapList.entrySet()) {
//                System.out.println( entry.getKey()  + " = " +entry.getValue() + ",");
                System.out.println(mapList.get("name"));
                System.out.println(mapList.get("value"));
                baseInfoXml.setSERVICEVERSION("1");
                baseInfoXml.setDATAVERSION("1");
                baseInfoXml.setDEPTNAME("市场服务二室");
                baseInfoXml.setDEPTID("58f3588cf9ae4a8cbfe56bac4803b4d8");
                baseInfoXml.setSERVICECODE_ID("0f861034eddc446db50d39a19033b413");
                baseInfoXml.setBUS_TYPE("0");
                baseInfoXml.setAPPLY_CARDTYPE("身份证");
                if ("bjbh".equals(mapList.get("name"))) {
                    baseInfoXml.setPROJID(mapList.get("value"));
                }
                if ("sxmc".equals(mapList.get("name"))) {
                    baseInfoXml.setSERVICENAME(mapList.get("value"));
                    sxmc=mapList.get("value");
                }
                if ("projid".equals(mapList.get("name"))) {
                    baseInfoXml.setPROJID(RandomsUtil.getProjid());
                }
                if ("sxid".equals(mapList.get("name"))) {
                    baseInfoXml.setSERVICECODE(mapList.get("value"));
                }
                if ("bjlx".equals(mapList.get("name"))) {
                    baseInfoXml.setINFOTYPE(mapList.get("value"));
                }
                if ("xm".equals(mapList.get("name"))) {
                baseInfoXml.setAPPLYNAME(mapList.get("value"));
                xm = mapList.get("value");
                }
                if ("sfzh".equals(mapList.get("name"))) {
                baseInfoXml.setAPPLY_CARDNUMBER(mapList.get("value"));
                sfzh = mapList.get("value");
                }
                if ("sjh".equals(mapList.get("name"))) {
                baseInfoXml.setTELPHONE(mapList.get("value"));
                }
                if ("jtsxmc".equals(mapList.get("name"))) {
                    baseInfoXml.setSERVICENAME(mapList.get("value"));
                }
                if ("sjrzjlx".equals(mapList.get("name"))) {
                    baseInfoXml.setAPPLY_CARDTYPE(mapList.get("value"));
                }
                if ("dlrzjlx".equals(mapList.get("name"))) {

                    baseInfoXml.setCONTACTMAN_CARDTYPE(mapList.get("value"));
                }
//                System.out.println(mapList.get("value"));
                if ("dlrzjlx".equals(mapList.get("name"))) {
                    baseInfoXml.setCONTACTMAN_CARDTYPE(mapList.get("value"));
                }
                if ("dlrzjhm".equals(mapList.get("name"))) {
                    baseInfoXml.setCONTACTMAN_CARDNUMBER(mapList.get("value"));
                }
                if ("sqrmc".equals(mapList.get("name"))) {
                    baseInfoXml.setAPPLYNAME(mapList.get("value"));
                }
                if ("dlrxm".equals(mapList.get("name"))) {
                    baseInfoXml.setCONTACTMAN(mapList.get("value"));
                }
                if ("sqrlxdh".equals(mapList.get("name"))) {
                    baseInfoXml.setTELPHONE(mapList.get("value"));
                }
                if ("fddbr".equals(mapList.get("name"))) {
                    baseInfoXml.setLEGALMAN(mapList.get("value"));
                }
                if ("yb".equals(mapList.get("name"))) {
                    baseInfoXml.setPOSTCODE(mapList.get("value"));
                }
                if ("xmbh".equals(mapList.get("name"))) {
                    baseInfoXml.setBELONGTO(mapList.get("value"));
                }
                if ("xxdz".equals(mapList.get("name"))) {
                    baseInfoXml.setADDRESS(mapList.get("value"));
                }

//            }
        }*/
        sxmc=item.getName();
        String projectName = "关于" + applyName + "的" + sxmc;
        baseInfoXml.setPROJECTNAME(projectName);
        baseInfoXml.setCONTACTMAN(applyName);
        baseInfoXml.setCONTACTMAN_CARDTYPE("身份证");
        baseInfoXml.setCONTACTMAN_CARDNUMBER(applyCardNumber);
//        baseInfoXml1.setRECORD(baseInfoXml);
        String toXml = JaxbUtil.convertToXml(baseInfoXml);
        System.out.println(toXml);//formInfo 转化为xml

       if(Detect.notEmpty(attrInfo)){
           List<Map<String, String>> listAttrInfo = JSONArray.parseObject(attrInfo, List.class);
           //创建根
           AttrXmlVO attrXmlVO = new AttrXmlVO();
           List<AttrXmlNodeVo> a_node_list = new ArrayList<>();
           for (Map<String, String> mapList : listAttrInfo) {
               AttrXmlNodeVo a_node = new AttrXmlNodeVo();
               a_node.setDATAVERSION("1");
               a_node.setATTRNAME(mapList.get("materialName"));
               a_node.setUNID(UUID.randomUUID().toString());
               a_node.setATTRID(mapList.get("materialCode"));
               List<FileInfoXml> file_info_list = new ArrayList<>();
               FileInfoXml FileInfoXml = new FileInfoXml();
               FileInfoXml.setFILENAME(mapList.get("certName"));
               FileInfoXml.setFILEURL(mapList.get("url"));
               file_info_list.add(FileInfoXml);
               a_node.setFileInfoXmls(file_info_list);
               a_node_list.add(a_node);
           }
           attrXmlVO.setRECORD(a_node_list);
           String toXml1 = JaxbUtil.convertToXml(attrXmlVO);
           toXml1 = toXml1.replace("areacode","Areacode");
           System.out.println(toXml1);//attrInfo 转化为xml
           dataParam.put("attrXml", toXml1);
       }else{
           dataParam.put("attrXml", "");
        }


//        String url = "http://172.25.1.23:8030/xzsp-interface/a/rpc/HttpPostXml/httpPostXml";
          String url = "http://172.31.129.237:8030/xzsp-interface/a/rpc/HttpPostXml/httpPostXml";
//        String url = "http://smdt.yinchuan.gov.cn/xzsp-interface/a/rpc/HttpPostXml/httpPostXml";

        dataParam.put("baseInfoXml", toXml);

        dataParam.put("formXml", "");
        dataParam.put("apasPostXml", "");
        String json = JSON.toJSONString(dataParam);
        JSONObject jsonObject = JSON.parseObject(json);

        String result = HttpClientUtil.sendPost(url, jsonObject);

//        String projectId = null;
//        for (int i = 0; i < 3; i++) {
//            projectId = aliProjectMgmtService.generateUnicode(applyName, applicantUid, applyCardNumber, applyCardType,
//                    applyForm, areaCode, busTypeId, relBusId, serviceCodeId);
//            if(projectId != null) {
//                break;
//            }
//        }
//        if(projectId == null) {
//            throw new BaseException("获取统一赋码失败");
//        }
//
//        Map<String, Object> result = aliProjectMgmtService.sendProject(projectId, formInfo, item, dept, formInfoStr, attrInfo, postInfo);

        return result;
    }

    @Override
    public Map<String, Object> loadTempFrom(String projId) {
        JSONObject result = fdpClientMgmtService.loadTempFormData(projId);
        if (result != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("formInfo", result.getJSONArray("FORM").get(0));
            String fjStr = null;
            if (result.containsKey("FILE")) {
                fjStr = result.getJSONArray("FILE").getString(0);
            } else {
                fjStr = result.getString("FILE_CONTENT");
            }
            if (Detect.notEmpty(fjStr)) {
                JSONArray ja = JSONObject.parseArray(fjStr);
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject fj = ja.getJSONObject(i);
                    if (fj.get("attachPath") != null) {
                        String base64 = "";
                        String[] urls = fj.get("attachPath").toString().split(",");
                        for (int j = 0; j < urls.length; j++) {
                            String type = urls[j].substring(urls[j].lastIndexOf(".") + 1).toLowerCase();
                            if (type.equals("jpg") || type.equals("png") || type.equals("pdf")) {
                                try {
                                    base64 += FileUtil.urlToBase64(urls[j]) + ",";
                                } catch (Exception e) {
                                    throw new BaseException("材料转码失败");
                                }
                                base64 += null;
                            } else {
                                base64 += " ,";
                            }
                        }
                        ja.getJSONObject(i).put("base64", base64);
                    }
                }
                map.put("fileInfo", ja);
            } else {
                map.put("fileInfo", null);
            }
            return map;
        } else {
            throw new BaseException("未获取到暂存数据");
        }
    }


    @Override
    public Map<String, Object> submitYcsl(String formBusiCode, String formInfoStr, String attrInfo, String postInfo) {
        logger.info("submitYcsl begin:" + formBusiCode);
        //保存表单信息
        saveFormData(formBusiCode, formInfoStr, postInfo, attrInfo);

        JSONObject formInfo = FormInfoContext.get();
        String itemCode = formInfo.getString("ITEM_CODE");
        ItemVo item = dataCacheService.getItem(itemCode);
        DepartmentVo dept = dataCacheService.getDepartment(Long.parseLong(item.getDepartmentId()));

        String projectId = ycslMgmtService.sendProject(formInfo, item, dept, formInfoStr, attrInfo, postInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("projectId", projectId);
        return result;
    }

    @Override
    public String getTerminalFormUrl(String itemCode, String personInfo, String areaCode) {
        logger.info("【getTerminalFormUrl begin】itemCode:{}, personInfo:{}", itemCode, personInfo);
        //判断事项是否存在
        ItemVo item = dataCacheService.getItem(itemCode);
        if (item == null) {
            throw new BaseException("事项编码错误");
        }
        //判断事项是否关联表单
        FormPageVo form = dataCacheService.getFormPage(item.getFormCode());
        if (form == null) {
            throw new BaseException("事项未关联表单");
        }
        //解析用户信息
        personInfo = personInfo.replaceAll(" ", "+");
        String info = AESUtil.decrypt(personInfo, Constant.TERMINAL_KEY);
        JSONObject jsonObject = JSON.parseObject(info);
        String cerNo = jsonObject.getString("idcode");
        String name = jsonObject.getString("username");

        String formBusiCode;
        if (form.getType() == 6) {//创建公安表单信息并获得表单流水号
            formBusiCode = createGaForm(itemCode, cerNo, name, "");
        } else {
            formBusiCode = UUID.randomUUID().toString().replaceAll("-", "");
        }
        //创建表单信息
        String formUrl = createFormInfo(formBusiCode, itemCode, name, cerNo, ApplySourceType.TERMINAL.getName());
        formUrl += "?formBusiCode=" + formBusiCode + "&areaCode=" + areaCode;
        logger.info("【getTerminalFormUrl end】 formUrl:{}", formUrl);
        return formUrl;
    }

    @Override
    public String getOfflineFormUrl(String itemInnerCode, String cerNo, String cerName, String projectId) {
        logger.info("【getOfflineFormUrl begin】itemInnerCode:{}, cerNo:{}, formBusiCode:{}", itemInnerCode, cerNo, projectId);
        String formBusiCode = MD5Util.MD5(projectId + Constant.OFFLINE_KEY);
        //判断是否第一次创建表单
        JSONObject jsonObject = fdpClientMgmtService.loadFormInfo(formBusiCode);
        if (Detect.notEmpty(jsonObject)) {
            return jsonObject.getString("FORM_URL") + "?formBusiCode=" + formBusiCode;
        }
        //判断事项是否存在
        ItemVo item = dataCacheService.getItem(itemInnerCode);
        if (item == null) {
            throw new BaseException("事项编码错误");
        }
        //判断事项是否关联表单
        FormPageVo form = dataCacheService.getFormPage(item.getFormCode());
        if (form == null) {
            throw new BaseException("事项未关联表单");
        }
        String formUrl = createFormInfo(formBusiCode, itemInnerCode, cerName, cerNo, ApplySourceType.OFFLINE.getName());
        formUrl += "?formBusiCode=" + formBusiCode;
        logger.info("【getOfflineFormUrl end】 formUrl:{}", formUrl);
        return formUrl;
    }

    @Override
    public void submitOfflineForm(String formBusiCode, String formInfoStr) {
        logger.info("submitOfflineForm begin:" + formBusiCode);
        //保存表单信息
        saveFormData(formBusiCode, formInfoStr, null, null);

        JSONObject formInfo = FormInfoContext.get();
        String itemCode = formInfo.getString("ITEM_CODE");
        ItemVo item = dataCacheService.getItem(itemCode);
        Optional<MaterialVo> materialOp = item.getSituationMap().get(itemCode).getMaterialList().stream()
                .filter(materialVo -> materialVo.getType() == 3).findAny();
        MaterialVo materialVo = materialOp.get();
        String certCode = materialVo.getTempCodeList().get(0);
        String pdf = certificateFileMgmtService.createFormPdf(certCode, formInfoStr);
        try {
            //FileUtil.decoderBase64File(pdf, "F:\\123.pdf" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 推送给南威一窗

    }

    @Override
    public Map<String, Object> loadOfflineFormData(String formBusiCode) {
        logger.info("loadOfflineFormData:" + formBusiCode);
        // 获取表单信息
        JSONObject formInfo = FormInfoContext.get();
        // 获取表单数据
        Map<String, Object> result = new HashMap<>();
        String applyCardNumber = formInfo.getString("APPLY_CARD_NUMBER");
        String applyName = formInfo.getString("APPLY_NAME");
        String itemCode = formInfo.getString("ITEM_CODE");
        String formCode = formInfo.getString("FORM_CODE");
        // 用户基本信息
        Map<String, Object> personInfo = new HashMap<>();
        personInfo.put("APPLY_NAME", applyName);
        personInfo.put("APPLY_CARD_NUMBER", applyCardNumber);
        personInfo.put("ITEM_CODE", itemCode);
        ItemVo item = dataCacheService.getItem(itemCode);
        personInfo.put("ITEM_NAME", item.getName());
        result.put("personInfo", personInfo);
        //表单信息
        JSONObject formDataJson = fdpClientMgmtService.loadFormData(formBusiCode);
        if (Detect.notEmpty(formDataJson)) {
            JSONObject formContent = formDataJson.getJSONObject("formContent");
            JSONObject formData = formContent.getJSONObject("formInfo");
            result.put("formData", formData);
        }
        return result;
    }

    private String createGaForm(String itemCode, String cerNo, String name, String mobile) {
        String serviceCode = "terminal;" + itemCode;
        Map<String, Object> formReq = new HashMap<>();
        formReq.put("service_code", serviceCode);
        formReq.put("applyer_name", name);
        formReq.put("applyer_cert_no", cerNo);
        formReq.put("applicant_uid", cerNo);
        formReq.put("applyer_cert_type", "31");//证件类型，31身份证,51营业执照
        formReq.put("applyForm", "2");//1-PC端,2-移动端，9-一窗
        formReq.put("busType", "0");//业务类型
        formReq.put("applyer_mobile", mobile);//联系电话
        try {
            String url = gaServerUrl + serviceCode;
            Map<String, String> headers = new HashMap<>();
            headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_JSON);
            HttpClientResult result = HttpClientUtil.doPost(url, headers, formReq);
            String content = result.getContent();
            logger.info("createGaForm result:" + content);
            JSONObject jsonObject = JSONObject.parseObject(content);
            jsonObject = jsonObject.getJSONObject("data");
            return jsonObject.getString("formBusiCode");
        } catch (Exception e) {
            throw new BaseException("创建表单异常", e);
        }
    }

    private String createFormInfo(String formBusiCode, String itemCode, String name, String cerNo, String applySource) {
        logger.info("createFormInfo begin");
        // 1. 获取事项和表单信息
        ItemVo item = dataCacheService.getItem(itemCode);
        FormPageVo form = dataCacheService.getFormPage(item.getFormCode());
        // 2. 保存业务信息（保存原始数据）
        Map<String, Object> formInfo = new HashMap<String, Object>();
        formInfo.put("FORM_BUSINESS_CODE", formBusiCode);
        formInfo.put("APPLY_SOURCE", applySource);//应用来源
        formInfo.put("FORM_CODE", form.getCode());
        formInfo.put("ITEM_CODE", item.getInnerCode());
        formInfo.put("SERVICE_ID", item.getServiceId());
        formInfo.put("APPLY_SCENA", item.getInnerCode());
        formInfo.put("FORM_CREATOR", name);
        formInfo.put("APPLY_NAME", name);
        formInfo.put("APPLY_CARD_NUMBER", cerNo);
        formInfo.put("APPLICANT_UID", cerNo);
        formInfo.put("APPLY_CARD_TYPE", "31");
        //formInfo.put("APPLY_MOBILE", jsonObj.getString("applyer_mobile"));
        formInfo.put("FORM_CREATED", new Date());
        formInfo.put("FORM_VERSION", form.getVersion());
        //获取表单地址
        String formUrl = formRootUrl + applySource + "/" + form.getCode() + "_" + form.getVersion() + ".html";
        formInfo.put("FORM_URL", formUrl);
        boolean result = fdpClientMgmtService.createFormInfo(formInfo);
        if (result) {
            return formUrl;
        } else {
            throw new BaseException("生成表单信息失败");
        }
    }

    private List<Map<String, Object>> collectData(String formCode, String cerNo, String cerName, String itemCode) {
        try {
            FormPageVo formPage = dataCacheService.getFormPage(formCode);
            List<String> tempCodes = formPage.getTempCodes();
            if(!Detect.notEmpty(tempCodes)) {
                throw new BaseException("表单未关联数据查询证照");
            }
            List<Map<String, Object>> listData = dataCollectionMgmtService.getListData(tempCodes, cerNo, cerName, itemCode, "");
            /*List<Map<String, Object>> listData = new CopyOnWriteArrayList<>();
            //模拟数据
            Map<String, Object> map1 = new HashMap<>();
            logger.info("统一社会信用代码：" + "1136262889");
            map1.put("DycspjspjBusiness010", "1136262889");
            listData.add(map1);*/
            if (!Detect.notEmpty(listData)) {
                return Collections.emptyList();
            }
            //将所有数据合并在一起
            Map<String, Object> allDataMap = new HashMap<>();
            listData.forEach(allDataMap::putAll);
            //组合数据
            Map<String, Object> dataMap = new HashMap<>();
            CertResourceVo certResource = dataCacheService.getCertResource(formPage.getCode());
            if (certResource != null && Detect.notEmpty(certResource.getFieldList())) {
                List<CertResourceFieldVo> fieldList = certResource.getFieldList();
                fieldList.forEach(fieldVo -> {
                    Object value = allDataMap.get(fieldVo.getFieldName());
                    if (value != null) {
                        dataMap.put(fieldVo.getFieldCode(), value);
                    }
                });
            } else {
                dataMap.putAll(allDataMap);
            }
            dataMap.put("APPLY_NAME", cerName);
            dataMap.put("APPLY_CARD_NUMBER", cerNo);
            dataMap.put("username", cerName);
            dataMap.put("idnum", cerNo);
            dataMap.put("DycspjspjBusiness010", "1136262889");
            return Collections.singletonList(dataMap);
        } catch (RuntimeException e) {
            logger.error("加载表单数据失败:" + e.getMessage());
        }
        return Collections.emptyList();
    }

    /*private List<Map<String, Object>> collectData(String formCode, String cerNo, String cerName, String itemCode) {
        try {
            FormPageVo formPage = dataCacheService.getFormPage(formCode);
            List<String> tempCodes = formPage.getTempCodes();
            if(!Detect.notEmpty(tempCodes)) {
                throw new BaseException("表单未关联数据查询证照");
            }
            List<Map<String, Object>> listData = dataCollectionMgmtService.getListData(tempCodes, cerNo, cerName, itemCode, "");
            Map<String,Object> map1 = new HashMap<>();
            logger.info("统一社会信用代码："+"1136262889");
            map1.put("DycspjspjBusiness010","1136262889");
            listData.add(map1);
            if(!Detect.notEmpty(listData)) {
                return Collections.emptyList();
            }
            //将所有数据合并在一起
            Map<String, Object> allDataMap = new HashMap<>();
            listData.forEach(allDataMap::putAll);
            //组合数据
            Map<String, Object> dataMap = new HashMap<>();
            CertResourceVo certResource = dataCacheService.getCertResource(formPage.getCode());
            if(certResource != null && Detect.notEmpty(certResource.getFieldList())) {
                List<CertResourceFieldVo> fieldList = certResource.getFieldList();
                fieldList.forEach(fieldVo -> {
                    Object value = allDataMap.get(fieldVo.getFieldName());
                    if(value != null) {
                        dataMap.put(fieldVo.getFieldCode(), value);
                    }
                });
            } else {
                dataMap.putAll(allDataMap);
            }
            dataMap.put("APPLY_NAME", cerName);
            dataMap.put("APPLY_CARD_NUMBER", cerNo);
            dataMap.put("username", cerName);
            dataMap.put("idnum", cerNo);
            return Collections.singletonList(dataMap);
        } catch (RuntimeException e) {
            logger.error("加载表单数据失败:" + e.getMessage());
        }
        return Collections.emptyList();
    }*/

    // 获取数据
    private JSONArray getData(List<CertResourceFieldVo> fields, JSONObject formInfo) throws IOException {
        String retdataelementstr = "";
        for (CertResourceFieldVo field : fields) {
            retdataelementstr += field.getFieldCode() + ",";
        }
        retdataelementstr = retdataelementstr.substring(0, retdataelementstr.length() - 1);
        String retnum = "1";
        String sfid = formInfo.getString("APPLY_CARD_NUMBER");
        String name = formInfo.getString("APPLY_NAME");
        Map<String, Object> params = new HashMap<>();
        params.put("retDataElementStr", retdataelementstr);
        params.put("retnum", retnum);
        params.put("conditionStr",
                "DPEOPLE003=" + sfid + ";sfid=" + sfid + ";name=" + URLEncoder.encode(name, "utf-8"));
        System.out.println("call usercombineddata : " + retdataelementstr);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);
        try {
            HttpClientResult httpClientResult = HttpClientUtil.doPost(dataServiceUrl, headers, params);
            String resultstr = httpClientResult.getContent();
            JSONObject jsonObject = JSON.parseObject(resultstr);
            if (600 == jsonObject.getIntValue("code")) {
                return jsonObject.getJSONArray("data");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public String uploadOss(String base64,String path) {
        OSSClientUtil oSSClientUtil = new OSSClientUtil();
        String key = "";
        if(path.equals("PC")){
            key = oSSClientUtil.ROOT + oSSClientUtil.PC + oSSClientUtil.uploadImg2OssBase64(base64, oSSClientUtil.ROOT + oSSClientUtil.PC);
        }else{
            key = oSSClientUtil.ROOT+oSSClientUtil.APP + oSSClientUtil.uploadImg2OssBase64(base64,oSSClientUtil.ROOT+oSSClientUtil.APP);
        }
        return oSSClientUtil.getUrl(key);
    }
}
