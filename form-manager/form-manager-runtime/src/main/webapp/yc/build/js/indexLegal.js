mui.init();//mui组件初始化
var busiCode=getQueryVariable('busiCode');
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
			'dd.biz.util.selectLocalCity',
	 	],
	});
});

//获取材料
/*
 *situationCode：事项code(非必填)
 * */
function getMaterial(){
	$('#wxts').show();
	var url = 'tempInfoAction!findLegalTempInfoAll.action';
	$('#docListBox').html('');
	$.ajax({
        url: url,
        type: "POST",
        data:{
        	"busiCode":busiCode,
        	"situations":getSituations()
        },
        dataType: "json",
        error: function(request, textStatus, errorThrown) {
        	$("#popWindow").hide();
        	$('#wxts').hide();
        },
        success: function(data) {
        	$("#popWindow").hide();
        	$('#wxts').hide();
            if (data.returnCode == 0) {
                var datas = data.data;
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
                        } else {
                            dataStr += '<div class="f_mll10"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">请核对您的材料，如有错误请到线下办理！<p class="f_lh20" style="height:30px;">'+(i+1)+'、'+datas[i].materialName+'</p>'
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
	//getFormData($('#addForm'));
	$.ajax({
		url:'tempInfoAction!saveLegalFormData.action',
		type : 'post',
		data:'busiCode='+busiCode+"&situations="+getSituations()+'&attrInfo='+JSON.stringify(attrData),
		//data:{'busiCode':busiCode,"situations":getSituations(),'formInfo':JSON.stringify(formData),'postInfo':JSON.stringify(postData),'attrInfo':JSON.stringify(attrData)},
		error:function(request,textStatus, errorThrown){
			$('#popWindow').hide();
		},
		success:function(data){
			$('#popWindow').hide();
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
			//item.certFile = base64.split(',')[1];
			//console.log(attrData);
			$.ajax({
				url:'materialOssAction!saveLegal.action',
				type:"POST",
				data:{"code":busiCode,"materialCode":materialCode,"materialName":item.materialName,"base64":base64.split(',')[1]},
				dataType:"json",
				error:function(request,textStatus, errorThrown){
				},
				success:function(data){
				}
			});
			item.upload = "1";
			return false;
		}
	});
	$(obj).next().attr('src', base64);
	$(obj).next().show();
}

function findByBusiCode(){
	 $.ajax({
		url:'legalAction!findByBusiCode.action',
		type:"POST",
		data:{"busiCode":busiCode},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			if (data.item){
				var result = data.item;
				$("#id1").val(result.name);
				$("#id2").val("关于申请"+result.name);
				document.title = result.name;
			}
			if (data.legal){
				var result = data.legal;
				$("#id3").val(result.companyName);
				$("#id4").val(result.cerNo);
			}
			if (data.situations){
				var situationArry = data.situations;
				if (situationArry.length == 0 || (situationArry.length == 1 && situationArry[0].name == '缺省情形')) {

				} else {
					getSituation(situationArry);
				}
			}
			if (data.form){
				var results = data.form;
				if (results){
					$(results).each(function(i, item) {
						if (item.code){
							certCode=item.code;
						}
					});
				}
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
	if (checkInfo()){
		$("#progressBar li").removeClass("on");
		$(".contentPanel").hide();
		$(".operationBtn").hide();
		$("#progressBar li:eq(0),#progressBar li:eq(1),#progressBar li:eq(2),#progressBar li:eq(3)").addClass("on");
		$(".contentPanel:eq(3)").show();
		$(".operationBtn:eq(5),.operationBtn:eq(6)").show();
		saveUser();
		if (certCode == '') {
			nextStep5();
		} else {
			$("#popWindow").hide();
			hideImg();
		}
	} else {
		$("#popWindow").hide();
		hideImg();
	}
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
	var fddbr = $("#id5").val();
	var frsfz = $("#id6").val();
	var lxrxm = $("#id7").val();
	var lxrsj = $("#id8").val();
	var lxrsfz = $("#id9").val();
	var lxrdh = $("#id10").val();
	var lxrdz = $("#id11").val();
	var postcode = $("#id12").val();
	var memo = $("#id13").val();
	$.ajax({
		url:'legalAction!update.action',
		type : 'post',
		data:{
				'fddbr':fddbr,
				'frsfz':frsfz,
				'lxrxm':lxrxm,
				'lxrsj':lxrsj,
				'lxrsfz':lxrsfz,
				'lxrdh':lxrdh,
				'lxrdz':lxrdz,
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
	var fddbr = $("#id5").val();
	var frsfz = $("#id6").val();
	var lxrxm = $("#id7").val();
	var lxrsj = $("#id8").val();
	var lxrsfz = $("#id9").val();
	var lxrdz = $("#id11").val();
	if (fddbr == ""){
		mui.toast('法定代表人不能为空');
		return false;
	}
	if (frsfz == ""){
		mui.toast('法人身份证不能为空');
		return false;
	}
	if (lxrxm == ""){
		mui.toast('联系人姓名不能为空');
		return false;
	}
	if (lxrsj == ""){
		mui.toast('联系人手机不能为空');
		return false;
	}
	if (lxrsfz == ""){
		mui.toast('联系人身份证不能为空');
		return false;
	}
	if (lxrdz == ""){
		mui.toast('联系人通讯地址不能为空');
		return false;
	}
	if (!checkIdCard(frsfz)){
		return false;
	}
	if (!checkPhone(lxrsj)){
		return false;
	}
	if (!checkIdCard(lxrsfz)){
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