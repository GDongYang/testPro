var okBtn=0;//判断是删除还是修改
var addFormValidator;
$('input[type="radio"]').each(function(){
	$(this).change(function(){
		$(this).attr('checked',true);
		$(this).siblings().attr('checked',false);
	});
});
//新增
function add(){
	okBtn=0;
    formValidator();
	formatClearForm("addForm");
	addFormValidator.resetForm();	
	formatDialog($("#addFormWrap"),{title:"新增",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": insert});
}
//修改
function update(data) {
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
	formatDialog($("#addFormWrap"),{title:"修改",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": insert});
}
//新增修改确认
function insert(){
	if(!formatValidate(addFormValidator)){
		return;
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
			data:$('#addForm').serialize().substr(4)+radioStr
		};
	}
	formatAjax(ajaxParams,tipsDialog);
	addFormValidator.resetForm();
	$(this).dialog("close");
}
//删除
function remove(id) {
	Modal.confirm({
		msg:"是否删除？"
	}).on( function (e) {
		if(e) {
			$.ajax({
				cache : true,
				type : "POST",
				url : deleteUrl,
				dataType : 'json',
				data : {"id":id},
				async : false,
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					Modal.alert({ msg:'删除成功！', title:'提示', btnok:'确定' }).on(function(e){
						search();
					});
				}
			});
		}
	});
}