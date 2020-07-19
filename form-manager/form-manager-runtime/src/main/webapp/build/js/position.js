var cardUrl= 'positionAction!findPage.action';
var addUrl='positionAction!save.action';
var editUrl='positionAction!update.action';
var deleteUrl='positionAction!remove.action';
var addFormValidator;
var oldHtml = "";
var posId = "";
var treeSearchFlag=false;//判断是否需要模糊搜索
$(document).ready(function(){
	ajaxRequest();
	loadDepartment('treeDemo','treeDemo2');
	formValidator();
	selectConfig();//下拉框初始化
	addFormValidator = $('#addForm').data('bootstrapValidator');
});
//新增树点击回调函数
function treeCallBack2(treeName,treeId){
	$("#deptSearchId2").val(treeName);
	$("#deptId").val(treeId);
	//findItemByDept(treeId,0);
}
//搜索树点击回调函数
function treeCallBack(treeName,treeId){
	$("#deptSearchId").val(treeName);
	$("#deptIdSearch").val(treeId);
}
//新增
function addPosition(){
	$("#itemCodes").val('');
	selectUpdated($("#itemCodes"));//下拉框变动更新
	add();
}
//修改
function updatePosition(data){
	update(data);
	//findItemByDept(data.departmentId,data.type);
	//findRPosByCode(data.id);	
}
//详情弹框
function detail(data) {
	//获取事项列表
	findItem(data.departmentId);
	posId = data.id;
	$.ajax({
		type : 'post',
		url : 'itemAction!findByPosition.action',
		dataType : 'json',
		data:{'positionId':data.id},
		cache : false,
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			$("#noData").nextAll().remove();
			var dataStr='';
			dataStr = "<div class='itemDetail'>"
			if(data.items != null){
				$('#noData').addClass('base_hidden');
				for(var i=0;i<data.items.length;i++){				
					dataStr+= '<div class="detailItem"><h3 class="detailTitle">'+data.items[i].name+'<i class="fa fa-minus-circle base_margin-l-4" onclick="deleteItem(this)"></i></h3><div class="row">'			
					+'<div class="base_hidden"><input class="itemId" value="' + data.items[i].id + '"/></div>'
					+'<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">权利编码：<span class="certCode" title="'+data.items[i].code+'">'+data.items[i].code+'</span></div>'
					+'<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">唯一编码：<span class="innerCode" title="'+data.items[i].innerCode+'">'+data.items[i].innerCode+'</span></div></div></div>';						
				}
				dataStr += "</div>";
			}else{
				dataStr += "</div>";
				$('#noData').removeClass('base_hidden');
			}						
			$("#formDetail").append(dataStr)
			formatDialog($("#formDetail"),{title:"详情",dialogClass:"mydialog"},{"取消": formatDialogClose , "确定": createBind});
		}
	});
}
/*事项列表中查找*/
function searchItem2(){
	$("#itemSelectId").empty();
	oldHtml = "";	
    selectUpdated($("#itemSelectId"));
    findItem($("#sourceDeptId").val());	
}
/*删除当前事项*/
function deleteItem(_this){
	$(_this).parent().parent().remove();
}
/*移除所有事项*/
$("#removeAllItem").click(function(){
	 $(".itemDetail").children(".detailItem").remove();
})
/*获取事项列表*/
function findItem(id) {
    var name  = $("#itemName2").val();
	$.ajax({
		cache : false,
		type : "post",
		url : 'itemAction!findPaeTable.action',
		dataType : 'json',
		data: {'departmentId': id,'pageNum':1,'pageSize':200,name:name},
		async : false,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
                $("#itemSelectId").empty();
			if (data.rows != null) {
				var htmlStr = "<option value=\"\"></option>";
				for (var i = 0; i < data.rows.length; i++) {
					var json = {};
					json.code = data.rows[i].code;
					json.name = data.rows[i].name;
					json.id = data.rows[i].id;
					json.innerCode = data.rows[i].innerCode;
					htmlStr += "<option value=\"" + data.rows[i].id + "\"data='" + JSON.stringify(json) + "'>"
							+ data.rows[i].name + "(" + data.rows[i].departmentName + ")" + "</option>";
				}
				selectConfig();//下拉框初始化
				oldHtml = htmlStr + oldHtml;
				$("#itemSelectId").append(oldHtml);
				selectUpdated($("#itemSelectId"));
			}
		}
	});
}
 $('#itemSelectId').on('change',function(e,params){
 	var itemName = $("#itemSelectId").find("option:selected").text();
 	var data = $("#itemSelectId").find("option:selected").attr("data");
 	data = JSON.parse(data)//将JSON字符串转成JSON对象
    var itemId = params.selected; 
    var add = true;
    $('#noData').addClass('base_hidden');
    var dataStr= '<div class="detailItem"><h3 class="detailTitle">'+data.name+'<i class="fa fa-minus-circle base_margin-l-4" onclick="deleteItem(this)"></i></h3><div class="row">'			
		   +'<div class="base_hidden"><input class="itemId" value="' + data.id + '"/></div>'
		   +'<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">权利编码：<span class="certCode" title="'+data.code+'">'+data.code+'</span></div>'
    	   +'<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">唯一编码：<span class="innerCode" title="'+data.innerCode+'">'+data.innerCode+'</span></div></div></div>';		
    var i = 0;
    //遍历列表检查是否已经存在该事项
  	 var detailItemArr = $(".itemDetail").children(".detailItem");
	//遍历
	for(var i = 0;i < detailItemArr.length;i++){
		var id = $(detailItemArr[i]).children(".row").children(".base_hidden").children(".itemId").val();
		if( id == data.id){
			add = false;
		}
	}
    //将事项放在数源列表里
    if(add){
   		 $(".itemDetail").prepend(dataStr)
   	}
})

