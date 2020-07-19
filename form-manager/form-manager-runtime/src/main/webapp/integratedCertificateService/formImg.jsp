<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <style type="text/css">
 </style>
 <title>表单图片查看</title>
 </head>
 <style>
 	.imgBtn{
 	   width:100%;
 	   height:50px;
 	   padding-left:20px;
 	}
 	.imgBtn button{
 		display:inline-block;
 		width:100px;
 		height:32px;
 		font-size:16px;
 		text-align:center;
 		border:none;
 		border-radius:4px;
 	}
 	.imgBtn button:focus{
 		outline:none;
 	}
 	.active{
 		border:none;
 		border-radius:4px;
 		background:#0072FF;
 		color:#fff;
 	}
 </style>
 <body>
	<div id="container" class="div"> 
		<div class="imgBtn">
			<button type="button" class="active" data-value ='appImage'>浙里办</button>
			<button type="button" data-value ='onlineImage'>政务服务网</button>
			<button type="button" data-value ='offlineImage'>一窗受理</button>
		</div>
		<div class="imgWrap">
		
		</div>
     <img id="certImg" src="" />
	</div>
	<script type="text/javascript" src='../jquery/3.2.1/jquery-3.2.1.min.js'></script>
	<script type="text/javascript">
		var html = '';
		var certFile='';
		var imgData;
		$(document).ready(function(){
			getImgData(${param.id});
		});
		function getImgData(idData){
			$.ajax({
				type : 'post',
				url : 'formPageAction!findImagesById.action',
				dataType : 'json',
				cache : false,
				async : true,
				data : {'id': idData},
				error : function(request, textStatus, errorThrown) {
				},
				success : function(data) {
					if(data != null){
						imgData = data;
						$(".imgBtn button").each(function(){
							if($(this).hasClass('active')){
								certFile = imgData[$(this).attr('data-value')];
								html = '<img src="data:image/png;base64,'+certFile+'" />'
								$('.imgWrap').html(html);
							}
							$(this).click(function(){ 
								if(!$(this).hasClass('active')){
									$(this).siblings().removeClass('active');
									$(this).addClass('active');
									certFile = imgData[$(this).attr('data-value')];
								}
								html = '<img src="data:image/png;base64,'+certFile+'" />'				
								$('.imgWrap').html(html);
							})
						});
					}
				}
			});
		}		
	</script>
 </body>
</html>