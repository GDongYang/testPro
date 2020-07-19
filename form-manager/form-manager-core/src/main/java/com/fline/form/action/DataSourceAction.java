package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.fline.form.access.model.DataSource;
import com.fline.form.access.service.DataSourceAccessService;
import com.opensymphony.xwork2.ModelDriven;

public class DataSourceAction extends AbstractAction implements ModelDriven<DataSource>{
	
	private static final long serialVersionUID = 5229492697426944252L;
	private DataSourceAccessService dataSourceAccessService;
	private DataSource ds;
	private Map<String, Object> dataMap = new HashMap<String, Object>();

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDataSourceAccessService(DataSourceAccessService dataSourceAccessService) {
		this.dataSourceAccessService = dataSourceAccessService;
	}
	
	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public String findDepts() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", 1);
		dataMap.put("result", dataSourceAccessService.find(params));
		return SUCCESS;
	}
	
	public String findByParent() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentCode", ds.getParentCode());
		dataMap.put("result", dataSourceAccessService.find(params));
		return SUCCESS;
	}
	
	@Override
	public DataSource getModel() {
		if(ds == null) {
			ds= new DataSource();
		}
		return ds;
	}

}
