mui.init();//mui组件初始化
var busiCode=getQueryVariable('busiCode');
var innerCode=getQueryVariable('innerCode');
var formData={};//事项的基本信息数据
var attrData=[];//文件提交的数据
var postData={};//邮寄地址的数据
var formBusiCode;//表单的code,由createForm方法获取
var sfName=getQueryVariable('username');//申请人姓名
var sfId=getQueryVariable('idnum');//身份证Id
var idtype=getQueryVariable('idtype');//获取url证件类型
var mobile=getQueryVariable('mobile');//获取url参数手机号
var mask = mui.createMask();//mui组件蒙版定义,图片放大查看需要
var imgBase64='';//图片base64
var otherParam = {};//孩子姓名 
var moveCode;//随行人员id
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
	})
})

function test(){
    var url='ssoLogin.action';
    $.ajax({
        url:url,
        type:"post",
        data:{'ticket':'8afac0cc6b9f6c1c016bc6d410e60','sp':'app;1033f28c-1713-49fb-9945-fe3997ea6ff2'},
        dataType:"json",
        error:function(request,textStatus, errorThrown){
        	alert(2);
        },
        success:function(data){
        	alert(1);
            formBusiCode = data.data;
        }
    });
}


//创建表单
function createForm(){
    var url='tempInfoAction!createForm.action';
    $.ajax({
        url:url,
        type:"post",
        data:{'serviceId':serviceId,'sfId':sfId,'idtype':idtype,'sfName':sfName,'itemCode':itemCode,'formId':formId,'situationCode':itemCode},
        dataType:"json",
        error:function(request,textStatus, errorThrown){
        },
        success:function(data){
            formBusiCode = data.data;
        }
    });
}
//获取基本信息
/*
 * certCode：查询信息
 * success:成功回调函数
 */
function getBaseInfo(certCode,success){
	var url = 'tempInfoAction!getCertData.action';
	$("#popWindow").show();
	$.ajax({
		url:url,
		type:"GET",
		data:{
				"sfId":sfId,
				"sfName":sfName,
				"certCode":certCode
			},
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			if(data.returnCode==0){
				var datas=data.data;
				if(datas==null){
					$("#popWindow").hide();
					mui.alert('查询不到数据，您不具备办理此项资格');
					$('#step1').show();
					$('#step2').hide();
					$('#stepBtn1').show();
					$('#stepBtn2').hide();
				}else{
					success(datas)
				}
			}else{
				$('.stepBtn').attr("disabled",true);
				$("#popWindow").hide();
				mui.alert(data.returnMessage);
			}
		}
	});
};
//基本信息回显处理
function baseInfoData(datas){
	$("#popWindow").hide();
	if(datas.length>0){
		for(var i=0;i<datas.length;i++){
			for(var item in datas[0]){
				$('div[data-field="'+item+'"]').text(datas[0][item]);
				$('input[data-field="'+item+'"]').val(datas[0][item]);
			}
		}
	}
}
//获取材料
/*
 *sfId:身份证号(必填)
 *sfName:姓名(必填)
 *itemCode：事项code(必填)
 *situationCode：事项code(非必填)
 *otherParam：孩子姓名(非必填)
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
	if(itemCode!='a4a4aa9e-143d-412d-a1cd-bc0e70b7bd7b'){
		getFormData($('#addForm'));
	}
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
//下一步
function next(obj){
	console.log(obj);
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
//手机号码验证
function checkPhone(phone,obj){ 
    if(!(/^1[3456789]\d{9}$/.test(phone))){ 
        mui.toast('手机号码有误，请重填！'); 
        $(obj).val('');
    } 
}
//验证身份证
function IdCard(idNo){
	var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if(!regIdNo.test(idNo)){  
		mui.toast('身份证号填写有误');  
	    return false;
	}  
}
//随机生成uuid
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    uuidNew = s.join("");
    return uuidNew;
}
//不对图片进行压缩，直接转成base64
function directTurnIntoBase64(fileObj,callback){
	var r = new FileReader();
	// 转成base64
	r.onload = function(){
    	//变成字符串
    	imgBase64 = r.result;
    	callback(imgBase64);
	}
	r.readAsDataURL(fileObj); //转成Base64格式
}
var picFile={};
function inputChange(obj) {
	$('#popWindow').show();
	picFile['certName']=obj.files[0].name;
   	directTurnIntoBase64(obj.files[0],function(e){
   		picFile['materiaName']='驾驶证照片';
   		picFile['materiaCode']='f7df696b-37ec-4647-82aa-5e13d2dfb9a6';
   		$.ajax({
   		 	url: 'https://apicall.id-photo-verify.com/api/cut_pic',
          	method:'post',
	    	data: JSON.stringify({
		    	"spec_id": '5',//这个是合成哪种类型的证件照，1代表一寸蓝底
		    	"app_key": '33a598751824ee147aa46fdda2cf9e5a2e7b635a',//这个是自己申请KEY
		    	"file": e.split(',')[1]
	    	}),
	    	contentType: "application/json", 
	    	success: function (data) {
	    		if(data.code==200){
	    			getReturnImg(data.result.file_name[0]);
	    		}else{
	    			mui.alert(data.result);
	    			$('#popWindow').hide();
	    		}
	    	},
	    	error: function () {
	    		$('#popWindow').hide();
	    		mui.toast('上传文件发送请求失败！'); 
	    	}
	    }) 
   	})
}
function getReturnImg(code){
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
                $('.img').attr("src",e.target.result);
                picFile['certFile']=encodeURIComponent(e.target.result.split(',')[1]);
                attrData.push(picFile);
            }
        }
        else{
        	mui.toast('制作发送请求失败！'); 
        }
    }
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
