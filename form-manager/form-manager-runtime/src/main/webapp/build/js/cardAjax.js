var pageSize = 11; pageNum = 1, pages = 1,newPages='';
$('#paginationUl').page({
	leng: pages,//分页总数
	activeClass: 'activP' , //active 类样式定义
	maxShowPage:5, // 最多显示的页数
	clickBack:function(page){
		return clickBack(page);
	}
});
$('#pageSize li').click(function(){
	pageSize = $(this).find("a").text();
	$('.page-size').text(pageSize);
	ajaxRequest();
})
function clickBack(page){
	pageNum = page;
	$("#pageNum").text(pageNum);
	ajaxRequest();
}
//查询
function search() {
	pageNum=1;
	ajaxRequest();
	$('#paginationUl').setLength(newPages);
}
//清除
function clean() {
	pageNum = 1;
	$('#searchForm .form-control').each(function(){
		$(this).val("");
	});
	ajaxRequest();
	$('#paginationUl').setLength(newPages);
}
function ajaxRequest() {
	var dataStr = $('#searchForm').serialize() + '&pageNum=' + pageNum
	+ '&pageSize=' + pageSize;
	$.ajax({
		type : 'post',
		url : cardUrl,
		dataType : 'json',
		cache : false,
		async : true,
		data : dataStr,
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
			$("#pageNum").text(pageNum);
			if(datas!=null){
				var dataStr='';
				for(var i=0;i<datas.length;i++){
					dataStr+=getCardContent(datas[i]);
				};
				$('#addCard').after(dataStr);
			}
		}
	});
}
