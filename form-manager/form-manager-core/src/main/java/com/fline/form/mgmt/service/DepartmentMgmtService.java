package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;

public interface DepartmentMgmtService {
	Pagination<Department> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Department> page);

	void update(Department department);

	void remove(Department department);
	void softRemove(Department Department) ;

	Department create(Department department);

	Department findById(long id);

	List<Department> findAll();

	Department findByName(String name);
	
	List<Department> find(Map<String, Object> param);

	List<Department> findDepartByRole(long roleId);

	Department findOne(Map<String, Object>param);
	
	List<Long> findDeptsByUser(long userId);
	
	List<Department> findWithout(Map<String, Object> param);
	
	List<Department> findTree();
	
	List<Department> findList(Map<String, Object> params);
	
	List<Department> findTreeByParentId(String id);
	
}
