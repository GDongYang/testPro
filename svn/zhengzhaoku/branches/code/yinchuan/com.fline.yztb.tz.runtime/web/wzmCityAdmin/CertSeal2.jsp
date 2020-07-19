<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="description" content="3 styles with inline editable feature" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>共享材料申请</title>
<jsp:include page="../css/PageletCSS.jsp">
	<jsp:param
		value="mcustomscrollbar,table,icheck,tips,chosen,dialog,icon" name="p" />
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
										<button type="button" class="btn btn-sm btn-info"
											onClick="search()">搜索</button>
										<button type="button" class="btn btn-sm btn-info"
											onClick="clean()">清空</button>
									</div>
								</div>
								<!-- /.base_options-area -->

								<div
									class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
									<label
										class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15"
										for="status">盖章状态</label>
									<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
										<select class="form-control chosen-select-deselect"
											chosen-position="true" name="status" id="status"><option
												value="4" selected=selected>请选择</option>
											<option value="1">已盖章</option>
											<option value="0">待盖章</option>
											<option value="2">未处理</option>
											<option value="3">已过期</option>
											<option value="5">已办结</option>
											<option value="6">已撤回</option>
										</select>
									</div>
								</div>
							</div>
						</form>
					</div>
					<h2 class="page-header base_page-header">
						<!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> -->
					</h2>
				</div>
				<!-- /.base_query-area -->

				<div name="boxSkin" class="box base_box-area-aqua">
					<div class="box-header with-border base_box-header">
						<h3 class="box-title">
							<i class="fa fa-tag"></i> <span class="base_text-size-15">盖章证件详情</span>
						</h3>
						<div class="box-tools pull-right">
							<button type="button" class="btn btn-xs btn-info" onClick="ask()">申请</button>
						</div>
					</div>
					<div class="box-body">
						<div class="form-group">
							<table id="dataTable" class="box base_tablewrap"
								data-toggle="table" data-locale="zh-CN" data-ajax="ajaxRequest"
								data-side-pagination="server" data-striped="true"
								data-single-select="false" data-click-to-select="true"
								data-pagination="true" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页">
								<thead style="text-align: center;">
									<tr>
										<th data-field="status" data-formatter="formatStatus"
											data-width="60">盖章状态</th>
										<th data-field="deptName">盖章部门</th>
										<th data-field="createDate" data-formatter="formatDate">申请时间</th>
										<th data-field="cert">证件材料</th>
										<th data-field="business">事项</th>
										<th data-field="inquiredName">被查询人</th>
										<th data-field="sfid">身份证号</th>
										<th data-field="companyName">企业名称</th>
										<th data-field="companyCode">企业统一信用代码</th>
										<th data-field="excuteDate" data-formatter="formatDate">处理时间</th>
										<th data-field="uploadPerson">上传人</th>
										<th data-field="signDate" data-formatter="formatDate">盖章时间</th>
										<th data-field="sealPerson">盖章人</th>
										<th data-field="sealPersonPhone">盖章人手机号</th>
										<th data-field="" data-formatter="details" data-width="100">操作</th>
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

	<!-- 申请 -->
	<div id="dataFormWrap" class="base_hidden">
	<div id="readCardDiv">

		</div>
		<button type="button" id="readCardBtn" class="btn btn-sm btn-info"
			onClick="readCard()">读取身份证号</button>
		<form class="form-horizontal base_dialog-form" id="dataForm"
			name="dataForm">
			<input type="hidden" id="ids" name="ids" value="0">
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项类型</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<select class="form-control chosen-select-deselect"
						id="businessType" name="businessType" onchange='choose(this);'>
						<option value="0" selected="selected">个人</option>
						<option value="1" >企业</option>
					</select>
				</div>
			</div>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<select class="form-control chosen-select-deselect" id="business"
						name="business" onchange="loadCert()">
						
						<!-- <option value="开发测试事项">开发测试事项</option>
						<option value="企业年金方案（实施细则）重要条款变更备案(测试)">企业年金方案（实施细则）重要条款变更备案(测试)</option>
						<option value="离职提取住房公积金">离职提取住房公积金</option> -->
					</select>
				</div>
			</div>
			<!-- /.base_query-group -->
			<div
				class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group ">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">姓名</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text"
						id="inquiredName" name="inquiredName" placeholder="姓名"
						readonly="readonly" />
				</div>
			</div>
			<!-- /.base_query-group -->
			<div
				class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group ">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">身份证号码</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text" id="sfid"
						name="sfid" style="width: 205px;" placeholder="身份证号码"
						readonly="readonly" />
				</div>
			</div>

			<!-- /.base_query-group -->
			<div id="companyName"
				class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group "  style="display: none">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">企业名称</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text"
						id="companyName" name="companyName" placeholder="企业名称" />
				</div>
			</div>
			<!-- /.base_query-group -->
			<div id="companyCode"
				class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group "  style="display: none">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">统一社会信用代码</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text"
						id="companyCode" name="companyCode" style="width: 205px;"
						placeholder="统一社会信用代码" />
				</div>
			</div>

