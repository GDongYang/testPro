package com.fline.form.mgmt.service.impl;


import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.yztb.access.model.CertResource;
import com.fline.form.access.service.CertResourceAccessService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.CertResourceMgmtService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.vo.CertResourceFieldVo;
import com.fline.form.vo.CertResourceVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 
 * @author wangn
 * 2019-4-28
 * r_cert_resource表mgmt层实现类
 *
 */

@Service("certResourceMgmtService")
public class CertResourceMgmtServiceImpl implements CertResourceMgmtService, Cacheable {
	
	
	@Resource
	private CertResourceAccessService certResourceAccessService;
	@Resource
	private DataCacheService dataCacheService;

	/**
	 * find方法查找
	 */
	@Override
	public Pagination<CertResource> findPagination(Map<String, Object> param, Ordering order, Pagination<CertResource> page) {
		return certResourceAccessService.findPagination(param, order,page);
	}
	
	/**
	 * 修改数据
	 */
	@Override
	public void update(CertResource certResource) {
		certResourceAccessService.update(certResource);
	}

	/**
	 * 删除数据
	 */
	@Override
	public void remove(CertResource certResource) {
		certResourceAccessService.remove(certResource);
	}
	
	/**
	 * 添加数据
	 */
	@Override
	public void create(CertResource certResource) {
		certResourceAccessService.create(certResource);
		
	}

    @Override
    public void refreshCache() {
	    //清空
        dataCacheService.clearCertResource();
        //获取全部
        List<CertResource> certResources = certResourceAccessService.findAll();
        //根据code分组
        Map<String, CertResourceVo> resourceVoMap = new HashMap<>();
        for (CertResource certResource : certResources) {
            String tempCode = certResource.getTempCode();
            CertResourceVo certResourceVo = resourceVoMap.get(tempCode);
            if(certResourceVo == null) {
                certResourceVo = new CertResourceVo();
                certResourceVo.setTempId(certResource.getTempId());
                certResourceVo.setResourceCode(certResource.getResourceCode());
                certResourceVo.setTempCode(tempCode);
                certResourceVo.setFieldList(new ArrayList<CertResourceFieldVo>());
                resourceVoMap.put(tempCode, certResourceVo);
            }
            CertResourceFieldVo fieldVo = new CertResourceFieldVo();
            fieldVo.setFieldCode(certResource.getFieldCode());
            fieldVo.setFieldName(certResource.getFieldName());
            fieldVo.setFieldPath(certResource.getFieldPath());
            fieldVo.setResourceType(certResource.getResourceType());
            certResourceVo.getFieldList().add(fieldVo);
        }
        //存入redis
        dataCacheService.setCertResources(new ArrayList<>(resourceVoMap.values()));
    }

	@Override
	public void createToCache(List<CertResource> certResources) {
		if(Detect.notEmpty(certResources)) {
			CertResourceVo certResourceVo = certResources.get(0).toVo();
			certResourceVo.setFieldList(new ArrayList<CertResourceFieldVo>());
			for(CertResource certResource:certResources) {
				CertResourceFieldVo fieldVo = new CertResourceFieldVo();
				fieldVo.setFieldCode(certResource.getFieldCode());
	            fieldVo.setFieldName(certResource.getFieldName());
	            fieldVo.setFieldPath(certResource.getFieldPath());
	            fieldVo.setResourceType(certResource.getResourceType());
	            certResourceVo.getFieldList().add(fieldVo);
			}
			 //存入redis
	        dataCacheService.setCertResource(certResourceVo);
		}
	}

    @Override
    public void cacheByForm(long formId) {
        /*刷入证件数据资源缓存*/
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tempId", formId);
        List<CertResource> certResources = certResourceAccessService.find(param);
        createToCache(certResources);
    }
}
