/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:DataQlItemServiceImp.java
 * Package Name:com.fline.yztb.mgmt.service.impl
 * Date:2019年8月2日下午2:29:01
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.mgmt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DataQlItemService;
import com.fline.form.vo.DepartmentVo;
import com.fline.form.vo.DictionaryVo;

/**
 * ClassName:DataQlItemServiceImp 
 * Function: 获取权力事项库的事项数据接口实现类. 
 * Reason: TODO ADD REASON
 * Date: 2019年8月2日 下午2:29:01
 * 
 * @author 邵炜
 * @version
 * @see
 */
@Service("dataQlItemService")
public class DataQlItemServiceImp implements DataQlItemService {

	// 公安前置库数据操作模板
	@Resource
	private JdbcTemplate gaqzkJdbcTemplate;
	
	// 缓存
	@Resource
	private DataCacheService dataCacheService;

	@Override
	public List<Map<String, Object>> findQlItems(Pagination<Item> page, Item item, String startTime, String endTime) {
		List<Object> list = new ArrayList<Object>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		String sql = "SELECT QL_NAME AS NAME, LEAD_DEPT, QL_INNER_CODE AS innerCode, OUGUID, QL_KIND AS CODE , "
				+ "CONCAT(QL_MAINITEM_ID, '-', QL_SUBITEM_ID) AS areaCode , BELONGXIAQUCODE "
				+ "AS bcode, MATERIAL_INFO,tong_time FROM qlt_qlsx where 1=1 ";
		StringBuffer sb = new StringBuffer(80);
		this.setFindTerm(item, startTime, endTime,list, sb);
		sb.append(" order by tong_time desc LIMIT ?, ? ");
		list.add(page.getStart());
		list.add(page.getSize());
		Object[] para = list.toArray(new Object[list.size()]);
		resultList = this.gaqzkJdbcTemplate.queryForList(sql + sb.toString(), para);
		return resultList;
	}

	@Override
	public Integer findQlItemCount(Item item, String startTime, String endTime) {
		List<Object> list = new ArrayList<Object>();
		String sqlCount = "SELECT count(*) from qlt_qlsx where 1=1 ";
		StringBuffer sb = new StringBuffer(80);
		this.setFindTerm(item, startTime, endTime, list, sb);
		Object[] para = list.toArray(new Object[list.size()]);
		Integer count = this.gaqzkJdbcTemplate.queryForObject(sqlCount + sb.toString(), para, Integer.class);
		return count == null ? 0 : count;
	}
	
	/**
	 * setFindTerm:设置查询条件. 
	 *
	 * @author 邵炜
	 * @param item
	 * @param list
	 * @param sb
	 */
	private void setFindTerm(Item item, String startTime, String endTime, List<Object> list, StringBuffer sb) {
		if (Detect.notEmpty(item.getCode())) { //
			if(item.getCode().indexOf("-") > -1) {
				Map<Object, Object> dictionary = dataCacheService.getDictionary();
				@SuppressWarnings("unchecked")
				List<DictionaryVo> listDiry = (List<DictionaryVo>) dictionary.get("26");// 权力编码类型
				sb.append(" AND QL_KIND = ? AND QL_MAINITEM_ID = ? AND QL_SUBITEM_ID = ? ");
				String code = item.getCode();
				String[] split = code.split("-");
				int length = split.length;
				for (DictionaryVo vo : listDiry) {
					if(vo.getName().equals(split[0])) {
						split[0] = vo.getCode();
					}
				}
				list.add(split[0]);
				list.add(split[1]);
				if (length == 3) {
					list.add(split[2]);
				} else if (length == 4) {
					list.add(split[2] + "-" +split[3]);
				}
			}else {
				sb.append(" AND QL_NAME = ? ");
				list.add(item.getCode());
			}
		} 
		if(Detect.notEmpty(item.getDepartmentId()) && !"1".equals(item.getDepartmentId())) {
			DepartmentVo vo = this.dataCacheService.getDepartment(Long.parseLong(item.getDepartmentId()));
			sb.append(" AND OUGUID like ? ");
			list.add(vo.getCode() + "%");
		}
		if(Detect.notEmpty(startTime)) {
			sb.append(" AND tong_time >= STR_TO_DATE(? , %Y-%m-%d) ");
		}
		if(Detect.notEmpty(endTime)) {
			sb.append("AND tong_time <= STR_TO_DATE(? , %Y-%m-%d)");
		}
		if (Detect.notEmpty(item.getInnerCode())) {
			sb.append(" AND QL_INNER_CODE = ? ");
			list.add(item.getInnerCode());
		}
	}

}
