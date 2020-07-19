	var depts = [];
	var deptObj = {};
	var lastAjax = null;
	var sealList = [];    //公章信息列表
	var departSetting = {
			data: {
				simpleData: {
					enable: true,
					idKey: "oid",
					pIdKey: "poid",
					rootPId: null
				}
			},callback: {
				onClick: zTreeOnClick,
			}
		};

	$(function() {
		setInnerPage();//设置内容页面宽和高
		getDepartmentList(); //左侧树
		loadDataSource();
		selectConfig();//下拉框初始化
		//bootstrapTable单击事件

		$("#tablewrap").on('click-row.bs.table', function (row, $element, field) {
			$(field[0]).find(":radio").iCheck('check');//选中行内radio
		});
		
		$("#addTypeExcel").on('ifChecked',function(event){
			$("#importwrap input[id='addType2Excel']").iCheck('uncheck');//未选中radio
			$("#importwrap input[id='addTypeExcel']").iCheck('check');//选中radio
			$("#add_type_div").show();    
			$("#add_type_div2").hide();  
		});
		
		$("#addType2Excel").on('ifChecked',function(event){
			$("#importwrap input[id='addTypeExcel']").iCheck('uncheck');//未选中radio
			$("#importwrap input[id='addType2Excel']").iCheck('check');//选中radio
			$("#add_type_div2").show();    
			$("#add_type_div").hide(); 
			
		});
		//初始化上传组件
		//var oFileInput = new FileInput();
		//oFileInput.Init("#importFile");
		$("#importFile").fileinput({ showUpload : false,
	        showRemove : false,
	        language : 'zh'});
		
		$("#importFileExcel").fileinput({
			language : 'zh',
			showUpload : false,
			showRemove : false,
			showPreview : false,
			allowedFileExtensions : [ 'xlsx' ],
			uploadUrl : "../sealInfoAction!analysisExcel.action",
			uploadAsync : true,
			uploadExtraData : function() {
				var obj = {};
				obj["id"] = 0;
				return obj;
			}
		});
		$('#importFileExcel').on('filebatchuploaderror',
				function(event, param) {
					alert(param.jqXRH.responseText);
			});
		
		$('#importFileExcel').on('filebatchuploadsuccess',
				function(event, data, previewId) {
				Modal.alert({
					msg : "上传成功！",
					title : '提示',
					btnok : '确定'
				});
		});
		
		$("#catalogCode").change(function(){
			loadCertSource($("#catalogCode").val());
		});
		
		//加载公章信息
		loadSealInfo();
	});
	
	function typeFormatter(val) {
		if(val == 1) {
			return "个人"
		} else if(val == 2) {
			return "企业";
		} else {
			return "-";
		}
	}

	//加载公章信息
	function loadSealInfo(){
		$.ajax({
//			url : "../tempDetailAction!getSealInfo.action",
			url : "../sealInfoAction!findAll.action",
			type : "POST",
			dataType : "json",
			async : true,
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				sealList = data.result;
			}
		});
	}
	
	function loadDataSource(){
		$.ajax({
			url : "dataSourceAction!findDepts.action",
			type : "POST",
			dataType : "json",
			async : true,
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				var depts = data.result;
				var html = '<option value="0">请选择</option>';
				for(var i=0;i<depts.length;i++) {
					html += '<option value="'+ depts[i].code +'">'+ depts[i].name +'</option>';
				}
				$("#catalogCode").html(html);
				selectUpdated($("#catalogCode"));//下拉框变动更新
			}
		});
	}
	
	function loadCertSource(code){
		$("#dataCode").empty();
		$.ajax({
			url : "dataSourceAction!findByParent.action",
			type : "POST",
			dataType : "json",
			data:{'parentCode':code},
			async : false,
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				var depts = data.result;
				var html = '<option value="0">请选择</option>';
				if(depts != null) {
					for(var i=0;i<depts.length;i++) {
						html += '<option value="'+ depts[i].code +'">'+ depts[i].name +'</option>';
					}
				}
				$("#dataCode").html(html);
				selectUpdated($("#dataCode"));//下拉框变动更新
			}
		});
	}
	
	var selectCode = "";
	//树节点点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		//alert(treeNode.pid+"   "+JSON.stringify(treeNode))
		if (treeNode.id == 2) {
			selectCode = "";
		} else {
			selectCode = treeNode.id;
		}
		//上一个请求在未返回前，直接终止掉
		if (lastAjax != null)
			lastAjax.abort();
		//请求下一个
		$("#tablewrap").bootstrapTable('refresh');
	}
	
	//得到所有部门
	function getDepartmentList() {
		$.ajax({
		   	url:"departmentAction!findTree.action",
		   	type:"POST",
		   	dataType:"json",
		   	async:true,
		  	error:function(request,textStatus, errorThrown){
		  		fxShowAjaxError(request, textStatus, errorThrown);
			},
		   	success:function(data){
		   		if(data && data.departments){
					$.fn.zTree.init($("#treeDemo"), departSetting, data.departments);
					$(data.departments).each(function(i, item) {
						deptObj[item.id] = item.name;
					});
		   		} else {
		   			var list= [];
					$.fn.zTree.init($("#treeDemo"), departSetting, list);
		   		}
			}
		});
	}

	function ajaxRequest(params) {
		var datas;
		var list;
		var count;
		var p = getParams(params);
		lastAjax = $.ajax({
			type : 'post',
			url : 'certTempAction!findPage.action',
			dataType : 'json',
			cache : false,
			async : true,
			data : p,
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				datas = data.page;
				if (datas != null)
					count = datas.count;
				list = datas.items ? datas.items : [];
				params.success({
					total : count,
					rows : list
				});
				params.complete();
				//监听及渲染ICheck
				drawICheck("tablewrap");
			}
		});
	}
	
	//查询
	function ajaxRequest1(params){
		var datas;
		var list;
		var count;
		var pageSize = params.data.limit;
		var pageNum = params.data.offset / pageSize + 1;
		index1 = params.data.offset + 1;
		var sort = params.data.sort ? params.data.sort : "id";
		var order = params.data.order ? params.data.order : "desc";
		var data = {
			sort : sort,
			order : order,
			pageNum : pageNum,
			pageSize : pageSize
		};
		if(priId != ""){
			data.tempCode = priId;
		}
		$.ajax({
			type : 'post',
			url : '../tempDetailAction!findPage.action',
			dataType : 'json',
			cache : false,
			async : true,
			data : data,
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				datas = data.page;
				if (datas != null)
					count = datas.count;
				list = datas.items ? datas.items : [];
				params.success({
					total : count,
					rows : list
				});
				params.complete();
			}
		});
	}

	function getParams(params) {
		var pageSize = params.data.limit;
		var pageNum = params.data.offset / pageSize + 1;
		index = params.data.offset + 1;
		var sort = params.data.sort ? params.data.sort : "id";
		var order = params.data.order ? params.data.order : "desc";
		var data = {
			sort : sort,
			order : order,
			pageNum : pageNum,
			pageSize : pageSize
		};
		if (selectCode != "") {
			data.departId = selectCode;
		}
		return data;
	}
	
	function idFormatter1(value, row) {
		return index1++;
	}

	function idFormatter(value, row) {
		return index++;
	}
	//返回部门信息
	function departMentFormatter(value, row) {
		return deptObj[value];
	}
	//主表操作
	function detailFormatter(value,row){
		return "<a style=\"cursor:pointer;\" onclick=\"showTempDetail('"+row.code+"')\">详情</a>";
	}
	//查看PDF
	function checkPDF(value,row){
		return "<a style=\"cursor:pointer;\" onclick=\"showPDF('"+row.createType+"','"+row.code+"')\">查看</a>";
	}
	//签章坐标
	function xyFormatter(value,row){
		return "X:"+row.signx+"&nbsp;&nbsp;Y:"+row.signy;
	}
	
	function createTypeFormatter(val) {
		if(val == 1) {
			return "PDF";
		} else if(val == 2){
			return "FreeMarker";
		} else if(val == 3){
			return "WORD"
		}else {
			return "-";
		}
	}
	//有无模板
	function hasTempFormatter(value,row){
	/*	if(value == 0){
			return "<span class=\"label label-danger\">无</span>";
		}else{
			return "<span class=\"label label-info\">有</span>";
		}*/
		return "<span class=\"label label-info\">有</span>";
	}
	//状态statusFormatter
	function statusFormatter(value,row){
		if(value == 1){
			return "<a style=\"cursor:pointer;color:#3c8dbc\" onclick=\"updateActive('"+row.id+"')\">已激活</a>";
		}else{
			return "<a style=\"cursor:pointer;color:#ff7529\" onclick=\"updateActive('"+row.id+"')\">未激活</a>";
		}
	}
	//公章 formatter
	function sealFormatter(value,row){
		var htmlStr = "<option value=\""+0+"\">"
				+ "------请选择-----" + "</option>";
		for(var i = 0;i < sealList.length;i++){
			if(sealList[i].id == row.sealId){
				htmlStr += "<option value=\""+sealList[i].id+"\" selected=\"selected\">"
				+ sealList[i].name + "</option>";
			}else{
				htmlStr += "<option value=\""+sealList[i].id+"\">"
				+ sealList[i].name + "</option>";
			}
		}
		return "<select class=\"form-control chosen-select-deselect\" name=\""+row.id+"\" chosen-position=\"true\" onchange=\"changeSeal(this)\">"+htmlStr+"</select>";
	}
	function changeSeal(obj){
		$.ajax({
			url : "../tempDetailAction!updateGz.action",
			type : "POST",
			dataType : "json",
			data:{id:$(obj).attr("name"),sealId:$(obj).val(),sealName:$(obj).find("option:selected").text()},
			error : function(request, textStatus, errorThrown) {
				fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				$("#tableDetail").bootstrapTable('refresh');
			}
		});
	}
	//新增
	function addTemplate() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length == 0 || nodes[0].id == 0) {
			Modal.alert({
				msg : '请选择部门！',
				title : '提示',
				btnok : '确定'
			});
			return;
		}
		$("#org").val(nodes[0].id);
		$("#departmentName").val(deptObj[nodes[0].id]);
		//$("#catalogCode").val(nodes[0].code);
		$("#name").val("");
		$("#code").val("");
		var dialog = getJqueryDialog();
		dialog.Container = $("#dataFormWrap");
		dialog.Title = "新增模板";
		if (!fBrowserRedirect()) {
			dialog.Width = "540";
		}
		dialog.Height = "300";
		dialog.CloseOperation = "destroy";
		dialog.ButtonJSON = {
			"取消" : function() {
				$(this).dialog("close");
			},
			"确定" : function() {
				submitData($(this));
			}
		};
		dialog.show();
		selectUpdated($("#org"));//下拉框变动更新
		selectUpdated($("#type"));//下拉框变动更新
		selectUpdated($("#dataCode"));//下拉框变动更新
		selectUpdated($("#catalogCode"));//下拉框变动更新
	}
	//修改数据
	function updateTemplate() {
		var info = $("#tablewrap").bootstrapTable("getSelections");
		if (info != 0) {
			$("#name").val(info[0].name);
			$("#departmentName").val(deptObj[info[0].departmentId]);
			$("#org").val(info[0].departmentId);
			$("#idKey").val(info[0].id);
			$("#dataFormWrap input[name='code']").val(info[0].code);
			$("#serial").val(info[0].serial);
			$("#version").val(info[0].version);
			$("#type").val(info[0].type);
			$("#catalogCode").val(info[0].catalogCode);
			loadCertSource(info[0].catalogCode);
			$("#dataCode").val(info[0].dataCode);
			var dialog = getJqueryDialog();
			dialog.Container = $("#dataFormWrap");
			dialog.Title = "修改模板";
			if (!fBrowserRedirect()) {
				dialog.Width = "540";
			}
			dialog.Height = "300";
			dialog.CloseOperation = "destroy";
			dialog.ButtonJSON = {
				"取消" : function() {
					$(this).dialog("close");
				},
				"确定" : function() {
					submitData($(this));
				}
			};
			selectUpdated($("#catalogCode"));//下拉框变动更新
			selectUpdated($("#dataCode"));//下拉框变动更新
			dialog.show();
		} else {
			Modal.alert({
				msg : '请选择要修改的数据！',
				title : '提示',
				btnok : '确定'
			});
		}
	}
	//删除数据
	function removeTemplate() {
			var rows = $('#tablewrap').bootstrapTable("getSelections");
			if(rows != 0) {
				var row = rows[0];
				Modal.confirm({
					msg:"是否删除该数据？"
				}).on( function (e) {
					if(e) {
						$.ajax({
							cache : true,
							type : "POST",
							url : 'certTempAction!removeTemplate.action',
							dataType : 'json',
							data : row,
							async : false,
							error : function(request, textStatus, errorThrown) {
								fxShowAjaxError(request, textStatus, errorThrown);
							},
							success : function(data) {
								Modal.alert({ msg:'删除成功！', title:'提示', btnok:'确定' }).on(function(e){
									refresh();
								});
								$("#tablewrap").bootstrapTable('refresh');
							}
						});
					}
				});
			} else {
				Modal.alert({ msg:'请选择要删除的数据！', title:'提示', btnok:'确定' });
			}
	}
	//提交数据
	function submitData(obj) {
		if(tipsRegionValidator($("#dataform"))){
			var url="";
			var dataStr = $("#dataform").serialize();
			url = "../certTempAction!save.action";
			if ($("#idKey").val() != null && $("#idKey").val() != ""){
				dataStr += "&id="+$("#idKey").val();
				url = "../certTempAction!update.action";
			}
			$.ajax({
				url : url,
				type : "POST",
				dataType : "json",
				data : dataStr,
				error : function(request, textStatus, errorThrown) {
					fxShowAjaxError(request, textStatus, errorThrown);
				},
				success : function(data) {
					Modal.alert({
						msg : '保存成功！',
						title : '提示',
						btnok : '确定'
					});
					obj.dialog("close");
					//请求下一个
					$("#tablewrap").bootstrapTable('refresh');
					$("#idKey").val("");
				}
			});
		}
	}
	//上传模板
	function uploadTemplate() {
		var info = $("#tablewrap").bootstrapTable("getSelections");
		if (info != 0) {
			try {
				$("#departmentId").val(info[0].departmentId);
				$("#tempCode").val(info[0].code);
				$("#certName").val(info[0].name);
				//初始化
				$(["signx","signy","memo","ordinal"]).each(function(i,key){
					$("#uploadDataform input[name='"+key+"']").val("");
				});
			    $(".fileinput-remove").click();
			} catch (e) {
				// TODO: handle exception
			}
			
			var dialog = getJqueryDialog();
			dialog.Container = $("#uploadFormWrap");
			dialog.Title = "上传模板";
			if (!fBrowserRedirect()) {
				dialog.Width = "540";
			}
			dialog.Height = "550";
			dialog.CloseOperation = "destroy";
			dialog.ButtonJSON = {
				"取消" : function() {
					$(this).dialog("close");
				},
				"确定" : function() {
					var dialog = $(this);
					if(tipsRegionValidator($("#uploadDataform"))){
						var options = {
							url :"../tempDetailAction!save.action",
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
		} else {
			Modal.alert({
				msg : '请选择模板！',
				title : '提示',
				btnok : '确定'
			});
		}
	}
	
	var priId = "";
	//显示详情
	function showTempDetail(id){
		priId = id;
		$("#tableDetail").bootstrapTable('refresh');
		var dialog = getJqueryDialog();
		dialog.Container = $("#tempDetailWrap");
		dialog.Title = "印章详情";
		if (!fBrowserRedirect()) {
			dialog.Width = "620";
		}
		dialog.Height = "480";
		dialog.CloseOperation = "destroy";
		dialog.show();
	}
	//查看PDF
	function showPDF(createType,tempCode){
		var url = '';
		if(createType == 1) {
			url = "PDFViewer.jsp?tempCode="+tempCode;
		} else if(createType == 3){
			url = "WordViewer.jsp?tempCode="+tempCode+"&fileType=3";
		}else
		{
			url = "certTempAction!getHtml.action?code=" + tempCode;
		}
		window.open(url, "newwindow", "'width="+1000+",height="+800+", toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no");
	}
	
	//删除明细
	function updateActive(id){
		$.ajax({
			url : "../tempDetailAction!updateActive.action",
			type : "POST",
			dataType : "json",
			data:{id:id},
			error : function(request, textStatus, errorThrown) {
				fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				$("#tableDetail").bootstrapTable('refresh');
			}
		});
	}
	
	
	function sealCreate() {
		$("#importwrap input[id='addTypeExcel']").iCheck('check');//选中radio
		$("#add_type_div").show();
		$("#dataformSeal input").val('');
		var dialog = getJqueryDialog();
		dialog.Container = $("#importwrap");
		dialog.Title = "上传电子印章";
		if (!fBrowserRedirect()) {
			dialog.Width = "500";
		}
		dialog.Height = "300";
		dialog.CloseOperation = "destroy";
		dialog.ButtonJSON = {
			"取消" : function() {
				$(this).dialog("close");
			},
			"确定" : function() {
				var addTypeExcel=$('#importwrap input:radio[name="addTypeExcel"]').attr("checked");
				var addType2Excel=$('#importwrap input:radio[name="addType2Excel"]').attr("checked");
//				console.log(addTypeExcel+","+addType2Excel+","+a);
//				return;
				if(addTypeExcel == 'checked'){
					var file = $("#importFileExcel").val();
					if(file != null && file != "") {
						$("#importFileExcel").fileinput('upload');
						$(this).dialog("close");
					}else{
						$.messager.alert("提示","请选择Excel!");
					}
				}else if(addType2Excel == 'checked'){
					$.ajax({
						cache : true,
						type : "POST",
						url : '../sealInfoAction!create.action',
						dataType : 'json',
						data : $("#dataformSeal").serialize(),
						async : false,
						error : function(request, textStatus, errorThrown) {
							fxShowAjaxError(request, textStatus,errorThrown);
						},
						success : function(data) {
							Modal.alert({
								msg : '新增成功',
								title : '提示',
								btnok : '确定',
								btncl : '取消'
							});
						}
					});
					$(this).dialog("close");
				}
			}
		};
		dialog.show();

	}

    function formatActive(val,row) {
        var html="";
        if (val == 1) {
            html+= '<div onclick=\"Show(this)\"><span class=\"a\"><font style="color:#0000FF">正常</font></span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: auto;\"><option value=\"true\" selected=\"selected\">正常</option><option value=\"false\">未激活</option></select>';
        } else {
            html+= '<div onclick=\"Show(this)\"><span class=\"a\"><font style="color:#FF0000">未激活</font></span></div><div style=\"display:none\"><select class=\"form-control chosen-select-deselect\" style=\"float:left;width: auto;\""><option value=\"true\" >正常</option><option value=\"false\" selected=\"selected\">未激活</option></select>';
        }
        html+="<button type=\"button\" style=\"margin:2px 5px;\" class=\"btn btn-xs btn-info glyphicon glyphicon-ok\" onclick=\"updateActive('active',this,'"+row.id+"')\"></button>"+
            "<button type=\"button\" class=\"btn btn-xs glyphicon glyphicon-remove\" onclick=\"Hidden(this)\"></button></div>";
        return html;
    }

    function updateActive(colName,obj,id) {
        var value = "";
        var ele = $(obj).prev();
        if(colName!="memo" && (ele.val()==null || ele.val() == "")) {
            Modal.alert({
                msg : '数据不能为空！',
                title : '提示',
                btnok : '确定',
                btncl : '取消'
            });
            return;
        }
        if(ele[0].tagName == "SELECT" && ele[0].multiple){
            $.each(ele.val(),function(i, val){
                value +=colName+"="+val+"&";
            });

        } else {
            value = colName+"="+ele.val()+"&";
        }
        var dataStr= value+"id="+ id;
        $.ajax({
            type : "POST",
            url : 'certTempAction!updateActive.action',
            dataType : 'json',
            data : dataStr,
            error : function(request, textStatus, errorThrown) {
                fxShowAjaxError(request, textStatus,errorThrown);
            },
            success : function(data) {
                Modal.alert({
                    msg : '修改成功',
                    title : '提示',
                    btnok : '确定',
                    btncl : '取消'
                });
                refresh();
            }
        });
    }

    function Hidden(obj){
        $(obj.parentNode).hide();
        $(obj.parentNode).prev().show();
        /* refresh(); */
    }

    function Show(obj){
        $(obj).hide();
        $(obj).next().show();
    }

    function refresh() {
        $("#tablewrap").bootstrapTable('refresh');
    }
	function fileName(){
	    //获取一个上传文件的扩展名
	    var myfile = document.getElementById('importFile');
	    var mybtn = document.getElementById('mybtn');
	    var div = document.getElementById('fileDiv');
	    mybtn.onclick = function() {
	        //获取文件上传文件的文件名和扩展名
	        if (myfile.files[0] == undefined) {
	            alert('未上传任何文件！');
	        } else {
	            //获取上传文件的文件名
//	            div.innerHTML= div.innerHTML+'<span style="color:#FF0000;">文件名：' + myfile.files[0].name + '</span><br>';
	            //获取上传文件的扩展名
	            var filevalue = myfile.value;
	            var index = filevalue.lastIndexOf('.');
	            var type=filevalue.substring(index);
	        }
	    }
	}    