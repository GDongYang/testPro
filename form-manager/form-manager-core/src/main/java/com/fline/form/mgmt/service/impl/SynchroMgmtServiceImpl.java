/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:SynchroMgmtServiceImpl.java
 * Package Name:com.fline.yztb.mgmt.service.impl
 * Date:2019年8月5日上午11:45:57
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.mgmt.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fline.form.access.model.Synchro;
import com.fline.form.access.service.SynchroAccessService;
import com.fline.form.mgmt.service.SynchroMgmtService;

/**
 * ClassName:SynchroMgmtServiceImpl 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2019年8月5日 上午11:45:57
 * @author   邵炜
 * @version  
 * @see 	 
 */
@Service("synchroMgmtService")
public class SynchroMgmtServiceImpl implements SynchroMgmtService {

	@Resource
	private SynchroAccessService synchroAccessService;
	
	@Override
	public void updateSate(Map<String, Object> map) {
		this.synchroAccessService.updateState(map);
	}

	@Override
	public Synchro findUpdateState(Map<String, Object> map) {
		return this.synchroAccessService.findUpdateState(map);
	}
	
	

}

