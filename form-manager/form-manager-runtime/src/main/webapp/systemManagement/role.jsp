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
		<title>角色管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="icon,page,tree,validator" name="p"/>
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/cardCss.css?v=2" />
		<style>
			#treeDemoList {margin-top: 25px}
			.sureButton1 , .cancelButton1 {width: 100px !important}		
			.sideAddModal button {margin-top: 80px}
			.departmentFilter {margin-left: -2px;}
		</style>
	</head>
	<body class="base_padding-20 base_background-EEF6FB">
		<form class="form form-inline" id="searchForm">
			<label class='base_margin-r-10'>角色名称:</label>
  			<input type="text" class="form-control" id="name" name="name" placeholder="角色名称" /> 
  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>				
		<div class='row base_margin-t-20'>
  			<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20' id='addCard' onclick='add()'>
  				<div class='cardBox base_height-190 base_line-height-190 text-center'>
  					<img src='../build/image/addNew.png' height='50%'/>
  				</div>
  			</div>
  		</div>
  		<div class="fixed-table-pagination">
			<div class="pull-left">
				<span>第&nbsp;<span id="pageNum">1</span>&nbsp;页&nbsp;/&nbsp;共&nbsp;<span id="totalPages"></span>&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;<span id="total"></span>&nbsp;条记录</span>
			</div>
			<div class="pull-right" id="paginationUl"></div>
			<div class="clearfix"></div>
		</div>
  		<div style="display: none;">
    		<!--新增、修改 -->
           	<div id="addFormWrap" class="addFormWrap">
				<form class="form-horizontal" id="addForm" name="addform">
					<input id="roleId" type="hidden" class="form-control" name="id" value="0">
					<div class="form-group">
						<label class="col-sm-3 control-label">角色名称：</label>
						<div class="col-sm-8">
							<input class="form-control" id='roleName' name="name" placeholder="角色名称"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">角色描述：</label>
						<div class="col-sm-8">
							<textarea class="form-control" name="description" placeholder="角色描述"></textarea>
						</div>
					</div>					
					<div class="form-group">
						<label class="col-sm-3 control-label">角色级别：</label>
						<div class="col-sm-8">
							<select class="form-control" name="level">
								<option value="0">高</option>
								<option value="1">中</option>
								<option value="-1">低</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">角色状态：</label>
						<div class="col-sm-8 base_line-height-35">
							<label><input type="radio" valueSet="true" nameSet='active' name="active1" checked='checked' value="true"/> 正常</label>&nbsp;&nbsp;&nbsp;&nbsp;
							<label><input type="radio" valueSet="false" nameSet='active' name="active1" value="false"/>未激活</label>
						</div>
					</div>					
				</form>
			</div>
		</div>
		<div class='sideAddModal base_hidden text-center'>
			<div class="base_hidden" id="menuFormWrap">
				<h4>菜单权限 <span class='fa fa-close pull-right' onclick='sideAddModalClose()'></span></h4>
				<ul id="tree" class="ztree"></ul>
			</div>
			<div class="base_hidden" id="departFormWrap" >
					<h4>部门权限 <span class='fa fa-close pull-right' onclick='sideAddModalClose()'></span></h4>
					<div style="height:10%; margin-bottom: 8px;">
						<button class="btn btn-sm btn-primary "onclick="clearAllDepts()" style="margin-top:0px"><span class="fa">清空关联</span></button>
						<button class="btn btn-sm btn-primary "onclick="resetUpdateDepts()" style="margin-top:0px"><span class="fa">重置修改</span></button>
					</div>
					<label class="base_margin-b-10">职能:</label>
					<select id="jobSelect">
						<option value=''>--请选择--</option>
						<option value='ga'>公安</option>
						<option value='mz'>民政</option>
					</select>
					<br>
					<label class="base_margin-b-10">级别:</label>
					<select id="levelSelect">
						<option value=''>--请选择--</option>
						<option value='2'>省级</option>
						<option value='3'>地市级</option>
					</select>
					<br>
					<label class="base_margin-b-10">部门:</label>
					<select id="deptSelect">
						<option value=''>--请选择--</option>
					</select>
					<!-- 加入目的部门筛选 -->
					<br>
					<label class=''>目的部门筛选:</label>
					<input class=" base_width-100 departmentFilter" id="deptFilter" />
					<button class="btn btn-sm btn-primary "onclick="filterDept()" style="margin-top:0px"><span class="fa">筛选</span></button>
					<ul id="treeDemoList" class="ztree"></ul>
			</div>
			<button class='btn btn-sm btn-primary base_hidden sureButton1' id='menuConfirm' onclick='menuConfirm()'>确定</button>
			<button class='btn btn-sm btn-primary base_hidden sureButton1' id='deptConfirm' onclick='deptConfirm()'>确定</button>
			<button class='btn btn-sm btn-default cancelButton1' onclick='sideAddModalClose()'>取消</button>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="page,validator,tree" name="p"/>
		</jsp:include>		
		<script type="text/javascript" src='../build/js/role.js'></script>
		<script type="text/javascript" src='../build/js/cardAjax.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
	</body>
</html>