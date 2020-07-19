/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:QlItemMgmtService.java
 * Package Name:com.fline.yztb.mgmt.service
 * Date:2019年8月12日上午10:47:00
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.mgmt.service;

import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.QlItem;

/**
 * ClassName:QlItemMgmtService 
 * Function: 权力实现业务层接口. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月12日 上午10:47:00
 * @author   邵炜
 * @version  
 * @see 	 
 */
public interface QlItemMgmtService {

	/**
	 * findPage:分页查询 
	 *
	 * @author 邵炜
	 * @param pageNum
	 * @param pageSize
	 * @param qlItem
	 * @return
	 */
	Pagination<QlItem> findPage(int pageNum, int pageSize, QlItem qlItem, String startTime, String endTime);

}

