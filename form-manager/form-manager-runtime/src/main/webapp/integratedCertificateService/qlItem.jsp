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
		<title>权力事项管理</title>
		<jsp:include page="../css/PageletCSS.jsp">
			<jsp:param value="icon,table,tree,validator,page,chosen" name="p" />
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/daterangepicker.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/login.css?v=2" />
		<style>
			#tablewrap td{max-width:150px;cursor:pointer;}
			/*情形新增、修改*/	
			#situationForm .chosen-container-single .chosen-single{height:34px;line-height:34px}
			#situationForm .chosen-container{width:100%!important;}
			#situationForm .chosen-container .chosen-drop {width: 100%;}
			.chosen-container-single .chosen-single div b{background-position:0 6px;}
			#situationForm .chosen-container-active.chosen-with-drop .chosen-single div b{background-position: -18px 8px;}
			.chosen-container-active .chosen-width-drop .chosen-single div b{background-position:0 6px;}
			.situation{border:1px solid #ddd}
			.situationHeight .modal-body{max-height:700px!important}
			.mydialog .modal-body{height:600px max-height:100px}
			.situationType{width:35px;text-align:center}
			.formTempHeight .modal-body{height:200px!important}
			.material{border-bottom:1px dashed #ddd;}
			.rightBox{background-color:transparent;}
			.tableCont{background-color:transparent;padding:0;}
			#deleteTipWrap .modal-dialog{width:300px}
			.detailItem .innerText{width:60%}
			.mycopydialog .modal-body{height:500px!important;}
			.modal-body{max-height:300px}
			.mysize{
				width: 30%;height: 10%;
			}
			.code-string{color:#993300;}
			.code-number{color:#cc00cc;}
			.code-boolean{color:#000033;}
			.code-null{color:magenta;}
			.code-key{color:#003377;font-weight:bold;}
			
		</style>
	</head>
	<body class="base_background-EEF6FB">
		<div class="wrapper">
			<div class="leftBox">
				<h3 class="header">
					<i class="fa fa-tag"></i>
					<span>部门列表</span>
				</h3>
				<div class="body">
					<div class="searchBox">
						<input id="inputSearch" class='inputSearch'/>
						<i class="fa fa-search" id="treeSearchBtn"></i>
					</div>
					<div class="treeBox">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="rightBox">
				<div id="tempDetailWrap" class="tableCont">
					<form class="form form-inline" id="searchForm">
						<label class='base_margin-r-10 '>查询日期:</label>
						<input type="hidden" class="form-control" id="departmentId" name="departmentId" /> <!-- 部门Id -->
			            <input type="text" name="queryDate" id="queryDate" class="form-control" style="" autocomplete="off">
			            <input type="hidden" class="form-control" id="startDate" name="startDate" placeholder="起始时间" />
			            <input type="hidden" class="form-control" id="endDate" name="endDate" placeholder="结束时间" />
			            
			            <input type="text" class="form-control" id="code" name="code" style="margin-right: 0px;width: 180px;" placeholder="事项编码或者事项名称" />
			            <input type="text" class="form-control" id="qlInnerCode" name="qlInnerCode" placeholder="内部编码" />
						
			  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
			  			<button type="button" class="btn btn-primary btn-sm" onClick="cleanSearch()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
			  			
			  			<button type="button" class="btn btn-primary btn-sm" onClick="handle()"><i class='fa fa-download'></i>操作</button>
			  		</form>
			  		<div class='listBox'>
			  			<div id="addCard"></div>
			  		</div>
			  		<div class="fixed-table-pagination">
						<div class="pull-left">
							<span>第&nbsp;<span id="pageNum">1</span>&nbsp;页&nbsp;/&nbsp;共&nbsp;<span id="totalPages"></span>&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;<span id="total"></span>&nbsp;条记录</span>
						</div>
						<div class="pull-right" id="paginationUl"></div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>		
		</div>
		
		<div style="display: none;" >
			<!-- 内部编码更新-->
           	<div id="detailItem" class="addFormWrap" style='margin-bottom: 10px,margin-left: 50px'>
           		<form class="form-horizontal" id="addForm" name="addform">
					<input type="hidden" class="form-control"  name="id" id="add_id" />
					<div class="form-group">
						<label class="col-sm-3 control-label">事项名称：</label>
						<div class="col-sm-8">
							<input class="form-control" id="itemName" name="itemName" placeholder="事项名称"  readonly="readonly" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">权利编码：</label>
						<div class="col-sm-8">
							<input class="form-control" id="itemCode" name="itemCode" placeholder="权利编码"  readonly="readonly"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">内部编码：</label>
						<div class="col-sm-8">
							<input class="form-control" id="itemInnerCode" name="itemInnerCode" readonly="readonly" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">部门编码：</label>
						<div class="col-sm-8">
							<input class="form-control" id="itemDeptId" name="itemDeptId" readonly="readonly" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">入库时间：</label>
						<div class="col-sm-8">
							<input class="form-control" id="tongTime" name="tongTime"  readonly="readonly" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">材料信息：</label>
						<div class="col-sm-8">
							<textarea class="form-control" id="material" name="material" style="height: 227px;" readonly="readonly"></textarea>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div style="display: none;" >
			<!-- 内部编码更新-->
           	<div id="operation" class="addFormWrap" style='margin-bottom: 10px,margin-left: 50px'>
	  			<button type="button" class="btn btn-block loginButton" onClick="synchro('')"><i class='fa fa-download'></i>一键同步</button>
			  	<button type="button" class="btn btn-block loginButton" onClick="updateInnerCode()"><i class='fa fa-download'></i>编码更新</button> 
			</div>
		</div>
		<div style="display: none;" >
			<!-- 内部编码更新-->
           	<div id="updateInnerCode" class="addFormWrap" style='margin-bottom: 10px,margin-left: 50px'>
	  			
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1px;'>
					<label class='detailTitle'>最近事项更新时间范围:</label>
					<label id="updateTime"></label>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>事项更新状态:</label>
					<label id="updateState"></label>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>更新消耗时间:</label>
					<label id="takeTime"></label>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>最近更新操作时间:</label>
					<label id="updateDate"></label>
				</div>
				<div id="thisTime" class='base_width-200' style='display:none;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>本次事项更新时间范围:</label>
					<label id="thisUpdateTime"></label>
				</div>
			</div>
		</div>
		<div style="display: none;" >
			<!-- 内部编码更新-->
           	<div id="synchroInnerCode" class="addFormWrap" style='margin-bottom: 10px,margin-left: 50px'>
           		<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>最近事项同步条件:</label>
					<label id="term"></label> 
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>事项更新状态:</label>
					<label id="updateStateS"></label>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>更新消耗时间:</label>
					<label id="takeTimeS"></label>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>最近更新操作时间:</label>
					<label id="updateDateS"></label>
				</div>
				<div id="thisTermDiv" class='base_width-200' style='display:none;margin-bottom: 10px;margin-left: 15px;margin-top: 1.5px;'>
					<label class='detailTitle'>本次事项同步条件:</label>
					<label id="thisTerm"></label>
				</div>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp">
			<jsp:param value="table,tree,validator,page,chosen" name="p" />
		</jsp:include>
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
		<script src='../build/js/treeSearch.js'></script>
		<script type="text/javascript" src='../build/js/moment.js'></script>  
		 <script type="text/javascript" src='../build/js/daterangepicker.js'></script>  
		<script type="text/javascript">
		var userPower = '';
		var typeState = '';
		var typeStateS = '';
		var treeSearchFlag=true;
		var data = null;
		$(document).ready(function(){
			getUserPower();
			var windowHeight=window.innerHeight;
			$(".base_box-area-aqua").css("height",windowHeight-103);
			$(".leftBox").css("height",windowHeight-40);
			$(".treeBox").css("height",windowHeight-136);
			$(".tableCont").css("height",windowHeight-40);
			$('.listBox').css('height',windowHeight-130);
			itemAjaxRequest();
			loadDepartment('treeDemo');
			getSynchroMsg();
			getItemSynchro();
		});
		//分页
		var pageSize = 10; pageNum = 1, pages = 1,newPages='';
		$('#paginationUl').page({
			leng: pages,//分页总数
			activeClass: 'activP' , //active 类样式定义
			maxShowPage:5, // 最多显示的页数
			clickBack:function(page){
				return clickBack(page);
			}
		});
		$('#pageSize li').click(function(){
			pageSize = $(this).find("a").text();
			$('.page-size').text(pageSize);
			itemAjaxRequest();
		})
		function clickBack(page){
			pageNum = page;
			$("#pageNum").text(pageNum);
			itemAjaxRequest();
		}
		function search(){
			pageNum = 1;
			itemAjaxRequest();
			$('#paginationUl').setLength(newPages);
		}
		//清除
		function cleanSearch() {
			pageNum = 1;
			$('#searchForm .form-control').each(function(){
				$(this).val("");
			});
			itemAjaxRequest();
			$('#paginationUl').setLength(newPages);
		}
		function itemAjaxRequest() {
			var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
			+ '&pageSize=' + pageSize;	
			$.ajax({
				type : 'post',
				url : "qlItemAction!findPage.action",
				dataType : 'json',
				cache : false,
				async : true,
				data : dataStr,
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					$('#addCard').siblings().remove();
					var datas=data.rows;
					var count=data.total;
					if(count == 0){
						$('.fixed-table-pagination').addClass("displayNones");
					}else{
						$('.fixed-table-pagination').removeClass("displayNones");
					}
					newPages =  Math.ceil(count / pageSize);
					var content = $("#paginationUl").html();
					if(content.length != 0 && pages != newPages){
						$('#paginationUl').setLength(newPages);
						pages = newPages;
					}else{
						pages = newPages;
					}
					$("#totalPages").text(newPages);
					$("#total").text(count);
					$("#pageNum").text(pageNum);
					if(datas!=null){
						var dataStr = '';
						for(var i=0;i<datas.length;i++){
							 dataStr += getCardContent(datas[i]);
						};
						$('#addCard').after(dataStr);
					}
				}
			});
		}
		//请求数据
		function getCardContent(data) {
			var dataStr = '<div class="detailItem">';
			if(userPower == 'edit'){
				dataStr +='<div class="base_line-height-30 base_margin-b-15"><h3 class="detailTitle">'+data.qlItemName+'</h3><div class="operationBtn">'
						+'<button type="button" class="btn btn-primary btn-sm base_margin-r-10" onclick="detail('+JSON.stringify(data).replace(/\"/g,"'")+')"><i class="fa fa-pencil base_margin-r-5"></i>详情</button>'
						+'<button type="button" class="btn btn-primary btn-sm base_margin-l-10" onclick="synchro('+ JSON.stringify(data).replace(/\"/g,"'")+')"><i class="fa fa-trash-o base_margin-r-5"></i>同步</button></div></div>' 
			}
		   dataStr += '<div class="row itemContent"><div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 base_padding-l-0">事项编码：<span class="innerText" title="'+(data.code ? data.code : "无")+'">'+(data.code ? data.code : "无")+'</span></div>'
				+'<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 base_padding-l-0">内部编码：<span class="innerText" title="'+(data.qlInnerCode ? data.qlInnerCode : "无")+'">'+(data.qlInnerCode ? data.qlInnerCode : "无")+'</span></div>'
				+'<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 base_padding-l-0">单位编码：<span class="innerText" title="'+(data.qlOugUId + ' / ' + data.qlAreaCode)+'">'+(data.qlOugUId + ' / ' + data.qlAreaCode)+'</span></div>'
				+ '<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 base_padding-l-0">入库时间：<span class="innerText" title="'+(data.tongTime ? data.tongTime.replace("T"," ") : "无")+'">'+(data.tongTime ? data.tongTime.replace("T"," ") : "无")+'</span></div></div></div>'
				+'</div></div></div>'
			return dataStr;
		}
		
		function handle(){
			formatDialog($("#operation"),
					{title:"事项操作",dialogClass:"mycopydialog mysize"},{"取消": formatDialogClose});
		}
		
		function detail(obj){
			$("#material").val(obj.materialInfo);
			$("#itemName").val(obj.qlItemName);
			$("#itemCode").val(obj.code);
			$("#itemInnerCode").val(obj.qlInnerCode);
			$("#tongTime").val(obj.tongTime ? obj.tongTime.replace("T"," ") : "无");
			$("#itemDeptId").val(obj.qlOugUId + " / " + obj.qlAreaCode);
			formatDialog($("#detailItem"),
					{title:"权力事项基本信息",dialogClass:"mycopydialog situationHeight"},{"取消": formatDialogClose});
		}
		
		// 事项同步
		function synchro(obj){
			$("#operation").dialog("close");
			if(obj == null || obj == ''){
				Modal.alert({ msg:'目前已开启自动同步功能，不用在进行批量同步！', title:'提示', btnok:'确定' });
				return;
			} 
			getItemSynchro(obj == '' ? '' : obj.qlInnerCode);
			data = obj == '' ? null : obj;
			formatDialog($("#synchroInnerCode"),
					{title:"权力事项同步信息核对",dialogClass:"mycopydialog mysize"},
					{"取消": formatDialogClose , "提交": doSynchro});
		}
		// 事项更新
		function updateInnerCode(){
			Modal.alert({ msg:'目前已开启自动更新功能，不用在进行批量更新！', title:'提示', btnok:'确定' });
			return;
			$("#operation").dialog("close");
			getSynchroMsg();	
			formatDialog($("#updateInnerCode"),
					{title:"内部编码更新信息核对",dialogClass:"mycopydialog mysize"},
					{"取消": formatDialogClose , "提交": doUpdateInnerCode});
		}
		// 事项同步操作
		function doSynchro(){
			$(this).dialog("close");
			var url = '';
			var paramData = null;
			if(data != null && data != ''){ // 单挑同步
				url = 'itemAction!synchroByInnerCodeAndTongTime.action';
				paramData = {
						"tongTime":data.tongTime.replace("T"," "),
						"innerCode":data.qlInnerCode
				};
			} else{
				url = 'itemAction!synchroByTerm.action';
				paramData = $('#searchForm').serialize();
			}
			Modal.confirm({ msg:"确认开始事项同步?", title:'提示', btnok:'确定',btncl:'关闭' }).on(function(e){
				if(!e){
					return;
				}
				$.ajax({
					cache : false,
					type : "post",
					url : url,
					data: paramData,
					dataType : 'json',
					async : true,
					error : function(request, textStatus, errorThrown) {
					}
				});
			});
		}
		
		// 更新内部编码操作
		function doUpdateInnerCode(){
			$(this).dialog("close");
			if($("#queryDate").val() == null || $("#queryDate").val() == ''){
				Modal.alert({ msg:'请选择本次同步的时间范围!', title:'提示', btnok:'确定' });
				return;
			}
			if(typeState == 2){
				Modal.alert({ msg:'正在更新请稍等!', title:'提示', btnok:'确定' });
				return;
			}
			Modal.confirm({ msg:"确认开始更新内部编码?", title:'提示', btnok:'确定',btncl:'关闭' }).on(function(e){
				if(!e){
					return;
				}
				$.ajax({
					cache : false,
					type : "post",
					url : 'itemAction!updateInnerCode.action',
					data: $('#searchForm').serialize(),
					dataType : 'json',
					async : true,
					error : function(request, textStatus, errorThrown) {
					}
					/* success : function(data) {
						 Modal.alert({ msg:'操作成功！', title:'提示', btnok:'确定' }).on(function(e){
							 search();
                         });
					}  */
				});
			}); 
		}
		
		function getItemSynchro(innerCode){
			$.ajax({
				cache : false,
				type : "post",
				url : 'itemAction!findUpdateState.action',
				dataType : 'json',
				data:{"tableName":"qlt_qlsx"},
				async : false,
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					if(data != null && data.data != null){
						if(data.data.state == 1){
							$("#updateStateS").text("已更新");
						}else if(data.data.state == 2){
							$("#updateStateS").text("正在更新，请稍等！");
						}else{
							$("#updateStateS").text("更新失败");
						}
						typeStateS = data.data.state;
					}
					$("#takeTimeS").text(" "+data.data.takeTime + " 毫秒");
					$("#term").text(data.data.term);
					$("#updateDateS").text(" " + data.data.updateTime.replace('T',' '));
					if(data.data.state == 2){
						$("#takeTimeS").text("还未更新完成，请稍等。");
					}
					$("#thisTermDiv").css("display","inline-block");
					$("#thisTerm").text("");
					if(innerCode != null && innerCode != ''){
						$("#thisTerm").text(innerCode);
					}else{
						$("#thisTerm").append("<p>"+$("#qlInnerCode").val()+"</p>");
						$("#thisTerm").append("<p>"+$("#code").val()+"</p>");
						$("#thisTerm").append("<p>"+$("#queryDate").val()+"</p>");
					}
					
					
				}
			});
		}
		
		// 获取当前更新的信息
		function getSynchroMsg(){
			$.ajax({
				cache : false,
				type : "post",
				url : 'itemAction!findUpdateState.action',
				dataType : 'json',
				data:{"tableName":"c_item"},
				async : false,
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					if(data != null && data.data != null){
						$("#updateTime").text(data.data.startTime.substring(0,10) + " 至  " + 
								data.data.endTime.substring(0,10));
						if(data.data.state == 1){
							$("#updateState").text("已更新");
						}else if(data.data.state == 2){
							$("#updateState").text("正在更新，请稍等！");
						}else{
							$("#updateState").text("更新失败");
						}
						typeState = data.data.state;
					}
					$("#takeTime").text(" "+data.data.takeTime + " 毫秒");
					$("#updateDate").text(" " + data.data.updateTime.replace('T',' '));
					if(data.data.state == 2){
						$("#takeTime").text("还未更新完成，请稍等。");
					}
					if($("#queryDate").val() != null && $("#queryDate").val() != ''){
						$("#thisTime").css("display","inline-block");
						$("#thisUpdateTime").text($("#queryDate").val());
					}
				}
			});
		}
		
		//获取当前用户的权限
		function getUserPower(){
			$.ajax({
				cache : false,
				type : "post",
				url : 'userAction!findUserPower.action',//获取当前用户的权限
				dataType : 'json',
				async : false,
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					userPower = data.power;
					if(userPower == 'edit'){
						$("#addBtn").show();
						$("#copyBtn").show();
					}
				}
			});
		}
		
		 //区间时间插件
        $("input[name='queryDate']").daterangepicker(
                  {
                 timePicker24Hour : true,//设置小时为24小时制 默认false
                 showDropdowns: true,
                 autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
                 timePickerSeconds: false, //时间显示到秒
                 startDate: moment().hours(0).minutes(0), //设置开始日期
                 endDate: moment().hours(0).minutes(0), //设置结束器日期
                 showWeekNumbers: true,
                     locale: {
                         format: "YYYY/MM/DD",
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
              $("#startDate").val(picker.startDate.format('YYYY-MM-DD'));
              $("#endDate").val(picker.endDate.format('YYYY-MM-DD'));
              $("#queryDate").val(picker.startDate.format('YYYY-MM-DD')
            		  +" 至 "+picker.endDate.format('YYYY-MM-DD'));
      });
		 
        
      //部门列表树点击回调函数
        function treeCallBack(treeName,treeId){
			pageNum = 1
			$("#departmentId").val(treeId);
			itemAjaxRequest();
        }
     
		</script>
	</body>
</html>