package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.atgbusmng.api.client.AtgBusClient;
import com.alipay.atgbusmng.api.domain.ApplicantVO;
import com.alipay.atgbusmng.api.domain.StuffInfoVO;
import com.alipay.atgbusmng.api.exception.AtgBusException;
import com.alipay.atgbusmng.api.request.*;
import com.alipay.atgbusmng.api.response.*;
import com.alipay.atgbusmng.callbackapi.request.*;
import com.alipay.atgbusmng.callbackapi.response.*;
import com.feixian.tp.common.util.Detect;
import com.fline.form.config.AtgBusProperties;
import com.fline.form.constant.Constant;
import com.fline.form.constant.ProjectNodeEnum;
import com.fline.form.exception.AtgSubmitException;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.AliProjectMgmtService;
import com.fline.form.mgmt.service.AtgBizMgmtService;
import com.fline.form.mgmt.service.CertificateFileMgmtService;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.util.FileUtil;
import com.fline.form.vo.*;
import com.fline.yztb.vo.ItemVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.*;

@Service
public class AliProjectMgmtServiceImpl implements AliProjectMgmtService {

	private Log logger = LogFactory.getLog(AliProjectMgmtServiceImpl.class);

	@Value("${file.serverUrl}")
    private String fileServerUrl;
	@Autowired
	private AtgBusClient atgBusClient;
	@Autowired
	private AtgBusProperties atgBusProperties;
	@Autowired
    private FdpClientMgmtService fdpClientMgmtService;
	@Autowired
	private AtgBizMgmtService atgBizMgmtService;
	@Autowired
	private CertificateFileMgmtService certificateFileMgmtService;

	@Override
	public String generateUnicode(String applyName, String applicantUid, String applyCardNumber, String applyCardType,
                                  String applyForm, String areaCode, String busType, String deptId, String relBusId,
                                  String serviceCodeId) {
		AtgBizAffairUnicodeGenerateRequest atgBizAffairUnicodeGenerateRequest = new AtgBizAffairUnicodeGenerateRequest();

		atgBizAffairUnicodeGenerateRequest.setApplyName(applyName);
		atgBizAffairUnicodeGenerateRequest.setApplicantUid(applicantUid);
		atgBizAffairUnicodeGenerateRequest.setApplyCardNumber(applyCardNumber);
		atgBizAffairUnicodeGenerateRequest.setApplyCardType(applyCardType);
		atgBizAffairUnicodeGenerateRequest.setApplyForm(applyForm);
		atgBizAffairUnicodeGenerateRequest.setAreaCode(areaCode);
		atgBizAffairUnicodeGenerateRequest.setBelongSystem(atgBusProperties.getAppId());
		atgBizAffairUnicodeGenerateRequest.setBusType(busType);
		atgBizAffairUnicodeGenerateRequest.setDeptId(deptId);
		atgBizAffairUnicodeGenerateRequest.setRelBusId(relBusId);
		atgBizAffairUnicodeGenerateRequest.setServiceCodeId(serviceCodeId);
		RequestLog requestLog = new RequestLog();
        requestLog.setCerName(applyName);
        requestLog.setCerNo(applyCardNumber);
        requestLog.setItemInnerCode(serviceCodeId);
        requestLog.setRequestName("统一赋码");
        requestLog.setRequestCode("UnicodeGenerate");
        requestLog.setDataSource("政务中台");
        requestLog.setRequestTime(new Date());
		// 4. 请求开放服务，获取response
		try {
			AtgBizAffairUnicodeGenerateResponse response = atgBusClient.execute(atgBizAffairUnicodeGenerateRequest);
			String resultStatus = response.getResultStatus();
			if ("F".equals(resultStatus)) {
				logger.error("获取统一赋码失败:" + response.getResultMsg());
				requestLog.setStatus(0);
				requestLog.setResponseData("获取统一赋码失败:"+response.getResultMsg());
				return null;
			}
			else if ("S".equals(resultStatus)){
				logger.info("获取统一赋码成功:" + response.getData());
				requestLog.setResponseData(response.getData());
				requestLog.setStatus(1);
				return response.getData();
			}
		} catch (Exception e) {
			logger.error("获取统一赋码异常" + e.getMessage(), e);
			requestLog.setStatus(-1);
			requestLog.setResponseData("获取统一赋码异常:"+e.getMessage());
		}finally {
			requestLog.setRequestData(JSON.toJSONString(atgBizAffairUnicodeGenerateRequest));
            fdpClientMgmtService.saveRequestLog(requestLog);
        }
		return null;
	}

