<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>一证通办主页</title>
		<jsp:include page="css/PageletCSS.jsp" >
			<jsp:param value="" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="build/css/dashboard.css?v=2" />
	</head>
	<body class='base_padding-20 base_background-EEF6FB'>
		<div class='row'>
			<div class='col-sm-4 col-md-4 col-lg-4 textBox textBoxBg1'>
				<img src='build/image/1-1.png'/>
				<div class='textTitleBox'>
					<div class='base_text-size-22' id="cerNoCount"></div>
					<div class='base_text-size-16'>办件总量</div>
				</div>
			</div>
			<div class='col-sm-4 col-md-4 col-lg-4 textBox textBoxBg2'>
				<img src='build/image/2-1.png'/>
				<div class='textTitleBox'>
					<div class='base_text-size-22' id="tempCount"></div>
					<div class='base_text-size-16'>查询证件总量</div>
				</div>
			</div>
			<div class='col-sm-4 col-md-4 col-lg-4 textBox textBoxBg3'>
				<img src='build/image/3-1.png'/>
				<div class='textTitleBox'>
					<div class='base_text-size-22' id="itemCount"></div>
					<div class='base_text-size-16'>部署事项数量</div>
				</div>
			</div>
			<div class='col-sm-4 col-md-4 col-lg-4 textBox textBoxBg4'>
				<img src='build/image/4-1.png'/>
				<div class='textTitleBox'>
					<div class='base_text-size-22' id="certCount"></div>
					<div class='base_text-size-16'>证明模板数量</div>
				</div>
			</div>
			<div class='col-sm-4 col-md-4 col-lg-4 textBox textBoxBg5'>
				<img src='build/image/5-1.png'/>
				<div class='textTitleBox'>
					<div class='base_text-size-22' id="formCount"></div>
					<div class='base_text-size-16'>业务表单数量</div>
				</div>
			</div>
			<div class='col-sm-4 col-md-4 col-lg-4 textBox textBoxBg6'>
				<img src='build/image/6-1.png'/>
				<div class='textTitleBox'>
					<div class='base_text-size-22' id="caCount"></div>
					<div class='base_text-size-16'>个人CA数量</div>
				</div>
			</div>
			<div class='col-sm-12 col-md-8 col-lg-8'>
				<div class='chartTitle'>
					<img src='build/image/barIcon.png'/>民生事项部署情况
				</div>
				<div class='base_hidden noData' id='noData1'>暂无数据！</div>
				<div id="zhuzhuangbar" class="base_height-300 chartBox"></div>
			</div>
			<div class='col-sm-12 col-md-4 col-lg-4' >
				<div class='chartTitle' >
					<img src='build/image/pieIcon.png'/><span id="pieChartTitle">本周办件量分布</span>
					<div class='' id="choosePieDay" style="float:right" hidden>
						<button type="button" class="mybtn" name="-7">前7天</button>
						<button type="button" class="mybtn" name="-30">前30天</button>
						<button type="button" class="mybtn" name="7">本周</button>
						<button type="button" class="mybtn" name="30">本月</button>
					</div>
				</div>
				<div class='base_hidden noData' id='noData2'>暂无数据！</div>
				<div id="contentPie" class="base_height-300 chartBox"></div>
			</div>
			<div class='col-sm-12 col-md-12 col-lg-12 base_padding-0'>
				<div class='chartTitle base_padding-l-20'>
					<img src='build/image/lineIcon.png'/>办件量统计
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4" style='position:relative'>
					<div class='base_hidden noData' id='noData3'>暂无数据！</div>
					<div class='chartSmallTitle'>
						<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'>
						<img src='build/image/yellow.png' height='40'/>
						<div class='base_margin-tb-10'>本周办件量统计</div>
						<div class='chartTime'><span class='chartTimeSpan'></span>办件量</div>
						</div>
						<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 chartCount'></div>
					</div>
					<div id="zhexianBar1" class='base_height-250 chartBox'></div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<div class='base_hidden noData' id='noData4'>暂无数据！</div>
					<div class='chartSmallTitle'>
						<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'>
						<img src='build/image/blue.png' height='40'/>
						<div class='base_margin-tb-10'>本月办件量统计</div>
						<div class='chartTime'><span class='chartTimeSpan'></span>办件量</div>
						</div>
						<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 chartCount'></div>
					</div>
					<div id="zhexianBar2" class='base_height-250 chartBox'></div>
				</div>
				<!--<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
					<div class='base_hidden noData' id='noData5'>暂无数据！</div>
					<div class='chartSmallTitle'>
						<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'>
						<img src='build/image/blue.png' height='40'/>
						<div class='base_margin-tb-10'>办件总量</div>
						<div class='chartTime'><span class='chartTimeSpan'></span>办件量</div>
						</div>
						<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6 chartCount'></div>
					</div>
					<div id="zhexianBar3" class='base_height-250 chartBox'></div>
				</div>-->
			</div>
			<div class='col-sm-12 col-md-6 col-lg-6'>
				<div class='chartTitle'>
					<img src='build/image/queenIcon.png'/>地区办件排名
					<form id="chooseForm" onsubmit="return false;" class="pull-right">
						<select name="type" id="deptDateSelect">
							<option value="1" class="selectColor">当月</option>
							<option value='0' class="selectColor">总量</option>
						</select>
						<!--<select name="city">
							<option value='0' class="selectColor">省本级</option>
							<option value='1' class="selectColor">地市</option>
						</select>-->
					</form>
				</div>
				<div class='base_height-500 chartBox base_padding-t-10'>
					<table class="base_viewTable">
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
			<div class='col-sm-12 col-md-6 col-lg-6'>
				<div class='chartTitle' style='position:relative;'>
					<img src='build/image/queenIcon.png'/>事项办件排名
					<div class="week" id="areaSelect">
						<ul>
							<li>
								<a href="javascript:void(0)" name="type=1" class="selectWeek">当月</a> 
							</li>
							<li class="base_width-1 bg-info"></li>
							<li>
								<a href="javascript:void(0)" name="type=0">总量</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class='col-sm-12 col-md-6 col-lg-6 chartBox'>
				<div class='base_height-500 base_padding-t-10'>
					<table class="base_viewTable">
						<thead>
						    <tr>
								<th>排名</th>
								<th>事项名称</th>
								<th>办件量</th>
							</tr>
						</thead>
						<tbody id="bodyData2">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<div style="display: none;">
           	<div id="showWrap" class="showWrap" >
			</div>
		</div>
		<jsp:include page="js/PageletJS.jsp" >
			<jsp:param value="echarts" name="p"/>
		</jsp:include>
		<script type="text/javascript" src='build/js/dashboard.js'></script>
	</body>
</html>