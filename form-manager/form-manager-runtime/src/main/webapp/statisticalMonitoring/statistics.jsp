<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>证件查询</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,tree,chosen,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/list.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/bootstrap.min.css" />
		<style>
			.chosen-select-deselect{height:30px!important;}
			.chosen-container{width: 100%!important;}
			.chosen-container-multi .chosen-choices{width:100%}
			.chosen-container-single .chosen-single{height:30px;line-height:30px}
			.chosen-container .chosen-drop{width: 100%;}
			.chosen-container-single .chosen-single div b{background-position:0 7px;}
			.chosen-container-active .chosen-width-drop .chosen-single div b{background-position:0 7px;}
			.chosenSelect{width:200px;height:30px;border-radius: 4px;}			
            .mydialog .modal-body{height:500px!important;}
            #detail{width:60%;height:300px;}
		</style>
	</head>
	<body class="base_background-EEF6FB">
	    <div style="display: none;">
            <div class="draftTablewrap base_margin-b-20" id="sqlArea">
            
            </div>  
        </div>
	
		<form class="form form-inline base_padding-lr-20 base_padding-t-10 base_margin-b-10 base_height-70" id="searchForm">
			<label>查询日期:</label>
            <input type="text" name="queryDate" id="queryDate" class="form-control base_width-350 base_margin-r-20">
            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
            
  			<label>证件名称:</label>
			<div class='base_width-300' style='display:inline-block;position:relative;'>
				<select class="form-control chosenSelect chosen-select-deselect" data-placeholder="请选择" id="cert" name="certcode" ></select>
			</div>
			<div class="base_margin-t-10">
				<label>证件号码:</label>
	            <div class='base_width-200' style='display:inline-block;position:relative;'>
	                <input type="text" class="form-control" id="certNum" name="certNo" placeholder="证件号码" />
	            </div>
	            
				<label>状态:</label>
	            <div class='base_width-200' style='display:inline-block;position:relative;'>
	                <input type="text" class="form-control" id="statusSearch" name="ztStatus" placeholder="状态" />
	            </div>
	            
	  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
	  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
			</div>			
  		</form>		
		<div class="wrapper">
			<div style="overflow:auto">
				<div class="tableBox">
					<div class="tableHead">
						<h3 class="tableName">
							<i class="fa fa-tag"></i>
							<span>证件统计</span>
						</h3>
						<div class="tableBtns">
							<button onclick='exportExcel()'><i class="fa fa-cogs"></i><span>导出表格</span></button>
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
									<th data-field="status">状态</th>
									<th data-field="cerNo">证件号码</th>	
									<th data-field="businessId">业务ID</th>
									<th data-field="businessCode">业务编码</th>							
									<th data-field="certtempId">证件临时id</th>							
									<th data-field="certCode">证件编号</th>							
									<th data-field="certName">证件名称</th>							
									<th data-field="itemName">事项名称</th>							
									<th data-field="code">编码</th>							
									<th data-field="accountName">调用账号信息</th>							
