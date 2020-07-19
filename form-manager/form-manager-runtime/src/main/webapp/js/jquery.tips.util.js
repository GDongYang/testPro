/** tips校验(验证方法) **/
function tipsValidator(container,propertyJSON){
	var validatorData = true;
	if ($(container).length > 0 && !$(container).hasClass("notVerify")) {
		if (($(container).is(':visible') && !$(container).is(":radio") && !$(container).is(":checkbox")) || $(container).is("select")) {
			if (propertyJSON.tipsMessage) {
				if ($.trim($(container).val()) == "") {
					validatorData = false;
					addTipsHint(container,{msg:propertyJSON.tipsMessage});//添加tips校验提示
				}
			}
			
			if ($.trim($(container).val()) != "") {
				/** 最小长度、最大长度验证 **/
				if (propertyJSON.tipsMin) {
					if ($.trim($(container).val()).length < propertyJSON.tipsMin) {
						validatorData = false;
						addTipsHint(container,{msg:"最小长度不能小于" + propertyJSON.tipsMin + "位数！",time:5});//添加tips校验提示
					}
				}
				if (propertyJSON.tipsMax) {
					if ($.trim($(container).val()).length > propertyJSON.tipsMax) {
						validatorData = false;
						addTipsHint(container,{msg:"最大长度不能大于" + propertyJSON.tipsMax + "位数！",time:5});//添加tips校验提示
					}
				}
				/** 相同值、不同值验证 **/
				if (propertyJSON.tipsIdentical) {
					var tipsIdenticalJson = propertyJSON.tipsIdentical;
					var le = tipsIdenticalJson.length;
					for (var i = 0; i < tipsIdenticalJson.length; i++) {
						var field = tipsIdenticalJson[i].field;
						var message = tipsIdenticalJson[i].message;
						if ($.trim(field) != "" && $("#" + field).length > 0 && $.trim($(container).val()) != $.trim($("#" + field).val())) {
							validatorData = false;
							var time = 5 + le--;
							addTipsHint(container,{msg:message,side:3,time:time});//添加tips校验提示
						}
					}
				}
				if (propertyJSON.tipsDifferent) {
					var tipsDifferentJson = propertyJSON.tipsDifferent;
					var le = tipsDifferentJson.length;
					for (var i = 0; i < tipsDifferentJson.length; i++) {
						var field = tipsDifferentJson[i].field;
						var message = tipsDifferentJson[i].message;
						if ($.trim(field) != "" && $("#" + field).length > 0 && $.trim($(container).val()) == $.trim($("#" + field).val())) {
							validatorData = false;
							var time = 5 + le--;
							addTipsHint(container,{msg:message,side:3,time:time});//添加tips校验提示
						}
					}
				}
				/** 正则表达式验证 **/
				if (propertyJSON.tipsRegexp) {
					var tipsRegexpJson = propertyJSON.tipsRegexp;
					var code = tipsRegexpJson.code;
					var message = tipsRegexpJson.message;
					
					if (code.indexOf("/") == 0) {
						code = code.substring(1, code.length);
					}
					if (code.lastIndexOf("/") == code.length - 1) {
						code = code.substring(0, code.length - 1);
					}
					var re = new RegExp(code);
					var result = re.test($.trim($(container).val()));
					if (!result) {
						validatorData = false;
						addTipsHint(container,{msg:message,side:3,time:5});//添加tips校验提示
					}
				}
			}
		} else if ($(container).is(":radio") || $(container).is(":checkbox")) {
			if (propertyJSON.tipsMessage) {
				if (!returnChecked(container)) {
					validatorData = false;
					addTipsHint(container,{msg:propertyJSON.tipsMessage});//添加tips校验提示
				} else {
					removeTipsHint(container);//移除tips校验提示
				}
			}
		}
	}
	
	//全部验证都通过
	if (validatorData) {
		removeTipsHint(container);//移除tips校验提示
	}
	
	return validatorData;
}

