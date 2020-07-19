package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.yztb.access.model.CertResource;
import com.fline.form.mgmt.service.CertResourceMgmtService;
import com.opensymphony.xwork2.ModelDriven;
import java.util.*;

/**
 * 
 * @author wangn
 * 2019-4-28
 * r_cert_resource表action类
 *
 */
public class CertResourceAction extends AbstractAction implements ModelDriven<CertResource>{
	
	private static final long serialVersionUID = 1L;
	
	private CertResourceMgmtService certResourceMgmtService;
	
	private CertResource certResource;
	
	
	
	private Map<String,Object> responseData = new HashMap<String,Object>();
	
	private int pageNum;

	private int pageSize; 
	
	
	private String sort;
	
	public CertResource getCertResource() {
		return certResource;
	}

	public void setCertResource(CertResource certResource) {
		this.certResource = certResource;
	}


	public void setCertResourceMgmtService(CertResourceMgmtService certResourceMgmtService) {
		this.certResourceMgmtService = certResourceMgmtService;
	}
	
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}

	public Map<String, Object> getResponseData() {
		return responseData;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	@Override
	public CertResource getModel() {
		if(certResource == null){
			certResource = new CertResource();
		}
		return certResource;
	}
	
	/**
	 * 默认find方法查找
	 * @return
	 * @throws Exception
	 */
	public String findPagination() throws Exception{
		
		
		Pagination<CertResource> page = new Pagination<CertResource>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tempId", certResource.getTempId());
		Ordering order = new Ordering();
		order.addDesc(sort);
		 //order.addDesc("CREATED");
		Pagination<CertResource> pageData = certResourceMgmtService.findPagination(
				param, order, page);
		
		responseData.put("rows", pageData.getItems());
		return SUCCESS;
		
	}
	

	/**
	 * 修改数据
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception{
		certResourceMgmtService.update(certResource);
		responseData.put("result", "成功");
		return SUCCESS;
	}
	
	

	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception{
		certResourceMgmtService.remove(certResource);
		responseData.put("result", "成功");
		return SUCCESS;
	}

	/**
	 * 添加数据
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception{
		
		certResourceMgmtService.create(certResource);
		responseData.put("result", "成功");
		
		return SUCCESS;
	}

	

}
