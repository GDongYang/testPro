mui.init();//mui组件初始化
var formData={};//事项的基本信息数据
var attrData=[];//文件提交的数据
var postData={};//邮寄地址的数据
var formBusiCode;//表单的code,由createForm方法获取
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
//初始化获取基础数据
function getInfo(){
	var url='tempInfoAction!getCertData.action?sfId='+sfId+'&sfName='+sfName+'&certCode='+certCode;
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			if(data.returnCode==0){
				var datas=data.data;
				var selectArry=[];
				if(datas.length>0){
					for(var i=0;i<datas.length;i++){
						$('div').each(function(){
			    			if($(this).attr('data-submit')){
			    				formData[$(this).attr('data-submit')]=datas[0][$(this).attr('data-field')];
			    			}
			    		});
						for(var item in datas[0]){
							if(item!='YXQZ'){
								$('input[data-field="'+item+'"]').val(datas[0][item]);
								 if(item=='XM'){
									$('div[data-field="'+item+'"]').text('*'+datas[0][item].substring(1,(datas[0][item].length)));
								}else if(item=='SFZMHM'){
									$('div[data-field="'+item+'"]').text(datas[0][item].substring(0,10)+'****'+datas[0][item].substring(14,datas[0][item].length));
								}else{ 
									$('div[data-field="'+item+'"]').text(datas[0][item]);
								}
							} else{
								var yxqz=new Date(datas[0][item]);
								$('div[data-field="'+item+'"]').text(yxqz.getFullYear() + "-" + (yxqz.getMonth() + 1)+'-'+yxqz.getDate());
							}
						}
					}
					//getCkInfo(datas[0].FZJG);
					checkJsz(datas[0].SFZMHM,datas[0].FZJG);
					getAddress(datas[0].SFZMHM,datas[0].FZJG);
				}
			}else{
				mui.toast(data.returnMessage);
			}
		}
	});
}
//获取事项材料
function getMaterial(){
	return false;
	/* var flag=false;
	var itemCode='eff80d60-b895-49f7-902d-3356d02db8f8';
	var url = 'tempInfoAction!findTempInfoAll.action?sfName=汤军武&sfId=331004199308022518&itemCode=' + itemCode;
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        error: function(request, textStatus, errorThrown) {},
        success: function(data) {
            $('#docListBox .mui-slider-item').eq(0).siblings().remove();
            if (data.returnCode == 0) {
                var datas = data.data;
                if (datas != null) {
                	flag=true;
                    var dataStr = '';
                    for (var i = 0; i < datas.length; i++) {
                        if (datas[i].code == 0) {
                        	dataStr += '<div class="mui-slider-item"><div class="bgFEEEEE f_pl10 f_lh40 f_teFF0000">'
                                +'您由材料缺失，请到线下提交办理！</div><div class="docList">'
                                +'<img src="../build/image/nodata.png" /></div></div>'; 
                        } else {
                            dataStr += '<div class="mui-slider-item"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">'
                            +'请核对您的材料，如有错误请到线下办理！</div><div class="docList">'
                            +'<img src="data:image/png;base64,'+datas[i].certFile+'" /></div></div>';
                        }
                    }
                    dataStr += '<div class="mui-slider-item"><div class="bgF1F5F8 f_pl10 f_lh40 f_te4B99D3">'
                       +'请核对您的材料，如有错误请到线下办理！</div><div class="docList">'
                       +'<img src="'+$('.img').eq(0).attr('src')+'" class="img"/></div></div>';
                    $('#docListBox').append(dataStr);
                    mui('.mui-slider').slider();
                }
            }
        }
    });
    return flag; */
}
//校验
function checkJsz(cerNo,fzjg){
	var url='tempInfoAction!checkJsz.action?sfId='+sfId+'&sfName='+sfName+'&certCode='+certCode+'&serviceId='+serviceId+'&itemCode=eb423ed4-d12a-473e-9249-7dfd0d42e20d'+'&cerNo='+cerNo+'&fzjg=' + fzjg;
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			console.log(data)
		}
	});
}
//获取地址
function getAddress(cerNo,fzjg){
	var url='tempInfoAction!checkJsz.action?sfId='+sfId+'&sfName='+sfName+'&certCode='+certCode+'&cerNo='+cerNo+'&fzjg=' + fzjg;
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			if(data.returnCode==0){
				var datas=data.data;
				if(datas[0].lxzsxxdz){
					$('textarea[data-field="lxzsxxdz"]').val(datas[0].lxzsxxdz);
				}
			}else{
				mui.toast(data.returnMessage); 
			}
		}
	});
}
var jfLink='';
//数据提交
function submitForm(){
	var datas=getFormData($('#addForm'));
	if(itemCode!='eff80d60-b895-49f7-902d-3356d02db8f8'){
		formData['ywlx']='C';
		formData['ywyy']='F';
	}
	$.ajax({
		url:'tempInfoAction!saveFormData.action',
		type : 'post',
		data:{'formInfo':JSON.stringify(formData),'postInfo':JSON.stringify(postData),'attrInfo':JSON.stringify(attrData),'formBusiCode':formBusiCode},
		error:function(request,textStatus, errorThrown){
		},
		success:function(data){
			var datas=JSON.parse(data.data).data;
			var result = JSON.parse(datas.sendResult).data;
			var retData = result.data;
			if(result.data==null||result.data==''||result.data.indexOf('http')<0){
				mui.alert(result.msg); 
				$('#failConfirm').show();
				$('#successConfirm').hide();
			}else{
				jfLink=retData;
				$('#jsBtn').removeAttr('disabled');
				$('#failConfirm').hide();
				$('#successConfirm').show();
			}
			$('.footer').hide();
			$('#step5').show();
			$('#step5').siblings().hide();
			$('#popWindow').hide();
		}
	});
}
function jfFun(){
	if(jfLink!=''){
		window.open(jfLink);
	}
}
//表单所有数据拼接
function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    $.map(unindexed_array, function (n, i) {
    	postData[n['name']] = n['value'];
    });
    return postData;
}
var imgBase64='';
// 不对图片进行压缩，直接转成base64
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
function inputChange(obj) {
	$('#popWindow').show();
	attrData[0]={};
    attrData[0]['certName']=obj.files[0].name;
   	directTurnIntoBase64(obj.files[0],function(e){
   		attrData[0]['materiaName']='驾驶证照片';
   		attrData[0]['materiaCode']='f7df696b-37ec-4647-82aa-5e13d2dfb9a6';
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
    	"file_name": '73c374fc8f3211e9a63600163e0070b624863white2',//调用之前做过的图片不花钱
    	//"file_name":code,
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
                console.log(e.target.result.split(',')[1])
                attrData[0]['certFile']=encodeURIComponent(e.target.result.split(',')[1]);
            }
        }
        else{
        	mui.toast('制作发送请求失败！'); 
        }
    }
}
//后台接口传参上传文件的input
/*function inputChange(obj) {
   	var fileData = new FormData();
   	fileData.append("file", obj.files[0]);
    $('#popWindow').show();
   	$.ajax({
	 	url: '../photoHandlerAction!queryFileName.action',
      	method:'post',
    	data: fileData,
    	processData: false, // jQuery不要去处理发送的数据
        contentType: false, // jQuery不要去设置Content-Type请求头
    	success: function (data) {
    		$('#popWindow').hide();
    		if(data.photo.code=='01'){
        		$('.img').attr('src','data:image/jpeg;base64,'+data.photo.msg);
    		}else{
    			mui.alert(data.photo.msg);
    		}
    	},
    	error: function () {
    		$('#popWindow').hide();
    		mui.alert('请上传正确人像图片，或其他错误请求失败！'); 
    	}
    }) 
    //这段如果有类似的事项，要再单独调callback方法
    attrData[0]={};
    attrData[0]['certName']=obj.files[0].name;
   	directTurnIntoBase64(obj.files[0],function(e){
   		attrData[0]['certFile']=encodeURIComponent(e);
   		attrData[0]['materiaName']='驾驶证照片';
   		attrData[0]['materiaCode']='f7df696b-37ec-4647-82aa-5e13d2dfb9a6';
   	})
}*/
//单选框选择判断
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
//手机号码验证
function checkPhone(phone,obj){ 
    if(!(/^1[3456789]\d{9}$/.test(phone))){ 
        mui.toast('手机号码有误，请重填！'); 
        $(obj).val('');
    } 
}
//地图查询方法，还需删减无用代码
(function() {
    // 百度地图API功能
    var map = new BMap.Map("l-map");
    var myValue;
    var to = null;
    var geolocation = new BMap.Geolocation();
    to = new BMap.Autocomplete({
        "input": "newAddress",
        "location": map,
        "onSearchComplete": function (data) {
            to.hide();
        }
    });
    $("#newAddress").click(function(){
    	 to.show();
    });
    to.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
        getPostcode(_value.province, _value.city, myValue, 2);
    });
    function getPostcode(_province, city, areaname, type) {
        var url = 'https://api.k780.com/?app=life.postcode&areaname=' + _province + city + areaname + '&appkey=43059&sign=50c6d74aaefca5f6a37f8c3e38f6f528&format=json&jsoncallback=data'
        $.ajax({
            type: 'get',
            async: false,
            url: url,
            dataType: 'jsonp',
            jsonp: 'callback',
            jsonpCallback: 'data',
            success: function(data) {
                if (data.success != '1') {
                    alert(data.msgid + ' ' + data.msg);
                    exit;
                }
                //遍历
                var result = data.result.lists[0];
                postCode = result.postcode;
                province = result.areanm.split(',')[1];
                var tvalue = "";
                if (_province == "" && city != province) {
                    tvalue = province + areaname;
                }else if(city == province){
                    tvalue = areaname;
                }
                $("#newAddress").val(tvalue);
            },
            error: function() {
                alert('fail');
            }
        });
    }
})()