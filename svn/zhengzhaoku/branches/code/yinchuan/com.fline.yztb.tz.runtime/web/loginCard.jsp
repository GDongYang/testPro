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
		<link rel="stylesheet" type="text/css" href="css/login.css?v=2" />
	</head>
	<body class="backgroundOne">
    <h1 class="base_text-white text-center base_margin-t-90">台州市一证通办数据应用系统</h1>
	<h1 class="base_text-white text-center ">无证明城市支撑平台</h1>
		<div class="base_login-box base_margin-t-100">
			<form class="form-horizontal">
				<div id="login-box" class="base_height-150 bgWhite">
				    <div class="border-t-e5e4e4">
						<div class="loginImgBox"><img src="<%=path %>/images/userName.png"/>
						</div><div class="loginInputBox"><input type="text" class="form-control" id="name" name="name" placeholder="姓名" readonly="true"/></div>
					</div>
					<div class="border-t-e5e4e4">
						<div class="loginImgBox"><img src="<%=path %>/images/lock.png"/>
						</div><div class="loginInputBox"><input type="text" class="form-control" id="cardNo" name="cardNo"  placeholder="身份证号码" readonly="true"/></div>
					</div>
					<div class="text-right base_padding-10">
						<a href="sysManage/download.jsp">资源下载</a>
					</div>
				</div>
				<div class="text-center" style="margin-top:-25px;">
					<button type="button" onclick="readCard();" id = "readCardBtn" class="btn btn-sm btn-info loginButton">
						读取身份证
					</button>
				</div>
			</form>
		</div>
		<div style="width: 400px;position: absolute;left:50%;margin-left:-200px;top:455px;">
			<img src="images/loginBox3.png" width="400"/>
		</div>

		<div id="readCardDiv">

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
		<!-- 软键盘插件脚本 -->
	    <script type="text/javascript" src="<%=path %>/plugins/keypad/js/jquery.plugin.js"></script> 
	    <script type="text/javascript" src="<%=path %>/plugins/keypad/js/jquery.keypad.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/drag.js"></script>
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
			
			function login() {
				if (tipsRegionValidator($("#login-box"))) {
					var params = $("form").serialize() + getReturnUrl();
					  $.ajax({
					   	url:"userSessionAction!cardLogin.action",
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
					   			alert("用户不存在");
					   			//Modal.alert({ msg:'用户不存在', title:'提示', btnok:'确定' });
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
			
			var _authCode = "";
			//获得挑战码
			function getAuthCode(){
				 $.ajax({
					   	url:"userSessionAction!authCode.action",
					   	type:"POST",
					   	async: false,
					   	error:function(request,textStatus, errorThrown){
					   		//alert("请求出错..");
					   		//console.log(textStatus);
						},
					   	success:function(data){
					   		_authCode = data.authCode;
					   	}
				  });
			}
			
			//读取身份证信息
			function readCard(){
				$("#readCardBtn").attr("disabled","disabled");
				$("#name").val("");
				$("#cardNo").val("");
				try {
					var instanceId = "CVR_"+new Date().getTime();
					var instanceHtml = "<OBJECT classid=\"clsid:10946843-7507-44FE-ACE8-2B3483D179B7\"";
					instanceHtml += "id=\""+instanceId+"\" name=\""+instanceId+"\" width=\"0\" height=\"0\" >";
					instanceHtml += "</OBJECT>";
					$("#readCardDiv").html(instanceHtml);
					var CVR_IDCard = document.getElementById(instanceId);					
					var strReadResult = CVR_IDCard.ReadCard();	
					if(strReadResult == "0")
					{
						$("#cardNo").val(CVR_IDCard.CardNo);
						$("#name").val(CVR_IDCard.Name);
						login();
					}else{
						throw new Error(strReadResult);
					}
				} catch (e) {
					readCardByIDR();
				}
				var clId = setTimeout(function(){
					$("#readCardBtn").removeAttr("disabled");
					clearTimeout(clId);
				}, 1000);
			}
			
			//精伦读卡器
			function readCardByIDR(){
				$("#readCardDiv").html('<object classid="clsid:5EB842AE-5C49-4FD8-8CE9-77D4AF9FD4FF" id="IdrControl1" width="100" height="100"></object>');
				try {  
					var idr = document.getElementById("IdrControl1");
					var result=idr.ReadCard("2","");
					if (result==1){
						$("#cardNo").val(idr.GetCode());
						$("#name").val(idr.GetName());
						login();
					} else {
						throw new Error(strReadResult);
					}
					return result;
				} catch(e) {  
					readCardByDK();
				}  	
			}
			
			//德卡读卡器
			function readCardByDK(){
				$("#readCardDiv").html('<OBJECT id="IdrControl2" codeBase="comRD800.cab" WIDTH="0" HEIGHT="0" classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>');
				try {  
					var idr = document.getElementById("IdrControl2");
					var st = '';
					st = idr.dc_init(100, 115200);
					if(st <= 0) {
						alert("读卡器初始化失败");
						return ;
					}
					
					st = idr.DC_start_i_d();
					if (st < 0) {
						alert("读取身份证信息失败");
						return;
					}
					$("#cardNo").val(idr.DC_i_d_query_id_number());
					$("#name").val(idr.DC_i_d_query_name());
					idr.DC_end_i_d();
					idr.dc_exit();
					login();
					return ;
				} catch(e) {  
					Modal.alert({ msg:"请尝试将身份证移开读卡区然后重新放入读卡去！", title:'提示', btnok:'确定' });
				}  	
			}

            function notice() {
                window.open ('notice.html', 'newwindow1', 'height=250, width=520, top=120,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no') ;
            }
			
		</script>
	</body>
</html>