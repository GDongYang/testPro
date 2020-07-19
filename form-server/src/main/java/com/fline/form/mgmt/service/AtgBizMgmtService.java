package com.fline.form.mgmt.service;

import org.python.antlr.ast.Str;

public interface AtgBizMgmtService {
    String evaluationUrl(String projectId, String itemCode, String certType, String cerNo, String cerName);
}
