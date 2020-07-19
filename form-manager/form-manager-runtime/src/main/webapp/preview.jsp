<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<jsp:include page="css/PageletCSS.jsp" >
	<jsp:param value="" name="p"/>
</jsp:include>
<html>
<body>
<span id="reSpan"></span>
<div style="text-align:center;">
	<img id="image" />
</div>
<div id="useragent" style="width:200px;"></div>
<div id="showimg" style="width:100px; height:100px; float:left; *background-image:expression('url(mhtml:' + location.href + '!showimg)');"></div>
<div id="img2" style="width:100px; height:100px; float:left; *background-image:expression('url(mhtml:' + location.href + '!img2)');"></div>
<jsp:include page="js/PageletJS.jsp" >
	<jsp:param value="" name="p"/>
</jsp:include>
<script type="text/javascript">
$(function(){
	var busiCode = getQueryString("busiCode");
	var cerNo = getQueryString("cerNo");
	var certCode = getQueryString("certCode");
	var param = {"busiCode":busiCode,"cerNo":cerNo,"certCode":certCode};
	//$("#image").attr("src", "certPictureDownLoad.action?sfId=" + cerNo +"&certCode=" + certCode +"&busiCode=" + busiCode);
    $("#image").attr("src", "./certificates/" + cerNo + "/" + certCode +"_signed.jpg");
});

function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}
</script>
</body>
</html>