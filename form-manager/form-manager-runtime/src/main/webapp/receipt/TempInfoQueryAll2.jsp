<%@page import="java.net.URLDecoder"%>
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
			<jsp:param value="table,icheck,chosen,dialog" name="p"/>
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
										    <button type="button" id="readCardBtn" class="btn btn-sm btn-info" onClick="readCard()" style="width:180px;font-size:18px;">读取身份证号</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<div class="row">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group" style="padding-top:5px;">
											    <label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">姓名</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="sfName" name="sfName" placeholder="姓名" readonly="readonly"/>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group" style="padding-top:5px;">
											    <label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">身份证号码</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="sfId" name="sfId" style="width:205px;" placeholder="身份证号码" readonly="readonly" />
												</div>
											</div>
											<!-- /.base_query-group -->
											
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group" style="padding-top:5px;">
											    <label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<input type="text" class="form-control base_input-text" id="itemName" name="itemName" readonly="readonly"/>
													<input type="hidden" id="itemCode" name="itemCode" value="${param.innerCode}"/>
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
											<th data-field="certName" data-formatter="certNameFormatter">材料名称</th>
											<!-- <th data-field="isMust" data-formatter="mustFormatter">是否必要</th>   -->
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
					<!-- /.box -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataForm" name="dataForm">
				
			</form>
		</div>
		
		<!-- read Card ID -->
		<div id="readCardDiv">

		</div>
		<!-- loading -->
		<div id="svg" class="svg" style="display:none;z-index: 10;">
			<img src="/yztb/cert/images/loading.png">
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,icheck,chosen,dialog" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="/yztb/plugins/keypad/js/jquery.plugin.js"></script> 
	    <script type="text/javascript" src="/yztb/plugins/keypad/js/jquery.keypad.js"></script>

		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var dataTable;
			var cerNo = null;
			var dataPid = null;
			var sfName = null;
			
			var sfId = null;
			var certCode = null;
			var searchType;
			
			var _userName = null;
			var _certName = null;
			var allOtherParam =new Array();
			var datas;
			
			var itemName = '<%=URLDecoder.decode(request.getParameter("itemName"), "UTF-8")%>';
			
			function idFormatter(value, row) {
				return index++;
			}
			
			function mustFormatter(val) {
				if(val == 1) {
					return "必要"
				} else {
					return "非必要";
				}
			}
			
			function certNameFormatter(val,row) {
				var value;
				if(val != null) {
					value = val;
				} else if(row.name !=null) {
					value = row.name;
				}
				
				if(row.isMust == 1) {
					return '<lable style="color:red">(必要)</lable>'+value;
				} else if(row.isMust == 0) {
					return '<lable style="color:green">(非必要)</lable>'+value;
				} else {
					return value;
				}
			}
			
			function stateFormatter(val,row) {
				if (row !=null && row.type == 2) {
					return "待查询";
				} else {
					if(val == 0) {
						return "无证件信息";
					} else if (val == 1){
						return "成功";
					} else if(val == 2) {
						return "失败:" + row.errorMsg;
					}
				}
			}
			
			var searchTxt = "";
			var _items = null;
			var useName = "";
			$(function() {
				
				$("#itemName").val(itemName);
				selectConfig();//下拉框初始化
				//bootstrapTable单击事件
				$("#dataTable").on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				//loadCurrentUser();
				dataTable = $('#dataTable');		
				$("#sfId").keydown(function(e){
					if(e.keyCode == 36){
						$("#sfId").removeAttr("readonly");
						$("#sfName").removeAttr("readonly");
					}else if(e.keyCode == 13){
						search();
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
				/* var html = "../preview.jsp?certCode="+certCode+"&cerNo="+sfid+"&busiCode=" + busiCode;
				var windowId = "window_"+getRandId();
				window.open (html, windowId, "'width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+", toolbar=no, menubar=no, location=no,fullscreen=yes,resizable=yes,scrollbars=yes,status=no");
 */			
				var html = "getDownloadReturnFile.action?certCode="+certCode+"&sfId="+cerNo+ "&busiCode=" + busiCode;
				window.open(html);
			}
			
			function getRandId(){
				  return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
			}
			
			//刷新页面
			function refresh() {
				clean();
				search();
			}
			
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var url ;
				var dataStr;
				if(searchType == 1) {
					url = "tempInfoAction!findTempInfoAll.action";
					dataStr = getParams();
				} else {
					url = "certTempAction!findByInnerCode.action";
					dataStr = "innerCode=" + '${param.innerCode}';
				}
		
				$.ajax({
					type : 'post',
					url : url, 
					dataType : 'json',
					cache : false,
					async : true,
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						
						items = data.result ? data.result : [];
						params.success({
							total : data.result.length,
							rows : items
						});
						params.complete();
						datas = items;
						$('input').iCheck({
							checkboxClass : 'icheckbox_square-blue',
							radioClass : 'iradio_square-blue',
							increaseArea : '20%' // optional
						});
						drawICheck("dataTable");
					}
				}); 
			}
		
		function getData(busiCode, certCode, index) {
			console.log(busiCode)
			$('#dataTable').bootstrapTable('updateRow', {index: index, row: {type:4} });
			$.ajax({
				type : 'post',
				url : 'tempInfoAction!findTempInfoOne.action', 
				dataType : 'json',
				cache : false,
				async : true,
				data : {'busiCode':busiCode ,'itemCode':$("#itemCode").val() ,'certCode':certCode , 'sfId': $("#sfId").val(),'sfName':$("#sfName").val()},
				error : function(request, textStatus, errorThrown) {
					fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					$('#dataTable').bootstrapTable('updateRow', {index: index, row: data.result[0]});
				}
			}); 
		}
			
			function refreshRow(index,certCode,busiCode) {
				var sfId = $("#sfId").val();
				var sfName = $("#sfName").val();
				var itemCode = $("#itemCode").val();
				var html="";
				 $.each(allOtherParam, function (i, value) {  
					 if(certCode==value.certCode) {
					 	html+="<div class=\"col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group\">"
							+"<label class=\"col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15\" >"
							+value.paramName+"</label><div class=\"col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area\">"
							+"<input type=\"text\" class=\"form-control base_input-text\"  name=\""+value.paramField+"\" /></div></div>";
					 }
				 }); 
				$("#dataForm").empty()
				$("#dataForm").html(html);
				var dialog = getJqueryDialog();
				dialog.Container = $("#dataFormWrap");
				dialog.Title = "其他参数";
				dialog.Width=100;
				dialog.Height=250;
				dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
					var dataStr = $('#dataForm').serialize() + "&certCode=" +certCode+"&sfId="+sfId+"&sfName="+sfName+"&itemCode="+itemCode+"&busiCode="+busiCode ;
					$.ajax({
						cache : false,
						type : "POST",
						url : 'tempInfoAction!findTempInfoOne.action',
						dataType : 'json',
						data : dataStr,
						async : true,
						error : function(request, textStatus, errorThrown) {
							fxShowAjaxError(request, textStatus,
									errorThrown);
						},
						success : function(data) {
							var items = data.result ? data.result : [];
							$('#dataTable').bootstrapTable('updateRow', {index: index, row: items[0]});
						}
					});
					$(this).dialog("close");
					}
				}
				dialog.show();
				
			}
			
			function downloadApplyCert(deptCode, certCode) {
				window.open("../templates/" + deptCode + "/" + certCode + ".pdf");
            }

			
			function fankui(val,row) {
				if(row != null && row.type == null) {
					sfid = cerNo;
					certName = row.certName;
					var busiCode = row.BusinessCode;
					return "<a style=\"cursor:pointer;\" onclick=\"openFanKui('"+sfid+"','"+certName+"','"+row.certCode+"','"+ busiCode +"');\">反馈</a>";
				} else {
				    return "-";
				}
			}
			
			function openFanKui(sfId,certName,certCode ,busiCode) {
				window.open("../fankui.jsp?sfId=" + sfId +"&certName=" + certName + "&certCode=" + certCode + "&busiCode=" + busiCode);
			}
			
			//查询
			function search() {
				if($("#itemCode").val() == null || $("#itemCode").val() == "") {
					Modal.alert({ msg:"请选择事项！", title:'提示', btnok:'确定' });
					return false;
				}
				searchType = 1;
				cerNo = $("#sfId").val();
				sfName = $("#sfName").val();
				
				$.ajax({
					type : 'post',
					url : 'tempInfoAction!createBusiness.action', 
					dataType : 'json',
					cache : false,
					async : true,
					data : {'itemCode':$("#itemCode").val(), 'sfId': $("#sfId").val(),'sfName':$("#sfName").val()},
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						if(data.busiCode != null && data.busiCode != "") {
							$.each(datas,function (i, d){
								if(d.type != 3) {
									getData(data.busiCode, d.code,i);
								}
							});
						}
					}
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
				var itemCode = $("#itemCode").val();
				var params = {
					'sfId' : sfId,
					'sfName' : sfName,
					'itemCode' : itemCode
				}
				return params;
			}
			//读取身份证信息
			function readCard(){
				if($("#itemCode").val() == null || $("#itemCode").val() == "") {
					Modal.alert({ msg:"请选择事项！", title:'提示', btnok:'确定' });
					return false;
				}
				$("#readCardBtn").attr("disabled","disabled");
				$("#itemCode").attr("disabled","disabled");
				$("#sfId").val("");
				$("#sfName").val("");
				try {
					var instanceId = "CVR_"+new Date().getTime();
					var instanceHtml = "<OBJECT classid=\"clsid:10946843-7507-44FE-ACE8-2B3483D179B7\"";
					instanceHtml += "id=\""+instanceId+"\" name=\""+instanceId+"\" width=\"0\" height=\"0\" >";
					instanceHtml += "</OBJECT>";
					$("#readCardDiv").html(instanceHtml);
					var CVR_IDCard = document.getElementById(instanceId);
					var strReadResult = CVR_IDCard.ReadCard();
					if(strReadResult == "0")
					{
						$("#sfId").val(CVR_IDCard.CardNo);
						$("#sfName").val(CVR_IDCard.Name);
						search();
					}else{
						throw new Error(strReadResult);
					}
				} catch (e) {
					readCardByIDR();
				}
				var clId = setTimeout(function(){
					$("#readCardBtn").removeAttr("disabled");
					$("#itemCode").removeAttr("disabled");
					clearTimeout(clId);
				}, 1000);
			}

			//精伦读卡器
			function readCardByIDR(){
				$("#readCardDiv").html('<object classid="clsid:5EB842AE-5C49-4FD8-8CE9-77D4AF9FD4FF" id="IdrControl1" width="100" height="100"></object>');
				try {
					var idr = document.getElementById("IdrControl1");
					var result=idr.ReadCard("2","");
					if (result==1){
						$("#sfId").val(idr.GetCode());
						$("#sfName").val(idr.GetName());
						search();
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
					$("#sfId").val(idr.DC_i_d_query_id_number());
					$("#sfName").val(idr.DC_i_d_query_name());
					idr.DC_end_i_d();
					idr.dc_exit();
					search();
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
                        $("#sfId").val(card[2]);
                        $("#sfName").val(card[5]);
                        search();
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
                        $("#sfId").val(idr4.pOutIDNum);
                        $("#sfName").val(idr4.pOutName);
                        search();
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
                        $("#sfId").val(idr4.pOutIDNum);
                        $("#sfName").val(idr4.pOutName);
                        search();
                    } else {
                        throw new Error("读卡错误！");
                    }
                } catch(e) {
                    Modal.alert({ msg:"请尝试将身份证移开读卡区然后重新放入读卡去！", title:'提示', btnok:'确定' });
                }
            }
            
            function typeFormatter(val,row,index) {
            	var tag = row.name.indexOf("表");
				if(tag >= 0) {
					return "来自一表通享";
				} else {
					return "来自一证通办";
				}
			}
			
            function details(val,row,index){
				if(index <= 3) {
                	var sfid = $("#sfId").val();
				 	var certCode = row.certCode;
				 	var busiCode = row.BusinessCode;
				 	return "<a style=\"cursor:pointer;\" onclick=\"clickTemp('"+sfid+"','"+certCode+"','"+busiCode+"');\">预览</a>&nbsp&nbsp&nbsp&nbsp"+
					"<a style=\"cursor:pointer;\" href=\"createDownloadReturnFile.action?certCode="+certCode+"&sfId="+sfid+ "&busiCode=" + busiCode + "\">下载</a>";
				} 
			
				//无证件信息
				else  {
					return "无证件信息";
				}
			}
			
		</script>
	</body>
</html>