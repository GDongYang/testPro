package com.fline.form.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.document.poi.WorkbookUtil;
import com.opensymphony.xwork2.ActionSupport;

public class FileAction extends ActionSupport {

	private static final long serialVersionUID = 7809356924418947728L;

	private String fileName;
	private String result;
	
	private String sql="select t.ECE011 as \"PayDate\", t.ECE012 as \"UnitCode\", b.AAB004 as \"EntName\", a.AAE006 as \"Dom\", "
			+ "t.ECE013 as \"BaseEndowIns\", t.ECE014 as \"PersonalEndoIns\", t.ECE021 as \"UnitEndoIns\",t.ECE015 as \"BaseMedIns\","
			+ "t.ECE016 as \"PersonalMedIns\",t.ECE022 as \"UnitMedIns\",t.ECE017 as \"BaseUnempIns\",t.ECE018 as \"PersonalUnempIns\","
			+ "t.ECE023 as \"UnitUnempIns\",t.ECE019 as \"PayStatus\" ,t.ECE024 as \"BaseEmpInjIns\" ,t.ECE025 as \"UnitEmpInjIns\","
			+ "t.ECE026 as \"BaseMateIns\",t.ECE027 as \"UnitMateIns\",t.ECE028 as \"BaseSeriIllIns\",t.ECE029 as \"UnitSeriIllIns\","
			+ "t.AAE143 as \"PayType\",t.AAC091 as \"ArrivalDate\" "
			+ "from GRCBZM t INNER JOIN V_AC01 v ON v.AAC001=t.AAC001 LEFT JOIN V_AB01 b ON b.AAB001 = t.ECE012 LEFT JOIN V_ABA1 a ON b.AAB001=a.AAB001 "
			+ "WHERE v.AAC002=? order by ECE011 DESC";
	
	private String sql1="select t.ECE011 as \"PayDate\", t.ECE012 as \"UnitCode\", b.AAB004 as \"EntName\", a.AAE006 as \"Dom\", "
			+ "t.AAE143 as \"PayType\",t.AAC091 as \"ArrivalDate\" "
			+ "from GRCBZM t INNER JOIN V_AC01 v ON v.AAC001=t.AAC001 LEFT JOIN V_AB01 b ON b.AAB001 = t.ECE012 LEFT JOIN V_ABA1 a ON b.AAB001=a.AAB001 "
			+ "WHERE v.AAC002=? and t.ECE011=? order by ECE011 DESC";
	
	private String selfSql="select t.ECE001 as \"Name\", t.ECE003 as \"Sex\", t.ECE002 as \"SocialInsNO\", t.ECE052 as \"AttWorkDate\", "
			+ "t.ECE004 as \"CurrInsStatusEndowIns\",t.ECE005 as \"CurrInsStatusMedIns\",t.ECE006 as \"CurrInsStatusEmpInjIns\",t.ECE007 as \"CurrInsStatusMateIns\", "
			+ "t.ECE008 as \"CurrInsStatusUnempIns\", t.ECE050 "
			+ "from GRCBZM1 t, V_AC01 v WHERE v.AAC002=? and v.AAC001=t.AAC001";
	
	private String gssql="select f.CERNO, h.ENTNAME,h.DOM,h.HZRQ from hz_qyhznr h, hz_qyfddbr f where h.PRIPID=f.PRIPID and f.CERNO=?";
	
	private JdbcTemplate rsjJdbcTemplate;
	
	private JdbcTemplate gsjJdbcTemplate;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setRsjJdbcTemplate(JdbcTemplate rsjJdbcTemplate) {
		this.rsjJdbcTemplate = rsjJdbcTemplate;
	}

	public void setGsjJdbcTemplate(JdbcTemplate gsjJdbcTemplate) {
		this.gsjJdbcTemplate = gsjJdbcTemplate;
	}

