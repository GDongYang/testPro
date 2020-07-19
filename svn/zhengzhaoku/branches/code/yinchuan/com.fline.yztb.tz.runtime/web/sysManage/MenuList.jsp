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
		<title>菜单管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,tree,icheck,tips,chosen,dialog,icon" name="p"/>
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							<a href="#" class="btn btn-info btn-block btn-xs margin-bottom">左侧显示</a>
							
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">菜单</span></h3>
									<div class="box-tools pull-right">
										<button type="button" class="btn btn-xs btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
									</div>
								</div>
								<div class="box-body no-padding">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
						
						<div class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border base_box-header">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">信息详情</span></h3>
									<div class="box-tools pull-right">
										<button id="updateMenu1" style="display: none;" type="button" class="btn btn-xs btn-info" onclick="updateMenu();">保存</button>
								  	 	<button id="saveMenu1"   type="button" class="btn btn-xs btn-info" onclick="saveMenu()">保存</button>
										<button type="button" class="btn btn-xs btn-info" onclick="addMenu()">新增</button>
										<button type="button" class="btn btn-xs btn-info" onclick="deleteMenu()">删除</button>
									</div>
								</div>
								<div class="box-body">
									<div class="row">
										<div class="col-xs-11 col-sm-11 col-md-10 col-lg-10">
										<form class="" id="dataform" name="dataform">
										<input type="hidden" name="visible" id="visible" />
										<input type="hidden" name="parentId" id="parentId" />
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">菜单名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="菜单名称" tips-message="请输入菜单名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="icon">菜单图标</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="icon" name="icon" placeholder="菜单图标"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="location">菜单地址</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="location" name="location" placeholder="菜单地址"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ordinal">菜单序数</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area"><!-- tips-message="请输入菜单序数" -->
													<input type="text" class="form-control base_input-text" id="ordinal" name="ordinal" tips-message="请输入序数,序数只能是数字" tips-regexp="{'code':'^[0-9]+$','message':'请输入正确格式的序数'}" placeholder="菜单序数" />
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ifVisible">是否激活</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<label><input type="checkbox" id="ifVisible" name="ifVisible"/> <!-- 是否激活 --></label>
												</div>
											</div>
											<!-- /.base_query-group -->
											</form>
										</div>
									</div>
									<!-- /.row -->
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
					</div>
					<!-- /.row -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,tree,icheck,tips,chosen,dialog" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},callback: {
					onClick: zTreeOnClick,
				}
			};
			
			function zTreeOnClick(event, treeId, treeNode){
				tipsRegionHintAddVerify($("#dataform"));
				if(treeNode.id != "0"){
					document.getElementById('saveMenu1').style.display = "none";
					document.getElementById('updateMenu1').style.display = "inline"; 
					$.ajax({
						url:"menuAction!findById.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : {
					   		id : treeNode.id
					   	},
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		if(data != null){
					   			$("#name").val(data.menu.name);
					   			
					   			$("#icon").val(data.menu.icon);
					   			$("#location").val(data.menu.location);
					   			$("#ordinal").val(data.menu.ordinal);
					   			if(data.menu.visible == "1"){
					   				$("#ifVisible").iCheck("check");
					   			}else{
					   				$("#ifVisible").iCheck("uncheck");
					   			}
					   		}
					   	}
					});
				}
			}
			
			function getMenuList() {
				$.ajax({
				   	url:"menuAction!findMenuTree.action",
				   	type:"POST",
				   	dataType:"json",
				   	async:true,
				  	error:function(request,textStatus, errorThrown){
				  		fxShowAjaxError(request, textStatus, errorThrown);
					},
				   	success:function(dataParam){
						$.fn.zTree.init($("#treeDemo"), setting, dataParam);
						$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
					}
				});
			}
			
			$(function(){
				setInnerPage();//设置内容页面宽和高
				getMenuList();
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				
				$("#ifVisible").click(function(){
					if (this.checked) {
						$("#visible").val(1);
					}
					else {
						$("#visible").val(0);
					}
					
				}); 
				//iCheck美化
				$('input').iCheck({
					checkboxClass: 'icheckbox_square-blue',
					radioClass: 'iradio_square-blue',
					increaseArea: '20%' // optional
				});
				
				//iCheck绑定事件,操作bootstrapTable点击行事件
				$('input[type=radio]').on('ifChecked',function(event){
					$(this).attr("checked", true);
				});
				$('input[type=radio]').on('ifUnchecked',function(event){
					$(this).attr("checked", false);
				});
				$('input[type=checkbox]').on('ifChecked',function(event){
					$(this).attr("checked", true);
				});
				$('input[type=checkbox]').on('ifUnchecked',function(event){
					$(this).attr("checked", false);
				});
			});
			
			function assignment(){
				if($('#ifVisible').is(':checked')) {
					$("#visible").val(1);
				}else{
					$("#visible").val(0);
				}
			}
			
			function addMenu() {
				tipsRegionHintAddVerify($("#dataform"));
				document.getElementById('saveMenu1').style.display = "inline";
				document.getElementById('updateMenu1').style.display = "none";
				$("#dataform input").val('');
			}
			
			function saveMenu(){
				if (tipsRegionValidator($("#dataform"))){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var node = treeObj.getSelectedNodes()[0];
					
				 	if(node == null && node == undefined){
				 		$("#parentId").val(0);
					}else{
						if(node.id == "0"){
							$("#parentId").val(0);
						}else{
							$("#parentId").val(node.id);
						}
						
					}
					//var param = $("#dataform").serialize()+"&id="+node.id+"&parentId="+node.model.parentId+"&visible="+$("#visible").val();
					assignment();
					var param = $("#dataform").serialize();
					$.ajax({
						url:"menuAction!save.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : param,
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		getMenuList();
					   		Modal.alert({
								msg: data,
								title: '标题',
								btnok: '确定',
								btncl:'取消'
							});
					   	}
					});
				}
			}
			
			function updateMenu() {
				if (tipsRegionValidator($("#dataform"))){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var node = treeObj.getSelectedNodes()[0];
					if(node == null && node == undefined){
						Modal.alert({
							msg: "请选择需要修改的菜单！",
							title: '标题',
							btnok: '确定',
							btncl:'取消'
						});
						return;
					}
					if(node == null && node == undefined){
						$("#parentId").val(0);
					}else{
						if(node.pId == null && node.pId == undefined){
							$("#parentId").val(0);
						}else{
							$("#parentId").val(node.pId);
						}
					}
					assignment();
					var param = $("#dataform").serialize()+"&id="+node.id;
				 	$.ajax({
						url:"menuAction!update.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : param,
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		getMenuList();
					   		Modal.alert({
								msg: data,
								title: '标题',
								btnok: '确定',
								btncl:'取消'
							});
					   	}
					}); 
				}
			}
			
			function deleteMenu() {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var node = treeObj.getSelectedNodes()[0];
				if(node == null && node == undefined){
					Modal.alert({
						msg: '请选择需要删除的菜单',
						title: '标题',
						btnok: '确定',
						btncl:'取消'
					});  
					return;
				}
				Modal.confirm({
					btnok: '确定',
					btncl:'取消',
					msg: "是否删除该菜单？"
				}).on( function (e) {
					if(e){
						if(node.children != null && node.children != undefined){
							Modal.alert({
								msg: "该菜单下存在子菜单，请先删除子菜单！",
								title: '标题',
								btnok: '确定',
								btncl:'取消'
							});
						}else{
							$.ajax({
							   	url:"menuAction!remove.action",
							   	type:"POST",
							   	dataType:"json",
							   	data : {
							   		id : node.id
							   	},
							   	async:true,
							  	error:function(request,textStatus, errorThrown){
							  		fxShowAjaxError(request, textStatus, errorThrown);
								},
							   	success:function(data){
							   		$("#dataform input").val('');
							   		getMenuList();
							   		Modal.alert({
										msg: data,
										title: '标题',
										btnok: '确定',
										btncl:'取消'
									});
							   	}
							});
						}
					}
				});
			}
		</script>
	</body>
</html>