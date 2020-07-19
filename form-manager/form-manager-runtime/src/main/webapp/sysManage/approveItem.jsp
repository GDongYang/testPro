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
		<title>审批事项管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,tree,icon" name="p"/>
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
							<div id="searchForm">
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 pull-right">
										<div class="form-group text-right base_options-area">
											<button id="searchBtn" type="button" class="btn btn-sm btn-info" onClick="search()">搜索</button>
											<button type="button" class="btn btn-sm btn-info" onClick="clean()">清空</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div class="row">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="names" name="name" placeholder="事项名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<!-- <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="department">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="department" id="department"></select>
												</div>
											</div> -->
											<!-- /.base_query-group -->
										</div>
										<!-- /.row -->
									</div>
								</div>
							</div>
						</div>
						<h2 class="page-header base_page-header">
							<!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> -->
						</h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua" >
						<div class="box-header with-border base_box-header">
							<h3 class="box-title">
								<i class="fa fa-tag"></i> <span class="base_text-size-15">审批事项管理</span>
							</h3>
							<div class="box-tools pull-right">
<!-- 								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button> -->
<!-- 								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button> -->
<!-- 								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button> -->
<!-- 								<button type="button" class="btn btn-xs btn-info" onClick="updateAssociatedCerts()">配置事项证件</button> -->
								
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap"
									data-toggle="table" data-locale="zh-CN" data-ajax="ajaxRequest"
									data-side-pagination="server" data-striped="true"
									data-single-select="true" data-click-to-select="true"
									data-pagination="true" data-pagination-first-text="首页"
									data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
									data-pagination-last-text="末页"
									data-detail-view="true"
									data-detail-formatter="detailFormatter"
									>
									<thead style="text-align: center;">
										<tr>
											<th data-radio="true"></th>
											<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="name">事项名称</th>
											<!-- <th data-field="code">事项编码</th>
											<th data-field="departmentName">单位名称</th>
											<th data-field="positionName" data-formatter="formatPosition">岗位名称</th> -->
											<th data-field="memo">权利编码</th>
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
				<!-- <input type="hidden" id="postionId" name="id"/> -->
<!-- 				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group"> -->
<!-- 					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">事项名称</label> -->
<!-- 					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area"> -->
<!-- 						<input disabled=true type="text" class="form-control base_input-text" id="name" name="name" placeholder="事项名称" tips-message="请输入事项名称" /> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<!-- /.base_query-group -->
				<!-- <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="departmentId">所属部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select disabled=true class="form-control chosen-select-deselect" chosen-position="true" id="departmentId" name="departmentId" tips-message="请选择部门"></select>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<!-- <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="positionId">岗位名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						data-tips-message="请选择岗位"
						<select disabled=true class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="positionId" name="positionIdS" tips-message="请选择岗位"></select>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="certTempId">证件权限</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="certTempId" name="certTempIdS" tips-message="请选择证件"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
