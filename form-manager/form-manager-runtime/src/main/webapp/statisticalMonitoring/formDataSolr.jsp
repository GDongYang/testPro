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
        <title>表单数据表</title>
        <jsp:include page="../css/PageletCSS.jsp" >
            <jsp:param value="table,icon,page" name="p"/>
        </jsp:include>
        <link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" href="../iconfont/iconfont.css">
        <style type="text/css">
              .itemImg{ width: 50px;}
              .situationHeight .modal-body{max-height:700px!important}
        </style>
    </head>
    <body class="base_background-EEF6FB">
    
        <div style="display: none;">
            <div class="draftTablewrap base_margin-b-20" id="draftTablewrapDiv">
				<div style="display: none;" >
				</div>
            </div>  
        </div> 
          
        <form class="form form-inline base_padding-20 base_margin-b-10" id="searchForm">
            <label>查询日期:</label>
            <input type="text" name="queryDate" id="queryDate" class="form-control base_margin-r-20" style="">
            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
            
            <label>表单流水号:</label>
            <input type="text" class="form-control base_margin-r-20" id="formBusinessCodeSearch" name="formBusinessCode" placeholder="表单流水号" />
                        
            <label>身份证号码:</label>
            <input type="text" class="form-control base_margin-r-20" id="cernumSearch" name="certNum" placeholder="身份证号码" />
            
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
                        <span>表单数据表</span>
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
                                <th data-field="dataCreated" data-formatter="formatDate">数据提交时间</th>
                                <th data-field="formCode">表单编码</th>
                                <th data-field="itemName"> 事项名称</th>
                                <th data-field="formBusiCode" >表单流水号</th>
                                <th data-field="applyerCardNumber" >身份证号码</th>
                                <th data-formatter="operation" >操作</th>  
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>                  
        </div>
        <jsp:include page="../js/PageletJS.jsp" >
            <jsp:param value="table,daterang,page" name="p"/>
        </jsp:include>    
        <script type="text/javascript" src='../build/js/loadDepartment.js'></script>
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
            function formatDate(val) {
                if(val) {
                    return val.replace("T"," ");
                }
            }
            
            function formatSfz(val) {
                if(val) {
                    return val.substring(2,20);
                }
            }
            var id = 1;
			function idFormatter(value, row){
				return id++;
			}
            //ajax请求
            function ajaxRequest(params) {
                var pageSize = params.data.limit;
                var pageNum = params.data.offset/pageSize + 1;
                var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
                + '&pageSize=' + pageSize;
                console.log(dataStr);
                $.ajax({
                    type : 'post',
                    url : 'formDataSolrAction!findPage.action',
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
                loadCert();
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
            	id = 1;
            	 pageNum = 1;
                $('#dataTable').bootstrapTable('refresh');
            }
            //清除
            function clean() {
                pageNum = 1;
                id = 1;
                $('#searchForm .form-control').each(function(){
                    $(this).val("");
                });
                $('#dataTable').bootstrapTable('refresh');
            }
            function operation(value,rows){
                var btn = '<button type="button" class="btn btn_span btn-sm base_margin-r-10" onclick="detail('+JSON.stringify(rows.formBusiCode).replace(/\"/g,"'")+')"><i class="iconfont icon-chakan base_margin-r-5"></i>详情</button>';
                return btn;
            }
            
            function detail(formBusiCode){
                $.ajax({
                    url : "formDataSolrAction!findByBusinessCode.action",
                    type : "POST",
                    data : {"formBusinessCode":formBusiCode},
                    dataType : "json",
                    error : function(request, textStatus, errorThrown) {
                        //fxShowAjaxError(request, textStatus, errorThrown);
                    },
                    success : function(data) {
                         var datas = data ? data.rows : [];
                         var count = data ? data.total : 0;
                         var createDate = "";
                         $("#draftTablewrapDiv form").remove();
                         if(datas !=null && datas.length > 0){
                        	 var html = '<form class="form-horizontal"> '+
             					'<input type="hidden" class="form-control"  name="id" id="add_id" /> '+
             					'	<div class="form-group">'+
             					'		<label class="col-sm-3 control-label detailTitle">事项名称：</label>'+
             					'		<div class="col-sm-8">'+
             					'			<input class="form-control"  placeholder="事项名称"  value="'+datas[0].itemName+'" readonly="readonly" />'+
             					'		</div>'+
             					'	</div>'+
             					'	<div class="form-group">'+
             					'		<label class="col-sm-3 control-label detailTitle">权利编码：</label>'+
             					'		<div class="col-sm-8">'+
             					'			<input class="form-control"  placeholder="权利编码"   value="'+datas[0].itemCode+'"  readonly="readonly"/>'+
             					'		</div>'+
             					'	</div>'+
             					'	<div class="form-group">'+
             					'		<label class="col-sm-3 control-label detailTitle">表单流水号：</label>'+
             					'		<div class="col-sm-8">'+
             					'			<input class="form-control" readonly="readonly" value="'+datas[0].formBusiCode+'" />'+
             					'		</div>'+
             					'	</div>'+
             					'	<div class="form-group">'+
             						'	<label class="col-sm-3 control-label detailTitle">证件号码：</label>'+
             						'	<div class="col-sm-8">'+
             							'	<input class="form-control"   readonly="readonly" value="'+datas[0].applyerCardNumber+'" />'+
             							'</div>'+
             						'</div>'+
             					'</form>';
             				$("#draftTablewrapDiv").append(html);
                       	 	$.each(datas, function (index, value) {
                       	 	    console.log(value)
                       		 	createDate = value.dataCreated;
		                        createDate = (createDate==null ? "":createDate.replace("T"," "));
		                        var htmlstr = '<form class="form-horizontal" id="addForm" name="addform">'+
		                        '	<div class="form-group">'+
									'	<label class="col-sm-3 control-label">创建时间：</label>'+
									'	<div class="col-sm-8">'+
									'		<input class="form-control" readonly="readonly" value="'+ createDate +'" />'+
									'	</div>'+
								'	</div>'+
								'	<div class="form-group">'+
									'	<label class="col-sm-3 control-label">表单内容：</label>'+
										'<div class="col-sm-8">'+
										'	<textarea class="form-control" style="height: 126px;" readonly="readonly">'+value.formContent+'</textarea>'+
										'</div>'+
									'</div>'+
								'</form>';
		                        $("#draftTablewrapDiv").append(htmlstr);
                       	 	});
                         }else{
                         	$("#draftTablewrapDiv").html( "<tr><td colspan='8'>数据为空</td></tr>");
                         }                        
                         formatDialog($("#draftTablewrapDiv"),
                        		 {title:"表单详情",dialogClass:"mydialog situationHeight"},{"取消": formatDialogClose});
                         $(this).dialog("close");
                    }
                });
            }
            
          //区间时间插件
            $("input[name='queryDate']").daterangepicker(
                      {
                     autoUpdateInput: true,
                     timePicker: true, //显示时间
                     timePicker24Hour : true,//设置小时为24小时制 默认false
                     showDropdowns: true,
                     autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
                     timePickerSeconds: false, //时间显示到秒
                     startDate: moment().hours(0).minutes(0), //设置开始日期
                     endDate: moment(), //设置结束器日期
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
            
            function initDrafTable(data) {
            }
            
            function fillImg(certName,certFile){
            	return 1;
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
                str = "<%=path %>"+"/rest/excel/exportFormDataExcel?"+dataStr;
                window.location.href = str;
            } 
        </script>
    </body>
</html>