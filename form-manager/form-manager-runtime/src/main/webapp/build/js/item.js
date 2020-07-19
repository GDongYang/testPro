var cardUrl='itemAction!findPaeTable.action';
var addUrl='itemAction!save.action';
var editUrl='itemAction!update.action';
var deleteUrl='itemAction!remove.action';
var pageNum = 1;
var addFormValidator;
var treeSearchFlag=true;//判断是否需要模糊搜索
var situationParams = {};//情形
var formCodeParams = {};//表单getQrcode
var situations = [];
var paramsOne = {};//缺省情形
var oldParamsOne = {};//存缺省情形，用来判断用户是否修改过，是否需再次提交保存
var isAddSituation = 0;//是否是增加情形 如果是 1:是 0:不是
var isAddMaterial = 0;//是否是增加材料 如果是 1:是 0:不是
var executable = '';//是否只看一证通办
var userPower = '';
var oldHtml = '';
var departmentId = 0;    //部门Id号
var active = "";	//事项的发布状态
var repeat= ""; // 事项重复判断
var clickCount = 2;
var saveDept = false;
var needSync = false;//是否修改同步到其他相同的事项
var settings = {
	data: {
		simpleData: {
			enable: true,
			idKey: "oid",
			pIdKey: "poid",
			rootPId: null
		}
	},check: {
		enable: true,
		chkboxType : { "Y" : "", "N" : "" }
	},callback: {
		onClick: zTreeOnClick1,
		onExpand:zTreeOnClick1
	}
};
//分页
var pageSize = 11; pageNum = 1, pages = 1,newPages='';
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
$(document).ready(function(){
	getUserPower();
	var windowHeight=window.innerHeight;
	$(".base_box-area-aqua").css("height",windowHeight-103);
	$(".leftBox").css("height",windowHeight-40);
	$(".treeBox").css("height",windowHeight-136);
	$(".tableCont").css("height",windowHeight-40);
	$('.listBox').css('height',windowHeight-130);
	itemAjaxRequest();
	loadDepartment('treeDemo','treeDemo2');
	formValidator();
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
function getUserPower(){
	//获取当前用户的权限
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
				$("#syncBtn").show();
                $("#copyBtn").show();
			}
		}
	});
}
$("#departmentId").change(function(){
	findPositionAll($("#departmentId").val());
});
//按事项名称、编码和内部编码查询
function search(){
	pageNum = 1;
	itemAjaxRequest();
	$('#paginationUl').setLength(newPages);
}
function searchRepeat(){
	pageNum = 1;
	if(repeat == "3"){
		repeat = "";
		$("#repeat").css("background","#0173FF");
	}else{
		repeat = "3";
		$("#repeat").css("background","#27bb2a");
	}
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
//只看一证通办
$("#yztb").change(function(){
	var type = $("#yztb").val();
	active = "";
	executable =""; 
	if(type == 1){
		executable = 1;
	}else if(type == 2){
		active = 2;
	}else if(type == 0){
		executable = 0;
	}
	pageNum = 1;
	search();
})
//请求数据
function itemAjaxRequest() {
	var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
	+ '&pageSize=' + pageSize+'&departmentId='+selectCode+'&executable='+executable + '&active=' + active + '&repeat=' + repeat;	
	//console.log(dataStr);
	$.ajax({
		type : 'post',
		url : cardUrl,
		dataType : 'json',
		cache : false,
		async : true,
		data : dataStr,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			$('#addCard').siblings().remove();
			var datas=data.rows;
			var situationDatas = data.counts;
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
					 dataStr += getCardContent(datas[i],situationDatas[i]);					
				};
				$('#addCard').after(dataStr);
			}
		}
	});
}
//
//新增树点击回调函数
function treeCallBack2(treeName,treeId){
	$("#deptSearchId").val(treeName);
	$("#deptId").val(treeId);
	findPositionAll(treeId);
}
//部门列表树点击回调函数
function treeCallBack(treeName,treeId){
	itemAjaxRequest();
}

