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
		<title>业务账号管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,icon" name="p"/>
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
												<label class="col-xs-4 col-sm-5 col-md-5 col-lg-4 text-right base_input-title base_text-size-15" >账号拥有者</label>
												<div class="col-xs-8 col-sm-7 col-md-7 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="nameSearch" name="name" placeholder="账号拥有者" />
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
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">业务账号管理</span></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
								<button type="button" class="btn btn-xs btn-info" onClick="updatePass()">修改密码</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
								<!-- <button type="button" class="btn btn-xs btn-info" onClick="ipAddress()">IP白名单</button> -->
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
											<th data-field="username">用户名</th>
											<th data-field="name">账号拥有者</th>
											<th data-field="code">账号编码</th>
											<th data-field="departmentName">部门</th>
											<th data-field="positionName">岗位</th>
											<!--  <th data-field="ipaddress">IP地址列表</th>-->
											<th data-field="memo">备注</th>
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
				<!--
				<div id="userNameDiv" class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="username">用户名</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="username" name="username" placeholder="用户名" tips-message="请输入用户名"/>
					</div>
				</div>
				-->
				<!--
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="password">密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="password" name="password" placeholder="密码" tips-message="请输入密码" />
					</div>
				</div>
				-->
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">账号拥有者</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="账号拥有者" tips-message="请输入账号拥有者"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="departmentId">部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" name="departmentId" id="departmentId" tips-message="请选择部门"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="positionId">岗位</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" name="positionId" id="positionId" tips-message="请选择岗位"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<!--
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ipaddress">IP地址</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="ipaddress" name="ipaddress" placeholder="IP地址" />
					</div>
				</div>
				-->
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">备注</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="备注"></textarea>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">状态</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="acc_statusRadios1" name="active" value="true" tips-message="请选择状态"/> 正常</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="acc_statusRadios0" name="active" value="false"/> 未激活</label>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<div id="passFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="passForm" name="passForm">
				<input type="hidden" class="form-control" name="Id" id="Id"/>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="oldPass">旧密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="oldPass" name="oldPass" placeholder="旧密码" tips-message="请输入旧密码"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="newPass">新密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="password" class="form-control base_input-text" id="newPass" name="newPass" placeholder="新密码" tips-message="请输入新密码"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="queryPass">确认密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="password" class="form-control base_input-text" id="queryPass" name="queryPass" placeholder="确认密码" tips-identical="[{'field':'newPass','message':'密码不一致'}]"/>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<div id="ipFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="ipForm" name="ipForm">
				<button style="margin-left:10px" type="button" class="btn btn-xs btn-info" onClick="addIP()">新增</button>
				<div id="ipDiv"></div>
			</form>
		</div>	
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,cookie" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			
			function idFormatter(value, row) {
				return index++;
			}
			
			$(function(){
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				selectConfig();//下拉框初始化
				loadDepartment();
				dataTable = $('#dataTable');
				//bootstrapTable单击事件
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
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
				$("#positionId").val('');
				$('#memo').val('');
				//$("#username").val('');
				//$('#ipaddress').val('');
				selectUpdated($("#departmentId"));//下拉框变动更新
				selectUpdated($("#positionId"));
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
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'serviceAccountAction!findPage.action',
					dataType : 'json',
					cache : false,
					async : true,
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
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
						drawICheck('dataTable');
					}
				});
			}
			//查询
			function search() {
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#nameSearch').val("");
				$('#department').val("");
				dataTable.bootstrapTable('refresh');
			}
			
			//新增业务账号
			function add() {
				$("#positionId").empty();
				initFormElement_add();
				//$("#userNameDiv").show();
				$('#acc_statusRadios1').iCheck("check");
				
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#dataFormWrap");
		   		dialog.Title = "新增业务账号";
		   		dialog.CloseOperation = "destroy";
		   		dialog.Height = Math.round($(window).height() * 0.75);//设置对话框高度
		   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
		   		dialog.ButtonJSON = {
	   				"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						if (tipsRegionValidator($("#dataForm"))) {
							//服务器验证
							//var url = "serviceAccountAction!checkUserName.action";
							//var data = {username:$('#username').val()};
							//if (serversValidator(url,data)) {
								var dataStr = $('#dataForm').serialize();
								$.ajax({
									cache : true,
									type : "POST",
									url : 'serviceAccountAction!save.action',
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										Modal.alert({ msg:'新增业务账号成功！', title:'提示', btnok:'确定' }).on(function(e){
											//refresh();
											dataTable.bootstrapTable('refresh');
										});
									}
								});
								$(this).dialog("close");
							//} else {
							//	addTipsHint($("#username"),{msg:"用户名已存在"});
							//}
						}
					}
		   		}
		   		dialog.show();
			}
			
			//修改业务账号
			function update() {
				initFormElement_add();
				//$("#userNameDiv").hide();
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					if (obj[0].active == 1) {
						$("#dataForm input[id='acc_statusRadios0']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios1']").iCheck('check');//选中radio
					} else {
						$("#dataForm input[id='acc_statusRadios1']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios0']").iCheck('check');//选中radio
					}
					$('#departmentId').val(obj[0].departmentId);
					selectUpdated("departmentId");
					loadPosition(obj[0].departmentId,obj[0].positionId);
					
					$('#name').val(obj[0].name);
					$('#memo').val(obj[0].memo);
					//$("#positionId").val(obj[0].positionId);
					//$('#username').val(obj[0].username);
					//$('#ipaddress').val(obj[0].ipaddress);
					//selectUpdated("positionId");
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "修改";
			   		dialog.CloseOperation = "destroy";
			   		dialog.Height = Math.round($(window).height() * 0.75);//设置对话框高度
			   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator(document)) {
								var dataStr = $('#dataForm').serialize()+"&id="+obj[0].id;
								$.ajax({
									cache : true,
									type : "POST",
									url : 'serviceAccountAction!update.action',
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										Modal.alert({ msg:'修改业务账号成功！', title:'提示', btnok:'确定' }).on(function(e){
											refresh();
										});
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
	
			}
			
			//删除业务账号
			function remove() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if(rows != 0) {
					var row = rows[0];
					Modal.confirm({
						msg:"是否删除该业务账号？"
					}).on( function (e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'serviceAccountAction!remove.action',
								dataType : 'json',
								data : row,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'业务账号删除成功！', title:'提示', btnok:'确定' }).on(function(e){
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
						var htmlstr = "<option value=\"\"></option>";
						for (var i = 0; i < info.length; i++) {
							htmlstr += "<option value=\""+info[i].id+"\">"
									+ info[i].name + "</option>";
							htmlStr += "<option value=\""+info[i].id+"\">"
									+ info[i].name + "</option>";
						}
						$("#departmentId").html(htmlstr);
						selectUpdated("departmentId");
					}
	
				});
			}
			
			function loadPosition(departmentId,positionId) {
				$.ajax({
					url : "positionAction!findByDept.action",
					type : "POST",
					data : {"searchDept":departmentId},
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//初始化表单
						var info = data.result;
						var htmlStr = "";
						var htmlstr = "<option value=\"\"></option>";
						if(info) {
							for (var i = 0; i < info.length; i++) {
								htmlstr += "<option value=\""+info[i].id+"\">"
										+ info[i].name + "</option>";
								htmlStr += "<option value=\""+info[i].id+"\">"
										+ info[i].name + "</option>";
							}
						}
						$("#positionId").html(htmlstr);
						$("#positionId").val(positionId);
						selectUpdated("positionId");
					}
				});
			}
			
			$("#departmentId").change(function(){
				loadPosition($("#departmentId").val(),null);
			});
			
			//修改业务账号密码
			function updatePass() {
				$("#passForm input").val("");
				var info = dataTable.bootstrapTable("getSelections");
				if(info != 0) {
					$("#Id").val(info[0].id);
					
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#passFormWrap");
			   		dialog.Title = "修改密码";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 540;
			   		}
			   		dialog.Height = 250;
			   		dialog.CloseOperation = "destroy";
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator($("#passForm"))) {
								//服务器验证
								var url = "serviceAccountAction!checkOldPass.action";
								var data = {id:$("#passForm #Id").val(),oldPass:$('#passForm #oldPass').val()};
								if (serversValidator(url,data)) {
	 								var dataStr = $("#passForm").serialize();
									$.ajax({
										type : 'get',
										url : 'serviceAccountAction!updatePass.action',
										dataType : 'json',
										async : false,
										data : dataStr,
										error : function(request, textStatus, errorThrown) {
											//fxShowAjaxError(request, textStatus, errorThrown);
										},
										success : function(data) {
											Modal.alert({ msg:'业务账号修改密码成功！', title:'提示', btnok:'确定' }).on(function(e){
												refresh();
											});
										}
									});
									$(this).dialog("close");
								} else {
									addTipsHint($("#oldPass"),{msg:"密码错误"});
								}
							}
						}
			   		};
			   		dialog.show();
				} else {
					Modal.alert({ msg:'请选择要修改密码的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//ip白名单
			function ipAddress() {
				$("#ipDiv").html('');
				var info = dataTable.bootstrapTable("getSelections");
				if(info != 0) {
					if(info[0].ipaddress) {
						var html = '';
						var ips = info[0].ipaddress.split(",");
						for(var i=0;i<ips.length;i++) {
							html +='<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">';
							html +='<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ipAdd">ip地址</label>';
							html +='<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">';
							html += '<input type="text" class="form-control base_input-text" name="ipAdd"  value="'+ips[i] +'">';
							html += '</div></div>';
						}
						$("#ipDiv").append(html);
					}else {
						addIP();
					}
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#ipFormWrap");
			   		dialog.Title = "IP白名单";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 540;
			   		}
			   		dialog.Height = 250;
			   		dialog.CloseOperation = "destroy";
			   		dialog.ButtonJSON = {
		   				"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							var ips = '';
							$.each($("input[name='ipAdd']"), function (i) {
								$this = $(this);
								if($this.val()){
									if(i > 0) {
										ips +=",";
									}
									ips +=$this.val() ;
								}
						    });
							//alert(ips)
							if (tipsRegionValidator($("#ipForm"))) {
								$.ajax({
									type:'post',
									url:'serviceAccountAction!updateIP.action',
									data:{'id':info[0].id,'ipaddress':ips},
									dataType:'json',
									error : function(request, textStatus, errorThrown) {
										//fxShowAjaxError(request, textStatus, errorThrown);
									},
									success:function(data) {
										Modal.alert({ msg:'操作成功！', title:'提示', btnok:'确定' }).on(function(e){
											refresh();
										});
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
			}
			
			//新增ip白名单
			function addIP() {
				var html = '';
				html +='<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">';
				html +='<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ipAdd">ip地址</label>';
				html +='<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">';
				html += '<input type="text" class="form-control base_input-text" name="ipAdd">';
				html += '</div></div>';
				$("#ipDiv").append(html);
			}
		</script>
	</body>
</html>