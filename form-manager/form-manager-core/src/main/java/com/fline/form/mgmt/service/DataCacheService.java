package com.fline.form.mgmt.service;

import com.fline.aic.db.vo.redis.DataFormatVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.aic.resourcesharing.vo.CatalogVo;
import com.fline.aic.resourcesharing.vo.ResourceVo;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.model.Secret;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.vo.*;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataCacheService {

	List<String> getSubCatalogIds(String catalogId);
	
	CatalogVo getCatalog(String catalogId);

	List<String> getSubResourceCodes(String resourceCode);
	
	ResourceVo getResource(String resourceCode);
	
	List<PackageCategory> getPackageCategoryList();
	
	List<PackageVo> getPackageList(String categoryCode);
	
	PackageVo getPackage(String packageId);
	
	DataFormatVo getDataFormat(String dataFormatId);

	void setItem(ItemVo itemVo);

    void setItems(List<ItemVo> items);

    void clearItem();

	void setPosition(PositionVo positionVo);

    void setPositions(List<PositionVo> positions);

    void clearPosition();

	void setSealInfo(String key, SealInfo sealInfo);

    void setSealInfos(List<SealInfoVo> sealInfos);

    void clearSealInfo();
	
	void setServiceAccount(String key, ServiceAccount serviceAccount);

    void setServiceAccounts(List<ServiceAccountVo> serviceAccounts);

    void clearServiceAccount();
	
	void setDepartment(DepartmentVo departmentVo);

    void setDepartments(List<DepartmentVo> departments);

    void clearDepartment();
	
	void setCertTemp(CertTempVo certTempVo);

    void setCertTemps(List<CertTempVo> certTemps);

    void clearCertTemp();

    void removeRedis(String key, String childKey);
    
    void setCertResource(CertResourceVo certResource);

    void setCertResources(List<CertResourceVo> certResources);

    void clearCertResource();

	void setSecret(Secret secret);

	void clearSecret();

	void setSecrets(List<SecretVo> findAllVo);
	
    void setVichelDistricts(Map<String, Object> map);
    
    void clearVichelDistrict();
    
    void setPcsDistricts(Map<String, Object> map);
    
    void clearPcsDistrict();

    void setItemThemes(String theme, List<String> itemCodes);

    void clearItemTheme();

    void setItemThemeKeys(String[] themeKeys);

    void clearThemeKeys();

    Set<String> getThemeKeys();

    void setItemTheme(String deptThemeCode, String itemTheme);

    void setItemThemeKey(String themeKey);

    void clearDictionaries();
    
    void setDictionaries(Map<String, Object> map);
    
    void clearDictionary();
    
    void setDictionary(Map<String, Object> map);
    
    /*页面统计缓存方法*/
    void setDeptItemCountByDay(Map<String, Object> map);
    
    Map<String, Object> getDeptItemCountByDay();//饼图：统计本周事项办件的分布图
    
    void setDayItemCountChange(Map<String, Object> map,int days);
    
    Map<String,Object> getDayItemCountChange(int days);//折线图：统计本周/本月的每天的事项办件情况
    
    void setDeptItemCount(Map<String, Object> map,int type);
    
    Map<String, Object> getDeptItemCount(int type);//排名图:统计全量/本月 部门办件的排名
    
    void setCityItemCount(Map<String, Object> map,int type);
    
    Map<String, Object>getCityItemCount(int type);//排名图：统计全量/本月地区办件的排名
    
    void setServiceItemCount(Map<String, Object> map,int type);
    
    Map<String, Object> getServiceItemCount(int type);//办理事项总数
    
    String getStatisticState();
    
    void setStatisticState(String state,int expireTime);
    
    boolean setStatisticLock(String requestId);
    
    String getStatisticLock();
    
    void delStatisticLock();
    
    void setFormPage(FormPageVo formPageVo);

    void setFormPages(List<FormPageVo> formPages);

    void clearFormPage();

    void setBaseItems(String code, List<String> baseItems);

    void clearBaseItem();

    void setAreaOrgs(List<AreaOrgVo> areaOrgs);

    void clearAreaOrg();
    
    Map<Object,Object> getDepartment();
    
    Map<Object, Object> getDictionary();
    
    DepartmentVo getDepartment(long depId);
    
    ItemVo getItem(String code);

    void setSecurityCode(String uuid, String securityCode, int expireTime);

    String getSecurityCode(String uuid);

    void delSecurityCode(String uuid);
}