	@Override
	public Map<String, Object> sendProject(String projectId, JSONObject formInfo, ItemVo item, DepartmentVo dept, String formInfoStr, String attrInfoStr, String postInfoStr) {
        String formBusiCode = formInfo.getString("FORM_BUSINESS_CODE");
        String applyUid = formInfo.getString("APPLICANT_UID");
        String applyName = formInfo.getString("APPLY_NAME");
        String applyCardType = formInfo.getString("APPLY_CARD_TYPE");
        String applyCardNumber = formInfo.getString("APPLY_CARD_NUMBER");
        String applyMobile = formInfo.getString("APPLY_MOBILE");

        AtgBizAffairReceiveRequest request = new AtgBizAffairReceiveRequest();
        ProjectLifecycle lifecycle = new ProjectLifecycle();
        try {
			//申请人信息
			ApplicantVO applicantVO = new ApplicantVO();
			applicantVO.setApplyUid(applyUid);
			applicantVO.setApplyName(applyName);
			applicantVO.setApplyCardNo(applyCardNumber);
			applicantVO.setApplyCardType(applyCardType);
			applicantVO.setIsAgent("0");//是否代理 （0否
			applicantVO.setContactUid(applyUid);
			applicantVO.setContactName(applyName);
			applicantVO.setContactCardNo(applyCardNumber);
			applicantVO.setContactCardType(applyCardType);
			//自主终端机手机号码为空特殊处理
            if(!Detect.notEmpty(applyMobile)) {
                JSONObject jsonObject = JSON.parseObject(formInfoStr);
                JSONArray form1 = jsonObject.getJSONArray("form1");
                for (int i = 0; i < form1.size(); i++) {
                    JSONObject fieldObj = form1.getJSONObject(i);
                    if("APPLY_MOBILE".equals(fieldObj.getString("name")) ) {
                        applyMobile = fieldObj.getString("value");
                    }
                }
            }
            applicantVO.setContactTelNo(applyMobile);//联系人手机
            if("51".equals(applyCardType)) {
                applicantVO.setApplyType("01");//申请人类型 （00个人,01法人）
                applicantVO.setLegalMan(formInfo.getString("LEGAL_MAN"));
            } else {
                applicantVO.setApplyType("00");//申请人类型 （00个人,01法人）
            }

			//办件材料信息
			List<StuffInfoVO> suffInfoList = new ArrayList<>();
			if (Detect.notEmpty(attrInfoStr)) {
				JSONArray attrArray = JSON.parseArray(attrInfoStr);
				for (int ii = 0; ii < attrArray.size(); ii++) {
					JSONObject attrObj = attrArray.getJSONObject(ii);
					if(attrObj.isEmpty()) {
					    continue;
                    }
					String materialName = attrObj.getString("materialName");
					String materialCode = attrObj.getString("materialCode");
                    String materialType = attrObj.getString("materialType");//材料类型：1为证明，3为申请表
					String fileName = attrObj.getString("certName");
                    String certUrl = attrObj.getString("certUrl");
                    String certFile = attrObj.getString("certFile");

                    StuffInfoVO stuffInfoVO = new StuffInfoVO();
                    stuffInfoVO.setStuffName(materialName);
                    stuffInfoVO.setStuffUniId(materialCode);
                    stuffInfoVO.setAttachName(fileName);
                    stuffInfoVO.setStuffNum(1L);//材料数量

                    String key = item.getInnerCode() + "/" + formBusiCode + "/" + fileName;
                    String fileKey = Constant.FDP_FILE_KEY + "/" + key;
                    String fileUrl = fileServerUrl + key;
                    byte[] bs = this.fdpClientMgmtService.readFile(fileKey);
                    if(Detect.notEmpty(bs)){
                        stuffInfoVO.setAttachPath(fileUrl);
                        stuffInfoVO.setStuffType("2");//材料类型 （2电子)
                        stuffInfoVO.setFetchMode("03");//收取类型 （附件上传02，电子证照引用03）
                        //申请表加上用户签名
                        if("3".equals(materialType)) {
                            JSONObject jsonObject = JSON.parseObject(formInfoStr);
                            String signature = jsonObject.getString("signature");//用户签名图片，base64
                            if(Detect.notEmpty(signature)) {
                                //FileUtil.decoderBase64File(signature, "F:\\123.jpg");
                                byte[] bytes = certificateFileMgmtService.addUserSignature(bs, signature);
                                fdpClientMgmtService.writeFile(fileKey, null, bytes);
                                //FileUtil.decoderBase64File(new BASE64Encoder().encode(bytes), "F:\\123.pdf");
                            }
                        }
                    } else if (Detect.notEmpty(certUrl)) {
                        stuffInfoVO.setAttachPath(certUrl);
                        stuffInfoVO.setStuffType("1");//材料类型 （1纸质)
                        stuffInfoVO.setFetchMode("02");//收取类型 （附件上传02，电子证照引用03）
                    } else if (Detect.notEmpty(certFile)) {
                        fdpClientMgmtService.writeFile(fileKey, null, new BASE64Decoder().decodeBuffer(certFile));
                        stuffInfoVO.setAttachPath(fileUrl);
                        stuffInfoVO.setStuffType("1");//材料类型 （1纸质)
                        stuffInfoVO.setFetchMode("02");//收取类型 （附件上传02，电子证照引用03）
                    } else {
                        continue;
                    }
					suffInfoList.add(stuffInfoVO);
				}
			}

			request.setProjId(projectId);
			request.setMatterCode(Detect.notEmpty(item.getParent()) ? item.getParent() : item.getInnerCode());
			request.setDeptCode(dept.getCode());
			request.setAreaCode(dept.getAreaCode());

//			request.setBizType(item.getBizType());
//			request.setAffairType(item.getAffaitType());
//          request.setProjectNature(item.getProjectNature());
//          request.setApproveType(item.getApproveType());
			//request.setRelBizId("");

            request.setBizType("0");
			request.setAffairType("01");
            request.setProjectNature("99");
            request.setApproveType("01");
			request.setApplicantVO(applicantVO);
			request.setProjectName(item.getName());
			request.setRecvDeptCode(dept.getCode());
			request.setRecvDeptName(dept.getName());
			request.setExecDeptOrgCode(dept.getCode());
			request.setApplyOrigin("2");//申报来源 (移动端网上申报2
			request.setRecvUserType("1");//创建用户对应类型：1/2/3/4 (1：浙江政务服务网单点登录进来的网上申报用户；
			request.setRecvUserId(applyUid);
			request.setRecvUserName(applyName);
			Map<String, String> affFormInfo = new HashMap<>();
            affFormInfo.put("formInfo", formInfoStr);
            affFormInfo.put("postInfo", postInfoStr);
			request.setAffFormInfo(JSON.toJSONString(affFormInfo, SerializerFeature.DisableCircularReferenceDetect));//表单信息
			request.setSuffInfoList(suffInfoList);//办件材料信息
			request.setGmtApply(new Date());
			request.setAppId(atgBusProperties.getAppId());

            lifecycle.setSendData(JSON.toJSONString(request, SerializerFeature.DisableCircularReferenceDetect));
			AtgBizAffairReceiveResponse response = atgBusClient.execute(request);
            lifecycle.setResponseData(response.getBody());
			String resultStatus = response.getResultStatus();

			if ("S".equals(resultStatus)){
                String data = response.getData();
                logger.info("提交中台办件收件成功:" + data);
                Map<String, Object> result = new HashMap<>();
                //获取好差评地址
                String url = atgBizMgmtService.evaluationUrl(projectId, item.getInnerCode(), applyCardType, applyCardNumber, applyName);
                result.put("projectId", projectId);
                result.put("sendResult", data);
                result.put("evaluationUrl", url);
                return result;
			} else {
                throw new AtgSubmitException("提交办件收件失败:" + response.getResultMsg());
            }
		} catch (Exception e) {
            lifecycle.setErrorMsg(e.getMessage());
            lifecycle.setStatus(ResultCode.FAIL.getCode());
            String errorMsg = e instanceof AtgSubmitException ? e.getMessage() : "提交办件收件失败";
			throw new BaseException(errorMsg, e);
		} finally {
            lifecycle.setApplyerCardNumber(applyCardNumber);
            lifecycle.setApplyerName(applyName);
            lifecycle.setFormBusiCode(formBusiCode);
            lifecycle.setItemCode(item.getInnerCode());
            lifecycle.setItemName(item.getName());
            lifecycle.setCreateTime(new Date());
            lifecycle.setProjectId(projectId);
            lifecycle.setNode(ProjectNodeEnum.RECEIVE.getValue());
            lifecycle.setNodeName(ProjectNodeEnum.RECEIVE.getName());
            fdpClientMgmtService.saveProjectLifecycle(lifecycle);
        }
	}

