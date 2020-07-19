<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta name="description" content="3 styles with inline editable feature" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>申请材料审批</title>
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
									<div
										class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
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
							<i class="fa fa-tag"></i> <span class="base_text-size-15">证件材料盖章</span>
						</h3>
						<div class="box-tools pull-right">
							<button type="button" class="btn btn-xs btn-info"
								onClick="doSignByActiveX()">本地Ukey盖章</button>
							<button type="button" class="btn btn-xs btn-info"
								onClick="uploadFile()">上传</button>
						</div>
					</div>
					<div class="box-body">
						<div class="form-group">
							<table id="dataTable" class="box base_tablewrap"
								data-toggle="table" data-locale="zh-CN" data-ajax="ajaxRequest"
								data-side-pagination="server" data-striped="true"
								data-single-select="true" data-click-to-select="true"
								data-pagination="true" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页">
								<thead style="text-align: center;">
									<tr>
										<th data-checkbox="true"></th>
										<!-- <th data-field="id" data-formatter="idFormatter" data-width="20">序号</th> -->
										<!-- <th data-radio="true"></th> -->
										<th data-field="status" data-formatter="formatStatus"
											data-width="60">盖章状态</th>
										<th data-field="applyDept">申请部门</th>
										<th data-field="name">申请人</th>
										<th data-field="applyPersonPhone">申请人手机号</th>
										<th data-field="createDate" data-formatter="formatDate">申请时间</th>
										<th data-field="cert">证件材料</th>
										<th data-field="business">事项</th>
										<th data-field="inquiredName">被查询人</th>
										<th data-field="sfid">身份证号</th>
										<th data-field="companyName">企业名称</th>
										<th data-field="companyCode">企业统一信用代码</th>
										<th data-field="memo">备注</th>
										<th data-field="excuteDate" data-formatter="formatDate">处理时间</th>
										<th data-field="signDate" data-formatter="formatDate">盖章时间</th>
										<th data-field="" data-formatter="details" id="operateId" data-width="128px"  >操作</th>
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
		<form class="form-horizontal base_dialog-form" id="dataForm"
			name="dataForm">
			<input type="hidden" id="id" name="id" value="0">
			<div
				class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group ">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">姓名</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text" id="sfName"
						name="sfName" placeholder="姓名" readonly="readonly" />
				</div>
			</div>
			<!-- /.base_query-group -->
			<div
				class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group ">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">身份证号码</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text" id="sfId"
						name="sfId" style="width: 205px;" placeholder="身份证号码"
						readonly="readonly" />
				</div>
			</div>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">事项</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<select class="form-control chosen-select-deselect" id="itemCode"
						name="itemCode" style="width: 245px;">
					</select>
				</div>
			</div>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15"
					for="name">材料名称</label>
				<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
					<input type="text" class="form-control base_input-text" id="cert"
						name="cert" placeholder="材料名称" readonly="readonly" />
				</div>
			</div>
		</form>
	</div>

	<!--上传材料-->
	<div id="dataFormWrap1" class="base_hidden">
		<form class="form-horizontal base_dialog-form" id="dataForm1"
			name="dataForm1">
			<input type="hidden" id="fileId" name="id" value="0">
			<div class="form-group">
				<label
					class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15">上传材料</label>
				<input type="file" name="file" id="file" accept=".doc,.docx,.pdf" />
			</div>

		</form>
	</div>

	<!-- 调取控件加盖印章 -->
	<div id="dataFormWrap2" class="base_hidden">
		<form class="form-horizontal base_dialog-form" id="dataForm2"
			name="dataForm2">
			<div>
				<button type="button" class="btn btn-sm btn-info" onclick="sign()">加盖印章</button>
				&nbsp&nbsp&nbsp&nbsp&nbsp
				<button type="button" class="btn btn-sm btn-info"
					onclick="saveFile()">保存</button>
			</div>

			<object id="TGPDFPlugIn"
				classid="clsid:04DDDFAA-0AC0-4D47-9315-9F442F65D403" width="800"
				height="800">
				<embed name="TGPDFPlugIn" type="application/nptgpdfplugin"
					width="900" height="800"></embed>
			</object>
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
			var basePath = "";
			
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
			
			
			function reason(memo) {
				Modal.alert({
					msg : memo,
					title : '驳回原因',
					btnok : '确定',
				});
			}
			
			function formatCertType(value){
				if(value == 0 ){
					return '系统获取';
				}else if(value == 1){
					return '手动上传';
				}
			}
			
			$(function(){
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				selectConfig();//下拉框初始化
				loadDepartment();
				loadCurrentUser();
				dataTable = $('#dataTable');
				dataTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
				//获取请求当前页面的请求地址 http://localhost:8080/people/toGetPeopleList.action
				var curRequestPath = window.document.location.href;
				//获取项目请求路径 /people/toGetPeopleList.action
				var pathName = window.document.location.pathname;
				var ipAndPort = curRequestPath.indexOf(pathName); 
				var localhostPath = curRequestPath.substring(0,ipAndPort);
				var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
				basePath = localhostPath + projectName;

			});
			
	    	var currentUser;
	    	function loadCurrentUser() {
				$.ajax({
					url:"userSessionAction!loadCurrentUser.action",
					type:"POST",
					dataType:"json",
					error:function(request,textStatus, errorThrown){
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success:function(data){
						currentUser = data;
					}
				});
	    	}
			
			function initFormElement_add() {
				$("#name").val('');
				$("#code").val('');
				$("#area").val('');
			}
			
			function formatDate(val) {
				if(val) {
					return val.replace("T"," ");
				}
			}
			
			function loadDepartment() {
				$.ajax({
					url : "departmentAction!findList.action",
					type : "POST",
					data : "",
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//初始化表单
						var info = data.departments;
						var htmlStr = "";
						var htmlstr = "";
						for (var i = 0; i < info.length; i++) {
							htmlstr += "<option value=\""+info[i].id+"\">" + info[i].name + "</option>";
							htmlStr += "<option value=\""+info[i].id+"\">" + info[i].name + "</option>";
						}
						$("#department").append(htmlstr);
						$("#departmentId").append(htmlStr);
						selectUpdated($("#department"));
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
					url : 'sealUncoveredAction!findSealPage.action',
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
							rows : datas ?  datas : []
						});
						params.complete();
					}
				});
			}
			//查询
			function search() {
				dataTable.bootstrapTable('selectPage', 1);
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#nameSearch').val("");
				$('#department').val("");
				$('#status').val(4);
				dataTable.bootstrapTable('refresh');
			}
			
			//加盖印章
			function doSign() {
				confirm("确认加盖印章？")
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length >= 1) {
					if(obj[0].status != 0) {
						Modal.alert({ msg:'请选择待盖章的数据', title:'提示', btnok:'确定' });
					  return;
					}
					var row = obj[0];
					$.ajax({
						cache : false,
						type : "post",
						url : 'sealUncoveredAction!doSign.action',
						data:{'id':row.id,'sfid':row.sfid,'cert':row.cert,'busicode':row.busicode,'departmentId':row.departmentId
								,'name':row.name,'createDate':row.createDate,'signDate':row.signDate},
						dataType : 'json',
						async : false,
						error : function(request, textStatus, errorThrown) {
							fxShowAjaxError(request, textStatus, errorThrown);
						},
						success : function(data) {
							Modal.alert({ msg:'成功！', title:'提示', btnok:'确定' }).on(function(e){
								dataTable.bootstrapTable('refresh');
							});
						}
					});
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要盖章的材料！', title:'提示', btnok:'确定' });
				}
			}
			
			
			//在弹窗口添加dom
			 function parseDom(arg) {

				　　 var objE = document.createElement("div");

				　　 objE.innerHTML = arg;

				　　 return objE.childNodes[0];

			};
				
			//使用天谷客户端控件加盖电子印章
			var TGPDFPlugIn;
			var key;
			var currentPath;
			function doSignByActiveX(){
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				if (obj.length >= 1) {
					if(obj[0].status != 0) {
						Modal.alert({ msg:'请选择未盖章的数据！', title:'提示', btnok:'确定' }).on(function(e){
						});
						return;
					}
					key = obj[0].key;
					var id= obj[0].id;
					var dialog = getJqueryDialog();
					var tgpDom = document.getElementById('TGPDFPlugIn');
					var tgpDomStr = '<object id="TGPDFPlugIn" classid="clsid:04DDDFAA-0AC0-4D47-9315-9F442F65D403" width="800" height="500"><embed name="TGPDFPlugIn" type="application/nptgpdfplugin" width="900" height="500"></embed></object>'
					var tgpForm = document.getElementById('dataForm2');
					tgpForm.removeChild(tgpDom);
					tgpForm.appendChild(parseDom(tgpDomStr)) ;
					
			   		dialog.Container = $("#dataFormWrap2");
			   		dialog.Title = "加盖印章";
			   		dialog.CloseOperation = "destroy";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 800;
			   		}
			   		dialog.Height = Math.round($(window).height());//设置对话框高度
			   		//dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataFormWrap"));};
			   		load();
			   		setTimeout("initActiveX()",500);
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							VerifySign();
							console.log("signStatus=="+currentPath)
							if(currentPath==1){
								$(this).dialog("close");
								Modal.alert({ msg:'请先加盖印章！', title:'提示', btnok:'确定' }).on(function(e){
								});
							}else if(currentPath==0){
								TGPDFPlugIn.ControlPDF(205);
								GetCurrentFile();
								uploadCurrentFileToServer(id,currentPath)
								console.log("上传服务器成功~~~")
								$(this).dialog("close");
								Modal.alert({ msg:'成功！', title:'提示', btnok:'确定' }).on(function(e){
									dataTable.bootstrapTable('refresh');
								});
							}else{
								$(this).dialog("close");
								Modal.alert({ msg:'印章验证失败！', title:'提示', btnok:'确定' }).on(function(e){
									dataTable.bootstrapTable('refresh');
								});
							}
						}
					};
			   		dialog.show();
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择待盖章的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//控件上传文件到服务器
			function uploadCurrentFileToServer(id,filePath) {
				// removeCookie('USER_SESSION_ID');
				var extraParam = "{\"key\":\""+key+"\",\"id\":\""+id+"\",\"sealPerson\":\""+currentUser.name+"\",\"sealPersonId\":\""+currentUser.id+"\"}";
				var url=basePath+"/sealUncoveredAction!complete.action?USER_SESSION_ID="+getCookie('USER_SESSION_ID');

	            var Res = TGPDFPlugIn.UploadFileToServer(url, filePath,'file', extraParam);
	        }
			function removeCookie(key){
				setCookie(key,"",-1); // 把cookie设置为过期
			};
			function setCookie(key,value,t){
				var oDate=new Date();
				oDate.setDate(oDate.getDate()+t);
				document.cookie=key+"="+value+"; expires="+oDate.toDateString();
			}
			function getCookie(cookie_name)
			{
				var allcookies = document.cookie;
				var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度

				// 如果找到了索引，就代表cookie存在，
				// 反之，就说明不存在。
				if (cookie_pos != -1)
				{
					// 把cookie_pos放在值的开始，只要给值加1即可。
					cookie_pos += cookie_name.length + 1;      //这里容易出问题，所以请大家参考的时候自己好好研究一下
					var cookie_end = allcookies.indexOf(";", cookie_pos);

					if (cookie_end == -1)
					{
						cookie_end = allcookies.length;
					}

					var value = unescape(allcookies.substring(cookie_pos, cookie_end));         //这里就可以得到你想要的cookie的值了。。。
				}
				return value;
			}
			//根据所选项用控件打开文件
			function initActiveX(){
				var pdfUrl = encodeURI(basePath)+"/wzmCity/sealUncoveredAction!readFile.action?key="+(key);
				TGPDFPlugIn.DisplayToolBar(0, 0);
				//TGPDFPlugIn.LoadPDF(encodeURI(encodeURI(pdfUrl)));
				TGPDFPlugIn.LoadPDF(encodeURI(pdfUrl));
			}
			
			//获取文档路径
			function GetCurrentFile(){
				 var outInfo;
		         var outInfoLen;
		         var Res = TGPDFPlugIn.GetCurrentDocInfo(1, outInfo, outInfoLen);
		        // ShowResultMessage("GetCurrentFileInfo", Res);
		         console.log("currentPath==="+currentPath)
		         
			}
			
			 //验证签名
	        function VerifySign() {
	            var Res = TGPDFPlugIn.VerifyPDF(11205, 0);
	            ShowResultMessage("VerifySign", Res);
	        }
			
			//控件盖章
			function sign(){
				TGPDFPlugIn.ControlPDF(801);
			}
			//文件另存为
			function saveFile(){
				TGPDFPlugIn.ControlPDF(402);
			}
			
			//打开文件
			function openFile(){
				TGPDFPlugIn.ControlPDF(1);
			}
			//控件demo
			function load(){
				TGPDFPlugIn = getPluginObjcet("TGPDFPlugIn");
				 attach_event();
			}
			//控件demo
			 function getPluginObjcet(objname) {
		            if (!isIEBrowser()) {
		                if (document.embeds && document.embeds[objname])
		                    return document.embeds[objname];
		            }
		            else {
		                return document.getElementById(objname);
		            }
		        }
			 
			 //控件demo
		        function isIEBrowser() {
		            var isAtLeastIE11 = !!(navigator.userAgent.match(/Trident/) && !navigator.userAgent.match(/MSIE/));

		            if (isAtLeastIE11) {
		                return true;
		            }

		            return (navigator.appName.indexOf("Microsoft Internet") != -1);
		        }
			//控件demo
	        function attach_event() {
	            try {
	                if (!isIEBrowser()) {
	                	console.log("不是ie~~~~~~~~~~~~")
	                    TGPDFPlugIn.attachEvent("OnEventFinish", "jsOnEventFinish");
	                }
	                else {
	                	console.log("是ie~~~~~~~~")
	                	TGPDFPlugIn.attachEvent("OnEventFinish", jsOnEventFinish);
	                //	TGPDFPlugIn.addEventListener("OnEventFinish", jsOnEventFinish);
	                	console.log("attachEvent成功！")
	                }
	            }
	            catch (e) {
	            	console.log("attach_event抛出异常！")
	            	console.log(e)
	                return false;
	            }
	            return true;
	        }
	        //控件demo
	        function jsOnEventFinish(eventID, errorCode, result)
	        {
	            switch(eventID)
	            {
	                case 11201:
	                    ShowResultMessage("获取当前文件路径" + errorCode, result)
	                    break;
	                case 11202:
						document.form1.filepath.value = result;
	                    ShowResultMessage("文档缓存路径" + errorCode, result)
	                    break;
	                case 11203:
	                    ShowResultMessage("文档总页数" + errorCode, result)
	                    break;
	                case 11204:
	                    ShowResultMessage("文档当前页码" + errorCode, result)
	                    break;
	                case 11205:
	                    ShowResultMessage("签章个数" + errorCode, result)
	                    break;
	                case 11206:
	                    ShowResultMessage("签章信息字符串形式" + errorCode, result)
	                    break;
	                case 11207:
	                    ShowResultMessage("签章信息文件路径" + errorCode, result)
	                    break;
	                case 11300:
	                    SignContext = errorCode;
	                    ShowResultMessage("签名环境", errorCode)
	                    break;
	                case 12600:
	                    ShowResultMessage("上传文件" + errorCode, result)
	                    break;
	                case 12700:
	                    ShowResultMessage("转换文件" + errorCode, result)
	                    break;
	                default:
	                    ShowResultMessage("结果" + errorCode, result)
	                    break;
	            }
	        }
	        //控件demo
	        function ShowResultMessage(msg, result) {
	            var resultMsg = "";
	            try{
	                resultMsg = TGPDFPlugIn.GetErrorMsg(result);
	            }catch(e){

	            }
	            currentPath = result;
	          //  document.form1.Result.value += msg + ":" + result.toString() + resultMsg + "\n";
	          //  document.getElementById('Result').scrollTop = document.getElementById('Result').scrollHeight;
	        }
			
			//驳回
			function refuse() {
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				$('#memo').val('');
				$('#id').val(0);
				if (obj.length >= 1) {
					if(obj[0].status != 0) {
						Modal.alert({ msg:'aaaa！', title:'提示', btnok:'确定' }).on(function(e){
						});
						return;
					}
					$('#id').val(obj[0].id);
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "驳回";
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
						"确定" : function() {
							var dialog = $(this);
							var sealUncovered = {
									url :"sealUncoveredAction!refuse.action",
									type : 'post',
									success : function(data) {
										dialog.dialog("close");
										Modal.alert({ msg:'成功！', title:'提示', btnok:'确定' }).on(function(e){
											dataTable.bootstrapTable('refresh');
										});
									}
								};
							 $("#dataForm").ajaxSubmit(sealUncovered);
						}
					};
			   		dialog.show();
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			
			//处理
			function excute() {
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				$('#id').val(0);
				if (obj.length >= 1) {
					if(obj[0].status != 0) {
						Modal.alert({ msg:'请选择未处理数据！', title:'提示', btnok:'确定' }).on(function(e){
						});
						return;
					}
					$('#id').val(obj[0].id);
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "处理";
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
						"确定" : function() {
							var dialog = $(this);
							var sealUncovered = {
									url :"sealUncoveredAction!excute.action",
									type : 'post',
									success : function(data) {
										dialog.dialog("close");
										Modal.alert({ msg:'成功！', title:'提示', btnok:'确定' }).on(function(e){
											dataTable.bootstrapTable('refresh');
										});
									}
								};
							 $("#dataForm").ajaxSubmit(sealUncovered);
						}
					};
			   		dialog.show();
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			function uploadFileAjax(form,url){
				var ajax_sealUncovered={
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
				form.ajaxSubmit(ajax_sealUncovered);
			};
			
			
			
			//操作
			function details(val,row){
			 	if(row.status==2){
			 		//return "<a style=\"cursor:pointer;\" href=\"../underlineCert/letterOfAcceptance.doc\">下载模板</a>";
					var word1="<a style=\"cursor:pointer;\" onclick=\"dowmloadTemp('"+row.cert+"');\">下载模板</a>";

					var word2="<a style=\"cursor:pointer;\" onclick=\"dowmloadTemp('"+row.cert+"');\">下载模板</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
							"<a style=\"cursor:pointer;\" href=\"createDownloadAttachFile.action?sealUncoveredId="+row.id+ "\">下载附件</a>";
				if(row.isContainFileAttach==0){
                    return word1;
                }else if(row.isContainFileAttach==1){
					$("#operateId").attr('data-width','200');
				    return word2;
                }

			 	}else if(row.status==0 || row.status ==1 || row.status ==5){
					return "<a style=\"cursor:pointer;\" onclick=\"clickTemp('"+encodeURIComponent(row.key)+"');\">查看</a>";
			 	}
			}
			
			function clickTemp(key){
				window.open ("sealUncoveredAction!readFile.action?key=" + (key));
			}
			
			//下载模板
			function dowmloadTemp(cert){
				
				$.ajax({
					type : "POST",
					url : "sealUncoveredAction!downloadFile.action",
					data : {cert:(cert)},
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						window.open("../templates/"+data.certs.deptCode+"/"+data.certs.code+".docx");						
					//	return "<a style=\"cursor:pointer;\" href=\"../templates/"+data.certs.deptCode+"/"+data.certs.code+".doc\">下载</a>";

					}
				});
			}

			
			//上传附件
			function uploadFile() {
				var obj = $('#dataTable').bootstrapTable("getAllSelections");
				var suffix ="";
				$('#fileId').val(0);
				if (obj.length >= 1) {
					if (obj[0].status != 2&&obj[0].status !=0) {
						Modal.alert({ msg:'请选择未处理数据！', title:'提示', btnok:'确定' }).on(function(e){
						});
						return;
					}
					$('#fileId').val(obj[0].id);
					 $("#file").change(function(){
					        var fileName = $(this).val();
					        
					        suffix = fileName.split(".")[fileName.split(".").length-1];
					        console.log("filename:"+fileName)
					        console.log("suffix:"+suffix)
					 });
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap1");
			   		dialog.Title = "上传附件";
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
						"确定" : function() {
							var dialog = $(this);
							var sealUncovered = {
									url :"sealUncoveredAction!uploadFile.action",
									type : 'post',
									data: {"suffix":suffix},
									success : function(data) {
										data=eval('('+data+')');
										var code = data.code;
										if (code == -1) {
											dialog.dialog("close");
											Modal.alert({msg: data.msg, title: '提示', btnok: '确定'}).on(function (e) {
												dataTable.bootstrapTable('refresh');
											});
										} else {
											dialog.dialog("close");
											Modal.alert({msg: '成功！', title: '提示', btnok: '确定'}).on(function (e) {
												dataTable.bootstrapTable('refresh');
											});
										}
									}
								};
							 $("#dataForm1").ajaxSubmit(sealUncovered);
						}
					};
			   		dialog.show();
				} else if(obj.length == 0){
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
			}
			
/* 			function clickTemp(sfid,certCode, busiCode){
				var html = "./preview.jsp?certCode="+certCode+"&cerNo="+sfid+"&busiCode=" + busiCode;
				var windowId = "window_"+getRandId();
				window.open (html, windowId, "'width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+", toolbar=no, menubar=no, location=no,fullscreen=yes,resizable=yes,scrollbars=yes,status=no");
			} */
			
			function getRandId(){
				  return Math.floor((1 + Math.random()) * 0x10000)
	               .toString(16)
	               .substring(1);
			}
			
			

/* 			//取出上传文件格式的后缀名
			var fileAccept = $("#file").val().split(".")[1];//获取上传文件的后缀
			if( fileAccept!="doc" && fileAccept!="docx" && fileAccept!="pdf" ){
				alert("只能上传.doc、.docx和.pfd的文件！");
			} */
		</script>
</body>
</html>