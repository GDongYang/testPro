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
		<title>缓存列表</title>
		<jsp:include page="../css/PageletCSS.jsp" >
			<jsp:param value="table,icon" name="p"/>
		</jsp:include>
		<link rel="stylesheet" type="text/css" href="../build/css/tableTreeList.css" />
	</head>
	<body class="base_background-EEF6FB">
		<div class="wrapper">			
			<div class="tableBox">
				<div class="tableHead">
					<h3 class="tableName">
						<i class="fa fa-tag"></i>
						<span>缓存列表</span>
					</h3>
                    <div class="tableBtns">
                        <button onclick='refreshAllCache()'><span>一键缓存</span></button>
                    </div>
				</div>
				<div id="tempDetailWrap" class="tableCont">
					<table id="dataTable" class="box base_tablewrap" data-toggle="table" data-locale="zh-CN"
						data-ajax="ajaxRequest" data-side-pagination="server"
						data-striped="true" data-single-select="true"
						data-click-to-select="true" data-pagination="true"
						data-pagination-first-text="首页" data-pagination-pre-text="上一页"
						data-pagination-next-text="下一页" data-pagination-last-text="末页">
						<thead style="text-align:center;">
							<tr>
								<th data-field="id" data-formatter="idFormatter">序号</th>
								<th data-field="name">名称</th>
								<th data-field="status" data-formatter="statusFormatter">状态</th>
                                <th data-field="refreshTime" data-formatter="dateFormatter">最近缓存时间</th>
                                <th data-field="code"  data-formatter="codeFormatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>					
		</div>
		<jsp:include page="../js/PageletJS.jsp" >
			<jsp:param value="table" name="p"/>
		</jsp:include>		
		<script type="text/javascript">
			var pageNum = 1;
			var pageSize = 10;
			function idFormatter(value, row) {
				return index++;
			}
			function dateFormatter(val) {
                if(val != null) {
                    return val.replace("T", " ");
                }
            }
			function statusFormatter(value,row){
				if(value==1){
					return "已缓存";
				} else if (value == 2){
					return "正在缓存中";
				} else if (value == 0){
				    return "未缓存";
                } else {
                	return "缓存失败";
                }
			}
			function codeFormatter(value) {
                return "<button class='btn btn-xs btn-primary' onclick=\"refreshCache('"+value+"')\">刷新缓存</a>";
            }

			function refreshCache(value,row){
				Modal.confirm({
					msg:"是否操作？"
				}).on( function (e) {
					if(e) {
						$.ajax({
							type : 'post',
							url : 'cacheInfoAction!refreshCache.action',
							data:{'code' : value},
							dataType : 'json',
							cache : false,
							async : true,
							error : function(request, textStatus, errorThrown) {
							},
							success : function(data) {
								Modal.alert({ msg:'操作成功！', title:'提示', btnok:'确定' }).on(function(e){
                                    $("#dataTable").bootstrapTable("refresh");
                                });
							}
						});
					}
				});
			}
            function refreshAllCache(){
                Modal.confirm({
                    msg:"是否操作？"
                }).on( function (e) {
                    if(e) {
                        $.ajax({
                            type : 'post',
                            url : 'cacheInfoAction!refreshAll.action',
                            dataType : 'json',
                            cache : false,
                            async : true,
                            error : function(request, textStatus, errorThrown) {
                            },
                            success : function(data) {
                                Modal.alert({ msg:'操作成功！', title:'提示', btnok:'确定' }).on(function(e){
                                    $("#dataTable").bootstrapTable("refresh");
                                });
                            }
                        });
                    }
                });
            }
			//ajax请求
			function ajaxRequest(params) {
				var pageSize = params.data.limit;
			    var pageNum = params.data.offset/pageSize + 1;
				var dataStr = 'pageNum=' + pageNum
				+ '&pageSize=' + pageSize;
				$.ajax({
					type : 'post',
					url : 'cacheInfoAction!findPage.action',
					data:dataStr,
					dataType : 'json',
					cache : false,
					async : true,
					error : function(request, textStatus, errorThrown) {
					},
					success : function(data) {
						var datas = data ? data.rows : [];
						var count = data ? data.total : 0;
						params.success({
							total : count,
							rows : datas ? datas : []
						});
					}
				});
			}
		</script>
	</body>
</html>