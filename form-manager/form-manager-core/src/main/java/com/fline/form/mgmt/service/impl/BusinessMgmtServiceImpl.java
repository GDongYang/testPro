package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Business;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.access.service.BusinessAccessService;
import com.fline.form.access.service.BusinessItemAccessService;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.mgmt.service.*;
import com.fline.form.vo.*;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("businessMgmtService")
public class BusinessMgmtServiceImpl implements BusinessMgmtService {

	private Log logger = LogFactory.getLog(BusinessMgmtServiceImpl.class);

	@Resource
	private CertTempMgmtService certTempMgmtService;
	@Resource
	private BusinessAccessService businessAccessService;
	@Resource
	private BusinessItemAccessService businessItemAccessService;
	@Resource
	private ServiceAccountMgmtService serviceAccountMgmtService;
	@Resource
	private DataCacheService dataCacheService;
	@Resource
	private FdpClientMgmtService fdpClientMgmtService;
	
	@Resource
	private DepartmentAccessService departmentAccessService;

	@Resource
	private ItemMgmtService itemMgmtService;

	@Resource
	private ItemAccessService itemAccessService;
	@Override
	public Pagination<Business> findPagination(Map<String, Object> param, Ordering order, Pagination<Business> page) {
		return businessAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Business business) {
		businessAccessService.update(business);
	}

	@Override
	public void remove(Business business) {
		businessAccessService.remove(business);
	}

	@Override
	public ResponseResult create(Business business) {
		BusinessContext context = serviceAccountMgmtService.getCurrentContext();
		try {
			ServiceAccount account = context.getAccount();
			if (account == null) {
				return null;
			}
			Item itemCout = itemMgmtService.findItemCountByInner(account.getPositionId(),
					business.getItemInnerCode().trim());

			List<CertTemp> certTemps = certTempMgmtService.findByInnerCode(business.getItemInnerCode().trim());
			if (itemCout != null && certTemps != null) {
				business.setAccountId(account.getId());
				long deptId = account.getDepartmentId();
				business.setDepartmentId(deptId);
				business.setStatus(1);
				business.setApplicantUnit(context.getApplicantUnit());
				business.setApplicantUser(context.getApplicantUser());
				business.setCreateDate(new Date());
				business.setType(2);// 接口
				business.setItemCode(itemCout.getCode());
				businessAccessService.create(business);

				context.setBusiness(business);
				context.setItem(itemCout.getName());
				Map<String, DataCollectionParam> paramMap = new HashMap<>();

				for (CertTemp certTemp : certTemps) {
					// 申请表不查询
					if (certTemp.getType() == 3) {
						continue;
					}
					String catalogCode = certTemp.getCatalogCode();
					DataCollectionParam param = paramMap.get(catalogCode);

					if (param == null) {
						param = new DataCollectionParam();
						param.setCatalogCode(catalogCode);
						param.setBusiCode(business.getCode());
						param.setCerNo(business.getCerno());
						param.setCerName(business.getCerName());
						param.setOtherCerName(business.getOtherCerName());
						param.setOtherCerNo(business.getOtherCerNo());
						param.setOtherParam(business.getNameLike());
						if (Detect.notEmpty(business.getNameLike())) {
							try {
								JSONObject json = JSONObject.fromObject(business.getNameLike());
								if (json.get("uniscid") != null) {
									param.setUniscid((String) json.get("uniscid"));
								}
								if (json.get("entName") != null) {
									param.setEntName((String) json.get("entName"));
								}
								if (json.get("regNo") != null) {
									param.setRegNo((String) json.get("regNo"));
								}
							} catch (Exception e) {
								return ResponseResult.error("参数错误");
							}

						}
						param.setCerts(new ArrayList<CertTemp>());
						param.setDepartmentId(Long.parseLong(itemCout.getDepartmentId()));
						param.setAskDeptId(business.getDepartmentId());
						param.setBusiness(business);
						param.setItem(itemCout.getName());
						param.setItemCode(itemCout.getCode());
						param.setApplicantUnit(context.getApplicantUnit());
						param.setApplicantUser(context.getApplicantUser());
						paramMap.put(catalogCode, param);
					}
					param.getCerts().add(certTemp);
				}
				Collection<DataCollectionParam> params = paramMap.values();
				List<Map<String, Object>> certDatas = new Vector<>();
				ExecutorService pool = Executors.newFixedThreadPool(params.size());
				CountDownLatch lactch = new CountDownLatch(params.size());
				for (DataCollectionParam collectionParam : params) {
					pool.execute(new SingerCertificateData(collectionParam, lactch, certDatas));
				}
				try {
					lactch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					pool.shutdown();
				}
				for (Map<String, Object> map : certDatas) {
					// map.remove("errorMsg");
					map.remove("state");
					map.remove("timeConsuming");
				}
				return ResponseResult.success(certDatas);
			} else {
				return ResponseResult.error("权限不足，该帐号无法执行该业务事项");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResponseResult.error("失败");
		} finally {
			serviceAccountMgmtService.logout();
		}
	}

	private class SingerCertificateData implements Runnable {
		private DataCollectionParam param;
		private CountDownLatch lactch;
		private List<Map<String, Object>> certDatas;

		SingerCertificateData(DataCollectionParam param, CountDownLatch lactch, List<Map<String, Object>> certDatas) {
			this.param = param;
			this.certDatas = certDatas;
			this.lactch = lactch;
		}

		@Override
		public void run() {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lactch.countDown();
			}
		}
	}

