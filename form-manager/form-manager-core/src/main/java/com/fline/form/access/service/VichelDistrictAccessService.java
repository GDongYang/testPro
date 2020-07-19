package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.VichelDistrict;
import com.fline.form.vo.VichelDistrictVo;

public interface VichelDistrictAccessService extends AbstractNamespaceAccessService<VichelDistrict> {
	
	List<VichelDistrictVo> findAllVo();
}
