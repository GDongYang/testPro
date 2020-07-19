var cardUrl= 'sealInfoAction!findPagination.action';
var addUrl='sealInfoAction!create.action';
var editUrl='sealInfoAction!update.action';
var deleteUrl='sealInfoAction!remove.action';
var addFormValidator;
var treeSearchFlag=false;//判断是否需要模糊搜索
//上传图片初始化
var obj ={};
$(document).ready(function(){
	ajaxRequest();
	formValidator();
	loadDepartment('treeDemo');
	selectConfig();//下拉框初始化
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
//树点击回调函数
function treeCallBack(treeName,treeId){
	$("#deptSearchId").val(treeName);
	$('#deptmentId').val(treeId);
	findCertTemp(treeId);
}
//新增
function addCard(){
	//上传图片初始化(新增)
	$("#certTempIda").val('');
	selectUpdated($("#certTempIda"));//下拉框变动更新
	$("#deptSearchId").val('浙江省');	
	$('#deptmentId').val('1');
	findCertTemp('1');
	okBtn=0;
    formValidator();
	formatClearForm("addForm");
	addFormValidator.resetForm();	
	formatDialog($("#addFormWrap"),{title:"新增",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交":function(){
		submit(this)
	}});
}
//修改
function updateCard(data){
	okBtn=1;
	formValidator();
	formatClearForm("addForm");
	$(".itemImg").attr('src','');
	addFormValidator.resetForm();
	findCertTemp(data.departmentId);
	//证件回显
	findRCertTemp(data.code);
	//图片回显
	findRImg(data.id);
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
function findItemByDept(departmentId,type) {
	$("#itemCodes").empty();
	$.ajax({
		cache : true,
		type : "get",
		url : 'itemAction!findList.action',
		data:{'departmentId':departmentId,'type':type},
		dataType : 'json',
		async : false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var htmlStr = "";
			if (data.result != null) {
				htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.result.length; i++) {
					htmlStr += "<option value=\""+data.result[i].id+"\">"
							+ data.result[i].name + "(" + data.result[i].departmentName + ")(" + data.result[i].code + ")" + "</option>";
				}
			}
			$("#itemCodes").append(htmlStr);
		}
	});
}
//发布数据
function updateSealActive(id) {
	Modal.confirm({
		msg:"是否发布该数据？"
	}).on( function (e) {
		if(e) {
			$.ajax({
				cache : true,
				type : "POST",
				url : 'sealInfoAction!updateActive.action',
				dataType : 'json',
				data : {'id':id},
				async : false,
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					Modal.alert({ msg:'发布成功！', title:'提示', btnok:'确定' }).on(function(e){
						ajaxRequest();
					});
				}
			});
		}
	});
}
//根据部门查询到部门下的证件 
function findCertTemp(departmentId) {
	var datas;
	var list;
	var count;	
	var dataStr = "departId=" + departmentId +"&pageSize=" + 100 + "&pageNum=" + 0;
	$.ajax({
		type : 'post',
		url : 'certTempAction!findPage.action',
		dataType : 'json',
		cache : false,
		async : false,
		data : dataStr,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			if (data.rows != null) {
				$("#certTempIda").empty();
				var allTemp = data.rows;
				
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < allTemp.length; i++) {
					htmlStr += "<option value=\""+allTemp[i].id+"\">"
							+ allTemp[i].name + "</option>";
				}
				$("#certTempIda").append(htmlStr);
				selectUpdated($("#certTempIda"));
			}
		}
	});
	
}
//根据code查询关联证件
function findRCertTemp(itemCode){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'sealInfoAction!findCertSeal.action',
		dataType : 'json',
		data : {
			code : itemCode
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			$("#certTempIda").val(data.certs);
			selectUpdated($("#certTempIda"));//下拉框变动更新
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
						message : '名称不能为空！'
					}
				}
			},
			code : {
				validators : {
					notEmpty : {
						message : '编码不能为空！'
					}
				}
			}
		}
	})
}
function getCardContent(data){
	var dataStr="<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20'>"
	+"<div class='cardBox'><div class='cardBody'><div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'>"
	if(data.visible==1){
		dataStr+="<img src='../build/image/9-1.png'/>";
	}else{
		dataStr+="<img src='../build/image/9.png'/>";
	}
	dataStr+="</div><div class='col-xs-9 col-sm-9 col-md-9 col-lg-9 base_padding-0'><div class='cardTitle' title='"+data.name+"' >"
	+(data.name ? data.name : '无')+"</div><div class='col-xs-9 col-sm-9 col-md-9 col-lg-9 base_padding-l-0'><div class='cardTitleSmall'>"
	+"印章编码</div><div class='cardMsg'>"+(data.code ? data.code : '无')+"</div></div></div></div>"
	+"<div class='cardFooter "+(data.visible==1?'':'disabled')+"'><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff' title='发布' onclick='updateSealActive("+data.id+")'>"
	+'<i class="fa fa-file-text-o"></i></div><div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff" title="修改" onclick="updateCard('+JSON.stringify(data).replace(/\"/g,"'")+')">'
	+"<i class='fa fa-edit'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4' title='删除' onclick='remove("+data.id+")'>"
	dataStr+="<i class='fa fa fa-trash-o'></i></div></div></div></div>";
	return dataStr;
}
//图片上传
function buttonClick(obj){
	if($('.itemImg').attr('src')){
		$('.itemImg').css('display','none');
		//$(".itemImg").attr('src','');
	}
	$(obj).prev().click();
};
function fileChange(file){
    var upload_file = $.trim($(file).val());    //获取上传文件
    $(file).prev().val(upload_file);     //赋值给自定义input框
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
//根据id查询关联图片
function findRImg(itemId){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'sealInfoAction!getImageById.action',
		dataType : 'json',
		data : {
			id : itemId
		},
		async : false,
		error : function(request, textStatus,errorThrown) {
			$('.itemImg').css({'display':'inline-block', 'width':'355px'});
			var srcStr = "http://127.0.0.1:8000/yztb/integratedCertificateService/sealInfoAction!getImageById.action?id="+itemId;		
			$(".itemImg").attr('src',srcStr);
		},
		success : function(data) {
//			$('.itemImg').css({'display':'inline-block'});
//			$(".itemImg").attr('alt','请上传图片！');
		}
	});
}

