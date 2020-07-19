var cardUrl='itemAction!findPaeTable.action';
var treeSearchFlag=true;//判断是否需要模糊搜索
var situationDatas=[];
var confirmFormValidator;
$(document).ready(function(){
	getUserPower();
	var windowHeight=window.innerHeight;
	$(".base_box-area-aqua").css("height",windowHeight-103);
	$(".leftBox").css("height",windowHeight-40);
	$(".treeBox").css("height",windowHeight-136);
	$(".tableCont").css("height",windowHeight-40);
	$('.listBox').css('height',windowHeight-130);
	loadDepartment('treeDemo','treeDemo2');
	formValidator();
	confirmFormValidator = $('#confirmform').data('bootstrapValidator');
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
				$("#copyBtn").show();
			}
		}
	});
}
//按事项名称、编码和内部编码查询
function search(){
	$("#tablewrap").bootstrapTable('selectPage',1);
}
//清除
function cleanSearch() {
	$('#searchForm .form-control').each(function(){
		$(this).val("");
	});
	$("#tablewrap").bootstrapTable('selectPage',1);
}
//请求数据
function ajaxRequest(params) {
	var pageSize = params.data.limit;
	var pageNum = params.data.offset / pageSize + 1;
	var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
	+ '&pageSize=' + pageSize+'&departmentId='+selectCode;	
	lastAjax = $.ajax({
		type : 'post',
		url : cardUrl,
		dataType : 'json',
		cache : false,
		async : true,
		data : dataStr,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			situationDatas = data.counts;
			var datas = data.rows?data.rows:{};
			var count = data.rows?data.total:0;
			params.success({
				total : count,
				rows : datas
			});
		}
	});
}
function confirm(){
	var selectTr=$("#tablewrap").bootstrapTable('getSelections');
	if(selectTr.length!=0){
		formValidator();
		formatClearForm("confirmform");
		confirmFormValidator.resetForm();	
		formatDialog($("#confirmFormWrap"),{title:"确认",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交":function(){
			submitConfirm(this,selectTr)
		}});
	}else{
		Modal.alert({ msg:'请选择确认内容！', title:'提示', btnok:'确定' });
	}
}
function submitConfirm(obj,selectArry){
	if(!formatValidate(confirmFormValidator)){
		return;
	}
	var radioStr='',itemIds=[],itemNames=[],itemInnerCodes=[];
	$('#confirmform input[type="radio"]').each(function(){
		if($(this).attr('checked')){
			radioStr=$(this).attr('valueSet');
		}
	});
	for(var i=0;i<selectArry.length;i++){
		itemIds.push(selectArry[i].id);
		itemNames.push(selectArry[i].name);
		itemInnerCodes.push(selectArry[i].innerCode);
	}
	$.ajax({
	   	url:"itemConfirmAction!confirmList.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:false,
	   	traditional:true,
	   	data:{'itemIds':itemIds,'itemNames':itemNames,'itemInnerCodes':itemInnerCodes,'memo':$('textarea[name="memo"]').val(),'status':radioStr},
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		if(data.code==1){
	   			Modal.alert({ msg:'提交成功！', title:'提示', btnok:'确定' });
	   		}else{
	   			Modal.alert({ msg:'提交失败！', title:'提示', btnok:'确定' });
	   		}
	   	}
	})
	$(obj).dialog("close");
}
function operation(value, row){
	var button='';
	button+= '<span onclick="getSituationInfo(this,'+JSON.stringify(value).replace(/\"/g,"'")
	+')"><span>详细信息</span> <i class="fa fa-angle-down"></i></span><span class="base_margin-l-10" onclick="uploadImg(\''+value+'\')">上传</span>'
	if(row.hasImg==1){
		button+='<span class="base_margin-l-10" onclick="previewImg(\''+value+'\')">预览</span>'
	}
	return button;
}
function previewImg(value){
	$.ajax({
	   	url:"itemConfirmAction!previewImg.action",
	   	type:"POST",
	   	dataType:"json",
	   	async:false,
	   	traditional:true,
	   	data:{'itemId':value},
	  	error:function(request,textStatus, errorThrown){
		},
	   	success:function(data){
	   		if(data.code==1){
	   			$('#previewImg').show();
	   			$('#previewImg img').attr('src','data:image/png;base64,'+data.img)
	   		}else{
	   			Modal.alert({ msg:'预览失败！', title:'提示', btnok:'确定' });
	   		}
	   	}
	})
}
function closeImg(){
	$('#previewImg').hide();
	$('#previewImg img').attr('src','');
}
function uploadImg(itemId){
	$('#uploadForm input').each(function(){
		$(this).val('');
	});
	$('.filesInputBox span').hide();
	$('#uploadForm input[name="itemId"]').val(itemId);
	formatDialog($("#uploadFormWrap"),{title:"上传",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交":function(){
		uploadFileAjax(this)
	}});
}
//图片上传
function buttonClick(obj){
	$(obj).prev().click();
};
function fileChange(file){
    var upload_file = $.trim($(file).val());    //获取上传文件
    $(file).prev().val(upload_file);     //赋值给自定义input框
};
function uploadFileAjax(_this){
	if($('#uploadForm input[name="file"]').val()==''){
		$('.filesInputBox span').show();
		return;
	}
	var ajax_option={
		url: "itemConfirmAction!uploadImg.action",
		type : 'post',
		error : function(request, textStatus, errorThrown) {
		},
		success:function(data){
			if(data.code==1){
				Modal.alert({ msg:'上传成功！', title:'提示', btnok:'确定' }).on(function(e){
					search();
				});
			}else{
				Modal.alert({ msg:'上传失败！', title:'提示', btnok:'确定' });
			}
		}
	};
	$('#uploadForm').ajaxSubmit(ajax_option);
	$(_this).dialog("close");
};
function confirmStatus(value, row){
	if(value==1){
		return '<span class="base_text-green">确认有效</span>'
	}else if(value==2){
		return '<span class="base_text-red">确认无效</span>'
	}else{
		return '<span class="base_text-orange">未确认</span>'
	}
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
//部门列表树点击回调函数
function treeCallBack(treeName,treeId){
	$("#tablewrap").bootstrapTable('selectPage',1);
}
//点击展示情形信息请求数据
function getSituationInfo(that,id){
	if($(that).find('i').hasClass('fa-angle-down')){
		$(that).find('i').removeClass('fa-angle-down').addClass('fa-angle-up');
	}else{
		$(that).find('i').removeClass('fa-angle-up').addClass('fa-angle-down');
	}
	if($(that).parents('tr').next().hasClass('detailInfoTr')){
		$(that).parents('tr').next().toggle();
		return;
	}
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
			$(that).parents('tr').siblings('.detailInfoTr').remove();
			$(that).parents('tr').after('<tr class="detailInfoTr"><td colspan="7">'+dataStr+'</td></tr>')
		}
	})
}
function formValidator(){
	$('#confirmform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			memo : {
				validators : {
					notEmpty : {
						message : '请输入理由！'
					}
				}
			},
		}
	})
}
