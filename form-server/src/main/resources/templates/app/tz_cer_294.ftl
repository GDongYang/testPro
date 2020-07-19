<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">.b1{white-space-collapsing:preserve;}
        .b2{margin: 1.0in 1.25in 1.0in 1.25in;}
        .p1{text-align:start;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p2{text-align:justify;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p3{text-align:center;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p4{text-align:end;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p5{margin-top:0.2361111in;margin-bottom:0.22916667in;text-align:center;hyphenate:auto;keep-together.within-page:always;keep-with-next.within-page:always;font-family:Calibri;font-size:22pt;}
        .td1{width:0.9736111in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td2{width:0.95763886in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td3{width:4.5659723in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td4{width:5.523611in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td5{width:2.2409723in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td6{width:0.8840278in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td7{width:2.398611in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td8{width:3.3465278in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td9{width:3.1506944in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .r1{height:0.14027777in;keep-together:always;}
        .t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}
        .s1{font-weight:bold;}</style>
    <meta content="DELL" name="author"/>
</head>
<body class="b1 b2">
<p class="p5">
    <span class="s1">烟草专卖零售许可证管理类事项申请表</span>
</p>
<table class="t1">
    <tbody>
    <tr class="r1">
        <td class="td1" rowspan="4">
            <p class="p1">
                <span>申请类型</span>
            </p>
        </td>
        <td class="td2" rowspan="4">
            ${ycj_sqlx2!DefaultValue}
        </td>
        <td class="td3" colspan="4">
            <p class="p1">
                <span>事由：${ycj_sy!DefaultValue}</span>
            </p>
            <p class="p1">
                <span>申请停业期限：自 ${Csmc!DefaultValue}  至  ${Csmc!DefaultValue}       日</span>
            </p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td3" colspan="4">
            <p class="p1">
                <span>原停业期限：自 ${Csmc!DefaultValue}    至     ${Csmc!DefaultValue}     日</span>
            </p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td3" colspan="4">
            <p class="p1">
                <span>□正本   □副本</span>
            </p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td3" colspan="4">
            <p class="p1">
                <span>事由：${Csmc!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>许可证号</span>
            </p>
        </td>
        <td class="td4" colspan="5">
            ${ycj_xkzh!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>联系人</span>
            </p>
        </td>
        <td class="td5" colspan="2">
            ${ycj_lxr!DefaultValue}
        </td>
        <td class="td6" colspan="2">
            <p class="p2">
                <span>联系电话</span>
            </p>
        </td>
        <td class="td7">
            ${ycj_lxdh!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1" rowspan="2">
            <p class="p3">
                <span>文书</span>
            </p>
            <p class="p3">
                <span>送达方式</span>
            </p>
        </td>
        <td class="td4" colspan="5">
            <p class="p2">
                <span>□邮寄送达。邮寄地址：${Csmc!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td4" colspan="5">
            <p class="p2">
                <span>□直接送达       □其他</span>
            </p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td8" colspan="4">
            <p class="p2">
                <span>申请人</span>
            </p>
            ${ycj_sqr!DefaultValue}
            <p class="p4">
                <span>（签名或印章）</span>
            </p>
            <p class="p4">
                <span>年    月    日</span>
            </p>
        </td>
        <td class="td9" colspan="2">
            <p class="p2">
                <span>代理人</span>
            </p>
            ${Csmc!DefaultValue}
            <p class="p4">
                <span>（签名或印章）</span>
            </p>
            <p class="p4">
                <span>年   月   日</span>
            </p>
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>