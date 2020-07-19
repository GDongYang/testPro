var FormInfo={};//基本信息
var AutoTable={};//赡养人及共同居住人基本情况删除添加
var FileInfo=[];//上传附件
var Item =[];
var Item01 =[];
// 审批条件验证
function isChecked() {
	if($("input[name=checkedItems]").prop("checked")==true) {
		jvForm.action = "page_2.html";
		jvForm.submit();
	}else {
		alert("请阅读所有申报条件，并全部勾选！");
	}
}
//不需要审批条件验证
function submitFrom() {
	jvForm.action = "page_2.html";
	jvForm.submit();
}
//全选、反选（选框）
function doCheckAll(){
    if($("#checkedAll").prop("checked") == true){
    	$(":checkbox[name='checkedItems']").prop("checked",true);
    	$("#spanSelectAll").html("取消");
    }else{
    	$(":checkbox[name='checkedItems']").prop("checked",false);
    	$("#spanSelectAll").html("全选");
    }
}

//全选、反选（文字）
function doCheckAll2(){
    if($("#checkedAll").prop("checked") == false){
		$("#checkedAll").prop("checked",true);
    	$(":checkbox[name='checkedItems']").prop("checked",true);
    	$("#spanSelectAll").html("取消");		
    }else{
    	$("#checkedAll").prop("checked",false);
    	$(":checkbox[name='checkedItems']").prop("checked",false);
    	$("#spanSelectAll").html("全选");
    }
}

//关闭温馨提醒
function doCloseDiv(divId){
	$('#'+divId).css("display", "none");
}
$(".uploadifyBtn").click(function(){
	 $('#uploadify_1').click();
});
//获取上传图片
function file(){
	 var files = $("#uploadify_1").prop('files');//获取到文件列表
	 if(files.length == 0){
	   alert('请选择文件');
	   return;
	 }else{
	   var reader = new FileReader();//新建一个FileReader
	   reader.readAsText(files[0], "UTF-8");//读取文件 
	   reader.onload = function(evt){ //读取完文件之后会回来这里
	     var fileString = evt.target.result;
	     $(".fileInput").val(fileString); //设置隐藏input的内容
	   }
	 }
}
//添加赡养人
function addPeople(){
	var strTable = '';
	strTable ='<tr>'
		     +'<td>'
		     +'<select class="selectSthle02">'                   
		     +'<option data-value="01">本人</option>' 
		     +'<option data-value="10">配偶 </option>' 
		     +'<option data-value="20">子 </option>' 
		     +'<option data-value="30">女/媳 </option>' 
		     +'<option data-value="40">孙子、孙女或外孙子、外孙女 </option>' 
		     +'<option data-value="50">父母 </option>' 
		     +'<option data-value="60">祖父母或外祖父母 </option>' 
		     +'<option data-value="70">兄弟姐妹</option> ' 
		     +'<option data-value="99">其他</option>'  
		     +'</select>'
		     +'<input type="hidden" data-name="dgx" data-cn="关系" value=""/>'
		     +'</td>'
		     +'<td><input type="text" data-name="dxm" data-cn="姓名" value=""  class="inputStyle01"/></td>'
		     +'<td><select class="selectSthle02"><option data-value="1">男</option><option data-value="2">女</option></select><input type="hidden" data-name="dxb" data-cn="性别" value=""/></td>'
		     +'<td><input type="text" data-name="fsfz" data-cn="身份证号" value=""  class="inputStyle01"/></td>'
		     +'<td><input type="text" data-name="dsjhm"  data-cn="手机号码" value="" class="inputStyle01 newPhone"/></td>'
		     +'</tr>'
    $("#addPeople").append(strTable);
}
//删除赡养人
function delPeople(){
	var long=document.getElementById("addPeople").rows.length;
	if(long>2){
		var nodes = document.getElementById("addPeople").childNodes[0].childNodes;
		  document.getElementById("addPeople").deleteRow(nodes.length - 1);
	}else{
		
	}
}
//获取选中的select的值
$(".selectStyle").change(function(){
	var selectVal = $(this).find('option:selected').attr("data-value");//选中的值
	$(this).parent().find("input").val(selectVal);
});
$("#addPeople").on("change",".selectSthle02",function(){
	var selectVal = $(this).find('option:selected').attr("data-value");//选中的值
	$(this).parent().find("input").val(selectVal);
})
$('.newPhone').blur(function(){
	checkPhone($(this).val(),$(this));
});
$(".bgyy").blur(function(){
	$("#bgyy").val($(this).val());
	var time1 = new Date().Format("yyyy-MM-dd");
	$("#bgrq").val(time1);
});
$(".zzxyy").blur(function(){
	$("#zzxyy").val($(this).val());
	var time2 = new Date().Format("yyyy-MM-dd");
	$("#zzxrq").val(time2);
});
//手机号码验证
function checkPhone(phone,obj){ 
    if(!(/^1[3456789]\d{9}$/.test(phone))){ 
        alert('手机号码有误，请重填！'); 
        $(obj).val('');
    } 
}
function submitTable(name01,name02){
	/*$(".table01 input").each(function(){
		var obj ={};
		obj['name']=$(this).attr('data-name');
		obj['name_cn']=$(this).attr('data-cn');
		if($(this).val() == '' && $(this).val() == 0){
			alert("请填写"+$(this).attr('data-cn'));
		}else{
			obj['value']=$(this).val();
			Item.push(obj);
		}
	});*/
	$(".sbTableStyle02 input").each(function(){
		var obj01 = new Object();
		obj01.obj ={};
		if($(this).val() !== '' && $(this).val() !== 0){
			obj01.obj['name']=$(this).attr('data-name');
			obj01.obj['name_cn']=$(this).attr('data-cn');
			obj01.obj['value']=$(this).val();
		}
			console.log(obj);
			//Item01.push(obj01);
		
	});
	//console.log(Item01);
	$(".fileInput").each(function(){
		var obj ={};
		obj['name']=$(this).attr('data-name');
		obj['url']=$(this).attr('data-url');
		obj['type']=$(this).attr('data-type');
		FileInfo.push(obj);
	});
	FormInfo['name'] =name01;
	FormInfo['Item'] = Item;
	AutoTable['name'] =name02;
	//console.log(FormInfo);
}
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) {
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "H+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}