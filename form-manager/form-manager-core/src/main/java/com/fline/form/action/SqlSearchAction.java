package com.fline.form.action;

import com.alibaba.fastjson.JSON;
import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.BusinessMgmtService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SqlSearchAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3732270898013589234L;

	@Autowired
	private BusinessMgmtService businessMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private Integer dbSort;

	private String sql;

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public Integer getDbSort() {
		return dbSort;
	}

	public String getSql() {
		return sql;
	}

	public void setDbSort(Integer dbSort) {
		this.dbSort = dbSort;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String findBySql() {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> param = new HashMap<>();
		if (dbSort != null) {
			param.put("dbSort", dbSort);
		}
		if (Detect.notEmpty(sql)) {
			param.put("sql", sql);
		}
		resultMap = businessMgmtService.sqlSearch(param);
		String resultJSON = null;
		if (resultMap != null) {
			// resultJSON = JSONObject.toJSONString(resultMap.get("rows"));
			resultJSON = JSON.toJSONString(resultMap.get("rows"));
		}
		dataMap.put("result", resultJSON);
		dataMap.put("total", resultMap.get("total"));
		return SUCCESS;
	}
}
