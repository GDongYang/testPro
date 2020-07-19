var cardUrl= 'roleAction!findPage.action';
var addUrl='roleAction!save.action';
var editUrl='roleAction!update.action';
var deleteUrl='roleAction!remove.action';
var addFormValidator;
var roleId=0;
var deptObj = {};
var lastAjax = null;
var selectCode = "";
var selectName='';
var rId = null;
var showDeptIds=[];
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: true,
		chkboxType :  {"Y" : "ps", "N" : "ps"}
	}
};
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
		chkboxType : { "Y" : "ps", "N" : "ps" }
	},callback: {
		onClick: zTreeOnClick,
		onExpand:zTreeOnClick
	}
};
$(document).ready(function(){
	ajaxRequest();
	formValidator();
	getMenuList();
	loadDepartment();
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
//修改
function updateRole(data){
	okBtn=1;
	formValidator();
	formatClearForm("addForm");
	addFormValidator.resetForm();
	$('#addForm .form-control').each(function(){
		for(var item in data){
			if(item==$(this).attr('name')){
				$(this).val(data[item]);
			}
		}
	});
	formatDialog($("#addFormWrap"),{title:"修改",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": function(){
		insertRole(this)
	}});
}
//修改确认
function insertRole(_this){
	var flag = formatValidate(addFormValidator);
	setTimeout(function(){//解决提交两次，非最佳方案
		var flag2 = formatValidate(addFormValidator);
		if(!flag2){
			return
		}
		var radioStr='';
		$('input[type="radio"]').each(function(){
			if($(this).attr('checked')){
				radioStr+='&'+$(this).attr('nameSet')+'='+$(this).attr('valueSet');
			}
		});
		if(okBtn==1){
			ajaxParams = {
				url:editUrl,
				data:$('#addForm').serialize()+radioStr
			};
		}else{
			ajaxParams = {
				url:addUrl,
				data:$('#addForm').serialize()+radioStr
			};
		}
		formatAjax(ajaxParams,tipsDialog);
		addFormValidator.resetForm();
		_this.dialog("close");
   }, 500);
}
//获取菜单列表
function getMenuList() {
	$.ajax({
	   	url:"menuAction!findMenuTree.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
	  		//fxShowAjaxError(request, textStatus, errorThrown);
		},
	   	success:function(dataParam){
			$.fn.zTree.init($("#tree"), setting, dataParam);
			$.fn.zTree.getZTreeObj("tree").expandAll(true);
		}
	});
}
//关联菜单
function setMenu(id) {
	roleId=id;
	$.ajax({
		url:"menuAction!findMenuByRole.action",
		type:"POST",
		dataType:"json",
		data:{"roleId":id},
		async:true,
		error:function(request,textStatus, errorThrown){
		},
		success:function(dataParam){
			if (dataParam) {
				var length = dataParam.length;
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				treeObj.checkAllNodes(true);
				var checkedNodes = treeObj.getCheckedNodes(true);
				var len = checkedNodes.length;
				if(length != len -1) {
					var str = "";
					for (var ii = 0; ii < length; ii++) {
						str += dataParam[ii].name + dataParam[ii].id + ",";
					}
					for (var ii = 0; ii < len; ii++) {
						var param = checkedNodes[ii].name + checkedNodes[ii].id;
						if (str.indexOf(param) == -1) {
							treeObj.checkNode(checkedNodes[ii], false, false);
						}
					}
					treeObj.checkNode(treeObj.getNodeByTId("tree_1"), true, false);
				}
			} else {
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				treeObj.checkAllNodes(false);
			}
			$('.sideAddModal').removeClass('base_hidden');
			$('#menuFormWrap').removeClass('base_hidden');
			$('#departFormWrap').addClass('base_hidden');
			$('#menuConfirm').removeClass('base_hidden');
			$('#deptConfirm').addClass('base_hidden');
		}
	});
}
function menuConfirm(){
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var checkedNodes = treeObj.getCheckedNodes(true);
	var len = checkedNodes.length;
	var menuIds = [];
	for (var ii = 0; ii < len; ii++) {
		menuIds.push(checkedNodes[ii].id);
	}
	$.ajax({
		cache: true,
		type: "POST",
		url:'roleAction!saveMenu.action',
		dataType:'json',
		traditional :true, 
		data:{'id':roleId,'menuIds':menuIds},
		async: false,
		error: function(request,textStatus, errorThrown) {
			fxShowAjaxError(request, textStatus, errorThrown);
		},
		success: function(data) {
			Modal.alert({ msg:'关联菜单成功！', title:'提示', btnok:'确定' }).on(function(e){
				sideAddModalClose();
			});
		}
	});
}
function sideAddModalClose(){
	$('.sideAddModal').addClass('base_hidden');
	roleId=0;
}
//获取当前角色已经关联的部门
function setDepart(id) {
	roleId=id;
	$.ajax({
		url:"departmentAction!findDepartByRole.action",
		type:"POST",
		dataType:"json",
		data:{"roleId":id},
		async:true,
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			rId = data;
			if (data != null && data.result != null) {
				var length = data.result.length;
				var treeObj = $.fn.zTree.getZTreeObj("treeDemoList");
				treeObj.checkAllNodes(true);
				var checkedNodes = treeObj.getCheckedNodes(true);
				var len = checkedNodes.length;
				if(length != len -1) {
					var str = "";
					for (var ii = 0; ii < length; ii++) {
						str += data.result[ii].name + data.result[ii].id + ",";
					}
					for (var ii = 0; ii < len; ii++) {
						var param = checkedNodes[ii].name + checkedNodes[ii].id;
						if (str.indexOf(param) == -1) {
							treeObj.checkNode(checkedNodes[ii], false, false);
						}
					}
				}
			} else {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemoList");
				treeObj.checkAllNodes(false);
			}
			$('.sideAddModal').removeClass('base_hidden');
			$('#menuFormWrap').addClass('base_hidden');
			$('#departFormWrap').removeClass('base_hidden');
			$('#menuConfirm').addClass('base_hidden');
			$('#deptConfirm').removeClass('base_hidden');
		}
	});
}
/*关联所有部门*/
function deptConfirm(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemoList");
	var checkedNodes = treeObj.getCheckedNodes(true);
	var len = checkedNodes.length;
	var departIds = [];
	for (var ii = 0; ii < len; ii++) {
		departIds.push(checkedNodes[ii].id);
	}
	
	var jobDeptId = $("#deptSelect").val();
	var jobLevel = $("#levelSelect").val();
	var jobStr = $("#jobSelect").val();
	$.ajax({
		cache: true,
		type: "POST",
		url:'roleAction!saveDepartAll.action',
		dataType:'json',
		traditional :true, 
		data:{'id':roleId,
		'departIds':departIds,
		'jobDeptId':jobDeptId,
		'jobLevel':jobLevel,
		'jobStr':jobStr,
		'showDeptIds':showDeptIds},
		async: false,
		error: function(request,textStatus, errorThrown) {
		},
		success: function(data) {
			Modal.alert({ msg:'关联部门成功！', title:'提示', btnok:'确定' }).on(function(e){
				sideAddModalClose();
			});
		}
	});
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
						message : '角色名称不能为空！'
					},
					remote : {
						url:'roleAction!isExists.action',
						message:'已存在',
						type:'post',
						dataType:"json",
						data:function(validator) {
	                       return {
	                    	   name:$('#roleName').val(),
	                    	   id:$('#roleId').val(),
	                       };
	                    }
					}
				}
			},
			description : {
				validators : {
					 notEmpty : {
						message : '角色描述不能为空！'
					},
				}
			}
		}
	})
}
function getCardContent(data){
	var dataStr="<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20'>"
	+"<div class='cardBox'><div class='cardBody'><div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'>"
	if(data.active==1){
		dataStr+="<img src='../build/image/7-1.png'/>";
	}else{
		dataStr+="<img src='../build/image/7.png'/>";
	}
	dataStr+="</div><div class='col-xs-9 col-sm-9 col-md-9 col-lg-9 base_padding-0'><div class='cardTitle'>"
	+(data.name ? data.name : '无')+"</div><div class='cardTitleSmall'>角色描述</div><div class='cardMsg'>"
	+(data.description ? data.description : '无')+"</div><div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 base_padding-l-0'><div class='cardTitleSmall'>"
	+"创建者</div><div class='cardMsg'>"+(data.creator ? data.creator : '无')+"</div></div>"
	+"<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'><div class='cardTitleSmall'>级别</div>";
	if(data.level == 0) {
		dataStr+= "<div class='cardMsg'>高</div></div></div></div>";
	} else if (data.level == 1) {
		dataStr+= "<div class='cardMsg'>中</div></div></div></div>";
	} else {
		dataStr+= "<div class='cardMsg'>低</div></div></div></div>";
	}
	dataStr+='<div class="cardFooter '+(data.active==1?'':'disabled')+'"><div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 border-r-fff" title="修改" onclick="updateRole('+JSON.stringify(data).replace(/\"/g,"'")+')">'
	+"<i class='fa fa-edit'></i></div><div class='col-xs-3 col-sm-3 col-md-3 col-lg-3 border-r-fff' title='删除' onclick='remove("+data.id+")'>"
	+"<i class='fa fa-trash-o'></i></div><div class='col-xs-3 col-sm-3 col-md-3 col-lg-3 border-r-fff' title='菜单权限' onclick='setMenu("+data.id+")'>"
	+"<i class='fa fa-bars'></i></div><div class='col-xs-3 col-sm-3 col-md-3 col-lg-3' title='部门权限' onclick='setDepart("+data.id+")'>"
	dataStr+="<i class='fa fa-share-alt-square'></i></div></div></div></div>";
	return dataStr;
}
//得到所有部门
function loadDepartment() {
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
					showDeptIds.push(item.id);
					deptObj[item.id] = item.name;
					item.isParent = true;
				});
				$.fn.zTree.init($("#treeDemoList"), settings, data.departments);
	   		} else {
	   			var list= [];
				$.fn.zTree.init($("#treeDemoList"), settings, list);
	   		}
		}
	});
}
//树节点点击事件
function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode.id == 0) {
		selectCode = "";
		$('#departmentId').val('');
	} else {
		selectCode = treeNode.id;
		selectName=treeNode.name;
		var id = selectCode;
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
							showDeptIds.push(item.id);
							deptObj[item.id] = item.name;
							if(item.isLeaf == "0"){
								item.isParent=true;
							}
							if(rId != null && rId != ""){
								$(rId.result).each(function(f, itema) {
								    if(item.id == itema.id){
								        item.checked = true;    
								    }
								});   
							}
							var deptName = $("#deptFilter").val()
							if(deptName.replace(" ","") != ""){
								if(item.name.indexOf(deptName) != -1){
									item.checked=true;
								}
							}
						});
	                    $.fn.zTree.getZTreeObj("treeDemoList").addNodes(treeNode,eval(data.departments));
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
//筛选部门列表
function filterDept(){
	//遍历部门树
	var treeObj = $.fn.zTree.getZTreeObj("treeDemoList");
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

/*清空用户关联的所有部门*/
function clearAllDepts(){
	Modal.confirm({ msg:"您是否要确认清空该角色的关联部门(此操作不可逆)", title:'提示', btnok:'确定',btncl:'关闭' }).on(function(e){
		if(e){
			$.ajax({
				cache: true,
				type: "POST",
				url:'roleAction!cleaerAllDepts.action',
				dataType:'json',
				traditional :true, 
				data:{'id':roleId},
				async: false,
				error: function(request,textStatus, errorThrown) {
				},
				success: function(data) {
					Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' }).on(function(e){
						sideAddModalClose();
					});
				}
			});
		}
	});
}

/*重置用户部门的修改*/
function resetUpdateDepts(){
	setDepart(roleId);
}
