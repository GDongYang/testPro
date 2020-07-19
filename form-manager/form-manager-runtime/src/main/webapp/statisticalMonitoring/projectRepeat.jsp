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
        <title>办件重推</title>
        <jsp:include page="../css/PageletCSS.jsp" >
            <jsp:param value="table,icon,page" name="p"/>
        </jsp:include>
        <link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/bootstrap.min.css" />
        <style type = "text/css">
            td{text-align:left;}
			#spanId{display:inline-block;width:300px;word-wrap:break-word}
			.situationHeight .modal-body{max-height:700px!important}	
        </style>
    </head>
    <body class="base_background-EEF6FB">
        <div style="display: none;">
            <div id="draftTablewrapDiv" class="addFormWrap" style='margin-bottom: 10px,margin-left: 15px'>
             
            </div>
        </div> 
    
        <form class="form form-inline base_padding-40 base_margin-b-10" id="searchForm">
            <label class='base_margin-r-10 '>查询日期:</label>
            <input type="text" name="queryDate" id="queryDate" class="form-control" style="" autocomplete="off" >
            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
            
            <label class='base_margin-r-10'>业务系统状态:</label>
            <select id="sysStatus" class="form-control">
            	<option value="-2">请选择</option>
            	<option value="-1">-1</option>
            	<option value="0">0</option>
            	<option value="1">1</option>
            </select>
            <label class='base_margin-r-10'>中台状态:</label>
            <select id="ztStatus" class="form-control">
            	<option value="-2">请选择</option>
            	<option value="-1">-1</option>
            	<option value="0">0</option>
            	<option value="1">1</option>
            </select>
            <label class='base_margin-r-10 '>办件流水号:</label>
            <input type="text"  id="projectId" class="form-control" style="" >
            <button type="button" class="btn btn-primary btn-sm" onClick="search()">
            <i class='fa fa-search base_margin-r-5'></i>查询</button>
            <button type="button" class="btn btn-primary btn-sm" onClick="clean()">
            <i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
        </form> 
        <div class="wrapper">           
            <div class="tableBox">
                <div class="tableHead">
                    <h3 class="tableName">
                        <i class="fa fa-tag"></i>
                        <span>办件信息</span>
                    </h3>
                    <div class="tableBtns">
                        <!--<button onclick='exportExcel()'><i class="fa fa-cloud-upload"></i><span>导出表格</span></button>-->                        
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
                                <th data-field="projectId">办件流水号</th>
                                <th data-field="sysStatus"> 业务系统状态</th>
                                <th data-field="ztStatus" > 中台状态</th>
                                <th data-field="repeatNum" > 推送次数</th>
                                <th data-field="updateTime" data-formatter="formatDate">更新时间</th>
                                <th data-formatter="operation" >操作</th>  
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>                  
        </div>
        <jsp:include page="../js/PageletJS.jsp" >
            <jsp:param value="table,page" name="p"/>
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
                $("#tempDetailWrap").css("height",windowHeight-140);
                $(".base_box-area-aqua").css("height",windowHeight-103);
                $(".rightBox").css("height",windowHeight-260);
                $(".treeBox").css("height",windowHeight-136);
            });
            function formatDate(val) {
                if(val) {
                    return val.replace("T"," ");
                }
            }
            //ajax请求
            function ajaxRequest(params) {
                var pageSize = params.data.limit;
                var pageNum = params.data.offset/pageSize + 1;
                var ztStatus = $("#ztStatus").val();
                var sysStatus = $("#sysStatus").val();
                var projectId = $("#projectId").val();
                var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
                + '&pageSize=' + pageSize + "&projectId= " + projectId;
                if(ztStatus != -2){
                	dataStr += '&ztStatus=' + ztStatus;
                }
                if(sysStatus != -2){
                	dataStr += '&sysStatus=' + sysStatus;
                }
                $.ajax({
                    type : 'post',
                    url : 'projectRepeatAction!findPage.action',
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
          //查询
            function search() {
                $('#dataTable').bootstrapTable('selectPage',1);
            }
            //清除
            function clean() {
                pageNum = 1;
                $('#searchForm .form-control').each(function(){
                    $(this).val("");
                });
                 $('#dataTable').bootstrapTable('selectPage',1);
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
          
            function operation(value,rows){
                var btn = '<button type="button" class="btn btn-primary btn-sm base_margin-r-10" onclick="detail('+JSON.stringify(rows).replace(/\"/g,"'")+')"><i class="fa fa-pencil base_margin-r-5"></i>详情</button>';
                if(rows.sysStatus != 1 || rows.ztStatus != 1){
                	btn += '<button type="button" class="btn btn-primary btn-sm base_margin-r-10" onclick="repush('+JSON.stringify(rows).replace(/\"/g,"'")+')"><i class="fa fa-pencil base_margin-r-5"></i>重推</button>';
                }
                return btn;
            }
           
            function detail(rows){
                $.ajax({
                    url : "projectRepeatAction!findProjectReateInfo.action",
                    type : "POST",
                    data : {"id":rows.id},
                    dataType : "json",
                    error : function(request, textStatus, errorThrown) {
                    	
                    },
                    success : function(data) {
                        var datas = data ? data.rows : [];
                        console.log("datas is " + JSON.stringify(datas))
                        var count = data ? data.total : 0;
                        var createDate ="";
                        var contentmsg = "";
                        var height = "";
                        $("#draftTablewrapDiv form").remove();
                        if(datas != null){
                        	 var head = '<form class="form-horizontal" id="addForm" name="addform"> '+
             					'<input type="hidden" class="form-control"  name="id" id="add_id" /> '+
             						'<div class="form-group"> '+
             						'	<label class="col-sm-3 control-label detailTitle">办件流水号：</label> '+
             						'	<div class="col-sm-8"> '+
             						'		<input class="form-control" readonly="readonly"   id="" name="" value="'+ (datas.projectId ? datas.projectId : "无") +'" placeholder="办件流水号"/>'+
             						'	</div>'+
             					'	</div>'+
             					'<div class="form-group"> '+
		 						'<label class="col-sm-3 control-label detailTitle">业务系统状态：</label>'+
		 						'	<div class="col-sm-8">'+
		 						'		<input class="form-control" readonly="readonly"   placeholder="业务系统状态" value="'+datas.sysStatus+'"/>'+
		 						   '</div>'+
		 						'</div>'+
	             				'<div class="form-group"> '+
	         						'	<label class="col-sm-3 control-label detailTitle">中台状态：</label> '+
	         						'	<div class="col-sm-8"> '+
	         						'		<input class="form-control" readonly="readonly"   id="" name="" value="'+datas.ztStatus+'" placeholder="中台状态"/>'+
	         						'	</div>'+
	         					'</div>'+
	         					'<div class="form-group"> '+
	         						'	<label class="col-sm-3 control-label detailTitle">发送数据链接：</label> '+
	         						'	<div class="col-sm-8"> '+
	         						'		<input class="form-control" readonly="readonly"   id="" name="" value="'+datas.sendUrl+'" placeholder="发送数据链接"/>'+
	         						'	</div>'+
	         					'</div>'+
	         					'<div class="form-group"> '+
	         						'	<label class="col-sm-3 control-label detailTitle">办件创建时间：</label> '+
	         						'	<div class="col-sm-8"> '+
	         						'		<input class="form-control" readonly="readonly"   id="" name="" value="'+datas.createTime+'" placeholder="办件创建时间"/>'+
	         						'	</div>'+
	         					'</div>'+
	         					'<div class="form-group"> '+
	         						'	<label class="col-sm-3 control-label detailTitle">办件更新时间：</label> '+
	         						'	<div class="col-sm-8"> '+
	         						'		<input class="form-control" readonly="readonly"   id="" name="" value="'+datas.updateTime+'" placeholder="办件更新时间"/>'+
	         						'	</div>'+
	         					'</div>'+
	        					'<div class="form-group"> '+
        						'	<label class="col-sm-3 control-label detailTitle">已推送次数：</label> '+
        						'	<div class="col-sm-8"> '+
        						'		<input class="form-control"  readonly="readonly"    id="" name="" value="'+datas.repeatNum+'" placeholder="已推送次数"/>'+
        						'	</div>'+
	        					'</div>'+
	        					'<div class="form-group"> '+
        						'	<label class="col-sm-3 control-label detailTitle">最大推送次数：</label> '+
        						'	<div class="col-sm-8"> '+
        						'		<input class="form-control"  readonly="readonly"    id="" name="" value="'+datas.repeatMaxNum+'" placeholder="最大推送次数"/>'+
        						'	</div>'+
	        					'</div>'+
	        					'<div class="form-group" style="'+(rows.sendData ? "" : "display : none")+'"  >'+
			 						'<label class="col-sm-3 control-label detailTitle">请求内容：</label>'+
			 						'<div class="col-sm-8">'+
			 							'<textarea class="form-control"  readonly="readonly"   id="" name="" style="height: '+(rows.sendData ? 126 : 33.99)+'px;">'+(rows.sendData ? rows.sendData : " ")+'</textarea>'+
			 						'</div>'+
		 						'</div>'+
			 					'<div class="form-group" style="'+(rows.aliSendData ? "" : "display : none")+'" >'+
			 						'<label class="col-sm-3 control-label detailTitle">阿里发送数据：</label>'+
			 						'<div class="col-sm-8">'+
			 							'<textarea class="form-control"  readonly="readonly"   id="" name="" style="height: '+(rows.aliSendData ? 80 : 33.99)+'px;">'+(rows.aliSendData ? rows.aliSendData : " ")+'</textarea>'+
			 						'</div>'+
		 						'</div>'+
	         					'<div class="form-group">'+
			 						'<label class="col-sm-3 control-label detailTitle">业务系统信息：</label>'+
			 						'<div class="col-sm-8">'+
			 							'<textarea class="form-control"  readonly="readonly"   id="" name="" style="height: '+(rows.sysMsg ? 80 : 33.99)+'px;">'+(rows.sysMsg ? rows.sysMsg : " ")+'</textarea>'+
			 						'</div>'+
		 						'</div>'+
	         					'<div class="form-group"> '+
        						'	<label class="col-sm-3 control-label detailTitle">中台信息：</label> '+
        						'	<div class="col-sm-8"> '+
        						'		<textarea class="form-control"  readonly="readonly"   id="" name="" style="height: '+(rows.ztMsg ? 80 : 33.99)+'px;">'+(rows.ztMsg ? rows.ztMsg : " ")+'</textarea>'+
        						'	</div>'+
	        					'</div>'+
             				'</form>';
                        	 $("#draftTablewrapDiv").append(head);
                        }
                        formatDialog($("#draftTablewrapDiv"),
                                {title:"办件详情",dialogClass:"mydialog situationHeight"},{"取消": formatDialogClose});
                        $(this).dialog("close");
                    }
                });
            }
            function repush(rows){
            	if(rows.repeatNum == rows.repeatMaxNum){
            		Modal.alert({ msg:'已达最大重推次数!', title:'提示', btnok:'确定' });
            	}else{
		            $.ajax({
		                    url : "projectRepeatAction!repushOne.action",
		                    type : "POST",
		                    data : {"projectId":rows.projectId},
		                    dataType : "json",
		                    error : function(request, textStatus, errorThrown) {
		                    	
		                    },
		                    success : function(data) {
		                    	Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' });
		                    }
		            });
	            }
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