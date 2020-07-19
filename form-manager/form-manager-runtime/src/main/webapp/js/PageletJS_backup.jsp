<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String jsRoot = request.getContextPath();
%>
<!--[if !IE]> -->
<script type="text/javascript" src="<%=jsRoot %>/jquery/2.2.4/jquery-2.2.4.min.js"></script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript" src="<%=jsRoot %>/jquery/1.12.4/jquery-1.12.4.min.js"></script>
<![endif]-->
<script type="text/javascript" src="<%=jsRoot %>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<!-- AdminLTE App -->
<script type="text/javascript" src="<%=jsRoot %>/dist/js/app.js"></script>
<%
	/**
	* TODO:some js only need to be contained by a few pages, they should be imported occasionally.
	*/
	//String jsRequestPath = request.getServletPath();
    String plugin = request.getParameter("p");
	String[] plgs = plugin.split(",");
	String[] linkSrc = {};
	for(int i = 0;i < plgs.length;i++){
		if("table".equals(plgs[i])){
			linkSrc = new String[]{"resource/jquery/jquery.js"};
		}else{
			linkSrc = new String[]{};
		}
	}
%>

<script type="text/javascript" src="<%=jsRoot %>/plugins/slimScroll/jquery.slimscroll.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/fileinput/js/fileinput.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/fileinput/js/fileinput_locale_zh.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/validator/js/bootstrapValidator.0.4.5.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/treeview/js/bootstrap-treeview.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/switch/js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/table/extensions/editable/bootstrap-table-editable.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/table/extensions/export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/table/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/editable/js/bootstrap-editable.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/seatcharts/js/jquery.seat-charts.js"></script>
<%-- 屏蔽自带bootstrap.dialog插件 <script type="text/javascript" src="<%=jsRoot %>/js/jquery.bootstrap.min.js"></script> --%>
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.twbsPagination.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/chinaoly_verifiy.js"></script>
<!--[if lte IE 8]><script type="text/javascript" src="<%=jsRoot %>/js/flot/excanvas.min.js"></script><![endif]-->
<!-- FLOT CHARTS -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/flot/jquery.flot.min.js" ></script>
<!-- FLOT RESIZE PLUGIN - allows the chart to redraw when the window is resized -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/flot/jquery.flot.resize.min.js" ></script>
<!-- FLOT PIE PLUGIN - also used to draw donut charts -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/flot/jquery.flot.pie.min.js" ></script>
<!-- FLOT CATEGORIES PLUGIN - Used to draw bar charts -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/flot/jquery.flot.categories.min.js" ></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/flot/jquery.flot.time.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/flot/jquery.flot.symbol.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/iCheck/icheck.min.js"></script>

<script type="text/javascript" src="<%=jsRoot %>/plugins/echarts/dist/echarts-all.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/bootstrap-combobox/js/bootstrap-combobox.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/select2/js/select2.full.js"></script>
<!-- jquery slimScroll滚动条插件脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<%-- <script type="text/javascript" src="<%=jsRoot %>/js/feixian.util.js"></script> --%>
<!-- jquery.cookie信息存储脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.cookie.js"></script>
<!-- jquery.tips提示验证脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.tips.util.js"></script>
<!-- 软键盘插件脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/keypad/js/jquery.plugin.js"></script> 
<script type="text/javascript" src="<%=jsRoot %>/plugins/keypad/js/jquery.keypad.js"></script>
<!-- select下拉选择框模拟插件脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/chosen/1.7.0/chosen.jquery.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/chosen/chosen.jquery.util.js"></script>
<!-- ztree树形菜单脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/zTree/3.5.28/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/plugins/zTree/3.5.28/js/jquery.ztree.excheck.js"></script>
<!-- jquery双向select插件脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/plugins/bootstrap-duallistbox/dist/jquery.bootstrap-duallistbox.min.js"></script>
<!-- jquery.mousewheel鼠标滚动支持事件脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.mousewheel.js"></script>
<!-- jquery.dialog弹窗脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.dialog.util.js"></script>
<!-- 重写messageModal弹出框脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/message.modal.js"></script>
<!-- 公共自定义脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/common.min.js"></script>

<script type="text/javascript" src="<%=jsRoot %>/js/jquery.printarea.js"></script>

<script type="text/javascript" src="<%=jsRoot %>/js/chartjs/Chart.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/chartjs/Chart.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/slimScroll/jquery.slimscroll.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/slimScroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/fastclick/fastclick.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/fastclick/fastclick.min.js"></script>

<script type="text/javascript" src="<%=jsRoot %>/plugins/fullcalendar/fullcalendar.min.js"></script>
<%-- <%=jsRequestPath %> --%>