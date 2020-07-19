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
		<title>一证通办主页</title>
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar" name="p"/>
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<canvas class="base_cavs"><!-- 鼠标特效 --></canvas>
					<img title="主页背景" alt="" src="<%=path %>/images/mainbg.jpg" class="base_mainBg"/>
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar" name="p"/>
		</jsp:include>
		
		<!-- 鼠标特效 -->
		<script type="text/javascript" src="<%=path %>/js/ban.js"></script>
		<script type="text/javascript">
			$(function() {
				setInnerPage();//设置内容页面宽和高
			});
		</script>
	</body>
</html>