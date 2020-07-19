package com.fline.form.mgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ApproveItem;
import com.fline.form.access.model.CountryDo;
import com.fline.form.access.service.ApproveItemAccessService;
import com.fline.form.action.vo.TreeNode;
import com.fline.form.action.vo.TreeNodePage;
import com.fline.form.mgmt.service.ApproveItemMgmtService;

@Service("approveItemMgmtService")
public class ApproveItemMgmtServiceImpl implements ApproveItemMgmtService {

	@Resource
	private ApproveItemAccessService approveItemAccessService;


	@Override
	public Pagination<ApproveItem> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ApproveItem> page) {
		return approveItemAccessService.findPagination(param, order, page);
	}

	@Override
	// public void update(ApproveItem item,String[] positions,String[]
	// certTempS) {
	// if(item.getActive().equals("true")){
	// item.setActive("1");
	// }else{
	// item.setActive("0");
	// }
	// approveItemAccessService.update(item);
	//
	// Map<String, Object> params = null;
	// //List<ApproveItem> listApproveItem = null;
	// if(positions != null && positions.length > 0){
	// params = new HashMap<>();
	// params.put("ITEM_ID", item.getId());
	// approveItemAccessService.deletePositionApproveItem(params);
	//
	// for(int i=0; i < positions.length; i++){
	// params.put("POSITION_ID", positions[i]);
	// approveItemAccessService.createPositionApproveItem(params);
	// }
	// }
	// if(certTempS != null && certTempS.length > 0){
	// params = new HashMap<>();
	// params.put("ITEM_ID", item.getId());
	// approveItemAccessService.deleteApproveItemTemp(params);
	// for(int i=0; i < certTempS.length; i++){
	// params.put("TEMP_ID", certTempS[i]);
	// approveItemAccessService.createApproveItemTemp(params);
	// }
	//
	// }
	// }
	//
	public void updateAssociatedCerts(ApproveItem item, String[] positions,
			String[] certTempS) {
		Map<String, Object> params = new HashMap<>();
		params.put("memo", item.getMemo());
		approveItemAccessService.deleteApproveItemTemp(params);
		if (certTempS != null && certTempS.length > 0) {
			for (int i = 0; i < certTempS.length; i++) {
				params.put("tempId", certTempS[i]);
				approveItemAccessService.createApproveItemTemp(params);
			}

		}
	}

	// @Override
	// public void remove(ApproveItem item) {
	// approveItemAccessService.remove(item);
	// Map<String, Object> params = new HashMap<>();
	// params.put("ITEM_ID", item.getId());
	// approveItemAccessService.deletePositionApproveItem(params);
	// approveItemAccessService.deleteApproveItemTemp(params);
	// }

	// @Override
	// public ApproveItem create(ApproveItem item,String[] positions,String[]
	// certTempS) {
	// if(item.getActive().equals("true")){
	// item.setActive("1");
	// }else{
	// item.setActive("0");
	// }
	// ApproveItem resultIitem = approveItemAccessService.create(item);
	//
	// Map<String, Object> params = null;
	// //List<ApproveItem> listApproveItem = null;
	// if(positions != null && positions.length > 0){
	// params = new HashMap<>();
	// for(int i=0; i < positions.length; i++){
	// params.put("POSITION_ID", positions[i]);
	// params.put("ITEM_ID", resultIitem.getId());
	// approveItemAccessService.createPositionApproveItem(params);
	// }
	// }
	// if(certTempS != null && certTempS.length > 0){
	// /*listApproveItem = new ArrayList<>();
	// for(int i=0; i < certTempS.length; i++){
	// ApproveItem items=new ApproveItem();
	// items.setCertTempId(certTempS[i]);
	// items.setCode(String.valueOf(item.getId()));
	// listApproveItem.add(items);
	// }*/
	// params = new HashMap<>();
	// for(int i=0; i < certTempS.length; i++){
	// params.put("TEMP_ID", certTempS[i]);
	// params.put("ITEM_ID", resultIitem.getId());
	// approveItemAccessService.createApproveItemTemp(params);
	// }
	//
	// }
	//
	// return resultIitem;
	// }

	@Override
	public ApproveItem findById(long id) {
		return approveItemAccessService.findById(id);
	}

	@Override
	public Pagination<ApproveItem> findPaginationTable(
			Map<String, Object> param, Ordering order,
			Pagination<ApproveItem> page) {
		Pagination<ApproveItem> pApproveItem = approveItemAccessService
				.findPaginationTable(param, order, page);
		/*
		 * if(pApproveItem.getApproveItems() != null &&
		 * pApproveItem.getApproveItems().size() > 0){ for(int i = 0; i <
		 * pApproveItem.getApproveItems().size(); i++){
		 * pApproveItem.getApproveItems
		 * ().get(i).setPositionName(findPositionName
		 * (String.valueOf(pApproveItem.getApproveItems().get(i).getId()))); } }
		 */
		return pApproveItem;
	}

	@Override
	public List<String> findRTemp(String memo) {
		Map<String, Object> params = new HashMap<>();
		params.put("memo", memo);
		// approveItemAccessService.findRTemp(params);
		return approveItemAccessService.findRTemp(params);
	}

	@Override
	public String findPositionName(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ITEM_ID", id);
		List<String> listStr = approveItemAccessService
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
		// approveItemAccessService.findRTemp(params);
		return approveItemAccessService.findPositionId(params);
	}

	@Override
	public ApproveItem findApproveItemCount(long positionId, String itemCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("positionId", positionId);
		params.put("itemCode", itemCode);
		return approveItemAccessService.findApproveItemCount(params);
	}

	@Override
	public List<ApproveItem> findApproveItemByPositionId(long positionId) {
		return approveItemAccessService.findApproveItemByPositionId(positionId);
	}

	@Override
	public ApproveItem findByCode(String code) {
		return approveItemAccessService.findByCode(code);
	}

	@Override
	public ApproveItem save(ApproveItem item) {
		return approveItemAccessService.save(item);
	}

	@Override
	public TreeNode findCountry(int page, int pageSize) {
		TreeNodePage parent = new TreeNodePage();
		parent.setId(0);
		parent.setName("父节点");
		Map<String, Object> map = new HashMap<>();
		int min = (page - 1) * pageSize + 1;
		int max = page * pageSize;
		map.put("min", min);
		map.put("max", max);
		int count = approveItemAccessService.findCountrycount();
		List<CountryDo> strList = approveItemAccessService.findCountry(map);
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for (int i = 0; i < strList.size(); i++) {
			TreeNode node = new TreeNode();
			node.setName(strList.get(i).getDeptname());
			node.setpId("0");
			node.setId(Long.valueOf(strList.get(i).getDeparentId()));
			nodeList.add(node);
		}

		parent.setPage(page);
		parent.setPageSize(pageSize);
		parent.setChildren(nodeList);
		int maxPage = (count % pageSize == 0 ? count / pageSize : count
				/ pageSize + 1);
		parent.setMaxPage(maxPage);
		return parent;
	}

}
