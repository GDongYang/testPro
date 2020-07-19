<html>
<head>
﻿<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Exception</title>

<style>
BODY {
	font-size:12px;
	font-family:Verdana,STXinwei,Simsun;	
}
.x_form_label {
	border-top:1px solid #EEE;
	border-right:1px solid #EEE;
	height:18px;
	padding:1px;
	font-size:12px;
}
.x_form_field {
	border-top:1px solid #EEE;
	padding:1px;
	font-size:12px;
}
</style>

</head>

<body style="margin:0;overflow-x:hidden;" scroll="no" onload="onLoad();" onunload="onUnload();">

<table style="width:100%;height:100%;border:5 red solid;" cellpadding="0" cellspacing="0" border="0">

<tr>
<td style="height:30;color:#F00;font-size:18px;font-weight:bold;white-space:nowrap;"> &nbsp; Exception occurs</td>
</tr>

<tr>
<td valign="top">

	<table style="width:100%;height:100%;" cellpadding="0" cellspacing="0" border="0">
	<!--    style="background-color:#EAF3FE;"-->
	<tr>
	<td class="x_form_label" style="width:120;">status code</td>
	<td class="x_form_field"> </td>
	</tr>
	<tr>
	<td class="x_form_label" onclick="switchEncoding();">request uri</td>
	<td class="x_form_field" id="requestUri" encoded="true">${(Request["_reqeust_string_"])?if_exists?js_string}</td>
	</tr>
    <tr>
	<td class="x_form_label">servlet name</td>
	<td class="x_form_field"> </td>
	</tr>
    <tr>
	<td class="x_form_label">message</td>
	<td class="x_form_field">${(Request["_exception_message_"])?if_exists?js_string}</td>
	</tr>
	
	<tr>
	<td class="x_form_label" style="height:30%;">stack trace</td>
	<td class="x_form_field">
		<table style="width:100%;height:100%;table-layout:fixed;" cellpadding="0" cellspacing="0" border="0">
    	<tr>
		<td>	
	    	<div style="width:100%;height:100%;overflow:auto;"><pre style="font-size:12;font-family:Verdana,Simsun;">${(Request["_exception_stack_trace_"])?if_exists?js_string}</pre></div></td>
		</tr>
		</table>
	</tr>	
	</table>
	
</td>
</tr>
</table>

<script>
function switchEncoding() {
	var o = document.getElementById('requestUri');
	if('true'==o.encoded) {
		o.innerHTML = decodeURIComponent(o.innerHTML);
		o.encoded = 'false';
	} else {
		o.innerHTML = encodeURIComponent(o.innerHTML);
		o.encoded = 'true';
	}
}
</script>

</body>
<script>
function onLoad() {
	switchEncoding();
	window.status = 'onLoad';
}

function onUnload() {
	window.status = 'onUnload';	
}
</script>
</html>