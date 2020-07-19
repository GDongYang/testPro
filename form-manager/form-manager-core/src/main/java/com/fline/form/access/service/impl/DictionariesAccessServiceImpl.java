package com.fline.form.access.service.impl;

import java.util.List;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Dictionaries;
import com.fline.form.access.service.DictionariesAccessService;
import com.fline.form.vo.DictionariesVo;

public class DictionariesAccessServiceImpl extends AbstractNamespaceAccessServiceImpl<Dictionaries>
		implements DictionariesAccessService {

	@SuppressWarnings("unchecked")
	@Override
	public List<DictionariesVo> findAllVo() {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findAllVo");
	}

}
