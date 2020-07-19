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
		<title>部门管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="tree,table,icheck,chosen,dialog,icon" name="p"/>
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
							<!-- <a href="#" class="btn btn-info btn-block btn-xs margin-bottom">左侧显示</a> -->
							
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">所有部门</span></h3>
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
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">部门管理</span></h3>
									<div class="box-tools pull-right">
										<button type="button" class="btn btn-xs btn-info" onclick="saveDepartment()">保存</button>
										<button type="button" class="btn btn-xs btn-info" onclick="toAddDepartment()">新增</button>
										<button type="button" class="btn btn-xs btn-info" onclick="deleteDepartment()">删除</button>
									</div>
								</div>
								<div class="box-body">
									<div class="row">
										<form id="dataform" class="col-xs-11 col-sm-11 col-md-10 col-lg-10">
											<input type="hidden" name="parentId" id="parentId" />
											<!-- <input type="hidden" name="id" id="modelId"> -->
											
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="modelId">部门ID</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="modelId" name="id" readonly = "readonly" />
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">部门Code</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="code" name="code"  />
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">部门名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="name" name="name" tips-message="请输入部门名称" placeholder="部门名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">部门备注</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="memo" name="memo" placeholder="部门备注"/>
												</div>
											</div>
											<!-- /.base_query-group -->
										<!-- 	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ipAddress">ip白名单</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="ipAddress" name="ipAddress" placeholder="ip白名单"/>
												</div>
											</div> -->
											<!-- /.base_query-group -->
										<!-- 	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="returnAddress">回调地址</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="returnAddress" name="returnAddress" placeholder="回调地址"/>
												</div>
											</div> -->
											<!-- /.base_query-group -->
										<!-- 	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="isLeaf">是否叶子</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<label><input type="checkbox" id="isLeaf" name="isLeaf"/> 是否叶子</label>
												</div>
											</div> -->
											<!-- /.base_query-group -->
										</form>
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
			<jsp:param value="tree,table,icheck,tips,chosen,dialog" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			//ztree setting
			var setting = {
				data: {
					simpleData: {
						enable: true,
						idKey: "oid",
						pIdKey: "poid",
						rootPId: 0
					}
				},callback: {
					onClick: zTreeOnClick
				}
			};
			
			//初始化
			$(function(){
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				getDepartmentList();
				
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
			
			//点击树节点的回调函数
			function zTreeOnClick(event, treeId, treeNode){
				if(treeNode.id!=0)
				$.ajax({
					url:"departmentAction!findById.action",
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
				   		if(data){
				   			$("#modelId").val(data.department.id);
				   			$("#code").val(data.department.code);
				   			$("#name").val(data.department.name);
				   			$("#memo").val(data.department.memo);
				   			$("#memo").val(data.department.memo);
				   			//$("#ipAddress").val(data.department.ipAddress);
				   			//$("#returnAddress").val(data.department.returnAddress);
				   			if(data.department.isLeaf =="1"){
				   				$("#isLeaf").iCheck("check");
				   			}
				   			else {
				   				$("#isLeaf").iCheck("uncheck");
				   			}
				   		}
				   	}
				});
				else{
					$("#dataform input").val('');
					$("#dataform input[type='checkbox']").iCheck("uncheck");
				}
			}
			
			//得到所有
			function getDepartmentList() {
				$("#dataform input").val('');
				$("#dataform input[type='checkbox']").iCheck("uncheck");
				$.ajax({
				   	url:"departmentAction!findTree.action",
				   	type:"POST",
				   	dataType:"json",
				   	async:true,
				  	error:function(request,textStatus, errorThrown){
				  		fxShowAjaxError(request, textStatus, errorThrown);
					},
				   	success:function(data){
				   		if(data && data.departments){
				   			
							$.fn.zTree.init($("#treeDemo"), setting, data.departments);
							var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
							var nodes = treeObj.getNodes();
				   		} else {
				   			var list= [];
				   			list.push({parentId:null,id:0,name:'一证通办'});
							$.fn.zTree.init($("#treeDemo"), setting, list);
							
							$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
							var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
							var nodes = treeObj.getNodes();
				   		}
					}
				});
			}
			
			//切换到新增
			function toAddDepartment() {
				$("#dataform input").val('');
				$("#dataform input[type='checkbox']").iCheck("uncheck");
			}
			
			//根据条件更新或添加
			function saveDepartment(){
				if(tipsRegionValidator($("#dataform"))){
					
					if($('#isLeaf').attr("checked"))
						$('#isLeaf').val('1');
					else
						$('#isLeaf').val('0');
					
					if($("#modelId").val()&&!$("#modelId").val()==(''))
						updateDepartment();
					else
						addDepartment();
				}
			}
			
			//添加
			function addDepartment(){
				if (tipsRegionValidator(document)){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var node = treeObj.getSelectedNodes()[0];
				 	if(!node){
						$("#parentId").val(0);
					}else{
						$("#parentId").val(node.id);
					}
					
					var param = $("#dataform").serialize();
				 	$.ajax({
						url:"departmentAction!save.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : param,
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		if(data.success){
					   			
					   			Modal.alert({
									msg: "新增成功",
									title: '新增部门',
									btnok: '确定',
									btncl:'取消'
								});
					   			getDepartmentList();
					   		}
					   	}
					});
				}
			}
			
			//更新
			function updateDepartment() {
				if (tipsRegionValidator(document)){
					//var node = $('#tree').treeview('getSelected')[0];
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var node = treeObj.getSelectedNodes()[0];
					
					if(!node){
						Modal.alert({
							msg: "请选择需要修改的菜单！",
							title: '修改部门',
							btnok: '确定',
							btncl:'取消'
						});
						return;
					}
					$("#parentId").val(0);
					if(node.parentId){
						$("#parentId").val(node.parentId);
					}
					
					var param = $("#dataform").serialize();
				 	$.ajax({
						url:"departmentAction!update.action",
					   	type:"POST",
					   	dataType:"json",
					   	data : param,
					   	async:true,
					  	error:function(request,textStatus, errorThrown){
					  		fxShowAjaxError(request, textStatus, errorThrown);
						},
					   	success:function(data){
					   		if(data.success){
					   			Modal.alert({
									msg: "修改成功",
									title: '修改部门',
									btnok: '确定',
									btncl:'取消'
								});
					   			getDepartmentList();
					   		}	   		
					   	}
					}); 
				}
			}
			
			//删除
			function deleteDepartment() {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var node = treeObj.getSelectedNodes()[0];
				if(!node||!node.id){
					
					Modal.alert({
						msg: "请选择需要删除的部门！",
						title: '删除部门',
						btnok: '确定',
						btncl:'取消'
					});
					return;
				}
				if(node.children != null && node.children != undefined){
					Modal.alert({
						msg: "该部门下存在子部门，请先删除子部门！",
						title: '删除部门',
						btnok: '确定',
						btncl:'取消'
					});
					return;
				}
				Modal.confirm({
					btnok: '确定',
					btncl:'取消',
					msg: "是否删除该部门？"
				}).on( function (e) {
					if(e){
						if(node.children != null && node.children != undefined){
							Modal.alert({
								msg: "该部门下存在子部门，请先删除子部门！",
								title: '删除部门',
								btnok: '确定',
								btncl:'取消'
							});
							
						}else{
							$.ajax({
							   	url:"departmentAction!softRemove.action",
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
							   		getDepartmentList();
							   		if(data.success)
								   		Modal.alert({
											msg: "删除成功",
											title: '删除部门',
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