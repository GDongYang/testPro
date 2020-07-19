package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Department;
import com.fline.form.vo.DepartmentVo;

public interface DepartmentAccessService extends
		AbstractNamespaceAccessService<Department> {
	
	List<Long> findDeptsByUser(long userId);
	
	List<Department> findWithAop(Map<String,Object> param);
	
	//根据父类的uniqueCoding更新子类的uniqueCoding  
	void updateChildUniquecoding(Map<String,Object> param);

    List<DepartmentVo> findAllVo();
    /**
     * @Description: 根据部门的memo字段查找相关的部门
     * @param params
     * @return List<Department>
     */
    List<Department> findByMemo(Map<String, Object> params);
}
