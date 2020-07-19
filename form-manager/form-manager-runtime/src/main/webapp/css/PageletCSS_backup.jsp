<%
	String cssRoot = request.getContextPath();
	/**
	* TODO:some css only need to be contained by a few pages, they should be imported occasionally.
	*/
    //String cssRequestPath = request.getServletPath();
%>
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/dist/css/AdminLTE.css" />
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/dist/css/skins/_all-skins.css" />

<!--  <link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/select2/css/select2.css" /> -->
<!-- 图标 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/font-awesome/4.3.0/css/font-awesome.min.css" />

<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/ionicons/2.0.1/css/ionicons.min.css" />
<!-- 日期选择控件 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/datetimepicker/bootstrap-datetimepicker.css" />
<!-- 验证框架 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/validator/css/bootstrapValidator.css" />
<!-- 文件上传 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/fileinput/css/fileinput.min.css" />

<!-- <link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/treeview/css/bootstrap-treeview.css" /> -->


<!-- <link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/switch/css/bootstrap3/bootstrap-switch.min.css" /> -->
<!-- table -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/table/bootstrap-table.css" />
<!-- <link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/editable/css/bootstrap-editable.css" /> -->

<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/iCheck/all.css"  />

<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/bootstrap-combobox/css/bootstrap-combobox.css" />
<!-- 图表 -->
<!-- <link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/seatcharts/css/jquery.seat-charts.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/seatcharts/css/seatCSS.css" /> -->

<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/fullcalendar/fullcalendar.min.css" />
<!-- 软键盘插件样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/keypad/css/jquery.keypad.css" />
<!-- select下拉选择框模拟插件样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/chosen/1.7.0/chosen.min.css"/>
<!-- ztree树形菜单样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css">
<!-- jquery双向select插件样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/plugins/bootstrap-duallistbox/dist/bootstrap-duallistbox.min.css">
<!-- jquery.dialog弹窗样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/jquery-ui/1.12.1/ui/themes/other/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/jquery-ui/1.12.1/ui/themes/other/jquery-ui-util.css"/>
<!-- 公共自定义样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/css/base.min.css" />

<!--[if lt IE 9]>
   	<script src="<%=cssRoot %>/plugins/bootstrap-wysihtml5/html5shiv.min.js"></script>
   	<script src="<%=cssRoot %>/plugins/bootstrap-wysihtml5/respond.min.js"></script>
<![endif]-->
<%-- <%=cssRequestPath %> --%>