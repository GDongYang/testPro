package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.SmsMgmtService;
import com.fline.form.vo.SmsVo;

@Service("smsMgmtService")
public class SmsMgmtServiceImpl implements SmsMgmtService {

	@Resource
	private FdpClientMgmtService fdpClientMgmtService;

	@Override
	public Map<String, Object> findPage(Map<String, Object> param) {
		String sqlSolr = "select * from c_sms where";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = (int) param.get("pageNum");
		int pageSize = (int) param.get("pageSize");
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();

		if (param.containsKey("startDate")) { // 开始时间
			sb.append("and create_date > {ts '" + param.get("startDate") + "'} ");
			sb.append("and create_date < {ts '" + param.get("stopDate") + "'}");
		}
		if (param.containsKey("itemCode")) {
			sb.append(" and item_code = '" + param.get("itemCode") + "'");
		}
		if (param.containsKey("formCode")) {
			sb.append(" and form_code='" + param.get("formCode") + "'");
		}
		if (param.containsKey("phone")) {
			sb.append(" and phone='" + param.get("phone") + "'");
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by create_date desc limit  " + (pageNum - 1) * pageSize + "," + pageSize);
		JSONArray array;
		try {
			resultMap = fdpClientMgmtService.querySmsFromSolr(sqlSolr + sb.toString().substring(3));
			if (resultMap != null) {
				array = (JSONArray) resultMap.get("JSONArray");
				String jsonStr = JSONObject.toJSONString(array);
				List<SmsVo> list = JSONObject.parseArray(jsonStr, SmsVo.class);
				page.put("total", resultMap.get("total"));
				page.put("rows", list);
				return page;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
