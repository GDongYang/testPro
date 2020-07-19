package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.fline.form.vo.DepartmentVo;

@Service("departmentMgmtService")
public class DepartmentMgmtServiceImpl implements DepartmentMgmtService, Cacheable {
	@Resource
	private DepartmentAccessService departmentAccessService;
	@Resource
	private DataCacheService dataCacheService;
	@Resource
	private UserSessionManagementService userSessionManagementService;

	@Override
	public Pagination<Department> findPagination(Map<String, Object> param, Ordering order,
			Pagination<Department> page) {
		return departmentAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Department department) {
		departmentAccessService.update(department);
		DepartmentVo departmentVo = department.toVo();
		dataCacheService.setDepartment(departmentVo);// 更新缓存
	}

	@Override
	public void remove(Department department) {
		departmentAccessService.remove(department);
		dataCacheService.removeRedis(KeyConstant.YZTB_DEPARTMENT, String.valueOf(department.getId()));
	}

	public void softRemove(Department department) {
		Department dept = departmentAccessService.findById(Long.parseLong(department.getParentId()));// 获取父部门
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", department.getParentId());
		params.put("active", "1");
		List<Department> depts = departmentAccessService.find(params);
		if (Detect.notEmpty(depts) && depts.size() == 1) { // 若已不存在active = 1 的子节点
			dept.setIsLeaf("1");
			departmentAccessService.update(dept);
		}
		department.setActive("0");
		departmentAccessService.update(department);
	}

	@Override
	public Department create(Department department) {
		// 获取父级部门判断是否是叶子节点
		Department dept = departmentAccessService.findById(Long.parseLong(department.getParentId()));
		// 将parentDepartment isLeaf 变为 0
		if (Detect.notEmpty(dept.getIsLeaf()) && dept.getIsLeaf().equals("1")) {
			dept.setIsLeaf("0");
			departmentAccessService.update(dept);
		}
		department.setUniquecoding(dept.getUniquecoding());
		department.setPoid(dept.getOid());
		Department newDepartment = departmentAccessService.create(department);
		DepartmentVo departmentVo = newDepartment.toVo();
		dataCacheService.setDepartment(departmentVo); // 插入缓存
		return newDepartment;
	}

	@Override
	public Department findById(long id) {
		return departmentAccessService.findById(id);
	}

	@Override
	public List<Department> findAll() {
		return departmentAccessService.findAll();
	}

	public Department findByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		return departmentAccessService.findOne(params);
	}

	@Override
	public List<Department> find(Map<String, Object> param) {
		return departmentAccessService.find(param);
	}

	@Override
	public List<Department> findDepartByRole(long roleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", roleId);
		return departmentAccessService.find("findDepartByRole", param);
	}

	@Override
	public Department findOne(Map<String, Object> param) {
		return departmentAccessService.findOne(param);
	}

	@Override
	public List<Long> findDeptsByUser(long userId) {
		return departmentAccessService.findDeptsByUser(userId);
	}

	@Override
	public List<Department> findWithout(Map<String, Object> param) {
		return departmentAccessService.find(param);
	}

	@Override
	public List<Department> findTree() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("citys", 1);
		param.put("active", 1);
		List<Department> depts = departmentAccessService.findWithAop(param);
		if (Detect.notEmpty(depts)) {
			String poid = "";
			for (Department dept : depts) {
				String uniquecoding = dept.getUniquecoding();
				if (uniquecoding != null && uniquecoding != "") {
					if (uniquecoding.length() == 6) {
						poid = dept.getOid();
					} else {
						dept.setPoid(poid);
					}
				}
			}
		}
		return depts;
	}

	@Override
	public List<Department> findList(Map<String, Object> params) {
		params.put("citys", 1);
		//params.put("nameWithDept", 1);
		return departmentAccessService.findWithAop(params);
	}

	@Override
	public List<Department> findTreeByParentId(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("citys", 1);
		param.put("active", 1);
		param.put("parentId", id);
		List<Department> depts = departmentAccessService.findWithAop(param);
		if (Detect.notEmpty(depts)) {
//			String poid = "";
//			for (Department dept : depts) {
				/*
				 * if("1".equals(dept.getIsLeaf())){ dept.setIsLeaf("true"); }
				 */
//			}
		}
		return depts;
	}

	/**
	 * 刷新缓存
	 */
	@Override
	public void refreshCache() {
		dataCacheService.clearDepartment();
		dataCacheService.setDepartments(departmentAccessService.findAllVo());
	}

}
