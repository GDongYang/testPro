package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.freemarker.FreeMarkerUtil;
import com.fline.form.constant.AttachmentType;
import com.fline.form.constant.Constant;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.CertificateFileMgmtService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.util.FileUtil;
import com.fline.form.util.PdfUtil;
import com.fline.form.vo.CertTempVo;
import com.fline.form.vo.DataCollectionParam;
import com.fline.form.vo.SealLogVo;
import com.fline.form.vo.TempAttachmentVo;
import com.fline.yztb.vo.FormPageVo;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.codec.Base64;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Service
public class CertificateFileMgmtServiceImpl implements CertificateFileMgmtService {
	
	private Log logger = LogFactory.getLog(CertificateFileMgmtServiceImpl.class);
	
	private static String EMPTY_FILLER = "";
	@Autowired
	private FdpClientMgmtService fdpClientMgmtService;
	@Autowired
	private DataCacheService dataCacheService;
	
	
	@SuppressWarnings("unchecked")
	private String createCertificateFile(Map<String, Object> param) {
		Map<String, Object> dataMap = (Map<String, Object>) param.get("dataItem");
		String certCode = param.get("certCode")!=null?param.get("certCode").toString():"";
		String cerNo = param.get("cerNo")!=null?param.get("cerNo").toString():"";
		String certContent = param.get("certContent")!=null?param.get("certContent").toString():"";

		String realpath = Constant.REAL_PATH;
		String outputPath = realpath + File.separator + "certificates" + File.separator + cerNo;
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

		String fileName = certCode + ".pdf";
        String outputFile = outputPath + File.separator + fileName;
		String fileSignedName = certCode + "_signed.pdf";
		String outputSignedFile = outputPath + File.separator + fileSignedName;

        //将参数中的null值和空字符串替换成 指定字符串
  		for(Object o : dataMap.entrySet()){
  			Entry<String,Object> entry = (Entry<String,Object>)o;
  			if(null==entry.getValue()||"".equals(entry.getValue())){
  				entry.setValue(EMPTY_FILLER);
  			}
  		}
  		//生成pdf
  		createHtmlPDFWithContent(certContent,certCode, outputPath, param);

  		//额外操作
        if(param.containsKey("attachmentMap")) {
            Map<Integer, List<TempAttachmentVo>> attachmentMap = (Map<Integer, List<TempAttachmentVo>>) param.get("attachmentMap");
            for(Entry<Integer, List<TempAttachmentVo>> entry : attachmentMap.entrySet()) {
                int code = entry.getKey();
                TempAttachmentVo tempAttachmentVo = entry.getValue().get(0);
                //添加图片
                if (AttachmentType.PHOTO.getCode() == code) {
                    Map<String, Object> photoData = new HashMap<>();
                    photoData.put("x", tempAttachmentVo.getCoordinatex());
                    photoData.put("y", tempAttachmentVo.getCoordinatey());
                    photoData.put("height", tempAttachmentVo.getHeight());
                    photoData.put("width", tempAttachmentVo.getWidth());
                    photoData.put("img", param.get("photo"));
                    addImage(outputFile, photoData);
                }
                //添加二维码
                if (AttachmentType.QR_CODE.getCode() == code) {
                    //生成二维码内容
                    String content = dataMap.get(tempAttachmentVo.getKeyWord()) + "";
                    Map<String, Object> photoData = new HashMap<>();
                    photoData.put("x", tempAttachmentVo.getCoordinatex());
                    photoData.put("y", tempAttachmentVo.getCoordinatey());
                    photoData.put("height", tempAttachmentVo.getHeight());
                    photoData.put("width", tempAttachmentVo.getWidth());
                    //photoData.put("img", QRCoderUtil.encoderQRCoder(content));
                    addImage(outputFile, photoData);
                }
                //添加水印
                if (AttachmentType.WATERMARK.getCode() == code) {
                    addWatermark(outputFile, tempAttachmentVo.getKeyWord());
                }
                //添加其他图片，如logo等
//                if (param.containsKey("images")) {
//                    if(param.get("images") instanceof List<?>) {
//                        List<Map<String,Object>> images= (List<Map<String,Object>>)param.get("images");
//                        for(Map<String,Object> image : images){
//                            addImage(outputFile, image);
//                        }
//                    }
//                }
            }
        }

		String certFile = getFileBase64(outputFile);//转base64
		//盖章
		String signedCertFile = null;
		if (param.containsKey("signInfo")) {
			String outUrl = outputSignedFile;
			List<TempAttachmentVo> signInfo = (List<TempAttachmentVo>) param.get("signInfo");
			List<SealLogVo> logList = (List<SealLogVo>) param.get("sealLog");
			int index = 0;
			for(TempAttachmentVo sign : signInfo){
				try {
					//SignUtil.doSign(outputFile, outUrl, sign);
					fdpClientMgmtService.insertDataIntoSolr(KeyConstant.YZTB_SOLR_TABLE_SEALLOG, logList.get(index++));
					outputFile = outUrl;
				} catch (Exception e) {
					logger.error("盖章失败：" + e.getMessage());
					e.printStackTrace();
				}
			}
			signedCertFile = getFileBase64(outUrl);

		}
        return signedCertFile == null ? certFile : signedCertFile;
	}

