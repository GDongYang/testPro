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
		<title>操作日志查询</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,chosen,date,icon" name="p"/>
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
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="resStartTime">用户名</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="idSearch" name="userName" placeholder="用户名"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="searchDate">日期</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<div class="input-group base_input-group">
														<div class="input-group-addon base_input-groupleft-area">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" class="form-control pull-left base_input-groupright-area" id="searchDate" name='accessDate' data-date-format="yyyy-mm-dd"/>
													</div>
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
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">操作日志查询</span></h3>
							<div class="box-tools pull-right">
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
											<th data-field="resName">资源名称</th>
											<th data-field="resCode">资源编码</th>
											<th data-field="userName" >用户名</th>
											<th data-field="accessDate" data-formatter="formatDate">访问时间</th>
											<th data-field="serverEndpoint" >服务器地址</th>
											<th data-field="remoteEndpoint" >客户端地址</th>
											<th data-field="guid" >唯一编码</th>
											<th data-field="accessType" >访问类型</th>
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
			<jsp:param value="mcustomscrollbar,table,icheck,chosen,datetime,cookie" name="p"/>
		</jsp:include>
		
		<script type="text/javascript">
			var bootstrapValidator;
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				dataTable = $('#dataTable');
				
				//bootstrapTable单击事件
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				$('#searchDate').datetimepicker({
					autoclose:true,
					minView:2,
					language: 'zh-CN'
				});
			});
			
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
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
					url : 'accessLogAction!findPage.action',
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
							rows : datas ? datas : []
						});
						params.complete();
						drawICheck('dataTable');
					}
				});
			}
			
			//查询
			function search() {

				var id="idSearch";
				var idval=$("#idSearch").val();
				checkText(id,idval);
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			function clean() {
				$('#idSearch').val('');
				$('#searchDate').val('');
				pageNum = 1;
				pageSize = 10;
			}
			
			$("#searchDate").click(function() {
				$('#searchDate').datetimepicker('show');
			});
			//验证文本框输入特殊字符
			function checkText(id, text) {//xss攻击特殊字符过滤
				var arr = new Array();
				arr = [  "\"","\;","alert", "eval", "<script>", "<\/script>", "onblur", "onload", "onfocus", "onerror", "onclick", "onMouseOver", "onMouseOut", "onSelect", "onChange", "onSubmit", "console", "href", "<iframe>", "<\/iframe>", "<img>", "<\/img>", "<iframe>", "<\/iframe>", "<video>", "<\/video>", "<canvas>", "<\/canvas>", "<label>", "<\/label>", "<span>", "<\/span>", "document", "location", "javascript"];
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