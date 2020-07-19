package com.fline.form.mgmt.service;

import java.util.Map;

public interface RequestInfoMgmtService {
	
	Map<String, Object> getInfos(Map<String, Object> param, Integer pageNum, Integer pageSize);


	Map<String, Object> getCounts(Integer type);

}
