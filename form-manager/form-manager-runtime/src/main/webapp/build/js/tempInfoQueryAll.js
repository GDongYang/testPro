//读取身份证信息
function readCard(){
	if($("#itemCode").val() == null || $("#itemCode").val() == "") {
		Modal.alert({ msg:"请选择事项！", title:'提示', btnok:'确定' });
		return false;
	}
	$("#readCardBtn").attr("disabled","disabled");
	$("#itemCode").attr("disabled","disabled");
	$("#sfId").val("");
	$("#sfName").val("");
	try {
		var instanceId = "CVR_"+new Date().getTime();
		var instanceHtml = "<OBJECT classid=\"clsid:10946843-7507-44FE-ACE8-2B3483D179B7\"";
		instanceHtml += "id=\""+instanceId+"\" name=\""+instanceId+"\" width=\"0\" height=\"0\" >";
		instanceHtml += "</OBJECT>";
		$("#readCardDiv").html(instanceHtml);
		var CVR_IDCard = document.getElementById(instanceId);
		var strReadResult = CVR_IDCard.ReadCard();
		if(strReadResult == "0"){
			$("#sfId").val(CVR_IDCard.CardNo);
			$("#sfName").val(CVR_IDCard.Name);
			search();
		}else{
			throw new Error(strReadResult);
		}
	} catch (e) {
		readCardByIDR();
	}
	var clId = setTimeout(function(){
		$("#readCardBtn").removeAttr("disabled");
		$("#itemCode").removeAttr("disabled");
		clearTimeout(clId);
	}, 1000);
}
//精伦读卡器
function readCardByIDR(){
	$("#readCardDiv").html('<object classid="clsid:5EB842AE-5C49-4FD8-8CE9-77D4AF9FD4FF" id="IdrControl1" width="100" height="100"></object>');
	try {
		var idr = document.getElementById("IdrControl1");
		var result=idr.ReadCard("2","");
		if (result==1){
			$("#sfId").val(idr.GetCode());
			$("#sfName").val(idr.GetName());
			search();
		} else {
			throw new Error(strReadResult);
		}
		return result;
	} catch(e) {
		readCardByDK();
	}
}

//德卡读卡器
function readCardByDK(){
	$("#readCardDiv").html('<OBJECT id="IdrControl2" codeBase="comRD800.cab" WIDTH="0" HEIGHT="0" classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>');
	try {  
		var idr = document.getElementById("IdrControl2");
		var st = '';
		st = idr.dc_init(100, 115200);
		if(st <= 0) {
            throw new Error("读卡器初始化失败");
		}
		st = idr.DC_start_i_d();
		if (st < 0) {
            throw new Error("读取身份证信息失败");
		}
		$("#sfId").val(idr.DC_i_d_query_id_number());
		$("#sfName").val(idr.DC_i_d_query_name());
		idr.DC_end_i_d();
		idr.dc_exit();
		search();
		return ;
	} catch(e) {
	    try {
            idr.DC_end_i_d();
            idr.dc_exit();
		}catch (e) {
        }
        readSbCardByDk();
	}
}

//德卡读社保
function readSbCardByDk(){
    $("#readCardDiv").html('<OBJECT id="IdrControl3" WIDTH="0" HEIGHT="0" classid="clsid:BF140FAF-D4D5-461B-8E7C-C88DC3F7399C"></OBJECT>');
    try {
        var idr3 = document.getElementById("IdrControl3");
        var info = idr3.getData("4|3|");
        var card = info.split("|");
		if(card[0] == 0) {
            $("#sfId").val(card[2]);
            $("#sfName").val(card[5]);
            search();
		} else {
            throw new Error("读卡错误！");
		}
    } catch(e) {
        readSbCardByMt();
    }
}

//明泰读社保
function readSbCardByMt(){
    $("#readCardDiv").html('<OBJECT id="IdrControl4" WIDTH="0" HEIGHT="0" classid="clsid:3DF9EF3F-BDBA-49BD-A3FC-C75968C35EBE" codebase="HZHKCARD.cab#version=1,0,0,1"></OBJECT>');
    try {
        var idr4 = document.getElementById("IdrControl4");
        var n = idr4.iReadCard(1);
        if(n==0) {
            $("#sfId").val(idr4.pOutIDNum);
            $("#sfName").val(idr4.pOutName);
            search();
        } else {
            throw new Error("读卡错误！");
        }
    } catch(e) {
        readCardByMt();
    }
}

//明泰读身份证
function readCardByMt(){
    $("#readCardDiv").html('<OBJECT id="IdrControl4" WIDTH="0" HEIGHT="0" classid="clsid:3DF9EF3F-BDBA-49BD-A3FC-C75968C35EBE" codebase="HZHKCARD.cab#version=1,0,0,1"></OBJECT>');
    try {
        var idr4 = document.getElementById("IdrControl4");
        var n = idr4.iReadCard(3);
        if(n==0) {
            $("#sfId").val(idr4.pOutIDNum);
            $("#sfName").val(idr4.pOutName);
            search();
        } else {
            throw new Error("读卡错误！");
        }
    } catch(e) {
        Modal.alert({ msg:"请尝试将身份证移开读卡区然后重新放入读卡去！", title:'提示', btnok:'确定' });
    }
}