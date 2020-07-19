<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>出入境证件查询结果</title>
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
.text8{font-size:12px;}
</style>

</head>

<#escape x as "<span class='freemark'>"+x?string+"</span>">
<body class="lwl-prite-wrap">
	<div class="list-header mb20">
	    <h2 class="list-title">出入境证件查询结果</h2>
		<br/>
	</div>
	<table class="table table-border fsz-14 table-th-c mt5" id="ftlTable">
        <tr>
            <th colspan="6" class="text8">被查询人姓名</th>
            <th colspan="6" class="text8">身份证号</th>
        </tr>
        <tr>
            <td colspan="6" class="text8">${cerName!DefaultValue}</td>
            <td colspan="6" class="text8">${cerNo!DefaultValue}</td>
        </tr>
		<tr>
			<td colspan="12" style="text-align:left" class="text8">
					<span style='font-family: 宋体'>经查询，结果如下：</span>
			</td>
		</tr>
		<tr>
            <th width="15%" class="text8">护照类型</th>
            <th width="6%" class="text8">护照号码</th>
            <th width="8%" class="text8">中文名</th>
            <th width="15%" class="text8">英文名</th>
            <th width="5%" class="text8">出生日期</th>
            <th width="5%" class="text8">出生地</th>
            <th width="5%" class="text8">性别</th>
            <th width="10%" class="text8">民族</th>
            <th width="10%" class="text8">证件号码</th>
            <th width="21%" class="text8">国籍</th>
            <th width="5%" class="text8">出境原因</th>
            <th width="5%" class="text8">证件有效期</th>
            <th width="5%" class="text8">归集时间</th>
            <th width="5%" class="text8">证件签发日期</th>
            <th width="5%" class="text8">审批机关</th>
            <th width="5%" class="text8">是否过期</th>
        </tr>
        <#if datas??>
        	<#list datas as item>
	            <tr>
	                <td class="text8">${(item.ZJZLZW)!DefaultValue}</td>
	                <td class="text8">${(item.ZJHM)!DefaultValue}</td>
	                <td class="text8">${(item.ZWXM)!DefaultValue}</td>
	                <td class="text8">${(item.YWXM)!DefaultValue}</td>
	                <td class="text8">${(item.CSRQ)!DefaultValue}</td>
	                <td class="text8">${(item.CSD)!DefaultValue}</td>
	                <td class="text8">${(item.XB)!DefaultValue}</td>
	                <td class="text8">${(item.MZ)!DefaultValue}</td>
	                <td class="text8">${(item.SFZH)!DefaultValue}</td>
	                <td class="text8">${(item.GJDQ)!DefaultValue}</td>
	                <td class="text8">${(item.CJYY)!DefaultValue}</td>
	                <td class="text8">${(item.ZJYXQ)!DefaultValue}</td>
	                <td class="text8">${(item.tong_time)!DefaultValue}</td>
	                <td class="text8">${(item.zjqfrq)!DefaultValue}</td>
	                <td class="text8">${(item.spjg)!DefaultValue}</td>
	                <td class="text8">${(item.sfgq)!DefaultValue}</td>
	                
	            </tr>
            </#list>
        </#if>
		<tr style='height: 13.8pt'>
			<td colspan="12" style="text-align:left" class="text8"><p >
					<span style='font-family: 宋体'>说明：</span>
				</p>
				<p>
					<span>1、<span
						style='font: 7.0pt "Times New Roman"'>&nbsp; </span></span><span
						style='font-family: 宋体'>如提供的身份证号码与登记系统中的记录不一致导致无法查询的不在此查询范围内。</span>
				</p>
				<p >
					<span >2、<span
						style='font: 7.0pt "Times New Roman"'>&nbsp; </span></span><span
						style='font-family: 宋体'>若查询结果与出入境证件记载不一致的，以出入境证件为准。当事人如有异议，请到出入境管理中心窗口进一步核查。</span>
				</p>
				<p >
					<span >3、<span
						style='font: 7.0pt "Times New Roman"'>&nbsp; </span></span><span
						style='font-family: 宋体'>以上查询结果依申请仅用于查询申请人参考使用（不得用于其他用途）。查询申请人对查询结果中涉及个人隐私、商业秘密的信息负有保密义务，不得泄露给他人，不得用于非法途径。</span>
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
