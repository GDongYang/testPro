package com.fline.form.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.encrypt.PasswordEncoder;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.ServiceAccountMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.opensymphony.xwork2.ModelDriven;

public class ServiceAccountAction extends AbstractAction implements
		ModelDriven<ServiceAccount> {

	private static final long serialVersionUID = 5094159874832542554L;

	private ServiceAccountMgmtService serviceAccountMgmtService;
	
	private DepartmentMgmtService departmentMgmtService;
	
	private UserSessionManagementService userSessionManagementService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;
	
	private String newPass;
	
	private String oldPass;
	
	private ServiceAccount serviceAccount;
	
	private PasswordEncoder passwordEncoder;
	
	public void setServiceAccountMgmtService(
			ServiceAccountMgmtService serviceAccountMgmtService) {
		this.serviceAccountMgmtService = serviceAccountMgmtService;
	}
	
	public void setDepartmentMgmtService(DepartmentMgmtService departmentMgmtService) {
		this.departmentMgmtService = departmentMgmtService;
	}
	
	public UserSessionManagementService getUserSessionManagementService() {
		return userSessionManagementService;
	}

	public void setUserSessionManagementService(UserSessionManagementService userSessionManagementService) {
		this.userSessionManagementService = userSessionManagementService;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

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

	public ServiceAccount getServiceAccount() {
		return serviceAccount;
	}

	public void setServiceAccount(ServiceAccount serviceAccount) {
		this.serviceAccount = serviceAccount;
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String save() {
		try {
			Department department = departmentMgmtService.findById(serviceAccount.getDepartmentId());
			serviceAccount.setCode(department.getCode());
			serviceAccount.setPassword(passwordEncoder.encodePassword("123456", null));//初始密码
			long idNew = serviceAccountMgmtService.create(serviceAccount).getId();
			ServiceAccount serviceAccountNew = serviceAccountMgmtService.findById(idNew);
			serviceAccountMgmtService.createToCache(serviceAccountNew);
			dataMap.put("msg", "新增成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "新增失败!");
		}
		return SUCCESS;
	}

	public String update() {
		try {
			ServiceAccount sa = serviceAccountMgmtService.findById(serviceAccount.getId());
			sa.setActive(serviceAccount.isActive());
			sa.setDepartmentId(serviceAccount.getDepartmentId());
			sa.setMemo(serviceAccount.getMemo());
			sa.setName(serviceAccount.getName());
			sa.setPositionId(serviceAccount.getPositionId());
			sa.setIpaddress(serviceAccount.getIpaddress());
			serviceAccountMgmtService.update(sa);
			dataMap.put("msg", "修改成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "修改失败!");
		}
		return SUCCESS;
	}

	public String remove() {
		ServiceAccount serviceAccountOld = serviceAccountMgmtService.findById(serviceAccount.getId());
		serviceAccountMgmtService.remove(serviceAccountOld);
		return SUCCESS;
	}

	public String findById() {
		ServiceAccount serviceAccount1 = serviceAccountMgmtService
				.findById(serviceAccount.getId());
		dataMap.put("serviceAccount", serviceAccount1);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<ServiceAccount> page = new Pagination<ServiceAccount>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(serviceAccount.getName())) {
			param.put("nameLike", serviceAccount.getName());
		}
		Ordering order = new Ordering();
		order.addDesc("ID");
		page = serviceAccountMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	/** 修改密码 */
	public String updatePass(){
		String password = passwordEncoder.encodePassword(newPass, null);
		serviceAccountMgmtService.passWordReset(String.valueOf(serviceAccount.getId()), password);
		return SUCCESS;
	}
	/** 验证密码 */
	public String checkOldPass() {
		ServiceAccount sa = serviceAccountMgmtService.findById(serviceAccount.getId());
		if(sa.getPassword().equals(oldPass) || sa.getPassword().equals(passwordEncoder.encodePassword(oldPass, null))){
			dataMap.put("valid", true);
		} else {
			dataMap.put("valid", false);
		}
		return SUCCESS;
	}
	
	/**
	 * 用户名是否存在
	 * @return
	 */
	public String checkUserName() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", serviceAccount.getUsername());
		List<ServiceAccount> list = serviceAccountMgmtService.findList(param);
		if(list != null && !list.isEmpty()) {
			dataMap.put("valid", false);
		}else {
			dataMap.put("valid", true);
		}
		return SUCCESS;
	}
	
	public String updateIP() {
		serviceAccountMgmtService.updateIP(serviceAccount.getId(), serviceAccount.getIpaddress());
		return SUCCESS;
		
	}

	@Override
	public ServiceAccount getModel() {
		if (serviceAccount == null) {
			serviceAccount = new ServiceAccount();
		}
		return serviceAccount;
	}

}
