mui.init();//mui组件初始化
var FormInfo={};//基本信息
var AutoTable={};//赡养人及共同居住人基本情况删除添加
var FileInfo=[];//上传附件
var Item =[];
var Item01 =[];
$(function(){
	var windowHeight=window.innerHeight;
	$(".content").css("height",windowHeight-130);
	$('#stepBtn1').siblings().hide();
	$('#step4').siblings().hide();
})
//判断是否阅读温馨提示
function getRadioRes(className) {
	var rdsObj = document.getElementsByClassName(className);
	var chackVal = null;
	for(i = 0; i < rdsObj.length; i++) {
		if (rdsObj[i].checked) {
			chackVal = rdsObj[i].value;
		}
	}
	return chackVal;
}
//下一步
function next(obj){
	$(obj).hide();
	$(obj).siblings().hide();
	$(obj).next().show();
	var idStr=$(obj).attr('id');
	var idNum=Number(idStr.substring(idStr.length-1));
	$('#step'+idNum).hide();
	$('#step'+idNum).siblings().hide();
	$('#step'+idNum).next().show();
	$(".step li").eq(idNum).addClass("on");
	$("#prevBtn").hide();
}