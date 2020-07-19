<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<title>事项统计</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,tree,icon" name="p"/>
		</jsp:include>				
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/list.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/bootstrap.min.css" />
	</head>
	<body class='base_background-EEF6FB'>					
  		<div class="wrapper">
  			<form class="form form-inline base_margin-b-20" id="searchForm">
	  			<label>查询日期:</label>
	            <input type="text" name="queryDate" id="queryDate" class="form-control base_margin-r-20" style="" autocomplete="off">
	            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
	            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
	  			<label>事项名称:</label>
	  			<input type="text" class="form-control base_margin-r-20" id="cernoSearch" name="itemName" placeholder="事项名称" />
	  			<label>所属部门:</label>
	  			<div style='position:relative;display:inline-block;' class='base_width-200'>
	  				<input class="form-control" name="departmentId" id="deptmentId" type='hidden'/>
	  				<input class="form-control" id="deptSearchId" onclick="showMenu('menuContent');" readOnly/>
					<div id="menuContent" class="menuContent base_hidden searchTree">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
	  			</div>
	  			<button  type="button" class="btn btn-primary btn-sm" onclick='search()'><i class="fa fa-search base_margin-r-5"></i>搜索</button>
	  			<button  type="button" class="btn btn-primary btn-sm" onclick='clean()'><i class="fa fa-trash-o base_margin-r-5"></i>清空</button>
	  		</form>			
			<div class="tableBox">
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>事项统计</span>
					</h3>
                    <div class="tableBtns">
						<button onclick='exportExcel()'><i class="fa fa-cloud-upload"></i><span>导出表格</span></button>						
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
								<th data-field="id" data-formatter="idFormatter">序号</th>
								<th data-field="itemName">事项名称</th>
								<th data-field="otherParam" >地区</th>
                                <th data-field="departmentName" >部门名称</th>
                                <th data-field="count" >数量</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>					
		</div>
		<div style="display: none;">
			<div id="formDetail" class="formDetail">
				<h3 class='text-center base_hidden noDataTitle' id='noData'>暂无数据！</h3>
			</div>
		</div>
		<div id="reasonWrap" class="base_hidden"></div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,tree,icon" name="p"/>
		</jsp:include>	
		<script src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript" src='../build/js/moment.js'></script>  
        <script type="text/javascript" src='../build/js/daterangepicker.js'></script> 
		<script>
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			var index;
			var treeSearchFlag=false;//判断是否需要模糊搜索
			$(function() {
				loadDepartment('treeDemo');
				$('#dataTable').on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").attr('checked','checked');//选中行内radio
				});
				var windowHeight=window.innerHeight;
				$("#tempDetailWrap").css("height",windowHeight-140);
			});
// 			$("#queryDate").daterangepicker({
// 				locale : {
// 					applyLabel : '确定',
// 					cancelLabel : '取消',
// 					fromLabel : '起始',
// 					toLabel : '结束',
// 					weekLabel : 'W',
// 					customRangeLabel : '自定义',
// 					daysOfWeek : [ "周日", "周一", "周二",
// 							"周三", "周四", "周五", "周六" ],
// 					monthNames : [ "一月", "二月", "三月",
// 							"四月", "五月", "六月", "七月",
// 							"八月", "九月", "十月", "十一月",
// 							"十二月" ],
// 					firstDay : 1
// 				}
// 			});
			//树点击回调函数
			function treeCallBack(treeName,treeId){
				$("#deptSearchId").val(treeName);
				$('#deptmentId').val(treeId);
			}
			//刷新页面
			function refresh() {
				search();
			}
			var id = 1;
			function idFormatter(value, row){
				return id++;
			}
			//表格请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'businessAction!findItemCount.action',
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
							rows : datas ?  datas : []
						});
					}
				});
			}
			//查询
			function search() {
				id = 1;
				$('#dataTable').bootstrapTable('selectPage', 1);
			}
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#searchForm .form-control').each(function(){
					$(this).val("");
				});
				search();
			}
			var queryParam = {};
			function exportExcel(){
				queryParam.fileName="table";
				queryParam.downloadType="2";
				window.open("businessToExcelAction!businessToExcel.action?"+$.param(queryParam));
			}
			
			//区间时间插件
            $("input[name='queryDate']").daterangepicker(
                    {
                       // autoApply: true,
                       //autoUpdateInput: false,
                       // alwaysShowCalendars: true,
                   timePicker: true, //显示时间
                   timePicker24Hour : true,//设置小时为24小时制 默认false
                   showDropdowns: true,
                   autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
                   timePickerSeconds: false, //时间显示到秒
                   startDate: moment().hours(0).minutes(0), //设置开始日期
                   endDate: moment().hours(0).minutes(0), //设置结束器日期
//                 maxDate: moment(new Date()), //设置最大日期
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