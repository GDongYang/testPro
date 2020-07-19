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
		<title>问题反馈管理</title>
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
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >姓名</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="nameSearch" name='name' placeholder="姓名"/>
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
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">用户反馈管理</span></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove1()">删除</button>
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="tablewrap" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="true"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页">
									<thead style="text-align:center;">
										<tr>
											<th data-radio="true"></th>
											<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="name">姓名</th>
											<th data-field="cerno">身份证号</th>
											<th data-field="department">区域</th>
											<th data-field="certTemp">模板</th>
											<th data-field="bewrite">问题描述</th>
											
											
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
			<input type="hidden" id="idKey" name="idKey"/>
			
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">姓名</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="名称" tips-message="请输入姓名" />
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="cerno">身份证号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="cerno" name="cerno" placeholder="身份证号" tips-message="请输入身份证号"/>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >区域</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect"  id="departmentId" name="departmentId" >
							<option value=""></option>
							<option value=12>杭州市</option>
							<option value=16915>绍兴市</option>
							<option value="20">嘉兴市</option>
							<option value="532">丽水市</option>
							<option value="34017">台州市</option>
						
						</select>
					</div>
				</div>
				
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >证件</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect"  id="certTempId" name="certTempId" >
							
						
						</select>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >问题描述</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect"  id="bewrite" name="bewrite" >
							<option value=""></option>
							<option value="数据缺失">数据缺失</option>
							<option value="数据未更新">数据未更新</option>
							
						
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
				//bootstrapTable单击事件
				$("#tablewrap").on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				$("#departmentId").change(function(){
					var choose = $("#departmentId").val();//
					console.log(choose)
					$.ajax({
						type : 'post',
						url : 'questionAction!findCerta.action',
						dataType : 'json',
						cache : false,
						async : true,
						data : {
							quid:choose
						},
						error : function(request, textStatus, errorThrown) {
							//fxShowAjaxError(request, textStatus, errorThrown);
						},
						success : function(data) {
							$("#certTempId").empty();
							if (data.page.items != null) {
								//$("#certTempId").empty();
								//console.log("find return data is " + JSON.stringify(data));
								allTemp = data.page;
								var htmlStr = "<option value=\"\"></option>";
								for (var i = 0; i < data.page.items.length; i++) {
									htmlStr += "<option value=\""+data.page.items[i].id+"\">"
											+ data.page.items[i].name + "</option>";
								}
								$("#certTempId").append(htmlStr);
							}
							selectUpdated($("#certTempId"));
						}
					});
				});
				
				
			});
			
			
			function initFormElement_add() {
				$("#name").val('');
				$("#cerno").val('');
				$("#departmentId").val('');
				$("#certTempId").val('');
				$("#bewrite").val('');
			}
			
			//刷新页面
			function refresh() {
				clean();
				search();
			}
			
			//查询
			function search() {
				$("#tablewrap").bootstrapTable('selectPage',1);
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
					url : 'questionAction!findPagination.action',
					dataType : 'json',
					cache : false,
					async : true,
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						
							datas = data.page;  
							if (datas != null)
								count = datas.count;
							list = datas.items ? datas.items : [];
							params.success({
								total : count,
								rows : list
							});
							params.complete();
							//监听及渲染ICheck
							drawICheck("tablewrap");
						
					}
				});
			}
			
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#nameSearch').val("");
				//$('#department').val("");
				$("#tablewrap").bootstrapTable('selectPage',1);
			}
			
			
			
			//新增
			function add() {
				initFormElement_add();
				//findCertTemp();
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#dataFormWrap");
		   		dialog.Title = "新增";
		   		dialog.CloseOperation = "destroy";
		   		dialog.Height = Math.round($(window).height() * 0.5);//设置对话框高度
		   		dialog.Width = Math.round($(window).width() * 0.5);
		   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
		   		dialog.ButtonJSON = {
	   				"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						
						if (tipsRegionValidator($("#dataForm"))) {
							//assignment();
							var dataStr = $('#dataForm').serialize();
							
							console.log(dataStr)
							$.ajax({
								cache : true,
								type : "POST",
								url : 'questionAction!create.action',
								dataType : 'json',
								data : dataStr,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'新增成功！', title:'提示', btnok:'确定' }).on(function(e){
										$("#tablewrap").bootstrapTable('refresh');
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
				var obj = $("#tablewrap").bootstrapTable("getSelections");
				console.log(obj);
				if (obj.length == 1) {
					findCertTemp();
					$("#name").val(obj[0].name);
					$("#cerno").val(obj[0].cerno);
					$("#departmentId").val(obj[0].departmentId);
					$("#certTempId").val(obj[0].certTempId);
					$("#bewrite").val(obj[0].bewrite);
					//findRTemp(obj[0].id);
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "修改";
			   		dialog.CloseOperation = "destroy";
			   		dialog.Height = Math.round($(window).height() * 0.5);//设置对话框高度
			   		dialog.Width = Math.round($(window).width() * 0.5);
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
									url : 'questionAction!update.action',
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										Modal.alert({ msg:'修改成功！', title:'提示', btnok:'确定' }).on(function(e){
											$("#tablewrap").bootstrapTable('refresh');
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
			function remove1() {
				var rows = $("#tablewrap").bootstrapTable("getSelections");
				if(rows != 0) {
					var row = rows[0];
					var id= row.id;
					//console.log(row);
					Modal.confirm({
						msg:"是否删除？"
					}).on( function (e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'questionAction!remove.action',
								dataType : 'json',
								data : {
									id:id
								},
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'删除成功！', title:'提示', btnok:'确定' }).on(function(e){
										//refresh();
										$("#tablewrap").bootstrapTable('refresh');
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
			
			
			

		</script>
	</body>
</html>