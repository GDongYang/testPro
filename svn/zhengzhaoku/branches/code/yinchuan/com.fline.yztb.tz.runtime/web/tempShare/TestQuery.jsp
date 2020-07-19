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
		<title>证件信息查询</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,icheck,chosen" name="p"/>
		</jsp:include>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<div class="base_query-area">
						<div class="col-lg-12">
							<form id="searchForm">
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 pull-right">
										<div class="form-group text-right base_options-area">
											<button type="button" class="btn btn-sm btn-info" style="display: none;" onClick="search()">搜索</button>
											<button type="button" class="btn btn-sm btn-info" style="display: none;" onClick="clean()">清空</button>
										    <button type="button" id="readCardBtn" class="btn btn-sm btn-info" onClick="readCard()" style="width:180px;font-size:18px;">查询</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div class="row">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group" style="padding-top:5px;">
											    <label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">姓名</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="sfName" name="sfName" placeholder="姓名" />
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group" style="padding-top:5px;">
											    <label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">身份证号码</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="sfId" name="sfId" style="width:205px;" placeholder="身份证号码"  />
												</div>
											</div>
											<!-- /.base_query-group -->
											
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group" style="padding-top:5px;">
											    <label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">证件</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" id="certCode" name="certCode" style="width:245px;">
													</select>
												</div>
											</div>
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
												
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
								<i class="fa fa-tag"></i> <span class="base_text-size-15">证件信息 </span>
							</h3>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="dataTable" class="box base_tablewrap"
									data-toggle="table" data-locale="zh-CN" data-ajax="ajaxRequest"
									data-side-pagination="server" data-striped="true"
									data-single-select="true" data-click-to-select="true"
									data-pagination="false" data-pagination-first-text="首页"
									data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
									data-pagination-last-text="末页">
									<thead style="text-align: center;">
										<tr>
											<th data-radio="true"></th>
											<th data-field="certCode" >证件编码</th> 
											<th data-field="certName" >证件类型</th>
											<th data-field="timeConsuming" >耗时(ms)</th>
											<th data-field="" data-formatter="details">操作</th>
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
		<!-- read Card ID -->
		<div id="readCardDiv">
			 
		</div>
		<!-- loading -->
		<div id="svg" class="svg" style="display:none;z-index: 10;">
			<img src="/yztb/cert/images/loading.png">
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,icheck,chosen" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="/yztb/plugins/keypad/js/jquery.plugin.js"></script> 
	    <script type="text/javascript" src="/yztb/plugins/keypad/js/jquery.keypad.js"></script>
		
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var _selectRow = null;
			var cerNo = null;
			var dataPid = null;
			var sfName = null;
			
			var sfId = null;
			var certCode = null;
			
			var btnDownload = document.getElementById("btnDownload");
			
			var _userName = null;
			var _certName = null;
			
			function idFormatter(value, row) {
				return index++;
			}
			
			function stateFormatter(val,row) {
				if(val == 0) {
					return "无证件信息";
				} else if (val == 1){
					return "成功";
				} else if(val == 2) {
					return "失败:" + row.errorMsg;
				}
			}
			
			var searchTxt = "";
			var _items = null;
			var useName = "";
			$(function() {
				selectConfig();//下拉框初始化
				//bootstrapTable单击事件
				$("#dataTable").on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				loadCurrentUser();
				dataTable = $('#dataTable');		
				$("#sfId").keydown(function(e){
					if(e.keyCode == 36){
						$("#sfId").removeAttr("readonly");
						$("#sfName").removeAttr("readonly");
					}else if(e.keyCode == 13){
						search();
					}
				});
				
				$.ajax({
					type : 'post',
					url : '<%=path%>/tempInfoAction!findCurrentUserDepartMent.action', 
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						searchTxt = "应 "+data.depart+" ";
						_items = data.items;
						dataPid = data.depar;
						useName = data.userName;
						_userName = data.userName;
					}
				});
				
				$.ajax({
					type : 'post',
					url : 'certTempAction!findAll.action', 
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var _items = data.result;
						if(_items != null) {
							var itemHtml = '<option value="">---请选择---</option>';
							for(var i=0; i<_items.length; i++) {
								itemHtml += '<option value="'+ _items[i].code +'">'+_items[i].name+'</option>';
							}
							$("#certCode").html(itemHtml);
							selectUpdated($("#certCode"));
						}
					}
				});
			});
		 	var currentUser;
			function loadCurrentUser() {
				$.ajax({
					url:"userSessionAction!loadCurrentUser.action",
					type:"POST",
					dataType:"json",
					async:false,
					error:function(request,textStatus, errorThrown){
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success:function(data){
						currentUser = data;
					}
				});
			}
			function clickTemp(sfid,certCode, busiCode){
				var row = null;
				$(items).each(function(i,item){
					if(item.certCode == certCode){
						row = item;
					}
				});
				if(row){
					var html = "../preview.jsp?certCode="+certCode+"&cerNo="+cerNo+"&busiCode=" + busiCode;
					var windowId = "window_"+getRandId();
					window.open (html, windowId, "'width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+", toolbar=no, menubar=no, location=no,fullscreen=yes,resizable=yes,scrollbars=yes,status=no");
				}
			}
			
			function getRandId(){
				  return Math.floor((1 + Math.random()) * 0x10000)
	               .toString(16)
	               .substring(1);
			}
			
			//刷新页面
			function refresh() {
				clean();
				search();
			}
			//对象列表的ajax请求
			var todoList;
			var items;
			function ajaxRequest(params) {
				
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var param = getParams();
				var datas;
		
				$.ajax({
					type : 'post',
					url : '<%=path%>/tempInfoAction!findInfoByCertCode.action', 
					dataType : 'json',
					cache : false,
					async : true,
					data : param,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						if(data.result.length > 0){
							if(data.result[0].returnResult != undefined){
								Modal.alert({
									msg : '您暂无权限进行此操作',
									title : '提示',
									btnok : '确定',
									btncl : '取消'
								});
								return false;
							}
						}
						
						items = data.result ? data.result : [];
						params.success({
							total : data.result.length,
							rows : items
						});
						params.complete();
						
						$('input').iCheck({
							checkboxClass : 'icheckbox_square-blue',
							radioClass : 'iradio_square-blue',
							increaseArea : '20%' // optional
						});
						drawICheck("dataTable");
					}
				}); 
			}
			function details(val,row){
				if(row !=null && row.certData != null){
					var itemId = $("#selectItems").val();
					sfid = cerNo;
				 	certCode = row.certCode;
				 	var busiCode = row.BusinessCode;
				 	return "<a style=\"cursor:pointer;\" onclick=\"clickTemp('"+sfId+"','"+certCode+"','"+busiCode+"');\">预览</a>&nbsp&nbsp&nbsp&nbsp"+
					"<a style=\"cursor:pointer;\" href=\"createDownloadReturnFile.action?certCode="+certCode+"&sfId="+sfid+ "&busiCode=" + busiCode + "\">下载</a>";
				} else {
					return "";
				}
			}
			
			
			//查询
			function search() {
				if($("#certCode").val() == null || $("#certCode").val() == "") {
					Modal.alert({ msg:"请选择证件！", title:'提示', btnok:'确定' });
					return false;
				}
				cerNo = $("#sfId").val();
				sfName = $("#sfName").val();
				dataTable.bootstrapTable('refresh', {
					queryParams : getParams()
				}); 
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#searchForm input').val("");
			}
			
			function getParams() {
				var sfId = $("#sfId").val();
				var sfName = $("#sfName").val();
				var certCode = $("#certCode").val();
				var params = {
					'sfId' : sfId,
					'sfName' : sfName,
					'certCode' : certCode
				}
				return params;
			}
			
			//读取身份证信息
			function readCard(){
				if($("#certCode").val() == null || $("#certCode").val() == "") {
					Modal.alert({ msg:"请选择证件！", title:'提示', btnok:'确定' });
					return false;
				}
				search();
			}
			
		</script>
	</body>
</html>