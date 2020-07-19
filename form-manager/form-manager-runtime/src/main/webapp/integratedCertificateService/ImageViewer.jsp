<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <style type="text/css">
 </style>
 <title>证件模板查看</title>
 </head>

 <body>
	<div id="container" class="div">
     <img id="certImg" src="certTempAction!getImage.action?code=${param.tempCode}" />
	</div>
 </body>
</html>