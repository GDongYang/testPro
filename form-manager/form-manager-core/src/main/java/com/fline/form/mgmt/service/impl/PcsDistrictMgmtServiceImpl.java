package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.PcsDistrict;
import com.fline.form.access.service.PcsDistrictAccessService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.PcsDistrictMgmtService;
import com.fline.form.vo.PcsDistrictVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("pcsDistrictMgmtService")
public class PcsDistrictMgmtServiceImpl implements PcsDistrictMgmtService,Cacheable {

	@Resource
	private PcsDistrictAccessService pcsDistrictAccessService;
	
	@Resource
	private DataCacheService dataCacheService;

	@Override
	public Pagination<PcsDistrict> findPagination(Map<String, Object> param,
			Ordering order, Pagination<PcsDistrict> page) {
		return pcsDistrictAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(PcsDistrict pcsDistrict) {
		pcsDistrictAccessService.update(pcsDistrict);
	}

	@Override
	public void remove(PcsDistrict pcsDistrict) {
		pcsDistrictAccessService.remove(pcsDistrict);
	}

	@Override
	public PcsDistrict create(PcsDistrict pcsDistrict) {
		return pcsDistrictAccessService.create(pcsDistrict);
	}

	@Override
	public PcsDistrict findById(long id) {
		return pcsDistrictAccessService.findById(id);
	}

	@Override
	public List<PcsDistrictVo> findAllVo() {
		return pcsDistrictAccessService.findAllVo();
	}

	@Override
	public void refreshCache() {
		//清空缓存
		dataCacheService.clearPcsDistrict();
		//获取所有数据
		List<PcsDistrictVo> pcsDistrictVos = pcsDistrictAccessService.findAllVo();
		
		Map<String,Object> pcsDistrictMap = new  HashMap<String, Object>();
		
		if(Detect.notEmpty(pcsDistrictVos)) {//根据qhdm字段进行分组
			for (PcsDistrictVo pcsDistrictVo : pcsDistrictVos) {
				if(pcsDistrictMap.containsKey(pcsDistrictVo.getQhdm())) {
					List<PcsDistrictVo> vichelDistrictList = (List<PcsDistrictVo>) pcsDistrictMap.get(pcsDistrictVo.getQhdm());
					vichelDistrictList.add(pcsDistrictVo);
				}else {
					List<PcsDistrictVo> vichelDistrictList = new LinkedList<PcsDistrictVo>();
					vichelDistrictList.add(pcsDistrictVo);
					pcsDistrictMap.put(pcsDistrictVo.getQhdm(), vichelDistrictList);
				}
			}
		}
		
		dataCacheService.setPcsDistricts(pcsDistrictMap);
	}

}
