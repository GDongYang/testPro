package com.fline.form.action;

import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Secret;
import com.fline.form.mgmt.service.SecretMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class SecretAction extends AbstractAction implements ModelDriven<Secret>{
	
	private static final long serialVersionUID = 1L;
	private SecretMgmtService secretMgmtService;
	private Secret secret;
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

	public void setSecretMgmtService(SecretMgmtService secretMgmtService) {
		this.secretMgmtService = secretMgmtService;
	}

	public Secret getSecret() {
		return secret;
	}

	public void setSecret(Secret secret) {
		this.secret = secret;
	}
	
	public String findPage() {
		Pagination<Secret> page= new Pagination<Secret>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		page = secretMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	public String save() {
		secretMgmtService.create(secret);
		return SUCCESS;
	}
	
	public String update() {
		secretMgmtService.update(secret);
		return SUCCESS;
	}
	
	public String remove() {
		secretMgmtService.remove(secret);
		return SUCCESS;
	}
	
	/**
	 * 应用密钥是否存在
	 * @return
	 */
	public String isExists() {
		boolean flag = secretMgmtService.isExists(secret.getAppKey());
		if(flag) {
			dataMap.put("valid", false);
		}else {
			dataMap.put("valid", true);
		}
		return SUCCESS;
	}
	
	@Override
	public Secret getModel() {
		if(secret == null) {
			secret= new Secret();
		}
		return secret;
	}

}