<!-- 									<th data-field="requestparam">请求参数</th>							 -->
									<th data-field="message">返回信息</th>							
									<th data-field="createDate" data-formatter="formatDate">创建时间</th>							
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>					
		<div id="reasonWrap" class="base_hidden"></div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,tree,chosen,icon" name="p"/>
		</jsp:include>
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
<!-- 		<script type="text/javascript" src='../build/js/statistics.js'></script> -->
		<script type="text/javascript" src='../build/js/moment.js'></script>  
        <script type="text/javascript" src='../build/js/daterangepicker.js'></script> 
		<script type="text/javascript">
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			var index;
			var treeSearchFlag=false;
			var amount = 0;
			
			$(function() {
				$('#dataTable').on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").attr('checked','checked');//选中行内radio
				});
				var windowHeight=window.innerHeight;
				$("#tempDetailWrap").css("height",windowHeight-140);
				$(".base_box-area-aqua").css("height",windowHeight-103);
				$(".rightBox").css("height",windowHeight-260);
				$(".treeBox").css("height",windowHeight-136);
			});
			$(document).ready(function(){				
				selectConfig();//下拉框初始化
				loadCert();
				loadDepartment('treeDemo');
			});
			function formatDate(val) {
                if(val) {
                    return val.replace("T"," ");
                }
            }
			//查询
			function search() {
				$('#dataTable').bootstrapTable('refresh');
			}
			//清除
			function clean() {
				pageNum = 1;
				$('#searchForm .form-control').each(function(){
					$(this).val("");
				});
				//清除下拉框的值
				selectConfig();//下拉框初始化
				selectUpdated($("#cert"));
				$('#dataTable').bootstrapTable('refresh');
			}
			//获取所有模板
			function loadCert() {
				$.ajax({
					url : "certTempAction!findAll.action",
					type : "POST",
					data : "",
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						if (data.result != null) {
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].code+"\">"
										+ data.result[i].name + "</option>";
							}
							selectConfig();//下拉框初始化
							$("#cert").append(htmlStr);
							selectUpdated($("#cert"));
						}
					}
				});
			}
			//表格请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
            	var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
					+ '&pageSize=' + pageSize;
					$.ajax({
						type : 'post',
						url : 'businessAction!findStaticFromSolr.action',
						data:dataStr,
						dataType : 'json',
						cache : false,
						async : true,
						error : function(request, textStatus, errorThrown) {
						},
						success : function(data) {
							var datas = data.rows ? data.rows : [];
							var count = data.rows ? data.total : 0;
							params.success({
								total : count,
								rows : datas
							});
							amount = count;
						}
					});
				    
			}
			//树点击回调函数
			function treeCallBack(treeName,treeId){
				$("#deptSearchId").val(treeName);
				$('#departmentId').val(treeId);
			}
			//导出表格
			function exportExcel(){
				if(amount > 1000){
					alert("当前数量为"+amount+",最多保存1,000条记录");
					amount = 1000;
				}
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
                + '&pageSize=' + pageSize+'&total='+amount;
				var info = $("#dataTable").bootstrapTable("getSelections");
				//获取选中的数据
				info.fileName="table";
				var str = "";
				
                str = "<%=path %>"+"/rest/excel/exportStaticExcel?"+dataStr;
	            window.location.href = str;
			} 
			
			function BrowseFolder() {
			    try {
			        var Message = "Please select the folder path.";  //选择框提示信息
			        var Shell = new ActiveXObject("Shell.Application");
			        var Folder = Shell.BrowseForFolder(0, Message, 0x0040, 0x11); //起始目录为：我的电脑
			        //var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
			        if (Folder != null) {
			            Folder = Folder.items();  // 返回 FolderItems 对象
			            Folder = Folder.item();  // 返回 Folderitem 对象
			            Folder = Folder.Path;   // 返回路径
			            if (Folder.charAt(Folder.length - 1) != "\\") {
			                Folder = Folder + "\\";
			            }
			            return Folder;
			        }
			    } catch (e) {
			        alert(e.message);
			    }
			}
			
			 //区间时间插件
            $("input[name='queryDate']").daterangepicker(
                      {
                         // autoApply: true,
                     autoUpdateInput: true,
                         // alwaysShowCalendars: true,
                     timePicker: true, //显示时间
                     timePicker24Hour : true,//设置小时为24小时制 默认false
                     showDropdowns: true,
                     autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
                     timePickerSeconds: false, //时间显示到秒
                     startDate: moment().hours(0).minutes(0), //设置开始日期
                     endDate: moment().hours(0).minutes(0), //设置结束器日期
//                   maxDate: moment(new Date()), //设置最大日期
 					 opens: "right",
                     showWeekNumbers: true,
                     locale: {
                         format: "YYYY/MM/DD HH:MM:00",
                         separator: " - ",
                         applyLabel: "确认",
                         cancelLabel: "清空",
                         fromLabel: "开始时间",
                         toLabel: "结束时间",
                         customRangeLabel: "自定义",
                         daysOfWeek: ["日","一","二","三","四","五","六"],
                         monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
                     }
                  }
	              ).on('cancel.daterangepicker', function(ev, picker) {
	                  $("#queryDate").val("请选择日期范围");
	                  $("#startDate").val("");
	                  $("#endDate").val("");
	              }).on('apply.daterangepicker', function(ev, picker) {
	                  $("#startDate").val(picker.startDate.format('YYYY-MM-DD HH:MM:00'));
	                  $("#endDate").val(picker.endDate.format('YYYY-MM-DD HH:MM:00'));
	                  $("#queryDate").val(picker.startDate.format('YYYY-MM-DD HH:MM:00')
	                          +" 至 "+picker.endDate.format('YYYY-MM-DD HH:MM:00'));
	          });
		</script>
	</body>
</html>