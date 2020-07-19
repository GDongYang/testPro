package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.PcsDistrict;
import com.fline.form.vo.PcsDistrictVo;

public interface PcsDistrictAccessService extends AbstractNamespaceAccessService<PcsDistrict> {
	
	List<PcsDistrictVo> findAllVo();
}
