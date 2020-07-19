/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:SynchronTaskController.java
 * Package Name:com.fline.yztb.controller
 * Date:2019年8月13日下午3:35:37
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fline.form.job.ItemAddJobService;
import com.fline.form.job.ItemChangeJobService;
import com.fline.form.vo.ResponseResult;

/**
 * ClassName:SynchronTaskController 
 * Function: 同步定时任务接口. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月13日 下午3:35:37
 * @author   邵炜
 * @version  
 * @see 	 
 */
@RestController
@RequestMapping("/synchron")
public class SynchronTaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(SynchronTaskController.class);
	
	@Resource
	private ItemAddJobService itemAddJobService;
	
	@Resource
	private ItemChangeJobService itemChangeJobService;
	
	/**
	 * synchronItemAddJob:同步事项数据接口. 
	 *
	 * @author 邵炜
	 * @return
	 */
	@RequestMapping("/itemJob")
	public ResponseResult<String> synchronItemAddJob() {
		try {
			logger.info("开始调用事项同步接口");
			this.itemAddJobService.cronJob("");
			logger.info("结束调用事项同步接口");
			return ResponseResult.success("成功");
		} catch (Exception e) {
			logger.error("调用事项同步接口异常", e);
		}
		return ResponseResult.error("失败");
	}

	/**
	 * synchronItemUpdateJob:事项内部编码更新操作. 
	 *
	 * @author 邵炜
	 * @return
	 */
	@RequestMapping("/itemUpdate")
	public ResponseResult<String> synchronItemUpdateJob(){
		try {
			logger.info("开始调用事项更新接口");
			this.itemChangeJobService.cronJob();
			logger.info("结束调用事项更新接口");
			return ResponseResult.success("成功");
		} catch (Exception e) {
			logger.error("调用事项更新接口异常", e);
		}
		return ResponseResult.error("失败");
	}
}

