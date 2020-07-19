package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;
import com.fline.yztb.vo.ItemVo;

public interface ItemMgmtService {
	Pagination<Item> findPagination(Map<String, Object> param, Ordering order,
			Pagination<Item> page);

	void update(Item item,String[] positions,String[] certTempS, String[] certTempIdS1);

	void remove(Item item);

	Item create(Item item,String[] positions,String[] certTempS, String[] certTempIdS1);

	Item findById(long id);
	
	List<String> findRTemp(String id, String isMust);

    List<Map<String, Object>> findRTempWithName(String id,String isMust);
	
	List<String> findPositionId(String id);
	
	String findPositionName(String id);
	
	Pagination<Item> findPaginationTable(Map<String, Object> param, Ordering order,
			Pagination<Item> page);
	
	Item findItemCount(long positionId,String itemCode);

	List<Item> findItemByPositionId(long positionId);
	
	void createNanWeiItem(Item item);
	
	Item findByCode(String code);
	
	void deletePositionItem(Map<String, Object> params);
	
	List<Item> findAll();

	int findItemTempCount(String innerCode, String certCode);
	
	List<Item> findByUser(long userId);
	
	Item findItemCountByInner(long positionId, String innerCode);
	
	List<Item> findList(Map<String, Object> params);
	
	List<ItemVo> findListVo(Map<String, Object> params);
	
	int copy(long[] itemIds,String[] positions, String deptId, String deptName);

	Item findByInnerCode(String innerCode);
	
	void createToCache(Item item);
	
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
     * @Description: 更新事项关联的表单code 
     * @param params   itemId , formCode 
     * @return void
     */
    void bindFormTemp(Map<String, Object>params);
    
    /**
     * findItemByFormCode:根据formCode查询相关的事项信息 
     *
     * @author 邵炜
     * @param formCode
     * @return
     */
    List<ItemVo> findItemByFormCode(String formCode);
    
    /**
     * @Description: 将当前事项复制到其他地区下包括其 情形 材料 和证明
     * @param params
     * @return String 完成状态
     */
    String copyItem(Map<String, Object> params);

	/**
	 * findQlItemPage:查询权力事项库的事项信息. 
	 *
	 * @author 邵炜
	 * @param page
	 * @param item
	 * @return
	 */
	Pagination<Item> findQlItemPage(Pagination<Item> page, Item item, String startTime, String endTime);

	/**
	 * updateInnerCode:根据时间更新内部编码. 
	 *
	 * @author 邵炜
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	void updateInnerCode(String startTime, String endTime);

	/**
	 * synchroByInnerCodeAndTongTime:根据入库时间和内部编码定位事项并同步. 
	 *
	 * @author 邵炜
	 * @param tongTime
	 * @param innerCode
	 */
	void synchroByInnerCodeAndTongTime(String tongTime, String innerCode) throws Exception;

    void syncAllItem(String deptId) throws Exception;

    /**
	 * synchroByTerm:根据条件同步事项. 
	 *
	 * @author 邵炜
	 * @param item
	 */
	void synchroByTerm(Item item);
}
