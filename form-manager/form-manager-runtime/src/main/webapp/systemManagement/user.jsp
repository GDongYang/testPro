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
		<title>用户管理</title>
	    <jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="icon,validator,page,tree" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/cardCss.css?v=2" />
		<style type="text/css">
			.cardBody{height:90px;}
			.cardBtns{width:100%;height:45px;}
			.activeBtn{width:60px;}
		</style>
	</head>
	<body class='base_padding-20 base_background-EEF6FB'>
		<form class="form form-inline" id="searchForm">
			<label class='base_margin-r-10'>用户名:</label>
  			<input type="text" class="form-control" id="usernameSearch" name='username' placeholder="用户名" />
			<label class='base_margin-r-10'>姓名:</label>
  			<input type="text" class="form-control" id="nameSearch" name='name' placeholder="姓名" />  			
			<label class='base_margin-r-10'>所属部门:</label>
			<div class='base_width-200' style='display:inline-block;position:relative'>
				<input class="form-control" name="department" id="deptmentId" type='hidden'/>
				<input class="form-control" id="deptSearchId" onclick="showMenu('menuContent');" readOnly/>
				<div id="menuContent" class="menuContent base_hidden searchTree">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>	
  		<div class='row base_margin-t-20'>
  			<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20' id='addCard' onclick='add()'>
  				<div class='cardBox base_height-175 base_line-height-175 text-center'>
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
						<label class="col-sm-3 control-label">用户名 ：</label>
						<div class="col-sm-8">
							<input class="form-control" id="userName" name="username" placeholder="用户名"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">姓名： </label>
						<div class="col-sm-8">
							<input class="form-control" id="name" name="name" placeholder="姓名"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">身份证号： </label>
						<div class="col-sm-8">
							<input class="form-control" id="cardNo" name="cardNo" placeholder="身份证号"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">CA证书编码 ：</label>
						<div class="col-sm-8">
							<input class="form-control" id="certNo" name="certNo" placeholder="CA证书编码 "/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">所属单位：</label>
						<div class="col-sm-8">
							<input class="form-control" type='hidden' name="orgId" id="orgId"/>
							<input class="form-control" name="orgName" id="deptSearchId2" onclick="showMenu('menuContent2');" readOnly style='position:relative'/>
							<div id="menuContent2" class="menuContent base_hidden searchTree">
								<ul id="treeDemo2" class="ztree"></ul>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">岗位：</label>
						<div class="col-sm-8">
							<select class="form-control" tips="请选择" name="positionId" id="positionId"></select>
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
			<div id="roleFormWrap" class='text-center'>
				<table class="row base_width-percent-100 base_margin-auto">
					<tr>
						<td><label class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left base_text-size-15" for="select">未选角色：</label></td>
						<td></td>
						<td><label class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left base_text-size-15" for="select2">已选角色：</label></td>
					</tr>
					<tr>
						<td class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
							<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select" id="select"></select>
						</td>
						<td class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center">
							<button class="btn btn-sm btn-primary base_margin-b-10" name="right" id="right"><span class="glyphicon glyphicon-step-forward"></span></button><br/>
							<button class="btn btn-sm btn-primary base_margin-b-10" name="rightAll" id="rightAll"><span class="glyphicon glyphicon-fast-forward"></span></button><br/>
							<button class="btn btn-sm btn-primary base_margin-b-10" name="leftAll" id="leftAll" ><span class="glyphicon glyphicon-fast-backward"></span></button><br/>
							<button class="btn btn-sm btn-primary base_margin-b-10" name="left" id="left"><span class="glyphicon glyphicon-step-backward"></span></button>
						</td>
						<td class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
							<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select2" id="select2"></select>
						</td>
					</tr>
				</table>
			</div>
			<div id="setPostDiv" >
				 <div class="user-position"></div>
			</div>
		 	<!--修改密码-->
           	<div id="updatePwdFormWrap" class="updatePwdFormWrap">
	           	<div class="col-sm-12">
					<label class="col-sm-2 control-label">旧密码 :</label>
					<div class="col-sm-6">
						<input class="form-control" id="oldPwd" name="oldPwd" placeholder="请输入旧密码"/>
					</div>
					<label class="col-sm-4 control-label base_hidden" style="color:red" id="wrongOldPwd"></label>
				</div>
				<div class="col-sm-12">	
					<label class="col-sm-2 control-label">新密码 :</label>
					<div class="col-sm-6">
						<input class="form-control" id="newPwd" name="newPwd" placeholder="请输入新密码"/>
					</div>
					<label class="col-sm-4 control-label base_hidden" style="color:red" id="wrongNewPwd"></label>
				</div>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="validator,page,tree" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="../cert/js/zjcacmt_cert.js"></script>
		<script type="text/javascript" src="../cert/js/zjcacmt_com.js"></script>
		<script type="text/javascript" src="../cert/js/zjcacmt_key.js"></script>
		<script type="text/javascript" src="../cert/js/zjcacmt_websocket.js"></script>
		<script type="text/javascript" src="../cert/js/login_mod.js"></script>
		<script type="text/javascript" src='../build/js/userCert.js'></script>
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src='../build/js/user.js'></script>
		<script type="text/javascript" src='../build/js/cardAjax.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
	</body>
</html>
