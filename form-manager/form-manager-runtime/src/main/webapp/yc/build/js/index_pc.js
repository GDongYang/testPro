$(function() {
    // 六个固定事项
    eventInfo();
    // 部门服务
    departmentInfo();
    //deptToItem('部门服务', );
    deptToItem(1, '<li tabid="1" class="choice hqbm">部门服务</li>', '', '');
})

function eventInfo() {
    $.ajax({
        url: "approveItemAction!findToWeb.action",
        type: "GET",
        data: {
            areaCode: '330683',
            isMain: 1
        },
        dataType: "json",
        success: function(data) {
            $(".sx_content").empty();
            $.each(data.data, function(i, item) {
                var html = '';
                html += '<li class="clearfix">'
                if (item.departmentName.indexOf("公安局") > -1) {
                    html += '<img src="../build/image/gongan.png" alt="" class="left">'
                }
                html += '<span class="left"><p>' + item.name + '</p><p>' + item.detail + '</p></span>'
                html += '</li>'
                $(".sx_content").append(html);
            })
        }
    })
}

function departmentInfo() {
    $.ajax({
        url: "departmentAction!findToWeb.action",
        type: "GET",
        data: {
            areaCode: '330683'
        },
        dataType: "json",
        success: function(data) {
            // $("#pane_dept").empty();
            $.each(data.data, function(i, item) {
                var html = '';
                html += '<div class="dept_branch hqnr" onclick="deptToItem(1,this,\'' + item.id + '\')">' + item.name + '</div>'
                $("#pane_dept").append(html);
            })
        }
    })
}
// var pageNum = 1;
// var total = 1;
var total = 1;

// function getTotal() {
function deptToItem(pageNum, obj, deptId, val, counterpart) {
    $.ajax({
        url: "approveItemAction!findToWebByPage.action",
        type: "GET",
        data: {
            areaCode: '330683',
            departmentId: deptId,
            pageNum: pageNum,
            pageSize: 10,
            counterpart: counterpart
        },
        dataType: "json",
        success: function(data) {
            console.log(data);
            $('.grzt_name').text($(obj).text());
            var strNum = '(共' + data.page.count + '个事项)';
            $('.grzt_num').text(strNum);
            $(".zt_item").empty();
            $.each(data.page.items, function(i, item) {
		        	var cptype = '法人';
		        	if (item.counterpart.indexOf("1") > -1) {
		        		cptype = '个人';
		        	}
                    var str = '';
                    str += '<div class="item">'
                    str += '<span>' + item.name + '</span>'
                    str += '<div class="ycbf"><span onclick="openBszn(\'' + item.code + '\')"><img src="../build/image/zxbl.png" alt="">办事指南</span>'
                        // str += `<span onclick="window.location.href='${item.webUrl}'"><img src="../build/image/zxbl.png" alt="">在线办理</span>`
                    str += '<span onclick="doonline(\''+item.code+'\',\''+item.grFlag+'\',\''+item.webUrl+'\',\''+item.webUrl+'\',\''+cptype+'\',\''+item.areaCode+'\',\''+item.deptCode+'\');"><img src="../build/image/zxbl.png" alt="">在线办理</span>'
                    str += '</div>'
                    if (counterpart == 1 || item.counterpart == 1) {
                        str += '<img src="../build/image/geren.png" alt="">'
                    } else if (counterpart == 2 || item.counterpart == 2) {
                        str += '<img src="../build/image/faren.png" alt="">'
                    } else {
                        str += '<img src="../build/image/gefaren.png" alt="">'
                    }
                    str += '</div>'
                    $(".zt_item").append(str);
                    // console.log(str);
                })
                // 调用分页插件
                // console.log(pageNum)
            $("#myPage").sPage({
                page: pageNum, //当前页码
                pageSize: 10, //每页显示多少条数据，默认10条
                total: data.page.count, //数据总条数,后台返回
                backFun: function(page) {
                    pageNum = page;
                    console.log(pageNum);
                    //点击分页按钮回调函数，返回当前页码
                    deptToItem(page, obj, deptId, val);
                }
            });


        }
    })
}

