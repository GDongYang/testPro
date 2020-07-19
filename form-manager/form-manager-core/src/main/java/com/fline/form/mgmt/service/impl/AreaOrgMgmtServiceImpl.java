package com.fline.form.mgmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.vo.AreaOrgVo;
import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.AreaOrg;
import com.fline.form.access.service.AreaOrgAccessService;
import com.fline.form.mgmt.service.AreaOrgMgmtService;

@Service("areaOrgMgmtService")
public class AreaOrgMgmtServiceImpl implements AreaOrgMgmtService, Cacheable {

	@Resource
	private AreaOrgAccessService areaOrgAccessService;
	@Resource
	private DataCacheService dataCacheService;

	@Override
	public Pagination<AreaOrg> findPagination(Map<String, Object> param,
			Ordering order, Pagination<AreaOrg> page) {
		return areaOrgAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(AreaOrg areaOrg) {
		areaOrgAccessService.update(areaOrg);
	}

	@Override
	public void remove(AreaOrg areaOrg) {
		areaOrgAccessService.remove(areaOrg);
	}

	@Override
	public AreaOrg create(AreaOrg areaOrg) {
		return areaOrgAccessService.create(areaOrg);
	}

	@Override
	public AreaOrg findById(long id) {
		return areaOrgAccessService.findById(id);
	}

    @Override
    public void refreshCache() {
        List<AreaOrgVo> areaOrgVos = areaOrgAccessService.findAllVo();
        dataCacheService.clearAreaOrg();
        dataCacheService.setAreaOrgs(areaOrgVos);
    }
}
