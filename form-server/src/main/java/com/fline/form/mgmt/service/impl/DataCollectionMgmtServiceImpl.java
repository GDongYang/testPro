package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.Constant;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.CertificateFileMgmtService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DataCollectionMgmtService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.util.CustomThreadPoolExecutor;
import com.fline.form.util.HttpClientResult;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.util.PDFbox;
import com.fline.form.vo.*;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;
import com.itextpdf.io.util.ResourceUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DataCollectionMgmtServiceImpl implements DataCollectionMgmtService {
	
	private Log logger = LogFactory.getLog(DataCollectionMgmtServiceImpl.class);
	@Autowired
	private DataCacheService dataCacheService;
	@Autowired
	private FdpClientMgmtService fdpClientMgmtService;
	@Autowired
	private CertificateFileMgmtService certificateFileMgmtService;
    @Value("${yztb.username}")
	private String username;
    @Value("${yztb.secret}")
	private String secret;
    @Value("${yztb.url}")
    private String yztbUrl;

    /**
     * 获取单个证照（含pdf）
     * @param business
     * @return
     */
    @Override
    public CertificateResult getCertificateSingleWithFile(BusinessVo business) {
        business.setNeedFile(true);
        return getCertificateSingleInner(business);
    }

    /**
     * 获取单个证照（不含pdf）
     * @param business
     * @return
     */
    @Override
    public CertificateResult getCertificateSingle(BusinessVo business) {
        business.setNeedFile(false);
        return getCertificateSingleInner(business);
    }


    /**
     * 多事项获取证照（pdf）
     * @param itemCertificateVo
     * @return
     */
    @Override
    public List<CertificateResult> findDataByItems(ItemCertificateVo itemCertificateVo) {
        return findDataByItemsInner(itemCertificateVo, false);
    }

    /**
     * 多事项获取证照（jpg）
     * @param itemCertificateVo
     * @return
     */
    @Override
    public List<CertificateResult> findDataByItems2Jpg(ItemCertificateVo itemCertificateVo) {
        return findDataByItemsInner(itemCertificateVo, true);
    }

    /**
     * 多个事项，情形获取电子证照（结构化数据和板式文件） 1.材料根据名称去重 2.只传事项编码不传情形编码只返回必要材料
     * @param itemCertificateVo
     * @return
     */
    private List<CertificateResult> findDataByItemsInner(ItemCertificateVo itemCertificateVo, boolean pdf2Jpg) {
        String itemName = "";//存储当前事项的名字
        List<ItemSituationVo> items = itemCertificateVo.getItems();
        if(!Detect.notEmpty(items)){
            throw new BaseException("获取电子证照失败:事项不能为空！");
        }
        BusinessVo business = new BusinessVo();
        //处理事项
        Set<MaterialVo> materialList = new HashSet<>();//材料,根据名称去重
        for (ItemSituationVo item : items) {
            String itemInnerCode = item.getInnerCode();
            ItemVo itemVo = dataCacheService.getItem(itemInnerCode);//从缓存获取事项信息
            if(itemVo == null) {
                continue;
            }
            Map<String, SituationVo> situationMap = itemVo.getSituationMap();//获取事项下情形信息
            if(!Detect.notEmpty(situationMap)) {
                continue;
            }
            List<String> situationCodes = item.getSituationCodes();//获取情形编码
            if (Detect.notEmpty(situationCodes)) {//有传情形编码取情形下材料
                for (String situationCode : situationCodes) {
                    SituationVo situationVo = situationMap.get(situationCode);//得到单个情形
                    if(situationVo == null) {
                        continue;
                    }
                    List<MaterialVo> materialVoList = situationVo.getMaterialList();//获取材料列表
                    if (!Detect.notEmpty(materialVoList)) {
                        continue;
                    }
                    materialList.addAll(materialVoList);
                }
            } else {//没有情形编码取缺省情形下材料
                SituationVo situationVo = situationMap.get(itemInnerCode);//得到缺省情形
                List<MaterialVo> list = situationVo.getMaterialList();
                //只返回必要材料
                List<MaterialVo> collect = list.stream().filter(materialVo -> materialVo.getIsMust() == 1).collect(Collectors.toList());
                materialList.addAll(collect);
            }
            itemName = itemVo.getName();
            //存入办件信息
            business.setCode(itemCertificateVo.getBusiCode());
            business.setItemInnerCode(itemInnerCode);
            business.setCerno(itemCertificateVo.getCerNo());
            business.setCerName(itemCertificateVo.getCerName());
            long deptId = Long.parseLong(itemVo.getDepartmentId());
            business.setDepartmentId(deptId);
            business.setStatus(1);
            business.setDepartmentName(itemVo.getDeptName());
            business.setCreateDate(new Date());
            business.setType(2);//接口
            business.setItemCode(itemVo.getCode());
            business.setItemName(itemVo.getName()); // 存入事项名称
            //fdpClientMgmtService.insertDataIntoSolr(KeyConstant.YZTB_SOLR_TABLE_BUSINESS, business);

        }
        if(!Detect.notEmpty(materialList)) {
            return null;
        }
        List<DataCollectionParam> params = new ArrayList<>();
        for (MaterialVo materialVo : materialList) {
            DataCollectionParam param = new DataCollectionParam();
            param.setNeedFile(true);
            param.setPdf2Jpg(pdf2Jpg);
            param.setMaterialVo(materialVo);
            param.setCerNo(itemCertificateVo.getCerNo());
            param.setCerName(itemCertificateVo.getCerName());
            param.setOtherParam(itemCertificateVo.getOtherParam());
            param.setBusiCode(itemCertificateVo.getBusiCode());
            param.setItemCode(business.getItemCode());
            param.setItemInnerCode(items.get(0).getInnerCode());
            param.setItem(itemName);
            param.setBusinessVo(business);
            param.setFormData(itemCertificateVo.getFormData());
            params.add(param);
        }
        List<CertificateResult> results = new CopyOnWriteArrayList<>();
        ExecutorService pool = CustomThreadPoolExecutor.newFixedThreadPool(params.size());
        for (DataCollectionParam collectionParam : params) {
                pool.execute(new SingerCertificateData(collectionParam, results));
        }
        pool.shutdown();
        try {
            if(!pool.awaitTermination(Constant.THREAD_POOL_TIME_OUT, TimeUnit.SECONDS)) {
                logger.error("【findDataByItemsInner timeout】params:" + params);
            }
        } catch (InterruptedException e) {
            logger.error("【findDataByItemsInner error】params:" + params);
        }
        return results;
    }

    /**
     * 获取单个电子证照(数据和板式文件)
     * @param business
     * @return
     */
    private CertificateResult getCertificateSingleInner(BusinessVo business) {
        logger.info("【getCertificateSingleInner begin】itemInnerCode:" + business.getItemInnerCode() + ",situationCode:" + business.getSituationCode());
        BusinessItemVo businessItem = new BusinessItemVo();
        try {
            ItemVo itemVo = dataCacheService.getItem(business.getItemInnerCode().trim());
            if(itemVo == null){
                throw new BaseException("获取电子证照失败:事项编码有误！");
            }
            Map<String, SituationVo> situationMap = itemVo.getSituationMap();
            if(situationMap == null) {
                throw new BaseException("获取电子证照失败:事项未配置情形！");
            }

            Set<MaterialVo> materialList = new HashSet<>();//材料
            if(Detect.notEmpty(business.getSituationCode())) {//有传情形编码取情形下的材料
                SituationVo situationVo = situationMap.get(business.getSituationCode());
                if(situationVo == null) {
                    throw new BaseException("获取电子证照失败:情形编码错误，该事项下没有对应的情形编码！");
                }
                materialList.addAll(situationVo.getMaterialList());
            } else {//没有传情形编码取事项的缺省情形
                SituationVo situationVo = situationMap.get(itemVo.getInnerCode());
                if(situationVo != null) {
                    materialList.addAll(situationVo.getMaterialList());
                }
            }
            if(!Detect.notEmpty(materialList)) {
                throw new BaseException("获取电子证照失败:权限不足，该事项没有配置材料！");
            }

            Optional<MaterialVo> materialOptional = materialList.stream().filter(materialVo -> materialVo.getCode().equals(business.getCertCode())).findAny();
            if(!materialOptional.isPresent()) {
                throw new BaseException("获取电子证照失败:材料编码错误，该事项情形下没有对应的材料编码！");
            }
            MaterialVo material = materialOptional.get();

            List<String> tempCodeList = material.getTempCodeList();//获取该材料下配置的证件模板编码
            if(!Detect.notEmpty(tempCodeList)) {
                throw new BaseException("获取电子证照失败:该材料下未配置电子证照！");
            }
            List<CertTempVo> certTempList = dataCacheService.getItemCertTempList(tempCodeList);
            if(certTempList == null) {
                throw new BaseException("获取电子证照失败:该材料下未配置电子证照！");
            }
            String busiCode = business.getCode();
            //获取business属性并保存
            business.setDepartmentId(Long.parseLong(itemVo.getDepartmentId()));
            business.setStatus(1);
            business.setDepartmentName(itemVo.getDeptName());
            business.setCreateDate(new Date());
            business.setItemCode(itemVo.getCode());
            business.setItemName(itemVo.getName()); // 存入事项的名称
            //fdpClientMgmtService.insertDataIntoSolr(KeyConstant.YZTB_SOLR_TABLE_BUSINESS, business);

            businessItem.setCerno(business.getCerno());
            businessItem.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
            businessItem.setCreateDate(new Date());
            businessItem.setStatus(ResultCode.NO_DATA.getCode());
            businessItem.setBusinessCode(busiCode);
            businessItem.setDeptId(Long.parseLong(itemVo.getDepartmentId()));
            businessItem.setDeptName(itemVo.getDeptName());
            businessItem.setItemName(itemVo.getName());
            businessItem.setItemInnerCode(itemVo.getInnerCode());
            businessItem.setMaterialCode(material.getCode());//材料编码
            businessItem.setMaterialName(material.getName());//材料名称

            DataCollectionParam param = new DataCollectionParam();
            param.setPdf2Jpg(business.getPdf2Jpg());
            param.setNeedFile(business.isNeedFile());
            param.setDepartmentId(Long.parseLong(itemVo.getDepartmentId()));
            param.setBusiCode(business.getCode());
            param.setCerNo(business.getCerno());
            param.setCerName(business.getCerName());
            param.setOtherParam(business.getOtherParam());
            param.setAskDeptId(business.getDepartmentId());
            param.setItem(itemVo.getName());
            String itemBaseCode = business.getItemBaseCode();
            param.setItemCode(Detect.notEmpty(itemBaseCode) ? itemBaseCode : itemVo.getCode());

            CertificateResult result = new CertificateResult();
            result.setBusiItemCode(busiCode);
            result.setMaterialCode(material.getCode());
            result.setIsMust(material.getIsMust());//是否必要
            result.setMaterialName(material.getName());
            result.setMaterialTips(material.getTips());
            result.setNeedUpload(material.getNeedUpload());
            for (CertTempVo certTempVo : certTempList) {
                dataHandler(certTempVo, param, result);//获取数据
                businessItem.setCertTempId(certTempVo.getId());
                businessItem.setCertCode(certTempVo.getCode());
                businessItem.setCertName(certTempVo.getName());
                businessItem.setStatus(result.getCode());
                businessItem.setMessage(result.getMsg());
                if(result.getCode() == ResultCode.OK.getCode()) {
                    break;
                }
            }
            if(result.getCode() == ResultCode.FAIL.getCode()) {
                throw new RuntimeException("获取电子证照失败:" + result.getMsg());
            }
            return result;
        } catch (Exception e) {
            businessItem.setStatus(ResultCode.FAIL.getCode());
            businessItem.setMessage(e.getMessage());
            throw new BaseException("获取电子证照失败", e);
        } finally {
            //fdpClientMgmtService.insertDataIntoSolr(KeyConstant.YZTB_SOLR_TABLE_BUSINESS_ITEM, businessItem);
        }
    }


    private class SingerCertificateData implements Runnable {
        private DataCollectionParam param;
        private List<CertificateResult> results;

        SingerCertificateData(DataCollectionParam param,List<CertificateResult> results) {
            this.param = param;
            this.results = results;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("pool-SingerCertificateData-thread-" + param.getMaterialVo().getCode());
            logger.info("【SingerCertificateData begin】materialCode:" + param.getMaterialVo().getCode());
            CertificateResult result = new CertificateResult();
            BusinessItemVo businessItem = new BusinessItemVo();
            try {
                MaterialVo materialVo = param.getMaterialVo();
                result.setBusiItemCode(param.getBusiCode());
                result.setMaterialCode(materialVo.getCode());//材料编码
                result.setMaterialName(materialVo.getName());//材料名称
                result.setMaterialType(materialVo.getType());//材料类型
                result.setIsMust(materialVo.getIsMust());//是否必要
                result.setMaterialTips(materialVo.getTips());
                result.setNeedUpload(materialVo.getNeedUpload());

                businessItem.setCerno(param.getCerNo());
                businessItem.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
                businessItem.setCreateDate(new Date());
                businessItem.setStatus(ResultCode.NO_DATA.getCode());
                businessItem.setBusinessCode(param.getBusiCode());
                businessItem.setMaterialCode(materialVo.getCode());//材料编码
                businessItem.setMaterialName(materialVo.getName());//材料名称
                BusinessVo businessVo = param.getBusinessVo();
                businessItem.setDeptId(businessVo.getDepartmentId());
                businessItem.setAccountId(businessVo.getAccountId());
                businessItem.setAccountName(businessVo.getAccountName());
                businessItem.setDeptName(businessVo.getDepartmentName());
                businessItem.setItemInnerCode(businessVo.getItemInnerCode());
                businessItem.setItemName(businessVo.getItemName());

                List<String> tempCodeList = materialVo.getTempCodeList();
                if (!Detect.notEmpty(tempCodeList)) {
                    return;
                }
                List<CertTempVo> certTempList = dataCacheService.getItemCertTempList(tempCodeList);
                if (!Detect.notEmpty(certTempList)) {
                    return;
                }
                for (CertTempVo certTempVo : certTempList) {
                    dataHandler(certTempVo, param, result);//获取数据
                    businessItem.setCertTempId(certTempVo.getId());
                    businessItem.setCertCode(certTempVo.getCode());
                    businessItem.setCertName(certTempVo.getName());
                    businessItem.setStatus(result.getCode());
                    businessItem.setMessage(result.getMsg());
                    businessItem.setTimeConsuming(result.getTimeConsuming());
                    if(result.getCode() == ResultCode.OK.getCode()) {
                        break;
                    }
                }

            } finally {
            	results.add(result);
            	//fdpClientMgmtService.insertDataIntoSolr(KeyConstant.YZTB_SOLR_TABLE_BUSINESS_ITEM, businessItem);
            }
        }
    }

    /**
     * 获取电子证照
     * @param certTempVo
     * @param param
     * @param result
     */
    private void dataHandler(CertTempVo certTempVo, DataCollectionParam param, CertificateResult result) {
        String certCode = certTempVo.getCode();
        logger.info("【dataHandler begin】: certCode:" + certCode + ",cerNo:" + param.getCerNo());
        try {
            String suffix = certTempVo.getType() == 4 ? ".jpg" : ".pdf";//材料文件后缀
            result.setCertName(certTempVo.getName() + suffix);
            result.setCertCode(certCode);

            String certFile;
            if(certTempVo.getType() == 3) {//生成申请表
                certFile = certificateFileMgmtService.createFormPdf(certCode, param.getFormData());
            } else {//获取数据
                certFile = getSingleFile(certCode, param.getCerNo(), param.getCerName(), param.getItemInnerCode(), param.getOtherParam());
            }
            if (!Detect.notEmpty(certFile) || "null".equals(certFile)) {
                result.setCode(ResultCode.NO_DATA.getCode());
                result.setMsg(ResultCode.NO_DATA.getMsg());
                return;
            }

            result.setCode(ResultCode.OK.getCode());
            result.setMsg(ResultCode.OK.getMsg());
            result.setCertFile(certFile);

            //电子证照存入大数据
            if (param.isNeedFile()) {
                if(!Detect.notEmpty(result.getCertFile())) {
                    result.setCode(ResultCode.NO_DATA.getCode());
                    result.setMsg(ResultCode.NO_DATA.getMsg());
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(Constant.FDP_FILE_KEY)
               // .append("/").append(param.getItemInnerCode())
                .append("/").append(param.getBusiCode())//表单流水号
                .append("/").append(certTempVo.getName() + suffix);
                try {
                	fdpClientMgmtService.writeFile(sb.toString(), null, new BASE64Decoder().decodeBuffer(result.getCertFile()));
                } catch (Exception e) {
                	e.printStackTrace();
                	logger.error("文件存入大数据失败");
                }
            }
            if (param.isPdf2Jpg()) {
                if (!".jpg".equals(suffix)) {
                    byte[] bytes = PDFbox.pdf2Jpg(new BASE64Decoder().decodeBuffer(result.getCertFile()));
                    String encode = new BASE64Encoder().encode(bytes);
                    result.setCertFile(encode);
                }
            }
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL.getCode());
            result.setMsg(e.getMessage());
            logger.error("【dataHandler error】: certCode:" + certCode + ",cerNo:" + param.getCerNo() + ",msg:" + e.getMessage(), e);
        }
    }

    @Override
    public JSONArray getSingleData(String certCode, String cerNo, String cerName, String itemCode, String otherParam) {
        JSONArray result = new JSONArray();
        JSONObject jsonObject = sendRequest(certCode, cerNo, cerName, itemCode, otherParam);
        Object certData = jsonObject.get("certData");
        if(certData instanceof JSONObject) {
            result.add(certData);
        } else if (certData instanceof JSONArray){
            result = (JSONArray) certData;
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getListData(List<String> certCodes, String cerNo, String cerName, String itemCode, String otherParam) {
        ExecutorService pool = CustomThreadPoolExecutor.newFixedThreadPool(certCodes.size());
        List<Map<String, Object>> result = new CopyOnWriteArrayList<>();
        certCodes.forEach((certCode) -> pool.execute(() -> {
            Thread.currentThread().setName("pool-getListData-thread-" + certCode);
            try {
                JSONArray jsonArray = getSingleData(certCode, cerNo, cerName, itemCode, otherParam);
                if(jsonArray != null && jsonArray.size() > 0) {
                    result.add(jsonArray.getJSONObject(0));
                }
            } catch (Exception e) {
                logger.error("【getListData error】cerNo:" + cerNo + ",certCode:" + certCode + ",msg:" + e.getMessage(), e);
            }
        }));
        pool.shutdown();
        try {
            if(!pool.awaitTermination(Constant.THREAD_POOL_TIME_OUT, TimeUnit.SECONDS)) {
                logger.error("【getListData timeout】cerNo:" + cerNo + ",itemCode:" + itemCode);
            }
        } catch (InterruptedException e) {
            logger.error("【getListData error】cerNo:" + cerNo + ",itemCode:" + itemCode);
        }
        return result;
    }

    private String getSingleFile(String certCode, String cerNo, String cerName, String itemCode, String otherParam) {
        JSONObject jsonObject = sendRequest(certCode, cerNo, cerName, itemCode, otherParam);
        return jsonObject.getString("certFile");
    }

    /**
     * 演示模拟
     * @param certCode
     * @param cerNo
     * @param cerName
     * @param itemCode
     * @param otherParam
     * @return
     */
   /* public static String getSingleFile(String certCode, String cerNo, String cerName, String itemCode, String otherParam) {
        File file = null;
        String res = null;
        try {
             file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "zhengjian/yingyezhizhao.pdf");
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024*10];
            int len = bin.read(buffer);
            while(len!=-1){
                bout.write(buffer,0,len);
                len = bin.read(buffer);
            }
            //读取完毕
            bout.flush();
            byte[] bytes = baos.toByteArray();
            res = new BASE64Encoder().encode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }*/

    /*public static void main(String[] args) {
        String singleFile = getSingleFile("", "", "", "", "");
        System.out.println(singleFile);
    }*/

    private JSONObject sendRequest(String certCode, String cerNo, String cerName, String itemInnerCode, String otherParam) {
        logger.info("【sendRequest begin】certCode：" +certCode + ",cerNo:" + cerNo + ",cerName:" + cerName);
        RequestLog requestLog = new RequestLog();
        Map<String, Object> params = new HashMap<>();//请求参数
        String busiCode = UUID.randomUUID().toString();
        params.put("busiCode", busiCode);
        params.put("cerNo", cerNo);
        params.put("cerName", cerName);
        params.put("itemCode", itemInnerCode);
        params.put("username", username);
        params.put("secret", secret);
//        params.put("applicantUnit", "台州市掌上办");
//        params.put("applicantUser", "台州市掌上办");
        params.put("applicantUnit", "台州市公安局");
        params.put("applicantUser", "台州市公安局自助机");
        params.put("certCode", certCode);
        requestLog.setRequestData(JSON.toJSONString(params));
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientUtil.CONTENT_TYPE_KEY, HttpClientUtil.CONTENT_TYPE_URLFORM);
        try {
            HttpClientResult httpClientResult = HttpClientUtil.doPost(yztbUrl, headers, params);
            String content = httpClientResult.getContent();
            logger.info("【sendRequest result】" + content);
            requestLog.setResponseData(content.length() > 200 ? content.substring(0, 200) : content);
            JSONObject jsonObject = JSON.parseObject(content);
            if("0".equals(jsonObject.getString("returnCode"))) {
                return jsonObject;
            } else {
                throw new RuntimeException(jsonObject.getString("returnMessage"));
            }
        } catch (Exception e) {
            requestLog.setStatus(-1);
            requestLog.setResponseData(e.getMessage());
            throw new BaseException("获取数据失败！", e);
        } finally {
            requestLog.setCerName(cerName);
            requestLog.setCerNo(cerNo);
            requestLog.setItemInnerCode(itemInnerCode);
            CertTempVo certTemp = dataCacheService.getCertTemp(certCode);
            requestLog.setRequestName(certTemp.getName());
            requestLog.setRequestCode(certCode);
            requestLog.setDataSource("一证通办");
            requestLog.setRequestTime(new Date());
            fdpClientMgmtService.saveRequestLog(requestLog);
        }
    }
}