function openBszn(code) {
    window.open('./enchiridion.html?code=' + code)
}

function openZxbl(code) {
    window.open('./zjs_sz_dept_1.html')
        // http: //220.191.227.107:8081/yztb/pc/pcIndex.html?sid={事
}

function searchSysx(pageNum) {
    var serchValue = $("#searchSxname").val();
    // console.log(serchValue);
    $.ajax({
        url: "approveItemAction!findToWebByPage.action",
        type: "GET",
        data: {
            areaCode: '330683',
            pageNum: pageNum,
            pageSize: 10,
            nameLike: serchValue
        },
        dataType: "json",
        success: function(data) {
            // console.log(data.page.count);
            var strNum = '(共' + data.page.count + '个事项)';
            $('.grzt_num').text(strNum);
            // console.log(data.page.count);
            $(".zt_item").empty();
            $.each(data.page.items, function(i, item) {
            	var cptype = '法人';
            	if (item.counterpart.indexOf("1") > -1) {
            		cptype = '个人';
            	}
                var str = '';
                str += '<div class="item">'
                str += '<span>' + item.name + '</span>'
                str += '<div class="ycbf"><span><img src="../build/image/zxbl.png" alt="">办事指南</span>'
                    // str += `<span onclick="window.location.href='${item.webUrl}'"><img src="../build/image/zxbl.png" alt="">在线办理</span>`
                str += '<span onclick="doonline(\''+item.code+'\',\''+item.grFlag+'\',\''+item.webUrl+'\',\''+item.webUrl+'\',\''+cptype+'\',\''+item.areaCode+'\',\''+item.deptCode+'\');"><img src="../build/image/zxbl.png" alt="">在线办理</span>'
                str += '</div>'
                $(".zt_item").append(str);
            })
            $("#myPage").sPage({
                page: pageNum, //当前页码
                pageSize: 10, //每页显示多少条数据，默认10条
                total: data.page.count, //数据总条数,后台返回
                backFun: function(page) {
                    pageNum = page;
                    //点击分页按钮回调函数，返回当前页码
                    searchSysx();
                }
            });

        }
    })
}

// $('#searchSxname').bind('input propertychange', function() {
//     searchSysx();
// })
var cpLock = false;
$('#searchSxname').on('compositionstart', function() {
    // 输入汉语拼音时锁住搜索框，不进行搜索，或者从汉语拼音转到字母时也可触发
    cpLock = true;
    // console.log('不搜索')
});
$('#searchSxname').on('compositionend', function() {
    // 结束汉语拼音输入并生成汉字时，解锁搜索框，进行搜索
    cpLock = false;
    // console.log('汉字搜索');
    // 接下去放ajax请求生成下拉框内容
    searchSysx()
});
$('#searchSxname').on('input', function() {
    if (!cpLock) {
        // console.log('字母搜索')    
        // 接下去放ajax请求生成下拉框内容
        searchSysx();
    }
});



var _type2='';// 下面jsonp方法需要使用到所以加个全局变量
var _type='';
var _url1='';
var _url2='';
var _sysapp='';
var _itemcode='';
var _qhcode= '';
var _qljbcode= '';
var _bmcode='';
var _rand="";
//在线办理
function doonline(itemcode,sysapp,url1,url2,type,qhcode,bmcode){
	var qljbcode = '';
	var type2 = 1;
	if(type.indexOf("个人")>-1 || type.indexOf("允许个人代办")>-1){
		type2 = 1;
	}else if(type.indexOf("法人")>-1 || type.indexOf("其他组织")>-1){
		type2 = 2;
	}else{
		type2 = 2;
	}
	_type2=type2;
	_type=encodeURIComponent(type);
	_url1=encodeURIComponent(url1);
	_url2=encodeURIComponent(url2);
	_sysapp=sysapp;
	_itemcode=itemcode;
	_qhcode=qhcode;
	_qljbcode=encodeURIComponent(encodeURIComponent(qljbcode));
	_bmcode=bmcode;
	_rand=Math.random();
	var script=document.createElement('script');
	script.type='text/javascript';
	script.src="http://www.zjzwfw.gov.cn/zjservice/item/detail/doitem.do?callback=jsonpdoonline&type="+_type+"&itemcode="+itemcode+"&sysapp="+sysapp+"&url1="+_url1+"&url2="+_url2+"&dotype=2&type2="+type2+"&qhcode="+_qhcode+"&qljbcode="+_qljbcode+"&bmcode="+_bmcode+"&rand="+_rand;
	document.getElementsByTagName('head')[0].appendChild(script);
}

