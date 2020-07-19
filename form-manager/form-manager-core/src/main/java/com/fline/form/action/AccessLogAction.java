package com.fline.form.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.AccessLog;
import com.fline.form.mgmt.service.AccessLogMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class AccessLogAction extends AbstractAction implements ModelDriven<AccessLog>{
	
	private static final long serialVersionUID = 5229492697426944252L;
	private AccessLogMgmtService accessLogMgmtService;
	private AccessLog accessLog;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private String userName;
	private String accessTime;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public void setAccessLogMgmtService(AccessLogMgmtService accessLogMgmtService) {
		this.accessLogMgmtService = accessLogMgmtService;
	}


	public AccessLog getAccessLog() {
		return accessLog;
	}

	public void setAccessLog(AccessLog accessLog) {
		this.accessLog = accessLog;
	}
	
	public String findPage() {
		Pagination<AccessLog> page= new Pagination<AccessLog>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(accessLog.getUserName())){
			param.put("userName", accessLog.getUserName());
		}
		if(accessLog.getAccessDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			param.put("accessDate", sdf.format(accessLog.getAccessDate()));
		}
		Ordering order = new Ordering();
		order.addDesc("ACCESS_DATE");
		page = accessLogMgmtService.findPagination(param, order, page);
		dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	
	@Override
	public AccessLog getModel() {
		if(accessLog == null) {
			accessLog= new AccessLog();
		}
		return accessLog;
	}

}
