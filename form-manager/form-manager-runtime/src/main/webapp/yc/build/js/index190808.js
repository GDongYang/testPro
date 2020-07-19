mui.init();//mui组件初始化
var busiCode=getQueryVariable('busiCode');
var innerCode=getQueryVariable('innerCode');
var formData={};//事项的基本信息数据
var attrData=[];//文件提交的数据
var postData={};//邮寄地址的数据
var mask = mui.createMask();//mui组件蒙版定义,图片放大查看需要
var imgBase64='';//图片base64
$(function(){
	var windowHeight=window.innerHeight;
	$(".content").css("height",windowHeight-130);
	$('#stepBtn1').siblings().hide();
	$('#step1').siblings().hide();
	//test();
    //createForm();
	dd.ready({
		usage: [
			'dd.device.notification.chooseImage',
			'dd.biz.util.selectLocalCity',
	 	],
	});
});

//获取材料
/*
 *situationCode：事项code(非必填)
 * */
function getMaterial(){
	var url = 'tempInfoAction!findTempInfoAll.action';
	$('#docListBox').html('');
	$.ajax({
        url: url,
        type: "POST",
        data:{
        	"busiCode":busiCode
        },
        dataType: "json",
        error: function(request, textStatus, errorThrown) {},
        success: function(data) {
        	$("#popWindow").hide();
            if (data.returnCode == 0) {
                var datas = data.data;
                console.log(datas);
                if (datas != null) {
                    var dataStr = '';
                    for (var i = 0; i < datas.length; i++) {
                    	attrData.push(datas[i]);
                        if (datas[i].code == 0) {
                        	dataStr += '<div class="f_mll10"><div class="bgFEEEEE f_pl10 f_lh40 f_teFF0000"><p class="f_lh20" style="height:30px;">'+(i+1)+'、'+datas[i].materialName+'</p>'
                            +'<div class="mui-input-row material3">'
                        	+'<div class="uploadBox">'
							+'<button style="line-height:0px;height:30px;margin-top:5px;padding:4px;" type="button" class="mui-btn mui-btn-primary" onclick="loadImg(this,\''+datas[i].materialCode+'\',getAttrData)">上传</button>'
							+'<img src="" style="display:none;"/></div></div>'
						    
                        	
                        	
                        	+'</div></div>'; 
                            //$("#stepBtn5").attr("disabled",true);
                        } else {
                            dataStr += '<div class="f_mll10"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">请核对您的材料，如有错误请到线下办理！<p class="f_lh20" style="height:30px;">'+(i+1)+'、'+datas[i].materialName+'</p>'
                            +'</div><div class="docList">'
                            +'<img src="data:image/png;base64,'+datas[i].certFile+'" /></div></div>';
                            //$("#stepBtn5").removeAttr("disabled");
                        }
                    }
                    $('#docListBox').append(dataStr);
                }else{
                	return false;
                }
            }else{
            	$("#stepBtn5").attr("disabled",true);
            	$('#docListBox').html('');
				mui.alert(data.returnMessage); 
			}
        }
    });
}

function submitForm(){
	$("#popWindow").show();
	getFormData($('#addForm'));
	$.ajax({
		url:'tempInfoAction!saveFormData.action',
		type : 'post',
		data:{'formInfo':JSON.stringify(formData),'postInfo':JSON.stringify(postData),'attrInfo':JSON.stringify(attrData),'busiCode':busiCode},
		error:function(request,textStatus, errorThrown){
			$("#popWindow").hide();
		},
		success:function(data){
			var result = JSON.parse(data.data);
			if(result){
				if(result.result == '01'){
					$("#successConfirm").show();
					$("#failConfirm").hide();
					submitCallBack(result);
				}else{
					$("#successConfirm").hide();
					$("#failConfirm").show();
					$("#errorMassage").text(result.msg);
				}
			}else{
				$("#failConfirm").show();
				$("#errorMassage").text('无返回结果');
			}
			$("#popWindow").hide();
			$('#step5').show().siblings().hide();
			$('.footer').hide();
		}
	});
}

//表单所有数据拼接
function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    $.map(unindexed_array, function (n, i) {
    	formData[n['name']] = n['value'];
    });
    return formData;
}

