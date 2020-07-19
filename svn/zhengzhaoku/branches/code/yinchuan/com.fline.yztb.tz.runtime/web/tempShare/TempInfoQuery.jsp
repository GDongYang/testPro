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
													<select class="form-control chosen-select-deselect" id="itemCode" name="itemCode" style="width:245px;">
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
											<th data-field="state" data-formatter="stateFormatter">状态</th>
											<th data-field="timeConsuming" >耗时(ms)</th>
											<th data-field="" data-formatter="details">操作</th>
											<th data-field="" data-formatter="fankui">反馈</th>
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

				
				$.ajax({
					type : 'post',
					url : '<%=path%>/tempInfoAction!findCurrentUserDepartMent2.action',
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						/* searchTxt = "应 "+data.depart+" 单位 "; */
						searchTxt = "应 "+data.depart+" ";
						_items = data.items;
						dataPid = data.depar;
						useName = data.userName;
						_userName = data.userName;
						if(_items != null) {
							var itemHtml = '<option value="">---请选择---</option>';
							for(var i=0; i<_items.length; i++) {
								if(i == 0){
									//    <optgroup label="部门"> </optgroup>
								itemHtml += '<optgroup label="'+ _items[i].departmentName +'">';
								itemHtml += '<option value="'+ _items[i].innerCode +'">'+_items[i].name+'</option>';
								}else if(i == _items.length - 1 ){
									if(_items[i].departmentId == _items[i-1].departmentId){
									itemHtml += '<option value="'+ _items[i].innerCode +'">'+_items[i].name+'</option>';
									}else{
										itemHtml += '</optgroup>';
										itemHtml += '<optgroup label="'+ _items[i].departmentName +'">';
										itemHtml += '<option value="'+ _items[i].innerCode +'">'+_items[i].name+'</option>';
									}
									//结尾</optgroup>
								itemHtml += '</optgroup>';
								}else{
									if(_items[i].departmentId == _items[i-1].departmentId){
										itemHtml += '<option value="'+ _items[i].innerCode +'">'+_items[i].name+'</option>';
									}else{
										itemHtml += '</optgroup>';
										itemHtml += '<optgroup label="'+ _items[i].departmentName +'">';
										itemHtml += '<option value="'+ _items[i].innerCode +'">'+_items[i].name+'</option>';
									}
								}	
							}
							$("#itemCode").html(itemHtml);
							selectUpdated($("#itemCode"));
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
					url : '<%=path%>/tempInfoAction!findWZMTempInfo.action',
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
							} else {
								var datas = data.result;
								for(var i=0;i<datas.length;i++) {
									if("tz_fy_cer_080" == datas[i].certCode && "1" == datas[i].state ) {
										alert("被查询人为失信人员！");
									}
								}
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
				if(row !=null && row.state == 1){
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
			
			function fankui(val,row) {
				if(row) {
					sfid = cerNo;
					certName = row.certName;
					var busiCode = row.BusinessCode;
					return "<a style=\"cursor:pointer;\" onclick=\"openFanKui('"+sfid+"','"+certName+"','"+row.certCode+"','"+ busiCode +"');\">反馈</a>";
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
			
			
		</script>
	</body>
</html>