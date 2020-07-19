function icheckConfig(container,region) {
	var region = $(region).length > 0 ? $(region) : $(document);
	container = $(region).find(container);
	if ($(container).length > 0 && $(container).hasClass("icheck") && ($(container).is(":radio") || $(container).is(":checkbox"))) {
		$(container).each(function(index,element){
			$(element).iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%'
			});
			
			$(element).on('ifChecked',function(event){
				$(this).attr("checked", true);
			});
			$(element).on('ifUnchecked',function(event){
				$(this).attr("checked", false);
			});
		});
	}
}

function bootstrapTableIcheckConfig(container,tableRegion) {
	if ($(tableRegion).length > 0 && $(tableRegion).is("table")) {
		//循环table
		$(tableRegion).each(function(indexTable,elementTableRegion){
			
			//获取bootstrapTable参数
			var tableOptions = $(elementTableRegion).bootstrapTable('getOptions');
			
			//模拟icheck
			icheckConfig(container,$(elementTableRegion));
			
			//循环table中的每一个tr行
			$(elementTableRegion).find("tbody tr").each(function(indexTr,elementTrRegion){
				var itemInput = $(elementTrRegion).find("input[name='btSelectItem']")[0];
				if ($(itemInput).length > 0 && $(itemInput).next("ins").length > 0) {
					$(itemInput).on('ifChecked',function(event){
						//只允许单选
						if (tableOptions.singleSelect) {
							//循环取消选中（排除当前选择）
							$(elementTableRegion).find("tbody tr").each(function(indexTrUpdate,elementTrRegionUpdate){
								var itemInputUpdate = $(elementTrRegionUpdate).find("input[name='btSelectItem']")[0];
								if (!$(itemInputUpdate).is($(event.target))) {
									if ($(itemInputUpdate).length > 0 && $(itemInputUpdate).next("ins").length > 0) {
										$(itemInputUpdate).iCheck("uncheck").iCheck("update");
									}
									//删除行样式
									$(elementTrRegionUpdate).removeClass("on");
									$(elementTrRegionUpdate).children("td").removeClass("on base_c-004b97 base_cursor-pointer");
								}
							});
						}
						//行内首个确认选中
						if ($(this).length > 0 && $(this).next("ins").length > 0) {
							$(this).iCheck("check").iCheck('update');
						}
						//添加行样式
						$(this).closest("tr").addClass("on");
						$(this).closest("tr").children("td").addClass("on base_c-004b97 base_cursor-pointer");
					}).on('ifUnchecked',function(event){
						//行内首个取消选中
						if ($(event).length > 0 && $(event).next("ins").length > 0) {
							if ($(event).is(":checkbox")) {
								$(event).iCheck("uncheck").iCheck("update");
							}
						}
						//删除行样式
						$(this).closest("tr").removeClass("on");
						$(this).closest("tr").children("td").removeClass("on base_c-004b97 base_cursor-pointer");
					});
				}
			});
			
			
			//选中、取消选中事件
			$(elementTableRegion).on('check.bs.table', function (e, row, elem){
				//只允许单选
				if (tableOptions.singleSelect) {
					//循环取消选中
					$(e.target).find("tbody tr").each(function(indexTrUpdate,elementTrRegionUpdate){
						var itemInputUpdate = $(elementTrRegionUpdate).find("input[name='btSelectItem']")[0];
						if (!$(itemInputUpdate).is($(elem))) {
							if ($(itemInputUpdate).length > 0 && $(itemInputUpdate).next("ins").length > 0) {
								$(itemInputUpdate).iCheck("uncheck").iCheck('update');
							}
							//删除行样式
							$(elementTrRegionUpdate).removeClass("on");
							$(elementTrRegionUpdate).children("td").removeClass("on base_c-004b97 base_cursor-pointer");
						}
					});
				}
				//行内首个确认选中
				if ($(elem).length > 0 && $(elem).next("ins").length > 0) {
					$(elem).iCheck("check").iCheck('update');
				}
				//添加行样式
				$(elem).closest("tr").addClass("on");
				$(elem).closest("tr").children("td").addClass("on base_c-004b97 base_cursor-pointer");
			})
			.on('uncheck.bs.table', function (e, row, elem){
				//行内首个取消选中
				if ($(elem).length > 0 && $(elem).next("ins").length > 0) {
					if ($(elem).is(":checkbox")) {
						$(elem).iCheck("uncheck").iCheck("update");
					}
				}
				//删除行样式
				$(elem).closest("tr").removeClass("on");
				$(elem).closest("tr").children("td").removeClass("on base_c-004b97 base_cursor-pointer");
			});
			
		});
	}
}