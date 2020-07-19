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
		<title>业务日志</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,daterang" name="p"/>
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
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="ItemName">事项名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="cernoSearch" name='ItemName' placeholder="事项名称"/>
												</div>
											</div>
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="queryDate">查询日期</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="queryDate" name="queryDate" placeholder="查询日期" />
												</div>
											</div>
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="department">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="departmentId" id="departmentId"><option value="0"></option></select>
												</div>
											</div>
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-3 col-sm-3 col-md-3 col-lg-3 text-right base_input-title base_text-size-15" for="itemCreateType">事项类型</label>
												<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="itemCreateType" id="itemCreateType">
														<option value="0">请选择</option>
														<option value="1">一证事项</option>
														<option value="2">无证明共享</option>
														<option value="4">无证明综合</option>
														<option value="5">掌上办</option>
													</select>
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
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">业务日志</span></h3>
							<div class="box-tools pull-right">
								<div id="toolbar" style="float: left">
									<button id="deleteTest" type="button" class="btn btn-xs btn-info" style="display:none;" onClick="deleteTest()">删除测试数据</button>
									<button type="button" class="btn btn-xs btn-info" onClick="detail()">详情</button>
									<button type="button" class="btn btn-xs btn-info" onClick="exportExcel()">导出表格</button>
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
											<th data-field="itemCode">事项编码</th>
											<th data-field="itemName">事项名称</th>
											<th data-field="cerName">被查询人姓名</th>
											<th data-field="cerno">身份证号码</th>
											<!-- <th data-field="status" data-formatter="formatActive">状态</th> -->
											<th data-field="departmentName">办事部门</th>
											<th data-field="accountName" data-formatter="formatUser">办事人员</th>
											<th data-field="accessIP">访问IP</th>
											<th data-field="createDate" data-formatter="formatDate">创建时间</th>
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
		
		<div id="businessDetailWrap" class="base_hidden">
			<table id="tableDetail" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
				data-ajax="ajaxRequestItem" data-side-pagination="server"
				data-striped="true" data-single-select="true"
				data-click-to-select="true" data-pagination="true"
				data-pagination-first-text="首页" data-pagination-pre-text="上一页"
				data-pagination-next-text="下一页" data-pagination-last-text="末页">
				<thead style="text-align:center;">
					<tr>
						<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
						<!-- <th data-field="code">编码</th> -->
						<th data-field="certCode">证件模板编码</th>
						<th data-field="certName">证件模板名称</th>
						<th data-field="cerno">身份证</th>
					    <th data-field="status" data-formatter="statusFormatter">状态</th>
					    <th data-field="timeConsuming">耗时</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div id="reasonWrap" class="base_hidden"></div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,cookie,daterang" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			var index;
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				dataTable = $('#dataTable');
				selectConfig();//下拉框初始化
				initDatepicker();//初始化date组件
				loadDepart();
				//bootstrapTable单击事件
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				//双击事件
				$('#tableDetail').on('dbl-click-row.bs.table', function (row, $element, field) {
					if($element.status == 2){
						reason($element.memo);
					}
				});
				$("#cernoSearch").keydown(function(e){
					if(e.keyCode == 36){
						$("#deleteTest").toggle();
					}
				});
			});
			
			//刷新页面
			function refresh() {
				search();
			}
			
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
				}
			}
			
			function formatActive(val) {
				if(val == 1) {
					return '<font style="color:#0000FF">成功</font>';
				}
				if(val == 0){
					return '<font style="color:#00EC00">无证件信息</font>';
				}
				else {
					return '<font style="color:#FF0000">失败</font>';
				}
			}
			
			function formatUser(val,row) {
				if(val != null) {
					return val;
				} else if(row.userName != null) {
					return row.userName;
				} else {
					return "-";
				}
			}
			
			function idFormatter(value, row) {
				return index++;
			}
			
			//状态statusFormatter
			function statusFormatter(value,row){
				if(value == 1){
					return '<font style="color:#0000FF">成功</font>';
				}
				if(value == 0){
					return '<font style="color:#00EC00">无证件信息</font>';
				}
				else{
					return "<a style=\"color:#FF0000;cursor:pointer;\" onclick=\"reason('"+row.memo+"');\">失败</a>";
				}
			}
			
			function reason(memo) {
				Modal.alert({
					msg : memo,
					title : '失败原因',
					btnok : '确定',
				});
			}
			
			function initDatepicker(){
				$("#queryDate").daterangepicker(
						{
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
						});
			}
			
			//ajax请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'businessAction!findPage.action',
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
			
			//ajax请求
			function ajaxRequestItem(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
			    index = params.data.offset + 1;
			    var obj = $('#dataTable').bootstrapTable("getAllSelections");
			    var businessId = "";
			    if(obj.length == 1) {
			    	businessId = obj[0].id;
			    } else {
			        return;
				}
				$.ajax({
					type : 'post',
					url : 'businessAction!findItemPage.action',
					dataType : 'json',
					data:{'pageNum':pageNum,'pageSize':pageSize,'businessId':businessId},
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
					}
				});
			}
			
			function detail() {
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					$("#tableDetail").bootstrapTable('refresh');
					var dialog = getJqueryDialog();
					dialog.Container = $("#businessDetailWrap");
					dialog.Title = "详情";
					if (!fBrowserRedirect()) {
						dialog.Width = "720";
					}
					dialog.Height = "480";
					dialog.CloseOperation = "destroy";
					dialog.show();
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要查看的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//查询
			function search() {
				var id="cernoSearch";
				var idval=$("#cernoSearch").val();
				checkText(id,idval);
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			function clean() {
				$('#cernoSearch').val('');
				pageNum = 1;
				pageSize = 10;
			}
			
			function loadDepart() {
				$.ajax({
					type : 'POST',
					url : 'departmentAction!findList.action',
					dataType : 'json',
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						$.each(data.departments,function(i,org){
							$("#departmentId").append('<option value="'+org.id+'">'+org.name+'</option>');
						});
						selectUpdated($("#departmentId"));//下拉框变动更新
					}
				});
			}
			
			
			function deleteTest() {
				Modal.confirm({
					msg: "是否删除测试数据？"
				}).on( function(e) {
					if(e) {
						$.ajax({
							type : 'POST',
							url : 'businessAction!deleteTest.action',
							dataType : 'json',
							error : function(request, textStatus, errorThrown) {
								//fxShowAjaxError(request, textStatus, errorThrown);
							},
							success : function(data) {
								alert(data.resultMsg);
								search();
							}
						});
					}
				})
			}
			
			 function exportExcel(){
				 //console.log($("#searchForm").serialize())
					window.open("businessToExcelAction!businessToExcel.action?downloadType=3&" + $("#searchForm").serialize());
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