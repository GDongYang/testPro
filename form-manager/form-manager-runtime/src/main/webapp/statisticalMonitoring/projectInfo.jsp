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
        <title>办件信息</title>
        <jsp:include page="../css/PageletCSS.jsp" >
            <jsp:param value="icon,table,page,chosen" name="p"/>
        </jsp:include>
        <link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/projectInfo/dialog.css" />
        <link rel="stylesheet" href="../iconfont/iconfont.css">
        <style type = "text/css">
            td{text-align:left;}
			#spanId{display:inline-block;width:300px;word-wrap:break-word}
			.situationHeight .modal-body{max-height:700px!important}	
				
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
    <body class="base_background-EEF6FB">
        <div style="display: none;">
            <div id="draftTablewrapDiv" class="addFormWrap" style='margin-bottom: 10px;margin-left: 15px'>
             
            </div>
        </div> 
    
        <form class="form form-inline base_padding-lr-20 base_margin-t-20 base_height-50 massageForm" id="searchForm">           
           	<label class='normalFont'>查询日期:</label>
            <input type="text" name="queryDate" id="queryDate" class="form-control base_margin-r-20 base_margin-b-10"  placeholder="查询日期" autocomplete="off" >
            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
            
<%--            <label class='normalFont'>事项编码:</label>--%>
<%--            <input type="text" class="form-control base_margin-b-10"  id="itemCodeSearch" name="itemNum" placeholder="事项编码" />--%>
<%--            <label class='normalFont'>事项名称:</label>--%>
<%--            <select class="chosenSelect chosen-select-deselect" style="width: 200px;" data-placeholder="请选择" id="formPageCode" name="itemCode" >--%>
<%--           	</select>--%>
<%--            <label class='normalFont'>办件进度:</label>--%>
<%--            <select class="chosenSelect" name="projectNode" id="projectNode" >--%>
<%--            	<option value="">--请选择--</option>--%>
<%--            	<option value="0">收件</option>--%>
<%--            	<option value="1">受理</option>--%>
<%--            	<option value="2">办结</option>--%>
<%--           	</select>--%>
            &nbsp;&nbsp;&nbsp;&nbsp;
           	<label class='normalFont '>证件号码:</label>
            <input type="text" class="form-control base_margin-r-20" id="cernumSearch" name="certNum" placeholder="证件号码" />
            <label class='normalFont'>流水号:</label>
            <input type="text" class="form-control " id="projectIdSearch" name="projectId" placeholder="办件流水号" />
            
            <button type="button" class="btn btn-primary btn-sm pull-right base_margin-t-2" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
            <button type="button" class="btn btn-primary btn-sm pull-right base_margin-r-6  base_margin-t-2" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
        </form> 
        <div class="wrapper">           
            <div class="tableBox">
                <div class="tableHead">
                    <h3 class="tableName">
                        <i class="fa fa-tag"></i>
                        <span>办件信息</span>
                    </h3>
                    <div class="tableBtns">
                       <!-- <button onclick='exportExcel()'><i class="fa fa-cloud-upload"></i><span>导出表格</span></button>  -->                      
                    </div>
                </div>
                <div id="tempDetailWrap" class="tableCont">
                    <table id="dataTable" class="box base_tablewrap repeaatTablePadding" data-toggle="table" data-locale="zh-CN"
                        data-ajax="ajaxRequest" data-side-pagination="server"
                        data-striped="true" data-single-select="true"
                        data-click-to-select="true" data-pagination="true"
                        data-pagination-first-text="首页" data-pagination-pre-text="上一页"
                        data-pagination-next-text="下一页" data-pagination-last-text="末页">
                        <thead style="text-align:center;">
                            <tr>
                                <th data-radio="true"></th>
                                <th data-field="projectId">办件流水号</th>
                                <th data-field="itemName"> 事项名称</th>
                                <th data-field="applyerName" >申请人姓名</th>
                                <th data-field="applyerCardNumber" >申请人证件号</th>
