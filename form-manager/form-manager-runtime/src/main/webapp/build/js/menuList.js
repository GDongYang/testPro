var addFormValidator;
var setting = {
	view:{
		showLine:false
	},
	data: {
		keep:{
			parent: true
		},
		key:{
		},
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parentId",
			rootPId: null
		}
	},callback: {
		onClick: zTreeOnClick,
		onExpand:zTreeOnClick
	}
};
$(function(){
	formValidator();
	addFormValidator = $('#dataform').data('bootstrapValidator');
	//设置tree，table高度
	var windowHeight=window.innerHeight;
	$(".leftBox").css("height",windowHeight-40);
	$(".treeBox").css("height",windowHeight-136);
	$(".tableCont").css("height",windowHeight-90);
	getMenuList();
});
$("#ifVisible").click(function(){
	if (this.checked) {
		$("#visible").val(1);
	}else {
		$("#visible").val(0);
	}
});
function zTreeOnClick(event, treeId, treeNode){
	if(treeNode.id != "0"){
		$.ajax({
			url:"menuAction!findById.action",
		   	type:"POST",
		   	dataType:"json",
		   	data : {
		   		id : treeNode.id
		   	},
		   	async:true,
		  	error:function(request,textStatus, errorThrown){
			},
		   	success:function(data){
		   		formValidator();
		   		formatClearForm("dataform");
		   		addFormValidator.resetForm();
		   		if(data != null){
		   			$("#modelId").val(data.menu.id);
		   			$("#name").val(data.menu.name);
		   			$("#icon").val(data.menu.icon);
		   			$("#location").val(data.menu.location);
		   			$("#ordinal").val(data.menu.ordinal);
		   			$('#visible').val(data.menu.visible);
		   			if(data.menu.visible == "1"){
		   				$("#ifVisible").prop("checked",true);
		   			}else{
		   				$("#ifVisible").prop("checked",false);
		   			}
		   		}
		   	}
		});
	}else{
		toAddDepartment();
	}
}
function getMenuList() {
	toAddDepartment();
	$.ajax({
	   	url:"menuAction!findMenuTree.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(dataParam){
			$.fn.zTree.init($("#treeDemo"), setting, dataParam);
			$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
			fuzzySearch('treeDemo','#inputSearch',null,true); //初始化模糊搜索方法
		}
	});
}
function toAddDepartment() {
	$('#dataform .form-control').each(function(){
		$(this).val('');
	});
	$("#ifVisible").prop("checked",false);
	addFormValidator.resetForm();
	$('#visible').val(0);
}
function saveMenu(){
 	if(!formatValidate(addFormValidator)){
		return;
	}
	if($("#modelId").val()&&!$("#modelId").val()==('')){
		updateMenu();
	}else{
		addMenu();
	}
}
function addMenu(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var node = treeObj.getSelectedNodes()[0];
 	if(node == null && node == undefined){
 		$("#parentId").val(0);
	}else{
		if(node.id == "0"){
			$("#parentId").val(0);
		}else{
			$("#parentId").val(node.id);
		}
	}
	var param = $("#dataform").serialize();
	$.ajax({
		url:"menuAction!save.action",
	   	type:"POST",
	   	dataType:"json",
	   	data : param,
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		getMenuList();
	   		Modal.alert({
				msg: data,
				title: '标题',
				btnok: '确定',
				btncl:'取消'
			});
	   	}
	});
}
function updateMenu() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var node = treeObj.getSelectedNodes()[0];
	if(node == null && node == undefined){
		Modal.alert({
			msg: "请选择需要修改的菜单！",
			title: '标题',
			btnok: '确定',
			btncl:'取消'
		});
		return;
	}
	if(node == null && node == undefined){
		$("#parentId").val(0);
	}else{
		if(node.pId == null && node.pId == undefined){
			$("#parentId").val(0);
		}else{
			$("#parentId").val(node.pId);
		}
	}
	var param = $("#dataform").serialize();
 	$.ajax({
		url:"menuAction!update.action",
	   	type:"POST",
	   	dataType:"json",
	   	data : param,
	   	async:true,
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		getMenuList();
	   		Modal.alert({
				msg: data,
				title: '标题',
				btnok: '确定',
				btncl:'取消'
			});
	   	}
	}); 
}
function deleteMenu() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var node = treeObj.getSelectedNodes()[0];
	if(node == null && node == undefined){
		Modal.alert({
			msg: '请选择需要删除的菜单',
			title: '标题',
			btnok: '确定',
			btncl:'取消'
		});  
		return;
	}
	Modal.confirm({
		btnok: '确定',
		btncl:'取消',
		msg: "是否删除该菜单？"
	}).on( function (e) {
		if(e){
			if(node.children != null && node.children != undefined){
				Modal.alert({
					msg: "该菜单下存在子菜单，请先删除子菜单！",
					title: '标题',
					btnok: '确定',
					btncl:'取消'
				});
			}else{
				$.ajax({
				   	url:"menuAction!remove.action",
				   	type:"POST",
				   	dataType:"json",
				   	data : {
				   		id : node.id
				   	},
				   	async:true,
				  	error:function(request,textStatus, errorThrown){
					},
				   	success:function(data){
				   		$("#dataform input").val('');
				   		getMenuList();
				   		Modal.alert({
							msg: data,
							title: '标题',
							btnok: '确定',
							btncl:'取消'
						});
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
						message : '菜单名称不能为空！'
					}
				}
			},
			ordinal: {
				validators : {
					notEmpty : {
						message : '菜单序数不能为空！'
					}
				}
			},
		}
	})
}