	private void discardUnicode(String appId, String projectId) {
		try {
			AtgBizAffairUnicodeDiscardRequest request = new AtgBizAffairUnicodeDiscardRequest();
			request.setBelongSystem(appId);
			request.setProjId(projectId);
			AtgBizAffairUnicodeDiscardResponse response = atgBusClient.execute(request);
			String resultStatus = response.getResultStatus();
			if ("F".equals(resultStatus)) {
				logger.error("赋码废弃失败:" + response.getResultMsg());
			} else if ("S".equals(resultStatus)){
				logger.info("赋码废弃成功:" + response.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("赋码废弃异常:" + e.getMessage());
		}
	}

	@Override
	public CallbackAtgBizAffairReceiveResponse receiveCallback(CallbackAtgBizAffairReceiveRequest req) {
		CallbackAtgBizAffairReceiveResponse response = new CallbackAtgBizAffairReceiveResponse();
		response.setResultStatus("S");
		response.setResultMsg("成功");
		response.setResultCode("success");
		saveCallbackInfo(req.getProjId(), "receiveCallback", JSON.toJSONString(req));
		return response;
	}

	@Override
	public CallbackAtgBizAffairAcceptResponse acceptCallback(CallbackAtgBizAffairAcceptRequest req) {
        CallbackAtgBizAffairAcceptResponse response = new CallbackAtgBizAffairAcceptResponse();
		response.setResultStatus("S");
		response.setResultMsg("成功");
		response.setResultCode("success");
		saveCallbackInfo(req.getProjId(), "acceptCallback", JSON.toJSONString(req));
		return response;
	}

	@Override
	public CallbackAtgBizAffairFinishResponse finishCallback(CallbackAtgBizAffairFinishRequest req) {
		CallbackAtgBizAffairFinishResponse response = new CallbackAtgBizAffairFinishResponse();
		response.setResultStatus("S");
		response.setResultMsg("成功");
		response.setResultCode("success");
		saveCallbackInfo(req.getProjId(), "finishCallback", JSON.toJSONString(req));
		return response;
	}

	@Override
	public CallbackAtgBizAffairSpecProcFinishResponse specProcCallback(CallbackAtgBizAffairSpecProcFinishRequest req) {
        CallbackAtgBizAffairSpecProcFinishResponse response = new CallbackAtgBizAffairSpecProcFinishResponse();
		response.setResultStatus("S");
		response.setResultMsg("成功");
		response.setResultCode("success");
        saveCallbackInfo(req.getProjId(), "specProcCallback", JSON.toJSONString(req));
		return response;
	}

	@Override
	public CallbackAtgBizAffairFlowSyncResponse flowSyncCallback(CallbackAtgBizAffairFlowSyncRequest req) {
		CallbackAtgBizAffairFlowSyncResponse response = new CallbackAtgBizAffairFlowSyncResponse();
		response.setResultStatus("S");
		response.setResultMsg("成功");
		response.setResultCode("success");
		saveCallbackInfo(req.getProjId(), "flowSyncCallback", JSON.toJSONString(req));
		return response;
	}

	@Override
	public CallbackAtgBizAffairSupplementAcceptResponse supplementCallback(CallbackAtgBizAffairSupplementAcceptRequest req) {
		CallbackAtgBizAffairSupplementAcceptResponse response = new CallbackAtgBizAffairSupplementAcceptResponse();
		response.setResultStatus("S");
		response.setResultMsg("成功");
		response.setResultCode("success");
		saveCallbackInfo(req.getProjId(), "supplementCallback", JSON.toJSONString(req));
		return response;
	}

	private void saveCallbackInfo(String projectId, String methodName, String content) {
		AliCallBackInfoVo callBackInfoVo = new AliCallBackInfoVo();
		callBackInfoVo.setProjectId(projectId);
		callBackInfoVo.setCreateTime(new Date());
		callBackInfoVo.setMethodName(methodName);
		callBackInfoVo.setContent(content);
		fdpClientMgmtService.saveCallbackInfo(callBackInfoVo);
	}

    @Override
    public void sendMsg(String cerNo, String serviceCode, Map<String, String> templateArgs, String projectId, int msgType) {
    	String ztMsg = "";
    	try {
			AtgBizMessageSendMessageRequest request = new AtgBizMessageSendMessageRequest();
			//总线注册的应用id
			request.setAppId(atgBusProperties.getAppId());
			//消息的唯一id，用于幂等
			request.setRequestId(UUID.randomUUID().toString());
			//具体通知业务编码，获取可联系开发人员
			request.setServiceCode(serviceCode);
			//如果可以获取易和id，则使用易和id，否则按如下规则业务编码+身份证号
			request.setToUserId(serviceCode + cerNo);
			//渠道配置取值
			//如通知渠道为浙里办，当前发送接口支持对易和id和身份证的参数，若为其他渠道，需与开发对接
			Map<String, String> receiver = new HashMap<String, String>();
			//易和id
			//receiver.put("commnet_id", userId);
			//身份证
			receiver.put("id_card", cerNo);

			request.setReceiver(receiver);
			request.setTemplateArgs(templateArgs);
			AtgBizMessageSendMessageResponse response = atgBusClient.execute(request);
			String resultStatus = response.getResultStatus();
			if ("F".equals(resultStatus)) {
			    logger.error("发送浙里办消息失败:" + response.getResultMsg());
			    ztMsg = response.getResultMsg();
			    throw new RuntimeException("发送浙里办消息失败:" + response.getResultMsg());
			} else if ("S".equals(resultStatus)){
			    logger.info("发送浙里办消息成功:" + response.getData());
			    ztMsg = response.getData();
			}
		} catch (AtgBusException e) {
			ztMsg = e.getMessage();
			throw new RuntimeException(e.getMessage());
		}finally {
	        SendMsgInfo sendMsgInfo = new SendMsgInfo();
	        sendMsgInfo.setCerNo(cerNo);
	        sendMsgInfo.setCreateTime(new Date());
	        sendMsgInfo.setMsgType(""+msgType);
	        sendMsgInfo.setProjectId(projectId);
	        sendMsgInfo.setRequestData(Detect.notEmpty(templateArgs)?templateArgs.toString():"");
	        sendMsgInfo.setServiceCode(serviceCode);
	        sendMsgInfo.setZtMsg(ztMsg);
	        //fdpClientMgmtService.saveSendMsgInfo(sendMsgInfo);
		}
    }

    @Override
    public void test() {
        AtgBizMatterqueryCategoryQuerylistRequest atgBizMatterqueryCategoryQuerylistRequest = new AtgBizMatterqueryCategoryQuerylistRequest();
        /**
         * 业务条线代码
         */
        atgBizMatterqueryCategoryQuerylistRequest.setBizLineCode(null);

        /**
         * 第几页
         */
        atgBizMatterqueryCategoryQuerylistRequest.setCurrent(null);

        /**
         * 主项代码
         */
        atgBizMatterqueryCategoryQuerylistRequest.setMainItemCode(null);

        /**
         * 行使层级
         */
        atgBizMatterqueryCategoryQuerylistRequest.setMatLevel(null);

        /**
         * 事项名称
         */
        atgBizMatterqueryCategoryQuerylistRequest.setMatName(null);

        /**
         * 事项类型
         */
        atgBizMatterqueryCategoryQuerylistRequest.setMatType(null);

        /**
         * 每页数量
         */
        atgBizMatterqueryCategoryQuerylistRequest.setSize(null);

        /**
         * 子项代码
         */
        atgBizMatterqueryCategoryQuerylistRequest.setSubItemCode(null);

        //4. 请求开放服务，获取response
        AtgBizMatterqueryCategoryQuerylistResponse response = atgBusClient.execute(atgBizMatterqueryCategoryQuerylistRequest);
        System.out.println(response.getBody());
    }

    @Override
    public void test2() {
        AtgBizMatterqueryMatterQuerysimplelistRequest atgBizMatterqueryMatterQuerysimplelistRequest = new AtgBizMatterqueryMatterQuerysimplelistRequest();
        /**
         * 易和区域编码
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setAdCode(null);

        /**
         * 行政相对人性质
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setAdminCounterPart(null);

        /**
         * 业务条线代码
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setBizLineCode(null);

        /**
         * 第几页
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setCurrent(null);

        /**
         * 组织机构代码
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setEntityOid(null);

        /**
         * 行使层级
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setImpleLevel(null);

        /**
         * 实施类型
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setImpleType(null);

        /**
         * 辖区代码，6位行政区划
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setJurisCode("331000");

        /**
         * 实施或牵头处室
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setLeadDept(null);

        /**
         * 基本编码
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setMatCode(null);

        /**
         * 部门编码，权力事项库部门编码
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setOuGuid("001008010010024");

        /**
         * 事项所属业务类型
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setOuTypeCode(null);

        /**
         * 权力属性
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setRightAttribute(null);

        /**
         * 每页数量
         */
        atgBizMatterqueryMatterQuerysimplelistRequest.setSize(null);

        //4. 请求开放服务，获取response
        AtgBizMatterqueryMatterQuerysimplelistResponse response = atgBusClient.execute(atgBizMatterqueryMatterQuerysimplelistRequest);
        System.out.println(response.getBody());
    }
}
