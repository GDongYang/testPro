package com.fline.form.access.service;

import java.util.List;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.Dictionary;
import com.fline.form.vo.DictionaryVo;

public interface DictionaryAccessService extends AbstractNamespaceAccessService<Dictionary> {

	List<DictionaryVo> findAllVo();
}
