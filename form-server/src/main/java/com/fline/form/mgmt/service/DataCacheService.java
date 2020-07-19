package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fline.form.vo.AreaOrgVo;
import com.fline.form.vo.CertResourceVo;
import com.fline.form.vo.CertTempVo;
import com.fline.form.vo.DepartmentVo;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;
import com.fline.form.vo.PositionVo;
import com.fline.form.vo.SecretVo;
import com.fline.form.vo.ServiceAccountVo;

public interface DataCacheService {

	/**
	 * @return
	 */
	ItemVo getItem(String code);

	/**
	 * @param positionId
	 * @return
	 */
	PositionVo getPosition(long positionId);

	/**
	 * @param tempCodeList
	 * 获取某个事项的所有模板信息
	 * @return
	 */
	List<CertTempVo> getItemCertTempList(List<String> tempCodeList);

	/**
	 * @param tempCode
	 * @return
	 */
	CertTempVo getCertTemp(String tempCode);

    FormPageVo getFormPage(String code);

    /**
	 * @param depId
	 * @return
	 */
	DepartmentVo getDepartment(long depId);

	/**
	 * @param accountCode
	 * @return
	 */
	ServiceAccountVo getServiceAccount(String accountCode);

    List<String> getThemeItem(String deptThemeCode);

    Set<String> getThemeKeys();

    CertResourceVo getCertResource(String resourceCode);

    /**
	 * 获取密钥
	 * @param appKey
	 * @return
	 */
	SecretVo getSecret(String appKey);
	
	/**
	 * 更新密钥
	 * @param secret
	 * @return
	 */
	SecretVo updateSecret(SecretVo secret);

    boolean exists(String key);

    void pexpireAt(String key, long expireTime);

    long incr(String key);
    
    Map<Object, Object> getDictionary();

    void setToken(String token, int expireTime);

    String getToken();

    boolean setTokenLock(String requestId);

    void delTokenLock(String requestId);

    long decr(String key);

    void delTokenReadCount();

    long getTokenReadCount();

    Map<Object, Object> getVichelDistrict();
    
    Map<Object, Object> getPcsDistrict();
    
    Map<Object, Object> getJgDictionaries();
    
    Map<Object, Object> getCarType();
    
    Map<Object, Object> getAllDictionary();

    List<String> getBaseItem(String code);

    AreaOrgVo getAreaOrg(String areaCode);

    String getItemInnerCode(String orgCode, String itemCode);
}
