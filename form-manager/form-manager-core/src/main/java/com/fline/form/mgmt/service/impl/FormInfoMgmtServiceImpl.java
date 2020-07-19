package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.FormInfoMgmtService;
import com.fline.form.vo.FormInfoVo;
import com.fline.yztb.vo.ItemVo;

@Service("formInfoMgmtService")
public class FormInfoMgmtServiceImpl implements FormInfoMgmtService {

	@Resource
	private FdpClientMgmtService fdpClientMgmtService;
	
	@Resource
	private ItemAccessService itemAccessService;
	
	@Resource
	private DataCacheService dataCacheService;

	@Override
	public Map<String, Object> findPagination(Map<String, Object> param) {
		String sqlSolr = "select * from form_info where";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = (int) param.get("pageNum");
		int pageSize = (int) param.get("pageSize");
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		if (param.containsKey("startDate")) { // 开始时间
			sb.append("and FORM_CREATED > {ts '" + param.get("startDate") + "'} ");
			sb.append("and FORM_CREATED < {ts '" + param.get("stopDate") + "'}");
		}
		if (param.containsKey("stopDate")) {
		}
		if (param.containsKey("certcode")) {
			sb.append("and APPLY_CARD_TYPE = " + param.get("certcode"));
		}
		if (param.containsKey("certNum")) {
			sb.append("and APPLY_CARD_NUMBER='" + param.get("certNum") + "'");
		}
		if (param.containsKey("formCode")) {
			sb.append("and FORM_CODE='" + param.get("formCode") + "'");
		}
		if (param.containsKey("certTempCode")) {
			sb.append("and APPLY_CARD_TYPE='" + param.get("certTempCode") + "'");
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by FORM_CREATED desc limit  " + (pageNum - 1) * pageSize + "," + pageSize);
		JSONArray array;
		try {
			resultMap = fdpClientMgmtService.queryFormFromSolr(sqlSolr + sb.toString().substring(3));
			if (resultMap != null) {
				array = (JSONArray) resultMap.get("JSONArray");
				String jsonStr = JSONObject.toJSONString(array);
				List<FormInfoVo> list = JSONObject.parseArray(jsonStr, FormInfoVo.class);
				ItemVo item = null;
				for (FormInfoVo vo : list) {
					item = this.dataCacheService.getItem(vo.getItemCode());
					if(item != null) {
						vo.setItemName(item.getName());
					}
				}
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
