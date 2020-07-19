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
		<title>协查事项统计</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,daterang,select2" name="p"/>
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
									<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 pull-right">
										<div class="form-group text-right base_options-area">
											<button type="button" class="btn btn-sm btn-info" onClick="search()">搜索</button>
											<button type="button" class="btn btn-sm btn-info" onClick="clean()">清空</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
										<div class="row">
											<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="queryDate">查询日期</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="queryDate" name="queryDate" placeholder="查询日期" />
												</div>
											</div>
	<%--										<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="department">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control  chosen-select-deselect " chosen-position="true" name="department" id="department"></select>
												</div>
											</div>--%>

											<div
													class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label
														class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15"
														for="status">盖章状态</label>
												<div
														class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect"
															chosen-position="true" name="status" id="status"><option
															value="4" selected=selected>请选择</option>
														<option value="1">已盖章</option>
														<option value="0">待盖章</option>
														<option value="2">未处理</option>
														<option value="3">已过期</option>
														<option value="5">已办结</option>
														<option value="6">已撤回</option>
													</select>
												</div>
											</div>

										</div>

									</div>
								</div>

							</form>
						</div>
						<h2 class="page-header base_page-header"></h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua">
						<div class="box-header with-border base_box-header">
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">协查事项统计</span></h3>
							<div class="box-tools pull-right">
								<button type="button" id="delBut" class="btn btn-xs btn-info" onClick="remove()" >删除记录</button>
								<button type="button" class="btn btn-xs btn-info" onClick="exportExcel()">导出表格</button>
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="true"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页"
									>
									<thead style="text-align:center;">
										<tr>
											<th data-radio="true"></th>

											<th data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="cert">相关证明材料</th>
											<th data-field="business">协查事项名称</th>
											<th data-field="createDate" data-formatter="formatDate">创建日期</th>
											<th data-field="sfid">查询人身份证</th>
											<th data-field="inquiredName">查询人姓名</th>
											<th data-field="name">办理窗口人员</th>
											<th data-field="applyDept">申请部门</th>
											<th data-field="busicode">盖章部门</th>
											<th data-field="status" data-formatter="formatStatus"
												data-width="60">盖章状态</th>
<%--											<th data-field="number">数量<span id="sum"></span></th>--%>
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
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,daterang" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var queryParam = null;
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
				initDatepicker();//初始化date组件
				dataTable = $('#dataTable');