function treeCallBack3(treeName,treeId){
	//获取事项
	$("#sourceDeptName").val(treeName);
	$("#sourceDeptId").val(treeId);
	$("#itemSelectId").empty();
	oldHtml = "";	
    selectUpdated($("#itemSelectId"));
    departmentId = treeId;
    findItem(departmentId);									   //根据部门Id获取事项
    
}
//目的部门树点击
function treeCallBack4(treeName,treeId){
	$("#destDeptName").val(treeName);
	$("#destDeptId").val(treeId);
}
//岗位
function findPositionAll(deptId) {
	$("#positionId").empty();
	$.ajax({
		cache : false,
		type : "get",
		url : 'positionAction!findByDept.action',
		dataType : 'json',
		async : false,
		data : {
			searchDept : deptId
		},
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var htmlStr = "";
			if (data.result != null) {
				allPosit = data.result;
				htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					htmlStr += "<option value=\""+data.result[i].id+"\">"
							+ data.result[i].name + "</option>";
				}
			}
			$("#positionId").append(htmlStr);
			selectUpdated($("#positionId"));
			selectConfig();	//下拉框初始化
		}
	});
}
//新增
function addItem(){
	$("#positionId").val('');
	selectUpdated($("#positionId"));//下拉框变动更新
	findCertTemp();
	add();
	$("#deptSearchId").val('宁夏回族自治区');
	$('#deptId').val('26802');
	findPositionAll('1');
}
//修改
function updateItem(data){
	update(data);
	findPositionAll(data.departmentId);
	findCertTemp();
	//岗位回显
	findRPosition(data.id);
	$("#acc_statusRadios0").prop("checked", false);
	$("#acc_statusRadios1").prop("checked", false);
	$("#acc_statusRadios2").prop("checked", false);
	var radioId1 = "#acc_statusRadios" + data.active;
	$(radioId1).prop("checked", "checked");
	var radioId2 = "#acc_executable" + data.executable;
	$("#acc_executable0").prop("checked", false);
	$("#acc_executable1").prop("checked", false);
	$(radioId2).prop("checked", "checked");
}
//请求数据
function getCardContent(data,situationData) {
	var executableStr= '';
	if(data.executable == 0){
		executableStr = '否';
	}else if(data.executable == 1){
		executableStr = '是';
	}
	var dataStr = '<div class="detailItem">';
	if(userPower == 'edit'){
		dataStr +='<div class="base_line-height-30 base_margin-b-15"><h3 class="detailTitle">'+data.name+'</h3><div class="operationBtn">'
				+'<button type="button" class="btn btn-primary btn-sm base_margin-r-10" onclick="saveToCache('+JSON.stringify(data).replace(/\"/g,"'")+')"><i class="iconfont icon-baseline-save-px base_margin-r-5"></i>缓存</button>'
				+'<button type="button" class="btn btn-primary btn-sm base_margin-r-10" onclick="bindFormTemp('+JSON.stringify(data).replace(/\"/g,"'")+')"><i class="iconfont icon-gongxiang1 base_margin-r-5"></i>关联表单</button>'
				+'<button type="button" class="btn btn-primary btn-sm" onclick="setSituations('+JSON.stringify(data).replace(/\"/g,"'")+')"><i class="fa fa-newspaper-o base_margin-r-5"></i>情形</button>'
				+'<button type="button" class="btn btn-primary btn-sm base_margin-l-10" onclick="updateItem('+JSON.stringify(data).replace(/\"/g,"'")+')"><i class="fa fa-pencil base_margin-r-5"></i>修改</button>'
				+'<button type="button" class="btn btn-primary btn-sm base_margin-l-10" onclick="remove('+data.id+')"><i class="fa fa-trash-o base_margin-r-5"></i>删除</button>'
                +'<button type="button" class="btn btn-primary btn-sm base_margin-l-10" onclick="syncItem(\''+data.innerCode+'\')">同步</button>'
                +'<button type="button" class="btn btn-primary btn-sm base_margin-l-10" onclick="getQrcode(\''+data.innerCode+'\')">二维码</button></div></div>'
	}
   dataStr += '<div class="row itemContent"><div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 base_padding-l-0">事项编码：<span class="innerText" title="'+(data.code ? data.code : "无")+'">'+(data.code ? data.code : "无")+'</span></div>'
		+'<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 base_padding-l-0">内部编码：<span class="innerText" title="'+(data.innerCode ? data.innerCode : "无")+'">'+(data.innerCode ? data.innerCode : "无")+'</span></div>'
		//+'<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">一证通办：<span>'+executableStr+'</span></div>'
		+'<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 base_padding-l-0">单位名称：<span class="innerText" title="'+(data.departmentName ? data.departmentName : '无')+'">'+(data.departmentName ? data.departmentName : '无')+'</span></div></div>'
		+'<div><div class="totalInfo"><div class="situationTotal"><span>'+situationData.sCnt+'情形</span> <span>'+situationData.mCnt+'材料</span> <span>'+situationData.tCnt+'证明</span></div>'
		+'<div class="showDetial" onclick="showDetialInfo(this,'+JSON.stringify(situationData.itemId).replace(/\"/g,"'")+')"><span>详细信息</span> <i class="fa fa-angle-down"></i></div></div><div class="situationBox base_hidden">'
		+'</div></div></div>'
	return dataStr;
}
function getQrcode(itemInnerCode){
    $('#qrcodeCompany').empty()
    $('#qrcodePerson').empty()
    $('#qrcodeCompany').qrcode({
        render: "canvas", // 渲染方式有table方式和canvas方式
        width: 200,   //默认宽度
        height: 200, //默认高度
        typeNumber: -1,   //计算模式一般默认为-1
        correctLevel: 2, //二维码纠错级别
        background: "#ffffff",  //背景颜色
        foreground: "#000000",  //二维码颜色
        text: "http://esso.zjzwfw.gov.cn/opensso/spsaehandler/metaAlias/sp?spappurl=https://tzszlbzsbs.yyhj.zjzwfw.gov.cn/servlet/legalSso?goto=app:"+itemInnerCode
    });
    console.log("http://esso.zjzwfw.gov.cn/opensso/spsaehandler/metaAlias/sp?spappurl=https://tzszlbzsbs.yyhj.zjzwfw.gov.cn/servlet/legalSso?goto=app:"+itemInnerCode)
    $('#qrcodePerson').qrcode({
        render: "canvas", // 渲染方式有table方式和canvas方式
        width: 200,   //默认宽度
        height: 200, //默认高度
        typeNumber: -1,   //计算模式一般默认为-1
        correctLevel: 2, //二维码纠错级别
        background: "#ffffff",  //背景颜色
        foreground: "#000000",  //二维码颜色
        text: "https://puser.zjzwfw.gov.cn/sso/mobile.do?action=oauth&scope=1&servicecode=tzszsb&goto=app:"+itemInnerCode
    });
	formatDialog($("#qrcodeModal"),{title:"二维码",dialogClass:"mydialog"},{"取消": formatDialogClose });
}
//点击展示情形信息
function showDetialInfo(_this,itemId){
	if($(_this).parent().next().hasClass('base_hidden')){
		$(_this).parent().next().removeClass('base_hidden');
		$(_this).find($("i")).removeClass('fa-angle-down');
		$(_this).find($("i")).addClass('fa-angle-up');
		getSituationInfo(_this,itemId);
	}else{
		$(_this).parent().next().addClass('base_hidden');
		$(_this).find($("i")).removeClass('fa-angle-up');
		$(_this).find($("i")).addClass('fa-angle-down');
	}
	
}
//点击展示情形信息请求数据
function getSituationInfo(that,id){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'situationAction!getSituationInfomationByItemId.action',
		dataType : 'json',
		data : {
			itemId : id
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			//console.log(data)
			$(that).parent().next().empty();
			if(data.situations != []){
				var dataStr = '';
				for(var i=0;i<data.situations.length;i++){
					dataStr +='<div class="situtionShow"><div class="base_margin-b-5"><span class="situtionNum">'+(i+1)+'</span> <span class="fontWeight">情形'+(i+1)+'：'+data.situations[i].name+'</span></div>'
					if(data.situations[i].materialList != null){
						for(var j=0;j<data.situations[i].materialList.length;j++){
							if(data.situations[i].materialList[j].isMust == 1){
								dataStr +='<p class="mustMaterial">'+data.situations[i].materialList[j].name+''
							}else{
								dataStr +='<p>'+data.situations[i].materialList[j].name+''
							}
								dataStr += '(' + data.situations[i].materialList[j].code + ')'
							for(var k=0;k<data.situations[i].materialList[j].temps.length;k++){
								dataStr +='——<span>'+data.situations[i].materialList[j].temps[k].name +'(' + data.situations[i].materialList[j].temps[k].code +')</span>'
							}
							dataStr +='</p>'
						}
					}
					dataStr +='</div>'
				}
			}
			$(that).parent().next().append(dataStr);
		}
	})
}
function titleFormatter(value,row){
	return '<span title="'+value+'">'+value+'</span>'
}	
function getParams(params) {
	var pageSize = params.data.limit;
	var pageNum = params.data.offset / pageSize + 1;
	var findByActive = "";
	index = params.data.offset + 1;
	var sort = params.data.sort ? params.data.sort : "id";
	var order = params.data.order ? params.data.order : "desc";
	var active = $("#activeSelect").val();
	if(active != ""){
		findByActive = "1";
	}
	var data = {
		sort : sort,
		order : order,
		pageNum : pageNum,
		pageSize : pageSize,
		active : active,
		findByActive : findByActive,
	};
	if (selectCode != "") {
		data.departId = selectCode;
	}
	return data;
}
function formValidator(){
	$('#addForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '请输入事项名称！'
					}
				}
			},
			departmentName : {
				validators : {
					 notEmpty : {
						message : '请选择部门！'
					},
				}
			},
			active1 : {
				validators : {
					 notEmpty : {
						message : '请选择事项状态！'
					},
				}
			},
			executable : {
				validators : {
					 notEmpty : {
						message : '请选择一证通办状态！'
					},
				}
			}
		}
	})
}
//证件权限
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
				$("#certTempId").empty();
				$("#certTempId1").empty();
			if (data.result != null) {
				allTemp = data.result;
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					htmlStr += "<option value=\""+data.result[i].id+"\">"
							+ data.result[i].name + "</option>";
				}
				$("#certTempId").append(htmlStr);
				selectUpdated($("#certTempId"));
				$("#certTempId1").append(htmlStr);
				selectUpdated($("#certTempId1"));
			}
		}
	});
}
//根据id查询关联证件权限
function findRTemp(itemId,isMust){
	var result='';
	$.ajax({
		cache : false,
		type : "POST",
		url : 'itemAction!findRTemp.action',
		dataType : 'json',
		data : {
			id : itemId,
			isMust:isMust
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			result = data.result;
		}
	});
	return result;
}
//根据id查询关联岗位
function findRPosition(itemId){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'itemAction!findPositionId.action',
		dataType : 'json',
		data : {
			id : itemId
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			$("#positionId").val(data.result);
			selectUpdated($("#positionId"));//下拉框变动更新
		}
	});
}


