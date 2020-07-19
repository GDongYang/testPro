var userTable;	//用户列表
			var pageNum = 1;
			var pageSize = 10;
			var elementTable;
			var index = 1;
			
			function idFormatter(value, row) {
				return index++;
			}
			
			$(function() {
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				selectConfig();//下拉框初始化
				loadDepart();
				userTable = $('#tablewrap');
				
				//bootstrapTable单击事件
				userTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
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
			    
			    $("#org").change(function(){
					loadPosition2($("#org").val(),null);
				});
			    
			});
			
			function formatActive(val, row) {
				if(val)
					return '<font style="color:#0000FF">已激活</font>';
				else 
					return '<font style="color:#FF0000">未激活</font>';
			}
			
			function formatSex(val, row) {
				if(val == 1)
					return "男";
				else if(val == 0)
					return "女";
				else 
					return "-";
			}
			
			function formatDate(val,rec)
			{
				if(val)
					return localeDate(val);
				else return ""
			}
			
			function localeDate(val)
			{
				var dateStr = val.toString();
				dateStr = dateStr.substring(0,10);
				dateStr= dateStr.replace(/-/g,'/');
				var dt = new Date(dateStr);
				var year = dt.getFullYear();
				var month = dt.getMonth() + 1;
				var day = dt.getDate();
	
				dateStr = val.replace(/T/g, " ");
				return dateStr;
			}
			
			function formatRCount(val) {
				if(val == 0){
					return "<span class=\"label label-danger\">否</span>";
				}else{
					return "<span class=\"label label-info\">是</span>";
				}
			}
			
			function loadDepart() {
				$.ajax({
					type : 'POST',
					url : 'departmentAction!findList.action',
					dataType : 'json',
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						$.each(data.departments,function(i,org){
							$("#org").append('<option value="'+org.id+'">'+org.name+'</option>');
							$("#department").append('<option value="'+org.id+'">'+org.name+'</option>');
						});
						selectUpdated($("#org"));//下拉框变动更新
						selectUpdated($("#department"));//下拉框变动更新
					}
				});
			}
			
			//刷新页面
			function refresh() {
				clear1();
                dataTable.bootstrapTable('refresh');
			}
			var tempPosition = {};
			var load_position = false;
			//对象列表的ajax请求
			function ajaxRequest(params) {
				if(!load_position){
					loadAllPosition();
				}
				var pageSize = params.data.limit;
				var pageNum = params.data.offset / pageSize + 1;
				index = params.data.offset + 1;
				var sort = params.data.sort ? params.data.sort : "id";
				var order = params.data.order ? params.data.order : "desc";
				var datas;
				var userlist;
				var count;
				var dataStr =$('#searchForm').serialize() + '&pageNum=' + pageNum
						+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'userAction!findPage.action',
					dataType : 'json',
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						datas = data.page;
						if (datas != null)
							count = datas.count;
						userlist = datas.items ? datas.items : [];
	
						params.success({
							total : count,
							rows : userlist
						});
						params.complete();
						drawICheck("tablewrap");
					}
				});
			}
			
			//加载所有岗位
			function loadAllPosition(){
				$.ajax({
					type : 'POST',
					url : 'positionAction!findAll.action',
					dataType : 'json',
					async: false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//alert(JSON.stringify(data));
						load_position = true;
						$(data.result).each(function(i,item){
							tempPosition[item.id] = item.name;
						});
					}
				});
			}
			
			function formatPosition(val, row){
				return tempPosition[val];
			}
			
			//查询
			function search() {
				userTable.bootstrapTable('selectPage', 1);
			}
			
			//清除
			function clear1() {
				pageNum = 1;
				pageSize = 10;
				$('#usernameSearch').val("");
				$('#nameSearch').val("");
				$('#certNo').val("");
				$('#department').val("");
				selectUpdated($("#department"));//下拉框变动更新
			}
			
			//新增用户
			function add() {
				//loadDepart();
				$("#dataform input").val("");
				$("#dataform select").val(0);
				$("#userNameDiv").css("display","block");
				var dialog = getJqueryDialog();
		   		dialog.Container = $("#dataFormWrap");
		   		dialog.Title = "用户新增";
		   		dialog.CloseOperation = "destroy";
		   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataform"));};
				//dialog.Open = function(){tipsRegionHintRemoveVerify($("#dataform"));};
		   		dialog.ButtonJSON = {
					"取消" : function() {
						$(this).dialog("close");
					},
					"确定" : function() {
						if (tipsRegionValidator($("#dataform"))) {
							//服务器验证
							var url = "userAction!checkUserName.action";
							var data = {id:$("#dataform #id").val(),username:$('#dataform #userName').val()};
							if (serversValidator(url,data)) {
								var dataStr = $("#dataform").serialize()+"&orgName="+$("#org").find("option:selected").text();
								$.ajax({
									type : 'POST',
									url : 'userAction!save.action',
									dataType : 'json',
									data : dataStr,
									error : function(request, textStatus, errorThrown) {
										//fxShowAjaxError(request, textStatus, errorThrown);
									},
									success : function(data) {
										userTable.bootstrapTable(('refresh'));
										Modal.alert({ msg:'新增用户成功！', title:'提示', btnok:'确定' }).on(function(e){
											
										});
									}
								});
								$(this).dialog("close");
							} else {
								addTipsHint($("#dataform #userName"),{msg:"用户名已存在"});
							}
						}
					}
		   		};
		   		dialog.show();
		   		
		   		selectUpdated($("#org"));//下拉框变动更新
		   		selectUpdated($("#positionId"));
			}
			
			//修改用户
			function update() {
				//loadDepart();
				$("#dataform input").val("");
				$("#dataform select").val(0);
				$("#userNameDiv").css("display","none");
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					//console.log(info);
					var htmlStr = "<input type=\"hidden\" name=\"id\" value=\""+info[0].id+"\"/>";
					//htmlStr += "<input type=\"hidden\" name=\"positionId\" value=\""+info[0].positionId+"\"/>";
					htmlStr += "<input type=\"hidden\" name=\"active\" value=\""+info[0].active+"\"/>";
					$("#hiddenDiv").html(htmlStr);
					$("#userName").val(info[0].username);
					$("#name").val(info[0].name);
					$("#workNo").val(info[0].workNo);
					$("#sex").val(info[0].sex);
					$("#org").val(info[0].orgId);
					$("#mobilePhone").val(info[0].mobilePhone);
					$("#officePhone").val(info[0].officePhone);
					$("#email").val(info[0].email);
					$("#virtualMobilePhone").val(info[0].virtualMobilePhone);
					//$("#fax").val(info[0].fax);
					$("#cardNo").val(info[0].cardNo);
					$("#certNo").val(info[0].certNo);
					loadPosition2(info[0].departmentId,info[0].positionId);
					
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#dataFormWrap");
			   		dialog.Title = "用户修改";
			   		dialog.CloseOperation = "destroy";
			   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#dataform"));};
					//dialog.Open = function(){tipsRegionHintRemoveVerify($("#dataform"));};
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator($("#dataform"))) {
								//服务器验证
//								var url = "userAction!checkUserName.action";
//								var data = {id:info[0].id,username:$('#dataform #userName').val()};
//								if (serversValidator(url,data)) {
									var dataStr = $("#dataform").serialize()+"&orgName="+$("#org").find("option:selected").text();
									//console.log(dataStr)
									$.ajax({
										type : 'post',
										url : 'userAction!update.action',
										dataType : 'json',
										data : dataStr,
										error : function(request, textStatus, errorThrown) {
											//fxShowAjaxError(request, textStatus, errorThrown);
										},
										success : function(data) {
											userTable.bootstrapTable(('refresh'));
											Modal.alert({ msg:'修改用户成功！', title:'提示', btnok:'确定' }).on(function(e){
												
											});
										}
									});
									$(this).dialog("close");
//								} else {
//									addTipsHint($("#dataform #userName"),{msg:"用户名已存在"});
//								}
							}
						}
			   		};
			   		dialog.show();
				} else {
					Modal.alert({ msg:'请选择要修改的数据！', title:'提示', btnok:'确定' });
				}
				
		   		selectUpdated($("#sex"));
		   		selectUpdated($("#org"));
			}
			
			//删除用户
			function remove() {
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					Modal.confirm({
						msg: "是否删除该用户？"
					}).on(function(e) {
						if(e){
							$.ajax({
								cache : true,
								type : "POST",
								url : 'userAction!remove.action',
								dataType : 'json',
								data : info[0],
								error : function(request, textStatus, errorThrown) {
									//fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									userTable.bootstrapTable(('refresh'));
									Modal.alert({ msg:'用户删除成功！', title:'提示', btnok:'确定' }).on(function(e){
										
									});
								},
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要删除的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//激活用户
			function active() {
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					var msg="";
					var msg1="";
					if(info[0].active){
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
								data : info[0],
								error : function(request, textStatus, errorThrown) {
									//fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									userTable.bootstrapTable(('refresh'));
									Modal.alert({ msg:msg1, title:'提示', btnok:'确定' }).on(function(e){
										
									});
								},
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要激活的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//重置用户密码
			function passReset(){
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					Modal.confirm({
						msg:"是否重置该用户密码？"
					}).on(function(e) {
						if(e) {
							$.ajax({
								cache : true,
								type : "POST",
								url : 'userAction!passWordReset.action',
								dataType : 'json',
								data : info[0],
								error : function(request, textStatus, errorThrown) {
									//fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									userTable.bootstrapTable(('refresh'));
									Modal.alert({ msg:'用户重置密码成功！', title:'提示', btnok:'确定' }).on(function(e){

									});
								},
							});
						}
					});
				} else {
					Modal.alert({ msg:'请选择要重置密码的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//修改用户密码
			function updatePass() {
				$("#passForm input").val("");
				
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					$("#Id").val(info[0].id);
					
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#passFormWrap");
			   		dialog.Title = "修改密码";
			   		if (!fBrowserRedirect()) {
			   			dialog.Width = 540;
			   		}
			   		dialog.Height = 250;
			   		dialog.CloseOperation = "destroy";
			   		dialog.BeforeClose = function(){tipsRegionHintAddVerify($("#passForm"));};
					//dialog.Open = function(){tipsRegionHintRemoveVerify($("#passForm"));};
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							if (tipsRegionValidator($("#passForm"))) {
								//服务器验证
								var url = "userAction!checkOldPass.action";
								var data = {id:$("#passForm #Id").val(),oldPass:$('#passForm #oldPass').val()};
								if (serversValidator(url,data)) {
	 								var dataStr = $("#passForm").serialize();
									$.ajax({
										type : 'get',
										url : 'userAction!updatePass.action',
										dataType : 'json',
										data : dataStr,
										error : function(request, textStatus, errorThrown) {
											//fxShowAjaxError(request, textStatus, errorThrown);
										},
										success : function(data) {
											userTable.bootstrapTable(('refresh'));
											Modal.alert({ msg:'用户修改密码成功！', title:'提示', btnok:'确定' }).on(function(e){
		
											});
										}
									});
									$(this).dialog("close");
								} else {
									addTipsHint($("#oldPass"),{msg:"密码错误"});
								}
							}
						}
			   		};
			   		dialog.show();
				} else {
					Modal.alert({ msg:'请选择要修改密码的数据！', title:'提示', btnok:'确定' });
				}
			}
			
			//分配用户角色
			function assignRoles() {
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					if(info[0].active) {
						$("#select").empty();
						$("#select2").empty();
						loadRole(getRole(), findUserRole(info[0].id));
						var dialog = getJqueryDialog();
				   		dialog.Container = $("#roleFormWrap");
				   		dialog.Title = "分配角色";
				   		if (!fBrowserRedirect()) {
				   			dialog.Width = 540;
				   		}
				   		dialog.Height = 420;
				   		dialog.CloseOperation = "destroy";
				   		dialog.ButtonJSON = {
							"取消" : function() {
								$(this).dialog("close");
							},
							"确定" : function() {
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
											"id" : info[0].id
										},
										error : function(request, textStatus, errorThrown) {
											//fxShowAjaxError(request, textStatus, errorThrown);
										},
										success : function(data) {
											//refresh();
											userTable.bootstrapTable(('refresh'));
											Modal.alert({ msg:'用户分配角色成功！', title:'提示', btnok:'确定' }).on(function(e){
												
											});
										}
									}); 
									$(this).dialog("close");
								} else {
									Modal.alert({ msg:'请选择您要分配的角色！', title:'提示', btnok:'确定' });
								}
							}
				   		};
				   		dialog.show();
					} else {
						Modal.alert({ msg:'未激活用户无法分配角色！', title:'提示', btnok:'确定' });
					}
				} else {
					Modal.alert({ msg:'请选择您要分配角色的用户！', title:'提示', btnok:'确定' });
				}
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
						//fxShowAjaxError(request, textStatus, errorThrown);
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
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						roles = data.datas;
					}
				});
				return roles;
			}
			
			function loadRole(allroles, roles) {
				if(roles != "") {
					var flag;
					$.each(roles,function(i,role) {
						$("#select2").prepend('<option value="'+role.id+'">'+role.name+'</option>');
					});
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
				} else {
					$.each(allroles,function(i,role) {
						$("#select").prepend('<option value="'+role.id+'">'+role.name+'</option>');
					});
				}
			}
			
			function unBind() {
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0){
					if(info[0].certNo != null && info[0].certNo != "") {
						 $.ajax({
							   	url:"userAction!unBind.action",
							   	data:{id:info[0].id},
							   	type:"POST",
							   	error:function(request,textStatus, errorThrown){
							   		//alert("请求出错..");
							   		//console.log(textStatus);
								},
							   	success:function(data){
							   		userTable.bootstrapTable(('refresh'));
							   		Modal.alert({ msg:'解绑成功！', title:'提示', btnok:'确定' });
							   	}
						 });
					} else {
						Modal.alert({ msg:'该用户尚未绑定，无需解绑！', title:'提示', btnok:'确定' });
					}
				} else {
					Modal.alert({ msg:'请选择您要解绑的用户！', title:'提示', btnok:'确定' });
				}
			}
			
			var last_select = "";
			var load_success = false;
			//分配岗位
			function postHandler(){
				var info = userTable.bootstrapTable("getSelections");
				if(info != 0) {
					last_select = "";
					//alert(JSON.stringify(info));
					loadPosition(info[0].orgId,info[0].positionId);
					if(!load_success){
						Modal.alert({ msg:'该用户所属部门没有任何岗位！', title:'提示', btnok:'确定' });
						return;
					}
					var dialog = getJqueryDialog();
			   		dialog.Container = $("#setPostDiv");
			   		dialog.Title = "分配岗位";
			   		dialog.Width = 280;
			   		dialog.Height = 300;
			   		dialog.CloseOperation = "destroy";
			   		dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							var tempUser = info[0];
							tempUser.positionId = last_select;
							$.ajax({
								type : 'post',
								url : 'userAction!update.action',
								dataType : 'json',
								data : tempUser,
								error : function(request, textStatus, errorThrown) {
									//fxShowAjaxError(request, textStatus, errorThrown);
								},
								success : function(data) {
									userTable.bootstrapTable(('refresh'));
									Modal.alert({ msg:'分配岗位成功！', title:'提示', btnok:'确定' });
								}
							});
							$(this).dialog("close");
						}
					};
			   		dialog.show();
				}else{
					Modal.alert({ msg:'请选择数据！', title:'提示', btnok:'确定' });
				}
			}
			//load 岗位数据
			function loadPosition(orgId,positionId){
				$.ajax({
					type : 'POST',
					url : 'positionAction!findByDept.action',
					dataType : 'json',
					data:{searchDept:orgId},
					async: false,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						var datas = data.result;
						if(datas != null){
							var htmlStr = "";
							$(datas).each(function(i,item){
								htmlStr += "<div class=\"sex\">";
								htmlStr += " <input type=\"radio\" id=\"sex-man\" name=\"radio-sex\" value=\""+item.id+"\"";
								if(item.id == positionId){
									htmlStr += " checked";
								}
								htmlStr += " />";
								htmlStr += " <label for=\"check2\">"+item.name+"</label>";
								htmlStr += "</div>";
							});
							$('.user-position').html(htmlStr);
							$('.user-position input').each(function(){
								var self = $(this),
	                            label = self.next(),
	                            label_text = label.text();
	                            label.remove();
			                    self.iCheck({
			                         checkboxClass: 'icheckbox_sm-blue',
			                         radioClass: 'radio_sm-blue',
			                         insert: label_text
			                    });
							});
							$('.user-position input').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
								last_select = $(this).val();
						    }); 
							load_success = true;
						}else{
							load_success = false;
						}
					}
				});
			}
			
			function loadPosition2(departmentId,positionId) {
				$.ajax({
					url : "positionAction!findByDept.action",
					type : "POST",
					data : {"searchDept":departmentId},
					dataType : "json",
					error : function(request, textStatus, errorThrown) {
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						//console.log(data)
						//初始化表单
						var info = data.result;
						var htmlstr = "";
						if(info) {
							for (var i = 0; i < info.length; i++) {
								htmlstr += "<option value=\""+info[i].id+"\">"+ info[i].name + "</option>";
							}
						}
						$("#positionId").html(htmlstr);
						$("#positionId").val(positionId == null ? 0 : positionId);
						selectUpdated("positionId");
					}
				});
			}
			
			
			//-----------------------------------------------证书代码-----------start------
			var _authCode = "";
			//获得挑战码
			function getAuthCode(){
				 $.ajax({
					   	url:"userSessionAction!authCode.action",
					   	type:"POST",
					   	async: false,
					   	error:function(request,textStatus, errorThrown){
					   		//alert("请求出错..");
					   		//console.log(textStatus);
						},
					   	success:function(data){
					   		_authCode = data.authCode;
					   	}
				  });
			}
			/* 使用哪种JS接口(1:COM接口(仅用于IE)；2:Websocket接口) */
			var _usingInterfaceType = 2;
			
			/* ZJCA接口对象 */
			var _zjcaCMT = null;
			
			/* 当前要显示的证书用途类型(1:签名;2:加密) */
			var _certUsage = 1;
			
			/* 证书信息列表 */
			var _certInfoArray = null;
			
			/* 当前选择的证书 */
			var _selCertInfo = null;
			
			var _certInfo = "";
			
			/* 当前选择的签名结果格式(默认P1) */
			var _signFormat = 1;

			// 版本
			var g_ZJCAClientVer = "1.0.0.0";

			//游览器类型判断是否是IE
			
			function isIE(){
				if (!!window.ActiveXObject || "ActiveXObject" in window){
			        return true;
				}
			    else{
			        return false;
			    }
			}
			

			/*
			 *	页面初始化
			 */
			window.onload = function() {	
				//客户端检测
				if (isIE()) {
					_usingInterfaceType = 1;
					try{
						var versionObj = new ActiveXObject("ZJCAKeyManagerSF.ZJCAVersion.1");
						g_ZJCAClientVer = versionObj.GetVersion();
					}catch(e){
						g_ZJCAClientVer = "1.0.0.0";
					}				
				}else{
					_usingInterfaceType = 2;
				}

				if (1 == _usingInterfaceType) {
					_zjcaCMT = new zjca_COM(onErrorCallbackFunc, onKeyEventCallbackFunc);	
				}
				else {
					_zjcaCMT = new zjca_Websocket(onErrorCallbackFunc, onKeyEventCallbackFunc, onResultCallbackFunc);
				}
			
				try {
					//初始化接口			
					_zjcaCMT.init();
					
					if (1 == _usingInterfaceType) 
					{
						//枚举所有证书
						var certArrary = _zjcaCMT.getCertList(-1, 0, _certUsage);
						FillCertInfoList(certArrary);
					}
				}
				catch (e) {
					onErrorCallbackFunc(e.number, e.description);			
				}
			}
			
			/*
			 *	页面关闭
			 */
			window.onunload = function () {
				if (_zjcaCMT) {
					_zjcaCMT.finaled();
				}
			}
			
			/*
			 *	错误事件回调函数
			 */
			function onErrorCallbackFunc(name, code, message) {
				stop_loading();
				var errCode = 0;
				if (code < 0) {
					var errNumber = 0x100000000;
					errNumber += parseInt(code);
					errCode = errNumber.toString(16);
				}
				else {
					var errNumber = parseInt(code);
					errCode = errNumber.toString(16);
				}
				var errMsg = "操作失败！\n错误代码：0x";
				errMsg += errCode;
				errMsg += "\n";
				errMsg += "错误信息：";
				errMsg += message;
				//alert(errMsg);	
				//console.log(errMsg);
			}
			
			/*
			 *	设备事件处理函数
			 */
			function onKeyEventCallbackFunc(type, index, name) {
				if (_zjcaCMT) {
					//枚举所有签名证书
					var certArrary = _zjcaCMT.getCertList(-1, 0, _certUsage);	
					
					// 如果结果同步返回，则显示结果		
					if (typeof(certArrary) != "undefined") {
						FillCertInfoList(certArrary);
					}
					else {
						loading();	
					}
				}
			}		
			
			/*
			 *	WebSocket接口返回结果的回调函数
			 */
			function onResultCallbackFunc(respType, param1) {
				stop_loading();
				// 初始化成功
				if (RESP_INIT == respType) {
					_zjcaCMT.getCertList(-1, 0, _certUsage);
				}
				// 返回证书信息列表
				else if (RESP_GET_CERTIFICATE_LIST == respType) {
					FillCertInfoList(param1);				
				}
			}
			//var count = 0;
	        /*
	         *  选择证书
	         */
			function OnSelCertChanged(selectvalue) {
	        	if(handlerCount > 1){
	        		return ;
	        	}
				//count++;
			    var certIndex = selectvalue;

			    if (isNaN(certIndex) || certIndex == -1) {
			    	// alert("请插入证书");
			    	return;
			    }
				// console.log(certIndex);	

				if (certIndex >= 0 && certIndex < _certInfoArray.length) {	
					_selCertInfo = _certInfoArray[certIndex];				
				}			
				else {
					_selCertInfo = null;
				}
					
				// 显示证书主要信息
				if (_selCertInfo) {
	                //USB key info.
	                //console.log(info);
					var content  = _zjcaCMT.getCertContent(_selCertInfo);
					if (typeof(content) != "undefined") {
						//ShowCertContent(content);
						//客户端证书内容.
						_certInfo = content;
						//console.log(content);
						//发送数据至服务端.
						loginByCert();
					}
					else {
						loading();	
					}
				}
			}
	        
	        function loginByCert(){
	        	//获取挑战码
			    getAuthCode();
	        	
	        	var signature = "";
	        	try {
					signature = _zjcaCMT.signMessage(_selCertInfo, 	// 签名证书
													 _signFormat,	// 签名格式，1:P1;2:P7
													 7,				// 签名附加信息(可组合)，1:带证书;2:带原文;4:带时间戳
													 _authCode			// 消息
													 );
				}
				catch (e) {
					//onErrorCallbackFunc("", e.number, e.description);			
				}	
			    
				 var info = userTable.bootstrapTable("getSelections");
				 if(signature == ""){
					$("#sign_up").removeAttr('disabled');
					return;
				 }
	        	 $.ajax({
					   	url:"userAction!bindUser.action",
					   	data:{id:info[0].id,authCode:_authCode,certInfo:_certInfo,signature:signature},
					   	type:"POST",
					   	error:function(request,textStatus, errorThrown){
					   		//alert("请求出错..");
					   		//console.log(textStatus);
						},
					   	success:function(data){
					   		//_authCode = data.authCode;
					   		if(data.result == "0"){
					   			Modal.alert({ msg:'绑定失败!', title:'提示', btnok:'确定' });
					   		}else if(data.result == "1"){
					   			Modal.alert({ msg:'绑定成功!', title:'提示', btnok:'确定' });
					   			userTable.bootstrapTable(('refresh'));
					   		}else {
					   			Modal.alert({ msg:'该证书已被绑定其他用户!', title:'提示', btnok:'确定' });
					   			$("#certNo").val(data.result);
					   		}
					   	    $("#sign_up").removeAttr('disabled');
					   	}
				});
	        }
		
			/*
			 *	更新证书信息列表
			 */

			function FillCertInfoList(certInfos) {
				// console.log(certInfos + '1');
				// 删除老的选项
				var obs = $(".obs");
				$(".obs > div").remove();
				
				// 保存新的证书信息列表
				_certInfoArray = new Array;
				_certInfoArray = certInfos;
				
				// 显示证书信息
				funAppend(_certInfoArray, OnSelCertChanged);
			}
			
		/*
		*  loding
		*/
		function loading(){
			var loading = document.getElementById("svg");
			loading.style.display = "block";
		}
		function stop_loading(){
			var loading = document.getElementById("svg");
			loading.style.display = "none";
		}