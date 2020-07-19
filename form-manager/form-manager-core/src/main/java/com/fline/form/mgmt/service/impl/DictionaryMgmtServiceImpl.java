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
import com.fline.form.access.model.Dictionary;
import com.fline.form.access.service.DictionaryAccessService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DictionaryMgmtService;
import com.fline.form.util.DataShare;
import com.fline.form.vo.DictionaryVo;

@Service("dictionaryMgmtService")
public class DictionaryMgmtServiceImpl implements DictionaryMgmtService, Cacheable {

	@Resource
	private DictionaryAccessService dictionaryAccessService;
	
	@Resource
	private DataCacheService dataCacheService;
	

	@Override
	public Pagination<Dictionary> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Dictionary> page) {
		return dictionaryAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(Dictionary dictionary) {
		dictionaryAccessService.update(dictionary);
	}

	@Override
	public void remove(Dictionary dictionary) {
		dictionaryAccessService.remove(dictionary);
	}

	@Override
	public Dictionary create(Dictionary dictionary) {
		return dictionaryAccessService.create(dictionary);
	}

	@Override
	public Dictionary findById(long id) {
		return dictionaryAccessService.findById(id);
	}

	@Override
	public void refreshCache() {
		// 清除缓存
		this.dataCacheService.clearDictionary();
		List<DictionaryVo> findAllVo = this.dictionaryAccessService.findAllVo();
		
		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
		if(Detect.notEmpty(findAllVo)) {
			for (DictionaryVo dictionaryVo : findAllVo) {
				if(dictionaryMap.containsKey(dictionaryVo.getType().toString())) {
					@SuppressWarnings("unchecked")
					List<DictionaryVo> list = (List<DictionaryVo>) dictionaryMap.get(dictionaryVo.getType().toString());
					list.add(dictionaryVo);
				}else {
					List<DictionaryVo> list = new LinkedList<DictionaryVo>();
					list.add(dictionaryVo);
					dictionaryMap.put(dictionaryVo.getType().toString(), list);
				}
			}
		}
		this.dataCacheService.setDictionary(dictionaryMap);
	}

}