	@Override
	public ResponseResult createFile(Business business) {
		try {
			CertTemp certTemp = certTempMgmtService.findByCode(business.getCertCode());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResponseResult.error("失败");
		} finally {
			serviceAccountMgmtService.logout();
		}
		return null;
	}

	@Override
	public Business findById(long id) {
		return businessAccessService.findById(id);
	}

	@Override
	public List<Map<String, Object>> cumulativeTempItemRequest() {
		return businessAccessService.cumulativeTempItemRequest();
	}

	@Override
	public int count() {
		return businessAccessService.counts();
	}

	@Override
	public List<Map<String, Object>> serviceItemCount(Map<String, Object> params) {
		//先从缓存中获取数据
		Map<String,Object> cerNoCache = dataCacheService.getServiceItemCount(1);//获取事项数量信息
		Map<String,Object> tempCache = dataCacheService.getServiceItemCount(0);//获取证明数量信息
		List<Map<String, Object>> result = businessAccessService.serviceItemCount();
		StringBuilder sqlItemSolr = new StringBuilder("select id from project_info ");
		StringBuilder sqlCertSolr = new StringBuilder(" select id from yztbbusinessitemtable ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer cerNoCountCache = null;
		Integer tempCountCache = null;
		Boolean forCache = (Boolean) params.get("forCache");
		if(cerNoCache != null && forCache == null ) {//缓存中有数据，则只需要获取时间大于缓存时间的数据
			String time = sdf.format(cerNoCache.get("time"));
			cerNoCountCache = (Integer) cerNoCache.get("cerNoCount");
			sqlItemSolr.append(" where createTime > {ts '" + time + "'} ");
		}
		if(tempCache != null && forCache == null ) {
			String time = sdf.format(tempCache.get("time"));
			tempCountCache = (Integer) tempCache.get("tempCount");
			sqlCertSolr.append(" where createDate > {ts '" + time + "'} ");
		}
		//从solr中获取办件总量
		StringBuilder sb = new StringBuilder();
		sb.append(" limit 0,9999999");
		JSONArray itemArray = this.fdpClientMgmtService.queryDataFromFormSolrDb(sqlItemSolr + sb.toString());
		JSONArray certArray = this.fdpClientMgmtService.queryDataFromSolr(sqlCertSolr + sb.toString());
		
		if (forCache != null && forCache == true) {//表示此次是缓存
			Map<String, Object> cerNoMap = new HashMap<>();	
			cerNoMap.put("time", new Date());
			cerNoMap.put("cerNoCount", itemArray.size());
			dataCacheService.setServiceItemCount(cerNoMap, 1);
			Map<String, Object>tempMap = new HashMap<>();
			tempMap.put("time", new Date());
			tempMap.put("tempCount", certArray.size());
			dataCacheService.setServiceItemCount(tempMap, 0);
		}
		
		result.get(0).put("cerNoCount", (cerNoCountCache == null)?itemArray.size() : itemArray.size() + cerNoCountCache);
		result.get(0).put("tempCount", (tempCountCache == null)?certArray.size() :certArray.size() + tempCountCache);
		return result;
		
	}

	@Override
	public List<Map<String, Object>> dayItemCount() {
		return businessAccessService.dayItemCount();
	}

	@Override
	public List<Map<String, Object>> monthItemCount() {
		return businessAccessService.monthItemCount();
	}

	@Override
	public List<Map<String, Object>> monthCerNoCount() {
		return businessAccessService.monthCerNoCount();
	}

	@Override
	public List<Map<String, Object>> weekItemCount(Map<String, Object> param) {
		return businessAccessService.weekItemCount(param);
	}

	@Override
	public List<Map<String, Object>> dayTempCount() {
		return businessAccessService.dayTempCount();
	}

	@Override
	public Pagination<Business> findTempCount(Map<String, Object> param, Ordering order, Pagination<Business> page) {
		return businessAccessService.findTempCount(param, order, page);
	}

	@Override
	public long getTempSum(Map<String, Object> parameter) {
		return businessAccessService.getTempSum(parameter);
	}

	@Override
	public String deleteTest() {
		int count1 = businessAccessService.deleteTest();
		int count2 = businessItemAccessService.deleteTest();
		return "删除测试事项" + count1 + "删除测试证件" + count2;
	}

	@Override
	public Pagination<ItemCount> findItemCount(Map<String, Object> parameter, Ordering ord,
			Pagination<ItemCount> page) {
		return businessAccessService.findItemCount(parameter, ord, page);
	}

	@Override
	public long getItemSum(Map<String, Object> parameter) {
		return businessAccessService.getItemSum(parameter);
	}

	@Override
	public Map<String, Object> findItemCountBySolr(Map<String, Object> parameter) {
		int pageNum = (int) parameter.get("pageNum");
		int pageSize = (int) parameter.get("pageSize");
		String sqlSolr = " select * from yztbbusinesstable where ";
		StringBuffer sb = new StringBuffer(80);
		Map<String, Object> page = new HashMap<String, Object>();
		if (parameter.containsKey("startDate")) { // 开始时间
			sb.append("and createDate > {ts '" + parameter.get("startDate") + "'} ");
		}
		if (parameter.containsKey("stopDate")) {
			sb.append("and createDate < {ts '" + parameter.get("stopDate") + "'}");
		}
		if (parameter.containsKey("departmentId")) {
			sb.append("and departmentId = " + parameter.get("departmentId"));
		}
		if (parameter.containsKey("certNum")) {
			sb.append("and cerno = " + parameter.get("certNum"));
		}
		if(parameter.containsKey("itemName")) {
			sb.append("and itemName = " + parameter.get("itemName"));
		}
		if(sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("   group by itemName,departmentId order by itemName desc limit ");// 字符串前面要有3个空格
		pageNum = (pageNum - 1) * pageSize;
		JSONArray jsonArray = this.fdpClientMgmtService.queryItemDataGroupByCount(sqlSolr + sb.toString().substring(3).concat(pageNum + "," + pageSize));
		Integer count = this.fdpClientMgmtService.queryDataCount(sqlSolr + sb.toString().substring(3).concat("0,2000"));
		List<BusinessVo> list = JSONArray.parseArray(jsonArray.toJSONString(), BusinessVo.class);
		String deptName = "";
		DepartmentVo department = null;
		for (BusinessVo businessVo : list) {
			deptName = businessVo.getDepartmentName();
			if(!Detect.notEmpty(deptName)) {
				department = this.dataCacheService.getDepartment(businessVo.getDepartmentId());
				if(department != null) {
					businessVo.setDepartmentName(department.getName());
					businessVo.setOtherParam("0".equals(department.getParentId()) ? department.getName() : 
						this.fitDeptName(Long.parseLong(department.getParentId())));
				}
			}else {
				int lastIndexOf = deptName.lastIndexOf("/");
				businessVo.setDepartmentName(lastIndexOf < 0 ? deptName : deptName.substring(lastIndexOf + 1).trim());
				businessVo.setOtherParam(lastIndexOf < 0 ? deptName : deptName.substring(0, lastIndexOf).trim());
			}
		}
		page.put("total", count);
		page.put("rows", list);
		return page;
	}
	
	
	/**
	 * fitDeptName:查询部门的名称. 
	 *
	 * @author 邵炜
	 * @param deptId
	 * @return
	 */
	private String fitDeptName(Long deptId) {
		String deptName = "";
		DepartmentVo department = this.dataCacheService.getDepartment(deptId);
		deptName = department.getName();
		long pId = Long.parseLong(department.getParentId());
		while(department != null && pId != 1l && pId != 2l && pId != 3l) {
			department = this.dataCacheService.getDepartment(pId);
			pId = Long.parseLong(department.getParentId());
			if(deptName.equals(department.getName())) {
				continue;
			}
			deptName = department.getName() + " / "+ deptName ;
		}
		return deptName;
	}

	@Override
	public List<Map<String, Object>> deptItemCountByDay(Map<String, Object> params) {
		Map<String, Object> cacheResults = null;
		Integer days = (Integer) params.get("days");
		//从Solr中获取
		String sqlSolr = " select createTime,nodeName from project_lifecycle ";
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Boolean forCache = (Boolean) params.get("forCache");
		if(forCache == null || !forCache) {
			//从缓存中获取数据	
			cacheResults = dataCacheService.getDeptItemCountByDay();
		}
		String time = "";
		if(cacheResults != null && forCache == null) {//缓存中有数据则只需要统计比缓存时间大的数据
			time = sdf.format(cacheResults.get("time"));
		}else {
			time ="2019-01-01 00:00:00"; //缓存全部数据之后进行分类排序
		}
		sb.append( "  where createTime > {ts '" + time + "'} ");
		if(params.get("forCache") != null) {
        	time = sdf.format(new Date()); //time为今天的0点
        	sb.append( " and createTime < {ts '" + time + "'} ");
        }
		sb.append("   order by nodeName desc limit 0,9999999");
		
		long startTime = System.currentTimeMillis();
		
		JSONArray jsonArray = this.fdpClientMgmtService.queryDataFromFormSolrDb(sqlSolr + sb.toString());
		List<ProjectLifecycle> list = JSONArray.parseArray(jsonArray.toJSONString(), ProjectLifecycle.class);
		long endTime = System.currentTimeMillis();
		System.out.println("从solr获取数据及转换花费时间：" + (endTime - startTime) + " ms");
		
		//缓存中的数据加上最新的数据
		if(cacheResults != null) {
			List<ProjectLifecycle> cacheList = (List<ProjectLifecycle>)cacheResults.get("dataList");
			cacheList.addAll(list);
			list = cacheList;
		}
		startTime = System.currentTimeMillis();
		Map<String, Map<String,Object>> results = new HashMap<>();
		if(days != null && days != 0) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			if(days == -7) {//前七天
				cal2.add(Calendar.DAY_OF_MONTH, -7);
				cal1.set(Calendar.HOUR_OF_DAY, 0);
				cal1.set(Calendar.MINUTE,0);
				cal1.set(Calendar.SECOND, 0);
				cal1.set(Calendar.MILLISECOND,0);
			}else if(days == -30) {//前三十天
				cal2.add(Calendar.MONTH,-1);
				cal1.set(Calendar.HOUR_OF_DAY, 0);
				cal1.set(Calendar.MINUTE,0);
				cal1.set(Calendar.SECOND, 0);
				cal1.set(Calendar.MILLISECOND,0);
			}else if(days == 7) {//本周
				cal2.add(Calendar.WEEK_OF_MONTH, 0);
				cal2.set(Calendar.DAY_OF_WEEK, 2);
			}else if(days == 30) {//本月
				cal2.add(Calendar.MONTH, 0);
				cal2.set(Calendar.DAY_OF_MONTH, 1);
			}
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE,0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND,0);
			for(ProjectLifecycle projectLifecycle : list) {
				String nodeName = projectLifecycle.getNodeName();
				if((projectLifecycle.getCreateTime() == null || projectLifecycle.getCreateTime().compareTo(cal2.getTime()) == -1||
					projectLifecycle.getCreateTime().compareTo(cal1.getTime()) == 1)) {
					continue;
				}
				if(!Detect.notEmpty(nodeName)) {
					continue;
				}
				if(results.containsKey(nodeName)) {
					//如果存在则数量 + 1
					Map<String, Object> itemInfo = results.get(nodeName);
					int itemCount = (int) itemInfo.get("itemCount");
					itemInfo.put("itemCount", ++itemCount);
				}else {
					Map<String, Object> itemInfo = new HashMap<>();
					itemInfo.put("itemCount", 1);
					itemInfo.put("departmentName", nodeName);
					results.put(nodeName, itemInfo);
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("数据分组花费时间: " + (endTime - startTime) + " ms");
		
		if(forCache != null && forCache == true ) {//将数据置入缓存
			try {
				Map<String, Object> cacheMap = new HashMap<>();
				cacheMap.put("dataList", list);
				cacheMap.put("time", sdf.parse(time));
				dataCacheService.setDeptItemCountByDay(cacheMap);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		startTime = System.currentTimeMillis();
		ArrayList<Map<String, Object>> mapList = new  ArrayList<Map<String,Object>>(results.values());
		Collections.sort(mapList,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return  (int)o2.get("itemCount") - (int)o1.get("itemCount");
			}});
		endTime = System.currentTimeMillis();
		System.out.println("排序花费时间: " + (endTime - startTime) + " ms");
		return mapList.size() > 10?mapList.subList(0, 7):mapList;
		
	}

	@Override
	public Map<String, Object> dayItemCountChange(Map<String, Object> params) {
		int days =(int) params.get("days");
		//先从缓存中取
		Map<String, Object> cacheResults = null;
		Boolean forCache = (Boolean) params.get("forCache");
		if(forCache == null || !forCache) {
			cacheResults = dataCacheService.getDayItemCountChange(days);
		}
		//获取当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = "";
		Calendar cal=Calendar.getInstance();
		//从Solr中获取
		String sqlSolr = " select createTime from project_info ";
		StringBuilder sb = new StringBuilder();
		if(cacheResults != null && forCache == null) {//若缓存中有数据则 只需要获取比缓存天数大的数据
			Date date = (Date) cacheResults.get("time");
			sb.append(" where ");
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			sb.append(" createTime > {ts '" + time + "'} ");
		}else if((int)params.get("days") == 7) {//代表查询本周的办件记录
			sb.append(" where ");
	        cal.add(Calendar.WEEK_OF_MONTH, 0);
	        cal.set(Calendar.DAY_OF_WEEK, 2);
	        time = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" 00:00:00"; //time为本周的第一天
	        sb.append( " createTime > {ts '" + time + "'} ");
		}else if ((int)params.get("days") == 30){//代表查询本月的办件记录
			sb.append(" where ");
			cal.add(Calendar.MONTH, 0);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        time = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" 00:00:00"; //time为本月的第一天
	        sb.append( " createTime > {ts '" + time + "'} ");
		}
		if(forCache != null && forCache == true) {
        	time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); //time为当前时间
        	sb.append( " and createTime < {ts '" + time + "'} ");
        }
		sb.append("   order by createTime asc limit 0,9999999");
		JSONArray jsonArray = this.fdpClientMgmtService.queryDataFromFormSolrDb(sqlSolr + sb.toString());
		List<ProjectInfoVo> list = JSONArray.parseArray(jsonArray.toJSONString(), ProjectInfoVo.class);
		if(cacheResults != null) {
			List<ProjectInfoVo> cacheList = (List<ProjectInfoVo>)cacheResults.get("dataList");
			cacheList.addAll(list);
			list = cacheList;
		}
		//根据时间分组
		Map<String, Map<String, Object>> results = new LinkedHashMap<>();
		Map<String, Object> result = new HashMap<>();
		long sum = 0;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		for(ProjectInfoVo projectInfoVo : list) {
			String busTime = sdf2.format(projectInfoVo.getCreateTime());
			sum++;
			if(!Detect.notEmpty(busTime)) {
				continue;
			}
			if(results.containsKey(busTime)) {
				Map<String, Object> itemInfo = results.get(busTime);
				int itemCount = (int) itemInfo.get("itemCount");
				itemInfo.put("itemCount", ++itemCount);
			}else {
				Map<String, Object> itemInfo = new HashMap<>();
				itemInfo.put("itemCount", 1);
				itemInfo.put("time",busTime);
				results.put(busTime, itemInfo);
			}
		}
		result.put("result", new ArrayList<>(results.values()));
		result.put("sum", sum);
		if(forCache != null && forCache == true) {//将数据置入缓存
			try {
				Map<String, Object> cacheMap = new HashMap<>();
				cacheMap.put("dataList", list);
				cacheMap.put("time",sdf.parse(time));
				dataCacheService.setDayItemCountChange(cacheMap,days);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> deptItemCount(Map<String,Object>params) {
		int type = (int) params.get("type");
		Boolean forCache = (Boolean) params.get("forCache");
		Map<String, Object> cacheResults = null;
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> itemCountMap = new HashMap<String, Object>();
		if(forCache == null || !forCache) {
			//从缓存中获取数据
			//cacheResults = dataCacheService.getDeptItemCount(type);
		}
		List<Map<String, Object>> items = itemAccessService.findDeptInfo(params);
		Map<String, String> deptNameMap = new HashMap<>();
		Map<String, Object> departmentIdMap = new HashMap<String, Object>();
		for(Map<String,Object> map:items) {//根据事项InnerCode获取部门名称
			deptNameMap.put((String)map.get("innerCode"), (String)map.get("deptName"));
			departmentIdMap.put((String)map.get("innerCode"), map.get("departmentId"));
		}
		//从Solr中获取
		String sqlSolr = " select itemCode from project_info  ";
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		Calendar cal=Calendar.getInstance();
		if(cacheResults != null && forCache == null) {
			time = sdf.format(cacheResults.get("time"));
			sb.append( " where  createTime > {ts '" + time + "'} ");
		}else if(type == 1) {//代表查询本月
			sb.append("where");
			cal.add(Calendar.MONTH, 0);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        time = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" 00:00:00"; //time为本月第一天
	        sb.append( " createTime > {ts '" + time + "'} ");
	        if(forCache != null && forCache == true) {
				time = sdf.format(new Date());
				sb.append( " and  createTime < {ts '" + time + "'} ");
			}
		}
			
		sb.append("   order by itemCode desc limit 0,9999999");
		JSONArray jsonArray = this.fdpClientMgmtService.queryDataFromFormSolrDb(sqlSolr + sb.toString());
		List<ProjectInfoVo> list = JSONArray.parseArray(jsonArray.toJSONString(), ProjectInfoVo.class);
		if(cacheResults != null) {
			List<ProjectInfoVo> cacheList = (List<ProjectInfoVo>)cacheResults.get("dataList");
			cacheList.addAll(list);
			list = cacheList;
		}
		Map<String, Map<String, Object>> results = new HashMap<>();
		for(ProjectInfoVo projectInfoVo : list) {
			String innerCode = projectInfoVo.getItemCode();
			if(!deptNameMap.containsKey(innerCode)) {
				continue;
			}
			if(itemCountMap.containsKey(innerCode)){
				int count = (int) itemCountMap.get(innerCode);
				itemCountMap.put(innerCode, ++count);
			}else {
				itemCountMap.put(innerCode, 1);
			}
			String deptName = deptNameMap.get(innerCode);
			Long departmentId = (Long) departmentIdMap.get(innerCode);
			if(results.containsKey(deptName)) {
				//如果存在则数量 + 1
				Map<String, Object> itemInfo = results.get(deptName);
				int itemCount = (int) itemInfo.get("itemCount");
				itemInfo.put("itemCount", ++itemCount);
			}else {
				Map<String, Object> itemInfo = new HashMap<>();
				itemInfo.put("itemCount", 1);
				itemInfo.put("departmentName", deptName);
				itemInfo.put("departmentId", departmentId);
				results.put(deptName, itemInfo);
			}
		}
		if(params.get("forCache") != null && forCache == true) {//将数据置入缓存
			try {
				Map<String, Object> cacheMap = new HashMap<>();
				cacheMap.put("dataList", list);
				cacheMap.put("time", sdf.parse(time));
				dataCacheService.setDeptItemCount(cacheMap, type);//加入缓存
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Map<String, Object>> mapList = new  ArrayList<Map<String,Object>>(results.values());
		Collections.sort(mapList,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return  (int)o2.get("itemCount") - (int)o1.get("itemCount");
			}});
		resultMap.put("result",  (mapList.size() > 10)?mapList.subList(0, 10):mapList);
		resultMap.put("allData", itemCountMap);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> cityItemCount(Map<String, Object> params) {
		int type = (int) params.get("type");
		Boolean forCache = (Boolean) params.get("forCache");
		Map<String, Object> cacheResults = null;
		//先从缓存中取
		if(forCache == null || !forCache) {
			cacheResults = dataCacheService.getCityItemCount(type);
		}
	
		List<Map<String, Object>> items = itemAccessService.findDeptInfo(params);
		Map<Object, Object> deptMap = new HashMap<>();
		for(Map<String,Object> map:items) {//根据事项InnerCode获取部门名称
			deptMap.put(map.get("innerCode"), map.get("deptName"));
		}
		//从solr中获取
		String sqlSolr = " select itemCode from project_info ";
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		Map<String, Map<String, Object>> results = new HashMap<>();
		
		if(Detect.notEmpty(cacheResults)) {//缓存中有数据则查询比缓存时间大的数据
			Date date = (Date) cacheResults.get("time");
			sb.append(" where ");
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			sb.append(" createTime > {ts '" + time + "'} ");
		}else if((int)params.get("type") == 1) {
			//代表查询本月
			sb.append("where ");
			cal.add(Calendar.MONTH, 0);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        time = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" 00:00:00"; //time为本月
	        sb.append( " createTime > {ts '" + time + "'} ");
	        if(forCache != null) {
	        	time = sdf.format(new Date()); //time为本月
	        	sb.append( " and createTime < {ts '" + time + "'} ");
	        }
		}
		sb.append("   order by itemCode desc limit 0,9999999");
		JSONArray jsonArray = this.fdpClientMgmtService.queryDataFromFormSolrDb(sqlSolr + sb.toString());
		
		List<ProjectInfoVo> list = JSONArray.parseArray(jsonArray.toJSONString(), ProjectInfoVo.class);
		if(Detect.notEmpty(cacheResults)) {//如果缓存数据不为空 则数据相加
			list.addAll((List<ProjectInfoVo>)cacheResults.get("dataList"));
		}
		String regx =  "^[\\u4e00-\\u9fa5]* / [\\u4e00-\\u9fa5]*";//正则匹配条件
		Pattern pattern = Pattern.compile(regx);
		for (ProjectInfoVo projectInfoVo : list) {
			//根据名字分组
			String itemCode = projectInfoVo.getItemCode();
			if(!deptMap.containsKey(itemCode)) {
				continue;
			}
			Matcher m = pattern.matcher((String)deptMap.get(itemCode));
			if(!m.find()) {
				continue;
			}
			String deptName = m.group(0);
			if(results.containsKey(deptName)) {
				//如果存在则数量 + 1
				Map<String, Object> itemInfo = results.get(deptName);
				int itemCount = (int) itemInfo.get("itemCount");
				itemInfo.put("itemCount", ++itemCount);
			}else {
				Map<String, Object> itemInfo = new HashMap<>();
				itemInfo.put("itemCount", 1);
				itemInfo.put("departmentName", deptName);
				results.put(deptName, itemInfo);
			}
		}
		ArrayList<Map<String, Object>> mapList = new  ArrayList<Map<String,Object>>(results.values());
		Collections.sort(mapList,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return  (int)o2.get("itemCount") - (int)o1.get("itemCount");
			}});
		if(params.get("forCache") != null) {
			try {
				Map<String, Object> cacheMap = new HashMap<>();
				cacheMap.put("dataList", list);
				cacheMap.put("time", sdf.parse(time));
				dataCacheService.setCityItemCount(cacheMap, type);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(mapList.size() > 10) {
			return mapList.subList(0, 10);
		}
		//更新到缓存
		return mapList;
	}

	@Override
	public Map<String, Object> findStaticFromSolr(Map<String, Object> param) {
		String sqlSolr = "select * "
				+ "from yztbbusinessitemtable where";
		StringBuffer sb = new StringBuffer(80);
		int pageNum = (int) param.get("pageNum");
		int pageSize = (int) param.get("pageSize");
		Map<String, Object> page = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();

		if (param.containsKey("startDate")) { // 开始时间
			sb.append("and createDate > {ts '" + param.get("startDate") + "'} ");
			sb.append("and createDate < {ts '" + param.get("stopDate") + "'}");
		}
		if (param.containsKey("certNo")) {
			sb.append("and cerno = '" + param.get("certNo") + "'");
		}
		if (param.containsKey("certcode")) {
			sb.append("and certCode='" + param.get("certcode") + "'");
		}
		if (param.containsKey("formCode")) {
			sb.append("and form_code='" + param.get("formCode") + "'");
		}
		if (param.containsKey("status")) {
			sb.append("and status='" + param.get("status") + "'");
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("     order by createDate desc limit  " + (pageNum - 1) * pageSize + "," + pageSize);
		JSONArray array;
		try {
			resultMap = fdpClientMgmtService.queryLogDbFromSolr(sqlSolr + sb.toString().substring(3));
			array = (JSONArray) resultMap.get("JSONArray");
			String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(array);
			List<StaticVo> list = com.alibaba.fastjson.JSONObject.parseArray(jsonStr, StaticVo.class);
			page.put("total", resultMap.get("total"));
			page.put("rows", list);

			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void lock(String requestId) {
		while(dataCacheService.setStatisticLock(requestId)) {};
	}

	@Override
	public void unLock(String requestId) {
		String statisticLock = dataCacheService.getStatisticLock();
		if(requestId.equals(statisticLock)) {
			dataCacheService.delStatisticLock();
		}
	}

	@Override
	public Map<String, Object> findBySql(String sql) {
		Map<String, Object> resultMap = new HashMap<>();
		JSONArray array;
		Map<String, Object> page = new HashMap<>();
		try {
			resultMap = fdpClientMgmtService.queryLogDbFromSolr(sql);
			array = (JSONArray) resultMap.get("JSONArray");
			String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(array);
			List<StaticVo> list = com.alibaba.fastjson.JSONObject.parseArray(jsonStr, StaticVo.class);
			page.put("total", resultMap.get("total"));
			page.put("rows", list);

			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> sqlSearch(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<>();
		JSONArray array;
		Map<String, Object> page = new HashMap<>();
		try {
			if (0 == (int) param.get("dbSort")) {
				resultMap = fdpClientMgmtService.queryFormFromSolr(param.get("sql").toString());
			} else {
				resultMap = fdpClientMgmtService.queryLogDbFromSolr(param.get("sql").toString());
			}
			array = (JSONArray) resultMap.get("JSONArray");
			String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(array);
			ArrayList<?> jsonObjList = JSON.parseObject(jsonStr, ArrayList.class);
			page.put("total", resultMap.get("total"));
			page.put("rows", jsonObjList);

			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> findBusinessMonitor(Map<String, Object> parameter) {
		int pageNum = (int) parameter.get("pageNum");
		int pageSize = (int) parameter.get("pageSize");
		String sqlSolr = " select * from yztbbusinesstable where ";
		StringBuffer sb = new StringBuffer(80);
		Map<String, Object> page = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();
		if (parameter.containsKey("startDate")) { // 开始时间
			sb.append("and createDate > {ts '" + parameter.get("startDate") + "'} ");
		}
		if (parameter.containsKey("stopDate")) {
			sb.append("and createDate < {ts '" + parameter.get("stopDate") + "'}");
		}
		if (parameter.containsKey("departmentId")) {
			sb.append("and departmentId = " + parameter.get("departmentId"));
		}
		if (parameter.containsKey("certNum")) {
			sb.append("and cerno = " + parameter.get("certNum"));
		}
		if (parameter.containsKey("itemName")) {
			sb.append("and itemName = " + parameter.get("itemName"));
		}
		if (sb.length() < 1) {
			sqlSolr = sqlSolr.replace("where", "");
		}
		sb.append("   order by createDate desc limit " + (pageNum - 1) * pageSize + "," + pageSize);// 字符串前面要有3个空格
		try {
			resultMap = this.fdpClientMgmtService.queryLogDbFromSolr(sqlSolr + sb.toString().substring(3));
			if (resultMap != null) {
				JSONArray array = (JSONArray) resultMap.get("JSONArray");
				List<BusinessVo> list = JSONArray.parseArray(array.toJSONString(), BusinessVo.class);
				page.put("total", resultMap.get("total"));
				page.put("rows", list);
				return page;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> itemCountRank(Map<String, Object> params) {
		int type = (int) params.get("type");
		Boolean forCache = (Boolean) params.get("forCache");
		Map<String, Object> cacheResults = null;
		//先从缓存中取
		if(forCache == null || !forCache) {
			//cacheResults = dataCacheService.getCityItemCount(type);
		}
		//从solr中获取
		String sqlSolr = " select itemCode from project_info ";
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		Map<String, Map<String, Object>> results = new HashMap<>();
		
		if(Detect.notEmpty(cacheResults)) {//缓存中有数据则查询比缓存时间大的数据
			Date date = (Date) cacheResults.get("time");
			sb.append(" where ");
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			sb.append(" createTime > {ts '" + time + "'} ");
		}else if((int)params.get("type") == 1) {
			//代表查询本月
			sb.append("where ");
			cal.add(Calendar.MONTH, 0);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        time = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" 00:00:00"; //time为本月
	        sb.append( " createTime > {ts '" + time + "'} ");
	        if(forCache != null) {
	        	time = sdf.format(new Date()); //time为本月
	        	sb.append( " and createTime < {ts '" + time + "'} ");
	        }
		}
		sb.append("  order by itemCode desc limit 0,9999999");
		JSONArray jsonArray = this.fdpClientMgmtService.queryDataFromFormSolrDb(sqlSolr + sb.toString());
		List<String> innerCodes = new LinkedList();
		Map<String, Object> itemCountMap = new HashMap<>();
		for(int i = 0;i < jsonArray.size();i++ ) {
			String innerCode = (jsonArray.getJSONObject(i).getString("itemCode"));
			if(innerCodes.contains(innerCode)) {
				Integer count = (Integer) itemCountMap.get(innerCode);
				itemCountMap.put(innerCode, ++count);
				continue;
			}
			itemCountMap.put(innerCode, 1);
			innerCodes.add(innerCode);
			
		}
		params.put("innerCodes", innerCodes);
		List<Item> itemInfos = itemAccessService.findItemInfoList(params);
		Map<Object, Object> itemMap = new HashMap<>();
		for(Item itemInfo :itemInfos) {//根据事项InnerCode获取部门名称
			itemMap.put(itemInfo.getInnerCode(),itemInfo);
		}
		List<ProjectInfoVo> list = JSONArray.parseArray(jsonArray.toJSONString(), ProjectInfoVo.class);
		if(Detect.notEmpty(cacheResults)) {//如果缓存数据不为空 则数据相加
			list.addAll((List<ProjectInfoVo>)cacheResults.get("dataList"));
		}
		for (ProjectInfoVo projectInfoVo : list) {
			//根据名字分组
			String itemCode = projectInfoVo.getItemCode();
			if(!itemMap.containsKey(itemCode)) {
				continue;
			}
			Item item = (Item) itemMap.get(itemCode);
			String code = item.getCode();
			if(results.containsKey(code)) {
				//如果存在则数量 + 1
				Map<String, Object> itemInfo = results.get(code);
				int itemCount = (int) itemInfo.get("itemCount");
				itemInfo.put("itemCount", ++itemCount);
			}else {
				Map<String, Object> itemInfo = new HashMap<>();
				itemInfo.put("itemCount", 1);
				itemInfo.put("itemName", item.getName());
				itemInfo.put("itemCode", item.getCode());
				results.put(code, itemInfo);
			}
		}
		ArrayList<Map<String, Object>> mapList = new  ArrayList<Map<String,Object>>(results.values());
		Collections.sort(mapList,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return  (int)o2.get("itemCount") - (int)o1.get("itemCount");
			}});
		if(params.get("forCache") != null) {
			try {
				Map<String, Object> cacheMap = new HashMap<>();
				cacheMap.put("dataList", list);
				cacheMap.put("time", sdf.parse(time));
				dataCacheService.setCityItemCount(cacheMap, type);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("results", (mapList.size() > 10)? mapList.subList(0, 10):mapList);
		
		resultMap.put("allData", itemCountMap);
		return resultMap;
	}

	@Override
	public Map<String, Object> findDeptItemCountByCode(Map<String, Object> params) {
		String itemCode = (String) params.get("itemCode");
		String allDataStr = (String) params.get("allDataStr");
		JSONObject allDataJson = JSONObject.fromObject(allDataStr);
		Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(allDataJson, HashMap.class);
		Set<String> innerCodeSet = map.keySet();
		List<String> innerCodes = new LinkedList();
		Map<String, Map<String, Object>> results = new HashMap<String, Map<String,Object>>();
		for(String innerCode :innerCodeSet) {
			innerCodes.add(innerCode);
		}
		params.put("innerCodes", innerCodes);
		params.put("code", itemCode);
		List<Item> itemInfos = itemAccessService.findItemInfoList(params);
		for(Item itemInfo:itemInfos) {
			String innerCode = itemInfo.getInnerCode();
			String departmentId = itemInfo.getDepartmentId();
			if(results.containsKey(departmentId)) {
				Map<String,Object> item = results.get(departmentId);
				int	itemCount = (int) item.get("itemCount");
				int dataItemCount = (int) map.get(innerCode);
				item.put("itemCount", itemCount + dataItemCount);
			}else {
				Map<String, Object> item = new HashMap<>();
				int dataItemCount = (int) map.get(innerCode);
				item.put("itemCount",dataItemCount);
				item.put("deptName", itemInfo.getDepartmentName());
				results.put(departmentId, item);
			}
		}
		ArrayList<Map<String, Object>> mapList = new  ArrayList<Map<String,Object>>(results.values());
		Collections.sort(mapList,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return  (int)o2.get("itemCount") - (int)o1.get("itemCount");
			}});
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("results", mapList);
		return resultMap;
	}

	@Override
	public Map<String, Object> findItemCountByDept(Map<String, Object> params) {
		Long departmentId = (Long) params.get("departmentId");
		String allDataStr = (String) params.get("allDataStr");
		JSONObject allDataJson = JSONObject.fromObject(allDataStr);
		Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(allDataJson, HashMap.class);
		Set<String> innerCodeSet = map.keySet();
		List<String> innerCodes = new LinkedList();
		int sumCount = 0;
		List<Map<String, Object>> results = new LinkedList<Map<String,Object>>();
		for(String innerCode :innerCodeSet) {
			innerCodes.add(innerCode);
		}
		
		params.put("innerCodes", innerCodes);
		params.put("departmentId", departmentId);
		List<Item> itemInfos = itemAccessService.findItemInfoList(params);//获取指定部门的 事项
		for(Item itemInfo:itemInfos) {
			int count = (int) map.get(itemInfo.getInnerCode());
			Map<String, Object> itemMap = new HashMap<>();
			itemMap.put("count", count);
			itemMap.put("itemName", itemInfo.getName());
			results.add(itemMap);
			sumCount += count;
		}
		System.out.println("sumCount is : " + sumCount);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Collections.sort(results,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return  (int)o2.get("count") - (int)o1.get("count");
			}});
		resultMap.put("results",results);
		return resultMap;
	}

}