//情形
function setSituations(data) {
	Modal.confirm({ msg:"此次修改是否要同步到其他相同事项", title:'提示', btnok:'是',btncl:'否' }).on(function(e){
		situationParams.itemId = data.id;
		selectConfig();	//下拉框初始化
	    findProves();	//获取证明    
		findSituations(data.id);
		needSync = e;
		getOldParam(data.id);
		formatDialog($("#situationFormWrap"),{title:"情形",dialogClass:"mydialog situationHeight"},{"取消": formatDialogClose , "提交": insertSituation});
	})
}
//情形确认
function insertSituation(){
	submitSituation(situationParams.itemId);
	situations = [];
	var situationArr =$('#situationGroup').children('.situation');
	for(var i=0;i<situationArr.length;i++){
		var situationObj = {};
		situationObj.id = $(situationArr[i]).find('input[type="hidden"]').attr('id');
		situationObj.name = $(situationArr[i]).find('.situationlData').val();
		situationObj.type = $(situationArr[i]).find('.situationType').val();
		situationObj.describe = $(situationArr[i]).find('.situationDescribe').val();
		var materialArr =$(situationArr[i]).find('.material');
		situationObj.materialList = [];
		for(var j=0;j<materialArr.length;j++){
			var materialObj = {};
			materialObj.name = $(materialArr[j]).find('.materialData option:selected').text();
			materialObj.code = $(materialArr[j]).find('.materialData option:selected').val();
			materialObj.isMust = 2;
			materialObj.needUpload =2;
			materialObj.type = $(materialArr[j]).find('.materialType option:selected').val();
			//console.log("check is " +$(materialArr[j]).find('.isMust').prop("checked"))
			if($(materialArr[j]).find('.isMust').prop("checked") == true){
				materialObj.isMust = 1;
			}
			if($(materialArr[j]).find('.needUpload').prop("checked") == true){
				materialObj.needUpload = 1;
			}
			materialObj.tempIds = [];
			var proveArr =$(materialArr[j]).find('.proveData');
			for(var k=0;k<proveArr.length;k++){
				materialObj.tempIds.push($(proveArr[k]).val());
			}
			situationObj.materialList.push(materialObj);
		}
	situations.push(situationObj);
	}
	situationParams.situations = situations;
	ajaxParams = {
		url:"situationAction!createBind.action",
		data:{"situationJson":JSON.stringify(situationParams.situations),"itemId":situationParams.itemId,"needSync":needSync},
		async : false
	};
	//console.log(ajaxParams);
	formatAjax(ajaxParams,tipsDialog);
	$(this).dialog("close");
}
//根据事项id获取情形
function findSituations(itemId){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'situationAction!getSituationInfomationByItemId.action',
		dataType : 'json',
		data : {
			itemId : itemId
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			 if(data.situations == '' || data.situations == null || data.situations == []){
				 $('#situationGroup').empty();
				 var htmlStr = '<div class="situation"><div class="form-group base_margin-t-15"><label class="col-sm-3 control-label">情形1</label>'
					 		+'<div class="col-sm-6"><input class="form-control situationlData" readOnly value="缺省情形"/></div></div><div class="materialGroup">'
					 		+'<div class="material"><div class="form-group"><label class="col-sm-3 control-label">材料1</label><div class="col-sm-6">'
					 		+'<input type="hidden"  id=""/><input class="form-control materialData"/></div>'
					 		+'<div class="col-sm-3" style="text-align:left"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="isMust" name="isMust"  value="1"/>必要</label><i class="fa fa-trash-o deleteIcon" onclick="deleteMaterial(this)"></i></div></div>'
					 		+'<div class="form-group"><label class="col-sm-3 control-label">材料类别</label><div class="col-sm-6">'
	 						+'<select class="form-control chosen-select-deselect materialType" data-placeholder="请选择">'
	 						+'<option value="1">证明</option><option value="2">不需要显示</option><option value="3">申请表</option>'
	 						+'</select></div><div class="col-sm-3" style="text-align:left"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="needUpload" name="needUpload"  value="1"/>上传</label></div></div>'
					 		+'<div class="proveGroup"><div class="form-group"><label class="col-sm-3 control-label">证明</label>'
					 		+'<div class="col-sm-6"><select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select>'
					 		+'</div><div class="col-sm-2"><span class="addDeleteIcon"><i class="fa fa-plus-circle base_margin-r-4" onclick="addProve(this)"></i>'
					 		+'<i class="fa fa-minus-circle base_margin-r-4" onclick="deleteProve(this)"></i></span><span class="moveIcon">'
					 		+'<i class="fa fa-chevron-circle-up base_margin-r-4" onclick="upProve(this)"></i><i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i>'
					 		+'</span></div></div></div></div></div>	<div class="addMaterial">'
					 		+'<span onclick="addMterial(this)"><i class="fa fa-plus-circle"></i>添加材料</span></div></div>'
			 		$('#situationGroup').append(htmlStr);
					selectConfig();	//下拉框初始化
					findProves();	//获取证明

			 }else{
				 $('#situationGroup').empty();
				 var htmlStr = '';
				 var situationNum = '';
				 for(var i=0;i<data.situations.length;i++){
					    situationNum = i+1;
					 	htmlStr +='<div class="situation"><div class="form-group base_margin-t-15"><label class="col-sm-3 control-label">情形'+situationNum+'</label>'
		 				if(data.situations[i].name == '缺省情形'){
					 		htmlStr +='<div class="col-sm-6"><input class="form-control situationlData" readOnly value="'+data.situations[i].name+'"/></div></div><div class="materialGroup">'
					    }else{
					    	htmlStr +='<div class="col-sm-6"><input type="hidden"  id="'+data.situations[i].id+'"/><input class="form-control situationlData" value="'+data.situations[i].name+'"/></div>'
					    		+'<div class="col-sm-3"><label class="col-sm-7 control-label base_padding-lr-0 ">类别<input type="number" class="situationType" name="type"  value="'+data.situations[i].type +'" min="0" /></label><i class="fa fa-trash-o deleteIcon" onclick="deleteSituation(this)"></i></div></div>'
								+'<div class="form-group base_margin-t-15"><label class="col-sm-3 control-label">描述</label>'
								+'<div class="col-sm-6"><input class="form-control situationDescribe" value="'+data.situations[i].describe+'"/></div></div>'
								+'<div class="materialGroup">'
						}
						if(data.situations[i].materialList != null){
			 				for(var j=0;j<data.situations[i].materialList.length;j++){
			 				htmlStr +='<div class="material base_margin-t-15"><div class="form-group"><label class="col-sm-3 control-label">材料'+(j+1)+'</label><div class="col-sm-6">'
			 				if(i == 0){
			 					htmlStr +='<input type="hidden"  id="'+data.situations[0].materialList[j].id+'"/><input class="form-control materialData" value="'+data.situations[0].materialList[j].name+'"/>';
							}else{
								htmlStr +='<select class="form-control chosen-select-deselect materialData" data-placeholder="请选择"></select>';
							}

			 				htmlStr +='</div><div class="col-sm-3"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="isMust" name="isMust"  value="1"/>必要</label><i class="fa fa-trash-o deleteIcon" onclick="deleteMaterial(this)"></i></div></div>'
			 						+'<div class="form-group"><label class="col-sm-3 control-label">材料类别</label><div class="col-sm-6">'
			 						+'<select class="form-control chosen-select-deselect materialType" data-placeholder="请选择">'
			 						+'<option value="1">证明</option><option value="2">不需要显示</option><option value="3">申请表</option>'
			 						+'</select></div><div class="col-sm-3" style="text-align:left"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="needUpload" name="needUpload"  value="1"/>上传</label></div></div>'
			 						+'<div class="proveGroup">'
			 				
			 				for(var k=0;k<data.situations[i].materialList[j].tempIds.length;k++){
			 					htmlStr +='<div class="form-group"><label class="col-sm-3 control-label">证明</label><div class="col-sm-6">'
			 							+'<select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select></div>'
		 								+'<div class="col-sm-2"> <span class="addDeleteIcon"><i class="fa fa-plus-circle base_margin-r-4" onclick="addProve(this)"></i>'
		 								+'<i class="fa fa-minus-circle base_margin-r-4" onclick="deleteProve(this)"></i></span><span class="moveIcon">'
										+'<i class="fa fa-chevron-circle-up base_margin-r-4" onclick="upProve(this)"></i><i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i>'
										+'</span></div></div>'
			 				}
			 				if(data.situations[i].materialList[j].tempIds.length == 0){
			 					htmlStr +='<div class="form-group"><label class="col-sm-3 control-label">证明</label><div class="col-sm-6">'
		 							+'<select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select></div>'
	 								+'<div class="col-sm-2"> <span class="addDeleteIcon"><i class="fa fa-plus-circle base_margin-r-4" onclick="addProve(this)"></i>'
	 								+'<i class="fa fa-minus-circle base_margin-r-4" onclick="deleteProve(this)"></i></span><span class="moveIcon">'
									+'<i class="fa fa-chevron-circle-up base_margin-r-4" onclick="upProve(this)"></i><i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i>'
									+'</span></div></div>'
			 				}
			 				htmlStr +='</div></div>'
						 }	
						}
					 	htmlStr +='</div><div class="addMaterial"><span onclick="addMterial(this)"><i class="fa fa-plus-circle"></i>添加材料</span></div></div>';					
					}
				 	$('#situationGroup').append(htmlStr);
				 	selectConfig();//下拉框初始化
				 	//获取材料
					findMaterials(situationParams.itemId);
					//获取证明
					findProves();
					for(var i=0;i<data.situations.length;i++){
						if(data.situations[i].materialList == null){
							continue;
						}
						if(data.situations[i].type != 0){
							$('.situation').eq(i).find($(".situationType")).val(data.situations[i].type);
						}
						for(var j=0;j<data.situations[i].materialList.length;j++){
							for(var k=0;k<data.situations[i].materialList[j].tempIds.length;k++){
								$('.situation').eq(i).find($('.proveGroup')).eq(j).find($('.proveData')).eq(k).val(data.situations[i].materialList[j].tempIds[k]);
				 				selectUpdated($('.situation').eq(i).find($('.proveGroup')).eq(j).find($('.proveData').eq(k)));//下拉框变动更新
							}
							if(i == 0){
								$('.materialGroup').eq(i).find($('.materialData')).eq(j).val(data.situations[i].materialList[j].name);
							}else{
								$('.materialGroup').eq(i).find($('.materialData')).eq(j).val(data.situations[i].materialList[j].code);
							}
							if(data.situations[i].materialList[j].isMust == 1){
								$('.materialGroup').eq(i).find($('.isMust')).eq(j).prop("checked",true);
							}
							if(data.situations[i].materialList[j].needUpload == 1){
								$('.materialGroup').eq(i).find($('.needUpload')).eq(j).prop("checked",true);
							}
							$('.materialGroup').eq(i).find($('.materialType')).eq(j).val(data.situations[i].materialList[j].type)
			 				selectUpdated($('.materialGroup').eq(i).find($('.materialData')).eq(j));//下拉框变动更新
			 				selectUpdated($('.materialGroup').eq(i).find($('.materialType')).eq(j));//下拉框变动更新
						}
					}
			 }			
		}
	});
}
//根据事项id获取材料
function findMaterials(itemId){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'materialAction!findDefaultSituationMaterials.action',
		dataType : 'json',
		data : {
			itemId : itemId
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			//console.log(data);
			if (data.materials != null) {
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.materials.length; i++) {
					htmlStr += "<option value=\""+data.materials[i].code+"\">"
							+ data.materials[i].name + "</option>";
				}
				$(".materialData:empty").append(htmlStr);
				selectUpdated($(".materialData:empty"));//下拉框变动更新
			}
		}
	});
}
//删除材料
function deleteMaterial(_this){
	var tipStr = $(_this).parent().parent().parent().parent().prev().find($('.situationlData')).val();
	if(tipStr == '缺省情形'){		
		formatDialog($("#deleteTipWrap"),{title:"提示",dialogClass:"mydialog base_width-300"},{"取消": formatDialogClose , "确定": function(){
			$(this).dialog("close");;
			$(_this).parent().parent().parent().remove();
		}});
	}else{
		$(_this).parent().parent().parent().remove();
	}
}
//添加材料
function addMterial(_this){
	var materialArr =$(_this).parent().prev().children('.material');
	var materialNum = materialArr.length+1;
	isAddMaterial = 1;
	var htmlStr = '<div class="material base_margin-t-15"><div class="form-group"><label class="col-sm-3 control-label">材料'+materialNum+'</label><div class="col-sm-6">'
	if($(_this).parent().parent().find('.situationlData').val() == '缺省情形'){
		htmlStr +='<input type="hidden" id=""/><input class="form-control materialData"/></div>'
				+'<div class="col-sm-3"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="isMust" name="isMust" value="1"/>必要</label><i class="fa fa-trash-o deleteIcon" onclick="deleteMaterial(this)"></i></div></div>';
	}else{
		htmlStr +='<select class="form-control chosen-select-deselect materialData" data-placeholder="请选择"></select></div>'
		+'<div class="col-sm-3"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="isMust" name="isMust" value="1" checked=true/>必要</label><i class="fa fa-trash-o deleteIcon" onclick="deleteMaterial(this)"></i></div></div>';
	}
	htmlStr +='<div class="base_margin-t-15"><div class="form-group"><label class="col-sm-3 control-label">材料类别</label><div class="col-sm-6">'
			+'<select class="form-control chosen-select-deselect materialType" data-placeholder="请选择">'
			+'<option value="1">证明</option><option value="2">不需要显示</option><option value="3">申请表</option>'
			+'</select></div><div class="col-sm-3" style="text-align:left"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="needUpload" name="needUpload"  value="1"/>上传</label></div></div>'
	htmlStr +='<div class="proveGroup"><div class="form-group"><label class="col-sm-3 control-label">证明</label><div class="col-sm-6">'
		+'<select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select>'
		+'</div><div class="col-sm-2"><span class="addDeleteIcon"><i class="fa fa-plus-circle base_margin-r-4" onclick="addProve(this)"></i>'
		+'<i class="fa fa-minus-circle base_margin-r-4" onclick="deleteProve(this)"></i></span><span class="moveIcon">'
		+'<i class="fa fa-chevron-circle-up base_margin-r-4" onclick="upProve(this)"></i><i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i></span></div></div></div></div>';	
	$(_this).parent().prev().append(htmlStr);
	selectConfig();//下拉框初始化
	findMaterials(situationParams.itemId);
	//获取证明
	findProves();
	isAddMaterial = 0;
}
//添加情形
function addSituation(){
	//提交缺省情形
	submitSituation(situationParams.itemId);
	isAddSituation = 1;
	var situationArr =$('#situationGroup').children('.situation');
	var situationNum = situationArr.length+1;	
	var htmlStr = '<div class="situation"><div class="form-group base_margin-t-15"><label class="col-sm-3 control-label">情形'+situationNum+'</label>'
			+'<div class="col-sm-6"><input class="form-control situationlData"/></div><div class="col-sm-3"><label class="col-sm-7 control-label base_padding-lr-0 ">类别<input type="number" class="situationType" name="type"  value="0" min="0" /></label><i class="fa fa-trash-o deleteIcon" onclick="deleteSituation(this)"></i></div></div>'
			+'<div class="form-group base_margin-t-15"><label class="col-sm-3 control-label">描述</label>'
			+'<div class="col-sm-6"><input class="form-control situationDescribe" value=""/></div></div>'
			+'<div calss="materialGroup"><div class="material"><div class="form-group"><label class="col-sm-3 control-label">材料1</label>'
			+'<div class="col-sm-6"><select class="form-control chosen-select-deselect materialData" data-placeholder="请选择"></select>'
			+'</div><div class="col-sm-3"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="isMust" name="isMust" value="1" checked=true/>必要</label><i class="fa fa-trash-o deleteIcon" onclick="deleteMaterial(this)"></i></div>'
			+'</div><div class="base_margin-t-15"><div class="form-group"><label class="col-sm-3 control-label">材料类别</label><div class="col-sm-6">'
			+'<select class="form-control chosen-select-deselect materialType" data-placeholder="请选择">'
			+'<option value="1">证明</option><option value="2">不需要显示</option><option value="3">申请表</option>'
			+'</select></div><div class="col-sm-3" style="text-align:left"><label class="col-sm-6 control-label base_padding-lr-0 "><input type="checkbox" class="needUpload" name="needUpload"  value="1"/>上传</label></div></div>'
			+'</div><div class="proveGroup"><div class="form-group"><label class="col-sm-3 control-label">证明</label><div class="col-sm-6">'
			+'<select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select>'
			+'</div><div class="col-sm-2"><span class="addDeleteIcon"><i class="fa fa-plus-circle base_margin-r-4" onclick="addProve(this)"></i>'
			+'<i class="fa fa-minus-circle base_margin-r-4" onclick="deleteProve(this)"></i></span><span class="moveIcon"><i class="fa fa-chevron-circle-up base_margin-r-4" onclick="upProve(this)"></i>'
			+'<i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i></span></div></div></div></div></div><div class="addMaterial">'
			+'<span onclick="addMterial(this)"><i class="fa fa-plus-circle"></i>添加材料</span></div></div>'
    $('#situationGroup').append(htmlStr);
	selectConfig();//下拉框初始化
	//获取材料
	findMaterials(situationParams.itemId);
	//获取证明
	findProves();
	isAddSituation = 0;
}
//删除情形
function deleteSituation(_this){
	$(_this).parent().parent().parent().remove();
}


