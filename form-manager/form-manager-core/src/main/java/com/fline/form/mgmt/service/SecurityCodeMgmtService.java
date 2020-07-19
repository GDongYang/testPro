package com.fline.form.mgmt.service;

import java.io.IOException;
import java.util.Map;

public interface SecurityCodeMgmtService {
	
	String getFromCache(String uuid);

    Map<String, Object> generateCode();
}
