package com.fline.form.access.service.impl;

import java.util.List;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.VichelDistrict;
import com.fline.form.access.service.VichelDistrictAccessService;
import com.fline.form.vo.VichelDistrictVo;

public class VichelDistrictAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<VichelDistrict> implements
        VichelDistrictAccessService {
	@Override
	public List<VichelDistrictVo> findAllVo() {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
	}
}
