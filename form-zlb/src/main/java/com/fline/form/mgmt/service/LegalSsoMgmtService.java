package com.fline.form.mgmt.service;

import com.alibaba.fastjson.JSONObject;

public interface LegalSsoMgmtService {
    JSONObject doQuery(String ssotoken);
}
