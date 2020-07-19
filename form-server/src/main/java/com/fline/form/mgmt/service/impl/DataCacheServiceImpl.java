package com.fline.form.mgmt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fline.aic.redis.mgmt.service.RedisMgmtService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.constant.KeyConstant;
import com.fline.form.vo.AreaOrgVo;
import com.fline.form.vo.CertResourceVo;
import com.fline.form.vo.CertTempVo;
import com.fline.form.vo.DepartmentVo;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;
import com.fline.form.vo.PositionVo;
import com.fline.form.vo.SecretVo;
import com.fline.form.vo.ServiceAccountVo;

@Service
public class DataCacheServiceImpl implements DataCacheService {

    private final String SET_IF_NOT_EXIST = "NX";
    private final String SET_WITH_EXPIRE_TIME = "PX";

	@Autowired
	private RedisMgmtService redisMgmtService;


	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.DataCacheService#getItem(java.lang.String)
	 */
	@Override
	public ItemVo getItem(String code) {
        Object hget = redisMgmtService.hget(KeyConstant.YZTB_ITEM, code);
        return (ItemVo) hget;
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.DataCacheService#getPosition(long)
	 */
	@Override
	public PositionVo getPosition(long positionId) {
		return (PositionVo) redisMgmtService.hget(KeyConstant.YZTB_POSITION, positionId+"");
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.DataCacheService#getItemCertTempList(java.util.List)
	 */
	@Override
	public List<CertTempVo> getItemCertTempList(List<String> tempCodeList) {
		List<CertTempVo> certTempList = new ArrayList<CertTempVo>();
		for(String tempCode : tempCodeList){
			certTempList.add((CertTempVo) redisMgmtService.hget(KeyConstant.YZTB_CERT_TEMP, tempCode));
		}
		return certTempList;
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.DataCacheService#getCertTemp(java.lang.String)
	 */
	@Override
	public CertTempVo getCertTemp(String tempCode) {
		return (CertTempVo) redisMgmtService.hget(KeyConstant.YZTB_CERT_TEMP, tempCode);
	}

	@Override
	public FormPageVo getFormPage(String code) {
        return (FormPageVo) redisMgmtService.hget(KeyConstant.YZTB_FORM_PAGE, code);
    }

	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.DataCacheService#getDepartment(long)
	 */
	@Override
	public DepartmentVo getDepartment(long depId) {
		return (DepartmentVo) redisMgmtService.hget(KeyConstant.YZTB_DEPARTMENT, depId+"");
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.DataCacheService#getServiceAccount(java.lang.String)
	 */
	@Override
	public ServiceAccountVo getServiceAccount(String accountCode) {
		return (ServiceAccountVo) redisMgmtService.hget(KeyConstant.YZTB_SERVICE_ACCOUNT, accountCode);
	}


	@Override
	public List<String> getThemeItem(String deptThemeCode) {
        return redisMgmtService.getJedisCluster().lrange(deptThemeCode, 0, -1);
    }

    @Override
    public Set<String> getThemeKeys() {
        return redisMgmtService.getJedisCluster().smembers(KeyConstant.YZTB_ITEM_THEME_KEYS);
    }

    @Override
    public CertResourceVo getCertResource(String resourceCode) {
	    return (CertResourceVo) redisMgmtService.hget(KeyConstant.YZTB_CERT_RESOURCE, resourceCode);
    }

	@Override
	public SecretVo getSecret(String appKey) {
		// TODO Auto-generated method stub
		return (SecretVo)redisMgmtService.hget(KeyConstant.YZTB_SECRET, appKey);
	}

	@Override
	public SecretVo updateSecret(SecretVo secret) {
		redisMgmtService.del(secret.getAppKey());
		redisMgmtService.hset(KeyConstant.YZTB_SECRET,secret.getAppKey(),secret);
		return (SecretVo)redisMgmtService.hget(KeyConstant.YZTB_SECRET, secret.getAppKey());
	}

	@Override
	public boolean exists(String key) {
        return redisMgmtService.exists(key);
    }

    @Override
    public void pexpireAt(String key, long expireTime) {
	    redisMgmtService.getJedisCluster().pexpireAt(key, expireTime);
    }

    @Override
    public long incr(String key) {
	    return redisMgmtService.getJedisCluster().incr(key);
    }

    @Override
    public long decr(String key) {
        return redisMgmtService.getJedisCluster().decr(key);
    }

    @Override
    public void delTokenReadCount() {
        redisMgmtService.getJedisCluster().del(KeyConstant.YZTB_TOKEN_READ_COUNT);
    }

    @Override
    public long getTokenReadCount() {
        Object o = redisMgmtService.get(KeyConstant.YZTB_TOKEN_READ_COUNT);
        return o != null ? Long.parseLong(o.toString()) : 0L;
    }

	@Override
	public Map<Object, Object> getDictionary() {
		return this.redisMgmtService.hmget(KeyConstant.YZTB_DICTIONARY);
	}

    @Override
	public void setToken(String token, int expireTime) {
	    redisMgmtService.getJedisCluster().setex(KeyConstant.YZTB_TOKEN, expireTime, token);
    }

    @Override
    public String getToken() {
        Object o = redisMgmtService.get(KeyConstant.YZTB_TOKEN);
        return o != null ? o.toString() : null;
    }

    @Override
    public boolean setTokenLock(String requestId) {
//        Long result = redisMgmtService.getJedisCluster().setnx(KeyConstant.YZTB_TOKEN_LOCK, requestId);
//        if(result == 1L) {
//            redisMgmtService.expire(KeyConstant.YZTB_TOKEN_LOCK, 2 * 60);
//            return true;
//        } else {
//            return false;
//        }
        String result = redisMgmtService.getJedisCluster().set(KeyConstant.YZTB_TOKEN_LOCK, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 2 * 60 * 1000);
        if ("OK".equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void delTokenLock(String requestId) {
	    //redisMgmtService.getJedisCluster().del(KeyConstant.YZTB_TOKEN_LOCK);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        redisMgmtService.getJedisCluster().eval(script, Collections.singletonList(KeyConstant.YZTB_TOKEN_LOCK), Collections.singletonList(requestId));	}

	@Override
	public Map<Object, Object> getVichelDistrict() {
		return this.redisMgmtService.hmget(KeyConstant.YZTB_VICHEL_DISTRICT);
	}

	@Override
	public Map<Object, Object> getPcsDistrict() {
		return this.redisMgmtService.hmget(KeyConstant.YZTB_PCS_DISTRICT);
	}

	@Override
	public Map<Object, Object> getJgDictionaries() {
		return this.redisMgmtService.hmget(KeyConstant.YZTB_DICTIONARIES);
	}

	@Override
	public Map<Object, Object> getCarType() {
		return this.redisMgmtService.hmget(KeyConstant.YZTB_DICTIONARIES);
	}

	@Override
	public Map<Object, Object> getAllDictionary() {
		return this.redisMgmtService.hmget(KeyConstant.YZTB_DICTIONARY);
	}

	@Override
    public List<String> getBaseItem(String code) {
        return redisMgmtService.getJedisCluster().lrange(code, 0, -1);
    }

    @Override
    public AreaOrgVo getAreaOrg(String areaCode) {
	    return (AreaOrgVo) redisMgmtService.hget(KeyConstant.YZTB_AREA_ORG, areaCode);
    }

    @Override
    public String getItemInnerCode(String orgCode, String itemCode) {
	    return (String) redisMgmtService.hget(KeyConstant.YZTB_ITEM_CODE, orgCode + "_" + itemCode);
    }

}
