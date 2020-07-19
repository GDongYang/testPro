package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.mgmt.service.ProjectInfoMgmtService;
import com.fline.yztb.vo.ItemVo;
import com.fline.form.vo.ProjectInfoVo;
import com.fline.form.vo.ProjectLifecycle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("projectInfoMgmtService")
public class ProjectInfoMgmtServiceImpl implements ProjectInfoMgmtService {
	@Resource
	private FdpClientMgmtService fdpClientMgmtService;
	
	@Resource
	private DataCacheService dataCacheService;
	
	@Override
	public Map<String, Object> findPagination(Map<String, Object> param) {
		String sqlSolr = "select * from project_lifecycle where   ";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = (int) param.get("pageNum");
		int pageSize = (int) param.get("pageSize");
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
        if(param.size() <= 2) {
            sb.append("and node = 0");
        }
		if (param.containsKey("startDate")) { // 开始时间
			sb.append("and createTime > {ts '" + param.get("startDate") + "'} ");
			sb.append("and createTime < {ts '" + param.get("stopDate") + "'}");
		}
		if (param.containsKey("certcode")) {
			sb.append("and CARD_TYPE = '" + param.get("certcode") + "'");
		}
		if (param.containsKey("projectId")) {
			sb.append("and projectId = '" + param.get("projectId") + "'");
		}
		if (param.containsKey("certNum")) {
			sb.append("and applyerCardNumber = '" + param.get("certNum") + "'");
		}
		if (param.containsKey("itemCode")) {
			sb.append("and itemCode = '" + param.get("itemCode") + "'");
		}
		if (param.containsKey("qlCode")) {
			sb.append("and qlCode = '" + param.get("qlCode") + "'");
		}
		if (param.containsKey("projectNode")) {
			sb.append("and node = '" + param.get("projectNode") + "'");
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("    order by createTime desc limit " + (pageNum - 1) * pageSize + "," + pageSize);
		try {
			System.out.println(sqlSolr + sb.toString().substring(3));
			resultMap = fdpClientMgmtService.queryFormFromSolr(sqlSolr + sb.toString().substring(3));
			if (resultMap != null) {
				JSONArray array = (JSONArray) resultMap.get("JSONArray");
				String jsonStr = JSONObject.toJSONString(array);
				List<ProjectInfoVo> list = JSONObject.parseArray(jsonStr, ProjectInfoVo.class);
				ItemVo item = null;
				for (ProjectInfoVo projectInfoVo : list) {
					item = this.dataCacheService.getItem(projectInfoVo.getItemCode());
					if(item != null) {
						projectInfoVo.setItemName(item.getName());
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

	@Override
	public Map<String, Object> findByProjectId(Map<String, Object> param) {
		String sqlSolr = "select * from project_lifecycle where   ";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = (int) param.get("pageNum");
		int pageSize = (int) param.get("pageSize");
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		if (param.containsKey("projectId")) {
			sb.append("and projectId = '" + param.get("projectId") + "'");
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("    order by createTime asc limit " + (pageNum - 1) * pageSize + "," + pageSize);
		try {
			resultMap = fdpClientMgmtService.queryFormFromSolr(sqlSolr + sb.toString().substring(3));
			JSONArray array = (JSONArray) resultMap.get("JSONArray");
			String jsonStr = JSONObject.toJSONString(array);
			List<ProjectLifecycle> list = JSONObject.parseArray(jsonStr, ProjectLifecycle.class);
			ItemVo item = null;
			for (ProjectLifecycle ProjectLifecycle : list) {
				item = this.dataCacheService.getItem(ProjectLifecycle.getItemCode());
				if(item != null) {
					ProjectLifecycle.setItemName(item.getName());
				}
			}
			page.put("total", resultMap.get("total"));
			page.put("rows", list);

			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
