<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <style type="text/css">
      body{ text-align:center;padding:0px;margin:0px;} 
      .div{ margin:0 auto; width:800px; height:100%; }
 </style>
 <title>嵌入PDF浏览</title>
 <script type="text/javascript" src="../js/pdfobject.min.js"></script>
 <script type="text/javascript" src="../jquery/1.12.4/jquery-1.12.4.min.js"></script>
 <script type="text/javascript" src="../js/jquery.media.js"></script>
 <script>
	var tempCode = '<%=request.getParameter("tempCode")%>';
	
	var uid = guid();
	
 	$(function(){
 		  var pdfurl = "certTempAction!getDownload.action?code="+tempCode;
 		  $('#pdfFrame').attr("src", pdfurl);
	});
 	
 	function guid() {
 	    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
 	        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
 	        return v.toString(16);
 	    });
 	}

 </script>
 </head>
 <body>
	<div id="container" class="div">
	<p style="margin-top:-50px;margin-bottom:0px;">
     <iframe id="pdfFrame" width="100%" height="650"　style="border-style: none;"></iframe>
     </p>
	</div>
 </body>
</html>