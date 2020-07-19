package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Material;
import com.fline.form.access.model.Situation;
import com.fline.form.access.service.*;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.*;
import com.fline.form.mgmt.service.impl.vo.MaterialTempVo;
import com.fline.form.job.ItemAddJobService;
import com.fline.form.job.ItemChangeJobService;
import com.fline.form.vo.DictionaryVo;
import com.fline.yztb.vo.ItemVo;
import com.fline.form.vo.MaterialVo;
import com.fline.form.vo.SituationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("itemMgmtService")
public class ItemMgmtServiceImpl implements ItemMgmtService, Cacheable {
	@Resource
	private ItemAccessService itemAccessService;

	@Resource
	private DataCacheService dataCacheService;
	
	@Resource
	private SituationAccessService situationAccessService;
	
	@Resource
	private SituationMgmtService situationMgmtService;
	
	@Resource
	private MaterialAccessService materialAccessService;
	
	@Resource
	private DataQlItemService dataQlItemService;
	
	@Resource
	private ItemChangeJobService itemChangeJobService;
	
	@Resource
	private SynchroAccessService synchroAccessService;
	
	@Resource
	private ItemAddJobService itemAddJobService;
	
	@Resource
	private DepartmentAccessService departmentAccessService;
	
	@Override
	public Pagination<Item> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Item> page) {
		return itemAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Item item,String[] positions,String[] certTempS,String[] certTempS1) {
		Item findItem = itemAccessService.findById(item.getId());//获取到旧事项
		if(!item.getInnerCode().equals(findItem.getInnerCode())) {//事项内部编码 修改 其缺省情形的innerCode
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", findItem.getInnerCode());
			List<Situation> findSituations = situationAccessService.find(params);//获取指定code的情形
			if (Detect.notEmpty(findSituations)) {
				Situation findSituation = findSituations.get(0);
				findSituation.setCode(item.getInnerCode());
				situationAccessService.update(findSituation);
			}
		}
		itemAccessService.update(item);
		
		Map<String, Object> params = null;
		//List<Item> listItem = null;
		if(positions != null && positions.length > 0){
			params = new HashMap<>();
			params.put("ITEM_ID", item.getId());
			itemAccessService.deletePositionItem(params);
			
			for(int i=0; i < positions.length; i++){
				if(!Detect.notEmpty(positions[i])) {
					continue;
				}
				params.put("POSITION_ID", positions[i]);
				itemAccessService.createPositionItem(params);
			}
		}
		//必要
		if((certTempS != null && certTempS.length > 0)||(certTempS1 != null && certTempS1.length > 0)){
			params = new HashMap<>();
			params.put("ITEM_ID", item.getId());
			itemAccessService.deleteItemTemp(params);
			if(certTempS != null) {
				for(int i=0; i < certTempS.length; i++){
					if(!Detect.notEmpty(certTempS[i])) {
						continue;
					}
					params.put("TEMP_ID", certTempS[i]);
					params.put("IS_MUST", "1");
					itemAccessService.createItemTemp(params);
				}
			}
			if(certTempS1 != null) {
				for(int i=0; i < certTempS1.length; i++){
					if(!Detect.notEmpty(certTempS1[i])) {
						continue;
					}
					params.put("TEMP_ID", certTempS1[i]);
					params.put("IS_MUST", 0);
					itemAccessService.createItemTemp(params);
				}
			}
		}
	}

	@Override
	public void remove(Item item) {
		itemAccessService.remove(item);
		Map<String, Object> params = new HashMap<>();
		params.put("ITEM_ID", item.getId());
		params.put("itemId", item.getId());
		//itemAccessService.deletePositionItem(params);
		//itemAccessService.deleteItemTemp(params);
		situationMgmtService.clearBindRelation(params);//删除事项下对应情形->材料->证明的联系
		//删除redis缓存
		dataCacheService.removeRedis(KeyConstant.YZTB_ITEM,item.getInnerCode());
	}

	@Override
	public Item create(Item item,String[] positions,String[] certTempS,String[] certTempS1) {
		if(item.getActive().equals("true")){
			item.setActive("1");
		}else{
			item.setActive("0");
		}
		if(!Detect.notEmpty(item.getInnerCode())) {
			item.setInnerCode(UUID.randomUUID().toString());
		}
		Item resultIitem = itemAccessService.create(item);
		
		Map<String, Object> params = null;
		//List<Item> listItem = null;
		if(positions != null && positions.length > 0){
			params = new HashMap<>();
			for(int i=0; i < positions.length; i++){
				if(!Detect.notEmpty(positions[i])) {
					continue;
				}
				params.put("POSITION_ID", positions[i]);
				params.put("ITEM_ID", resultIitem.getId());
				itemAccessService.createPositionItem(params);
			}
		}
		//必要
		if(certTempS != null && certTempS.length > 0){
			/*listItem = new ArrayList<>();
			for(int i=0; i < certTempS.length; i++){
				Item items=new Item();
				items.setCertTempId(certTempS[i]);
				items.setCode(String.valueOf(item.getId()));
				listItem.add(items);
			}*/
			params = new HashMap<>();
			for(int i=0; i < certTempS.length; i++){
				if(!Detect.notEmpty(certTempS[i])) {
					continue;
				}
				params.put("TEMP_ID", certTempS[i]);
				params.put("ITEM_ID", resultIitem.getId());
				params.put("IS_MUST", 1);
				itemAccessService.createItemTemp(params);
			}
			
		}
		if(certTempS1 != null && certTempS1.length > 0){
			
			params = new HashMap<>();
			for(int i=0; i < certTempS1.length; i++){
				if(!Detect.notEmpty(certTempS1[i])) {
					continue;
				}
				params.put("TEMP_ID", certTempS1[i]);
				params.put("ITEM_ID", resultIitem.getId());
				params.put("IS_MUST", 0);
				itemAccessService.createItemTemp(params);
			}
			
		}

		//增加redis缓存
		this.createToCache(item);
		return resultIitem;
	}
	
	@Override
	public int copy(long[] itemIds,String[] positions, String deptId, String deptName) {
		List<Item> items = itemAccessService.findByIds(itemIds);
		int count = 0;
		for(Item item : items) {
			long oldId = item.getId();
			item.setId(0);
			item.setInnerCode(UUID.randomUUID().toString());
			item.setDepartmentId(deptId);
			item.setDepartmentName(deptName);
			itemAccessService.create(item);
			
			List<String> certTempS = null;//findRTemp(oldId + "",null);//
			if(certTempS != null && certTempS.size() > 0){
				Map<String,Object> params = new HashMap<>();
				for(String certId : certTempS){
					params.put("TEMP_ID", certId);
					params.put("ITEM_ID", item.getId());
					itemAccessService.createItemTemp(params);
				}
			}
			
			if(positions != null && positions.length > 0){
				Map<String,Object> params = new HashMap<>();
				for(int i=0; i < positions.length; i++){
					if(!Detect.notEmpty(positions[i])) {
						continue;
					}
					params.put("POSITION_ID", positions[i]);
					params.put("ITEM_ID", item.getId());
					itemAccessService.createPositionItem(params);
				}
			}
			count ++;
		}
		return count;
	}

	@Override
	public Item findById(long id) {
		return itemAccessService.findById(id);
	}

	@Override
	public Pagination<Item> findPaginationTable(Map<String, Object> param, Ordering order, Pagination<Item> page) {
		return itemAccessService.findPaginationTable(param, order, page);
	}

	@Override
	public List<String> findRTemp(String id,String isMust) {
		Map<String, Object> params = new HashMap<>();
		params.put("itemId", id);
		params.put("isMust", isMust);
		//itemAccessService.findRTemp(params);
		return itemAccessService.findRTemp(params);
	}

    @Override
    public List<Map<String, Object>> findRTempWithName(String id,String isMust) {
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", id);
        params.put("isMust", isMust);
        //itemAccessService.findRTemp(params);
        return itemAccessService.findRTempWithName(params);
    }

	@Override
	public String findPositionName(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ITEM_ID", id);
		List<String> listStr = itemAccessService.findPositionName(params);
		StringBuffer sbf = new StringBuffer();
		if(listStr != null && listStr.size() > 0){
			for(int i=0; i< listStr.size(); i++){
				sbf.append(listStr.get(i)+"，");
			}
			if(sbf != null){
				return sbf.toString().substring(0, sbf.toString().length()-1);
			}
		}
		
		return sbf.toString();
	}

	@Override
	public List<String> findPositionId(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ITEM_ID", id);
		//itemAccessService.findRTemp(params);
		return itemAccessService.findPositionId(params);
	}

	@Override
	public Item findItemCount(long positionId, String itemCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("positionId", positionId);
		params.put("itemCode", itemCode);
		return itemAccessService.findItemCount(params);
	}
	
	@Override
	public Item findItemCountByInner(long positionId, String innerCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("positionId", positionId);
		params.put("innerCode", innerCode);
		return itemAccessService.findItemCountByInner(params);
	}

	@Override
	public List<Item> findItemByPositionId(long positionId) {
		return itemAccessService.findItemByPositionId(positionId);
	}

	@Override
	public void createNanWeiItem(Item item) {
		Map<String, Object> params = new HashMap<>();
		params.put("active", item.getActive());
		params.put("name", item.getName());
		params.put("code", item.getCode());
		params.put("departmentId", item.getDepartmentId());
		params.put("memo", "正式使用(南威)");
		itemAccessService.createNanWeiItem(params);
	}

	@Override
	public Item findByCode(String code) {
		return itemAccessService.findByCode(code);
	}

	@Override
	public void deletePositionItem(Map<String, Object> params) {
		itemAccessService.deletePositionItem(params);
		
	}

	@Override
	public List<Item> findAll() {
		List<Item> listItem = itemAccessService.findAll();
		return listItem;
	}

	@Override
	public int findItemTempCount(String innerCode, String certCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("innerCode", innerCode);
		params.put("certCode", certCode);
		return itemAccessService.count("findItemTemp_count", params);
	}
	
	@Override
	public List<Item> findByUser(long userId) {
		return itemAccessService.findByUser(userId);
	}
	
	@Override
	public List<Item> findList(Map<String, Object> params) {
		return itemAccessService.find(params);
	}
	
	@Override
	public List<ItemVo> findListVo(Map<String, Object> params) {
		List<Item> list = itemAccessService.find(params);
		if (Detect.notEmpty(list)) {
			List<ItemVo> itemVos = new ArrayList<ItemVo>(list.size());
			for (Item item : list) {
				itemVos.add(item.toVo());
			}
			return itemVos;
		}
			
		return null;
	}

	@Override
	public Item findByInnerCode(String innerCode) {
		return itemAccessService.findByInnerCode(innerCode);
	}

    /**
     * 刷新缓存
     */
	@Override
	public void refreshCache() {
        //获取所有事项
        List<ItemVo> itemVos = itemAccessService.findAllVo();
        //放入hash表
        Map<Long, ItemVo> itemVoMap = new HashMap<>();//key事项id
        for (ItemVo itemVo : itemVos) {
            String departmentCode = itemVo.getDepartmentCode();
            departmentCode = departmentCode.length() > 11 ? departmentCode.substring(0,12) : departmentCode;
            itemVo.setDepartmentCode(departmentCode);
            itemVoMap.put(itemVo.getId(), itemVo);
        }

	    //获取所有材料
        List<MaterialTempVo> materialTempVos = materialAccessService.findMaterialTempVo();
        for (MaterialTempVo materialTempVo : materialTempVos) {
            long itemId = materialTempVo.getItemId();//事项id
            String situationCode = materialTempVo.getSituationCode();//情形编码
            //从hash表获取单个事项
            ItemVo itemVo = itemVoMap.get(itemId);
            if(itemVo == null) {//找不到跳过
                continue;
            }
            //获取事项情形hash表
            Map<String, SituationVo> situationMap = itemVo.getSituationMap();
            if(situationMap == null) {
                situationMap = new HashMap<>();
                itemVo.setSituationMap(situationMap);
            }
            //根据情形编码获取对应情形
            SituationVo situationVo = situationMap.get(situationCode);
            if(situationVo == null) {//没有则创建
                situationVo = new SituationVo();
                situationVo.setId(materialTempVo.getSituationId());
                situationVo.setCode(situationCode);
                situationVo.setName(materialTempVo.getSituationName());
                situationVo.setType(materialTempVo.getSituationType());
                situationVo.setDescribe(materialTempVo.getSituationDescribe());
                situationVo.setMaterialList(new ArrayList<>());//材料列表
                situationMap.put(situationCode, situationVo);
            }
            //创建材料
            if(Detect.notEmpty(materialTempVo.getName())) {
                MaterialVo materialVo = new MaterialVo();
                materialVo.setCode(materialTempVo.getCode());
                materialVo.setName(materialTempVo.getName());
                materialVo.setType(materialTempVo.getType());
                materialVo.setIsMust(materialTempVo.getIsMust());
                materialVo.setTips(materialTempVo.getTips());
                materialVo.setNeedUpload(materialTempVo.getNeedUpload());
                //证件模板编码列表
                String tempCode = materialTempVo.getTempCode();
                if(Detect.notEmpty(tempCode)) {
                    materialVo.setTempCodeList(Arrays.asList(tempCode.split(",")));
                }
                situationVo.getMaterialList().add(materialVo);
            }
        }
        //清空缓存
        dataCacheService.clearItem();
        dataCacheService.setItems(itemVos);
    }

	/**
	 * 将事项存入缓存中
	 */
	@Override
	public void createToCache(Item item) {
		ItemVo itemVo = item.toVo();
		Map<String,Object> param = new HashMap<>();
		Map<String, SituationVo> situationMap = new HashMap<>();
		param.put("itemId", item.getId());
		List<Situation> listSituation = this.situationAccessService.findSituationByItemId(param);
		if(Detect.notEmpty(listSituation)){
		    for (Situation situation : listSituation) {
                List<MaterialVo> materialList = new ArrayList<>();
		        param.put("situationId", situation.getId());
		        List<Material> listMaterial = this.materialAccessService.findMaterialBySId(param);
		        if(Detect.notEmpty(listMaterial)){
		            for (Material material : listMaterial) {
		                param.put("id", material.getId());
		                List<String> listTempCode = this.materialAccessService.findTempCodeByMap(param);
                        MaterialVo vo = material.toVo();
		                vo.setTempCodeList(listTempCode);
		                materialList.add(vo);
                    }
		        }
                SituationVo sVo = situation.toVo();
		        sVo.setMaterialList(materialList);
		        situationMap.put(sVo.getCode(), sVo);
            }
		}
		itemVo.setSituationMap(situationMap);
        dataCacheService.setItem(itemVo);
		//公安专区
        if(Detect.notEmpty(itemVo.getTheme()) && Detect.notEmpty(itemVo.getFormCode()) && "2".equals(itemVo.getActive())) {
            String deptCode = itemVo.getDepartmentCode();
            deptCode = deptCode.length() > 12 ? deptCode.substring(0,12) : deptCode;
            String deptThemeCode = deptCode + "_" + itemVo.getTheme();
            String alias = itemVo.getAlias();
            String name = itemVo.getName();
            String itemTheme = name + "|" + alias + ":" + itemVo.getInnerCode() + ":" + itemVo.getStarLevel();
            dataCacheService.setItemThemeKey(deptThemeCode);
            dataCacheService.setItemTheme(deptThemeCode, itemTheme);
        }
	}

	@Override
	public List<Long> findItemIdsByDeptIds(Map<String, Object> params) {
		return itemAccessService.findItemIdsByDeptIds(params);
	}

	@Override
	public List<Map<String, Object>> findRelateCounts(Map<String, Object> params) {
		return itemAccessService.findRelateCounts(params);
	}

	@Override
	public void bindFormTemp(Map<String, Object> params) {
		itemAccessService.bindFormTemp(params);
	}

	@Override
	public List<ItemVo> findItemByFormCode(String formCode) {
		List<ItemVo> listVo = new ArrayList<ItemVo>();
		if(Detect.notEmpty(formCode)) {
			List<Item> list = this.itemAccessService.findItemByFormCode(formCode);
			if (Detect.notEmpty(list)) {
				for (Item item : list) {
					listVo.add(item.toVo());
				}
			}
		}
		return listVo;
	}

	@Override
	public String copyItem(Map<String, Object> params) {
		long[] fromItemIds =  (long[]) params.get("fromItemIds");
		List<Long> copyDeptIds = (List<Long>) params.get("copyDeptIds"); // 待复制的目的部门
//		String[] copyDeptNames = (String[]) params.get("copyDeptNames");
		List<Item> fromItems = itemAccessService.findByIds(fromItemIds);
		
		Long jobDeptId = (Long) params.get("jobDeptId");//具体的部门
    	Integer jobLevel = (Integer) params.get("jobLevel");//级别 ： 省级/市级
    	String jobStr = (String) params.get("jobStr");//职责
		
    	Map<Long, String> deptNameMap = new HashMap<Long, String>(); 
		if (!Detect.notEmpty(fromItems)) {
			return "事项不存在";
		}
		if(jobDeptId !=null &&jobDeptId != 0) {//需要根据职能分配部门
    		Map<String, Object> param = new HashMap<>();
    		if(jobLevel == 3) {//地市级部门 需要配置codelike 获取改地市下的所有相关部门
    			Department department = departmentAccessService.findById(jobDeptId);
    			param.put("codeLike", department.getCode());
    		}
    		/*根据部门的memo查询相同职能的部门*/
    		if(Detect.notEmpty(jobStr)) {//部门职责
    			param.put("memo", jobStr);
    			List<Department> sameMemoDepts = departmentAccessService.findByMemo(param);
    			this.getCanInsertList(sameMemoDepts,copyDeptIds);
    		}
		}
		/*存储当前所有部门的事项中的部门名*/
		params.put("departmentIds", copyDeptIds);
		List<Map<String, Object>> deptInfos = itemAccessService.findDeptInfo(params);
		for(Map<String, Object> deptInfo : deptInfos) {
			Long departmentId = (Long) deptInfo.get("departmentId");
			if(deptNameMap.containsKey(departmentId)) {
				continue;
			}
			String deptName = (String) deptInfo.get("deptName");
			deptNameMap.put(departmentId,deptName);
		}
		
		for (Item fromItem : fromItems) {
			long fromItemId = fromItem.getId();
			Item parentItem = null;
			if(Detect.notEmpty(fromItem.getParent())) {
				parentItem = itemAccessService.findByInnerCode(fromItem.getParent());
			}
			//获取当前事项的情形->材料->证明信息
			List<Situation> situations = situationMgmtService.getSituationInfomationByItemId(fromItemId);
			for(Long copyDeptId:copyDeptIds) {
				//通过itemCode判断当前地区下是否有该事项
				if (copyDeptId == Integer.parseInt(fromItem.getDepartmentId())) {
					continue;//对数据源事项不作修改
				}
				params.put("deptIdSingle", copyDeptId);//部门ID准确匹配
				boolean needCreate = true;				//是否需要生成新的细分事项
				String parent="";						//细分事项parent的值
				List<String> codes = new LinkedList();  //条件查询的code列表
				codes.add(fromItem.getCode());
				if(parentItem != null) {
					codes.add(parentItem.getCode());
				}
				params.put("codes", codes);
				List<Item> findItems = itemAccessService.find(params);//查找是否目的部门有该事项及其细分事项
				List<Item> copytItems = new ArrayList<Item>();//待更新的事项
				if(!Detect.notEmpty(findItems)) {
					continue;
				}
				for(Item item:findItems) {
					if(parentItem != null) {
						if(item.getCode().equals(parentItem.getCode())) {//设置父级事项
							parent = item.getInnerCode();
						}else {
							needCreate = false;
							copytItems.add(item);
						}
					}else {
						needCreate = false;
						copytItems.add(item);//只更新细分出来的事项
					}
				}
				for(Item item : copytItems) {
					/*更新参数*/
					item.setParent(parent);
					item.setActive(fromItem.getActive());
					item.setName(fromItem.getName());
					item.setDeptClassify(fromItem.getDeptClassify());
					item.setExecutable(fromItem.getExecutable());
					item.setServiceId(fromItem.getServiceId());
					item.setFormCode(fromItem.getFormCode());
					item.setAffaitType(fromItem.getAffaitType());
					item.setBizType(fromItem.getBizType());
					item.setProjectNature(fromItem.getProjectNature());
					item.setApproveType(fromItem.getApproveType());
					item.setNotice(fromItem.getNotice());
					item.setHandleFrequency(fromItem.getHandleFrequency());
					item.setStarLevel(fromItem.getStarLevel());
					item.setTheme(fromItem.getTheme());
					item.setAlias(fromItem.getAlias());
					item.setOrderNum(fromItem.getOrderNum());
					itemAccessService.update(item);
					params.put("itemId", item.getId());//清除关联情形材料关系
					situationMgmtService.clearBindRelation(params);
				}
				if(needCreate) {//只有当有主事项 却需要生成子事项时执行
					Item copyItem = fromItem;//将要生成的
					String deptName = deptNameMap.get(copyDeptId);
					if(deptName == null) {//若部门名称为null则表示该部门没有任何事项
						
					}
					copyItem.setDepartmentId(""+copyDeptId);
					copyItem.setDepartmentName(deptName);
					copyItem.setInnerCode(UUID.randomUUID().toString());
					copyItem.setParent(parent);
					Item createdItem = itemAccessService.create(copyItem);
					copytItems.add(createdItem);//加入待复制的地区事项
				}
				/*复制事项情形->材料->证明*/
				if(!Detect.notEmpty(situations)) {
					continue;
				}
				Map<String, Object> materialMap = new HashMap<String, Object>();//存储缺省情形的材料Code
				for(Situation findSituation : situations) {
					for(Item item:copytItems) {//每个待复制的事项配置情形、材料、证明
						Situation situation = new Situation();
						situation.setCode(UUID.randomUUID().toString());
						if("缺省情形".equals(findSituation.getName())) {
							situation.setCode(item.getInnerCode());//缺省情形code和事项相同
						}
						situation.setName(findSituation.getName());
						situation.setItemId((int)item.getId());
						situation.setType(findSituation.getType());
						situation.setDescribe(findSituation.getDescribe());
						situation.setConfirm(findSituation.getConfirm());
						Situation createdSituation = situationAccessService.create(situation);
						List<Material> materialList = findSituation.getMaterialList();
						if(!Detect.notEmpty(findSituation.getMaterialList())) {
							continue;
						}
						for(Material material:materialList) {
							List<String> tempIds = material.getTempIds();
							Material newMaterial = new Material();
							if("缺省情形".equals(findSituation.getName())) {
								String materialCode = UUID.randomUUID().toString();
								newMaterial.setCode(materialCode);
								materialMap.put(material.getName(), materialCode);
							}
							else {//缺省情形外材料与缺省情形的材料相同
								//newMaterial.setCode((String) materialMap.get(material.getName()));
                                Object obj = materialMap.get(material.getName());
                                newMaterial.setCode(obj != null ? obj.toString() : UUID.randomUUID().toString());
                            }
							newMaterial.setName(material.getName());
							newMaterial.setSituationId((int)createdSituation.getId());
							newMaterial.setType(material.getType());
							newMaterial.setIsMust(material.getIsMust());
							Material createdMaterial = materialAccessService.create(newMaterial);
							if(!Detect.notEmpty(tempIds)) {
								continue;
							}
							for(String tempId : tempIds) {
								if(!(Detect.notEmpty(tempId) && !"null".equals(tempId))) {
									continue;
								}
							    params.put("materialId", createdMaterial.getId());
							    params.put("tempId", Long.parseLong(tempId));
							    params.put("isMust", 1);
								materialAccessService.createMaterialTemp(params);
							}
						}
					}
				}
			}
		}
		return "复制成功!";
	}
	@Override
	public Pagination<Item> findQlItemPage(Pagination<Item> page, Item item, String startTime, String endTime) {
		Pagination<Item> result = new Pagination<>();
		List<Item> items = null;
		List<Map<String, Object>> list = this.dataQlItemService.findQlItems(page, item, startTime, endTime);
		Integer count = this.dataQlItemService.findQlItemCount(item, startTime, endTime);
		// TODO 待优化
		items = this.returnItemList(list);
		result.setItems(items);
		result.setCount(count);
		return result;
	}

	/**
	 * returnItemList:设置对应字段数据. 
	 *
	 * @author 邵炜
	 * @param list
	 * @return
	 */
	private List<Item> returnItemList(List<Map<String, Object>> list){
		List<Item> items = new ArrayList<Item>();
		Map<Object, Object> dictionary = this.dataCacheService.getDictionary();
		@SuppressWarnings("unchecked")
		List<DictionaryVo> listDiry = (List<DictionaryVo>) dictionary.get("26");// 权力编码类型
		Item item = null;
		Date tongTime = null;
		String itemCode = "";
		for (Map<String, Object> map : list) {
			item = new Item();
			item.setDepartmentName((map.get("OUGUID") == null ? "无" : map.get("OUGUID")) + " / "+ 
					(map.get("bcode") == null ? "无" : map.get("bcode")));
			item.setName(map.get("NAME") ==  null ? "" : map.get("NAME").toString());
			item.setArea(map.get("LEAD_DEPT") ==  null ? "" : map.get("LEAD_DEPT").toString());
			item.setInnerCode(map.get("innerCode") ==  null ? "" : map.get("innerCode").toString());
			item.setTheme(map.get("MATERIAL_INFO") ==  null ? "" : map.get("MATERIAL_INFO").toString());
			tongTime = map.get("tong_time") == null ? null : (Date) map.get("tong_time");
			item.setAlias(tongTime == null ? "" : tongTime.toString());
			for (DictionaryVo vo : listDiry) {
				if (map.get("CODE") != null && map.get("CODE").equals(vo.getCode())) {
					itemCode = vo.getName().concat("-") + map.get("areaCode");
					break;
				}
			}
			item.setCode(itemCode);
			items.add(item);
		}
		return items;
	}

	@Override
	public void updateInnerCode(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long takeTime = this.itemChangeJobService.ItemChangeJob(startTime, endTime);
		map.put("tableName", "c_item");
		map.put("state", 1);// 同步完成
		map.put("takeTime", takeTime);
		this.synchroAccessService.updateState(map);
		// 更新缺省情形的CODE
		this.situationAccessService.updateSituationCode();
	}

	@Override
	public void synchroByInnerCodeAndTongTime(String tongTime, String innerCode) throws Exception {
		StringBuffer sb = new StringBuffer(80);
		//sb.append(" And tong_time = STR_TO_DATE('").append(tongTime).append("','%Y-%m-%d %H:%i:%s')");
		sb.append(" AND QL_INNER_CODE = '").append(innerCode).append("'");
		this.itemAddJobService.job(sb.toString());
	}

	@Override
	public void syncAllItem(String deptId) throws Exception{
        Department dept = departmentAccessService.findById(Long.parseLong(deptId));
        String term = " AND OUGUID like '" + dept.getCode() + "%'";
        itemAddJobService.cronJob(term);
    }

	@Override
	public void synchroByTerm(Item item) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(80);
		if(Detect.notEmpty(item.getInnerCode())) {
			sb.append(" AND QL_INNER_CODE = '" + item.getInnerCode() +"'");
		}
		if(Detect.notEmpty(item.getCode())) {
			Map<Object, Object> dictionary = dataCacheService.getDictionary();
			@SuppressWarnings({ "unchecked"})
			List<DictionaryVo> listDiry = (List<DictionaryVo>) dictionary.get("26");// 权力编码类型
			String code = item.getCode();
			String[] split = code.split("-");
			int length = split.length;
			if(3 == length) {
				for (DictionaryVo vo : listDiry) {
					if(vo.getName().equals(split[0])) {
						split[0] = vo.getCode();
					}
				}
				sb.append(" AND QL_KIND = '"+split[0]+"' AND QL_MAINITEM_ID = '"+split[1]+"' AND QL_SUBITEM_ID = '"+split[2]+"' ");
			}else if(4 == length) {
				for (DictionaryVo vo : listDiry) {
					if(vo.getName().equals(split[0])) {
						split[0] = vo.getCode();
					}
				}
				sb.append(" AND QL_KIND = '"+split[0]+"' AND QL_MAINITEM_ID = '"+split[1]+"' AND QL_SUBITEM_ID = '"+split[2].concat("-").concat(split[3])+"' ");
			}
		}
        try {
            this.itemAddJobService.job(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        map.put("tableName", "qlt_qlsx");
//		map.put("state", 1);// 同步完成
//		map.put("takeTime", takeTime);
//		this.synchroAccessService.updateState(map);
	}
	public void getCanInsertList(List<Department> deptList,List<Long> insertList ) {
    	/*用递归的方式添加需要新增的部门*/
    	if(Detect.notEmpty(deptList) && deptList.size() > 0) {
    		int i = 0;
    		long[] parentIdList = new long[deptList.size()];
    		List<Department> parentList = new LinkedList();
    		for(Department department :deptList) {
    			if(!insertList.contains(department.getId())) {
    				insertList.add(department.getId());
    			}
    			if(Detect.notEmpty(department.getParentId()) && !"".equals(department.getParentId())) {
    				parentIdList[i++] = Long.parseLong(department.getParentId());
    			}
    		}
    		if(Detect.notEmpty(parentIdList)) {
    			parentList = departmentAccessService.findByIds(parentIdList);
    		}
    		getCanInsertList(parentList, insertList);
    	}
    }
}
