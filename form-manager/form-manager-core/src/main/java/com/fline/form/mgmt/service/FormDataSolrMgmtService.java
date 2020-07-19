package com.fline.form.mgmt.service;

import java.util.Map;

public interface FormDataSolrMgmtService {

	Map<String, Object> findPagination(Map<String, Object> map);

	Map<String, Object> findSms(Map<String, Object> map);

}
