package com.fline.form.mgmt.service;

import java.util.List;

import com.fline.aic.db.vo.redis.DataElementVo;
import com.fline.aic.db.vo.redis.DataFormatVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.aic.resourcesharing.vo.CatalogVo;
import com.fline.aic.resourcesharing.vo.ResourceVo;


/**
 * 
 * @author zhaoxz
 * 资源目录接口
 *
 */
public interface ResourceCatalogMgmtService {
	
	//资源目录
	List<CatalogVo> getCatalogList();
	
	//资源对象
	List<ResourceVo> getResourceList(String parentId);
	
	//资源对象
	ResourceVo getResource(String code);
	
	//业务分类
	List<PackageCategory> getPackageCategoryList();
	
	//业务对象
	List<PackageVo> getPackageList(String parentId);

	//数据元
	List<DataElementVo> getDataElementList(String parentId);
	
	DataFormatVo getDataFormat(String dataFormatId);
	
	PackageVo getPackage(String packageId);

    List<DataFormatVo> getDataFormats(List<String> dataFormatIds);
}
