mui.init();//mui组件初始化
var formData={};//事项的基本信息数据
var attrData=[];//文件提交的数据
var postData={};//邮寄地址的数据
var formBusiCode=getQueryVariable('formBusiCode');;//表单的code,由createForm方法获取
var mask = mui.createMask();//mui组件蒙版定义,图片放大查看需要
var imgBase64='';//图片base64
var otherParam = {};//孩子姓名 
var moveCode;//随行人员id
var picFile={};//上传拍照图片，一寸照
var step=0;//上下步判断。
var cityId='';//当前城市所属辖区
var isNullFlag = false;//判断表单信息是否为空的标识
var dZJCXLixt = [
	{'name':'A1','value':'A3B1B2'},
	{'name':'A1A2','value':'A2A3'},
	{'name':'A2','value':'B1B2'},
	{'name':'A2A3','value':'A3B1B2'},
	{'name':'A3','value':'C1'},
	{'name':'A3B1','value':'B1'},
	{'name':'A3B2','value':'B2'},
	{'name':'A3B1B2','value':'B1B2'},
	{'name':'B1','value':'C1M'},
	{'name':'B1B2','value':'B2'},
	{'name':'B2','value':'C1M'}
]
$(function(){
	var windowHeight=window.innerHeight;
	$(".content").css("height",windowHeight-130);
	//加载浙里办方法
	dd.ready({
		usage: [
			'dd.device.notification.chooseImage',
			'dd.biz.util.selectLocalCity',
	 	],
	})
})
//单选
$(".radioChoose").each(function(j){
	$(this).find("b").each(function(i){
		if(i==0){
			formData[$(this).parent().parent().attr("data-submit")]=$(this).attr('data-value');
			$('#step4 div[data-field="'+$(this).parent().parent().attr("data-submit")+'"]').text($(this).parent().text());
		}
		$(this).click(function() {
			if (!$(this).hasClass('active')) {
				$(this).addClass('active');
				$(this).parent().siblings().find('b').removeClass('active');
				formData[$(this).parent().parent().attr("data-submit")]=$(this).attr('data-value');
				$('#step4 div[data-field="'+$(this).parent().parent().attr("data-submit")+'"]').text($(this).parent().text());
			} 
		})
	});
})
//上一步
$(".prevBtn").click(function() {
	if (step == 2) {
		$(".step li").eq(1).nextAll().removeClass("on");
		$("#step2").show().siblings().hide();
		$("#stepBtn2").show().siblings().hide();
	}else if (step == 1) {
		$(".step li").eq(0).nextAll().removeClass("on");
		$("#step1").show().siblings().hide();
		$("#stepBtn1").show().siblings().hide();
		$('#prevBtn').hide();
		$('.nextBtn').width('100%')

	}else if (step == 3){
		$("#step2").show().siblings().hide();
		$("#stepBtn2").show().siblings().hide();
		$(".step li").eq(2).nextAll().removeClass("on");
		step--;
	}
	step--;
});
//地址长度控制
// $('#newAddress').bind('textarea propertychange', 'textarea', function() {
// 	var curLength = $(that._els.replay).val().trim().length;
// 	if (curLength < 14) {
// 		var num = $(that._els.replay).val().trim().substr(0, 140);
// 		$(that._els.replay).val(num);
// 		alert("超过字数限制，多出的字将被截断！");
// 	}
// 	$("#newAddressCount").text($(that._els.replay).val().trim().length);
// });
//获取基本信息
/*
 * certCode：查询信息
 * success:成功回调函数
 */
