var url='../certTempAction!findPage.action';
var addUrl='../certTempAction!save.action';
var editUrl='../certTempAction!update.action';
var deleteUrl='certTempAction!removeTemplate.action';
var pageNum = 1;
var sealList = [];    //印章信息列表
var addFormValidator;
var treeSearchFlag=true;//判断是否需要模糊搜索
var draftData = '';//草稿数据
var currentData = '';//当前版本数据
var userPower = '';//用户的权限
var saveDept = false;//是否保存复制的目的部门
var oldHtml = ""//存储旧的HTML
//上传图片初始化
var obj ={};
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
$(document).ready(function(){
	getUserPower();
	loadDepartment('treeDemo','treeDemo2');
	formValidator();
	loadSealInfo(); //加载公章信息
	selectConfig();//下拉框初始化	
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
$("#departmentId").change(function(){
	loadPosition($("#departmentId").val(),null);
});
$("#signMethodSelect").change(function(){
	var choose = $("#signMethodSelect").val();//获取是关键字还是坐标
	if(choose != ""){
		if( 1 == choose){//若选取的是关键字则显示关键字输入框反之显示坐标输入框
			$("#keyWordDiv").removeClass('base_hidden');
			if(!$("#signxDiv").hasClass('base_hidden')){
				$("#signxDiv").addClass('base_hidden');
			}
			if(!$("#signyDiv").hasClass('base_hidden')){
				$("#signyDiv").addClass('base_hidden');
			}
		}else{
			$("#signxDiv").removeClass('base_hidden');
			$("#signyDiv").removeClass('base_hidden');
			if(!$("#keyWordDiv").hasClass('base_hidden')){
				$("#keyWordDiv").addClass('base_hidden');
			}
		}
	}else{
		hideKeywordAndSign();
	}
});

function getUserPower(){
	//获取当前用户的权限
	$.ajax({
		cache : false,
		type : "post",
		url : 'userAction!findUserPower.action',//根据部门获取印章
		dataType : 'json',
		async : false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			//console.log("data is " + JSON.stringify(data));
			userPower = data.power;
			if(userPower == 'edit'){
				$(".tableBtns").show();
			}
			
		}
	});
}
function hideKeywordAndSign(){//隐藏 关键字和坐标的输入框并将select默认赋为"请选择"
	//$(" select option[value='1']").attr("selected","selected");
	$("#signMethodSelect option:first").prop("selected", 'selected');
	if(!$("#signxDiv").hasClass('base_hidden')){
		$("#signxDiv").addClass('base_hidden');
		$("#signx").val("");
	}
	if(!$("#signyDiv").hasClass('base_hidden')){
		$("#signyDiv").addClass('base_hidden');
		$("#signy").val("");
	}
}
//树点击回调函数
function treeCallBack(treeName,treeId){
	$("#tablewrap").bootstrapTable('selectPage',1);
	findSealInfo(treeId);
}
function search(){
	//$("#tablewrap").bootstrapTable('refresh');
	$("#tablewrap").bootstrapTable('selectPage',1);
}
function cleanSearch(){
	$("#searchName").val("");
	$("#searchType").val(0);
}
//查找当前部门下的印章信息
function findSealInfo(departmentId){
	//获取当前部门的印章加入到印章来源中
	$.ajax({
		cache : false,
		type : "post",
		url : 'sealInfoAction!findPagination.action',//根据部门获取印章
		dataType : 'json',
		async : false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			if (data.rows != null) { //获取到印章
				$("#sealId").empty();
				allTemp = data.result;
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.rows.length; i++) {
					htmlStr += "<option value=\""+data.rows[i].code+"\">"
							+ data.rows[i].name + "</option>";
				}
				$("#sealId").append(htmlStr);
				selectUpdated($("#sealId"));
			}
		}
	});
}
//新增
function addTable(){
	hideKeywordAndSign();
	if(selectCode){
		$("#imgDiv").css('display','none')
		add();
		$('#deptName').val(selectName);
		$('#org').val(selectCode);
	}else{
		Modal.alert({ msg:'请选择部门！', title:'提示', btnok:'确定' });
	}
}
//修改
function updateTable(){
	$("#imgDiv").css('display','block');
	$("#imgMsg").css('display','none');
	hideKeywordAndSign();
	var info = $("#tablewrap").bootstrapTable("getSelections");
	if (info != 0) {
		currentData = info[0];
		if(currentData.active == 1){
			draftData = findHighVersion(info[0].id);
			if(draftData != null){
				chooseUpdateVersion();
			}else{
				openUpdateWindow(currentData);
			}
		}else{
			openUpdateWindow(currentData);	
		}
	}else{
		Modal.alert({ msg:'请选择修改内容！', title:'提示', btnok:'确定' });
	}
}
//修改弹框
function openUpdateWindow(data){
	updateCertTemp(data);
	findSealInfo(data.departmentId);
	//插入关联的印章id号
    $("#sealId").val(findRSeal(data.id));
    selectUpdated($("#sealId"));//下拉框变动更新	
}
//修改
function updateCertTemp(data){
	okBtn=1;
	formValidator();
	formatClearForm("addForm");
	$(".itemImg").attr('src','');
	addFormValidator.resetForm();
//	//图片回显
//	findRImg(data.id);
	$('#addForm .form-control').each(function(){
		for(var item in data){
			if(item==$(this).attr('name')){
				$(this).val(data[item]);
			}
		}
	});
	formatDialog($("#addFormWrap"),{title:"修改",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": function(){
	submit(this)
	}});	
}
//提交
function submit(_this){
	var form = $('#addForm');
	var url;
	if(!formatValidate(addFormValidator)){
		return;
	}
	if(okBtn==1){		
			url=editUrl;
			
	}else{		
			url=addUrl;			
	}
	uploadFileAjax(form,url,_this);
};
//图片上传
function buttonClick(obj){
	if($('.itemImg').attr('src')){
		$('.itemImg').css('display','none');
		//$(".itemImg").attr('src','');
	}
	$(obj).prev().click();
};
function fileChange(file){
	if(file.files[0].size/1024>=65){
		$("#imgMsg").css("display","block");
		$(file).val("");
		$("input[name='fileName']").val("");
	}else{
		$("#imgMsg").css("display","none");
		var upload_file = $.trim($(file).val());    //获取上传文件
		$(file).prev().val(upload_file);     //赋值给自定义input框
	}
};
function uploadFileAjax(form,url,_this){
	var ajax_option={
		url: url,
		type : 'post',
		error : function(request, textStatus, errorThrown) {
		},
		success:function(data){
			Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' }).on(function(e){
				search();
			});
		}
	};
	form.ajaxSubmit(ajax_option);
	$(_this).dialog("close");
};
//查询已发布版本是否有草稿
function findHighVersion(certId){
	var draft='';
	$.ajax({
		cache : false,
		type : "post",
		url : 'certTempAction!haveHighVersion.action',
		dataType : 'json',
		data : {
			id : certId
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
				draft = data.data;
		}
	});
	return draft;
}
//选择修改版本弹框
function chooseUpdateVersion() {	
	formatDialog($("#updateVersionWrap"),{title:"修改信息",dialogClass:"mydialog modal-sm"},{"取消": formatDialogClose , "确定": insertUpdateVersion});
}
//选择修改版本弹框确认
function insertUpdateVersion(){
	var chooseVal = $('#updateVersionWrap input[type="radio"]:checked').val();
	if(chooseVal == 1){
		openUpdateWindow(currentData);
	}else{
		openUpdateWindow(draftData[0]);
	}
	$(this).dialog("close");
}
//查看关联该模板的印章
function findRSeal(certId){
	var result='';
	var sealCodes = new Array();
	$.ajax({
		cache : false,
		type : "POST",
		url : 'certTempAction!findRSeal.action',
		dataType : 'json',
		data : {
			id : certId
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			var bindSeals = data.result;
			result = data.result;
			if(bindSeals){
				for(var i = 0;i < bindSeals.length;i++){
					sealCodes.push(bindSeals[i].code);
				}
			}			
		}
	});
	return sealCodes;
}
//加载公章信息
function loadSealInfo(){
	$.ajax({
		url : "sealInfoAction!findAll.action",
		type : "POST",
		dataType : "json",
		async : true,
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			sealList = data.result;
		}
	});
}
//公章 formatter
function sealFormatter(value,row){
	var htmlStr = "<option value=\""+0+"\">"
			+ "------请选择-----" + "</option>";
	for(var i = 0;i < sealList.length;i++){
		if(sealList[i].id == row.sealId){
			htmlStr += "<option value=\""+sealList[i].id+"\" selected=\"selected\">"
			+ sealList[i].name + "</option>";
		}else{
			htmlStr += "<option value=\""+sealList[i].id+"\">"
			+ sealList[i].name + "</option>";
		}
	}
	return "<select class=\"form-control chosen-select-deselect\" name=\""+row.id+"\" chosen-position=\"true\" onchange=\"changeSeal(this)\">"+htmlStr+"</select>";
}
function removeTable(){
	var info = $("#tablewrap").bootstrapTable("getSelections");
	if (info != 0) {
		remove(info[0].id);
	}else{
		Modal.alert({ msg:'请选择删除内容！', title:'提示', btnok:'确定' });
	}
}
//发布模板将active状态置为1
function publishTemplate(){
	var info = $("#tablewrap").bootstrapTable("getSelections");//获取选取的数据
    if(info != 0){
    	//console.log(info[0].active);
    	if(info[0].active == 1){
    		Modal.alert({
                msg : '模板已发布',
                title : '提示',
                btnok : '确定'
            });
    		return;
    	}else{
    		Modal.confirm({
                msg : '是否发布？',
                title : '提示',
                btnok : '发布',
                btncl : '取消'
            }).on(function(e){
            	if(e){
	        		var id = info[0].id;
	                var dataStr = "id=" + id +"&active=1";
	                $.ajax({
	        	        type : "POST",
	        	        url : 'certTempAction!updateActive.action',
	        	        dataType : 'json',
	        	        data : dataStr,
	        	        error : function(request, textStatus, errorThrown) {
	        	            //fxShowAjaxError(request, textStatus,errorThrown);
	        	        },
	        	        success : function(data) {
	        	        	if(data.resultMsg == null || data.resultMsg ==""){
	        	                Modal.alert({
	        	                    msg : '发布成功',
	        	                    title : '提示',
	        	                    btnok : '确定',
	        	                    btncl : '取消'
	                            });
	        	        	}else{
	        	        		Modal.alert({
	        	                    msg : data.resultMsg,
	        	                    title : '提示',
	        	                    btnok : '确定',
	        	                    btncl : '取消'
	        	                });
	        	        	}
	                        //refresh();
	                        $("#tablewrap").bootstrapTable('refresh');
	        	        }
	        	    });
            	}
            });
    	}
    	
    	
        
	}else{
		Modal.alert({ msg:'请选择发布内容！', title:'提示', btnok:'确定' });
	}
}
function idFormatter(value, row) {
	return index++;
}
function editCertTemp(val,row){
	if(row.type == 6){//如果是其他类型则显示为不可用
		return "<button class='btn btn-xs btn-primary' disabled='disabled' onClick='editTemp("+val+")'><i class='iconfont icon-wentifankui' style='padding-right:5px;'></i>编辑模板</button>";
	}
	if(userPower == 'edit'){
		return "<button class='btn btn-xs btn-primary' onClick='editTemp("+val+")'><i class='iconfont icon-wentifankui' style='padding-right:5px;'></i>编辑模板</button>";
	}else {
		return "<button class='btn btn-xs btn-primary' disabled='disabled' onClick='editTemp("+val+")'><i class='iconfont icon-wentifankui' style='padding-right:5px;'></i>编辑模板</button>";
	}
	
}
//查看其他模板
function nameFormatter(value,row){
	return "<span class='base_text-blue' title='"+value+"' onclick=\"seeTemp('"+row.code+"','"+row.id+"')\">"+value+"</span>";
}
function seeTemp(code,id){
	var historyPar ={'code': code};
	var draftPar = {'id': id};
	//历史
	historyTempAjax(historyPar);
	//草稿
	drafTempAjax(draftPar);
	formatDialog($("#seeOtherTemp"),{title:"查看其它版本",dialogClass:"mydialog modal-lg"});
}
//获取草稿模板
function drafTempAjax(params){
	var dataStr= '';
	$.ajax({
		type : 'post',
		url : 'certTempAction!findRelateVersion.action',
		dataType : 'json',
		cache : false,
		async : true,
		data : params,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			initDrafTable(data);
		}
	});
}
//获取历史模板
function historyTempAjax(params){
	var dataStr= '';
	$.ajax({
		type : 'post',
		url :  'certTempAction!findHistoryVersion.action',
		dataType : 'json',
		cache : false,
		async : true,
		data : params,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			initHistoryTable(data);
		}
	});
}
//草稿table初始化
function initDrafTable(data) {
	$("#draftTablewrap").bootstrapTable('destroy');
    $('#draftTablewrap').bootstrapTable({
        striped : true,
        smartDisplay: true, 
        uniqueId:'id',
        columns : [{
            field: 'id',
            title: '序号',
            visible : false
        },{
            field : 'code',
            title : '模板编码',
            halign : 'center'
        },{
            field : 'name',
            title : '模板名称',
            halign : 'center'
        },{
            field : 'version',
            title : '版本号',
            halign : 'center'
        },{
            field : 'catalogDeptName',
            title : '部门',
            halign : 'center',
            formatter: titleFormatter
        },{
            field : 'type',
            title : '证件类型',
            halign : 'center',
            formatter: typeFormatter
        },{
            field : 'keywords',
            title : '关键字',
            halign : 'center'
        },{
            field : 'id',
            title : '查看模板',
            halign : 'center',
            formatter: checkPDF
        }],
        data : data
    });
    $('#draftTablewrap').bootstrapTable('load',data)
}
//历史table初始化
function initHistoryTable(data) {
	 $("#historyTablewrap").bootstrapTable('destroy');
    $('#historyTablewrap').bootstrapTable({
        striped : true,
        //singleSelect: true,   
        smartDisplay: true, 
        uniqueId:'id',
        columns : [{
            field: 'id',
            title: '序号',
            visible : false
        },{
            field : 'code',
            title : '模板编码',
            halign : 'center'
        },{
            field : 'name',
            title : '模板名称',
            halign : 'center'
        },{
            field : 'version',
            title : '版本号',
            halign : 'center'
        },{
            field : 'catalogDeptName',
            title : '部门',
            halign : 'center',
            formatter: titleFormatter
        },{
            field : 'type',
            title : '证件类型',
            halign : 'center',
            formatter: typeFormatter
        },{
            field : 'keywords',
            title : '关键字',
            halign : 'center'
        },{
            field : 'id',
            title : '查看模板',
            halign : 'center',
            formatter: checkPDF
        }],
        data : data
    });
    $('#historyTablewrap').bootstrapTable('load',data)
}
//编辑模板
function editTemp(id){
	window.open("../flash/CertTempDev.jsp?certTempId="+id);
}
function typeFormatter(val) {
	if(val == 1 || val == 2) {
		return "<span title='证明'>证明</span>"
	}else if(val == 3 ) {
		return "<span title='申请表'>申请表</span>";
	} else if(val == 4){
    	return "<span title='照片'>照片</span>";
    }  else if(val == 6) {
		return "其他" ;
	} else{
		return '-';
	}
}
function createTypeFormatter(val) {
	if(val == 1) {
		return "PDF";
	} else if(val == 2){
		return "FreeMarker";
	} else {
		return "-";
	}
}
function formatActive(val,row) {
    var html="";
    if (val == 1) {
        html+= '<span title="已发布"><font style="color:#0000FF">已发布</font></span>';
    } else if(val == 2){
       html+= '<span title="草稿"><font style="color:#FF0000">草稿</font></span>';
    } else {
       html+= '<span title="未发布"><font style="color:#FF0000">未发布</font></span>';
    }
    return html;
}
//查看PDF
function checkPDF(value,row){
	if(row.type != 6){
		return "<button class='btn btn-xs btn-primary'  onclick=\"showPDF('"+row.createType+"','"+row.code+"')\"><i class='iconfont icon-chakan' style='padding-right:5px;'></i>查看</button>";
	}else{
		return "<button class='btn btn-xs btn-primary'  disabled='disabled' onclick=\"showPDF('"+row.createType+"','"+row.code+"')\"><i class='iconfont icon-chakan' style='padding-right:5px;'></i>查看</button>";
	}
}
//查看PDF
function showPDF(createType,tempCode){
	var url = "ImageViewer.jsp?tempCode="+tempCode;
	window.open(url, "newwindow", "'width="+1000+",height="+800+", toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no");
}
//请求数据
function ajaxRequest(params) {
	var datas;
	var list;
	var count;
	var p = getParams(params);
	lastAjax = $.ajax({
		type : 'post',
		url : 'certTempAction!findPage.action',
		dataType : 'json',
		cache : false,
		async : true,
		data : p,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var datas = data.rows?data.rows:{};
			var count = data.rows?data.total:0;
			params.success({
				total : count,
				rows : datas
			});
		}
	});
}
function getParams(params) {
	var pageSize = params.data.limit;
	var pageNum = params.data.offset / pageSize + 1;
	var findByActive = "";
	index = params.data.offset + 1;
	var sort = params.data.sort ? params.data.sort : "id";
	var order = params.data.order ? params.data.order : "desc";
	var type = $("#searchType").val() ;
	var nameLike = $("#searchName").val();
	var data = {
		sort : sort,
		order : order,
		pageNum : pageNum,
		pageSize : pageSize,
		name : nameLike,
		type : type
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
						message : '模板名称不能为空！'
					}
				}
			},
			type : {
				validators : {
					 notEmpty : {
						message : '证件类型不能为空！'
					},
				}
			},
			signx: {
				validators : {
					 notEmpty : {
						message : '相对模板左上方的水平位移不能为空！'
					},
				}
			},
			signy: {
				validators : {
					 notEmpty : {
						message : '相对模板左上方的垂直位移不能为空！'
					},
				}
			},
		}
	})
}
//查询类型切换触发
function typeChange(){
		$("#tablewrap").bootstrapTable('selectPage',1);
}
//刷入缓存
function saveToCache(){
	var info = $("#tablewrap").bootstrapTable("getSelections");
	if(info != 0){
		$.ajax({
			type : 'post',
			url : 'certTempAction!saveToCache.action',
			dataType : 'json',
			cache : false,
			async : true,
			data : {id:info[0].id},
			error : function(request, textStatus, errorThrown) {
			},
			success : function(data) {
				if(data.msg == null || data.msg ==""){
		            Modal.alert({
		                msg : data.msg,
		                title : '提示',
		                btnok : '确定',
		                btncl : '取消'
		            });
		    	}else{
		    		Modal.alert({
		                msg : data.msg,
		                title : '提示',
		                btnok : '确定',
		                btncl : '取消'
		            });
		    	}
			}
		});
	}else{
		Modal.alert({ msg:'请选择发布内容！', title:'提示', btnok:'确定' });
	}
}
//新增/修改树点击回调函数
function treeCallBack2(treeName,treeId){
	$("#departmentName").val(treeName);
	$("#departmentId").val(treeId);
}
//显示证件复制面板
function showCopyForm(){
	//复制事项
	loadDepartment('treeDemo3');
	if(!saveDept){
		loadDepartment1();
	}
	selectConfig();	//下拉框初始化
	formatDialog($("#copyItemWrap"),{title:"批量绑定证明",dialogClass:"mycopydialog "},{"取消": formatDialogClose , "提交":copyTempToOtherDept });
}
function loadDepartment1(){
	
	var data= {
		id: "0"
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
/*数源部门点击回调事件*/
function treeCallBack3(treeName,treeId){
	//获取事项
	$("#sourceDeptName").val(treeName);
	$("#sourceDeptId").val(treeId);
	$("#tempSelectId").empty();
	oldHtml = "";	
    selectUpdated($("#tempSelectId"));
    var departmentId = treeId;
    findTempList(departmentId);			//根据部门Id获取证明
    
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
/*获取证明列表*/
function findTempList(departId){
	$.ajax({
		cache : false,
		type : "get",
		url : 'certTempAction!findTempList.action',
		dataType : 'json',
		data: {"departId":departId},
		async : false,
		error : function(request, textStatus, errorThrown) {
			fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			if (data.result != null) {
				allTemp = data.result;
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					var catalogDeptName = data.result[i].catalogDeptName == null?  "无": data.result[i].catalogDeptName;
					htmlStr += "<option value=\""+data.result[i].id+"\">"
							+ data.result[i].name + "(" + catalogDeptName + ")</option>";
				}
				selectConfig();//下拉框初始化
				oldHtml = htmlStr + oldHtml;
				$("#tempSelectId").append(oldHtml);
				selectUpdated($("#tempSelectId"));
			}
		}
	});
}
/*证明列表下拉框改变事件*/
$("#tempSelectId").on('change',function(e,params){
	var tempName = $("#tempSelectId").find("option:selected").text();
    var tempId = params.selected; 
    var html = '<option value="'+tempId+'">'+tempName+'</option>';
    var i = 0;
    //遍历列表检查是否已经存在该事项
    $("#select option").each(function() {
		var id =$(this).val();
		if( id == tempId){
			html = "";
			alert('请勿重复添加事项!');
		}
		i++;
	});
    //将事项放在数源列表里
    $("#select").append(html);
})
/*移除证件按钮点击触发*/
$("#removeTemp").on('click',function(){
	//获取事项列表框中选中的
	var rmItemId =  $("#select").get(0).selectedIndex;
	$("#select").find("option").get(rmItemId).remove() // i为index
})
/*清空证件按钮点击触发*/
$("#removeAllTemp").on('click',function(){
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
function copyTempToOtherDept(){
	var tempIds = [];
	var deptIds = [];
	var i = 0;
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo4");
	var checkedNodes = treeObj.getCheckedNodes(true);
	var len = checkedNodes.length;
	var html = "";
	for (var ii = 0; ii < len; ii++) {
		var treeNode = checkedNodes[ii];
		var fullDepartment = "";
		deptIds.push(treeNode.id)
	}
	//遍历select 获取tempIds
	$("#select option").each(function() {
		tempIds[i]=$(this).val();
		i++;
	});
	i = 0;
	if(tempIds.length!=0 && deptIds.length!=0){
		$.ajax({
				type : 'post',
				url : 'certTempAction!copyTempsToOtherDept.action',
				dataType : 'json',
				traditional :true, 
				data : {
					"tempIds" : tempIds,
					"deptIds" : deptIds
				},
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					$("#copyItemWrap").dialog("close");
					Modal.confirm({ msg:"复制成功!是否保留部门选项", title:'提示', btnok:'确定',btncl:'关闭' }).on(function(e){
						saveDept = e;
						$("#tablewrap").bootstrapTable('selectPage',1);
					});
				}
		});
	}else{
		alert("数源证明或目的部门为空，请重试！")
	}
}