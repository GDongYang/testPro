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
		<title>岗位管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,chosen,dialog,icon,tree" name="p"/>
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-blue">
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
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="nameSearch">岗位名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="nameSearch" name="name" placeholder="岗位名称" />
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="department">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="department" id="department"><option value=""></option></select>
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
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">岗位管理</span></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
								<button type="button" class="btn btn-xs btn-info" onClick="item()">事项</button>
								<!-- <button type="button" class="btn btn-xs btn-info" onClick="assign()">分配岗位</button> -->
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
											<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="name">职务</th>
											<th data-field="code">编码</th>
											<th data-field="departmentName">所属单位</th>
											<th data-field="memo">岗位说明</th>
											<th data-field="active" data-formatter="formatActive">状态</th>
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
				<input type="hidden" id="postionId" name="id" value="0"/>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">岗位名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="岗位名称" tips-message="请输入岗位名称"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="departmentId">所属部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="departmentId" name="departmentId" placeholder="所属部门" tips-message="请输入所属部门"></select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >事项名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="itemCodes" name="itemCodes"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">岗位描述</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="岗位描述"></textarea>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group" style="display: none">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">岗位状态</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="acc_statusRadios1" name="active" value="true" tips-message="请选择岗位状态"/> 正常</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="acc_statusRadios0" name="active" value="false"/> 未激活</label>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="type">综合窗口</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="type1" name="type" value="1" /> 是</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="type0" name="type" value="0"/>否</label>
					</div>
				</div>
			</form>
		</div>
		
		<div id="userFormWrap" class="base_hidden">
			<table class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<tr>
					<td>
						<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="select">未分配：</label>
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
		<div id="detailWrap" class="base_hidden">
			<div class="box-body" >
				<div class="form-group">
					<table id="detailTable" class="box base_tablewrap"
						data-toggle="table" data-locale="zh-CN" data-ajax="ajaxRequestDetail"
						data-side-pagination="server" data-striped="true"
						data-single-select="true" data-click-to-select="true"
						data-pagination="false">
						<thead style="text-align: center;">
							<tr>
								<th data-field="name">事项名称<span id="sum"></span></th>
								<th data-field="code">权利编码</th>
								<th data-field="innerCode">唯一编码</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>	
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,tree" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var userSetting = {
					data: {
						simpleData: {
							enable: true
						}
					},
				};
			
			function idFormatter(value, row) {
				return index++;
			}
			
			$(function(){
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				
				//iCheck美化
 				$('input').iCheck({
					checkboxClass: 'icheckbox_square-blue',
					radioClass: 'iradio_square-blue',
					increaseArea: '20%' // optional
				});
				
				selectConfig();//下拉框初始化
				
				loadDepartment();
				//findAllItems();
				dataTable = $('#dataTable');
				
				//bootstrapTable单击事件
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				//iCheck绑定事件,操作bootstrapTable点击行事件
				$('#dataTable input[type=radio]').on('ifChecked',function(event){
					$(this).closest("tr").addClass("selected");
					$(this).attr("checked", true);
					var dataIndex = $(this).attr("data-index");
					dataTable.bootstrapTable("check",dataIndex);
				});
				$('#dataTable input[type=radio]').on('ifUnchecked',function(event){
					$(this).closest("tr").removeClass("selected");
					$(this).attr("checked", false);
					dataTable.bootstrapTable("uncheckAll");
				});
				
				$('#dataForm input[type=radio]').on('ifChecked',function(event){
					$(this).attr("checked", true);
				});
				$('#dataForm input[type=radio]').on('ifUnchecked',function(event){
					$(this).attr("checked", false);
				});
				
				$("#departmentId").change(function (){
					var $this = $(this);
					findItemByDept($this.val(),0);
				});
			});
			
			function formatActive(val) {
				if (val == 1) {
					return '<font style="color:#0000FF">正常</font>';
				} else {
					return '<font style="color:#FF0000">未激活</font>';
				}
			}
			
			function initFormElement_add() {
				$("#name").val('');
				$("#departmentId").val('');
				$('#memo').val('');
			}
			//刷新页面
			function refresh() {
				clean();
				search();
			}
			//对象列表的ajax请求
			var todoList;
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var param = getParams();
				$.ajax({
					type : 'post',
					url : 'positionAction!findPage.action?pageNum=' + pageNum +'&pageSize='+pageSize,
					dataType : 'json',
					cache : false,
					async : true,
					data : param,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//console.log(data);
						todoList = data.rows;
						if (data.total == 0) {
							params.success({
								total : 0,
								rows : []
							});
						} else {
							params.success({
								total : data.total,
								rows : data.rows
							});
						}
						params.complete();
						drawICheck("dataTable");
					}
				});
			}
			//查询
			function search() {
				//dataTable.bootstrapTable('selectPage', 1);
				var id="nameSearch";
				var idval=$("#nameSearch").val();
				checkText(id,idval);
				dataTable.bootstrapTable('refresh', {
					queryParams : getParams()
				});
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#nameSearch').val("");
				$('#department').val("");
				selectUpdated($("#department"));
				dataTable.bootstrapTable('refresh');
			}
			
			//新增岗位
			function add() {
				initFormElement_add();
				$("#itemCodes").val('');
				$('#acc_statusRadios1').iCheck("check");
				$('#type1').iCheck("check");
				
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#dataFormWrap");
		   		dialog.Title = "岗位新增";
		   		dialog.CloseOperation = "destroy";
		   		dialog.ButtonJSON = {
	   				"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						if (tipsRegionValidator($("#dataForm"))) {
							var dataStr = $('#dataForm').serialize();
							$.ajax({
								cache : true,
								type : "POST",
								url : 'positionAction!save.action',
								dataType : 'json',
								data : dataStr,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'新增岗位成功！', title:'提示', btnok:'确定' }).on(function(e){
										//refresh();
									});
								}
							});
							$(this).dialog("close");
						}
					}
		   		}
		   		dialog.show();
		   		
		   		selectUpdated($("#departmentId"));//下拉框变动更新
		   		selectUpdated($("#itemCodes"));//下拉框变动更新
			}
			
			//修改岗位
			function update() {
 				initFormElement_add();
				var obj = dataTable.bootstrapTable("getSelections");
				if(obj != 0) {
					if (obj[0].active == 1) {
						$("#dataForm input[id='acc_statusRadios0']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios1']").iCheck('check');//选中radio
					} else {
						$("#dataForm input[id='acc_statusRadios1']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios0']").iCheck('check');//选中radio
					}
					if (obj[0].type == 1) {
						$("#dataForm input[id='type0']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='type1']").iCheck('check');//选中radio
					} else {
						$("#dataForm input[id='type1']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='type0']").iCheck('check');//选中radio
					}
					$('#postionId').val(obj[0].id);
					$('#name').val(obj[0].name);
					$('#departmentId').val(obj[0].departmentId);
					findItemByDept(obj[0].departmentId,obj[0].type);
					$('#memo').val(obj[0].memo);
					findRPosByCode(obj[0].id);
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "岗位修改";
			   		dialog.CloseOperation = "destroy";
			   		dialog.ButtonJSON = {
		   				"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator(document)) {
								var dataStr = $('#dataForm').serialize();
								$.ajax({
									cache : true,
									type : "POST",
									url : 'positionAction!update.action',
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										//fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										if(data.resultCode==0) {
											Modal.alert({msg: '修改岗位成功！', title: '提示', btnok: '确定'}).on(function (e) {
												//refresh();
											});
										}else if(data.resultCode==-1){
											Modal.alert({msg: data.resultMsg, title: '提示', btnok: '确定'}).on(function (e) {
												//refresh();
											});
										}
									}
								});
								$(this).dialog("close");
							}
						}
			   		};
					dialog.show();
				} else {
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
				
				selectUpdated($("#departmentId"));//下拉框变动更新
				selectUpdated($("#itemCodes"));//下拉框变动更新
				$("#itemCodes").next().find(".chosen-choices").css({"max-height":"328px","overflow":"auto"});
			}
			
			//删除岗位
			function remove() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if(rows != 0) {
					var row = rows[0];
					delete row[0];
					//alert(JSON.stringify(row));
					Modal.confirm({
						msg:"是否删除该岗位？"
					}).on( function (e) {
						if (e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'positionAction!remove.action',
								dataType : 'json',
								data : row,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									if(data.resultCode==0) {
										Modal.alert({ msg:'岗位删除成功！', title:'提示', btnok:'确定' }).on(function(e){
											refresh();
										});
									}else if(data.resultCode==-1){
										Modal.alert({msg: data.resultMsg, title: '提示', btnok: '确定'}).on(function (e) {
											//refresh();
										});
									}

								}
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要删除的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			function loadDepartment() {
				$.ajax({
					url : "departmentAction!findList.action",
					type : "POST",
					data : "",
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//初始化表单
						var info = data.departments;
						var htmlStr = "";
						var htmlstr = "";
						for (var i = 0; i < info.length; i++) {
							htmlstr += "<option value=\""+info[i].id+"\">" + info[i].name + "</option>";
							htmlStr += "<option value=\""+info[i].id+"\">" + info[i].name + "</option>";
						}
						$("#department").append(htmlstr);
						$("#departmentId").append(htmlStr);
						selectUpdated($("#department"));
					}
				});
			}
			
			function getParams(){
				var searchName = $("#nameSearch").val();
				var searchDept = $("#department").val();
				var params={
					'searchName':searchName,
					'searchDept':searchDept
				}
				return params;
			}
			
			function getUserByPosition(id){
				var users = "";
				$.ajax({
					type : 'post',
					url : 'userAction!findList.action',
					dataType : 'json',
					async: false,
					traditional :true, 
					data : {"position" : id},
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						users = data.datas;
					}
				});
				return users;
			}
			
			//获取未分配岗位户
			function getAllUser() {
				var allUser = "";
				$.ajax({
					type : 'post',
					url : 'userAction!findList.action',
					dataType : 'json',
					data : {"position" : 0},
					async: false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						allUser = data.datas;
						console.log(allUser);
					}
				});
				return allUser;
			}
			
			function loadUser(allUser, users) {
				var treeObj = $.fn.zTree.getZTreeObj("userTree");
				var nodes = treeObj.getNodesByParam("isParent", false);
				treeObj.hideNodes(nodes);
				$.each(allUser,function(i,user) {
					$("#select").prepend('<option value="'+user.id+'">'+user.name+'</option>');
					var nodes = treeObj.getNodesByParam("id", user.id, null);
					treeObj.showNode(nodes[0]);
				});
				$.each(users,function(i,user) {
					$("#select2").prepend('<option value="'+user.id+'">'+user.name+'</option>');
				});
				
			}
			
			//分配岗位
			function assign() {
				$("#userName").val('');
				$("#select").hide();
				$("#treeSelect").show();
				var info = dataTable.bootstrapTable("getSelections");
				if(info != 0) {
					if(info[0].active) {
						getTree();
						$("#select").empty();
						$("#select2").empty();
						loadUser(getAllUser(), getUserByPosition(info[0].id));
						var dialog = getJqueryDialog();
				   		dialog.Container = $("#userFormWrap");
				   		dialog.Title = "分配岗位";
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
									url : 'userAction!assignPosition.action',
									dataType : 'json',
									traditional :true, 
									data : {
										"ids" : ids,
										"position" : info[0].id
									},
									error : function(request, textStatus, errorThrown) {
										//fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										//refresh();
										dataTable.bootstrapTable(('refresh'));
										Modal.alert({ msg:'分配岗位成功！', title:'提示', btnok:'确定' }).on(function(e){
											
										});
									}
								}); 
								$(this).dialog("close");
							}
				   		};
				   		dialog.show();
					} else {
						Modal.alert({ msg:'未激活岗位无法分配！', title:'提示', btnok:'确定' });
					}
				} else {
					Modal.alert({ msg:'请选择您要分配的岗位！', title:'提示', btnok:'确定' });
				}
			}
			
			$("#userName").change(function() {
				var $this = $(this);
				var username = $this.val();
				var info = dataTable.bootstrapTable("getSelections");
				$.ajax({
					type : 'post',
					url : 'userAction!findList.action',
					dataType : 'json',
					data:{'name':username,"positionOr" : info[0].id},
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
			
			//查询所有审批事项
			function findAllItems() {
				$.ajax({
					cache : true,
					type : "get",
					url : 'itemAction!findAll.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var htmlStr = "";
						if (data.result != null) {
							htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "(" + data.result[i].departmentName + ")(" + data.result[i].code + ")" + "</option>";
							}
						}
						$("#itemCodes").append(htmlStr);
						selectUpdated($("#itemCodes"));
					}
				});
			}
			
			function findRPosByCode(positionId){
				$.ajax({
					cache : false,
					type : "POST",
					url : 'positionAction!findItems.action',
					dataType : 'json',
					data : {'id' : positionId},
					async : false,
					error : function(request, textStatus,errorThrown) {
						fxShowAjaxError(request, textStatus,errorThrown);
					},
					success : function(data) {
						$("#itemCodes").val(data.result);
					}
				});
			}
			
			function findItemByDept(departmentId,type) {
				$("#itemCodes").empty();
				$.ajax({
					cache : true,
					type : "get",
					url : 'itemAction!findList.action',
					data:{'departmentId':departmentId,'type':type},
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var htmlStr = "";
						if (data.result != null) {
							htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "(" + data.result[i].departmentName + ")(" + data.result[i].code + ")" + "</option>";
							}
						}
						$("#itemCodes").append(htmlStr);
						selectUpdated($("#itemCodes"));
					}
				});
				selectUpdated($("#itemCodes"));
			}
			
			//事项详情列表
			function item() {
				$('#detailTable').bootstrapTable('load',{total : 0,rows : []});
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if(rows == 0) {
					alert('请选择岗位！');
					//Modal.alert({ msg:'请选择事项！', title:'提示', btnok:'确定' });
					return ;
				}
				$('#detailTable').bootstrapTable('refresh');
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#detailWrap");
		   		dialog.Title = "事项详情列表";
		   		dialog.CloseOperation = "destroy";
		   		if (!fBrowserRedirect()) {
		   			dialog.Width = 840;
		   		}
		   		dialog.Height = Math.round($(window).height() * 0.6);//设置对话框高度
		   		dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						$(this).dialog("close");
					}
				};
		   		dialog.show();
			}
			
			function ajaxRequestDetail(params) {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if(rows == 0) {
					return ;
				}
				$.ajax({
					type : "POST",
					url : 'itemAction!findByPosition.action',
					dataType : 'json',
					data : {'positionId':rows[0].id},
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						if(data !=null && data.items != null) {
							var result = data.items;
							params.success({
								total : result.length,
								rows : result
							});
						} else {
							params.success({
								total : 0,
								rows : []
							});
						}
						$("#sum").html("(数量:"+result.length+")");
						params.complete();
						
					}
				});
			}
			//验证文本框输入特殊字符
			function checkText(id, text) {//xss攻击特殊字符过滤
				var arr = new Array();
				arr = [ "\"","\;","alert", "eval", "<script>", "<\/script>", "onblur", "onload", "onfocus", "onerror", "onclick", "onMouseOver", "onMouseOut", "onSelect", "onChange", "onSubmit", "console", "href", "<iframe>", "<\/iframe>", "<img>", "<\/img>", "<iframe>", "<\/iframe>", "<video>", "<\/video>", "<canvas>", "<\/canvas>", "<label>", "<\/label>", "<span>", "<\/span>", "document", "location", "javascript"];
				$.each(arr, function (index, value) {
					var result=text.indexOf(value);
					if (result!= -1) {
						//输入信息包含恶意字符
						Modal.alert({
							msg: '包含恶意字符,请重新输入',
							title: '提示',
							btnok: '确定'
						});
						dataTable.bootstrapTable('selectPage', 1);
						$('#' + id).val("");
						$('#' + id).focus();
						return false;
					}
				});
			}
		</script>
	</body>
</html>