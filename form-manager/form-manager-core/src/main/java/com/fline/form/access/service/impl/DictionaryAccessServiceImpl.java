package com.fline.form.access.service.impl;

import java.util.List;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Dictionary;
import com.fline.form.access.service.DictionaryAccessService;
import com.fline.form.vo.DictionaryVo;

public class DictionaryAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<Dictionary> implements
DictionaryAccessService {

	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryVo> findAllVo() {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
	}

}
