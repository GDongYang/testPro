var url='../formPageAction!findPage.action';
var addUrl='../formPageAction!save.action';
var editUrl='../formPageAction!update.action';
var deleteUrl='formPageAction!remove.action';
var pageNum = 1;
var sealList = [];    //印章信息列表
var addFormValidator;
var treeSearchFlag=true;//判断是否需要模糊搜索
var draftData = '';//草稿数据
var currentData = '';//当前版本数据
var userPower = '';//用户的权限
var saveDept = false;//是否保存复制的目的部门
var oldHtml = ""//存储旧的HTML
var formCodeParams = {};
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
	loadDepartment('treeDemo');
	formValidator();
	//loadSealInfo(); //加载公章信息
	selectConfig();//下拉框初始化	
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
$("#departmentId").change(function(){
	loadPosition($("#departmentId").val(),null);
});
$("#type").change(function(){
	var choose = $("#type").val();//获取是关键字还是坐标
	if(choose != ""){
		if(5 == choose){//若选取的是关键字则显示关键字输入框反之显示坐标输入框
			if(!$(".formUrl").hasClass('hidden')){
				$(".formUrl").addClass('hidden');formUrl
				$("#formUrl").val('');
			}
		}else if(6 == choose){
			if($(".formUrl").hasClass('hidden')){
				$(".formUrl").removeClass('hidden');
			}
		}
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
			userPower = data.power;
			if(userPower == 'edit'){
				$(".tableBtns").show();
			}
			
		}
	});
}
//树点击回调函数
function treeCallBack(treeName,treeId){
	$("#tablewrap").bootstrapTable('selectPage',1);
}
function search(){
//	$("#tablewrap").bootstrapTable('refresh');
	$("#tablewrap").bootstrapTable('selectPage',1);
}
function cleanSearch(){
	$("#searchName").val("");
	$("#searchType").val(0);
}
//新增
function addTable(){
	$('.formUrl').addClass('hidden');
	$("#formUrl").val('');
	if(selectCode){
		add();
		$('#deptName').val(selectName);
		$('#org').val(selectCode);
	}else{
		Modal.alert({ msg:'请选择部门！', title:'提示', btnok:'确定' });
	}
}
//修改
function updateTable(){
	var info = $("#tablewrap").bootstrapTable("getSelections");
	if (info != 0) {
		update(info[0]);
		if(info[0].type == 5){
			$("#type").val(5);
			if(!$(".formUrl").hasClass('hidden')){
				$(".formUrl").addClass('hidden')
			}
		}else if(info[0].type == 6){
			$("#type").val(6);
			if($(".formUrl").hasClass('hidden')){
				$(".formUrl").removeClass('hidden');
			}			
			$("#formUrl").val(info[0].formUrl);
		}
	}else{
		Modal.alert({ msg:'请选择修改内容！', title:'提示', btnok:'确定' });
	}
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
//表单名称
function nameFormatter(value,row){
	return "<span class='base_text-blue' title='"+value+"'>"+value+"</span>";
}
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
function removeTable(){
	var info = $("#tablewrap").bootstrapTable("getSelections");
	if (info != 0) {
		remove(info[0].id);
	}else{
		Modal.alert({ msg:'请选择删除内容！', title:'提示', btnok:'确定' });
	}
}
//发布表单将active状态置为1
function publishTemplate(){
	var info = $("#tablewrap").bootstrapTable("getSelections");//获取选取的数据
    if(info != 0){
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
	        	        url : 'formPageAction!updateActive.action',
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
	}else{
		Modal.alert({ msg:'请选择发布内容！', title:'提示', btnok:'确定' });
	}
}
function idFormatter(value, row) {
	return index++;
}
//function editCertTemp(val,row){
//	if(row.type == 6){//如果是其他类型则显示为不可用
//		return "<button class='btn btn-xs btn-primary' disabled='disabled' onClick='editTemp("+val+", \""+row.type+"\", \""+row.code+"\")'>编辑模板</button>";
//	}
//	if(userPower == 'edit'){
//		return "<button class='btn btn-xs btn-primary' onClick='editTemp("+val+", \""+row.type+"\", \""+row.code+"\")'>编辑模板</button>";
//	}else {
//		return "<button class='btn btn-xs btn-primary' disabled='disabled' onClick='editTemp("+val+", \""+row.type+"\", \""+row.code+"\")'>编辑模板</button>";
//	}
//	
//}

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
//操作
function formOption(value,row){
	var optionHtml = '';
	optionHtml = "<button class='btn btn-xs btn_span base_margin-r-10' onclick=\"showFormImgs('"+row.id+"')\"><i class='iconfont icon-chakan' style='padding-right:5px;'></i>查看</button>";
	if(row.type == 6){//如果是其他类型则显示为不可用
		optionHtml += "<button class='btn btn-xs btn_span' onClick='editTemp("+value+")'><i class='iconfont icon-wentifankui' style='padding-right:5px;'></i>编辑</button>";
	}else if(userPower == 'edit'){
		optionHtml += "<button class='btn btn-xs btn_span' onClick='editTemp("+value+")'><i class='iconfont icon-wentifankui' style='padding-right:5px;'></i>编辑</button>";
	}else {
		optionHtml += "<button class='btn btn-xs btn_span' disabled='disabled' onClick='editTemp("+value+")'><i class='iconfont icon-wentifankui' style='padding-right:5px;'></i>编辑</button>";
	}
	//optionHtml += "<button class='btn btn-xs btn-primary base_margin-l-10' onClick='manageData(\""+row.code+"\")'>管理值</button>";
    //optionHtml += "<button class='btn btn-xs btn-xs btn_span' onClick='bindFormTemp(\""+row.code+"\")'>关联</button>";
    return optionHtml
}
//管理值打开弹窗
function manageData(code){
	$('#msgDataTable tbody').empty();
	$.ajax({
		type : 'get',
		url : 'dataElementAction!findByFormCode.action',
		dataType : 'json',
		data : {'formCode':code},
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var datas=data.elements;
			if(datas!=null&&datas.length>0){
				var dataStr='';
				for(var i=0;i<datas.length;i++){
					dataStr+='<tr><td><input type="text" name="field" class="form-control" value="'+datas[i].code+'"/></td>'
						+'<td><input type="text" name="fieldName" class="form-control" value="'+datas[i].name+'"/></td>'
						+'<td><span class="" onclick="deleteMsgData(this)">删除</span></td></tr>';
				}
				$('#msgDataTable tbody').append(dataStr);
			}
		}
	});
	formatDialog($("#msgDataModal"),{title:"管理值",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交":function(){
		msgData(this,code)
	}});
}
//添加
function addMsgTable(){
	var datas='<tr><td><input type="text" name="field" class="form-control"/></td>'
		+'<td><input type="text" name="fieldName" class="form-control"/></td>'
		+'<td><span class="" onclick="deleteMsgData(this)">删除</span></td></tr>';
	$('#msgDataTable tbody').append(datas);
}
function deleteMsgData(obj){
	$(obj).parent().parent().remove();
} 
//提交确认
function msgData(_this,code){
	var fields=[],fieldName=[],flag=true;
	$('#msgDataTable tbody tr').each(function(){
		if($(this).find('input[name="field"]').val()!=''&&$(this).find('input[name="fieldName"]').val()!=''){
			fields.push($(this).find('input[name="field"]').val());
			fieldName.push($(this).find('input[name="fieldName"]').val());
		}else{
			$(this).find('input').each(function(){
				if($(this).val()==''){
					$(this).css('border','1px solid #ff0000');
				}
			})
			flag=false;
			return ;
		}
	})
	if(flag){
		$.ajax({
			type : 'post',
			url : 'dataElementAction!create.action',
			dataType : 'json',
			traditional:true,
			data : {'formCode':code,'fields':fields,'fieldNames':fieldName},
			error : function(request, textStatus, errorThrown) {
			},
			success : function(data) {
				if(data.code==1){
					Modal.alert({
		                msg : '提交成功！',
		                title : '提示',
		                btnok : '确定',
		                btncl : '取消'
		            })
				}else{
					Modal.alert({
		                msg : '提交失败！',
		                title : '提示',
		                btnok : '确定',
		                btncl : '取消'
		            })
				}
			}
		});
		$(_this).dialog("close");
	}
}
//查看表单
//function checkPDF(value,row){
//	return "<button class='btn btn-xs btn-primary' onclick=\"showFormImgs('"+row.id+"')\">查看</button>";
//}
//查看图片
function showFormImgs(id){
	var url = "formImg.jsp?id="+id;
	window.open(url, "newwindow", "'width="+1000+",height="+800+", toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no");
}
//编辑表单
function editTemp(id){
	window.open("../flash/PageDev.jsp?formId="+id);
}
//请求数据
function ajaxRequest(params) {
	var datas;
	var list;
	var count;
	var p = getParams(params);
	lastAjax = $.ajax({
		type : 'post',
		url : 'formPageAction!findPage.action',
		dataType : 'json',
		cache : false,
		async : true,
		data : p,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var datas = data.page.items?data.page.items:{};
			var count = data.page.count?data.page.count:0;
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
	var nameStr = $("#searchName").val();
	var data = {
		sort : sort,
		order : order,
		pageNum : pageNum,
		pageSize : pageSize,
		nameLike : nameStr,
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
						message : '表单名称不能为空！'
					}
				}
			},
			type : {
				validators : {
					 notEmpty : {
						message : '证件类型不能为空！'
					},
				}
			}
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
			url : 'formPageAction!saveToCache.action',
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

//显示表单复制面板
function showCopyForm(){
	//复制表单
	loadDepartment('treeDemo3');
	if(!saveDept){
		loadDepartment1();
	}
	selectConfig();	//下拉框初始化
	formatDialog($("#copyItemWrap"),{title:"批量绑定表单",dialogClass:"mycopydialog "},{"取消": formatDialogClose , "提交":copyTempToOtherDept });
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
/*数源部门点击回调事件*/
function treeCallBack3(treeName,treeId){
	//获取事项
	$("#sourceDeptName").val(treeName);
	$("#sourceDeptId").val(treeId);
	$("#formSelectId").empty();
	oldHtml = "";	
    selectUpdated($("#formSelectId"));
    var departmentId = treeId;
    findFormPageList(departmentId);			//根据部门Id获取证明
    
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
function findFormPageList(departId){
	$.ajax({
		cache : false,
		type : "get",
		url : 'formPageAction!findList.action',
		dataType : 'json',
		data: {"departmentId":departId},
		async : false,
		error : function(request, textStatus, errorThrown) {
			fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			if (data.result != null) {
				allTemp = data.result;
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					var departmentName = data.result[i].departmentName == null?  "无": data.result[i].departmentName;
					htmlStr += "<option value=\""+data.result[i].id+"\">"
							+ data.result[i].name + "(" + departmentName + ")</option>";
				}
				selectConfig();//下拉框初始化
				oldHtml = htmlStr + oldHtml;
				$("#formSelectId").append(oldHtml);
				selectUpdated($("#formSelectId"));
			}
		}
	});
}
/*证明列表下拉框改变事件*/
$("#formSelectId").on('change',function(e,params){
	var tempName = $("#formSelectId").find("option:selected").text();
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
	var formPageIds = [];
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
	//遍历select 获取formPageIds
	$("#select option").each(function() {
		formPageIds[i]=$(this).val();
		i++;
	});
	i = 0;
	if(formPageIds.length!=0 && deptIds.length!=0){
		$.ajax({
				type : 'post',
				url : 'formPageAction!copyFormsToOtherDept.action',
				dataType : 'json',
				traditional :true, 
				data : {
					"formPageIds" : formPageIds,
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
		alert("数源表单或目的部门为空，请重试！")
	}
}

function bindFormTemp(){
    var info = $("#tablewrap").bootstrapTable("getSelections");
    if (info != 0) {
        formCodeParams.formCode = info[0].code;
        //关联表单
        selectConfig();	//下拉框初始化
        findFormTemp();	//获取表单
        formatDialog($("#formTempFormWrap"),{title:"证照",dialogClass:"mydialog formTempHeight"},{"取消": formatDialogClose , "提交": bindFormTempToItem });

    }else{
        Modal.alert({ msg:'请选择修改内容！', title:'提示', btnok:'确定' });
    }
}

function bindFormTempToItem(){
    var tempCodes = $(".formTempData").val();
    ajaxParams = {
        url:"formPageAction!saveFormTemp.action",
        data:{"code":formCodeParams.formCode,
            "tempCodes":tempCodes},
        async : false,
        traditional:true
    };
    formatAjax(ajaxParams,tipsDialog);
    $(this).dialog("close");
    //search();
    $("#tablewrap").bootstrapTable('refresh');
}

function findFormTemp(){
    $.ajax({
        cache : false,
        type : "POST",
        url : 'certTempAction!findList.action',
        dataType : 'json',
        data:{"type":2},
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
            $(".formTempData").val(findTempCodes());
            selectUpdated($(".formTempData"));//下拉框变动更新
        }
    });
}

function findTempCodes() {
    var result;
    $.ajax({
        cache: false,
        type: "POST",
        url: 'certTempAction!findByForm.action',
        dataType: 'json',
        data: {"formCode": formCodeParams.formCode},
        async: false,
        error: function (request, textStatus, errorThrown) {
        },
        success: function (data) {
            result = data.tempCodes;
        }
    });
    return result;
}