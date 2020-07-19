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
		<title>业务事项管理</title>
		<jsp:include page="../css/PageletCSS.jsp">
			<jsp:param value="icon,table,tree,validator,page,chosen" name="p" />
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" href="../iconfont/iconfont.css">
		<style>
			#tablewrap td{max-width:150px;cursor:pointer;}
			/*情形新增、修改*/	
			#situationForm .chosen-container-single .chosen-single{height:34px;line-height:34px}
			#situationForm .chosen-container{width:100%!important;}
			#situationForm .chosen-container .chosen-drop {width: 100%;}
			#situationForm .chosen-container-single .chosen-single div b{background-position:0 6px;}
			#situationForm .chosen-container-active.chosen-with-drop .chosen-single div b{background-position: -18px 8px;}
			#situationForm .chosen-container-active .chosen-width-drop .chosen-single div b{background-position:0 6px;}
			.situation{border:1px solid #ddd}
			.situationHeight .modal-body{max-height:700px!important}
			.mydialog .modal-body{height:600px max-height:100px}
			.situationType{width:35px;text-align:center}
			.formTempHeight .modal-body{height:200px!important}
			.material{border-bottom:1px dashed #ddd;}
			.rightBox{background-color:transparent;}
			.tableCont{background-color:transparent;padding:0;}
			#deleteTipWrap .modal-dialog{width:300px}
			.detailItem .innerText{width:60%}
			.mycopydialog .modal-body{height:500px!important;}
			.modal-body{max-height:300px}
			.seeYztb .form-control{padding:4px 12px}
			.wrapper .iconfont{font-size:12px}
			#formTempFormWrap label{text-align:right;padding-right:0px;line-height:26px}
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
<%--			  			<button type="button" id="repeat" class="btn btn-primary btn-sm" onClick="searchRepeat()"><i class='fa fa-search base_margin-r-5'></i>重复</button>--%>
<%--			  			<label class="seeYztb">--%>
<%--			  				<select class="form-control" id="yztb">--%>
<%--			  					<option value=-1>查看全部</option>--%>
<%--			  					<option value=1>已一证通办</option>--%>
<%--			  					<option value=0>未一证通办</option>--%>
<%--			  					<option value=2>已上线</option>--%>
<%--			  				</select>--%>
<%--			  			</label>--%>
			  			<button type="button" style="display:none" class="btn btn-primary btn-sm addItemBtn" onClick="copyItem()" id="copyBtn"><i class="iconfont icon-fuzhi base_margin-r-5"></i>复制事项</button>
			  			<button type="button" style="display:none" class="btn btn-primary btn-sm addItemBtn  base_margin-r-10" onClick="addItem()" id="addBtn"><i class="fa fa-plus-circle base_margin-r-5"></i>新增事项</button>
                        <button type="button" style="display:none" class="btn btn-primary btn-sm addItemBtn  base_margin-r-10" onClick="syncAll()" id="syncBtn"><i class="fa fa-plus-circle base_margin-r-5"></i>同步事项</button>
			  		</form>
			  		<div class='listBox'>
			  			<div id="addCard"></div>
			  		</div>
			  		<div class="fixed-table-pagination">
						<div class="pull-left">
							<span>第&nbsp;<span id="pageNum">1</span>&nbsp;页&nbsp;/&nbsp;共&nbsp;<span id="totalPages"></span>&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;<span id="total"></span>&nbsp;条记录</span>
						</div>
						<div class="pull-right" id="paginationUl"></div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>		
		</div>
		<div style="display: none;">
    		<!--新增、修改 -->
           	<div id="addFormWrap" class="addFormWrap">
				<form class="form-horizontal" id="addForm" name="addform">
					<input type="hidden" class="form-control"  name="id" id="add_id" />
					<div class="form-group">
						<label class="col-sm-3 control-label">事项名称：</label>
						<div class="col-sm-8">
							<input class="form-control" id="name" name="name" placeholder="事项名称"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">权利编码：</label>
						<div class="col-sm-8">
							<input class="form-control" id="code" name="code" placeholder="权利编码"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">内部编码：</label>
						<div class="col-sm-8">
							<input class="form-control" id="innerCode" name="innerCode" placeholder="内部编码"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">所属部门：</label>
						<div class="col-sm-8">
							<input class="form-control" type='hidden' name="departmentId" id="deptId"/>
							<input class="form-control" name="departmentName" id="deptSearchId" onclick="showMenu('menuContent');" style='position:relative'/>
							<div id="menuContent" class="menuContent base_hidden searchTree">
								<ul id="treeDemo2" class="ztree" style=""></ul>
							</div>
						</div>
					</div>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">岗位名称：</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<select class="form-control chosen-select-deselect" data-placeholder="请选择" multiple="multiple" id="positionId" name="positionIdS" ></select>--%>
<%--						</div>--%>
<%--					</div>--%>
					<div class="form-group">
						<label class="col-sm-3 control-label">事项描述：</label>
						<div class="col-sm-8">
							<textarea class="form-control" id="memo" name="memo" placeholder="事项描述"></textarea>
						</div>
					</div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">表单温馨提示：</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" id="notice" name="notice" placeholder="表单温馨提示"></textarea>
                        </div>
                    </div>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 control-label">事项类型：</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input  class="form-control" name="type" id="type"  value="1" placeholder="事项类型"/>--%>
<%--						</div>--%>
<%--					</div>					--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 text-right">事项状态：</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input type="radio" id="acc_statusRadios1" valueSet="1" nameSet='active'  value="1"/>正常--%>
<%--							<input type="radio" id="acc_statusRadios0" valueSet="0" nameSet='active'  value="0"/>未激活--%>
<%--							<input type="radio" id="acc_statusRadios2" valueSet="2" nameSet='active'  value="2"/>已上线--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="form-group">--%>
<%--						<label class="col-sm-3 text-right">是否可一证通办：</label>--%>
<%--						<div class="col-sm-8">--%>
<%--							<input type="radio" id="acc_executable1" valueSet="1" name="type1" nameSet="executable" value="1" /> 是--%>
<%--							<input type="radio" id="acc_executable0" valueSet="0" name="type1" nameSet="executable" value="0"/> 否--%>
<%--						</div>--%>
<%--					</div>	--%>
				</form>
			</div>
    		<!--情形 -->
           	<div id="situationFormWrap" class="addFormWrap">
           		<form class="form-horizontal" id="situationForm" name="situationForm">
					<div id="situationGroup">
						<div class="situation">
							<div class="form-group base_margin-t-15">
								<label class="col-sm-3 control-label">情形1</label>
								<div class="col-sm-6">
									<input class="form-control situationlData" readOnly value="缺省情形"/>
								</div>
							</div>
							<div class="materialGroup">
							    <div class="material">
							    	<div class="form-group">
										<label class="col-sm-3 control-label">材料1</label>
										<div class="col-sm-6">
											<input type="hidden"  id=""/>
											<input class="form-control materialData"/>
										</div>
										<div class="col-sm-2">
											<i class="fa fa-trash-o deleteIcon" onclick="deleteMaterial(this)"></i>
										</div>
									</div>
									<div class="proveGroup">
										<div class="form-group">
											<label class="col-sm-3 control-label">证明</label>
											<div class="col-sm-6">
												<select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select>
											</div>
											<div class="col-sm-2">
											    <span class="addDeleteIcon">
											    	<i class="fa fa-plus-circle" onclick="addProve(this)"></i>
											    	<i class="fa fa-minus-circle" onclick="deleteProve(this)"></i>
											    </span>
											    <span class="moveIcon">
													<i class="fa fa-chevron-circle-up" onclick="upProve(this)"></i>
											    	<i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i>
											    </span>
											</div>
										</div>
									</div>
							    </div>
							</div>	
							<div class="addMaterial">
								<span onclick="addMterial(this)"><i class="fa fa-plus-circle"></i>添加材料</span>
							</div>
						</div>
					</div>
					<div class="addSituation">
						<span onclick="addSituation()"><i class="fa fa-plus-circle"></i>添加情形</span>
					</div>
				</form>
           	</div>	
    		<!--删除提示 -->
           	<div id="deleteTipWrap">
           		删除缺省情形材料可能会影响其他情形，您确定删除吗？
           	</div>
			<!-- 关联表单-->
           	<div id="formTempFormWrap" class="addFormWrap">
           		<form class="form-horizontal" id="formTempForm" name="formTempForm">
					<div class="form-group">
						<label class="col-sm-3">表单列表：</label>
						<div class="col-sm-6">
							<select class="form-control chosenSelect chosen-select-deselect formTempData" data-placeholder="请选择">
								<option value="">无关联表单</option>
							</select>
						</div>
					</div>	
				</form>	
			</div>
			<!-- 复制事项-->
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
					<input type="checkBox" value="1" id="deptOpt"/>部门优化
				</div>
				<label>职能</label>
				<select id="jobSelect" class="form-control base_width-120" style="display:inline">
					<option value=''>--请选择--</option>
					<option value='ga'>公安</option>
					<option value='mz'>民政</option>
				</select>
				<label>级别</label>
				<select id="levelSelect" class="form-control base_width-120"style="display:inline">
					<option value=''>--请选择--</option>
					<option value='2'>省级</option>
					<option value='3'>地市级</option>
				</select>
				<label>部门</label>
				<select id="deptSelect" class="form-control base_width-150"style="display:inline">
					<option value=''>--请选择--</option>
				</select>
				
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px'>
           			<label class='base_margin-r-10'>事项信息:</label>
	  				<input class="form-control" name="itemName2" id="itemName2"  placeholder="事项名/innerCode/code"/>
				</div>
				<div class='base_width-50' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary "onclick="searchItem2()"><span class="fa">查询</span></button>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px,margin-left: 50px'>
					
				</div>
				<div class='base_width-200' style='display:inline-block;position:relative;'>
					<label class='base_margin-r-10'>数源事项:</label> 
	  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
					<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="itemSelectId" name="itemSelectId" ></select>
				</div>
				<div class='base_width-300' style='display:inline-block;position:relative;'>
					<!-- <button class="btn btn-sm btn-primary base_margin-l-20" name="addItem" id="addItem"><span class="glyphicon glyphicon-step-forward"></span></button>-->
					<button class="btn btn-sm btn-primary base_margin-l-20" name="removeItem" id="removeItem"><span class="fa fa-minus-circle">移除事项</span></button>
					<!-- <button class="btn btn-sm btn-primary " name="addDestDept" id="addDestDept" ><span class="fa fa-plus-circle">清空事项</span></button>-->
					<button class="btn btn-sm btn-primary " name="removeAllItem" id="removeAllItem" ><span class="fa fa-minus-circle">清空事项</span></button>
					<!--<button class="btn btn-sm btn-primary " name="removeDestDept" id="removeDestDept"><span class="fa fa-minus-circle">移除部门</span></button>-->
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<label >数源事项列表</label>
				</div>
				<div class='col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center'>
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
			<div id='qrcodeModal'>
				<div class='row'>
					<div class='col-md-6'>
						<div>个人</div>
						<div id='qrcodePerson'></div>
					</div>
					<div class='col-md-6'>
						<div>法人</div>
						<div id='qrcodeCompany'></div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="table,tree,validator,page,chosen" name="p" />
		</jsp:include>
		<script type="text/javascript" src='../build/js/jquery.qrcode.js'></script>
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
		<script src='../build/js/treeSearch.js'></script>
<!-- 		<script type="text/javascript" src='../build/js/cardAjax.js'></script> -->
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
		<script type="text/javascript" src="../build/js/item.js?v=2"></script>
	</body>
</html>