//提交缺省情形
function submitSituation(itemId){
		var situationObj = {};
		situationObj.itemId = itemId;
		var situationData = $('.situation:first');
		situationObj.name = situationData.find('.situationlData').val();
		var materialArr = situationData.find('.material');
		situationObj.materialList = [];
		for(var j=0;j<materialArr.length;j++){
			var materialObj = {};
			materialObj.id = $(materialArr[j]).find('input[type="hidden"]').attr('id');
			materialObj.name = $(materialArr[j]).find('.materialData').val();
			var isMust = 2;
			if($(materialArr[j]).find('.isMust').prop("checked") == true){
				isMust = 1;
			}
			materialObj.isMust = isMust;
			var needUpload = 2;
			if($(materialArr[j]).find('.needUpload').prop("checked") == true){
				needUpload = 1;
			}
			materialObj.needUpload = needUpload;
			materialObj.type = $(materialArr[j]).find('.materialType option:selected').val();
			if($(materialArr[j]).find('.isMust').prop("checked") == true){
				materialObj.isMust = 1;
			}
			if($(materialArr[j]).find('.needUpload').prop("checked") == true){
				materialObj.needUpload = 1;
			}
			materialObj.tempIds = [];
			var proveArr =$(materialArr[j]).find('.proveData');
			for(var k=0;k<proveArr.length;k++){
				materialObj.tempIds.push($(proveArr[k]).val());
			}
			situationObj.materialList.push(materialObj);
		}
	paramsOne.situation = situationObj;
	if(JSON.stringify(paramsOne) == oldParamsOne){
		return;
	}
	$.ajax({ 
		cache : false,
		type : "POST",
		url : 'situationAction!saveDefaultSituation.action',
		dataType : 'json',
        data : {"defaultSituationJson":JSON.stringify(paramsOne.situation),"needSync":needSync},
        //data : situationObj ,
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			if(data != '' && data.result != '' && data.result != null){
				for(var i=0;i<data.result.length;i++){
					 $('.situation:first').find('input[type="hidden"]').eq(i).attr('id',data.result[i]);
				}	
			}
		}
	});
	oldParamsOne = JSON.stringify(paramsOne);
}
//获取证明
function findProves(){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'certTempAction!findAllActive.action',
		dataType : 'json',
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			//console.log(data);
			if (data.result != null) {
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					var catalogDeptName = data.result[i].catalogDeptName == null?  "无": data.result[i].catalogDeptName;
					htmlStr += "<option value=\""+data.result[i].id+"\">"
							+ data.result[i].name +"(" + catalogDeptName + ")</option>";
				}
				$(".proveData:empty").append(htmlStr);
				selectUpdated($(".proveData:empty"));//下拉框变动更新
			}
		}
	});
}
//添加证明
function addProve(_this){
	var htmlStr= '<div class="form-group"><label class="col-sm-3 control-label">证明</label><div class="col-sm-6">'
			+'<select class="form-control chosen-select-deselect proveData" data-placeholder="请选择"></select></div>'
			+'<div class="col-sm-2"><span class="addDeleteIcon"><i class="fa fa-plus-circle base_margin-r-4" onclick="addProve(this)"></i>'
			+'<i class="fa fa-minus-circle base_margin-r-4" onclick="deleteProve(this)"></i></span><span class="moveIcon">'
			+'<i class="fa fa-chevron-circle-up base_margin-r-4" onclick="upProve(this)"></i><i class="fa fa-chevron-circle-down" onclick="downProve(this)"></i></span></div></div>'
	$(_this).parent().parent().parent().parent().append(htmlStr);
	selectConfig();//下拉框初始化
	findProves();//获取
}
//删除证明
function deleteProve(_this){
	$(_this).parent().parent().parent().remove();
}
//上移证明
function upProve(_this){
	if($(_this).parent().parent().parent().prev().length == 0){
		return;
	}
	$(_this).parent().parent().parent().prev().before($(_this).parent().parent().parent());
}
//下移证明
function downProve(_this){
	if($(_this).parent().parent().parent().next().length == 0){
		return;
	}
	$(_this).parent().parent().parent().next().after($(_this).parent().parent().parent());
}

