package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CacheInfo;
import com.fline.form.mgmt.service.CacheInfoMgmtService;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.Map;

public class CacheInfoAction extends AbstractAction implements ModelDriven<CacheInfo>{
	
	private static final long serialVersionUID = 1L;
	private CacheInfoMgmtService cacheInfoMgmtService;
	private CacheInfo cacheInfo;
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

	public void setCacheInfoMgmtService(CacheInfoMgmtService cacheInfoMgmtService) {
		this.cacheInfoMgmtService = cacheInfoMgmtService;
	}

	public CacheInfo getCacheInfo() {
		return cacheInfo;
	}

	public void setCacheInfo(CacheInfo cacheInfo) {
		this.cacheInfo = cacheInfo;
	}
	
	public String findPage() {
		Pagination<CacheInfo> page= new Pagination<CacheInfo>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		page = cacheInfoMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}

	public String refreshCache() {
        cacheInfoMgmtService.refreshCache(cacheInfo.getCode());
        return SUCCESS;
    }

    public String refreshAll() {
        cacheInfoMgmtService.refreshAll();
        return SUCCESS;
    }
	
	@Override
	public CacheInfo getModel() {
		if(cacheInfo == null) {
			cacheInfo= new CacheInfo();
		}
		return cacheInfo;
	}

}
