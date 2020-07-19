<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>反馈</title>
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="icheck,chosen,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="css/css.css"/>
	</head>
	<body>
		<div class="borderBox">
			<div class="title">提交问题反馈</div>
			<div class="title textadadad">如果提交有关问题反馈，请填写联系方式和问题反馈，以便我们尽快对应查找问题并解决</div>
			<form id="submitForm" onsubmit="return false;" class="form form-horizontal base_margin-t-10">
				<input type="hidden" name="sfId" value="${param.sfId}">
				<input type="hidden" name="certCode" value="${param.certCode}">
				<input type="hidden" name="busiCode" value="${param.busiCode}">
				<div class="form-group">
					<label class="col-sm-2 control-label">联系人姓名</label>
					<div class="col-sm-9">
						<input class="form-control" name="username" id="username"/>
					</div>
					<div class="col-sm-1 textRed base_padding-l-0">*</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系方式</label>
					<div class="col-sm-9">
						<input class="form-control" name="phone" id="phone"/>
					</div>
					<div class="col-sm-1 textRed base_padding-l-0">*</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">证件名称</label>
					<div class="col-sm-9">
						<input class="form-control" id="certName" name="certName" value=""/>
					</div>
					<div class="col-sm-1 textRed base_padding-l-0">*</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">类别</label>
					<div class="col-sm-9">
						<select class="form-control" id="type" name="type">
							<option value="">请选择</option>
							<option value="错误数据">错误数据</option>
							<option value="无数据">无数据</option>
							<option value="其他">其他</option>
						</select>
					</div>
					<div class="col-sm-1 textRed base_padding-l-0">*</div>
				</div>
				<div class="form-group">
					<label for="content" class="col-sm-2 control-label">反馈意见</label>
					<div class="col-sm-9">
						<textarea class="form-control" rows="4" name="content" id="" ></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上传附件</label>
					<div class="col-sm-9">
						<input class="filesInput" type="text"/>
						<input type="file" name="file" onchange="fileChange(this)" style="display:none;"/>
						<button class="btn btn-md darkBlueButton" onclick="buttonClick(this)">选择文件</button>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">验证码</label>
					<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
						<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 no-padding">
							<input type="text" id="code" name="code" class="form-control base_input-text base_height-35" placeholder="验证码" tips-message="请输入验证码" />
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<img title="点击更换验证码" id="codeImg" alt="验证码" src="<%=path %>/images/verify-img.png">
						</div>
						<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 base_padding-l-0">
							<button class="btn btn-middle darkBlueButton" onclick="submitaa()">提交</button>
						</div>
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label class="col-sm-2 control-label">证件详情</label>--%>
					<%--<div class="col-sm-9">--%>
						<%--<img id="image" style="width:100%;height:100%"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			</form>
		</div>
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="icheck,chosen,cookie" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="js/jquery-form.js"></script>
		<script type="text/javascript">
		
			var certName = '<%=new String(request.getParameter("certName").getBytes("ISO-8859-1"),"UTF-8")%>';
			
			$(function (){
				$("#username").val(window.opener._userName);
				$("#certName").val(certName);
				$("#image").attr("src", "certificates/"+'${param.sfId}' + "/" + '${param.busiCode}' +"/"+'${param.certCode}'+"_signed.jpg");
		
			});
			function changeCode1() {
				$("#codeImg").attr("src", "codeSessionAction!getCodeGenerate.action?t=" + genTimestamp());
			};
			
			changeCode1();
			function genTimestamp() {
				var time = new Date();
				return time.getTime();
			};
			$("#codeImg").bind("click", changeCode1);
			function submitaa(){
				if($("#username").val() == "" || $("#certName").val() == "" || $("#type").val() == "" || $("#phone").val() == "") {
					alert("必填项请填写完整！");
					return false;
				}
				var form = $('#submitForm');
				var url="opinionAction!create.action";
				uploadFileAjax(form,url);
			};
			function buttonClick(obj){
				$(obj).prev().click();
			};
			function fileChange(file){
			    var upload_file = $.trim($(file).val());    //获取上传文件
			    $(file).prev().val(upload_file);     //赋值给自定义input框
			};
			function uploadFileAjax(form,url){
				var ajax_option={
					url:url,
					type : 'post',
					success:function(data){
						var result = data.result;
						if (result == 1) {
							alert("提交成功！");
							window.close();
				   		} else {
				   			alert(data.msg);
				   		}
					}
				};
				form.ajaxSubmit(ajax_option);
			};
		</script>
	</body>
</html>