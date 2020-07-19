package com.fline.form.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.ErrorXml;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Material;
import com.fline.form.access.model.Situation;
import com.fline.form.access.service.CertTempAccessService;
import com.fline.form.access.service.DepartmentAccessService;
import com.fline.form.access.service.ErrorXmlAccessService;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.access.service.MaterialAccessService;
import com.fline.form.access.service.SituationAccessService;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.util.LocalHostUtil;
import com.fline.form.vo.DepartmentVo;
import com.fline.form.vo.DictionaryVo;

@Component("itemAddJobService")
public class ItemAddJobService {

	// 公安前置库
	@Resource
	private JdbcTemplate gaqzkJdbcTemplate;
	// 事项库
	@Resource
	private ItemAccessService itemAccessService;
	// 情形
	@Resource
	private SituationAccessService situationAccessService;
	// 部门
	@Resource
	private DepartmentAccessService departmentAccessService;

	// 材料
	@Resource
	private MaterialAccessService materialAccessService;
//
//	// 获取同步的时间
//	@Resource
//	private SynchroAccessService synchroAccessService;

	// 村放无法解析的xml配置
	@Resource
	private ErrorXmlAccessService errorXmlAccessService;

	// 模板
	@Resource
	private CertTempAccessService certTempAccessService;
	
	@Resource
	private ItemChangeJobService itemChangeJobService;
	
	/**
	 * 缓存.
	 */
	@Resource
	private DataCacheService dataCacheService;

	private List<DepartmentVo> listAllDept = null;

	private List<String> listAllInnerCode = null;

	private List<DictionaryVo> listDict = null;

	private List<String> vaildInnerCodeList = new ArrayList<String>();
	
	private List<String> vaildInnerCodeCronList = new ArrayList<String>();
	

	/**
	 * 清除多余的关联
	 */
	private static final String[][] DEL_KWYWORD = new String[][] { { "代理人身份证明", "2" }, { "机动车驾驶证申请表", "5" },
			{ "陪同人的身份证明", "2" }, { "监护人的身份证明", "2" }, { "被委托人", "2" } };

	/**
	 * 不显示的材料 类型设置 = 2
	 */
	private static final String[] NOT_SHOW_MATERIAL = { "往来台湾通行证", "往来港澳通行证" };

	/**
	 * 打印日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemAddJobService.class);

	/**
	 * 不重复的数据
	 */
	private static final String SQL_ONLY = "SELECT QL_NAME AS NAME, LEAD_DEPT, QL_INNER_CODE AS innerCode, OUGUID, MATERIAL_INFO, QL_KIND AS CODE, "
			+ "CONCAT( QL_MAINITEM_ID, '-', QL_SUBITEM_ID ) msCode, BELONGXIAQUCODE bcode, 1 active, UPDATE_DATE FROM qlt_qlsx WHERE 1 = 1 %s  ";

	/**
	 * 不重复的数据 总数
	 */
	private static final String COUNT_SQL_ONLY = " SELECT count(*) FROM qlt_qlsx WHERE 1 = 1 %s  ";

