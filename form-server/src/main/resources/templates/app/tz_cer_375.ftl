<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">.b1{white-space-collapsing:preserve;}
        .b2{margin: 1.0in 1.25in 1.0in 1.25in;}
        .s1{font-weight:bold;}
        .p1{text-align:center;hyphenate:auto;font-family:宋体;font-size:16pt;}
        .p2{text-align:center;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p3{text-align:start;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p4{text-align:end;hyphenate:auto;font-family:宋体;font-size:10pt;}
        .p5{text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:22pt;}
        .p6{text-align:start;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
        .td1{width:1.0923611in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td2{width:1.6680555in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td3{width:0.625in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td4{width:0.75in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td5{width:0.5in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td6{width:1.15in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td7{width:2.4in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td8{width:4.6930556in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td9{width:0.44791666in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td10{width:2.3611112in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td11{width:0.45833334in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td12{width:2.5180554in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .r1{height:0.32291666in;}
        .r2{height:0.3125in;}
        .r3{height:0.31388888in;keep-together:always;}
        .r4{height:0.4625in;keep-together:always;}
        .r5{height:0.40625in;keep-together:always;}
        .r6{height:0.36527777in;keep-together:always;}
        .r7{height:1.3645834in;keep-together:always;}
        .r8{height:1.1055555in;keep-together:always;}
        .r9{height:1.4479166in;keep-together:always;}
        .t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}</style>
    <meta content="DELL" name="author"/>
</head>
<body class="b1 b2">
<p class="p1">
    <span class="s1">挖掘城市道路审批申请表</span>
</p>
<table class="t1">
    <tbody>
    <tr class="r1">
        <td class="td1" colspan="2" rowspan="2">
            <p class="p2">
                <span>申请单位</span>
            </p>
        </td>
        <td class="td2" rowspan="2">
            ${APPLY_NAME!DefaultValue}
        </td>
        <td class="td3" colspan="3">
            <p class="p2">
                <span>联系人</span>
            </p>
        </td>
        <td class="td4">
            ${WJ_LXR!DefaultValue}
        </td>
        <td class="td5">
            <p class="p2">
                <span>电话</span>
            </p>
        </td>
        <td class="td6">
            ${APPLY_MOBILE!DefaultValue}
        </td>
    </tr>
    <tr class="r2">
        <td class="td3" colspan="3">
            <p class="p2">
                <span>地址</span>
            </p>
        </td>
        <td class="td7" colspan="3">
            ${WJ_DZ!DefaultValue}
        </td>
    </tr>
    <tr class="r3">
        <td class="td1" colspan="2">
            <p class="p2">
                <span>施工单位</span>
            </p>
        </td>
        <td class="td2">
            ${WJ_SGDW!DefaultValue}
        </td>
        <td class="td3" colspan="3">
            <p class="p2">
                <span>负责人</span>
            </p>
        </td>
        <td class="td4">
            ${WJ_SGDWLXR!DefaultValue}
        </td>
        <td class="td5">
            <p class="p2">
                <span>电话</span>
            </p>
        </td>
        <td class="td6">
            ${WJ_SGDWLXRDH!DefaultValue}
        </td>
    </tr>
    <tr class="r4">
        <td class="td1" colspan="2">
            <p class="p2">
                <span>挖掘地点</span>
            </p>
        </td>
        <td class="td8" colspan="7">
            ${WJ_WJDD!DefaultValue}
        </td>
    </tr>
    <tr class="r5">
        <td class="td1" colspan="2">
            <p class="p2">
                <span>挖掘道路种类及面积</span>
            </p>
        </td>
        <td class="td8" colspan="7">
            <p class="p3">
                <span>人行道（路面材料：    ）  长   米，宽  米，面积     平方米。</span>
            </p>
            <p class="p3">
                <span>车行道（路面材料：    ）   长   米，宽  米，面积     平方米。</span>
            </p>
        </td>
    </tr>
    <tr class="r5">
        <td class="td1" colspan="2">
            <p class="p2">
                <span>申请挖掘时间</span>
            </p>
        </td>
        <td class="td8" colspan="7">
            <p class="p2">
                <span>年      月       日起至     年      月      日止</span>
            </p>
        </td>
    </tr>
    <tr class="r6">
        <td class="td1" colspan="2">
            <p class="p3">
                <span>影响交通市政设施情况</span>
            </p>
        </td>
        <td class="td8" colspan="7">
            <p class="p3"/>
        </td>
    </tr>
    <tr class="r7">
        <td class="td1" colspan="2">
            <p class="p2">
                <span>申</span>
            </p>
            <p class="p2">
                <span>请</span>
            </p>
            <p class="p2">
                <span>挖</span>
            </p>
            <p class="p2">
                <span>掘</span>
            </p>
            <p class="p2">
                <span>理</span>
            </p>
            <p class="p2">
                <span>由</span>
            </p>
        </td>
        <td class="td8" colspan="7">
            <p class="p3"/>
            <p class="p3"/>
            <p class="p3">
                <span/>
            </p>
            <p class="p3"/>
            <p class="p4">
                <span>申请单位盖章     年     月    日</span>
            </p>
        </td>
    </tr>
    <tr class="r8">
        <td class="td1" colspan="2">
            <p class="p2">
                <span>示意图</span>
            </p>
        </td>
        <td class="td8" colspan="7">
            <p class="p2"/>
            <p class="p2"/>
            <p class="p2"/>
        </td>
    </tr>
    <tr class="r9">
        <td class="td9">
            <p class="p2">
                <span>市政工程管理</span>
            </p>
            <p class="p2">
                <span>部门</span>
            </p>
            <p class="p2">
                <span>意见</span>
            </p>
        </td>
        <td class="td10" colspan="3">
            <p class="p3"/>
            <p class="p3"/>
            <p class="p3"/>
            <p class="p3"/>
            <p class="p3"/>
            <p class="p4">
                <span>（盖章）                                                  年    月    日</span>
            </p>
        </td>
        <td class="td11">
            <p class="p2">
                <span>公安交通管理部门意见</span>
            </p>
        </td>
        <td class="td12" colspan="4">
            <p class="p4"/>
            <p class="p4"/>
            <p class="p4"/>
            <p class="p4"/>
            <p class="p4"/>
            <p class="p4">
                <span>（盖章）                                                  年    月    日</span>
            </p>
        </td>
    </tr>
    </tbody>
</table>
<p class="p5"/>
<p class="p6"/>
</body>
</html>