package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Material;

public interface MaterialMgmtService {
	Pagination<Material> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Material> page);

	void update(Material material);
	
	void updateMaterialTemp(long materialId,String[] certTempIdS,String[] certTempIdS1);
	
	void remove(Material material);

	Material create(Material material);

	Material findById(long id);
	
	List<String> findCertMaterial(long materialId,int isMust);
	
	void createMaterialTemp(long materialId,long tempId,int isMust);
	
	void deleteMaterialTemp(long materialId,int isMust);
	
	/**
	 * @Description: 根据情形id获取对应的材料数据
	 * @param map
	 * @return List<Material>   
	 * @throws
	 */
	 List<Material> findMaterialBySId(Map<String, Object> map);
	 
 	/**
     * @throws 
     * @Description: 获取一个事项下的缺省情形的材料
     * @param params itemId : 事项id
     * @return List<Material>
     */
    List<Material> findDefaultSituationMaterials(Map<String,Object> params);
    
    /**
     * @Description: 获取材料列表 
     * @param params nameLike: 材料的code 或者 name
     * @return List<Material>
     */
    List<Material> findMaterialList(Map<String, Object> params);
    
    /**
	 * @Description: 批量关联材料和证明
	 * @return 绑定结果
	 */
    String bindMaterialsAndTemps(Map<String, Object> params);
}
