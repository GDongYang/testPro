package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Position;
import com.fline.form.vo.PositionVo;

public interface PositionAccessService extends
		AbstractNamespaceAccessService<Position> {
	
	void createPositionItem(Map<String, Object> param);
	
	void deletePositionItem(long positionId);
	
	List<String> findItems(long positionId);

    List<PositionVo> findAllVo();

    List<Map<String, Object>> findPositionItem();
	
    
    List<Map<String,Object>> findPositionItemByMap(Map<String,Object> params);
}
