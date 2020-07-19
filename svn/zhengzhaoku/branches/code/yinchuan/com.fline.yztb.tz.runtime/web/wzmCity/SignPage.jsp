<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath();
%>

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=11" />
<meta name="description" content="3 styles with inline editable feature" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>材料盖章</title>
<jsp:include page="../css/PageletCSS.jsp">
	<jsp:param
		value="mcustomscrollbar,table,icheck,tips,chosen,dialog,icon" name="p" />
</jsp:include>
</head>
<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
<div style="height:800px">
	<iframe src="" id="iframe_1" width="100%" style="height:90vh;" frameborder="1" name="iframe_1"  scrolling="auto"
			sandbox="allow-forms allow-scripts allow-same-origin allow-popups" >
	</iframe>

</div>

	<jsp:include page="../js/PageletJS.jsp">
		<jsp:param
			value="mcustomscrollbar,table,icheck,tips,chosen,dialog,cookie"
			name="p" />
	</jsp:include>
	<script type="text/javascript" src="<%=path%>/js/jquery-form.js"></script>
	<script type="text/javascript">
		var originSource;
		var count=1;
		window.addEventListener('message',function(e)
				{
					console.log(e);
					if(e.data!=null&&e.data=='发送盖章消息'){
						//保存源页面的引用
						if(count==1){
							originSource=e.source;
							count++;
						}
					}else if(e.data!=null&&e.data=='签署成功'){
						Modal.confirm({
							msg: "签署成功，是否上传盖章文件？"
						}).on(function (e) {
							if (e) {
							 UploadFileToServer(originSource);
							}else{
								// deleteFiles();
							}
						});
					}

					//window.originSource.postMessage("jjkk;pl", encodeURI(basePath));
				}
		);
		// document.body.onbeforeunload = function (event) {
		// 	Modal.alert({ msg:'盖章文件上传成功！', title:'提示', btnok:'确定' }).on(function(e){
		// 		window.originSource.postMessage("文件上传成功", encodeURI(basePath));
		// 	});
		//
		// };

		var id;
		var key;
		var basePath = "";
		var tempPath = 'D://temp//1.pdf';

		$(function () {
           id=getQueryVariable("id");
           key=getQueryVariable("key");
			loadCurrentUser();
			//获取请求当前页面的请求地址 http://localhost:8080/people/toGetPeopleList.action
			var curRequestPath = window.document.location.href;
			//获取项目请求路径 /people/toGetPeopleList.action
			var pathName = window.document.location.pathname;
			var ipAndPort = curRequestPath.indexOf(pathName);
			var localhostPath = curRequestPath.substring(0,ipAndPort);
			var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
			basePath = localhostPath + projectName;
			//alert("id is "+id+"   \n"+"key is   "+key);
			 init();
			setTimeout(function () {
				openSignHtml();
			},700);
		});

		var currentUser;
		function loadCurrentUser() {
			$.ajax({
				url:"userSessionAction!loadCurrentUser.action",
				type:"POST",
				dataType:"json",
				error:function(request,textStatus, errorThrown){
					fxShowAjaxError(request, textStatus, errorThrown);
				},
				success:function(data){
					currentUser = data;
				}
			});
		}
		// 文件上传到服务器
		function UploadFileToServer(originSource){
			var filePathSrc = tempPath;
			var serverUrl = basePath+"/sealUncoveredAction!completeforNew.action?USER_SESSION_ID="+getCookie('USER_SESSION_ID').replace(/"/g,"");
			var fileForm = 'file';
			var extraParam = "{\"key\":\""+key+"\",\"id\":\""+id+"\",\"sealPerson\":\""+currentUser.name+"\",\"sealPersonId\":\""+currentUser.id+"\"}";
			extraParam=encodeURI(extraParam);
			$.ajax({
				type : "post",
				url : "https://localhost:7688/TGCtrlApi",
				data :"{\"serverName\":\"{0DADE507-64D6-4306-956A-2ED144FF0ED1}\",\"funcName\":\"UploadFileToServer\",\"param\":\"{\\\"serverUrl\\\":\\\""+serverUrl+"\\\",\\\"filePath\\\":\\\""+filePathSrc+"\\\",\\\"fileForm\\\":\\\""+fileForm+"\\\",\\\"extraParam\\\":\\\""+extraParam+"\\\"}\"}",
				dataType : "json",
				processData:false,
				contentType:false,
				beforeSend:function(XMLHttpRequest){
				},
				success : function(data) {
					var errorCode = data.errorCode;
					if(errorCode==0){
						var result = data.result;
						var obj2 = JSON.parse(result);
						var errorMsg = obj2.errorMsg;
						console.log("上传文件文件："+errorMsg);
						Modal.alert({ msg:'盖章文件上传成功！', title:'提示', btnok:'确定' }).on(function(e){
							window.originSource.postMessage("文件上传成功", encodeURI(basePath));
						});
					}else{
						var errorMsg = data.errorMsg;
						console.log("调用上传接口："+errorMsg);
						Modal.alert({ msg:errorMsg, title:'提示', btnok:'确定' }).on(function(e){
							window.close();
						});
					}

				},
				error : function(data) {
					Modal.alert({ msg:"盖章失败", title:'提示', btnok:'确定' }).on(function(e){
						window.close();
					});
				},
				complete: function () {

				}
			})

		}


		//打开签署页面
		function openSignHtml() {
			var signPath="https://localhost:7688/index.html?file=";
			var signFilePath = encodeURI(basePath)+"/wzmCity/sealUncoveredAction!readFile.action?key="+(key);
			var url = encodeURI(signFilePath+"&destFile="+tempPath);
			var turePath="https://localhost:7688/index.html?file="+url+"";
			document.getElementById("iframe_1").src=turePath;

		}
		function init(){
			var projectid = '5000000004'; // 分配给每个应用系统的应用iD
			var projectsecret = '7be5a473649b476318b13fb44fb5bda6';// 分配给每个应用系统的应用secret
			$.ajax({
				type : "post",
				url : "https://localhost:7688/TGCtrlApi",
				data :"{\"serverName\": \"{0DADE507-64D6-4306-956A-2ED144FF0ED1}\",\"funcName\": \"TG_SetConfig\",\"param\": \"{\\\"config\\\": \\\"{			\\\\\\\"sectionList\\\\\\\": [{\\\\\\\"TGPrintCtrl\\\\\\\": {\\\\\\\"ProjectID\\\\\\\": \\\\\\\""+projectid+"\\\\\\\",	\\\\\\\"ProjectSecret\\\\\\\": \\\\\\\""+projectsecret+"\\\\\\\"}},	{\\\\\\\"GetSealList\\\\\\\": {\\\\\\\"project_id\\\\\\\": \\\\\\\""+projectid+"\\\\\\\",	\\\\\\\"project_secret\\\\\\\": \\\\\\\""+projectsecret+"\\\\\\\"}}]}\\\"}\"}",
				dataType : "json",
				processData:false,
				contentType:false,

				success : function(data) {
					var errorCode = data.errorCode;
					if(errorCode==0){
						var result = data.result;
						var obj2 = JSON.parse(result);
						var errorMsg = obj2.errorMsg;
						console.log("调用从初始化接口："+errorMsg);
					}else{
						var errorMsg = data.errorMsg;
						console.log("接口调用失败"+errorMsg);
						Modal.alert({ msg:'盖章控件初始化失败！', title:'提示', btnok:'确定' }).on(function(e){
							return;
						});
					}

				},
				error : function(data) {
					alert(data.result);
				},
				complete: function () {

				}
			})

		};

		function getQueryVariable(variable)
		{
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i=0;i<vars.length;i++) {
				var pair = vars[i].split("=");
				if(pair[0] == variable){return pair[1];}
			}
			return(false);
		}

		function removeCookie(key){
			setCookie(key,"",-1); // 把cookie设置为过期
		};
		function setCookie(key,value,t){
			var oDate=new Date();
			oDate.setDate(oDate.getDate()+t);
			document.cookie=key+"="+value+"; expires="+oDate.toDateString();
		}


		function getCookie(cookie_name)
		{
			var allcookies = document.cookie;
			var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度

			// 如果找到了索引，就代表cookie存在，
			// 反之，就说明不存在。
			if (cookie_pos != -1)
			{
				// 把cookie_pos放在值的开始，只要给值加1即可。
				cookie_pos += cookie_name.length + 1;      //这里容易出问题，所以请大家参考的时候自己好好研究一下
				var cookie_end = allcookies.indexOf(";", cookie_pos);

				if (cookie_end == -1)
				{
					cookie_end = allcookies.length;
				}

				var value = unescape(allcookies.substring(cookie_pos, cookie_end));         //这里就可以得到你想要的cookie的值了。。。
			}
			return value;
		}

	</script>
</body>
</html>