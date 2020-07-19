var cardUrl='serviceAccountAction!findPage.action';
var addUrl='serviceAccountAction!save.action';
var editUrl='serviceAccountAction!update.action';
var deleteUrl='serviceAccountAction!remove.action';
var pageNum = 1;
var addFormValidator;
var passFormValidator;
var treeSearchFlag=false;//判断是否需要模糊搜索
$(document).ready(function(){
	ajaxRequest();
	formValidator();
	loadDepartment('treeDemo');
	addFormValidator = $('#addForm').data('bootstrapValidator');
	passwordFormValidator();
	selectConfig();//下拉框初始化
	passFormValidator = $('#passForm').data('bootstrapValidator');
});
//新增
function addAccount(){
	$("#positionId").val('');
	selectUpdated($("#positionId"));//下拉框变动更新
	add();
}
//修改
function updateCard(data){	
	$('#departmentId').val(selectName);
	$('#deptId').val(selectCode);
	update(data);
	loadPosition(data.departmentId,data.positionId);
}
//修改业务账号密码
function updatePass(data) {
	passwordFormValidator();
	formatClearForm("passForm");
	passFormValidator.resetForm();
	$('#passForm .form-control').each(function(){
		for(var item in data){
			if(item==$(this).attr('name')){
				$(this).val(data[item]);
			}
		}
	});
	$('#pass_id').val(data);
	formatDialog($("#passFormWrap"),{title:"修改密码",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": insertPass});
}
function insertPass(){
	if(!formatValidate(passFormValidator)){
		return;	
	}
	ajaxParams = {
		url:"serviceAccountAction!updatePass.action",
		data:$('#passForm').serialize()
	};
	formatAjax(ajaxParams,tipsDialog);
	passFormValidator.resetForm();
	$(this).dialog("close");
}
//岗位下拉框数据
function loadPosition(departmentId,positionId) {
	$.ajax({
		url : "positionAction!findByDept.action",
		type : "POST",
		data : {"searchDept":departmentId},
		dataType : "json",
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			//初始化表单
			var info = data.result;
			var htmlStr = "";
			var htmlstr = "<option value=\"\"></option>";
			if(info) {
				for (var i = 0; i < info.length; i++) {
					htmlstr += "<option value=\""+info[i].id+"\">"
							+ info[i].name + "</option>";
					htmlStr += "<option value=\""+info[i].id+"\">"
							+ info[i].name + "</option>";
				}
			}
			$("#positionId").html(htmlstr);
			$("#positionId").val(positionId);
			selectUpdated("#positionId");
		}
	});
}
//新增树回调函数
function treeCallBack(treeName,treeId){
	$("#departmentId").val(treeName);
	$("#deptId").val(treeId);
	loadPosition(treeId,null);
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
						message : '账号拥有者不能为空！'
					}
				}
			},
			departmentId : {
				validators : {
					 notEmpty : {
						message : '部门不能为空！'
					},
				}
			},
			positionId : {
				validators : {
					 notEmpty : {
						message : '岗位不能为空！'
					}, 
				}
			}
		}
	})
}
function passwordFormValidator(){
	$('#passForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			oldPass : {
				validators : {
					notEmpty : {
						message : '请输入旧密码！'
					},
					remote : {
						url:'serviceAccountAction!checkOldPass.action',
						message:'旧密码不正确',
						type:'post',
						dataType:"json",
						data:function(validator) {
	                       return {
	                    	   name:$('#oldPass').val(),
	                    	   id:$('#pass_id').val(),
	                       };
	                    }
					}
				}
			},
			newPass : {
				validators : {
					 notEmpty : {
						message : '请输入新密码！'
					},
				}
			},
			queryPass : {
				validators : {		 
					identical:{
            			message: '确认密码和密码不同',
            			field: 'newPass'
            		}
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
	+data.name+"/"+data.username+"</div><div class='cardTitleSmall'>账号编码</div><div class='cardMsg'>"
	+data.code+"</div><div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 base_padding-l-0'><div class='cardTitleSmall'>"
	+"部门</div><div class='cardMsg'>"+data.departmentName+"</div></div>"
	+"<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'><div class='cardTitleSmall'>岗位</div>"
	+"<div class='cardMsg'>"+data.positionName+"</div></div></div></div>"
	+'<div class="cardFooter '+(data.active==1?'':'disabled')+'"><div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff" title="修改" onclick="updateCard('+JSON.stringify(data).replace(/\"/g,"'")+')">'
	+"<i class='fa fa-edit'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff' title='修改密码' onclick='updatePass("+data.id+")'>"
	+"<i class='fa fa-lock'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4' title='删除' onclick='remove("+data.id+")'>"
	dataStr+="<i class='fa fa-trash-o'></i></div></div></div></div>";
	return dataStr;
}