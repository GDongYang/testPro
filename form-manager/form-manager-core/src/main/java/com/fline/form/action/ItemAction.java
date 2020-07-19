package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Synchro;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.form.mgmt.service.SynchroMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.fline.yztb.vo.ItemVo;
import com.opensymphony.xwork2.ModelDriven;

import javax.annotation.Resource;
import java.util.*;

public class ItemAction extends AbstractAction implements ModelDriven<Item> {

	private static final long serialVersionUID = 9124265885866185554L;

	private ItemMgmtService itemMgmtService;
	private UserSessionManagementService userSessionManagementService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;

	private Item item;
	private String result;
	private String repeat;

	private String[] certTempIdS;
	private String[] certTempIdS1;
	private String[] positionIdS;
	private long[] itemIds;
	private List<Long> deptIds;
	private String[] deptNames;
	private String isMust;
	private String startDate;
	private String endDate;
	private String tongTime;
	private String tableName;
	private Long jobDeptId;
	private String jobStr;
	private Integer jobLevel;

	@Resource
	private SynchroMgmtService synchroMgmtService;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTongTime() {
		return tongTime;
	}

	public void setTongTime(String tongTime) {
		this.tongTime = tongTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public long[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(long[] itemIds) {
		this.itemIds = itemIds;
	}

	public String[] getCertTempIdS() {
		return certTempIdS;
	}

	public void setCertTempIdS(String[] certTempIdS) {
		this.certTempIdS = certTempIdS;
	}

	public String[] getCertTempIdS1() {
		return certTempIdS1;
	}

	public void setCertTempIdS1(String[] certTempIdS1) {
		this.certTempIdS1 = certTempIdS1;
	}

	public String[] getPositionIdS() {
		return positionIdS;
	}

	public void setPositionIdS(String[] positionIdS) {
		this.positionIdS = positionIdS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getDataMap() {

		return dataMap;

	}

	public void setDataMap(Map<String, Object> dataMap) {

		this.dataMap = dataMap;

	}

	public void setItemMgmtService(ItemMgmtService itemMgmtService) {

		this.itemMgmtService = itemMgmtService;

	}

	public ItemMgmtService getItemMgmtService() {
		return itemMgmtService;
	}

	public UserSessionManagementService getUserSessionManagementService() {
		return userSessionManagementService;
	}

	public void setUserSessionManagementService(UserSessionManagementService userSessionManagementService) {
		this.userSessionManagementService = userSessionManagementService;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setDeptNames(String[] deptNames) {
		this.deptNames = deptNames;
	}

	public void setJobDeptId(Long jobDeptId) {
		this.jobDeptId = jobDeptId;
	}

	public void setJobStr(String jobStr) {
		this.jobStr = jobStr;
	}

	public void setJobLevel(Integer jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String save() {
		try {
            item.setActive("true");
            item.setExecutable(1);
			itemMgmtService.create(item, positionIdS, certTempIdS,certTempIdS1);
			dataMap.put("msg", "新增成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "新增失败!");
		}

		return "resultMap";
	}

	public String copy() {
		int count = itemMgmtService.copy(itemIds, positionIdS, item.getDepartmentId(), item.getDepartmentName());
		dataMap.put("resultCode", 1);
		dataMap.put("resultMsg",  "复制成功,共" + count + "条");
		return "resultMap";
	}

	public String update() {
		try {
		    item.setActive("1");
			itemMgmtService.update(item, positionIdS, certTempIdS, certTempIdS1);
			Item lastItem = itemMgmtService.findById(item.getId());//获取最新版本的数据
			itemMgmtService.createToCache(lastItem);
			dataMap.put("msg", "修改成功!");
		}catch(Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "修改失败!");
		}
		return "resultMap";
	}

	public String updateC() {
		Item it = itemMgmtService.findById(item.getId());
		if (item.getName() != null) {
			it.setName(item.getName());
		}
		if (item.getDepartmentId() != null) {
			it.setDepartmentId(item.getDepartmentId());
			Map<String, Object> params = new HashMap<>();
			params.put("ITEM_ID", item.getId());
			itemMgmtService.deletePositionItem(params);
		}
		if (item.getMemo() != null) {
			it.setMemo(item.getMemo());
		}
		if (item.getActive() != null) {
			it.setActive(item.getActive());
		}
		if (item.getParent() != null) {
			it.setParent(item.getParent());
		}
		itemMgmtService.update(it, positionIdS, certTempIdS,certTempIdS1);
		return SUCCESS;
	}

	public String findPositionName() {
		String listStr = itemMgmtService.findPositionName(String.valueOf(item.getId()));
		dataMap.put("result", listStr);
		return "resultMap";
	}

	public String findPositionId() {
		List<String> listStr = itemMgmtService.findPositionId(String.valueOf(item.getId()));
		dataMap.put("result", listStr);
		return "resultMap";
	}

	public String remove() {
		itemMgmtService.remove(item);
		return SUCCESS;
	}

	public String findById() {
		Item item1 = itemMgmtService.findById(item.getId());
		dataMap.put("item", item1);
		return SUCCESS;
	}

	/**
	 * findItemByFormCode:根据formCode查询相关的事项信息
	 *
	 * @author 邵炜
	 * @return
	 */
	public String findItemByFormCode() {
		List<ItemVo> list = itemMgmtService.findItemByFormCode(item.getFormCode());
		dataMap.put("item", list);
		return "resultMap";
	}

	public String findAll() {
		List<Item> ListItem = itemMgmtService.findAll();
		dataMap.put("result", ListItem);
		return "resultMap";
	}

	public String findRTemp() {
        List<Map<String, Object>> list = itemMgmtService.findRTempWithName(String.valueOf(item.getId()),isMust);
		dataMap.put("result", list);
		return "resultMap";
	}

	public String findPaeTable() {
		Pagination<Item> page = new Pagination<Item>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		String departId = item.getDepartmentId();
		Map<String, Object> param = new HashMap<String, Object>();

		//param.put("name", item.getName());
		param.put("nameLike",item.getName());
		// 判断是否是重新绑定类型
		param.put("executable", item.getExecutable());
		param.put("active", 1);
		if(Detect.notEmpty(repeat)) {
			param.put("repeat", "repeat");
		}
		//param.put("code", item.getCode());
		//param.put("innerCode", item.getInnerCode());

		Ordering order = new Ordering();
		if(Detect.notEmpty(departId) && !departId.equals("1")) {//如果不是顶级节点则进入
            param.put("deptIdLike", departId);
		}
		Pagination<Item> pageData = itemMgmtService.findPaginationTable(param, order, page);
		//存储事项ID列表
		List<Long> itemIds = new LinkedList();
		if(Detect.notEmpty(pageData.getItems())) {
			for(Item item : pageData.getItems()) {
				itemIds.add(item.getId());
			}
			param.put("itemIds", itemIds);
			//获取情形->材料->证明 的数量
			List<Map<String, Object>> relateCounts = itemMgmtService.findRelateCounts(param);
			dataMap.put("counts", relateCounts);
		}
		dataMap.put("rows",pageData.getItems());
		dataMap.put("total", pageData.getCount());
		return "resultMap";
	}

	public String findList() {
		Map<String, Object> param = new HashMap<String, Object>();

		if(item.getType() == 1) {
			param.put("type", 1);
			param.put("deptId", item.getDepartmentId());
		} else {
			param.put("deptIdLike", item.getDepartmentId());
		}
		List<Item> list = itemMgmtService.findList(param);
		dataMap.put("result", list);
		return "resultMap";
	}

	public String findByPosition() {
		List<Item> items = itemMgmtService.findItemByPositionId(Long.parseLong(item.getPositionId()));
		dataMap.put("items", items);
		return "resultMap";
	}

	public String bindFormTemp() {
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemId", item.getId());
			params.put("formCode", item.getFormCode());
			itemMgmtService.bindFormTemp(params);
			Item findItem = itemMgmtService.findById(item.getId());
			itemMgmtService.createToCache(findItem);
			dataMap.put("msg", "关联成功!");
		}catch (Exception e) {
			dataMap.put("msg", "关联失败!");
		}

		return "resultMap";
	}

	public String copyItem() {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("fromItemIds", itemIds);//待复制的事项id
			params.put("copyDeptIds", deptIds);//复制到的部门Id
			params.put("copyDeptNames", deptNames);
			params.put("jobDeptId", jobDeptId);
			params.put("jobStr",jobStr);
			params.put("jobLevel", jobLevel);
			itemMgmtService.copyItem(params);
			dataMap.put("msg", "复制成功");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "复制失败");
		}
		return "resultMap";
	}

	public String saveToCache() {
		try {
			Item findItem = itemMgmtService.findById(item.getId());
			itemMgmtService.createToCache(findItem);
			dataMap.put("msg", "更新缓存成功!");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "更新缓存失败，请重试");
		}
		return "resultMap";
	}
	@Override
	public Item getModel() {
		if (item == null) {
			item = new Item();
		}
		return item;
	}

	/**
	 * findQlItemPage:查询权力事项库的事项信息.
	 *
	 * @author 邵炜
	 * @return
	 */
	public String findQlItemPage() {
		Pagination<Item> pageData;
		try {
			Pagination<Item> page = new Pagination<Item>();
			page.setIndex(pageNum);
			page.setSize(pageSize);
			pageData = this.itemMgmtService.findQlItemPage(page, item, endDate, startDate);
			dataMap.put("rows",pageData.getItems());
			dataMap.put("total", pageData.getCount());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("rows", new ArrayList<>());
			dataMap.put("total", 0);
		}
		return "resultMap";
	}

	/**
	 * updateInnerCode:更新本地事项库的内部便编码.
	 *
	 * @author 邵炜
	 * @return
	 */
	public String updateInnerCode() {
		Map<String, Object> map = new HashMap<>();
		if (Detect.notEmpty(startDate) && Detect.notEmpty(endDate)) {
			map.put("tableName", "c_item");
			map.put("endTime", endDate);
			map.put("startTime", startDate);
			map.put("updateTime", new Date());
			try {
				map.put("state", 2);// 正在同步中
				this.synchroMgmtService.updateSate(map);
				this.itemMgmtService.updateInnerCode(startDate, endDate);
			} catch (Exception e) {
				map.put("state", 3);// 同步异常
				this.synchroMgmtService.updateSate(map);
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * findUpdateState:获取当前更新编码的信息.
	 *
	 * @author 邵炜
	 * @return
	 */
	public String findUpdateState() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("tableName", tableName);
			Synchro synchro = this.synchroMgmtService.findUpdateState(map);
			dataMap.put("data", synchro);
		} catch (Exception e) {
			dataMap.put("data", null);
			e.printStackTrace();
		}
		return "resultMap";
	}

	/**
	 * synchroByInnerCodeAndTongTime:同步单条事项.
	 *
	 * @author 邵炜
	 * @return
	 */
	public String synchroByInnerCodeAndTongTime() {
		try {
			this.itemMgmtService.synchroByInnerCodeAndTongTime(tongTime,item.getInnerCode());
            dataMap.put("code", 1);
		} catch (Exception e) {
		    dataMap.put("code", -1);
			e.printStackTrace();
		}
		return "resultMap";
	}

    public String syncAllItem() {
        try {
            this.itemMgmtService.syncAllItem(item.getDepartmentId());
            dataMap.put("code", 1);
        } catch (Exception e) {
            dataMap.put("code", -1);
            e.printStackTrace();
        }
        return "resultMap";
    }

	/**
	 * synchroByTerm:根据前端输入的条件进行同步.
	 *
	 * @author 邵炜
	 * @return
	 */
	public String synchroByTerm() {
		Map<String, Object> map = new HashMap<>();
		map.put("updateTime", new Date());
		map.put("tableName", "qlt_qlsx");
		map.put("term", "批量同步条件：" + (Detect.notEmpty(item.getInnerCode()) ? item.getInnerCode() : "") +
				(Detect.notEmpty(item.getCode()) ? "," + item.getCode() : ""));
		try {
			map.put("state", 2);// 正在同步中
			this.synchroMgmtService.updateSate(map);
			this.itemMgmtService.synchroByTerm(item);
		} catch (Exception e) {
			map.put("state", 3);// 同步异常
			this.synchroMgmtService.updateSate(map);
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
