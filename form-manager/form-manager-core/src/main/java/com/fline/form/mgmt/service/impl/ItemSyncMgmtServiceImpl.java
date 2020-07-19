package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.*;
import com.fline.form.access.service.*;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.ItemSyncMgmtService;
import com.fline.form.job.ItemAddJobService;
import com.fline.form.job.ItemChangeJobService;
import com.fline.form.util.LocalHostUtil;
import com.fline.form.vo.DepartmentVo;
import com.fline.form.vo.DictionaryVo;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("itemSyncMgmtService")
public class ItemSyncMgmtServiceImpl implements ItemSyncMgmtService {

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
    // 获取同步的时间
    @Resource
    private SynchroAccessService synchroAccessService;
    //放无法解析的xml配置
    @Resource
    private ErrorXmlAccessService errorXmlAccessService;
    // 模板
    @Resource
    private CertTempAccessService certTempAccessService;
    @Resource
    private ItemChangeJobService itemChangeJobService;
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
            + "CONCAT( QL_MAINITEM_ID, '-', QL_SUBITEM_ID ) msCode, BELONGXIAQUCODE bcode, 1 active, UPDATE_DATE, ( CASE WHEN QL_DEP LIKE '%%交%%警%%' "
            + "THEN '交警总队' WHEN QL_DEP LIKE '%%网警%%' THEN '网警总队' WHEN QL_DEP LIKE '%%消防%%' THEN '消防局' WHEN QL_DEP LIKE '%%禁毒%%' THEN '禁毒总队' "
            + "WHEN QL_DEP LIKE '%%治安%%' THEN '治安总队' ELSE NULL END ) AS memo1, ( CASE WHEN Acp_institution LIKE '%%交%%警%%' THEN '交警总队' "
            + "WHEN Acp_institution LIKE '%%网警%%' THEN '网警总队' WHEN Acp_institution LIKE '%%消防%%' THEN '消防局' WHEN Acp_institution LIKE '%%禁毒%%' "
            + "THEN '禁毒总队' WHEN Dec_institution LIKE '%%治安%%' THEN '治安总队' ELSE NULL END ) memo2, ( CASE WHEN Dec_institution LIKE '%%交%%警%%' THEN '交警总队' "
            + "WHEN Dec_institution LIKE '%%网警%%' THEN '网警总队' WHEN Dec_institution LIKE '%%消防%%' THEN '消防局' WHEN Dec_institution LIKE '%%禁毒%%' THEN '禁毒总队' "
            + "WHEN Dec_institution LIKE '%%治安%%' THEN '治安总队' ELSE NULL END ) memo3, ( CASE WHEN LEAD_DEPT LIKE '%%交%%警%%' THEN '交警总队' WHEN LEAD_DEPT LIKE "
            + "'%%网警%%' THEN '网警总队' WHEN LEAD_DEPT LIKE '%%消防%%' THEN '消防局' WHEN LEAD_DEPT LIKE '%%禁毒%%' THEN '禁毒总队' WHEN LEAD_DEPT LIKE '%%治安%%' "
            + "THEN '治安总队' ELSE NULL END ) memo4 FROM qlt_qlsx WHERE 1 = 1 %s  ";

    /**
     * 不重复的数据 总数
     */
    private static final String COUNT_SQL_ONLY = " SELECT count(*) FROM qlt_qlsx WHERE 1 = 1 %s  ";