function getBaseInfo(certCode,success,otherParam){
	return;
	var url = '/yztb-ga/tempInfoAction!getFormData.action';
	$("#popWindow").show();
	$.ajax({
		url:url,
		type:"GET",
		data:{
			"formBusiCode":formBusiCode,
			"certCode":certCode,
			"otherParam":otherParam
		},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			$("#popWindow").hide();
			if(data.returnCode==0){
				var datas=data.data.certData;
				if(datas==null){
					if(certCode!=''){
						mui.alert('未查询到相关数据，不能线上办理！');
						$('#stepBtn1').attr('disabled','disabled');
					}else{
						success(data.data)
					}
				}else{
					success(data.data)
				}
			}else{
				mui.alert(data.returnMessage);
				$('#stepBtn1').attr('disabled','disabled');
			}
		}
	});
};
//基本信息回显处理
function baseInfoData(datas){
	$("#popWindow").hide();
	var personInfo=datas.personInfo;
	var certData=datas.certData;
	if(certData!=null){
		for(var item in certData[0]){
			if(item=='YXQZ'){
				var yxqz=new Date(certData[0][item]);
				$('div[data-field="'+item+'"]').text(yxqz.getFullYear() + "-" + (yxqz.getMonth() + 1)+'-'+yxqz.getDate());
			}else{
				$('div[data-field="'+item+'"]').text(certData[0][item]);
				$('input[data-field="'+item+'"]').val(certData[0][item]);
			}
			$('#addForm *[data-submit]').each(function(){
				if($(this).attr('data-field')==item){
					if($(this).attr('data-submit')=='hpzl'){
						formData[$(this).attr('data-submit')]=certData[0]['HPZLCODE'];
					}else if($(this).attr('data-submit')=='hphm'){
						formData[$(this).attr('data-submit')]='浙'+certData[0][$(this).attr('data-field')];
					}else{
						formData[$(this).attr('data-submit')]=certData[0][$(this).attr('data-field')];
					}
				}
			});
		}
	}
	for(var item in personInfo){
		if(item=='cerName'){
			$('div[data-field="'+item+'"]').text('*'+personInfo[item].substring(1,(personInfo[item].length)));
		}else if(item=='cerNo'){
			$('div[data-field="'+item+'"]').text(personInfo[item].substring(0,10)+'****'+personInfo[item].substring(14,personInfo.length));
		}else{
			$('div[data-field="'+item+'"]').text(personInfo[item]);
		}
		$('input[data-field="'+item+'"]').val(personInfo[item]);
		$('#addForm *[data-submit]').each(function(){
			if($(this).attr('data-field')==item){
				formData[$(this).attr('data-submit')]=personInfo[$(this).attr('data-field')];
			}
		});
	}
}
//获取材料
/*
 *sfId:身份证号(必填)
 *sfName:姓名(必填)
 *situationCode：事项code(非必填)
 *otherParam：孩子姓名(非必填)
 * */
