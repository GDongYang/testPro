<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
	String jsRoot = request.getContextPath();
	/**
	* TODO:some js only need to be contained by a few pages, they should be imported occasionally.
	*/
	String jsRequestPath = request.getServletPath();
	List<String> jsChartList = new ArrayList<String>();
	jsChartList.add("/daas/resource/ECSResView.jsp");
%>
<script src="<%=jsRoot%>/plugins/jQuery/jQuery-2.1.3.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/message.modal.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/build/js/common.js"></script>
<script type="text/javascript" src="<%=jsRoot %>/js/jquery.bootstrap.js"></script>
<%
    String plugin = request.getParameter("p");
    if(plugin != null){
		String[] plgs = plugin.split(",");
		String[] linkSrc = {};
		for(int i = 0;i < plgs.length;i++){
			if("fileinput".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/fileinput/js/fileinput.min.js","/plugins/fileinput/js/fileinput_locale_zh.js"};
			}else if("date".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/datepicker/bootstrap-datepicker.js","/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"};
			}else if("datetime".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/datetimepicker/bootstrap-datetimepicker.js","/plugins/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"};
			}else if("daterang".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/daterangepicker/daterangepicker.js"};
			}else if("validator".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/validator/js/bootstrapValidator.0.4.5.js"};
			}else if("table".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/bootstrapTable/bootstrap-table.min.js","/plugins/bootstrapTable/locale/bootstrap-table-zh-CN.min.js"};
			}else if("icheck".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/iCheck/icheck.min.js"};
			}else if("scroll".equals(plgs[i])){
				linkSrc = new String[]{"/js/slimScroll/jquery.slimscroll.min.js"};
			}else if("mcustomscrollbar".equals(plgs[i])){
				linkSrc = new String[]{"/js/jquery.mCustomScrollbar.concat.min.js"};
			}else if("tips".equals(plgs[i])){
				linkSrc = new String[]{"/js/jquery.tips.js","/js/jquery.tips.util.js"};
			}else if("chosen".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/chosen/1.7.0/chosen.jquery.min.js","/plugins/chosen/chosen.jquery.util.js"};
			}else if("tree".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/zTree/3.5.28/js/jquery.ztree.core.min.js","/plugins/zTree/3.5.28/js/jquery.ztree.excheck.js","/plugins/zTree/3.5.28/js/jquery.ztree.exhide.min.js"};
			}else if("dialog".equals(plgs[i])){
				linkSrc = new String[]{"/js/jquery.mousewheel.js","/js/jquery.dialog.util.js"};
			}else if("bootstrapDialog".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/bootstrap-dialog/1.35.4/dist/js/bootstrap-dialog.min.js"};
			}else if("echarts".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/echarts/echart/echarts.js"};
			}else if("page".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/page/jquery.page.js"};
			}else if("cookie".equals(plgs[i])){
				linkSrc = new String[]{"/js/jquery.cookie.js"};
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
