package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Material;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.form.mgmt.service.MaterialMgmtService;
import com.fline.form.mgmt.service.SituationMgmtService;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MaterialAction extends AbstractAction implements ModelDriven<Material>{
	
	private static final long serialVersionUID = 1L;
	private MaterialMgmtService materialMgmtService;
	private ItemMgmtService itemMgmtService;
	private DepartmentMgmtService departmentMgmtService;
	private SituationMgmtService situationMgmtService;
	private Material material;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private int isMust;
	private String[] certTempIdS;
	private String[] certTempIdS1;
	private String dataStr;
	private Long itemId;
	private Long departmentId;
	private String[] materialNames;
	
	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

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

	public void setMaterialMgmtService(MaterialMgmtService materialMgmtService) {
		this.materialMgmtService = materialMgmtService;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public int getIsMust() {
		return isMust;
	}

	public void setIsMust(int isMust) {
		this.isMust = isMust;
	}
	
	public String[] getCertTempIdS() {
		return certTempIdS;
	}

	public void setCertTempIdS(String[] certTempIdS) {
		this.certTempIdS = certTempIdS;
	}

	public String[] getCertTempIdS1() {
		return certTempIdS1;
	}

	public void setCertTempIdS1(String[] certTempIdS1) {
		this.certTempIdS1 = certTempIdS1;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public void setItemMgmtService(ItemMgmtService itemMgmtService) {
		this.itemMgmtService = itemMgmtService;
	}

	public void setDepartmentMgmtService(DepartmentMgmtService departmentMgmtService) {
		this.departmentMgmtService = departmentMgmtService;
	}

	public void setSituationMgmtService(SituationMgmtService situationMgmtService) {
		this.situationMgmtService = situationMgmtService;
	}

	public void setMaterialNames(String[] materialNames) {
		this.materialNames = materialNames;
	}

	public String findPage() {
		Pagination<Material> page= new Pagination<Material>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("nameLike", material.getName());
		List<Long> itemIds = null;
		List<Long> situationIds = null;
		if(material.getSituationId() != null) {//情形不为空表示是根据情形精确查询
			param.put("situationId", material.getSituationId());
		}else if(itemId != null && itemId != 0){
			//获取当前事项下的所有情形Id
			itemIds = new LinkedList();
			itemIds.add(itemId);
			param.put("itemIds",itemIds);
		    situationIds = situationMgmtService.findSituationIdByItemIds(param);			
		}else if(departmentId != 1 && departmentId != 0) {//如果不是浙江省或者缺省的id
			//获取当前部门下所有事项Id
			Map<String,Object> map = new HashMap<>();
			map.put("deptId",departmentId);
			List<Department> allDepartment = departmentMgmtService.find(map);
			//获取所有子节点
			List<String> list = new LinkedList();
			list.add("" + departmentId);									//防止传递空数据
			if(allDepartment != null) {
				for(Department d : allDepartment) {
					list.add(String.valueOf(d.getId()));
				}
			}
			map.put("deptIds", list);
			itemIds = itemMgmtService.findItemIdsByDeptIds(map);
			map.put("itemIds", itemIds);
			if(Detect.notEmpty(itemIds)) {
				situationIds = situationMgmtService.findSituationIdByItemIds(map);
			}
		}
		if((material.getSituationId() != null || Detect.notEmpty(situationIds)) || (departmentId <= 1 && itemId == 0)) {
			//只有当有确定情形或有情形数据 或者（部门ID <= 1(缺省或者是浙江省 并且 没选择具体事项)   ） 才进行查询操作
			param.put("situationIds", situationIds);
			Ordering order = new Ordering();
			page = materialMgmtService.findPagination(param, order, page);
			dataMap.put("total", page.getCount());
	        dataMap.put("rows", page.getItems());
		}
		return SUCCESS;
	}
	
	public String create() {
		materialMgmtService.create(material);
		return SUCCESS;
	}
	
	public String update() {
		materialMgmtService.update(material);
		return SUCCESS;
	}
	
	public String remove() {
		materialMgmtService.remove(material);
		return SUCCESS;
	}
	
	@Override
	public Material getModel() {
		if(material == null) {
			material= new Material();
		}
		return material;
	}
	
	public String findCertMaterial() {
		List<String> certs = materialMgmtService.findCertMaterial(material.getId(), material.getIsMust());
		dataMap.put("certs", certs);
		return SUCCESS;
	}
	
	/**
	 * @throws
	 * @Description: 为该材料绑定证明
	 * @return dataMap: msg：绑定结果   
	 */
	public String bindCertTemp() {
		try {
			materialMgmtService.updateMaterialTemp(material.getId(), certTempIdS, certTempIdS1);
			dataMap.put("msg", "关联成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "关联失败!");
		}
		return SUCCESS;
	}
	
	/**
	 * @Description: 根据情形Id获取具体的材料数据 传入参数为 situationId
	 * @return dataMap:materials 当前情形下的材料信息 
	 */
	public String findMaterialBySituationId() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("situationId", material.getSituationId());
		List<Material> materials = materialMgmtService.findMaterialBySId(map);
		dataMap.put("materials", materials);
		return SUCCESS;
	}
	
	/**
	 * @Description: 获取该事项下缺省情形的材料 也就是该事项下的所有材料
	 * @return dataMap: materials:当前缺省情形下的所有材料信息
	 */
	public String findDefaultSituationMaterials() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("itemId", itemId);
		List<Material> materials = materialMgmtService.findDefaultSituationMaterials(params);
		dataMap.put("materials", materials);
		return SUCCESS;
	}
	/**
	 * @Description: 材料批量关联模板 中 获取材料列表 
	 * @return String
	 */
	public String findMaterialList() {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("nameLike", material.getName());
		//获取材料列表
		List<Material> result = materialMgmtService.findMaterialList(params);
		dataMap.put("result", result);
		return SUCCESS;
	}
	/**
	 * @Description: 批量关联材料和证明
	 * @return 绑定结果
	 */
	public String bindMaterialsAndTemps() {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("certTempIds", certTempIdS);
		params.put("materialNames", materialNames);
		String result = materialMgmtService.bindMaterialsAndTemps(params);
		dataMap.put("msg", result);
		return SUCCESS;
	}
}
