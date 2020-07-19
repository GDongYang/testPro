package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.DataElement;

public interface DataElementMgmtService {
	Pagination<DataElement> findPagination(Map<String, Object> param,
			Ordering order, Pagination<DataElement> page);

	void update(DataElement dataElement);

	void remove(DataElement dataElement);

	DataElement create(DataElement dataElement);

	DataElement findById(long id);

    void createList(List<DataElement> dataElements, String formCode);

}
