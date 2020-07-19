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
		<title>查询</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,chosen,date,icon" name="p"/>
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			<form id="dataFrom">
				url:<input type="text" name="url" style="width:500px;">
				param:<input type="text" name="param" style="width:500px;">
				<button type="button" onclick="sendPost()">提交</button>
			</form>
			结果：<textarea id="content" rows="15" style="width:500px;"></textarea>
			
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,chosen,datetime,cookie" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
		
		function sendPost() {
			$.ajax({
				type : 'post',
				url : 'tempInfoAction!sendPost.action',
				data: $("#dataFrom").serialize(),
				dataType : 'json',
				cache : false,
				async : true,
				error : function(request, textStatus, errorThrown) {
					fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					if(data != null) {
						$("#content").val(data.result);
					}
				}
			});
		}
		
		</script>
	</body>
</html>