function jsonpdoonline(text){
	text=text.text;
	if(text){
		var data=text.split(";");
		if(data[0]=="true"){
			if(data[1]){
				var us=data[1].split('&goto=');
				if(us.length>1){
					data[1]=us[0]+"&goto="+encodeURIComponent(us[1]);
				}
				if(data[1].indexOf("http") < 0){
					data[1]="http://"+data[1];
				}
				if(data[1].indexOf("ticket") >= 0){ //有票据用post
					window.open(data[1]);
				}else{
					if(_type2 == 1 ){//个人办事页面
						if(_sysapp == ''){
							window.open(data[1]);
						}else{
							zyj('http://www.zjzwfw.gov.cn/zjservice/item/detail/showlogin.do?url='+data[1]);
						}
					}else if(_type2 == 2){//法人办事
						if( _url1 == ''){
							if(_sysapp != ''){//由于详情页静态页面type2参数固定写死为2所以添加判断-- OG
								zyj('http://www.zjzwfw.gov.cn/zjservice/item/detail/showlogin.do?url='+data[1]);
							}else{
								window.open(data[1]);
							}
						}else{
							zyj('http://www.zjzwfw.gov.cn/zjservice/item/detail/showlogin.do?url='+data[1]);
						}
					}else if(_type2 == 3){
						_type = decodeURIComponent(_type);
						if(_type.indexOf("个人")>=0&&_type.indexOf("法人")<0&&_type.indexOf("其他组织")<0){
							_type="1";
						}else if(_type.indexOf("个人")<0){
							_type="2";
						}else {
							_type="3";
						}
						if(_type == '1'){
							if( _sysapp == ''){
								window.open(data[1]);
							}
							else{
								zyj('http://www.zjzwfw.gov.cn/zjservice/item/detail/showlogin.do?url='+data[1]);
							}
						}else if(_type == '2'){
							if( _url1 == ''){
								window.open(data[1]);
							}else{
								zyj('http://www.zjzwfw.gov.cn/zjservice/item/detail/showlogin.do?url='+data[1]);
							}
						}else if(_type == '3'){
							zyj("http://www.zjzwfw.gov.cn/zjservice/item/detail/reserves.do?type="+_type+"&itemcode="+_itemcode+"&sysapp="+_sysapp+"&url1="+_url1+"&url2="+_url2+"&webid="+webid+"&dotype=2&type2="+_type2+"&rand="+_rand);
						}
					}
				}
			}
		}else{
			data[1] = decodeURIComponent(data[1]);
			if(data[1] == "行政审批"){
				zyj("http://www.zjzwfw.gov.cn/zjservice/item/detail/reserve.do?type="+_type+"&itemcode="+_itemcode+"&sysapp="+_sysapp+"&url1="+_url1+"&url2="+_url2+"&webid="+webid+"&dotype=2&type2="+_type2+"&rand="+_rand);
			}
			if(data[1] == "只有法人可以办理"){
				alert(data[1]);
			}
			if(data[1] == "只有个人可以办理"){
				alert(data[1]);
			}
			if(data[1] == "行政审批登录个人法人其他组织"){
				zyj("http://www.zjzwfw.gov.cn/zjservice/item/detail/reserves.do?type="+_type+"&itemcode="+_itemcode+"&sysapp="+_sysapp+"&url1="+_url1+"&url2="+_url2+"&webid="+webid+"&dotype=2&type2="+_type2+"&rand="+_rand);
			}
		}
	}
}

function zyj(url) {
    $("#dialogiframe").attr("src", url);
    $("#dialogmain").show();
    $("#dialogmain").height(document.body.offsetHeight);
    $("#dialogmain").width(document.body.offsetWidth);
}

function zyj2(){
    $("#dialogmain").hide();	
}