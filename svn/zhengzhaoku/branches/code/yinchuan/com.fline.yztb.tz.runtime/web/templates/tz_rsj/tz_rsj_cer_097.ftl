<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>医疗保险参保人员信息</title>
<style>
<#include "pdfstyle.css"> 
<#include "pdfcss.css"> 
.freemark {
	line-height: 20px;
}

p {
	line-height: 20px;
}
#ftlTable td{
	text-align:center
}
tr{
	height:40px
}
.text8{font-size:10px;}
</style>

</head>

<#escape x as "<span class='freemark'>"+x?string+"</span>">
<body class="lwl-prite-wrap">
	<div class="list-header mb20">
	    <h2 class="list-title">医疗保险参保人员信息查询结果</h2>
		<br/>
	</div>
	<table class="table table-border fsz-14 table-th-c mt5" id="ftlTable">
        <tr>
            <td colspan="1" class="text8">姓名</td>
            <td colspan="1" class="text8">${cerName!DefaultValue}</td>
            <td colspan="1" class="text8">身份证号</td>
            <td colspan="1" class="text8">${cerNo!DefaultValue}</td>
        </tr>
		<tr>
			<td colspan="4" style="text-align:left" class="text8">
					<span style='font-family: 宋体'>经查询，结果如下：</span>
			</td>
		</tr>
		<tr>
            <th width="33%" class="text8">参保状态</th>
            <th width="36%" class="text8">离退休状态</th>
            <th width="31%" class="text8">数据归集日期</th>
        </tr>
        <#if datas??>
        	<#list datas as item>
	            <tr>
	                <td class="text8">${(item.insuredStatus)!DefaultValue}</td>
	                <td class="text8">${(item.retiredStatus)!DefaultValue}</td>
	                <td class="text8">${(item.tong_time)!DefaultValue}</td>
	            </tr>
            </#list>
        </#if>
		<tr style='height: 13.8pt'>
			<td colspan="10" style="text-align:left" class="text8"><p >
					<span style='font-family: 宋体'>声明：</span>
				</p>
				<p>
					<span>1、<span
						style='font: 7.0pt "Times New Roman"'>&nbsp; </span></span><span
						style='font-family: 宋体'>该信息仅限用于办理${ApplyCause!DefaultValue}事项使用，当天有效；请严格保密，不得违法、违规对外泄露。</span>
				</p>
				<p >
					<span >2、<span
						style='font: 7.0pt "Times New Roman"'>&nbsp; </span></span><span
						style='font-family: 宋体'>当事人如有异议，请到证明所在窗口核查。</span>
				</p>
			</td>
		</tr>
	</table>

	<p style='text-align:right'>
		<span style='font-family: 宋体;text-align:right'>提供日期：${printDate!DefaultValue}</span>
		<br />
		<span style='color:white'>${signFlag!DefaultValue}</span>
	</p>
</body>
</#escape>
</html>
