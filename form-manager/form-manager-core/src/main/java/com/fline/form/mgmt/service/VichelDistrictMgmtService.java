package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.VichelDistrict;
import com.fline.form.vo.VichelDistrictVo;

public interface VichelDistrictMgmtService {
	Pagination<VichelDistrict> findPagination(Map<String, Object> param,
			Ordering order, Pagination<VichelDistrict> page);

	void update(VichelDistrict vichelDistrict);

	void remove(VichelDistrict vichelDistrict);

	VichelDistrict create(VichelDistrict vichelDistrict);

	VichelDistrict findById(long id);

	List<VichelDistrictVo> findAllVo();
}
