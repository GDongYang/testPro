package com.fline.form.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.BusinessMgmtService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.util.ExportExcelSeedBack;
import com.fline.form.vo.BusinessVo;
import com.fline.form.vo.FormDataSolrVo;
import com.fline.form.vo.FormInfoVo;
import com.fline.form.vo.ProjectInfoVo;
import com.fline.form.vo.SmsVo;
import com.fline.form.vo.StaticVo;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	@Autowired
	private BusinessMgmtService businessMgmtService;
	@Autowired
	private FdpClientMgmtService fdpClientMgmtService;

	@RequestMapping(value = "/exportStaticExcel", method = RequestMethod.GET)
	public void exportStaticExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "certNo", required = true) String certNo,
			@RequestParam(value = "pageNum", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "certcode", required = true) String certcode[],
			@RequestParam(value = "ztStatus", required = true) String ztStatus,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "total", required = true) String total) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		String sqlSolr = "select * from yztbbusinessitemtable where";
		StringBuffer sb = new StringBuffer(80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (certcode != null && certcode.length > 0) {
			if (!"".equals(certcode[0])) {
				sb.append("and certCode='" + certcode[0] + "'");
			}
		}
		if (Detect.notEmpty(certNo)) {
			sb.append("and cerno = '" + certNo + "'");
		}
		if (Detect.notEmpty(ztStatus)) {
			sb.append("and status='" + ztStatus + "'");
		}
		if (Detect.notEmpty(startDate)) {
			sb.append("and createDate > {ts '" + startDate + "'} ");
			sb.append("and createDate < {ts '" + endDate + "'}");
		}

		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by createDate desc limit  " + 0 + "," + total);

		String sql = sqlSolr + sb.toString().substring(3);
		// 从solr获取数据
		JSONArray jsonArray = fdpClientMgmtService.queryDataFromSolr(sql);
		List<StaticVo> grouppList = JSONArray.parseArray(jsonArray.toJSONString(), StaticVo.class);
		// 导出文件的标题
		String title = "证件统计" + System.currentTimeMillis() + ".xls";
		// 设置表格标题行
		String[] headers = new String[] { "序号", "状态", "事项名称", "证件号", "证件编码", "证件名称", "耗时", "编码", "创建时间", "返回信息",
				"调用账号信息" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		if (grouppList != null) {
			for (int i = 0; i < grouppList.size(); i++) {
				objs = new Object[headers.length];
				objs[0] = 0;// 设置序号,在工具类中会出现
				objs[1] = grouppList.get(i).getStatus();
				objs[2] = grouppList.get(i).getItemName();
				objs[3] = grouppList.get(i).getCerNo();
				objs[4] = grouppList.get(i).getCertCode();
				objs[5] = grouppList.get(i).getCertName();
				objs[6] = grouppList.get(i).getTimeConsuming();
				objs[7] = grouppList.get(i).getCode();
				objs[8] = sdf.format(grouppList.get(i).getCreateDate());
				objs[9] = grouppList.get(i).getMessage();
				objs[10] = grouppList.get(i).getAccountName();
				dataList.add(objs);// 数据添加到excel表格
			}
		}
		// 使用流将数据导出
		OutputStream out = null;
		try {
			// 防止中文乱码
			String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
			response.setContentType("octets/stream");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			out = response.getOutputStream();
			ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);// 没有标题
			ex.export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportBusinessMonitorExcel", method = RequestMethod.GET)
	public void exportBusinessMonitorExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "certNum", required = true) String certNum,
			@RequestParam(value = "departmentId", required = true) String departmentId,
			@RequestParam(value = "pageNum", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "total", required = true) String total) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		String sqlSolr = "select * from yztbbusinesstable where";
		StringBuffer sb = new StringBuffer(80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (Detect.notEmpty(certNum)) {
			sb.append("and certNum = '" + certNum + "'");
		}
		if (Detect.notEmpty(departmentId)) {
			sb.append("and departmentId = '" + departmentId + "'");
		}
		if (Detect.notEmpty(startDate)) {
			sb.append("and createDate > {ts '" + startDate + "'} ");
			sb.append("and createDate < {ts '" + endDate + "'}");
		}

		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by createDate desc limit  " + 0 + "," + total);

		String sql = sqlSolr + sb.toString().substring(3);
		// 从solr获取数据
		JSONArray jsonArray = fdpClientMgmtService.queryDataFromSolr(sql);
		List<BusinessVo> grouppList = JSONArray.parseArray(jsonArray.toJSONString(), BusinessVo.class);
		// 导出文件的标题
		String title = "业务日志" + System.currentTimeMillis() + ".xls";
		// 设置表格标题行
		String[] headers = new String[] { "序号", "事项编码", "事项名称", "被查询人姓名", "身份证号码", "办事人员", "创建时间", "办事部门", "访问IP" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		if (grouppList != null) {
			for (int i = 0; i < grouppList.size(); i++) {
				objs = new Object[headers.length];
				objs[0] = 0;// 设置序号,在工具类中会出现
				objs[1] = grouppList.get(i).getItemCode();
				objs[2] = grouppList.get(i).getItemName();
				objs[3] = grouppList.get(i).getCerName();
				objs[4] = grouppList.get(i).getCerno();
				objs[5] = grouppList.get(i).getAccountName();
				objs[6] = sdf.format(grouppList.get(i).getCreateDate());
				objs[7] = grouppList.get(i).getDepartmentName();
				objs[8] = grouppList.get(i).getAccessIP();
				dataList.add(objs);// 数据添加到excel表格
			}
		}
		// 使用流将数据导出
		OutputStream out = null;
		try {
			// 防止中文乱码
			String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
			response.setContentType("octets/stream");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			out = response.getOutputStream();
			ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);// 没有标题
			ex.export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportFormDataExcel", method = RequestMethod.GET)
	public void exportFormDataExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "formBusinessCode", required = true) String formBusinessCode,
			@RequestParam(value = "certNum", required = true) String certNum,
			@RequestParam(value = "pageNum", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "total", required = true) String total) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		String sqlSolr = "select DATA_CREATED, SFZHM, FORM_CODE, ITEM_CODE, FORM_BUSINESS_CODE, DATA_BUSINESS_CODE "
				+ "APPLY_CARD_TYPE, APPLY_CARD_NUMBER from form_data where  ";
		StringBuffer sb = new StringBuffer(80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (Detect.notEmpty(certNum)) {
			sb.append("and SFZHM = '" + certNum + "'");
		}
		if (Detect.notEmpty(formBusinessCode)) {
			sb.append("and FORM_BUSINESS_CODE = '" + formBusinessCode + "'");
		}
		if (Detect.notEmpty(startDate)) {
			sb.append("and DATA_CREATED > {ts '" + startDate + "'} ");
			sb.append("and DATA_CREATED < {ts '" + endDate + "'}");
		}

		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by DATA_CREATED desc limit  " + 0 + "," + total);

		String sql = sqlSolr + sb.toString().substring(3);
		// 从solr获取数据
		JSONArray jsonArray = fdpClientMgmtService.queryDataFromFormSolrDb(sql);
		List<FormDataSolrVo> grouppList = JSONArray.parseArray(jsonArray.toJSONString(), FormDataSolrVo.class);
		// 导出文件的标题
		String title = "表单数据" + System.currentTimeMillis() + ".xls";
		// 设置表格标题行
		String[] headers = new String[] { "序号", "表单编码", "事项编码", "表单流水号", "数据流水号", "表单时间", "身份证号码",
				"证件类型", "证件号码" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		if (grouppList != null) {
			for (int i = 0; i < grouppList.size(); i++) {
				objs = new Object[headers.length];
				objs[0] = 0;// 设置序号,在工具类中会出现
				objs[1] = grouppList.get(i).getFormCode();
				objs[2] = grouppList.get(i).getItemCode();
				objs[3] = grouppList.get(i).getFormBusinessCode();
				objs[4] = grouppList.get(i).getDataBusinessCode();
				objs[5] = grouppList.get(i).getDataCreated() == null ? ""
						: sdf.format(grouppList.get(i).getDataCreated());
				objs[6] = grouppList.get(i).getSfzhm() == null ? "" : grouppList.get(i).getSfzhm();
				objs[7] = grouppList.get(i).getApplyCardNumber();
				objs[8] = grouppList.get(i).getApplyCardNumber();
				// objs[9] = grouppList.get(i).getFormContent();
				// objs[10] = grouppList.get(i).getDataDigest();
				dataList.add(objs);// 数据添加到excel表格
			}
		}
		// 使用流将数据导出
		OutputStream out = null;
		try {
			// 防止中文乱码
			String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
			response.setContentType("octets/stream");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			out = response.getOutputStream();
			ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);// 没有标题
			ex.export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportFormInfoExcel", method = RequestMethod.GET)
	public void exportFormInfoExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "formCode", required = true) String formCode,
			@RequestParam(value = "certNum", required = true) String certNum,
			@RequestParam(value = "pageNum", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "total", required = true) String total) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		String sqlSolr = "select * from form_info where";
		StringBuffer sb = new StringBuffer(80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (Detect.notEmpty(certNum)) {
			sb.append("and APPLY_CARD_NUMBER = '" + certNum + "'");
		}
		if (Detect.notEmpty(formCode)) {
			sb.append("and FORM_CODE = '" + formCode + "'");
		}
		if (Detect.notEmpty(startDate)) {
			sb.append("and FORM_CREATED > {ts '" + startDate + "'} ");
			sb.append("and FORM_CREATED < {ts '" + endDate + "'}");
		}

		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by FORM_CREATED desc limit  " + 0 + "," + total);

		String sql = sqlSolr + sb.toString().substring(3);
		// 从solr获取数据
		JSONArray jsonArray = fdpClientMgmtService.queryDataFromFormSolrDb(sql);
		List<FormInfoVo> grouppList = JSONArray.parseArray(jsonArray.toJSONString(), FormInfoVo.class);
		// 导出文件的标题
		String title = "表单中心" + System.currentTimeMillis() + ".xls";
		// 设置表格标题行
		String[] headers = new String[] { "序号", "申请人姓名", "申请人唯一标识", "申请人证件号码", "表单编码", "事项编码", "表单业务流水号", "表单创建日期",
				"表单URL", "联系方式", "申请人证件类型", "表单创建人", "场景编码", "表单版本", "部门ID", "地区编码", "部门编码" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		if (grouppList != null) {
			for (int i = 0; i < grouppList.size(); i++) {
				objs = new Object[headers.length];
				objs[0] = 0;// 设置序号,在工具类中会出现
				objs[1] = grouppList.get(i).getApplyName();
				objs[2] = grouppList.get(i).getApplicantUid();
				objs[3] = grouppList.get(i).getApplyCardNumber();
				objs[4] = grouppList.get(i).getFormCode();
				objs[5] = grouppList.get(i).getItemCode();
				objs[6] = grouppList.get(i).getFormBusinessCode();
				objs[7] = grouppList.get(i).getFormCreated() == null ? ""
						: sdf.format(grouppList.get(i).getFormCreated());
				objs[8] = grouppList.get(i).getFormUrl();
				objs[9] = grouppList.get(i).getApplyMobile();
				objs[10] = grouppList.get(i).getApplyCardType();
				objs[11] = grouppList.get(i).getFormCreator();
				objs[12] = grouppList.get(i).getApplyScena();
				objs[13] = grouppList.get(i).getFormVersion();
				objs[14] = grouppList.get(i).getDeptId();
				objs[15] = grouppList.get(i).getAreaCode();
				objs[16] = grouppList.get(i).getDeptCode();
				dataList.add(objs);// 数据添加到excel表格
			}
		}
		// 使用流将数据导出
		OutputStream out = null;
		try {
			// 防止中文乱码
			String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
			response.setContentType("octets/stream");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			out = response.getOutputStream();
			ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);// 没有标题
			ex.export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportProjectInfoExcel", method = RequestMethod.GET)
	public void exportProjectInfoExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "itemNum", required = true) String itemNum,
			@RequestParam(value = "certNum", required = true) String certNum,
			@RequestParam(value = "pageNum", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "total", required = true) String total) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		String sqlSolr = "select * from project_info where";
		StringBuffer sb = new StringBuffer(80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (Detect.notEmpty(certNum)) {
			sb.append("and certNum = '" + certNum + "'");
		}
		if (Detect.notEmpty(itemNum)) {
			sb.append("and itemNum = '" + itemNum + "'");
		}
		if (Detect.notEmpty(startDate)) {
			sb.append("and createTime > {ts '" + startDate + "'} ");
			sb.append("and createTime < {ts '" + endDate + "'}");
		}

		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by createTime desc limit  " + 0 + "," + total);

		String sql = sqlSolr + sb.toString().substring(3);
		// 从solr获取数据
		JSONArray jsonArray = fdpClientMgmtService.queryDataFromFormSolrDb(sql);
		List<ProjectInfoVo> grouppList = JSONArray.parseArray(jsonArray.toJSONString(), ProjectInfoVo.class);
		// 导出文件的标题
		String title = "办件信息" + System.currentTimeMillis() + ".xls";
		// 设置表格标题行
		String[] headers = new String[] { "序号", "办件流水号", "事项编码", "唯一业务流水号", "申请人姓名", "申请人证件号", "状态", "表单时间", "支付链接",
				"发送数据", "响应数据" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		if (grouppList != null) {
			for (int i = 0; i < grouppList.size(); i++) {
				objs = new Object[headers.length];
				objs[0] = 0;// 设置序号,在工具类中会出现
				objs[1] = grouppList.get(i).getProjectId();
				objs[2] = grouppList.get(i).getItemCode();
				objs[3] = grouppList.get(i).getFormBusiCode();
				objs[4] = grouppList.get(i).getApplyerName();
				objs[5] = grouppList.get(i).getApplyerCardNumber();
				objs[6] = grouppList.get(i).getStatus();
				objs[7] = grouppList.get(i).getCreateTime() == null ? ""
						: sdf.format(grouppList.get(i).getCreateTime());
				objs[8] = grouppList.get(i).getPayUrl();
				// objs[9] = grouppList.get(i).getSendData();
				// objs[10] = grouppList.get(i).getResponseData();
				dataList.add(objs);// 数据添加到excel表格
			}
		}
		// 使用流将数据导出
		OutputStream out = null;
		try {
			// 防止中文乱码
			String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
			response.setContentType("octets/stream");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			out = response.getOutputStream();
			ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);// 没有标题
			ex.export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportSmsExcel", method = RequestMethod.GET)
	public void exportSmsExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "itemCode", required = true) String itemCode,
			@RequestParam(value = "formCode", required = true) String formCode,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "pageNum", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "total", required = true) String total) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		String sqlSolr = "select * from c_sms where";
		StringBuffer sb = new StringBuffer(80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (Detect.notEmpty(itemCode)) {
			sb.append("and item_code = '" + itemCode + "'");
		}
		if (Detect.notEmpty(formCode)) {
			sb.append("and form_code = '" + formCode + "'");
		}
		if (Detect.notEmpty(phone)) {
			sb.append("and phone = '" + phone + "'");
		}
		if (Detect.notEmpty(startDate)) {
			sb.append("and create_date > {ts '" + startDate + "'} ");
			sb.append("and create_date < {ts '" + endDate + "'}");
		}

		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by create_date desc limit  " + 0 + "," + total);

		String sql = sqlSolr + sb.toString().substring(3);
		// 从solr获取数据
		JSONArray jsonArray = fdpClientMgmtService.queryDataFromSolr(sql);
		List<SmsVo> grouppList = JSONArray.parseArray(jsonArray.toJSONString(), SmsVo.class);
		// 导出文件的标题
		String title = "短信日志" + System.currentTimeMillis() + ".xls";
		// 设置表格标题行
		String[] headers = new String[] { "序号", "手机号码", "短信内容", "验证码", "事项编码", "表单编码", "状态", "创建时间" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		if (grouppList != null) {
			for (int i = 0; i < grouppList.size(); i++) {
				objs = new Object[headers.length];
				objs[0] = 0;// 设置序号,在工具类中会出现
				objs[1] = grouppList.get(i).getPhone();
				objs[2] = grouppList.get(i).getContent();
				objs[3] = grouppList.get(i).getCheckedCode();
				objs[4] = grouppList.get(i).getItemCode();
				objs[5] = grouppList.get(i).getFormCode();
				objs[6] = grouppList.get(i).getStatus();
				objs[7] = grouppList.get(i).getCreateDate() == null ? ""
						: sdf.format(grouppList.get(i).getCreateDate());
				// objs[9] = grouppList.get(i).getSendData();
				// objs[10] = grouppList.get(i).getResponseData();
				dataList.add(objs);// 数据添加到excel表格
			}
		}
		// 使用流将数据导出
		OutputStream out = null;
		try {
			// 防止中文乱码
			String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
			response.setContentType("octets/stream");
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			out = response.getOutputStream();
			ExportExcelSeedBack ex = new ExportExcelSeedBack(title, headers, dataList);// 没有标题
			ex.export(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
