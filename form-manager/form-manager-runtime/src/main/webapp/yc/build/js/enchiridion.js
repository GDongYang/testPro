$(function() {
    serviceInfo();
})

function serviceInfo() {
    var code = window.location.href.split("=");
    code = code[1];
    $.ajax({
        url: "approveItemAction!findItemDetail.action",
        data: {
            innerCode: code
        },
        dataType: "json",
        success: function(data) {
            var data = JSON.parse(data.data);
            console.log(data);
            $(".cbdj").text(data.service.servicejson.servicename);
            $(".zn_title").text(data.service.servicejson.servicename + '服务指南');
            // $(".one span:last-child").text('适用对象:' + data.service.servicejson.applicablea);
            $('.two span').text(data.service.servicejson.outypecode);
            // $(".four span").text(data.service.servicejson.orgname);
            // $(".five span").text(data.service.servicejson.deptname);
            // $(".six span").text(data.service.servicejson.countlimit);
            // $('.seven span').text()
            // $(".eight span").text(data.service.servicejson.banrequirement);
            $(".ten span").html(data.service.servicejson.out_flow_desc);
            $("eleven span:first-child").text('承诺期限' + data.service.servicejson.promiseday);
            $("eleven span:first-child").text('法定时限' + data.service.servicejson.promiseday);
        }
    })
}