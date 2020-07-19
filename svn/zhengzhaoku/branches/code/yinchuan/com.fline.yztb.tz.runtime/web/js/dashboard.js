//证件查询统计
var myChart2,myChart3,myChart1,typeSelect="";
$(function(){
	deptItemCount();
	cityItemCount("type=1");
	createClock();
	$("select").each(function(){
		$(this).change(function(){
			deptItemCount();
		});
	});
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
	         myChart1 = ec.init(document.getElementById('zhexianBar'));
	         myChart3 = ec.init(document.getElementById('contentPie'));
	         myChart2 = ec.init(document.getElementById('zhuzhuangbar'));
	         dayTempCount();
	    	 getDayItem();
	    	 getWeekItemCount("days=7");
	    	 getServiceCount();
	});
	$(".week a").click(function(){
		var selectName = $(this).attr("name");
		$(this).addClass("selectWeek");
		$(this).parent().siblings().find("a").removeClass("selectWeek");
	});

//    var timeid = window.setInterval(
//    		function () {
//        deptItemCount();
//        cityItemCount("type=1");
//        dayTempCount();
//        getDayItem();
//        getWeekItemCount("days=7");
//        getServiceCount();
//    },60000
//    );
});
$("#weekSelect").find("a").click(function(){
	var type=$(this).attr("name");
	getWeekItemCount(type);
});
$("#areaSelect").find("a").click(function(){
	var type=$(this).attr("name");
	cityItemCount(type);
});
function createClock(){
	//时钟
	var clock = setInterval(function(){
		var date = new Date(); 
		var m = date.getMonth()+1;
		if(m < 10){
			m = "0"+m;
		};
		var str = date.getFullYear()+"-"+m+"-"+date.getDate();
		//小时
		var hour = date.getHours()+"";
		if(hour.length < 2){
			hour = "0"+hour;
		};
		//分钟
		var miuntes = date.getMinutes()+"";
		if(miuntes.length < 2){
			miuntes = "0"+miuntes;
		};
		//秒
		var seconds = date.getSeconds()+"";
		if(seconds.length < 2){
			seconds = "0"+seconds;
		};
		$(".text_time").html(str+" "+hour+":"+miuntes+":"+seconds+"");
	}, 1000);
};
//部门办件排名
function deptItemCount(){
	var dataStr=$('#chooseForm').serialize()+"&rand="+new Date().getTime();
	var url='businessAction!deptItemCount.action';
	$.ajax({
		type : 'POST',
		url : url,
		dataType : 'json',
		data:dataStr,
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			var htmlStr = "";
			var result = data.result;
			$(result).each(function(i,item){
				htmlStr += "<tr>";
				htmlStr += "<td>"+ (i+1) +"</td>";
				htmlStr += "<td title='"+item.departmentName+"'>"+item.departmentName+"</td>";
				htmlStr += "<td>"+item.itemCount+"</td>";
				htmlStr += "</tr>";
		
			});
			$("#bodyData").html(htmlStr);
		}
	});
};

