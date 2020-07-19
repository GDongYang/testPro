package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.VichelDistrict;
import com.fline.form.access.service.VichelDistrictAccessService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.VichelDistrictMgmtService;
import com.fline.form.vo.VichelDistrictVo;

@Service("vichelDistrictMgmtService")
public class VichelDistrictMgmtServiceImpl implements VichelDistrictMgmtService,Cacheable {

	@Resource
	private VichelDistrictAccessService vichelDistrictAccessService;
	
	@Resource
	private DataCacheService dataCacheService;

	@Override
	public Pagination<VichelDistrict> findPagination(Map<String, Object> param,
			Ordering order, Pagination<VichelDistrict> page) {
		return vichelDistrictAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(VichelDistrict vichelDistrict) {
		vichelDistrictAccessService.update(vichelDistrict);
	}

	@Override
	public void remove(VichelDistrict vichelDistrict) {
		vichelDistrictAccessService.remove(vichelDistrict);
	}

	@Override
	public VichelDistrict create(VichelDistrict vichelDistrict) {
		return vichelDistrictAccessService.create(vichelDistrict);
	}

	@Override
	public VichelDistrict findById(long id) {
		return vichelDistrictAccessService.findById(id);
	}

	@Override
	public List<VichelDistrictVo> findAllVo() {
		return  vichelDistrictAccessService.findAllVo();
 	}
	/**
	 * 刷入缓存
	 */
	@Override
	public void refreshCache() {
		//清空所有数据
		dataCacheService.clearVichelDistrict();
		//获取所有的数据
		List<VichelDistrictVo> vichelDistrictVos = vichelDistrictAccessService.findAllVo();
	
		Map<String,Object> vichelDistrictMap = new  HashMap<String, Object>();
		if(Detect.notEmpty(vichelDistrictVos)) {//根据license分组
			for (VichelDistrictVo vichelDistrictVo : vichelDistrictVos) {
				if(vichelDistrictMap.containsKey(vichelDistrictVo.getLicense())) {
					List<VichelDistrictVo> vichelDistrictList = (List<VichelDistrictVo>) vichelDistrictMap.get(vichelDistrictVo.getLicense());
					vichelDistrictList.add(vichelDistrictVo);
				}else {
					List<VichelDistrictVo> vichelDistrictList = new LinkedList<VichelDistrictVo>();
					vichelDistrictList.add(vichelDistrictVo);
					vichelDistrictMap.put(vichelDistrictVo.getLicense(), vichelDistrictList);
				}
			}
		}
		dataCacheService.setVichelDistricts(vichelDistrictMap);
	}

}
