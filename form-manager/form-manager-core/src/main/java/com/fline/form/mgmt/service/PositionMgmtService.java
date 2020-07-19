package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Position;

public interface PositionMgmtService {
	Pagination<Position> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Position> page);

	void update(Position position);//只更新岗位相关的信息

	void remove(Position position);

	Position create(Position position,String[] itemCodes);

	Position findById(long id);
	
	//Pagination<Position> findAllPagination(Map<String, Object> parameter, Pagination<Position> page);

	List<Position> findList(Map<String, Object> parameter);
	
	List<Position> findAll(Map<String, Object> parameter);
	
	List<String> findItems(long positionId);
	
	void createToCache(Position position);
	
	void bindItems(Map<String, Object> params);

}
