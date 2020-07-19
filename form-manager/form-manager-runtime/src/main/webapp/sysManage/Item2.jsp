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
		<title>事项管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,icon" name="p"/>
		</jsp:include>
		<style type="text/css">
			.a { 
				border-bottom:1px dashed #99CCFF;
				cursor:pointer;
				padding-top:5px;
			}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<div class="base_query-area">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<form id="searchForm">
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 pull-right">
										<div class="form-group text-right base_options-area">
											<button type="button" class="btn btn-sm btn-info" onClick="search()">搜索</button>
											<button type="button" class="btn btn-sm btn-info" onClick="clean()">清空</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div class="row">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="names" name="name" placeholder="事项名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="department">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="department" id="department"></select>
												</div>
											</div>
											<!-- /.base_query-group -->
										</div>
										<!-- /.row -->
									</div>
								</div>
							</form>
						</div>
						<h2 class="page-header base_page-header">
							<!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> -->
						</h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua" >
						<div class="box-header with-border base_box-header">
							<h3 class="box-title">
								<i class="fa fa-tag"></i> <span class="base_text-size-15">事项管理</span>
							</h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap"
								   data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="true"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页"
									data-detail-view="true"
          							data-detail-formatter="detailFormatter">
									<thead style="text-align: center;">
										<tr>
											<th data-radio="true"></th>
											<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="name"  data-formatter="itemNameFormatter">事项名称</th>
											<th data-field="code">事项编码</th>
											<th data-field="departmentName" data-formatter="deprtFormatter">单位名称</th>
											<th data-field="positionName" data-formatter="formatPosition" >岗位名称</th>
											<th data-field="parent" data-formatter="parentFormat">主项名称</th>
											<th data-field="memo" data-formatter="memoFormat">事项说明</th>
											<th data-field="active" data-formatter="formatActive">事项状态</th>
										</tr>
									</thead>
								</table>
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		<!-- 新增、修改 -->
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataForm" name="dataForm">
				<!-- <input type="hidden" id="postionId" name="id"/> -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">事项名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="事项名称" tips-message="请输入事项名称" />
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="departmentId">所属部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="departmentId" name="departmentId" tips-message="请选择部门"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="positionId">岗位名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择岗位" -->
						<select class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="positionId" name="positionIdS" tips-message="请选择岗位"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="certTempId">证件权限</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="certTempId" name="certTempIdS" tips-message="请选择证件"></select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="parent">主项名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="parent" name="parent" placeholder="主项名称" />
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group" hidden="true">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="type">事项类型</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="hidden" class="form-control base_input-text" id="type" name="type" value="2" placeholder="事项类型" />
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">事项描述</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area"><!-- data-tips-message="请选择事项描述" -->
						<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="事项描述"></textarea>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">事项状态</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="acc_statusRadios1" name="active" value="true" tips-message="请选择事项状态"/> 正常</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="acc_statusRadios0" name="active" value="false"/> 未激活</label>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog" name="p"/>
		</jsp:include>
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var allTemp;
			var allDepart;
			var allPosit;
			
			function idFormatter(value, row) {
				return index++;
			}
			function itemNameFormatter(value, row){
				if(value==null || value == ""){
					value="-";
				}
				return "<div onclick=\"Show(this)\"><span class=\"a\">"+value+"</span></div><div style=\"display:none\"><input class=\"form-control base_input-text\" type=\"text\" value=\""+value+"\" tips-message=\"请输入事项名称\" style=\"float:left;width: auto;\"/>"+
						"<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('name',this,'"+row.id+"')\"></button>"+
						"<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
			}
			function deprtFormatter(value, row) {
				if(value==null || value == ""){
					value="-";
				}
				var html="<div onclick=\"Show(this)\"><span class=\"a\">"+value+"</span></div><div style=\"display:none\"><select value=\""+value+"\" class=\"form-control chosen-select-deselect\" style=\"float:left;width: 150px;\">";
				for(var i=0;i<allDepart.length;i++) {
					if((allDepart[i]).id == row.departmentId) {
						html += "<option selected = \"selected\" value=\""+allDepart[i].id+"\">"
						+ allDepart[i].name
						+ "</option>";
					} else {
						html += "<option value=\""+allDepart[i].id+"\">"
						+ allDepart[i].name
						+ "</option>";
					}
				}
				html +="</select><button style=\"margin:2px 5px;\" type=\"button\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('departmentId',this,'"+row.id+"')\"></button>"+
				"<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
				return html;
				
			}
			function parentFormat(value,row){
				return "<div onclick=\"Show(this)\"><span class=\"a\">"+value+"</span></div><div style=\"display:none\"><input class=\"form-control base_input-text\" type=\"text\" value=\""+value+"\" tips-message=\"请输入主项名称\" style=\"float:left;width: auto;\"/>"+
				"<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('parent',this,'"+row.id+"')\"></button>"+
				"<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
			}
			function memoFormat(value, row) {
				var val = value;
				if(value == "" || value == null) {
					val = "-";
					value="";
				}
					
				return "<div onclick=\"Show(this)\"><span class=\"a\">"+val+"</span></div><div style=\"display:none\"><input class=\"form-control base_input-text\" type=\"text\" value=\""+value+"\" style=\"float:left;width: auto;\"/>"+
				"<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('memo',this,'"+row.id+"')\"></button>"+
				"<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
			}
			function Show(obj){
				$(obj).hide();
				$(obj).next().show();
			}
			function Hidden(obj){
				$(obj.parentNode).hide();
				$(obj.parentNode).prev().show();
				/* refresh(); */
			}
			function updateC(colName,obj,id) {
				var value = "";
				var ele = $(obj).prev();
				if(colName!="memo" && (ele.val()==null || ele.val() == "")) {
					Modal.alert({
						msg : '数据不能为空！',
						title : '提示',
						btnok : '确定',
						btncl : '取消'
					});
					return;
				}
				if(ele[0].tagName == "SELECT" && ele[0].multiple){
					$.each(ele.val(),function(i, val){
						value +=colName+"="+val+"&";
					});
					
				} else {
					value = colName+"="+ele.val()+"&";
				}
				var dataStr= value+"id="+ id;
				console.log(dataStr);
				$.ajax({
					type : "POST",
					url : 'itemAction!updateC.action',
					dataType : 'json',
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						Modal.alert({
							msg : '修改成功',
							title : '提示',
							btnok : '确定',
							btncl : '取消'
						});
						refresh();
					}
				});
			}
			
			
			function detailFormatter(index, row) {
				return '<div id="rowDetail'+index+'"></div>';
		    }
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				selectConfig();//下拉框初始化
				
				//bootstrapTable单击事件
				$("#dataTable").on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				dataTable = $('#dataTable');
				dataTable.on('expand-row.bs.table',expandRow);
				//加载部门下拉
				findDepartmentAll();
				
				findCertTemp();
			});
			
            function expandRow(event,index,row){
				
				//detail的div
				var result = $('#rowDetail'+index);
				//已关联的证件的列表
				result.append($('<ul class="list-group">'));
				//已关联的证件的id
				var tempIds = findRTemp(row.id);
				tempIds = $.map(tempIds,function(item){return parseInt(item)});
				//选择框，用于添加未关联的的证件
				var $select = $('<select class="form-control" style="display:inline-block;width:75%;border-radius:4px;margin-right:20px">')
				$select.append($('<option>').html('--------------------------------'))
				//将证件添加到已关联列表
				function addC(cert){
					var $li = $('<li class="list-group-item" style="display:inline-block;width:25%;border-radius: 4px;">');
					var removeIcon = $('<span style="float:right" class="glyphicon glyphicon-remove" aria-hidden="true"></span>');
					
					//删除已关联的证件
					removeIcon.click(function(){
						var $this = $(this)
						var $parentLi = $this.parent('li');
						removedId = $parentLi.attr('certId');
						$select.children('option[certId='+removedId+']').attr('disabled',false);
						$parentLi.remove();
					})
					result.children('ul').append($li.append(cert.name).attr('certId',cert.id).append(removeIcon));
					if($.inArray(cert.id,tempIds)<0){
						
						tempIds.push(cert.id);
					}
				}
			
				//初始化已关联列表
				for(var i=0;i<tempIds.length;i++){
					$.each(allTemp,function(j,temp){
						if(tempIds[i] == temp.id)
							addC(temp);
					});	
				}
				
				//初始化选择框
				$.each(allTemp,function(i,item){
					var $option = $('<option>').append(item.name).attr('certId',item.id).prop('cert',item)
					if($.inArray(item.id , tempIds)>-1)
						$option.attr('disabled',true);
					
					$select.append($option);
				})
				//绑定选择框事件
				$select.change(function(){
					var $selectedOption = $(this).children(':selected');
					if(!$selectedOption.attr('certId'))
						return
					addC($selectedOption.prop('cert'));
					$selectedOption.attr('disabled',true);
				});
				
				
				result.append($select);
				//创建提交按钮
				var $submit = $('<button type="button" class="btn btn-sm btn-info">保存</input>');
				$submit.click(function(){
					
					var param='id='+row.id;
					var $lis = result.find('li[certId]');
					if($lis.length == 0 ) {
						Modal.alert({
							msg : '证书不能为空！',
							title : '提示',
							btnok : '确定',
							btncl : '取消'
						});
						return;
					}
					
					$.each($lis,function(i,item){
						param+='&certTempIdS='+$(item).attr('certId');
					
					})
					//请求后台提交数据
					$.ajax({
						cache : true,
						type : "POST",
						url : 'itemAction!updateC.action', 
						dataType : 'json',
						data : param,
						error : function(request, textStatus, errorThrown) {
							fxShowAjaxError(request, textStatus, errorThrown);
						},
						success : function(data) {
							Modal.alert({
								msg : '修改成功',
								title : '提示',
								btnok : '确定',
								btncl : '取消'
							});
							refresh();
						}
					});
				})
				result.append($submit)
			}
			
			function findCertTemp() {
				$.ajax({
					cache : false,
					type : "get",
					url : 'certTempAction!findAll.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						if (data.result != null) {
							$("#certTempId").empty();
							allTemp = data.result;
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "</option>";
							}
							$("#certTempId").append(htmlStr);
						}
					}
				});
			}
			
			function findDepartmentAll() {
				$.ajax({
					cache : true,
					type : "get",
					url : 'departmentAction!findTree.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						/* 	console.log(data.departments);
							console.log(data.departments[0].name); */
						if (data.departments != null) {
							allDepart = data.departments;
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.departments.length; i++) {
								htmlStr += "<option value=\""+data.departments[i].id+"\">"
										+ data.departments[i].name
										+ "</option>";
							}
							//console.log(htmlStr);
							$("#department").append(htmlStr);
							$("#departmentId").append(htmlStr);
						}
						//$("#department")
					}
				});
				//绑定下拉框对象
				selectUpdated($("#department"));
			}
			$("#departmentId").on("change", function(e) {
				var techDirec = $("#departmentId").val();
				findPositionAll(techDirec);
			});
			
			//岗位
			function findPositionAll(positionId) {
				$("#positionId").empty();
				$.ajax({
					cache : false,
					type : "get",
					url : 'positionAction!findByDept.action',
					dataType : 'json',
					async : false,
					data : {
						searchDept : positionId
					},
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//console.log(data);
						var htmlStr = "";
						if (data.result != null) {
							allPosit = data.result;
							htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "</option>";
							}
							//console.log(htmlStr);
						}
						$("#positionId").append(htmlStr);
						//绑定下拉框对象
						selectUpdated($("#positionId"));
					}
				});
			}
			
			function formatActive(val,row) {
				var html="";
				if (val == 1) {
					html+= '<div onclick=\"Show(this)\"><span class=\"a\"><font style="color:#0000FF">正常</font></span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: auto;\"><option value=\"true\" selected=\"selected\">正常</option><option value=\"false\">未激活</option></select>';
				} else {
					html+= '<div onclick=\"Show(this)\"><span class=\"a\"><font style="color:#FF0000">未激活</font></span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: auto;\""><option value=\"true\" >正常</option><option value=\"false\" selected=\"selected\">未激活</option></select>';
				}
				html+="<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('active',this,'"+row.id+"')\"></button>"+
				"<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
				return html;
			}
			function formatPosition(value,row,index){
				var values = "-";
				$.ajax({
					cache : false,
					type : "POST",
					url : 'itemAction!findPositionName.action',
					dataType : 'json',
					data : {
						id : row.id
					},
					async : false,
					error : function(request, textStatus,
							errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						if(data != null && data.result != ""){
							values = data.result;
						}
					}
				});
				var html="<div onclick=\"Show(this)\"><span class=\"a\">"+values+"</span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: 200px;\" chosen-position=\"true\" multiple=\"multiple\">";
				allPosit = null;
				findPositionAll(row.departmentId);
				if(allPosit != undefined && allPosit != null) {
					$.each(allPosit,function(i,val){
						if(val.name == values){
							html += "<option selected = \"selected\" value=\""+val.id+"\">"
							+ val.name
							+ "</option>";
						} else {
							html += "<option value=\""+val.id+"\">"
							+ val.name
							+ "</option>";
						}
					});
				}
				
				html += "</select><button  style=\"margin:2px 5px;\" type=\"button\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('positionIdS',this,'"+row.id+"')\"></button>"+
				        "<button  type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
				return html; 
			}
			function initFormElement_add() {
				$("#name").val('');
				$("#departmentId").val('');
				$('#memo').val('');
			}
			//刷新页面
			function refresh() {
				clean();
				search();
			}
			//对象列表的ajax请求
			var todoList;
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var param = getParams();
				//console.log(param);
				var datas;
				var items;
				$.ajax({
					type : 'post',
					url : 'itemAction!findPaeTable.action?pageNum=' + pageNum
							+ '&pageSize=' + pageSize,
					dataType : 'json',
					cache : false,
					async : true,
					data : param,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						datas = data.page;
						if (datas != null)
							count = datas.count;
						items = datas.items ? datas.items : [];
						params.success({
							total : count,
							rows : items
						});
						params.complete();
						
						$('input').iCheck({
							checkboxClass : 'icheckbox_square-blue',
							radioClass : 'iradio_square-blue',
							increaseArea : '20%' // optional
						});
						//iCheck绑定事件,操作bootstrapTable点击行事件
						drawICheck("dataTable");
						/* $('#dataTable input[type=radio]').on('ifChecked',function(event){
							$(this).closest("tr").addClass("selected");
							$(this).attr("checked", true);
							var dataIndex = $(this).attr("data-index");
							$('#dataTable').bootstrapTable("check",dataIndex);
						});
						$('#dataTable input[type=radio]').on('ifUnchecked',function(event){
							$(this).closest("tr").removeClass("selected");
							$(this).attr("checked", false);
							$('#dataTable').bootstrapTable("uncheckAll");
						}); */
					}
				});
			}
			//查询
			function search() {
				//dataTable.bootstrapTable('selectPage', 1);
				dataTable.bootstrapTable('refresh', {
					queryParams : getParams()
				});
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#names').val("");
				$('#department').val("");
				selectUpdated($("#department"));
			}
			
			//新增用户
			function add() {
				
				findCertTemp();
				//findDepartmentAll();
				selectUpdated($("#departmentId"));//下拉框变动更新
				selectUpdated($("#positionId"));//下拉框变动更新
				selectUpdated($("#certTempId"));
				
				initFormElement_add();
				$('#acc_statusRadios1').iCheck("check");
				
				var dialog = getJqueryDialog();
				dialog.Container = $("#dataFormWrap");
				dialog.Title = "事项新增";
				dialog.CloseOperation = "destroy";
				dialog.BeforeClose = function(){
					$('#departmentId').val("");//$("#departmentId").empty();
					$("#positionId").empty();
					$("#certTempId").empty();
					tipsRegionHintAddVerify($("#dataFormWrap"));
				};
				dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						if (tipsRegionValidator($("#dataForm"))) {
							var dataStr = $('#dataForm').serialize();
							$.ajax({
								cache : true,
								type : "POST",
								url : 'itemAction!save.action',
								dataType : 'json',
								data : dataStr,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus,
											errorThrown);
								},
								success : function(data) {
									Modal.alert({
										msg : '新增成功',
										title : '提示',
										btnok : '确定',
										btncl : '取消'
									});
									refresh();
								}
							});
							$(this).dialog("close");
						}
					}
				}
				dialog.show();
			}
			
			function update() {
				findCertTemp();
				//findDepartmentAll();
				
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					$("#name").val(obj[0].name);
					$("#memo").val(obj[0].memo);
					if (obj[0].active == 1) {
						$("#dataForm input[id='acc_statusRadios0']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios1']").iCheck('check');//选中radio
					} else {
						$("#dataForm input[id='acc_statusRadios1']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios0']").iCheck('check');//选中radio
					}
					$("#departmentId").val(obj[0].departmentId);
					findPositionAll(obj[0].departmentId);
					/* var a=[7,13];
					$("#positionId").val(a); */
					$("#positionId").val(obj[0].positionId);
					//查询证件权限
					$("#certTempId").val(findRTemp(obj[0].id));
					findRPosition(obj[0].id);
					//$("#departmentId").append('<option value="'+obj[0].departmentId+'">'+obj[0].departmentName+'</option>');
					var dialog = getJqueryDialog();
					dialog.Container = $("#dataFormWrap");
					dialog.Title = "事项修改";
					dialog.CloseOperation = "destroy";
					dialog.BeforeClose = function(){
						$('#departmentId').val("");
						//$("#departmentId").empty();
						$("#positionId").empty();
						$("#certTempId").empty();
						tipsRegionHintAddVerify($("#dataFormWrap"));
					};
					//dialog.Open = function(){tipsRegionHintRemoveVerify($("#dataFormWrap"));};
					dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator($("#dataForm"))) {
								var dataStr = $('#dataForm').serialize()+"&id="+obj[0].id;
								//return;
								$.ajax({
									cache : true,
									type : "POST",
									url : 'itemAction!update.action', 
									dataType : 'json',
									data : dataStr,
									async : false,
									error : function(request, textStatus,
											errorThrown) {
										fxShowAjaxError(request, textStatus,
												errorThrown);
									},
									success : function(data) {
										Modal.alert({
											msg : '修改成功',
											title : '提示',
											btnok : '确定',
											btncl : '取消'
										});
										refresh();
									}
								});
								$(this).dialog("close");
							}
						}
					}
					dialog.show();
				} else {
					Modal.alert({
						msg : '请选择要修改的数据',
						title : '提示',
						btnok : '确定',
						btncl : '取消'
					});
				}
				selectUpdated($("#departmentId"));//下拉框变动更新
				selectUpdated($("#positionId"));//下拉框变动更新
				selectUpdated($("#certTempId"));//下拉框变动更新
			}
			function findRPosition(itemId){
				$.ajax({
					cache : false,
					type : "POST",
					url : 'itemAction!findPositionId.action',
					dataType : 'json',
					data : {
						id : itemId
					},
					async : false,
					error : function(request, textStatus,
							errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						$("#positionId").val(data.result);
					}
				});
			}
			function findRTemp(itemId){
				var result='';
				$.ajax({
					cache : false,
					type : "POST",
					url : 'itemAction!findRTemp.action',
					dataType : 'json',
					data : {
						id : itemId
					},
					async : false,
					error : function(request, textStatus,
							errorThrown) {
						fxShowAjaxError(request, textStatus,
								errorThrown);
					},
					success : function(data) {
						result = data.result;
					}
				});
				return result;
			}
			
			function remove() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if (rows != 0) {
					var row = rows[0];
					Modal.confirm({
						msg : "是否删除该事项？"
					}).on(function(e) {
						if(e){
							$.ajax({
								cache : false,
								type : "POST",
								url : 'itemAction!remove.action',
								dataType : 'json',
								data : {
									id : row.id
								},
								async : true,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({
										msg : '事项删除成功',
										title : '提示',
										btnok : '确定',
										btncl : '取消'
									}).on(function() {
										refresh();
									});
								}
							});
						}
					});
				} else {
					Modal.alert({
						msg : '请选择要删除的事项',
						title : '提示',
						btnok : '确定',
						btncl : '取消'
					});
				}
			}
			/* 			
			 */
			function getParams() {
				var name = $("#names").val();
				var department = $("#department").val();
				var params = {
					'name' : name,
					'departmentId' : department,
					'type' : 2
				}
				return params;
			}
		</script>
	</body>
</html>