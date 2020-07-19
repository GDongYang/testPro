package com.fline.form.access.service.impl;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.Synchro;
import com.fline.form.access.service.SynchroAccessService;

public class SynchroAccessServiceImpl extends AbstractNamespaceAccessServiceImpl<Synchro> implements SynchroAccessService {

	@Override
	public void updateEndTimeByTableName(Map<String, Object> map) {
		update("updateEndTimeByTableName", map);
		
	}

	@Override
	public void updateStartTimeByEndTime(Map<String, Object> map) {
		update("updateStartTimeByEndTime", map);
		
	}

	@Override
	public Synchro findStartTime() {
		return (Synchro) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForObject("Synchro.findStartTime");
	}

	@Override
	public void updateState(Map<String, Object> map) {
		update("updateState", map);
	}

	@Override
	public Synchro findUpdateState(Map<String, Object> map) {
		List<Synchro> list = find("findUpdateState", map);
		return Detect.notEmpty(list) ? list.get(0) : null;
	}
	
	

}
