mui.init();//mui组件初始化
var busiCode=getQueryVariable('formBusiCode');
var innerCode=getQueryVariable('innerCode');
var formData={};//事项的基本信息数据
var attrData=[];//文件提交的数据
var postData={};//邮寄地址的数据
var mask = mui.createMask();//mui组件蒙版定义,图片放大查看需要
var imgBase64='';//图片base64
var certCode='';
var situationChoose = [];
$(function(){
	var windowHeight=window.innerHeight;
	$(".content").css("height",windowHeight-130);

	dd.ready({
		usage: [
			'dd.device.notification.chooseImage',
	 	],
	});
	
});

//获取材料
/*
 *situationCode：事项code(非必填)
 * */
function getMaterial(){
	$('#wxts').show();
	var url = 'userAction!findTempInfoAll.action';
	$('#docListBox').html('');
	$.ajax({
        url: url,
        type: "POST",
        data:{
        	"formBusiCode":busiCode,
        	"situations":getSituations()
        },
        dataType: "json",
        error: function(request, textStatus, errorThrown) {
        	$("#popWindow").hide();
        	$('#wxts').hide();
        },
        success: function(data) {
        	//console.log(data);
        	$("#popWindow").hide();
        	$('#wxts').hide();
            if (data.returnCode == 0) {
                var datas = data.data;
                if (datas != null) {
                    var dataStr = '';
                    for (var i = 0; i < datas.length; i++) {
                    	attrData.push(datas[i]);
                        if (datas[i].code == 0) {
                        	dataStr += '<div class="f_mll10"><div class="bgFEEEEE f_pl10 f_lh40 f_teFF0000"><p class="f_lh20" style="height:30px;">';
                        	if (datas[i].isMust == 1) {
								dataStr += '*';
							}
                        	dataStr += (i+1)+'、'+datas[i].materialName+'</p>'
                            +'<div class="mui-input-row material3">'
                        	+'<div class="uploadBox">'
							+'<button style="line-height:0px;height:30px;margin-top:5px;padding:4px;" type="button" class="mui-btn mui-btn-primary" onclick="uploadImage(\''+i+'\',\''+datas[i].materialCode+'\')">上传</button>'
                        	+'<input id="file'+i+'" style="display:none;" accept="image/*" onchange="showPic(\''+i+'\',\''+datas[i].materialCode+'\',this)" type="file"/>'
							+'<img src="" style="display:none;"/></div></div>'
                        	+'</div></div>';
                        } else {
                            dataStr += '<div class="f_mll10"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">请核对您的材料，如有错误请到线下办理！<p class="f_lh20" style="height:30px;">';
							if (datas[i].isMust == 1) {
								dataStr += '*';
							}
							dataStr += (i+1)+'、'+datas[i].materialName+'</p>'
                            +'</div><div class="docList">'
                            +'<img src="data:image/png;base64,'+datas[i].certFile+'" /></div></div>';
                        }
                    }
                    if (dataStr == ''){
                    	dataStr += '<div class="f_mll10"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">该事项无需材料，请直接提交！<p class="f_lh20" style="height:30px;"></p>'
                        +'</div></div>';
                    }
                    $('#docListBox').append(dataStr);
                }else{
                	return false;
                }
                $("#stepBtn9").removeAttr("disabled");
            }else{
            	$("#stepBtn9").attr("disabled",true);
            	$('#docListBox').html('');
				mui.alert(data.returnMessage); 
			}
        }
    });
}

