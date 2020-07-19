package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.access.service.ServiceAccountAccessService;
import com.fline.form.vo.ServiceAccountVo;

public class ServiceAccountAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<ServiceAccount> implements
		ServiceAccountAccessService {
	
	@Override
	public void passWordReset(String id, String password) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id", id);
		param.put("passWord", password);
		update("passWordReset", param);
		
	}
	
	@Override
	public ServiceAccount findByCode(String code) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("code", code);
		return findOne(param);
	}
	
	public void updateIP(long id, String ipaddress) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("id", id);
		param.put("ipaddress", ipaddress);
		update("updateIP", param);
	}

    @Override
    public List<ServiceAccountVo> findAllVo() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
    }

}
