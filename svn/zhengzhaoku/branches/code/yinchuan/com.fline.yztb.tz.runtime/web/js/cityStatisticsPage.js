var chart1=echarts.init(document.getElementById('barChart'));
var chart2=echarts.init(document.getElementById('lineChart'));
var itemStatus=0;
var timevalue="";
$(document).ready(function () {
	var windowHeight=window.innerHeight;
	$('body').css('min-height',windowHeight);
	setInterval("NowTime()",1000);
	getTableData('businessAction!wzmDeptItemCount.action','dataTable','select');
	getTableData('businessAction!wzmCityItemCount.action','dataTable2','select2');

	var startTime='2019-05-01';
	var startTime2=startTime.replace('-','').replace('-','');
	var now=new Date();
	var year=now.getFullYear();
	var month=now.getMonth()+1;
	month=month<10?'0'+month:month;
	var day=now.getDate();
	var endTime=(year+'-'+month+'-'+day);
	var endTime2=endTime.replace('-','').replace('-','');
	timevalue=startTime2+"-"+endTime2;
    $('.startDate').val(startTime);
    $('.endDate').val(endTime);
	barChart(chart1,timevalue);
	getDivData();
	lineChart(chart2);
	$('.dateChoose').datetimepicker({
		format:'yyyy-mm-dd',
		autoclose:true,
		minView:2,
		language: 'zh-CN'
	});

	$("#cxltjTitle").bind("click",function () {
		itemStatus=0;
		barChart(chart1,timevalue);
	})
	$("#bjltjTitle").bind("click",function () {
		itemStatus=2;
		barChart(chart1,timevalue);
	})
});
$('.dateChoose').each(function(){
	$(this).change(function(){
	    var startDate=$('.startDate').val().replace('-','').replace('-','');
	    var endDate=$('.endDate').val().replace('-','').replace('-','');
	    if(startDate!=null && startDate.length>0&&endDate!=null && endDate.length>0) {
			timevalue = startDate + '-' + endDate;
            barChart(chart1, timevalue);
}

	})
})
$(".chooseBox div").each(function(){
	$(this).click(function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		lineChart(chart2)
	});
});
$('#select').change(function(){
	getTableData('businessAction!wzmDeptItemCount.action','dataTable','select')
})
$('#select2').change(function(){
	getTableData('businessAction!wzmCityItemCount.action','dataTable2','select2')
})
function getTableData(url,tableId,selectId){
	$.ajax({
		type : 'get',
		url : url,
		data:{'type':$('#'+selectId).val()},
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//console.log(errorThrown)
		},
		success : function(data) {
			$('#'+tableId).find('.nodata').siblings().remove();
			var datas=data.result;
			var dataStr='';
			var num=0;
			if(datas!=null&&datas.length!=0){
				for(var i=0;i<datas.length;i++){
					num++;
					for(var item in datas[i]){
						dataStr+='<tr><td>'+num+'</td><td>'+item+'</td><td>'+datas[i][item]+'</td></tr>'
					}
				}
				$('#'+tableId).find('.nodata').hide();
			}else{
				$('#'+tableId).find('.nodata').show();
			}
			$('#'+tableId).find('tbody').append(dataStr);
		}
	})
}
function getDivData(){
	$.ajax({
		type : 'get',
		url : 'businessAction!wzmItemCount.action',
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//console.log(errorThrown)
		},
		success : function(data) {
			$('.dataListBody').empty();
			var datas=data.result;
			var dataStr='';
			if(datas!=null&&datas.length!=0){
				for(var i=0;i<datas.length;i++){
					dataStr+='<div><span>'+datas[i].city+'</span><span>'+datas[i].gxcount+'</span><span>'+datas[i].xccount+'</span><span>'+datas[i].zhcount+'</span></div>'
				}
			}else{
				dataStr+='<div>暂无数据！</div>'
			}
			$('.dataListBody').append(dataStr);
		}
	})
}
function barChart(chart,value) {
	var url1="businessAction!wzmgxxcItemCount.action";
	var url2="businessAction!wzmgxxcItemCountbj.action";
	var url=url1;
	if(itemStatus==2){
		url=url2;
	}
	$.ajax({
		type : 'get',
		url : url,
		data:{'queryDate':value},
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
			//console.log(errorThrown)
		},
		success : function(data) {
			var datas = data.result;
			var xlabel = [],datas1=[],datas2=[];
			for(var i=0;i<datas.length;i++){
				xlabel.push(datas[i].city);
				datas1.push(datas[i].gxcount);
				datas2.push(datas[i].xccount);
			}
			var memoryOption2 = {
				tooltip : {
					trigger: 'axis',
					axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					}, position:[3,3]
				},
				grid: {
					x:'50',
					y:'40',
					x2:'20',
					y2:'80',
					borderWidth:'0'
				},
				legend: {
					data: ['共享办件量', '协查办件量'],
					align: 'left',
					textStyle: {
						color:'#fff'
					}
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
								fontSize:'14'
							},
							interval:0,
//			                rotate:40
						} ,
						axisLine: {
							lineStyle: {
								type: 'solid',
								color: 'rgb(242,242,242)',//左边线的颜色
								width:'1'//坐标线的宽度
							},
							show:false,
						},
						splitLine:{
							show:false,
						}
					}
				],
				yAxis : [
					{
						name: '数据量(件)',
						type : 'value',
						axisLabel: {
							textStyle: {
								color: 'rgb(164,176,191)',
								fontSize:'13'
							}
						},
						axisLine: {
							lineStyle: {
								type: 'solid',
								color: 'rgb(242,242,242)',//左边线的颜色
								width:'1'//坐标线的宽度
							},
							show:false,
						},
						splitLine:{
							show:false,
						}
					}
				],
				series : [{
					name:'共享办件量',
					type:'bar',
					data:datas1,
					barWidth:'20',
					itemStyle : {
						normal: {
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									{offset: 0, color: '#2AF1FF'},
									{offset: 1, color: '#007BFF'}
								]
							),
							label : {
								show: true,
								color:'#fff',
								position: 'top'
							},textStyle : {
								fontSize : '16'
							},position: 'top'
						}
					}
				},{
					name:'协查办件量',
					type:'bar',
					data:datas2,
					barWidth:'20',
					itemStyle : {
						normal: {
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									{offset: 0, color: '#FAD601'},
									{offset: 1, color: '#D28A20'}
								]
							),
							label : {
								show: true,
								color:'#fff',
								position: 'top'
							},textStyle : {
								fontSize : '16'
							},position: 'top'}
					}
				}]
			};
			var memoryOption1 = {
				tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }, position:[3,3]
			    },
			    grid: {
			    	x:'50',
			    	y:'40',
			        x2:'20',
			        y2:'80',
			        borderWidth:'0'
			    },
			    legend: {
			        data: ['共享查询量', '协查查询量'],
			        align: 'left',
					textStyle: {
			        	color:'#fff'
					}
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
			                    fontSize:'14'
			                },
			                interval:0,  
