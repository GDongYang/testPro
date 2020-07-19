var itemCode = '';
// 跳转到当前页面时获取busiCode
var busiCode = window.location.href.split("=");
busiCode = busiCode[1];
// 表单数据
var finalRes = {};
// 材料数据
var meteriaInfo = {};
$(function() {
        // 获取个人基本信息
        peopleInfo(busiCode);
    })
    // 点击下一步之前必须勾选复选框(不需要审批条件验证)
    // 点击下一步触发的事件
function submitFrom() {
    if ($("#step1 input[name='checkbox']").is(':checked')) {
        $('#step2').show().siblings().hide();
        $('.titleBox').show();
        $(".processContent li:eq(2)").find("img").attr("src", "../build/image/lc_icon_a2.gif");
        $(".contentFooter button:eq(0)").show();
        $(".contentFooter button:eq(1)").hide();
        $(".contentFooter button:eq(2)").show();
        peopleInfo(busiCode);
        // formShow();

    } else {
        alert("请点击我已阅读并确认授权！");
    }
}
//关闭温馨提醒
function doCloseDiv(divId) {
    $('#' + divId).css("display", "none");
}
// 返回上一步
function comeBack() {
    $('#step1').show().siblings().hide();
    $(".processContent li:eq(2)").find("img").attr("src", "../build/image/lc_icon_b2.gif");
    $(".contentFooter button:eq(2)").hide();
    $(".contentFooter button:eq(1)").show();
    $(".contentFooter button:eq(0)").hide();
}
// 回显用户基本信息
function peopleInfo(busiCode) {
    var url = 'userIfAction!findByBusiCode.action';
    $.ajax({
        url: url,
        type: "GET",
        data: {
            busiCode: busiCode
        },
        dataType: "json",
        success: function(data) {
            if (data.form) {
                $("#iframeDiv").show();
                var results = data.form;
                if (results) {
                    $(results).each(function(i, item) {
                        if (item.code) {
                            certCode = item.code;
                        }
                    });
                }
                if (certCode != '') {
                    var result = data.ybtx;
                    $("#formHide").attr('src', "http://220.191.225.200:9081/index.html?cerNo=" + result.idnum + "&appId=" + result.appId + "&validateStr=" + result.sign + "&noice=" + result.noice + "&applicationTableCode=" + certCode + "&areaCode=" + result.areaCode);
                }
            } else {
            	$("#iframeDiv").hide();
            }
            itemCode = data.item.itemCode;
            personInfo = data.userIf;
            getApprovalterm(personInfo.innerCode);
            getSqcl(personInfo.innerCode);
            if (personInfo != null) {
                for (var item in personInfo) {
                    $('[data-field="' + item + '"]').val(personInfo[item]);
                    if (item == 'cerName') {
                        $('.cerName').val('*' + personInfo[item].substring(1, (personInfo[item].length)));
                    } else if (item == 'cerNo') {
                        $('.cerNo').val(personInfo[item].substring(0, 1) + '***' + personInfo[item].substring(4, 12) + '*****' + personInfo[item].substring(17, personInfo[item].length))
                    }
                }
            }
        }
    });
};
$('.cerName').focus(function() {
    $(this).val($(this).next().val())
})
$('.cerName').blur(function() {
	var cerName = $(this).val();
    $(this).next().val(cerName);
    $(this).val('*' + cerName.substring(1, cerName.length));
})
$('.cerNo').focus(function() {
    $(this).val($(this).next().val())
})
$('.cerNo').blur(function() {
	var cerNo = $(this).val();
    $(this).next().val(cerNo);
    $(this).val(cerNo.substr(0, 1) + '***' + cerNo.substr(4, 8) + '*****' + cerNo.substring(17, cerNo.length));
})
    // 获取审批条件
