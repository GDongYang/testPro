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
		<title>问题反馈</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,chosen,date,icon,dialog,fileinput" name="p"/>
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
									
									<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
										<div class="row">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="sfId">身份证号</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="sfId" name="sfId" placeholder="身份证号"/>
												</div>
											</div>
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="searchDate">日期</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<div class="input-group base_input-group">
														<div class="input-group-addon base_input-groupleft-area">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" class="form-control pull-left base_input-groupright-area" id="searchDate" name='searchDate' data-date-format="yyyy-mm-dd"/>
													</div>
												</div>
											</div>
											<!--反馈人搜索-->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="username">反馈人</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="username" name="username" placeholder="反馈人"/>
												</div>
											</div>	
											<!-- /.base_query-group 部门搜索 -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="deptName">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="deptName" id="deptName"><option value=""></option></select>
												</div>
											</div>								
										</div>
									</div>
								</div>
							</form>
						</div>
						<h2 class="page-header base_page-header"><div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div></h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua">
						<div class="box-header with-border base_box-header">
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">问题反馈</span></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="exportExcel()">导出表格</button>
								<button type="button" class="btn btn-xs btn-info" onClick="excute()">处理</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="false"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页">
									<thead style="text-align:center;">
										<tr>
											<th data-checkbox = "true"></th>
											<!-- <th data-field="id" data-formatter="idFormatter" data-width="20">序号</th> -->
											<th data-field="certName">证件名称</th>
											<th data-field="interfaceName">接口名称</th>
											<th data-field="itemName" >事项名称</th>
											<th data-field="type">类别</th>
											<th data-field="content" data-width="80">反馈问题</th>
											<th data-field="sfId" >身份证号</th>
											<th data-field="username" >反馈人</th>
											<!-- <th data-field="phone" >联系方式</th> -->
											<th data-field="deptName" >所属部门</th>
											<th data-field="createDate" data-formatter="formatDate">反馈时间</th>
											<th data-field="certCode"  data-formatter="preview">详情</th>
											<th data-field="fileKey" data-formatter="formatFile">附件</th>
											<th data-field="status" data-formatter="formatStatus" data-width="60">状态</th>
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
				<input type="hidden" id="ids" name="ids" value="0">
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >状态</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control" id="status" name="status">
							<option value="未处理">未处理</option>
							<option value="已受理">已受理</option>
							<option value="已回复">已回复</option>
						</select>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >备注</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="备注" ></textarea>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >附件</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="file" name="importFile" id="importFile" />
					</div>
				</div>
			</form>
		</div>
		
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,chosen,datetime,cookie,dialog,fileinput" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="<%=path %>/js/jquery-form.js"></script>
		
		<script type="text/javascript">
			var bootstrapValidator;
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				selectConfig();
				loadDepart();//下拉框
				dataTable = $('#dataTable');
				
				$('#searchDate').datetimepicker({
					autoclose:true,
					minView:2,
					language: 'zh-CN'
				});
				$("#importFile").fileinput({ showUpload : false,
			        showRemove : false,
			        //showPreview : false,
			        language : 'zh'});
			});
			
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
				}
			}
			
			function idFormatter(value, row) {
				return index++;
			}
			
			function formatFile(val,row) {
				if(val) {
					return "<a style=\"cursor:pointer;\" onclick=\"openDetail('"+ val +"','"+ row.fileName +"');\" >查看</a>";
				} else {
					return "-";
				}
			}
			
			function formatStatus(val) {
				if("已回复" == val) {
					return '<font style="color:#f3c312">已回复</font>';
				} else if("已受理" == val) {
					return '<font style="color:#00a65a">已受理</font>';
				} else if("未处理" == val) {
					return '<font style="color:#FF0000">未处理</font>';
				}
			}
			
			function preview(val,row) {
				if(row.type == "无数据") {
					return "-";
				} else {
					return "<a style=\"cursor:pointer;\" onclick=\"clickTemp('"+row.sfId+"','"+val+"','"+row.busiCode+"');\">预览</a>";
				}
			}
			
			function clickTemp(sfid, certCode, busiCode) {
				var html = "../preview.jsp?certCode="+certCode+"&cerNo="+sfid+"&busiCode=" + busiCode;
				var windowId = "window_"+getRandId();
				window.open (html, windowId, "'width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+", toolbar=no, menubar=no, location=no,fullscreen=yes,resizable=yes,scrollbars=yes,status=no");
			}
			
			function exportExcel(){
				window.open("opinionAction!downloadExcel.action?"+$('#searchForm').serialize() );
			} 
			
			function getRandId(){
				  return Math.floor((1 + Math.random()) * 0x10000)
	               .toString(16)
	               .substring(1);
			}
			
			function openDetail(fileKey,fileName) {
				window.open('opinionAction!readFile.action?fileKey=' + fileKey + '&fileName=' + fileName);
			}
			
			//ajax请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				index = params.data.offset + 1;
				$.ajax({
					type : 'post',
					url : 'opinionAction!findPage.action',
					data:dataStr,
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var datas = data ? data.page.items : [];
						var count = data ? data.page.count : 0;
						params.success({
							total : count,
							rows : datas ? datas : []
						});
						params.complete();
						//drawICheck('dataTable');
					}
				});
			}
			
			//查询
			function search() {
				var id="username";
				var idval=$("#username").val();
				checkText(id,idval);

				var id="sfId";
				var idval=$("#sfId").val();
				checkText(id,idval);
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			function clean() {
				$('#sfId').val('');
				$('#searchDate').val('');
				$('#username').val('');
				$('#deptName').val('');
				selectUpdated($("#deptName"));//下拉框变动更新
				pageNum = 1;
				pageSize = 10;
				search()
			}
			
			$("#searchDate").click(function() {
				$('#searchDate').datetimepicker('show');
			});
			
			
			function excute() {
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				 $(".fileinput-remove").click();
				 //$('#status').val('');
				 //$("#importFile").val('');
				 $('#memo').val('');
				if (obj.length >= 1) {
					var ids = '';
					$.each(obj,function(i, val){
						ids += val.id + ",";
					});
					$('#ids').val(ids);
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "修改";
			   		dialog.CloseOperation = "destroy";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 540;
			   		}
			   		dialog.Height = Math.round($(window).height() * 0.7);//设置对话框高度
			   		//dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"提交" : function() {
							var dialog = $(this);
							var options = {
									url :"opinionAction!update.action",
									type : 'post',
									success : function(data) {
										dialog.dialog("close");
										Modal.alert({ msg:'修改成功！', title:'提示', btnok:'确定' }).on(function(e){
											dataTable.bootstrapTable('refresh');
										});
									}
								};
						   $("#dataForm").ajaxSubmit(options);
						}
					};
			   		dialog.show();
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//删除
			function remove() {
				var rows = $('#dataTable').bootstrapTable("getAllSelections");
				if(rows != 0) {
					var row = rows[0];
					Modal.confirm({
						msg: "是否删除？"
					}).on( function(e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'opinionAction!remove.action',
								dataType : 'json',
								data : row,
								async : false,
								error : function(request, textStatus, errorThrown) {
									fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									Modal.alert({ msg:'删除成功！', title:'提示', btnok:'确定' }).on(function(e){
										dataTable.bootstrapTable('refresh');
									});
								}
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要删除的数据！', title:'提示', btnok:'确定' });
				}
			}
			//下拉框
			function loadDepart() {
				$.ajax({
					type : 'POST',
					url : 'departmentAction!findList.action',
					dataType : 'json',
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						$.each(data.departments,function(i,org){
							$("#deptName").append('<option value="'+org.id+'">'+org.name+'</option>');
						});
						selectUpdated($("#org"));//下拉框变动更新
						selectUpdated($("#deptName"));//下拉框变动更新
					}
				});
			}

			//验证文本框输入特殊字符
			function checkText(id, text) {//xss攻击特殊字符过滤
				var arr = new Array();
				arr = [ "\"","\;","alert", "eval", "<script>", "<\/script>", "onblur", "onload", "onfocus", "onerror", "onclick", "onMouseOver", "onMouseOut", "onSelect", "onChange", "onSubmit", "console", "href", "<iframe>", "<\/iframe>", "<img>", "<\/img>", "<iframe>", "<\/iframe>", "<video>", "<\/video>", "<canvas>", "<\/canvas>", "<label>", "<\/label>", "<span>", "<\/span>", "document", "location", "javascript"];
				$.each(arr, function (index, value) {
					var result=text.indexOf(value);
					if (result!= -1) {
						//输入信息包含恶意字符
						Modal.alert({
							msg: '包含恶意字符,请重新输入',
							title: '提示',
							btnok: '确定'
						});
						dataTable.bootstrapTable('selectPage', 1);
						$('#' + id).val("");
						$('#' + id).focus();
						return false;
					}
				});
			}
			
		</script>
	</body>
</html>