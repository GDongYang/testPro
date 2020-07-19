var selectvalue = -1;
$(document).on('click', '.cin', function(event) {
    $(".cin").each(function(){
        $(this).removeClass("cinh");
    });
    $(this).addClass("cinh");
   selectvalue = $(this).attr("data-cert");
});
//回调
var handlerCount = 0;

$(document).ready(function(){
    $(".closeicon").click(function() {
        $("#owidow").hide();
    });
    $(".closeicon").click(function(){
    	 $("#sign_up").removeAttr('disabled');
    });
    
    $("#sign_up").click(function(){
    	var info = userTable.bootstrapTable("getSelections");
		if(info != 0) {
	        $(".keyBtn,.certBtn").attr({'disabled':'disabled'});
	        //禁止点击两次
	        //$("#sign_up").attr({'disabled':'disabled'});
	        //禁止点击两次
	        try {
	           if (_certInfoArray.length > 0) {
	           	 $("#sign_up").attr({'disabled':'disabled'});
	           	 $("#owidow").show();
	           }else{
	           	 Modal.alert({ msg:"请检查是否插入USB KEY!", title:'提示', btnok:'确定' });
	           }
			} catch (e) {
				// TODO: handle exception
				if(!isIE()){
					 Modal.alert({ msg:"请使用IE浏览器!", title:'提示', btnok:'确定' });
				}
			}
	        handlerCount = 1;
		}else{
			Modal.alert({ msg:'请选择您需要绑定的用户！', title:'提示', btnok:'确定' });
		}
    });
    $("#btn_no").click(function() {
        $(".keyBtn,.certBtn").removeAttr('disabled');
        $("#owidow").hide();
        $("#sign_up").removeAttr('disabled');
    });
});

function funAppend(my_cert, callback_cert) {
    // 删除老的选项
    var obs = $(".obs");
    $(".obs > div").remove();

    if (my_cert.length > 0) {
        for (var n = 0; n < my_cert.length; n++) {
            obs.append('<div class="cin" data-cert="' + n + '"><div class="clogo"></div><div class="cinfo"><p class="cinfot fs16">' + my_cert[n].getSubjectCN() + '</p><p class="cinfot">颁布者：' + my_cert[n].getIssuerCN() + '</p><p class="cinfot">有效期：' + my_cert[n].getFrom() + ' 至 ' + my_cert[n].getUntil() + '</p><p class="cinfot"></p><p class="cinfot"><a href="#">单击此处查看证书属性</a></p></div></div>');
        }
    }else {
        obs.append('<div id="selectcert" value="-1">未发现合法证书</div>');
    }

    $(document).on('click', '#btn_sure', function(){
    	if(selectvalue == -1){
    		Modal.alert({ msg:"请选择证书登录!", title:'提示', btnok:'确定' });
    		return;
    	}else{
    		 $(".keyBtn,.certBtn").removeAttr('disabled');
    	     $("#owidow").hide();
    	     callback_cert(selectvalue); 
    	}
    });
}