/** tips校验(标注提示方法) **/
function tipsHint(container){
	if ($(container).length > 0) {
		if (($(this).attr("tips-message") || $(this).attr("tips-min") || $(this).attr("tips-max") || $(this).attr("tips-identical") || $(this).attr("tips-different") || $(this).attr("tips-regexp")) && !$(container).hasClass("notVerify")) {
			var id = $(container).attr("id");
			$(document).find("label").each(function(index,n){
				if ($(n).attr("for") == id) {
					if ($(n).find("#font_" + id).length <= 0) {
						$(n).append("<font id='font_" + id + "' color='red'>✲</font>");
					}
				}
			});
		} else {
			var id = $(container).attr("id");
			$(document).find("label").each(function(index,n){
				if ($(n).attr("for") == id) {
					if ($(n).find("#font_" + id).length > 0 && $(n).find("#font_" + id).text() == "✲") {
						$(n).find("#font_" + id).remove(); 
					}
				}
			});
		}
	}
}

function returnChecked(container) {
	var checked = false;
	if ($(container).is(":radio") || $(container).is(":checkbox") && $(container).length > 0) {
		var name = $(container).attr("name");
		if (name != "") {
			$("input[name='" + name + "']").each(function(){
				if ($(this).is(':checked')) {
					checked = true;
				}
			});
		} else {
			if ($(container).is(':checked')) {
				checked = true;
			}
		}
	}
	return checked;
}

/** tips校验(区域验证方法) **/
function tipsRegionValidator(region){
	var validatorData = true;
	$(region).find("input[type!='submit'][type!='reset'][type!='button'][type!='hidden'],textarea,select").each(function(){
		removeTipsHint($(this));//移除tips校验提示
		if (!$(this).hasClass("notVerify")) {
			if (($(this).is(':visible') && !$(this).is(":radio") && !$(this).is(":checkbox")) || $(this).is("select")) {
				if ($(this).attr("tips-message")) {
					if ($.trim($(this).val()) == "") {
						validatorData = false;
						addTipsHint(this);//添加tips校验提示
					}
				}
				if ($.trim($(this).val()) != "") {
					//如果元素值非空情况
					/** 最小长度、最大长度验证 **/
					if ($(this).attr("tips-min")) {
						if ($.trim($(this).val()).length < $(this).attr("tips-min")) {
							validatorData = false;
							addTipsHint(this,{msg:"最小长度不能小于" + $(this).attr("tips-min") + "位数！",time:5});//添加tips校验提示
						}
					}
					if ($(this).attr("tips-max")) {
						if ($.trim($(this).val()).length > $(this).attr("tips-max")) {
							validatorData = false;
							addTipsHint(this,{msg:"最大长度不能大于" + $(this).attr("tips-max") + "位数！",time:5});//添加tips校验提示
						}
					}
					/** 相同值、不同值验证 **/
					if ($(this).attr("tips-identical")) {
						var tipsIdenticalStr = $(this).attr("tips-identical");
						var tipsIdenticalJson = eval('(' + tipsIdenticalStr + ')');
						var le = tipsIdenticalJson.length;
						for (var i = 0; i < tipsIdenticalJson.length; i++) {
							var field = tipsIdenticalJson[i].field;
							var message = tipsIdenticalJson[i].message;
							if ($.trim($(this).val()) != $.trim($("#" + field).val())) {
								validatorData = false;
								var time = 5 + le--;
								addTipsHint(this,{msg:message,side:3,time:time});//添加tips校验提示
							}
						}
					}
					if ($(this).attr("tips-different")) {
						var tipsDifferentStr = $(this).attr("tips-different");
						var tipsDifferentJson = eval('(' + tipsDifferentStr + ')');
						var le = tipsDifferentJson.length;
						for (var i = 0; i < tipsDifferentJson.length; i++) {
							var field = tipsDifferentJson[i].field;
							var message = tipsDifferentJson[i].message;
							if ($.trim($(this).val()) == $.trim($("#" + field).val())) {
								validatorData = false;
								var time = 5 + le--;
								addTipsHint(this,{msg:message,side:3,time:time});//添加tips校验提示
							}
						}
					}
					/** 正则表达式验证 **/
					if ($(this).attr("tips-regexp")) {
						var tipsRegexpStr = $(this).attr("tips-regexp");
						var tipsRegexpJson = eval('(' + tipsRegexpStr + ')');
						var code = tipsRegexpJson.code;
						var message = tipsRegexpJson.message;
						
						if (code.indexOf("/") == 0) {
							code = code.substring(1, code.length);
						}
						if (code.lastIndexOf("/") == code.length - 1) {
							code = code.substring(0, code.length - 1);
						}
						var re = new RegExp(code);
						var result = re.test($.trim($(this).val()));
						if (!result) {
							validatorData = false;
							addTipsHint(this,{msg:message,side:3,time:5});//添加tips校验提示
						}
					}
				}
			} else if ($(this).is(":radio") || $(this).is(":checkbox")) {
				if ($(this).attr("tips-message")) {
					if (!returnChecked(this)) {
						validatorData = false;
						addTipsHint(this);//添加tips校验提示
					} else {
						removeTipsHint(this);//移除tips校验提示
					}
				}
			}
		}
		
		//全部验证都通过
		if (validatorData) {
			removeTipsHint(this);//移除tips校验提示
		}
	});
	
	return validatorData;
}

