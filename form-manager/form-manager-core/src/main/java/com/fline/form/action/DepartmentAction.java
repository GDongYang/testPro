package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DepartmentAction extends AbstractAction implements
		ModelDriven<Department> {

	private static final long serialVersionUID = 6248344710994441574L;

	private DepartmentMgmtService departmentMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;

	private Department department;
	
	private String id;
	
	private long roleId;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDepartmentMgmtService(DepartmentMgmtService departmentMgmtService) {
		this.departmentMgmtService = departmentMgmtService;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String save() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		department.setIsLeaf("1");
		department.setActive("1");
		department.setOrgtype(2);
		//department.setUniquecoding(dept.getUniquecoding().substring(0, 6) + uuid.substring(0, 8));
		department.setOid(uuid);
		
		departmentMgmtService.create(department);
		dataMap.put("success", true);
		return SUCCESS;
	}

	public String update() {
		Department temp = departmentMgmtService.findById(department.getId());
		
		if(department.getActive()!=null)
			temp.setActive(department.getActive());
		
		if(department.getCreateDate()!=null)
			temp.setCreateDate(department.getCreateDate());
		if(department.getCreator()!=null)
			temp.setCreator(department.getCreator());
		
		temp.setIsLeaf(department.getIsLeaf());
	
		if(department.getMemo()!=null)
			temp.setMemo(department.getMemo());
		if(department.getName()!=null)
			temp.setName(department.getName());
		if(department.getNameLike()!=null)
			temp.setNameLike(department.getNameLike());
		if(department.getParentId()!=null)
			temp.setParentId(department.getParentId());
		if(department.getIpAddress()!=null)
			temp.setIpAddress(department.getIpAddress());
		if(department.getReturnAddress()!=null)
			temp.setReturnAddress(department.getReturnAddress());
		
		
		departmentMgmtService.update(temp);
		dataMap.put("success", true);
		return SUCCESS;
	}

	public String softRemove() {
		department = departmentMgmtService.findById(department.getId());
		departmentMgmtService.softRemove(department);
		dataMap.put("success", true);
		return SUCCESS;
	}

	public String findById() {
		Department department1 = departmentMgmtService.findById(department.getId());
		dataMap.put("department", department1);
		return SUCCESS;
	}
	
	public String findAll(){
		List<Department> departments = departmentMgmtService.findAll();
		dataMap.put("departments", departments);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<Department> page = new Pagination<Department>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		Ordering order = new Ordering();
		// order.addDesc("CREATED");
		Pagination<Department> pageData = departmentMgmtService.findPagination(
				param, order, page);
		dataMap.put("page", pageData);
		dataMap.put("rows",pageData.getItems());
		dataMap.put("total", pageData.getCount());
		return SUCCESS;
	}
	
	public String validNameRepeat(){
		String name = department.getName();
		Department temp = null;
		if(name!=null&&!"".equals(name.trim())){
			temp = departmentMgmtService.findByName(name);
			if(temp!=null&&temp.getActive()=="1") {
				dataMap.put("valid",false);
			} else {
				dataMap.put("valid", true);
			}
		} else {
			dataMap.put("valid",true);
		}
		return SUCCESS;
	}
	
	public String findDepartByRole() {
		List<Department> departList = departmentMgmtService.findDepartByRole(roleId);
		dataMap.put("result", departList);
		return SUCCESS;
	}
	
	public String findList() {
		Map<String, Object> params = new HashMap<>();
		if(Detect.notEmpty(department.getParentId())) {
			params.put("parentId", department.getParentId());
		}
		List<Department> depts = departmentMgmtService.findList(params);
		dataMap.put("departments", depts);
		return SUCCESS;
	}
	
	public String findTree() {
		List<Department> depts = departmentMgmtService.findTree();
		dataMap.put("departments", depts);
		return SUCCESS;
	}
	
	public String findTreeByParentId() {
		List<Department> depts = departmentMgmtService.findTreeByParentId(String.valueOf(department.getId()));
		dataMap.put("departments", depts);
		return SUCCESS;
	}
	
	@Override
	public Department getModel() {
		if(getId()!=null){
			department = departmentMgmtService.findById(Long.parseLong(id));
		}
		if (department == null) {
			department = new Department();
		}
		return department;
	}
	
	
	
}