	/**
	 * 添加水印
	 * @param pdfPath
	 * @param watermark
	 */
	private void addWatermark(String pdfPath, String watermark) {
		File sourceFile = new File(pdfPath);
		try {
			byte[] bytes = FileUtils.readFileToByteArray(sourceFile);
			bytes = PdfUtil.pdfWatermark(bytes, watermark);
			FileUtils.writeByteArrayToFile(sourceFile, bytes);
		} catch (Exception e) {
			logger.error("添加水印失败：" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void addQrCode(String pdfPath, TempAttachmentVo qrCode) {
    }

	/**
	 * 添加图片
	 * @param pdfPath
	 * @param photoData
	 */
	private void addImage(String pdfPath, Map<String, Object> photoData) {
		Number x = (Number)photoData.get("x");
		Number y = (Number)photoData.get("y");
		Number height = (Number)photoData.get("height");
		Number width = (Number)photoData.get("width");
		byte[] img = (byte[])photoData.get("img");

		x = x == null ? 0 : x;
		y = y == null ? 0 : y;
		height = height == null ? 0 : height;
		width = width == null ? 0 : width;
		
        File sourceFile = new File(pdfPath);
		try {
			byte[] bytes = FileUtils.readFileToByteArray(sourceFile);
			bytes = PdfUtil.addImg2Pdf(bytes, img, x.floatValue(), y.floatValue(), height.floatValue(), width.floatValue());
			FileUtils.writeByteArrayToFile(sourceFile, bytes);
		} catch (Exception e) {
			logger.error("添加图片失败", e);
		}
	}
	/**
	 * @author zhaoxz
	 * @param certContent ftl内容
	 * @param certCode
	 * @param outputPath
	 * @param dataMap
	 *  根据ftl内容生成PDF
	 */
	private static void createHtmlPDFWithContent(String certContent, String certCode, String outputPath, Map<String, Object> dataMap) {
		String fileName = certCode +".pdf";
		dataMap.put("DefaultValue", "");

        Document document = null;
		try {
			String htmlCode = FreeMarkerUtil.processString(certContent, dataMap);
			String outputFile = outputPath + File.separator + fileName;
			System.out.println("outputPath---"+outputFile);
			PdfDocument pd = new PdfDocument(new PdfWriter(outputFile));

			document = new Document(pd, PageSize.A4);
			
			FontProvider font = new FontProvider();
			font.addFont(Constant.FONT_PATH);
			ConverterProperties properties = new ConverterProperties();
			properties.setFontProvider(font);
			properties.setCharset("utf-8");
			
			//设置页面边距 必须先设置边距，再添加内容，否则页边距无效
		    document.setMargins(0, 0, 0, 0);
		    List<IElement> list = HtmlConverter.convertToElements(htmlCode, properties);
		    for (IElement ie : list) {
		        document.add((IBlockElement)ie);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("createHtmlPDFWithContent error:" + e.getMessage());
		}
		finally {
	        document.close();
		}
	}

	@Override
    public String createPdfByFtl(Map<String, Object> dataMap, String templatePath) {
        dataMap.put("DefaultValue", "");
        Document document = null;
        try {
            String htmlCode = FreeMarkerUtil.processPath(templatePath, dataMap);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument pd = new PdfDocument(new PdfWriter(outputStream));
            document = new Document(pd, PageSize.A4);

            FontProvider font = new FontProvider();
            font.addFont(Constant.FONT_PATH);
            ConverterProperties properties = new ConverterProperties();
            properties.setFontProvider(font);
            properties.setCharset("utf-8");

            //设置页面边距 必须先设置边距，再添加内容，否则页边距无效
            //document.setMargins(0, 10, 0, 0);
            List<IElement> list = HtmlConverter.convertToElements(htmlCode, properties);
            for (IElement ie : list) {
                document.add((IBlockElement)ie);
            }
            document.close();
            return new BASE64Encoder().encode(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	private String getFileBase64(String outputPath) {
		File destFile = new File(outputPath);
		InputStream in = null;
		String certFile = null;
        try {
        	in = new FileInputStream(destFile);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            certFile = Base64.encodeBytes(bytes, 0, length);
        }
        catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
        		if (in != null) {
        			in.close();
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return certFile;
	}


	/**
     * 插pdf文件到大数据
     */
	private boolean insert2BigData(String file, String sfid, String certCode, String busiCode) {
//		String key = sfid + File.separator + certCode + File.separator + busiCode + ".pdf";
//		Map<String,String> labels = new HashMap<String, String>();
//		labels.put("certNo", sfid);
//		labels.put("certCode", certCode);
//		labels.put("busiCode", busiCode);
//        boolean flag = fdpClientMgmtService.writeFile(key, labels, Base64.decode(file));
//        logger.debug("key: " + key + " flag=" + flag);
//        return flag;
        return true;
    }

	/**
	 * 插jpg文件到大数据
	 */
	private boolean insertJPG2BigData(String file, String sfid, String certCode, String busiCode) {
//		String key = sfid + File.separator + certCode + File.separator + busiCode + ".jpg";
//		Map<String,String> labels = new HashMap<String, String>();
//		labels.put("certNo", sfid);
//		labels.put("certCode", certCode);
//		labels.put("busiCode", busiCode);
//		boolean flag = fdpClientMgmtService.writeFile(key, labels, Base64.decode(file));
//		logger.debug("key: " + key + " flag=" + flag);
//		return flag;
        return true;
	}
    
	
	@Override
	public String createCommonFile(CertTempVo cert, DataCollectionParam param, Map<String, Object> data) {
		logger.info("【生成PDF begin】 cerName:"+cert.getName() + ",sfId:" + param.getCerNo());
		String certCode = cert.getCode();
		String busiCode = param.getBusiCode();
		String applicantName = param.getApplicantUser();
		String applicantUnit = param.getApplicantUnit();
		String item = param.getItem();
		String cerNo = param.getCerNo();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("certCode", certCode);
		params.put("cerNo", cerNo);
		params.put("busiCode", busiCode);
		params.put("deptId", param.getDepartmentId());
		params.put("deptCode", cert.getDeptCode());

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("ApplicantName", applicantName);
		dataMap.put("ApplicantUnit", applicantUnit);
		dataMap.put("ApplyCause", item);
		dataMap.putAll(data);

		DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
        dataMap.put("UpdateTime", sdf2.format(new Date()));
		dataMap.put("printDate", sdf.format(new Date()));
		dataMap.put("signFlag", Constant.SIGN_FLAG);

		params.put("dataItem", dataMap);

		if (data.get("photo") != null) {
			params.put("photo", data.get("photo"));
		}

		params.put("certContent", cert.getContent());
		//获取附加信息
        Map<Integer, List<TempAttachmentVo>> attachmentMap = cert.getAttachmentMap();
        if (Detect.notEmpty(attachmentMap)) {
            //印章信息
            List<TempAttachmentVo> signVoList = attachmentMap.get(AttachmentType.SEAL.getCode());
            if(Detect.notEmpty(signVoList)) {
                List<SealLogVo> logList = new ArrayList<SealLogVo>();
                for (TempAttachmentVo signVo : signVoList){
                    SealLogVo sealLog = new SealLogVo(param.getDepartmentId(), signVo.getId(),
                            item, cert.getName(), cerNo, busiCode , applicantName);
                    logList.add(sealLog);
                }
                params.put("sealLog", logList);
                params.put("sealInfo", signVoList);
            }
		}
		
		String result = createCertificateFile(params);
		insert2BigData(result, cerNo, certCode, busiCode);
		logger.info("【生成PDF end】 cerName:"+cert.getName() + ",sfId:" + cerNo);
		return result;
	}
	
	/**
	 * 多行
	 */
	@Override
	public String createCommonFile(CertTempVo cert, DataCollectionParam param,List<Map<String, Object>> datas) {
		logger.info("【生成PDF begin】 cerName:"+cert.getName() + ",sfId:" + param.getCerNo());
		String certCode = cert.getCode();
		String busiCode = param.getBusiCode();
		String applicantName = param.getApplicantUser();
		String applicantUnit = param.getApplicantUnit();
		String item = param.getItem();
		String cerNo = param.getCerNo();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("certCode", certCode);
		params.put("cerNo", cerNo);
		params.put("busiCode", busiCode);
		params.put("deptId", param.getDepartmentId());//事项部门
		params.put("deptCode", cert.getDeptCode());//证件部门编码
		params.put("createType", cert.getType());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("ApplicantName", applicantName);
		dataMap.put("ApplicantUnit", applicantUnit);
		dataMap.put("ApplyCause", item);
		dataMap.put("cerName", param.getCerName());
		dataMap.put("cerNo", cerNo);
		dataMap.put("datas", datas);
		dataMap.putAll(datas.get(0));

		if(cert.getType() == 1) {
			int index = 1;
			for(Map<String,Object> map : datas) {
				for(String key : map.keySet()) {
					dataMap.put(key + "_" + index, map.get(key));
				}
				dataMap.put("id_" + index, index);
				index++;
			}
		}
		
		DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		dataMap.put("UpdateTime", sdf2.format(new Date()));
		dataMap.put("printDate", sdf.format(new Date()));
		dataMap.put("signFlag", Constant.SIGN_FLAG);

		params.put("dataItem", dataMap);
        params.put("certContent", cert.getContent());
        //获取附加信息
        Map<Integer, List<TempAttachmentVo>> attachmentMap = cert.getAttachmentMap();
        if (Detect.notEmpty(attachmentMap)) {
            List<TempAttachmentVo> signVoList = attachmentMap.get(AttachmentType.SEAL.getCode());
            if(Detect.notEmpty(signVoList)) {
                List<SealLogVo> logList = new ArrayList<SealLogVo>();
                for (TempAttachmentVo signVo : signVoList){
                    SealLogVo sealLog = new SealLogVo(param.getDepartmentId(), signVo.getId(),
                            item, cert.getName(), cerNo, busiCode , applicantName);
                    logList.add(sealLog);
                }
                params.put("sealLog", logList);
            }
        }
		
		String result = createCertificateFile(params);
		insert2BigData(result, param.getCerNo(), certCode, busiCode);
		logger.info("【生成PDF end】 cerName:"+cert.getName() + ",sfId:" + param.getCerNo());
		return result;
	}

	@Override
	public String createFileByByte(CertTempVo cert, DataCollectionParam param, byte[] bytes) {
		//String busiCode = param.getBusiCode();
		String certCode = cert.getCode();
		String cerNo = param.getCerNo();
		//String deptCode = cert.getDeptCode();
		String realpath = Constant.REAL_PATH;
		String suffix = "hz_cer_057".equals(certCode) ? ".docx" : ".pdf";//法院判决书为docx
		String outputPath = realpath + File.separator + "certificates" + File.separator + cerNo;
		File outputDir = new File(outputPath);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}
		String fileName = certCode + suffix;
		String outputFile = outputPath + File.separator + fileName;
		String filePreviewName = certCode + "_signed.jpg";
		String outputPreviewFile = outputPath + File.separator + filePreviewName;

		try {
			FileUtils.writeByteArrayToFile(new File(outputFile), bytes);
			//PDFbox.PDF2JPG(outputFile, outputPreviewFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = "";
		try {
			result = FileUtil.encodeBase64File(outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    @Override
    public String createFormPdf(String certCode, String formDataStr) {
        JSONObject formData = JSON.parseObject(formDataStr);
        JSONArray form1 = formData.getJSONArray("form1");
        Map<String, Object> dataMap = new HashMap<>();
        JSONObject formInfo = FormInfoContext.get();
        if(Constant.COMMON_APPLICATION_FORM.equals(certCode)) {
            String formCode = formInfo.getString("FORM_CODE");
            FormPageVo formPage = dataCacheService.getFormPage(formCode);
            dataMap.put("formName", formPage.getName());
            dataMap.put("datas", form1);
        } else {
            for (int i = 0; i < form1.size(); i++) {
                JSONObject jsonObject = form1.getJSONObject(i);
                String name = jsonObject.getString("name");
                Object value = jsonObject.containsKey("value_cn") ? jsonObject.get("value_cn") : jsonObject.get("value");
                dataMap.put(name, value);
            }
            if(formData.size()>1){
				formData.remove("form1");
				int num =2;
				for(int i =0;i<formData.size();i++){
					JSONArray form = formData.getJSONArray("form"+num);
					JSONObject jsonObject = form.getJSONObject(0);
					String name = "form"+num;
					JSONArray autotable = jsonObject.getJSONArray("autotable");

					List<Map<String, Object>> dataList = new ArrayList<>();
					for(int j=0;j<autotable.size();j++){
						JSONArray jsonArray = autotable.getJSONArray(j);
						Map<String, Object> autoMap = new HashMap<>();
						for(int o=0;o<jsonArray.size();o++){
							JSONObject autotableOBJ = jsonArray.getJSONObject(o);
							String autoname = autotableOBJ.getString("name");
							Object value =null;
							if(autotableOBJ.containsKey("value_cn") && autotableOBJ.get("value_cn").toString().split(",").length>0){
								value = autotableOBJ.get("value");
							}else {
								value = autotableOBJ.containsKey("value_cn") ? autotableOBJ.get("value_cn") : autotableOBJ.get("value");
							}
							autoMap.put(autoname,value);
						}
						dataList.add(autoMap);
					}
					dataMap.put(name, dataList);
					num++;
				}
			}
        }
        dataMap.put("formCreated", formInfo.getDate("FORM_CREATED"));
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.TEMPLATES_PATH).append(formInfo.getString("APPLY_SOURCE")).append("/").append(certCode).append(".ftl");
        String templatePath = sb.toString();
		//String jsonString= JSON.toJSONString(dataMap);
        return createPdfByFtl(dataMap, templatePath);
    }

    @Override
    public byte[] addUserSignature(byte[] pdf, String signature) {
	    float x = 0;
        float y = 0;
        float height = 100;
        float width = 100;
        try {
            byte[] img = new BASE64Decoder().decodeBuffer(signature);
            return PdfUtil.addImg2Pdf(pdf, img, x, y, height, width);
        } catch (Exception e) {
            logger.error("添加签名失败", e);
            return pdf;
        }
    }
	
}
