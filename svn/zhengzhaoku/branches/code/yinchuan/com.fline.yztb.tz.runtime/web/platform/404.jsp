<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>台州市一证通办数据应用系统及无证明城市支撑平台</title>
		<link href="../images/favicon.ico" mce_href="images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="../images/favicon.ico" mce_href="images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="../images/favicon.ico" mce_href="images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="" name="p"/>
		</jsp:include>
	</head>
	<body style="background:url(<%=path %>/images/404.jpg) no-repeat;background-size:100% 100%">
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="" name="p"/>
		</jsp:include>
		<script type="text/javascript">
			$(function(){
				var windowHeight=window.innerHeight;
				$("body").css("min-height",windowHeight);
			});
		</script>
	</body>
</html>