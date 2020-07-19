    var myChart1 = echarts.init(document.getElementById('zhuzhuangbar'));
    var myChart2 = echarts.init(document.getElementById('contentPie'));
    var myChart3 = echarts.init(document.getElementById('zhexianBar1'));
    var myChart4 = echarts.init(document.getElementById('zhexianBar2'));
    //var myChart5 = echarts.init(document.getElementById('zhexianBar3'));
    var allData = {};
    var allDeptData = {};
    
$(document).ready(function(){
    getServiceCount();
    deptItemCount();
    //cityItemCount("type=1");
    itemCountRank("type=1");
	barChart(myChart1,$('#noData1'))
    pieChart(myChart2,$('#noData2'),7);
	lineChart(myChart3,"days=7",'#FF9900','#FFFFCC',$('#noData3'));
	lineChart(myChart4,"days=30",'#3366CC','#99CCFF',$('#noData4'));
	//lineChart(myChart5,"days=-1",'#AAF6FF','#3390FF',$('#noData5'));
});

$('.chartTimeSpan').each(function(){
	var myDate = new Date();
	var year=myDate.getFullYear();
	var month=myDate.getMonth() + 1; 
	var date=myDate.getDate();
	$(this).text(year+'年'+month+'月'+date+'日');
});
$("#areaSelect").find("a").click(function(){
	var type=$(this).attr("name");
	//cityItemCount(type);
	itemCountRank(type);
	$(this).addClass('selectWeek');
	$(this).parent().siblings().find('a').removeClass('selectWeek');
});
$("#deptDateSelect").change(function(){
	$("#bodyData").empty;
	deptItemCount();
});
//服务事项统计
function getServiceCount(){
	$.ajax({
		type : 'POST',
		url : 'businessAction!serviceItemCount.action?rand='+new Date().getTime(),
		dataType : 'json',
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var item = data.result[0];
			$("#cerNoCount").html(item.cerNoCount);
			$("#tempCount").html(item.tempCount);
			$("#itemCount").html(item.itemCount);
			$("#certCount").html(item.certCount);
			$("#formCount").html(item.formCount);
			$("#caCount").html(item.caCount);
		}
	});
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
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var htmlStr = "";
			var result = data.result;
			allDeptData = data.allData;          
			$(result).each(function(i,item){
				var departmentId = item.departmentId;
				htmlStr += "<tr>";
				htmlStr += "<td>"+ (i+1) +"</td>";
				htmlStr += "<td title='"+item.departmentName+"' onclick=findItemCountByDept('" + departmentId +  "')>"+item.departmentName+"</td>";
				htmlStr += "<td>"+item.itemCount+"</td>";
				htmlStr += "</tr>";
		
			});
			$("#bodyData").html(htmlStr);
		}
	});
};
function findItemCountByDept(departmentId){
	formatDialog($("#showWrap"),{title:"事项列表",dialogClass:"showDialog"},{});
	var allDataStr = JSON.stringify(allDeptData);
	$("#showWrap").empty();
	$.ajax({
		type : 'POST',
		url : 'businessAction!findItemCountByDept.action',
		data:{
			'allData':allDataStr,
			'department': departmentId
		},
		traditional:true,
		dataType : 'json',
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var html = "";
			$(data.result.results).each(function(i,item){
				html+="<div>" + item.itemName + item.count + "个</div>";
			})
			$("#showWrap").append(html)
		}
	});
}

