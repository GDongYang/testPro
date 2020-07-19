package com.fline.form.mgmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.BusinessItem;
import com.fline.form.access.service.BusinessItemAccessService;
import com.fline.form.mgmt.service.BusinessItemMgmtService;

@Service("businessItemMgmtService")
public class BusinessItemMgmtServiceImpl implements BusinessItemMgmtService {
	
   @Resource
   private BusinessItemAccessService businessItemAccessService;


   @Override
   public Pagination<BusinessItem> findPagination(Map<String, Object> param,
   Ordering order, Pagination<BusinessItem> page) {
      return businessItemAccessService.findPagination(param, order,page);
   }

   @Override
   public void update(BusinessItem businessItem) {
      businessItemAccessService.update(businessItem);
   }

   @Override
   public void remove(BusinessItem businessItem) {
      businessItemAccessService.remove(businessItem);
   }

   @Override
   public BusinessItem create(BusinessItem businessItem) {
      return businessItemAccessService.create(businessItem);
   }

   @Override
   public BusinessItem findById(long id) {
      return businessItemAccessService.findById(id);
   }

	@Override
	public BusinessItem findByCodeAndID(String certCode, String businessCode) {
		return businessItemAccessService.findByCodeAndID(certCode, businessCode);
	}

	@Override
	public List<BusinessItem> findByBusinessId(long businessId) {	
		return businessItemAccessService.findByBusinessId(businessId);
	}

}