//放大图片隐藏
function hideImg(){
	$('#bigImg').hide();
	mask.close();
}
//放大图片展示
function showImg(){
	$('#bigImg').show();
	mask.show();
}

//获取url参数
function getQueryVariable(variable){
   var query = window.location.search.substring(1);
   var vars = query.split("&");
   for (var i=0;i<vars.length;i++) {
           var pair = vars[i].split("=");
           if(pair[0] == variable){return pair[1];}
   }
   return(false);
}

//手机号码验证
function checkPhone(phone){
    if(!(/^1[3456789]\d{9}$/.test(phone))){ 
        mui.toast('手机号码填写有误'); 
        return false;
    }
	return true;
}

//验证身份证
function checkIdCard(idNo){
	var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if(!regIdNo.test(idNo)){  
		mui.toast('身份证号填写有误');
	    return false;
	}
	return true;
}

function submitCallBack(result){
	$("#projid").text("申报号："+result.projid);
}

//文件图片上传
function loadImg(obj,materialCode,success) {
	$('#popWindow').show();
	dd.device.notification.chooseImage({
		upload: false,
		onSuccess: function(data) {
			var picSrc = data.picSrc;
			if (picSrc == null || picSrc == undefined) {
				mui.alert('APP版本过低，请升级后重新操作!');
				return;
			};
			$('#popWindow').hide();
			success(obj,materialCode,picSrc[0]);	
		},
		onFail: function(error) {
			$('#popWindow').hide();
		}
	});
	$('#popWindow').hide();
}
//普通文件添加参数
function getAttrData(obj,materialCode,base64){
	$(attrData).each(function(i, item) {
		if (item.materialCode == materialCode){
			item.certFile = base64.split(',')[1];
			item.upload = "1";
			return false;
		}
	});
	$(obj).next().attr('src', base64);
	$(obj).next().show();
}

function findByBusiCode(){
	 $.ajax({
		url:'userIfAction!findByBusiCode.action',
		type:"POST",
		data:{"busiCode":busiCode},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
		   var result =data.result;
		   console.log(result);
		   $("#id3").val(result.username);
		   $("#id4").val(result.idnum);
		   $("#id5").val(result.cerName);
		   $("#id6").val(result.mobile);
		   $("#id7").val(result.cerNo);
		   $("#id8").val(result.homephone);
		   $("#id9").val(result.homeaddress);
		   $("#id10").val(result.postcode);
		   $("#id11").val(result.memo);
		   getUserInfo(busiCode,result.idnum);
		}
	});
}

/** 表单提交 **/
function formSubmit() {
	var flag = true;
	$(attrData).each(function(i, item) {
		if (item.isMust == 1 && item.certFile == ''){
			flag = false;
			return false;
		}
	});
	if (flag){
		$(".contentPanel:eq(4)").show();
		showImg();
		submitForm();
	}else{
		$(".contentPanel:eq(3)").show();
		$(".operationBtn:eq(3),.operationBtn:eq(4)").show();
		mui.alert('请上传材料');
		$("#popWindow").hide();
		return false;
	}
}

function findItemByCode(){
	 $.ajax({
		url:'tempInfoAction!findItemByCode.action',
		type:"POST",
		data:{"busiCode":busiCode},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			if (data){
				if (data.result){
					var result =data.result.result;
					$("#id1").val(result.name);
					$("#id2").val("关于申请"+result.name);
					document.title = result.name;
				}
			}
		   
		}
	});
}