function submitForm(){
	formData["form1"]=[];
	formData["form1"].push({"name":"sxmc","name_cn":"事项名称","value":$("#id1").val()});
	formData["form1"].push({"name":"bjlx","name_cn":"办件类型","value":$("#id2").val()});
	formData["form1"].push({"name":"bljg","name_cn":"办理机构","value":$("#id3").val()});
	formData["form1"].push({"name":"bjsx","name_cn":"办结时限","value":$("#id4").val()});
	formData["form1"].push({"name":"sfsf","name_cn":"是否收费","value":$("#id5").val()});
	formData["form1"].push({"name":"zxdh","name_cn":"咨询电话","value":$("#id6").val()});
	formData["form1"].push({"name":"sxid","name_cn":"事项ID","value":$("#id7").val()});
	formData["form1"].push({"name":"sfzh","name_cn":"身份证号","value":$("#id8").val()});
	formData["form1"].push({"name":"projid","name_cn":"申报号","value":"1"});
	formData["form1"].push({"name":"xm","name_cn":"姓名","value":$("#id9").val()});
	formData["form1"].push({"name":"sjh","name_cn":"手机号","value":$("#id10").val()});
	$.ajax({
		url:'userAction!saveFormData.action',
		type : 'post',
		data:'formBusiCode='+busiCode+"&situations="+getSituations()+'&attrInfo='+JSON.stringify(attrData)+'&formInfo='+JSON.stringify(formData)+'&postInfo='+JSON.stringify(postData),
		error:function(request,textStatus, errorThrown){
			$('#popWindow').hide();
		},
		success:function(data){
			$('#popWindow').hide();
			var result = JSON.parse(data.data);
			console.log(result);
			if(result){
				if(result.returnCode == '0'){
					$("#successConfirm").show();
					$("#failConfirm").hide();
					submitCallBack(JSON.parse(result.data));
				}else{
					$("#successConfirm").hide();
					$("#failConfirm").show();
					$("#errorMassage").text(result.msg);
				}
			}else{
				$("#successConfirm").hide();
				$("#failConfirm").show();
				$("#errorMassage").text('无返回结果');
			}
			$('.footer').hide();
		}
	});
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
	if (phone.indexOf("*") < 0) {
	    if(!(/^1[3456789]\d{9}$/.test(phone))){ 
	        mui.toast('手机号码填写有误'); 
	        return false;
	    }
	}
	return true;
}

//验证身份证
function checkIdCard(idNo){
	if (idNo.indexOf("*") < 0) {
		var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		if(!regIdNo.test(idNo)){  
			mui.toast('身份证号填写有误');
		    return false;
		}
	}
	return true;
}

function submitCallBack(result){
	$("#projid").text("申报查询密码："+result.projpwd);
}

//文件图片上传
function loadImg(obj,success) {
    $(obj).prev().click();
//	$('#popWindow').show();
//	dd.device.notification.chooseImage({
//		upload: false,
//		onSuccess: function(data) {
//			var picSrc = data.picSrc;
//			if (picSrc == null || picSrc == undefined) {
//				mui.alert('APP版本过低，请升级后重新操作!');
//				return;
//			};
//			$('#popWindow').hide();
//			success(obj,materialCode,picSrc[0]);	
//		},
//		onFail: function(error) {
//			$('#popWindow').hide();
//		}
//	});
//	$('#popWindow').hide();
}
//普通文件添加参数
function getAttrData(obj,materialCode,base64){
	$(attrData).each(function(i, item) {
		if (item.materialCode == materialCode){
			item.certFile = base64.split(',')[1];
			//console.log(attrData);
//			$.ajax({
//				url:'materialOssAction!saveUserIf.action',
//				type:"POST",
//				data:{"code":busiCode,"materialCode":materialCode,"materialName":item.materialName,"base64":base64.split(',')[1]},
//				dataType:"json",
//				error:function(request,textStatus, errorThrown){
//				},
//				success:function(data){
//				}
//			});
			item.upload = "1";
			return false;
		}
	});
	$(obj).next().attr('src', base64);
	$(obj).next().show();
}

function findByBusiCode(){
	 $.ajax({
		url:'userAction!findByBusiCode.action',
		type:"POST",
		data:{"formBusiCode":busiCode},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			//console.log(data);
			if (data.item){
				var result = data.item;
				$("#id1").val(result.sxmc);
				$("#id2").val(result.lx);
				$("#id3").val(result.bljg);
				$("#id4").val(result.bjsx);
				$("#id5").val(result.sfsf);
				$("#id6").val(result.zxdh);
				$("#id7").val(result.sxid);
				document.title = result.sxmc;
			}
			if (data.userIf){
				var result = data.userIf;
				$("#id8").val(result.idnum);
				$("#id9").val(result.trueName);
				$("#id10").val(result.phone);
			}
			if (data.situations){
				var situationArry = data.situations;
				if (situationArry.length == 0 || (situationArry.length == 1 && situationArry[0].name == '缺省情形')) {

				} else {
					getSituation(situationArry);
				}
			}
			if (data.form){
				certCode=data.form;
				if (certCode != ''){
					addStep();
					var result = data.ybtx;
					$("#iframeInset").attr('src',"http://220.191.225.200:9080/index.html?cerNo="+result.idnum+"&appId="+result.appId+"&validateStr="+result.sign+"&noice="+result.noice+"&applicationTableCode="+certCode+"&areaCode="+result.areaCode);
				}
			}
		}
	});
}

