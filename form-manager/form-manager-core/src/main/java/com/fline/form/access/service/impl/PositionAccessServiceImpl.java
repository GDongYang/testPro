package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Position;
import com.fline.form.access.service.PositionAccessService;
import com.fline.form.vo.PositionVo;

public class PositionAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<Position> implements
        PositionAccessService {

	@Override
	public void createPositionItem(Map<String, Object> param) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.insert(namespace+".createPositionItem", param);
	}
	
	@Override
	public void deletePositionItem(long positionId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("positionId", positionId);
		this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.delete(namespace+".deletePositionItem", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findItems(long positionId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("positionId", positionId);
 		List<String> listStr = this.getIbatisDataAccessObject()
				.getSqlMapClientTemplate()
				.queryForList(namespace+".findItems", param);
		return listStr;
	}

    @Override
    public List<PositionVo> findAllVo() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
    }

    @Override
    public List<Map<String, Object>> findPositionItem() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findPositionItem");
    }

	@Override
	public List<Map<String, Object>> findPositionItemByMap(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
				.queryForList(namespace + ".findPositionItemByMap",params);
	}
}
