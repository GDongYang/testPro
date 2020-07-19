package com.fline.form.mgmt.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface ExcelMgmtService {

	HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb);

}
