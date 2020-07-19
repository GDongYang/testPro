package com.fline.form.mgmt.service;

public interface TokenMgmtService {

    String getToken();

    void decrReadCount();
}
