

//证件查询统计
var myChart2;
//饼图
var myChart3;
//折线图
var myChart1;
//右侧柱状图
var myChart ;

var last_select = "select_0";  
$(function(){
    	setResizeWindow();
    	 if(isIE()){
    		 var els = $('.dataStatistics').find('.digit_set');
   	    	 $(els).each(function(i,item){
   	    		$(item).css({"background-color":"#00C0EF"}).css("border-radius","0 0 3px 3px");
   	    		$(item).html("<span style=\"margin-left:4px;\">0</span>");
   	    	 });
    		 $(".left_bar").each(function(i,item){
    			 $(item).css("height",$(item).parent().height());
    		 });
    		 $(".right_bar").each(function(i,item){
    			 $(item).css("height",$(item).parent().height());
    		 });
    		// $("#gridData").mCustomScrollbar({theme:"minimal-dark",scrollInertia:20,axis:"y",set_height:340});
    	 }
    	 $(window).load(function(){  
    		$("#gridData").mCustomScrollbar({
    			 scrollInertia:20,
    			 axis:"y",
    			 set_height:166,
    			 horizontalScroll : false//水平滚动条 
    		});
    		 var h = $(window).height();
    		 $("body").mCustomScrollbar({
    			 theme:"minimal-dark",
    			 scrollInertia:20,
    			 axis:"y",
    			 set_height:h,
    			 horizontalScroll : false,//水平滚动条
    			 alwaysTriggerOffsets:false
    		});
    	 });
    	 
    	 var ccId = setTimeout(function(){
    		 clearTimeout(ccId);
    		 getItemRequest();
        	 refreshData();
    	 }, 2000);
    	 createClock();
    	
   	//设置主要样式  
   		require(  
   		    [  
   		        'echarts',  
   		        'echarts/chart/bar',  
   		        'echarts/chart/line',
   		        'echarts/chart/pie'
   		    ],  
   		    function(ec){   
   		         //初始化echart对象  
   		         myChart = ec.init(document.getElementById('contentBar'));
   		         myChart1 = ec.init(document.getElementById('zhexianBar'));
   		         myChart3 = ec.init(document.getElementById('contentPie'));
   		         myChart2 = ec.init(document.getElementById('zhuzhuangbar'));
   		});
   		
   		$(".week a").click(function(){
   			var selectName = $(this).attr("name");
   			$(this).addClass("selectWeek");
   			$(".week a").each(function(i,item){
   				if($(item).attr("name") != selectName){
   					$(item).removeClass("selectWeek");
   				}
   			});
   			if(selectName != last_select){
   				last_select = selectName;
   				if(last_select == "select_0"){
   					//本周
   					getWeekItemCount(0);
   				}else{
   					//上周
   					getWeekItemCount(1);
   				}
   			}
   		});
    });
    function createClock(){
    	//时钟
   		var clock = setInterval(function(){
   			var date = new Date(); 
   			var m = date.getMonth()+1;
   			if(m < 10){
   				m = "0"+m;
   			}
   			var str = date.getFullYear()+"-"+m+"-"+date.getDate();
   			//小时
   			var hour = date.getHours()+"";
   			if(hour.length < 2){
   				hour = "0"+hour;
   			}
   			//分钟
   			var miuntes = date.getMinutes()+"";
   			if(miuntes.length < 2){
   				miuntes = "0"+miuntes;
   			}
   			//秒
   			var seconds = date.getSeconds()+"";
   			if(seconds.length < 2){
   				seconds = "0"+seconds;
   			}
   			$(".text_time").html(str+" "+hour+":"+miuntes+":"+seconds+"");
   		}, 1000);
    }
    
    function refreshData(){
         dayTempCount();
    	 
    	 getDayItem();
    	 
    	 getWeekItemCount(0);
    	 
    	 getServiceCount();
    	 
    	 getMonthItemCount();
    }
    function isIE() { //ie?
        if (!!window.ActiveXObject || "ActiveXObject" in window)
          return true;
        else
          return false;
    }
    function loadNum(numStr){
    	var els = $('.dataStatistics').find('.digit_set span');
    	var nums = numStr.split("");
    	var tempI = nums.length;
    	for(var i = els.length;i >= 0;i--){
    		$(els[i]).html("0");
    		if(tempI >= 0){
    			$(els[i]).html(nums[tempI]);
        		tempI--;
    		}
    	}
    }
    
    var lastCount = 0;
    //累积服务事项请求次数
    function getItemRequest(){
    	$.ajax({
			type : 'POST',
			url : 'businessAction!cumulativeTempItemRequest.action?rand='+new Date().getTime(),
			dataType : 'json',
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				//console.log("累积服务事项请求次数:"+JSON.stringify(data));
				var count = data.count;
				//alert(JSON.stringify(data.result));
				if(isIE()){
					//IE下显示数字变化
					if(lastCount !=0 && lastCount != count){
						loadNum(count+"");
						lastCount = count;
						refreshData();
					}else if(lastCount == 0){
						loadNum(count+"");
						lastCount = count;
					}
				}else{
					if(lastCount !=0 && lastCount != count){
						//其他浏览器下显示数字变化
						var htmlStr = "";
						for(var i = 0;i < 8;i++){
							if(i < 7){
								htmlStr += "<div class=\"digit_set\"></div>";
							}else{
								htmlStr += "<div class=\"digit_set set_last\"></div>";
							}
						}
						$('.dataStatistics').html(htmlStr);
						$('.dataStatistics').dataStatistics({min:lastCount,max:count,time:1000,len:8});
						lastCount = count;
						refreshData();
					}else if(lastCount == 0){
						//其他浏览器下显示数字变化
						var htmlStr = "";
						for(var i = 0;i < 8;i++){
							if(i < 7){
								htmlStr += "<div class=\"digit_set\"></div>";
							}else{
								htmlStr += "<div class=\"digit_set set_last\"></div>";
							}
						}
						$('.dataStatistics').html(htmlStr);
						$('.dataStatistics').dataStatistics({min:count,max:count,time:1000,len:8});
						lastCount = count;
					}
				}
				var htmlStr = "";
				$(data.result).each(function(i,item){
					htmlStr += "<tr>";
					htmlStr += "<td>"+item.departmentName+"</td>";
					htmlStr += "<td>"+item.itemCount+"</td>";
					htmlStr += "<td>"+item.certCount+"</td>";
					htmlStr += "</tr>";
				});
				$("#bodyData").html(htmlStr);
				if(data.result.length == 0){
					$("#bodyData").css({"border-color":"#666666"});
				}
				var cltId = setTimeout(function(){
					clearInterval(cltId);
					getItemRequest();
				}, 10000);
			}
		});
    }
    //证件查询统计
    function dayTempCount(){
    	$.ajax({
			type : 'POST',
			url : 'businessAction!dayTempCount.action?rand='+new Date().getTime(),
			dataType : 'json',
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				//console.log(JSON.stringify(data));
				//console.log("证件查询统计:"+JSON.stringify(data));
				var tempObj = {};
				var certObj = {};
				$(data.result).each(function(i,item){
					if(typeof(tempObj[item.tempName]) != 'undefined'){
						tempObj[item.tempName].push(item);
					}else{
						tempObj[item.tempName] = [item];
					}
					certObj[item.tempName] = "";
				});
				//console.log(JSON.stringify(tempObj));
				var ylabel = [];
				for(var key in tempObj){
					ylabel.push(key);
				}
				var xlabel = [];
				for(var key in certObj){
					xlabel.push(key);
				}
				
				var s = [];
				$(xlabel).each(function(i,item){
					var o = {
				            name: item,
				            type: 'bar',
				            stack: '总量',
				            label: {
				                normal: {
				                    show: true,
				                    position: 'insideRight'
				                }
				            }
				    }
					var datas = [];
					for(var key in tempObj){
						var tempCount = 0;
						$(tempObj[key]).each(function(iii,iiitem){
							if(iiitem.tempName == item){
								tempCount = iiitem.tempCount;
							}
						});
						datas.push(tempCount);
					}
					o.data = datas;
					s.push(o);
				});
				
				
				//柱状图
				var option2 = {
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        },
					        formatter:function(params)
					        {
					        	//console.log(JSON.stringify(a));
					        	var res = '<font color="#00ecf5" style="font-size:20px;">' + params[0].name+"</font>";
					            for (var i = 0, l = params.length; i < l; i++) {
					            	if(params[i].value != 0){
					            		res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
					            	}
					            }
					        	return res;
					        }
					    },
					    grid: {
					    	x:'30',
					    	y:'10',
					        x2:'10',
					        y2:'40',
					        borderWidth:'0',
					        borderColor:'#21242c'
					    },
					    yAxis:  {
					        type: 'value',
					        splitLine:{
			                	show:false
			                },
			                axisLabel: {
				                textStyle: {
				                    color: '#666666',
				                    fontSize:'10'
				                }
				            }
					    },
					    xAxis: {
					        type: 'category',
					        data: ylabel,
					        splitLine:{
			                	show:false
			                },
			                axisLabel: {
				                textStyle: {
				                    color: '#666666',
				                    fontSize:'13'
				                }
				            }
					    },
					    series:s
					};
		        myChart2.setOption(option2,true);
			}
        });
    }
    
    //当天事项请求分布
    function getDayItem(){
    	//businessAction!dayItemCount
    	$.ajax({
			type : 'POST',
			url : 'businessAction!dayItemCount.action?rand='+new Date().getTime(),
			dataType : 'json',
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				//alert(JSON.stringify(data))
				//console.log("按天请求分布:"+JSON.stringify(data));
				var temp = [];
				$(data.result).each(function(i,item){
					temp.push({value:item.dayCount,name:item.departmentName});
				});
				//饼图
				var option = {
					    tooltip: {
					        trigger: 'item',
					        formatter: "{a} <br/>{b}: {c} ({d}%)"
					    },
					    avoidLabelOverlap: false,
					    series: [
					        {
					            type:'pie',
					            radius: ['30%', '60%'],
					            avoidLabelOverlap: false,
					            label: {
					                normal: {
					                    show: false,
					                    position: 'inside'
					                },
					                emphasis: {
					                    show: true,
					                    textStyle: {
					                        fontSize: '30',
					                        fontWeight: 'bold'
					                    }
					                }
					            },
					            data:temp
					        }
					    ]
					};
				  if(temp.length > 0){
					  myChart3.setOption(option,true);   
				  }
			}
		});
    }
   
    //按周事项统计
    function getWeekItemCount(p){
    	$.ajax({
			type : 'POST',
			url : 'businessAction!weekItemCount.action?rand='+new Date().getTime(),
			dataType : 'json',
			data:{week:p},
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				//alert(JSON.stringify(data));
				//console.log("按周事项统计:"+JSON.stringify(data));
				var datas = data.result;
				var xlabel = [];
				var yvalue = [];
				$(datas).each(function(i,item){
					//alert(item.weekDay);
					//if(item.weekCount != 0){
						xlabel.push(item.weekDay);
						yvalue.push(item.weekCount);
					//}
				});
				//alert(xlabel.length);
				//折线图
				var option1 = {
				        tooltip : {
				            trigger: 'axis'
				        },
				        grid: {
				        	x:'35',
				        	y:'10',
				            x2:'20',
				            y2:'25',
				            /*borderWidth:'1',*/
				          /*  borderColor:'#666666'*/
				        },
				        xAxis : [
				            {
				                type : 'category',
				                data : xlabel,
				                axisLabel: {
				                    textStyle: {
				                        color: '#666666',
				                        fontSize:'13'
				                    }
				                },
				                splitLine:{
				                	show:true,
				                	lineStyle:{
				                		 /* color: ['#ccc', '#ccc']*/
				                	}
				                }
				            }
				        ],
				        yAxis : [
				            {
				                type : 'value',
				                axisLabel : {
				                    formatter: '{value} °C'
				                },
				                axisLabel: {
				                    textStyle: {
				                        color: '#666666',
				                        fontSize:'13'
				                    }
				                },
				                splitArea:{
				                	show:true,
				                	areaStyle:{
				                		/*color:['rgba(20,20,20,0.3)','rgba(20,20,20,0.3)']*/
				                	}
				                },
				                splitLine:{
				                	show:true,
				                	lineStyle:{
				                		 /* color: ['#ccc', '#ccc']*/
				                	}
				                }
				            }
				        ],
				        series : [
				            {
				                name:'请求次数',
				                type:'line',
				                data:yvalue
				            }
				        ]
				    };
				
				 myChart1.setOption(option1,true);     
			}
		});
    }
    //按月事项统计
    function getMonthItemCount(){
    	$.ajax({
			type : 'POST',
			url : 'businessAction!monthCerNoCount.action?rand='+new Date().getTime(),
			dataType : 'json',
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				//alert(JSON.stringify(data));
				//console.log("按月事项统计:"+JSON.stringify(data));
				var xlabel = [];
				var ylabel = [];
				$(data.result).each(function(i,item){
					xlabel.push(item.months+"月");
					ylabel.push(item.monthCount);
				});
				
				var option3 = {
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
					    },
					    grid: {
					    	x:'55',
					    	y:'10',
					        x2:'20',
					        y2:'30',
					        borderWidth:'1',
					        borderColor:'#21242c'
					    },
					    xAxis : [
					        {
					            type : 'category',
					            data : xlabel,
					            axisTick: {
					                alignWithLabel: true
					            },
					            axisLabel: {
					                textStyle: {
					                    color: '#666666',
					                    fontSize:'13'
					                }
					            } ,
				                splitLine:{
				                	show:true,
				                	lineStyle:{
				                		  color: ['#21242c', '#21242c']
				                	}
				                }
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel: {
					                textStyle: {
					                    color: '#666666',
					                    fontSize:'13'
					                }
					            },
					            axisLabel: {
				                    textStyle: {
				                        color: '#666666',
				                        fontSize:'13'
				                    }
				                },
				                splitLine:{
				                	show:true,
				                	lineStyle:{
				                		  color: ['#21242c', '#21242c']
				                	}
				                }
					        }
					    ],
					    series : [
					        {
					            name:'数量',
					            type:'bar',
					            data:ylabel
					        }
					    ]
					};
				myChart.setOption(option3,true);      
			}
		});
    }
    //服务事项统计
    function getServiceCount(){
    	$.ajax({
			type : 'POST',
			url : 'businessAction!serviceItemCount.action?rand='+new Date().getTime(),
			dataType : 'json',
			error : function(request, textStatus, errorThrown) {
				//fxShowAjaxError(request, textStatus, errorThrown);
			},
			success : function(data) {
				//alert(JSON.stringify(data));
				//console.log("服务事项统计:"+JSON.stringify(data));
				var item = data.result[0];
				$("#dayTempCount").html(item.dayTempCount);
				$("#tempCount").html(item.tempCount);
				$("#certNoCount").html(item.cerNoCount);
				$("#departMentName").html(item.departmentName);
			}
		});
    }
    
    function returnToHome(){
    	window.location.href = "index.jsp";
    }