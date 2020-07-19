<%
	String cssRoot = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssRoot %>/css/common.min.css"/>
<%
    String plugin = request.getParameter("p");
    if(plugin != null){
		String[] plgs = plugin.split(",");
		String[] linkSrc = {};
		for(int i = 0;i < plgs.length;i++){
			if("icon".equals(plgs[i])){
				linkSrc = new String[]{"/font-awesome/4.3.0/css/font-awesome.min.css","/plugins/ionicons/2.0.1/css/ionicons.min.css"};
			}else if("daterang".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/daterangepicker/daterangepicker-bs3.css"};
			}else if("date".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/datetimepicker/bootstrap-datetimepicker.css"};
			}else if("validator".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/validator/css/bootstrapValidator.css"};
			}else if("fileinput".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/fileinput/css/fileinput.min.css"};
			}else if("table".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/bootstrapTable/bootstrap-table.css"};
			}else if("icheck".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/iCheck/all.css"};
			}else if("mcustomscrollbar".equals(plgs[i])){
				linkSrc = new String[]{"/css/jquery.mCustomScrollbar.css"};
			}else if("chosen".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/chosen/1.7.0/chosen.min.css"};
			}else if("tree".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css"};
			}else if("dialog".equals(plgs[i])){
				linkSrc = new String[]{"/jquery-ui/1.12.1/ui/themes/other/jquery-ui.min.css","/jquery-ui/1.12.1/ui/themes/other/jquery-ui-util.css"};
			}else if("bootstrapDialog".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/bootstrap-dialog/1.35.4/dist/css/bootstrap-dialog.min.css"};
			}else if("page".equals(plgs[i])){
				linkSrc = new String[]{"/plugins/page/jquery.page.css"};
			}else{
				linkSrc = new String[]{};
			}
			if(linkSrc.length > 0){
				for(int n = 0;n < linkSrc.length;n++){
					out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+cssRoot+linkSrc[n]+"\" />");
				}
			}
		}
    }
%>
<script type="text/javascript">
</script>