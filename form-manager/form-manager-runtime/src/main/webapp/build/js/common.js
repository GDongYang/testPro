
var index = 1;
var table = {dataTotal :0};
function idFormatter(value,row){
	return index++;
}

function timeFormatter(value, row){
	if(value){
		return value.replace("T", " ");
	}
	return value;
}
/**
 表单点击回车时不会自动刷新整个页面
 **/
$("form").keydown(function(e){
    e.stopPropagation();
    var e = e || event;
    var currKey = e.keyCode || e.which || e.charCode;//支持IE,FireFox
    if (currKey == 13) {
        return false;
    }
})

/**
 * textarea回车换行
 * @param textareaId: textarea 的id
 * useage:
 *  onkeydown="enterKey13(textareaId)"
 * **/
var enterKey13 = function(textareaId){
    if(window.event.keyCode==13){             //window.event.keyCode获取按下键盘对应的值，13为enter对应的值
        var rawData = $("#" +textareaId).val();  //首先获取原本编辑框里面的值，保存起来
        $("#" + textareaId).val(rawData+"\n");    //内容加上换行符“\n”,重新写到编辑框里面，这样就实现了用户键入enter，编辑框                                                                              //光标移动到下一行，从而实现换行
    }
}


/**
 * 跳转页面提交后提示信息
 * @param url: 跳转的url,
 * @param text： 返回的文字, 
 * @param content提示的内容
*/
function returnList(url,text,content,url1){
	var texts ="返回" + text;
	$.messager.model={
		ok: {text: texts, classed: 'btn-primary'},
		cancel:{text: "确定", classed: 'btn-default none'}
	};
	$.messager.confirm("提示",content,function(){
			window.location.href= url;
	})
}

/**
 * 表单提交之前的二次验证
 * @returns
 */
function formatValidate(validator){
	validator.validate();
	return validator.isValid();
}

/**
 * 生成一个新窗口
 * @param mywin 窗口对象
 * @param winParam 窗口参数
 * {title:窗口标题}
 * @param buttons 窗口按钮
 * {按钮名1:按钮方法,按钮名2:按钮方法}
 */
function formatDialog(mywin,winParam,buttons){
	mywin.dialog({
		title:winParam.title,
		backdrop:"static",
		dialogClass : winParam.dialogClass?winParam.dialogClass:"",
		onClose:formatDialogClose,
		buttons:buttons
	});
}

/**
 * 关闭dialog并清空选择项
 */
function formatDialogClose() {
	$(this).dialog("close");
}

/**
 * 清理表单
 * @param form 表单对象
 * @param control 表单是否上锁
 */
function formatClearForm(formId,control){
	tag = ["input","textarea","select"];
	//一般对象处理
	for(var i =0; i< tag.length; i++){
		$("#" + formId).find(tag[i]).attr({
			disabled:control?control:false
		}).val("");
	}
	//特殊对象处理
	$('#'+ formId + ' input').val('')
	$('#'+ formId + ' select option').removeAttr("selected");
	$('#'+ formId + ' select option').eq(0).attr("selected", true);
	
}

/**
 * 新增
 *	
*/
 function addRow(){
	 
 }

/**
 * 修改
 * @param obj : 修改行的数据
 * @param formId： 修改的form表单的id
 * @param control 是否disabled
 *  @param separator  分隔符  比如 model.name
 */
 function setUpdateRow(obj,formId,control,separator){
	$("#" + formId + " .form-control").each(function() {
		var name;
		var nameLike = $(this).attr("name");
		if(separator&&nameLike){
			var nameArr = nameLike.split(separator);
			name = nameArr[nameArr.length-1];
		}else{
			name = nameLike;
		}
		for (var key in obj) {
			if (key == name) {
				$(this).val(obj[key]);
				$(this).attr("disabled",control?control:false);
			}
		}
	})
 }

 /**
  * 删除
  * @param url 删除的url+参数
  * @param text 删除的文本内容
  * @param propContent 
  * 	false: 不需要提示的文本内容 直接是返回值作为提示内容
  * 	!false: 需要提示的文本内容
  */