<!-- 			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
				<label
					class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15"
					for="departmentId">盖章部门</label>
				<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<select class="form-control "
						 name="departmentId" id="departmentId" ></select>
				</div>
			</div>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15"
					for="cert">选择材料</label>
				<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<select chosen-select-deselectid="cert" name="cert"
						placeholder="模板名称" tips-message="请选择证件模板">
					</select>
				</div>
			</div> -->


			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >备注</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<textarea class="form-control base_input-textarea" id="memo" name="memo" placeholder="备注" ></textarea>
					</div>

			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 base_query-group">
							<table id="certTable1" class="box base_tablewrap">
							<thead>  
									<tr>
										<!-- 	<th ><input type='checkbox' name='chkb' /></th> -->
											<th >证件名称</th>
											<th >协查部门</th>
											<th id="uploadFileAttachID" style="display: none">上传附件</th>
									</tr>
									 <tbody id="tbody">
									 </tbody>
							</thead>  		
							</table>
			</div> 
		</form>
	</div>

	<jsp:include page="../js/PageletJS.jsp">
		<jsp:param
			value="mcustomscrollbar,table,icheck,tips,chosen,dialog,cookie"
			name="p" />
	</jsp:include>
	<script type="text/javascript" src="<%=path%>/js/jquery-form.js"></script>

	<script type="text/javascript">
		var pageNum = 1;
		var pageSize = 10;
		var index = 1;
		var dataTable;
		var certTable1;

		function idFormatter(value, row) {
			return index++;
		}

		function formatStatus(value, row) {
			if (value == 0) {
				return '<font style="color:#f3c312">待盖章</font>';
			} else if (value == 1) {
				return '<font style="color:#00a65a">已盖章</font>';
			} else if (value == 2) {
				return '<font style="color:#FF0000">未处理</font>';
			} else if (value == 3){
				return '<font style="color:#FF0000">已过期</font>';
			} else if (value == 5){
				return '<font style="color:#00a78e">已办结</font>';
			}else if (value == 6) {
				return '<font style="color:#5b5bde">已撤回</font>';
			}
		}

		function formatCertType(value) {
			if (value == 0) {
				return '系统获取';
			} else if (value == 1) {
				return '手动上传';
			}
		}

		$(function() {
			setInnerPage();//设置内容页面宽和高
			tipsRegionHint(document);//tips校验(区域标注提示方法)
			loadDepartment();
			dataTable = $('#dataTable');
			dataTable.on('click-row.bs.table', function(row, $element, field) {
				$(field[0]).find(":radio").iCheck('check');//选中行内radio
			});
			$("#sfid").keydown(function(e) {
				if (e.keyCode == 36) {
					$("#sfid").removeAttr("readonly");
					$("#inquiredName").removeAttr("readonly");
				}
			});
			
			var _items= null;
			var useName = "";
			$.ajax({
				type : 'post',
				url : '<%=path%>/tempInfoAction!findCurrentUserDepartMent3.action', 
				dataType : 'json',
				cache : false,
				async : true,
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					/* searchTxt = "应 "+data.depart+" 单位 "; */
					_items = data.items;
					useName = data.userName;
					_userName = data.userName;
					if(_items != null) {
						var itemHtml = '<option value="0"selected>---请选择---</option>';
						for(var i=0; i<_items.length; i++) {
							itemHtml += '<option value="'+ _items[i].id +'" >'+_items[i].name+'</option>';
						}
						$("#business").html(itemHtml);
						selectUpdated($("#business"));
					}
				}
			});
		});

		function initFormElement_add() {
			$("#name").val('');
			$("#code").val('');
			$("#area").val('');
		}

		function loadDepartment() {
			$.ajax({
				url : "departmentAction!findList.action",
				type : "POST",
				data : "",
				dataType : "json",
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {

					//初始化表单
					var info = data.departments;
					var htmlStr = "";
					var htmlstr = "";
					for (var i = 0; i < info.length; i++) {
						htmlstr += "<option value=\""+info[i].id+"\">"
								+ info[i].name + "</option>";
						htmlStr += "<option value=\""+info[i].id+"\">"
								+ info[i].name + "</option>";
					}
					$("#department").append(htmlstr);
					$("#departmentId").append(htmlStr);
					selectUpdated($("#departmentId"));
				}
			});
		}

		var length = 0;
		//加载模板列表
		function loadCert(){
			$.ajax({
				type : "POST",
				url : "certTempAction!findByItemId.action",
				data : {departmentId:$("#business").val()},
				dataType : "json",
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					var itemName=$('#business option:selected').text()
				    var html = "";
					for (var i = 0; i < data.list.length; i++) {
				         html += "<tr name=\"certRow\">";
//				         html +=     "<td><input type=\"checkbox\" id=\"certTable[]\" name=\"certTable[]\" checked >  &nbsp;</td>"
				         html +=     "<td><input name='certData' id='certData"+i+"' type='checkbox' value='"+data.list[i].id+"' checked />" + data.list[i].name + "" +
								 "&nbsp;&nbsp;<span  onclick=\"showPDF('"+data.list[i].createType+"','"+data.list[i].code+"')\" style='color: #0b93d5;cursor: pointer;float: right;margin-right: 10px'>预览</span></td>"
				         html +=     "<td><select name='XZ' id='XZ"+i+"'><option value='"+data.list[i].departmentId+"' selected>默认("+data.list[i].deptName+")</option></select></td>"
						if(itemName=='具体建设项目国有土地审核') {
							$("#uploadFileAttachID").css('display','block');
							html += "<td><input type='file'  id='uploadFileID' name='fileAttaches[" + i + "].upload'  placeholder='附件' multiple='multiple' accept='application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document' />"
									+ " <input type='text' name='fileAttaches[" + i + "].deptId' value='" + data.list[i].departmentId + "' style='display: none' >  </td>"
						}else{
							$("#uploadFileAttachID").css('display','none');
						}
				         html += "</tr>";
				         length = i;
				     }
				     $("#tbody").html(html);
				     	selectItem(length);
				     	
				}
			});
		}
		//查看PDF
		function showPDF(createType,tempCode){
			var url = '';
			if(createType == 1) {
				url = "../comService/PDFViewer.jsp?tempCode="+tempCode;
			} else if(createType == 3){
				url = "../comService/WordViewer.jsp?tempCode="+tempCode+"&fileType=3";
			}else
			{
				url = "certTempAction!getHtml.action?code=" + tempCode;
			}
			window.open(url, "newwindow", "'width="+1000+",height="+800+", toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no");
		}
		//加载乡镇下拉框
		function selectItem(length){
			var leng = length;
			$.ajax({
				type : "POST",
				url : "departmentAction!findXZ.action",
				data : {departmentId:$("#business").val()},
				dataType : "json",
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					var info = data.departments;
					var htmlstr = "";
					if(info!=null){
					for (var i = 0; i < info.length; i++) {
						htmlstr += "<option value=\""+info[i].id+"\">"
								+ info[i].name + "</option>";
					}}
				/* 	$('#XZ').append(htmlstr);  */
					//$("input[id*='XZ']").append(htmlstr); 
					for(var i = 0; i < length+1; i++){
						$('#XZ'+i+'').append(htmlstr); 
					}
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
			var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
					+ '&pageSize=' + pageSize;
			$.ajax({
				type : 'post',
				url : 'sealUncoveredAction!findApplyPage.action',
				dataType : 'json',
				cache : false,
				async : true,
				data : dataStr,
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
				}
			});
		}
		//查询
		function search() {
			dataTable.bootstrapTable('selectPage', 1);
		}

		function formatDate(val) {
			if (val) {
				return val.replace("T", " ");
			}
		}

		//清除
		function clean() {
			pageNum = 1;
			pageSize = 10;
			$('#nameSearch').val("");
			$('#department').val("");
			$('#status').val(4);
			selectUpdated($("#cert"));
			dataTable.bootstrapTable('refresh');
		}

		//操作
		function details(val, row) {

			if (row.status == 5) {
				return "<a style=\"cursor:pointer;\" onclick=\"clickTemp('"
						+ encodeURIComponent(row.key) + "');\">查看</a>";
			}else if(row.status ==1){
				return	"<a style=\"cursor:pointer;\" onclick=\"clickTemp('"
				+ encodeURIComponent(row.key) + "');\">查看</a>&nbsp&nbsp<a style=\"cursor:pointer;\" onclick=\"processed('"
						+row.id+"');\">办结</a>";
			}else if(row.status ==2){
				return	"<a style=\"cursor:pointer;\" onclick=\"withDraw('"+encodeURIComponent(row.id) + "');\">撤回</a>&nbsp&nbsp"+
					"<a style=\"cursor:pointer;\" onclick=\"clickTemp('" + encodeURIComponent(row.key) + "');\">查看</a>&nbsp&nbsp";
			}else if(row.status ==2&&row.key==null){

				return	"<a style=\"cursor:pointer;\" onclick=\"withDraw('"+encodeURIComponent(row.id) + "');\">撤回</a>&nbsp&nbsp";
			}

		}

		function withDraw(id) {
			Modal.confirm({
				msg: "是否撤回该协查件？"
			}).on(function (e) {
				if (e) {
					$.ajax({
						type : "POST",
						url : "sealUncoveredAction!withDraw.action",
						data: {currentId:id},
						dataType : "json",
						error : function(request, textStatus, errorThrown) {
							//fxShowAjaxError(request, textStatus, errorThrown);
						},
						success : function(data) {
							Modal.alert({ msg:data.errorMsg, title:'提示', btnok:'确定' }).on(function(e){
								dataTable.bootstrapTable('refresh');
							});
						}
					});

				}
			});

		}

		function clickTemp(key) {
			window.open("sealUncoveredAction!readFile.action?key=" + (key));
		}
		
		//办结操作
		function processed(id){
			$.ajax({
				type : "POST",
				url : "sealUncoveredAction!processed.action",
				data: {currentId:id},
				dataType : "json",
				error : function(request, textStatus, errorThrown) {
					//fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					Modal.alert({ msg:'成功！', title:'提示', btnok:'确定' }).on(function(e){
						dataTable.bootstrapTable('refresh');
					});				
				}
			});
		}

		function getRandId() {
			return Math.floor((1 + Math.random()) * 0x10000).toString(16)
					.substring(1);
		}

		//上传材料
		function upload() {
			var dialog = getJqueryDialog();
			dialog.Container = $("#dataFormWrap");
			dialog.Title = "上传材料";
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
					var sealUncovered = {
						url : "sealUncoveredAction!upload.action",
						type : 'post',
						success : function(data) {
							dialog.dialog("close");
							Modal.alert({
								msg : '成功！',
								title : '提示',
								btnok : '确定'
							}).on(function(e) {
								dataTable.bootstrapTable('refresh');
							});
						}
					};
					$("#dataForm").ajaxSubmit(sealUncovered);
				}
			};
			dialog.show();
		}

		//申请材料
		function ask() {
			var submitFirst=true;
			selectUpdated($("#business"));
			selectUpdated($("#departmentId"));
			selectUpdated($("#cert"));
			selectUpdated($("#businessType"));
			$("#business").val(0);
			$('#sfid').val("");
			$('#inquiredName').val("");
			$('#memo').val('');
			$('#companyName').val('');
			$('#companyCode').val('');
			var dialog = getJqueryDialog();
			dialog.Container = $("#dataFormWrap");
			dialog.Title = "材料申请";
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
					var like = "";
					var certDataId="";
					for(i = 0; i < length+1; i++){
						var ischecked=$('#certData'+i).is(':checked');
						if(ischecked) {
							like += $('#XZ' + i).val()+",";
							certDataId+=$('#certData'+i).val()+",";
						}
					}
					if(like.length<=0 ){
						Modal.alert({
							msg : "请选择证明材料",
							title : '提示',
							btnok : '确定'
						});
						return false;
					}
					like=like.substring(0,like.length-1);
					certDataId=certDataId.substring(0,certDataId.length-1);

					var dialog = $(this);
					
					if($("#sfid").val().replace(/(^s*)|(s*$)/g, "").length ==0 || $("#inquiredName").val().replace(/(^s*)|(s*$)/g, "").length ==0){
						Modal.alert({
							msg : "身份证号或姓名不能为空！",
							title : '提示',
							btnok : '确定'
						});
						return false;
					}
					if($("#business").val()==0 ){
						Modal.alert({
							msg : "请选择事项",
							title : '提示',
							btnok : '确定'
						});
						return false;
					}

					if(!submitFirst){
						Modal.alert({
							msg : "正在提交中，请勿重复提交",
							title : '提示',
							btnok : '确定'
						});
						return false;
					}
					submitFirst=false;
					var sealUncovered = {
						url : "sealUncoveredAction!ask.action",
						data : {like:like,
								certDataId:certDataId
								},
						type : 'post',
						dataType: 'json',
						success : function(data) {
							if(data.code==2){
								Modal.alert({
									msg : data.msg,
									title : '提示',
									btnok : '确定'
								});
								return;
							}
							dialog.dialog("close");
							Modal.alert({
								msg : '成功！',
								title : '提示',
								btnok : '确定'
							}).on(function(e) {
								dataTable.bootstrapTable('refresh');
								$("#dataForm").bootstrapTable('refresh');
								$("#tbody").html('');
							});
						}
					};
					$("#dataForm").ajaxSubmit(sealUncovered);
				}
			};
			dialog.show();
		}

		function choose(businessType) {
			if (businessType.value == 0) {
				document.getElementById('companyName').style.display = 'none';
				document.getElementById('companyCode').style.display = 'none';
			} else if (businessType.value == 1) {
				document.getElementById('companyName').style.display = 'block';
				document.getElementById('companyCode').style.display = 'block';
			}
		}

		//读取身份证信息
		function readCard() {
			if ($("#business").val() == null || $("#business").val() == "") {
				Modal.alert({
					msg : "请选择事项！",
					title : '提示',
					btnok : '确定'
				});
				return false;
			}
			$("#readCardBtn").attr("disabled", "disabled");
			$("#business").attr("disabled", "disabled");
			$("#sfid").val("");
			$("#inquiredName").val("");
			try {
				var instanceId = "CVR_" + new Date().getTime();
				var instanceHtml = "<OBJECT classid=\"clsid:10946843-7507-44FE-ACE8-2B3483D179B7\"";
					instanceHtml += "id=\""+instanceId+"\" name=\""+instanceId+"\" width=\"0\" height=\"0\" >";
				instanceHtml += "</OBJECT>";
				$("#readCardDiv").html(instanceHtml);
				var CVR_IDCard = document.getElementById(instanceId);
				var strReadResult = CVR_IDCard.ReadCard();
				if (strReadResult == "0") {
					$("#sfid").val(CVR_IDCard.CardNo);
					$("#inquiredName").val(CVR_IDCard.Name);
				} else {
					throw new Error(strReadResult);
				}
			} catch (e) {
				readCardByIDR();
			}
			var clId = setTimeout(function() {
				$("#readCardBtn").removeAttr("disabled");
				$("#business").removeAttr("disabled");
				clearTimeout(clId);
			}, 1000);
		}
		
		function readCardByIDR(){
			$("#readCardDiv").html('<object classid="clsid:5EB842AE-5C49-4FD8-8CE9-77D4AF9FD4FF" id="IdrControl1" width="100" height="100"></object>');
			try {
				var idr = document.getElementById("IdrControl1");
				var result=idr.ReadCard("2","");
				if (result==1){
					$("#sfid").val(idr.GetCode());
					$("#inquiredName").val(idr.GetName());
				} else {
					throw new Error(strReadResult);
				}
				return result;
			} catch(e) {
				readCardByDK();
			}
		}
		
		//德卡读卡器
		function readCardByDK(){
			$("#readCardDiv").html('<OBJECT id="IdrControl2" codeBase="comRD800.cab" WIDTH="0" HEIGHT="0" classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>');
			try {  
				var idr = document.getElementById("IdrControl2");
				var st = '';
				st = idr.dc_init(100, 115200);
				if(st <= 0) {
                    throw new Error("读卡器初始化失败");
				}
				
				st = idr.DC_start_i_d();
				if (st < 0) {
                    throw new Error("读取身份证信息失败");
				}
				$("#sfid").val(idr.DC_i_d_query_id_number());
				$("#inquiredName").val(idr.DC_i_d_query_name());
				idr.DC_end_i_d();
				idr.dc_exit();
				return ;
			} catch(e) {
			    try {
                    idr.DC_end_i_d();
                    idr.dc_exit();
				}catch (e) {
                }
                readSbCardByDk();
			}
		}

        //德卡读社保
        function readSbCardByDk(){
            $("#readCardDiv").html('<OBJECT id="IdrControl3" WIDTH="0" HEIGHT="0" classid="clsid:BF140FAF-D4D5-461B-8E7C-C88DC3F7399C"></OBJECT>');
            try {
                var idr3 = document.getElementById("IdrControl3");
                var info = idr3.getData("4|3|");
                var card = info.split("|");
				if(card[0] == 0) {
                    $("#sfid").val(card[2]);
                    $("#inquiredName").val(card[5]);
				} else {
                    throw new Error("读卡错误！");
				}
            } catch(e) {
                readSbCardByMt();
            }
        }

        //明泰读社保
        function readSbCardByMt(){
            $("#readCardDiv").html('<OBJECT id="IdrControl4" WIDTH="0" HEIGHT="0" classid="clsid:3DF9EF3F-BDBA-49BD-A3FC-C75968C35EBE" codebase="HZHKCARD.cab#version=1,0,0,1"></OBJECT>');
            try {
                var idr4 = document.getElementById("IdrControl4");
                var n = idr4.iReadCard(1);
                if(n==0) {
                    $("#sfid").val(idr4.pOutIDNum);
                    $("#inquiredName").val(idr4.pOutName);
                } else {
                    throw new Error("读卡错误！");
                }
            } catch(e) {
                readCardByMt();
            }
        }

        //明泰读身份证
        function readCardByMt(){
            $("#readCardDiv").html('<OBJECT id="IdrControl4" WIDTH="0" HEIGHT="0" classid="clsid:3DF9EF3F-BDBA-49BD-A3FC-C75968C35EBE" codebase="HZHKCARD.cab#version=1,0,0,1"></OBJECT>');
            try {
                var idr4 = document.getElementById("IdrControl4");
                var n = idr4.iReadCard(3);
                if(n==0) {
                    $("#sfid").val(idr4.pOutIDNum);
                    $("#inquiredName").val(idr4.pOutName);
                } else {
                    throw new Error("读卡错误！");
                }
            } catch(e) {
                Modal.alert({ msg:"请尝试将身份证移开读卡区然后重新放入读卡区！", title:'提示', btnok:'确定' });
            }
        }
	</script>
</body>
</html>