// 				initDataTable();
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				loadCurrentUser();//加载当前用户
				//iCheck美化
 				$('input').iCheck({
					checkboxClass: 'icheckbox_square-blue',
					radioClass: 'iradio_square-blue',
					increaseArea: '20%' // optional
				});
				
				selectConfig();//下拉框初始化
				
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
				
				// loadDepartment();/*注释加载部门*/
				//loadCert();
				//loadItem();
				var currentUser;
				function loadCurrentUser() {
                    $("#delBut").hide();
					$.ajax({
						url:"sealUncoveredAction!valPrivige.action",
						type:"POST",
						dataType:"json",
						async:false,
						error:function(request,textStatus, errorThrown){
							fxShowAjaxError(request, textStatus, errorThrown);
						},
						success:function(data){
							if(data.status==1){
                                $("#delBut").show();
							}
						}
					});
				}
			});
			function remove() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if (rows != 0) {
					var row = rows[0];
					Modal.confirm({
						msg: "是否删除该协查记录？"
					}).on(function (e) {
						if (e) {
							$.ajax({
								cache: false,
								type: "POST",
								url: 'sealUncoveredAction!remove.action',
								dataType: 'json',
								data: {
									sealUncoveredId: row.id
								},
								async: true,
								error: function (request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success: function (data) {
									Modal.alert({
										msg: data.errorMsg,
										title: '提示',
										btnok: '确定',
										btncl: '取消'
									}).on(function () {
										dataTable.bootstrapTable('selectPage', 1);
									});
								}
							});
						}
					});
				} else {
					Modal.alert({
						msg: '请选择要删除的协查记录',
						title: '提示',
						btnok: '确定',
						btncl: '取消'
					});
				}
			}

			function exportExcel(){
				queryParam.fileName="table";
				queryParam.downloadType="2";
				window.open("sealUncoveredToExcelAction!sealUncoveredToExcel.action?"+$.param(queryParam));
			} 
			/* function exportExcel() {
			  	  $("#searchForm").attr('action', 'businessToExcelAction!businessToExcel.action');
			  	  $("#searchForm").submit();
			} */
			
			function initDatepicker(){
				$("#queryDate").daterangepicker(
						{
							timePicker : true,
							timePicker12Hour : false,
							locale : {
								applyLabel : '确定',
								cancelLabel : '取消',
								fromLabel : '起始',
								toLabel : '结束',
								weekLabel : 'W',
								customRangeLabel : '自定义',
								daysOfWeek : [ "周日", "周一", "周二",
									"周三", "周四", "周五", "周六" ],
								monthNames : [ "一月", "二月", "三月",
									"四月", "五月", "六月", "七月",
									"八月", "九月", "十月", "十一月",
									"十二月" ],
								firstDay : 1
							}
						}, function(start, end, label) {
							// 格式化日期显示框
							$("#queryDate").val(start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD'));
						});
				
			}
		
			
		
			//刷新页面
			function refresh() {
				clean();
				search();
			}
			//对象列表的ajax请求
			
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var param = getParams();
				//保存查询参数，用于导出表格
				if(queryParam==null)
					queryParam = {};
				$.extend(queryParam,param);
				
				
				var datas;
				$.ajaxSettings.traditional=true;
				$.ajax({
					type : 'post',
					url : 'sealUncoveredAction!findAssistItemCount.action?pageNum=' + pageNum +'&pageSize='+pageSize,
					dataType : 'json',
					cache : false,
					async : true,
					data : param,
					traditional: true,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
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
						// $("#sum").html("(共"+data.sum+"条)");
					}
				});
			}
			//查询
			function search() {
				//dataTable.bootstrapTable('selectPage', 1);
				dataTable.bootstrapTable('refresh',{silent:true});
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$("#cert").val('');
				$("#item").val('');
				// $("#department").val('');
				$("#queryDate").val('');
				$("#status").val(4);
				// selectUpdated($("#department"));
				selectUpdated($("#cert"));
				selectUpdated($("#item"));
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
						var htmlstr = "<option value=\"\" ></option>";
						for (var i = 0; i < info.length; i++) {
							htmlstr += "<option value=\""+info[i].id+"\">" + info[i].name + "</option>";
						}
						$("#department").append(htmlstr);
						selectUpdated($("#department"));
					}
				});
			}
			
			function loadCert() {
				$.ajax({
					url : "certTempAction!findAll.action",
					type : "POST",
					data : "",
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//初始化表单
						var info = data.result;
						var htmlstr = "";
						for (var i = 0; i < info.length; i++) {
							htmlstr += "<option value=\""+info[i].code+"\">" + info[i].name + "</option>";
						}
						$("#cert").append(htmlstr);
						selectUpdated($("#cert"));
					}
				});
			}
			
			function loadItem() {
				$.ajax({
					url : "itemAction!findAll.action",
					type : "POST",
					data : "",
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//初始化表单
						var info = data.result;
						var htmlstr = "";
						for (var i = 0; i < info.length; i++) {
							htmlstr += "<option value=\""+info[i].code+"\">" + info[i].name + "</option>";
						}
						$("#item").append(htmlstr);
						selectUpdated($("#item"));
					}
				});
			}
			
			function getParams(){
				var certCode = $("#cert").val();
				var itemCode = $("#item").val();
				// var department = $("#department").val();
				var queryDate = $("#queryDate").val();
				var status=$("#status").val();
				var params={
					'certcode':certCode,
					'itemcode':itemCode,
					// 'department':department,
					'queryDate':queryDate,
					'signstatus':status
				} 
				return params;
			}
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
				}
			}
			function formatStatus(value, row) {
				if (value == 0) {
					return '<font style="color:#f3c312">待盖章</font>';
				} else if (value == 1) {
					return '<font style="color:#00a65a">已盖章</font>';
				} else if (value == 2) {
					return '<font style="color:#FF0000">未处理</font>';
				} else if (value == 3){
					return '<font style="color:#FF0000">已过期</font>';
				} else if (value == 5){
					return '<font style="color:#00a78e">已办结</font>';
				}else if (value == 6) {
					return '<font style="color:#5b5bde">已撤回</font>';
				}
			}



		</script>
	</body>
</html>