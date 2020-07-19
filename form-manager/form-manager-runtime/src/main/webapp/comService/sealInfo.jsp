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
			<jsp:param value="mcustomscrollbar,icon,table,icheck,tree,dialog,chosen,fileinput,tips" name="p" />
		</jsp:include>
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
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >印章名称</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="nameSearch" name='name' placeholder="印章名称"/>
												</div>
											</div>
											<!-- /.base_query-group -->
										
										
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
													<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="activeSearch">发布状态</label>
													<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
														<!-- <input type="text" class="form-control base_input-text" id="activeSearch" name="active" placeholder="发布状态" /> -->
														<select class="form-control base_input-text" id="activeSelect">
															<option value="">请选择</option>
															<option value="1">已发布</option>
															<option value="0">未发布</option>
														</select>
													</div>
											</div>
										</div>
										<!-- /.row -->
									</div>
									

									
									
									
									
									
								</div>
							</form>
						</div>
						<h2 class="page-header base_page-header"><!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> --></h2>
					</div>
				
				
					
					<div class="row">
						<div class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
							
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
							<div name="boxSkin" class="box base_box-area-aqua">
								<div class="box-header with-border base_box-header">
									<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">电子印章管理</span></h3>
									<div class="box-tools pull-right">
										<button type="button" class="btn btn-xs btn-info" onClick="addSeal()">新增</button>
										<button type="button" class="btn btn-xs btn-info" onClick="updateSeal()">修改</button>
										<button type="button" class="btn btn-xs btn-info" onClick="removeSeal()">废除</button>
										<button type="button" class="btn btn-xs btn-info" onClick="updateSealActive()">发布</button>
									</div>
								</div>
								<div class="box-body">
									<div class="form-group">
										<table id="tablewrap" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
											data-ajax="ajaxRequest" data-side-pagination="server"
											data-striped="true" data-single-select="true"
											data-click-to-select="true" data-pagination="true"
											data-pagination-first-text="首页" data-pagination-pre-text="上一页"
											data-pagination-next-text="下一页" data-pagination-last-text="末页">
											<thead style="text-align:center;">
												<tr>
													<th data-radio="true"></th>
													<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
													<th data-field="name">名称</th>
													<th data-field="code">编码</th>
													<th data-field="code" data-formatter="detail">查看</th>
												
													<th data-field="visible" data-formatter="formatActive">状态</th>
												</tr>
											</thead>
										</table>
									</div>
									<!-- /.form-group -->
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
					</div>
					<!-- /.row -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		
		
		<!-- 新增、修改 -->
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataForm" name="dataForm">
			<input type="hidden" id="idKey" name="idKey"/>
			
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">名称</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="名称" tips-message="请输入名称" />
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="code">编码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="code" name="code" placeholder="编码" tips-message="请输入编码"/>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >证件</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<!-- data-tips-message="请选择证件" -->
						<select class="form-control chosen-select-deselect"  multiple="multiple" id="certTempIda" name="certTempIdS" ></select>
					</div>
				</div>
				
				
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="keyword">关键字</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="keyword" name="keyword" placeholder="仅支持关键字盖章" />
					</div>
				</div>
				
				
			</form>
		</div>
		
		
		
		
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="table,icheck,tree,dialog,chosen,fileinput,tips,mcustomscrollbar" name="p" />
		</jsp:include>
		
		<script type="text/javascript" src="<%=path %>/js/jquery-form.js"></script>
		<script type="text/javascript" >
		var depts = [];
		var deptObj = {};
		var lastAjax = null;

		var departSetting = {
				data: {
					keep:{
						parent: true
					},
					key:{
					},
					simpleData: {
						enable: true,
						/*idKey: "oid",
						pIdKey: "poid",*/
						idKey: "id",
						pIdKey: "parentId",
						rootPId: null
					}
				},callback: {
					onClick: zTreeOnClick,
					onExpand:zTreeOnClick
				}
			};
		
		$(function() {
			setInnerPage();//设置内容页面宽和高
			getDepartmentList(); //左侧树
			
			selectConfig();//下拉框初始化
			
			//bootstrapTable单击事件
			$("#tablewrap").on('click-row.bs.table', function (row, $element, field) {
				$(field[0]).find(":radio").iCheck('check');//选中行内radio
			});
			
		});
		
		
		
		
		
		
		var selectCode = "";
		//树节点点击事件
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
			//上一个请求在未返回前，直接终止掉
			if (lastAjax != null)
				lastAjax.abort();
			$("#activeSelect").val("");
			$("#nameSearch").val("");
			//请求下一个
			$("#tablewrap").bootstrapTable('selectPage',1);
		}
		
		//得到所有部门
		function getDepartmentList() {
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
						$.fn.zTree.init($("#treeDemo"), departSetting, data.departments);
						$(data.departments).each(function(i, item) {
							deptObj[item.id] = item.name;
						});
	                    $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
			   		} else {
			   			var list= [];
						$.fn.zTree.init($("#treeDemo"), departSetting, list);
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
						$.fn.zTree.init($("#treeDemo"), departSetting, data.departments);
	                    $.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
			   		} else {
			   			var list= [];
						$.fn.zTree.init($("#treeDemo"), departSetting, list);
			   		}
				}
			});
		}
		
		//查询
		function search() {
			
			$("#tablewrap").bootstrapTable('selectPage',1);
		}
		//清除
		function clean() {
			pageNum = 1;
			pageSize = 10;
			$('#nameSearch').val("");
			$('#activeSelect').val("");
			$("#tablewrap").bootstrapTable('selectPage',1);
		}
		
		

		function ajaxRequest(params) {
			var datas;
			var list;
			var count;
			var p = getParams(params);
			lastAjax = $.ajax({
				type : 'post',
				url : 'sealInfoAction!findPagination.action',
				dataType : 'json',
				cache : false,
				async : true,
				data : p,
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					console.log("data is " + data);
					datas = data.page;  
					if (datas != null)
						count = datas.count;
					list = datas.items ? datas.items : [];
					params.success({
						total : count,
						rows : list
					});
					params.complete();
					//监听及渲染ICheck
					drawICheck("tablewrap");
				}
			});
		}
		
		

		function getParams(params) {
			var pageSize = params.data.limit;
			var pageNum = params.data.offset / pageSize + 1;
			index = params.data.offset + 1;
			var sort = params.data.sort ? params.data.sort : "id";
			var order = params.data.order ? params.data.order : "desc";
			var name = $("#nameSearch").val();
			var visible = $("#activeSelect").val();
			console.log("url=== is " + visible);
			var data = {
				sort : sort,
				order : order,
				pageNum : pageNum,
				pageSize : pageSize,
				name:name,
				visible:visible
			};
			if (selectCode != "") {
				data.departId = selectCode;
			}
			return data;
		}
		
		

		function idFormatter(value, row) {
			return index++;
		}
		
		
		
		
		
		
		
		//新增
		function addSeal() {
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getSelectedNodes();
			if (nodes.length == 0 || nodes[0].id == 0) {
				Modal.alert({
					msg : '请选择部门！',
					title : '提示',
					btnok : '确定'
				});
				return;
			}
			
			findCertTemp(nodes[0].id);
			$("#name").val("");
			$("#code").val("");
			$("#keyword").val("");
			var dialog = getJqueryDialog();
			dialog.Container = $("#dataFormWrap");
			dialog.Title = "新增印章";
			if (!fBrowserRedirect()) {
				dialog.Width = "540";
			}
			dialog.Height = "300";
			dialog.CloseOperation = "destroy";
			dialog.ButtonJSON = {
				"取消" : function() {
					$(this).dialog("close");
				},
				"确定" : function() {
					submitData($(this),nodes[0].id);
				}
			};
			dialog.show();
			
		}
		//修改数据
		function updateSeal() {
			var info = $("#tablewrap").bootstrapTable("getSelections");
			
			
			if (info != 0) {
				
				findCertTemp(info[0].departmentId);
				
				$("#idKey").val(info[0].id);
				$("#name").val(info[0].name);
				$("#code").val(info[0].code);
				findByidkeyword(info[0].id);
				//console.log("select is" + $("#certTempIda").val());
				var dialog = getJqueryDialog();
				dialog.Container = $("#dataFormWrap");
				dialog.Title = "修改印章";
				if (!fBrowserRedirect()) {
					dialog.Width = "540";
				}
				dialog.Height = "300";
				dialog.CloseOperation = "destroy";
				dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						submitData($(this),info[0].departmentId);
					}
				};
				
				dialog.show();
			} else {
				Modal.alert({
					msg : '请选择要修改的数据！',
					title : '提示',
					btnok : '确定'
				});
			}
		}
		
		
		
		//发布数据
		function updateSealActive() {
			var rows = $('#tablewrap').bootstrapTable("getSelections");
			if(rows != 0) {
				var row = rows[0];
				
				Modal.confirm({
					msg:"是否发布该数据？"
				}).on( function (e) {
					if(e) {
						$.ajax({
							cache : true,
							type : "POST",
							url : 'sealInfoAction!updateActive.action',
							dataType : 'json',
							data : row,
							async : false,
							error : function(request, textStatus, errorThrown) {
								fxShowAjaxError(request, textStatus, errorThrown);
							},
							success : function(data) {
								Modal.alert({ msg:'发布成功！', title:'提示', btnok:'确定' }).on(function(e){
									refresh();
								});
								$("#tablewrap").bootstrapTable('refresh');
							}
						});
					}
				});
			} else {
				Modal.alert({ msg:'请选择要发布的数据！', title:'提示', btnok:'确定' });
			}
				
				
		}
		
		
		//删除数据
		function removeSeal() {
				var rows = $('#tablewrap').bootstrapTable("getSelections");
				if(rows != 0) {
					var row = rows[0];
					console.log("url is " + row);
					Modal.confirm({
						msg:"是否废除该数据？"
					}).on( function (e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'sealInfoAction!remove.action',
								dataType : 'json',
								data : row,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'废除成功！', title:'提示', btnok:'确定' }).on(function(e){
										refresh();
									});
									$("#tablewrap").bootstrapTable('refresh');
								}
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要废除的数据！', title:'提示', btnok:'确定' });
				}
		}
		//提交数据
		function submitData(obj,departmentId) {
			if(tipsRegionValidator($("#dataForm"))){
				
				var dataStr = $("#dataForm").serialize()+ "&departId=" + departmentId;
				if ($("#idKey").val() != null && $("#idKey").val() != ""){
					dataStr += "&id="+$("#idKey").val();
				}
				
				var url = "../sealInfoAction!create.action";
				if ($("#idKey").val() != "" && typeof($("#idKey").val())!="undefined") {
					url = "../sealInfoAction!update.action";
				}
				
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						Modal.alert({
							msg : '保存成功！',
							title : '提示',
							btnok : '确定'
						});
						obj.dialog("close");
						//请求下一个
						$("#tablewrap").bootstrapTable('refresh');
					}
				});
			}
		}
		
		

		
		
	    function formatActive(val,row) {
	        var html="";
	        if (val == 1) {
	            html+= '<div onclick=\"Show(this)\"><span class=\"a\"><font style="color:#0000FF">已发布</font></span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: auto;\"><option value=\"true\" selected=\"selected\">正常</option><option value=\"false\">未激活</option></select>';
	        } else {
	            html+= '<div onclick=\"Show(this)\"><span class=\"a\"><font style="color:#FF0000">未发布</font></span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: auto;\""><option value=\"true\" >正常</option><option value=\"false\" selected=\"selected\">未激活</option></select>';
	        }
	        html+="<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateActive('active',this,'"+row.id+"')\"></button>"+
	            "<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
	        return html;
	    }

	   
	    function Hidden(obj){
	        $(obj.parentNode).hide();
	        $(obj.parentNode).prev().show();
	        /* refresh(); */
	    }

	    function Show(obj){
	        $(obj).hide();
	        $(obj).next().show();
	    }

	    function refresh() {
	        $("#tablewrap").bootstrapTable('refresh');
	    }
	    
	    
	    
	    //查看
	    function detail(value){
			return "<a style=\"cursor:pointer;\" onclick=\"showSeal('"+value+"')\">查看</a>";
		}
	    
	  //查看
		function showSeal(code){
			var url = "SealViewer.jsp?code="+code;
			window.open(url, "newwindow", "'width="+1000+",height="+800+", toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no");
		}
		
	    /* 根据印章Id查出关联模板关键字以及所关联的模板 */
		function findByidkeyword(Id) {
			var result='';
			var sealIds = new Array();
			$.ajax({
				type : 'post',
				url : 'sealInfoAction!findByidkeyword.action',
				dataType : 'json',
				cache : false,
				async : false,
				data : {
					id : Id
				},

				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					console.log("resultData is " + JSON.stringify(data));
					keywords = data.keyword;
					certTemps = data.certTemp;
					for(var i = 0;i < certTemps.length;i++){
						console.log("data111 is " + certTemps[i].id);
						sealIds.push(certTemps[i].id);
					}
					for(var i = 0;i < sealIds.length;i++){
						console.log("sealIds is " + sealIds[i]);
					}
					 $("#keyword").val(keywords);
				}
			});
			return sealIds;
		}
		
		
		
		
		
		
		
		
	    
	    /* 根据部门查询到部门下的证件 */
	    function findCertTemp(departmentId) {
	    	var datas;
			var list;
			var count;
			
			var dataStr = "departId=" + departmentId +"&pageSize=" + 100 + "&pageNum=" + 0;
			$.ajax({
				type : 'post',
				url : 'certTempAction!findPage.action',
				dataType : 'json',
				cache : false,
				async : false,
				data : dataStr,
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					if (data.page != null) {
						$("#certTempIda").empty();
						allTemp = data.page;
						var htmlStr = "";
						for (var i = 0; i < data.page.items.length; i++) {
							htmlStr += "<option value=\""+data.page.items[i].id+"\">"
									+ data.page.items[i].name + "</option>";
						}
						$("#certTempIda").append(htmlStr);
						selectUpdated($("#certTempIda"));
					}
				}
			});
			
		}
	    
	    
		
		</script>
	</body>
</html>