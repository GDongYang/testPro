package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Material;
import com.fline.form.mgmt.service.impl.vo.MaterialTempVo;

public interface MaterialAccessService extends AbstractNamespaceAccessService<Material> {
    
    /**
     * @Description :根据情形ID查找材料信息
     * @author : shaowei
     * @param map
     * @return
     */
    List<Material> findMaterialBySId(Map<String, Object> map);
	
	/**
	 * 根据材料id查找相关联的证明code
	 * @param params
	 * @return List<String> 证明的code
	 */
	List<String> findTempCodeByMap(Map<String,Object> params);
	
	/**
	 * 获取关联的模板id
	 * @param params materialId isMust
	 * @return 关联的模板ID
	 */
	List<String> findCertMaterial(Map<String,Object> params);
	
	/**
	 * 创建模板和材料的关联信息
	 * @param params mater
	 */
	void createMaterialTemp(Map<String,Object> params);
	
	/**
	 * 删除模板和材料的关联信息 根据材料的Id
	 * @param params
	 */
	void deleteMaterialTemp(Map<String,Object> params);

    List<MaterialTempVo> findMaterialTempVo();
    
    /**
     * @throws
     * @Description: 根据情形Id删除材料 
     * @param params     
     * @return void   
     */
    void removeBySituationId(long situationId);
    
    /**
     * @throws
     * @Description: 根据Map进行动态的性的删除语句 materialIds:材料ID的List
     * @param params     
     * @return void   
     */
    void removeByMap(Map<String,Object> params);
    
    /**
     * @throws
     * @Description: 根据Map进行动态的性的删除语句 materialIds:材料ID的List
     * @param params     
     * @return void   
     */
    void removeMaterialTempByMap(Map<String,Object> params);
    
    /**
     * @throws 
     * @Description: 获取一个事项下的缺省情形的材料
     * @param params itemId : 事项id
     * @return List<Material>
     */
    List<Material> findDefaultSituationMaterials(Map<String,Object> params);
    
    
    /**
     * @Description :通过innerCode删除材料和模板的关联
     * @author : shaowei
     * @param map
     * @return
     */
    void deleteMaterialTempByInnerCode(Map<String,Object> params);
    
    /**
     * @Description :通过innerCode删除材料
     * @author : shaowei
     * @param map
     * @return
     */
    void deleteMaterialByInnerCode(Map<String,Object> params);
    
    /**
     * @Description :模糊关联材料和模板
     * @author : shaowei
     * @param map
     * @return
     */
    void createRmaterialTemp(Map<String,Object> params);
    
    /**
     * @Description :删除代理人身份证明的模板关联
     * @author : shaowei
     * @param map
     * @return
     */
    void deleteOtherCard(Map<String,Object> params);
    
    /**
     * @Description: 获取材料列表 
     * @param params nameLike: 材料的code 或者 name
     * @return List<Material> 材料列表
     */
    List<Material> findMaterialList(Map<String, Object> params);
    
    /**
     * @Description: 根据材料名称列表获取材料的ID号
     * @param params materialNames : 材料名称列表
     * @return List<Long> id 列表
     */
    List<Long> findIdsByNames(Map<String, Object> params);
    
    /**
     * @Description: 插入多条关联数据
     * @param params     
     * @return void
     */
    void insertRMaterialsTemps(Map<String, Object> params);
    
    /**
     * @Description: 筛选材料ID
     * @param params
     * @return     
     * @return List<Long>
     */
    List<Long> findMaterialIds(Map<String, Object> params);
    
    /**
     * @Description: 批量更新材料和模板的关联关系
     * @param params     
     * @return void
     */
    void updateRMaterialsTemps(Map<String, Object> params);
}
