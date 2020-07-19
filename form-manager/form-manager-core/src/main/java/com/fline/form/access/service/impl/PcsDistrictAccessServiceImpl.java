package com.fline.form.access.service.impl;

import java.util.List;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.PcsDistrict;
import com.fline.form.access.service.PcsDistrictAccessService;
import com.fline.form.vo.PcsDistrictVo;

public class PcsDistrictAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<PcsDistrict> implements
        PcsDistrictAccessService {

	@Override
	public List<PcsDistrictVo> findAllVo() {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
	}
	
}
