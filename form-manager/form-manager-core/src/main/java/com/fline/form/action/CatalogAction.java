package com.fline.form.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Catalog;
import com.fline.form.action.vo.TreeNode;
import com.fline.form.mgmt.service.CatalogMgmtService;
import com.opensymphony.xwork2.ModelDriven;

public class CatalogAction extends AbstractAction implements
		ModelDriven<Catalog> {

	private static final long serialVersionUID = -4513394162488296853L;

	private CatalogMgmtService catalogMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private int pageNum;

	private int pageSize;

	private Catalog catalog;

	public Map<String, Object> getDataMap() {

		return dataMap;

	}

	public void setDataMap(Map<String, Object> dataMap) {

		this.dataMap = dataMap;

	}

	public void setCatalogMgmtService(CatalogMgmtService catalogMgmtService) {

		this.catalogMgmtService = catalogMgmtService;

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

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	//新增
	public String save() {
		catalogMgmtService.create(catalog);
		return SUCCESS;
	}

	//修改
	public String update() {
		catalogMgmtService.update(catalog);
		return SUCCESS;
	}

	//删除
	public String remove() {
		catalogMgmtService.remove(catalog);
		return SUCCESS;
	}
	
	//激活
	public String authorize() {
		catalogMgmtService.authorize(catalog);
		return SUCCESS;
	}
	
	//查询tree
	public String findTree() {
		Map<String, Object> params = new HashMap<String,Object>();
		if(catalog.isActive()){
			params.put("active", 1);
		}
		TreeNode node = new TreeNode();
		node.setId(0L);
		node.setName("根目录");
		node.setChildren(catalogMgmtService.findTree(params,0));
		List<TreeNode> tList = new ArrayList<>();
		tList.add(node);
		dataMap.put("catalogTree", tList);
		return SUCCESS;
	}
	
	/**根据id查询*/
	public String findById() {
		Catalog catalog1 = catalogMgmtService.findById(catalog.getId());
		dataMap.put("catalog", catalog1);
		return SUCCESS;
	}

	/**分页查询*/
	public String findPage() {
		Pagination<Catalog> page = new Pagination<Catalog>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		Ordering order = new Ordering();
		// order.addDesc("CREATED");
		Pagination<Catalog> pageData = catalogMgmtService.findPagination(param,
				order, page);
		dataMap.put("rows", pageData.getItems());
		return SUCCESS;
	}

	@Override
	public Catalog getModel() {
		if (catalog == null) {
			catalog = new Catalog();
		}
		return catalog;
	}
}