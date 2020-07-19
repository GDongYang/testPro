package com.fline.form.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ItemConfirm;
import com.fline.form.mgmt.service.ItemConfirmMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class ItemConfirmAction extends AbstractAction implements ModelDriven<ItemConfirm>{
	
	private static final long serialVersionUID = 1L;
	private ItemConfirmMgmtService itemConfirmMgmtService;
	private ItemConfirm itemConfirm;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private int pageNum;
	private int pageSize;
	private long[] itemIds;
	private String[] itemInnerCodes;
	private String[] itemNames;
	private File file;

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

	public void setItemConfirmMgmtService(ItemConfirmMgmtService itemConfirmMgmtService) {
		this.itemConfirmMgmtService = itemConfirmMgmtService;
	}

	public ItemConfirm getItemConfirm() {
		return itemConfirm;
	}

	public void setItemConfirm(ItemConfirm itemConfirm) {
		this.itemConfirm = itemConfirm;
	}

    public long[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(long[] itemIds) {
        this.itemIds = itemIds;
    }

    public String[] getItemInnerCodes() {
        return itemInnerCodes;
    }

    public void setItemInnerCodes(String[] itemInnerCodes) {
        this.itemInnerCodes = itemInnerCodes;
    }

    public String[] getItemNames() {
        return itemNames;
    }

    public void setItemNames(String[] itemNames) {
        this.itemNames = itemNames;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String findPage() {
		Pagination<ItemConfirm> page= new Pagination<ItemConfirm>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		Map<String,Object> param = new HashMap<String,Object>();
		Ordering order = new Ordering();
		page = itemConfirmMgmtService.findPagination(param, order, page);
		dataMap.put("total", page.getCount());
        dataMap.put("rows", page.getItems());
		return SUCCESS;
	}
	
	public String create() {
		itemConfirmMgmtService.create(itemConfirm);
		return SUCCESS;
	}
	
	public String update() {
		itemConfirmMgmtService.update(itemConfirm);
		return SUCCESS;
	}
	
	public String remove() {
		itemConfirmMgmtService.remove(itemConfirm);
		return SUCCESS;
	}

	public String confirmList() {
	    try {
            itemConfirmMgmtService.confirmList(itemConfirm.getMemo(), itemConfirm.getStatus(), itemIds, itemNames, itemInnerCodes);
            dataMap.put("code", 1);
	    } catch (Exception e) {
            dataMap.put("code", -1);
	        e.printStackTrace();
        }
        return SUCCESS;
    }

    public String uploadImg() {
        try {
            itemConfirmMgmtService.uploadImg(itemConfirm.getItemId(), file);
            dataMap.put("code", 1);
        } catch (Exception e) {
            dataMap.put("code", -1);
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String previewImg() {
        try {
            String img = itemConfirmMgmtService.previewImg(itemConfirm.getItemId());
            dataMap.put("code", 1);
            dataMap.put("img", img);
        } catch (Exception e) {
            dataMap.put("code", -1);
            e.printStackTrace();
        }
        return SUCCESS;
    }
	
	@Override
	public ItemConfirm getModel() {
		if(itemConfirm == null) {
			itemConfirm= new ItemConfirm();
		}
		return itemConfirm;
	}

}
