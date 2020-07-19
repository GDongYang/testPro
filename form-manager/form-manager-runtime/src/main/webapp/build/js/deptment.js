var addFormValidator;
var treeSearchFlag=true;//判断是否需要模糊搜索
//初始化
$(function(){
	formValidator();
	addFormValidator = $('#dataform').data('bootstrapValidator');
	//设置tree，table高度
	var windowHeight=window.innerHeight;
	$(".leftBox").css("height",windowHeight-40);
	$(".treeBox").css("height",windowHeight-136);
	$(".tableCont").css("height",windowHeight-90);
	loadDepartment('treeDemo');
});
//树节点点击回调函数
function treeCallBack(treeName,treeId){
	$.ajax({
		url:"departmentAction!findById.action",
	   	type:"POST",
	   	dataType:"json",
	   	data : {
	   		id : treeId
	   	},
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
	  		//fxShowAjaxError(request, textStatus, errorThrown);
		},
	   	success:function(data){
	   		formValidator();
	   		formatClearForm("dataform");
	   		addFormValidator.resetForm();
	   		if(data){
	   			$("#modelId").val(data.department.id);
	   			$("#code").val(data.department.code);
	   			$("#name").val(data.department.name);
	   			$("#memo").val(data.department.memo);
	   		}
	   	}
	});
}
//切换到新增
function toAddDepartment() {
	$('#dataform .form-control').each(function(){
		$(this).val('');
	});
	addFormValidator.resetForm();
}
//根据条件更新或添加
function saveDepartment(){
	if(!formatValidate(addFormValidator)){
		return;
	}
	if($("#modelId").val()&&!$("#modelId").val()==('')){
		updateDepartment();
	}else{
		addDepartment();
	}
}
//添加
function addDepartment(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var node = treeObj.getSelectedNodes()[0];
 	if(!node){
		$("#parentId").val(0);
	}else{
		$("#parentId").val(node.id);
	}
	var param = $("#dataform").serialize();
 	$.ajax({
		url:"departmentAction!save.action",
	   	type:"POST",
	   	dataType:"json",
	   	data : param,
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		if(data.success){
	   			Modal.alert({
					msg: "新增成功",
					title: '新增部门',
					btnok: '确定',
					btncl:'取消'
				});
	   			getDepartmentList();
	   			addFormValidator.resetForm();
	   		}
	   	}
	});
}
//更新
function updateDepartment() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var node = treeObj.getSelectedNodes()[0];
	if(!node){
		Modal.alert({
			msg: "请选择需要修改的菜单！",
			title: '修改部门',
			btnok: '确定',
			btncl:'取消'
		});
		return;
	}
	$("#parentId").val(0);
	if(node.parentId){
		$("#parentId").val(node.parentId);
	}
	var param = $("#dataform").serialize();
 	$.ajax({
		url:"departmentAction!update.action",
	   	type:"POST",
	   	dataType:"json",
	   	data : param,
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		if(data.success){
	   			Modal.alert({
					msg: "修改成功",
					title: '修改部门',
					btnok: '确定',
					btncl:'取消'
				});
	   			getDepartmentList();
	   			addFormValidator.resetForm();
	   		}	   		
	   	}
	}); 
}
//删除
function deleteDepartment() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var node = treeObj.getSelectedNodes()[0];
	if(!node||!node.id){
		Modal.alert({
			msg: "请选择需要删除的部门！",
			title: '删除部门',
			btnok: '确定',
			btncl:'取消'
		});
		return;
	}
	if(node.children != null && node.children != undefined){
		Modal.alert({
			msg: "该部门下存在子部门，请先删除子部门！",
			title: '删除部门',
			btnok: '确定',
			btncl:'取消'
		});
		return;
	}
	Modal.confirm({
		btnok: '确定',
		btncl:'取消',
		msg: "是否删除该部门？"
	}).on( function (e) {
		if(e){
			if(node.children != null && node.children != undefined){
				Modal.alert({
					msg: "该部门下存在子部门，请先删除子部门！",
					title: '删除部门',
					btnok: '确定',
					btncl:'取消'
				});
				
			}else{
				$.ajax({
				   	url:"departmentAction!softRemove.action",
				   	type:"POST",
				   	dataType:"json",
				   	data : {
				   		id : node.id
				   	},
				   	async:true,
				  	error:function(request,textStatus, errorThrown){
					},
				   	success:function(data){
				   		toAddDepartment();
				   		getDepartmentList();
				   		if(data.success){
				   			Modal.alert({
								msg: "删除成功",
								title: '删除部门',
								btnok: '确定',
								btncl:'取消'
							});
				   		}
				   	}
				});
			}
		}
	});
}
function formValidator(){
	$('#dataform').bootstrapValidator({
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
						message : '部门名称不能为空！'
					}
				}
			},
		}
	})
}
//得到所有部门列表
function getDepartmentList() {
	$("#dataform input").val('');
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
	  		//fxShowAjaxError(request, textStatus, errorThrown);
		},
	   	success:function(data){
	   		if(data && data.departments){
				$(data.departments).each(function(i, item) {
					deptObj[item.id] = item.name;
					item.isParent = true;
				});
				$.fn.zTree.init($("#treeDemo"), departSetting, data.departments);
                $.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
	   		} else {
	   			var list= [];
	   			list.push({parentId:null,id:0,name:'一证通办'});
				$.fn.zTree.init($("#treeDemo"), setting, list);
				
				$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeObj.getNodes();
	   		}
		}
	});
}