/** 切换页面 **/
function goPage(pageNumber) {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	if (pageNumber == 1 || typeof(pageNumber) == "undefined") {
		$("#progressBar li:eq(0)").addClass("on");
		$(".contentPanel:eq(0)").show();
		$(".operationBtn:eq(0)").show();
    	$("#popWindow").hide();
	} else if (pageNumber == 2) {
		var res = getRadioRes('itemRadio');
        if (res == null) {
    		$("#progressBar li:eq(0)").addClass("on");
    		$(".contentPanel:eq(0)").show();
    		$(".operationBtn:eq(0)").show();
        	$("#popWindow").hide();
	    	hideImg();
            mui.toast('请阅读须知！');
            return false;
        }
		$("#progressBar li:eq(0),#progressBar li:eq(1)").addClass("on");
		$(".contentPanel:eq(1)").show();
		$(".operationBtn:eq(1),.operationBtn:eq(2)").show();
    	$("#popWindow").hide();
	} else if (pageNumber == 3) {
		if (!checkInfo()){
			$("#progressBar li:eq(0),#progressBar li:eq(1)").addClass("on");
			$(".contentPanel:eq(1)").show();
			$(".operationBtn:eq(1),.operationBtn:eq(2)").show();
	    	$("#popWindow").hide();
	    	hideImg();
	    	return false;
		}
		$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2)").addClass("on");
		$(".contentPanel:eq(3)").show();
		$(".operationBtn:eq(3),.operationBtn:eq(4)").show();
		saveUser();
        getMaterial();
	} else if (pageNumber == 4) {
		$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2),#progressBar li:eq(3)").addClass("on");
		formSubmit();
	}
	
	hideImg();
}

/** 设置iframe高度 **/
function setIframeHeight() {
	var winH = $(window).height();
	var headerT = 8;//.header的top距顶部8px;
	var headerH = $(".header").outerHeight();
	var footerH = $(".footer").outerHeight();
	var contentH = winH - headerH - footerH - headerT;
	$("#iframeInset").height(contentH);
}

//获取嵌入系统信息参数 
// http://127.0.0.1:8000/yztb/app/index.html?cerNo=330602199101041032&cerName=寿卓人&innerCode=e5009a09-251d-49f6-ab00-9a861f35fb45
function getUserInfo(busiCodenum,cerNo){
	$.ajax({
		url:'tempInfoAction!getUserData.action',
		type:"GET",
		data:{
				 "busiCode":busiCodenum
			},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			//alert(data.result);
			$("#iframeInset").attr('src',"http://10.75.23.46:8443/index.html?cerNo="+cerNo+"&appId=test_app_1&validateStr="+data.result+"&noice=null&applicationTableCode=zjs_cer_290&areaCode=330683");
		}
	});
} 

function pushFormData(){
	$('input').each(function(){
		if($(this).attr('data-submit')){
			formData[$(this).attr('data-submit')]=$(this).val();
		}
	});
}

$("#docListBox").on("click", "img", function() {
    $(this).attr("src");
    $("#bigImg img").attr("src", $(this).attr("src"));
    showImg();
});

//复选框回显：
function checkBoxVal() {
    var val = '';
    $.each($('input:checkbox:checked'), function() {
        val += $(this).val() + ',';
    });
    $("#checkBoxVal").val(val.slice(0, val.length - 1));
}

function saveUser(){
	$("#popWindow").show();
	var cerName = $("#id5").val();
	var mobile = $("#id6").val();
	var cerNo = $("#id7").val();
	var homephone = $("#id8").val();
	var homeaddress = $("#id9").val();
	var postcode = $("#id10").val();
	var memo = $("#id11").val();
	$.ajax({
		url:'userIfAction!saveUser.action',
		type : 'post',
		data:{
				'cerName':cerName,
				'mobile':mobile,
				'cerNo':cerNo,
				'homephone':homephone,
				'homeaddress':homeaddress,
				'postcode':postcode,
				'memo':memo,
				'busiCode':busiCode
			},
		error:function(request,textStatus, errorThrown){
			$("#popWindow").hide();
		},
		success:function(data){
		}
	});
}

function checkInfo(){
	var cerName = $("#id5").val();
	var mobile = $("#id6").val();
	var cerNo = $("#id7").val();
	var homeaddress = $("#id9").val();
	if (cerName == ""){
		mui.toast('联系人姓名不能为空');
		return false;
	}
	if (mobile == ""){
		mui.toast('联系人手机不能为空');
		return false;
	}
	if (cerNo == ""){
		mui.toast('联系人身份证不能为空');
		return false;
	}
	if (homeaddress == ""){
		mui.toast('联系人通讯地址不能为空');
		return false;
	}
	if (!checkIdCard(cerNo)){
		return false;
	}
	if (!checkPhone(mobile)){
		return false;
	}
	return true;
}

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
 