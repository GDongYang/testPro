package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Material;
import com.fline.form.access.model.Situation;

public interface SituationMgmtService {
	Pagination<Situation> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Situation> page);

	void update(Situation situation);

	void remove(Situation situation);

	Situation create(Situation situation);

	Situation findById(long id);
	
	/**
	 * @throws
	 * @Description: 根据事项id查询对应的情形
	 * @param map(itemId)
	 * @return List<Situation>   
	 */
	List<Situation> findSituationByItemId(Map<String, Object> map);
	
	/**
	 * @throws
	 * @Description: 清除指定事项下 情形 -> 材料 -> 证明的 所有关联关系
	 * @param params     
	 * @return void   
	 */
	void clearBindRelation(Map<String,Object> params);
	
	/**
	 * @throws
	 * @Description: 建立 指定事项下 情形 -> 材料 -> 证明的 所有关联关系
	 * @param params     
	 * @return void   
	 */
	 Map<String, Object> createBindRelation(Map<String,Object> params,List<Situation> situations,boolean needSync);
	
	/**
	 * @Description: 获取一个事项下情形->材料->证明的关系
	 * @param itemId :事项ID     
	 * @List<Situation> 
	 */
	List<Situation> getSituationInfomationByItemId(Long itemId);

	/**
	 * @Description: 修改缺省情形 情形->材料->证明的关系
	 * @param materials 材料数据
	 * @param situationId 情形ID
	 * @param backMaterialIds 回传的ID列表
	 * @return List<Material> 待增加的材料
	 */
	List<Material> updateDefaultSituation(List<Material> materials, Situation situation, List<Long> backMaterialIds);
	
	/**
	 * @Description: 保存缺省情形的数据
	 * @param situation
	 * @return     
	 * @return List<Long>
	 */
	Map<String, Object> saveDefaultSituation(Situation situation,boolean needSync);
	
	/**
     * @Description: 根据事项Id列表 获取所有的情形ID
     * @param params itemIds
     * @return     
     * @return List<Long>
     */
    List<Long> findSituationIdByItemIds(Map<String,Object> params);
    
    
    /**
     * @Description: 更新情形数据 
     * @param situations     
     * @return void
     */
    void updateSituations(List<Situation> situations,long itemId);
    

}
