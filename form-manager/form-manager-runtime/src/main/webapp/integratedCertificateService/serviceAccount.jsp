<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>业务账号管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="icon,page,validator,tree,chosen" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/cardCss.css?v=2" />
	</head>
	<body class='base_padding-20 base_background-EEF6FB'>
		<form class="form form-inline" id="searchForm">
			<label class='base_margin-r-10'>账号拥有者:</label>
  			<input type="text" class="form-control" id="nameSearch" name="name" placeholder="账号拥有者" />
  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>搜索</button>
  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>
  		<div class='row base_margin-t-20'>
  			<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20' id='addCard' onclick='addAccount()'>
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
					<input type="hidden" class="form-control"  name="id" id="add_id" />
					<div class="form-group">
						<label class="col-sm-3 control-label">账号拥有者：</label>
						<div class="col-sm-8">
							<input class="form-control" name="name" placeholder="账号拥有者"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">部门：</label>
						<div class="col-sm-8">
							<input class="form-control" type='hidden' name="departmentId" id="deptId"/>
							<input class="form-control" name="departmentName" id="departmentId" onclick="showMenu('menuContent');" readOnly style='position:relative'/>
							<div id="menuContent" class="menuContent base_hidden searchTree">
								<ul id="treeDemo" class="ztree" style=""></ul>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">岗位：</label>
						<div class="col-sm-8">
							<select class="form-control chosen-select-deselect" data-placeholder="请选择" name="positionId" id="positionId"></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">备注：</label>
						<div class="col-sm-8">
							<textarea class="form-control" id="memo" name="memo" placeholder="备注"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">状态：</label>
						<div class="col-sm-8 base_line-height-35">
                            <input type="radio" valueSet="true" nameSet='active' name="active1" checked value="true"/> 正常
                            <input type="radio" valueSet="false" nameSet='active' name="active1" value="false"/>未激活
                        </div>
					</div>
				</form>
			</div>
			<!--修改密码 -->
			<div id="passFormWrap" class="passFormWrap">
				<form class="form-horizontal" id="passForm" name="passForm">
					<input type="hidden" class="form-control"  name="id" id="pass_id" />
					<div class="form-group">
						<label class="col-sm-3 control-label">旧密码：</label>
						<div class="col-sm-8 base_line-height-35">
							<input type="text" class="form-control" id="oldPass" name="oldPass" placeholder="旧密码" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">新密码：</label>
						<div class="col-sm-8 base_line-height-35">
							<input type="password" class="form-control" id="newPass" name="newPass" placeholder="新密码" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">请确认密码：</label>
						<div class="col-sm-8 base_line-height-35">
							<input type="password" class="form-control" id="queryPass" name="queryPass" placeholder="确认密码" />
						</div>
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="page,validator,tree,chosen" name="p"/>
		</jsp:include>
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src='../build/js/cardAjax.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
		<script type="text/javascript" src='../build/js/serviceAccount.js'></script>
	</body>
</html>