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
			<jsp:param value="icon,table,tree,validator,chosen" name="p" />
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/papersManage.css">
		<link rel="stylesheet" type="text/css" href="../iconfont/iconfont.css">
		<style>
			#tablewrap td{max-width:150px;cursor:pointer; }
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
					<h3 class="tableName">
						<form class="form form-inline papersManageForm1">
                            <input type="text" class="form-control " id="searchName" placeholder="证明名称、证明编码">
                            <button type="button" class="btn btn-primary btn-sm papersManageClearBtn papersManageClearBtn1" onClick="search()"><i class='fa fa-search base_margin-r-5 clearIcon1'></i>搜索</button>
                            <button type="button" class="btn btn-primary btn-sm papersManageClearBtn papersManageClearBtn2" onClick="cleanSearch()"><i class='fa fa-trash-o base_margin-r-5 clearIcon1'></i>清空</button>
                            <form class="form form-inline" id="searchForm">
					  			<div class="form-group base_float-right base_margin-r-35">
					  				<label class="control-label ">按照证件类型筛选:</label>
					  				<select class="form-control" id="searchType" name="type" onchange="typeChange()">
										<option value="0">全部</option>
										<option value="1">证明</option>
										<option value="2">证明(不需要模板)</option>					
										<option value="3">申请表</option>
										<option value="4">照片</option>
										<option value="6">其他</option>
									</select>
					  			</div>
							</form>		 					 
						</form>					
					</h3>
				<div class="tableHead">
					
					<div class="tableBtns" style="display:none">
<%--						<button class="tableHeaderBtn saveToCacheBtn"  onclick='showCopyForm()'><i class="fa fa-plus-circle"></i><span>复制</span></button>--%>
						<button class="tableHeaderBtn saveToCacheBtn"  onclick='saveToCache()'><i class="fa fa-plus-circle"></i><span>缓存</span></button>
						<button class="tableHeaderBtn addBtn"  onclick='addTable()'><i class="fa fa-plus-circle"></i><span>增加</span></button>
						<button class="tableHeaderBtn editBtn" onclick="updateTable()"><i class="fa fa-pencil"></i><span>修改</span></button>
						<button class="tableHeaderBtn deleteBtn" onclick="removeTable()"><i class="fa fa-trash-o"></i><span>删除</span></button>
						<button class="tableHeaderBtn updateBtn" onclick='publishTemplate()'><i class="fa fa-cloud-upload"></i><span>发布</span></button>
					</div>
				</div>
				
				<div id="tempDetailWrap" class="tableCont">
					<table id="tablewrap" class="box base_tablewrap papersManageTable2" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="true"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-radio="true"></th>
								<th data-field="id" data-formatter="idFormatter" >序号</th>
								<th data-field="name" data-formatter="nameFormatter">模板名称</th>
								<th data-field="version">版本号</th>
								<th data-field="code">模板编码</th>
								<th data-field="deptName" data-formatter="titleFormatter">数源部门</th>
								<th data-field="type" data-formatter="typeFormatter">证件类型</th>
								<th data-field="active" data-formatter="formatActive">状态</th>							 
							    <th data-field="id" data-formatter="checkPDF">模板</th>
							    <th data-field="id" data-formatter="editCertTemp">编辑模板</th>
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
					<div class="form-group">
						<label class="col-sm-3 control-label">模板名称:</label>
						<div class="col-sm-8">
							<input class="form-control" id="name" name="name" placeholder="模板名称"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">使用部门:</label>
						<div class="col-sm-8">
							<input type="hidden" class="form-control" id="org" name="catalogDeptId" />
							<input class="form-control" id="deptName" name="catalogDeptName" placeholder="使用部门" readOnly/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">数源部门:</label>
						<div class="col-sm-8">
							<input type="hidden" class="form-control"  name="departmentId" id="departmentId" />
							<input class="form-control" name="deptName" id="departmentName" onclick="showMenu('menuContent');" style='position:relative'/>
							<div id="menuContent" class="menuContent base_hidden searchTree">
								<ul id="treeDemo2" class="ztree" style=""></ul>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label ">证件类型:</label>
						<div class="col-sm-8">
							<select class="form-control" id="type" name="type">
								<option value="1">证明</option>
								<option value="2">证明(不需要模板)</option>							
								<option value="3">申请表</option>
								<option value="4">照片</option>
								<option value="6">其他</option>
							</select>
						</div>
					</div>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">数据来源:</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="dataSource" name="dataSource" placeholder="数据来源"/>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">有效数据字段名</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="dataCode" name="dataCode" placeholder="有效数据字段名"/>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">身份证字段名</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="cerNoParam" name="cerNoParam" placeholder="身份证字段名"/>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">姓名字段名</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="cerNameParam" name="cerNameParam" placeholder="姓名字段名"/>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">其他字段(";"分隔)</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="otherParams" name="otherParams" placeholder="其他字段(','分隔)"/>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">请求处理方法</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="requestHandler" name="requestHandler" placeholder="请求处理方法"/>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">结果处理方法</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="resultHandler" name="resultHandler" placeholder="结果处理方法"/>--%>
