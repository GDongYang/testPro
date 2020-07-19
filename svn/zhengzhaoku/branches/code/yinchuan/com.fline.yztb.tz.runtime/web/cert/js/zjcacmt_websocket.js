/* 
 * ZJCA Websocket object Script Interface for HTML
 * Version: v1.0
 * Date: 2016-12-03
 * 2015 - 2018 ZJCA. All rights reserved.
 * 
 *
*/
/* Websocket操作消息定义 */
var FUNC_INIT						= "cmt_init"			//初始化客户端
var FUNC_FINALED					= "cmt_finaled"			//释放客户端
var	FUNC_GET_DEVICE_LIST			= "enum_dev"			//获取所有设备信息列表
var FUNC_GET_CERTIFICATE_LIST		= "enum_cert"			//获取所有证书信息列表,参数:(设备序号)(证书类型)(证书用途)
var	FUNC_GET_CERTIFICATE_DATA		= "get_cert"			//获取制定序列号的证书内容,参数:(设备序号)(证书SN)
//
var	FUNC_SIGN_MESSAGE				= "sign_message"		//签名消息,参数:(设备序号)(算法类型)(签名类型)(附带标识)(消息原文)(描述信息)(编码类型)
var	FUNC_VERIFY_MESSAGE				= "verify_message"		//验证消息签名,参数:(设备序号)(消息原文)(描述信息)(编码类型)(Base64格式的签名)(Base64格式的证书)
//
/* Websocket响应消息定义 */
var RESP_INIT						= "init_resp"
var	RESP_GET_DEVICE_LIST			= "enum_dev_resp"
var RESP_GET_CERTIFICATE_LIST		= "enum_cert_resp"
var	RESP_GET_CERTIFICATE_DATA		= "get_cert_resp"
//
var	RESP_SIGN_MESSAGE				= "sign_message_resp"		//参数:(Base64格式的签名结果)
var	RESP_VERIFY_MESSAGE				= "verify_message_resp"		//参数:(是否通过验证)
//
/* 设备事件消息定义 */
var RESP_DEVICE_EVENT				= "device_event_resp"		//参数:(事件类型)(设备序号)(DevName)

/* 签名操作时的附加信息定义 */
var RSA_SIGATURE_DESC = "";
var SM2_SIGATURE_DESC = "1234567812345678";

/**
 * 原型：字符串格式化
 * @param args 格式化参数值
 */
String.prototype.format = function(args) {
    var result = this;
    if (arguments.length < 1) {
        return result;
    }

    var data = arguments; // 如果模板参数是数组
    if (arguments.length == 1 && typeof (args) == "object") {
        // 如果模板参数是对象
        data = args;
    }
    for ( var key in data) {
        var value = data[key];
        if (undefined != value) {
            result = result.replace("{" + key + "}", value);
        }
    }
    return result;
}
	   

/*
 *	ZJCA客户端Websocket接口封装，用来支持非IE浏览器
 */
