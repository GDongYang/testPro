package com.fline.form.mgmt.service;

import com.alibaba.fastjson.JSONObject;
import com.fline.form.vo.DepartmentVo;
import com.fline.yztb.vo.ItemVo;

public interface YcslMgmtService {
    String sendProject(JSONObject formInfo, ItemVo item, DepartmentVo dept, String formInfoStr, String attrInfoStr, String postInfoStr);
}
