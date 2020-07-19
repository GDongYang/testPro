package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Position;
import com.fline.form.mgmt.service.PositionMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionAction extends AbstractAction implements
		ModelDriven<Position> {

	private static final long serialVersionUID = 5217006702612459083L;

	private PositionMgmtService positionMgmtService;
	
	private UserSessionManagementService userSessionManagementService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;

	private Position position;
	
	private String searchName;
	
	private String searchDept;
	
	private String[] itemCodes;
	
	private long[] itemIds;

	public String[] getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(String[] itemCodes) {
		this.itemCodes = itemCodes;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchDept() {
		return searchDept;
	}

	public void setSearchDept(String searchDept) {
		this.searchDept = searchDept;
	}

	public PositionMgmtService getPositionMgmtService() {
		return positionMgmtService;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setPositionMgmtService(PositionMgmtService positionMgmtService) {
		this.positionMgmtService = positionMgmtService;
	}

	public UserSessionManagementService getUserSessionManagementService() {
		return userSessionManagementService;
	}

	public void setUserSessionManagementService(UserSessionManagementService userSessionManagementService) {
		this.userSessionManagementService = userSessionManagementService;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public long[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(long[] itemIds) {
		this.itemIds = itemIds;
	}

	public String save() {
		try {
			Position positionNew = positionMgmtService.create(position, itemCodes);
			Position lastPosition = positionMgmtService.findById(positionNew.getId());//最新更新的position数据
			positionMgmtService.createToCache(lastPosition);
			dataMap.put("msg", "新增成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "新增失败!");
		}
		return SUCCESS;
	}

	public String update() {
		try {
			positionMgmtService.update(position);
			Position lastPosition = positionMgmtService.findById(position.getId());//刚插入的position数据
			positionMgmtService.createToCache(lastPosition);
			dataMap.put("msg", "修改成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "修改失败!");
		}
		return SUCCESS;
	}

	public String remove() {
		positionMgmtService.remove(position);
		return SUCCESS;
	}

	public String findById() {
		Position position1 = positionMgmtService.findById(position.getId());
		dataMap.put("position", position1);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<Position> page = new Pagination<Position>();
		if(pageSize> 0){
			page.setIndex(pageNum);
			page.setSize(pageSize);
		}else{
			page.setIndex(1);
			page.setSize(15);
		}
		page.setCounted(true);
		Map<String, Object> param = new HashMap<String, Object>();
		if(!"".equals(searchDept)){
			param.put("searchDept", searchDept);
		}
		if(!"".equals(searchName)){
			param.put("searchName", searchName);
		}
		param.put("active", true);
		Ordering order = new Ordering();
		page = positionMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	public String findByDept() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchDept", searchDept);
		param.put("active", 1);//状态为有效
		List<Position> result = positionMgmtService.findList(param);
		dataMap.put("result", result);
		return SUCCESS;
	}
	
	public String findAll() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("active", 1);//状态为有效
		List<Position> result = positionMgmtService.findAll(param);
		dataMap.put("result", result);
		return SUCCESS;
	}
	
	public String findItems() {
		dataMap.put("result", positionMgmtService.findItems(position.getId()));
		return SUCCESS;
	}
	public String bindItems() {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("positionId", position.getId());
			params.put("itemCodes", itemCodes);
			positionMgmtService.bindItems(params);
			Position lastPosition = positionMgmtService.findById(position.getId());//更新后的的position数据
			positionMgmtService.createToCache(lastPosition);
			dataMap.put("msg", "关联成功");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "关联失败");
		}
		return SUCCESS;
	}
	@Override
	public Position getModel() {
		if (position == null) {
			position = new Position();
		}
		return position;
	}
}
