/**
 * 基于Date Range Picker的月份选择器扩展
 * github：https://github.com/RidingACodeToStray/daterangepicker-monthrangepicker
 * @param {起始时间} s 
 * @param {终止时间} e 
 * @param {最外层的div对象} classDom 
 * @param {内层的span对象} idDom 
 * @param {显示时间格式} sformat 
 * @param {是否显示日历} showCalendars 
 * @param {配置默认可选的时间范围} ranges 
 * @param {是否展示自定义范围} scrl 
 * @param {是否使用月份选择器} monthRange 
 */
var datePickers = function(s, e, classDom, idDom, sformat = 'YYYY-MM-DD', showCalendars = true, ranges, scrl = true,monthRange,callback) {
    if (!ranges) {
        ranges = {
            '今日': [moment(), moment()],
            '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            '上周': [moment().subtract(6, 'days'), moment()],
            '上个月': [moment().subtract(29, 'days'), moment()],
            '今年': [moment().startOf('month'), moment().endOf('month')],
            '去年': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }
    var start = s || moment();
    var end = e || moment();
 
    function cb(start, end) {
        classDom.html(start.format(sformat) + " - " + end.format(sformat));
        if(callback){
        	callback(start, end)
        }
    }
    idDom.daterangepicker({
            locale: {
                format: sformat,
                "applyLabel" : "确定",
	            "cancelLabel" : "取消",
	            customRangeLabel:'',
            },
            alwaysShowCalendars: showCalendars,
            showDropdowns: true,
            startDate: start,
            showCustomRangeLabel: scrl,
            endDate: end,
            opens: "left",
            ranges: ranges
        },
        cb
    );
    cb(start, end);
    if(monthRange){
        //修改日期选择器
        $('div.drp-calendar').empty().html('<div class="s-cal"><div class="s-calTitle"><span class="glyphicon glyphicon-arrow-left s-calLastYear"aria-hidden="true"></span><p>2018</p><span class="glyphicon glyphicon-arrow-right s-calNextYear"aria-hidden="true"></span></div><ul class="s-calMonth"><li data-month="01">一月</li><li data-month="02">二月</li><li data-month="03">三月</li><li data-month="04">四月</li></ul><ul class="s-calMonth"><li data-month="05">五月</li><li data-month="06">六月</li><li data-month="07">七月</li><li data-month="08">八月</li></ul><ul class="s-calMonth"><li data-month="09">九月</li><li data-month="10">十月</li><li data-month="11">十一月</li><li data-month="12">十二月</li></ul></div>');
        $('div.drp-calendar.left > .s-cal').before('<div class="s-calViewTitle">开始月份</div>');
        $('div.drp-calendar.right > .s-cal').before('<div class="s-calViewTitle">结束月份</div>');
        var timePickerDom = $('.s-timePicker'),
            sYearView = $($('.s-calTitle > p')[0]),
            eYearView = $($('.s-calTitle > p')[1]),
            monthViewLis = $('.s-calMonth > li'),
            sMonthViewLis = $($('.s-cal')[0]).find('.s-calMonth > li'),
            eMonthViewLis = $($('.s-cal')[1]).find('.s-calMonth > li'),
            tabs = $('div.daterangepicker > div.ranges > ul > li'),
            lastTab = $('div.daterangepicker > div.ranges > ul > li:last-child');
        //缓存日期
        var tempSYear,
            tempEyear,
            tempSMonth,
            tempEMonth;
        //变换日历视图修改样式
        function changeView(isAngle = false){
            var currentSYear = sYearView.text();
            var currentEYear = eYearView.text();
            eMonthViewLis.removeClass('disabled');
            if(isAngle){
                //年份不一致判断
                if(currentSYear > currentEYear){
                    eMonthViewLis.addClass('disabled');
                }
                if(currentSYear < currentEYear){
                    eMonthViewLis.removeClass('disabled');
                }
                if(currentSYear == tempSYear){
                    sMonthViewLis.eq(tempSMonth).addClass('onFocus');
                }
                if(currentEYear == tempEyear){
                    eMonthViewLis.eq(tempEMonth).addClass('onFocus');
                }
            }else{
                if(tempSMonth > tempEMonth){
                    //如果选中起始月份较大，则将日期赋值为起始月份
                    eMonthViewLis.removeClass('onFocus');
                    eMonthViewLis.eq(tempSMonth).addClass('onFocus');
                    putRangeDate();
                }
                eMonthViewLis.each(function(index){
                    if((index) == Number(tempSMonth)){
                        return false;
                    }
                    $(this).addClass('disabled');
                })
            }
        }
        //生成日期
        function putRangeDate(){
            var sYearDate = $($('.s-cal')[0]).find('.s-calTitle > p').text();
            var sMonthDate = $($('.s-cal')[0]).find('.s-calMonth > li.onFocus').data('month');
            var eYearDate = $($('.s-cal')[1]).find('.s-calTitle > p').text();
            var eMonthDate = $($('.s-cal')[1]).find('.s-calMonth > li.onFocus').data('month');
            tempSYear = sYearDate;
            tempEyear = eYearDate;
            tempSMonth = Number(sMonthDate) - 1;
            tempEMonth = Number(eMonthDate) - 1;
            timePickerDom.text(sYearDate+'-'+sMonthDate+' - '+eYearDate+'-'+eMonthDate);
            var monthDate=sYearDate+sMonthDate+'-'+eYearDate+eMonthDate;
            barChart(chart1,monthDate);
            changeView();
        }
        //给View赋值样式
        function putDateView(sy,ey,sm,em){
            sy && sYearView.text(sy);//赋值起始年份
            ey && eYearView.text(ey);//赋值终止年份
            sm && sMonthViewLis.eq(sm).addClass('onFocus');//高亮起始月份
            em && eMonthViewLis.eq(em).addClass('onFocus'); //高亮终止月份
        }
        //取日期给View赋值样式
        function getRangeDate(){
            var datePeriod = timePickerDom.text().split(' - ');
            var sDate = datePeriod[0].split('-'); //起始年月
            var eDate = datePeriod[1].split('-'); //终止年月
            tempSYear = sDate[0];
            tempEyear = eDate[0];
            tempSMonth = Number(sDate[1]) - 1;
            tempEMonth = Number(eDate[1]) - 1;
            putDateView(tempSYear,tempEyear,tempSMonth,tempEMonth);
        }
        getRangeDate();
        changeView();
        //前一年
        $('.s-calLastYear').click(function(e){
            var startDateDom = $(e.target).next('p');
            startDateDom.text(Number(startDateDom.text()) - 1);
            $(this).parents('.s-cal').find('.s-calMonth > li').removeClass('onFocus');
            changeView(true);
        })
        //后一年
        $('.s-calNextYear').click(function(e){
            var startDateDom = $(e.target).prev('p');
            startDateDom.text(Number(startDateDom.text()) + 1);
            $(this).parents('.s-cal').find('.s-calMonth > li').removeClass('onFocus');
            changeView(true);
        })
        //选中月份
        monthViewLis.click(function(){
            tabs.removeClass('active');
            lastTab.addClass('active');
            //两个if处理使用箭头移动导致都没有选中月份的情况
            if(!sMonthViewLis.hasClass('onFocus')){
                sMonthViewLis.eq(0).addClass('onFocus');
            }
            if(!eMonthViewLis.hasClass('onFocus')){
                eMonthViewLis.eq(0).addClass('onFocus');
            }
            $(this).parents('.s-cal').find('.s-calMonth > li').removeClass('onFocus');
            $(this).addClass('onFocus');
            putRangeDate();
        })
        
    }
};
