package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.mgmt.service.TempAttachmentMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class TempDetailAction extends AbstractAction implements ModelDriven<TempAttachment> {

	private static final long serialVersionUID = -7584312180842876502L;

	private TempAttachmentMgmtService tempAttachmentMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;
	
	private TempAttachment tempDetail;
	
	private long id;

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void settempAttachmentMgmtService( TempAttachmentMgmtService tempAttachmentMgmtService) {
		this.tempAttachmentMgmtService = tempAttachmentMgmtService;
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

	public TempAttachment getTempDetail() {
		return tempDetail;
	}

	public void setTempDetail(TempAttachment tempDetail) {
		this.tempDetail = tempDetail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String save() {
		tempAttachmentMgmtService.create(tempDetail);
		return SUCCESS;
	}
	
	public String updateActive(){
		tempDetail = tempAttachmentMgmtService.findById(tempDetail.getId());

		tempAttachmentMgmtService.update(tempDetail);
		return SUCCESS;
	}

	public String update() {
		tempAttachmentMgmtService.update(tempDetail);
		return SUCCESS;
	}

	public String remove() {
		tempAttachmentMgmtService.remove(tempDetail);
		return SUCCESS;
	}

	public String findById() {
		TempAttachment tempDetail1 = tempAttachmentMgmtService.findById(110);
		dataMap.put("tempDetail", tempDetail1);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<TempAttachment> page = new Pagination<TempAttachment>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
        
		Map<String, Object> param = new HashMap<String, Object>();

		Ordering order = new Ordering();
		// order.addDesc("CREATED");
		Pagination<TempAttachment> pageData = tempAttachmentMgmtService.findPagination(
				param, order, page);
		dataMap.put("total",pageData.getCount());
		dataMap.put("rows",pageData.getItems());
		return SUCCESS;
	}
	
	public String updateGz(){
		TempAttachment tempDetail1 = tempAttachmentMgmtService.findById(tempDetail
				.getId());

		tempAttachmentMgmtService.update(tempDetail1);
		return SUCCESS;
	}

	@Override
	public TempAttachment getModel() {
		if (tempDetail == null) {
			tempDetail = new TempAttachment();
		}
		return tempDetail;
	}
}