function zjca_Websocket(errCallback, eventCallback, resultCallback) {
	var _serverPort = 5150;
	var _charEncode = 3;	//UTF8
	var _websocket = null;
	var _isConnected = false;
	var _isResponded = false;
	var _onError = errCallback
	var _onKeyEvent = eventCallback;
	var _onResult = resultCallback;
	
	/*
	 *	连接成功事件响应函数
	 */
	function onConnected(e) {
		_isConnected = true;
		if (_onResult){
			_onResult(RESP_INIT, 0);			
		}
	}	
	
	/*
	 *	服务器断开事件响应函数
	 */
	function onDisconnected(event) {
		_onError(FUNC_INIT, -1, "ZJCA CMT客户端未启动、或者已退出！")
	}
	
	/*
	 *	客户端处理结果接受函数
	 */
	function onReceived(event) {
		var resp = event.data;
		var regCmd = new RegExp('\\{([^\\}]*)\\}');
		var regParam = new RegExp('\\(([^\\)]*)\\)', 'g');
					
		if (resp) {
			var param;
			var paramList = new Array;
			
			// 获取返回消息类型
			var cmd = resp.match(regCmd);
			
			// 获取返回参数
			while ((param = regParam.exec(resp)) != null) {
				paramList.push(param[1]);
			}
			
			// “设备事件”消息
			if (cmd[1] == RESP_DEVICE_EVENT) {
				onKeyEvent(paramList);
			}
			// “获取设备列表”消息
			else if (cmd[1] == RESP_GET_DEVICE_LIST) {
				onGetKeyListResp(paramList);
			}
			// “获取证书列表”消息
			else if (cmd[1] == RESP_GET_CERTIFICATE_LIST) {
				onGetCertListResp(paramList);				
			}
			// “获取证书内容”消息
			else if (cmd[1] == RESP_GET_CERTIFICATE_DATA) {
				onGetCertContentResp(paramList);				
			}
			// “签名消息”消息
			else if (cmd[1] == RESP_SIGN_MESSAGE) {
				onSignResp(paramList);				
			}
			// “验签消息”消息
			else if (cmd[1] == RESP_VERIFY_MESSAGE) {
				onVerifyResp(paramList);				
			}
		}
	}
	
	/*
	 *	初始化函数
	 */
	this.init = function () {	
		
		// 先释放老的
		this.finaled();				
		
		// 创建一个Socket实例，尝试连接服务器
		var ishttps = 'https:' == document.location.protocol ? true: false;
		if (ishttps) {
			_websocket = new WebSocket('wss://127.0.0.1:' + _serverPort);			
		}
		else {
			try {
				//_websocket = new WebSocket('ws://127.0.0.1:' + _serverPort);		
			} catch (e) {
				// TODO: handle exception
			}
					
		}
		
		// 握手成功
		_websocket.onopen = onConnected;
		
		// 监听消息
		_websocket.onmessage = onReceived; 

		// 监听Socket的关闭
		_websocket.onclose = onDisconnected;		
	}
	
	/*
	 *	结束函数
	 */
	this.finaled = function () {
		if (_websocket) {
			_websocket.close();
			_websocket = null;
			_isResponded = false;
			_isConnected = false;
		}		
	}		
	
	/*
	 *	获取所有设备信息列表(不强制刷新)
	 */
	this.getKeyList = function () {
		
		// 获取所有设备
		var msg = "{{0}}({1})";
		msg = msg.format(FUNC_GET_DEVICE_LIST, "0");		
		_websocket.send(msg);		
	}	
	/*
	 *	"获取所有设备信息列表"响应函数
	 */
	function onGetKeyListResp(paramList) {
		
		/* 检查执行结果 */
		var res = paramList.shift();
		if (0 != res) {
			_onError(FUNC_GET_DEVICE_LIST, res, "操作失败！");
			return;			
		}		
		
		/* 检查参数是否正确 */
		if ((paramList.length - 1) % 5 != 0) {
			_onError(FUNC_GET_DEVICE_LIST, -1, "返回结果错误！");
			return;
		}		
		var count = paramList[0];
		if ((paramList.length - 1) / 5 != count) {
			_onError(FUNC_GET_DEVICE_LIST, -1, "返回结果错误！");
			return;
		}		
		
		/* 分解每个Key的SN,Label和Manufacturer */
		var keyArray = new Array;
		for (i = 0; i < count; i++) {
			var key = new zjca_Key(	i,					//总序号 
									paramList[5*i + 1], //SN
									paramList[5*i + 2], //Label
									paramList[5*i + 3]);//厂商名
			keyArray.push(key);
		}
		
		/* 通过回调函数传回结果 */
		if (_onResult) {
			_onResult(RESP_GET_DEVICE_LIST, keyArray);						
		}			
	}			
	
	/*
	 *	获取所有证书信息列表
	 */
	this.getCertList = function (keyIndex, type, usage) {
		
		// 获取所有证书信息列表
		var msg = "{{0}}({1})({2})({3})";
		msg = msg.format(FUNC_GET_CERTIFICATE_LIST, keyIndex, type, usage);		
		_websocket.send(msg);		
	}	
	/*
	 *	"获取所有证书信息列表"响应函数
	 */
	function onGetCertListResp(paramList) {
		
		/* 检查执行结果 */
		var res = paramList.shift();
		if (0 != res) {
			_onError(FUNC_GET_CERTIFICATE_LIST, res, "操作失败！");
			return;			
		}		
		
		/* 检查参数是否正确 */
		if ((paramList.length - 1) % 8 != 0) {
			_onError(FUNC_GET_CERTIFICATE_LIST, -1, "返回结果错误！");
			return;
		}		
		var count = paramList[0];
		if ((paramList.length - 1) / 8 != count) {
			_onError(FUNC_GET_CERTIFICATE_LIST, -1, "返回结果错误！");
			return;
		}		
		
		/* 分解每个证书的SN,Alg,Usage,CN等 */
		var certArray = new Array;
		for (i = 0; i < count; i++) {
			var cert = new zjca_Cert(i, 				 //总序号
									 paramList[8*i + 1], //SN
									 paramList[8*i + 2], //Alg
									 paramList[8*i + 3], //Usage
									 paramList[8*i + 4], //DN
									 paramList[8*i + 5], //Issuer
									 paramList[8*i + 6], //起始时间
									 paramList[8*i + 7], //失效时间
									 paramList[8*i + 8]	 //所在KEY的序号									
									 );
			certArray.push(cert);
		}
		
		/* 通过回调函数传回结果 */
		if (_onResult) {
			_onResult(RESP_GET_CERTIFICATE_LIST, certArray);						
		}			
	}	
	
	/*
	 *	获取证书内容
	 */
	this.getCertContent = function (cert) {	
		//(设备SN)(证书SN)
		var msg = "{{0}}({1})({2})";
		msg = msg.format(FUNC_GET_CERTIFICATE_DATA, cert.getKeyIndex(), cert.getSN());		
		_websocket.send(msg);		
	}	
	/*
	 *	"获取证书内容"响应函数
	 */
	function onGetCertContentResp(paramList) {		
		/* 检查执行结果 */
		var res = paramList.shift();
		if (0 != res) {
			_onError(FUNC_GET_CERTIFICATE_DATA, res, "操作失败！");
			return;			
		}
		
		/* 返回的数据错误 */
		if (1 != paramList.length) {
			_onError(FUNC_GET_CERTIFICATE_DATA, -1, "返回的数据个数！");
			return;			
		}
		
		/* 证书内容(Base64格式) */
		var certData = paramList[0];
		
		/* 通过回调函数传回结果 */
		if (_onResult) {
			_onResult(RESP_GET_CERTIFICATE_DATA, certData);						
		}			
	}
	
	/*
	 *	使用证书签名消息
	 */
	this.signMessage = function (cert, type, flags, message) {		
		var desc = (1 == cert.getAlg()) ? RSA_SIGATURE_DESC : SM2_SIGATURE_DESC;
		
		//(设备序号)(算法类型)(签名类型)(附带标识)(消息原文)(描述信息)(原文字符集)
		var msg = "{{0}}({1})({2})({3})({4})({5})({6})({7})";
		msg = msg.format(FUNC_SIGN_MESSAGE, cert.getKeyIndex(), cert.getAlg(), type, flags, message, desc, _charEncode);		
		_websocket.send(msg);		
	}	
	/*
	 *	"签名消息"响应函数
	 */
	function onSignResp(paramList) {		
		/* 检查执行结果 */
		var res = paramList.shift();
		if (0 != res) {
			_onError(FUNC_SIGN_MESSAGE, res, "操作失败！");
			return;			
		}
		
		/* 签名值 */
		var signature = paramList[0];		
		
		/* 通过回调函数传回结果 */
		if (_onResult) {
			_onResult(RESP_SIGN_MESSAGE, signature);						
		}			
	}	
	
	/*
	 *	使用证书验证消息签名
	 */
	this.verifyMessage = function (keyIndex, message, base64Sign, base64Cert) {
		//(设备序号)(消息原文)(描述信息)(原文字符集)(Base64格式的签名)(Base64格式的证书)
		var msg = "{{0}}({1})({2})({3})({4})({5})({6})";
		msg = msg.format(FUNC_VERIFY_MESSAGE, keyIndex, message, "", _charEncode, base64Sign, base64Cert);		
		_websocket.send(msg);		
	}		
	/*
	 *	"验证消息签名"响应函数
	 */
	function onVerifyResp(paramList) {		
		/* 检查执行结果 */
		var passed = false;
		var res = paramList.shift();
		if (0 != res) {
			passed = false;
		}	
		else {
			passed = true;
		}
		
		/* 通过回调函数传回结果 */
		if (_onResult) {
			_onResult(RESP_VERIFY_MESSAGE, passed);						
		}
	}	
	
	/*
	 *	设备事件响应函数
	 */
	function onKeyEvent(paramList) {
		/* 事件类型 */
		var eventType = paramList[0];
		
		/* 设备序号 */
		var index = paramList[1];
		
		/* 设备名 */
		var name = paramList[2];
		
		/* 回调 */
		if (_onKeyEvent != null) {
			_onKeyEvent(eventType, index, name);
		}
	}
}