function bindFormTemp(data){
	//关联表单
	formCodeParams.id = data.id;
	formCodeParams.formCode = data.formCode;
	selectConfig();	//下拉框初始化
    findFormTemp();	//获取表单
	formatDialog($("#formTempFormWrap"),{title:"表单",dialogClass:"mydialog formTempHeight"},{"取消": formatDialogClose , "提交": bindFormTempToItem });
}

function bindFormTempToItem(){
	var formCode = $(".formTempData").val();
	// $.ajax({ 
	// 	cache : false,
	// 	type : "POST",
	// 	url : 'itemAction!bindFormTemp.action',
	// 	dataType : 'json',
	// 	data : {"id":formCodeParams.id,
	// 			"formCode":formCode},
    //     //data : situationObj ,
	// 	async : true,
	// 	error : function(request, textStatus,errorThrown) {
	// 	},
	// 	success : function(data) {
			
	// 	}
	// });
	ajaxParams = {
		url:"itemAction!bindFormTemp.action",
		data:{"id":formCodeParams.id,
	 			"formCode":formCode},
		async : false
	};
	formatAjax(ajaxParams,tipsDialog);
	$(this).dialog("close");
	//itemAjaxRequest();
	//search();

}

function tipsDialog(datas){
    if(datas){
        Modal.alert({ msg:datas.msg, title:'提示', btnok:'确定' }).on(function(e){
            itemAjaxRequest();
        });
    }
}
function findFormTemp(){
$.ajax({
		cache : false,
		type : "POST",
		url : 'formPageAction!findList.action',
		dataType : 'json',
		data:{"active":true,"isActive":1},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			//console.log(data);
            $(".formTempData").empty();
			if (data.result != null) {
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					htmlStr += "<option value=\""+data.result[i].code+"\">"
							+ data.result[i].name + "</option>";
				}
				$(".formTempData").append(htmlStr);
				selectUpdated($(".formTempData"));//下拉框变动更新
			}
				$(".formTempData").val(formCodeParams.formCode);
				selectUpdated($(".formTempData"));//下拉框变动更新
		}
	});
}

