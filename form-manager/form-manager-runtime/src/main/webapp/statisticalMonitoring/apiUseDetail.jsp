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
		<title>接口调用</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,tree,icon,chosen" name="p"/>
		</jsp:include>				
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/list.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" href="../iconfont/iconfont.css">
        <style>
            #searchForm .form-control {
                height: 33px;
            }
            .left-content {
                float: left;
                width: 27%;
                height: auto;
                padding: 20px;
                padding-left: 0px;
            }
            .wrapper {
                float: left;
                width: 73%;
            }
            .content-detail {
                width: 100%;
                height: 100%;
                background-color: #fff;
                padding: 15px;
            }
            .total-number {
                display: flex;
                align-items: center;
                justify-content: space-between;
                width:100%;
                height: 37px;
                background:rgba(142,201,255,0.16);
                border-radius:26px;
                padding: 0 25px;
            }
            .special-components {
                background-color:#fff;
                border-radius:none;
            }
            .content-detail .title {
                margin-bottom: 15px;
            }
            .panel-body {
                overflow: auto;
            }
            #searchForm  {
                height: auto;
            }
            .form-bottom {
                margin-top: 10px;
            }
            .mydatechoosedialog{
            	width: 300px
            }
            
            /*chosen相关*/
            .chosen-select-deselect{height:30px!important;}
			.searchInput{height:30px;}
			.chosen-container{width: 200px!important;}
			.chosen-container-multi .chosen-choices{width:100%}
			.chosen-container-single .chosen-single{height:30px;line-height:30px}
			.chosen-container .chosen-drop{width: 100%;}
			.chosen-container-single .chosen-single div b{background-position:0 4px;}
			.chosen-container-active .chosen-width-drop .chosen-single div b{background-position:0 4px;}
			.chosenSelect{width:200px;height:30px;border-radius: 4px;}
        </style>
	</head>
	<body class='base_background-EEF6FB'>	
        <div style="display: none;">
            <div id="draftTablewrapDiv" class="addFormWrap" style='margin-bottom: 10px;margin-left: 15px'></div>
        </div> 				
  		<div class="wrapper">
  			<form class="form form-inline base_margin-b-20" id="searchForm">
                <label class="normalFont">接口名称:</label>
	  			<select class="form-control chosenSelect chosen-select-deselect" style="width:100px" data-placeholder="请选择" id="apiName" name="requestName" ></select>
	  			<label class="normalFont">查询日期:</label>
	            <input type="text" name="queryDate" id="queryDate" class="form-control base_margin-r-20"  autocomplete="off" placeholder="请选择查询日期">
	            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
	            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
                <br>
                <div class="form-bottom">
                    <label class="normalFont">接口状态:</label>
                    <select id="status" class="form-control base_margin-r-20" name="status" style="width: 178px" onchange="handleChange()">
                            <option value="">请选择</option>
                            <option value="-1">失败</option>
                            <option value="1">成功</option>
                    </select>
                    <button  type="button" class="btn btn_base btn-primary pull-right" onclick='clean()'><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
                    <button  type="button" class="btn btn_base btn-primary pull-right base_margin-r-6" onclick='search()'><i class="fa fa-search base_margin-r-5"></i>查询</button>
                </div>
	  		</form>			
			<div class="tableBox">
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>接口详情</span>
					</h3>
					<div class="tableBtns">
