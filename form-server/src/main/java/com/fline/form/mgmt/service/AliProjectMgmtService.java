package com.fline.form.mgmt.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.atgbusmng.callbackapi.request.*;
import com.alipay.atgbusmng.callbackapi.response.*;
import com.fline.form.vo.DepartmentVo;
import com.fline.yztb.vo.ItemVo;

import java.util.Map;

public interface AliProjectMgmtService {
    String generateUnicode(String applyName, String applicantUid, String applyCardNumber, String applyCardType,
                           String applyForm, String areaCode, String busType, String deptId, String relBusId,
                           String serviceCodeId);

    Map<String, Object> sendProject(String projectId, JSONObject formInfo, ItemVo item, DepartmentVo dept, String formInfoStr, String attrInfoStr, String postInfoStr);

    CallbackAtgBizAffairReceiveResponse receiveCallback(CallbackAtgBizAffairReceiveRequest req);

    CallbackAtgBizAffairAcceptResponse acceptCallback(CallbackAtgBizAffairAcceptRequest req);

    CallbackAtgBizAffairFinishResponse finishCallback(CallbackAtgBizAffairFinishRequest req);

    CallbackAtgBizAffairSpecProcFinishResponse specProcCallback(CallbackAtgBizAffairSpecProcFinishRequest req);

    CallbackAtgBizAffairFlowSyncResponse flowSyncCallback(CallbackAtgBizAffairFlowSyncRequest req);

    CallbackAtgBizAffairSupplementAcceptResponse supplementCallback(CallbackAtgBizAffairSupplementAcceptRequest req);

    void sendMsg(String cerNo, String serviceCode, Map<String, String> templateArgs, String projectId, int msgType);

    void test();

    void test2();
}