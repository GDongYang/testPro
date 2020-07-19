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
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,icon,tree" name="p"/>
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
					
					<!-- 左侧树 -->
					<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
						<!-- <a href="#" class="btn btn-info btn-block btn-xs margin-bottom">左侧显示</a> -->
						
						<div name="boxSkin" class="box base_box-area-aqua">
							<div class="box-header with-border">
								<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">部门列表</span></h3>
								<div class="box-tools pull-right">
									<button type="button" class="btn btn-xs btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
								</div>
							</div>
							<div class="box-body no-padding">
								<ul id="treeDemo" class="ztree"></ul>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<div class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
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
											<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="names" name="name" placeholder="事项名称"/>
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
								<!-- <button type="button" class="btn btn-xs btn-info" onClick="copy()">复制</button> -->
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
											<th data-field="name"  >事项名称</th>
											<th data-field="code">事项编码</th>
											<th data-field="departmentName" >单位名称</th>
											<th data-field="executable" data-formatter="executableFormatter">一证通办</th>
											<!-- <th data-field="positionName" >岗位名称</th> -->
										<!-- 	<th data-field="memo" >事项说明</th> -->
											<!-- 	<th data-field="active" data-formatter="formatActive">事项状态</th> -->
										</tr>
									</thead>
								</table>
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.box-body -->
					</div>
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
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >权利编码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="code" name="code" placeholder="权利编码" />
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="departmentId">所属部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="departmentId" name="departmentId" tips-message="请选择部门"></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >岗位名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择岗位" -->
						<select class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="positionId" name="positionIdS" ></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >证件权限(必要)</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect" multiple="multiple" id="certTempId" name="certTempIdS" ></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >证件权限</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect" multiple="multiple" id="certTempId1" name="certTempIdS1" ></select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="memo">事项描述</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area"><!-- data-tips-message="请选择事项描述" -->
						<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="事项描述"></textarea>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group" hidden="true">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="type">事项类型</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="hidden" class="form-control base_input-text" id="type" name="type" value="1" placeholder="事项类型" />
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">事项状态</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="acc_statusRadios1" name="active" value="true" tips-message="请选择事项状态"/> 正常</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="acc_statusRadios0" name="active" value="false"/> 未激活</label>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="acc_statusRadios1">是否可一证通办</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<label><input type="radio" id="acc_statusRadios3" name="executable" value="1" tips-message="请选择一证通办状态"/> 是</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio" id="acc_statusRadios2" name="executable" value="0"/> 否</label>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,tree" name="p"/>
		</jsp:include>
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var allTemp;
			var allDepart;
			var allPosit;
			var selectCode="";	//选中树节点
			var lastAjax = null;
			var deptObj = {};
			//ztree setting
			var setting = {
				data: {
					simpleData: {
						enable: true,
						/* idKey: "oid",
						pIdKey: "poid", */
						idKey: "id",
						pIdKey: "parentId",
						rootPId: null
					}
				},callback: {
					onClick: zTreeOnClick,
					onExpand:zTreeOnClick
				}
			};
			
			function zTreeOnClick(event, treeId, treeNode) {
				//alert(treeNode.pid+"   "+JSON.stringify(treeNode))
				if (treeNode.id == 0) {
					selectCode = "";
				} else {
					selectCode = treeNode.id;
					var id = selectCode;
					var data= {
						id:id
					}
					if((treeNode.isParent == true && typeof(treeNode.children) =="undefined" )){
						$.ajax({
						   	url:"departmentAction!findTreeByParentId.action",
						   	type:"POST",
						   	dataType:"json",
						   	async:false,
						   	data:data,
						  	error:function(request,textStatus, errorThrown){
						  		fxShowAjaxError(request, textStatus, errorThrown);
							},
						   	success:function(data){
						   		if(data && data.departments){
									//var zTree = $.fn.zTree.init($("#treeDemo"), departSetting, data.departments);
									$(data.departments).each(function(i, item) {
										deptObj[item.id] = item.name;
										if(item.isLeaf == "1"){
											item.isParent=true;
										}
									});
				                   // $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				                    $.fn.zTree.getZTreeObj("treeDemo").addNodes(treeNode,eval(data.departments));
						   		} /*else {
						   			var list= [];
									$.fn.zTree.init($("#treeDemo"), departSetting, list);
						   		}*/
							}
						});
					}
				}
				if (lastAjax != null)
					lastAjax.abort();
				//请求下一个
				$("#dataTable").bootstrapTable('selectPage', 1);
			}
			
			function idFormatter(value, row) {
				return index++;
			}
			function executableFormatter(value,row){
				if(value == 0){
					return "否";
				}
				if(value == 1){
					return "是";
				}
			}
			function itemNameFormatter(value, row){
			
				return "<div onclick=\"Show(this)\"><span class=\"a\">"+value+"</span></div><div style=\"display:none\"><input class=\"form-control base_input-text\" type=\"text\" value=\""+value+"\" tips-message=\"请输入事项名称\" style=\"float:left;width: auto;\"/>"+
						"<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateC('name',this,'"+row.id+"')\"></button>"+
						"<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
			}
			function deprtFormatter(value, row) {
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
			
			//得到所有部门
			function getDepartmentList() {
				$("#dataform input").val('');
				$("#dataform input[type='checkbox']").iCheck("uncheck");
				/* $.ajax({
				   	url:"departmentAction!findTree.action",
				   	type:"POST",
				   	dataType:"json",
				   	async:true,
				  	error:function(request,textStatus, errorThrown){
				  		fxShowAjaxError(request, textStatus, errorThrown);
					},
				   	success:function(data){
				   		if(data && data.departments){
							$.fn.zTree.init($("#treeDemo"), setting, data.departments);
							var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
							var nodes = treeObj.getNodes();
                            $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				   		} else {
				   			var list= [];
							$.fn.zTree.init($("#treeDemo"), setting, list);
							var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
							var nodes = treeObj.getNodes();
				   		}
					}
				}); */
				var data= {
						id: "0"
				};
				
				$.ajax({
				   	url:"departmentAction!findTreeByParentId.action",
				   	type:"POST",
				   	dataType:"json",
				   	async:true,
				   	data:data,
				  	error:function(request,textStatus, errorThrown){
				  		fxShowAjaxError(request, textStatus, errorThrown);
					},
				   	success:function(data){
				   		if(data && data.departments){
							$(data.departments).each(function(i, item) {
								deptObj[item.id] = item.name;
								item.isParent = true;
							});
							$.fn.zTree.init($("#treeDemo"), setting, data.departments);
		                    $.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
				   		} else {
				   			var list= [];
							$.fn.zTree.init($("#treeDemo"), departSetting, list);
				   		}
					}
				});
			}
			
			function loadDepart() {
				$.ajax({
					type : 'POST',
					url : 'departmentAction!findList.action',
					dataType : 'json',
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						allDepart = data.departments;
						var htmlStr = "<option value=\"\"></option>";
						for (var i = 0; i < data.departments.length; i++) {
							htmlStr += "<option value=\""+data.departments[i].id+"\">"
									+ data.departments[i].name
									+ "</option>";
						}
						$("#department").append(htmlStr);
						$("#departmentId").append(htmlStr);
						selectUpdated($("#department"));
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
				getDepartmentList();	//加载所有部门
				loadDepart();
				//bootstrapTable单击事件
				$("#dataTable").on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				
				dataTable = $('#dataTable');
				dataTable.on('expand-row.bs.table',expandRow);
				findCertTemp();
			});
			
            function expandRow(event,index,row){
				
				//detail的div
				var result = $('#rowDetail'+index);
				//已关联的证件的列表
				result.append($('<ul class="list-group">'));
				//已关联的证件的id
				var tempIds = findRTemp(row.id,'1');
				var tempIds1 = findRTemp(row.id,'0');
				tempIds = $.map(tempIds,function(item){return parseInt(item)});
				tempIds1 = $.map(tempIds1,function(item){return parseInt(item)});
				//选择框，用于添加未关联的的证件
                var $select = $('<select class="form-control chosen-select-deselect" chosen-position="true" style="display:inline-block;width:75%;border-radius:4px;margin-right:20px">')
				$select.append($('<option>').html('--------------------------------'));
				var $select1 = $('<select class="form-control chosen-select-deselect" chosen-position="true" style="display:inline-block;width:75%;border-radius:4px;margin-right:20px">')
				$select1.append($('<option>').html('--------------------------------'));
				//将证件添加到已关联列表
				function addC(cert,isMust){
					var $li = $('<li class="list-group-item" style="display:inline-block;width:25%;border-radius: 4px;">');
					var removeIcon = $('<span style="float:right" class="glyphicon glyphicon-remove" aria-hidden="true"></span>');
					
					//删除已关联的证件
					/* removeIcon.click(function(){
						var $this = $(this)
						var $parentLi = $this.parent('li');
						removedId = $parentLi.attr('certId');
						$select.children('option[certId='+removedId+']').attr('disabled',false);
						$select1.children('option[certId='+removedId+']').attr('disabled',false);
						$parentLi.remove();
						selectUpdated($select);
						selectUpdated($select1);
					}) */
					if(isMust == '1')
					 	result.children('ul').append($li.append('<lable style="color:red">(必要)</lable>'+cert.name).attr('certId',cert.id));
					if(isMust == '0')
						result.children('ul').append($li.append('<lable style="color:green">(非必要)</lable>'+cert.name).attr('certId',cert.id));
					if(isMust == '1') {
						if($.inArray(cert.id,tempIds)<0){
							tempIds.push(cert.id);
						}
					} else if(isMust == '0') {
						if($.inArray(cert.id,tempIds1)<0){
							tempIds1.push(cert.id);
						}
					}
					
				}
			
				//初始化已关联列表（必要）
				for(var i=0;i<tempIds.length;i++){
					$.each(allTemp,function(j,temp){
						if(tempIds[i] == temp.id)
							addC(temp,'1');
					});	
				}
				
				for(var i=0;i<tempIds1.length;i++){
					$.each(allTemp,function(j,temp){
						if(tempIds1[i] == temp.id)
							addC(temp,'0');
					});	
				}
				
				//初始化选择框
				/* $.each(allTemp,function(i,item){
					var $option = $('<option>').append(item.name).attr('certId',item.id).prop('cert',item)
					if($.inArray(item.id , tempIds)>-1)
						$option.attr('disabled',true);
					if($.inArray(item.id , tempIds1)>-1)
						$option.attr('disabled',true);
					$select.append($option);
					$select1.append($option);
				})
				//绑定选择框事件
				$select.change(function(){
					var $selectedOption = $(this).children(':selected');
					if(!$selectedOption.attr('certId'))
						return
					addC($selectedOption.prop('cert'),'1');
					$selectedOption.attr('disabled',true);
					selectUpdated($select);
				});
				$select1.change(function(){
					var $selectedOption = $(this).children(':selected');
					if(!$selectedOption.attr('certId'))
						return
					addC($selectedOption.prop('cert'),'0');
					$selectedOption.attr('disabled',true);
					selectUpdated($select1);
				});
				
				result.append($select);
				result.append($select1);
				selectConfig($select);
				selectConfig($select1); */
				//创建提交按钮
				/* var $submit = $('<button type="button" class="btn btn-sm btn-info">保存</input>');
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
						if($(item).attr('isMust')== '1')
							param+='&certTempIdS='+$(item).attr('certId');
						else if($(item).attr('isMust')== '0') 
							param+='&certTempIdS1='+$(item).attr('certId');
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
							//refresh();
							dataTable.bootstrapTable('refresh');
						}
					});
				})
				result.append($submit) */
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
							$("#certTempId").empty();
							$("#certTempId1").empty();
						if (data.result != null) {
							allTemp = data.result;
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].id+"\">"
										+ data.result[i].name + "</option>";
							}
							$("#certTempId").append(htmlStr);
							selectUpdated($("#certTempId"));
							
							$("#certTempId1").append(htmlStr);
							selectUpdated($("#certTempId1"));
						}
					}
				});
			}
			
			$("#departmentId").on("change", function(e) {
				var techDirec = $("#departmentId").val();
				findPositionAll(techDirec);
			});
			
			//岗位
			function findPositionAll(deptId) {
				$("#positionId").empty();
				$.ajax({
					cache : false,
					type : "get",
					url : 'positionAction!findByDept.action',
					dataType : 'json',
					async : false,
					data : {
						searchDept : deptId
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
				$("#code").val('');
				$("#departmentId").val('');
				$('#memo').val('');
			}
			//刷新页面
			function refresh() {
                $("#dataTable").bootstrapTable('refresh');
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
				lastAjax = $.ajax({
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
				dataTable.bootstrapTable('selectPage', 1);
				// dataTable.bootstrapTable('refresh', {
				// 	queryParams : getParams()
				// });
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#names').val("");
				$('#department').val("");
				selectUpdated($("#department"));
				dataTable.bootstrapTable('refresh');
			}
			
			//新增
			function add() {
				initFormElement_add();
				findCertTemp();
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeObj.getSelectedNodes();
				if (nodes.length != 0 && nodes[0].id != 0) {
					$("#departmentId").val(nodes[0].id);
					findPositionAll(nodes[0].id);
				}
				
				selectUpdated($("#departmentId"));//下拉框变动更新
				selectUpdated($("#positionId"));//下拉框变动更新
				selectUpdated($("#certTempId"));//【必要】
				selectUpdated($("#certTempId1"));
				
				$('#acc_statusRadios1').iCheck("check");
				
				var dialog = getJqueryDialog();
				dialog.Container = $("#dataFormWrap");
				dialog.Title = "事项新增";
				dialog.CloseOperation = "destroy";
				dialog.BeforeClose = function(){
					$('#departmentId').val("");//$("#departmentId").empty();
					$("#positionId").empty();
					$("#certTempId").empty();//必要
					$("#certTempId1").empty();
					tipsRegionHintAddVerify($("#dataFormWrap"));
				};
				dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						if (tipsRegionValidator($("#dataForm"))) {
							var dataStr = $('#dataForm').serialize() + "&departmentName=" + $("#departmentId").find("option:selected").text();
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
				
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length == 1) {
					$("#name").val(obj[0].name);
					$("#memo").val(obj[0].memo);
					$("#code").val(obj[0].code);
					if (obj[0].active == 1) {
						$("#dataForm input[id='acc_statusRadios0']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios1']").iCheck('check');//选中radio
					} else {
						$("#dataForm input[id='acc_statusRadios1']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios0']").iCheck('check');//选中radio
					}
					if (obj[0].executable == 1) {
						$("#dataForm input[id='acc_statusRadios2']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios3']").iCheck('check');//选中radio
					} else {
						$("#dataForm input[id='acc_statusRadios3']").iCheck('uncheck');//未选中radio
						$("#dataForm input[id='acc_statusRadios2']").iCheck('check');//选中radio
					}
					$("#departmentId").val(obj[0].departmentId);
					findPositionAll(obj[0].departmentId);
					findRPosition(obj[0].id);
					//查询证件权限
					$("#certTempId").val(findRTemp(obj[0].id,'1'));//必要
					$("#certTempId1").val(findRTemp(obj[0].id,'0'));
					var dialog = getJqueryDialog();
					dialog.Container = $("#dataFormWrap");
					dialog.Title = "事项修改";
					dialog.CloseOperation = "destroy";
					dialog.BeforeClose = function(){
						$('#departmentId').val("");
						//$("#departmentId").empty();
						$("#positionId").empty();
						$("#certTempId").empty();
						$("#certTempId1").empty();
						tipsRegionHintAddVerify($("#dataFormWrap"));
					};
					//dialog.Open = function(){tipsRegionHintRemoveVerify($("#dataFormWrap"));};
					dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator($("#dataForm"))) {
								var dataStr = $('#dataForm').serialize()+"&id="+obj[0].id + "&departmentName=" + $("#departmentId").find("option:selected").text();
								//console.log(dataStr);
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
										dataTable.bootstrapTable('selectPage', 1);
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
				selectUpdated($("#certTempId1"));//下拉框变动更新
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
			function findRTemp(itemId,isMust){
				var result='';
				$.ajax({
					cache : false,
					type : "POST",
					url : 'itemAction!findRTemp.action',
					dataType : 'json',
					data : {
						id : itemId,
						isMust:isMust
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
										dataTable.bootstrapTable('selectPage', 1);
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
				var params = {
					'name' : name,
					'departmentId' : selectCode,
					'type':1
				}
				return params;
			}
			
			function copy() {
				window.open("ItemChoice.jsp");
			}
		</script>
	</body>
</html>