<%--                        <button onclick='openDateChoose()'><img src="../build/image/exportIndex.png" class='exportImg'><span>导出表格</span></button>                        --%>
                    </div>
				</div>
				<div id="tempDetailWrap" class="tableCont">
					<table id="dataTable" class="box base_tablewrap itemTablePadding" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="true"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-field="requestName">接口名称</th>
								<th data-field="requestCode">编码</th>
								<th data-field="dataSource" >接口来源</th>
                                <th data-field="requestTime" data-formatter="timeFormatter" >时间</th>
                                <th data-field="status" data-formatter="statusFormatter">状态</th>
                                <th data-formatter="opreationFormatter">操作</th>  
							</tr>
						</thead>
					</table>
				</div>
			</div>					
		</div>
        <div class="left-content">
            <div class="content-detail">
                <div class="title">接口调用</div>
                <div class="total-number">
                    <span>总次数</span>
                    <span style="color: #1890FF" id="allCount"></span>
                </div>
                <div class="total-number special-components">
                    <span>成功次数</span>
                    <span style="color: #1890FF" id="successCount"></span>
                </div>
                <div class="total-number special-components">
                    <span>失败次数</span>
                    <span style="color: #1890FF" id="faildCount"></span>
                </div>
                <div class="bottom-content" style="margin-left: 25px;"></div>
                <div class="total-number">
                    <span>当天次数</span>
                    <span style="color: #1890FF" id="currentallCount"></span>
                </div>
                <div class="total-number special-components">
                    <span>成功次数</span>
                    <span style="color: #1890FF" id="currentsuccessCount"></span>
                </div>
                <div class="total-number special-components">
                    <span>失败次数</span>
                    <span style="color: #1890FF" id="currentfaildCount"></span>
                </div>
                <div class="current-bottom-content" style="margin-left: 25px;"></div>

            </div>
        </div>
		<div style="display: none;">
			<div id="formDetail" class="formDetail">
				<h3 class='text-center base_hidden noDataTitle' id='noData'>暂无数据！</h3>
			</div>
		</div>
		<div id="reasonWrap" class="base_hidden"></div>
		<div style="display: none;" >
           	<div id="dateChooseForm" class="addFormWrap" style='margin-bottom: 10px,margin-left: 50px'>
           		<form class="" id="" >
           			<select class= "form-control" style="display: inline-block;width:80px" id="exportYear"></select>年
           			<select class= "form-control" style="display: inline-block;width:70px" id="exportMonth"></select>月
           		
				</form>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,tree,icon,chosen" name="p"/>
		</jsp:include>	
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
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
				findCertTemp();
				$('#dataTable').on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").attr('checked','checked');//选中行内radio
				});
				var windowHeight=window.innerHeight;
				$("#tempDetailWrap").css("height",windowHeight-140);
				$(".tableCont").css("height",'auto');
			});
			function handleChange() {
				search()
			}
			//树点击回调函数
			function treeCallBack(treeName,treeId){
				$("#deptSearchId").val(treeName);
				$('#deptmentId').val(treeId);
			}
			//刷新页面
			function refresh() {
				ajaxRequest();
			}
            /**
            * 总次数请求方法
            */
            function getApiUseNumber () {
                $.ajax({
                    type : 'get',
					url : 'requestInfoAction!getCounts.action?type=0',
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
                        alert(errorThrown)
					},
                    success : function(data) {
                        if (data.msg == "获取成功!") {
                            $('#allCount').text(data.allCount || '--');
                            $('#successCount').text(data.successCount || '--');
                            $('#faildCount').text(data.faildCount || '--');
                            var res = data.dataSources;
                            $('.bottom-content').empty();
                            if (res.length != 0) {
                                res.forEach( (item,index) => {
                                    var apiUseNumberHtml = '<div class="total-number special-components">'+
                                        '<span>'+item.errorSouce+'</span>'+
                                        '<span style="color: #1890FF" id="faildCount">'+item.failedCount+'</span>'+
                                    '</div>'
                                    $('.bottom-content').append(apiUseNumberHtml)
                                })
                            }
                        }
					}
                })
            }
            getApiUseNumber();
            /**
            * 当天次数请求方法
            */
            function getApiUseNumberCurrent () {
                $.ajax({
                    type : 'get',
					url : 'requestInfoAction!getCounts.action?type=1',
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
                        alert(errorThrown)
					},
                    success : function(data) {
                        if (data.msg == "获取成功!") {
                            $('#currentallCount').text(data.allCount || '--');
                            $('#currentsuccessCount').text(data.successCount || '--');
                            $('#currentfaildCount').text(data.faildCount || '--');
                            var res = data.dataSources;
                            $('.current-bottom-content').empty();
                            if (res != '') {
                                res.forEach( (item,index) => {
                                    var apiUseNumberHtml = '<div class="total-number special-components">'+
                                        '<span>'+item.errorSouce+'</span>'+
                                        '<span style="color: #1890FF" id="faildCount">'+item.failedCount+'</span>'+
                                    '</div>'
                                    $('.current-bottom-content').append(apiUseNumberHtml)
                                })
                            } 
                        }
					}
                })
            }
            getApiUseNumberCurrent();
			//表格请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'requestInfoAction!getInfos.action',
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
            // 格式化状态
            function statusFormatter (value,row) {
                if (value == '1') {
                    return value = '<img src="../build/img/success.png" style="width: 20px;height: 20px"/>'
                } else {
                    return value = '<img src="../build/img/failed.png" style="width: 20px;height: 20px"/>'
                }
            }
            // 格式化操作按钮
            function opreationFormatter(value,row,index) {
                var btn = '<a href="javascript:;" onclick="detail('+JSON.stringify(row).replace(/\"/g,"'")+')"><i class="iconfont icon-chakan base_margin-r-5"></i>详情</a>';
                return btn;
            }
            // 详情事件
            function detail(row) {
                $("#draftTablewrapDiv").empty(); 
                row.requestTime = row.requestTime.replace('T',' ')
                var dialogHtml = '<div class="panel panel-default">'+
                                    '<div class="panel-heading">接口名称:</div>'+
                                    '<div class="panel-body">'+row.requestName+'</div>'+
                                    '<div class="panel-heading">编码:</div>'+
                                    '<div class="panel-body">'+row.requestCode+'</div>'+
                                    '<div class="panel-heading">接口来源:</div>'+
                                    '<div class="panel-body">'+row.dataSource+'</div>'+
                                    '<div class="panel-heading">请求时间:</div>'+
                                    '<div class="panel-body">'+row.requestTime+'</div>'+
                                    '<div class="panel-heading">请求参数:</div>'+
                                    '<div class="panel-body">'+row.requestData+'</div>'+
                                    '<div class="panel-heading">返回参数:</div>'+
                                    '<div class="panel-body">'+row.responseData+'</div>'+
                                    
                                '</div>' 	
                $("#draftTablewrapDiv").append(dialogHtml); 
                formatDialog($("#draftTablewrapDiv"),
                                {title:"接口调用详情",dialogClass:"mydialog situationHeight"},{"取消": formatDialogClose,"确认": formatDialogClose});
                $(this).dialog("close");        
            }
            // 格式化时间
            function timeFormatter(value) {
                return value = value.replace('T',' ')
            }
			//查询
			function search() {
                $('#dataTable').bootstrapTable('refresh');
			}
            // 查询详情
            function searchDetail(element) {
                $("#draftTablewrapDiv").empty(); 
                element.requestTime = element.requestTime.replace('T',' ')
                var dialogHtml = '<div class="panel panel-default">'+
                                    '<div class="panel-heading">接口名称:</div>'+
                                    '<div class="panel-body">'+element.requestName+'</div>'+
                                    '<div class="panel-heading">编码:</div>'+
                                    '<div class="panel-body">'+element.requestCode+'</div>'+
                                    '<div class="panel-heading">接口来源:</div>'+
                                    '<div class="panel-body">'+element.dataSource+'</div>'+
                                    '<div class="panel-heading">请求时间:</div>'+
                                    '<div class="panel-body">'+element.requestTime+'</div>'+
                                    '<div class="panel-heading">请求参数:</div>'+
                                    '<div class="panel-body">'+element.requestData+'</div>'+
                                    '<div class="panel-heading">返回参数:</div>'+
                                    '<div class="panel-body">'+element.responseData+'</div>'+
                                    
                                '</div>' 	
                $("#draftTablewrapDiv").append(dialogHtml); 
                formatDialog($("#draftTablewrapDiv"),
                                {title:"接口调用详情",dialogClass:"mydialog situationHeight"},{"取消": formatDialogClose,"确认": formatDialogClose});
                $(this).dialog("close");
            }
            // 清除
            function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#searchForm .form-control').each(function(){
					$(this).val("");
				});
			}
			var queryParam = {};
			
			//区间时间插件
            $("input[name='queryDate']").daterangepicker({
                   timePicker: true, //显示时间
                   timePicker24Hour : true,//设置小时为24小时制 默认false
                   showDropdowns: true,
                   autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
                   timePickerSeconds: false, //时间显示到秒
                   startDate: moment().hours(0).minutes(0), //设置开始日期
                   endDate: moment().hours(0).minutes(0), //设置结束器日期
                   opens: "right",
                   showWeekNumbers: true,
                       locale: {
                           format: "MM/DD/YYYY HH:mm",
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
                    $("#startDate").val(picker.startDate.format("YYYY-MM-DD HH:mm:00"));
                    $("#endDate").val(picker.endDate.format("YYYY-MM-DD HH:mm:00"));
                    $("#queryDate").val(picker.startDate.format("YYYY-MM-DD HH:mm:00")
                            +" 至 "+picker.endDate.format("YYYY-MM-DD HH:mm:00"));
            });
            
            function openDateChoose(){
            	$("#exportYear").empty();
            	$("#exportMonth").empty();
            	/*年份下拉框赋值*/
            	var yearStr = "";
            	
            	for(var i = 2019;i < 2050;i++){
            		yearStr += "<option class='form-control'  value='" + i + "'>" + i + "</option>";
            	}
            	$("#exportYear").append(yearStr);
            	/*月份下拉框赋值*/
            	var monthStr = "";
            	for(var i = 1;i < 13;i++){
            		monthStr += "<option class='form-control' value='" + i + "'>" + i + "</option>";
            	}
            	$("#exportMonth").append(monthStr);
            	
            	/*获取当前月份赋值*/
            	var date = new Date();
		        var year = date.getFullYear();
		        var month = date.getMonth() + 1;
      			$("#exportYear").val(year);
      			$("#exportMonth").val(month);
            	formatDialog($("#dateChooseForm"),
    					{title:"请选择导出月份",dialogClass:"mydatechoosedialog"},{"取消": formatDialogClose,"提交": exportExcel});
            } 
            
            /*导出excel表格*/
            function exportExcel(){
             var year = $("#exportYear").val();
             var month = $("#exportMonth").val();
             if(year == "" || month == "" || month == undefined || year == undefined){
          	   $(this).dialog("close");
          	   Modal.alert({ msg:'清输入正确日期!', title:'提示', btnok:'确定' });
          	   return;
             }
             var str = "";
             str = "<%=path %>"+"/requestInfoAction!exportExcel.action?year=" + year + "&month=" + month;
             window.location.href = str;
            }
            
            /*获取所有证明*/
            function findCertTemp() {
				$.ajax({
					cache : false,
					type : "get",
					url : 'certTempAction!findAll.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
							$("#apiName").empty();
						if (data.result != null) {
							allTemp = data.result;
							var htmlStr = "<option value=\"\">全部接口</option>";
							for (var i = 0; i < data.result.length; i++) {
								htmlStr += "<option value=\""+data.result[i].name+"\">"
										+ data.result[i].name + "</option>";
							}
							selectConfig();//下拉框初始化
							$("#apiName").append(htmlStr);
							selectUpdated($("#apiName"));
						}
					}
				});
}
		</script>
	</body>
</html>