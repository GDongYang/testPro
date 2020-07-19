package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.fline.form.mgmt.service.SecurityCodeMgmtService;
import com.fline.form.vo.ResponseResult;

import java.util.Map;

public class SecurityCodeAction extends AbstractAction {

	private static final long serialVersionUID = -1766083959991364652L;

	private ResponseResult<?> responseResult;

    public ResponseResult<?> getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(ResponseResult<?> responseResult) {
        this.responseResult = responseResult;
    }

    private SecurityCodeMgmtService securityCodeMgmtService;

    public void setSecurityCodeMgmtService(SecurityCodeMgmtService securityCodeMgmtService) {
        this.securityCodeMgmtService = securityCodeMgmtService;
    }

    public String getCodeGenerate() {
        Map<String, Object> data = securityCodeMgmtService.generateCode();
        responseResult = ResponseResult.success(data);
        return SUCCESS;
	}
}
