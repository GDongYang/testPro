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
		<title>资源下载</title>
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
					
					
					<div name="boxSkin" class="box base_box-area-aqua">
						<div class="box-header with-border base_box-header">
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">资源下载</span></h3>
							<div class="box-tools pull-right">
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN">
								<thead style="text-align:center;">
									<tr>
										<th >资源名称</th>
										<th >操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td >杭州一证通办系统窗口人员操作手册</td>
										<td ><a href="../resources/杭州一证通办系统窗口人员操作手册.pdf" download="杭州一证通办系统窗口人员操作手册.pdf">下载</a></td>
									</tr>
									<tr>
										<td >杭州一证通办系统工作手册</td>
										<td ><a href="../resources/杭州一证通办系统工作手册.pdf" download="杭州一证通办系统工作手册.pdf">下载</a></td>
									</tr>
									<tr>
										<td >华视电子CVR100U读卡器驱动</td>
										<td ><a href="../resources/CVRDLL.exe">下载</a></td>
									</tr>
									<tr>
										<td >浙江CA数字证书客户端政务版</td>
										<td ><a href="../resources/ZJCA ZhengWu CMT V.1.0.5.0.zip">下载</a></td>
									</tr>
									<tr>
										<td >精伦电子身份证阅读控件</td>
										<td ><a href="../resources/SetupOCX.exe">下载</a></td>
									</tr>
									<tr>
										<td >德卡T10N安装包</td>
										<td ><a href="../resources/T10N.zip">下载</a></td>
									</tr>
									<tr>
										<td >IE8</td>
										<td ><a href="../resources/IE8-WindowsVista-x86_Setup.zip">下载</a></td>
									</tr>
									<tr>
										<td >360极速浏览器</td>
										<td ><a href="../resources/360cse_official.exe">下载</a></td>
									</tr>
									<tr>
										<td >明泰读卡器控件</td>
										<td ><a href="../resources/MingTai.zip">下载</a></td>
									</tr>
									<tr>
										<td >德卡社保卡控件</td>
										<td ><a href="../resources/T10.exe">下载</a></td>
									</tr>
									
								</tbody>		
								</table>
							</div>
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
		</script>
	</body>
</html>