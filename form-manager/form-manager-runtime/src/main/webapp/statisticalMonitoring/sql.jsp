<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta name="description" content="3 styles with inline editable feature" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <title>材料管理</title>
        <jsp:include page="../css/PageletCSS.jsp" >
            <jsp:param value="table,tree,chosen,daterang,icon" name="p"/>
        </jsp:include>
        <link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/list.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="../build/css/bootstrap.min.css" />
        <style>
            .chosen-select-deselect{height:30px!important;}
/*          .mydialog .modal-body{height:150px!important;} */
            .searchInput{height:30px;}
            .chosen-container{width: 100%!important;}
            .chosen-container-multi .chosen-choices{width:100%}
            .chosen-container-single .chosen-single{height:30px;line-height:30px}
            .chosen-container .chosen-drop{width: 100%;}
            .chosen-container-single .chosen-single div b{background-position:0 4px;}
            .chosen-container-active .chosen-width-drop .chosen-single div b{background-position:0 4px;}
            .chosenSelect{width:200px;height:30px;border-radius: 4px;}          
            .mydialog .modal-body{height:500px!important;}
            #detail{width:60%;height:300px;}
            .tableHead {background: -webkit-linear-gradient(left,#fff, #fff) !important;padding-left:50px; height: 70px; padding-top: 20px}
            .tableHead .tableName span { font-family:PingFangSC-Medium;font-weight:500;color:rgba(51,51,51,1);}   
            .tableBox {height: 330px !important;}        
        </style>
    </head>
    <body class="base_background-EEF6FB">
        <div style="display: none;">
            <div class="draftTablewrap base_margin-b-20" id="sqlArea">
            
            </div>  
        </div>
    
        <form class="form form-inline base_padding-20 base_margin-b-10 base_height-60" id="searchForm">
            <label class='base_margin-r-10'>数据库:</label>
            <div class='base_width-200' style='display:inline-block;position:relative;'>
                <select class="form-control chosenSelect chosen-select-deselect" data-placeholder="请选择" id="dbSort" name="dbSort" >
                    <option value = "0">表单中心库</option>
                    <option value = "1">一证通办log库</option>
                </select>
            </div>
            
            <label class='base_margin-r-10'>sql:</label>
            <div class='base_width-600' style='display:inline-block;position:relative;'>
                <input type="text" class="form-control" style = "width:600px" id="sqlSearch" name="sql" 
                placeholder="请输入您要查询的sql语句" />
            </div>
            <button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
            <button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
        </form>     
        <div class="wrapper">
            <div style="overflow: hidden">
                <div class="tableBox">
                    <div class="tableHead">
                        <h3 class="tableName">                          
                            <span>语句查询结果展示:</span>
                        </h3>
<!--                         <div class="tableBtns"> -->
<!--                             <button onclick='exportExcel()'><i class="fa fa-cogs"></i><span>导出表格</span></button> -->
<!--                         </div> -->
                    </div>
                    <div id="tempDetailWrap" class="tableCont" style="padding: 0 50px;">
                        <span  id="sqlResult" name="sqlResult"  readonly="readonly"
                        style="display:inline-block; width:100%;height: 230px; background:rgba(245,245,245,1);"></span>
                    </div>
                </div>
            </div>
        </div>                  
        <div id="reasonWrap" class="base_hidden"></div>
        <jsp:include page="../js/PageletJS.jsp" >
            <jsp:param value="table,tree,chosen,icon,daterang" name="p"/>
        </jsp:include>
        <script type="text/javascript" src='../build/js/loadDepartment.js'></script>
<!--        <script type="text/javascript" src='../build/js/statistics.js'></script> -->
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
            });
            //查询
            function search() {
                var dbSort = $("#dbSort").val();
                var sql = $("#sqlSearch").val();
                var dataStr = 'sql='+sql + "&dbSort="+dbSort;
                $.ajax({
                    type : 'post',
                    url : 'sqlSearchAction!findBySql.action',
                    data:dataStr,
                    dataType : 'json',
                    cache : false,
                    async : true,
                    error : function(request, textStatus, errorThrown) {
                    	console.log(1);
                    },
                    success : function(data) {
                        if(data != null){
	                        var datas = JSON.stringify(JSON.parse(data.result), null, 4);
	                        var count = data.rows ? data.total : 0;
                        	$("#sqlResult").val(datas);
                            var htmlStr = '<textarea id="detail" readonly="readonly">'+datas+'</textarea>';
                        }else{
                        	$("#sqlResult").val("无数据");;
                        }
                    }
                });
            }
            //清除
            function clean() {
                pageNum = 1;
                $("#sqlResult").val("");
                $('#searchForm .form-control').each(function(){
                    $(this).val("");
                });
                //清除下拉框的值
                selectConfig();//下拉框初始化
                selectUpdated($("#cert"));
                $('#dataTable').bootstrapTable('refresh');
            }
            //表格请求
            function ajaxRequest(params) {
            }
            
            
             //区间时间插件
        </script>
    </body>
</html>