function getApprovalterm(code) {
    $.ajax({
        url: "approveItemAction!findItemDetail.action",
        data: {
            innerCode: code
        },
        dataType: "json",
        success: function(data) {
            var data = JSON.parse(data.data);
            $('.spyj').html(data.service.servicejson.applyterm);
            $('.spsxmc').text(data.service.servicejson.servicename);
            if (data.service.servicejson.is_touzip == 1) {
                $("input[name=checkRadio]:eq(0)").prop("checked", 'checked');
            } else {
                $("input[name=checkRadio]:eq(1)").prop("checked", 'checked');
            }
        }
    })
}
// 获取收取材料
function getSqcl(code) {
    $.ajax({
        url: "approveItemAction!findItemMaterial.action",
        data: {
            innerCode: code
        },
        dataType: "json",
        success: function(data) {
            var data = JSON.parse(data.data);
            $('.spsxmc').val(data.materials[0].infoname);
            $(".top_hz span").text(data.materials[0].infoname);
        }
    })
}
// 判断通讯地址是否为空
function txdzCheck() {
    var txdzValue = $('input[data-field="homeaddress"]').val();
    if (txdzValue == '' || txdzValue == null) {
        $(".sjxs").css("display", "block");
    } else {
    	$(".sjxs").css("display", "none");
    }
}
//点击证照获取要显示的材料
function getMaterial() {
    $.ajax({
        url: "tempInfoAction!findTempInfoAll.action",
        data: {
            busiCode: busiCode,
            situations: itemCode
        },
        dataType: "json",
        success: function(data) {
            meteriaInfo = data.data;
            $(".layer").show();
            $(".layer_photo img").attr("src", "data:image/png;base64," + meteriaInfo[0].certFile);
        }
    })
}
// 点击图片关闭放大的图片
function closePhoto() {
    $(".layer").hide();
}

// 提交页面用户信息及材料信息给后台
function submitTabel() {
    submitInfo();
}
// 提交页面
function submitInfo() {
    getFormData();
    $.ajax({
        url: "userIfAction!saveUser.action",
        data: {
            busiCode: busiCode,
            cerName: finalRes.cerName,
            mobile: finalRes.lxrsj,
            cerNo: finalRes.IdCard,
            homephone: finalRes.lxrdh,
            homeaddress: finalRes.txdz,
            postcode: finalRes.yb,
            memo: finalRes.memo
        },
        dataType: "json",
        success: function(data) {
            submitMaterial();
        }
    })
}
// 提交材料信息
function submitMaterial() {
    $.ajax({
        url: "tempInfoAction!saveFormData.action",
        type: 'POST',
        data: {
            busiCode: busiCode,
            situations: itemCode,
            attrInfo: JSON.stringify(meteriaInfo)
        },
        dataType: "json",
        error: function(request, textStatus, errorThrown) {
            $('#step3').show().siblings().hide();
            $(".processContent li:eq(4)").find("img").attr("src", "../build/image/lc_icon_a3.gif");
            $(".contentFooter").hide();
            alert("提交材料失败");
            $("#userSbh").text('无');
            $(".btn-success").attr("disabled", false);
        },
        success: function(data) {
        	var result = JSON.parse(data.data);
            $(".btn-success").attr("disabled", false);
            $(".contentFooter").hide();
            $('#step3').show().siblings().hide();
            $(".processContent li:eq(4)").find("img").attr("src", "../build/image/lc_icon_a3.gif");
            if (result) {
                if (result.result == '01') {
                    $("#userSbh").text(result.projid);
                    $("#userSuccess").html("办件结果是：申报成功");
                } else {
                    $("#userSuccess").html("办件结果是：申报失败");
                }
            } else {
                $("#userSbh").html("无");
                $("#userSuccess").html("办件结果是：申报失败，错误信息："+data.returnMessage);
            }
        }
    })
}
// 获取获取回显数据以及用户填写数据信息(表单数据)
function getFormData() {
    finalRes = $("#addForm").serializeArray().reduce(function(result, item) {
        result[item.name] = item.value;
        return result;
    }, {})
}

// 进入申报信息页面时判断是否有对应表单。有就显示
// function formShow() {
//     $.ajax({
//         url: "/yztb-sz/userIfAction!findByBusiCode.action",
//         type: 'POST',
//         data: {
//             busiCode: busiCode,
//         },
//         dataType: "json",
//         error: function(request, textStatus, errorThrown) {},
//         success: function(data) {
//             if (data.form) {
//                 var results = data.form;
//                 if (results) {
//                     $(results).each(function(i, item) {
//                         if (item.code) {
//                             certCode = item.code;
//                         }
//                     });
//                 }
//                 if (certCode != '') {
//                     var result = data.ybtx;
//                     $("#formHide").attr('src', "http://220.191.225.200:9080/index.html?cerNo=" + result.idnum + "&appId=" + result.appId + "&validateStr=" + result.sign + "&noice=" + result.noice + "&applicationTableCode=" + certCode + "&areaCode=" + result.areaCode);
//                 }
//             }
//         }
//     })
// }