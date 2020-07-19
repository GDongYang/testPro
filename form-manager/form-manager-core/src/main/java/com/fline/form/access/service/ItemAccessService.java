package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;
import com.fline.yztb.vo.ItemVo;

import java.util.List;
import java.util.Map;

public interface ItemAccessService extends AbstractNamespaceAccessService<Item> {
	public Pagination<Item> findPaginationTable(Map<String, Object> param, Ordering order, Pagination<Item> page);
	public void createPositionItem(Map<String, Object> param);
	public void createItemTemp(Map<String, Object> param);
	public void deletePositionItem(Map<String, Object> param);
	public void deleteItemTemp(Map<String, Object> param);
	
	public List<String> findRTemp(Map<String, Object> param);
	public List<String> findPositionId(Map<String, Object> param);
	
	public List<String> findPositionName(Map<String, Object> param);
	
	public Item findItemCount(Map<String,Object> param); 
	
	List<Item> findItemByPositionId(long positionId);
	
	public void createNanWeiItem(Map<String, Object> param);
	
	public Item findByCode(String code);
	
	List<Item> findByUser(long userId);
	
	Item findItemCountByInner(Map<String, Object> param);
	
	Item findByZh(long deptId,String itemCode);

	Item findByInnerCode(String innerCode);

    List<ItemVo> findAllVo();

    List<Map<String, Object>> findItemTemp();
    
    List<Map<String, Object>> findItemTempByMap(Map<String,Object> map);

    List<Map<String, Object>> findRTempWithName(Map<String, Object> param);
    
    /**
     * @Description :批量存入事项
     * @author : shaowei
     * @param list
     */
    void saveItemByQzk(List<Map<String, Object>> list);
    
    /**
     * @Description :查找
     * @author : shaowei
     * @param map
     * @return
     */
    List<Map<String, Object>> findIdByInnerCode(Map<String, Object> map);
    
    /**
     * @Description :获取所有的InnerCode
     * @author : shaowei
     * @return
     */
    List<String> findAllInnerCode();
    
    /**
     * @Description :查询事项的code的对应value值
     * @author : shaowei
     * @return
     */
    List<Map<String, Object>> findAllDict();
    
    /**
     * @Description :通过innercode删除事项
     * @author : shaowei
     * @return
     */
    void deleteItemByInnerCode(Map<String, Object> map);
    
    /**
      * @Description : 有问题的事项数据通过内部编码把状态改为0
      * @author : shaowei
      * @return
      */
    void updateActive(Map<String, Object> map);
    
    /**
     * @Description: 根据部门Id列表获取事项ID列表
     * @param params
     * @return List<Long>
     */
    List<Long> findItemIdsByDeptIds(Map<String, Object> params);
    
    /**
     * @Description: 根据部门ID列表获取对应的情形、材料、证明数量
     * @param itemIds:事项ID好列表
     * @return List<Map>
     */
    List<Map<String, Object>> findRelateCounts(Map<String, Object> params);
    
    /**
     * findItemNameByinnercode:查找事项名称通过innercode
     *
     * @author 邵炜
     * @param innerCode
     * @return
     */
    String findItemNameByinnercode(String innerCode);
    
    /**
     * @Description: 更新事项关联的表单code 
     * @param params   itemId , formCode 
     * @return void
     */
    void bindFormTemp(Map<String, Object> params);
    
    /**
     * findItemByFormCode:根据formCode查询相关的事项信息
     *
     * @author 邵炜
     * @param formCode
     * @return
     */
    List<Item> findItemByFormCode(String formCode);
    
    /**
     * 
     * @Description:获取事项的部门信息 部门名和部门Id
     * @return     
     * @return List<Item>
     */
    List<Map<String, Object>> findDeptInfo(Map<String, Object> params);
    
    /**
     * @Description: 获取具有与数据源有相同情形数量和情形名字的事项
     * @param code:事项的code , count:情形个数 , situationNames:情形名字列表
     * @return List<Long> 相同的事项的Id列表
     */
    List<Long> getSameItems(Map<String, Object> params);
    /**
     * @Description:  
     * @param params
     * @return List<Long> 相同的事项的Id列表
     */
    List<Long> findSameDefaultSituationsMaterialItems(Map<String, Object> params);
    
    List<Long> findEmptyMaterialItems(Map<String, Object> params);
    
    /**
     * findAllAppItem:获取所有的App事项. 
     *
     * @author 邵炜
     * @return
     */
    List<Item> findAllAppItem();
    
    /**
     * updateInnerCode:修改事项内部编码. 
     *
     * @author 邵炜
     * @param parmas
     */
    void updateInnerCode(Map<String, Object> parmas);
    
    /**
     * updteDeptNameByInnerCode:更新部门名称为空的事项信息. 
     *
     * @author 邵炜
     * @param parmas
     */
    void updteDeptNameByInnerCode(Map<String, Object> parmas);
    
    /**
     * findItemByInnerCode:查询事项信息通过innerCode. 
     *
     * @author 邵炜
     * @param parmas
     * @return
     */
    List<Item> findItemByInnerCode(Map<String, Object> parmas);
    
    
    List<Item> findItemInfoList(Map<String, Object> params);
    
	/**
	 * findOlineAppItem:检查是否是上线app事项. 
	 *
	 * @author 邵炜
	 * @return
	 */
	List<Item> findAppItem(Map<String, Object> params);

    void updateConfirmStatus(long id, Integer status);

    void updateImg(long id, byte[] img);

    Item findImg(long id);
}
