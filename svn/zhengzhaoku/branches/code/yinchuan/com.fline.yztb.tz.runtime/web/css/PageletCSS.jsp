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
			if("icon".equals(plgs[i])){
				//文件选择控件
				linkSrc = new String[]{"/font-awesome/4.3.0/css/font-awesome.min.css","/plugins/ionicons/2.0.1/css/ionicons.min.css"};
			}else if("daterang".equals(plgs[i])){
				//时间段
				linkSrc = new String[]{"/plugins/daterangepicker/daterangepicker-bs3.css"};
			}else if("daterangMonth".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/daterangepicker/dateMonthRange.css"};
			}else if("date".equals(plgs[i])){
				//日期
				linkSrc = new String[]{"/plugins/datetimepicker/bootstrap-datetimepicker.css"};
			}else if("validator".equals(plgs[i])){
				//验证
				linkSrc = new String[]{"/plugins/validator/css/bootstrapValidator.css"};
			}else if("fileinput".equals(plgs[i])){
				//文件选择框
				linkSrc = new String[]{"/plugins/fileinput/css/fileinput.min.css"};
			}else if("table".equals(plgs[i])){
				//表格
				linkSrc = new String[]{"/plugins/table/bootstrap-table.css"};
			}else if("icheck".equals(plgs[i])){
				//单选
				linkSrc = new String[]{"/plugins/iCheck/all.css"};
			}else if("mcustomscrollbar".equals(plgs[i])){
				//mcustomscrollbar滚动条
				linkSrc = new String[]{"/css/jquery.mCustomScrollbar.css"};
			}else if("chosen".equals(plgs[i])){
				//模拟下拉框
				linkSrc = new String[]{"/plugins/chosen/1.7.0/chosen.min.css"};
			}else if("tree".equals(plgs[i])){
				//树控件样式
				linkSrc = new String[]{"/plugins/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css"};
			}else if("dialog".equals(plgs[i])){
				//弹出框
				linkSrc = new String[]{"/jquery-ui/1.12.1/ui/themes/other/jquery-ui.min.css","/jquery-ui/1.12.1/ui/themes/other/jquery-ui-util.css"};
			}else{
				linkSrc = new String[]{};
			}
			
			if(linkSrc.length > 0){
				//linkSrc = basePath+linkSrc;
				for(int n = 0;n < linkSrc.length;n++){
					out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+cssRoot+linkSrc[n]+"\" />");
				}
			}
		}
    }
%>
<!-- 公共自定义样式 -->
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/css/base.min.css?v=1" />

<!--[if lt IE 9]>
   	<script src="<%=cssRoot %>/plugins/bootstrap-wysihtml5/html5shiv.min.js"></script>
   	<script src="<%=cssRoot %>/plugins/bootstrap-wysihtml5/respond.min.js"></script>
<![endif]-->
<%-- <%=cssRequestPath %> --%>
<script type="text/javascript">
"use strict";//严格模式.
</script>