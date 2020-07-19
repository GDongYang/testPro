<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>杭州市一证通办数据共享系统</title>
		<link href="images/favicon.ico" mce_href="images/favicon.ico" rel="bookmark" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="icon" type="image/x-icon" /> 
        <link href="images/favicon.ico" mce_href="images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/dashboard3.css?v=2.4"/>
	</head>
	<body>
		<div class="base_dashboard-head text-center base_position-relative">
			<img src="images/logoTitle.png" height="50%"/>
			<span class="text_time">000-00-00 00:00:00</span>
			<a href="index.jsp" style="color: #ffffff;margin-right: 20px;font-size: 16px;"><span class="glyphicon glyphicon-log-out" style="font-size: 16px;"></span>返回</a>
		</div>
		<div class="row bgBlue base_padding-10 base_margin-0">
			<div class="base_width-percent-25">
				<div class="base_dataBlock-title">
					<img src="images/logo (1).png" height="20px"/>民生事项部署情况
				</div>
				<div id="zhuzhuangbar" class="base_height-215 base_margin-percent-t-6"></div>
			</div>
			<div class="base_width-percent-48 base_padding-t-10">
				<div class="titleBox">
					<div>办件总量</div><div id="cerNoCount"></div>
				</div>
				<div class="titleBox">
					<div>查询证件总量</div><div id="tempCount"></div>
				</div>
				<div class="titleBox">
					<div>部署事项数量</div><div id="itemCount"></div>
				</div>
				<div class="titleBox">
					<div>证明模板数量</div><div id="certCount"></div>
				</div>
				<div class="titleBox">
					<div>电子印章数量</div><div id="sealCount"></div>
				</div>
				<div class="titleBox">
					<div>个人CA数量</div><div id="caCount"></div>
				</div>
			</div>
			<div class="base_width-percent-25 base_margin-b-10">
				<div class="base_dataBlock-title">
					<img src="images/logo (3).png" height="20px"/>部门办件排名
					<form id="chooseForm" onsubmit="return false;" class="pull-right">
						<select name="type">
							<option value="1" class="selectColor">当月</option>
							<option value='0' class="selectColor">总量</option>
						</select>
						<select name="city">
							<option value='0' class="selectColor">市本级</option>
							<option value='1' class="selectColor">区县</option>
						</select>
					</form>
				</div>
				<div class="base_height-percent-80" style="overflow:auto">
					<table class="base_viewTable" style="min-width:100%;" id="dayShow">
						<thead>
						    <tr>
								<th>排名</th>
								<th>部门名称</th>
								<th>办件量</th>
							</tr>
						</thead>
						<tbody id="bodyData">
						</tbody>
					</table>
				</div>
			</div>
			<div class="base_width-percent-25">
				<div class="base_width-percent-100 base_data-block base_margin-b-10">
					<div class="base_dataBlock-title">
						<img src="images/logo (5).png" height="20px"/>今日办件量分布
					</div>
					<div id="contentPie" class="base_height-215 base_position-relative base_margin-percent-t-6"></div>
				</div>
			</div>
			<div class="base_width-percent-48">
				<div class="base_dataBlock-title">
					<img src="images/logo (2).png" height="20px"/>办件量统计
					<div class="pull-right week" id="weekSelect">
						<ul>
							<li>
								<a href="#" name="days=7" class="selectWeek">周</a> 
							</li>
							<li class="base_width-1 bg-info"></li>
							<li>
								<a href="#" name="days=30">月</a>
							</li>
							<li class="base_width-1 bg-info"></li>
							<li>
								<a href="#" name="days=-1">总量</a>
							</li>
						</ul>
					</div>
				</div>
				<div id="zhexianBar" class="base_height-200 base_position-relative base_margin-percent-t-6"></div>
			</div>
			<div class="base_width-percent-25">
				<div class="base_dataBlock-title">
					<img src="images/logo (4).png" height="20px"/>区县办件排名
					<div class="pull-right week" id="areaSelect">
						<ul>
							<li>
								<a href="#" name="type=1" class="selectWeek">当月</a> 
							</li>
							<li class="base_width-1 bg-info"></li>
							<li>
								<a href="#" name="type=0">总量</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="base_height-percent-80" style="overflow:auto">
					<table class="base_viewTable" style="min-width:100%;" id="dayShow">
						<thead>
						    <tr>
								<th>排名</th>
								<th>区/县名称</th>
								<th>办件量</th>
							</tr>
						</thead>
						<tbody id="bodyData2">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="cookie" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="<%=path %>/js/chartjs/echarts.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jquery.dataStatistics.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jquery.mousewheel.js"></script>
		<script type="text/javascript" src="<%=path %>/js/dashboard.js?v=3.2"></script>
		<script type="text/javascript">
			window.onload = function() {
				if(window.parent != null){
					var hp = window.parent.window.location.href;
					var hc = window.location.href;
					if(hp != hc){
						window.parent.window.location.href = hc+"?rand="+new Date().getTime();
					};
				};
				var windowHeight=$(window).height();
				$(".bgBlue").css("height",windowHeight-70);
			};
			var clid = 0;
			//路径配置  
			require.config({
				paths:{
					echarts: '<%=path %>/js/chartjs',
				}
			});
		</script>
	</body>
</html>