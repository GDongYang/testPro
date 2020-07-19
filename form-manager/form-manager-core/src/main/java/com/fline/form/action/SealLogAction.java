package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.SealLog;
import com.fline.form.mgmt.service.SealLogMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class SealLogAction extends AbstractAction implements ModelDriven<SealLog>{
	
	private static final long serialVersionUID = 9131502327353673099L;
	private SealLogMgmtService sealLogMgmtService;
	private SealLog sealLog;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;

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
	
	public SealLog getSealLog() {
		return sealLog;
	}

	public void setSealLog(SealLog sealLog) {
		this.sealLog = sealLog;
	}

	public void setSealLogMgmtService(SealLogMgmtService sealLogMgmtService) {
		this.sealLogMgmtService = sealLogMgmtService;
	}

	public String findPage() {
		Pagination<SealLog> page= new Pagination<SealLog>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		order.addDesc("ID");
		page = sealLogMgmtService.findPagination(param, order, page);
		dataMap.put("total",page.getCount());
		dataMap.put("rows",page.getItems());
		return SUCCESS;
	}
	
	
	@Override
	public SealLog getModel() {
		if(sealLog == null) {
			sealLog= new SealLog();
		}
		return sealLog;
	}

}
