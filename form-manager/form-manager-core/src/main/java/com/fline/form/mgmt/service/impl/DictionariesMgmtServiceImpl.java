package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Dictionaries;
import com.fline.form.access.service.DictionariesAccessService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DictionariesMgmtService;
import com.fline.form.vo.DictionariesVo;

@Service("dictionariesMgmtService")
public class DictionariesMgmtServiceImpl implements DictionariesMgmtService,Cacheable {

	@Resource
	private DictionariesAccessService dictionariesAccessService;

	@Resource
	private DataCacheService dataCacheService;
	
	@Override
	public Pagination<Dictionaries> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Dictionaries> page) {
		return dictionariesAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Dictionaries dictionaries) {
		dictionariesAccessService.update(dictionaries);
	}

	@Override
	public void remove(Dictionaries dictionaries) {
		dictionariesAccessService.remove(dictionaries);
	}

	@Override
	public Dictionaries create(Dictionaries dictionaries) {
		return dictionariesAccessService.create(dictionaries);
	}

	@Override
	public Dictionaries findById(long id) {
		return dictionariesAccessService.findById(id);
	}

	@Override
	public void refreshCache() {
		// 清除缓存
		this.dataCacheService.clearDictionaries();
		List<DictionariesVo> findAllVo = this.dictionariesAccessService.findAllVo();
		
		Map<String,Object> dictionariesMap = new  HashMap<String, Object>();
		if(Detect.notEmpty(findAllVo)) {
			for (DictionariesVo dictionariesVo : findAllVo) {
				if(dictionariesMap.containsKey(dictionariesVo.getTemplate())) {
					@SuppressWarnings("unchecked")
					List<DictionariesVo> list = (List<DictionariesVo>) dictionariesMap.get(dictionariesVo.getTemplate());
					list.add(dictionariesVo);
				}else {
					List<DictionariesVo> list = new LinkedList<DictionariesVo>();
					list.add(dictionariesVo);
					dictionariesMap.put(dictionariesVo.getTemplate(), list);
				}
			}
		}
		this.dataCacheService.setDictionaries(dictionariesMap);
		
	}

}
