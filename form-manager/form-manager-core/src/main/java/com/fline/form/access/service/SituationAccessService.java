package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Situation;

public interface SituationAccessService extends AbstractNamespaceAccessService<Situation> {

    /**
     * @Description :根据事项ID查找相对应的情形
     * @author : shaowei
     * @param map
     * @return
     */
    List<Situation> findSituationByItemId(Map<String, Object> map);
    
    /**
     * @Description: 根据事项Id来删除情形
     * @param map     
     * @return void   
     * @throws
     */
    void removeByItemId(Map<String,Object> params);
    
    
    /**
     * @Description :通过innercode删除相对应的情形
     * @author : shaowei
     * @param map
     * @return
     */
    void deleteSituationByInnerCode(Map<String, Object> map);
    
    /**
     * @Description: 根据事项Id列表 获取所有的情形ID
     * @param params itemIds
     * @return     
     * @return List<Long>
     */
    List<Long> findSituationIdByItemIds(Map<String,Object> params);
    
    /**
     * @Description: 根据Map删除情形
     * @param params  situationIds ： 情形的id列表
     * @return void
     */
    void removeByMap(Map<String, Object> params);
    
    /**
     * findSituationIdByInnerCode:通過内部编码查询情形ID. 
     *
     * @author 邵炜
     * @param params
     * @return
     */
    List<Long> findSituationIdByInnerCode(Map<String,Object> params);
    
    /**
     * updateSituationCode:更新缺省情形的code. 
     *
     * @author 邵炜
     */
    void updateSituationCode();
}
