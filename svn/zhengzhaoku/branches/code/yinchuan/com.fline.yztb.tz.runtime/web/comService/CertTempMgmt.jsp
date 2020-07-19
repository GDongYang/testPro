<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<title>证件模板管理</title>
		<jsp:include page="../css/PageletCSS.jsp">
			<jsp:param value="mcustomscrollbar,icon,table,icheck,tree,dialog,chosen,fileinput,tips" name="p" />
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">部门列表</span></h3>
									<div class="box-tools pull-right">
										<button type="button" class="btn btn-xs btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
									</div>
								</div>
								<div class="box-body no-padding">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
						
						<div class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border base_box-header">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">模板列表</span></h3>
									<div class="box-tools pull-right">
										<button type="button" class="btn btn-xs btn-info" onClick="addTemplate()">新增</button>
										<button type="button" class="btn btn-xs btn-info" onClick="updateTemplate()">修改</button>
										<button type="button" class="btn btn-xs btn-info" onClick="removeTemplate()">删除</button>
										<button type="button" class="btn btn-xs btn-info" onClick="uploadTemplate()">上传模板</button>
										<!-- <button type="button" class="btn btn-xs btn-info" onClick="sealCreate()">上传电子印章</button> -->
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
													<th data-field="name">模板名称</th>
													<th data-field="code">模板编码</th>
													<th data-field="deptName">部门</th>
													<th data-field="type" data-formatter="typeFormatter">证件类型</th>
													<th data-field="createType" data-formatter="createTypeFormatter">模板类型</th>
													<th data-field="active" data-formatter="formatActive">状态</th>
												   <!--  <th data-field="id" data-formatter="detailFormatter">印章</th> -->
												    <th data-field="id" data-formatter="checkPDF">模板</th>
												</tr>
											</thead>
										</table>
									</div>
									<!-- /.form-group -->
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
					</div>
					<!-- /.row -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataform" name="dataform">
				<input type="hidden" id="idKey" name="idKey"/>
				<input type="hidden" id="version" name="version" value="0"/>
				<input type="hidden" name="code"/>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group" id="userNameDiv">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">模板名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="模板名称" tips-message="请输入模板名称"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- <input type="hidden" id="catalogCode" name="catalogCode" /> -->
						<input type="hidden" id="org" name="departmentId" />
						<input type="text" class="form-control base_input-text" id="departmentName" disabled="disabled"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">数据来源</label>
					<div class="col-xs-6 col-sm-4 col-md-4 col-lg-4 base_input-area">
						<select class="form-control" id="catalogCode" name="catalogCode" placeholder="数据来源" tips-message="请选择数据来源"></select>
					</div>
					<div class="col-xs-6 col-sm-4 col-md-4 col-lg-4 base_input-area">
						<select class="form-control" id="dataCode" name="dataCode" placeholder="数据来源" tips-message="请选择数据来源"></select>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="type">证件类型</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control" id="type" name="type" placeholder="证件类型" tips-message="请选择证件类型">
							<option value="1">个人</option>
							<option value="2">企业</option>
						</select>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="createType">证件类型</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control" id="createType" name="createType" placeholder="模板类型" tips-message="请选择模板类型">
							<option value="1">PDF</option>
							<option value="2">FreeMarker</option>
							<option value="3">WORD</option>
						</select>
					</div>
				</div>
			</form>
		</div>
		
		<div id="uploadFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="uploadDataform" name="uploadDataform">
				<input type="hidden" id="id" name="id" value="-1"/> 
				<input type="hidden" id="tempCode" name="tempCode"/>
				<input type="hidden" id="departmentId" name="departmentId"/>
				<input type="hidden" name="version" value="0"/>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">模板名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="certName" name="name" placeholder="模板名称" tips-message="请输入模板模板名称" readonly="readonly"/>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">关键字</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" name="keyWord" value="加盖电子公章" readonly="readonly"/>
					</div>
				</div>
				<!-- /.base_query-group -->
			<!-- 	<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">签章坐标</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 no-padding">
							<input type="text" class="form-control base_input-text" id="signx" name="signx" placeholder="X坐标" tips-message="请输入X坐标" tips-regexp="{'code':'^[0-9]+$','message':'请输入数字!'}"/>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 no-padding">
							<input type="text" class="form-control base_input-text" id="signy" name="signy" placeholder="Y坐标" tips-message="请输入Y坐标" tips-regexp="{'code':'^[0-9]+$','message':'请输入数字!'}"/>
						</div>
					</div>
				</div> -->
				<!-- /.base_query-group -->
			<!-- 	<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15"　for="memo">备　　注</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text"　id="memo" name="memo" placeholder="备注"　tips-message="请输入模板备注"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
<!-- 				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">序　　号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" name="ordinal" placeholder="文件序号" tips-message="请输入模板文件序号" tips-regexp="{'code':'^[0-9]+$','message':'请输入数字!'}"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">模板文件</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area" id ="fileDiv">
						<input type="file" name="file" id="importFile" accept="application/pdf+msword" value=""/>
					</div>
				<!-- 	<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<button type="button" id="mybtn" class="btn btn-xs btn-info" onClick="fileName()">点击</button>
	
					</div> -->
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<div id="tempDetailWrap" class="base_hidden">
			<table id="tableDetail" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
				data-ajax="ajaxRequest1" data-side-pagination="server"
				data-striped="true" data-single-select="true"
				data-click-to-select="true" data-pagination="true"
				data-pagination-first-text="首页" data-pagination-pre-text="上一页"
				data-pagination-next-text="下一页" data-pagination-last-text="末页">
				<thead style="text-align:center;">
					<tr>
						<th data-field="id" data-formatter="idFormatter1" data-width="40">序号</th>
						<th data-field="name">模板名称</th>
<!-- 						<th data-field="signx" data-formatter="xyFormatter">签章坐标</th>
						<th data-field="memo">备注</th>
						<th data-field="ordinal">排序号</th> -->
						<th data-field="sealId" data-formatter="sealFormatter">公章</th>
					    <th data-field="active" data-formatter="statusFormatter">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="importwrap" class="base_hidden">
			<div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">类型</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="addTypeExcel" name="addTypeExcel" value="true" tips-message="请选择事项状态"/> Excel</label>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input type="radio" id="addType2Excel" name="addType2Excel" value="false"/>手动</label>
					</div>
				</div>
			</div> 
			<div id="add_type_div" style="display: none;">
				<form class="form-horizontal base_dialog-form" id="dataformExcel" name="dataformExcel">
					<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
						<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">电子印章文件</label>
						<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
							<input type="file" name="fileExcel" id="importFileExcel" accept="application/pdf"/>
						</div>
					</div>
				</form>
			</div>
			
			<div id="add_type_div2" style="display: none;">
				<form class="form-horizontal base_dialog-form" id="dataformSeal" name="dataformSeal">
					<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
						<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">名称</label>
						<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
							<input type="text" class="form-control base_input-text" name="name" placeholder="印章名称"/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
						<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">印章KEY</label>
						<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
							<input type="text" class="form-control base_input-text" name="code" placeholder="印章KEY"/>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="table,icheck,tree,dialog,chosen,fileinput,tips,mcustomscrollbar" name="p" />
		</jsp:include>
		
		<script type="text/javascript" src="<%=path %>/js/jquery-form.js"></script>
		<script type="text/javascript" src="<%=path %>/js/certtemp.js?v=2"></script>
	</body>
</html>