/** tips校验辅助(添加tips校验提示) **/
function addTipsHint(obj,propertyJSON) {
	var side = 2, msg = $(obj).attr("tips-message"), bg = '#90ddfd', color = '#FF0000', time = 3;
	if (propertyJSON && propertyJSON.side) {
		side = propertyJSON.side;
	}
	if (propertyJSON && propertyJSON.bg) {
		bg = propertyJSON.bg;
	}
	if (propertyJSON && propertyJSON.color) {
		color = propertyJSON.color;
	}
	if (propertyJSON && propertyJSON.time) {
		time = propertyJSON.time;
	}
	if (propertyJSON && propertyJSON.msg) {
		msg = propertyJSON.msg;
	}
	if ($(obj).is("select") && $(obj).is(":hidden") && $(obj).next("div .chosen-container").length > 0) {
		//chosen下拉框模式
		if (!$(obj).next("div .chosen-container").hasClass("validator_hint")){
			$(obj).next("div .chosen-container").addClass("validator_hint");
		}
		$(obj).next("div .chosen-container").focus();
		$(obj).next("div .chosen-container").tips({side:side,msg:msg,bg:bg,color:color,time:time});
	} else if (($(obj).is(":radio") || $(obj).is(":checkbox")) && $(obj).next("ins").length > 0) {
		//icheck选择框模式
		$(obj).parent().addClass("validator_hint");
		$(obj).parent().focus();
		$(obj).parent().tips({side:side,msg:msg,bg:bg,color:color,time:time});
	} else {
		$(obj).addClass("validator_hint");
		$(obj).focus();
		$(obj).tips({side:side,msg:msg,bg:bg,color:color,time:time});
	}
}

/** tips校验辅助(移除tips校验提示) **/
function removeTipsHint(obj) {
	if ($(obj).is("select") && $(obj).is(":hidden") && $(obj).next("div .chosen-container").length > 0) {
		//chosen下拉框模式
		if ($(obj).next("div .chosen-container").hasClass("validator_hint")){
			$(obj).next("div .chosen-container").removeClass("validator_hint");
		}
	} else if (($(obj).is(":radio") || $(obj).is(":checkbox")) && $(obj).next("ins").length > 0) {
		//icheck选择框模式
		if ($(obj).parent().hasClass("validator_hint")) {
			$(obj).parent().removeClass("validator_hint");
		}
	} else {
		if ($(obj).val() != "" && !$(obj).is(":file")) {
			//try {
				var temp = $(obj).val();
				$(obj).val($.trim(temp));
			//} catch (e) {
				// TODO: handle exception
			//}
		}
		if ($(obj).hasClass("validator_hint")){
			$(obj).removeClass("validator_hint");
		}
	}
}

