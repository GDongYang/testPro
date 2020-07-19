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
		<title>角色管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,tree" name="p"/>
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<div class="base_query-area">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	
							<form id="searchForm">
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 pull-right">
										<div class="form-group text-right base_options-area">
											<button type="button" class="btn btn-sm btn-info" onClick="search()">搜索</button>
											<button type="button" class="btn btn-sm btn-info" onClick="clean()">清空</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div class="row">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="resStartTime">角色名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="nameSearch" name='name' placeholder="角色名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
										</div>
										<!-- /.row -->
									</div>
								</div>
							</form>
						</div>
						<h2 class="page-header base_page-header"><!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> --></h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua">
						<div class="box-header with-border base_box-header">
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">角色管理</span></h3>
							<div class="box-tools pull-right">
								<div id="toolbar" style="float: left">
									<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
									<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
									<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
									<button type="button" class="btn btn-xs btn-info" onClick="setMenu()">菜单权限</button>
									<button type="button" class="btn btn-xs btn-info" onClick="setDepart()">部门权限</button>
									<!-- <button type="button" class="btn btn-xs btn-info" onClick="assignRoles()">分配角色</button> -->
								</div>
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="true"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页">
									<thead style="text-align:center;">
										<tr>
											<th data-radio="true"></th>
											<th data-field="name">角色名称</th>
											<th data-field="description">角色描述</th>
											<th data-field="active" data-formatter="formatActive">状态</th>
											<th data-field="creator">创建者</th>
											<th data-field="level" data-formatter="formatLevel">级别</th>
										</tr>
									</thead>
								</table>
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		<!-- 新增、修改 -->
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataForm" name="dataForm">
				<input type="hidden" id="roleId" name="id" value="0">
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="roleName">角色名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" name="name" id="roleName"  placeholder="角色名称" tips-message="请输入角色名称">
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="roleDescription">角色描述</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<textarea class="form-control base_input-textarea" id="roleDescription" name="description" placeholder="角色描述"  tips-message="请输入角色描述"></textarea>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="level">角色级别</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select id="level" class="form-control" name = "level">
							<option value="0">高</option>
							<option value="1">中</option>
							<option value="-1">低</option>
						</select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">角色状态</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input id="acc_statusRadios1" type="radio" value="true" name="active" tips-message="请选择角色状态"/> 正常</label>
						<label><input id="acc_statusRadios0" type="radio" value="false" name="active"/> 未激活</label>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<!-- 菜单权限 -->
		<div id="menuFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="menuForm" name="menuForm">
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<ul id="tree" class="ztree"></ul>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<!-- 部门权限 -->
		<div id="departFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="departForm" name="departForm">
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<ul id="dtree" class="ztree"></ul>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<div id="userFormWrap" class="base_hidden">
			<table class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<tr>
					<td>
						<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="select">未选用户：</label>
						<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
							<input type="text" class="form-control base_input-text" id="userName" name='userName' placeholder="用户名称"/>
						</div>
					</td>
					<td></td>
					<td><label class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left base_text-size-15" for="select2">已选用户：</label></td>
				</tr>
				<tr>
					<td class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
						
						<select multiple="multiple" size="15" class="form-control base_multiple-select" 
						name="select" id="select" style="display:none;">
						</select>
						 
						 <div class="form-control base_multiple-select" style="overflow-y: scroll;" name="treeSelect" id="treeSelect"> 
						 	<ul id="userTree" class="ztree"></ul>
						 </div>
					</td>
					<td class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center">
						<button class="btn btn-sm btn-info base_table-btn" name="right" id="right"><span class="glyphicon glyphicon-step-forward"></span></button><br/>
						<button class="btn btn-sm btn-info base_table-btn" name="rightAll" id="rightAll"><span class="glyphicon glyphicon-fast-forward"></span></button><br/>
						<button class="btn btn-sm btn-info base_table-btn" name="leftAll" id="leftAll" ><span class="glyphicon glyphicon-fast-backward"></span></button><br/>
						<button class="btn btn-sm btn-info base_table-btn" name="left" id="left"><span class="glyphicon glyphicon-step-backward"></span></button>
					</td>
					<td class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
						<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select2" id="select2"></select>
					</td>
				</tr>
			</table>
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,tree,cookie" name="p"/>
		</jsp:include>
		<script type="text/javascript">
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true,
					chkboxType :  {"Y" : "ps", "N" : "ps"}
				}
			};
			
			var settings = {
					data: {
						simpleData: {
							enable: true,
							idKey: "oid",
							pIdKey: "poid",
							rootPId: null
						}
					},check: {
						enable: true,
						chkboxType :  {"Y" : "", "N" : ""}
					}
				};
			var userSetting = {
					data: {
						simpleData: {
							enable: true
						}
					},
				};
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				dataTable = $('#dataTable');
				
				//bootstrapTable单击事件
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				getMenuList();
				getDepartmentList();
				
				$("#right").click(function(){
					var treeObj = $.fn.zTree.getZTreeObj("userTree");
					var nodes = treeObj.getSelectedNodes();
					if(nodes.length !=0) {
						treeObj.hideNode(nodes[0]);
						var $options=$("#select").find("[value='"+nodes[0].id+"']");
						var $remove=$options.remove();
						$remove.appendTo("#select2"); 
					} else if($("#select option:selected")) {
						var $options = $("#select option:selected");
						var $remove=$options.remove();
					    $remove.appendTo("#select2");
					    var nodes = treeObj.getNodesByParam("id", $options.val(), null);
					    treeObj.hideNode(nodes[0]);
					}
					
				   
				   	//$("#select2").prepend('<option value="'+nodes[0].id+'">'+nodes[0].name+'</option>');
			    }); 
			    $("#rightAll").click(function(){
				    var treeObj = $.fn.zTree.getZTreeObj("userTree");
				    $("#select option").each(function(){
				    	var $this = $(this);
				    	var nodes = treeObj.getNodesByParam("id", $this.val(), null);
				    	treeObj.hideNodes(nodes);
				    });
				    var $options = $("#select option");
				    $options.appendTo("#select2");
			    });
			    $("#left").click(function(){
				    var $options=$("#select2 option:selected");
				    var $remove=$options.remove();
				    $remove.appendTo("#select");
				    var treeObj = $.fn.zTree.getZTreeObj("userTree");
					var nodes = treeObj.getNodesByParam("id", $options.val(), null);
				    treeObj.showNode(nodes[0]);
			    });
			    $("#leftAll").click(function(){
				    var $options=$("#select2 option");
				    var $remove=$options.remove();
				    $remove.appendTo("#select");
				    var treeObj = $.fn.zTree.getZTreeObj("userTree");
				    var nodes = treeObj.getNodesByParam("isHidden", true);
				    treeObj.showNodes(nodes);
			    });
			});
			
			//刷新页面
			function refresh() {
                dataTable.bootstrapTable('refresh');
			}
			
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
				}
			}
			
			function formatActive(val) {
				if(val == 1) {
					return '<font style="color:#0000FF">正常</font>';
				}else {
					return '<font style="color:#FF0000">未激活</font>';
				}
			}
			
			function formatLevel(val) {
				if(val == 0) {
					return "高";
				} else if (val == 1) {
					return "中";
				} else {
					return "低";
				}
			}
			
			//表单初始化
			function initFormElement_add() {
				$("#roleDescription").val('');
				$("#roleName").val('');
				$('#roleId').val(0);
			}
			//获取菜单列表
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
						$.fn.zTree.init($("#tree"), setting, dataParam);
						$.fn.zTree.getZTreeObj("tree").expandAll(true);
					}
				});
			}
			
			//获取部门列表
			function getDepartmentList() {
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
				   			
				   	/* 		data.departments.sort(function(a,b){
				   				var alastDate = a.updateDate?a.updateDate:a.createDate;
				   				var blastDate = b.updateDate?b.updateDate:b.createDate;
				   				if(alastDate<blastDate)
				   					return 1;
				   				else if(alastDate>blastDate)
				   					return -1;
				   				else
				   					return 0;
				   			}); */

				   			
				   			//data.departments.push({parentId:null,id:0,name:'一证通办'});
							$.fn.zTree.init($("#dtree"), settings, data.departments);
							
							//$.fn.zTree.getZTreeObj("dtree").expandAll(true);
				   		} else {
				   			var list= [];
				   			//list.push({parentId:null,id:0,name:'一证通办'});
							$.fn.zTree.init($("#dtree"), settings, list);
							
							//$.fn.zTree.getZTreeObj("dtree").expandAll(true);
				   		}
					}
				});
			}
			
			//新增角色
			function add() {
				initFormElement_add();
				$("#dataForm input[id='acc_statusRadios1']").iCheck('check');//选中radio
				var url='roleAction!isExists.action';
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#dataFormWrap");
		   		dialog.Title = "新增";
		   		dialog.CloseOperation = "destroy";
		   		if (!fBrowserRedirect()) {
		   			dialog.Width = 540;
		   		}
		   		dialog.Height = Math.round($(window).height() * 0.6);//设置对话框高度
		   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
		   		dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"提交" : function() {
						if (tipsRegionValidator($("#dataForm"))) {
							//服务器验证
							var url='roleAction!isExists.action';
							var data = {id:$("#roleId").val(),name:$('#roleName').val()};
							if (serversValidator(url,data)) {
								var dataStr = $('#dataForm').serialize();
								$.ajax({
									cache : true,
									type : "POST",
									url : 'roleAction!save.action',
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										Modal.alert({ msg:'新增角色成功！', title:'提示', btnok:'确定' }).on(function(e){
											refresh();
										});
									}
								});
								$(this).dialog("close");
							} else {
								addTipsHint($("#roleName"),{msg:"角色名称已存在"});
							}
						}
					}
				};
				dialog.show();
			}
			
			//修改角色
			function update() {
				initFormElement_add();
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					$('#roleName').val(obj[0].name);
					$('#roleDescription').val(obj[0].description);
					$('#roleId').val(obj[0].id);
					$('#level').val(obj[0].level);
					if(obj[0].active == 1) {
						$("#dataForm input[id='acc_statusRadios0']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios1']").iCheck('check');//选中radio
					}else {
						$("#dataForm input[id='acc_statusRadios1']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios0']").iCheck('check');//选中radio
					}
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "修改";
			   		dialog.CloseOperation = "destroy";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 540;
			   		}
			   		dialog.Height = Math.round($(window).height() * 0.6);//设置对话框高度
			   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"提交" : function() {
							if (tipsRegionValidator($("#dataForm"))) {
								//服务器验证
								var url='roleAction!isExists.action';
								var data = {id:$("#roleId").val(),name:$('#roleName').val()};
								if (serversValidator(url,data)) {
									var dataStr = $('#dataForm').serialize();
									$.ajax({
										cache : true,
										type : "POST",
										url : 'roleAction!update.action',
										dataType : 'json',
										data : dataStr,
										async : false,
										error : function(request, textStatus, errorThrown) {
											fxShowAjaxError(request, textStatus, errorThrown);
										},
										success : function(data) {
											Modal.alert({ msg:'修改角色成功！', title:'提示', btnok:'确定' }).on(function(e){
												refresh();
											});
										}
									});
									$(this).dialog("close");
								} else {
									addTipsHint($("#roleName"),{msg:"角色名称已存在"});
								}
							}
						}
					};
			   		dialog.show();
			   		
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//删除角色
			function remove() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if(rows != 0) {
					var row = rows[0];
					Modal.confirm({
						msg: "是否删除该角色？"
					}).on( function(e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'roleAction!remove.action',
								dataType : 'json',
								data : row,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'删除角色成功！', title:'提示', btnok:'确定' }).on(function(e){
										refresh();
									});
								}
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要删除的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//ajax请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'roleAction!findPage.action',
					data:dataStr,
					dataType : 'json',
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
						drawICheck('dataTable');
					}
				});
			}
			
			//查询
			function search() {
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			//关联菜单
			function setMenu() {
				var obj = $('#dataTable').bootstrapTable("getSelections");
				if(obj != 0){
					$.ajax({
						url:"menuAction!findMenuByRole.action",
						type:"POST",
						dataType:"json",
						data:{"roleId":obj[0].id},
						async:true,
						error:function(request,textStatus, errorThrown){
							fxShowAjaxError(request, textStatus, errorThrown);
						},
						success:function(dataParam){
							if (dataParam) {
								var length = dataParam.length;
								var treeObj = $.fn.zTree.getZTreeObj("tree");
								treeObj.checkAllNodes(true);
								var checkedNodes = treeObj.getCheckedNodes(true);
								var len = checkedNodes.length;
								if(length != len -1) {
									var str = "";
									for (var ii = 0; ii < length; ii++) {
										str += dataParam[ii].name + dataParam[ii].id + ",";
									}
									for (var ii = 0; ii < len; ii++) {
										var param = checkedNodes[ii].name + checkedNodes[ii].id;
										if (str.indexOf(param) == -1) {
											treeObj.checkNode(checkedNodes[ii], false, false);
										}
									}
									treeObj.checkNode(treeObj.getNodeByTId("tree_1"), true, false);
								}
							} else {
								var treeObj = $.fn.zTree.getZTreeObj("tree");
								treeObj.checkAllNodes(false);
							}
						}
					});
					
					var dialog = getJqueryDialog();
					dialog.Container = $("#menuFormWrap");
					dialog.Title = "菜单权限";
					dialog.CloseOperation = "destroy";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 620;
			   		}
					dialog.ButtonJSON = {
						"取消": function() {
							$(this).dialog("close");
						},
						"确定": function() {
							var treeObj = $.fn.zTree.getZTreeObj("tree");
							var checkedNodes = treeObj.getCheckedNodes(true);
							var len = checkedNodes.length;
							var menuIds = [];
							for (var ii = 0; ii < len; ii++) {
								menuIds.push(checkedNodes[ii].id);
							}
							$.ajax({
								cache: true,
								type: "POST",
								url:'roleAction!saveMenu.action',
								dataType:'json',
								traditional :true, 
								data:{'id':obj[0].id,'menuIds':menuIds},
								async: false,
								error: function(request,textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success: function(data) {
									Modal.alert({ msg:'关联菜单成功！', title:'提示', btnok:'确定' }).on(function(e){
										refresh();
									});
								}
							});
							
							$(this).dialog("close");
						}
					};
					dialog.show();
				} else {
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//关联部门
			function setDepart() {
				var obj = $('#dataTable').bootstrapTable("getSelections");
				if(obj != 0){
					$.ajax({
						url:"departmentAction!findDepartByRole.action",
						type:"POST",
						dataType:"json",
						data:{"roleId":obj[0].id},
						async:true,
						error:function(request,textStatus, errorThrown){
							fxShowAjaxError(request, textStatus, errorThrown);
						},
						success:function(data){
							if (data != null && data.result != null) {
								var length = data.result.length;
								var treeObj = $.fn.zTree.getZTreeObj("dtree");
								treeObj.checkAllNodes(true);
								var checkedNodes = treeObj.getCheckedNodes(true);
								var len = checkedNodes.length;
								if(length != len -1) {
									var str = "";
									for (var ii = 0; ii < length; ii++) {
										str += data.result[ii].name + data.result[ii].id + ",";
									}
									for (var ii = 0; ii < len; ii++) {
										var param = checkedNodes[ii].name + checkedNodes[ii].id;
										if (str.indexOf(param) == -1) {
											treeObj.checkNode(checkedNodes[ii], false, false);
										}
									}
									//treeObj.checkNode(treeObj.getNodeByTId("tree_1"), true, false);
								}
							} else {
								var treeObj = $.fn.zTree.getZTreeObj("dtree");
								treeObj.checkAllNodes(false);
							}
						}
					});
					
					var dialog = getJqueryDialog();
					dialog.Container = $("#departFormWrap");
					dialog.Title = "部门权限";
					dialog.CloseOperation = "destroy";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 620;
			   		}
					dialog.ButtonJSON = {
						"取消": function() {
							$(this).dialog("close");
						},
						"确定": function() {
							var treeObj = $.fn.zTree.getZTreeObj("dtree");
							var checkedNodes = treeObj.getCheckedNodes(true);
							var len = checkedNodes.length;
							var departIds = [];
							for (var ii = 0; ii < len; ii++) {
								departIds.push(checkedNodes[ii].id);
							}
							$.ajax({
								cache: true,
								type: "POST",
								url:'roleAction!saveDepart.action',
								dataType:'json',
								traditional :true, 
								data:{'id':obj[0].id,'departIds':departIds},
								async: false,
								error: function(request,textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success: function(data) {
									Modal.alert({ msg:'关联部门成功！', title:'提示', btnok:'确定' }).on(function(e){
										refresh();
									});
								}
							});
							
							$(this).dialog("close");
						}
					};
					dialog.show();
				} else {
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			function clean() {
				$('#nameSearch').val('');
				pageNum = 1;
				pageSize = 10;
			}
			
			function getUserByRole(id){
				var users = "";
				$.ajax({
					type : 'post',
					url : 'userAction!findByRoleId.action',
					dataType : 'json',
					async: false,
					traditional :true, 
					data : {"roleId" : id},
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						users = data.datas;
					}
				});
				return users;
			}
			
			//获取全部用户
			function getAllUser() {
				var allUser = "";
				$.ajax({
					type : 'post',
					url : 'userAction!findAll.action',
					dataType : 'json',
					async: false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						allUser = data.datas;
					}
				});
				return allUser;
			}
			
			function loadUser(allUser, users) {
				if(users != "") {
					var flag;
					$.each(users,function(i,user) {
						$("#select2").prepend('<option value="'+user.id+'">'+user.name+'</option>');
					});
					$.each(allUser,function(i,user) {
						var flag = true;
						$.each(users,function(i,u) {
							if(user.id == u.id) {
								flag = false;
								var treeObj = $.fn.zTree.getZTreeObj("userTree");
								var nodes = treeObj.getNodesByParam("id", user.id, null);
							    treeObj.hideNode(nodes[0]);
							}
						});
						if(flag) {
							$("#select").prepend('<option value="'+user.id+'">'+user.name+'</option>');
						}
					});
				} else {
					$.each(allUser,function(i,user) {
						$("#select").prepend('<option value="'+user.id+'">'+user.name+'</option>');
					}); 
				}
			}
			
			//分配用户角色
			function assignRoles() {
				$("#userName").val('');
				$("#select").hide();
				$("#treeSelect").show();
				var info = dataTable.bootstrapTable("getSelections");
				if(info != 0) {
					if(info[0].active) {
						getTree();
						$("#select").empty();
						$("#select2").empty();
						loadUser(getAllUser(), getUserByRole(info[0].id));
						var dialog = getJqueryDialog();
				   		dialog.Container = $("#userFormWrap");
				   		dialog.Title = "分配角色";
				   		if (!fBrowserRedirect()) {
				   			dialog.Width = 540;
				   		}
				   		dialog.Height = 420;
				   		dialog.CloseOperation = "destroy";
				   		dialog.ButtonJSON = {
							"取消" : function() {
								$(this).dialog("close");
							},
							"确定" : function() {
								var ids=[] ;
								var i=0;
								$("#select2 option").each(function() {
									ids[i]=$(this).val();
									i++;
								});
								$.ajax({
									type : 'get',
									url : 'roleAction!saveUserRole.action',
									dataType : 'json',
									traditional :true, 
									data : {
										"userIds" : ids,
										"id" : info[0].id
									},
									error : function(request, textStatus, errorThrown) {
										//fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										//refresh();
										dataTable.bootstrapTable(('refresh'));
										Modal.alert({ msg:'分配角色成功！', title:'提示', btnok:'确定' }).on(function(e){
											
										});
									}
								}); 
								$(this).dialog("close");
							}
				   		};
				   		dialog.show();
					} else {
						Modal.alert({ msg:'未激活角色无法分配！', title:'提示', btnok:'确定' });
					}
				} else {
					Modal.alert({ msg:'请选择您要分配的角色！', title:'提示', btnok:'确定' });
				}
			}
			
			$("#userName").change(function() {
				var $this = $(this);
				var username = $this.val();
				$.ajax({
					type : 'post',
					url : 'userAction!findList.action',
					dataType : 'json',
					data:{'name':username},
					async: false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var allUser = data.datas;
						$("#select").empty();
						$.each(allUser,function(i,user) {
							var flag = true;
							$("#select2 option").each(function() {
								var $this = $(this);
								if(user.id == $this.val()) {
									flag = false;
								}
							}); 
							if(flag) {
								$("#select").prepend('<option value="'+user.id+'">'+user.name+'</option>');
							}
						});
						if(username && username != "") {
							$("#select").show();
							$("#treeSelect").hide();
						} else {
							$("#select").hide();
							$("#treeSelect").show();
						}
					}
				});
			});
			
			function getTree() {
				$.ajax({
					type : 'post',
					url : 'userAction!getTree.action',
					dataType : 'json',
					async: false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var nodes = data.nodes;
						$.fn.zTree.init($("#userTree"), userSetting, nodes);
						//$.fn.zTree.getZTreeObj("userTree").expandAll(true);
					}
				});
			}
		</script>
	</body>
</html>