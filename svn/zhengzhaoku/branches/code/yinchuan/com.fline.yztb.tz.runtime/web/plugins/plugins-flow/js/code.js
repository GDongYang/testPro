

$(function() {
   
	// ie 9 以下CSS3	 start   
    if (window.PIE) {
        $('.rounded').each(function() {PIE.attach(this);});
		$('.navTip a').each(function() {PIE.attach(this);});
		$('.BoxTyin').each(function() {PIE.attach(this);});
		$('.rounded50').each(function() {PIE.attach(this);});
		$('.selected a').each(function() {PIE.attach(this);});
		$("a").each(function() {PIE.attach(this);});
		$('img').each(function() {PIE.attach(this);});
		$('.pagination').each(function() {PIE.attach(this);});
		$('.swiper-pagination-switch').each(function() {PIE.attach(this);});
		$('h3').each(function() {PIE.attach(this);});
		$('.tipCtn').each(function() {PIE.attach(this);});
    }
	// ie 9 以下CSS3 end	
	
	
	

	// 返回顶部
	function getBrowserDim(){
	   var windowobj = $(window);
       var browserwidth = windowobj.width()/2;
       var scrollLeft = windowobj.scrollLeft();
	   var b=$(".w1000").width()/2;
	   var Left = scrollLeft+browserwidth+b;
	   return Left;
	}

	$(".backToTop").css("left",getBrowserDim());
	$(".smBtn").css("left",getBrowserDim())
	$(".backToTop").hover(function(){$(this).css("background-position","-45px 0px")},function(){$(this).css("background-position","0px 0px")});
	
	//判断 ie 版 start
	if($.browser.msie){ 
	  var ie =$.browser.version;
	  if(ie<9){
		  $(".backToTop").css("left",getBrowserDim()+2)
		  }
	}
	//判断 ie 版 end
	/*
	$(".levl1li").each(function(){
	  $(this).hover(function(){$(this).addClass("hover")},function(){$(this).removeClass("hover")})							
	
	});*/
	
	
});
