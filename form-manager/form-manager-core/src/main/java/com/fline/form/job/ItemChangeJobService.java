/**
 * Project Name:com.fline.yztb.provice.core
 * File Name:ItemChangeJobService.java
 * Package Name:com.fline.yztb.quartz
 * Date:2019年7月24日下午1:47:56
 * Copyright (c) 2019, www.windo-soft.com All Rights Reserved.
 *
*/

package com.fline.form.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Synchro;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.access.service.SynchroAccessService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.form.util.LocalHostUtil;
import com.fline.form.vo.DepartmentVo;
import com.fline.form.vo.DictionaryVo;

/**
 * ClassName:ItemChangeJobService <br>
 * Function: 修改innercode为事项库中的innercode. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2019年7月24日 下午1:47:56 <br>
 * 
 * @author 邵炜
 * @version
 * @see
 */
@Component("itemChangeJobService")
public class ItemChangeJobService {

	/**
	 * 公安前置库
	 */
	@Resource
	private JdbcTemplate gaqzkJdbcTemplate;

	/**
	 * 事项库
	 */
	@Resource
	private ItemAccessService itemAccessService;

	/**
	 * 缓存.
	 */
	@Resource
	private DataCacheService dataCacheService;
	
	@Resource
	private SynchroAccessService synchroAccessService;
	
	@Resource
	private DepartmentAccessService departmentAccessService;
	
	@Resource
	private ItemMgmtService itemMgmtService;

	/**
	 * 打印日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemAddJobService.class);

	private List<DepartmentVo> listAllDept = null;

	private List<Item> listAllAppItem = null;
	
	private List<DictionaryVo> listDict = null;
	
	/**
	 * 不重复的数据
	 */
	private static final String SQL_ONLY = "SELECT QL_NAME AS NAME, QL_INNER_CODE AS innerCode, QL_KIND AS CODE , CONCAT(QL_MAINITEM_ID, '-', QL_SUBITEM_ID) AS areaCode , OUGUID, BELONGXIAQUCODE AS bcode FROM qlt_qlsx "
			+ "WHERE tong_time >= STR_TO_DATE('%s', '%%Y-%%m-%%d %%H:%%i:%%s') AND tong_time < STR_TO_DATE('%s', '%%Y-%%m-%%d %%H:%%i:%%s')";

	/**
	 * 不重复的数据 总数
	 */
	private static final String COUNT_SQL_ONLY = "SELECT COUNT(*) FROM qlt_qlsx WHERE tong_time >= STR_TO_DATE('%s', '%%Y-%%m-%%d %%H:%%i:%%s') AND tong_time < STR_TO_DATE('%s', '%%Y-%%m-%%d %%H:%%i:%%s')";

