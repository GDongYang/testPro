/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:DataQlItemService.java
 * Package Name:com.fline.yztb.mgmt.service
 * Date:2019年8月2日下午2:24:47
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;

/**
 * ClassName:DataQlItemService 
 * Function: 获取权力事项库的事项数据接口. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月2日 下午2:24:47
 * @author   邵炜
 * @version  
 * @see 	 
 */
public interface DataQlItemService {
	
	/**
	 * findQlItems:获取权力事项库的事项数据信息. 
	 *
	 * @author 邵炜
	 * @param pageNum
	 * @param pageSize
	 * @param item
	 * @return
	 */
	List<Map<String, Object>> findQlItems(Pagination<Item> page, Item item, String startTime, String endTime);
	
	/**
	 * findQlItemCount:获取事项的数据. 
	 *
	 * @author 邵炜
	 * @param item
	 * @return
	 */
	Integer findQlItemCount(Item item, String startTime, String endTime);

}

