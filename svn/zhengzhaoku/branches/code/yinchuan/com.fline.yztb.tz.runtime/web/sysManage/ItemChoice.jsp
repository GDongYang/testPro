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
			<jsp:param value="mcustomscrollbar,table,tips,chosen,dialog,icon,tree" name="p"/>
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
								<button type="button" class="btn btn-xs btn-info" onClick="add()">确认</button>
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap"
								   data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="false"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页"
									data-response-handler="responseHandler">
									<thead style="text-align: center;">
										<tr>
											<th data-field="state" data-checkbox="true"></th>
											<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="name"  >事项名称</th>
											<th data-field="code">事项编码</th>
											<th data-field="departmentName" >单位名称</th>
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
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="departmentId">所属部门</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="departmentId" name="departmentId" tips-message="请选择部门"></select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >岗位名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择岗位" -->
						<select class="form-control chosen-select-deselect" chosen-position="true" multiple="multiple" id="positionId" name="positionIdS" ></select>
					</div>
				</div>
			</form>
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,tips,chosen,dialog,tree" name="p"/>
		</jsp:include>
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var allDepart;
			var allPosit;
			var selectCode="";	//选中树节点
			var lastAjax = null;
			var selections = [];
			
			//ztree setting
			var setting = {
				data: {
					simpleData: {
						enable: true,
						idKey: "oid",
						pIdKey: "poid",
						rootPId: null
					}
				},callback: {
					onClick: zTreeOnClick,
				}
			};
			
			function zTreeOnClick(event, treeId, treeNode) {
				//alert(treeNode.pid+"   "+JSON.stringify(treeNode))
				if (treeNode.id == 2) {
					selectCode = "";
				} else {
					selectCode = treeNode.id;
				}
				//上一个请求在未返回前，直接终止掉
				if (lastAjax != null)
					lastAjax.abort();
				//请求下一个
				$("#dataTable").bootstrapTable('refresh');
			}
			
			function idFormatter(value, row) {
				return index++;
			}
			
			//得到所有部门
			function getDepartmentList() {
				$.ajax({
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
				   		} else {
				   			var list= [];
							$.fn.zTree.init($("#treeDemo"), setting, list);
							var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
							var nodes = treeObj.getNodes();
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
				dataTable = $('#dataTable');
				
				//选中事件操作数组  
			    var union = function(array,rows){  
			        $.each(rows, function (i, row) {  
			            if($.inArray(row,array)==-1){  
			                array[array.length] = row.id;  
			            }  
			             });  
			            return array;  
			    };  
			    //取消选中事件操作数组  
			    var difference = function(array,rows){
			            $.each(rows, function (i, row) {  
			                 var index = $.inArray(row.id,array); 
			                 if(index!=-1){  
			                     array.splice(index, 1);  
			                 }  
			             });  
			            return array;  
			    };  
			    var _ = {"union":union,"difference":difference};  
			    
				//保存选中数据
				dataTable.on('check.bs.table check-all.bs.table ' +
		                'uncheck.bs.table uncheck-all.bs.table', function (e, rows) {
		            var rowss = $.map(!$.isArray(rows) ? [rows] : rows, function (row) {
		                    return row;
		            }),
		            func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';
		            selections = _[func](selections, rowss);
		        });
			});
			
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
				dataTable.bootstrapTable('refresh');
			}
			
			
			function getParams() {
				var name = $("#names").val();
				var params = {
					'name' : name,
					'departmentId' : selectCode,
					'type':1
				}
				return params;
			}
			
			function responseHandler(res) {
				var index;
		        $.each(res.rows, function (i, row) {
		        	index = $.inArray(row.id, selections);
		        	if(index != -1)
		           		 row.state = true;
		        	else
		        		row.state = false;
		        });
		        return res;
		    }

			//新增用户
			function add() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if (rows == 0) {
					Modal.alert({
						msg : '请选择要复制的事项',
						title : '提示',
						btnok : '确定',
						btncl : '取消'
					});
					return false;
				}
				var dialog = getJqueryDialog();
				dialog.Container = $("#dataFormWrap");
				dialog.Title = "复制";
				dialog.CloseOperation = "destroy";
				dialog.Width = "500px";
				dialog.Height = Math.round($(window).height() * 0.5);//设置对话框高度
				dialog.BeforeClose = function(){
					$('#departmentId').val("");
					$("#positionId").empty();
					tipsRegionHintAddVerify($("#dataFormWrap"));
				};
				dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						if (tipsRegionValidator($("#dataForm"))) {
							$.ajax({
								type : "POST",
								url : 'itemAction!copy.action',
								dataType : 'json',
								data : {'itemIds':selections,'departmentName':$("#departmentId").find("option:selected").text(),
										'departmentId' : $("#departmentId").val() , 'positionIdS' : $("#positionId").val()},
								async : false,
								traditional:true,
								error : function(request, textStatus, errorThrown) {
									Modal.alert({
										msg : '失败',
										title : '提示',
										btnok : '确定',
										btncl : '取消'
									});
								},
								success : function(data) {
									console.log(data)
									if(data.resultCode == 1) {
										Modal.alert({
											msg : data.resultMsg,
											title : '提示',
											btnok : '确定',
											btncl : '取消'
										});
										dataTable.bootstrapTable('selectPage', 1);
									} else {
										Modal.alert({
											msg : '失败',
											title : '提示',
											btnok : '确定',
											btncl : '取消'
										});
									}
									
								}
							});
							$(this).dialog("close");
						}
					}
				}
				selectUpdated($("#departmentId"));
				selectUpdated($("#positionId"));
				dialog.show();
			}
			
		</script>
	</body>
</html>