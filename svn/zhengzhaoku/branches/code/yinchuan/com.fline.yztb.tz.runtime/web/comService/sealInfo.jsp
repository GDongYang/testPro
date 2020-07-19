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
		<title>电子印章管理</title>
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
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >印章名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="nameSearch" name='name' placeholder="印章名称"/>
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
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">电子印章管理</span></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
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
											<th data-field="code">编码</th>
											<th data-field="name">名称</th>
											<th data-field="code" data-formatter="detail">查看</th>
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
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">编码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="code" name="code" placeholder="编码" tips-message="请输入编码"/>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="名称" tips-message="请输入名称" />
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >证件</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect" multiple="multiple" id="certTempId" name="certTempIdS" ></select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >地区</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" multiple="multiple" id="area" name="area" >
							<option value="331000">市本级</option>
							<option value="331002">椒江区</option>
							<option value="331004">路桥区</option>
							<option value="331003">黄岩区</option>
							<option value="331081">温岭市</option>
							<option value="331082">临海市</option>
							<option value="331021">玉环市</option>
							<option value="331022">三门县</option>
							<option value="331023">天台县</option>
							<option value="331024">仙居县</option>
						</select>
					</div>
				</div>
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
				dataTable = $('#dataTable');
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
			});
			
			
			function initFormElement_add() {
				$("#name").val('');
				$("#code").val('');
				$("#area").val('');
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
					url : 'sealInfoAction!findPagination.action',
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
				var id="nameSearch";
				var idval=$("#nameSearch").val();
				checkText(id,idval);
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
			
			//新增
			function add() {
				initFormElement_add();
				findCertTemp();
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#dataFormWrap");
		   		dialog.Title = "新增";
		   		dialog.CloseOperation = "destroy";
		   		dialog.Height = Math.round($(window).height() * 0.75);//设置对话框高度
		   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
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
								url : 'sealInfoAction!create.action',
								dataType : 'json',
								data : dataStr,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'新增成功！', title:'提示', btnok:'确定' }).on(function(e){
										dataTable.bootstrapTable('refresh');
									});
								}
							});
							$(this).dialog("close");
						}
					}
		   		}
		   		selectUpdated($("#area"));
		   		selectUpdated($("#certTempId"));
		   		dialog.show();
			}
			
			//修改
			function update() {
				initFormElement_add();
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					findCertTemp();
					$("#name").val(obj[0].name);
					$("#code").val(obj[0].code);
					findRTemp(obj[0].id);
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
							if (tipsRegionValidator($("#dataForm"))) {
								var dataStr = $('#dataForm').serialize() + "&id=" + obj[0].id;
								$.ajax({
									cache : true,
									type : "POST",
									url : 'sealInfoAction!update.action',
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										Modal.alert({ msg:'修改成功！', title:'提示', btnok:'确定' }).on(function(e){
											dataTable.bootstrapTable('refresh');
										});
									}
								});
								$(this).dialog("close");
							}
						}
			   		}
			   		selectUpdated($("#area"));
			   		selectUpdated($("#certTempId"));
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
						msg:"是否删除？"
					}).on( function (e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'sealInfoAction!remove.action',
								dataType : 'json',
								data : row,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'删除成功！', title:'提示', btnok:'确定' }).on(function(e){
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
			
			function findCertTemp() {
				$.ajax({
					cache : false,
					type : "get",
					url : 'certTempAction!findAll.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						if (data.result != null) {
							$("#certTempId").empty();
							allTemp = data.result;
							var htmlStr = "";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "</option>";
							}
							$("#certTempId").append(htmlStr);
							selectUpdated($("#certTempId"));
						}
					}
				});
			}
			
			function findRTemp(sealId){
				$.ajax({
					cache : false,
					type : "POST",
					url : 'sealInfoAction!findCertSeal.action',
					dataType : 'json',
					data : {
						id : sealId
					},
					async : false,
					error : function(request, textStatus,
							errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						console.log(data)
						$("#area").val(data.areas);
						$("#certTempId").val(data.certs);
					}
				});
			}
			
			//查看
			function detail(value){
				return "<a style=\"cursor:pointer;\" onclick=\"showSeal('"+value+"')\">查看</a>";
			}
			
			//查看
			function showSeal(code){
				var url = "SealViewer.jsp?code="+code;
				window.open(url, "newwindow", "'width="+1000+",height="+800+", toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no");
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