//将当前事项刷新到缓存中
function saveToCache(data){
	ajaxParams = {
		url:"itemAction!saveToCache.action",
		data:{"id":data.id},
		async : false
	};
	formatAjax(ajaxParams,tipsDialog);
}
//复制事项
function copyItem(){
	//复制事项
	loadDepartment('treeDemo3');
	if(!saveDept){
		loadDepartment1();
	}
	selectConfig();	//下拉框初始化
	formatDialog($("#copyItemWrap"),{title:"复制事项",dialogClass:"mycopydialog situationHeight"},{"取消": formatDialogClose , "提交": copyItems2OthereDept});
}
function loadDepartment1(){
	
	var data= {
		id: "-1"
	};
	$.ajax({
	   	url:"departmentAction!findTreeByParentId.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:true,
	   	data:data,
	  	error:function(request,textStatus, errorThrown){
	  		fxShowAjaxError(request, textStatus, errorThrown);
		},
	   	success:function(data){
	   		if(data && data.departments){
				$(data.departments).each(function(i, item) {
					deptObj[item.id] = item.name;
					item.isParent = true;
				});
				$.fn.zTree.init($("#treeDemo4"), settings, data.departments);
	   		} else {
	   			var list= [];
				$.fn.zTree.init($("#treeDemo4"), settings, list);
	   		}
		}
	});
}
//树节点点击事件
function zTreeOnClick1(event, treeId, treeNode) {
	if (treeNode.id == 0) {
		selectCode = "";
		$('#departmentId').val('');
	} else {
		var id =treeNode.id;
		selectName=treeNode.name;
		var data= {
			id:id
		}
		if((treeNode.isParent == true && typeof(treeNode.children) =="undefined" )){
			$.ajax({
			   	url:"departmentAction!findTreeByParentId.action",
			   	type:"POST",
			   	dataType:"json",
			   	async:false,
			   	data:data,
			  	error:function(request,textStatus, errorThrown){
			  		fxShowAjaxError(request, textStatus, errorThrown);
				},
			   	success:function(data){
			   		if(data && data.departments){
						$(data.departments).each(function(i, item) {
							deptObj[item.id] = item.name;
							if(item.isLeaf == "0"){
								item.isParent=true;
							}
							var deptName = $("#deptFilter").val()
							if(deptName.replace(" ","") != ""){
								if(item.name.indexOf(deptName) != -1){
									item.checked=true;
								}
							}
						});
	                    $.fn.zTree.getZTreeObj("treeDemo4").addNodes(treeNode,eval(data.departments));
	                    
	                    if($("#deptOpt").prop("checked") == true){//开启部门优化
		                    if(treeNode.parentTId == 'treeDemo4_3'){
		                    	$(".level3").trigger("click")
		                    }
	                    }
			   		} 
				}
			});
		}
	}
	//上一个请求在未返回前，直接终止掉
	if (lastAjax != null){
		lastAjax.abort();
	}
}
function copyItems2OthereDept(){
	var itemIds = [];
	var deptIds = [];
	var deptNames = [];
	var i = 0;
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo4");
	var checkedNodes = treeObj.getCheckedNodes(true);
	var len = checkedNodes.length;
	var html = "";
	var jobDeptId = $("#deptSelect").val();//具体的部门
	var jobLevel = $("#levelSelect").val();//部门的级别：市级/省级
	var jobStr = $("#jobSelect").val();//部门的职责
	for (var ii = 0; ii < len; ii++) {
		var treeNode = checkedNodes[ii];
		var fullDepartment = "";
		var allTreeNode = treeNode.getPath();
		deptIds.push(treeNode.id)
		if(allTreeNode.length <= 2){
			for(var j = 0;j < allTreeNode.length;j++){
				fullDepartment += allTreeNode[j].name + " / " ;
			}
		}else{
			for(var j = 2;j < allTreeNode.length;j++){
				fullDepartment += allTreeNode[j].name + " / " ;
			}
		}
		fullDepartment = fullDepartment.substr(0,fullDepartment.lastIndexOf(' /'));
		deptNames.push(fullDepartment)
	}
	//遍历select 获取itemIds
	$("#select option").each(function() {
		itemIds[i]=$(this).val();
		//console.log("item id = " + itemIds[i]);
		i++;
	});
	i = 0;
	/*$("#select2 option").each(function() {
		deptIds[i]=$(this).val();
		deptNames[i]=$(this).text();
		//console.log("deptId = " + deptIds[i] + " deptName = " + deptNames[i]);
		i++;
	});*/
	if(itemIds.length!=0 && deptIds.length!=0){
		$.ajax({
				type : 'post',
				url : 'itemAction!copyItem.action',
				dataType : 'json',
				traditional :true, 
				data : {
					"itemIds" : itemIds,
					"deptIds" : deptIds,
					"deptNames" : deptNames,
					'jobDeptId':jobDeptId,
					'jobLevel':jobLevel,
					'jobStr':jobStr
				},
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					$("#copyItemWrap").dialog("close");
					Modal.confirm({ msg:"复制成功!是否保留部门选项", title:'提示', btnok:'确定',btncl:'关闭' }).on(function(e){
						saveDept = e;
						search();
					});
				}
		});
	}else{
		alert("数源事项或目的部门为空，请重试！")
	}
}
function searchItem2(){
	$("#itemSelectId").empty();
	oldHtml = "";	
    selectUpdated($("#itemSelectId"));
    findItem($("#sourceDeptId").val());	
}
function findItem(id) {
    var name  = $("#itemName2").val();
	$.ajax({
		cache : false,
		type : "post",
		url : 'itemAction!findPaeTable.action',
		dataType : 'json',
		data: {'departmentId': id,'pageNum':1,'pageSize':200,name:name},
		async : false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
                $("#itemSelectId").empty();
			if (data.rows != null) {
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.rows.length; i++) {
					htmlStr += "<option value=\""+data.rows[i].id+"\">"
							+ data.rows[i].name + "(" + data.rows[i].departmentName + ")" + "</option>";
				}
				selectConfig();//下拉框初始化
				oldHtml = htmlStr + oldHtml;
				$("#itemSelectId").append(oldHtml);
				selectUpdated($("#itemSelectId"));
			}
		}
	});
}//chosen查询找不到结果触发
/*$('.chosen-select-deselect').on('chosen:no_results',function(e,params){
	//只有当包含中文的时候才进行调接口查询减少开销
	var reg = /[\u4e00-\u9fa5]/g;
	if(reg.test($(".chosen-search-input").val())){
		var chosenSelectVal = $(".chosen-search-input").val();
		console.log("值 为" + chosenSelectVal);
		findItem(departmentId);
	}
});	*/
 $('#itemSelectId').on('change',function(e,params){
 	var itemName = $("#itemSelectId").find("option:selected").text();
    var itemId = params.selected; 
    var html = '<option value="'+itemId+'">'+itemName+'</option>';
    var i = 0;
    //遍历列表检查是否已经存在该事项
    $("#select option").each(function() {
		var id =$(this).val();
		if( id == itemId){
			html = "";
			alert('请勿重复添加事项!');
		}
		i++;
	});
    //将事项放在数源列表里
    $("#select").append(html);
})

