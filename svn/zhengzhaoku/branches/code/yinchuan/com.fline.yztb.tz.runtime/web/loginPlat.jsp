<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>台州市一证通办数据应用系统及无证明城市支撑平台</title>
		<link href="<%=path %>/images/favicon.ico" mce_href="<%=path %>/images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="<%=path %>/images/favicon.ico" mce_href="<%=path %>/images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="<%=path %>/images/favicon.ico" mce_href="<%=path %>/images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="icheck" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="cert/css/1.css" />
		<link rel="stylesheet" type="text/css" href="css/login.css?v=2" />
	</head>
	<body class="backgroundOne">
    <h1 class="base_text-white text-center base_margin-t-90">台州市一证通办数据应用系统</h1>
	<h1 class="base_text-white text-center ">无证明城市支撑平台</h1>
		<div class="base_login-box base_margin-t-150">
			<form class="form-horizontal">
				<div id="login-box" class="box no-border no-margin base_box-area-gray">
					<div class="box-body">
						<div class="form-group base_margin-tb-20" align="center">
							<button type="button" id="sign_up" name="loginBtn" class="btn btn-sm btn-info loginButton">
								<i class="fa fa-fw fa-shield"></i>证书登录
							</button>
						</div>
						<div class="text-right">
							<a href="sysManage/download.jsp">资源下载</a>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div style="width: 400px;position: absolute;left:50%;margin-left:-200px;top:455px;">
			<img src="images/loginBox2.png" width="400"/>
		</div>
		<input type="hidden" name="cert"/>
		<input type="hidden" name="authCode"/>
		<input type="hidden" name="signature"/>
		<input type="hidden" name="charset"/>
		<input type="hidden" name="signAlgo"/>
	    <div id="svg" class="svg" style="display:none;z-index: 10;">
	    	<img src="<%=path %>/cert/images/loading.png">
	    </div>

		<!-- 仿Windows弹出框 -->

		<div id="owidow" class="sameWindow">
			<div class="obt" id="double">
				<div class="closeicon"></div>
				<div class="atitle fs16">选择证书</div>
			</div>
			<div class="obs">


			</div>
			<div class="obb">
				<div class="obbb">
					<div class="btna" id="btn_sure">确　定</div>
					<div class="btna" id="btn_no">取　消</div>
				</div>
			</div>
		</div>

		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="" name="p"/>
		</jsp:include>	
		<script type="text/javascript" src="<%=path %>/cert/js/drag.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_cert.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_com.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_key.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_websocket.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/login_cert.js"></script>
		<script type="text/javascript">
            notice();
			function getReturnUrl() {
				var url = window.document.location.href.toString();
				var u = url.split("?");
				if (u.length == 2)
					return "&" + u[1];
				else return "";
			}
			function login() {
				if (tipsRegionValidator($("#login-box"))) {
					var params = $("form").serialize() + getReturnUrl();
					  $.ajax({
					   	url:"userSessionAction!login.action",
					   	type:"POST",
					   	data:params,
					   	dataType:"json",
					   	error:function(request,textStatus, errorThrown){
					   		fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		var result = data.result;
					   		if (result == 1) {
					   			saveCookie();//jquery.cookie保存用户信息
					   			window.location = "index.jsp";
					   		} else {
					   			Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' });
					   		}
					   	}
					  });
				}
			}
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

						//显示证书信息列表
						FillCertInfoList(certArrary);
					}
				}
				catch (e) {
					onErrorCallbackFunc("", e.number, e.description);			
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
				stop_loading();
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
				//alert(errMsg);	
				//console.log(errMsg);
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
					}
					else {
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
				// 返回证书内容
				else if (RESP_GET_CERTIFICATE_DATA == respType) {
					//ShowCertContent(param1)
				}
				// 返回签名结果
				else if (RESP_SIGN_MESSAGE == respType) {
					//ShowSignatureResult(param1);
				}
				// 返回验签结果
				else if (RESP_VERIFY_MESSAGE == respType) {
					//ShowVerifyResult(param1);
				}
			}
	        /*
	         *  选择证书
	         */
			function OnSelCertChanged(selectvalue) {
			    var certIndex = selectvalue;

			    if (isNaN(certIndex) || certIndex == -1) {
			    	// alert("请插入证书");
			    	return;
			    }
				// console.log(certIndex);	

				if (certIndex >= 0 && certIndex < _certInfoArray.length) {	
					_selCertInfo = _certInfoArray[certIndex];				
				}			
				else {
					_selCertInfo = null;
				}
					
				// 显示证书主要信息
				if (_selCertInfo) {
	                //console.log(info);
					var content  = _zjcaCMT.getCertContent(_selCertInfo);
					if (typeof(content) != "undefined") {
						//ShowCertContent(content);
						//客户端证书内容.
						_certInfo = content;
						//console.log(content);
						//发送数据至服务端.
						loginByCert();
					}
					else {
						loading();	
					}
				}
			}
	        
	        function loginByCert(){
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
					onErrorCallbackFunc("", e.number, e.description);			
				}	
				if(signature == ""){
					$("#sign_up").removeAttr('disabled');
					return;
			    }
	        	 $.ajax({
					   	url:"userSessionAction!certLogin.action",
					   	data:{authCode:_authCode,certInfo:_certInfo,signature:signature},
					   	type:"POST",
					   	error:function(request,textStatus, errorThrown){
					   		//alert("请求出错..");
					   		//console.log(textStatus);
						},
					   	success:function(data){
					   		//_authCode = data.authCode;
					   		var result = data.result;
					   		if (result == "1") {
					   			window.location = "index.jsp";
					   		} else {
					   			$("#sign_up").removeAttr('disabled');
					   			Modal.alert({ msg:"登录失败!", title:'提示', btnok:'确定' });
					   		}
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
			
		/*
		*  loding
		*/
		function loading(){
			var loading = document.getElementById("svg");
			loading.style.display = "block";
		}
		function stop_loading(){
			var loading = document.getElementById("svg");
			loading.style.display = "none";
		}

		function notice() {
            window.open ('notice.html', 'newwindow1', 'height=300, width=560, top=120,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no') ;
        }
		</script>
	</body>
</html>