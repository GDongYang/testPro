<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>基本信息</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,icheck,chosen,dialog" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../css/css.css"/>
	</head>
	<body>
		<div class="borderBox">
			<div class="title">基本信息</div>
			<form id="submitForm" onsubmit="return false;" class="form form-horizontal base_margin-t-10">
				<div class="form-group">
					<label class="col-sm-2 control-label">所属部门</label>
					<div class="col-sm-9">
						${param.deptName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">事项名称</label>
					<div class="col-sm-9">
						${param.itemName}
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-9">
						<input class="form-control" id="sfName" name="sfName" value=""/>
					</div>
					<div class="col-sm-1 textRed base_padding-l-0">*</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">身份证</label>
					<div class="col-sm-9">
						<input class="form-control" id="sfId" name="sfId" value=""/>
					</div>
					<div class="col-sm-1 textRed base_padding-l-0">*</div>
				</div>
			</form>
			
			
		</div>
		
		<div name="boxSkin" class="box base_box-area-aqua" >
						<div class="box-header with-border base_box-header">
							<h3 class="box-title">
								<i class="fa fa-tag"></i> <span class="base_text-size-15">证件列表 </span>
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
											<th data-field="name" >材料名称</th> 
											<th data-field="type" data-formatter="typeFormatter">收取方式</th>
											<th data-field="code" data-formatter="details">操作</th>
										</tr>
									</thead>
								</table> 
								
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.box-body -->
					</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,icheck,chosen,dialog" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="../js/jquery-form.js"></script>
		<script type="text/javascript">
		
			var index = 0;
			
			$(function (){
		
			});
			
			function typeFormatter(val) {
				if(val == 3) {
					return "上传";
				} else {
					return "电子证照";
				}
			}
			
			function details(val,row){
				if(row.type == 1) {
					return "<a style=\"cursor:pointer;\" onclick=\"getData('"+row.code+"');\">提取材料</a>";
				}
				//申请表
				else if (row.type == 3) {
                    return "<a style=\"cursor:pointer;\" onclick=\"downloadApplyCert('"+row.deptCode+"','"+row.certCode+"');\">下载</a>";
                }
			}
			
			function submitaa(){
				if($("#username").val() == "" || $("#certName").val() == "" || $("#type").val() == "" || $("#phone").val() == "") {
					alert("必填项请填写完整！");
					return false;
				}
				var form = $('#submitForm');
				var url="opinionAction!create.action";
				uploadFileAjax(form,url);
			};
			function buttonClick(obj){
				$(obj).prev().click();
			};
			function fileChange(file){
			    var upload_file = $.trim($(file).val());    //获取上传文件
			    $(file).prev().val(upload_file);     //赋值给自定义input框
			};
			function uploadFileAjax(form,url){
				var ajax_option={
					url:url,
					type : 'post',
					success:function(data){
						var result = data.result;
						if (result == 1) {
							alert("提交成功！");
							window.close();
				   		} else {
				   			alert(data.msg);
				   		}
					}
				};
				form.ajaxSubmit(ajax_option);
			};
			
		function ajaxRequest(params) {
				
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var datas;
		
				$.ajax({
					type : 'post',
					url : 'certTempAction!findByInnerCode.action', 
					dataType : 'json',
					cache : false,
					async : true,
					data : {'innerCode':'${param.innerCode}'},
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						
						items = data.list ? data.list : [];
						params.success({
							total : data.list.length,
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
		
		function getData(certCode) {
			$.ajax({
				type : 'post',
				url : 'tempInfoAction!findInfoByCertCode.action', 
				dataType : 'json',
				cache : false,
				async : true,
				data : {'certCode':certCode , 'sfId': $("#sfId").val(),'sfName':$("#sfName").val()},
				error : function(request, textStatus, errorThrown) {
					fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					alert("获取成功")
				}
			}); 
		}
		</script>
	</body>
</html>