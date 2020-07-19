var cardUrl= 'userAction!findPage.action';
var addUrl='userAction!save.action';
var editUrl='userAction!update.action';
var deleteUrl='userAction!remove.action';
var addFormValidator;
var _authCode = "";
var last_select = "";
var load_success = false;
var treeSearchFlag=false;//判断是否需要模糊搜索
var canUpdatePwd = false;//判断是否可以提交修改密码
$(document).ready(function(){
	ajaxRequest();
	loadDepartment('treeDemo','treeDemo2');
	formValidator();
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
$("#right").click(function(){
    var $options=$("#select option:selected");
    var $remove=$options.remove();
    $remove.appendTo("#select2"); 
}); 
$("#rightAll").click(function(){
    var $options = $("#select option");
    $options.appendTo("#select2"); 
});
$("#left").click(function(){
    var $options=$("#select2 option:selected");
    var $remove=$options.remove();
    $remove.appendTo("#select");
});
$("#leftAll").click(function(){
    var $options=$("#select2 option");
    var $remove=$options.remove();
    $remove.appendTo("#select");
});
//搜索树点击回调函数
function treeCallBack(treeName,treeId){
	$("#deptSearchId").val(treeName);
	$('#deptmentId').val(treeId);
}
//新增树点击回调函数
function treeCallBack2(treeName,treeId){
	$("#deptSearchId2").val(treeName);
	$("#orgId").val(treeId);
	loadPosition3(treeId,null);
}
//分配用户角色
function assignRoles(id,active) {
	if(active) {
		$("#select").empty();
		$("#select2").empty();
		loadRole(getRole(), findUserRole(id));
		formatDialog($("#roleFormWrap"),{title:"分配角色",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交":function(){
			insertRole(this,id)
		}});
	} else {
		Modal.alert({ msg:'未激活用户无法分配角色！', title:'提示', btnok:'确定' });
	}
}
function insertRole(_this,id){
	var ids=[] ;
	var i=0;
	$("#select2 option").each(function() {
		ids[i]=$(this).val();
		i++;
	});
	if(ids.length != 0) {
		$.ajax({
			type : 'get',
			url : 'userAction!saveUserRole.action',
			dataType : 'json',
			traditional :true, 
			data : {
				"ids" : ids,
				"id" : id
			},
			error : function(request, textStatus, errorThrown) {
			},
			success : function(data) {
				Modal.alert({ msg:'用户分配角色成功！', title:'提示', btnok:'确定' }).on(function(e){
					ajaxRequest();
				});
			}
		}); 
	} else {
		Modal.alert({ msg:'请选择您要分配的角色！', title:'提示', btnok:'确定' });
	}
	$(_this).dialog("close");
}
function findUserRole(id){
	var roles = "";
	$.ajax({
		type : 'post',
		url : 'userAction!findUserRole.action',
		dataType : 'json',
		async: false,
		traditional :true, 
		data : {"id" : id},
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			roles = data.roles;
		}
	});
	return roles;
}
//获取角色列表
function getRole() {
	var roles = "";
	$.ajax({
		type : 'post',
		url : 'roleAction!findList.action',
		dataType : 'json',
		async: false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			roles = data.datas;
		}
	});
	return roles;
}
function loadRole(allroles, roles) {
	if(roles != null) {
		var flag;
		$.each(roles,function(i,role) {
			$("#select2").prepend('<option value="'+role.id+'">'+role.name+'</option>');
		});
		if(allroles != null){
			$.each(allroles,function(i,role) {
				var flag = true;
				$.each(roles,function(i,role1) {
					if(role.id == role1.id) {
						flag = false;
					}
				});
				if(flag) {
					$("#select").prepend('<option value="'+role.id+'">'+role.name+'</option>');
				}
			});
		}
	} else {
		if(allroles!=null){
			$.each(allroles,function(i,role) {
				$("#select").prepend('<option value="'+role.id+'">'+role.name+'</option>');
			});
		}
	}
}
//修改
function updateUser(data){
	loadPosition3(data.orgId,data.positionId);
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
		insertUser(this)
	}});
}
//修改确认
function insertUser(_this){
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
//分配岗位
function postHandler(data){
	last_select = "";
	loadPosition2(data.orgId,data.positionId);
	if(!load_success){
		Modal.alert({ msg:'该用户所属部门没有任何岗位！', title:'提示', btnok:'确定' });
		return;
	}
	formatDialog($("#setPostDiv"),{title:"分配岗位",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交":function(){
		insertHandler(this,data)
	}});
}
function insertHandler(_this,data){
	var tempUser = data;
	tempUser.positionId = last_select;
	$.ajax({
		type : 'post',
		url : 'userAction!assignPosition.action',
		dataType : 'json',
		data : {'ids':data.id,'position':last_select},
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			ajaxRequest();
			Modal.alert({ msg:'分配岗位成功！', title:'提示', btnok:'确定' });
		}
	});
	$(_this).dialog("close");
}
function formValidator(){
	$('#addForm').bootstrapValidator({
		live: 'enabled', //验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
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
						message : '姓名不能为空！'
					}
				}
			},
			username : {
				validators : {
					notEmpty : {
						message : '用户名不能为空！'
					},
					remote : {//ajax验证。server result:{"valid",true or false} （返回前台类型）
						url:'userAction!checkUserName.action',
						message:'已存在',
						type:'post',
						delay: 500, //ajax刷新的时间是0.5秒一次
						dataType:"json",
						data:function(validator) {
	                       return {
	                    	   name:$('#userName').val(),
	                    	   id:$('#add_id').val(),
	                       };
	                    }
					}
				}
			}
		}
	})
}
function getCardContent(data){
	var dataStr="<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20'>"
	+"<div class='cardBox userCradBox'><div class='cardBody "+(data.rCount ? 'userCradBodyBlue': 'userCradBodyGrey')+"'><div class='col-xs-3 col-sm-3 col-md-3 col-lg-3'>"
	if(data.active==1){
		dataStr+="<img src='../build/image/7-1.png'/>";
	}else{
		dataStr+="<img src='../build/image/7.png'/>";
	}
	dataStr+="</div><div class='col-xs-9 col-sm-9 col-md-9 col-lg-9 base_padding-0'><div class='cardTitle'>"
	+(data.username ? data.username : '无')+"/"+(data.name ? data.name : '无')+"</div><div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 base_padding-l-0'><div class='cardTitleSmall'>"
	+"所属单位</div><div class='cardMsg' title='"+data.orgName+"'>"+(data.orgName ? data.orgName : '无')+"</div></div>"
	+"<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'><div class='cardTitleSmall'>岗位</div>"
	+"<div class='cardMsg' title='"+loadPosition(data.orgId)+"'>"+loadPosition(data.orgId)+"</div></div></div></div>"
	
	+"<div class='cardBtns'><div class='col-sm-12 col-md-12 base_padding-lr-0'>"
	+'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 base_margin-t-10 base_padding-l-15">'
	+'<button class="btn btn-xs btn-primary activeBtn" onclick="updatePwd('+data.id+')">'+'修改密码'+'</button></div>'
	+'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 base_margin-t-10 base_padding-l-15">'
	+'<button class="btn btn-xs btn-primary activeBtn" onclick="active('+data.id+','+data.active+')">'+(data.active?'取消激活':'激活')+'</button></div>'
	+'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 base_margin-t-10 base_padding-l-10">'
	+'<button class="btn btn-xs btn-primary" onclick="postHandler('+JSON.stringify(data).replace(/\"/g,"'")+')">分配岗位</button></div>'
	+'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 base_padding-l-0 base_margin-t-10">'
	+"<button class='btn btn-xs btn-primary base_margin-l-5' id='sign_up'>证书绑定</button></div></div></div>"
	
	+'<div class="cardFooter '+(data.active==1?'':'disabled')+'"><div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff" title="修改" onclick="updateUser('+JSON.stringify(data).replace(/\"/g,"'")+')">'
	+"<i class='fa fa-edit'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff' title='删除' onclick='remove("+data.id+")'>"
	+"<i class='fa fa-trash-o'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4' title='分配角色' onclick='assignRoles("+data.id+","+data.active+")'>"
	dataStr+="<i class='fa fa-user-plus'></i></div></div></div></div>";
	return dataStr;
}
//load 岗位数据(数据回显用)
function loadPosition(orgId){
	var dept = '';
	$.ajax({
		type : 'POST',
		url : 'positionAction!findByDept.action',
		dataType : 'json',
		data:{searchDept:orgId},
		async: false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var deptArry=[];
			if(data.result!=null){
				for(var i=0;i<data.result.length;i++){
					deptArry.push(data.result[i].name)
				}
				dept=deptArry.join(',');
			}
		}
	});
	return dept
}
//load 岗位数据(分配岗位)
function loadPosition2(orgId,positionId){
	$.ajax({
		type : 'POST',
		url : 'positionAction!findByDept.action',
		dataType : 'json',
		data:{searchDept:orgId},
		async: false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var datas = data.result;
			if(datas != null){
				var htmlStr = "";
				$(datas).each(function(i,item){
					htmlStr += "<div class='sex'>";
					htmlStr += " <input type='radio' id='sex-man' name='radio-sex' value='"+item.id+"'";
					if(item.id == positionId){
						htmlStr += "checked=true";
						last_select=item.id;
					}
					htmlStr += " />";
					htmlStr += "<span>"+item.name+"</span></div>";
				});
				$('.user-position').html(htmlStr);
				$('.user-position input').each(function(){
					$(this).click(function(){
						last_select = $(this).val();
					});
				});
				load_success = true;
			}else{
				load_success = false;
			}
		}
	});
}
function loadPosition3(departmentId,positionId) {
	$.ajax({
		url : "positionAction!findByDept.action",
		type : "POST",
		data : {"searchDept":departmentId},
		dataType : "json",
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var info = data.result;
			var htmlstr = "<option style='display:none' value=\"\"></option>";
			if(info) {
				for (var i = 0; i < info.length; i++) {
					htmlstr += "<option value=\""+info[i].id+"\">"+ info[i].name + "</option>";
				}
			}
			$("#positionId").html(htmlstr);
			$("#positionId").val(positionId == null ? 0 : positionId);
		}
	});
}
//激活用户
function active(id,active) {
	var msg="";
	var msg1="";
	if(active){
		msg = "是否关闭该用户的激活状态？";
		msg1 = "激活状态已关闭！";
	} else {
		msg = "是否激活该用户？";
		msg1 = "激活成功！";
	}
	Modal.confirm({
		msg:msg
	}).on(function(e) {
		if(e){
			$.ajax({
				cache : true,
				type : "POST",
				url : 'userAction!active.action',
				dataType : 'json',
				data : {'id':id,'active':active},
				error : function(request, textStatus, errorThrown) {},
				success : function(data) {
					Modal.alert({ msg:msg1, title:'提示', btnok:'确定' }).on(function(e){});
					search();
				},
			});
		}
	});
}

