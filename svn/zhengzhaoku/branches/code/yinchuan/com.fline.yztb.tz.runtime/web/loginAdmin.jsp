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
			<jsp:param value="icon,icheck" name="p"/>
		</jsp:include>
		<!-- 软键盘插件样式 -->
		<link rel="stylesheet" type="text/css" href="<%=path %>/plugins/keypad/css/jquery.keypad.css" />
        <link rel="stylesheet" type="text/css" href="css/login.css?v=2" />
	</head>
	<body class="backgroundOne">
		<h1 class="base_text-white text-center base_margin-t-90">台州市一证通办数据应用系统</h1>
		<h1 class="base_text-white text-center ">无证明城市支撑平台</h1>
		<div class="base_login-box">
			<form class="form-horizontal">
				<div id="login-box" class="base_height-180 bgWhite">
					<div class="border-t-e5e4e4">
						<div class="loginImgBox"><img src="<%=path %>/images/userName.png"/>
						</div><div class="loginInputBox"><input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" tips-message="请输入用户名"/></div>
					</div>
					<div class="border-t-e5e4e4">
						<div class="loginImgBox"><img src="<%=path %>/images/lock.png"/>
						</div><div class="loginInputBox">
						<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" tips-message="请输入密码"/>
						<input type="text" class="form-control base_input-text" id="publicKey" name="publicKey" style="display: none" value="0"/>
						<input type="text" class="form-control base_input-text" id="publicKeyId" name="publicKeyId" style="display: none" value="0"/>
					</div>
					</div>
					<div class="base_margin-t-10">
						<div class="base_display-inlineBlock base_padding-lr-10" style="line-height:40px;color:#bbb">请您输入验证码
						</div><div class="loginInputBox base_width-250 ">
							<img title="点击更换验证码" id="codeImg" alt="验证码" src="<%=path %>/images/verify-img.png">
							<input type="text" id="code" name="code" class="form-control base_width-135 base_display-inlineBlock" placeholder="验证码" tips-message="请输入验证码" style="border:1px solid #e5e4e4"/>
						</div>
					</div>
				</div>
				<div class="text-center" style="margin-top:-35px;margin-left: -3px;">
					<button type="button" id="loginBtn" name="loginBtn" class="btn btn-sm btn-info loginButton">
						登录
					</button>
				</div>
			</form>
		</div>
		<div style="width: 400px;position: absolute;left:50%;margin-left:-200px;top:450px;">
			<img src="images/loginBox.png" width="400"/>
		</div>
		<input type="hidden" name="cert"/>
		<input type="hidden" name="authCode"/>
		<input type="hidden" name="signature"/>
		<input type="hidden" name="charset"/>
		<input type="hidden" name="signAlgo"/>
	    <div id="svg" class="svg" style="display:none;z-index: 10;">
	    	<img src="<%=path %>/cert/images/loading.png">
	    </div>
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="icheck,tips" name="p"/>
		</jsp:include>	
		<script type="text/javascript" src="<%=path %>/cert/js/drag.js"></script>
		<script type="text/javascript" src="js/md5.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jsencrypt.js"></script>
		<script type="text/javascript">
            notice();
			function getReturnUrl() {
				var url = window.document.location.href.toString();
				var u = url.split("?");
				if (u.length == 2)
					return "&" + u[1];
				else return "";
			}
			
			$(function(){
				getCookieTemplate();//获取cook中的模板
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				$('input').iCheck({
					checkboxClass: 'icheckbox_square-blue',
					radioClass: 'iradio_square-blue',
					increaseArea: '20%' // optional
				});
				
				$('#password').next().hide();
				$('#signup_password').next().hide();
				$('#signup_pwd').next().hide();
		 		
		 		$(document).keyup(function(event) {
					if (event.keyCode == 13) {
						if ($("#login-box").is(":visible")) {
							$("#login-box .enter").trigger("click");
						} else if ($("#signup-box").is(":visible")) {
							$("#signup-box .enter").trigger("click");
						} else if ($("#forgot-box").is(":visible")) {
							$("#forgot-box .enter").trigger("click");
						}
					}
				});
		 		
		 		var username = $.cookie('username');
				var password = $.cookie('password');
				if (typeof(username) != "undefined" && typeof(password) != "undefined") {
					$("#username").val(username);
					$("#password").val(password);
					$("#saveid").iCheck("check");
				}
				
				$("#loginBtn").click(login);
				$("#signupBtn").click(signup);
				$("#forgotBtn").click(forgot);
				
				changeCode1();
				$("#codeImg").bind("click", changeCode1);
		
				
				//动态插入base标签
			});
			
			function ckKeypad(obj) {
				$(obj).next().click();
			}
			
			function changeCode1() {
				$("#codeImg").attr("src", "codeSessionAction!getCodeGenerate.action?t=" + genTimestamp());
			}
			
			function genTimestamp() {
				var time = new Date();
				return time.getTime();
			}
			
			//登录注册找回密码页面切换
			function changepage(value) {
				if (value == 1) {
					$("#login-box").show();
					$("#signup-box").hide();
					$("#forgot-box").hide();
				} else if(value == 2) {
					$("#login-box").hide();
					$("#signup-box").show();
					$("#forgot-box").hide();
					$("#clause").iCheck("check");
				} else {
					$("#login-box").hide();
					$("#signup-box").hide();
					$("#forgot-box").show();
				}
			}
			
			function savePaw() {
				if (!$("#saveid").is(":checked")) {
					$.cookie('username', '', {
						expires: -1
					});
					$.cookie('password', '', {
						expires: -1
					});
					$("#username").val('');
					$("#password").val('');
				}
			}
			
			/** jquery.cookie保存用户信息 **/
			function saveCookie() {
				if ($("#saveid").is(":checked")) {
					$.cookie('username', $("#username").val(), {
						expires: 7
					});
					$.cookie('password', $("#password").val(), {
						expires: 7
					});
				}
			}

			var encrypt = new JSEncrypt();
			function getParams(){
				 var username = encrypt.getParams($("#username").val());
				var params=encrypt.getParams(hex_md5($("#password").val()));
			     var password = params;
			     var code = $("#code").val();
			     var publicKeyId=$("#publicKeyId").val();
			     var params = {
			         'username' : username,
			         'password' : password,
			         'code' : code,
					 'publicKeyId':publicKeyId
			     }
			     return params;
			}
			
			function getPublicKey() {
				$.ajax({
					url:"userSessionAction!getPubKey.action",
					type:'Get',
					dataType:"json",
					async:false,
					success:function(data){
						if(data.publicKey=="0"){
							Modal.alert({ msg:data.errMsg, title:'提示', btnok:'确定' }).on(function (e){
								changeCode1();
							});
						}else {
							$("#publicKey").val(data.publicKey);
							$("#publicKeyId").val(data.publicKeyId);
						}
					}
				});

			}

			function login() {
				if (tipsRegionValidator($("#login-box"))) {
					getPublicKey();
					var params =getParams();
					  $.ajax({
					   	url:"userSessionAction!login.action",
					   	type:"POST",
					   	data:params,
					   	dataType:"json",
					   	error:function(request,textStatus, errorThrown){

						},
					   	success:function(data){
					   		var result = data.result;
					   		if (result == 1) {
					   			saveCookie();//jquery.cookie保存用户信息
					   			window.location = "index.jsp?loginFlag=1";
					   		} else {
					   			Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' }).on(function (e){
					   				changeCode1();
					   			});
					   		}
					   	}
					  });
				}
			}
			
			function signup() {
				if (tipsRegionValidator($("#signup-box"))) {
					
				}
			}
			
			function forgot() {
				if (tipsRegionValidator($("#forgot-box"))) {
					
				}
			}

            function notice() {
                window.open ('notice.html', 'newwindow1', 'height=300, width=560, top=120,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no') ;
            }
			
		</script>
	</body>
</html>