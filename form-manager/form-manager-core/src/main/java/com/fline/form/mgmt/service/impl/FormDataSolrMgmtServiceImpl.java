package com.fline.form.mgmt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.FormDataSolrMgmtService;
import com.fline.yztb.vo.ItemVo;
import com.fline.form.vo.SmsVo;

@Service("formDataSolrMgmtService")
public class FormDataSolrMgmtServiceImpl implements FormDataSolrMgmtService {

	@Resource
	private FdpClientMgmtService fdpClientMgmtService;
	
	@Resource
	private DataCacheService dataCacheService;

	@Override
	public Map<String, Object> findPagination(Map<String, Object> param) {
		String sqlSolr = "select * from form_data where  ";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = 1;
		int pageSize = 10;
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		if (param.containsKey("pageNum")) {
			pageNum = (int) param.get("pageNum");
		}
		if (param.containsKey("pageSize")) {
			pageSize = (int) param.get("pageSize");
		}

		if (param.containsKey("startDate")) { // 开始时间
			sb.append("and dataCreated > {ts '" + param.get("startDate") + "'} ");
			sb.append("and dataCreated < {ts '" + param.get("stopDate") + "'}");
		}
		if (param.containsKey("certcode")) {
			sb.append("and CARD_TYPE = " + param.get("certcode"));
		}
		if (param.containsKey("certNum")) {
			sb.append("and applyerCardNumber = '" + param.get("certNum") + "'");
		}
		if (param.containsKey("formBusinessCode")) {
			sb.append("and formBusiCode = '" + param.get("formBusinessCode") + "'");
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("    order by dataCreated desc limit  " + (pageNum - 1) * pageSize + "," + pageSize);

		try {
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			resultMap = fdpClientMgmtService.queryFormFromSolr(sqlSolr + sb.toString().substring(3));
			if (resultMap != null) {
				JSONArray array = (JSONArray) resultMap.get("JSONArray");
				if(!Detect.notEmpty(array)) {
					page.put("total", resultMap.get("total"));
					page.put("rows", array);
					return page;
				}
				ItemVo item = null;
				String formContent = "", itemCode = "", itemName = "";
				JSONObject jb = new JSONObject();
				for (Object ob : array) {
					jb = (JSONObject) ob;
					itemCode = jb.getString("itemCode");
					item = this.dataCacheService.getItem(itemCode);
					if(item != null) {
						itemName = item.getName();
					}
					jb.put("itemName", itemName);
                    jb.put("dataCreated", df.format(jb.getDate("dataCreated")));
				}
				page.put("total", resultMap.get("total"));
				page.put("rows", array);
				return page;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> findSms(Map<String, Object> param) {
		String sqlSolr = "select phone, content, checkedcode, create_date, item_code,"
				+ "form_code, status from c_sms where  ";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = (int) param.get("pageNum");
		int pageSize = (int) param.get("pageSize");
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		if (param.containsKey("pageNum")) {
			pageNum = (int) param.get("pageNum");
		}
		if (param.containsKey("pageSize")) {
			pageSize = (int) param.get("pageSize");
		}

		if (param.containsKey("startDate")) { // 开始时间
			sb.append("and DATA_CREATED > {ts '" + param.get("startDate") + " 00:00:00'} ");
			sb.append("and DATA_CREATED < {ts '" + param.get("stopDate") + " 00:00:00'}");
		}
		if (param.containsKey("certcode")) {
			sb.append("and CARD_TYPE = " + param.get("certcode"));
		}
		if (param.containsKey("certNum")) {
			sb.append("and CARD_NUMBER = " + param.get("certNum"));
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		if (param.containsKey("dataBusinessCode")) {
			sb.append("   DATA_BUSINESS_CODE ='" + param.get("dataBusinessCode") + "' order by DATA_CREATED desc");
			sqlSolr = "select * from form_data where ";
		}
		sb.append("    order by create_date desc limit  " + (pageNum - 1) * pageSize + "," + pageSize);

		try {
			resultMap = fdpClientMgmtService.queryLogDbFromSolr(sqlSolr + sb.toString().substring(3));
			JSONArray array = (JSONArray) resultMap.get("JSONArray");
			String jsonStr = JSONObject.toJSONString(array);
			List<SmsVo> list = JSONObject.parseArray(jsonStr, SmsVo.class);
			page.put("total", resultMap.get("total"));
			page.put("rows", list);

			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
