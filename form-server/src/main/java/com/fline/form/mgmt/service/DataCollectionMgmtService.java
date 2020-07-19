package com.fline.form.mgmt.service;

import com.alibaba.fastjson.JSONArray;
import com.fline.form.vo.BusinessVo;
import com.fline.form.vo.CertificateResult;
import com.fline.form.vo.ItemCertificateVo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhaoxz
 * 2019-04-25 数据收集处理接口
 */
public interface DataCollectionMgmtService {

    CertificateResult getCertificateSingleWithFile(BusinessVo business);

    CertificateResult getCertificateSingle(BusinessVo business);

    List<CertificateResult> findDataByItems2Jpg(ItemCertificateVo itemCertificateVo);

    List<CertificateResult> findDataByItems(ItemCertificateVo itemCertificateVo);

    JSONArray getSingleData(String certCode, String cerNo, String cerName, String itemCode, String otherParam);

    List<Map<String, Object>> getListData(List<String> certCodes, String cerNo, String cerName, String itemCode, String otherParam);

}
