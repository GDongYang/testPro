package com.fline.form.controller;

import com.alipay.atgbusmng.callbackapi.request.*;
import com.alipay.atgbusmng.callbackapi.response.*;
import com.fline.form.mgmt.service.AliProjectMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ali/callback")
public class AliCallbackController {

	@Autowired
	private AliProjectMgmtService aliProjectMgmtService;

	/**
	 * 办件接收通知回调服务
	 * @return 
	 */
	@PostMapping(value = "/receive")
	public CallbackAtgBizAffairReceiveResponse receiveCallback(
			@RequestBody CallbackAtgBizAffairReceiveRequest req) {
		return aliProjectMgmtService.receiveCallback(req);
	}

	/**
	 * 办件受理通知回调服务
	 * @return 
	 */
	@PostMapping(value = "/accept")
	public CallbackAtgBizAffairAcceptResponse acceptCallback(
			@RequestBody CallbackAtgBizAffairAcceptRequest req) {
		return aliProjectMgmtService.acceptCallback(req);
	}

	/**
	 * 办件办结通知回调服务
	 * @return 
	 */
	@PostMapping(value = "/finish")
	public CallbackAtgBizAffairFinishResponse finishCallback(
			@RequestBody CallbackAtgBizAffairFinishRequest req) {
		return aliProjectMgmtService.finishCallback(req);
	}

	/**
	 * 办件特别程序通知回调服务
	 * @return 
	 */
	@PostMapping(value = "/specproc")
	public CallbackAtgBizAffairSpecProcFinishResponse specprocCallback(
			@RequestBody CallbackAtgBizAffairSpecProcFinishRequest req) {
		return aliProjectMgmtService.specProcCallback(req);
	}

	/**
	 * 补齐补正回调
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/supplement")
	public CallbackAtgBizAffairSupplementAcceptResponse supplementCallback(
			@RequestBody CallbackAtgBizAffairSupplementAcceptRequest req) {
		return aliProjectMgmtService.supplementCallback(req);
	}

	/**
	 * 办件特别程序通知回调服务
	 * @return 
	 */
	@PostMapping(value = "/flowsync")
	public CallbackAtgBizAffairFlowSyncResponse acceptCallback(
			@RequestBody CallbackAtgBizAffairFlowSyncRequest req) {
		return aliProjectMgmtService.flowSyncCallback(req);
	}

}