//区县办件排名
function cityItemCount(type){
	$.ajax({
		type : 'POST',
		url : 'businessAction!cityItemCount.action?'+type+'&rand='+new Date().getTime(),
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			var htmlStr = "";
			var result = data.result;
			$(result).each(function(i,item){
				htmlStr += "<tr>";
				htmlStr += "<td>"+ (i+1) +"</td>";
				htmlStr += "<td>"+item.departmentName+"</td>";
				htmlStr += "<td>"+item.itemCount+"</td>";
				htmlStr += "</tr>";
		
			});
			$("#bodyData2").html(htmlStr);
		}
	});
};
//证件查询统计
function dayTempCount(){
	$.ajax({
		type : 'POST',
		url : 'businessAction!implementedItem.action',
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			var result=data.result;
			var xlabel = [],total=[],implemented=[],canRealize=[];
			$(result).each(function(i,item){
				xlabel.push(item.name);
				total.push(item.total);
				implemented.push(item.implemented);
				canRealize.push(item.canRealize);
			});
			var option2 = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        },formatter:function(params){
		                var relVal = params[0].name+"<br/>";
		                relVal += params[0][0]+ ' : ' + params[0].value+"<br/>";
		                relVal +=params[1][0]+ ' : ' +params[1].value+"("+(params[1].value/params[0].value*100).toFixed(2)+"%)<br/>";
		                // relVal += params[2][0]+ ' : ' + params[2].value+"("+(params[2].value/params[0].value*100).toFixed(2)+"%)";
		                return relVal;
	                }, position:[3,3]
			    },
			    grid: {
			    	x:'35',
			    	y:'30',
			        x2:'20',
			        y2:'30',
			        borderWidth:'0'
			    },
			    legned:{
			    	borderColor:'rgb(18,60,112)',
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
			                    color: 'rgb(164,176,191)',
			                    fontSize:'10'
			                }
			            } ,
		                splitLine:{
		                	show:false,
		                }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            axisLabel: {
			                textStyle: {
			                    color: 'rgb(164,176,191)',
			                    fontSize:'13'
			                }
			            },
		                splitLine:{
		                	show:false,
		                }
			        }
			    ],
			    series : [{
		            name:'总量',
		            type:'bar',
		            data:total,
		            itemStyle: {        // 系列级个性化样式，纵向渐变填充
		                normal: {
		                	barBorderColor:'rgb(255,184,70)',
		                    barBorderWidth: '15px',
		                    color : (function (){
		                        var zrColor = require('zrender/tool/color');
		                        return zrColor.getLinearGradient(
		                            0, 400, 0, 300,
		                            [[0, 'rgb(255,184,70)'],[1, 'rgb(255,184,70)']]
		                        )
		                    })()
		                },
		            }
		        },
                //     {
		        //     name:'可实现',
		        //     type:'bar',
		        //     data:canRealize,
		        //     itemStyle: {        // 系列级个性化样式，纵向渐变填充
		        //         normal: {
		        //         	barBorderColor:'rgb(121,175,255)',
		        //             barBorderWidth: '15px',
		        //             color : (function (){
		        //                 var zrColor = require('zrender/tool/color');
		        //                 return zrColor.getLinearGradient(
		        //                     0, 400, 0, 300,
		        //                     [[0, 'rgb(121,175,255)'],[1, 'rgb(121,175,255)']]
		        //                 )
		        //             })()
		        //         },
		        //     }
		        // },
                    {
		            name:'已实现',
		            type:'bar',
		            data:implemented,
		            itemStyle: {        // 系列级个性化样式，纵向渐变填充
		                normal: {
		                	barBorderColor:'rgb(121,175,255)',
		                    barBorderWidth: '15px',
		                    color : (function (){
		                        var zrColor = require('zrender/tool/color');
		                        return zrColor.getLinearGradient(
		                            0, 400, 0, 300,
                                    [[0, 'rgb(121,175,255)'],[1, 'rgb(121,175,255)']]
		                        )
		                    })()
		                },
		            }
		        }]
			};
	        myChart2.setOption(option2,true);
		}
    });
};
//当天事项请求分布
function getDayItem(){
	$.ajax({
		type : 'POST',
		url : 'businessAction!deptItemCountByDay.action?rand='+new Date().getTime(),
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			var temp = [],xlabel=[];
			var result= data.result;
			$(result).each(function(i,item){
				temp.push({value:item.itemCount,name:item.departmentName});
				xlabel.push(item.departmentName);
			});
			//饼图
			var option = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "今日办件量:<br>{c} ({d}%)"
			    },
			    color:["rgb(225,98,175)","rgb(255,159,126)","rgb(255,219,92)","rgb(102,225,227)","rgb(54,163,219)"],
			    legend: {
                	show:true,
                    orient : 'vertical',
                    x: '0',
                    y:'3px',
                    data:xlabel,
                    textStyle:{color:'rgb(164,176,191)'}
                }, 
			    series: [{
		            type:'pie',
		            radius: ['50%', '70%'],
		            center: ['55%', '60%'],
		            minAngle: 5,           　　 //最小的扇区角度（0 ~ 360），用于防止某个值过小导致扇区太小影响交互
	                avoidLabelOverlap: true,
		            itemStyle : {
		            	normal : {
		            		label : {
		            			show : false,
		            		},labelLine: {
		                        show: false,
			                },textStyle:{       //这只是为了让文字居中而已
	                          fontSize : 8,
		            		}
		            	},
		            },
		            data:temp
		        }]
			};
			if(temp.length > 0){
				$("#contentPie").find(".noData").remove();
				myChart3.setOption(option,true);   
			}else{
				$("#contentPie").find(".noData").remove();
				var noData="<div class='noData'>暂无数据!</div>"
				$("#contentPie").append(noData);
			};
		}
	});
};
//按周事项统计
function getWeekItemCount(p){
	$.ajax({
		type : 'POST',
		url : 'businessAction!dayItemCountChange.action?'+p+'&rand='+new Date().getTime(),
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			var datas = data.result;
			var xlabel = [];
			var yvalue = [];
			$(datas).each(function(i,item){
				xlabel.push(item.time);
				yvalue.push(item.itemCount);
			});
			//折线图
			var option1 = {
		        tooltip : {
		            trigger: 'axis'
		        },
		        grid: {
		        	x:'40',
		        	y:'40',
		            x2:'50',
		            y2:'25',
		            borderWidth:'0'
		        },
		        xAxis : [
		            {
		                type : 'category',
		                data : xlabel,
		                axisLabel: {
		                    textStyle: {
		                        color: 'rgb(164,176,191)',
		                        fontSize:'13'
		                    }
		                },
		                splitLine:{
		                	show:false,
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
		                        color: 'rgb(164,176,191)',
		                        fontSize:'13'
		                    }
		                },
		                splitArea:{
		                	show:false,
		                },
		                splitLine:{
		                	show:false,
		                }
		            }
		        ],
		        series : [{
	                name:'请求次数',
	                type:'line',
	                data:yvalue,
	                markPoint : {
	                    data : [
	                        {type : 'max', name: '最大值'},
	                        {type : 'min', name: '最小值'}
	                    ]
	                },
	                markLine : {
	                    data : [
	                        {type : 'average', name: '平均值'}
	                    ]
	                },
	                itemStyle: {        // 数据级个性化折线样式
                        normal: {
                        	lineStyle: {            // 系列级个性化折线样式，横向渐变描边
                                width: 2,
                                color: (function (){
                                    var zrColor = require('zrender/tool/color');
                                    return zrColor.getLinearGradient(
                                        0, 0, 1000, 0,
                                        [[0, 'rgba(255,0,0,0.8)'],[0.8, 'rgba(255,255,0,0.8)']]
                                    )
                                })(),
                                shadowColor : 'rgba(0,0,0,0.5)',
                                shadowBlur: 10,
                                shadowOffsetX: 8,
                                shadowOffsetY: 8
                            }
                        },
                        emphasis: {
                            color: 'orange',
                            label : {
                                show: true,
                                position: 'inside',
                                textStyle : {
                                    fontSize : '20'
                                }
                            }
                        }
                    }
	            }]
		    };
			if(datas.length > 0){
				$(".noData").siblings().show();
				$("#zhexianBar").find(".noData").remove();
				myChart1.setOption(option1,true);     
			}else{
				$("#zhexianBar").find(".noData").remove();
				$(".noData").prev().hide();
				var noData="<div class='noData'>暂无数据!</div>"
				$("#zhexianBar").append(noData);
			};
		}
	});
};
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
			var item = data.result[0];
			$("#cerNoCount").html(item.cerNoCount);
			$("#tempCount").html(item.tempCount);
			$("#itemCount").html(item.itemCount);
			$("#certCount").html(item.certCount);
			$("#sealCount").html(item.sealCount);
			$("#caCount").html(item.caCount);
		}
	});
};
function returnToHome(){
	window.location.href = "index.jsp";
};