package com.fline.form.mgmt.service;

import java.util.Map;

public interface ProjectRepeatMgmtService {


    String pushByProjectId(String projectId);
    
    Map<String, Object> findPage(Map<String, Object> params);
    
    Map<String, Object> findProjectReateInfo(Map<String, Object> params);
    
}