/** 切换页面 **/
function nextStep1() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0)").addClass("on");
	$(".contentPanel:eq(0)").show();
	$(".operationBtn:eq(0)").show();
	$("#popWindow").hide();
	hideImg();
}

function nextStep2() {
	$("#popWindow").show();
	showImg();
	var res = getRadioRes('itemRadio');
    if (res == null) {
        mui.toast('请阅读须知！');
    } else {
    	$("#progressBar li").removeClass("on");
    	$(".contentPanel").hide();
    	$(".operationBtn").hide();
		$("#progressBar li:eq(0),#progressBar li:eq(1)").addClass("on");
		$(".contentPanel:eq(1)").show();
		$(".operationBtn:eq(1),.operationBtn:eq(2)").show();
    }
	$("#popWindow").hide();
	hideImg();
}

function nextStep3() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2)").addClass("on");
	$(".contentPanel:eq(2)").show();
	$(".operationBtn:eq(3),.operationBtn:eq(4)").show();
	$("#popWindow").hide();
	hideImg();
}

function nextStep4() {
	$("#popWindow").show();
	showImg();
//	if (checkInfo()){
		$("#progressBar li").removeClass("on");
		$(".contentPanel").hide();
		$(".operationBtn").hide();
		$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2),#progressBar li:eq(3)").addClass("on");
		$(".contentPanel:eq(3)").show();
		$(".operationBtn:eq(5),.operationBtn:eq(6)").show();
		//saveUser();
		if (certCode == '') {
			nextStep5();
		} else {
			$("#popWindow").hide();
			hideImg();
		}
//	} else {
//		$("#popWindow").hide();
//		hideImg();
//	}
}

function nextStep5() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2),#progressBar li:eq(3),#progressBar li:eq(4)").addClass("on");
	$(".contentPanel:eq(4)").show();
	$(".operationBtn:eq(7),.operationBtn:eq(8)").show();
	getMaterial();
	hideImg();
}

function nextStep6() {
	$("#popWindow").show();
	showImg();
	var flag = true;
	errorName = "";
	$(attrData).each(function(i, item) {
		if (item.isMust == 1 && item.upload != '1' && (item.certFile == '' || item.certFile == null)){
			flag = false;
			errorName = ":" + item.materialName;
			//mui.alert(item.materialName);
			return false;
		}
	});
	if (flag){
		$("#progressBar li").removeClass("on");
		$(".contentPanel").hide();
		$(".operationBtn").hide();
		$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2),#progressBar li:eq(3),#progressBar li:eq(4),#progressBar li:eq(5)").addClass("on");
		$(".contentPanel:eq(5)").show();
		submitForm();
	}else{
		mui.alert('请上传材料' + errorName);
		$("#popWindow").hide();
	}
	hideImg();
}

function lastStep1() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0)").addClass("on");
	$(".contentPanel:eq(0)").show();
	$(".operationBtn:eq(0)").show();
	$("#popWindow").hide();
	hideImg();
}

function lastStep2() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0),#progressBar li:eq(1)").addClass("on");
	$(".contentPanel:eq(1)").show();
	$(".operationBtn:eq(1),.operationBtn:eq(2)").show();
	$("#popWindow").hide();
	hideImg();
}

function lastStep3() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2)").addClass("on");
	$(".contentPanel:eq(2)").show();
	$(".operationBtn:eq(3),.operationBtn:eq(4)").show();
	$("#popWindow").hide();
	hideImg();
}

function lastStep4() {
	$("#popWindow").show();
	showImg();
	$("#progressBar li").removeClass("on");
	$(".contentPanel").hide();
	$(".operationBtn").hide();
	$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2),#progressBar li:eq(3)").addClass("on");
	$(".contentPanel:eq(3)").show();
	$(".operationBtn:eq(5),.operationBtn:eq(6)").show();
	if (certCode == '') {
		lastStep3();
	}
	$("#popWindow").hide();
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
	if (!checkPhone(mobile)){
		return false;
	}
	if (!checkIdCard(cerNo)){
		return false;
	}
	return true;
}