/** tips校验(区域标注提示方法) **/
function tipsRegionHint(region){
	$(region).find("input[type!='submit'][type!='reset'][type!='button'],textarea,select").each(function(){
		if (($(this).attr("tips-message") || $(this).attr("tips-min") || $(this).attr("tips-max") || $(this).attr("tips-identical") || $(this).attr("tips-different") || $(this).attr("tips-regexp")) && !$(this).hasClass("notVerify")) {
			var id = $(this).attr("id");
			$(region).find("label").each(function(index,n){
				if ($(n).attr("for") == id) {
					if ($(n).find("#font_" + id).length <= 0) {
						$(n).append("<font id='font_" + id + "' color='red'>✲</font>");
					}
				}
			});
		} else {
			var id = $(this).attr("id");
			$(region).find("label").each(function(index,n){
				if ($(n).attr("for") == id) {
					if ($(n).find("#font_" + id).length > 0 && $(n).find("#font_" + id).text() == "✲") {
						$(n).find("#font_" + id).remove(); 
					}
				}
			});
		}
	});
}

/** tips校验(区域去掉css样式标注,还原重现提示) **/
function tipsRegionHintAddVerify(region){
	$(region).find("input[type!='submit'][type!='reset'][type!='button'],textarea,select").each(function(){
		if (($(this).attr("tips-message") || $(this).attr("tips-min") || $(this).attr("tips-max") || $(this).attr("tips-identical") || $(this).attr("tips-different") || $(this).attr("tips-regexp")) && $(this).hasClass("notVerify")) {
			$(this).removeClass("notVerify");
		}
		if ($(this).is("select") && $(this).is(":hidden") && $(this).next("div .chosen-container").length > 0) {
			//chosen下拉框模式
			if ($(this).next("div .chosen-container").hasClass("validator_hint")){
				$(this).next("div .chosen-container").removeClass("validator_hint");
			}
		} else if (($(this).is(":radio") || $(this).is(":checkbox")) && $(this).next("ins").length > 0) {
			//icheck选择框模式
			if ($(this).parent().hasClass("validator_hint")) {
				$(this).parent().removeClass("validator_hint");
			}
		} else if ($(this).hasClass("validator_hint")) {
			$(this).removeClass("validator_hint");
		}
	});
	tipsRegionHint(region);
}

/** tips校验(区域添加css样式标注,屏蔽去掉提示) **/
function tipsRegionHintRemoveVerify(region){
	$(region).find("input[type!='submit'][type!='reset'][type!='button'],textarea,select").each(function(){
		if (($(this).attr("tips-message") || $(this).attr("tips-min") || $(this).attr("tips-max") || $(this).attr("tips-identical") || $(this).attr("tips-different") || $(this).attr("tips-regexp")) && !$(this).hasClass("notVerify")) {
			$(this).addClass("notVerify");
		}
		if ($(this).is("select") && $(this).is(":hidden") && $(this).next("div .chosen-container").length > 0) {
			//chosen下拉框模式
			if ($(this).next("div .chosen-container").hasClass("validator_hint")){
				$(this).next("div .chosen-container").removeClass("validator_hint");
			}
		} else if (($(this).is(":radio") || $(this).is(":checkbox")) && $(this).next("ins").length > 0) {
			//icheck选择框模式
			if ($(this).parent().hasClass("validator_hint")) {
				$(this).parent().removeClass("validator_hint");
			}
		} else if ($(this).hasClass("validator_hint")) {
			$(this).removeClass("validator_hint");
		}
	});
	tipsRegionHint(region);
}