<%--                                <th data-field="projectStatus" data-formatter="formatStatus" >进度</th>--%>
                                <th data-field="createTime" data-formatter="formatDate">创建时间</th>
                                <th data-field="status" data-formatter="statusFormatter"> 状态</th>
                                <th data-field="sendData" class= "hidden">发送数据</th>
                                <th data-field="responseData" class= "hidden"> 响应数据</th>
                                <th data-formatter="operation" >操作</th>  
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>                  
        </div>       
        <jsp:include page="../js/PageletJS.jsp" >
            <jsp:param value="icon,table,page,chosen" name="p"/>
        </jsp:include>    
        <script type="text/javascript" src='../build/js/loadDepartment.js'></script>
        <script type="text/javascript" src='../build/js/moment.min.js'></script>  
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
//                 $("#tempDetailWrap").css("height",windowHeight-140);
                $(".base_box-area-aqua").css("height",windowHeight-103);
                $(".rightBox").css("height",windowHeight-260);
                $(".treeBox").css("height",windowHeight-136);
                //findAllGaFormPage();
            });
            function formatDate(val) {
                if(val) {
                    return val.replace("T"," ");
                }
            }
            // 格式化状态
            function statusFormatter (value,row) {
                if (value == '1') {
                    return value = '<img src="../build/img/success.png" style="width: 20px;height: 20px"/>'
                } else {
                    return value = '<img src="../build/img/failed.png" style="width: 20px;height: 20px"/>'
                }
            }
            
            function findAllGaFormPage(){
				$.ajax({
					cache : false,
					type : "post",
					url : 'itemAction!findItemOnline.action',
					dataType : 'json',
					async : true,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
                        $("#formPageCode").empty();
						if (data.data != null) {
							allTemp = data.data;
							var htmlStr = "<option value=\"\">请选择</option>";
							for (var i = 0; i < allTemp.length; i++) {
								htmlStr += "<option value=\""+allTemp[i].code+"\">"
										+ allTemp[i].name + "</option>";
							}
							selectConfig();//下拉框初始化
							$("#formPageCode").append(htmlStr);
							selectUpdated($("#formPageCode"));
						}
					}
				});
			}
            function formatStatus(val){
            	if(val == 0){
            		return "收件";
            	}else if(val == 1){
            		return "受理";
            	}else if(val == 2){
            		return "办结";
            	}else{
            		return "未知";
            	}
            }
            //ajax请求
            function ajaxRequest(params) {
                var pageSize = params.data.limit;
                var pageNum = params.data.offset/pageSize + 1;
                var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
                + '&pageSize=' + pageSize;
                $.ajax({
                    type : 'post',
                    url : 'projectInfoAction!findPage.action',
                    data:dataStr,
                    dataType : 'json',
                    cache : false,
                    async : true,
                    error : function(request, textStatus, errorThrown) {
//                         fxShowAjaxError(request, textStatus, errorThrown);
                    },
                    success : function(data) {
                        var datas = data ? data.rows : [];
                        var count = data ? data.total : 0;
                        params.success({
                            total : count,
                            rows : datas ? datas : []
                        });
                        amount = count;
                    }
                });
            }
            
            $(document).ready(function(){               
            	
            });
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
                            $("#cert").append(htmlStr);
                        }
                    }
                });
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
                $("#projectNode").val("");
                $("#formPageCode").val("");
                selectUpdated($("#formPageCode"));
                $('#dataTable').bootstrapTable('refresh');
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
                     endDate: moment(), //设置结束器日期
