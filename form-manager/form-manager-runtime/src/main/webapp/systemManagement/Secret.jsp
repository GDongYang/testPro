<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>密钥管理</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="icon,page,validator" name="p"/>
		</jsp:include>		
		<link rel="stylesheet" type="text/css" href="../build/css/cardCss.css?v=2" />
	</head>
	<body class="base_padding-20 base_background-EEF6FB">
		<form class="form form-inline" id="searchForm">
			<label class='base_margin-r-10'>所属平台:</label>
  			<input type="text" class="form-control" id="platform" name="platform" placeholder="所属平台" /> 
  			<button type="button" class="btn btn-primary btn-sm" onClick="search()"><i class='fa fa-search base_margin-r-5'></i>查询</button>
  			<button type="button" class="btn btn-primary btn-sm" onClick="clean()"><i class='fa fa-trash-o base_margin-r-5'></i>清空</button>
  		</form>				
		<div class='row base_margin-t-20'>
  			<div class='col-xs-12 col-sm-6 col-md-3 col-lg-3 base_margin-b-20' id='addCard' onclick='add()'>
  				<div class='cardBox base_height-190 base_line-height-190 text-center'>
  					<img src='../build/image/addNew.png' height='50%'/>
  				</div>
  			</div>
  		</div>
  		<div class="fixed-table-pagination">
			<div class="pull-left">
				<span>第&nbsp;<span id="pageNum">1</span>&nbsp;页&nbsp;/&nbsp;共&nbsp;<span id="totalPages"></span>&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;<span id="total"></span>&nbsp;条记录</span>
			</div>
			<div class="pull-right" id="paginationUl"></div>
			<div class="clearfix"></div>
		</div>
  		<div style="display: none;">
    		<!--新增、修改 -->
           	<div id="addFormWrap" class="addFormWrap">
				<form class="form-horizontal" id="addForm" name="addform">
					<input id="id" type="hidden" class="form-control" name="id" value="0">
					<div class="form-group">
						<label class="col-sm-3 control-label">所属平台：</label>
						<div class="col-sm-8">
							<input class="form-control" id='platform' name="platform" placeholder="所属平台"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">应用KEY：</label>
						<div class="col-sm-8">
							<input class="form-control" id='appKey' name="appKey" placeholder="应用KEY"/>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label">应用密钥：</label>
						<div class="col-sm-8">
							<input class="form-control" id='appSecret' name="appSecret" placeholder="应用密钥"/>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label">请求密钥：</label>
						<div class="col-sm-8">
							<input class="form-control" id='requestSecret' name="requestSecret" placeholder="请求密钥"/>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label">刷新密钥：</label>
						<div class="col-sm-8">
							<input class="form-control" id='refreshSecret' name="refreshSecret" placeholder="刷新密钥"/>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label">请求密钥更新时间戳：</label>
						<div class="col-sm-8">
							<input class="form-control" id='requestSecretEndTime' name="requestSecretEndTime" placeholder="请求密钥更新时间戳"/>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label">刷新密钥更新时间戳：</label>
						<div class="col-sm-8">
							<input class="form-control" id='refreshSecretEndTime' name="refreshSecretEndTime" placeholder="刷新密钥更新时间戳"/>
						</div>
					</div>	
				</form>
			</div>
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="page,validator" name="p"/>
		</jsp:include>		
		<script type="text/javascript" src='../build/js/cardAjax.js'></script>
		<script type="text/javascript" src='../build/js/addEditDeleteModal.js'></script>
		<script type="text/javascript">
		var cardUrl= 'secretAction!findPage.action';
		var addUrl='secretAction!save.action';
		var editUrl='secretAction!update.action';
		var deleteUrl='secretAction!remove.action';
		var addFormValidator;
		var roleId=0;
		var deptObj = {};
		var lastAjax = null;
		var selectCode = "";
		var selectName='';
		var rId = null;
		
		$(document).ready(function(){
			ajaxRequest();
			formValidator();
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
		
		function sideAddModalClose(){
			$('.sideAddModal').addClass('base_hidden');
			roleId=0;
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
								url:'secretAction!isExists.action',
								message:'已存在',
								type:'post',
								dataType:"json",
								data:function(validator) {
			                       return {
			                    	   appKey:$('#appKey').val()
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
			+(data.platform ? data.platform : '无')+"</div><div class='cardTitleSmall'>appKey</div><div class='cardMsg'>"
			+(data.appKey ? data.appKey : '无')+"</div><div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 base_padding-l-0'><div class='cardTitleSmall'>"
			+"应用密钥</div><div class='cardMsg'>"+(data.appSecret ? data.appSecret : '无')+"</div></div></div></div>";
			
			dataStr+='<div class="cardFooter '+(data.active==1?'':'disabled')+'"><div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 border-r-fff" title="修改" onclick="updateRole('+JSON.stringify(data).replace(/\"/g,"'")+')">'
			+"<i class='fa fa-edit'></i></div><div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 border-r-fff' title='删除' onclick='remove("+data.id+")'>";
			dataStr+="<i class='fa fa-share-alt-square'></i></div></div></div></div>";
			return dataStr;
		}
		


		</script>
	</body>
</html>