//判断是否阅读温馨提示
function getRadioRes(className) {
	var rdsObj = document.getElementsByClassName(className);
	var chackVal = null;
	for(var i = 0; i < rdsObj.length; i++) {
		if (rdsObj[i].checked) {
			chackVal = rdsObj[i].value;
		}
	}
	return chackVal;
}

function addStep(){
	$("#step1").removeClass("base_w-19pct");
	$("#step2").removeClass("base_w-19pct");
	$("#step3").removeClass("base_w-19pct");
	$("#step5").removeClass("base_w-19pct");
	$("#step6").removeClass("base_w-19pct");
	$("#step1").addClass("base_w-16pct");
	$("#step2").addClass("base_w-16pct");
	$("#step3").addClass("base_w-16pct");
	$("#step5").addClass("base_w-16pct");
	$("#step6").addClass("base_w-16pct");
	$("#step4").show();
}

function getSituation(situationArry) {
	$('#situationBox').empty();
	if (situationArry != null && situationArry != '' && situationArry.length > 0) {
		var dataStr = '';
		var arry = [];
		for (var i = 0; i < situationArry.length; i++) {
			if (situationArry[i].name == '缺省情形') {
				$('#situListBox li').eq(0).attr('data-code', situationArry[i].code);
			} else {
				if (arry[situationArry[i].type] == undefined) {
					arry[situationArry[i].type] = [];
				}
				arry[situationArry[i].type].push(situationArry[i]);
			}
		}
		for (var i = 0; i < arry.length; i++) {
			if (arry[i] != undefined) {
				dataStr += "<div class='formTitle'><span></span>" + arry[i][0].describe + "</div><ul class='checkboxChoose'>";
				for (var j = 0; j < arry[i].length; j++) {
					dataStr += '<li><b data-code="' + arry[i][j].code + '"></b>' + arry[i][j].name + '</li>';
				}
				dataStr += '</ul>';
			}
		}
		$('#situationBox').append(dataStr);
		$(".checkboxChoose li b").each(function(i) {
			$(this).click(function() {
				var selectId = $(this).attr("data-code");
				if (!$(this).hasClass('active')) {
					if ($(this).parent().siblings().find('b.active').attr('data-code') != undefined) {
						situationChoose.splice(jQuery.inArray($(this).parent().siblings().find('b.active').attr('data-code'),
							situationChoose), 1);
					}
					$(this).addClass('active');
					$(this).parent().siblings().find('b').removeClass('active');
					if (selectId != '') {
						situationChoose.push(selectId);
					} else {
						situationChoose = [];
					}
				} else {
					if (selectId != '') {
						$(this).removeClass('active');
						situationChoose.splice(jQuery.inArray(selectId, situationChoose), 1);
					}
				}
				if (selectId == '') {
					$(this).parent().siblings().find('b').removeClass('active');
					$(this).parent().parent().siblings().find('b').removeClass('active');
				} else {
					$(".checkboxChoose li b[data-code='']").removeClass('active');
				}
			});
		});
	}
}

function getSituations(){
	var situations = "";
	$(situationChoose).each(function(i, item) {
		situations += "," + item;
	});
	return situations.substring(1);
}

$('.uploadUrlBtn').each(function () {
    $(this).click(function () {
        loadImg($(this), eval($(this).attr('data-callback')))
    });
});
//
//function loadImg(_this, success) {
//    if (request_context == '/formserver' || request_context == '/form-yinchuan') {
//        $(_this).prev().click();
//    } else {
//        dd.device.notification.chooseImage({
//            //upload: false,
//            upload: true,
//            onSuccess: function (data) {
//                //  var picSrc = data.picSrc;
//                var picSrc = data.picPath;
//                if (picSrc === null) {
//                    showAlert('APP版本过低，请升级后重新操作!');
//                    return;
//                };
//                if (success) {
//                    success(_this, picSrc[0]);
//                }
//            },
//            onFail: function (error) {
//            }
//        })
//    }
//};

function uploadImage(_index, materialCode) {
	$("#file"+_index).click();
}

function showPic(_index, materialCode, obj) {
	var upload = document.getElementById('file'+_index);
	var file = upload.files[0];
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function(e) {
//	  var img = new Image();
//	  img.src = this.result;
	  base64 = this.result;
		$(attrData).each(function(i, item) {
			if (item.materialCode == materialCode){
				item.certFile = base64.split(',')[1];
				item.upload = "1";
				return false;
			}
		});
		$(obj).next().attr('src', base64);
		$(obj).next().show();
	};
	
}
