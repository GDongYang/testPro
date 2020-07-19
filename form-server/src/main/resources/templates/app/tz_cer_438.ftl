<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">.b1{white-space-collapsing:preserve;}
        .b2{margin: 1.0in 1.25in 1.0in 1.25in;}
        .s1{font-weight:bold;color:black;}
        .s2{color:black;background-color:yellow;}
        .s3{color:black;}
        .p1{text-align:center;hyphenate:auto;font-family:宋体;font-size:18pt;}
        .p2{text-align:center;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p3{text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p4{text-indent:0.29166666in;text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p5{text-indent:0.21875in;text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p6{text-indent:0.7291667in;text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p7{text-indent:1.3125in;text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p8{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
        .td1{width:1.5111111in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td2{width:2.1854167in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td3{width:1.0458333in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td4{width:1.71875in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td5{width:4.95in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td6{width:2.0097222in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td7{width:1.25in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td8{width:1.6902778in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .r1{height:0.44444445in;}
        .r2{height:0.5534722in;}
        .r3{height:0.45208332in;}
        .r4{height:0.44027779in;}
        .r5{height:0.4486111in;}
        .r6{height:0.51319444in;}
        .r7{height:0.9166667in;}
        .r8{height:1.0243056in;}
        .t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}</style>
    <meta content="oo" name="author"/>
</head>
<body class="b1 b2">
<p class="p1">
    <span class="s1">计量标准封存（或撤销）申报表</span>
</p>
<table class="t1">
    <tbody>
    <tr>
        <td class="td1">
            <p class="p2">
                <span class="s2">计量标准名称</span>
            </p>
        </td>
        <td class="td2" colspan="2">
            ${jlbzmc!DefaultValue}
        </td>
        <td class="td3">
            <p class="p2">
                <span class="s2">代码</span>
            </p>
        </td>
        <td class="td4" colspan="2">
            ${test!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p2">
                <span class="s2">测量范围</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            ${clfw!DefaultValue}
        </td>
    </tr>
    <tr class="r2">
        <td class="td1">
            <p class="p2">
                <span class="s2">不确定度或准确度</span>
            </p>
            <p class="p2">
                <span class="s2">等级或最大允许误差</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            ${bqddhzqd!DefaultValue}
        </td>
    </tr>
    <tr class="r3">
        <td class="td1">
            <p class="p2">
                <span class="s2">计量标准</span>
            </p>
            <p class="p2">
                <span class="s2">考核证书号</span>
            </p>
        </td>
        <td class="td6">
            ${jlbzkhzsh!DefaultValue}
        </td>
        <td class="td7" colspan="3">
            <p class="p2">
                <span class="s2">计量标准</span>
            </p>
            <p class="p2">
                <span class="s2">考核证书有效期</span>
            </p>
        </td>
        <td class="td8">
            ${jlbzkhzsyxq!DefaultValue}
        </td>
    </tr>
    <tr class="r4">
        <td class="td1">
            <p class="p2">
                <span class="s2">申请类型</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            <p class="p3">
                <span class="s2">${sqlx!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r5">
        <td class="td1">
            <p class="p2">
                <span class="s2">封存或撤销原因</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            ${fchcxyy!DefaultValue}
        </td>
    </tr>
    <tr class="r6">
        <td class="td1">
            <p class="p5">
                <span class="s2">申请停用时间</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            <p class="p6">
                <span class="s2">年     月     日 &amp;mdash;&amp;mdash;         年     月     日</span>
            </p>
        </td>
    </tr>
    <tr class="r7">
        <td class="td1">
            <p class="p2">
                <span class="s2">建标单位意见</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            <p class="p3"/>
            <p class="p7"/>
            <p class="p2">
                <span class="s2">负责人签字：              (公章)</span>
            </p>
            <p class="p2">
                <span class="s2">年    月    日</span>
            </p>
        </td>
    </tr>
    <tr class="r8">
        <td class="td1">
            <p class="p2">
                <span class="s3">建标单位</span>
            </p>
            <p class="p2">
                <span class="s3">主管部门意见</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            <p class="p2"/>
            <p class="p2"/>
            <p class="p2"/>
            <p class="p3">
                <span class="s3">(公章)</span>
            </p>
            <p class="p3">
                <span class="s3">年    月    日</span>
            </p>
        </td>
    </tr>
    <tr class="r7">
        <td class="td1">
            <p class="p2">
                <span class="s3">主持考核的人民政府计量行政部门意见</span>
            </p>
        </td>
        <td class="td5" colspan="5">
            <p class="p2"/>
            <p class="p2"/>
            <p class="p2"/>
            <p class="p3">
                <span class="s3">(公章)</span>
            </p>
            <p class="p3">
                <span class="s3">年    月    日</span>
            </p>
        </td>
    </tr>
    </tbody>
</table>
<p class="p8"/>
</body>
</html>