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
		<title>业务事项确认管理</title>
		<jsp:include page="../css/PageletCSS.jsp">
			<jsp:param value="icon,table,tree,validator" name="p" />
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" href="../iconfont/iconfont.css">
		<style>
			.tableCont{padding:15px;}
			.tableCont tr>td span{width:auto;cursor:pointer;vertical-align: bottom;color:#0173FF;}
			.situtionNum{width:16px!important}
			#previewImg{display:none;background:rgba(0,0,0,.5);position:absolute;left:0px;top:0px;height:100%;width:100%;}
			#previewImg img{width:70%;margin:100px 15%;}
			#previewImg span{position:absolute;right:20px;top:20px;z-index:88;font-size:30px;}
		</style>
	</head>
	<body class="base_background-EEF6FB">
		<div class="wrapper">
			<div class="leftBox">
				<h3 class="header">
					<i class="fa fa-tag"></i>
					<span>部门列表</span>
				</h3>
				<div class="body">
					<div class="searchBox">
						<input id="inputSearch" class='inputSearch'/>
						<i class="fa fa-search" id="treeSearchBtn"></i>
					</div>
					<div class="treeBox">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="rightBox">
				<div id="tempDetailWrap" class="tableCont">
					<form class="form form-inline" id="searchForm">
						<input type="text" class="form-control" id="searchName" name="name" placeholder="事项名称、事项编码、内部编码" />
			  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
			  			<button type="button" class="btn btn-primary btn-sm" onClick="cleanSearch()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
			  			<button type='button' class="btn btn-primary btn-sm" onClick="confirm()">确认</button>
			  		</form>
					<table id="tablewrap" class="box base_tablewrap papersManageTable2" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="false"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-checkbox="true"></th>
								<th data-field="name">事项名称</th>
								<th data-field="code">权利基本码</th>
								<th data-field="innerCode">事项内部编码</th>
								<th data-field="departmentName">部门名称</th>
								<th data-field="confirmStatus" data-formatter="confirmStatus">确认情况</th>
							    <th data-field="id" data-width='200' data-formatter="operation">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>		
		</div>
		<div style="display: none;">
    		<!--确认 -->
           	<div id="confirmFormWrap" class="confirmFormWrap">
				<form class="form-horizontal" id="confirmform" name="confirmform">
					<div class="form-group">
						<label class="col-sm-3 control-label">理由：</label>
						<div class="col-sm-8">
							<textarea class="form-control" name="memo" placeholder="理由"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 text-right">是否有效：</label>
						<div class="col-sm-8">
							<input type="radio" valueSet="1" name="type1" nameSet="status" value="1" checked='checked'/> 确认有效
							<input type="radio" valueSet="2" name="type1" nameSet="status" value="2"/> 确认无效
						</div>
					</div>
				</form>
			</div>
    		<!--上传附件-->
           	<div id="uploadFormWrap" class="uploadFormWrap">
				<form class="form-horizontal" id="uploadForm" name="uploadform">
					<input type="hidden" class="form-control"  name="itemId"/>
					<div class="form-group">
						<label for="importFile" class="col-sm-3 control-label">上传图片:</label>
						<div class="col-sm-8 filesInputBox">
							<input class="filesInput" type="text" name="fileName"/>
							<input type="file" name="file" onchange="fileChange(this)" style="display:none;"/>
							<button type='button' class="btn btn-primary" onclick="buttonClick(this)">选择图片</button>
							<span class='base_text-red' style='display:none;'>请选择文件！</span>
						</div>
            		</div>					
				</form>
			</div>
		</div>
		<div id='previewImg'>
			<span class='base_text-white fa fa-close' onclick='closeImg()'></span>
			<img src=''/>
		</div>
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="tree,table,validator" name="p" />
		</jsp:include>
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
		<script src='../build/js/treeSearch.js'></script>
		<script type="text/javascript" src='../js/jquery-form.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
		<script type="text/javascript" src="../build/js/itemConfirm.js"></script>
	</body>
</html>