/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:QlItemMgmtServiceImpl.java
 * Package Name:com.fline.yztb.mgmt.service.impl
 * Date:2019年8月12日上午10:47:20
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.QlItem;
import com.fline.form.access.service.QlItemAccessService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.QlItemMgmtService;
import com.fline.form.vo.DepartmentVo;
import com.fline.form.vo.DictionaryVo;

/**
 * ClassName:QlItemMgmtServiceImpl </br>
 * Function: 权力事项业务实现层. </br>
 * Reason: TODO ADD REASON. </br>
 * Date: 2019年8月12日 上午10:47:20 </br>
 * 
 * @author 邵炜
 * @version
 * @see
 */
@Service("qlItemMgmtService")
public class QlItemMgmtServiceImpl implements QlItemMgmtService {

	private static final Logger Logger = LoggerFactory.getLogger(QlItemMgmtServiceImpl.class);

	@Resource
	private QlItemAccessService qlItemAccessService;

	@Resource
	private DataCacheService dataCacheService;

	@Override
	public Pagination<QlItem> findPage(int pageNum, int pageSize, QlItem qlItem, String startTime, String endTime) {
		Map<Object, Object> dictionary = dataCacheService.getDictionary();
		@SuppressWarnings("unchecked")
		// 权力编码类型
		List<DictionaryVo> listDiry = (List<DictionaryVo>) dictionary.get("26");
		Map<String, Object> param = new HashMap<String, Object>();
		Pagination<QlItem> page = new Pagination<QlItem>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		if (Detect.notEmpty(qlItem.getCode())) {
			if (qlItem.getCode().indexOf("-") > -1) {
				try {
					String code = qlItem.getCode();
					String[] split = code.split("-");
					int length = split.length;
					for (DictionaryVo vo : listDiry) {
						if (vo.getName().equals(split[0])) {
							param.put("qlKind", vo.getCode());
						}
					}
					param.put("qlMainitemId", split[1]);
					if (length == 3) {
						param.put("qlSubitemId", split[2]);
					} else if (length == 4) {
						param.put("qlSubitemId", split[2] + "-" + split[3]);
					}
				} catch (Exception e) {
					Logger.error("解析权力编码异常", e);
					param.put("qlItemName", qlItem.getCode());
				}
			} else {
				param.put("qlItemName", qlItem.getCode());
			}
		}
		if (qlItem.getDepartmentId() != null) {
			DepartmentVo vo = this.dataCacheService.getDepartment(qlItem.getDepartmentId());
			param.put("qlOugUId", vo.getCode());
		}
		param.put("qlInnerCode", qlItem.getQlInnerCode());
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		Ordering order = new Ordering();
		order.addDesc("tong_time");
		Pagination<QlItem> pagination = this.qlItemAccessService.findPagination(param, order, page);
		List<QlItem> items = pagination.getItems();
		if (!Detect.notEmpty(items)) {
			Logger.info("获取权力事项为空");
			return pagination;
		}
		for (QlItem item : items) {
			for (DictionaryVo dvo : listDiry) {
				if (dvo.getCode().equals(item.getQlKind())) {
					item.setCode(dvo.getName().concat("-")
							.concat(item.getQlMainitemId().concat("-").concat(item.getQlSubitemId())));
					break;
				}
			}
		}
		return pagination;
	}

}
