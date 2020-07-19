/* 
 * ZJCA COM object Script Interface for HTML
 * Version: v1.0
 * Date: 2016-12-07
 * 2015 - 2018 ZJCA. All rights reserved.
 * 
 *
*/
if (!Array.prototype.indexOf){  
	Array.prototype.indexOf = function(elt /*, from*/){  
		var len = this.length >>> 0;  
		var from = Number(arguments[1]) || 0;  
		from = (from < 0) ? Math.ceil(from) : Math.floor(from);  
		if (from < 0)  
		  from += len;  
		for (; from < len; from++)  
		{  
		  if (from in this &&  
			  this[from] === elt)  
			return from;  
		}  
		return -1;  
	};  
}

function zjca_COM(errCallback, eventCallback) {
	var _deviceEnum = null;
	var _onError = errCallback;
	var _onKeyEvent = eventCallback;
	
	/* 签名操作时的附加信息定义 */
	var RSA_SIGATURE_DESC = "";
	var SM2_SIGATURE_DESC = "1234567812345678";
	
	/*
	 *	设备事件响应函数
	 */
	function OnKeyChanged(name, index, type) {
		if (_onKeyEvent != null) {
			_onKeyEvent(type, index, name);
		}
	}
	/*
	 *	初始化函数，请在页面加载时调用
	 */
	this.init = function () {
		this.finaled();				
		_deviceEnum = new ActiveXObject("ZJCAKeyManagerSF.ZJCADeviceEnum.1");	
		_deviceEnum.AddHandler(OnKeyChanged);	
	}
	/*
	 *	结束函数，请在页面关闭前调用
	 */
	this.finaled = function () {
		if (_deviceEnum) {
			_deviceEnum.RemoveHandler(OnKeyChanged);
			_deviceEnum = null;
		}
	}	
	/*
	 *	获取所有设备信息列表(不强制刷新)
	 */
	this.getKeyList = function () {
		var keyCnt = 0;
		var keyArray = new Array;
		
		// 获取所有RSA设备
		_deviceEnum.EnumDevices(2, 0);
		keyCnt = _deviceEnum.Count;
		for (i = 0; i < keyCnt; i++) {
			var keyObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCADevice.1");
			_deviceEnum.get_Item(i, keyObj);				
			
			var key = new zjca_Key(i, keyObj.SN, keyObj.Label, keyObj.Manufacturer);
			keyArray.push(key);
		}			
		
		// 获取所有SM2设备
		_deviceEnum.EnumDevices(3, 0);
		keyCnt = _deviceEnum.Count;
		for (i = 0; i < keyCnt; i++) {
			var keyObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCADevice.1");
			_deviceEnum.get_Item(i, keyObj);
			
			// 查找该Key是否已经添加
			for (j = 0; j < keyArray.length; j++) {
				if (keyArray[j].getSN() == keyObj.SN) {
					isExist = true;
				}
			}
			if (!isExist) {
				var key = new zjca_Key(i, keyObj.SN, keyObj.Label, keyObj.Manufacturer);
				keyArray.push(key);		
			}
		}					
		
		return keyArray;
	}	
	/*
	 *	获取所有证书信息列表
	 */
	this.getCertList = function (keyIndex, type, usage) {
		var certIndex = 0;
		var certArray = new Array;
		var keyObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCADevice.1");
		var cert = new ActiveXObject("ZJCAKeyManagerSF.ZJCACertificate.1");
		
		// 通过RSA设备获取所有证书
		_deviceEnum.EnumDevices(2, 0);	//为兼容老版本的客户端的需要
		var keyCnt = _deviceEnum.Count;
		for (i = 0; i < keyCnt; i++) {
			if ((-1 != keyIndex) && (i != keyIndex)) {
				continue;
			}
			_deviceEnum.get_Item(i, keyObj);
			
			var certCnt = keyObj.CertificateCount;
			for (var index = 0; index < certCnt; index++) {
				keyObj.get_Certificate(index, cert);
				if ((0 != type) && (cert.KeyType != type)) {
					continue;
				}
				if ((0 != usage) && (cert.KeyUsage != usage)) {
					continue;
				}
				
				var certInfo = new zjca_Cert(certIndex++,		//总序号
											 cert.SN,       	//SN
											 cert.KeyType,  	//Alg
											 cert.KeyUsage, 	//Usage
											 cert.Subject,  	//DN
											 cert.Issuer,   	//Issuer
											 cert.ValidFrom,	//起始时间
											 cert.ValidUntil,	//失效时间
											 i         			//所在KEY的序号
											 );
				certArray.push(certInfo);
			}			
		}

		return certArray;
	}	
	/*
	 *	获取证书内容
	 */
	this.getCertContent = function (cert) {	
		var content = "";
		var keyObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCADevice.1");
		var certObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCACertificate.1");
		
		// 通过RSA设备获取所有证书
		_deviceEnum.EnumDevices(2, 0);
		var keyCnt = _deviceEnum.Count;
		for (i = 0; i < keyCnt; i++) {
			_deviceEnum.get_Item(i, keyObj);
			
			var certCnt = keyObj.CertificateCount;
			for (var index = 0; index < certCnt; index++) {
				keyObj.get_Certificate(index, certObj);
				if (certObj.SN == cert.getSN()) {
					content = certObj.ToString();
					break;
				}
			}			
		}

		return content;	
	}
	/*
	 *	使用证书签名消息
	 */
	this.signMessage = function (cert, type, flags, message) {		
		var signObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCASignedData.1");
		var keyObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCADevice.1");
		var certAlg = cert.getAlg();
		var keyIndex = -1;
		var desc = "";
				
		// 证书算法
		if (1 == certAlg) {		
			desc = RSA_SIGATURE_DESC;	
			_deviceEnum.EnumDevices(2, 0);	//为兼容老版本的客户端的需要
		}
		else if (2 == certAlg) {
			desc = SM2_SIGATURE_DESC;
			_deviceEnum.EnumDevices(3, 0);	//为兼容老版本的客户端的需要
		}
		else {
			return "";
		}
		
		// 找到对应的Key，进行签名
		keyIndex = cert.getKeyIndex();
		if (keyIndex >= 0 && keyIndex < _deviceEnum.Count) {
			_deviceEnum.get_Item(keyIndex, keyObj);
			keyObj.Sign(certAlg, message, 0x03, desc, type, flags, signObj);
			return signObj.ToString();
		}
		else {
			return "";
		}
	}
	/*
	 *	使用证书验证消息签名
	 */
	this.verifyMessage = function (keyIndex, message, base64Sign, base64Cert) {
		var pass = false;
		var desc = "";
		var certObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCACertificate.1");
		var signObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCASignedData.1");
		var keyObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCADevice.1");
		
		// 构造签名对象
		signObj.FromString(base64Sign);
		
		// 构造证书对象
		if (base64Cert != "") {
			certObj.FromString(base64Cert);					
		}
		else {
			signObj.get_Certificate(certObj);
		}
		
		// 是SM2签名
		if (2 == signObj.AlgId) {
			desc = SM2_SIGATURE_DESC;
			if (-1 == keyIndex) {	//SM2目前不支持软件验签，故取第一个SKF设备验签
				if (_deviceEnum.Count > 0) {
					keyIndex = 0;
				}
				else {			//没有SKF设备，不能验签SM2签名
					return false;
				}
			}
		}
		// 硬件验签RSA签名
		else if (keyIndex >= 0){	
			desc = RSA_SIGATURE_DESC;			
		}
		// 软件验签RSA签名
		else {
			//Nothing
		}			
		
		//使用MS CSP验签
		if (-1 == keyIndex) {
			pass = signObj.Verify(message, 0x03, certObj, desc);				
		}
		//使用硬件KEY验签
		else {
			_deviceEnum.get_Item(keyIndex, keyObj);
			pass = keyObj.Verify(message, 0x03, base64Sign, desc, certObj);
		}
		return pass;
	}
}	
	
	