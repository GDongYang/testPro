package com.fline.form.access.service;


import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Dictionaries;
import com.fline.form.vo.DictionariesVo;

public interface DictionariesAccessService extends AbstractNamespaceAccessService<Dictionaries> {
	
	List<DictionariesVo> findAllVo();
}
