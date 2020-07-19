var queryParam = null;
var pageSize = 12; pageNum = 1, pages = 1,newPages='';
$('#paginationUl').page({
	leng: pages,//分页总数
	activeClass: 'activP' , //active 类样式定义
	maxShowPage:5, // 最多显示的页数
	clickBack:function(page){
		return clickBack(page);
	}
});
//区间时间插件
$("input[name='queryDate']").daterangepicker(
        {
           // autoApply: true,
       autoUpdateInput: true,
           // alwaysShowCalendars: true,
       timePicker: true, //显示时间
       timePicker24Hour : true,//设置小时为24小时制 默认false
       showDropdowns: true,
       autoUpdateInput: false,//1.当设置为false的时候,不给与默认值(当前时间)2.选择时间时,失去鼠标焦点,不会给与默认值 默认true
       timePickerSeconds: false, //时间显示到秒
       startDate: moment(), //设置开始日期
       endDate: moment(), //设置结束器日期
//     maxDate: moment(new Date()), //设置最大日期
       "opens": "left",
       showWeekNumbers: true,
       locale: {
           format: "YYYY/MM/DD HH:MM:00",
           separator: " - ",
           applyLabel: "确认",
           cancelLabel: "清空",
           fromLabel: "开始时间",
           toLabel: "结束时间",
           customRangeLabel: "自定义",
           daysOfWeek: ["日","一","二","三","四","五","六"],
           monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
       }
    }
    ).on('cancel.daterangepicker', function(ev, picker) {
        $("#queryDate").val("请选择日期范围");
        $("#startDate").val("");
        $("#endDate").val("");
    }).on('apply.daterangepicker', function(ev, picker) {
        $("#startDate").val(picker.startDate.format('YYYY-MM-DD HH:MM:00'));
        $("#endDate").val(picker.endDate.format('YYYY-MM-DD HH:MM:00'));
        $("#queryDate").val(picker.startDate.format('YYYY-MM-DD HH:MM:00')
                +" 至 "+picker.endDate.format('YYYY-MM-DD HH:MM:00'));
});
$('#pageSize li').click(function(){
	pageSize = $(this).find("a").text();
	$('.page-size').text(pageSize);
	ajaxRequest();
})
function loadCert() {
	$.ajax({
		url : "certTempAction!findAll.action",
		type : "POST",
		data : "",
		dataType : "json",
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			//初始化表单
			var info = data.result;
			var htmlstr = "<option value=''>请选择证件</option>";
			for (var i = 0; i < info.length; i++) {
				htmlstr += "<option class='optionText' value=\""+info[i].code+"\" title=\""+info[i].name+"\">" + info[i].name + "</option>";
			}
			$("#cert").append(htmlstr);
		}
	});
}
function ajaxRequest() {
	var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
	+ '&pageSize=' + pageSize;
	var param = getParams();
	//保存查询参数，用于导出表格
	if(queryParam==null){
		queryParam = {};
	}
	$.extend(queryParam,param);
	$.ajax({
		type : 'post',
		url : cardUrl,
		dataType : 'json',
		cache : false,
		async : true,
		data : dataStr,
		traditional: true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			$('#addCard').siblings().remove();
			var datas=data.rows;
			var count=data.total;
			if(count == 0){
				$('.fixed-table-pagination').addClass("displayNones");
			}else{
				$('.fixed-table-pagination').removeClass("displayNones");
			}
			newPages =  Math.ceil(count / pageSize);
			var content = $("#paginationUl").html();
			if(content.length != 0 && pages != newPages){
				$('#paginationUl').setLength(newPages);
				pages = newPages;
			}else{
				pages = newPages;
			}
			$("#totalPages").text(newPages);
			$("#total").text(count);
			if(datas!=null){	
				for(var i=0;i<datas.length;i++){
					$('#addCard').addClass("base_hidden");
					$('#addCard').siblings().removeClass("base_hidden");
					$('#addCard').after(getCardContent(datas[i]));
				};
			}else{
				$('#addCard').siblings().addClass("base_hidden");
				$('#addCard').removeClass("base_hidden");
			}			
		}
	});
}
function getParams(){
	var certCode = $("#cert").val();
	var itemCode = $("#item").val();
	var department = $("#departmentId").val();
	var queryDate = $("#queryDate").val();
	var params={
		'certcode':certCode,
		'itemcode':itemCode,
		'department':department,
		'queryDate':queryDate
	} 
	return params;
}
function clickBack(page){
	pageNum = page;
	$("#pageNum").text(pageNum);
	ajaxRequest();
}
//查询
function search() {
	pageNum=1;
	ajaxRequest();
}
//清除
function clean() {
	pageNum = 1;
	$('#searchForm .form-control').each(function(){
		$(this).val("");
	});
	ajaxRequest();
}