<!-- 				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group"> -->
<!-- 					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">事项描述</label> -->
<!-- 					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">data-tips-message="请选择事项描述" -->
<!-- 						<textarea disabled=true class="form-control base_input-textarea" id="memo" name="memo" placeholder="事项描述"></textarea> -->
<!-- 					</div> -->
<!-- 				</div> -->
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
			var certTemps={};
			
			function idFormatter(value, row) {
				return index++;
			}
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				selectConfig();//下拉框初始化
				
				//bootstrapTable单击事件
				$("#dataTable").on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				dataTable = $('#dataTable');
				dataTable.on('expand-row.bs.table',expandRow)
				//加载部门下拉
				findDepartmentAll();
				
				findCertTemp();
				
				//查询绑定enter
				$('#names').keydown(function(event){
					if(event.keyCode==13){
						$('#searchBtn').click();
					}
				})
			});
			
			
			function expandRow(event,index,row){
				
				//detail的div
				var result = $('#rowDetail'+index);
				//已关联的证件的列表
				result.append($('<ul class="list-group">'));
				//已关联的证件的id
				var tempIds = findRTemp(row.memo);
				tempIds = $.map(tempIds,function(item){return parseInt(item)});
				//选择框，用于添加未关联的的证件
				var $select = $('<select class="form-control" style="display:inline-block;width:75%;border-radius:4px;margin-right:20px">')
				$select.append($('<option>').html('--------------------------------'))
				//将证件添加到已关联列表
				function add(cert){
					var $li = $('<li class="list-group-item" style="display:inline-block;width:25%;border-radius: 4px;">');
					var removeIcon = $('<span style="float:right" class="glyphicon glyphicon-remove" aria-hidden="true"></span>');
					
					//删除已关联的证件
					removeIcon.click(function(){
						var $this = $(this)
						var $parentLi = $this.parent('li');
						removedId = $parentLi.attr('certId');
						$select.children('option[certId='+removedId+']').attr('disabled',false);
						$parentLi.remove();
					})
					result.children('ul').append($li.append(cert.name).attr('certId',cert.id).append(removeIcon));
					if($.inArray(cert.id,tempIds)<0){
						
						tempIds.push(cert.id);
					}
				}
			
				//初始化已关联列表
				for(var i=0;i<tempIds.length;i++){
					var temp = certTemps[tempIds[i]];
					if(!temp)
						continue;
					add(temp);
					
				}
				
				//初始化选择框
				$.each(certTemps,function(i,item){
					var $option = $('<option>').append(item.name).attr('certId',item.id).prop('cert',item)
					if($.inArray(item.id , tempIds)>-1)
						$option.attr('disabled',true);
					
					$select.append($option);
				})
				//绑定选择框事件
				$select.change(function(){
					var $selectedOption = $(this).children(':selected');
					if(!$selectedOption.attr('certId'))
						return
					add($selectedOption.prop('cert'));
					$selectedOption.attr('disabled',true);
				});
				
				
				result.append($select);
				//创建提交按钮
				var $submit = $('<button type="button" class="btn btn-sm btn-info">保存</input>');
				$submit.click(function(){
					
					var param='memo='+row.memo;
					var $lis = result.find('li[certId]');
					$.each($lis,function(i,item){
						param+='&certTempIdS='+$(item).attr('certId');
					
					})
					//请求后台提交数据
					$.ajax({
						cache : true,
						type : "POST",
						url : 'approveItemAction!updateAssociatedCerts.action', 
						dataType : 'json',
						data : param,
						
						error : function(request, textStatus, errorThrown) {
							fxShowAjaxError(request, textStatus, errorThrown);
						},
						success : function(data) {
							Modal.alert({
								msg : '修改成功',
								title : '提示',
								btnok : '确定',
								btncl : '取消'
							});
							refresh();
						}
					});
				})
				result.append($submit)
			}
			
			function detailFormatter(index,row){
				
				return '<div id="rowDetail'+index+'"></div>';
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
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "</option>";
										
								//将result放入certTemps，并将其id作为索引
								certTemps[data.result[i].id]=data.result[i];
							}
							$("#certTempId").append(htmlStr);
							
						}
					}
				});
			}
			
			function findDepartmentAll() {
				$.ajax({
					cache : true,
					type : "get",
					url : 'departmentAction!findTree.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						/* 	console.log(data.departments);
							console.log(data.departments[0].name); */
						if (data.departments != null) {
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.departments.length; i++) {
								htmlStr += "<option value=\""+data.departments[i].id+"\">"
										+ data.departments[i].name
										+ "</option>";
							}
							//console.log(htmlStr);
							$("#department").append(htmlStr);
							$("#departmentId").append(htmlStr);
						}
						//$("#department")
					}
				});
				//绑定下拉框对象
				selectUpdated($("#department"));
			}
			$("#departmentId").on("change", function(e) {
				var techDirec = $("#departmentId").val();
				findPositionAll(techDirec);
			});
			
			//岗位
			function findPositionAll(positionId) {
				$("#positionId").empty();
				$.ajax({
					cache : true,
					type : "get",
					url : 'positionAction!findByDept.action',
					dataType : 'json',
					async : false,
					data : {
						searchDept : positionId
					},
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//console.log(data);
						var htmlStr = "";
						if (data.result != null) {
							htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "</option>";
							}
							//console.log(htmlStr);
						}
						$("#positionId").append(htmlStr);
						//绑定下拉框对象
						selectUpdated($("#positionId"));
					}
				});
			}
			
			function formatActive(val) {
				if (val == 1) {
					return '<font style="color:#0000FF">正常</font>';
				} else {
					return '<font style="color:#FF0000">未激活</font>';
				}
			}
			function formatPosition(value,row,index){
				var values = "";
				$.ajax({
					cache : true,
					type : "POST",
					url : 'approveItemAction!findPositionName.action',
					dataType : 'json',
					data : {
						id : row.id
					},
					async : false,
					error : function(request, textStatus,
							errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						if(data != ""){
							values = data.result;
						}
					}
				});
				return values; 
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
				//console.log(param);
				var datas;
				var items;
				$.ajax({
					type : 'post',
					url : 'approveItemAction!findPaeTable.action?pageNum=' + pageNum
							+ '&pageSize=' + pageSize,
					dataType : 'json',
					cache : false,
					async : true,
					data : param,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						datas = data.page;
						if (datas != null)
							count = datas.count;
						items = datas.items ? datas.items : [];
						params.success({
							total : count,
							rows : items
						});
						params.complete();
						
						$('input').iCheck({
							checkboxClass : 'icheckbox_square-blue',
							radioClass : 'iradio_square-blue',
							increaseArea : '20%' // optional
						});
						//iCheck绑定事件,操作bootstrapTable点击行事件
						drawICheck("dataTable");
						/* $('#dataTable input[type=radio]').on('ifChecked',function(event){
							$(this).closest("tr").addClass("selected");
							$(this).attr("checked", true);
							var dataIndex = $(this).attr("data-index");
							$('#dataTable').bootstrapTable("check",dataIndex);
						});
						$('#dataTable input[type=radio]').on('ifUnchecked',function(event){
							$(this).closest("tr").removeClass("selected");
							$(this).attr("checked", false);
							$('#dataTable').bootstrapTable("uncheckAll");
						}); */
					}
				});
			}
			//查询
			function search() {
				//dataTable.bootstrapTable('selectPage', 1);
				dataTable.bootstrapTable('refresh', {
					queryParams : getParams()
				});
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#names').val("");
				$('#department').val("");
				selectUpdated($("#department"));
			}
			
			
			
			function updateAssociatedCerts() {
				findCertTemp();
				//findDepartmentAll();
				
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					$("#name").val(obj[0].name);
					$("#memo").val(obj[0].memo);
					/* $("#departmentId").val(obj[0].departmentId); */
					/* findPositionAll(obj[0].departmentId); */
					/* var a=[7,13];
					$("#positionId").val(a); */
					/* $("#positionId").val(obj[0].positionId); */
					//查询证件权限
					findRTemp(obj[0].memo);
					//$("#departmentId").append('<option value="'+obj[0].departmentId+'">'+obj[0].departmentName+'</option>');
					var dialog = getJqueryDialog();
					dialog.Container = $("#dataFormWrap");
					dialog.Title = "修改事项证书配置";
					dialog.CloseOperation = "destroy";
					dialog.BeforeClose = function(){
						$('#departmentId').val("");
						//$("#departmentId").empty();
						$("#positionId").empty();
						$("#certTempId").empty();
						tipsRegionHintAddVerify($("#dataFormWrap"));
					};
					//dialog.Open = function(){tipsRegionHintRemoveVerify($("#dataFormWrap"));};
					dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
// 							if (tipsRegionValidator($("#dataForm"))) {
								var dataStr = $('#dataForm').serialize()+"&memo="+obj[0].memo;
								$.ajax({
									cache : true,
									type : "POST",
									url : 'approveItemAction!updateAssociatedCerts.action', 
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus, errorThrown) {
										fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										Modal.alert({
											msg : '修改成功',
											title : '提示',
											btnok : '确定',
											btncl : '取消'
										});
										refresh();
									}
								});
								$(this).dialog("close");
