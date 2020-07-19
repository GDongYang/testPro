<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>个人证件查询所有</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
	</head>
	<body class="base_background-EEF6FB">
		<div class="wrapper">
			<form class="form form-inline base_margin-b-20" id="searchForm">
				<label class='base_margin-r-10'>姓名：</label>
	  			<input type="text" class="form-control" id="sfName" name="sfName" placeholder="姓名" />
	  			<label class='base_margin-r-10'>身份证号码:</label>
	  			<input type="text" class="form-control" id="sfId" name="sfId" placeholder="身份证号" />
	  			<label class='base_margin-r-10'>事项:</label>
	  			<select class="form-control base_width-200" id="itemCode" name="itemCode"></select>
	  			<button  type="button" class="btn btn-primary btn-sm" onclick='search()'><i class="fa fa-search base_margin-r-5"></i>搜索</button>
	  			<button  type="button" class="btn btn-primary btn-sm" onclick='clean()'><i class="fa fa-trash-o base_margin-r-5"></i>清空</button>
	  		</form>
			<div class="tableBox">
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>证件信息</span>
					</h3>
					<div class="tableBtns">
						<button onclick='detail()'><i class="fa fa-list"></i><span>读取身份证号</span></button>
					</div>
				</div>
				<div id="tempDetailWrap" class="tableCont">
					<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="true"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-radio="true"></th>
								<th data-field="materialCode" >材料编码</th>
								<th data-field="materialName" data-formatter="nameFormatter">材料名称</th>
								<th data-field="code" data-formatter="stateFormatter">状态</th>
								<th data-field="timeConsuming" >耗时(ms)</th>
								<th data-field="" data-formatter="details">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>					
		</div>
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataForm" name="dataForm"></form>
		</div>
		<div id="readCardDiv"></div>
		<div style="display: none;">
			<div id="formDetail" class="formDetail">
				<h3 class='text-center base_hidden noDataTitle' id='noData'>暂无数据！</h3>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="/yztb/plugins/keypad/js/jquery.plugin.js"></script> 
	    <script type="text/javascript" src="/yztb/plugins/keypad/js/jquery.keypad.js"></script>
		<script src='../build/js/tempInfoQueryAll.js'></script>
		<script type="text/javascript">
			var _selectRow = null;
			var cerNo = null;
			var dataPid = null;
			var sfName = null;
			var searchTxt = "";
			var _items = null;
			var useName = "";
			var sfId = null;
			var certCode = null;
			var btnDownload = document.getElementById("btnDownload");
			var _userName = null;
			var _certName = null;
			var allOtherParam =new Array();
			var currentUser;
			$(function() {
				getItem();
				loadCurrentUser();
				var windowHeight=window.innerHeight;
				$("#tempDetailWrap").css("height",windowHeight-140);
			});
			$("#sfId").keydown(function(e){
				if(e.keyCode == 36){
					$("#sfId").removeAttr("readonly");
					$("#sfName").removeAttr("readonly");
				}else if(e.keyCode == 13){
					search();
				}
			});
			function loadCurrentUser() {
				$.ajax({
					url:"userSessionAction!loadCurrentUser.action",
					type:"POST",
					dataType:"json",
					async:false,
					error:function(request,textStatus, errorThrown){
					},
					success:function(data){
						currentUser = data;
					}
				});
			}
			function clickTemp(certCode, itemCode){
                var html = "tempInfoAction!getCertificateFile.action?certCode="+certCode+"&sfId="+cerNo+ "&itemCode=" + itemCode + "&sfName=" + sfName;
                window.open(html);
			}
			function getRandId(){
				  return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
			}
			function getItem(){
				$.ajax({
					type : 'post',
					url : '<%=path%>/tempInfoAction!findCurrentUserDepartMent.action', 
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
					    var data = data.data;
						searchTxt = "应 "+data.depart+" ";
						_items = data.items;
						dataPid = data.depar;
						useName = data.userName;
						_userName = data.userName;
						if(_items != null) {
							var itemHtml = '<option value="">---请选择---</option>';
							for(var i=0; i<_items.length; i++) {
								itemHtml += '<option value="'+ _items[i].innerCode +'">'+_items[i].name+'</option>';
							}
							$("#itemCode").html(itemHtml);
						}
					}
				});
			}
			function nameFormatter(val,row) {
				if(row.isMust == 1) {
					return '<lable style="color:red">(必要)</lable>'+val;
				} else if(row.isMust == 0) {
					return '<lable style="color:green">(非必要)</lable>'+val;
				} else {
					return val;
				}
			}
			function stateFormatter(val,row) {
                if(val == 1) {
                    if(row.certData.length != 0) {
                        return "成功";
                    } else {
                        return "无证件信息";
                    }
                } else if(val == -1) {
                    return "失败";
                }
			}

			function details(val,row){
			    if(row.code == 1 && row.certData.length != 0 && row.certData[0].code != 1) {
			        var materialCode = row.materialCode;
                    var itemCode = $("#itemCode").val();
                    return "<button class='btn btn-xs btn-primary' onclick=\"clickTemp('"+materialCode+"','"+itemCode+"');\">预览</button>&nbsp&nbsp&nbsp&nbsp"+
                    "<a class='btn btn-xs btn-primary' href=\"tempInfoAction!downloadCertificateFile.action?certCode="+materialCode+"&sfId="+cerNo+"&sfName="+sfName+"&itemCode=" + itemCode + "\">下载</a>";
			    } else if(row.code == 1 && cerNo == "330182198510160526") {
                    certCode = row.certCode;
                    var itemCode = $("#itemCode").val();
			        return "<button class='btn btn-xs btn-primary' onclick=\"testaa('"+row.certCode+"','"+itemCode+"');\">预览</button>&nbsp&nbsp&nbsp&nbsp"+
                        "<a class='btn btn-xs btn-primary' href=\"tempInfoAction!downloadTest.action?certCode="+row.certCode+"&sfId="+cerNo+"&sfName="+sfName+"&itemCode=" + itemCode + "\">下载</a>";
			    } else {
			        return "-";
                }
			}

            function testaa(certCode, itemCode){
                var html = "./pdf/" + certCode + ".pdf";
                window.open(html);
            }

			function regetRow(index,certCode,busiCode){
				var sfId = $("#sfId").val();
				var sfName = $("#sfName").val();
				var itemCode = $("#itemCode").val();
				console.log("busiCode is " + busiCode);
				var dataStr = "&certCode=" +certCode+"&sfId="+sfId+"&sfName="+sfName+"&itemCode="+itemCode+"&busiCode="+busiCode ;
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
			}
			function refreshRow(index,certCode,busiCode) {
				var sfId = $("#sfId").val();
				var sfName = $("#sfName").val();
				var itemCode = $("#itemCode").val() ;
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
				formatDialog($("#dataFormWrap"),{title:"其他参数",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": insert});
			}
			function insert(){
				var otherParam = '{ ';
				$("#dataForm").find('input').each(function(){
					otherParam+='"'+$(this).attr("name")+'":"'+$(this).val()+'",';
				 });
				otherParam = otherParam.substring(0,otherParam.length-1)+'}';
				var dataStr = "otherParam="+otherParam + "&certCode=" +certCode+"&sfId="+sfId+"&sfName="+sfName+"&itemCode="+itemCode+"&busiCode="+busiCode ;
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
			//表格请求
			function ajaxRequest(params) {
				var dataStr = $('#searchForm').serialize();
				$.ajax({
					type : 'post',
					url : '<%=path%>/tempInfoAction!findTempInfoAll.action',
					data:dataStr,
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
						var datas=data.data;
						if(data.returnCode != 0){
							Modal.alert({
                                msg : data.returnMessage,
                                title : '提示',
                                btnok : '确定',
                                btncl : '取消'
                            });
							return
						}
						var datas = data.data ? data.data : [];
						var count = data.data ? data.data.length : 0;
						params.success({
							total : count,
							rows : datas ?  datas : []
						});
					}
				});
			}
			//查询
			function search() {
				if($("#itemCode").val() == null || $("#itemCode").val() == "") {
					Modal.alert({ msg:"请选择事项！", title:'提示', btnok:'确定' });
					return false;
				}
				cerNo = $("#sfId").val();
				sfName = $("#sfName").val();
				$('#dataTable').bootstrapTable('refresh');
			}
			
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#searchForm .form-control').each(function(){
					$(this).val("");
				});
				search();
			}
		</script>
	</body>
</html>