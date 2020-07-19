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
		<title>证件目录管理</title>
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
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">证书目录</span></h3>
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
						
						<!-- 新增目录 -->
						<div class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border base_box-header">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">信息详情</span></h3>
									<div class="box-tools pull-right">
										 <button id="updateCatalog1" style="display: none;" type="button" class="btn btn-xs btn-info" onClick="updateCatalog();">保存</button>
								  	 	 <button id="saveCatalog1"   type="button" class="btn btn-xs btn-info" onClick="saveCatalog()">保存</button>
										 <button id="addCatalog1" type="button" class="btn btn-xs btn-info" onClick="addCatalog()">新增</button>
										 <button id="deleteCatalog1" type="button" class="btn btn-xs btn-info" onClick="deleteCatalog()">删除</button>
										 <button id="authorize1" type="button" class="btn btn-xs btn-info" onClick="authorize()">激活</button>
									</div>
								</div>
								<div class="box-body">
									<div class="row">
										<div class="col-xs-11 col-sm-11 col-md-10 col-lg-10">
											<form id="dataform" name="dataform">
											<input type="hidden" name="parentId" id="parentId" />
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">目录名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="目录名称" tips-message="请输入目录名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">目录编码</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="code" name="code" readonly="readonly" placeholder="目录编码" />
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">备注</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="备注" ></textarea>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="isLeaf1">是否叶子节点</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<label><input type="radio" id="isLeaf1" name="isLeaf" value="true"/> 是</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="isLeaf2" name="isLeaf" value="false" tips-message="请选择"/> 否</label>
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
			<jsp:param value="mcustomscrollbar,tree,icheck,tips,chosen,dialog,cookie" name="p"/>
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
				document.getElementById('saveCatalog1').style.display = "none";
				document.getElementById('updateCatalog1').style.display = "inline"; 
				//赋值
				$("#name").val(treeNode.model.name);
	   			$("#parentId").val(treeNode.model.parent);
	   			$("#code").val(treeNode.model.code);
	   			$("#memo").val(treeNode.model.memo);
	   			$("#active").val(treeNode.model.active);
	   			if(treeNode.model.isLeaf){
	   				$($("input[name='isLeaf']")[0]).iCheck("check");
	   			} else {
	   				$($("input[name='isLeaf']")[1]).iCheck("check");
	   			} 
	   			
	   			if(treeNode.model.active){
	   				$("#updateCatalog1").removeAttr("disabled", "true");
	   				$("#saveCatalog1").removeAttr("disabled", "true");
	   				$("#addCatalog1").removeAttr("disabled", "true");
	   				$("#deleteCatalog1").removeAttr("disabled", "true");
	   				
	   				$("#name").removeAttr("readonly","readonly");
	   				$("#memo").removeAttr("readonly","readonly");
	   				$("input[name='isLeaf']").iCheck('enable');
	   			} else {
	   				Modal.alert({
						msg: "该目录尚未激活，无法进行其他操作，请先激活！",
						title: '提示',
						btnok: '确定',
						btncl:'取消'
					});
	   				$("#updateCatalog1").attr("disabled", "true");
	   				$("#saveCatalog1").attr("disabled", "true");
	   				$("#addCatalog1").attr("disabled", "true");
	   				$("#deleteCatalog1").attr("disabled", "true");
	   				
	   				$("#name").attr("readonly","readonly");
	   				$("#memo").attr("readonly","readonly");
	   				$("input[name='isLeaf']").iCheck('disable');
	   			}
			}
			
			function getCatalog() {
				iniDataForm();
				$.ajax({
				   	url:"catalogAction!findTree.action",
				   	type:"POST",
				   	dataType:"json",
				   	async:true,
				  	error:function(request,textStatus, errorThrown){
				  		//fxShowAjaxError(request, textStatus, errorThrown);
					},
				   	success:function(data){
				   		$.fn.zTree.init($("#treeDemo"), setting, data.catalogTree);
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						zTree.expandAll(true);
						var nodes = zTree.getNodes();
						updateNodes(zTree,nodes);
					}
				});
			}
			
			function updateNodes(zTree,nodes) {
				$.each(nodes,function(i,node){
					if(node.model != null && !node.model.active){
						zTree.setting.view.fontCss["color"] = "#A9A9A9";
						zTree.updateNode(node);
					}
					if(node.children != null) {
						updateNodes(zTree,node.children);
					}
				});
			}
			
			$(function(){
				setInnerPage();//设置内容页面宽和高
				getCatalog();
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				
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
			
			function iniDataForm() {
				$("#name").val('');
				$("#code").val('');
				$("#memo").val('');
				$($("input[name='isLeaf']")[1]).prop("checked","checked");
				
				$("#name").removeAttr("readonly","readonly");
   				$("#memo").removeAttr("readonly","readonly");
   				$("input[name='isLeaf']").iCheck('enable');
			}
			
			function addCatalog() {
				document.getElementById('saveCatalog1').style.display = "inline";
				document.getElementById('updateCatalog1').style.display = "none";
				iniDataForm();
			}
			
			function saveCatalog() {
				if (tipsRegionValidator($("#dataform"))){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					if(treeObj == null) {
						$("#parentId").val(0);
					} else {
						var node = treeObj.getSelectedNodes()[0];
					 	if(node == null && node == undefined){
							$("#parentId").val(0);
						}else{
							$("#parentId").val(node.id);
						}
					}
					var param = $("#dataform").serialize();
				 	$.ajax({
						url:"catalogAction!save.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : param,
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		//fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		getCatalog();
					   		Modal.alert({
								msg: "保存成功！",
								title: '标题',
								btnok: '确定',
								btncl:'取消'
							});
					   	}
					});
				}
			}
			
			function updateCatalog() {
				if (tipsRegionValidator($("#dataform"))){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var node = treeObj.getSelectedNodes()[0];
					if(node == null && node == undefined){
						Modal.alert({
							msg: "请选择需要修改的目录！",
							title: '标题',
							btnok: '确定',
							btncl:'取消'
						});
						return;
					}else{
						if(node.pId == null && node.pId == undefined){
							$("#parentId").val(0);
						}else{
							$("#parentId").val(node.pId);
						}
					}
					var param = $("#dataform").serialize()+"&id="+node.id;
				 	$.ajax({
						url:"catalogAction!update.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : param,
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		//fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		getCatalog();
					   		Modal.alert({
								msg: "修改成功！",
								title: '标题',
								btnok: '确定',
								btncl:'取消'
							});
					   	}
					});
				}
			}
			
			function deleteCatalog() {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var node = treeObj.getSelectedNodes()[0];
				if(node == null && node == undefined){
					Modal.alert({
						msg: '请选择需要删除的目录',
						title: '标题',
						btnok: '确定',
						btncl:'取消'
					});  
					return;
				}
				Modal.confirm({
					btnok: '确定',
					btncl:'取消',
					msg: "是否删除该目录？"
				}).on( function (e) {
					if(e){
						if(node.children != null && node.children != undefined){
							Modal.alert({
								msg: "该目录下存在子目录，请先删除子目录！",
								title: '标题',
								btnok: '确定',
								btncl:'取消'
							});
						}else{
							$.ajax({
							   	url:"catalogAction!remove.action",
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
							   		getCatalog();
							   		Modal.alert({
										msg: "删除成功！",
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
			
			//激活用户
			function authorize() {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var node = treeObj.getSelectedNodes();
				if(node != 0) {
					var msg="";
					var msg1="";
					if(node[0].model.active){
						msg = "是否关闭激活状态？";
						msg1 = "激活状态已关闭！";
					} else {
						msg = "是否激活？";
						msg1 = "激活成功！";
					}
					Modal.confirm({
						msg:msg
					}).on(function(e) {
						if(e){
							$.ajax({
								cache : true,
								type : "POST",
								url : 'catalogAction!authorize.action',
								dataType : 'json',
								data : "active="+node[0].model.active+"&id="+node[0].id,
								error : function(request, textStatus, errorThrown) {
									//fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									getCatalog();
									Modal.alert({ msg:msg1, title:'提示', btnok:'确定' }).on(function(e){
										
									});
								},
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要激活的数据！', title:'提示', btnok:'确定' });
				}
			}
		</script>
	</body>
</html>