// 							}
						}
					}
					dialog.show();
				} else {
					Modal.alert({
						msg : '请选择要修改的数据',
						title : '提示',
						btnok : '确定',
						btncl : '取消'
					});
				}
				selectUpdated($("#departmentId"));//下拉框变动更新
				selectUpdated($("#positionId"));//下拉框变动更新
				selectUpdated($("#certTempId"));//下拉框变动更新
			}
			function findRPosition(itemId){
				$.ajax({
					cache : true,
					type : "POST",
					url : 'approveItemAction!findPositionId.action',
					dataType : 'json',
					data : {
						id : itemId
					},
					async : false,
					error : function(request, textStatus,
							errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						$("#positionId").val(data.result);
					}
				});
			}
			function findRTemp(itemMemo){
				var result;
				$.ajax({
					cache : true,
					type : "POST",
					url : 'approveItemAction!findRTemp.action',
					dataType : 'json',
					data : {
						memo : itemMemo
					},
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						$("#certTempId").val(data.result);
						result = data.result;
					}
				});
				if(result)
					return result;
				else
					return [];
			}
			
// 		
			function getParams() {
				var name = $("#names").val();
// 				var department = $("#department").val();
				var params = {
					'name' : name
// 					'departmentId' : department
				}
				return params;
			}
		</script>
	</body>
</html>