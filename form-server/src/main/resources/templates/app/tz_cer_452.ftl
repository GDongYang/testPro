<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">.b1{white-space-collapsing:preserve;}
        .b2{margin: 1.0in 1.25in 1.0in 1.25in;}
        .s1{font-weight:bold;}
        .p1{margin-top:0.2361111in;margin-bottom:0.22916667in;text-align:center;hyphenate:auto;keep-together.within-page:always;keep-with-next.within-page:always;font-family:Calibri;font-size:22pt;}
        .p2{text-align:center;hyphenate:auto;font-family:Calibri;font-size:14pt;}
        .td1{width:0.9791667in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .td2{width:2.8125in;padding-start:0.0in;padding-end:0.0in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
        .r1{keep-together:always;}
        .t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;}</style>
</head>
<body class="b1 b2">
<p class="p1">
    <span class="s1">${formName!DefaultValue}</span>
</p>
<table class="t1">
    <tbody>
    <#list datas as data>
        <#if data_index%2 == 0><tr class="r1"></#if>
        <td class="td1">
            <p class="p2">
                <span>${data.name_cn!DefaultValue}</span>
            </p>
        </td>
        <td class="td2">
            <p class="p2">${data.value!DefaultValue}</p>
        </td>
        <#if data_index%2 == 1></tr></#if>
    </#list>
    <#if datas?size%2 == 1></tr></#if>
    </tbody>
</table>
</body>
</html>