//修改用户密码
function updatePwd(id) {
	var msg="";
	var msg1="";
	Modal.confirm({
		msg:'您是否确定要修改密码？'
	}).on(function(e) {
		if(e){
			/*清空输入值，并隐藏错误提示*/
			$("#oldPwd").val("");
			$("#newPwd").val("");
			$("#wrongOldPwd").addClass("base_hidden");
			$("#wrongNewPwd").addClass("base_hidden");
			canUpdatePwd = false;
			/*弹出选框*/
			formatClearForm("updatePwdForm");
			formatDialog($("#updatePwdFormWrap"),{title:"修改密码",dialogClass:"mydialog"},{"取消": formatDialogClose , "提交": function(){
				updatesUserPwd(id,this);
			}});
		}
	});
}
function updatesUserPwd(id,_this){
	/*核验旧密码是否正确*/
	var oldPwd = $("#oldPwd").val();
	var newPass = $("#newPwd").val();
	if(oldPwd != '' && newPass != ''){
		canUpdatePwd = true;
	}else if(oldPwd == ''){
		$("#wrongOldPwd").text("旧密码不能为空");
		("#wrongOldPwd").removeClass("base_hidden");
		canUpdatePwd = false;
	}else if(newPass == ''){
		$("#wrongNewPwd").text('新密码不能为空')
		$("#wrongNewPwd").removeClass("base_hidden");
		canUpdatePwd = false;
	}
	if(canUpdatePwd){
		$.ajax({
			cache : false,
			type : "POST",
			url : 'userAction!checkOldPass.action',
			dataType : 'json',
			data : {'oldPass':oldPwd,'id':id},
			error : function(request, textStatus, errorThrown) {},
			success : function(data) {
				if(data.valid  == true){
					//设置用户新的密码
					$.ajax({
						cache : false,
						type : "POST",
						url : 'userAction!updatePass.action',
						dataType : 'json',
						data : {'newPass':newPass,'id':id},
						error : function(request, textStatus, errorThrown) {},
						success : function(data) {
							_this.dialog("close");
							Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' }).on(function(e){});
							search();
						},
					});
				}else{
					//提示用户旧密码错误 重新输入
					$("#wrongOldPwd").text("密码错误清重新输入");
					$("#wrongOldPwd").removeClass("base_hidden");
				}
			},
		});
	}
}