//
$("#addItem").click(function(){
	//获取事项框里的值
	//var html = "<option value="'+.id+'">'+role.name+'</option>"
})
//移除事项列表的单个事项
$("#removeItem").click(function(){
	//获取事项列表框中选中的
	var rmItemId =  $("#select").get(0).selectedIndex;
	$("#select").find("option").get(rmItemId).remove() // i为index
	
})
//移除事项列表的所有事项
$("#removeAllItem").click(function(){
	//获取事项列表框中选中的
	$("#select").empty();
})

//筛选部门列表
function filterDept(){
	//遍历部门树
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo4");
	var allNodes = treeObj.getNodes();
	var treeNodes = treeObj.transformToArray(allNodes);
	var len = treeNodes.length
	for(var i = 0; i < len;i++){
		var treeNode = treeObj.getNodeByTId(treeNodes[i].tId)
		treeNode.checked=false;
		var deptName = $("#deptFilter").val();
		if(deptName.replace(" ","") != ""){
			if(treeNode.name.indexOf(deptName)!=-1)
				treeNode.checked = true;
		}
		treeObj.updateNode(treeNode)
	}
}

//将部门放到目的部门列表
$("#addDestDept").click(function(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo4");
	var checkedNodes = treeObj.getCheckedNodes(true);
	var len = checkedNodes.length;
	var html = "";
	for (var ii = 0; ii < len; ii++) {
		var treeNode = checkedNodes[ii];
		var fullDepartment = "";
		var allTreeNode = treeNode.getPath();
		if(allTreeNode.length <= 2){
			for(var i = 0;i < allTreeNode.length;i++){
				fullDepartment += allTreeNode[i].name + " / " ;
			}
		}else{
			for(var i = 2;i < allTreeNode.length;i++){
				fullDepartment += allTreeNode[i].name + " / " ;
			}
		}
		
		fullDepartment = fullDepartment.substr(0,fullDepartment.lastIndexOf(' /'));
		var select = '<option value="'+treeNode.id+'">'+fullDepartment+'</option>';
		//遍历列表检查是否已经存在该事项
    	$("#select2 option").each(function() {
			var id =$(this).val();
			if( id == treeNode.id){
				select = "";
			}
		});
		html += select;
	}
	$("#select2").append(html);
})
//把部门移除目的部门列表
$("#removeDestDept").click(function(){
	var rmDept =  $("#select2").get(0).selectedIndex;
	$("#select2").find("option").get(rmDept).remove() // i为index
})
/*点情形时初始化OldParam*/
function getOldParam(itemId){
		var situationObj = {};
		situationObj.itemId = itemId;
		var situationData = $('.situation:first');
		situationObj.name = situationData.find('.situationlData').val();
		var materialArr = situationData.find('.material');
		situationObj.materialList = [];
		for(var j=0;j<materialArr.length;j++){
			var materialObj = {};
			materialObj.id = $(materialArr[j]).find('input[type="hidden"]').attr('id');
			materialObj.name = $(materialArr[j]).find('.materialData').val();
			var isMust = 2;
			if($(materialArr[j]).find('.isMust').prop("checked") == true){
				isMust = 1;
			}
			materialObj.isMust = isMust;
			var needUpload = 2;
			if($(materialArr[j]).find('.needUpload').prop("checked") == true){
				needUpload = 1;
			}
			materialObj.needUpload = needUpload;
			materialObj.type = $(materialArr[j]).find('.materialType option:selected').val();
			if($(materialArr[j]).find('.isMust').prop("checked") == true){
				materialObj.isMust = 1;
			}
			if($(materialArr[j]).find('.needUpload').prop("checked") == true){
				materialObj.needUpload = 1;
			}
			materialObj.tempIds = [];
			var proveArr =$(materialArr[j]).find('.proveData');
			for(var k=0;k<proveArr.length;k++){
				materialObj.tempIds.push($(proveArr[k]).val());
			}
			situationObj.materialList.push(materialObj);
		}
	paramsOne.situation = situationObj;
	oldParamsOne = JSON.stringify(paramsOne);
}

