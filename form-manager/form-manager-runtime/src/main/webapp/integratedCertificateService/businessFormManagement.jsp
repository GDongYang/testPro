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
		<title>业务表单管理</title>
		<jsp:include page="../css/PageletCSS.jsp">
			<jsp:param value="icon,table,tree,validator,chosen" name="p" />
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
        <link rel="stylesheet" type="text/css" href="../iconfont/iconfont.css">
		<style>
			#tablewrap td{max-width:150px;cursor:pointer;}
			.fixed-table-pagination .page-list{display:none;}
			.modal-body{max-height:350px;}
			#searchForm{margin:20px 0 20px 20px}
			#searchForm .form-control{height:32px;padding:4px 10px;}
			.mycopydialog .modal-body{height:800px!important};
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
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>表单列表</span>
					</h3>
					<div class="tableBtns" style="display:none">
                        <button class="saveToCacheBtn"  onclick='bindFormTemp()'><i class="fa fa-plus-circle"></i><span>关联</span></button>
						<button class="saveToCacheBtn"  onclick='showCopyForm()'><i class="fa fa-plus-circle"></i><span>复制</span></button>
						<!--<button class="saveToCacheBtn"  onclick='saveToCache()'><i class="fa fa-plus-circle"></i><span>缓存</span></button>-->
						<button class="addBtn"  onclick='addTable()'><i class="fa fa-plus-circle"></i><span>增加</span></button>
						<button class="editBtn" onclick="updateTable()"><i class="fa fa-pencil"></i><span>修改</span></button>
						<button class="deleteBtn" onclick="removeTable()"><i class="fa fa-trash-o"></i><span>删除</span></button>
						<button class="updateBtn" onclick='publishTemplate()'><i class="fa fa-cloud-upload"></i><span>发布</span></button>
					</div>
				</div>
				<form class="form form-inline" id="searchForm">
					<input type="text" class="form-control" id="searchName" name="name" placeholder="证明名称" />
		  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
		  			<button type="button" class="btn btn-primary btn-sm" onClick="cleanSearch()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
<!-- 		  			<div class="form-group base_float-right base_margin-r-35"> -->
<!-- 		  				<label class="control-label">按照证件类型筛选:</label> -->
<!-- 		  				<select class="form-control" id="searchType" name="type" onchange="typeChange()"> -->
<!-- 							<option value="0">全部</option> -->
<!-- 							<option value="5">默认</option> -->
<!-- 							<option value="6">其他</option> -->
<!-- 						</select> -->
<!-- 		  			</div> -->
				</form>
				<div id="tempDetailWrap" class="tableCont">
					<table id="tablewrap" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="true"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-radio="true"></th>
								<th data-field="id" data-formatter="idFormatter">序号</th>
								<th data-field="name" data-formatter="nameFormatter">名称</th>
								<th data-field="version">版本号</th>
								<th data-field="code">编码</th>
								<th data-field="departmentName" data-formatter="titleFormatter">部门</th>
<!-- 								<th data-field="type" data-formatter="typeFormatter">证件类型</th> -->
								<th data-field="active" data-formatter="formatActive">状态</th>							 
