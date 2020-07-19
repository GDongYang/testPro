<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <style type="text/css">
            .f_content{padding:20px 40px;}
            .f_title{font-size:24px;font-family: '黑体';text-align: center;}
            .f_date{text-align: right;margin:20px 0px;margin-right:40px;}
            table {
                border-collapse:collapse;
            }
            table td{line-height:30px;padding:8px 0px;text-align: center;border-bottom: 1px solid #000;border-left:1px solid #000;}
            table td:last-child{border-right:1px solid #000;}
            table tr:first-child td{border-top:1px solid #000;}
        </style>
    </head>
    <body>
        <div class="f_content">
            <div class="f_title">
                城市建筑物、设施上张挂、张贴宣传品审批申请表
            </div>
            <div class="f_date">
                申请日期：${formCreated?string('yyyy-MM-dd')!DefaultValue}
            </div>
            <table>
                <tbody>
                    <tr>
                        <td>
                            申请单位（个人）<br/>
                            名    称
                        </td>
                        <td colspan="4">
                            ${sqdwmc!DefaultValue}
                        </td>
                        <td colspan="2">
                            邮政编码
                        </td>
                        <td colspan="3">
                            ${yzbm!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            地址
                        </td>
                        <td colspan="9">
                            ${dz!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            法人代表
                        </td>
                        <td colspan="4">
                            ${frdb!DefaultValue}
                        </td>
                        <td colspan="2">
                            联系电话
                        </td>
                        <td colspan="3">
                            ${frdb_lxdh!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            经办人
                        </td>
                        <td colspan="4">
                            ${jbr!DefaultValue}
                        </td>
                        <td colspan="2">
                            联系电话
                        </td>
                        <td colspan="3">
                            ${jbr_lxdh!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            申请理由
                        </td>
                        <td colspan="9">
                            ${sqly!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            申请地点
                        </td>
                        <td colspan="9">
                            ${sqdd!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            数    量
                        </td>
                        <td colspan="4">
                            ${sl!DefaultValue}
                        </td>
                        <td colspan="2">
                            广告类型
                        </td>
                        <td colspan="3">
                            ${gglx!DefaultValue}
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="${szfl?size+1}">
                            设置分类
                        </td>
                        <td>
                            序号
                        </td>
                        <td colspan="2">
                            种类
                        </td>
                        <td colspan="4">
                            广告设施载体形式
                        </td>
                        <td colspan="2">
                            广告设施数量
                        </td>
                    </tr>
                    <#list szfl as item >
                        <tr>
                            <td>
                                ${item_index+1}
                            </td>
                            <td colspan="2">
                                ${item[0].value!DefaultValue}
                            </td>
                            <td colspan="4">
                                ${item[1].value!DefaultValue}
                            </td>
                            <td colspan="2">
                                ${item[2].value!DefaultValue}
                            </td>
                        </tr>
                    </#list>
                    <tr>
                        <td rowspan="${ggsscc?size+1}">
                            广告设施尺寸
                        </td>
                        <td>
                            序号
                        </td>
                        <td>
                            高<br/>（M）
                        </td>
                        <td colspan="2">
                            宽<br/>（M）
                        </td>
                        <td>
                            厚<br/>（M）
                        </td>
                        <td colspan="3">
                            下沿离地面高度<br/>（M）
                        </td>
                        <td>
                            面积<br/>（M²）
                        </td>
                    </tr>
                    <#list ggsscc as item>
                        <tr>
                            <td>
                                ${item_index+1}
                            </td>
                            <td>
                                ${item[0].value!DefaultValue}
                            </td>
                            <td colspan="2">
                                ${item[1].value!DefaultValue}
                            </td>
                            <td>
                                ${item[2].value!DefaultValue}
                            </td>
                            <td colspan="3">
                                ${item[3].value!DefaultValue}
                            </td>
                            <td>
                                ${item[4].value!DefaultValue}
                            </td>
                        </tr>
                    </#list>
                    <tr>
                        <td>
                            申请设置期限
                        </td>
                        <td colspan="9">
                            自${kssj!DefaultValue}至${jzsj!DefaultValue}止   共${ts!DefaultValue}天
                        </td>
                    </tr>
                    <tr>
                        <td>
                            现场勘查意见
                        </td>
                        <td colspan="9">
                            ${xckcyj!DefaultValue}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>