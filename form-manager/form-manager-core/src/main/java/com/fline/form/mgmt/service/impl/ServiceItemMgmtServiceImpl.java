package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ServiceItem;
import com.fline.form.access.service.ServiceItemAccessService;
import com.fline.form.mgmt.service.ServiceItemMgmtService;
@Service("serviceItemMgmtService")
public class ServiceItemMgmtServiceImpl implements ServiceItemMgmtService {
	@Resource
	private ServiceItemAccessService serviceItemAccessService;

	@Override
	public Pagination<ServiceItem> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ServiceItem> page) {
		return serviceItemAccessService.findPagination(param, order, page);
	}

	@Override
	// public void update(ServiceItem item,String[] positions,String[]
	// certTempS) {
	// if(item.getActive().equals("true")){
	// item.setActive("1");
	// }else{
	// item.setActive("0");
	// }
	// serviceItemAccessService.update(item);
	//
	// Map<String, Object> params = null;
	// //List<ServiceItem> listServiceItem = null;
	// if(positions != null && positions.length > 0){
	// params = new HashMap<>();
	// params.put("ITEM_ID", item.getId());
	// serviceItemAccessService.deletePositionServiceItem(params);
	//
	// for(int i=0; i < positions.length; i++){
	// params.put("POSITION_ID", positions[i]);
	// serviceItemAccessService.createPositionServiceItem(params);
	// }
	// }
	// if(certTempS != null && certTempS.length > 0){
	// params = new HashMap<>();
	// params.put("ITEM_ID", item.getId());
	// serviceItemAccessService.deleteServiceItemTemp(params);
	// for(int i=0; i < certTempS.length; i++){
	// params.put("TEMP_ID", certTempS[i]);
	// serviceItemAccessService.createServiceItemTemp(params);
	// }
	//
	// }
	// }
	//
	public void updateAssociatedCerts(ServiceItem item, String[] positions,
			String[] certTempS) {
		Map<String, Object> params = new HashMap<>();
		params.put("memo", item.getMemo());
		serviceItemAccessService.deleteServiceItemTemp(params);
		if (certTempS != null && certTempS.length > 0) {
			for (int i = 0; i < certTempS.length; i++) {
				params.put("tempId", certTempS[i]);
				serviceItemAccessService.createServiceItemTemp(params);
			}

		}
	}

	// @Override
	// public void remove(ServiceItem item) {
	// serviceItemAccessService.remove(item);
	// Map<String, Object> params = new HashMap<>();
	// params.put("ITEM_ID", item.getId());
	// serviceItemAccessService.deletePositionServiceItem(params);
	// serviceItemAccessService.deleteServiceItemTemp(params);
	// }

	// @Override
	// public ServiceItem create(ServiceItem item,String[] positions,String[]
	// certTempS) {
	// if(item.getActive().equals("true")){
	// item.setActive("1");
	// }else{
	// item.setActive("0");
	// }
	// ServiceItem resultIitem = serviceItemAccessService.create(item);
	//
	// Map<String, Object> params = null;
	// //List<ServiceItem> listServiceItem = null;
	// if(positions != null && positions.length > 0){
	// params = new HashMap<>();
	// for(int i=0; i < positions.length; i++){
	// params.put("POSITION_ID", positions[i]);
	// params.put("ITEM_ID", resultIitem.getId());
	// serviceItemAccessService.createPositionServiceItem(params);
	// }
	// }
	// if(certTempS != null && certTempS.length > 0){
	// /*listServiceItem = new ArrayList<>();
	// for(int i=0; i < certTempS.length; i++){
	// ServiceItem items=new ServiceItem();
	// items.setCertTempId(certTempS[i]);
	// items.setCode(String.valueOf(item.getId()));
	// listServiceItem.add(items);
	// }*/
	// params = new HashMap<>();
	// for(int i=0; i < certTempS.length; i++){
	// params.put("TEMP_ID", certTempS[i]);
	// params.put("ITEM_ID", resultIitem.getId());
	// serviceItemAccessService.createServiceItemTemp(params);
	// }
	//
	// }
	//
	// return resultIitem;
	// }

	@Override
	public ServiceItem findById(long id) {
		return serviceItemAccessService.findById(id);
	}

	@Override
	public Pagination<ServiceItem> findPaginationTable(
			Map<String, Object> param, Ordering order,
			Pagination<ServiceItem> page) {
		Pagination<ServiceItem> pServiceItem = serviceItemAccessService
				.findPaginationTable(param, order, page);
		/*
		 * if(pServiceItem.getServiceItems() != null &&
		 * pServiceItem.getServiceItems().size() > 0){ for(int i = 0; i <
		 * pServiceItem.getServiceItems().size(); i++){
		 * pServiceItem.getServiceItems
		 * ().get(i).setPositionName(findPositionName
		 * (String.valueOf(pServiceItem.getServiceItems().get(i).getId()))); } }
		 */
		return pServiceItem;
	}

	@Override
	public List<String> findRTemp(String memo) {
		Map<String, Object> params = new HashMap<>();
		params.put("memo", memo);
		// serviceItemAccessService.findRTemp(params);
		return serviceItemAccessService.findRTemp(params);
	}

	@Override
	public String findPositionName(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ITEM_ID", id);
		List<String> listStr = serviceItemAccessService
				.findPositionName(params);
		StringBuffer sbf = new StringBuffer();
		if (listStr != null && listStr.size() > 0) {
			for (int i = 0; i < listStr.size(); i++) {
				sbf.append(listStr.get(i) + "，");
			}
			if (sbf != null) {
				return sbf.toString().substring(0, sbf.toString().length() - 1);
			}
		}

		return sbf.toString();
	}

	@Override
	public List<String> findPositionId(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ITEM_ID", id);
		// serviceItemAccessService.findRTemp(params);
		return serviceItemAccessService.findPositionId(params);
	}

	@Override
	public ServiceItem findServiceItemCount(long positionId, String itemCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("positionId", positionId);
		params.put("itemCode", itemCode);
		return serviceItemAccessService.findServiceItemCount(params);
	}

	@Override
	public List<ServiceItem> findServiceItemByPositionId(long positionId) {
		return serviceItemAccessService.findServiceItemByPositionId(positionId);
	}

	@Override
	public ServiceItem findByCode(String code) {
		return serviceItemAccessService.findByCode(code);
	}

	@Override
	public ServiceItem save(ServiceItem item) {
		return serviceItemAccessService.save(item);
	}

}
