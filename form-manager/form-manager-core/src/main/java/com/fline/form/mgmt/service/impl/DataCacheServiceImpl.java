package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.fline.aic.db.vo.redis.*;
import com.fline.aic.redis.mgmt.service.RedisMgmtService;
import com.fline.aic.resourcesharing.vo.CatalogVo;
import com.fline.aic.resourcesharing.vo.ResourceSharingRedisKeys;
import com.fline.aic.resourcesharing.vo.ResourceVo;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.model.Secret;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.condition.ConditionalOnRedis;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.vo.*;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;

@Conditional(ConditionalOnRedis.class)
@Service("dataCacheService")
public class DataCacheServiceImpl implements DataCacheService {
	@Resource
	private RedisMgmtService redisMgmtService;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSubCatalogIds(String catalogId) {
		List<String> catalogIds = (List<String>)redisMgmtService.hget(ResourceSharingRedisKeys.CATALOG_LIST, catalogId);
		return catalogIds;
	}

	@Override
	public CatalogVo getCatalog(String catalogId) {
		CatalogVo vo = (CatalogVo)redisMgmtService.hget(ResourceSharingRedisKeys.CATALOG, catalogId);
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSubResourceCodes(String resourceCode) {
		List<String> resourceCodes = (List<String>)redisMgmtService.hget(ResourceSharingRedisKeys.RESOURCE_CODE, resourceCode);
		return resourceCodes;
	}

	@Override
	public ResourceVo getResource(String resourceCode) {
		ResourceVo vo = (ResourceVo)redisMgmtService.hget(ResourceSharingRedisKeys.RESOURCE_SHARING, resourceCode);
		return vo;
	}
	
	@SuppressWarnings("unchecked")
	public List<PackageCategory> getPackageCategoryList() {
		List<PackageCategory> result = (List<PackageCategory>)redisMgmtService.hmget(ConstantKey.PACKAGE_CATEGORY, "0");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<PackageVo> getPackageList(String categoryCode) {
		List<PackageVo> result = (List<PackageVo>)redisMgmtService.hget(ConstantKey.PACKAGE_LIST, categoryCode);
		return result;
	}
	
	public PackageVo getPackage(String packageId) {
		PackageVo result = (PackageVo)redisMgmtService.hget(ConstantKey.PACKAGE_KEY, packageId);
		if (Detect.notEmpty(result.getDataElementVos())) {
			for (DataElementVo ele : result.getDataElementVos()) {
				ele.setPackageId(packageId);
				if (Detect.notEmpty(ele.getDataFormatID())) {
					ele.setDataFormatVo(getDataFormat(ele.getDataFormatID()));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 更新/增加 单个部门信息
	 * @param departmentVo
	 */
	@Override
	public void setDepartment(DepartmentVo departmentVo) {
		redisMgmtService.hset(KeyConstant.YZTB_DEPARTMENT, String.valueOf(departmentVo.getId()), departmentVo);
	}

    /**
     * 存入多个部门
     * @param departments
     */
	@Override
    public void setDepartments(List<DepartmentVo> departments) {
	    Map<String, Object> map = new HashMap<>();
        for (DepartmentVo dept : departments) {
            map.put(String.valueOf(dept.getId()), dept);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_DEPARTMENT, map);
    }

    /**
     * 清空部门
     */
    @Override
    public void clearDepartment() {
        redisMgmtService.del(KeyConstant.YZTB_DEPARTMENT);
    }


	/**
	 * 更新/增加 单个证件信息
	 * @param certTempVo
	 */
	@Override
	public void setCertTemp(CertTempVo certTempVo) {
		redisMgmtService.hset(KeyConstant.YZTB_CERT_TEMP, certTempVo.getCode(), certTempVo);
	}

    /**
     * 存入多个证件信息
     * @param certTemps
     */
    @Override
    public void setCertTemps(List<CertTempVo> certTemps) {
        Map<String, Object> map = new HashMap<>();
        for (CertTempVo certTemp : certTemps) {
            map.put(certTemp.getCode(), certTemp);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_CERT_TEMP, map);
    }

    /**
     * 清空证件信息
     */
    @Override
    public void clearCertTemp() {
        redisMgmtService.del(KeyConstant.YZTB_CERT_TEMP);
    }

    /**
     * @Author jibing
     * @Date 8:40 2019/5/7
     * @Description  删除单个缓存对象
     **/
	@Override
	public void removeRedis(String key, String childKey) {
		redisMgmtService.hdel(key,childKey);
	}

	/**
	 * @Author jibing
	 * @Date 8:41 2019/5/7
	 * @Description  存入业务事项信息
	 **/
	@Override
	public void setItem(ItemVo itemVo) {
		redisMgmtService.hset(KeyConstant.YZTB_ITEM,itemVo.getInnerCode(),itemVo);
	}

    /**
     * 存入多个事项
     * @param items
     */
    @Override
    public void setItems(List<ItemVo> items) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> codeMap = new HashMap<>();
        for (ItemVo item : items) {
            map.put(item.getInnerCode(), item);
            codeMap.put(item.getDepartmentCode() + "_" + item.getCode(), item.getInnerCode());
        }
        redisMgmtService.hmset(KeyConstant.YZTB_ITEM, map);
        redisMgmtService.hmset(KeyConstant.YZTB_ITEM_CODE, codeMap);
    }

    /**
     * 清空事项
     */
    @Override
    public void clearItem() {
        redisMgmtService.del(KeyConstant.YZTB_ITEM);
        redisMgmtService.del(KeyConstant.YZTB_ITEM_CODE);
    }

    /**
     * @Author jibing
     * @Date 8:41 2019/5/7
     * @Description  存入业务岗位信息
     **/
	@Override
	public void setPosition(PositionVo positionVo) {
		redisMgmtService.hset(KeyConstant.YZTB_POSITION,String.valueOf(positionVo.getId()),positionVo);
	}

    @Override
    public void setPositions(List<PositionVo> positions) {
        Map<String, Object> map = new HashMap<>();
        for (PositionVo position : positions) {
            map.put(String.valueOf(position.getId()), position);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_POSITION, map);
    }

    @Override
    public void clearPosition() {
        redisMgmtService.del(KeyConstant.YZTB_POSITION);
    }

	@Override
	public void setSealInfo(String key, SealInfo sealInfo) {
		redisMgmtService.hset(KeyConstant.YZTB_SEAL,key,sealInfo.toVo());
	}

    /**
     * 存入多个印章信息
     * @param sealInfos
     */
    @Override
    public void setSealInfos(List<SealInfoVo> sealInfos) {
        Map<String, Object> map = new HashMap<>();
        for (SealInfoVo sealInfo : sealInfos) {
            map.put(sealInfo.getCode(), sealInfo);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_SEAL, map);
    }

    /**
     * 清空印章信息
     */
    @Override
    public void clearSealInfo() {
        redisMgmtService.del(KeyConstant.YZTB_SEAL);
    }

	@Override
	public void setServiceAccount(String key, ServiceAccount serviceAccount) {
		redisMgmtService.hset(KeyConstant.YZTB_SERVICE_ACCOUNT,key,serviceAccount.toVo());
	}

    /**
     * 存入多个业务账号
     * @param serviceAccounts
     */
    @Override
    public void setServiceAccounts(List<ServiceAccountVo> serviceAccounts) {
        Map<String, Object> map = new HashMap<>();
        for (ServiceAccountVo serviceAccount : serviceAccounts) {
            map.put(serviceAccount.getCode(), serviceAccount);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_SERVICE_ACCOUNT, map);
    }

    /**
     * 清空业务账号
     */
    @Override
    public void clearServiceAccount() {
        redisMgmtService.del(KeyConstant.YZTB_SERVICE_ACCOUNT);
    }

    
    /**
     * 存入单个证件数据资源
     */
    @Override
    public void setCertResource(CertResourceVo certResource) {
    	redisMgmtService.hset(KeyConstant.YZTB_CERT_RESOURCE, certResource.getTempCode(), certResource);
    }
    /**
     * 存入多个证件数据资源
     * @param certResources
     */
    @Override
    public void setCertResources(List<CertResourceVo> certResources) {
        Map<String, Object> map = new HashMap<>();
        for (CertResourceVo certResource : certResources) {
            map.put(certResource.getTempCode(), certResource);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_CERT_RESOURCE, map);
    }

    /**
     * 清空证件数据资源
     */
    @Override
    public void clearCertResource() {
        redisMgmtService.del(KeyConstant.YZTB_CERT_RESOURCE);
    }

	@Override
	public DataFormatVo getDataFormat(String dataFormatId) {
		DataFormatVo result = (DataFormatVo)redisMgmtService.hget(ConstantKey.DATA_FORMAT, dataFormatId);
		return result;
	}
	
	/**
	 * 存入多个数据
	 */
	@Override
	public void setVichelDistricts(Map<String, Object> map) {
		 redisMgmtService.hmset(KeyConstant.YZTB_VICHEL_DISTRICT, map);
	}
	/**
	 * 清空数据
	 */
	@Override
	public void clearVichelDistrict() {
		redisMgmtService.del(KeyConstant.YZTB_VICHEL_DISTRICT);
	}
	/**
	 * 存入多个数据
	 */
	@Override
	public void setPcsDistricts(Map<String, Object> map) {
		 redisMgmtService.hmset(KeyConstant.YZTB_PCS_DISTRICT, map);
	}
	/**
	 * 清空数据
	 */
	@Override
	public void clearPcsDistrict() {
		redisMgmtService.del(KeyConstant.YZTB_PCS_DISTRICT);
	}

	@Override
	public void setSecret(Secret secret) {
		redisMgmtService.hset(KeyConstant.YZTB_SECRET,secret.getAppKey(),secret.toVo());
	}

	@Override
	public void clearSecret() {
		redisMgmtService.del(KeyConstant.YZTB_SECRET);
	}

	@Override
	public void setSecrets(List<SecretVo> secrets) {
		Map<String, Object> map = new HashMap<>();
        for (SecretVo secret : secrets) {
            map.put(secret.getAppKey(), secret);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_SECRET, map);
		
	}

	@Override
	public void setItemThemes(String deptThemeCode, List<String> itemCodes) {
	    String[] itemCodeArray = new String[itemCodes.size()];
        redisMgmtService.getJedisCluster().rpush(KeyConstant.YZTB_ITEM_THEME + "_" +deptThemeCode ,itemCodes.toArray(itemCodeArray));
    }

    @Override
    public void clearItemTheme() {
        Set<String> keys = getThemeKeys();
        for (String key : keys) {
            redisMgmtService.getJedisCluster().ltrim(KeyConstant.YZTB_ITEM_THEME + "_" + key, 1, 0);
        }
    }

    @Override
    public void setItemThemeKeys(String[] themeKeys) {
	    redisMgmtService.getJedisCluster().sadd(KeyConstant.YZTB_ITEM_THEME_KEYS, themeKeys);
    }

    @Override
    public void clearThemeKeys() {
        Set<String> keys = getThemeKeys();
        if(Detect.notEmpty(keys)) {
            redisMgmtService.getJedisCluster().srem(KeyConstant.YZTB_ITEM_THEME_KEYS, keys.toArray(new String[keys.size()]));
        }
    }

    @Override
    public Set<String> getThemeKeys() {
        return redisMgmtService.getJedisCluster().smembers(KeyConstant.YZTB_ITEM_THEME_KEYS);
    }

    @Override
    public void setItemTheme(String deptThemeCode, String itemTheme) {
        redisMgmtService.getJedisCluster().rpush(KeyConstant.YZTB_ITEM_THEME + "_" +deptThemeCode , itemTheme);
    }

    @Override
    public void setItemThemeKey(String themeKey) {
        redisMgmtService.getJedisCluster().sadd(KeyConstant.YZTB_ITEM_THEME_KEYS, themeKey);
    }

	@Override
	public void clearDictionaries() {
		redisMgmtService.del(KeyConstant.YZTB_DICTIONARIES);
	}

	@Override
	public void setDictionaries(Map<String, Object> map) {
		redisMgmtService.hmset(KeyConstant.YZTB_DICTIONARIES ,map);
	}

	@Override
	public void clearDictionary() {
		redisMgmtService.del(KeyConstant.YZTB_DICTIONARY);
	}

	@Override
	public void setDictionary(Map<String, Object> map) {
		redisMgmtService.hmset(KeyConstant.YZTB_DICTIONARY, map);
	}
	
	@Override
	public void setDeptItemCountByDay(Map<String, Object> map) {
		redisMgmtService.hset(KeyConstant.YZTB_STATISTICS, KeyConstant.DEPT_ITEM_COUNT_BYDAY, map);
	}

	@Override
	public Map<String, Object> getDeptItemCountByDay() {
		return (Map<String, Object>) redisMgmtService.hget(KeyConstant.YZTB_STATISTICS, KeyConstant.DEPT_ITEM_COUNT_BYDAY);
	}
	
	
	@Override
	public void setDayItemCountChange(Map<String, Object> map, int days) {
		String dayItemCountChange = (days == 7)?KeyConstant.WEEK_ITEM_COUNT_CHANGE:KeyConstant.MONTH_ITEM_COUNT_CHANGE;
		redisMgmtService.hset(KeyConstant.YZTB_STATISTICS,dayItemCountChange, map);
	}

	@Override
	public Map<String, Object> getDayItemCountChange(int days) {
		String dayItemCountChange = (days == 7)?KeyConstant.WEEK_ITEM_COUNT_CHANGE:KeyConstant.MONTH_ITEM_COUNT_CHANGE;
		return (Map<String, Object>) redisMgmtService.hget(KeyConstant.YZTB_STATISTICS, dayItemCountChange);
	}

	@Override
	public void setDeptItemCount(Map<String, Object> map, int type) {
		String deptItemCount = (type == 1)? KeyConstant.MONTH_DEPT_ITEM_COUNT:KeyConstant.ALL_DEPT_ITEM_COUNT;
		redisMgmtService.hset(KeyConstant.YZTB_STATISTICS, deptItemCount, map);
	}

	@Override
	public Map<String, Object> getDeptItemCount(int type) {
		String deptItemCount = (type == 1)? KeyConstant.MONTH_DEPT_ITEM_COUNT:KeyConstant.ALL_DEPT_ITEM_COUNT;
		return (Map<String, Object>) redisMgmtService.hget(KeyConstant.YZTB_STATISTICS, deptItemCount);
	}

	@Override
	public void setCityItemCount(Map<String, Object> map, int type) {
		String cityItemCount = (type == 1)? KeyConstant.MONTH_CITY_ITEM_COUNT:KeyConstant.ALL_CITY_ITEM_COUNT;
		redisMgmtService.hset(KeyConstant.YZTB_STATISTICS, cityItemCount, map);
	}

	@Override
	public Map<String, Object> getCityItemCount(int type) {
		String cityItemCount = (type == 1)? KeyConstant.MONTH_CITY_ITEM_COUNT:KeyConstant.ALL_CITY_ITEM_COUNT;
		return (Map<String, Object>) redisMgmtService.hget(KeyConstant.YZTB_STATISTICS, cityItemCount);
		
	}

	@Override
	public void setServiceItemCount(Map<String, Object> map,int type) {
		String serviceItemCount = (type == 1)?KeyConstant.CERNO_COUNT:KeyConstant.TEMP_COUNT;
		redisMgmtService.hset(KeyConstant.YZTB_STATISTICS, serviceItemCount, map);
	}

	@Override
	public Map<String, Object> getServiceItemCount(int type) {
		String serviceItemCount = (type == 1)?KeyConstant.CERNO_COUNT:KeyConstant.TEMP_COUNT;
		return (Map<String, Object>) redisMgmtService.hget(KeyConstant.YZTB_STATISTICS, serviceItemCount);
	}

	@Override
	public String getStatisticState() {
		Object o = redisMgmtService.get(KeyConstant.STATISTIC_STATE);
		return (o != null)? o.toString():null;
	}

	@Override
	public void setStatisticState(String state,int expireTime) {
		redisMgmtService.getJedisCluster().setex(KeyConstant.STATISTIC_STATE, expireTime, state);
	}

	@Override
	public boolean setStatisticLock(String requestId) {
		Long result = redisMgmtService.getJedisCluster().setnx(KeyConstant.STATISTIC_LOCK, requestId);
		if(result == 1L) {
			redisMgmtService.getJedisCluster().expire(KeyConstant.STATISTIC_LOCK, 2 * 60);
			return true;
		}
		return false;
	}

	@Override
	public String getStatisticLock() {
		Object o = redisMgmtService.get(KeyConstant.STATISTIC_LOCK);
		return (o != null)? o.toString():null;
	}
	@Override
	public void delStatisticLock() {
		redisMgmtService.getJedisCluster().del(KeyConstant.STATISTIC_LOCK);
	}

	@Override
	public void setFormPages(List<FormPageVo> formPages) {
        Map<String, Object> map = new HashMap<>();
        for (FormPageVo formPageVo : formPages) {
            map.put(formPageVo.getCode(), formPageVo);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_FORM_PAGE, map);
    }

    @Override
    public void clearFormPage() {
	    redisMgmtService.del(KeyConstant.YZTB_FORM_PAGE);
    }

	@Override
	public void setFormPage(FormPageVo formPageVo) {
		redisMgmtService.hset(KeyConstant.YZTB_FORM_PAGE, formPageVo.getCode(), formPageVo);
	}

    @Override
    public void setBaseItems(String code, List<String> baseItems) {
        String[] array = new String[baseItems.size()];
        redisMgmtService.getJedisCluster().rpush(KeyConstant.YZTB_BASE_ITEM + "_" + code, baseItems.toArray(array));
    }

    @Override
    public void clearBaseItem() {
        Set<String> keys = keys(KeyConstant.YZTB_BASE_ITEM + "*");
        for (String key : keys) {
            redisMgmtService.getJedisCluster().ltrim(key, 1, 0);
        }
    }

    @Override
    public void setAreaOrgs(List<AreaOrgVo> areaOrgs) {
        Map<String, Object> map = new HashMap<>();
        for (AreaOrgVo areaOrgVo : areaOrgs) {
            map.put(areaOrgVo.getAreaCode(), areaOrgVo);
        }
        redisMgmtService.hmset(KeyConstant.YZTB_AREA_ORG, map);
    }

    @Override
    public void clearAreaOrg() {
        redisMgmtService.del(KeyConstant.YZTB_AREA_ORG);
    }

    private Set<String> keys(String pattern) {
        Set<String> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = redisMgmtService.getJedisCluster().getClusterNodes();
        Iterator<String> iterator = clusterNodes.keySet().iterator();

        while(iterator.hasNext()) {
            String k = iterator.next();
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();

            try {
                keys.addAll(connection.keys(pattern));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return keys;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public Map<Object,Object> getDepartment() {
		return redisMgmtService.hmget(KeyConstant.YZTB_DEPARTMENT);
	}

	@Override
	public Map<Object, Object> getDictionary() {
		return redisMgmtService.hmget(KeyConstant.YZTB_DICTIONARY);
	}

	@Override
	public DepartmentVo getDepartment(long depId) {
		return (DepartmentVo) redisMgmtService.hget(KeyConstant.YZTB_DEPARTMENT, depId+"");
	}
	
	@Override
	public ItemVo getItem(String code) {
		return (ItemVo) redisMgmtService.hget(KeyConstant.YZTB_ITEM, code);
	}

	@Override
	public void setSecurityCode(String uuid, String securityCode, int expireTime) {
        redisMgmtService.getJedisCluster().setex(KeyConstant.SECURITY_CODE + uuid, expireTime, securityCode);
    }

    @Override
    public String getSecurityCode(String uuid) {
        Object obj = redisMgmtService.get(KeyConstant.SECURITY_CODE + uuid);
        return obj == null ? null : obj.toString();
    }

    @Override
    public void delSecurityCode(String uuid) {
	    redisMgmtService.getJedisCluster().del(KeyConstant.SECURITY_CODE + uuid);
    }

}