	public String batchImport1() throws Exception {
		String basePath = ServletActionContext.getServletContext().getRealPath("/");
		String filePath = basePath+"/temp/6.xls";
		HSSFWorkbook book = WorkbookUtil.getWorkbook(new File(filePath));
		HSSFSheet sheet = book.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		List<List<?>> datas = new ArrayList<List<?>>();
		for (int ii = 0; ii < rowNum; ii++) {
			HSSFRow row = sheet.getRow(ii);
			if (row == null) {
				continue;
			}
			
			List<Object> data = new ArrayList<Object>();
			int cellNum = row.getLastCellNum();
			for (int jj=0; jj<cellNum; jj++) {
				HSSFCell cell = row.getCell(jj);
				if (cell == null) {
					data.add("");
				}
				else {
					data.add(cell.getStringCellValue());
				}
			}
			
			if (ii != 0 && row.getCell(4) != null) {
				String cerNo = row.getCell(4).getStringCellValue();
				List<Map<String, Object>> estates = gsjJdbcTemplate.queryForList(gssql, cerNo);
				if (Detect.notEmpty(estates)) {
					for (Map<String, Object> estate : estates) {
						data.add(estate.get("ENTNAME"));
						data.add(estate.get("DOM"));
						data.add(estate.get("HZRQ"));
					}
				}
			}
			
			datas.add(data);
		}

	    HSSFWorkbook target = WorkbookUtil.createWorkbook(datas);
	    String fileTargetPath = basePath+"/temp/6-complete.xls";
	    File targetFile = new File(fileTargetPath);
	    BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(targetFile));
	    bo.write(WorkbookUtil.marshall(target));
	    bo.close();
		return SUCCESS;
	}
	
	public String batchImport2() throws Exception {
		String basePath = ServletActionContext.getServletContext().getRealPath("/");
		String filePath = basePath+"/temp/8.xls";
		HSSFWorkbook book = WorkbookUtil.getWorkbook(new File(filePath));
		HSSFSheet sheet = book.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		List<List<?>> datas = new ArrayList<List<?>>();
		for (int ii = 0; ii < rowNum; ii++) {
			HSSFRow row = sheet.getRow(ii);
			if (row == null) {
				continue;
			}
			
			List<Object> data = new ArrayList<Object>();
			int cellNum = row.getLastCellNum();
			for (int jj=0; jj<cellNum; jj++) {
				HSSFCell cell = row.getCell(jj);
				if (cell == null) {
					data.add("");
				}
				else {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						data.add(cell.getStringCellValue());
					}
					else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						data.add(cell.getNumericCellValue());
					}
				}
			}
			
			if (ii != 0) {
				if (row.getCell(3) != null) {
					String cerNo = row.getCell(3).getStringCellValue();
					List<Map<String, Object>> estates = rsjJdbcTemplate.queryForList(sql, cerNo);
					if (Detect.notEmpty(estates)) {
						Map<String, Object> estate = estates.get(0);
						
//						data.add(estate.get("PayDate"));
						List<Map<String, Object>> estates1 = rsjJdbcTemplate.queryForList(sql1, cerNo, "201701");
						if (Detect.notEmpty(estates1)) {
							data.add(estates1.get(0).get("PayType"));
						}
						data.add(estate.get("EntName"));
						data.add(estate.get("Dom"));
//						List<Map<String, Object>> selfInfos = rsjJdbcTemplate.queryForList(selfSql, cerNo);
//						if (Detect.notEmpty(selfInfos)) {
//							Map<String, Object> selfInfo = selfInfos.get(0);
//							data.add(selfInfo.get("ECE050"));
//						}
						
						
						if (estates.size() == 1) {
							data.add(estate.get("PayDate"));
							data.add(1);
						}
						else {
							int k = 1;
							int jj=1; 
							for (;jj<estates.size(); jj++ ) {
								if (estates.get(0).get("EntName").equals(estates.get(jj).get("EntName"))) {
									if (!estates.get(jj - 1).get("PayDate").equals(estates.get(jj).get("PayDate"))) {
										k++;
									}
								}
								else {
									break;
								}
							}
							data.add(estates.get(jj - 1).get("PayDate"));
							data.add(k);
						}
					}
				}
				
//				if (row.getCell(5) != null) {
//					String cerNo = row.getCell(5).getStringCellValue();
//					List<Map<String, Object>> estates = rsjJdbcTemplate.queryForList(sql, cerNo);
//					if (Detect.notEmpty(estates)) {
//						Map<String, Object> estate = estates.get(0);
//						data.add(estate.get("PayType"));
//						data.add(estate.get("EntName"));
//						data.add(estate.get("Dom"));
//						data.add(estate.get("PayDate"));
//					}
//				}
				
			}
			
			datas.add(data);
		}

	    HSSFWorkbook target = WorkbookUtil.createWorkbook(datas);
	    String fileTargetPath = basePath+"/temp/8-complete.xls";
	    File targetFile = new File(fileTargetPath);
	    BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(targetFile));
	    bo.write(WorkbookUtil.marshall(target));
	    bo.close();
		return SUCCESS;
	}

}
