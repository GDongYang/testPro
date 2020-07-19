package com.fline.form.controller;

import com.alibaba.fastjson.JSONArray;
import com.fline.form.mgmt.service.DataMgmtService;
import com.fline.form.vo.CertificateResult;
import com.fline.form.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据加载
 *
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataMgmtService dataMgmtService;

    /**
     * 获取照片
     * @param formBusiCode
     * @return
     */
    @GetMapping("/photo/{formBusiCode}")
    public ResponseResult<String> getPhoto(@PathVariable(value = "formBusiCode") String formBusiCode) {
        return ResponseResult.success(dataMgmtService.getPhoto(formBusiCode));
    }

    /**
     * 核验
     * @param formBusiCode
     * @param otherParam
     * @return
     */
    @GetMapping("/check/{formBusiCode}")
    public Map<String, Object> check(@PathVariable(value = "formBusiCode") String formBusiCode,
                                     @RequestParam(value = "otherParam",required = false ) String otherParam) {
        return dataMgmtService.check(formBusiCode, otherParam);
    }

    /**
     * 获取电子证照
     * @param formBusiCode
     * @param situationCode
     * @param otherParam
     * @return
     */
    @PostMapping("/certificateData/{formBusiCode}")
    public ResponseResult<List<CertificateResult>> findCertificateData(@PathVariable(value = "formBusiCode") String formBusiCode
            , @RequestParam(value = "situationCode", required = false ) String situationCode
            , @RequestParam(value = "otherParam", required = false ) String otherParam
            , @RequestParam(value = "formData", required = false ) String formData) {
        List<CertificateResult> certificateData = dataMgmtService.findCertificateData(formBusiCode, situationCode, otherParam, formData);
        return ResponseResult.success(certificateData);
    }

    /**
     * 注册到aic
     * @param certCode
     * @param cerNo
     * @param cerName
     * @param itemCode
     * @param otherParam
     * @return
     */
    @GetMapping("/singleData/{certCode}")
    public ResponseResult<JSONArray> getSingleData(@PathVariable(value = "certCode") String certCode
            , @RequestParam(value = "cerNo", required = false ) String cerNo
            , @RequestParam(value = "cerName", required = false ) String cerName
            , @RequestParam(value = "itemCode", required = false ) String itemCode
            , @RequestParam(value = "otherParam", required = false ) String otherParam) {
        JSONArray data = dataMgmtService.getSingleData(certCode, cerNo, cerName, itemCode, otherParam);
        return ResponseResult.success(data);
    }
}
