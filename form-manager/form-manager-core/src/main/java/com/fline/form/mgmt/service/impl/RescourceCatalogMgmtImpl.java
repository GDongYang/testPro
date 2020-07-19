package com.fline.form.mgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.util.Detect;
import com.fline.aic.db.vo.redis.DataElementVo;
import com.fline.aic.db.vo.redis.DataFormatVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.aic.resourcesharing.vo.CatalogVo;
import com.fline.aic.resourcesharing.vo.ResourceVo;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.ResourceCatalogMgmtService;

@Service("resourceCatalogMgmtService")
public class RescourceCatalogMgmtImpl implements ResourceCatalogMgmtService {

	@Resource
	private DataCacheService dataCacheService;

	@Override
	public List<CatalogVo> getCatalogList() {
		List<CatalogVo> result = new ArrayList<CatalogVo>();
		List<String> catalogIds = dataCacheService.getSubCatalogIds("0");
		if (Detect.notEmpty(catalogIds)) {
			for (String catalogId : catalogIds) {
				CatalogVo vo = dataCacheService.getCatalog(catalogId);
				result.add(vo);
			}
		}
		
		return result;
	}

	@Override
	public List<ResourceVo> getResourceList(String parentId) {
		List<ResourceVo> result = new ArrayList<ResourceVo>();
		List<String> resourceCodes = dataCacheService.getSubResourceCodes(parentId);
		if (Detect.notEmpty(resourceCodes)) {
			for (String resourceCode : resourceCodes) {
				ResourceVo vo = dataCacheService.getResource(resourceCode);
				if (vo.getType() != 2) {
					result.add(vo);
				}
			}
		}
		return result;
	}
	
	@Override
	public ResourceVo getResource(String code) {
		return dataCacheService.getResource(code);
	}

	@Override
	public List<PackageCategory> getPackageCategoryList() {
		List<PackageCategory> result = dataCacheService.getPackageCategoryList();
		return result;
	}

	@Override
	public List<PackageVo> getPackageList(String parentId) {
		List<PackageVo> result = dataCacheService.getPackageList(parentId);
		return result;
	}
	
	@Override
	public PackageVo getPackage(String packageId) {
		PackageVo result = dataCacheService.getPackage(packageId);
		return result;
	}

	@Override
	public List<DataElementVo> getDataElementList(String packageId) {
		PackageVo result = dataCacheService.getPackage(packageId);
		return result.getDataElementVos();
	}
	
	@Override
	public DataFormatVo getDataFormat(String id) {
		DataFormatVo result = dataCacheService.getDataFormat(id);
		return result;
	}

    @Override
    public List<DataFormatVo> getDataFormats(List<String> dataFormatIds) {
        List<DataFormatVo> results = new ArrayList<DataFormatVo>();
        if (Detect.notEmpty(dataFormatIds)) {
            for (String dataFormatId : dataFormatIds) {
                results.add(getDataFormat(dataFormatId));
            }
        }
        return results;
    }

}