    /**
     * 数据不重复的 问题数据
     */
    private static final String SQL_ONLY_PROBLEM_DATA = "SELECT QL_INNER_CODE AS innerCode, count(*) AS count FROM qlt_qlsx where State2 is not null GROUP BY QL_INNER_CODE HAVING count = 1";

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
     * @param innerCode
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
    public void cronJob() throws Exception {
        // 获取本机IP
        String ip= LocalHostUtil.getLocalHostLANAddress().getHostAddress();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("开始执行数据迁移定时任务，服务器地址={}", ip);
        }
        long startTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sb = new StringBuffer(" AND tong_time >= STR_TO_DATE('");
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = sf.format(date);
        this.getDeptAndAllDist();
        // 获取同步的初始时间
        Synchro sy = this.synchroAccessService.findStartTime();
        sb.append(sf.format(sy.getStartTime()) + "','%Y-%m-%d %H:%i:%s') and tong_time <= STR_TO_DATE('");
        sb.append(endDate + "','%Y-%m-%d %H:%i:%s')");
        // 把数据库中的endTime时间设置为endDate
        map.put("endTime", sf.parse(endDate));
        map.put("tableName", "qlt_qlsx");
        this.synchroAccessService.updateEndTimeByTableName(map);
        this.jobByPage(SQL_ONLY, COUNT_SQL_ONLY, sb.toString());
        // 将开始时间设置为和结束时间一样
        this.synchroAccessService.updateStartTimeByEndTime(map);
        // 进行模板和材料的模糊关联 ，只对当前同步的数据有效
        if(Detect.notEmpty(this.vaildInnerCodeCronList)) {
            map.put("innerCodes", this.vaildInnerCodeCronList);
            List<Long> sIdsList = this.situationAccessService.findSituationIdByInnerCode(map);
            if(Detect.notEmpty(sIdsList)) {
                // 查找已经已经激活的模板数据
                List<CertTemp> findAllCertTemp = this.certTempAccessService.findAllActive();
                map.put("situationIds", sIdsList);
                for (CertTemp certTemp : findAllCertTemp) {
                    if (!Detect.notEmpty(certTemp.getKeywords())) {
                        continue;
                    }
                    map.put("id", certTemp.getId());
                    map.put("name", certTemp.getKeywords());
                    this.materialAccessService.createRmaterialTemp(map);
                }
            }
        }
        // 删除多余的关联
        for (int i = 0; i < DEL_KWYWORD.length; i++) {
            String[] ob = DEL_KWYWORD[i];
            map.put("materialName", ob[0]);
            map.put("tempId", Integer.valueOf(ob[1]));
            this.materialAccessService.deleteOtherCard(map);
        }
        // 错误数据的事项状态修改
        this.problemData();
        long endTime = System.currentTimeMillis();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("结束执行数据迁移定时任务, 执行时间={}", endTime- startTime);
        }
    }

    /**
     * job:数据同步.
     *
     * @author 邵炜
     * @param term
     * @return
     */
    public Long job(String term) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("开始执行数据迁移任务");
        }
        long takeTime = 0l;
        try {
            long startTime = System.currentTimeMillis();
            Map<String, Object> map = new HashMap<String, Object>();
            this.getDeptAndAllDist();
            this.jobByPage(SQL_ONLY, COUNT_SQL_ONLY, term);
            // 进行模板和材料的模糊关联 ，只对当前同步的数据有效
            if(Detect.notEmpty(this.vaildInnerCodeList)) {
                map.put("innerCodes", this.vaildInnerCodeList);
                List<Long> sIdsList = this.situationAccessService.findSituationIdByInnerCode(map);
                if(Detect.notEmpty(sIdsList)) {
                    // 查找已经已经激活的模板数据
                    List<CertTemp> findAllCertTemp = this.certTempAccessService.findAllActive();
                    map.put("situationIds", sIdsList);
                    for (CertTemp certTemp : findAllCertTemp) {
                        if (!Detect.notEmpty(certTemp.getKeywords())) {
                            continue;
                        }
                        map.put("id", certTemp.getId());
                        map.put("name", certTemp.getKeywords());
                        this.materialAccessService.createRmaterialTemp(map);
                    }
                }
            }
            // 删除多余的关联
            for (int i = 0; i < DEL_KWYWORD.length; i++) {
                String[] ob = DEL_KWYWORD[i];
                map.put("materialName", ob[0]);
                map.put("tempId", Integer.valueOf(ob[1]));
                this.materialAccessService.deleteOtherCard(map);
            }
            // 错误数据的事项状态修改
            this.problemData();
            long endTime = System.currentTimeMillis();
            takeTime = endTime - startTime;
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("结束执行数据迁移任务, 执行时间={}", takeTime);
            }
        } catch (Exception e) {
            LOGGER.error("事项同步异常", e);
            throw new RuntimeException("事项同步异常:" + e.getMessage());
        }
        return takeTime;
    }

    /**
     * @Description : 错误数据的事项状态修改
     * @author : shaowei
     * @return
     */
    private void problemData() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("错误事项数据处理");
        }
        List<String> problemDataList = new ArrayList<>();
        Map<String, Object> mapProblem = new HashMap<>();
        try {
            List<Map<String, Object>> listOnly = this.gaqzkJdbcTemplate.queryForList(SQL_ONLY_PROBLEM_DATA);
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

    /**
     * @Description :分页处理
     * @author : shaowei
     * @param sql
     * @throws Exception
     */
    private void jobByPage(String sql, String countSql, String term) throws Exception {
        List<Map<String, Object>> list = null;
        int size = this.gaqzkJdbcTemplate.queryForObject(String.format(countSql, term), Integer.class);
        boolean sp = size % 1000 == 0;
        int pageNo = sp == true ? size / 1000 : size / 1000 + 1;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("本次同步的事项数量为={},页数为={}", size, pageNo);
        }
        for (int i = 1; i <= pageNo; i++) {
            list = this.gaqzkJdbcTemplate.queryForList(String.format(sql, term) + " limit " + (i - 1) * 1000 + ",1000");
            this.itemJob(list);
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
    private void itemJob(List<Map<String, Object>> list) throws Exception {
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
            if("001008013001061".equals(deptCode)) {
                deptCode = "001008013010061";
            }
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
            String deptName = map.get("deptName") == null ? "" : map.get("deptName").toString();
            this.updateInnerCode(innerCode, deptCode, map.get("code") == null ? "" : map.get("code").toString(), deptName, this.listAllDept);
            // 如果事项库中存在则要替换掉老的事项
            if (this.listAllInnerCode.contains(innerCode) && this.checkIsAppItem(innerCode)) {
                it.remove(); // 如果事项库中存在 而且是App事项，则不需要重新加入事项表中,并且不需要删除情形和材料内容
                continue;
            } else if (this.listAllInnerCode.contains(innerCode) && !this.checkIsAppItem(innerCode)) {
                listRepeat.add(innerCode); // 如果事项库中存在 ,不是app事项 需要删除情形和材料内容。
                it.remove(); // 如果事项库中存在 ，则不需要重新加入事项表中
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
            String pop = rootElement.element("POPULARTYPES").getStringValue();
            // 处理情形
            Element conditions = rootElement.element("CONDITIONS");
            String elementMC = rootElement.element("CONDITIONS").getStringValue();
            Iterator<Element> conditionIt = conditions.elementIterator("CONDITION");
            if (Detect.notEmpty(elementMC)) {
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
