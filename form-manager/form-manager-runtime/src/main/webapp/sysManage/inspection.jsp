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
		<title>巡检日志</title>
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
						<h2 class="page-header base_page-header"><!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> --></h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua">
						<div class="box-header with-border base_box-header">
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">巡检日志</span></h3>
							<div class="box-tools pull-right">
								<div id="toolbar" style="float: left">
									<button type="button" class="btn btn-xs btn-info" onClick="detail()">详情</button>
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
											<th data-field="createDate" data-formatter="formatDate">时间</th>
											<th data-field="status" data-formatter="formatStatus">结果</th>
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
						<th data-field="certName">证件模板名称</th>
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
			
			function formatStatus(val) {
				if(val == 1) {
					return '<font style="color:#0000FF">正常</font>';
				} else {
					return '<font style="color:#FF0000">异常</font>';
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
			
			//ajax请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'businessAction!findPage.action?type=4',
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
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			function clean() {
				$('#cernoSearch').val('');
				pageNum = 1;
				pageSize = 10;
			}
			
		</script>
	</body>
</html>