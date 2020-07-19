<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>银川市表单管理平台</title>
		<link href="<%=path %>/images/favicon.ico" mce_href="<%=path %>/images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="<%=path %>/images/favicon.ico" mce_href="<%=path %>/images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="<%=path %>/images/favicon.ico" mce_href="<%=path %>/images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="icon,icheck" name="p"/>
		</jsp:include>
        <link rel="stylesheet" type="text/css" href="build/css/login.css?v=2" />
	</head>
	<body>
		<div class='loginImg'></div><div class='loginFormBox'>
			<h3 class="base_margin-b-50">银川市表单管理平台</h3>
			<form class="form-horizontal">
                <input type="hidden" id="codeId" name="codeId">
				<div id="login-box">
					<div>
						<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" tips-message="请输入用户名"/>
					</div>
					<div>
						<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" tips-message="请输入密码"/>
					</div>
					<div>
						<input type="text" id="code" name="code" class="form-control" placeholder="验证码" tips-message="请输入验证码"/>
						<img title="点击更换验证码" id="codeImg" alt="验证码" src="<%=path %>/images/verify-img.png">
					</div>
				</div>
				<div class="text-center">
					<button type="button" id="loginBtn" name="loginBtn" class="btn btn-block loginButton">
						登录
					</button>
				</div>
			</form>
		</div>
		<input type="hidden" name="cert"/>
		<input type="hidden" name="authCode"/>
		<input type="hidden" name="signature"/>
		<input type="hidden" name="charset"/>
		<input type="hidden" name="signAlgo"/>
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="icheck,tips,cookie" name="p"/>
		</jsp:include>	
		<script type="text/javascript" src="<%=path %>/cert/js/drag.js"></script>
		<script type="text/javascript">
			function getReturnUrl() {
				var url = window.document.location.href.toString();
				var u = url.split("?");
				if (u.length == 2)
					return "&" + u[1];
				else return "";
			}
			
			$(function(){
				var windowHeight=window.innerHeight;
				var loginFormBoxHeight=parseInt($('.loginFormBox').css('height'));
				$('body').css('height',windowHeight-2)
				$('.loginFormBox').css('margin-top',(windowHeight-loginFormBoxHeight)/2)
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				$('input').iCheck({
					checkboxClass: 'icheckbox_square-blue',
					radioClass: 'iradio_square-blue',
					increaseArea: '20%' // optional
				});
				$('.form-control').each(function(){
					$(this).val('');
				});
				$('#password').next().hide();
		 		var username = $.cookie('username');
				var password = $.cookie('password');
				if (typeof(username) != "undefined" && typeof(password) != "undefined") {
					$("#username").val(username);
					$("#password").val(password);
				}
				$("#loginBtn").click(login);
				changeCode1();
				$("#codeImg").bind("click", changeCode1);
				//动态插入base标签
			});
			
			function changeCode1() {
                $.ajax({
                    url:"securityCodeAction!getCodeGenerate.action",
                    type:"GET",
                    dataType:"json",
                    error:function(request,textStatus, errorThrown){
                        fxShowAjaxError(request, textStatus, errorThrown);
                    },
                    success:function(data){
                        if(data.returnCode != 0) {
                            alert("获取验证码失败");
                        } else {
                            var result = data.data;
                            $("#codeImg").attr("src", "data:image/jpg;base64," + result.imgBase64);
                            $("#codeId").val(result.codeId);
                        }
                    }
                });
			}
			
			function genTimestamp() {
				var time = new Date();
				return time.getTime();
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
                window.open ('notice.html', 'newwindow1', 'height=250, width=520, top=120,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no') ;
            }
			
		</script>
	</body>
</html>