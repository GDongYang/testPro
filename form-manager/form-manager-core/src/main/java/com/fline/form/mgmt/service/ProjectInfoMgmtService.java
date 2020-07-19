package com.fline.form.mgmt.service;

import java.util.Map;

public interface ProjectInfoMgmtService {

	Map<String, Object> findPagination(Map<String, Object> param);

	Map<String, Object> findByProjectId(Map<String, Object> param);

}
