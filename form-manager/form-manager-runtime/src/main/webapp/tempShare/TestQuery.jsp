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
		<!-- ./wrapper -->
		<!-- read Card ID -->
		<div id="readCardDiv">
			  <form class="form form-inline base_margin-b-200" id="searchForm">
				<label class='base_margin-r-10'>测试SQL：</label>
                  <!-- <input type="text" class="form-control" id="sql" name="sql" placeholder="测试sql" /> -->
                  <textarea class="form-control" id="sql" name="sql" placeholder="测试sql" rows="10" cols="20"></textarea>
	  			<button  type="button" class="btn btn-primary btn-sm" onclick='testSql()'><i class="fa fa-search base_margin-r-5"></i>测试</button>
	  			<button  type="button" class="btn btn-primary btn-sm" onclick='clean()'><i class="fa fa-trash-o base_margin-r-5"></i>清空</button>
               </form>
               <form class="form form-inline base_margin-b-20" id="searchForm">
				<label class='base_margin-r-10'>返回结果</label>
                  <!-- <input type="text" class="form-control" id="sql" name="sql" placeholder="测试sql" /> -->
                  <textarea class="form-control" id="returnData" name="returnData" placeholder="返回结果" rows="10" cols="20"></textarea>
	  		   </form>
	  		</form>
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
			function testSql(){
                var sql = $("#sql").val()
                console.log("sql is " + sql)
                $.ajax({
					type : 'post',
					url : '<%=path%>/businessAction!testSql.action', 
					dataType : 'json',
					cache : false,
                    async : true,
                    data : {"sql":sql},
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
                        if(data != null){
							$("#returnData").val("");
                            $("#returnData").val(data.result)
                        }
					}
				});
			}
			function clean(){
				$("#sql").val("");
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
			// function clean() {
			// 	pageNum = 1;
			// 	pageSize = 10;
			// 	$('#searchForm input').val("");
			// }
			
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