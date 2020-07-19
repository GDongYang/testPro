package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Department;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.vo.DepartmentVo;

public class DepartmentAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Department> implements
		DepartmentAccessService {
	
	@Override
	public List<Long> findDeptsByUser(long userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return this.getIbatisDataAccessObject().find(namespace, "findDeptsByUser", param);
	}
	
	@Override
	public List<Department> findWithAop(Map<String,Object> param) {
		return find(param);
	}

	@Override
	public void updateChildUniquecoding(Map<String, Object> param) {
		this.getIbatisDataAccessObject().update(namespace,"updateChildUniquecoding", param);
	}

	@Override
	public List<DepartmentVo> findAllVo() {
	    return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
    }

	@Override
	public List<Department> findByMemo(Map<String, Object> params) {
		return find(params);
	}

}