//移除事项列表的单个事项
$("#removeItem").click(function(){
	//获取事项列表框中选中的
	var rmItemId =  $("#select").get(0).selectedIndex;
	$("#select").find("option").get(rmItemId).remove() // i为index
	
})
//移除事项列表的所有事项
$("#removeAllItem").click(function(){
	//获取事项列表框中选中的
	$("#select").empty();
})
/*根据部门获取事项*/
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
			selectUpdated($("#itemCodes"));
		}
	});
	selectUpdated($("#itemCodes"));
}
function createBind(){
	var itemIds = [];
	var detailItemArr = $(".itemDetail").children(".detailItem");
	//遍历
	for(var i = 0;i < detailItemArr.length;i++){
		var id = $(detailItemArr[i]).children(".row").children(".base_hidden").children(".itemId").val();
		itemIds.push(id)
	}
	$.ajax({
		cache : false,
		type : "POST",
		url : 'positionAction!bindItems.action',
		dataType : 'json',
		traditional :true, 
		data : {'id' : posId,'itemCodes':itemIds},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			$("#formDetail").dialog("close");
			Modal.alert({ msg:data.msg, title:'提示', btnok:'确定' }).on(function(e){
					
			});
		}
	});
}
//根据id查询关联事项
function findRPosByCode(positionId){
	$.ajax({
		cache : false,
		type : "POST",
		url : 'positionAction!findItems.action',
		dataType : 'json',
		data : {'id' : positionId},
		async : false,
		error : function(request, textStatus,errorThrown) {
		},
		success : function(data) {
			$("#itemCodes").val(data.result);
			selectUpdated($("#itemCodes"));//下拉框变动更新
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
						message : '岗位名称不能为空！'
					}
				}
			},
			departmentId : {
				validators : {
					 notEmpty : {
						message : '所属部门不能为空！'
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
		dataStr+="<img src='../build/image/8-1.png'/>";
	}else{
		dataStr+="<img src='../build/image/8.png'/>";
	}
	dataStr+="</div><div class='col-xs-9 col-sm-9 col-md-9 col-lg-9 base_padding-0'><div class='cardTitle'>"
	+(data.name ? data.name : '无')+"</div><div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 base_padding-l-0'><div class='cardTitleSmall'>"
	+"编码</div><div class='cardMsg' title='"+data.code+"'>"+(data.code ? data.code : '无')+"</div></div>"
	+"<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'><div class='cardTitleSmall'>单位</div>"
	+"<div class='cardMsg'>"+(data.departmentName ? data.departmentName : '无' )+"</div></div>"
	+"<div class='col-xs-10 col-sm-10 col-md-10 col-lg-10 base_padding-lr-0'>"
	+"<div class='cardMemo'>"+(data.memo ? data.memo : '无' )+"</div></div></div></div>"
	+'<div class="cardFooter '+(data.active==1?'':'disabled')+'"><div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff" title="修改" onclick="updatePosition('+JSON.stringify(data).replace(/\"/g,"'")+')">'
	+"<i class='fa fa-edit'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4 border-r-fff' title='删除' onclick='remove("+data.id+")'>"
	+"<i class='fa fa-trash-o'></i></div><div class='col-xs-4 col-sm-4 col-md-4 col-lg-4' title='事项' onclick='detail("+JSON.stringify(data).replace(/\'/g,'"')+")'>"
	dataStr+="<i class='fa fa-list'></i></div></div></div></div>";
	return dataStr;
}