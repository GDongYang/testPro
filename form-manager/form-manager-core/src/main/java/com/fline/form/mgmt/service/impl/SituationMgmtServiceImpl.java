package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Material;
import com.fline.form.access.model.Situation;
import com.fline.form.access.service.CertTempAccessService;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.access.service.MaterialAccessService;
import com.fline.form.access.service.SituationAccessService;
import com.fline.form.mgmt.service.SituationMgmtService;
import com.fline.form.util.CloneUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service("situationMgmtService")
public class SituationMgmtServiceImpl implements SituationMgmtService {

	@Resource
	private SituationAccessService situationAccessService;
	
	@Resource 
	private MaterialAccessService materialAccessService;
	
	@Resource
	private CertTempAccessService certTempAccessService;
	
	@Resource
	private ItemAccessService itemAccessService;
	
	/*
	 * 作为更新缺省情形开线程使用
	 */
	private class SingleUpdateDefaultSituation implements Runnable{
		private CountDownLatch latch;
		private Map<String, Object> params;
		private List<String> successDeptNames;
		private List<String> failedDeptNames;
		
		public SingleUpdateDefaultSituation(CountDownLatch latch,Map<String, Object> params,List<String> successDeptNames,List<String> failedDeptNames) {
			this.latch = latch;
			this.params = params;
			this.successDeptNames = successDeptNames;
			this.failedDeptNames = failedDeptNames;
		}
		
		@Override
		public void run() {
			Long itemId = (Long) params.get("itemId");
			Item item = itemAccessService.findById(itemId);
			try {
				List<Material> srcMaterials = (List<Material>) params.get("srcMaterials");
				Situation defaultSituation = (Situation) params.get("defaultSituation");
				params.put("itemId", itemId);
				List<Material> findMaterials = materialAccessService.findDefaultSituationMaterials(params);//获取该事项缺省情形的材料信息
				Map<Long, Long> materialMapping = new HashMap<Long, Long>();
				for(Material srcMaterial:srcMaterials) {
					long srcMaterialId = srcMaterial.getId();
					String srcMaterialName = srcMaterial.getName();
					for(Material findMaterial : findMaterials) {
						if(srcMaterialName.equals(findMaterial.getName())) {
							materialMapping.put(srcMaterialId, findMaterial.getId());
						}
					}
				}
				/*/修改situation*/
				Situation situationTemp = CloneUtil.clone(defaultSituation);
				situationTemp.setItemId(Integer.parseInt(itemId+""));
				List<Material> materials = situationTemp.getMaterialList();
				for(Material material : materials) {
					long materialId = material.getId();
					if(materialMapping.containsKey(materialId)) {
						material.setId(materialMapping.get(materialId));
					}
				}
				SituationMgmtServiceImpl.this.saveDefaultSituation(situationTemp,false);
				successDeptNames.add(item.getDepartmentName());
			}catch (Exception e) {
				failedDeptNames.add(item.getDepartmentName());
			}finally {
				latch.countDown();
			}
		}
	}
	/*
	 * 更新非缺省情形开线程使用
	 */
	private class SingleUpdateSituation implements Runnable {
		private CountDownLatch latch;
		private Map<String, Object> params;
		private List<Situation> situations;
		private List<String> successDeptNames;
		private List<String> failedDeptNames;
		
