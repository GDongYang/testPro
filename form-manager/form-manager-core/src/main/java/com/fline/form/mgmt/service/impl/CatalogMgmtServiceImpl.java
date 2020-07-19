package com.fline.form.mgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Catalog;
import com.fline.form.access.service.CatalogAccessService;
import com.fline.form.action.vo.TreeNode;
import com.fline.form.mgmt.service.CatalogMgmtService;

@Service("catalogMgmtService")
public class CatalogMgmtServiceImpl implements CatalogMgmtService {
	
	@Resource
	private CatalogAccessService catalogAccessService;


	@Override
	public Pagination<Catalog> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Catalog> page) {
		return catalogAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Catalog catalog) {
		catalogAccessService.update(catalog);
	}

	@Override
	public void remove(Catalog catalog) {
		catalogAccessService.remove(catalog);
	}

	@Override
	public Catalog create(Catalog catalog) {
		return catalogAccessService.create(catalog);
	}

	@Override
	public Catalog findById(long id) {
		return catalogAccessService.findById(id);
	}

	@Override
	public List<TreeNode> findTree(Map<String, Object> map, long parentId) {
		List<Catalog> clist = catalogAccessService.findByCondition(map);
		if(clist != null && clist.size()>0) {
			return getTree(parentId,clist);
		}
		return null;
	}
	
	public List<TreeNode> getTree(long parentId, List<Catalog> clist) {
		List<TreeNode> result = null;
		Map<String, Boolean> state ;
		for (Catalog cat : clist) {
			if (cat.getParentId() == parentId) {
				if (result == null) {
					result = new ArrayList<TreeNode>();
				}
				TreeNode node = new TreeNode();
				state = new HashMap<String, Boolean>();
				state.put("active", cat.isActive());
				node.setId(cat.getId());
				node.setpId(String.valueOf(cat.getParentId()));
				node.setName(cat.getName());
				node.setChildren(getTree(cat.getId(), clist));
				node.setModel(cat);
				result.add(node);
			}
		}

		return result;
	}

	@Override
	public void authorize(Catalog catalog) {
		Map<String,Object> param = new HashMap<>();
		List<TreeNode> tlist = this.findTree(param,catalog.getId());
		
		if(!catalog.isActive()) {
			autChildren(tlist);
			catalog.setActive(true);
			catalogAccessService.authorize(catalog);
		} else {
			deaAutChildren(tlist);
			catalog.setActive(false);
			catalogAccessService.authorize(catalog);
			
			
		}
	}
	//激活子目录
	public void autChildren(List<TreeNode> tList) {
		if(tList != null) {
			for(TreeNode node : tList) {
				Catalog cat = (Catalog) node.getModel();
				if(!cat.isActive()) {
					cat.setActive(true);
					catalogAccessService.authorize(cat);
				}
				if(node.getChildren() != null) {
					autChildren(node.getChildren());
				}
			}
		}
	}
	
	//取消激活子目录
	public void deaAutChildren(List<TreeNode> tList) {
		if(tList != null) {
			for(TreeNode node : tList) {
				Catalog cat = (Catalog) node.getModel();
				if(cat.isActive()) {
					cat.setActive(false);
					catalogAccessService.authorize(cat);
				}
				if(node.getChildren() != null) {
					deaAutChildren(node.getChildren());
				}
			}
		}
	}

	@Override
	public long sequence() {
		return catalogAccessService.getSequence();
	}

}