function getMaterial(){
	$("#popWindow").show();
	var url = '/yztb-ga/tempInfoAction!findTempInfoAll.action';
	$('#docListBox').html('');
	next($('#stepBtn2'));
	$.ajax({
        url: url,
        type: "POST",
        data:{
        	"formBusiCode":formBusiCode,
        	"situationCode":moveCode,
        	"otherParam":otherParam,
		},
        dataType: "json",
        error: function(request, textStatus, errorThrown) {},
        success: function(data) {
        	$("#popWindow").hide();
            if (data.returnCode == 0) {
                var datas = data.data;
                if (datas != null) {
                	flag=true;
                    var dataStr = '';
                    for (var i = 0; i < datas.length; i++) {
						var picDoc={};
						picDoc['certName']=datas[i].certName;
						picDoc['materialCode']=datas[i].materialCode;
						picDoc['materialName']=datas[i].materialName;
                    	attrData.push(picDoc);
                        if (datas[i].code == 0) {
                        	dataStr += '<div class="f_mll10"><div class="bgFEEEEE f_pl10 f_lh40 f_teFF0000"><p class="f_lh20" style="height:30px;">'+(i+1)+'、'+datas[i].materialName+'</p>'
                                +'材料缺失，请到线下提交办理！</div></div>'; 
                            $("#stepBtn3").attr("disabled",true);
                        } else {
                            dataStr += '<div class="f_mll10"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">请核对您的材料，如有错误请到线下办理！<p class="f_lh20" style="height:40px;">'+(i+1)+'、'+datas[i].materialName+'</p>'
                            +'</div><div class="docList">'
                            +'<img src="data:image/png;base64,'+datas[i].certFile+'" /></div></div>';
                        }
                    }
					$('#docListBox').append(dataStr);
                }else{
                	mui.toast('该事项无需提交材料，为您直接办理！');
                }
            }else{
            	$("#stepBtn3").attr("disabled",true);
            	$('#docListBox').html('');
				mui.alert(data.returnMessage); 
			}
        }
    });
}
function getMaterial2(){
	$("#popWindow").show();
	var url = '/yztb-ga/tempInfoAction!findTempInfoAll.action';
	$('#docListBox').html('');
	next($('#stepBtn2'));
	$.ajax({
        url: url,
        type: "POST",
        data:{
        	"formBusiCode":formBusiCode,
        	"situationCode":moveCode,
        	"otherParam":otherParam,
		},
        dataType: "json",
        error: function(request, textStatus, errorThrown) {},
        success: function(data) {
        	$("#popWindow").hide();
            if (data.returnCode == 0) {
                var datas = data.data;
                if (datas != null) {
                	flag=true;
                    var dataStr = '';
                    for (var i = 0; i < datas.length; i++) {
						var picDoc={};
						picDoc['certName']=datas[i].certName;
						picDoc['materialCode']=datas[i].materialCode;
						picDoc['materialName']=datas[i].materialName;
                    	attrData.push(picDoc);
                        if (datas[i].code != 0) {
                            dataStr += '<div class="f_mll10"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">请核对您的材料，如有错误请到线下办理！<p class="f_lh20" style="height:40px;">'+(i+1)+'、'+datas[i].materialName+'</p>'
                            +'</div><div class="docList">'
                            +'<img src="data:image/png;base64,'+datas[i].certFile+'" /></div></div>';
                        }
                    }
					$('#docListBox').append(dataStr);
                }else{
                	mui.toast('该事项无需提交材料，为您直接办理！');
                }
            }else{
            	$("#stepBtn3").attr("disabled",true);
            	$('#docListBox').html('');
				mui.alert(data.returnMessage); 
			}
        }
    });
}
//校验驾驶证
function checkJsz2(otherParam){
	return;
	 $.ajax({
	 	url:'/yztb-ga/tempInfoAction!getFormData.action',
		type:"get",
		data:{
			'formBusiCode':'e686750e658f430aa15968b23a61fbba',
			'certCode':'f2628a03-0703-4615-b235-0bd267e5a4ed',
			'otherParam':otherParam
		},
	 	dataType:"json",
	 	error:function(request,textStatus, errorThrown){
	 	},
	 	success:function(data){
	 		var datas=data.data.certData;
	 		if(data.returnCode!=0){
	 			mui.alert(data.returnMessage);
	 		}else{
	 			if(datas.code!='01'){
					 mui.alert(data.data.msg);
					 $('#stepBtn1').attr('disabled','disabled');
	 			}else{
					$('textarea[data-field="lxzsxxdz"]').val(datas.data[0].lxzsxxdz);
					next($('#stepBtn1'));
	 			}
	 		}
	 	}
	 });
}
function checkJsz(fzjg){
	var url='/yztb-ga/tempInfoAction!checkJsz.action?formBusiCode='+formBusiCode+'&fzjg=' + encodeURI(encodeURI(fzjg));
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			var datas=data.data;
			if(data.returnCode!=0){
				mui.alert(data.returnMessage);
			}else{
				if(datas.code!='01'){
					mui.alert(data.data.msg);
					$('#stepBtn1').attr('disabled','disabled');
				}else{
				   $('textarea[data-field="lxzsxxdz"]').val(datas.data.lxzsxxdz);
				   next($('#stepBtn1'));
				}
			}
		}
	});
}
//校验行驶证
function checkXsz(hpzl,hphm){
	 var url='/yztb-ga/tempInfoAction!checkXsz.action?formBusiCode='+formBusiCode+'&hpzl=' + hpzl+'&hphm=' + encodeURI(encodeURI("浙"))+ hphm;
	 $.ajax({
	 	url:url,
	 	type:"get",
	 	dataType:"json",
	 	error:function(request,textStatus, errorThrown){
	 	},
	 	success:function(data){
	 		var datas=data.data;
	 		if(data.returnCode!=0){
	 			mui.alert(data.returnMessage);
	 		}else{
	 			if(datas.code!='01'){
					mui.alert(data.data.msg);
					$('#stepBtn1').attr('disabled','disabled');
	 			}else{
					next($('#stepBtn1'));
					$('textarea[data-field="zsxxdz"]').val(datas.data.zsxxdz);
					$('div[data-field="zsxxdz"]').text(datas.data.zsxxdz);
	 			}
				if(datas.code=='01'&& datas.data!=null){
					var yxqz=new Date(datas.data.yxqz);
					$('div[data-field="YXQZ"]').text(yxqz.getFullYear() + "-" + (yxqz.getMonth() + 1)+'-'+yxqz.getDate());
				}
	 		}
	 	}
	 });
}
function submitForm(){
	$("#popWindow").show();
	$.ajax({
		url:'/yztb-ga/tempInfoAction!saveFormData.action',
		type : 'post',
		data:{'formInfo':JSON.stringify(formData),'postInfo':JSON.stringify(postData),'attrInfo':JSON.stringify(attrData),'formBusiCode':formBusiCode},
		error:function(request,textStatus, errorThrown){
			$("#popWindow").hide();
		},
		success:function(data){	
			if(data.data!=''&&data.data!=null){
				var datas=data.data.sendResult;
				if(datas!=null){
					var result = JSON.parse(datas);
					if(result.code == '01'){
						$("#successConfirm").show();
						$("#failConfirm").hide();
						submitCallBack(result);
					}else{
						$("#successConfirm").hide();
						$("#failConfirm").show();
						$("#errorMassage").text(result.data.msg);
					}
				}else{
					$("#failConfirm").show();
					$("#errorMassage").text(data.data.returnMessage);
				}
			}else{
				$("#failConfirm").show();
				$("#errorMassage").text('无返回结果！');
			}
			$("#popWindow").hide();
			$('#step5').show().siblings().hide();
			$('.footer').hide();
		}
	});
}
//校验，传参本身，校验类型
function validate(obj,type){
	switch(type){
        case 'phoneNum':
			checkPhone($(obj).val(),$(obj));
          	break;
        case 'idNum':
			idCard($(obj).val(),$(obj));
          	break;
	}
}
//校验表单信息是否完善
function checkNull(isMaterial,isMaterial2){
	isNullFlag = false;
	$('#step2 .mui-input-clear').each(function(){
		if(($(this).val() == '') && !($(this).parent().hasClass('hidden'))){
			isNullFlag = true;			
			mui.toast($(this).prev().text() +'缺失，请完善！');
			return false;
		}
	})
	if(!isNullFlag && isMaterial){
		if(isMaterial2){
			getMaterial2();
		}else{
			getMaterial();
		}		
	}else if(!isNullFlag && !isMaterial){
		next($('#stepBtn2'));
		$('#stepBtn3').click();
	}	
}
//办理车辆选择
function getCarChoose(datas,obj){
	var selectArry=[];
	for(var i=0;i<datas.length;i++){
		var selectObj={};
		selectObj['value']=datas[i].HPZL+datas[i].HPHM;
		selectObj['text']=datas[i].HPZL+datas[i].HPHM;
		selectArry.push(selectObj);
	}
	checkXsz(datas[0].HPZLCODE,datas[0].HPHM);
	var userPicker = new mui.PopPicker();
	userPicker.setData(selectArry);
	$(obj).val(selectArry[0].text);
	$(obj).click(function(event) {
		userPicker.show(function(items) {
			$(obj).val(items[0].text);
			for(var i=0;i<datas.length;i++){
				for(var item in datas[i]){
					if(datas[i].HPHM==items[0].text){
						$('div[data-field="'+item+'"]').text(datas[i][item]);
						$('input[data-field="'+item+'"]').val(datas[i][item]);
						$('div').each(function(){
							if($(this).attr('data-submit')){
								if($(this).attr('data-submit')=='hpzl'){
									formData[$(this).attr('data-submit')]=datas[i]['HPZLCODE'];
								}else if($(this).attr('data-submit')=='hphm'){
									formData[$(this).attr('data-submit')]='浙'+datas[i][$(this).attr('data-field')];
								}else{
									formData[$(this).attr('data-submit')]=datas[i][$(this).attr('data-field')];
								}
							}
						});
						checkXsz(datas[i].HPZLCODE,datas[i].HPHM);
					}
				}
			}
		});
	});
}
//普通选择器
function getPickerChoose(datas,obj,success){
	var userPicker = new mui.PopPicker();
	userPicker.setData(datas);
	// $(obj).val(datas[0].text);
	// $('div[data-field="'+$(obj).attr('name')+'"]').text(datas[0].text);
	// formData[$(obj).attr('name')]=datas[0].value;
	$(obj).click(function(event) {
		userPicker.show(function(items) {
			$(obj).val(items[0].text);
			$('div[data-field="'+$(obj).attr('name')+'"]').text(items[0].text);
			formData[$(obj).attr('name')]=items[0].value;
			if(success){
				success(items,obj);
			}
		});
	});
}
//时间选择器
function getDateChoose(obj,success){
	var dtPicker = new mui.DtPicker({type: 'date'});
	$(obj).click(function() {
		dtPicker.show(function(selectItems) {
			$(obj).val(selectItems.text);
			$('div[data-field=' +$(obj).attr("name") + ']').text(selectItems.text);
			if(success){
				success(selectItems);
			}
		})
	})
}
//邮寄地址数据拼接
function getPostData() {
	$('.sendAddress .mui-input-clear').each(function(){
		postData[$(this).attr('name')]=$(this).val();
		if($(this).attr('data-field')=='cerName'||$(this).attr('data-field')=='mobile'){
			$('.sendAddress div[data-field="'+$(this).attr('name')+'"]').text($(this).val());
		}else{
			$('div[data-field="'+$(this).attr('name')+'"]').text($(this).val());
		}
	});
    return postData;
}
function getFormData() {
	$('.sendAddress').siblings().find('.mui-input-clear').each(function(){
		if($(this).attr('name')){
			var reg = new RegExp("(\-)", "g");
			formData[$(this).attr('name')]=$(this).val().replace(reg, '');
		}
		if($(this).attr('data-field')=='cerName'){
			$('.sendAddress div[data-field="'+$(this).attr('data-field')+'"]').text($(this).val());
		}else{
			$('div[data-field="'+$(this).attr('data-field')+'"]').text($(this).val());
		}
	});
    return formData;
}
//缴费方法
function jfFun(){
	if(jfLink!=''){
		window.open(jfLink);
	}
}
function showModal(){
	$('#infoModal').show();
}
//关闭弹窗
function closeModal(obj){
	$('#infoModal').hide();
}
//放大图片隐藏
function hideImg(){
	$('#bigImg').hide();
	mask.close();
}
//放大图片展示
function showImg(obj){
	$('#bigImg').show();
	$('#bigImg .img').attr('src',$(obj).attr('src'));
	mask.show();
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
//计算时间相差
function dateDif(enddate){
	var date = enddate - new Date().getTime(); 
	var days    = date / 1000 / 60 / 60 / 24;
	var daysRound   = Math.floor(days);
	return daysRound
}
//手机号码验证
function checkPhone(phone,obj){ 
    if(!(/^1[3456789]\d{9}$/.test(phone))){ 
        mui.toast('手机号码有误，请重填！'); 
        $(obj).val('');
    } 
}
//验证身份证
function idCard(idNo,obj){
	var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if(!regIdNo.test(idNo)){  
		mui.toast('身份证号填写有误');  
		$(obj).val('');
	}  
}
//地址输入提示
function initBaiduMapAddress(id,success) {
    // 百度地图API功能
    var map = new BMap.Map("l-map");
    var to = null;
    to = new BMap.Autocomplete({
		"input": id,
		"location": map,
	});
    to.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
        if (success) {
            var item = e.item.value
            success(item.city + item.district, item.province + item.city + item.district+item.street + item.business)
        }
    });
}
//文件图片上传
function loadImg(obj,success) {
	$('#popWindow').show();
	dd.device.notification.chooseImage({
		upload: false,
		onSuccess: function(data) {
			alert(JSON.stringify(data));
			var picSrc = data.picPath;
			if (picSrc === null) {
				mui.alert('APP版本过低，请升级后重新操作!');
				return;
			};
			$('#popWindow').hide();
			success(obj,picSrc[0]);	
		},
		onFail: function(error) {
			$('#popWindow').hide();
		}
	});
}
//普通文件添加参数
function getAttrData(obj,base64){
	alert(1);
	var picDoc={};
	alert(2);
	picDoc['materialName']=$(obj).next().attr('materialName');
	picDoc['certName']=$(obj).next().attr('certName');
	picDoc['materialCode']=$(obj).next().attr('materialCode');
	alert(3);
	var code = $(obj).next().attr('materialCode');
	alert(4);
	picDoc['certFile']=base64.split(',')[1];
	alert(5);
	attrData.push(picDoc)
	alert(6);
	$(obj).next().attr('src', base64);
	alert(7);
	$('.' + code).attr('src',base64);
}
function inputChange(obj,base64) {
	$('#popWindow').show();
	$.ajax({
		url: 'https://apicall.id-photo-verify.com/api/cut_pic',
		method:'post',
		data: JSON.stringify({
			"spec_id": '5',//这个是合成哪种类型的证件照，1代表一寸蓝底
			"app_key": '33a598751824ee147aa46fdda2cf9e5a2e7b635a',//这个是自己申请KEY
			"file": base64.split(',')[1]
		}),
		contentType: "application/json", 
		success: function (data) {
			if(data.code==200){
				getReturnImg(obj,data.result.file_name[0]);
			}else{
				$('#popWindow').hide();
				mui.alert(data.result);
			}
		},
		error: function () {
			$('#popWindow').hide();
			mui.toast('上传文件发送请求失败！'); 
		}
	})
	picDoc['materialName']=$(obj).next().attr('materialName');
	picDoc['certName']=$(obj).next().attr('certName');
	picDoc['materialCode']=$(obj).next().attr('materialCode');
}
function getReturnImg(obj,code){
	var url='https://apicall.id-photo-verify.com/api/take_cut_pic';   //请求的URl
    var xhr = new XMLHttpRequest();		//定义http请求对象
    xhr.open("POST", url, true);		
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(JSON.stringify({
    	//"file_name": '73c374fc8f3211e9a63600163e0070b624863white2',//调用之前做过的图片不花钱
    	"file_name":code,
		"app_key": '33a598751824ee147aa46fdda2cf9e5a2e7b635a'
	}));
    xhr.responseType = "blob";  // 返回类型blob
    xhr.onload = function() {   // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
    	$('#popWindow').hide();
        if (this.status===200) {
            var blob = this.response;
            var reader = new FileReader();
            reader.readAsDataURL(blob);  // 转换为base64，可以直接放入a表情href
            reader.onload=function (e) {
                $(obj).next().attr('src', base64);
				$('#step4 img[certName='+$(obj).next().attr('certName')+']').attr('src',base64);
                picFile['certFile']=encodeURIComponent(e.target.result.split(',')[1]);
                attrData.push(picFile);
            }
        }
        else{
        	mui.toast('制作发送请求失败！'); 
        }
    }
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
	$("#prevBtn").show();
	$(".footer div").width('49%')
	step++;
}
//设置第二天时间
function getNextDay(selectItems){
	var year=parseInt(selectItems.y.text);
	var month=parseInt(selectItems.m.text);
	var day=parseInt(selectItems.d.text);
	var textDate='';
	if((day==30&&(month==4||month==6||month==9||month==11))||(day==31&&(month==1||month==3||month==5||month==7||month==8||month==10))||(((day==28&&year%4!=0)||(day==29&&year%4==0))&&month==2)){
		if(month<10){
			textDate=year+'-0'+(month+1)+'-01';
		}else{
			textDate=year+'-'+(month+1)+'-01';
		}
	}else if(day==31&&month==12){
		textDate=(year+1)+'-01'+'-01';
	}else{
		if(month<10){
			if(day<10){
				textDate=year+'-0'+month+'-0'+(day+1);
			}else{
				textDate=year+'-0'+month+'-'+(day+1);
			}
		}else{
			if(day<10){
				textDate=year+'-'+month+'-0'+(day+1);
			}else{
				textDate=year+'-'+month+'-'+(day+1);
			}
		}
	}
	return textDate;
}
//获取号牌种类字典表
function getHpzl(){
	$.ajax({					
		url:'/yztb-ga/rest/address/dictionaries',
		type:'post',
		dataType:'json',
		data:{"template":"hpzl"},
		async:false,
		cache : false,
		success:function(data){
			var hpzlArry=[];
			if(data.data != null){							
				for(var i=0;i<data.data.length;i++){
					var hpzlObj = {};															
					hpzlObj['text'] = data.data[i].explain;
					hpzlObj['value'] = data.data[i].code;
					hpzlArry.push(hpzlObj);
				}
				getPickerChoose(hpzlArry,$('#showUserPicker3'),getHpzlCode);
			}

		},
		error:function(request,textStatus, errorThrown){}
	})
}
function getHpzlCode(item,obj){
	$(obj).attr('data-value',item[0].value);
}
function getToday(){
	var defaultBeginDate = new Date();
	var defaultYear =  defaultBeginDate.getFullYear(); 
	var defaultMonth = defaultBeginDate.getMonth()+1; 
	var defaultDay = defaultBeginDate.getDate();
	if(defaultMonth < 10){
		defaultMonth = '0' + defaultMonth;
	}
	if(defaultDay < 10){
		defaultDay = '0' + defaultDay;
	}
	var dataStr={
		'y':{'text':defaultYear},
		'm':{'text':defaultMonth},
		'd':{'text':defaultDay},
		'all':defaultYear +'-'+ defaultMonth +'-'+ defaultDay
	};
	return dataStr;
}
//获取当前所属辖区
function bindClick() {
	dd.biz.util.selectLocalCity({
		onSuccess: function (data) {
			cityId=data.cityId
		},
		onFail: function (res) {
		}
	});
}
//判断车辆类型
function configCllx(data){
	var resultData;
	$.ajax({					
		url:'/yztb-ga/rest/address/checkcllx',
		type:'post',
		dataType:'json',
		data:{"cllx":data},
		async:false,
		cache : false,
		success:function(data){
			if(data !=null){
				resultData = data;
			}
		},
		error:function(request,textStatus, errorThrown){}
	})
	return resultData;
}
//降级准驾车型
function downZJCX(data,checkData){
	if(data=='C1'){
		mui.toast('您的C1准驾车型无法降级！');
		$('#stepBtn1').attr('disabled','disabled');
	}else{
		checkJsz(checkData);
		for(item of dZJCXLixt){
			if(item.name == data){
				$('div[data-field="DZJCX"]').text(item.value);
				FormData["DZJCX"]=item.value
			}
		}
	}	
}