		public SingleUpdateSituation(CountDownLatch latch,Map<String, Object> params,List<Situation> situations,List<String> successDeptNames,List<String> failedDeptNames) {
			this.latch = latch;
			this.params = params;
			this.situations = situations;
			this.successDeptNames = successDeptNames;
			this.failedDeptNames = failedDeptNames;
		}
		@Override
		public void run() {
			Item needSyncItem = (Item) params.get("needSyncItem");
			try {
				List<Situation> srcItemSituationsInfos = (List<Situation>) params.get("srcItemSituationsInfos");
				List<Material> srcDefaultSituationMaterials = (List<Material>) params.get("srcDefaultSituationMaterials");
				
				Map<String,Object> situationMapping = new HashMap<>();
				params.put("itemId", needSyncItem.getId());
				List<Situation> needSycnSituations = situationAccessService.findSituationByItemId(params);
				List<Material> needSycnMaterials = materialAccessService.findDefaultSituationMaterials(params);
				for(Situation srcItemSituationInfo:srcItemSituationsInfos) {
					long srcSituationId = srcItemSituationInfo.getId();
					String srcSituationName = srcItemSituationInfo.getName();
					for(Situation needSycnSituation:needSycnSituations) {
						if(needSycnSituation.getName().equals(srcSituationName)) {
							situationMapping.put(srcSituationId + "", needSycnSituation);
						}
					}
				}
				Map<String, Object> materialMapping = new HashMap<>();
				if(!Detect.notEmpty(srcDefaultSituationMaterials)) {
					srcDefaultSituationMaterials = new ArrayList<Material>();
				}
				for(Material srcDefaultSituationMaterial :srcDefaultSituationMaterials) {
					String srcMaterialCode = srcDefaultSituationMaterial.getCode();
					String srcMaterialName = srcDefaultSituationMaterial.getName();
					for(Material needSycnMaterial:needSycnMaterials) {
						if(needSycnMaterial.getName().equals(srcMaterialName)) {
							materialMapping.put(srcMaterialCode, needSycnMaterial.getCode());
						}
					}
				}
				/*根据映射关系修改situations*/
				List<Situation> situationsTemp = CloneUtil.deepCopyList(situations);
				for(Situation situation:situationsTemp) {
					if(situation.getName().equals("缺省情形")) {
						continue;
					}
					if(situationMapping.containsKey(situation.getId() + "")) {
						long newSituationId = ((Situation)situationMapping.get(situation.getId() + "")).getId();
						situation.setId(newSituationId);
					}
					
					List<Material> materials = situation.getMaterialList();
					for(Material material :materials) {
						String newMaterialCode = (String) materialMapping.get(material.getCode());
						material.setCode(newMaterialCode);
					}
				}
				//更新
				SituationMgmtServiceImpl.this.createBindRelation(params, situationsTemp,false);
				successDeptNames.add(needSyncItem.getDepartmentName());//
			}catch (Exception e) {
				failedDeptNames.add(needSyncItem.getDepartmentName());//
			}finally {
				latch.countDown();
			}
		}
		
	}
	
