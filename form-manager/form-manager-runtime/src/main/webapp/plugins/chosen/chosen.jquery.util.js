/** chosen-select下拉框初始化 **/
function selectConfig() {
	if ($(document).find(".chosen-select-deselect").length <= 0) {
		return;
	}
	//下拉框样式绑定
	var config = {
		'.chosen-select-deselect' : {
			no_results_text:"没有找到",	//搜索无结果时显示的提示
			placeholder_text_single:"————请选择————",	//单选框没有选中项时显示的占位文字
			allow_single_deselect:true,	//是否允许取消选择
			//max_selected_options:6,	//当select为多选时，最多选择个数
			search_contains:true,	//关键字模糊搜索，设置为false，则只从开头开始匹配
			width: '108%',//初始宽度
			display_selected_options:true,
		}
	};
	for (var selector in config) {
		$(selector).chosen(config[selector]);
	}
	
	$(document).find('.chosen-select-deselect').on('change', function(e, params) {
		$(this).val($(this).val());
	});
	$(document).find('.chosen-select-deselect').on('chosen:showing_dropdown', function(e, params) {
		var chosenPosition = $(this).attr("chosen-position");//设置下拉选择项是否要浮动
		if ((/^true$/i).test(chosenPosition)) {
			var w = $(this).next().width();
			//$(this).next().find(".chosen-drop").css({"position":"fixed","top":"auto","width":w});
		}
	});
	$(document).find('.chosen-select-deselect').on('chosen:hiding_dropdown', function(e, params) {
		var chosenPosition = $(this).attr("chosen-position");//设置下拉选择项是否要浮动
		if ((/^true$/i).test(chosenPosition)) {
			$(this).next().find(".chosen-drop").css({"position":"absolute","top":"100%","width":"100%"});
		}
	});
}

/** chosen-select下拉框变动更新 **/
function selectUpdated(container) {
	if ($(document).find($(container)).length > 0) {
		$(document).find($(container)).trigger("chosen:updated");
		var choseSelectSetTimeout = setTimeout(function () {
			$(document).find($(container)).each(function(index, n) {
				var w = $(n).width() < $(n).css("width").replace("px","") ? $(n).css("width").replace("px","") : $(n).width();
				$(n).next("div").width(w-2);
				clearTimeout(choseSelectSetTimeout);//清除定时器
				choseSelectSetTimeout = null;
			});
		},1000);
	} else {
		$(document).find(".chosen-select-deselect").trigger("chosen:updated");
		var choseSelectSetTimeout = setTimeout(function () {
			$(document).find(".chosen-select-deselect").each(function(index, n) {
				var w = $(n).width() < $(n).css("width").replace("px","") ? $(n).css("width").replace("px","") : $(n).width();
				$(n).next("div").width(w-2);
				clearTimeout(choseSelectSetTimeout);//清除定时器
				choseSelectSetTimeout = null;
			});
		},1000);
	}
}