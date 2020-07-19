<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="description" content="3 styles with inline editable feature" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<title>系统用户</title>
	    <jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,chosen,dialog,icon" name="p"/>
		</jsp:include>
		 <link rel="stylesheet" type="text/css" href="<%=path %>/cert/css/1.css" />
	</head>
	<body class="hold-transition skin-blue sidebar-mini base_skin-aqua">
		<div class="wrapper">
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper base_content-wrapper">
				<!-- Main content -->
				<section class="content">
					
					<div class="base_query-area">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">	
							<form id="searchForm">
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 pull-right">
										<div class="form-group text-right base_options-area">
											<button type="button" class="btn btn-sm btn-info" onClick="search()">搜索</button>
											<button type="button" class="btn btn-sm btn-info" onClick="clear1()">清空</button>
										</div>
									</div>
									<!-- /.base_options-area -->
									
									<div class="col-xs-12 col-sm-12 col-md-10 col-lg-12">
										<div class="row">
											<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="usernameSearch">用户名</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<div class="input-group base_input-group">
														<input type="text" class="form-control base_input-text" id="usernameSearch" name='username' placeholder="用户名"/>
													</div>
												</div>
											</div>
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="nameSearch">姓名</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<div class="input-group base_input-group">
														<input type="text" class="form-control base_input-text" id="nameSearch" name='name' placeholder="姓名"/>
													</div>
												</div>
											</div>
											<!-- /.base_query-group -->
									<!-- 		<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="certNo">证书编码</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<div class="input-group base_input-group">
														<input type="text" class="form-control base_input-text" id="certNo" name='certNo' readonly="readonly" placeholder="证书编码"/>
													</div>
												</div>
											</div> -->
											<!-- /.base_query-group -->
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 base_query-group">
												<label class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="department">所属部门</label>
												<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 base_input-area">
													<select class="form-control chosen-select-deselect" chosen-position="true" name="department" id="department"><option value=""></option></select>
												</div>
											</div>
											<!-- /.base_query-group -->
										</div>
										<!-- /.row -->
									</div>
								</div>
							</form>
						</div>
						<h2 class="page-header base_page-header"><!-- <div onclick="moreHidden(this);" class="base_more-hidden base_more-openicon"></div> --></h2>
					</div>
					<!-- /.base_query-area -->
					
					<div name="boxSkin" class="box base_box-area-aqua">
						<div class="box-header with-border base_box-header">
							<h3 class="box-title"><i class="fa fa-tag"></i> <span class="base_text-size-15">用户管理</span></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-xs btn-info" onClick="add()">新增</button>
								<button type="button" class="btn btn-xs btn-info" onClick="update()">修改</button>
								<button type="button" class="btn btn-xs btn-info" onClick="remove()">删除</button>
							<!-- 	<button type="button" class="btn btn-xs btn-info" onClick="active()">用户激活</button> -->
		<!-- 						<button type="button" class="btn btn-xs btn-info" onClick="passReset()">密码重置</button>
								<button type="button" class="btn btn-xs btn-info" onClick="updatePass()">修改密码</button> -->
								<button type="button" class="btn btn-xs btn-info" onClick="assignRoles()">分配角色</button>
								<button type="button" class="btn btn-xs btn-info" onClick="postHandler()">分配岗位</button>
								<button type="button" class="btn btn-xs btn-info" id="sign_up">证书绑定</button>
							<!-- <button type="button" class="btn btn-xs btn-info" onClick="unBind()">解绑</button> -->
							</div>
						</div>
						<div class="box-body">
							<div class="form-group">
								<table id="tablewrap" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
									data-ajax="ajaxRequest" data-side-pagination="server"
									data-striped="true" data-single-select="true"
									data-click-to-select="true" data-pagination="true"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页">
									<thead style="text-align:center;">
										<tr>
											<th data-radio="true"></th>
											<th data-field="id" data-formatter="idFormatter" data-width="40">序号</th>
											<th data-field="username">用户名</th>
											<th data-field="name">姓名</th>
											<th data-field="cardNo">身份证号</th>
											<th data-field="certNo">证书编号</th>
											<!-- <th data-field="workNo">工号</th> -->
										<!-- 	<th data-field="sex" data-formatter="formatSex">性别</th> -->
											<th data-field="orgName">所属单位</th>
											<th data-field="positionId"  data-formatter="formatPosition">岗位</th>
											<!-- <th data-field="mobilePhone">移动电话</th>
											<th data-field="email">电子邮箱</th> -->
											<!-- <th data-field="virtualMobilePhone">手机虚拟号</th> -->
											<th data-field="rCount" data-formatter="formatRCount">是否分配角色</th>
										</tr>
									</thead>
								</table>
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
					
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
			
		</div>
		<!-- ./wrapper -->
		
		<!-- 新增、修改 -->
		<div id="dataFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="dataform" name="dataform">
				<div style="display:none" id="hiddenDiv">
			
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group" id="userNameDiv">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >用户名</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="userName" name="username" placeholder="用户名" />
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="name">姓名</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="name" name="name" placeholder="姓名" tips-message="请输入姓名"/>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >身份证号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="cardNo" name="cardNo" placeholder="身份证号" />
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >CA证书编号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="certNo" name="certNo" placeholder="CA证书编号" />
					</div>
				</div>
				<!-- /.base_query-group -->
			<!-- 	<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="workNo">工号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="workNo" name="workNo" placeholder="工号"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<!-- <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="sex">性别</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="sex" name="sex" placeholder="性别">
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="org">所属单位</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" id="org" name="orgId" placeholder="所属单位"></select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >岗位</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<select class="form-control chosen-select-deselect" chosen-position="true" name="positionId" id="positionId" ></select>
					</div>
				</div>
				<!-- /.base_query-group -->
		<!-- 		<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >移动电话</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="mobilePhone" name="mobilePhone" placeholder="移动电话" tips-min="0" tips-max="11" tips-regexp="{'code':'/^[0-9]+$/','message':'移动电话只能是数字'}"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<!-- <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="officePhone">办公室电话</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="officePhone" name="officePhone" placeholder="办公室电话" tips-regexp="{'code':'/^[0-9]+$/','message':'办公室电话只能是数字'}"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
				<!-- <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" >电子邮箱</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="email" name="email" placeholder="电子邮箱" tips-regexp="{'code':'/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/','message':'邮箱地址格式错误'}"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
			<!-- 	<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="virtualMobilePhone">手机虚拟号</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="virtualMobilePhone" name="virtualMobilePhone" placeholder="手机虚拟号" tips-min="0" tips-max="6" tips-regexp="{'code':'/^[0-9]+$/','message':'手机虚拟号只能是数字'}"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
			<!-- 	<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="fax">传真</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="fax" name="fax" placeholder="传真" tips-regexp="{'code':'/^[0-9]+$/','message':'传真只能是数字'}"/>
					</div>
				</div> -->
				<!-- /.base_query-group -->
			</form>
		</div>
		<!-- /.base_hidden -->
		
		<div id="passFormWrap" class="base_hidden">
			<form class="form-horizontal base_dialog-form" id="passForm" name="passForm">
				<input type="hidden" class="form-control" name="Id" id="Id"/>
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="oldPass">旧密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="text" class="form-control base_input-text" id="oldPass" name="oldPass" placeholder="旧密码" tips-message="请输入旧密码"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="newPass">新密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="password" class="form-control base_input-text" id="newPass" name="newPass" placeholder="新密码" tips-message="请输入新密码"/>
					</div>
				</div>
				<!-- /.base_query-group -->
				<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10 base_query-group">
					<label class="col-xs-12 col-sm-4 col-md-4 col-lg-4 text-right base_input-title base_text-size-15" for="queryPass">确认密码</label>
					<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8 base_input-area">
						<input type="password" class="form-control base_input-text" id="queryPass" name="queryPass" placeholder="确认密码" tips-identical="[{'field':'newPass','message':'密码不一致'}]"/>
					</div>
				</div>
				<!-- /.base_query-group -->
			</form>
		</div>
		<!-- /.base_hidden -->
		
		<div id="roleFormWrap" class="base_hidden">
			<table class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<tr>
					<td><label class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left base_text-size-15" for="select">未选角色：</label></td>
					<td></td>
					<td><label class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left base_text-size-15" for="select2">已选角色：</label></td>
				</tr>
				<tr>
					<td class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
						<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select" id="select"></select>
					</td>
					<td class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center">
						<button class="btn btn-sm btn-info base_table-btn" name="right" id="right"><span class="glyphicon glyphicon-step-forward"></span></button><br/>
						<button class="btn btn-sm btn-info base_table-btn" name="rightAll" id="rightAll"><span class="glyphicon glyphicon-fast-forward"></span></button><br/>
						<button class="btn btn-sm btn-info base_table-btn" name="leftAll" id="leftAll" ><span class="glyphicon glyphicon-fast-backward"></span></button><br/>
						<button class="btn btn-sm btn-info base_table-btn" name="left" id="left"><span class="glyphicon glyphicon-step-backward"></span></button>
					</td>
					<td class="col-xs-5 col-sm-5 col-md-5 col-lg-5 text-center base_input-area">
						<select multiple="multiple" size="15" class="form-control base_multiple-select" name="select2" id="select2"></select>
					</td>
				</tr>
			</table>
		</div>
		<!-- /.base_hidden -->
		<div id="setPostDiv" class="base_hidden">
			 <div class="user-position">
                  
			</div>
		</div>
		
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="mcustomscrollbar,table,icheck,tips,chosen,dialog,cookie" name="p"/>
		</jsp:include>
		<script type="text/javascript" src="<%=path %>/cert/js/drag.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_cert.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_com.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_key.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/zjcacmt_websocket.js"></script>
		<script type="text/javascript" src="<%=path %>/cert/js/login_mod.js"></script>
		<script type="text/javascript" src="<%=path %>/js/user.js?v=2.1" charset="UTF-8"></script>

		
		<!-- loading -->
	    <div id="svg" class="svg" style="display:none;z-index: 10;">
	    	<img src="<%=path %>/cert/images/loading.png">
	    </div>
	
		<!-- 仿Windows弹出框 -->
		
	    <div id="owidow" class="sameWindow">
	        <div class="obt" id="double">
	            <div class="closeicon"></div>
	            <div class="atitle fs16">选择证书</div>
	        </div>
	        <div class="obs">
	
	        
	        </div>
	        <div class="obb">
	            <div class="obbb">
	                <div class="btna" id="btn_sure">确　定</div>
	                <div class="btna" id="btn_no">取　消</div>
	            </div>
	        </div>
	    </div>
	</body>
</html>