	/**
	 * @Description :获取所有的部门、字典信息
	 * @author : shaowei
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getDeptAndAllDist() throws Exception {
		this.listAllDept = this.departmentAccessService.findAllVo();
		Map<Object, Object> dictionary = this.dataCacheService.getDictionary();
		this.listDict = (List<DictionaryVo>) dictionary.get("26");// 权力编码类型
		this.listAllAppItem = this.itemAccessService.findAllAppItem();
	}

	/**
	 * cronJob:使用大数据定时执行. 
	 *
	 * @author 邵炜
	 * @throws Exception
	 */
	public void cronJob() throws Exception{
		String ip = LocalHostUtil.getLocalHostLANAddress().getHostAddress();
		LOGGER.info("ip地址{}", ip);
		long start = System.currentTimeMillis();
		this.getDeptAndAllDist();
		Date date = new Date();
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", "c_item");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Synchro synchro = this.synchroAccessService.findUpdateState(map);
		map.put("endTime", date);
		this.synchroAccessService.updateEndTimeByTableName(map);
		this.jobByPage(SQL_ONLY, COUNT_SQL_ONLY, sf.format(synchro.getStartTime()), sf.format(date));
		map.put("endTime", date);
		this.synchroAccessService.updateStartTimeByEndTime(map);
		long end = System.currentTimeMillis();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("结束执行事项内部编码修改任务,执行时间={}毫秒", end - start);
		}
	}
	
	/**
	 * ItemChangeJob:根据时间执行编码更新操作
	 *
	 * @author 邵炜
	 */
	public Long ItemChangeJob(String startTime, String endTime) {
		long takeTime = 0l;
		try {
			String ip = LocalHostUtil.getLocalHostLANAddress().getHostAddress();
			LOGGER.info("ip地址{}", ip);
			long start = System.currentTimeMillis();
			this.getDeptAndAllDist();
			this.jobByPage(SQL_ONLY, COUNT_SQL_ONLY, startTime, endTime);
			long end = System.currentTimeMillis();
			takeTime = end - start;
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("结束执行事项内部编码修改任务,执行时间={}毫秒", takeTime);
			}
		} catch (Exception e) {
			LOGGER.error("事项内部编码修改异常", e);
			throw new RuntimeException("事项内部编码修改异常:" + e.getMessage());
		}
		return takeTime;
	}

	/**
	 * jobByPage:分页执行事项内部编码修改操作.
	 *
	 * @author 邵炜
	 * @param sql
	 * @param countSql
	 * @throws Exception
	 */
	private void jobByPage(String sql, String countSql, String startTime, String endTime) throws Exception {
		List<Map<String, Object>> list = null;
		int size = this.gaqzkJdbcTemplate.queryForObject(String.format(countSql, startTime, endTime), Integer.class);
		boolean sp = size % 1000 == 0;
		int pageNo = sp == true ? size / 1000 : size / 1000 + 1;
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("本次执行的事项数量为={},页数为={}", size, pageNo);
		}
		for (int i = 1; i <= pageNo; i++) {
			list = this.gaqzkJdbcTemplate.queryForList(String.format(sql, startTime, endTime) + " limit " + (i - 1) * 1000 + ",1000");
			this.doChangeInnerCode(list);
		}
	}

	/**
	 * doChangeInnerCode:执行事项内部编码修改操作
	 *
	 * @author 邵炜
	 * @param list
	 * @throws Exception
	 */
	private void doChangeInnerCode(List<Map<String, Object>> list) throws Exception {
		if (!Detect.notEmpty(list)) {
			LOGGER.info("本次执行的数据为空");
			return;
		}
		long deptId = 0;
		String name, innerCode, code, areaCode, ouguid;
		Map<String, Object> params = new HashMap<String, Object>();
		for (Map<String, Object> map : list) {
			deptId = 0;
			name = map.get("name") == null ? "" : map.get("name").toString(); // 事项名称
			innerCode = map.get("innerCode") == null ? "" : map.get("innerCode").toString();
			code = map.get("CODE") == null ? "" : map.get("CODE").toString();
			areaCode = map.get("areaCode") == null ? "" : map.get("areaCode").toString();
			ouguid = map.get("OUGUID") == null ? "" : map.get("OUGUID").toString();
			for (DictionaryVo vo : this.listDict) {
				if (code.equals(vo.getCode())) {
					code = vo.getName().concat("-") + areaCode;
					break;
				}
			}
			if("001008013001061".equals(ouguid)) {
				ouguid = "001008013010061";
			}
			for (DepartmentVo vo : this.listAllDept) {
				if(ouguid.equals(vo.getCode())) {
					deptId = vo.getId();
					break;
				}
			}
			for(Item item : this.listAllAppItem) {
				if(!Detect.notEmpty(item.getDepartmentId())) {
					continue;
				}
				if(code.equals(item.getCode()) && deptId == Long.parseLong(item.getDepartmentId())) { // 说明是同一个事项
					LOGGER.info("事项名称={},事项编码={},部门ID=" + deptId, name, code);
					if(innerCode.equals(item.getInnerCode())) { // 说明内部编码一样则不需要更改
						System.out.println("内部编码" + innerCode);
						break;
					}
					// 否则修改事项库中该事项的innerCode
					params.put("newInnerCode", innerCode);
					params.put("oldInnerCode", item.getInnerCode());
					params.put("deptId", item.getDepartmentId());
					this.itemAccessService.updateInnerCode(params);
					break;
				}
			}
			
		}
	}
	
	/**
	 * doChangeInnerCode:根据内部编码，地区编码，权力编码判断更新内部编码。. 
	 *
	 * @author 邵炜
	 * @param innerCode
	 * @param ouguId
	 * @param code
	 */
	public void doChangeInnerCode(String innerCode, String ouguId, String code ,String deptName,List<DepartmentVo> listDept) {
		Map<String, Object> parmas = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		parmas.put("code", code);
		parmas.put("active", 2);
		// 判断是否是app事项
		List<Item> list = this.itemAccessService.findAppItem(parmas);
		if(!Detect.notEmpty(list)) {
			return;
		}
		long deptId = 0l;
		for (DepartmentVo vo : listDept) {
			if(ouguId.equals(vo.getCode())) {
				deptId = vo.getId();
				break;
			}
		}
		if(deptId == 0l) {
			return;
		}
		parmas.put("departmentId", deptId);
		list = this.itemAccessService.findAppItem(parmas);
		// 判断该部门下的该事项是否上线
		if(Detect.notEmpty(list)) {
			Item item = list.get(0);
			if(innerCode.equals(item.getInnerCode())) {
				System.out.println("内部编码" + innerCode);
				return;
			}
			parmas.put("newInnerCode", innerCode);
			parmas.put("oldInnerCode", item.getInnerCode());
			parmas.put("deptId", deptId);
			this.itemAccessService.updateInnerCode(parmas);
			Item findItem = itemMgmtService.findById(item.getId());
			this.itemMgmtService.createToCache(findItem);
			return;
		}
		parmas.put("active", 1);
		list = this.itemAccessService.findAppItem(parmas);
		// 判断是否存在，并且还未上线,需要赋值上线
		if(Detect.notEmpty(list)) {
			// 判断innerCode是否相同
			Item item2 = list.get(0);
			if(!innerCode.equals(item2.getInnerCode())) {
				parmas.put("newInnerCode", innerCode);
				parmas.put("oldInnerCode", item2.getInnerCode());
				parmas.put("deptId", deptId);
				this.itemAccessService.updateInnerCode(parmas);
			}
			// 查找一个已经上线的相同的事项
			parmas.remove("departmentId");
			parmas.put("active", 2);
			List<Item> items = this.itemAccessService.findAppItem(parmas);
			if(Detect.notEmpty(items)) {
				Item item = items.get(0);
				map.put("fromItemIds", new long[] {item.getId()});
				ArrayList<Long> copyDeptIds = new ArrayList<Long>();
				copyDeptIds.add(deptId);
				map.put("copyDeptIds", copyDeptIds);
				this.itemMgmtService.copyItem(map);
				Item findItem = itemMgmtService.findById(item2.getId());
				this.itemMgmtService.createToCache(findItem);
			}
		}else {// 该部门下不存在，需要新增事项并且复制
			Item item = new Item();
			item.setInnerCode(innerCode);
			item.setDepartmentId(deptId + "");
			item.setDepartmentName(deptName);
			item.setCode(code);
			item = this.itemAccessService.save(item);
			parmas.remove("departmentId");
			parmas.put("active", 2);
			List<Item> items = this.itemAccessService.findAppItem(parmas);
			if(Detect.notEmpty(items)) {
				ArrayList<Long> copyDeptIds = new ArrayList<Long>();
				copyDeptIds.add(deptId);
				map.put("fromItemIds", new long[] {items.get(0).getId()});
				map.put("copyDeptIds", copyDeptIds);
				this.itemMgmtService.copyItem(map);
				Item findItem = itemMgmtService.findById(item.getId());
				this.itemMgmtService.createToCache(findItem);
			}
		}
		
	}
	
}
