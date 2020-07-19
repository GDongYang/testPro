package com.fline.form.mgmt.service.impl;


import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Role;
import com.fline.form.access.model.User;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.access.service.RoleAccessService;
import com.fline.form.mgmt.service.RoleMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("roleMgmtService")
public class RoleMgmtServiceImpl implements RoleMgmtService {
   @Resource
   private RoleAccessService roleAccessService;
   
   @Resource
   private DepartmentAccessService departmentAccessService;
   @Resource
   private UserSessionManagementService userSessionManagementService;
   

   @Override
	public Pagination<Role> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Role> page) {
      return roleAccessService.findPagination(param, order,page);
   }

   @Override
   public void update(Role role) {
      roleAccessService.update(role);
   }

   @Override
   public void remove(Role role) {
	  roleAccessService.removeRoleMenuByRole(role.getId());//删除该角色的菜单权限
	  roleAccessService.removeUserRoleByRole(role.getId());//删除该角色用户关联
      roleAccessService.remove(role);//删除角色
   }

   @Override
   public Role create(Role role) {
      return roleAccessService.create(role);
   }

   @Override
   public Role findById(long id) {
      return roleAccessService.findById(id);
   }
   
   @Override
   public List<Role> findAll() {
   		return roleAccessService.findAll();
   }
   
   public void saveRoleMenu(long roleId, long menuId) {
	   roleAccessService.saveRoleMenu(roleId, menuId);
   }
   
   public void removeRoleMenuByRole(long roleId) {
	   roleAccessService.removeRoleMenuByRole(roleId);
   }
   
   public void removeRoleMenuByMenu(long menuId) {
	   roleAccessService.removeRoleMenuByMenu(menuId);
   }
   
   public boolean isExists(long id,String name) {
	   return roleAccessService.isExists(id, name); 
   }

	@Override
	public List<Role> findByUserRole(long id) {
		return roleAccessService.findByUserRole(id);
	}
	
	/**
	 * 分配菜单权限
	 */
	@Override
	public void assignMenu(long roleId,long[] menuIds) {
		removeRoleMenuByRole(roleId);
		if (menuIds != null && menuIds.length > 0) {
			for (long menuId : menuIds) {
				saveRoleMenu(roleId, menuId);
			}
		}
	}

	@Override
	public void removeRoleDepartByRole(long roleId) {
		roleAccessService.removeRoleDepartByRole(roleId);
		
	}

	@Override
	public void saveRoleDepart(long roleId, long departId) {
		roleAccessService.saveRoleDepart(roleId, departId);
	}
	
	@Override
	public void assignDepart(long roleId,List<Long>departIds) {
		removeRoleDepartByRole(roleId);
		if (departIds != null && departIds.size() > 0) {
			for (long departId : departIds) {
				saveRoleDepart(roleId, departId);
			}
		}
	}
	
	@Override
	public List<Role> findList() {
		User user = (User) userSessionManagementService.findByContext();
		List<Role> roles = findByUserRole(user.getId());
		int level = -1;
		if(Detect.notEmpty(roles)) {
			for(Role role : roles) {
				if(role.getLevel() > level) {
					level = role.getLevel();
				}
			}
		}
		if(level == 0) {
			return roleAccessService.findAll();
		} else if (level == 1) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("level", -1);
			return roleAccessService.find(param);
		} else {
			return null;
		}
	}

    @SuppressWarnings("unchecked")
	@Override
    public void assignDepartAll(Map<String, Object> params) {
    	//List<Long> departIdsTemp = (List<Long>) params.get("departIds");
    	List<Long> departIds =   (List<Long>) params.get("departIds") == null? new  ArrayList<>():(List<Long>) params.get("departIds");//获取传入的部门
    	List<Long> showDeptIds = (List<Long>) params.get("showDeptIds");//获取页面展示的部门
    	Long roleId = (Long) params.get("roleId");//角色Id
    	Long jobDeptId = (Long) params.get("jobDeptId");//具体的部门
    	Integer jobLevel = (Integer) params.get("jobLevel");//级别 ： 省级/市级
    	String jobStr = (String) params.get("jobStr");//职责
    	
    	List<Long> bindDeptIds = roleAccessService.findDeptIdsByRole(params);//获取当前角色所有关联的部门
    	
    	List<Long> needHandleDeptIds = new LinkedList();//存储需要处理的部门ID列表
    	List<Long> needRemoveDeptIds = new LinkedList();//存储需要删除的部门ID列表
    	
    	if(Detect.notEmpty(bindDeptIds)) {
	    	for(Long bindDeptId:bindDeptIds) {//存储待处理部门即页面展示到且在关联的部门里
	    		if(showDeptIds.contains(bindDeptId)) {
	    			needHandleDeptIds.add(bindDeptId);
	    		}
	    	}
	    	
	    	if(Detect.notEmpty(needHandleDeptIds)) {
		    	for(Long needHandleDeptId:needHandleDeptIds) {
		    		if(departIds.contains(needHandleDeptId) == false) {
		    			needRemoveDeptIds.add(needHandleDeptId);
		    			bindDeptIds.remove(needHandleDeptId);
		    		}else {
						departIds.remove(needHandleDeptId);//删除已添加过的部门
					}
		    	}
	    	}
    	}
    	if (Detect.notEmpty(needRemoveDeptIds)) {
    		params.put("deptIds", needRemoveDeptIds);
			roleAccessService.removeDeptIds(params);//删除解除关联的部门
		}
    	if(jobDeptId !=null &&jobDeptId != 0) {//需要根据职能分配部门
    		Map<String, Object> param = new HashMap<>();
    		if(jobLevel == 3) {//地市级部门 需要配置codelike 获取改地市下的所有相关部门
    			Department department = departmentAccessService.findById(jobDeptId);
    			param.put("codeLike", department.getCode());
    		}
    		/*根据部门的memo查询相同职能的部门*/
    		if(Detect.notEmpty(jobStr)) {//部门职责
    			param.put("memo", jobStr);
    			List<Department> sameMemoDepts = departmentAccessService.findByMemo(param);
    			this.getCanInsertList(sameMemoDepts, bindDeptIds,departIds);
    		}
    	}
        if(Detect.notEmpty(departIds)){
        	//新加入到关联表
        	Map<String, Object> map = new HashMap<>();
        	map.put("deptIds", departIds);
        	map.put("roleId", roleId);
        	roleAccessService.saveRoleDepartAll(map);
        }
    }	
	
    public void getCanInsertList(List<Department> deptList,List<Long> hadBindList,List<Long> insertList ) {
    	/*用递归的方式添加需要新增的部门*/
    	if(Detect.notEmpty(deptList) && deptList.size() > 0) {
    		int i = 0;
    		long[] parentIdList = new long[deptList.size()];
    		List<Department> parentList = new LinkedList();
    		for(Department department :deptList) {
    			if(!hadBindList.contains(department.getId()) && !insertList.contains(department.getId())) {
    				insertList.add(department.getId());
    			}
    			if(Detect.notEmpty(department.getParentId()) && !"".equals(department.getParentId())) {
    				parentIdList[i++] = Long.parseLong(department.getParentId());
    			}
    		}
    		if(Detect.notEmpty(parentIdList)) {
    			parentList = departmentAccessService.findByIds(parentIdList);
    		}
    		getCanInsertList(parentList, hadBindList, insertList);
    	}
    }
}
