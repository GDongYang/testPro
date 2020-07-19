<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>台州市一证通办数据应用系统及无证明城市支撑平台</title>
		<link href="images/favicon.ico" mce_href="images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/datastatistic.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/dashboard2.css"/>
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua base_dashboard-body">
		<div class="wrapper base_dashboard-wrapper">
			<div class="base_dashboard-head base_margin-b-10">
				<h2 class="no-margin base_line-height-65" style="display:inline-block;"><img class="base_margin-lr-10 base_dashboard-logo" src="<%=path %>/images/logo2.png" style="height:40px;width:40px;"/>一证通办可视化服务平台</h2>
				<a href="index.jsp" class="pull-right" style="color: #ffffff;margin-right: 20px;font-size: 16px;"><span class="glyphicon glyphicon-log-out" style="font-size: 16px;"></span>返回</a>
			</div>
			<div class="base_width-percent-100 base_clear-left">
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
					<div class="base_width-percent-100 base_data-block base_margin-b-10">
						<div class="base_dataBlock-title">
							证件查询统计(每日)
						</div>
						<div id="zhuzhuangbar" class="base_dataBlock-body"></div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 base_dataBlock-padding">
					<div class="base_width-percent-100 base_data-block base_margin-b-10">
						<div class="title_bar" id="title_bar">
							<div class="base_width-percent-50 pull-left">
								<ul class="time_area-ul no-padding">
									<li class="base_dataBlock-title text-center">当前时间</li>
									<li class="text_count"><span class="text_time">000-00-00 00:00:00</span></li>
								</ul>
							</div>
							<div class="base_width-percent-50 pull-right">
								<ul class="time_area-ul no-padding">
									<li class="base_dataBlock-title text-center">累计服务事项请求:</li>
									<li class="text_count">
										<div class="dataStatistics pull-right">
											<div class="digit_set"></div>
											<div class="digit_set"></div>
											<div class="digit_set"></div>
											<div class="digit_set"></div>
											<div class="digit_set"></div>
											<div class="digit_set"></div>
											<div class="digit_set"></div>
											<div class="digit_set set_last"></div>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div class="base_viewTableExternum base_margin-lr-10 base_clear-left" style="height: 160px;overflow: auto;">
							<table class="base_viewTable" style="min-width:100%;" id="dayShow">
								<thead>
								    <tr>
										<th>部门</th>
										<th>服务次数</th>
										<th>证件查询次数</th>
									</tr>
								</thead>
								<tbody id="bodyData"></tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
					<div class="base_width-percent-100 base_data-block base_margin-b-10">
						<div class="base_dataBlock-title">
							业务请求服务统计
						</div>
						<div class="base_dataBlock-body">
							<div class="base_viewTableExternum base_margin-lr-10 base_clear-left">
								<table class="base_statementTabel base_serversCountTable base_text-size-14" id="certContent">
									<tr>
										<td class="base_line-height-20">累计服务身份证数量:<span id="certNoCount" class="countLabel"></span></td>
									</tr>
									<tr>
										<td class="base_line-height-20">累计查询证件数量:<span id="tempCount" class="countLabel"></span></td>
									</tr>
									<tr>
										<td class="base_line-height-20">平均每日请求服务次数:<span  id="dayTempCount" class="countLabel"></span></td>
									</tr>
									<tr>
										<td class="base_line-height-20">最多调用一证通平台业务部门</td>
									</tr>
									<tr>
										<td>
											<div class="departBgg">
												<div class="departBg" id="departMentName"></div>
											</div>
	                                    </td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="base_width-percent-100 base_clear-left">
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
					<div class="base_width-percent-100 base_data-block base_margin-b-10">
						<div class="base_dataBlock-title">
							业务请求按部门分布(每日)
						</div>
						<div id="contentPie" class="base_dataBlock-body"></div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 base_dataBlock-padding">
					<div class="base_width-percent-100 base_data-block base_margin-b-10">
						<div class="base_dataBlock-title">
							业务请求数量按周分布
							<div class="pull-right week">
								<ul>
									<li>
										<a href="#" name="select_1">上周</a> 
									</li>
									<li class="base_width-1 bg-info"></li>
									<li>
										<a href="#" name="select_0" class="selectWeek">本周</a>
									</li>
								</ul>
							</div>
						</div>
						<div id="zhexianBar" class="base_dataBlock-body"></div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
					<div class="base_width-percent-100 base_data-block base_margin-b-10">
						<div class="base_dataBlock-title">
							业务请求量
						</div>
						<div id="contentBar" class="base_dataBlock-body"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- ./wrapper -->
		
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,cookie" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="<%=path %>/js/chartjs/echarts.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jquery.dataStatistics.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jquery.mousewheel.js"></script>
		<script type="text/javascript" src="<%=path %>/js/dashboard1.js?v=3"></script>
		<script type="text/javascript">
			window.onload = function(){
				if(window.parent != null){
					var hp = window.parent.window.location.href;
					var hc = window.location.href;
					if(hp != hc){
						window.parent.window.location.href = hc+"?rand="+new Date().getTime();
					}
				}
			}
			var clid = 0;
			
			 window.onresize = function(){
				setResizeWindow();
				if(clid == 0){
					clid = setTimeout(function(){
						setResizeWindow();
						clearTimeout(clid);
						clid = 0;
				    }, 1000);
				} 
			} 
			function setResizeWindow(){
				$(".dashborad").css("height", $(window).height() );
				var minHeight = 190;
				var minMHeight = minHeight + 40;
				//左侧栏随窗口大小变动
				var left_height = ($(window).height() - 71 -80) / 2 ;
				if(parseInt(left_height) < (minHeight + 40)){
					left_height = minHeight + 40;
					}
				$("#leftTop").css("height",parseInt(left_height));
				$("#leftTop").css("minHeight",minHeight);
				$("#zhuzhuangbar").css("height",parseInt(left_height)-34);
				$("#zhuzhuangbar").css("minHeight",minHeight);
				$("#rightTop").css("height",parseInt(left_height));
				$("#rightTop").css("minHeight",minHeight);
				
				var center_height = left_height / 3;
				$("#title_bar").css("height",parseInt(center_height));
				$("#centerTop").css("height",parseInt(center_height * 2));
				
				$(window).resize(function(){
					if($(window).width()<992){	
						$("#title_bar").css("height","160");
						
					}
					$(".dashborad").css("height", $(window).height() );
					var minHeight = 190;
					var minMHeight = minHeight + 40;
					//左侧栏随窗口大小变动
					var left_height = ($(window).height() - 71 -80) / 2 ;
					if(parseInt(left_height) < (minHeight + 40)){
						left_height = minHeight + 40;
						}
					$("#leftTop").css("height",parseInt(left_height));
					$("#leftTop").css("minHeight",minHeight);
					$("#zhuzhuangbar").css("height",parseInt(left_height)-34);
					$("#zhuzhuangbar").css("minHeight",minHeight);
					$("#rightTop").css("height",parseInt(left_height));
					$("#rightTop").css("minHeight",minHeight);
					
					var center_height = left_height / 3;
					$("#title_bar").css("height",parseInt(center_height));
					$("#centerTop").css("height",parseInt(center_height * 2));
					
				});
				
			}
			
			//路径配置  
			require.config({  
				paths:{
					echarts: '<%=path %>/js/chartjs',
				}
			});
		</script>
	</body>
</html>