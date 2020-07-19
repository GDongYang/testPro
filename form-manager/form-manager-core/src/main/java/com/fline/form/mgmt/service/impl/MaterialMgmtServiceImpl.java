package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Material;
import com.fline.form.access.service.MaterialAccessService;
import com.fline.form.mgmt.service.MaterialMgmtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("materialMgmtService")
public class MaterialMgmtServiceImpl implements MaterialMgmtService {

	@Resource
	private MaterialAccessService materialAccessService;

	@Override
	public Pagination<Material> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Material> page) {
		return materialAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Material material) {
		materialAccessService.update(material);
	}

	@Override
	public void remove(Material material) {
		materialAccessService.remove(material);
	}

	@Override
	public Material create(Material material) {
		return materialAccessService.create(material);
	}

	@Override
	public Material findById(long id) {
		return materialAccessService.findById(id);
	}

	@Override
	public List<String> findCertMaterial(long materialId, int isMust) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("materialId", materialId);
		params.put("isMust", isMust);
		return materialAccessService.findCertMaterial(params);
	}

	@Override
	public void createMaterialTemp(long materialId, long tempId, int isMust) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("materialId", materialId);
		params.put("isMust", isMust);
		params.put("tempId", tempId);
		materialAccessService.createMaterialTemp(params);
	}

	@Override
	public void deleteMaterialTemp(long materialId, int isMust) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("materialId", materialId);
		params.put("isMust", isMust);
		materialAccessService.deleteMaterialTemp(params);
	}

	@Override
	public void updateMaterialTemp(long materialId, String[] certTempIdS, String[] certTempIdS1) {
		//删除关联的模板
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("materialId", materialId);
		materialAccessService.deleteMaterialTemp(params);
		//插入必要模板
		if(certTempIdS != null) {
			for(int i=0; i < certTempIdS.length; i++){
				if(!Detect.notEmpty(certTempIdS[i])) {
					continue;
				}
				params.put("isMust", "1");
				params.put("tempId", certTempIdS[i]);
				materialAccessService.createMaterialTemp(params);
			}
		}
		//插入非必要模板
		if(certTempIdS1 != null) {
			for(int i=0; i < certTempIdS1.length; i++){
				if(!Detect.notEmpty(certTempIdS1[i])) {
					continue;
				}
				params.put("isMust", "0");
				params.put("tempId", certTempIdS1[i]);
				materialAccessService.createMaterialTemp(params);
			}
		}
	}

	@Override
	public List<Material> findMaterialBySId(Map<String, Object> map) {
		return materialAccessService.findMaterialBySId(map);
	}

	@Override
	public List<Material> findDefaultSituationMaterials(Map<String, Object> params) {
		return materialAccessService.findDefaultSituationMaterials(params);
	}

	@Override
	public List<Material> findMaterialList(Map<String, Object> params) {
		return materialAccessService.findMaterialList(params);
	}

	@Override
	public String bindMaterialsAndTemps(Map<String, Object> params) {
		String result = "";
		try {
			String[] materialNames = (String[]) params.get("materialNames");
			String[] certTempIds = (String[]) params.get("certTempIds");
			List<Long> materialIds = materialAccessService.findIdsByNames(params);	//获取到符合材料名的所有材料ID号
			/*对各个证明分别关联材料*/
			if(Detect.notEmpty(certTempIds) && Detect.notEmpty(materialIds)) {
				//删除所有材料现有的关联模板
				Map<String, Object> map = new HashMap<>();
				map.put("materialIds", materialIds);
				materialAccessService.removeByMap(map);
				map.put("tempId", certTempIds[0]);
				//重新插入关联关系
				materialAccessService.insertRMaterialsTemps(map);
			}
			result = "绑定成功!";
		}catch (Exception e) {
			e.printStackTrace();
			result = "绑定失败！请重试"; 
		}
		return result;
	}
}
