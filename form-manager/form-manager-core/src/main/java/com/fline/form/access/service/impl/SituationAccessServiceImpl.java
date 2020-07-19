package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Situation;
import com.fline.form.access.service.SituationAccessService;

public class SituationAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<Situation> implements
SituationAccessService {

    @Override
    public List<Situation> findSituationByItemId(Map<String, Object> map) {
        return find("findSituationByItemId", map);
    }

    
	@Override
	public void removeByItemId(Map<String,Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Situation.removeByItemId", params);
	}
	
	@Override
	public void deleteSituationByInnerCode(Map<String, Object> map) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Situation.deleteSituationByInnerCode", map);		
	}


	@Override
	public List<Long> findSituationIdByItemIds(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Situation.findSituationIdByItemIds", params);
	}


	@Override
	public void removeByMap(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Situation.removeByMap", params);		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findSituationIdByInnerCode(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Situation.findSituationIdByInnerCode", params);
	}


	@Override
	public void updateSituationCode() {
		update("updateSituationCode",new HashMap<String, Object>());
	}
}
