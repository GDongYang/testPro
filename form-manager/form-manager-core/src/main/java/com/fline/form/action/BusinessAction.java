package com.fline.form.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Business;
import com.fline.form.access.model.BusinessItem;
import com.fline.form.access.service.BusinessAccessService;
import com.fline.form.mgmt.service.BusinessItemMgmtService;
import com.fline.form.mgmt.service.BusinessMgmtService;
import com.fline.form.mgmt.service.ExcelMgmtService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.vo.ItemCount;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BusinessAction extends AbstractAction implements ModelDriven<Business>{
	
	private static final long serialVersionUID = -8822833134730954352L;
	private BusinessMgmtService businessMgmtService;
	private BusinessItemMgmtService businessItemMgmtService;
	private FdpClientMgmtService fdpClientMgmtService;
	private BusinessAccessService businessAccessService;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private Business business;
	private int pageNum;
	private int pageSize;
	private long businessId;
	private int week;
	private int days;
	private int city;
	
	private String certcode[];
	private String itemcode[];
	
	private String allData;
	private String findItemCode;
	//将long改成Long，避免department不传值的时候，报错
	private Long department;
	private String queryDate;
	private String fileName;
	private InputStream excelStream;
	private int downloadType;
	private String inspection;
	private String sql;
	//测试使用 待删除
	private JSONArray queryDataFromSolr;
	private String certNo;
	private String cerno;
	private String certNum;
	private String startDate;
	private String endDate;
	private String ztStatus;
	private int total;
	OutputStream out = null;
	
	
	@Autowired
	private ExcelMgmtService excelMgmtService;
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getZtStatus() {
		return ztStatus;
	}

	public void setZtStatus(String ztStatus) {
		this.ztStatus = ztStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getCertcode() {
		return certcode;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String cerNum) {
		this.certNum = cerNum;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getInspection() {
		return inspection;
	}
	public void setInspection(String inspection) {
		this.inspection = inspection;
	}
	public int getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(int downloadType) {
		this.downloadType = downloadType;
	}
//	public long getDepartment() {
//		return department;
//	}
//	public void setDepartment(long department) {
//		this.department = department;
//	}
	
	public Long getDepartment() {
		return department;
	}

	public String getQueryDate() {
		return queryDate;
	}
	public void setDepartment(Long department) {
		this.department = department;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public void setBusinessAccessService(BusinessAccessService businessAccessService) {
		this.businessAccessService = businessAccessService;
	}
	
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	
	public void setFdpClientMgmtService(FdpClientMgmtService fdpClientMgmtService) {
		this.fdpClientMgmtService = fdpClientMgmtService;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public void setAllData(String allData) {
		this.allData = allData;
	}

	public void setFindItemCode(String findItemCode) {
		this.findItemCode = findItemCode;
	}


	public String findPage() {
		Pagination<Business> page = new Pagination<Business>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Ordering order = new Ordering();
		order.addDesc("createDate");
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(business.getCerno())){
			param.put("cerno", business.getCerno());
		}
		if(business.getDepartmentId() != 0){
			param.put("deptId", business.getDepartmentId());
		}
		if(business.getType() != 0) {
			param.put("type", business.getType());
		}
		if (!StringUtils.isEmpty(queryDate)) {
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			String[] applyTimes = queryDate.split("-");
			try {
				Date startDate = format.parse(StringUtils.trim(applyTimes[0]));
				Date stopDate = format.parse(StringUtils.trim(applyTimes[1]));
				
				param.put("startDate", StringUtils.trim(new SimpleDateFormat("yyyy-MM-dd").format(startDate)));
				param.put("stopDate",  StringUtils.trim(new SimpleDateFormat("yyyy-MM-dd").format(stopDate)));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		page = businessMgmtService.findPagination(param, order, page);
		dataMap.put("rows", page.getItems());
		dataMap.put("total",page.getCount());
		return SUCCESS;
	}
	
	public String businessToExcel() {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String code = codeString(
					Business.class.getClassLoader().getResource("applicationContext.properties").getFile());
			if (downloadType == 3) {
				this.fileName = "业务日志" + sdf.format(new Date()) + ".xls";
				excelStream = getExcelFile();
			} else if (downloadType == 2) {
				this.fileName = "事项统计" + sdf.format(new Date()) + ".xls";
				excelStream = getExcelFile2();
			} else {
				this.fileName = "证件统计" + sdf.format(new Date()) + ".xls";
				excelStream = getExcelFile3();
			}
			fileName = new String(fileName.getBytes(code), "ISO8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String codeString(String file) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}
		bin.close();
		return code;
	}
	
	public String findTempCount(){
		Pagination<Business> page = new Pagination<Business>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Ordering order = new Ordering("ASK_DATE","DESC");
		order.addDesc("ACCOUNT_CERT");
		Map<String,Object> param = new HashMap<String,Object>();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		if (!StringUtils.isEmpty(queryDate)) {
			String[] applyTimes = queryDate.split("-");
			try {
				Date startDate = format.parse(StringUtils.trim(applyTimes[0]));
				Date stopDate = format.parse(StringUtils.trim(applyTimes[1]));
				
				param.put("startDate", StringUtils.trim(new SimpleDateFormat("yyyy-MM-dd").format(startDate)));
				param.put("stopDate",  StringUtils.trim(new SimpleDateFormat("yyyy-MM-dd").format(stopDate)));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(certcode != null && certcode.length > 0){
			if(!"".equals(certcode[0])){
				param.put("certTempCode",certcode);
			}
		}
		if(itemcode != null && itemcode.length > 0){
			if(!"".equals(itemcode[0])){
				param.put("itemCode",itemcode);
			}
		}
		if(department != null && department > 0){
			param.put("departmentId",department);
		}
		page = businessMgmtService.findTempCount(param, order, page);
		dataMap.put("rows", page.getItems());
		dataMap.put("total", page.getCount());
		return SUCCESS;
	}
	public String findItemPage() {
		Pagination<BusinessItem> page = new Pagination<BusinessItem>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Ordering order = new Ordering();
		order.addDesc("ID");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("businessId", businessId);
		if(Detect.notEmpty(inspection)) {
			param.put("inspection", inspection);
		}
		page = businessItemMgmtService.findPagination(param, order, page);
		dataMap.put("rows", page.getItems());
		dataMap.put("total", page.getCount());
		return SUCCESS;
	}
	/**
	 * 累计服务事项请求
	 * @return
	 */
	public String cumulativeTempItemRequest(){
		List<Map<String, Object>> mapObj= businessMgmtService.cumulativeTempItemRequest();
		int count = businessMgmtService.count();
		dataMap.put("count", count);
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	
	/**
	 * 服务事项统计
	 * @return
	 */
	public String serviceItemCount(){
		Map<String, Object> params = new HashMap<>();
		//params.put("forCache", true);
		List<Map<String, Object>> mapObj= businessMgmtService.serviceItemCount(params);
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	
	/**
	 * 当天事项请求统计（圆形分布图）
	 * @return
	 */
	public String dayItemCount(){
		List<Map<String, Object>> mapObj= businessMgmtService.dayItemCount();
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	
	/**
	 * 按月事项请求统计（柱形分布图）
	 * @return
	 */
	public String monthItemCount(){
		List<Map<String, Object>> mapObj= businessMgmtService.monthItemCount();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(int i=1;i<13;i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("months", i);
			map.put("monthCount", 0);
			for(Map<String, Object> m : mapObj) {
				String s = "0" + i;
				if(s.substring(s.length()-2).equals(m.get("months"))) {
					map.put("monthCount", m.get("monthCount"));
					mapObj.remove(m);
					break;
				}
			}
			result.add(map);
		}
		dataMap.put("result", result);
		return SUCCESS;
	}
	
	//按月统计证件数量
	public String monthCerNoCount(){
		List<Map<String, Object>> mapObj= businessMgmtService.monthCerNoCount();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(int i=1;i<13;i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("months", i);
			map.put("monthCount", 0);
			for(Map<String, Object> m : mapObj) {
				String s = "0" + i;
				if(s.substring(s.length()-2).equals(m.get("months"))) {
					map.put("monthCount", m.get("monthCount"));
					mapObj.remove(m);
					break;
				}
			}
			result.add(map);
		}
		dataMap.put("result", result);
		return SUCCESS;
	}
	
	/**
	 * 按周事项请求统计（曲线形分布图）
	 * @return
	 */
	public String weekItemCount(){
		Map<String,Object> param = new HashMap<String,Object>();
		Calendar cal = Calendar.getInstance();
	    cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, 7*(1-week));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		param.put("date", cal.getTime());
		List<Map<String, Object>> mapObj= businessMgmtService.weekItemCount(param);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		for(int i=2;i<9;i++) {
			if(i == 8){
				cal.set(Calendar.DAY_OF_WEEK, 1);
			} else {
				cal.set(Calendar.DAY_OF_WEEK, i);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(cal.getTime());
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("weekDay", date);
			m.put("weekCount", 0);
			for(Map<String, Object> map :mapObj){
				if(date.equals(map.get("weekDay"))){
					m.put("weekCount",map.get("weekCount"));
					mapObj.remove(map);
					break;
				}
			}
			result.add(m);
		}
		dataMap.put("result", result);
		return SUCCESS;
	}
	
	/**
	 * 部门办件排名
	 * @return
	 */
	public String deptItemCount(){
		//List<Map<String, Object>> mapObj= businessAccessService.deptItemCount(business.getType(),city);
		Map<String,Object> params = new HashMap<>();
		params.put("type", business.getType());
		//params.put("forCache", true);
		Map<String, Object> result = businessMgmtService.deptItemCount(params);
		dataMap.put("result", result.get("result"));
		dataMap.put("allData",result.get("allData"));
		return SUCCESS;
	}
	
	/**
	 * 区县办件排名
	 * @return
	 */
	public String cityItemCount(){
		Map<String, Object> params = new HashMap<>();
		params.put("type", business.getType());
		List<Map<String, Object>> mapObj = businessMgmtService.cityItemCount(params);
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	/**
	 * @Description:  获取办件事项排名
	 * @return 各办件事项的数量
	 */
	public String itemCountRank() {
		Map<String, Object> params = new HashMap<>();
		params.put("type", business.getType());
		Map<String, Object> mapObj = businessMgmtService.itemCountRank(params);
		
		dataMap.put("result", mapObj.get("results"));
		dataMap.put("allData",mapObj.get("allData"));
		return SUCCESS;
	}
	
	/**
	 * 市本级当日饼图
	 * @return
	 */
	public String deptItemCountByDay(){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("days", days);
		List<Map<String, Object>> mapObj= businessMgmtService.deptItemCountByDay(params);
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	
	/**
	 * 事项折线图:周 月 半个月的统计
	 * @return
	 */
	public String dayItemCountChange(){
		Map<String,Object> params = new HashMap<>();
		params.put("days", days);
		params.put("forCache", true);
		dataMap = businessMgmtService.dayItemCountChange(params);
		return SUCCESS;
	}
	
	/**
	 * 民生事项部署情况
	 * @return
	 */
	public String implementedItem(){
		List<Map<String, Object>> mapObj= businessAccessService.implementedItem();
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	
	/**
	 * 证件查询统计
	 * @return
	 */
	public String dayTempCount(){
		List<Map<String, Object>> mapObj= businessMgmtService.dayTempCount();
		dataMap.put("result", mapObj);
		return SUCCESS;
	}
	
	public InputStream getExcelFile(){
		if(!SUCCESS.equals(findTempCount())) {
			return null;
		}
//		Pagination<Business> page =(Pagination<Business>)dataMap.get("page");
//
//		if(page==null)
//			return null;
		List<Business> businesses = (List<Business>) dataMap.get("rows");
		if(businesses==null) {
			return null;
		}
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("部门名称");
		row.createCell(1).setCellValue("证件名称");
		//row.createCell(2).setCellValue("日期");
		row.createCell(2).setCellValue("数量");
		long count = 0;
		for(int i=0,len=businesses.size();i<len;i++){
			HSSFRow dataRow = sheet.createRow(i+1);
			dataRow.createCell(0).setCellValue(businesses.get(i).getDepartmentName());
			dataRow.createCell(1).setCellValue(businesses.get(i).getItemName());
//			if(businesses.get(i).getAskDate() != null) {
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				dataRow.createCell(2).setCellValue(df.format(businesses.get(i).getAskDate()));
//			} else {
//				dataRow.createCell(2).setCellValue("");
//			}
			dataRow.createCell(2).setCellValue(businesses.get(i).getCertTempCount());
			count += businesses.get(i).getCertTempCount();
		}
		HSSFRow lastRow = sheet.createRow(sheet.getLastRowNum()+1);
		lastRow.createCell(0).setCellValue("总计");
		lastRow.createCell(2).setCellValue(count);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			book.write(os);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	public String deleteTest() {
		String result = businessMgmtService.deleteTest();
		dataMap.put("resultMsg", result);
		return SUCCESS;
	}
	
	public String findStaticFromSolr() {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (certcode != null && certcode.length > 0) {
			if (!"".equals(certcode[0])) {
				param.put("certcode", certcode[0]);
			}
		}
		if (Detect.notEmpty(certNo)) {
			param.put("certNo", certNo);
		}
		if (Detect.notEmpty(ztStatus)) {
			param.put("status", ztStatus);
		}
		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}
		dataMap = businessMgmtService.findStaticFromSolr(param);

		return SUCCESS;
	}

	public String findItemCount() {
		Map<String,Object> param = new HashMap<String,Object>();
		if(Detect.notEmpty(business.getItemName())) {
			param.put("itemName", business.getItemName());
		}
		if (null != business.getDepartmentId() && business.getDepartmentId() != 0) {
			param.put("departmentId", business.getDepartmentId());
		}
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(certNum)) {
			param.put("certNum", certNum);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}
		dataMap = this.businessMgmtService.findItemCountBySolr(param);
		return SUCCESS;
	}
	
	public String findBusinessMonitor() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (Detect.notEmpty(business.getItemName())) {
			param.put("itemName", business.getItemName());
		}
		if (null != business.getDepartmentId() && business.getDepartmentId() != 0) {
			param.put("departmentId", business.getDepartmentId());
		}
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(certNum)) {
			param.put("certNum", certNum);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}
		dataMap = this.businessMgmtService.findBusinessMonitor(param);
		return SUCCESS;
	}

	public InputStream getExcelFile2(){
		if(!SUCCESS.equals(findItemCount())) {
			return null;
		}
//		Pagination<ItemCount> page =(Pagination<ItemCount>)dataMap.get("page");
//
//		if(page==null)
//			return null;
		List<ItemCount> itemCounts = (List<ItemCount>) dataMap.get("rows");
		if(itemCounts==null) {
			return null;
		}
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("部门名称");
		row.createCell(1).setCellValue("事项名称");
		row.createCell(2).setCellValue("数量");
		row.createCell(3).setCellValue("地区");
		long count = 0;
		for(int i=0,len=itemCounts.size();i<len;i++){
			HSSFRow dataRow = sheet.createRow(i+1);
			dataRow.createCell(0).setCellValue(itemCounts.get(i).getDeptName());
			dataRow.createCell(1).setCellValue(itemCounts.get(i).getItemName());
			dataRow.createCell(2).setCellValue(itemCounts.get(i).getNumber());
			dataRow.createCell(3).setCellValue(itemCounts.get(i).getArea());
			count += itemCounts.get(i).getNumber();
		}
		HSSFRow lastRow = sheet.createRow(sheet.getLastRowNum()+1);
		lastRow.createCell(0).setCellValue("总计");
		lastRow.createCell(2).setCellValue(count);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			book.write(os);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	public InputStream getExcelFile3() {
		if(!SUCCESS.equals(findPage())) {
			return null;
		}
//		Pagination<Business> page =(Pagination<Business>)dataMap.get("page");
//
//		if(page==null)
//			return null;
		List<Business> itemCounts = (List<Business>) dataMap.get("rows");
		if(itemCounts==null) {
			return null;
		}
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("事项名称");
		row.createCell(1).setCellValue("事项编码");
		row.createCell(2).setCellValue("被查询人姓名");
		row.createCell(3).setCellValue("身份证号码");
		row.createCell(4).setCellValue("申请部门");
		row.createCell(5).setCellValue("业务账号");
		row.createCell(6).setCellValue("时间");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0,len=itemCounts.size();i<len;i++){
			HSSFRow dataRow = sheet.createRow(i+1);
			dataRow.createCell(0).setCellValue(itemCounts.get(i).getItemName());
			dataRow.createCell(1).setCellValue(itemCounts.get(i).getItemCode());
			dataRow.createCell(2).setCellValue(itemCounts.get(i).getCerName());
			dataRow.createCell(3).setCellValue(itemCounts.get(i).getCerno());
			dataRow.createCell(4).setCellValue(itemCounts.get(i).getDepartmentName());
			String username = itemCounts.get(i).getUserName();
			if(!Detect.notEmpty(username)) {
				username = itemCounts.get(i).getAccountName();
			}
			dataRow.createCell(5).setCellValue(username);
			dataRow.createCell(6).setCellValue(df.format(itemCounts.get(i).getCreateDate()));
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			book.write(os);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}

	public String testSql() {
		System.out.println("sql is " + sql);
		JSONArray queryDataFromSolr = fdpClientMgmtService.queryDataFromSolr(sql);
		dataMap.put("result", queryDataFromSolr.toString());
		dataMap.put("data", queryDataFromSolr);
		dataMap.put("msg", "ok");
		return SUCCESS;
	}
	public void setBusinessMgmtService(BusinessMgmtService businessMgmtService) {
		this.businessMgmtService = businessMgmtService;
	}
	
	public void setBusinessItemMgmtService(BusinessItemMgmtService businessItemMgmtService) {
		this.businessItemMgmtService = businessItemMgmtService;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}
	
	@Override
	public Business getModel() {
		if(business == null) {
			business= new Business();
		}
		return business;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}
	
	public void setCertcode(String[] certcode) {
		this.certcode = certcode;
	}
	public void setItemcode(String[] itemcode) {
		this.itemcode = itemcode;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	
	public String refreshAllCache() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("forCache", true);
		businessMgmtService.deptItemCountByDay(params);//饼图：统计本周事项办件量分布
		
		params.put("days", 7);//折线图统计本周每天的办件量
		businessMgmtService.dayItemCountChange(params);
		params.put("days", 30);//折线图统计本月每天的办件量
		businessMgmtService.dayItemCountChange(params);
		
		params.put("type", 0);
		businessMgmtService.deptItemCount(params);//部门排名图：全量
		params.put("type", 1);
		businessMgmtService.deptItemCount(params);//部门排名图：本月
		
		businessMgmtService.cityItemCount(params);//地区排名图：本月
		params.put("type", 0);
		businessMgmtService.cityItemCount(params);//地区排名图：全量
		
		businessMgmtService.serviceItemCount(params);//证明总数量
		businessMgmtService.serviceItemCount(params);//事项总数量
		return SUCCESS;
	}

	public String findBySql(){
		// sql = "";
		Map<String, Object> resultMap = new HashMap<>();
		resultMap = businessMgmtService.findBySql(sql);
		String resultJSON = JSONObject.toJSONString(resultMap.get("rows"));
		dataMap.put("result", resultJSON);
		dataMap.put("total", resultMap.get("total"));
		return SUCCESS;
	}
	/**
	 * @Description: 指定事项 办件部门的分布 
	 * @return String
	 */
	public String findDeptItemCountByCode() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("allDataStr", allData);
		params.put("itemCode", findItemCode);
		Map<String, Object> result = businessMgmtService.findDeptItemCountByCode(params);
		dataMap.put("result", result);
		return SUCCESS;
	}
	/**
	 * @Description: 指定部门办件事项的分布
	 * @return String
	 */
	public String findItemCountByDept() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("allDataStr", allData);
		params.put("departmentId", department);
		Map<String, Object> result = businessMgmtService.findItemCountByDept(params);
		dataMap.put("result", result);
		return SUCCESS;
	}
}