<%--						</div>--%>
<%--					</div>--%>
					<!--<div class="form-group">
						<label class="col-sm-3 control-label" for="sealId">印章来源:</label>
						<div class="col-sm-8">
							<select class="form-control chosen-select-deselect" data-placeholder="请选择" multiple="multiple" id="sealId" name="sealCodes" ></select>
						</div>
					</div>-->
					<!--<div class="form-group">
						<label class="col-sm-3 control-label">盖章方式:</label>
						<div class="col-sm-8">
							<select class="form-control" id="signMethodSelect" name="signMethodSelect">
								<option value="">请选择</option>
								<option value="1">关键字</option>
								<option value="0">坐标</option>
							</select>
						</div>
					</div>-->
					<div class="form-group base_hidden" id="keyWordDiv">
						<label class="col-sm-3 control-label" for="keyword">关键字名:</label>
						<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
							<input type="text" class="form-control" id="keyword" name="keyword" placeholder="关键字名"/>
						</div>
					</div>
					<div class="form-group base_query-group base_hidden " id="signxDiv">
						<label class="col-sm-3 control-label" for="signx">坐标X:</label>
						<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
							<input type="text" class="form-control base_input-text" id="signx" name="signx" placeholder="相对模板左上方的水平位移"/>
						</div>
					</div>
					<div class="form-group base_query-group base_hidden" id="signyDiv">
						<label class="col-sm-3 control-label" for="signy">坐标Y:</label>
						<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
							<input type="text" class="form-control" id="signy" name="signy" placeholder="相对模板左上方的垂直位移"/>
						</div>
					</div>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">材料关键词:</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input class="form-control" id="keywords" name="keywords" placeholder="关键词"/>--%>
<%--						</div>--%>
<%--					</div>--%>
					<div id="imgDiv" class="form-group" style="display:none">
						<label for="importFile" class="col-sm-3 control-label">上传图片:</label>
						<div class="col-sm-8 filesInputBox">
							<input class="filesInput" type="text" name="fileName"/>
							<input type="file" name="imgFile" accept="image/*" onchange="fileChange(this)" style="display:none;"/>
							<button class="btn btn-primary" onclick="buttonClick(this)">选择图片</button>
							<div id="imgMsg" style="display:none"><a style="color:red">图片大于65K，请重新选择！</a></div>
							<img class="itemImg" src="">
            			</div>
            		</div>	
				</form>
			</div>
		</div>	
		<div style="display: none;">
    		<!--查看其他模板 -->
    		<div id="seeOtherTemp">
    			<!--草稿 -->
	           	<div class="draftTablewrap base_margin-b-20">
	           	    <p class="rectangle"> 草稿</p>
	           		<table id="draftTablewrap"></table>
	           	</div>
	           	<!--历史 -->
	           	<div class="historyTablewrap">
	           		<p class="rectangle"> 历史</p>
	           		<table id="historyTablewrap"></table>
	           	</div>
    		</div>
		</div>
		
		<div style="display: none;">
    		<!--选择修改版本-->
    		<div id="updateVersionWrap">
    			<p>请选择你要修改的版本</p>
    			<div>
				  <label><input type="radio"  name="version" value="1" checked/> 当前版本</label>
				</div>
				<div>
				  <label><input type="radio"  name="version" value="0"/> 草稿</label>
				</div>
    		</div>
		</div>	
		
		<div style="display: none;">
			<!-- 复制证明-->
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
					<label class='base_margin-r-10'>数源证明:</label> 
	  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
					<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="tempSelectId" name="tempSelectId" ></select>
				</div>
				<div class='base_width-300' style='display:inline-block;position:relative;'>
					<button class="btn btn-sm btn-primary base_margin-l-20" name="removeTemp" id="removeTemp"><span class="fa fa-minus-circle">移除证明</span></button>
					<button class="btn btn-sm btn-primary " name="removeAllTemp" id="removeAllTemp" ><span class="fa fa-minus-circle">清空证明</span></button>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<label >数源证明列表</label>
				</div>
				<div col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<label >目的部门列表</label>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area" >
					<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select" id="select" style="height:400px"></select>
				</div>
				<div col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center>
				</div>
				<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7 text-center base_input-area" style="height:400px;overflow:scroll; border:1px solid #ccc;border-radius:4px">
					<ul id="treeDemo4" class="ztree"></ul>
				</div>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="table,tree,validator,chosen" name="p" />
		</jsp:include>
		<script src='../build/js/treeSearch.js'></script>
		<script type="text/javascript" src='../js/jquery-form.js'></script>
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src="../build/js/certempMgmt.js"></script>
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