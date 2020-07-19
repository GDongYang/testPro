package com.fline.form.mgmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.feixian.tp.common.util.Detect;
import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.DataElement;
import com.fline.form.access.service.DataElementAccessService;
import com.fline.form.mgmt.service.DataElementMgmtService;

@Service("dataElementMgmtService")
public class DataElementMgmtServiceImpl implements DataElementMgmtService {

	@Resource
	private DataElementAccessService dataElementAccessService;

	@Override
	public Pagination<DataElement> findPagination(Map<String, Object> param,
			Ordering order, Pagination<DataElement> page) {
		return dataElementAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(DataElement dataElement) {
		dataElementAccessService.update(dataElement);
	}

	@Override
	public void remove(DataElement dataElement) {
		dataElementAccessService.remove(dataElement);
	}

	@Override
	public DataElement create(DataElement dataElement) {
		return dataElementAccessService.create(dataElement);
	}

	@Override
	public DataElement findById(long id) {
		return dataElementAccessService.findById(id);
	}

    @Override
    public void createList(List<DataElement> dataElements, String formCode) {
        dataElementAccessService.removeByFormCode(formCode);
        if(Detect.notEmpty(dataElements)) {
            dataElementAccessService.saveList(dataElements);
        }
    }

}
