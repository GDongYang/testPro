var userTable;	//用户列表
			var pageNum = 1;
			var pageSize = 10;
			var elementTable;
			var index = 1;
			
			function idFormatter(value, row) {
				return index++;
			}
			
			$(function() {
				loadCurrentUser();
				setInnerPage();//设置内容页面宽和高
				tipsRegionHint(document);//tips校验(区域标注提示方法)
				userTable = $('#tablewrap');
				
				//bootstrapTable单击事件
				userTable.on('click-row.bs.table', function (row, $element, field) {
					$(field[0]).find(":radio").iCheck('check');//选中行内radio
				});
			});
			
			var currentUser;
			function loadCurrentUser() {
				$.ajax({
					url:"userSessionAction!loadCurrentUser.action",
					type:"POST",
					dataType:"json",
					error:function(request,textStatus, errorThrown){
						fxShowAjaxError(request, textStatus, errorThrown);
					},
					success:function(data){
						currentUser = data;
						if(currentUser.orgId == 43){
							//工商局显示
							$("#importCustomer").show();
						}else{
							$("#importCustomer").hide();
						}
					}
				});
			}
			//清除
			function clean() {
				pageNum = 1;
				pageSize = 10;
				$('#searchForm input').val("");
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
			
			//刷新页面
			function refresh() {
				clear1();
				search();
			}
			var load_position = false;
			//对象列表的ajax请求
			function ajaxRequest(params) {
				loading();
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
					url : 'customerAccountAction!findPage.action',
					dataType : 'json',
					data : dataStr,
					error : function(request, textStatus, errorThrown) {
						//fxShowAjaxError(request, textStatus, errorThrown);
					},
					success : function(data) {
						stop_loading()
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
			//上传名单
			function uploadTemplate() {
					
					var dialog = getJqueryDialog();
					dialog.Container = $("#uploadFormWrap");
					dialog.Title = "上传名单";
					if (!fBrowserRedirect()) {
						dialog.Width = "240";
					}
					dialog.Height = "250";
					dialog.CloseOperation = "destroy";
					dialog.ButtonJSON = {
						"取消" : function() {
							$(this).dialog("close");
						},
						"确定" : function() {
							var dialog = $(this);
							if(tipsRegionValidator($("#uploadDataform"))){
								var options = {
									url :"../importAction!importFile.action",
									type : 'post',
									success : function(data) {
										dialog.dialog("close");
										Modal.alert({
											msg : '保存成功！',
											title : '提示',
											btnok : '确定'
										});
									}
								};
								$("#uploadDataform").ajaxSubmit(options);
							}
						}
					};
					dialog.show();
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