//                   maxDate: moment(new Date()), //设置最大日期
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
          
            function operation(value,rows){
                var btn = '<button type="button" class="btn btn_span btn-xs" onclick="detail('+JSON.stringify(rows).replace(/\"/g,"'")+')"><i class="iconfont icon-chakan base_margin-r-5"></i>详情</button>';
                return btn;
            }
           
            function detail(rows){
                $.ajax({
                    url : "projectInfoAction!findByProjectId.action",
                    type : "POST",
                    data : {"projectId":rows.projectId},
                    dataType : "json",
                    error : function(request, textStatus, errorThrown) {
                    	
                    },
                    success : function(data) {
                        var datas = data ? data.rows : [];
                        var count = data ? data.total : 0;
                        var createDate ="";
                        var contentmsg = "";
                        var height = "";
                        $("#draftTablewrapDiv form").remove();  		
						var tHtml = '<form class="form-horizontal">'+
                            '<div class="yz-progress">'+
                                // '<div class="yz-top">'+
                                //     '<span class="receive">收件</span>'+
                                //     '<span class="accept">受理</span>'+
                                //     '<span class="complete">办结</span>'+
                                // '</div>'+
                                '<div class="yz-bottom">'+
                                '<div class="left-line"></div>'+
                                    '<div class="yz-progress-line">'+
                                        '<span class="yz-outer-circle1 yz-outer-circle"><i class="yz-inner-circle1 yz-inner-circle"></i></span>'+
                                        '<span class="yz-outer-circle2 yz-outer-circle"><i class="yz-inner-circle2 yz-inner-circle"></i></span>'+
                                        '<span class="yz-outer-circle3 yz-outer-circle"><i class="yz-inner-circle3 yz-inner-circle"></i></span>'+
                                    '</div>'+
                                    '<div class="right-line"></div>'+
                                '</div>'+
                            '</div>'+
	                        '<div class="dialog-title-box">'+
		 						'<span>详细内容：</span>'+
							'</div>'+
							'<div class="form-group createDate base_padding-tb-10">'+
		 						'<label class="col-sm-3 control-label">业务流水号：</label>'+
		 						'<div class="col-sm-8">'+		 							
		 							'<input class="form-control" readonly="readonly" value="'+(rows.formBusiCode ? rows.formBusiCode : "无")+'"/>'+
		 						'</div>'+
		 					'</div>'+
		 					'<div class="form-group createDate base_padding-tb-10">'+
		 						'<label class="col-sm-3 control-label">事项编码：</label>'+
		 						'<div class="col-sm-8">'+
		 							'<input class="form-control base_padding-lr-4" readonly="readonly" value="'+(datas[0].itemCode ? datas[0].itemCode : "无")+'"/>'+
		 						'</div>'+
		 					'</div>'+
		 					'<div class="form-group doSomthingContent">'+
		 						'<label class="col-sm-3 control-label">请求内容：</label>'+
		 						'<div class="col-sm-8">'+
		 							'<textarea class="form-control"  readonly="readonly"   id="material" name="material" style="height: 126px;">'+(rows.sendData ? rows.sendData : "无")+'</textarea>'+
		 						'</div>'+
		 					'</div>'+
		 					'<div class="form-group doSomthingContent">'+
		 						'<label class="col-sm-3 control-label">响应内容：</label>'+
		 						'<div class="col-sm-8">'+
		 							'<textarea class="form-control"  readonly="readonly"   id="material" name="material" style="height: 126px;">'+(rows.responseData ? rows.responseData : "无")+'</textarea>'+
		 						'</div>'+
		 					'</div></form>'		                	
                        $.each(datas, function (index, value) {
                        	createDate = value.createTime
	                        createDate = createDate.replace("T"," ");
                        	contentmsg = value.content ? value.content : contentmsg;
                        	height = value.content ? 126 : 33.99;
                        	tHtml += '<form class="form-horizontal">'+
                   			'<div class="form-group" style="margin-left: 130px;"> '+
// 	 						'<div class="col-sm-8" style="text-align: center;" > '+
// 	 							'<label class="detailTitle">办理节点：</label> '+
// 	 							'<label style="margin-top: 5px;font-size: 18px;">'+value.nodeName+'</label> '+
// 	 						'</div>'+
		 					'</div> '+
			 					'<div class="dialog-title-box">'+
		     						'<span>办理节点：</span>'+
		     						'<span>'+value.nodeName+'</span>'+
		 						'</div>'+
		 					'<div class="form-group createDate base_padding-tb-10">'+		 					
		 						'<label class="col-sm-3 control-label">创建时间：</label>'+
		 						'<div class="col-sm-8">'+
		 							'<input class="form-control" readonly="readonly"   placeholder="创建时间" value="'+createDate+'"/>'+
		 						'</div>'+
		 					'</div>'+
		 					'<div class="form-group itemName" style="'+(value.nodeName == "收件" ? "display : block" : "") +'"> '+	
	     						'	<label class="col-sm-3 control-label detailTitle">事项名称：</label> '+
	     						'	<div class="col-sm-8"> '+
	     						'		<input class="form-control" readonly="readonly"   id="itemName" name="itemName" value="'+ (datas[0].itemName ? datas[0].itemName : "无") +'" placeholder="事项名称"/>'+
	     						'	</div>'+
	     					'</div>'+
// 	     					'<div class="form-group itemCode" style="'+(value.nodeName == "收件" ? "display : block" : "") +'"> '+
// 	 							'<label class="col-sm-3 control-label detailTitle">事项编码：</label>'+
// 	 							'<div class="col-sm-8">'+
// 	 								'<input class="form-control" readonly="readonly"   placeholder="事项编码" value="'+datas[0].itemCode+'"/>'+
// 	 							'</div>'+
// 	 						'</div>'+
		 					
		 					'<div class="form-group defeatedReason"  style="'+(value.errorMsg  ? "" : "display : none")+'" >'+
		 						'<label class="col-sm-3 control-label">失败原因：</label>'+
		 						'<div class="col-sm-8">'+
		 							'<textarea class="form-control"  readonly="readonly"   id="material" name="material" style="height: 126px;">'+value.errorMsg+'</textarea>'+
		 						'</div>'+
	 						'</div>'+
		 					'<div class="form-group doSomthingContent" style="'+(value.nodeName == "收件" ? "display : none" : "")+'" >'+
		 						'<label class="col-sm-3 control-label">办件内容：</label>'+
		 						'<div class="col-sm-8">'+
		 							'<textarea class="form-control"  readonly="readonly"   id="material" name="material" style="height: '+height+'px;">'+contentmsg+'</textarea>'+
		 						'</div>'+
		 					'</div>'+
		 					'</form>'
                        });
		 				$("#draftTablewrapDiv").append(tHtml);
                         datas.forEach( function (element,index)  {
                            if (element.nodeName == "收件") {
                                $('.receive').addClass('font-active');
                                $('.yz-outer-circle1').addClass('border-active');
                                $('.yz-inner-circle1').addClass('backc-active');
                            }  else if (element.nodeName == "受理") {
                                $('.accept').addClass('font-active');
                                $('.yz-outer-circle2').addClass('border-active');
                                $('.yz-inner-circle2').addClass('backc-active');
                                $('.left-line').addClass('line-active')
                            } else if (element.nodeName == "办结") {
                                $('.complete').addClass('font-active');
                                $('.yz-outer-circle3').addClass('border-active');
                                $('.yz-inner-circle3').addClass('backc-active');
                                $('.right-line').addClass('line-active')
                            }
                         });
                        formatDialog($("#draftTablewrapDiv"),
                                {title:"详情",dialogClass:"mydialog situationHeight"},{"取消": formatDialogClose,"确认": formatDialogClose});
                        $(this).dialog("close");
                    }
                });
            }
            
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
                
                str = "<%=path %>"+"/rest/excel/exportProjectInfoExcel?"+dataStr;
                window.location.href = str;
            } 
        </script>
    </body>
</html>