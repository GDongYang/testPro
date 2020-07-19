<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">.b1{white-space-collapsing:preserve;}
        .b2{margin: 1.0in 1.25in 1.0in 1.25in;}
        .p1{margin-right:0.41666666in;text-align:justify;hyphenate:auto;font-family:仿宋_GB2312;font-size:10pt;}
        .p2{text-align:center;hyphenate:auto;font-family:黑体;font-size:22pt;}
        .p3{text-align:center;hyphenate:auto;font-family:Calibri;font-size:14pt;}
        .p4{text-align:justify;hyphenate:auto;font-family:Calibri;font-size:14pt;}
        .p5{text-align:end;hyphenate:auto;font-family:Calibri;font-size:14pt;}
        .p6{text-indent:4.6666665in;text-align:justify;hyphenate:auto;font-family:Calibri;font-size:14pt;}
        .p7{text-align:justify;hyphenate:auto;font-family:仿宋_GB2312;font-size:16pt;}
        .s1{font-weight:bold;}
        .td1{width:1.96875in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td2{width:4.2916665in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td3{width:1.5416666in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td4{width:1.0104166in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td5{width:1.7395834in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td6{width:6.2604165in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .r1{height:0.20833333in;keep-together:always;}
        .r2{height:0.3611111in;keep-together:always;}
        .r3{height:0.35069445in;keep-together:always;}
        .r4{height:0.3125in;keep-together:always;}
        .r5{height:0.44791666in;keep-together:always;}
        .r6{height:0.7395833in;keep-together:always;}
        .r7{height:0.7916667in;keep-together:always;}
        .r8{height:0.7291667in;keep-together:always;}
        .r9{height:1.0201389in;keep-together:always;}
        .r10{height:0.98541665in;keep-together:always;}
        .t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}</style>
    <meta content="lenovo" name="author"/>
</head>
<body class="b1 b2">
<p class="p1"/>
<p class="p2">
    <span class="s1">砍伐城市树木、迁移古树名木审批申请表</span>
</p>
<p class="p3">
    <span/>
</p>
<table class="t1">
    <tbody>
    <tr class="r1">
        <td class="td1">
            <p class="p3">
                <span>申请单位</span>
            </p>
        </td>
        <td class="td2" colspan="3">
            ${APPLY_NAME!DefaultValue}
        </td>
    </tr>
    <tr class="r2">
        <td class="td1">
            <p class="p3">
                <span>联 系 人</span>
            </p>
        </td>
        <td class="td3">
            ${lxr!DefaultValue}
        </td>
        <td class="td4">
            <p class="p3">
                <span>电    话</span>
            </p>
        </td>
        <td class="td5">
            ${APPLY_MOBILE!DefaultValue}
        </td>
    </tr>
    <tr class="r3">
        <td class="td1">
            <p class="p3">
                <span>树木座落、编号</span>
            </p>
            <p class="p3">
                <span>（等级）</span>
            </p>
        </td>
        <td class="td3">
            ${smzlbh!DefaultValue}
        </td>
        <td class="td4">
            <p class="p3">
                <span>树木品种</span>
            </p>
        </td>
        <td class="td5">
            ${smpz!DefaultValue}
        </td>
    </tr>
    <tr class="r4">
        <td class="td1">
            <p class="p3">
                <span>树木数量</span>
            </p>
        </td>
        <td class="td3">
            ${smsl!DefaultValue}
        </td>
        <td class="td4">
            <p class="p3">
                <span>树木规格</span>
            </p>
        </td>
        <td class="td5">
            ${smgg!DefaultValue}
        </td>
    </tr>
    <tr class="r4">
        <td class="td1">
            <p class="p3">
                <span>砍伐性质</span>
            </p>
        </td>
        <td class="td3">
            ${kfxz!DefaultValue}
        </td>
        <td class="td4">
            <p class="p3">
                <span>移植去向</span>
            </p>
        </td>
        <td class="td5">
            ${yzqx!DefaultValue}
        </td>
    </tr>
    <tr class="r4">
        <td class="td1">
            <p class="p3">
                <span>施工单位</span>
            </p>
        </td>
        <td class="td3">
            ${sgdw!DefaultValue}
        </td>
        <td class="td4">
            <p class="p3">
                <span>施工时间</span>
            </p>
        </td>
        <td class="td5">
            ${sgsj!DefaultValue}
        </td>
    </tr>
    <tr class="r5">
        <td class="td6" colspan="4">
            <p class="p4">
                <span>申请原因：</span>
            </p>
            ${sqyy!DefaultValue}
            <p class="p5">
                <span>申请单位盖章</span>
            </p>
            <p class="p6">
                <span>${yb!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r6">
        <td class="td6" colspan="4">
            <p class="p4">
                <span>申请人提供的材料目录（仅迁移古树名木需要）：${yb!DefaultValue}</span>
            </p>
            <p class="p5">
                <span>申请单位盖章</span>
            </p>
            <p class="p6">
                <span>${yb!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r7">
        <td class="td6" colspan="4">
            <p class="p4">
                <span>古树名木管护单位意见（仅迁移古树名木需要）：${yb!DefaultValue}</span>
            </p>
            <p class="p4"/>
            <p class="p6">
                <span>${yb!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r8">
        <td class="td6" colspan="4">
            <p class="p4">
                <span>县级以上园林绿化管理机构意见：${yb!DefaultValue}</span>
            </p>
            <p class="p6"/>
            <p class="p6"/>
            <p class="p6">
                <span>${yb!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r9">
        <td class="td6" colspan="4">
            <p class="p4">
                <span>县级以上建设（园林）行政主管部门意见：${yb!DefaultValue}</span>
            </p>
            <p class="p4"/>
            <p class="p6">
                <span>${yb!DefaultValue}</span>
            </p>
        </td>
    </tr>
    <tr class="r10">
        <td class="td6" colspan="4">
            <p class="p4">
                <span>县级以上人民政府意见（仅迁移古树名木需要）：${yb!DefaultValue}</span>
            </p>
            <p class="p4"/>
            <p class="p6">
                <span>${yb!DefaultValue}</span>
            </p>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>