	@Override
	public Pagination<Situation> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Situation> page) {
		return situationAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Situation situation) {
		situationAccessService.update(situation);
	}

	@Override
	public void remove(Situation situation) {
		situationAccessService.remove(situation);
	}

	@Override
	public Situation create(Situation situation) {
		return situationAccessService.create(situation);
	}

	@Override
	public Situation findById(long id) {
		return situationAccessService.findById(id);
	}

	@Override
	public List<Situation> findSituationByItemId(Map<String, Object> map) {
		return situationAccessService.findSituationByItemId(map);
	}

	@Override
	public void clearBindRelation(Map<String, Object> params) {
		List<Situation> situations = situationAccessService.findSituationByItemId(params); //获取当前事项下的所有情形
		if(Detect.notEmpty(situations)) {
			for(Situation situation:situations) {
				if((params.get("exceptName") != null && situation.getName().equals(params.get("exceptName")))) {//保留不被删除的情形
					continue;
				}
				long situationId = situation.getId();
				params.put("situationId", situationId);
				List<Material> materials = materialAccessService.findMaterialBySId(params);    //获取当前情形下的所有材料
				if(Detect.notEmpty(materials)) {
					List<Long> materialIds = new LinkedList<>();
					for(Material material:materials) {
						materialIds.add(material.getId());
					}
					params.put("materialIds", materialIds);
					materialAccessService.removeBySituationId(situationId);//删除当前情形下的所有材料
					materialAccessService.removeMaterialTempByMap(params); //删除该材料和证明的关联数据
				}
			}
			situationAccessService.removeByItemId(params);//删除当前事项除了保留情形外的所有情形
		}
	}

	@Override
	public Map<String, Object> createBindRelation(Map<String, Object> params,List<Situation> situations,boolean needSync) {
		Map<String, Object> result = new HashMap<>();
		long itemId = (long) params.get("itemId");
		List<Material> findMaterials = materialAccessService.findDefaultSituationMaterials(params);//获取缺省情形下的材料
		List<String> findMaterialCodes = new ArrayList<String>();
		if(Detect.notEmpty(findMaterials)) {
			for(Material findMaterial : findMaterials) {
				findMaterialCodes.add(findMaterial.getCode());
			}
		}
		if(needSync) {
			/*
			 * 如果需要同步到其他相同事项则使用多线程去执行
			 */
			Item srcItem = itemAccessService.findById(itemId);
			List<Situation> srcSituations = situationAccessService.findSituationByItemId(params);//获取该事项下的情形信息
			List<Long> sameItems = this.getSameItems(srcItem.getCode(), srcSituations, findMaterials, itemId);
			List<Situation> srcItemSituationInfo = this.getSituationInfomationByItemId(itemId);
			result = this.syncItemSituationInfo(situations, srcItemSituationInfo, sameItems);
		}
		this.updateSituations(situations,itemId);//更新情形数据 只保留新增的情形
		
		if(situations != null && situations.size() != 0) {
			for(Situation situation : situations) {
				if("缺省情形".equals(situation.getName()) || "".equals(situation.getName().replaceAll(" ", ""))) {
					continue;
				}
				long situationId = situation.getId();
				if(situationId == 0) {//表示需要新增的情形
					Situation newSituation = new Situation();
					newSituation.setItemId((int) itemId);
					newSituation.setName(situation.getName());
					newSituation.setCode(UUID.randomUUID().toString());
					newSituation.setType(situation.getType());//设置情形类别
					newSituation.setDescribe(situation.getDescribe());
					//设置为不需要被模糊匹配
					newSituation.setConfirm(2);
					Situation createdSituation = this.create(newSituation);
					situationId = createdSituation.getId();
				}
				List<Material> materialList = situation.getMaterialList();
				if(!Detect.notEmpty(situation.getMaterialList())) {
					continue;
				}
				for(Material material:materialList) {
					if(!findMaterialCodes.contains(material.getCode())) {
						continue;
					}
					List<String> tempIds = material.getTempIds();
					Material newMaterial = new Material();
					newMaterial.setCode(material.getCode());
					newMaterial.setName(material.getName());
					newMaterial.setSituationId((int)situationId);
					newMaterial.setType(material.getType());
					newMaterial.setIsMust(material.getIsMust());
					newMaterial.setNeedUpload(material.getNeedUpload());
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
		return result;
	}
	
	@Override
	public List<Situation> getSituationInfomationByItemId(Long itemId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("itemId", itemId);
		List<Situation> situations = situationAccessService.findSituationByItemId(params);//根据事项获取情形
		if(Detect.notEmpty(situations)) {
			for(Situation situation : situations) {
				params.put("situationId", situation.getId());
				List<Material> materials = materialAccessService.findMaterialBySId(params);//根据事项获取下面所有的材料
				if(!Detect.notEmpty(materials)) {
					continue;
				}
				for(Material material : materials) {
					params.put("materialId", material.getId());
					List<String> tempIds = materialAccessService.findCertMaterial(params);//根据材料获取关联的证明id
					//material.setTempIds(certs);
					List<CertTemp> certs = certTempAccessService.findByMaterial(params);//根据材料获取对应证明信息
					if(!Detect.notEmpty(certs)) {
						certs = new ArrayList<>();
					}
					material.setTemps(certs);
					material.setTempIds(tempIds);
				}
				situation.setMaterialList(materials);
			}
		}
		return situations;
	}

	@Override
	public List<Material> updateDefaultSituation(List<Material> materials, Situation situation,
			List<Long> backMaterialIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("situationId", situation.getId());
		List<Material> findMaterials = new ArrayList<Material>();
		findMaterials = materialAccessService.findMaterialBySId(map);//获取缺省情形的所有材料
		List<Long> materialIds = new ArrayList<Long>();	//存储材料id不为空的材料id 
		Iterator<Material> iterator = materials.iterator();
		while(iterator.hasNext()){
			Material material = iterator.next();
			String materialName = material.getName();
			Integer isMust = material.getIsMust();
			Long materialId = material.getId();
			int type = material.getType();
			Integer needUpload = material.getNeedUpload();
			List<String> tempIds = material.getTempIds();//材料关联的证明列表
			backMaterialIds.add(Detect.notEmpty(""+materialId) ? materialId:0);
			if(materialId == 0) {	//ID == 0表示是新加的材料
				continue;
			}
			for(Material findMaterial : findMaterials) {
				if(materialId == findMaterial.getId()) {//如果提交的材料等于查询的材料Id则表示可能需要修改操作
					if(!materialName.equals(findMaterial.getName()) || isMust != findMaterial.getIsMust()|| type != findMaterial.getType()
						||needUpload != findMaterial.getNeedUpload() ) {//更新该条材料名称
						findMaterial.setName(materialName);
						findMaterial.setIsMust(isMust);
						findMaterial.setType(type);
						findMaterial.setNeedUpload(needUpload);
						materialAccessService.update(findMaterial);
					}
					map.put("materialId", findMaterial.getId());
					materialAccessService.removeMaterialTempByMap(map);//删除旧的关联关系
					if(!Detect.notEmpty(tempIds)) {
						continue;
					}
					for(int j = 0;j < tempIds.size();j++) {
						if(!"".equals(tempIds.get(j))) { //可能存在空数据
							map.put("tempId",tempIds.get(j));
							map.put("isMust",1);
							materialAccessService.createMaterialTemp(map);//新增
						}
					}
				}
			}
			iterator.remove();//删除非新增的材料
			materialIds.add(materialId);	
		}
		if(Detect.notEmpty(findMaterials)) {
			for(Material findMaterial:findMaterials) {//如果查询出来的材料id在提交的有效材料id中就进行删除 留下的都是待删除的材料ID
				if(materialIds.contains(findMaterial.getId())) {
					materialIds.remove(findMaterial.getId());
				}else {
					materialIds.add(findMaterial.getId());//当删除全部之前的材料，则无法删除之前的材料
				}
			}
		}
		if(Detect.notEmpty(materialIds)) {//存储待删除的材料ID
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("materialIds", materialIds);
			materialAccessService.removeByMap(params);
			materialAccessService.removeMaterialTempByMap(params);
			
		}
	return materials;
	}

	@Override
	public List<Long> findSituationIdByItemIds(Map<String, Object> params) {
		return situationAccessService.findSituationIdByItemIds(params);
	}

	@Override
	public Map<String, Object> saveDefaultSituation(Situation situation,boolean needSync) {
		Map<String, Object> result = new HashMap<>();
		long itemId = situation.getItemId();
		Item item = itemAccessService.findById(itemId);
		//获取当前事项的缺省情形
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("itemId", itemId);
		map.put("name", "缺省情形");
		List<Situation> defaultSituation = situationAccessService.findSituationByItemId(map);//获取当前事项下的缺省情形
		List<Long> backMaterialIds = new LinkedList<Long>();//存储回传材料的id值
		int situationId = 0;
		if(!Detect.notEmpty(defaultSituation)) {//如果没有缺省情形则生成
			situation.setCode(item.getInnerCode());//缺省情形code与事项innercode相同
			Situation createdSituation = situationAccessService.create(situation);//生成新的情形数据
			situationId = (int) createdSituation.getId();
		}else {
			situationId = (int) defaultSituation.get(0).getId();
		}
		situation.setId(situationId);
		List<Material> materials = situation.getMaterialList();//获取提交的材料列表
		
		if (needSync) {//需要同步事项
			Map<String, Object>  params = new HashMap<String, Object>();
			params.put("itemId", itemId);
			List<Material> srcMaterials = materialAccessService.findDefaultSituationMaterials(params);
			List<Situation> srcSituations = situationAccessService.findSituationByItemId(params);
			List<Long> sameItems = this.getSameItems(item.getCode(), srcSituations, srcMaterials, itemId);//获取相同的事项列表
			result = this.syncDefaultSituationInfo(sameItems, situation, srcMaterials);
		}
		materials = this.updateDefaultSituation(materials, situation, backMaterialIds);
		if(Detect.notEmpty(materials)) {
			for(Material material : materials) {//增加新的材料数据
				if("".equals(material.getName())) {
					continue;
				}
				List<String> tempIds = material.getTempIds();
				Material newMaterial = new Material();
				newMaterial.setName(material.getName());
				newMaterial.setType(material.getType());
				newMaterial.setSituationId(situationId);
				newMaterial.setCode(UUID.randomUUID().toString());
				newMaterial.setIsMust(material.getIsMust());
				newMaterial.setNeedUpload(material.getNeedUpload());
				Material createdMaterial = materialAccessService.create(newMaterial);
				backMaterialIds.set(backMaterialIds.indexOf((long)0),createdMaterial.getId());
				if(Detect.notEmpty(tempIds)) {
					for(String tempId:tempIds) {
						if(!(Detect.notEmpty(tempId) && !"null".equals(tempId) && !"".equals(tempId))) {
							continue;
						}
						//新增材料和证明的关联关系
						map.put("materialId", createdMaterial.getId());
						map.put("tempId", Long.parseLong(tempId));
						map.put("isMust", 1);
						materialAccessService.createMaterialTemp(map);
					}
				}
			}
		}
		result.put("result", backMaterialIds);
		return result;
	}

	@Override
	public void updateSituations(List<Situation> situations,long itemId) {
		//获取当前事项下的所有的情形数据
		Map<String, Object> map = new HashMap<>();
		map.put("itemId", itemId);
		map.put("exceptName", "缺省情形");
		List<Situation> findSituations = situationAccessService.findSituationByItemId(map);
		List<Long> situationIds = new ArrayList<Long>();	//存储情形id不为空的情形id 
		Iterator<Situation> iterator = situations.iterator();
		while(iterator.hasNext()){
			Situation situation = iterator.next();
			String situationName = situation.getName();
			Integer type = situation.getType();
			String describe = situation.getDescribe();
			Long situationId = situation.getId();
			if(situationId == 0 || "缺省情形".equals(situationName)) {	//ID == 0表示是新加的情形
				continue;
			}
			for(Situation findSituation : findSituations) {
				if(situationId == findSituation.getId()) {//如果提交的情形等于查询的情形Id则表示可能需要修改操作
					if(!situationName.equals(findSituation.getName()) || findSituation.getConfirm() == 1 || type != findSituation.getType()
							|| !describe.equals(findSituation.getDescribe()) ) {
						//若名字变更或类型变更则更新该条情形
						findSituation.setName(situationName);
						findSituation.setConfirm(2);
						findSituation.setType(type);
						findSituation.setDescribe(describe);
						situationAccessService.update(findSituation);
						//TODO 更新其他的相同事项
					}
				}
			}
			situationIds.add(situationId);	//存储非新增的id
		}
		if(Detect.notEmpty(situationIds)) {//存储待删除的情形ID
			Map<String,Object> params = new HashMap<String,Object>();
			for(long situationId:situationIds) {
				params.put("situationId", situationId);
				List<Material> materials = materialAccessService.findMaterialBySId(params);
				if(Detect.notEmpty(materials)) {
					List<Long> materialIds = new LinkedList<>();
					for(Material material:materials) {
						materialIds.add(material.getId());
					}
					params.put("materialIds", materialIds);
					materialAccessService.removeBySituationId(situationId);//删除当前情形下的所有材料
					materialAccessService.removeMaterialTempByMap(params); //删除该材料和证明的关联数据
				}
			}
		}
		if(Detect.notEmpty(findSituations)) {
			for(Situation findSituation:findSituations) {//如果查询出来的情形id在提交的有效情形id中就进行删除 留下的都是待删除的材料ID
				if(situationIds.contains(findSituation.getId())) {
					situationIds.remove(findSituation.getId());
				}else {
					situationIds.add(findSituation.getId());//当删除全部之前的情形，则无法删除之前的情形
				}
			}
		}
		if(Detect.notEmpty(situationIds)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("situationIds", situationIds);
			situationAccessService.removeByMap(params);
			//TODO 删除其他的相同事项下的情形
		}
	}
	/**
	 * @Description: 获取指定事项的所有相同事项的情形列表     
	 * @param itemCode:修改源的事项Code srcSituations:修改源的事项情形列表
	 * @return 与修改事项相同的事项列表
	 */
	public List<Long> getSameItems(String itemCode,List<Situation> srcSituations,List<Material> materials,long itemId){
		Map<String, Object> params = new HashMap<>();
		params.put("situations", srcSituations);
		params.put("code", itemCode);
		params.put("count", srcSituations.size());
		List<Long> sameItems = itemAccessService.getSameItems(params);
		params.put("itemIds", sameItems);
		params.put("itemId",itemId);
		params.put("count", materials.size());
		if(Detect.notEmpty(materials)) {
			sameItems = itemAccessService.findSameDefaultSituationsMaterialItems(params);
		}else {
			sameItems = itemAccessService.findEmptyMaterialItems(params);
		}
		sameItems.remove(itemId);
		return sameItems;
	}
	
	/**
	 * @Description: 同步事项至其他部门  非缺省情形
	 * @param situations 修改后的情形信息
	 * @param srcItemSituationsInfo 数据源事项的情形信息
	 * @param itemIds 需要同步的事项Id列表
	 * @return void
	 */
	public Map<String, Object> syncItemSituationInfo(List<Situation> situations,List<Situation> srcItemSituationsInfos,List<Long> itemIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		/*对每个事项执行映射关系*/
		long[] itemArr = new long[itemIds.size()];
		int len = itemArr.length;
		for(int i = 0; i < len;i++) {
			itemArr[i] = itemIds.get(i);
		}
		List<Item> needSyncItems = itemAccessService.findByIds(itemArr);
		List<Material> srcDefaultSituationMaterials = srcItemSituationsInfos.get(0).getMaterialList();
		/*建立每个需要同步事项的situationId映射表 和 材料映射表*/
		if(Detect.notEmpty(needSyncItems) && needSyncItems.size() > 0) {
			ExecutorService pool = Executors.newFixedThreadPool(10);
			CountDownLatch latch = new CountDownLatch(needSyncItems.size());
			Vector<String> successDeptNames = new Vector<>();
			Vector<String> failedDeptNames  = new Vector<>();
			for(Item needSyncItem:needSyncItems) {
				Map<String, Object> params = new HashMap<>();
				params.put("needSyncItem", needSyncItem);
				params.put("srcDefaultSituationMaterials", srcDefaultSituationMaterials);
				params.put("srcItemSituationsInfos", srcItemSituationsInfos);
				pool.execute(new SingleUpdateSituation(latch, params, situations,successDeptNames,failedDeptNames));
			}
			try {
				latch.await();
				result.put("successDeptNames", successDeptNames);
				result.put("failedDeptNames", failedDeptNames);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				pool.shutdown();
			}
		}
		return result;
	}
	
	public Map<String, Object> syncDefaultSituationInfo(List<Long> itemIds,Situation defaultSituation,List<Material> srcMaterials) {
		Map<String, Object> result = new HashMap<>();
		/*添加材料Id映射关系*/
		ExecutorService pool = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(itemIds.size());
		Vector<String> successDeptNames = new Vector<>();
		Vector<String> failedDeptNames  = new Vector<>();
		for(Long itemId :itemIds) {
			Map<String, Object> params = new HashMap<>();
			params.put("itemId", itemId);
			params.put("defaultSituation", defaultSituation);
			params.put("srcMaterials", srcMaterials);
			pool.execute(new SingleUpdateDefaultSituation(latch, params,successDeptNames,failedDeptNames));
		}
		try {
			latch.await();
			result.put("successDeptNames", successDeptNames);
			result.put("failedDeptNames", failedDeptNames);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.shutdown();
		}
		return result;
	}
}