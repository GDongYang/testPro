package com.fline.form.mgmt.service;

import com.fline.form.vo.PersonInfo;

import java.util.Map;

public interface TempInfoService {

    PersonInfo getPersonInfo(String formBusiCode);

    Map<String, Object> loadTempFrom(String projId, String formBusiCode);

    String tempSaveFromData(String formBusiCode, String projId, String formInfo, String attrInfo);
}
