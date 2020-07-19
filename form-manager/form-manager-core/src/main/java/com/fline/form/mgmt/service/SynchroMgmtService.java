/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:SynchroMgmtService.java
 * Package Name:com.fline.yztb.mgmt.service
 * Date:2019年8月5日上午11:45:27
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.mgmt.service;

import java.util.Map;

import com.fline.form.access.model.Synchro;

/**
 * ClassName:SynchroMgmtService 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月5日 上午11:45:27
 * @author   邵炜
 * @version  
 * @see 	 
 */
public interface SynchroMgmtService {
	
	/**
	 * updateSate:更新事项同步的状态. 
	 *
	 * @author 邵炜
	 * @param map
	 */
	void updateSate(Map<String, Object> map);

	/**
	 * findUpdateState:获取事项操作的信息. 
	 *
	 * @author 邵炜
	 * @param map
	 * @return
	 */
	Synchro findUpdateState(Map<String, Object> map);

}

