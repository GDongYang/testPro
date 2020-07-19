<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">.b1{white-space-collapsing:preserve;}
        .b2{margin: 1.0in 1.25in 1.0in 1.25in;}
        .p1{text-align:justify;hyphenate:auto;font-family:Calibri;font-size:12pt;}
        .p2{margin-top:0.2361111in;margin-bottom:0.22916667in;text-align:center;hyphenate:auto;keep-together.within-page:always;keep-with-next.within-page:always;font-family:Calibri;font-size:22pt;}
        .p3{text-align:justify;hyphenate:auto;font-family:Calibri;font-size:10pt;}
        .td1{width:1.9513888in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td2{width:2.1666667in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td3{width:1.3611112in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td4{width:2.0069444in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .r1{keep-together:always;}
        .t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}
        .s1{font-weight:bold;}</style>
    <meta content="DELL" name="author"/>
</head>
<body class="b1 b2">
<p class="p2">
    <span class="s1">建筑起重机使用登记(使用)</span>
</p>
<table class="t1">
    <tbody>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>设备唯一号</span>
            </p>
        </td>
        <td class="td2">
            ${BaID!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>设备名称</span>
            </p>
        </td>
        <td class="td4">
            ${SBMC!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>备案编号</span>
            </p>
        </td>
        <td class="td2">
            ${xmmc!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>编号</span>
            </p>
        </td>
        <td class="td4">
            ${CQBH!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>工程名称</span>
            </p>
        </td>
        <td class="td2">
            ${ProName!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>施工许可证号</span>
            </p>
        </td>
        <td class="td4">
            ${ConsPermitNo!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>企业质安科长</span>
            </p>
        </td>
        <td class="td2">
            ${WeiBaoUnitMan!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>手机号码</span>
            </p>
        </td>
        <td class="td4">
            ${WeiBaoUnitManTel!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>使用日期</span>
            </p>
        </td>
        <td class="td2">
            ${UseRegDate!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>检测单位</span>
            </p>
        </td>
        <td class="td4">
            ${CheckUnitName!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>检测日期</span>
            </p>
        </td>
        <td class="td2">
            ${CheckDate!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>检测报告编号</span>
            </p>
        </td>
        <td class="td4">
            ${CheckReportNo!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>检测意见</span>
            </p>
        </td>
        <td class="td2">
            ${CheckContext!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>防坠器A编号</span>
            </p>
        </td>
        <td class="td4">
            ${AFallDetectNo!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>防坠器A检测日期</span>
            </p>
        </td>
        <td class="td2">
            ${ADetectDate!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>防坠器B编号</span>
            </p>
        </td>
        <td class="td4">
            ${BFallDetectNo!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>防坠器B检测日期</span>
            </p>
        </td>
        <td class="td2">
            ${BDetectDate!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>验收日期</span>
            </p>
        </td>
        <td class="td4">
            ${CheckDate1!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>验收意见</span>
            </p>
        </td>
        <td class="td2">
            ${CheckContext1!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>项目负责人</span>
            </p>
        </td>
        <td class="td4">
            ${ProManager!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>项目负责人手机号码</span>
            </p>
        </td>
        <td class="td2">
            ${ProManagerTel!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>安装编号</span>
            </p>
        </td>
        <td class="td4">
            ${InstallID!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>安装(拆卸)单位意见</span>
            </p>
        </td>
        <td class="td2">
            ${ApplyContent!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>日期</span>
            </p>
        </td>
        <td class="td4">
            ${ApplyDate!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>施工总承包单位审查意见</span>
            </p>
        </td>
        <td class="td2">
            ${xmmc!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>日期</span>
            </p>
        </td>
        <td class="td4">
            ${xmmc!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>使用单位意见</span>
            </p>
        </td>
        <td class="td2">
            ${xmmc!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>日期</span>
            </p>
        </td>
        <td class="td4">
            ${xmmc!DefaultValue}
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p1">
                <span>必须具备的资料</span>
            </p>
        </td>
        <td class="td2">
            ${ZL!DefaultValue}
        </td>
        <td class="td3">
            <p class="p1">
                <span>升降机单笼使用</span>
            </p>
        </td>
        <td class="td4">
            ${OnlyOne!DefaultValue}
        </td>
    </tr>
    </tbody>
</table>

<p class="p3"/>
</body>
</html>