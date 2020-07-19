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
		<title>盖章日志</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
	</head>
	<body class="base_background-EEF6FB">
		<div class="wrapper">			
			<div class="tableBox">
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>盖章日志</span>
					</h3>
				</div>
				<div id="tempDetailWrap" class="tableCont">
					<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="true"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-radio="true"></th>
								<th data-field="sealName">印章名称</th>
								<th data-field="deptName">调用部门</th>
								<th data-field="username">操作人员</th>
								<th data-field="itemName" >事项名称</th>
								<th data-field="certName" >证件名称</th>
								<th data-field="cerNo" >查询身份证号</th>
								<th data-field="createDate" data-formatter="formatDate">盖章时间</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>					
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table" name="p"/>
		</jsp:include>		
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
				}
			}
			//ajax请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = 'pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'sealLogAction!findPage.action',
					data:dataStr,
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var datas = data ? data.rows : [];
						var count = data ? data.total : 0;
						params.success({
							total : count,
							rows : datas ? datas : []
						});
					}
				});
			}
		</script>
	</body>
</html>