//区县办件排名
function cityItemCount(type){
	$.ajax({
		type : 'POST',
		url : 'businessAction!cityItemCount.action?'+type+'&rand='+new Date().getTime(),
		dataType : 'json',
		async : true,
		error : function(request, textStatus, errorThrown) {
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
function itemCountRank(type){
	$.ajax({
		type : 'POST',
		url : 'businessAction!itemCountRank.action?'+type+'&rand='+new Date().getTime(),
		dataType : 'json',
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var htmlStr = "";
			var result = data.result;
			allData = data.allData;
			$(result).each(function(i,item){
				htmlStr += "<tr>";
				htmlStr += "<td>"+ (i+1) +"</td>";
				var itemCode = item.itemCode;
				htmlStr += "<td onclick=findDeptItemCountByCode('" + itemCode + "')>"+item.itemName+"</td>";
				htmlStr += "<td>"+item.itemCount+"</td>";
				htmlStr += "</tr>";
		
			});
			$("#bodyData2").html(htmlStr);
		}
	});
}
function findDeptItemCountByCode(itemCode){
	formatDialog($("#showWrap"),{title:"部门列表",dialogClass:"showDialog situationHeight"},{});
	var allDataStr = JSON.stringify(allData);
	$("#showWrap").empty();
	$.ajax({
		type : 'POST',
		url : 'businessAction!findDeptItemCountByCode.action',
		data:{
			'allData':allDataStr,
			'findItemCode': itemCode
		},
		traditional:true,
		dataType : 'json',
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var html = "";
			$(data.result.results).each(function(i,item){
				html+="<div>" + item.deptName + item.itemCount + "个</div>";
			})
			$("#showWrap").append(html)
		}
	});
}
//柱状图
function barChart(chart,obj){
	$.ajax({
		type : 'post',
		url : 'businessAction!implementedItem.action',
		dataType : 'json',
		cache : false,
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var dataList=data.result;
			if(dataList.length==0){
				obj.removeClass('base_hidden');
				obj.siblings().addClass('base_hidden');
			}
			var xlabel = [],yvalue=[];
			$(dataList).each(function(i,item){
				xlabel.push(dataList[i].subDeptName);
				yvalue.push(dataList[i].total);
			}); 
			//柱状图
			var option = {
			    xAxis: {
			        data: xlabel,
			        axisLabel: {
			            textStyle: {
			                color: '#999'
			            }
			        },
			        axisTick: {
			            show: false
			        },
			        axisLine: {
			            show: false
			        },
			        z: 10
			    },
			    yAxis: {
			        axisLine: {
			            show: false
			        },
			        axisTick: {
			            show: false
			        },
			        axisLabel: {
			            textStyle: {
			                color: '#999'
			            }
			        }
			    },
			    dataZoom: [
			        {
			            type: 'inside'
			        }
			    ],
			    series: [
			        {
			            type: 'bar',
			            itemStyle: {
			                normal: {
			                	//柱形图圆角，初始化效果
	                            barBorderRadius:[5, 5, 5, 5],
	                            label: {
	                                show: true,//是否展示
	                                textStyle: {
	                                    fontWeight:'normal',
	                                    fontSize : '12',
	                                    fontFamily : '微软雅黑',
	                                }
	                            },
			                    color: new echarts.graphic.LinearGradient(
			                        0, 0, 0, 1,
			                        [
			                            {offset: 0, color: '#83bff6'},
			                            {offset: 0.5, color: '#188df0'},
			                            {offset: 1, color: '#188df0'}
			                        ]
			                    )
			                },
			                emphasis: {
			                	barBorderRadius: 5,
			                    color: new echarts.graphic.LinearGradient(
			                        0, 0, 0, 1,
			                        [
			                            {offset: 0, color: '#2378f7'},
			                            {offset: 0.7, color: '#2378f7'},
			                            {offset: 1, color: '#83bff6'}
			                        ]
			                    )
			                }
			            },
			            barWidth : 20,//柱图宽度
			            data: yvalue
			        }
			    ]
			};
		 	chart.setOption(option,true);   
		}
	}); 
};
//折线图
function lineChart(chart,time,color1,color2,obj){
	$.ajax({
		type : 'post',
		url : 'businessAction!dayItemCountChange.action?'+time+'&rand='+new Date().getTime(),
		dataType : 'json',
		cache : false,
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var dataList=data.result;
			if(dataList.length==0){
				obj.removeClass('base_hidden');
				obj.siblings().addClass('base_hidden');
			}
			obj.next().find('.chartCount').text(data.sum);
			var xlabel = [],yvalue=[];
			$(dataList).each(function(i,item){
				xlabel.push(dataList[i].time.substring(5,10));
				yvalue.push(dataList[i].itemCount);
			}); 
			//折线图
			var option = {
		        tooltip : {
		            trigger: 'axis',
		            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		                type : 'none' ,
		                lineStyle:{
		                	type:'dotted'
		                }
		            },formatter:function(params){
		                var relVal = params[0].axisValue+"<br/>";
		                relVal += params[0].seriesName+ ' : ' + params[0].data+"<br/>";
		                return relVal;
	                }, position:[3,3]
		        },
		         toolbox: { //可视化的工具箱
	               	 show: true,
	               	 feature: {
	                    restore: { //重置
	                        show: true
	                    },
	                    magicType: {//动态类型切换
	                        type: ['bar', 'line']
	                    }
	                }
	            },
		        grid: {
		        	x:'0',
		        	y:'100',
		            x2:'0',
		            y2:'0',
		            borderWidth:0
		        },
		        xAxis : [{
	            	type : 'category',
	                data : xlabel,
	                axisLabel: {
	                	inside: true,
	                    textStyle: {
	                        color: '#fff',
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
	            }],
		        yAxis : [{
	            	show:false,
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
	                	show:true,
	                	lineStyle: {
	                        type: 'solid',
	                        color: 'rgb(242,242,242)',//左边线的颜色
	                        width:'1'//坐标线的宽度
	                    }
	                }
	            }],
		        series : [{
		            name:'个数',
		            type:'line',
		         	smooth:true,
		         	symbolSize:4,
			        sampling: 'average',
			        itemStyle: {
			            normal: {
			                color: color1
			            }
			        },
			        areaStyle: {
			            normal: {
			                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
			                    offset: 0,
			                    color: color1
			                },{
			                    offset: 1,
			                    color: color2
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
//饼图
function pieChart(chart,obj,type){
	$.ajax({
		type : 'post',
		url : 'businessAction!deptItemCountByDay.action?rand='+new Date().getTime()+'&days=' +type ,
		dataType : 'json',
		cache : false,
		async : true,
		error : function(request, textStatus, errorThrown) {
		},
		success : function(data) {
			var dataList=data.result;
			if(dataList.length==0){
				obj.removeClass('base_hidden');
				obj.next().addClass('base_hidden');
			}
			var xlabel = [];
			var yvalue=[];
			$(dataList).each(function(i,item){
				xlabel.push(dataList[i].departmentName);
				item = {};
				item.name = dataList[i].departmentName;
				item.value = dataList[i].itemCount;
				yvalue.push(item);
			}); 
			//饼图
			var option = {
				tooltip : {
					show:false,
			        trigger: 'item',
			        formatter: "{a}{b} : {c}个 ({d}%)"
			    },
			    legend:{
				    width:5,
				    height:5,
				    left:10,
				},
			    toolbox: {//自定义工具栏
				    padding:10,
				    show : true,
				    right: 40,
				    feature : {
				    	myTool1: {  
			                show: true,  
			                title: '选择时间',  
			                icon: 'image://http://echarts.baidu.com/images/favicon.png',  
			                onclick: function (){  
			                   $("#choosePieDay").fadeToggle(500)
			                }
		                }
				    }
				},
			    color: [
		              "#ED4740",
		              "#336699",
		              "#FF9900",
		              "#CCFF66",
		              "#FFFF66",
		              "#3366CC",
		              "#0099CC",
		              "#CCCCFF"
		        
	              ],
			    series : [{
			    	 name:'',
			    	 label: {
		                normal: {
		                    show: true,
		                    position: 'center',
		                    textStyle: {
		                        fontSize: '13',
		                        fontWeight: 'bold'
		                    },
		                    formatter: "{a}{b} :\n {c}个({d}%) "
		                },
		                /*emphasis: {
		                	color: '#000',
		                    show: false,
		                    position:'outer',
		                    textStyle: {
		                        fontSize: '13'
		                        //fontWeight: 'bold'
		                    },
		                    formatter: "{a}{b} :\n {c}个({d}%) "
		                }*/
		            },
			    	 itemStyle : {
		                normal : {
		                    label : {
		                    	show: true,
		                        formatter: '{d}%'//多值的嵌套
		                    },
		                    labelLine : {
		                        show : true
		                    }
		                },
		                emphasis: {
		                    show: true,
		                    position:'center',
		                    textStyle: {
		                        fontSize: '20',
		                        fontWeight: 'bold'
		                    },
		                    formatter: "{a}{b} :\n {c}个 "
		                }
		            },
		            type:'pie',
		            radius: ['60%', '80%'],
		            center: ['50%', '50%'],
		            data: yvalue}]
			    };
			chart.setOption(option,true); 
		}
	}); 
};
$(".mybtn").on("click",function(){
	var type = $(this).attr("name");
	if(type == -7){
		$("#pieChartTitle").text('前七天办件量分布');
	}else if(type == -30){
		$("#pieChartTitle").text('前三十天办件量分布');
	}
	else if(type == 7){
		$("#pieChartTitle").text('本周办件量分布');
	}
	else if(type == 30){
		$("#pieChartTitle").text('本月办件量分布');
	}
	pieChart(myChart2,$('#noData2'),type);
	
})
window.onresize = function(){
	/*echarts 图表自适应调整*/
	 myChart1.resize();
 	 myChart2.resize();
 	 myChart3.resize();
	 myChart4.resize();
}