function removeRow(tableId,url,text,propContent, fun){
	$.messager.model={
		cancel: {text: "取消", classed: 'btn-default'},
		ok:{text: "确定", classed: 'btn-primary'}
	};
	//var texts ="您确定" + text + "此条记录吗？" ;
	var texts ="您确定" + text;
	console.log(texts);
	$.messager.confirm("提示",texts,function(){
		$.ajax({
			type:'post',
			url : url,
			dataType:'json',
			async:false,
            headers: {'Content-Type': 'application/json'},
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
			error: function(request,textStatus, errorThrown){
				fxShowAjaxError(request, textStatus, errorThrown);
			},
			success: function(data){
				if(fun){
					fun(data);
				}else{
					if(propContent == "false"){
						if(data.message){
							$.messager.alert("提示",data.message);
						}else if(data){
							$.messager.alert("提示",data.result);
						}
					}else{
						$.messager.alert("提示",propContent);
					}
				}
				if(tableId){
					refresh(tableId);
				}
				
			}
		})
	})
	
}
function removeIt(data){
	$.messager.alert("提示","该单位下有人员所属，不能删除");
}
/**
 * 	刷新
 *  @param tableId 表格 id
 * 
 */
 
function refresh(tableId) {
	var param = {
		silent:true,
    }
    /*$("#" + tableId).bootstrapTable('refresh', param);*/
    if(tableId){
        var tableArr = tableId.split("|");
        for(var i = 0; i< tableArr.length; i++){
            var param = {
                silent:true,
            }
            $("#" + trim(tableArr[i])).bootstrapTable('refresh', param);
        }
    }
}
function refreshMultiple(tableId) {
	if(tableId){
		var tableArr = tableId.split("|");
		for(var i = 0; i< tableArr.length; i++){
            var param = {
                silent:true,
            }
            $("#" + trim(tableArr[i])).bootstrapTable('refresh', param);
		}
	}

}
/**
 * 查询
 * 
 * @param tableId: 表格id
 * 
 * 
 */
function search(tableId) {
	$("#" + tableId).bootstrapTable('selectPage', 1);
}
/**
 * 重置
 */
function resetForm(formId,tableId) {
	console.log(formId,tableId)
	$('#' + formId +' input').val('');
	$('#' + formId + ' select').val(-1);
	refresh(tableId);
}


/**
 * 用于发起一个ajax请求
 * @param ajaxParams ajax请求参数
 * 	{type:请求类型,url:请求地址,dataType:请求数据类型,data:请求数据}
 * @param fn 请求成功时执行的函数
 * @param params fn的一个参数
 */
function formatAjax(ajaxParams,fn,params){
	var dataList ;
	$.ajax({
		cache : ajaxParams.cache?ajaxParams.cache:true ,
		type : ajaxParams.type?ajaxParams.type:'post' ,
		url : ajaxParams.url?ajaxParams.url:"" ,
		dataType : ajaxParams.dataType?ajaxParams.dataType:'json' ,
		async: ajaxParams.async,
		data : ajaxParams.data?ajaxParams.data:null ,
        timeout:10000,
		traditional: ajaxParams.traditional?ajaxParams.traditional:false ,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			datas = data;
			if(fn){
				dataList = fn(datas,params);
			}
			
		}
	});
	return dataList;
}

function tipsDialog(datas){
	if(datas){
		Modal.alert({ msg:datas.msg, title:'提示', btnok:'确定' }).on(function(e){
			search();
		});
	}	
}

function returnListData(datas,params){
	returnList(params.url,params.text,datas.message);
}

/**
 * 向表格中添加数据
 * @param data ajax查询返回的数据
 * @param params bootstrap内置表格参数
 */
function formatTableData(data,params){
	var datas, items,count;
	if(data.code == 600){
		if(data.pagination){
			datas = data.pagination;
			items = datas.items ? datas.items : [];
			count = datas.count;
		}else if(data.successObj){
			items = data.successObj ? data.successObj : [];
			count = items.length;
		}else if(data.data){
			if(data.data.items){
				items = data.data.items ? data.data.items : [];
				count = data.data.count;
			}else{
				items = data.data ? data.data : [];
				count = data.data.count ? data.data.count : items.length;
			}			
		}else{
			datas = data;
			items = datas.items ? datas.items : [];
			count = datas.count;
		}
		params.success({
			total : count,
			rows : items
		});
	}else{
		/*if(data.items){
			items = data.items ? data.items : [];
			count = data.count;
		}else if(data.row){
			items =  data.row ?  data.row : [];
			count = data.count;
		}*/

		params.success({
			total : 0,
			rows : []
		});
	}
}

function tableData(data,params){
	if(data.code == '600'){
		items = data.data ? data.data : [];
		count = items.length ? items.length : 0;
		params.success({
			total : count,
			rows : items
		});
	}
}


//从url中获取参数
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
//从url中获取参数
function getUrlParams(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

// 千分位格式化 123456789 -> 123,456,789
function formatterString(num) {
    var string = '';
    if (typeof num === "number")  {
        string = num.toString();
    }else if (typeof num === "string") {
        string = num;
    }else {
        string = num.toString();
    }

    return string.replace(/(\d{1,3})(?=(\d{3})+$)/g,'$1,');
}