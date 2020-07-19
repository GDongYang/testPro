package com.fline.form.access.service;

import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Synchro;

public interface SynchroAccessService extends AbstractNamespaceAccessService<Synchro> {

	/**
     * @Description :修改endTime时间
     * @author : shaowei
     * @param map
     * @return
     */
    void updateEndTimeByTableName(Map<String, Object> map);
    
    /**
     * @Description :修改StartTime时间
     * @author : shaowei
     * @param map
     * @return
     */
    void updateStartTimeByEndTime(Map<String, Object> map);
    
    /**
     * @Description :查找开始时间
     * @author : shaowei
     * @param map
     * @return
     */
    Synchro findStartTime();
    
    /**
     * updateState:更新同步状态. 
     *
     * @author 邵炜
     * @param map
     */
    void updateState(Map<String, Object> map);

	/**
	 * findUpdateState:获取事项更新的信息. 
	 *
	 * @author 邵炜
	 * @param map
	 * @return
	 */
    Synchro findUpdateState(Map<String, Object> map);
}
