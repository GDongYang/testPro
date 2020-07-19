/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:QlItemAction.java
 * Package Name:com.fline.yztb.action
 * Date:2019年8月12日上午10:48:21
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.QlItem;
import com.fline.form.mgmt.service.QlItemMgmtService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:QlItemAction 
 * Function: 权力事项操作控制层. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月12日 上午10:48:21
 * @author   邵炜
 * @version  
 * @see 	 
 */
public class QlItemAction extends AbstractAction implements ModelDriven<QlItem>{

	/**
	 */
	private static final long serialVersionUID = -8734134869884787121L;
	
	private QlItem qlItem;
	
	private int pageNum;
	
	private int pageSize;
	
	private String startDate;
	
	private String endDate;
	
	@Resource
	private QlItemMgmtService qlItemMgmtService;
	
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public QlItem getQlItem() {
		return qlItem;
	}

	public void setQlItem(QlItem qlItem) {
		this.qlItem = qlItem;
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

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	@Override
	public QlItem getModel() {
		if(qlItem == null) {
		   qlItem = new QlItem();
		}
		return qlItem;
	}

	/**
	 * findPage:分页查询权力事项信息. 
	 *
	 * @author 邵炜
	 * @return
	 */
	public String findPage() {
		Pagination<QlItem> pageData = this.qlItemMgmtService.findPage(pageNum, pageSize, qlItem, 
				startDate, endDate);
		dataMap.put("rows",pageData.getItems());
		dataMap.put("total", pageData.getCount());
		return SUCCESS;
	}

}

