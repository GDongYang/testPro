package com.fline.form.mgmt.service;

import java.util.Map;

public interface DataPlatformMgmtService {
    String sendRequest(String formCode, String interfaceType, Map<String, Object> requestParams);

    String sendRequest(String interfaceId, Map<String, Object> requestParams);
}
