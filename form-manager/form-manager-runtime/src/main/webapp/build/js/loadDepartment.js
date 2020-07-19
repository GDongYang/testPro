var deptObj = {};
var lastAjax = null;
var selectCode = "";
var selectName='';
var fullDepartment = "";
var departSetting = {
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
var departSetting2 = {
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
		onClick: zTreeOnClick2,
		onExpand:zTreeOnClick2
	}
};
//得到所有部门
//如果有模糊搜索,id一定要treeDemo,且为tree1
function loadDepartment(tree1,tree2) {
	var data= {id: "-1"};
	$.ajax({
	   	url:"departmentAction!findTreeByParentId.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:true,
	   	data:data,
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		if(data && data.departments){
				$(data.departments).each(function(i, item) {
					deptObj[item.id] = item.name;
					item.isParent = true;
				});
				$.fn.zTree.init($("#"+tree1), departSetting, data.departments);
				if(tree2){
					$.fn.zTree.init($("#"+tree2), departSetting2, data.departments);
				}
				
	   		} else {
	   			var list= [];
				$.fn.zTree.init($("#"+tree1), departSetting, list);
				if(tree2){
					$.fn.zTree.init($("#"+tree2), departSetting2, list);
				}
	   		}
	   		if(treeSearchFlag){
	   			fuzzySearch('treeDemo','#inputSearch',null,true); //初始化模糊搜索方法
	   		}
		}
	});
}
//树节点点击事件(树id统一叫treeDemo)
function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode.id == 0) {
		selectCode = "";
	} else {
		if(treeNode.tId.indexOf("treeDemo3") == -1){
			selectCode = treeNode.id;
		}
		selectName=treeNode.name;
		if(treeNode.oldname != null || treeNode.oldname != undefined){
			selectName = treeNode.oldname;
		}
		var id = treeNode.id;
		var data= {id:id}
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
						});
	                 if(treeNode.tId.indexOf("treeDemo3") != -1){
	                   	   $.fn.zTree.getZTreeObj("treeDemo3").addNodes(treeNode,eval(data.departments));
	                  }else if(treeNode.tId.indexOf("treeDemo") != -1){
	                 	   $.fn.zTree.getZTreeObj("treeDemo").addNodes(treeNode,eval(data.departments));
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
	/*
	 *回调函数处理不同页面点击事件
	 *需传参tree的name,tree的id
	 * */
	if(treeNode.tId.indexOf("treeDemo3") != -1){
		treeCallBack3(treeNode.name,treeNode.id);
	}else{
		treeCallBack(treeNode.name,treeNode.id)
	}
}
//树节点点击事件(树id统一叫treeDemo2)
function zTreeOnClick2(event, treeId, treeNode) {
	if (treeNode.id == 0) {
		selectCode = "";
	} else {
        //selectCode = treeNode.id;
        var id = treeNode.id;
		selectName=treeNode.name;
		//var id = selectCode;
		var data= {id:id}
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
						});
						if(treeNode.tId.indexOf("treeDemo2") != -1){
	                 	   $.fn.zTree.getZTreeObj("treeDemo2").addNodes(treeNode,eval(data.departments));
	                 	}
	                 	if(treeNode.tId.indexOf("treeDemo4") != -1){
	                   	   $.fn.zTree.getZTreeObj("treeDemo4").addNodes(treeNode,eval(data.departments));
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
	/*
	 *回调函数处理不同页面点击事件
	 *需传参tree的name,tree的id
	 * */
	fullDepartment = "";
	var allTreeNode = treeNode.getPath();
	//console.log("节点长度为" + allTreeNode.length)
	if(allTreeNode.length <= 2){
		for(var i = 0;i < allTreeNode.length;i++){
			fullDepartment += allTreeNode[i].name + " / " ;
		}
	}else{
		for(var i = 2;i < allTreeNode.length;i++){
			////console.log("节点名为" + allTreeNode[i].name);
			fullDepartment += allTreeNode[i].name + " / " ;
		}
	}
	fullDepartment = fullDepartment.substr(0,fullDepartment.lastIndexOf(' /'));
	if(treeNode.tId.indexOf("treeDemo2") != -1){
		treeCallBack2(fullDepartment,treeNode.id);
	}
	if(treeNode.tId.indexOf("treeDemo4") != -1){
		treeCallBack4(fullDepartment,treeNode.id);
	}
}
function showMenu(menuContentId){
	$('#'+menuContentId).removeClass('base_hidden');
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu(menuContentId) {
	$("."+menuContentId).addClass('base_hidden');
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == 'deptSearchId' || event.target.id == 'menuContent' || $(event.target).parents("#menuContent").length>0||event.target.id == 'deptSearchId2' 
	|| event.target.id == 'menuContent2' || $(event.target).parents("#menuContent2").length>0 || event.target.id == 'menuContent1' || $(event.target).parents("#menuContent1").length>0)) {
		hideMenu('menuContent');
	}
}