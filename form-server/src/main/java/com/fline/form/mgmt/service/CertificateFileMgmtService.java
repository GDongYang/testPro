package com.fline.form.mgmt.service;

import com.fline.form.vo.DataCollectionParam;
import com.fline.form.vo.CertTempVo;

import java.util.List;
import java.util.Map;

public interface CertificateFileMgmtService {
	
	String createCommonFile(CertTempVo cert, DataCollectionParam param, Map<String, Object> data);
	
	String createCommonFile(CertTempVo cert, DataCollectionParam param, List<Map<String, Object>> datas);

	String createFileByByte(CertTempVo cert, DataCollectionParam param, byte[] bytes);

    String createPdfByFtl(Map<String, Object> dataMap, String templatePath);

    String createFormPdf(String certCode, String formDataStr);

    byte[] addUserSignature(byte[] pdf, String signature);
}