//			                rotate:40  
			            } ,
			            axisLine: {
			            	 lineStyle: {
		                        type: 'solid',
		                        color: 'rgb(242,242,242)',//左边线的颜色
		                        width:'1'//坐标线的宽度
		                    },
		                    show:false,
		                },
		                splitLine:{
		                	show:false,
		                }
			        }
			    ],
			    yAxis : [
			        {
			        	name: '数据量(件)',
			            type : 'value',
			            axisLabel: {
			                textStyle: {
			                    color: 'rgb(164,176,191)',
			                    fontSize:'13'
			                }
			            },
			            axisLine: {
			            	 lineStyle: {
		                        type: 'solid',
		                        color: 'rgb(242,242,242)',//左边线的颜色
		                        width:'1'//坐标线的宽度
		                    },
		                    show:false,
		                },
		                splitLine:{
		                	show:false,
		                }
			        }
			    ],
			    series : [{
		            name:'共享查询量',
		            type:'bar',
		            data:datas1,
		            barWidth:'20',
		            itemStyle : { 
		            	normal: {
		            		color: new echarts.graphic.LinearGradient(
	                            0, 0, 0, 1,
	                            [
	                                {offset: 0, color: '#2AF1FF'},
	                                {offset: 1, color: '#007BFF'}
	                            ]
	                        ),
		            		label : {
		            			show: true,
		            			color:'#fff',
		            			position: 'top'
			                },textStyle : {
		                        fontSize : '16'
		                    },position: 'top'
                    	}
		    		}
		    	},{
		            name:'协查查询量',
		            type:'bar',
		            data:datas2,
		            barWidth:'20',
		            itemStyle : { 
		            	normal: {
		            		color: new echarts.graphic.LinearGradient(
	                            0, 0, 0, 1,
	                            [
	                                {offset: 0, color: '#FAD601'},
	                                {offset: 1, color: '#D28A20'}
	                            ]
	                        ),
		            		label : {
		            			show: true,
		            			color:'#fff',
		            			position: 'top'
			                },textStyle : {
		                        fontSize : '16'
		                    },position: 'top'}
			    		}
		        }]
			};
			var memoryOption=memoryOption1;
			if(itemStatus==2){
				memoryOption=memoryOption2;
			}
			chart.setOption(memoryOption,true);
		}
	});
};
//折线图
function lineChart(chart,name){
	var status=$(".chooseBox").eq(0).find(".active").attr("data-choose");
	$.ajax({
		type : 'post',
		url : 'businessAction!wzmDayItemCountChange.action',
		data:{'days':status},
		dataType : 'json',
		cache : false,
		async : true,
		error : function(request, textStatus, errorThrown) {
			fxShowAjaxError(request, textStatus, errorThrown);
		},
		success : function(data) {
			var dataList=data.result;
			var xlabel = [],yvalue=[];
			for(var i=0;i<dataList.length;i++){
				for(var item in dataList[i]){
					xlabel.push(item);
					yvalue.push(dataList[i][item]);
				}
			}
			//折线图
			var option = {
		        tooltip : {
		            trigger: 'axis',
		            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		                type : 'line' ,
		                lineStyle:{
		                	type:'dotted'
		                }
		            }
		        },
		        grid: {
		        	x:'50',
		        	y:'40',
		            x2:'25',
		            y2:'25',
		            borderWidth:0
		        },
		        xAxis : [
		            {
		                type : 'category',
		                data : xlabel,
		                axisLabel: {
		                    textStyle: {
		                        color: 'rgb(179,179,179)',
		                        fontSize:'13'
		                    }
		                },axisLine: {
		                    lineStyle: {
		                        type: 'solid',
		                        color: 'rgb(242,242,242)',//左边线的颜色
		                        width:'1'//坐标线的宽度
		                    }
		                },
		            	 boundaryGap : false,
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
		                        color: 'rgb(179,179,179)',
		                        fontSize:'13'
		                    },
		                 	formatter: '{value}'
		                },
			             axisLine: {
			            	 lineStyle: {
		                        type: 'solid',
		                        color: 'rgb(242,242,242)',//左边线的颜色
		                        width:'1'//坐标线的宽度
		                    },
		                    show:false,
		                },
		                splitLine:{
		                	show:false,
		                	lineStyle: {
		                        type: 'solid',
		                        color: 'rgb(242,242,242)',//左边线的颜色
		                        width:'1'//坐标线的宽度
		                    }
		                }
		            }
		        ],
		        series : [{
		            name:name,
		            type:'line',
		            smooth:true,
			        symbol: 'none',
			        sampling: 'average',
			        itemStyle: {
			            normal: {
			                color: '#007EFF'
			            }
			        },
			        markPoint: {
		                data: [
		                    {type: 'max', name: '最大值'},
		                    {type: 'min', name: '最小值'}
		                ]
		            },
			        areaStyle: {
			            normal: {
			                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
			                    offset: 0,
			                    color: '#0092FF'
			                },{
			                    offset: 1,
			                    color: '#071038'
		                    }])
		                }
		            },
		            data:yvalue
		        }]
		    };
			 chart.setOption(option,true);   
		}
	}); 
};
function NowTime(){
    //获取年月日
    var time=new Date();
    var year=time.getFullYear();
    var month=time.getMonth()+1;
    var day=time.getDate();
    //获取时分秒
    var h=time.getHours();
    var m=time.getMinutes();
    var s=time.getSeconds();
    //检查是否小于10
    h=check(h);
    m=check(m);
    s=check(s);
   $("#nowTime").text(year+"年"+month+"月"+day+"日  "+h+":"+m+":"+s);
}
//时间数字小于10，则在之前加个“0”补位。
function check(i){
    var num;
    i<10?num="0"+i:num=i;
    return num;
}