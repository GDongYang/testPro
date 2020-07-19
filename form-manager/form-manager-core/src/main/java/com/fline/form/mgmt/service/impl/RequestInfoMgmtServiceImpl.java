package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.RequestInfoMgmtService;
import com.fline.form.vo.RequestLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("requestInfoMgmtService")
public class RequestInfoMgmtServiceImpl implements RequestInfoMgmtService {
	
	
	@Resource
	private FdpClientMgmtService fdpClientMgmtService;
	
	
	@Override
	public Map<String, Object> getInfos(Map<String, Object> param, Integer pageNum, Integer pageSize) {
		/*拼接SQL查询语句*/
		StringBuilder sql = new StringBuilder("select * from request_log ");
		if(param.keySet().size() > 0) {//表示是带条件查询
			sql.append(" where id != ''");
			if(param.containsKey("requestName")) {
				String requestName = (String) param.get("requestName");
				String requestCode = requestName;
				sql.append(" and ( requestName_s = '" + requestName + "' or requestCode_s = '" + requestCode + "'"  + ")");
			}
			if(param.containsKey("startDate")) {
				sql.append(" and update_time > {ts '" + param.get("startDate") + "'} ");
			}
			if(param.containsKey("endDate")) {
				sql.append(" and update_time < {ts '" + param.get("endDate") + "'} ");
			}
			if(param.containsKey("status")) {
				sql.append(" and status = '" + param.get("status") + "'");
			}
			if(param.containsKey("dataSource")) {
				sql.append(" and dataSource_s = '" + param.get("source") + "'");
			}
		}
		/*计算页码*/
		sql.append("  order by update_time desc limit " + (pageNum - 1) * pageSize + "," + pageSize);
		try {
			Map<String, Object> page = null;
			/*通过SOLR查询*/
			Map<String, Object> solrDataMap = fdpClientMgmtService.queryFormFromSolr(sql.toString());
			
			if(solrDataMap != null) {
				page = new HashMap<String,Object>();
				JSONArray array = (JSONArray) solrDataMap.get("JSONArray");
				List<RequestLog> requestLogs = JSONObject.parseArray(JSONObject.toJSONString(array),RequestLog.class);
				
				page.put("rows", requestLogs);
				page.put("total", solrDataMap.get("total"));
			}
			return page;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Map<String, Object> getCounts(Integer type) {
		StringBuilder statusSql = new StringBuilder("select id from request_log where status = '1'");
		StringBuilder dataSourceSql = new StringBuilder("select id from request_log where (status_l = '-1' or status_l = '0') ");

		/*根据type判断是否要限定日期*/
		if(type == 1) {//查询当天
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String today = sdf.format(new Date());
			statusSql.append(" and update_time > {ts '" + today + "'} ");
			dataSourceSql.append(" and update_time > {ts '" + today + "'} ");
		}
		dataSourceSql.append(" limit 0,99999999");
		Map<String, Object> dataMap = new HashMap<>();
		try {
			//获取请求成功的个数
			Map<String, Object> statusMap = fdpClientMgmtService.queryFormFromSolr(statusSql.toString());
			dataMap.put("successCount", statusMap.get("total"));
				
			Map<String, Object> datasourceMap = fdpClientMgmtService.queryFormFromSolr(dataSourceSql.toString());
			JSONArray dataSourceSolr = (JSONArray) datasourceMap.get("JSONArray");
			dataMap.put("msg", "获取成功!");
			if(!Detect.notEmpty(dataSourceSolr)) {//如果没查询到则返回
				dataMap.put("faildCount", 0);
				dataMap.put("allCount", (int)statusMap.get("total"));
				dataMap.put("dataSources", null);
			}
			int failedCountSum = dataSourceSolr.size();
			Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();

			dataMap.put("faildCount", failedCountSum);
			dataMap.put("allCount", failedCountSum + (int)statusMap.get("total"));
			dataMap.put("dataSources", new ArrayList<Map<String,Object>>(resultMap.values()));
			return dataMap;
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "获取失败!请重试");
			return dataMap;
		}
	}

}
