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
		<title>岗位管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="page,icon,validator,tree,chosen" name="p"/>
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/cardCss.css?v=2" />
		<link rel="stylesheet" type="text/css" href="../build/css/list.css?v=2" />
		<style>
			#select {
			    direction: rtl;
			}
			#select option {
			    direction: rtr;
			}
		
		</style>
	</head>
	<body class='base_padding-20 base_background-EEF6FB'>
		<form class="form form-inline" id="searchForm">
			<label class='base_margin-r-10'>岗位名称:</label>
  			<input type="text" class="form-control" id="name" name="searchName" placeholder="岗位名称" />  			
			<label class='base_margin-r-10'>所属部门:</label>
  			<div class='base_width-200' style='display:inline-block;position:relative'>
  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
				<input class="form-control" id="deptSearchId" onclick="showMenu('menuContent');" readOnly/>
				<div id="menuContent" class="menuContent base_hidden searchTree">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>				
		<div class='row base_margin-t-20'>
  			<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20' id='addCard' onclick='addPosition()'>
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
						<label class="col-sm-3 control-label">岗位名称:</label>
						<div class="col-sm-8">
							<input class="form-control" name="name" placeholder="岗位名称"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">所属部门:</label>
						<div class="col-sm-8">
							<input class="form-control" type='hidden' name="departmentId" id="deptId"/>
							<input class="form-control" name="departmentName" id="deptSearchId2" onclick="showMenu('menuContent2');" readOnly style='position:relative'/>
							<div id="menuContent2" class="menuContent base_hidden searchTree">
								<ul id="treeDemo2" class="ztree"></ul>
							</div>
						</div>
					</div>
					<!--<div class="form-group">
						<label class="col-sm-3 control-label">事项名称:</label>
						<div class="col-sm-8">
							<select class="form-control chosen-select-deselect" data-placeholder="请选择" multiple="multiple" name="itemCodes" id="itemCodes"></select>
						</div>
					</div>-->
					<div class="form-group">
						<label class="col-sm-3 control-label">岗位描述:</label>
						<div class="col-sm-8">
							<textarea class="form-control" id="memo" name="memo" placeholder="岗位描述"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">岗位状态:</label>
						<div class="col-sm-8 base_line-height-35">
							<input type="radio" valueSet="true" nameSet='active' name="active1" checked value="true"/> 正常
							<input type="radio" valueSet="false" nameSet='active' name="active1" value="false"/>未激活
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">综合窗口:</label>
						<div class="col-sm-8 base_line-height-35">
							<input type="radio" id="type1" valueSet="1" name="type1" nameSet='type' value="1" checked/> 是
							<input type="radio" id="type0" valueSet="0" name="type1" nameSet='type' value="0"/>否
						</div>
					</div>
				</form>
			</div>
		</div>
		<div style="display: none;">
			<div id="formDetail" class="formDetail">
				<div class='base_width-300' style='position:relative;'>
					<!-- <button class="btn btn-sm btn-primary base_margin-l-20" name="addItem" id="addItem"><span class="glyphicon glyphicon-step-forward"></span></button>-->
					<!-- <button class="btn btn-sm btn-primary" name="addDestDept" id="addDestDept" ><span class="fa fa-plus-circle">添加事项</span>-->
					<!-- <button class="btn btn-sm btn-primary base_margin-l-20" name="removeItem" id="removeItem"><span class="fa fa-minus-circle">移除事项</span></button>-->
					<button class="btn btn-sm btn-primary " name="removeAllItem" id="removeAllItem" ><span class="fa fa-minus-circle">清空事项</span></button>
					<!--<button class="btn btn-sm btn-primary " name="removeDestDept" id="removeDestDept"><span class="fa fa-minus-circle">移除部门</span></button>-->
				</div>
				<div class='base_width-200 ' style='display:inline-block;position:relative;'>
					<label class='base_margin-r-10'>数源事项:</label> 
	  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
					<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="itemSelectId" name="itemSelectId" ></select>
				</div>
				<div class='base_width-200 base_margin-l-20' style='display:inline-block;margin-bottom: 10px'>
           			<label class='base_margin-r-10'>事项信息:</label>
	  				<input class="form-control" name="itemName2" id="itemName2"  placeholder="事项名/innerCode/code"/>
				</div>
				<div class='base_width-50' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary "onclick="searchItem2()"><span class="fa">查询</span></button>
				</div>
				<h3 class='text-center base_hidden noDataTitle' id='noData'>暂无数据！</h3>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="page,validator,tree,chosen" name="p"/>
		</jsp:include>		
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src='../build/js/position.js'></script>
		<script type="text/javascript" src='../build/js/cardAjax.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
	</body>
</html>