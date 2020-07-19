package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Opinion;
import com.fline.form.access.service.OpinionAccessService;
import com.fline.form.constant.Contants;
import com.fline.form.mgmt.service.CertTempMgmtService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.OpinionMgmtService;
import com.fline.form.util.DataShare;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service("opinionMgmtService")
public class OpinionMgmtServiceImpl implements OpinionMgmtService {
	@Resource
	private OpinionAccessService opinionAccessService;
	@Resource
	private CertTempMgmtService certTempMgmtService;
	@Resource
	private FdpClientMgmtService fdpClientMgmtService;
	
	@Value("${fankui.url}")
	private String fankuiUrl;
	
	@Override
	public Pagination<Opinion> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Opinion> page) {
		return opinionAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Opinion opinion) {
		opinionAccessService.update(opinion);
	}

	@Override
	public void remove(Opinion opinion) {
		opinionAccessService.remove(opinion);
	}

	@Override
	public void create(Opinion opinion) {
		CertTemp certTemp = certTempMgmtService.findByCode(opinion.getCertCode());
		File file = opinion.getFile();
		if(file != null) {
			String key = UUID.randomUUID().toString();
			String fileName = opinion.getCertCode() + "_" + opinion.getSfId() + 
					opinion.getFileName().substring(opinion.getFileName().lastIndexOf("."));
//			fdpClientMgmtService.writeFile(key, key, FileUtil.asBytes(file));
			opinion.setFileKey(key);
			opinion.setFileName(fileName);
		}
		opinionAccessService.create(opinion);
//		if(certTemp.getFeedback() == 1) {
//			sendRequest(opinion, certTemp);
//		}
	}
	
	private void sendRequest(Opinion opinion,CertTemp cert) {
		StringBuilder sb = new StringBuilder();
		sb.append("&problemDesc=" + opinion.getContent());
		sb.append("&itemType=" + opinion.getType());
		sb.append("&params="+opinion.getSfId());
		sb.append("&appName=杭州市一证通办系统");
		sb.append("&linkMan="+ opinion.getUsername());
		sb.append("&appKey="+Contants.APP_KEY);
		sb.append("&interfaceName=123");
		sb.append("&itemName=123");
//		sb.append("&interfaceCode=" + cert.getInterfaceCode());
		sb.append("&linkTel="+opinion.getPhone());
		DataShare.getDatas(fankuiUrl, sb.toString(),"");
	}
	
	@Override
	public Opinion findById(long id) {
		return opinionAccessService.findById(id);
	}
	
	@Override
	public InputStream getExcelFile(Map<String,Object> param){
		List<Opinion> opinions = opinionAccessService.find(param);
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("证件名称");
		row.createCell(1).setCellValue("接口名称");
		row.createCell(2).setCellValue("事项名称");
		row.createCell(3).setCellValue("类别");
		row.createCell(4).setCellValue("反馈问题");
		row.createCell(5).setCellValue("查询身份证");
		row.createCell(6).setCellValue("反馈人姓名");
		row.createCell(7).setCellValue("联系方式");
		row.createCell(8).setCellValue("所属部门");
		row.createCell(9).setCellValue("反馈时间");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0,len=opinions.size();i<len;i++){
			HSSFRow dataRow = sheet.createRow(i+1);
			dataRow.createCell(0).setCellValue(opinions.get(i).getCertName());
			dataRow.createCell(1).setCellValue(opinions.get(i).getInterfaceName());
			dataRow.createCell(2).setCellValue(opinions.get(i).getItemName());
			dataRow.createCell(3).setCellValue(opinions.get(i).getType());
			dataRow.createCell(4).setCellValue(opinions.get(i).getContent());
			dataRow.createCell(5).setCellValue(opinions.get(i).getSfId());
			dataRow.createCell(6).setCellValue(opinions.get(i).getUsername());
			dataRow.createCell(7).setCellValue(opinions.get(i).getPhone());
			dataRow.createCell(8).setCellValue(opinions.get(i).getDeptName());
			dataRow.createCell(9).setCellValue(df.format(opinions.get(i).getCreateDate()));
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			book.write(os);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return new ByteArrayInputStream(os.toByteArray());
	}

}
