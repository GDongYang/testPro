//-----------------------------------------------证书代码-----------start------
var _authCode = "";
//获得挑战码
function getAuthCode(){
	 $.ajax({
		   	url:"userSessionAction!authCode.action",
		   	type:"POST",
		   	async: false,
		   	error:function(request,textStatus, errorThrown){
			},
		   	success:function(data){
		   		_authCode = data.authCode;
		   	}
	  });
}
/* 使用哪种JS接口(1:COM接口(仅用于IE)；2:Websocket接口) */
var _usingInterfaceType = 2;
/* ZJCA接口对象 */
var _zjcaCMT = null;
/* 当前要显示的证书用途类型(1:签名;2:加密) */
var _certUsage = 1;
/* 证书信息列表 */
var _certInfoArray = null;
/* 当前选择的证书 */
var _selCertInfo = null;
var _certInfo = "";
/* 当前选择的签名结果格式(默认P1) */
var _signFormat = 1;
// 版本
var g_ZJCAClientVer = "1.0.0.0";
//游览器类型判断是否是IE
function isIE(){
	if (!!window.ActiveXObject || "ActiveXObject" in window){
        return true;
	}
    else{
        return false;
    }
}
/*
 *	页面初始化
 */
window.onload = function() {	
	//客户端检测
	if (isIE()) {
		_usingInterfaceType = 1;
		try{
			var versionObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCAVersion.1");
			g_ZJCAClientVer = versionObj.GetVersion();
		}catch(e){
			g_ZJCAClientVer = "1.0.0.0";
		}				
	}else{
		_usingInterfaceType = 2;
	}

	if (1 == _usingInterfaceType) {
		_zjcaCMT = new zjca_COM(onErrorCallbackFunc, onKeyEventCallbackFunc);	
	}
	else {
		_zjcaCMT = new zjca_Websocket(onErrorCallbackFunc, onKeyEventCallbackFunc, onResultCallbackFunc);
	}
	try {
		//初始化接口			
		_zjcaCMT.init();
		if (1 == _usingInterfaceType) 
		{
			//枚举所有证书
			var certArrary = _zjcaCMT.getCertList(-1, 0, _certUsage);
			FillCertInfoList(certArrary);
		}
	}
	catch (e) {
		onErrorCallbackFunc(e.number, e.description);			
	}
}
/*
 *	页面关闭
 */
window.onunload = function () {
	if (_zjcaCMT) {
		_zjcaCMT.finaled();
	}
}
/*
 *	错误事件回调函数
 */
function onErrorCallbackFunc(name, code, message) {
	var errCode = 0;
	if (code < 0) {
		var errNumber = 0x100000000;
		errNumber += parseInt(code);
		errCode = errNumber.toString(16);
	}
	else {
		var errNumber = parseInt(code);
		errCode = errNumber.toString(16);
	}
	var errMsg = "操作失败！\n错误代码：0x";
	errMsg += errCode;
	errMsg += "\n";
	errMsg += "错误信息：";
	errMsg += message;
}
/*
 *	设备事件处理函数
 */
function onKeyEventCallbackFunc(type, index, name) {
	if (_zjcaCMT) {
		//枚举所有签名证书
		var certArrary = _zjcaCMT.getCertList(-1, 0, _certUsage);	
		// 如果结果同步返回，则显示结果		
		if (typeof(certArrary) != "undefined") {
			FillCertInfoList(certArrary);
		}else {
			loading();	
		}
	}
}		
/*
 *	WebSocket接口返回结果的回调函数
 */
function onResultCallbackFunc(respType, param1) {
	stop_loading();
	// 初始化成功
	if (RESP_INIT == respType) {
		_zjcaCMT.getCertList(-1, 0, _certUsage);
	}
	// 返回证书信息列表
	else if (RESP_GET_CERTIFICATE_LIST == respType) {
		FillCertInfoList(param1);				
	}
}
/*
 *  选择证书
 */
function OnSelCertChanged(selectvalue) {
	if(handlerCount > 1){
		return ;
	}
    var certIndex = selectvalue;
    if (isNaN(certIndex) || certIndex == -1) {
    	// alert("请插入证书");
    	return;
    }
	if (certIndex >= 0 && certIndex < _certInfoArray.length) {	
		_selCertInfo = _certInfoArray[certIndex];				
	}else {
		_selCertInfo = null;
	}
	// 显示证书主要信息
	if (_selCertInfo) {
		var content  = _zjcaCMT.getCertContent(_selCertInfo);
		if (typeof(content) != "undefined") {
			_certInfo = content;
			loginByCert();
		}else {
			loading();	
		}
	}
}

function loginByCert(){
	//获取挑战码
    getAuthCode();
	
	var signature = "";
	try {
		signature = _zjcaCMT.signMessage(_selCertInfo, 	// 签名证书
										 _signFormat,	// 签名格式，1:P1;2:P7
										 7,				// 签名附加信息(可组合)，1:带证书;2:带原文;4:带时间戳
										 _authCode			// 消息
										 );
	}
	catch (e) {
		//onErrorCallbackFunc("", e.number, e.description);			
	}	
    
	 var info = userTable.bootstrapTable("getSelections");
	 if(signature == ""){
		$("#sign_up").removeAttr('disabled');
		return;
	 }
	 $.ajax({
		   	url:"userAction!bindUser.action",
		   	data:{id:info[0].id,authCode:_authCode,certInfo:_certInfo,signature:signature},
		   	type:"POST",
		   	error:function(request,textStatus, errorThrown){
		   		//alert("请求出错..");
		   		//console.log(textStatus);
			},
		   	success:function(data){
		   		//_authCode = data.authCode;
		   		if(data.result == "0"){
		   			Modal.alert({ msg:'绑定失败!', title:'提示', btnok:'确定' });
		   		}else if(data.result == "1"){
		   			Modal.alert({ msg:'绑定成功!', title:'提示', btnok:'确定' });
		   			userTable.bootstrapTable(('refresh'));
		   		}else {
		   			Modal.alert({ msg:'该证书已被绑定其他用户!', title:'提示', btnok:'确定' });
		   			$("#certNo").val(data.result);
		   		}
		   	    $("#sign_up").removeAttr('disabled');
		   	}
	});
}

/*
 *	更新证书信息列表
 */

function FillCertInfoList(certInfos) {
	// console.log(certInfos + '1');
	// 删除老的选项
	var obs = $(".obs");
	$(".obs > div").remove();
	
	// 保存新的证书信息列表
	_certInfoArray = new Array;
	_certInfoArray = certInfos;
	
	// 显示证书信息
	funAppend(_certInfoArray, OnSelCertChanged);
}