$("#levelSelect").on('change',function(){
	getDeptListByLevel();
})
/*根据选择级别 获取部门*/
function getDeptListByLevel(){
	var level = $("#levelSelect").val();//获取选择级别
	$.ajax({
	   	url:"departmentAction!findList.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:false,
	   	data:{"parentId":level},
	  	error:function(request,textStatus, errorThrown){
	  		fxShowAjaxError(request, textStatus, errorThrown);
		},
	   	success:function(data){
	   		$("#deptSelect").empty();
	   		/*填充部门下拉列表*/
			var htmlStr = "<option value=\"\"></option>";
			for (var i = 0; i < data.departments.length; i++) {
				htmlStr += "<option value=\""+data.departments[i].id+"\">"
						+ data.departments[i].name + "</option>";
			}
			$("#deptSelect").append(htmlStr);
		}
	});
}

//同步单个事项
function syncItem(innerCode) {
    Modal.confirm({
        msg:"是否同步权利事项库？"
    }).on( function (e) {
        if(e) {
            $.ajax({
                cache : false,
                type : "get",
                url : 'itemAction!synchroByInnerCodeAndTongTime.action',
                dataType : 'json',
                data:{'innerCode' : innerCode},
                async : false,
                error : function(request, textStatus, errorThrown) {
                    fxShowAjaxError(request, textStatus, errorThrown);
                },
                success : function(data) {
                    if(data != null && data.code == 1) {
                        alert("操作成功");
                    } else {
                        alert("操作失败");
                    }
                }
            });
        }
    });
}

//同步所有事项
function syncAll() {
    console.log(selectCode);
    if(selectCode == ""){
        Modal.alert({ msg:'请选择部门！', title:'提示', btnok:'确定' });
        return;
    }
    Modal.confirm({
        msg:"是否同步权利事项库？"
    }).on( function (e) {
        if(e) {
            $.ajax({
                cache : false,
                type : "get",
                url : 'itemAction!syncAllItem.action',
                dataType : 'json',
                data: {'departmentId': selectCode},
                async : false,
                error : function(request, textStatus, errorThrown) {
                    fxShowAjaxError(request, textStatus, errorThrown);
                },
                success : function(data) {
                    if(data != null && data.code == 1) {
                        alert("操作成功");
                    } else {
                        alert("操作失败");
                    }
                }
            });
        }
    });
}