    /**
     * 数据不重复的 问题数据  1为在用 2，挂起 3为 取消
     */
    private static final String SQL_ONLY_PROBLEM_DATA = " SELECT QL_INNER_CODE as innerCode,QL_STATE,QL_NAME from qlt_qlsx where QL_STATE != '1' %s ";

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
	}

	/**
	 * 通过InnerCode删除事项等关联信息
	 * 
	 * @return
	 */
	private Boolean deleteByInnerCode(List<String> innerCodes) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("更新的事项InnerCode={}", innerCodes);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("innerCodes", innerCodes);
		try {
			this.materialAccessService.deleteMaterialTempByInnerCode(params);
			this.materialAccessService.deleteMaterialByInnerCode(params);
			this.situationAccessService.deleteSituationByInnerCode(params);
			return true;
		} catch (Exception e) {
			LOGGER.error("删除关联信息时出错", e);
			return false;
		}
	}

	/**
	 * cronJob:定时任务. 
	 *
	 * @author 邵炜
	 * @throws Exception
	 */
	//@Scheduled(cron = "0 0 3 * * ?")
    public void cronJob(String term) throws Exception {
    	// 获取本机IP
    	String ip= LocalHostUtil.getLocalHostLANAddress().getHostAddress();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("开始执行数据迁移定时任务，服务器地址={}", ip);
        }
        this.getDeptAndAllDist();
		this.jobByPage(SQL_ONLY, COUNT_SQL_ONLY, term);
        this.problemData(term);
        LOGGER.info("cronJob end success");
    }
	
	/**
	 * job:数据同步. 
	 *
	 * @author 邵炜
	 * @param term
	 * @return
	 */
	public void job(String term) throws Exception {
        this.getDeptAndAllDist();
        this.jobByPageInner(SQL_ONLY, COUNT_SQL_ONLY, term, 0);
	}

	/**
	 * @Description : 错误数据的事项状态修改
	 * @author : shaowei
	 * @return
	 */
	private void problemData(String term) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("错误事项数据处理");
		}
		List<String> problemDataList = new ArrayList<>();
		Map<String, Object> mapProblem = new HashMap<>();
		try {
			List<Map<String, Object>> listOnly = this.gaqzkJdbcTemplate.queryForList(String.format(SQL_ONLY_PROBLEM_DATA, term));
			if (!Detect.notEmpty(listOnly)) {
				return;
			}
			for (Map<String, Object> ob : listOnly) {
				if (ob.get("innerCode") == null) {
					continue;
				}
				problemDataList.add(ob.get("innerCode").toString());
			}
			if (!Detect.notEmpty(problemDataList)) {
				return;
			}
			mapProblem.put("innerCodes", problemDataList);
			this.itemAccessService.updateActive(mapProblem);
		} catch (DataAccessException e) {
			LOGGER.error("处理错误事项数据是出错", e);
		}
	}

    private void jobByPage(String sql, String countSql, String term) throws Exception {
        jobByPageInner(sql, countSql, term, 1);
    }

	/**
	 * @Description :分页处理
	 * @author : shaowei
	 * @param sql
	 * @throws Exception
	 */
	private void jobByPageInner(String sql, String countSql, String term, int type) throws Exception {
		List<Map<String, Object>> list = null;
		int size = this.gaqzkJdbcTemplate.queryForObject(String.format(countSql, term), Integer.class);
		boolean sp = size % 1000 == 0;
		int pageNo = sp == true ? size / 1000 : size / 1000 + 1;
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("本次同步的事项数量为={},页数为={}", size, pageNo);
		}
		for (int i = 1; i <= pageNo; i++) {
			list = this.gaqzkJdbcTemplate.queryForList(String.format(sql, term) + " limit " + (i - 1) * 1000 + ",1000");
			this.itemJob(list, type);
		}
	}

	/**
	 * checkIsAppItem:判断是否是App事项
	 *
	 * @author 邵炜
	 * @param innerCode
	 * @return
	 */
	private boolean checkIsAppItem(String innerCode) {
		Item item = this.itemAccessService.findByInnerCode(innerCode);
		if (item != null && Detect.notEmpty(item.getTheme())) {
			return true;
		}
		return false;
	}

	/**
	 * checkActive:检查是否是有问题的事项. 
	 *
	 * @author 邵炜
	 * @param innerCode
	 * @return
	 */
	private Boolean checkActive(String innerCode) {
		Map<String, Object> parmas = new HashMap<>();
		parmas.put("innerCode", innerCode);
		List<Item> list = this.itemAccessService.findItemByInnerCode(parmas);
		if(Detect.notEmpty(list)) {
			Item item = list.get(0);
			if("0".equals(item.getActive()) && !Detect.notEmpty(item.getDepartmentName())) {// 
				return true;
			}
		}
		return false;
	}
	
	/**
	 * updateInnerCode:	存在并上线则更新内部编码.
	 *
	 * @author 邵炜
	 * @param innerCode
	 * @param ouguId
	 * @param code
	 */
	private void updateInnerCode(String innerCode, String ouguId, String code, String deptName,List<DepartmentVo> list) {
		this.itemChangeJobService.doChangeInnerCode(innerCode, ouguId, code, deptName, list);
	}
	
	
	/**
	 * @Description :处理前置库信息增加事项
	 * @author : shaowei
	 * @throws Exception
	 */
	private void itemJob(List<Map<String, Object>> list, int type) throws Exception {
		if (!Detect.notEmpty(list) || !Detect.notEmpty(this.listAllDept)) {
			return;
		}
		Document doc = null;
		String id = null;
		String name = null;
		String typeId = null;
		String necessity = null;
		Material m = null;
		Situation situation = null;
		String innerCode = null;
		ErrorXml ex = null;
		// 只存没有情形的事项InnerCode 
		List<String> listInnerCodeNotExit = new ArrayList<>();
		// 存有情形的事项InnerCode
		List<String> listInnerCodeExit = new ArrayList<>();
		// 存放需要更新的InnerCode
		List<String> listRepeat = new ArrayList<String>();
		Map<String, Object> mapDept = new HashMap<>();
		Map<String, Material> mapMateria = new HashMap<>();
		Map<String, Situation> mapSituation = new HashMap<>();
		Map<String, Object> map = null;
		// 查找部门ID
		Iterator<Map<String, Object>> it = list.iterator();
		this.listAllInnerCode = this.itemAccessService.findAllInnerCode();
		while (it.hasNext()) {
			map = it.next();
			if (map.get("innerCode") == null) {
				it.remove(); // 如果innserCode 为空 要将移除 -- 存在几率极低
				continue;
			}
			innerCode = (String) map.get("innerCode");
			if (map.get("code") != null) {
				for (DictionaryVo vo : this.listDict) {
					if (map.get("code").equals(vo.getCode())) {
						map.put("code", vo.getName().toString().concat("-") + map.get("msCode"));
						break;
					}
				}
			}
			String deptCode = map.get("OUGUID") == null ? "" : map.get("OUGUID").toString();
//			if("001008013001061".equals(deptCode)) {
//				deptCode = "001008013010061";
//			}
			for (DepartmentVo department3 : this.listAllDept) {
				if (deptCode.equals(department3.getCode())) {
					map.put("departmentId", department3.getId());
					for (DepartmentVo dept2 : this.listAllDept) {
						if (department3.getParentId() != null
								&& dept2.getId() == Integer.parseInt(department3.getParentId())) {
							for (DepartmentVo dept1 : this.listAllDept) {
								if (dept2.getParentId() != null
										&& dept1.getId() == Integer.parseInt(dept2.getParentId())) {
									map.put("deptName",
											dept1.getName() + " / " + dept2.getName() + " / " + department3.getName());
									break;
								}
							}
							if (!map.containsKey("deptName")) {
								map.put("deptName", dept2.getName() + " / " + department3.getName());
								break;
							}
						}
					}
					if (!map.containsKey("deptName")) {
						map.put("deptName", department3.getName());
					}
					map.put("active", 1);
					for (int i = 1; i <= 4; i++) {
						if (map.containsKey("memo")) {
							break;
						}
						if (map.get("memo" + i) != null) {
							map.put("memo", map.get("memo" + i).toString());
						} else if (i == 4 && !map.containsKey("memo")) {
							map.put("memo", map.get("LEAD_DEPT"));
						}
					}
				}
			}
			//没有对应的部门不添加事项
			if(map.get("departmentId") == null) {
                it.remove();
                continue;
            }
			String deptName = map.get("deptName") == null ? "" : map.get("deptName").toString();
			this.updateInnerCode(innerCode, deptCode, map.get("code") == null ? "" : map.get("code").toString(), deptName, this.listAllDept);
			// 如果事项库中存在则要替换掉老的事项
			if (type == 1 && this.listAllInnerCode.contains(innerCode)) {
				it.remove(); // 如果事项库中存在 而且是App事项，则不需要重新加入事项表中,并且不需要删除情形和材料内容
				continue;
			} else if (type == 0 && this.listAllInnerCode.contains(innerCode)){
                listRepeat.add(innerCode);
			    it.remove();
            }
			if(this.checkActive(innerCode)) {// 部门有问题的事项
				Map<String, Object> deptMap = new HashMap<>();
				deptMap.put("innerCode", innerCode);
				deptMap.put("deptName", map.get("deptName"));
				deptMap.put("departmentId", map.get("departmentId"));
				this.itemAccessService.updteDeptNameByInnerCode(deptMap);
			}
			if (!map.containsKey("deptName") || map.get("deptName") == null || !Detect.notEmpty(map.get("deptName").toString())) {
				map.put("active", 0);
			}
			// 材料处理
			if (map.get("MATERIAL_INFO") == null) {
				// 属于没有xml但自动配置缺省情形
				listInnerCodeNotExit.add(innerCode);
				continue;
			}
			try {
				doc = DocumentHelper.parseText(map.get("MATERIAL_INFO").toString());
			} catch (Exception e) {
				// 属于xml错误但自动配置缺省情形
				listInnerCodeNotExit.add(innerCode);
				LOGGER.error("xml解析有问题" + e.getMessage(), e);
				// 把有问题的写入数据库中
				ex = new ErrorXml();
				ex.setInnerCode(innerCode);
				this.errorXmlAccessService.create(ex);
				continue;
			}
			Element rootElement = doc.getRootElement();
			Element element = rootElement.element("MATERIALS");
			String elementM = rootElement.element("MATERIALS").getStringValue();
			Iterator<Element> elementIterator = element.elementIterator("MATERIAL");
			if (Detect.notEmpty(elementM)) {
				int a = 0;
				while (elementIterator.hasNext()) {
					m = new Material();
					Element next = elementIterator.next();
					id = next.element("MATERIALGUID").getStringValue(); // 材料ID
					name = next.element("NAME").getStringValue(); // 材料名称
					necessity = next.element("NECESSITY").getStringValue(); // 材料必要性
					m.setName(name);
					m.setCode(id);
					m.setType(name.contains("申请表") ? 3 : 1); // 材料类型
					if (m.getType() != 3 && Detect.notEmpty(name)) {
						for (int k = 0; k < NOT_SHOW_MATERIAL.length; k++) {
							if (name.contains(NOT_SHOW_MATERIAL[k])) {
								m.setType(2); // 2为不显示的材料的类型
							}
						}
					}
					m.setIsMust(necessity == "" ? 3 : Integer.valueOf(necessity));// 材料必要性空的话则默认为3
					mapMateria.put(map.get("innerCode").toString() + a++, m);
				}
			}
			// 处理情形的类型
			Element populartypes = rootElement.element("POPULARTYPES");
            String pop = populartypes != null ? populartypes.getStringValue() : "";

			// 处理情形
			Element conditions = rootElement.element("CONDITIONS");
			String elementMC = conditions != null ? conditions.getStringValue() : "";
			if (Detect.notEmpty(elementMC)) {
                Iterator<Element> conditionIt = conditions.elementIterator("CONDITION");
                // 存在情形时也增加一条 缺省情形 配上所有的材料
				listInnerCodeNotExit.add(innerCode);
				// 存在情形时加入关联
				listInnerCodeExit.add(innerCode);
				int b = 0;
				while (conditionIt.hasNext()) {
					String keyS = map.get("innerCode").toString() + b++;
					situation = new Situation();
					Element next = conditionIt.next();
					typeId = next.element("TYPEID").getStringValue();
					if(Detect.notEmpty(pop)) {
						Iterator<Element> populartypeList = populartypes.elementIterator("POPULARTYPE");
						while (populartypeList.hasNext()) {
							Element populartype = populartypeList.next();
							if(populartype.element("TYPEID").getStringValue().equals(typeId)) {
								String typeno = populartype.element("TYPENO").getStringValue();
								try {
									situation.setType(Detect.notEmpty(typeno) ? Integer.valueOf(typeno) : 0);
								} catch (Exception e) {
									situation.setType(0);
									LOGGER.error("办事情形类别序号为中文", e);
								}
								situation.setDescribe(populartype.element("TYPENAME").getStringValue());
								break;
							}
						}
					}
					id = next.element("CONDITIONID").getStringValue(); // 情形ID
					name = next.element("CONDITIONNAME").getStringValue(); // 情形名称
					situation.setCode(id);
					situation.setName(name);
					Element materials = next.element("MATERIALS");
					String materialsValue = materials.getStringValue();
					Iterator<Element> iteratorMId = materials.elementIterator("MATERIALGUID");
					if (!Detect.notEmpty(materialsValue)) {
						mapSituation.put(keyS, situation);
						continue;
					}
					List<String> mIds = new ArrayList<>();
					while (iteratorMId.hasNext()) {
						Element nextMId = iteratorMId.next();
						String mId = nextMId.getStringValue();
						mIds.add(mId);
					}
					situation.setMaterialIds(mIds);
					mapSituation.put(keyS, situation);
				}
			} else {
				// 属于没有情形，有材料 或者 没有材料 都自动配置缺省情形
				listInnerCodeNotExit.add(innerCode);
			}
		}
        LOGGER.info("add list size :" + list.size());
		if (!Detect.notEmpty(list) && !Detect.notEmpty(listRepeat)) { // 修改和新增的 数据都没有 则无需下一步操作
			return;
		}
		// 需要 删除 情形 和 材料信息 的内部编码
		if(Detect.notEmpty(listRepeat)) {
			this.deleteByInnerCode(listRepeat);
		}
		// 事项库中不存在的事项信息
		if (Detect.notEmpty(list)) {
			this.itemAccessService.saveItemByQzk(list);
		}
		List<Map<String, Object>> listItemId = new ArrayList<>();
		if (Detect.notEmpty(listInnerCodeNotExit)) {
			mapDept.put("listInnerCode", listInnerCodeNotExit);
			listItemId = this.itemAccessService.findIdByInnerCode(mapDept);
		}
		Situation si = null;
		Set<String> keyMateria = mapMateria.keySet();
		for (Map<String, Object> mapItem : listItemId) {
			if (mapItem.get("id") == null) {
				continue;
			}
			si = new Situation();
			si.setName("缺省情形");
			si.setConfirm(1);
			si.setCode(mapItem.get("innerCode").toString());
			si.setItemId(Integer.parseInt(mapItem.get("id").toString()));
			si = this.situationAccessService.save(si);
			for (String key : keyMateria) {
				if (!key.startsWith((mapItem.get("innerCode").toString()))) {
					continue;
				}
				m = mapMateria.get(key);
				m.setId(0);
				m.setSituationId((int) si.getId());
				this.materialAccessService.create(m);
			}
		}
		// 存入有情形的
		if (!Detect.notEmpty(listInnerCodeExit)) {
			return;
		}
		mapDept.put("listInnerCode", listInnerCodeExit);
		List<Map<String, Object>> listItemIdS = this.itemAccessService.findIdByInnerCode(mapDept);
		if (!Detect.notEmpty(listItemIdS)) {
			return;
		}
		// 有情形的 情形类
		Set<String> keySetSExit = mapSituation.keySet();
		for (Map<String, Object> mapExit : listItemIdS) {
			if (mapExit.get("id") == null) {
				continue;
			}
			for (String keyS : keySetSExit) {
				if (keyS.contains(mapExit.get("innerCode").toString())) {
					si = mapSituation.get(keyS);
					si.setConfirm(1);
					si.setItemId(Integer.parseInt(mapExit.get("id").toString()));
					List<String> materialIds = si.getMaterialIds();
					si = this.situationAccessService.save(si);
					long sId = si.getId();
					for (String keyM : keyMateria) {
						if (keyM.substring(0, keyM.length() - 1).equals(keyS.substring(0, keyS.length() - 1))
								|| keyM.substring(0, keyM.length() - 2).equals(keyS.substring(0, keyS.length() - 2))
								|| keyM.substring(0, keyM.length() - 3).equals(keyS.substring(0, keyS.length() - 3))
								|| keyM.substring(0, keyM.length() - 1).equals(keyS.substring(0, keyS.length() - 2))
								|| keyM.substring(0, keyM.length() - 2).equals(keyS.substring(0, keyS.length() - 1))) {
							if (Detect.notEmpty(materialIds)) {
								for (String mId : materialIds) {
									if (mId.equals(mapMateria.get(keyM).getCode())) {
										m = mapMateria.get(keyM);
										m.setId(0);
										m.setSituationId((int) sId);
										m.setIsMust(1);// 如果是有情形的则 都是必要的材料
										this.materialAccessService.create(m);
									}
								}
							}
						}
					}
				}
			}
		}
		this.vaildInnerCodeList.addAll(listInnerCodeNotExit);
		this.vaildInnerCodeList.addAll(listInnerCodeExit);
		this.vaildInnerCodeCronList.addAll(listInnerCodeNotExit);
		this.vaildInnerCodeCronList.addAll(listInnerCodeExit);
		
	}

}
