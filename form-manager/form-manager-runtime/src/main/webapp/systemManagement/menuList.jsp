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
		<title>菜单管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="tree,validator,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
	</head>
	<body class="base_background-EEF6FB">
		<div class="wrapper">
			<div class="leftBox">
				<h3 class="header">
					<i class="fa fa-tag"></i>
					<span>所有菜单</span>
				</h3>
				<div class="body">
					<div class="searchBox">
						<input class="inputSearch" id='inputSearch'/>
						<i class="fa fa-search"></i>
					</div>
					<div class="treeBox">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="rightBox">
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>菜单管理</span>
					</h3>
					<div class="tableBtns">
						<button onclick='saveMenu()'><i class="fa fa-floppy-o"></i><span>保存</span></button>
						<button onclick='toAddDepartment()'><i class="fa fa-plus-circle"></i><span>增加</span></button>						
						<button onclick='deleteMenu()'><i class="fa fa-trash-o"></i><span>删除</span></button>						
					</div>
				</div>
				<div id="tempDetailWrap" class="tableCont">
					<form class="form-horizontal formBox" id='dataform'>
						<input type="hidden" name="visible" id="visible" value='0'/>
						<input type="hidden" name="parentId" id="parentId"/>
						<input type="hidden" class="form-control" id="modelId" name="id" placeholder="菜单id"/>
				 		<div class="form-group">
				    		<label  class="col-sm-4 control-label">菜单名称：</label>
				    		<div class="col-sm-8">
				      			<input type="text" class="form-control" id="name" name="name" placeholder="菜单名称"/>
				    		</div>
				  		</div>
				  		<div class="form-group">
				    		<label  class="col-sm-4 control-label">菜单图标：</label>
				    		<div class="col-sm-8">
				      			<input type="text" class="form-control" id="icon" name="icon" placeholder="菜单图标"/>
				    		</div>
				  		</div>
				  		<div class="form-group">
				    		<label  class="col-sm-4 control-label">菜单地址：</label>
				    		<div class="col-sm-8">
				      			<input type="text" class="form-control" id="location" name="location" placeholder="菜单地址"/>
				    		</div>
				  		</div>
				  		<div class="form-group">
				    		<label  class="col-sm-4 control-label">菜单序数：</label>
				    		<div class="col-sm-8">
				      			<input type="text" class="form-control" id="ordinal" name="ordinal" placeholder="菜单序数"/>
				    		</div>
				  		</div>
				  		<div class="form-group">
			  				<label  class="col-sm-4 control-label">是否激活：</label>
				    		<div class="col-sm-8">
					      		<div class="checkbox">
					        		<label>
					          			<input type="checkbox" id="ifVisible"/>
					        		</label>
					      		</div>
					    	</div>
					  	</div>
					</form>
				</div>
			</div>		
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="tree,validator" name="p"/>
		</jsp:include>
		<script type="text/javascript" src='../build/js/menuList.js'></script>
		<script src='../build/js/treeSearch.js'></script>
	</body>
</html>