package com.fline.form.access.service;


import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.ErrorXml;

public interface ErrorXmlAccessService extends AbstractNamespaceAccessService<ErrorXml> {

	/**
	  * @Description : 增加重复绑定模板的数据
	  * @author : shaowei
	  * @return
	  */
	void createByRepeat();
}
