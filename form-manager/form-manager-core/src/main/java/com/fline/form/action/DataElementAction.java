package com.fline.form.action;

import java.util.*;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.DataElement;
import com.fline.form.mgmt.service.DataElementMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class DataElementAction extends AbstractAction implements ModelDriven<DataElement>{
	
	private static final long serialVersionUID = 1L;
	private DataElementMgmtService dataElementMgmtService;
	private DataElement dataElement;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private String[] fields;
	private String[] fieldNames;

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
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

	public void setDataElementMgmtService(DataElementMgmtService dataElementMgmtService) {
		this.dataElementMgmtService = dataElementMgmtService;
	}

	public DataElement getDataElement() {
		return dataElement;
	}

	public void setDataElement(DataElement dataElement) {
		this.dataElement = dataElement;
	}

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    public String findPage() {
		Pagination<DataElement> page= new Pagination<DataElement>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		page = dataElementMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	public String create() {
	    try {
            List<DataElement> dataElements = new ArrayList<>();
            if(fields != null && fields.length >0) {
                for (int i = 0; i < fields.length; i++) {
                    DataElement temp = new DataElement();
                    temp.setDataelementid(UUID.randomUUID().toString().replace("-",""));
                    temp.setDataformat("C0..200");
                    temp.setField(fields[i]);
                    temp.setFieldname(fieldNames[i]);
                    temp.setFormCode(dataElement.getFormCode());
                    dataElements.add(temp);
                }
            }
            dataElementMgmtService.createList(dataElements, dataElement.getFormCode());
            dataMap.put("code", 1);
        } catch (Exception e) {
	        e.printStackTrace();
            dataMap.put("code", -1);
        }
		return SUCCESS;
	}

	public String update() {
		dataElementMgmtService.update(dataElement);
		return SUCCESS;
	}
	
	public String remove() {
		dataElementMgmtService.remove(dataElement);
		return SUCCESS;
	}

    @Override
	public DataElement getModel() {
		if(dataElement == null) {
			dataElement= new DataElement();
		}
		return dataElement;
	}

}
