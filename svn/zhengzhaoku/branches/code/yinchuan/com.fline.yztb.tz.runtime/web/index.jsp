<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.owasp.encoder.Encode" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=10"/>
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>台州市一证通办数据应用系统及无证明城市支撑平台</title>
		<link href="images/favicon.ico" mce_href="images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,chosen,dialog,icon,table" name="p"/>
		</jsp:include>
		<style type="text/css">
		.base_skin-aqua .sidebar-menu .treeview-menu > li > a{padding-left:30px}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<header class="main-header">
				<a href="index.jsp" class="logo navbar-fixed-top base_text-size-10 base_padding-l-30"><b><div style="float:left;">台州市一证通办及无证明系统</div></b></a>
				<nav class="navbar navbar-fixed-top" role="navigation">
					<a href="#" class="sidebar-toggle navigationTag" data-toggle="offcanvas" role="button" >
						<span class="sr-only">Toggle navigation</span>
					</a>
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<li class="dropdown user user-menu" >
								<a href="#" class="dropdown-toggle base_dropdown-toggle" data-toggle="dropdown">
									<span >欢迎您，</span><span class="hidden-xs" id="username"></span>
								</a>
								<ul class="dropdown-menu">
									<li class="user-header base_user-header">
										<img src="<%=path %>/images/no-photo.jpg" class="img-circle" alt="User Image"/>
									</li>
									<li>
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
											<span>皮肤切换：</span>
											<ul class="fc-color-picker base_text-size-20" id="layout-skins-list">
												<li><a class="text-aqua" title="base_skin-aqua" data-skin="skin-blue base_skin-aqua" href="#"><i class="fa fa-square"></i></a></li>
												<li><a class="text-red" title="base_skin-red" data-skin="skin-red base_skin-red" href="#"><i class="fa fa-square"></i></a></li>
												<li><a class="text-gray" title="base_skin-gray" data-skin="skin-blue base_skin-gray" href="#"><i class="fa fa-square"></i></a></li>
												<li><a class="text-gray" title="base_skin-gray3d" data-skin="skin-blue base_skin-gray3d" href="#"><i class="fa fa-square"></i></a></li>
												
												<li><a class="text-blue" title="skin-blue" data-skin="skin-blue" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-light-blue" title="skin-blue-light" data-skin="skin-blue-light" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-yellow" title="skin-yellow" data-skin="skin-yellow" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-yellow" title="skin-yellow-light" data-skin="skin-yellow-light" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-green" title="skin-green" data-skin="skin-green" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-green" title="skin-green-light" data-skin="skin-green-light" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-purple" title="skin-purple" data-skin="skin-purple" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-purple" title="skin-purple-light" data-skin="skin-purple-light" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-red" title="skin-red" data-skin="skin-red" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-red" title="skin-red-light" data-skin="skin-red-light" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-black" title="skin-black" data-skin="skin-black" href="#"><i class="fa fa-square-o"></i></a></li>
												<li><a class="text-black" title="skin-black-light" data-skin="skin-black-light" href="#"><i class="fa fa-square-o"></i></a></li>
											</ul>
										</div>
										<h2 class="page-header base_page-header"></h2>
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
											<span>风格切换：</span>
											<ul class="fc-color-picker base_text-size-20" id="layout-template-list">
												<li><a class="text-aqua" title="fixed" data-template="fixed" href="#"><i class="fa fa-th-list"></i></a></li>
												<li><a class="text-aqua" title="layout-boxed" data-template="layout-boxed" href="#"><i class="fa fa-align-justify"></i></a></li>
												<li><a class="text-aqua" title="layout-collapse" data-template="layout-collapse" href="#"><i class="fa fa-list"></i></a></li>
											</ul>
										</div>
										<h2 class="page-header base_page-header"></h2>
									</li>
									<li class="user-footer">
										<div class="pull-left">
											<a href="#" class="btn btn-default btn-flat" onclick="showUserInfo();">用户信息</a>
										</div>
										<div class="pull-right">
											<a href="#" class="btn btn-default btn-flat" onclick="logout();">注销</a>
										</div>
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</nav>
			</header>
			<!-- ./main-header -->
			
			<aside class="main-sidebar navigationSidebar">
				<section class="sidebar left-side">
					<div class="user-panel">
						<div class="pull-left image">
							<!-- <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" /> -->
						</div>
						<div class="pull-left info">
							<!--  <p>Alexander Pierce</p>
							<a href="#"><i class="fa fa-circle text-success"></i> Online</a> -->
						</div>
					</div>
					<!-- /.user-panel -->
					
					<!-- <form action="#" method="get" class="sidebar-form">
						<div class="input-group">
							<input type="text" name="q" class="form-control" placeholder="查找..."/>
							<span class="input-group-btn">
								<button type='submit' name='search' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
							</span>
						</div>
					</form> -->
					<ul class="sidebar-menu content"></ul>
				</section>
				<!-- /.sidebar -->
			</aside>
			
			<div class="base_fill-space"></div>
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_iframe-content-wrapper">
				<iframe name="mainFrame" id="mainFrame" src="dashboard.jsp"></iframe>
			</div>
			
			<!-- <footer class="main-footer">
				<div class="pull-right hidden-xs">
					<b>Version</b> 2.0
				</div>
				<strong>Copyright &copy; 2014-2015 <a href="#">杭州中奥科技</a>.</strong> All rights reserved.
			</footer> -->
			
		</div>
		<!-- ./wrapper -->
		
		<!-- 用户信息 -->
		<div id="dataUserWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataform" name="dataform">
				<input type="hidden" id="id" name="id"/>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group" id="userNameDiv">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="userName">用户名</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="userName" name="username" placeholder="用户名" tips-message="请输入用户名"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">姓名</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="姓名" tips-message="请输入姓名"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="workNo">工号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="workNo" name="workNo" placeholder="工号"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="sex">性别</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="sex" name="sex" placeholder="性别">
							<option value="0">男</option>
							<option value="1">女</option>
						</select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="org">所属单位</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="org" name="orgId" placeholder="所属单位"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="mobilePhone">移动电话</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="mobilePhone" name="mobilePhone" placeholder="移动电话" tips-min="0" tips-max="11" tips-regexp="{'code':'/^[0-9]+$/','message':'移动电话只能是数字'}"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="officePhone">办公室电话</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="officePhone" name="officePhone" placeholder="办公室电话" tips-regexp="{'code':'/^[0-9]+$/','message':'办公室电话只能是数字'}"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="email">电子邮箱</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="email" name="email" placeholder="电子邮箱" tips-regexp="{'code':'/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/','message':'邮箱地址格式错误'}"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="virtualMobilePhone">手机虚拟号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="virtualMobilePhone" name="virtualMobilePhone" placeholder="手机虚拟号" tips-min="0" tips-max="6" tips-regexp="{'code':'/^[0-9]+$/','message':'手机虚拟号只能是数字'}"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="fax">传真</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="fax" name="fax" placeholder="传真" tips-regexp="{'code':'/^[0-9]+$/','message':'传真只能是数字'}"/>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		<!-- /.base_hidden -->
		
		<div id="businessDetailWrap" class="base_hidden">
			<table id="tableDetail" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
				data-ajax="ajaxRequestItem" data-side-pagination="server"
				data-striped="true" data-single-select="true"
				data-click-to-select="true" data-pagination="true"
				data-pagination-first-text="首页" data-pagination-pre-text="上一页"
				data-pagination-next-text="下一页" data-pagination-last-text="末页">
				<thead style="text-align:center;">
					<tr>
						<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
						<th data-field="certName">证件模板名称</th>
					    <th data-field="status" data-formatter="statusFormatter">状态</th>
					    <th data-field="timeConsuming">耗时</th>
					</tr>
				</thead>
			</table>
		</div>
		
		
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,chosen,dialog,cookie,table" name="p"/>
		</jsp:include>
		
		<script type="text/javascript" src="<%=path %>/bootstrap/js/docs.js"></script>
		<script type="text/javascript">
			var userPrivileges = {};
			var loginFlag = '<%=Encode.forUriComponent(request.getParameter("loginFlag"))%>';
			
			$(function(){
				getCookieTemplate();//获取cook中的模板
				setHomePage();//设置首页面菜单栏高
				selectConfig();//下拉框初始化
				
				//加载用户信息
				loadCurrentUser();
				$.AdminLTE.tree('.sidebar');
				
				initIndexMenu();//初始化主页侧边导航
				detail();//接口巡检结果
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
						$("#username").html(currentUser.name).attr("data-userid",currentUser.id);
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
	 					fxShowAjaxError(request, textStatus, errorThrown);
					},
	 				success:function(data){
	 					window.location.href = "loginPlat.jsp";
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
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success:function(data){
						//console.log(JSON.stringify(data));
						//alert(JSON.stringify(data));
						var length = data.length;
						//声明一个父层menu 列表或数组
						for (var ii = 0; ii < length; ii ++) {
							var menu = data[ii];
							var menuName = menu.name;
							var lab = $("<span>").text(menu.name);
							var vicon = $("<i>").attr("class", menu.icon);
							if (menu.children) {
								var va = $("<a>").attr("title",menuName).append(vicon).append(lab).attr("href", "#");
								var numLab = $("<span>").attr("class", "label label-primary pull-right base_menu-number").text(menu.children.length);
								va.append(numLab);
								var rli = $("<li>").append(va).attr("class", "treeview");
								var cul = $("<ul>").attr("class", "treeview-menu");
								showMenu(cul, menu.children);
								rli.append(cul);
							}
							else {
								var va = $("<a>").attr("title",menuName).append(vicon).append(lab).attr("href", menu.location).attr("target","mainFrame");
								var rli = $("<li>").append(va);
							}
							$(".sidebar-menu").append(rli);
						}
						
						$.AdminLTE.tree('.sidebar');
					}
				});
			}
			
			function showMenu(parentMenu, menus) {
				var length = menus.length;
				for (var ii = 0; ii < length; ii ++) {
					var menu = menus[ii];
					var menuName = menu.name;
					var lab = $("<span>").text(" " + menu.name);
					var vicon = $("<i>").attr("class", menu.icon);
					if (menu.children) {
						var va = $("<a>").attr("title",menuName).append(vicon).append(lab).attr("href", "#");
						var numLab = $("<span>").attr("class", "label label-primary pull-right base_menu-number").text(menu.children.length);
						va.append(numLab);
						var rli = $("<li>").append(va).attr("class", "treeview");
						var cul = $("<ul>").attr("class", "treeview-menu");
						showMenu(cul, menu.children);
						rli.append(cul);
					}
					else {
						var va = $("<a>").attr("title",menuName).append(vicon).append(lab).attr("href", menu.location).attr("target","mainFrame");
						var rli = $("<li>").append(va);
					}
					parentMenu.append(rli);
				}
			}
			
			function loadDepart() {
				$.ajax({
					type : 'get',
					url : 'departmentAction!findAll.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						$.each(data.departments,function(i,org){
							$("#dataUserWrap #org").append('<option value="'+org.id+'">'+org.name+'</option>');
						});
					}
				});
			}
			
			function showUserInfo(){
				loadDepart();
				var userId = $("#username").attr("data-userid");
 				$.ajax({
					type : 'post',
					url : 'userAction!findById.action',
					dataType : 'json',
					traditional : true,
					data : {"id":userId},
					error : function(request, textStatus, errorThrown) { 
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var user = data.user;
 						$("#dataUserWrap #id").val(user.id);
						$("#dataUserWrap #userName").val(user.username);
						$("#dataUserWrap #name").val(user.name);
						$("#dataUserWrap #workNo").val(user.workNo);
						$("#dataUserWrap #sex").val(user.sex);
						$("#dataUserWrap #org").val(user.orgId);
						$("#dataUserWrap #mobilePhone").val(user.mobilePhone);
						$("#dataUserWrap #officePhone").val(user.officePhone);
						$("#dataUserWrap #email").val(user.email);
						$("#dataUserWrap #virtualMobilePhone").val(user.virtualMobilePhone);
						$("#dataUserWrap #fax").val(user.fax);
						
						var dialog = getJqueryDialog();
				   		dialog.Container = $("#dataUserWrap");
				   		dialog.Title = "用户信息";
				   		if (!fBrowserRedirect()) {
				   			dialog.Width = 540;
				   		}
				   		dialog.ZIndex = 1050;
				   		dialog.CloseOperation = "destroy";
				   		dialog.ButtonJSON = {
							"确定" : function() {
								$(this).dialog("close");
							}
				   		};
				   		dialog.show();
				   		
				   		setRegionLock($("#dataUserWrap"));//设置区域锁定
				   		selectUpdated($("#dataUserWrap #sex"));//下拉框变动更新
				   		selectUpdated($("#dataUserWrap #org"));//下拉框变动更新
					}
				});
			}
			
			//ajax请求
			function ajaxRequestItem(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
			    index = params.data.offset + 1;
			    if(loginFlag != 1) {
			    	return false;
			    }
				$.ajax({
					type : 'post',
					url : 'businessAction!findItemPage.action',
					dataType : 'json',
					data:{'pageNum':pageNum,'pageSize':pageSize,'inspection':1},
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var datas = data ? data.page.items : [];
						var count = data ? data.page.count : 0;
						params.success({
							total : count,
							rows : datas ?  datas : []
						});
						params.complete();
					}
				});
			}
			
			function detail() {
			    if(loginFlag != 1) {
			    	return false;
			    }
				//$("#tableDetail").bootstrapTable('refresh');
				var dialog = getJqueryDialog();
				dialog.Container = $("#businessDetailWrap");
				dialog.Title = "接口巡检结果告知";
				if (!fBrowserRedirect()) {
					dialog.Width = "720";
				}
				dialog.ZIndex = 1050;
				dialog.Height = "480";
				dialog.CloseOperation = "destroy";
				dialog.show();
			}
			
			function statusFormatter(value,row){
				if(value == 1){
					return '<font style="color:#0000FF">成功</font>';
				}
				if(value == 0){
					return '<font style="color:#00EC00">无证件信息</font>';
				}
				else{
					return '<font style="color:#FF0000">失败</font>';
				}
			}
			
			function idFormatter(value, row) {
				return index++;
			}

		</script>
	</body>
</html>