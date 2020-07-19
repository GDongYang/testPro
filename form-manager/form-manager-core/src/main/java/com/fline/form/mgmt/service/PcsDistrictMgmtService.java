package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.PcsDistrict;
import com.fline.form.vo.PcsDistrictVo;

public interface PcsDistrictMgmtService {
	Pagination<PcsDistrict> findPagination(Map<String, Object> param,
			Ordering order, Pagination<PcsDistrict> page);

	void update(PcsDistrict pcsDistrict);

	void remove(PcsDistrict pcsDistrict);

	PcsDistrict create(PcsDistrict pcsDistrict);

	PcsDistrict findById(long id);
	
	List<PcsDistrictVo> findAllVo();

}
