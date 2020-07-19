package com.fline.form.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Situation;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.form.mgmt.service.MaterialMgmtService;
import com.fline.form.mgmt.service.SituationMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class SituationAction extends AbstractAction implements ModelDriven<Situation>{
	
	private static final long serialVersionUID = 1L;
	private SituationMgmtService situationMgmtService;
	private Situation situation;
	private List<Situation> situations;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private ItemMgmtService itemMgmtService;
	private boolean needSync;
	//接收传递的JSON字符串
	private String defaultSituationJson;
	private String situationJson;
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
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

	public void setSituationMgmtService(SituationMgmtService situationMgmtService) {
		this.situationMgmtService = situationMgmtService;
	}

	public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}
	
	public String getDefaultSituationJson() {
		return defaultSituationJson;
	}

	public void setDefaultSituationJson(String defaultSituationJson) {
		this.defaultSituationJson = defaultSituationJson;
	}
	
	public void setMaterialMgmtService(MaterialMgmtService materialMgmtService) {
	}
	

	public String getSituationJson() {
		return situationJson;
	}

	public void setSituationJson(String situationJson) {
		this.situationJson = situationJson;
	}
	
	public List<Situation> getSituations() {
		return situations;
	}

	public void setSituations(List<Situation> situations) {
		this.situations = situations;
	}
	
	public void setItemMgmtService(ItemMgmtService itemMgmtService) {
		this.itemMgmtService = itemMgmtService;
	}

	public void setNeedSync(boolean needSync) {
		this.needSync = needSync;
	}

	public String findPage() {
		Pagination<Situation> page= new Pagination<Situation>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		page = situationMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	public String create() {
		situationMgmtService.create(situation);
		return SUCCESS;
	}
	
	public String update() {
		situationMgmtService.update(situation);
		return SUCCESS;
	}
	
	public String remove() {
		situationMgmtService.remove(situation);
		return SUCCESS;
	}
	
	/**
	 * @Description: 根据师事项Id回传具体的情形数据
	 * @return dataMap: situations:当前事项下的所有情形信息
	 */
	public String findSituationByItemId() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("itemId", situation.getItemId());
		List<Situation> situations = situationMgmtService.findSituationByItemId(map);
		dataMap.put("situations", situations);
		return SUCCESS;
	}
	/**
	 * @Description: 新建 事项-> 情形 -> 材料 -> 证件的 关联关系 不更新缺省情形
	 * @return String   
	 */
	public String createBind() {
		try {
			long startTime = System.currentTimeMillis();
			situations  = JSON.parseArray(situationJson, Situation.class);
			long itemId = situation.getItemId();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemId", itemId);
			dataMap = situationMgmtService.createBindRelation(params, situations,needSync);
			//刷新事项加入缓存
			Item item = itemMgmtService.findById(itemId);
			itemMgmtService.createToCache(item);
			dataMap.put("msg","保存成功!");
			long endTime = System.currentTimeMillis();
			System.out.println("同步时间为:" + (endTime - startTime) + " ms");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "保存失败!");
		}
		
		return SUCCESS;
	}
	
	/**
	 * @Description: 更新 事项-> 情形 -> 材料 -> 证件的 关联关系
	 * @return String   
	 */
	public String updateBind() {
		Map<String,Object> params = new HashMap<String,Object>();
		situationMgmtService.clearBindRelation(params);//清除之前的绑定关系
		//TODO 生成情形
		//TODO 生成材料
		//TODO 生成材料证明的关联关系
		return SUCCESS;
	}
	/**
	 * @throws 
	 * @Description: 保存缺省情形
	 * @return dataMap: msg:返回信息 result:返回id号列表
	 */
	public String saveDefaultSituation() {  
		try {
			situation = JSONObject.parseObject(defaultSituationJson,Situation.class);
			long itemId = situation.getItemId();
			if(!Detect.notEmpty(situation.getName())) {
				situation.setName("缺省情形");
			}
			dataMap = situationMgmtService.saveDefaultSituation(situation,needSync);
			//刷新事项加入缓存
			Item item = itemMgmtService.findById(itemId);
			itemMgmtService.createToCache(item);
			dataMap.put("msg", "保存成功!");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg","保存失败!");
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @throws 
	 * @Description: 修改情形时回传当前事项的情形 -> 材料 ->证明 等信息
	 * @return dataMap situations: json格式的 当前格式下的事项 ->材料 ->证明 等信息
	 */
	public String getSituationInfomationByItemId() {
		List<Situation> situations = situationMgmtService.getSituationInfomationByItemId((long)situation.getItemId());//根据情形 -> 材料 ->证明的关联信息
		dataMap.put("situations", situations);
		return SUCCESS;
	}
	
	@Override
	public Situation getModel() {
		if(situation == null) {
			situation= new Situation();
		}
		return situation;
	}

}
