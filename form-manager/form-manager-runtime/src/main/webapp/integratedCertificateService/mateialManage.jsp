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
			<jsp:param value="table,tree,chosen,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
		<link rel="stylesheet" type="text/css" href="../build/css/list.css" />
		<link rel="stylesheet" href="../iconfont/iconfont.css">
		<style>
			.chosen-select-deselect{height:30px!important;}
			.mydialog .modal-body{height:150px!important;}
			.searchInput{height:30px;}
			.chosen-container{width: 100%!important;}
			.chosen-container-multi .chosen-choices{width:100%}
			.chosen-container-single .chosen-single{height:30px;line-height:30px}
			.chosen-container .chosen-drop{width: 100%;}
			.chosen-container-single .chosen-single div b{background-position:0 4px;}
			.chosen-container-active .chosen-width-drop .chosen-single div b{background-position:0 4px;}
			.chosenSelect{width:200px;height:30px;border-radius: 4px;}
			.mycopydialog .modal-body{height:800px!important;}
			.tableBtns .iconfont{font-size:14px;}
		</style>
	</head>
	<body class="base_background-EEF6FB">
		<form class="form form-inline base_padding-20 base_margin-b-10" id="searchForm">
			<label class='base_margin-r-10'>材料名称:</label>
  			<input type="text" class="form-control searchInput" id="name" name="name" placeholder="材料名称" /> 
  			<button type="button" class="btn btn-primary btn-sm base_margin-b-2" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
  			<button type="button" class="btn btn-primary btn-sm base_margin-b-2" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>	
		<div class="wrapper">
			<div class="leftBox">
					<h3 class="header">
						<i class="fa fa-tag"></i>
						<span>情形列表</span>
					</h3>
					<div class="body">
						<label class='base_margin-r-10'>所属部门:</label>
			  			<div class='base_width-200' style='display:inline-block;position:relative;margin-bottom: 20px'>
			  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
							<input class="form-control searchInput base_width-200" id="deptSearchId" onclick="showMenu('menuContent');" autocomplete="off"/>
							<div id="menuContent" class="menuContent base_hidden searchTree base_width-200">
								<ul id="treeDemo" class="ztree"></ul>
							</div>
						</div>
						<label class='base_margin-r-10'>所属事项:</label> 
						<div class='base_width-200' style='display:inline-block;position:relative;'>
			  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
							<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="itemSelectId" name="itemSelectId" ></select>
						</div>
						<!-- <div class="searchBox">
							<input id="inputSearch" class='inputSearch'/>
							<i class="fa fa-search" id="treeSearchBtn"></i>
						</div> -->
						<div class="treeBox">
							<ul id="treeDemo1" class="ztree"></ul>
						</div>
					</div>
				</div>
				<div class="rightBox" style="overflow:auto">
					<div class="tableBox">
						<div class="tableHead">
							<h3 class="tableName">
								<i class="fa fa-tag"></i>
								<span>材料列表</span>
							</h3>
							<div class="tableBtns">
								<button onclick='bindTemps()'><i class="iconfont icon-fenpei base_margin-r-3"></i><span>批量关联模板</span></button>
								<button onclick='association()'><i class="iconfont icon-gongxiang1 base_margin-r-3"></i><span>关联模板</span></button>
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
										<th data-field="id" data-formatter="idFormatter">序号</th>
										<th data-field="name">名字</th>
										<th data-field="code">编码</th>								
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>					
		<div style="display: none;">
    		<!--关联模板 -->
           	<div id="addFormWrap" class="addFormWrap">
				<form class="form-horizontal" id="addForm" name="addform">
					<input type="hidden"  class="form-control" id="id" name="id"/> 
					<input type="hidden" class="form-control" id="tempCode" name="code"/>
					<input type="hidden" class="form-control" id="dataCode" name="dataCode"/>
					<div class="form-group">
						<label class="col-sm-3 control-label">模板(必要)：</label>
						<div class="col-sm-8">
							<select class="form-control chosen-select-deselect" data-placeholder="请选择" multiple="multiple" id="certTempId" name="certTempIdS" ></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">模板：</label>
						<div class="col-sm-8">
							<select class="form-control chosen-select-deselect" data-placeholder="请选择" multiple="multiple" id="certTempId1" name="certTempIdS1" ></select>
						</div>
					</div>
				</form>
			</div>
		</div>		
		
		<div style="display: none;">
			<!-- 批量绑定证明-->
           	<div id="copyItemWrap" class="addFormWrap">
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px,margin-left: 50px'>
					<label class='base_margin-r-10'>材料信息:</label>
	  				<input class="form-control" name="materialInfo" id="materialInfo"  placeholder="材料名/材料Code"/>
				</div>
				<div class='base_width-50' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary base_margin-l-20"onclick="findMaterials()"><span class="fa">查询</span></button>
				</div>
	  			<div class='base_width-200' style='display:inline-block;margin-bottom: 10px'>
	  				
				</div>
				<div class='base_width-70' style='display:inline-block;position:relative;margin-bottom: 10px'>
				</div>
				<div class='base_width-200' style='display:inline-block;margin-bottom: 10px'>
           			<label class='base_margin-r-10'>获取的材料:</label> 
	  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
					<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="materialSelect" name="materialSelect" ></select>
				</div>
				<div class='base_width-100' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary base_margin-l-20" name="addAllMaterials" id="addAllMaterials"><span class="fa fa-plus-circle"></span>材料全选</button>
				</div>
				<div class='base_width-100' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary base_margin-l-20" name="removeMaterial" id="removeMaterial"><span class="fa fa-minus-circle">移除材料</span></button>
				</div>
				<div class='base_width-100' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<button class="btn btn-sm btn-primary base_margin-l-20" name="removeAllMaterials" id="removeAllMaterials" ><span class="fa fa-minus-circle">清空材料</span></button>
				</div>
				<div class='base_width-200' style='display:inline-block;position:relative;'>
					<label class='base_margin-r-10'>获取的证明:</label> 
	  				<input class="form-control" name="searchDept" id="deptIdSearch" type='hidden'/>
					<select class="chosenSelect chosen-select-deselect" data-placeholder="请选择" id="tempSelect" name="tempSelect" ></select>
				</div>
				<div class='base_width-100' style='display:inline-block;position:relative;margin-bottom: 10px'>
					<!--<button class="btn btn-sm btn-primary base_margin-l-20" name="removeTemp" id="removeTemp"><span class="fa fa-minus-circle">移除证明</span></button>-->
				</div>
				<div class='base_width-100' style='display:inline-block;position:relative;margin-bottom: 10px'>	
					<!--<button class="btn btn-sm btn-primary base_margin-l-20" name="removeAllTemps" id="removeAllTemps" ><span class="fa fa-minus-circle">清空证明</span></button>-->
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<label >材料列表</label>
				</div>
				<div col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center>
				</div>
				<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
					<!--<label >证明列表</label>-->
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center base_input-area" >
					<select multiple="multiple" size="15" class="form-control base_multiple-select" name="materialList" id="materialList" style="height:400px"></select>
				</div>
				<div col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center>
				</div>
				<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7 text-center base_input-area" style="height:400px;overflow:scroll; ">
					<!--<select multiple="multiple" size="15" class="form-control base_multiple-select" name="tempList" id="tempList" style="height:400px"></select>-->
				</div>
			</div>
		</div>
		<div id="reasonWrap" class="base_hidden"></div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table,tree,chosen,icon" name="p"/>
		</jsp:include>
		<script type="text/javascript" src='../build/js/loadDepartment.js'></script>
		<script type="text/javascript">
			var dataTable;
			var pageNum = 1;
			var pageSize = 10;
			var index = 1;
			var isSearch = false;	 //是否是搜索请求	
			var treeSearchFlag=false;//判断是否需要模糊搜索
			var oldHtml = "";		 //用来存储返回事项的数据
			var situationId = "";	 //存储情形的id用来获取材料	
            var chosenSelectVal = "";
            var itemId = 0;          //事项ID号
            var departmentId = 0;    //部门Id号
			
			function idFormatter(value, row) {
				return index++;
			}
			var situationSetting = { //情形树的配置
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							rootPId: null
						}
					},callback: {
						onClick: zTreeOnClick,
					}
			};
			$(function() {
				//loadDepartment('treeDemo');
				$('#dataTable').on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").attr('checked','checked');//选中行内radio
				});
				var windowHeight=window.innerHeight;
				$("#tempDetailWrap").css("height",windowHeight-140);
				$(".base_box-area-aqua").css("height",windowHeight-103);
				$(".leftBox").css("height",windowHeight-60);
				$(".rightBox").css("height",windowHeight-60);
				$(".treeBox").css("height",windowHeight-136);
			});
			$(document).ready(function(){				
				selectConfig();//下拉框初始化
				loadDepartment('treeDemo');
				//findItem('1');
			});	
			//chosen查询找不到结果触发
			$('.chosen-select-deselect').on('chosen:no_results',function(e,params){
				//console.log("查询的值为" + $(".chosen-search-input").val())
				//只有当包含中文的时候才进行调接口查询减少开销
				var reg = /[\u4e00-\u9fa5]/g;
				if(reg.test($(".chosen-search-input").val())){
					chosenSelectVal = $(".chosen-search-input").val();
					findItem(departmentId);
				}
			});	
			//搜索树点击回调函数
			function treeCallBack(treeName,treeId){
				$("#deptSearchId").val(treeName);
                $("#deptIdSearch").val(treeId);
                $("#itemSelectId").empty();
                selectUpdated($("#itemSelectId"));
                itemId = 0;
				oldHtml = "";										   //清空保存的事项数据
				situationId = ""									   //清空选择情形ID
                departmentId = treeId;								   //设置部门ID
				$.fn.zTree.init($("#treeDemo1"), situationSetting, []);//清空情形树的数据
				//$('#dataTable').bootstrapTable('refresh');             //刷新右侧材料列表
				$("#dataTable").bootstrapTable('selectPage',1);
                findItem(treeId);									   //根据部门Id获取事项
            }
            // 情形树点击触发 
			function zTreeOnClick(treeName,treeId,treeNode){
				situationId = treeNode.id;
				$('#dataTable').bootstrapTable('refresh');
			}
			//item下拉框选定后获取对应的情形
			 $('#itemSelectId').on('change',function(e,params){
                itemId = params.selected; 
                situationId = "";
				//$('#dataTable').bootstrapTable('refresh');
				$("#dataTable").bootstrapTable('selectPage',1);
                findSituationByItemId(params.selected);
			}) 
			//查询
			function search() {
				 pageNum = 1;
				 isSearch = true;
				//$('#dataTable').bootstrapTable('refresh');
				$("#dataTable").bootstrapTable('selectPage',1);
				//$('#dataTable').bootstrapTable('selectPage', 1);
			}
			//清除
			function clean() {
				pageNum = 1;
				$('#searchForm .form-control').each(function(){
					$(this).val("");
				});
				search();
			}
			//关联模板
			function association(){
				$("#certTempId").val('');//必要
				selectUpdated($("#certTempId"));//下拉框变动更新
				$("#certTempId1").val('');
				selectUpdated($("#certTempId1"));//下拉框变动更新
				var info = $("#dataTable").bootstrapTable("getSelections");
				if (info != 0) {
					$("#id").val(info[0].id);
					findCertTemp();	
					//模板回显
					$("#certTempId").val(findRTemp(info[0].id,'1'));//必要
					selectUpdated($("#certTempId"));//下拉框变动更新
					$("#certTempId1").val(findRTemp(info[0].id,'0'));//非必要
					selectUpdated($("#certTempId1"));//下拉框变动更新
			        formatDialog($("#addFormWrap"),{title:"关联模板",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": insert});
				}else{
					Modal.alert({ msg:'请选择关联内容！', title:'提示', btnok:'确定' });
				}				
			}
			//关联模板确认
			function insert(){
				var radioStr='';
				$('input[type="radio"]').each(function(){
					if($(this).attr('checked')){
						radioStr+='&'+$(this).attr('nameSet')+'='+$(this).attr('valueSet');
					}
				});				
				ajaxParams = {
					url:'materialAction!bindCertTemp.action',
					data:$('#addForm').serialize()+radioStr
				};				
				//formatAjax(ajaxParams,tipsDialog);
				$(this).dialog("close");
			}
			//表格请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
				index = params.data.offset + 1;
				// if(isSearch == false){
			   	// 	var pageNum = params.data.offset/pageSize + 1;
			   	// 	var dataStr = 'pageNum=' + pageNum
				// 	+ '&pageSize=' + pageSize + "&situationId=" + situationId;
				//  }else{
                    if(isSearch){
                        var pageNum = 1;
                        isSearch = false;
                    }else{
                        var pageNum = params.data.offset/pageSize + 1;
                    }
					isSearch = false;
					var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
					+ '&pageSize=' + pageSize + "&situationId=" + situationId + "&itemId=" + itemId + "&departmentId=" + departmentId;
				// } 
				$.ajax({
					type : 'post',
					url : 'materialAction!findPage.action',
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
					}
				});
			}
			//模板
			function findCertTemp() {
				$.ajax({
					cache : false,
					type : "get",
					url : 'certTempAction!findAllActive.action',
					dataType : 'json',
					async : false,
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
							$("#certTempId").empty();
							$("#certTempId1").empty();
							if (data.result != null) {
								allTemp = data.result;
								var htmlStr = "<option value=\"\"></option>";
								//todo去重
								for (var i = 0; i < data.result.length; i++) {
									var catalogDeptName = data.result[i].catalogDeptName == null?  "无": data.result[i].catalogDeptName;
									htmlStr += "<option value=\""+data.result[i].id+"\">"
											+ data.result[i].name + "(" + catalogDeptName + ")</option>";
							}
							$("#certTempId").append(htmlStr);
							selectUpdated($("#certTempId"));
							$("#certTempId1").append(htmlStr);
							selectUpdated($("#certTempId1"));
							$("#tempSelect").append(htmlStr);
							selectUpdated($("#tempSelect"));
						}
					}
				});
			}
			//根据id查询关联模板
			function findRTemp(itemId,isMust){
				var result='';
				$.ajax({
					cache : false,
					type : "POST",
					url : 'materialAction!findCertMaterial.action',
					dataType : 'json',
					data : {
						id : itemId,
						isMust:isMust
					},
					async : false,
					error : function(request, textStatus,errorThrown) {
					},
					success : function(data) {
						result = data.certs;
					}
				});
				return result;
			}
			//根据部门id获取事项
			function findItem(id) {
				var name = $(".chosen-search-input").val();
				$.ajax({
					cache : false,
					type : "post",
					url : 'itemAction!findPaeTable.action',
					dataType : 'json',
					data: {'departmentId': id,'pageNum':1,'pageSize':1000,'name':name},
					async : false,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
                            $("#itemSelectId").empty();
						if (data.rows != null) {
							allTemp = data.result;
							var htmlStr = "<option value=\"\"></option>";
							for (var i = 0; i < data.rows.length; i++) {
								htmlStr += "<option value=\""+data.rows[i].id+"\">"
										+ data.rows[i].name + "</option>";
							}
							selectConfig();//下拉框初始化
							oldHtml = htmlStr + oldHtml;
							$("#itemSelectId").append(oldHtml);
							selectUpdated($("#itemSelectId"));
							$(".chosen-search-input").val(chosenSelectVal);
						}
					}
				});
			}
			//根据事项id查询情形
			function findSituationByItemId(itemId){
				$.ajax({
					cache : false,
					type : "post",
					url : 'situationAction!findSituationByItemId.action',
					dataType : 'json',
					data: {'itemId': itemId},
					async : false,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
						if(data){
							$.fn.zTree.init($("#treeDemo1"), situationSetting, data.situations);
				   		} else {
				   			var list= [];
							$.fn.zTree.init($("#treeDemo1"), situationSetting, list);
				   		}
					}
				});
			}
			
			/*批量绑定模板*/
			function bindTemps(){
				$("#findCertTemp").val("");
				findCertTemp();
				formatDialog($("#copyItemWrap"),{title:"批量绑定证明",dialogClass:"mycopydialog "},{"取消": formatDialogClose , "提交":insertTempsAndMaterials });
			}
			/*获取材料列表*/
			function findMaterials(){
			 	var name = $("#materialInfo").val();
			 	if(name != ''){
					$.ajax({
						cache : false,
						type : "post",
						url : 'materialAction!findMaterialList.action',
						dataType : 'json',
						data: {'name': name},
						async : true,
						error : function(request, textStatus, errorThrown) {
						},
						success : function(data) {
							$("#materialSelect").empty();
							if (data.result != null) {
								allTemp = data.result;
								var htmlStr = "<option value=\"\"></option>";
								for (var i = 0; i < data.result.length; i++) {
									htmlStr += "<option value=\""+data.result[i].name+"\">"
											+ data.result[i].name + "</option>";
								}
							}
							$("#materialSelect").append(htmlStr);
							selectUpdated($("#materialSelect"));
						}
					});
				}
			}
			/*绑定材料和证明*/
			function insertTempsAndMaterials(){
				var materialNames = [];
				var certTempIds = [];
				/*获取需要关联测材料name数组*/
				$("#materialList option").each(function(e,params){
					var materialName = $(this).val();
					materialNames.push(materialName);				
				});
				/*获取需要关联的证明ID数组*/
				/*$("#tempList option").each(function(e,params){
					var certTempId = $(this).val();
					certTempIds.push(certTempId);
				});*/
				//证件选取单个
				var certTempId = $("#tempSelect").find("option:selected").val();
				certTempIds.push(certTempId);
				$.ajax({
					cache : false,
					type : "post",
					url : 'materialAction!bindMaterialsAndTemps.action',
					dataType : 'json',
					data: {'certTempIdS': certTempIds,'materialNames':materialNames},
					async : true,
					traditional:true,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
						$("#copyItemWrap").dialog("close");//关闭form
						Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' }).on(function(e){
					});
					}
				});
				
			}
			/*将单个材料加入到材料列表中*/
			$("#materialSelect").on('change',function(e,params){
				var materialName = $("#materialSelect").find("option:selected").text();
			    var html = '<option value="'+materialName+'">'+materialName+'</option>';
			    var i = 0;
			    //遍历列表检查是否已经存在该事项
			    $("#materialList option").each(function() {
					var id =$(this).val();
					if( id == itemId){
						html = "";
					}
					i++;
				});
			    //将事项放在数源列表里
			    $("#materialList").append(html);
			})
			/*将单个证明加入到证明列表*/
			$("#tempSelect").on('change',function(e,params){
				var tempName = $("#tempSelect").find("option:selected").text();
			    var tempId = params.selected; 
			    var html = '<option value="'+tempId+'">'+tempName+'</option>';
			    var i = 0;
			    //遍历列表检查是否已经存在该事项
			    $("#tempList option").each(function(e,params) {
					var id =$(this).val();
					if( id == itemId){
						html = "";
					}
					i++;
				});
			    //将事项放在数源列表里
			    $("#tempList").append(html);
			})
			
			/*将材料下拉框的全部内容加入到材料列表中*/	
			$("#addAllMaterials").on('click',function(){
				//遍历材料下拉列表
				$("#materialSelect option").each(function(e,params){
					if($(this).val() != ''){
						var html = '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
						var i = 0;
					    //遍历列表检查是否已经存在该事项
					    $("#tempList option").each(function(e,params) {
							var id =$(this).val();
							if( id == itemId){
								html = "";
							}
							i++;
						});
						//将事项放在数源列表里
				    	$("#materialList").append(html);
				    }
				});
			})
			/*从材料列表中移除单个材料*/
			$("#removeMaterial").on('click',function(){
				var rmMaterialId =  $("#materialList").get(0).selectedIndex;
				$("#materialList").find("option").get(rmMaterialId).remove() // i为index
			})
			/*清空材料列表*/
			$("#removeAllMaterials").on('click',function(){
				$("#materialList").empty();
			})
			/*从证明列表中移除单个证明*/
			$("#removeTemp").on('click',function(){
				var rmTempId =  $("#tempList").get(0).selectedIndex;
				$("#tempList").find("option").get(rmTempId).remove() // i为index
			})
			/*清空材料列表*/
			$("#removeAllTemps").on('click',function(){
				$("#tempList").empty();
			})
		</script>
	</body>
</html>