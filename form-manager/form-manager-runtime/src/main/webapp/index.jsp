<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>银川市表单管理平台</title>
		<link href="images/favicon.ico" mce_href="images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="build/css/index.css?v=2" />
		<link rel="stylesheet" href="iconfont/iconfont.css">
	</head>
	<body>
		<div class='menuListBox'>
			<div class='systemTitle'>银川市表单管理平台</div>
			<ul id="menuList"></ul>
		</div><div class='mainBody'>
			<div class='headerBox'>
				<img src='build/image/more.png' height='20' class='base_padding-lr-20'/>
				<span class='breadcrumbBox'></span>
				<a href="#" class="pull-right base_margin-r-20">
					<span>欢迎您，</span><span class="hidden-xs" id="username"></span><span class='base_padding-lr-10'>|</span><span onclick='logout()'>退出</span>
				</a>
			</div>
			<iframe name="mainFrame" id="mainFrame" src="dashboard.jsp"></iframe>
		</div>
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="" name="p"/>
		</jsp:include>
		<script type="text/javascript">
			var loginFlag = '<%=request.getParameter("loginFlag")%>';
			$(function(){
				//加载用户信息
				loadCurrentUser();
				initIndexMenu();//初始化主页侧边导航
				var windowHeight=window.innerHeight;
		    	$("#mainFrame").css("height",windowHeight-70);
		    	$('.menuListBox').css('height',windowHeight-5);
			});
	    	var currentUser;
	    	function loadCurrentUser() {
				$.ajax({
					url:"userSessionAction!loadCurrentUser.action",
					type:"POST",
					dataType:"json",
					error:function(request,textStatus, errorThrown){
					},
					success:function(data){
						currentUser = data;
						if(currentUser.name){
							$("#username").html(currentUser.name).attr("data-userid",currentUser.id);
						}else{
							window.location.href = "login.jsp";
						}
					}
				});
	    	}	    	
			//注销
	 		function logout(){
	 			$.ajax({
	 			    url:"userSessionAction!logout.action",
	 				type:"POST",
	 				dataType:"json",
	 				golbal:false,
	 				error:function(XMLHttpRequest,textStatus, errorThrown){
					},
	 				success:function(data){
	 					window.location.href = "login.jsp";
	 				}
	 			});
	 		}
			/** 初始化主页侧边导航 **/
			function initIndexMenu() {
				$.ajax({
					url:"menuAction!findMenuList.action",
					type:"POST",
					dataType:"json",
					async:true,
					global:false,
					error:function(request,textStatus, errorThrown){
						console.log(textStatus);
					},
					success:function(data){
						var dataStr='';
						for(var i=0;i<data.length;i++){
							if(data[i].children!=null){
								dataStr+='<li class="dropdown"><a href="#"><i class="'+data[i].icon+'"></i><span>'+data[i].name+'</span>';
								dataStr+='<span class="pull-right childrenLen">'+data[i].children.length+'</span></a><ul class="second_menu base_hidden">';
								var children=data[i].children;
								for(var j=0;j<children.length;j++){
									if(children[j].children!=null){
										dataStr+='<li class="dropdownSecond"><a href="#"><i class="'+children[j].icon+'"></i><span>'+children[j].name+'</span>';
										dataStr+='<span class="pull-right childrenLen">'+children[j].children.length+'</span></a><ul class="third_menu base_hidden">';
										var childrenChild=children[j].children;
										for(var k=0;k<childrenChild.length;k++){
											dataStr+='<li><a href="#"  onclick="showMenu(\''+childrenChild[k].location+'\',\''+childrenChild[k].name+'\',\''+children[j].name+'\',\''+data[i].name+'\')">';
											dataStr+='<i class="'+childrenChild[k].icon+'"></i><span>'+childrenChild[k].name+'</span></a></li>';
										}
										dataStr+='</ul>'
									}else{
										dataStr+='<li><a href="#" onclick="showMenu(\''+children[j].location+'\',\''+children[j].name+'\',\''+data[i].name+'\')"><i class="'+children[j].icon+'"></i><span>'+children[j].name+'</span></a>';
									}
									dataStr+='</li>';
								}
								dataStr+='</ul>';
							}else{
								dataStr+='<li class="dropdown active"><a href="#" onclick="showMenu(\''+data[i].location+'\',\''+data[i].name+'\')"><i class="'+data[i].icon+'"></i><span>'+data[i].name+'</span>';
							}
							dataStr+='</a></li>';
						}
						$('#menuList').append(dataStr);
						$('li.dropdown').each(function(){
							$(this).click(function(){
								$(this).addClass('active');
								$(this).siblings().removeClass('active');
								$(this).find('ul.second_menu').removeClass('base_hidden');
								$(this).siblings().find('ul.second_menu').addClass('base_hidden');
								$(this).siblings().find('ul.third_menu').addClass('base_hidden');
							});
							$(this).find('ul.second_menu>li').each(function(){
								$(this).click(function(){
									$(this).addClass('active');
									$(this).siblings().removeClass('active');
									$(this).parent().parent().siblings().find('li').removeClass('active');
								});
							});
						});
						$('li.dropdownSecond').each(function(){
							$(this).click(function(){
								$(this).find('ul.third_menu').removeClass('base_hidden');
								$(this).siblings().find('ul.third_menu').addClass('base_hidden');
							});
							$(this).find('ul.third_menu>li').each(function(){
								if(!$(this).hasClass('dropdownSecond')){
									$(this).click(function(){
										$(this).addClass('active');
										$(this).siblings().removeClass('active');
										$(this).parent().parent().siblings().find('li').removeClass('active');
										$(this).parent().parent().parent().parent().siblings().find('li').removeClass('active');
									});
								}
							});
						});
					}
				});
			}
			function showMenu(location,self,parent,parents) {
				$('.breadcrumbBox').empty();
				$('#mainFrame').attr('src',location);
				if(parents){
					var dataStr='<span class="parentBread">'+parents+'</span><span class="base_padding-lr-10 parentBread">/</span><span class="parentBread">'
					+parent+'</span><span class="base_padding-lr-10 parentBread">/</span><span>'+self+'</span>';
				}else{
					if(parent){
						var dataStr='<span class="parentBread">'+parent+'</span><span class="base_padding-lr-10 parentBread">/</span><span>'+self+'</span>';
					}else{
						var dataStr='<span>'+self+'</span>';
					}
				}
				$('.breadcrumbBox').append(dataStr);
			}
		</script>
	</body>
</html>