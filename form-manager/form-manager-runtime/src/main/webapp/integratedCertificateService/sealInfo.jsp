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
		<title>电子印章管理</title>
		<jsp:include page="../css/PageletCSS.jsp">
			<jsp:param value="icon,page,validator,tree,chosen" name="p" />
		</jsp:include>				
		<link rel="stylesheet" type="text/css" href="../build/css/cardCss.css?v=2" />
		<style type="text/css">
		   	.cardBody{height:100px}
		   	/*chosen select下拉框*/
		   	.chosen-container{width:108%!important;}
			.chosen-container-multi .chosen-choices{width:93%;min-height:32px;border-radius:4px}
			.chosen-container .chosen-drop{width:93%;}
			.filesInputBox{font-size:0;}
			.filesInput{height: 34px;width:77%!important;border-radius:4px 0 0 4px;border:1px solid #ccc;border-right:none;display:inline-block;vertical-align:top;font-size:14px}
			.filesInputBox .btn-primary{border-radius:0 4px 4px 0!important;}
			.itemImg{display:inline-block;width:355px;}
		</style>
	</head>
	<body class='base_padding-20 base_background-EEF6FB'>
		<form class="form form-inline" id="searchForm">
			<label class='base_margin-r-10'>印章名称:</label>
  			<input type="text" class="form-control" id="name" name="name" placeholder="印章名称" />
  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>				
		<div class='row base_margin-t-20'>
  			<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20' id='addCard' onclick='addCard()'>
  				<div class='cardBox base_height-140 base_line-height-140 text-center'>
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
						<label class="col-sm-3 control-label">名称:</label>
						<div class="col-sm-8">
							<input class="form-control" name="name" placeholder="名称"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">编码:</label>
						<div class="col-sm-8">
							<input class="form-control" name="code" placeholder="编码"/>
						</div>
					</div>					
					<div class="form-group">
						<label class="col-sm-3 control-label">所属部门:</label>
						<div class="col-sm-8">
							<input class="form-control" type='hidden' name="departmentId" id="deptmentId"/>
							<input class="form-control" id="deptSearchId" name="departmentName" autocomplete="off"  onclick="showMenu('menuContent');" style='position:relative'/>
							<div id="menuContent" class="menuContent base_hidden searchTree">
								<ul id="treeDemo" class="ztree" style=""></ul>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">证件:</label>
						<div class="col-sm-8">
							<select class="form-control chosen-select-deselect" data-placeholder="请选择" multiple="multiple" data-live-search="true" id="certTempIda" name="certTempIdS"></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">关键字:</label>
						<div class="col-sm-8">
							<input class="form-control" name="keyword" placeholder="仅支持关键字盖章"/>
						</div>
					</div>
					<div class="form-group">
						<label for="importFile" class="col-sm-3 control-label">上传图片:</label>
						<div class="col-sm-8 filesInputBox">
							<input class="filesInput" type="text" name="fileName"/>
							<input type="file" name="imgFile" onchange="fileChange(this)" style="display:none;"/>
							<button class="btn btn-primary" onclick="buttonClick(this)">选择图片</button>
							<img class="itemImg" src="">
            			</div>
            		</div>					
				</form>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="page,validator,tree,chosen" name="p" />
		</jsp:include>	
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src='../js/jquery-form.js'></script>
		<script type="text/javascript" src='../build/js/sealInfo.js'></script>		
		<script type="text/javascript" src='../build/js/cardAjax.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
	</body>
</html>