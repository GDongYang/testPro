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
<script type="text/javascript" src="<%=jsRoot %>/plugins/blockUI/jquery.blockUI.js"></script>
<!-- AdminLTE App -->
<script type="text/javascript" src="<%=jsRoot %>/dist/js/app.js"></script>
<!-- Jquery Cookies -->
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.cookie.js"></script>
<!-- 公共自定义脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/common.min.js"></script>
<%
	/**
	* TODO:some js only need to be contained by a few pages, they should be imported occasionally.
	*/
	//String jsRequestPath = request.getServletPath();
    String plugin = request.getParameter("p");
    if(plugin != null){
		String[] plgs = plugin.split(",");
		String[] linkSrc = {};
		for(int i = 0;i < plgs.length;i++){
			if("fileinput".equals(plgs[i])){
				//文件选择控件
				linkSrc = new String[]{"/plugins/fileinput/js/fileinput.min.js","/plugins/fileinput/js/fileinput_locale_zh.js"};
			}else if("date".equals(plgs[i])){
				//年月日选择
				linkSrc = new String[]{"/plugins/datepicker/bootstrap-datepicker.js","/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"};
			}else if("datetime".equals(plgs[i])){
				//精细时间选择
				linkSrc = new String[]{"/plugins/datetimepicker/bootstrap-datetimepicker.js","/plugins/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"};
			}else if("daterang".equals(plgs[i])){
				//时间段选择
				linkSrc = new String[]{"/plugins/daterangepicker/daterangepicker.js"};
			}else if("dateMonthRange".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/daterangepicker/monthRangeMoment.js","/plugins/daterangepicker/dateMonthRange.min.js","/plugins/daterangepicker/dateMonthRangeConfig.js"};
			}else if("validator".equals(plgs[i])){
				//验证框架
				linkSrc = new String[]{"/plugins/validator/js/bootstrapValidator.0.4.5.js"};
			}else if("table".equals(plgs[i])){
				//表格控件
				linkSrc = new String[]{"/plugins/table/bootstrap-table.js","/plugins/table/locale/bootstrap-table-zh-CN.min.js"};
			}else if("icheck".equals(plgs[i])){
				//选择框
				linkSrc = new String[]{"/plugins/iCheck/icheck.min.js"};
			}else if("scroll".equals(plgs[i])){
				//slimscroll滚动条
				linkSrc = new String[]{"/js/slimScroll/jquery.slimscroll.min.js"};
			}else if("mcustomscrollbar".equals(plgs[i])){
				//mcustomscrollbar滚动条
				linkSrc = new String[]{"/js/jquery.mCustomScrollbar.concat.min.js"};
			}else if("tips".equals(plgs[i])){
				//验证提示框
				linkSrc = new String[]{"/js/jquery.tips.js","/js/jquery.tips.util.js"};
			}else if("chosen".equals(plgs[i])){
				//select下拉选择框模拟插件脚本
				linkSrc = new String[]{"/plugins/chosen/1.7.0/chosen.jquery.min.js","/plugins/chosen/chosen.jquery.util.js"};
			}else if("tree".equals(plgs[i])){
				//ztree树形菜单脚本
				linkSrc = new String[]{"/plugins/zTree/3.5.28/js/jquery.ztree.core.min.js",
						"/plugins/zTree/3.5.28/js/jquery.ztree.excheck.js",
						"/plugins/zTree/3.5.28/js/jquery.ztree.exhide.min.js"};
			}else if("echarts".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/echarts/echart/echarts.js"};
			}else if("dialog".equals(plgs[i])){
				//dialog 弹出框
				linkSrc = new String[]{"/js/jquery.mousewheel.js","/js/jquery.dialog.util.js"};
			}else{
				linkSrc = new String[]{};
			}
			
			if(linkSrc.length > 0){
				//linkSrc = basePath+linkSrc;
				for(int n = 0;n < linkSrc.length;n++){
					out.println("<script src=\""+jsRoot+linkSrc[n]+"\" charset=\"UTF-8\"></script>");
				}
			}
		}
    }
%>

<%-- <script type="text/javascript" src="<%=jsRoot %>/js/feixian.util.js"></script> --%>
<!-- 重写messageModal弹出框脚本 -->
<script type="text/javascript" src="<%=jsRoot %>/js/message.modal.js"></script>
<%-- <%=jsRequestPath %> --%>