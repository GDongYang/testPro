package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Item;
import com.fline.form.access.service.ItemAccessService;
import com.fline.yztb.vo.ItemVo;

public class ItemAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Item> implements ItemAccessService {

	@Override
	public Pagination<Item> findPaginationTable(Map<String, Object> param, Ordering order, Pagination<Item> page) {
		return this.findPagination("findTable", param, order, page);
	}

	@Override
	public void createPositionItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Item.createPositionItem", param);
	}

	@Override
	public void createItemTemp(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Item.createItemTemp", param);
	}

	@Override
	public List<String> findRTemp(Map<String, Object> param) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findRTemp", param);
	}

	@Override
	public void deletePositionItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Item.deletePositionItem", param);
	}

	@Override
	public void deleteItemTemp(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Item.deleteItemTemp", param);
	}

	@Override
	public List<String> findPositionName(Map<String, Object> param) {
		List<String> listStr=this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findPositionName", param);
		return listStr;
	}

	@Override
	public List<String> findPositionId(Map<String, Object> param) {
		List<String> listStr=this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findPositionId", param);
		return listStr;
	}

	@Override
	public Item findItemCount(Map<String, Object> param) {
		Item itemCount = findOne("findItemCount",param);
		return itemCount;
	}
	
	@Override
	public Item findItemCountByInner(Map<String, Object> param) {
		Item itemCount = findOne("findItemCountByInner",param);
		return itemCount;
	}

	@Override
	public List<Item> findItemByPositionId(long positionId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("positionId", positionId);
		return this.find("findItemByPositionId", param);
	}

	@Override
	public void createNanWeiItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Item.createNanWeiItem", param);
	}

	@Override
	public Item findByCode(String code) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("code", code);
		return findOne("findByCode", param);
	}

	@Override
	public Item findByInnerCode(String innerCode) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("innerCode", innerCode);
		List<Item>  items = find(param);
		return Detect.notEmpty(items) ? items.get(0) : null;
	}
	
	@Override
	public List<Item> findByUser(long userId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		return find("findByUser", param);
	}
	
	@Override
	public Item findByZh(long deptId,String itemCode) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("deptId", deptId);
		param.put("itemCode", itemCode);
		return findOne("findByZh", param);
	}

    @Override
    public List<ItemVo> findAllVo() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
    }

    @Override
    public List<Map<String, Object>> findItemTemp() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findItemTemp");
    }

	@Override
	public List<Map<String, Object>> findItemTempByMap(Map<String, Object> param) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findItemTempByMap", param);
	}

    @Override
    public List<Map<String, Object>> findRTempWithName(Map<String, Object> param) {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findRTempWithName", param);
    }

    @Override
    public void saveItemByQzk(List<Map<String, Object>> list) {
        getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Item.saveItemByQzk", list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findIdByInnerCode(Map<String, Object> map) {
        return getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findIdByInnerCode", map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAllInnerCode() {
        return getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findAllInnerCode");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAllDict() {
        return getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findAllDict");
    }

	@Override
	public void deleteItemByInnerCode(Map<String, Object> map) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Item.deleteItemByInnerCode", map);		
	}

	@Override
	public void updateActive(Map<String, Object> map) {
		update("updateActive", map);
	}

	@Override
	public List<Long> findItemIdsByDeptIds(Map<String, Object> params) {
		return getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findItemIdsByDeptIds",params);
	}

	@Override
	public List<Map<String, Object>> findRelateCounts(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findRelateCounts", params);
	}

	@Override
	public String findItemNameByinnercode(String innerCode) {
		return (String) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForObject("Item.findItemNameByinnercode", innerCode);
	}

	@Override
	public void bindFormTemp(Map<String, Object> params) {
		update("bindFormTemp", params);
	}

	@Override
	public List<Item> findItemByFormCode(String formCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("formCode", formCode);
		return find("findItemByFormCode",params);
	}

	@Override
	public List<Map<String, Object>> findDeptInfo(Map<String, Object> params) {
		return  this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findDeptInfo",params);
	}

	@Override
	public List<Long> getSameItems(Map<String, Object> params) {
		return  this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.getSameItems",params);
	}

	@Override
	public List<Long> findSameDefaultSituationsMaterialItems(Map<String, Object> params) {
		return  this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findSameDefaultSituationsMaterialItems",params);
	}

	@Override
	public List<Long> findEmptyMaterialItems(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Item.findEmptyMaterialItems",params);
	}
	
	@Override
	public List<Item> findAllAppItem() {
		return find("findAllAppItem", new HashMap<String, Object>());
	}

	@Override
	public void updateInnerCode(Map<String, Object> parmas) {
		update("updateInnerCode", parmas);
	}

	@Override
	public void updteDeptNameByInnerCode(Map<String, Object> parmas) {
		update("updteDeptNameByInnerCode", parmas);
	}

	@Override
	public List<Item> findItemByInnerCode(Map<String, Object> parmas) {
		return find("findItemByInnerCode", parmas);
	}

	@Override
	public List<Item> findItemInfoList(Map<String, Object> params) {
		return find("findItemInfoList",params);
	}

	@Override
	public List<Item> findAppItem(Map<String, Object> params) {
		return find("findAppItem", params);
	}

    @Override
    public void updateConfirmStatus(long id, Integer status) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("id", id);
	    param.put("confirmStatus", status);
        update("updateConfirmStatus", param);
    }

    @Override
    public void updateImg(long id, byte[] img) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("formImg", img);
        update("updateImg", param);
    }

    @Override
    public Item findImg(long id) {
	    Map<String, Object> param = new HashMap<>();
        param.put("id", id);
	    return findOne("findImg", param);
    }

}
