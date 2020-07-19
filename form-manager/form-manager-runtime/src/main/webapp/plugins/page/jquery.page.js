
var ziArray=[];
var ziArray1=[];
var stateObject = {};
var title = "";
var zz=window.location.search;
var lenght;


/*使用方法
 *  $('.pageTest').page({
      leng: 14,//分页总数
      activeClass: 'activP' , //active 类样式定义
      maxShowPage:5, // 最多显示的页数
      clickBack:function(page){
        console.log(page);// 当前页数
      }
    });
    
 * */
(function ($) {
  //默认参数 (放在插件外面，避免每次调用插件都调用一次，节省内存)
  var defaults = {
    //id : '#paging',//id
    leng: 1,//总页数
    activeClass: 'page-active' ,//active类
    firstPage: '首页',//
    lastPage: '末页',
    maxShowPage:5,  // 最多显示的页数
    prv: '«',
    next: '»',
    clickBack:function(){
    }
  };
  var opts,myOptions;
  //扩展
  $.fn.extend({
    //插件名称
    page: function (options) {
      //覆盖默认参数
      myOptions = options
      opts = $.extend(defaults, options);
      
      //主函数
      return this.each(function () {
        //激活事件
        var obj = $(this);
        var str1 = '';
        var str = '';
        var l = opts.leng;
        lenght=opts.leng;
        var showCenter = Math.ceil(opts.maxShowPage / 2);

        if (l > 1 && l < opts.maxShowPage+1) {
          str1 = '<li><a href="javascript:" class="'+ opts.activeClass +'">1</a></li>';
          for (i = 2; i < l + 1; i++) {
            str += '<li><a href="javascript:">' + i + '</a></li>';
          }
        }else if(l > opts.maxShowPage){
		
          str1 = '<li><a href="javascript:" class="'+ opts.activeClass +'">1</a></li>';
          for (i = 2; i < opts.maxShowPage + 1 ; i++) {
            str += '<li><a href="javascript:">' + i + '</a></li>';
          }
          //str += '<li><a href="javascript:">...</a></li>'
        } else {
          str1 = '<li><a href="javascript:" class="'+ opts.activeClass +'">1</a></li>';
        }
        obj.html('<div class="next" style="float:right">' + opts.next + '</div><div class="last" style="float:right">' + opts.lastPage + '</div><ul class="pagingUl">' + str1 + str + '</ul><div class="first" style="float:right">' + opts.firstPage + '</div><div class="prv" style="float:right">' + opts.prv + '</div>');

        obj.on('click', '.next', function () {
          var pageshow = parseInt($('.' + opts.activeClass).html());
          if(pageshow==l){
            return false;
          }
          if(pageshow == l) {
          }else if(pageshow > l-showCenter&& pageshow < l){
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().next().find('a').addClass(opts.activeClass);
          }else if(pageshow > 0 && pageshow < showCenter+1){
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().next().find('a').addClass(opts.activeClass);
          }else {
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().next().find('a').addClass(opts.activeClass);
            fpageShow();
          }
          opts.clickBack(pageshow+1)
          //alert(pageshow+1);
        });
        obj.on('click', '.prv', function () {
          var pageshow = parseInt($('.' + opts.activeClass).html());
          if (pageshow == 1) {
            return false;
          }else if(pageshow > l-showCenter && pageshow < l+1){
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().prev().find('a').addClass(opts.activeClass);
                  //this.fpageBranch(pageshow-1);
          }else if(pageshow > 1 && pageshow < showCenter+1){
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().prev().find('a').addClass(opts.activeClass);
                  //this.fpageBranch(pageshow-1);
          }else {
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().prev().find('a').addClass(opts.activeClass);
                    //this.fpageBranch(pageshow-1);
            fpageShow();
          }
          opts.clickBack(pageshow-1)
          //alert(pageshow-1);
        });

        obj.on('click', '.first', function(){
          var pageshow = 1;
          var nowshow = parseInt($('.' + opts.activeClass).html());
          if(nowshow==1){
            return false;
          }
          $('.' + opts.activeClass).removeClass(opts.activeClass).parent().prev().find('a').addClass(opts.activeClass);
          fpagePrv(0);
          opts.clickBack(pageshow)
          //alert(pageshow);
        })
        obj.on('click', '.last', function(){
          var pageshow = l;
          var nowshow = parseInt($('.' + opts.activeClass).html());
          if(nowshow==l){
            return false;
          }
          if(l> opts.maxShowPage ){
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().prev().find('a').addClass(opts.activeClass);
            fpageNext(opts.maxShowPage-1);
          }else{
            $('.' + opts.activeClass).removeClass(opts.activeClass).parent().prev().find('a').addClass(opts.activeClass);
            fpageNext(l-1);
          }
          opts.clickBack(pageshow)
          //alert(pageshow);
        })

        obj.on('click', 'li', function(){
          var $this = $(this);
          var pageshow = parseInt($this.find('a').html());
          var nowshow = parseInt($('.' + opts.activeClass).html());
          if(pageshow==nowshow){
            return false;
          }
          if(l > opts.maxShowPage){
            if(pageshow > l-showCenter && pageshow < l+1){
              $('.' + opts.activeClass).removeClass(opts.activeClass);
              $this.find('a').addClass(opts.activeClass);
              fpageNext(opts.maxShowPage-1-(l-pageshow));
            }else if(pageshow > 0&&pageshow < showCenter){
              $('.' + opts.activeClass).removeClass(opts.activeClass);
              $this.find('a').addClass(opts.activeClass);
              fpagePrv(pageshow-1);
            }else{
              $('.' + opts.activeClass).removeClass(opts.activeClass);
              $this.find('a').addClass(opts.activeClass);
              fpageShow();
            }
          }else{
            $('.' + opts.activeClass).removeClass(opts.activeClass);
            $this.find('a').addClass(opts.activeClass);
          }
          opts.clickBack(pageshow)
        })
     
        function fpageShow(){
          var pageshow = parseInt($('.' + opts.activeClass).html());

          var pageStart = pageshow - showCenter + 1;
          if(pageStart < 1){
            pageStart = 1
          }
          var pageEnd = pageshow + showCenter;
          var str1 = '';
	  
          for(i=0;i< opts.maxShowPage ;i++){
            str1 += '<li><a href="javascript:" class="">' + (pageStart+i) + '</a></li>'
          }
          obj.find('ul').html(str1);
          obj.find('ul li').eq(showCenter-1).find('a').addClass(opts.activeClass);
          
        }

        function fpagePrv(prv){
          var str1 = '';
          if(l > opts.maxShowPage-1){
            for(i=0;i<opts.maxShowPage;i++){
              str1 += '<li><a href="javascript:" class="">' + (i+1) + '</a></li>'
            }
          }else{
            for(i=0;i<l;i++){
              str1 += '<li><a href="javascript:" class="">' + (i+1) + '</a></li>'
            }
          }
          obj.find('ul').html(str1);
          obj.find('ul li').eq(prv).find('a').addClass(opts.activeClass);
        }

        function fpageNext(next){

          var str1 = '';
          if(l>opts.maxShowPage-1){
            for(i= l - (opts.maxShowPage-1);i < l+1;i++){
              str1 += '<li><a href="javascript:" class="">' + i + '</a></li>'
            }
           obj.find('ul').html(str1);
           obj.find('ul li').eq(next).find('a').addClass(opts.activeClass);
          }else{
            for(i=0;i<l;i++){
              str1 += '<li><a href="javascript:" class="">' + (i+1) + '</a></li>'
            }
           obj.find('ul').html(str1);
           obj.find('ul li').eq(next).find('a').addClass(opts.activeClass);
          }
        }
        
       
        
      });
    },
    setLength: function(newLength){
      myOptions.leng = newLength
      $(this).html('')
      $(this).unbind()
      $(this).page(myOptions)
    }
    
  })
})(jQuery);