<!-- 							    <th data-field="id" data-formatter="checkPDF">表单</th> -->
<!-- 							    <th data-field="id" data-formatter="editCertTemp">编辑表单</th> -->
							    <th data-field="id" data-formatter="formOption">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>		
		</div>
		<div style="display: none;">
    		<!--新增、修改 -->
           	<div id="addFormWrap" class="addFormWrap">
				<form class="form-horizontal" id="addForm" name="addform">
					<input type="hidden"  class="form-control" id="id" name="id" value="-1"/> 
					<input type="hidden" class="form-control" id="tempCode" name="code"/>
					<input type="hidden" class="form-control" id="dataCode" name="dataCode"/>
					<div class="form-group">
						<label class="col-sm-3 control-label">表单名称:</label>
						<div class="col-sm-8">
							<input class="form-control" id="name" name="name" placeholder="表单名称"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">部门:</label>
						<div class="col-sm-8">
							<input type="hidden" class="form-control" id="org" name="departmentId" />
							<input class="form-control" id="deptName" name="departmentName" placeholder="部门" readOnly/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">证件类型:</label>
						<div class="col-sm-8">
							<select class="form-control" id="type" name="type">
								<option value="5">默认</option>
								<option value="6">其他</option>
							</select>
						</div>
					</div>
					<div class="form-group formUrl hidden">
						<label class="col-sm-3 control-label">表单链接:</label>
						<div class="col-sm-8">
							<input class="form-control" id="formUrl" name="formUrl" placeholder="表单链接"/>
						</div>
					</div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">前端校验函数:</label>
                        <div class="col-sm-8">
                            <input class="form-control" id="checkFunction" name="checkFunction" placeholder="前端校验函数"/>
                        </div>
                    </div>
				</form>
			</div>
			<div id='msgDataModal'>
				<button class='btn btn-xs btn-primary base_margin-b-15' type='button' onclick='addMsgTable()'>添加</button>
				<div>
					<table class='base_viewTable' id='msgDataTable'>
						<thead>
							<tr>
								<th>名称</th>
								<th>中文名</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
    		<!--查看表单 -->
           	<div id="seeForm" class="seeForm">
           	</div>
			<!-- 复制表单-->
           	<div id="copyItemWrap" class="addFormWrap">
	  			<div class='base_width-200' style='display:inline-block;margin-bottom: 10px'>
           			<label class='base_margin-r-10'>数源部门:</label>
	  				<input class="form-control" name="sourceDeptId" id="sourceDeptId" type='hidden'/>
					<input class="form-control searchInput base_width-185" id="sourceDeptName" onclick="showMenu('menuContent1');" autocomplete="off"/>
					<div id="menuContent1" class="menuContent base_hidden searchTree" style="left: 25px;top: 80px">
						<ul id="treeDemo3" class="ztree"></ul>
					</div>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px,margin-left: 50px'>
					<label class='base_margin-r-10'>目的部门筛选:</label>
					<input class="form-control base_width-185" id="deptFilter" />
				</div>
				<div class='base_width-50' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary "onclick="filterDept()"><span class="fa">筛选</span></button>
				</div>
				<div class='base_width-70' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<input type="checkBox" value="1" id="deptOpt">部门优化</input>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px'>
           			<!--<label class='base_margin-r-10'>事项信息:</label>
	  				<input class="form-control" name="itemName2" id="itemName2"  placeholder="事项名/innerCode/code"/>-->
				</div>
				<div class='base_width-50' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<!--<button class="btn btn-sm btn-primary "onclick="searchItem2()"><span class="fa">查询</span></button>-->
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px,margin-left: 50px'>
					
				</div>
				<div class='base_width-200' style='display:inline-block;position:relative;'>
					<label class='base_margin-r-10'>数源表单:</label> 
	  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
					<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="formSelectId" name="formSelectId" ></select>
				</div>
				<div class='base_width-300' style='display:inline-block;position:relative;'>
					<button class="btn btn-sm btn-primary base_margin-l-20" name="removeTemp" id="removeTemp"><span class="fa fa-minus-circle">移除证明</span></button>
					<button class="btn btn-sm btn-primary " name="removeAllTemp" id="removeAllTemp" ><span class="fa fa-minus-circle">清空证明</span></button>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<label >数源表单列表</label>
				</div>
				<div col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<label >目的部门列表</label>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area" >
					<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select" id="select" style="height:400px"></select>
				</div>
				<div class='col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center'>
				</div>
				<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7 text-center base_input-area" style="height:400px;overflow:scroll; border:1px solid #ccc;border-radius:4px">
					<ul id="treeDemo4" class="ztree"></ul>
				</div>
			</div>
            <!-- 关联证照数据-->
            <div id="formTempFormWrap" class="addFormWrap" style="height: 200px;">
                <form class="form-horizontal" id="formTempForm" name="formTempForm">
                    <div class="form-group">
                        <label class="col-sm-3">证照列表：</label>
                        <div class="col-sm-6">
                            <select class="form-control chosenSelect chosen-select-deselect formTempData" multiple="multiple" data-placeholder="请选择">
                                <option value="">无关联表单</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
		</div>
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="table,tree,validator,chosen" name="p" />
		</jsp:include>
		<script src='../build/js/treeSearch.js'></script>
		<script type="text/javascript" src='../js/jquery-form.js'></script>
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src="../build/js/businessFormManage.js"></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
		<script>
			var windowHeight=window.innerHeight;
	    	$(".base_box-area-aqua").css("height",windowHeight-103);
	    	$(".leftBox").css("height",windowHeight+40);
	    	$(".treeBox").css("height",windowHeight-126);
	    	$(".tableCont").css("height",windowHeight-90);
	    	function titleFormatter(value,row){
	    		return '<span title="'+(value ? value : '无' )+'">'+(value ? value : '无' )+'</span>'
	    	}	    	
		</script>
	</body>
</html>