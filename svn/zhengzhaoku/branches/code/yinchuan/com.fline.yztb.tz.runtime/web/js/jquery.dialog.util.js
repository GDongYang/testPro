/**
 * 封装的消息弹框
 * 方法，如：var dialog = new jqueryDialog(); dialog.Container=""; dialog.Title="标题"; dialog.Message="内容"; dialog.show();
 * Container：(String) 控件选择器，如：null|""
 * Title：(String) 可使用html语言的控件标题，如：null|""
 * Name：(String) 容器窗口name属性，如：null|""
 * Width：(String|Integer) 容器窗口宽度，如："500"|500
 * Height：(String|Integer) 容器窗口高度，如："500"|500
 * MaxWidth：(String|Integer) 容器窗口最大宽度，如："500"|500
 * MaxHeight：(String|Integer) 容器窗口最大高度，如："500"|500
 * MinWidth：(String|Integer) 容器窗口最小宽度，如："500"|500
 * MinHeight：(String|Integer) 容器窗口最小高度，如："500"|500
 * DialogClass：(String) 容器css样式，如：null|""
 * Modal：(Boolean) 是否使用模式窗口，如：true|false
 * Position：(Json) 设置对话框显示位置，如：{ my: "center", at: "center center+5", of: window }
 * AutoOpen：(Boolean) 被调用的时候是否自动打开dialog窗口，如：true|false
 * Draggable：(Boolean) 是否可以拖动，如：true|false
 * Resizable：(Boolean) 是否可以调整大小，如：true|false
 * Bgiframe：(Boolean) 是否使用bgiframe插件解决IE6下无法遮盖select元素，如：true|false
 * AutoClose：(Boolean|Integer) 是否可以自动关闭，如：true|false|3000
 * ShowMinButton：(Boolean) 是否显示最小化按钮，如：true|false
 * ShowMaxButton：(Boolean) 是否显示最大化按钮，如：true|false
 * ShowCloseButton：(Boolean) 是否显示关闭按钮，如：true|false
 * IsMaxSize：(Boolean) 是否显示最大化尺寸，如：true|false
 * IsButtonClose：(Boolean) 是否点击按钮时触发关闭事件，如：true|false
 * IsShowScroll：(Boolean) 容器显示区域是否显示滚动条，如：true|false
 * IsWriteArray：(Boolean) 是否写入到数组中存储变量，如：true|false
 * IsRelativePosition：(Boolean) 容器是否相对定位,跟随滚动条移动定位，如：true|false
 * IsTitlebarDblclick：(Boolean) 容器标题区域是否加载双击事件，如：true|false
 * TabControlJSON：(Json) 选项卡按钮组json格式，如：{"name":function(){},"name1":function(){},"name2":function(){},"func":function(){}}
 * ButtonJSON：(Json) 按钮组json格式，如：{"name":function(){},"name1":function(){},"name2":function(){}}
 * Create：(Function) 监控dialog创建时的事件，如：function(){}
 * Focus：(Function) 监控dialog获取焦点时的事件，如：function(){}
 * DragStart：(Function) 监控dialog拖动开始时的事件，如：function(){}
 * Drag：(Function) 监控dialog拖动时的事件，如：function(){}
 * DragStop：(Function) 监控dialog拖动结束时的事件，如：function(){}
 * ResizeStart：(Function) 监控dialog改变窗体大小开始时的事件，如：function(){}
 * Resize：(Function) 监控dialog改变窗体大小时的事件，如：function(){}
 * ResizeStop：(Function) 监控dialog改变窗体大小结束时的事件，如：function(){}
 * BeforeClose：(Function) 监控关闭时触发的关闭前事件，如：function(){}
 * Open：(Function) 监控打开时触发的打开事件，如：function(){}
 * Close：(Function) 监控关闭时触发的关闭事件，如：function(){}
 * Max：(Function) 监控最大化时触发的事件，如：function(){}
 * Min：(Function) 监控最小化时触发的事件，如：function(){}
 * CloseOperation：(String) 关闭操作参数，如："close"|"destroy"|"clear"
 * CloseOnEscape：(Boolean) 按Esc键之后,是否关闭对话框，如：true|false
 * MinText：(String) 最小化按钮文字，如：null|""
 * MaxText：(String) 最大化按钮文字，如：null|""
 * CloseText：(String) 关闭按钮文字，如：null|""
 * AppendTo：(Object) 网页元素对象，默认加载到window对象上，如：$(window)|$(grandParent())
 * ZIndex：(String|Integer) 设置对话框层叠数，如："auto"|999
 * Show：(Json) 容器容器显示时的特效，如：{effect:'drop', direction:'up'}
 * Hide：(Json) 容器容器隐藏时的特效，如：{effect:'drop', direction:'down'}
 **/
var timer = null, delayTimer = null, continueTimer = null;//声明定时器变量
var dialogItems = new Array();

var jqueryDialog = function() {
	this.Container = null;//控件选择器
	this.Title = "　";//设置对话框标题
	this.Name = "";//设置对话框name属性
	this.Width = "80%";//设置对话框宽度
	this.Height = Math.round($(window).height() * 0.8);//设置对话框高度
	this.MaxWidth = "80%";//设置对话框宽度
	this.MaxHeight = Math.round($(window).height() * 0.8);//设置对话框高度
	this.MinWidth = "80%";//设置对话框宽度
	this.MinHeight = Math.round($(window).height() * 0.8);//设置对话框高度
	this.DialogClass = "";//添加额外的对话框CSS
	this.Modal = true;//是否使用模式窗口
	this.Position = { my: "center", at: "center center+5", of: window };//设置对话框显示位置
	this.AutoOpen = true;//被调用的时候是否自动打开dialog窗口
	this.Draggable = true;//是否可以拖动
	this.Resizable = true;//是否可以调整大小
	this.Bgiframe = false;//是否使用bgiframe插件解决IE6下无法遮盖select元素
	this.AutoClose = false;//是否可以自动关闭
	this.ShowMinButton = true;//是否显示最小化按钮
	this.ShowMaxButton = true;//是否显示最大化按钮
	this.ShowCloseButton = true;//是否显示关闭按钮
	this.IsMaxSize = false;//是否显示最大化尺寸
	this.IsButtonClose = true;//是否点击按钮时触发关闭事件
	this.IsShowScroll = true;//容器显示区域是否显示滚动条
	this.IsWriteArray = true;//是否写入到数组中存储变量
	this.IsRelativePosition = true;//容器是否相对定位,跟随滚动条移动定位
	this.IsTitlebarDblclick = true;//容器标题区域是否加载双击事件
	this.TabControlJSON = {};//选项卡JSON,格式：{"name":function(){},"name1":function(){},"name2":function(){},"func":function name(){}}
	this.ButtonJSON = {};//按钮JSON,格式：{"name":function(){},"name1":function(){},"name2":function(){}}
	this.Create = function(){};//监控dialog创建时的事件
	this.Focus = function(){};//监控dialog获取焦点时的事件
	this.DragStart = function(){};//监控dialog拖动开始时的事件
	this.Drag = function(){};//监控dialog拖动时的事件
	this.DragStop = function(){};//监控dialog拖动结束时的事件
	this.ResizeStart = function(){};//监控dialog改变窗体大小开始时的事件
	this.Resize = function(){};//监控dialog改变窗体大小时的事件
	this.ResizeStop = function(){};//监控dialog改变窗体大小结束时的事件
	this.BeforeClose = function(){};//监控关闭时触发的关闭前事件
	this.Open = function(){};//监控打开时触发的打开事件
	this.Close = function(){};//监控关闭时触发的关闭事件
	this.Max = function(){};//监控最大化时触发的事件
	this.Min = function(){};//监控最小化时触发的事件
	this.CloseOperation = "close";//关闭操作参数
	this.CloseOnEscape = true;//按Esc键之后,是否关闭对话框
	this.MinText = "";//最小化按钮文字
	this.MaxText = "";//最大化按钮文字
	this.CloseText = "";//关闭按钮文字
	this.AppendTo = $(window);//默认加载到window对象上
	this.ZIndex = "";//设置对话框层叠数
	this.Show = {effect:'drop', direction:'up'};
	this.Hide = {effect:'drop', direction:'down'};
	
	this.timestamp = "dialogMsg" + new Date().getTime();
};

jqueryDialog.prototype.dialog = function(option,key,value) {
	var confirmdiv = $("." + this.timestamp);
	if (typeof(option) != "undefined" && typeof(key) != "undefined" && typeof(value) != "undefined") {
		$(confirmdiv).dialog(option,key,value);
	} else if (typeof(option) != "undefined" && typeof(key) != "undefined") {
		if (typeof(key) == "object") {
			$(confirmdiv).dialog(option,key);
		} else {
			return $(confirmdiv).dialog(option,key);
		}
	} else if (typeof(option) != "undefined") {
		if (option == "isOpen" || option == "widget") {
			return $(confirmdiv).dialog(option);
		} else if (option == "close" || option == "destroy" || option == "clear") {
			$(confirmdiv).dialog("option").close(option,true);
		} else if (option == "open") {
			$(confirmdiv).dialog("option").open(option,true);
		} else if (option == "moveToTop") {
			$(confirmdiv).dialog("moveToTop");
		}
	}
};

jqueryDialog.prototype.create = function() {
	if (this.IsWriteArray) {
		dialogItems.push(this.timestamp);//添加dialog到数组中
	}
	
	if ($("body").find($(this.Container)).length > 0) {
		$(this.Container).addClass(this.timestamp).attr("dialogId",this.timestamp);
	} else {
		var _$newContainer = $('<div name="'+ this.Name +'" class="dialogMsg_css ' + this.timestamp + '" dialogId="' + this.timestamp + '" style="height:auto;" title="' + this.Title + '">' + this.Container + '</div>');
		$("body").append(_$newContainer);
	}
	
	var confirmdiv = $("." + this.timestamp);
	
	var _isMinimize = false;
	var _originalWidth = this.Width || "80%";
	var _originalHeight = this.Height || Math.round($(window).height() * 0.8);
	
	var objProperty = {
		_container: confirmdiv,
		_closeOperation: this.CloseOperation || "close",//此参数在变量中只作为传递变量,dialog中是没有该属性的
		_window: $(this.AppendTo),
		title: this.Title || "　",
		width: this.Width || "80%",
		height: this.Height || Math.round($(window).height() * 0.8),
		maxWidth: this.MaxWidth || "80%",
		maxHeight: this.MaxHeight || Math.round($(window).height() * 0.8),
		minWidth: this.MinWidth || "80%",
		minHeight: this.MinHeight || Math.round($(window).height() * 0.8),
		dialogClass: this.DialogClass || "",
		modal: this.Modal,
		position: this.Position || { my: "center", at: "center center+5", of: window },
		autoOpen: this.AutoOpen,
		draggable: this.Draggable,
		resizable: this.Resizable,
		bgiframe: this.Bgiframe,
		showMinButton: this.ShowMinButton,
		showMaxButton: this.ShowMaxButton,
		showCloseButton: this.ShowCloseButton,
		isMaxSize: this.IsMaxSize,
		isRelativePosition: this.IsRelativePosition,
		isTitlebarDblclick: this.IsTitlebarDblclick,
		tabControlJSON: this.TabControlJSON || {},
		buttons: this.ButtonJSON || {},
		createFunc: this.Create || function(){},
		focusFunc: this.Focus || function(){},
		dragStartFunc: this.DragStart || function(){},
		dragFunc: this.Drag || function(){},
		dragStopFunc: this.DragStop || function(){},
		resizeStartFunc: this.ResizeStart || function(){},
		resizeFunc: this.Resize || function(){},
		resizeStopFunc: this.ResizeStop || function(){},
		beforeCloseFunc: this.BeforeClose || function(){},
		openFunc: this.Open || function(){},
		closeFunc: this.Close || function(){},
		maxFunc: this.Max || function(){},
		minFunc: this.Min || function(){},
		closeOnEscape: this.CloseOnEscape,
		minText: this.MinText,
		maxText: this.MaxText,
		closeText: this.CloseText,
		appendTo: $($(this.AppendTo)[0].document.body),
		zIndex: this.ZIndex,
		show: this.Show,
		hide: this.Hide,
		create: function (event, ui) {
			//触发dialog创建时的事件
			objProperty.createFunc();
			
			//设置Dialog窗口层叠数
			if (objProperty.zIndex) {
				$(confirmdiv).parent().css({"z-index":objProperty.zIndex});
			}
		},
		focus: function (event, ui) {
			//触发dialog获取焦点时的事件
			objProperty.focusFunc();
		},
		dragStart: function (event, ui) {
			//触发dialog拖动开始时的事件
			objProperty.dragStartFunc();
		},
		drag: function (event, ui) {
			//触发dialog拖动时的事件
			objProperty.dragFunc();
		},
		dragStop: function (event, ui) {
			//触发dialog拖动结束时的事件
			objProperty.dragStopFunc();
		},
		resizeStart: function (event, ui) {
			//触发dialog改变窗体大小开始时的事件
			objProperty.resizeStartFunc();
		},
		resize: function (event, ui) {
			//触发dialog改变窗体大小时的事件
			objProperty.resizeFunc();
		},
		resizeStop: function (event, ui) {
			//触发dialog改变窗体大小结束时的事件
			objProperty.resizeStopFunc();
		},
		beforeClose: function (event, ui) {
			//触发关闭前调用的事件
			objProperty.beforeCloseFunc();
		},
		close: function (event, ui) {
			if (typeof(ui) == "boolean" && ui) {
				objProperty._closeOperation = event;
			}
			var dialogId = $(confirmdiv).attr("dialogId");
			//触发关闭调用的事件
			objProperty.closeFunc();
			if (objProperty._closeOperation == "close") {
				confirmdiv.dialog("close");//(dialog模拟层还存在),除非是新增元素,该特性就消失
			} else if (objProperty._closeOperation == "destroy") {
				confirmdiv.dialog("destroy");//(dialog模拟层消失)
			} else if (objProperty._closeOperation == "clear") {
				confirmdiv.dialog("destroy");//(dialog模拟层消失并且删除弹窗元素)
				if(isBrowser().ie) {
					removeElement($(confirmdiv)[0]);
				} else {
					$(confirmdiv).remove();
				}
			} else {
				confirmdiv.dialog("close");
			}
			
			dialogItems.remove(dialogId);//数组中清除
			
			if ($(confirmdiv).hasClass(dialogId)) {
				//删除唯一css标识及对应属性
				$(confirmdiv).removeClass(dialogId).removeAttr("dialogId");
				//判断是否是新增元素,如果是就删除该元素
				if ($(this).hasClass("dialogMsg_css")) {
					if(isBrowser().ie) {
						removeElement($(this)[0]);
					} else {
						$(this).remove();
					}
				}
			}
			
			//使原UI关闭按钮重现
			$(".ui-xlgwr").hide();
			$(".ui-dialog-titlebar-close").show();
		},
		open: function (event, ui) {
			//加载窗口选项卡按钮
			if (objProperty.tabControlJSON) {
				if ($(confirmdiv).parent().find(".ui-dialog-titlebar").find(".dialog-tab-control-area").length > 0) {
					//移除原绑定事件,重新加载事件
					$(confirmdiv).parent().find(".ui-dialog-titlebar .dialog-tab-control-area .dialog-tab-control").unbind("click");
					var dialogTabControlArea = $(confirmdiv).parent().find(".ui-dialog-titlebar .dialog-tab-control-area");
					if(isBrowser().ie) {
						removeElement($(dialogTabControlArea)[0]);
					} else {
						$(dialogTabControlArea).remove();
					}
				}
				$(confirmdiv).parent().find(".ui-dialog-titlebar").prepend("<div class=\"dialog-tab-control-area\"></div>");
				var tabControl = 0;
				var scriptHtml = "<script type=\"text/javascript\">\n";
				for (var obj in objProperty.tabControlJSON) {
					if (obj == "func") {
						scriptHtml = scriptHtml + objProperty.tabControlJSON[obj] + "\n";
					} else if (typeof(objProperty.tabControlJSON[obj]) == "function") {
						$(confirmdiv).parent().find(".dialog-tab-control-area").prepend("<div id=\"_dialogTab" + tabControl + "\" class=\"dialog-tab-control\" title=\"" + obj + "\">" + obj + "</div>");
						scriptHtml = scriptHtml + "$(\"#_dialogTab" + tabControl + "\").click(" + objProperty.tabControlJSON[obj] + ");\n";
						scriptHtml = scriptHtml + "$(\"#_dialogTab" + tabControl + "\").click(" + 
						"function(){\n$(this).parent().find('.dialog-tab-control').removeClass('over');\n$(this).addClass('over');\n});\n";
						tabControl++;
					}
				}
				scriptHtml = scriptHtml + "</script>\n";
				$(confirmdiv).parent().find(".dialog-tab-control-area").prepend(scriptHtml);
				
				if ($(confirmdiv).parent().find(".dialog-tab-control").length > 0) {
					$(confirmdiv).parent().find(".dialog-tab-control").eq(0).addClass('over');
				}
			}
			
			//加载窗口控制按钮
			var operationButtons = '<p class="ui-xlgwr">';
			if (objProperty.showMinButton) {
				var _dialogBtnText = "";
				if ($.trim(objProperty.minText) != "") {
					_dialogBtnText = "_dialogBtnText";
				}
				operationButtons = operationButtons + '<span class="_dialogMin ' + _dialogBtnText + '" name="min">' + objProperty.minText + '</span>';
			}
			if (objProperty.showMaxButton) {
				var _dialogBtnText = "";
				if ($.trim(objProperty.maxText) != "") {
					_dialogBtnText = "_dialogBtnText";
				}
				operationButtons = operationButtons + '<span class="_dialogMax ' + _dialogBtnText + '" name="max">' + objProperty.maxText + '</span>';
			}
			if (objProperty.showCloseButton) {
				var _dialogBtnText = "";
				if ($.trim(objProperty.closeText) != "") {
					_dialogBtnText = "_dialogBtnText";
				}
				operationButtons = operationButtons + '<span class="_dialogClose ' + _dialogBtnText + '" name="close">' + objProperty.closeText + '</span>';
			}
			operationButtons = operationButtons + '</p>';
			$(".ui-dialog-titlebar-close").hide();
			$(".ui-dialog-titlebar-close").parent().append(operationButtons);
			//移除原绑定事件,重新加载事件
			$(confirmdiv).parent().find(".ui-xlgwr>span").unbind("click");
			$(confirmdiv).parent().find(".ui-xlgwr>span").click(function () {
				var spanname = $(this).attr("name");
				//alert("ok:ui-span" + spanname);
				if (spanname == "max") {
					if (_isMinimize) {
						objProperty.width = _originalWidth;
						objProperty.height = _originalHeight;
						objProperty.draggable = true;
						objProperty.resizable = true;
					} else {
						objProperty.width = $(window).width();
						objProperty.height = $(window).height();
						objProperty.draggable = false;
						objProperty.resizable = false;
						var maxBtn = $(confirmdiv).parent().find("._dialogMax");
						if ($(maxBtn).attr("name","maxsel").hasClass("_dialogBtnText")) {
							$(maxBtn).attr("name","maxsel").attr("class","_dialogMaxsel").addClass("_dialogBtnText");
						} else {
							$(maxBtn).attr("name","maxsel").attr("class","_dialogMaxsel");
						}
					}
					confirmdiv.dialog({
						width: objProperty.width,
						height: objProperty.height,
						position: objProperty.position,
						draggable: objProperty.draggable,
						resizable: objProperty.resizable
					});
					//是否是最小化变量变更
					_isMinimize = false;
					//触发最大化按钮调用的事件
					objProperty.maxFunc();
				} else if (spanname == "maxsel") {
					if (_isMinimize) {
						objProperty.width = $(window).width();
						objProperty.height = $(window).height();
						objProperty.draggable = false;
						objProperty.resizable = false;
					} else {
						objProperty.width = _originalWidth;
						objProperty.height = _originalHeight;
						objProperty.draggable = true;
						objProperty.resizable = true;
						var maxBtn = $(confirmdiv).parent().find("._dialogMaxsel");
						if ($(maxBtn).attr("name","max").hasClass("_dialogBtnText")) {
							$(maxBtn).attr("name","max").attr("class","_dialogMax").addClass("_dialogBtnText");
						} else {
							$(maxBtn).attr("name","max").attr("class","_dialogMax");
						}
					}
					confirmdiv.dialog({
						width: objProperty.width,
						height: objProperty.height,
						position: objProperty.position,
						draggable: objProperty.draggable,
						resizable: objProperty.resizable
					});
					//是否是最小化变量变更
					_isMinimize = false;
					//触发最大化按钮调用的事件
					objProperty.maxFunc();
				} else if (spanname == "min") {
					confirmdiv.dialog({
						width: 0,
						height: 0,
						position: { my: "top", at: "top", of: window },
						draggable: true,
						resizable: true
					});
					//是否是最小化变量变更
					_isMinimize = true;
					//触发最小化按钮调用的事件
					objProperty.minFunc();
				} else if (spanname == "close") {
                	confirmdiv.dialog("close");
				} else {
					alert("请选择正确的图标,谢谢.");
				}
			});
			
			//标题栏是否有双击事件
			if (objProperty.isTitlebarDblclick) {
				$(confirmdiv).parent().find(".ui-dialog-titlebar").dblclick(function(){
					if ($(confirmdiv).parent().find(".ui-xlgwr>span._dialogMax").length > 0) {
						$(confirmdiv).parent().find(".ui-xlgwr>span._dialogMax").click();
					} else if ($(confirmdiv).parent().find(".ui-xlgwr>span._dialogMaxsel").length > 0) {
						$(confirmdiv).parent().find(".ui-xlgwr>span._dialogMaxsel").click();
					}
				});
			}
			
			//是否显示为最大化
			if (objProperty.isMaxSize) {
				//改变最大化按钮
				var maxBtn = $(confirmdiv).parent().find("._dialogMax");
				if ($(maxBtn).attr("name","maxsel").hasClass("_dialogBtnText")) {
					$(maxBtn).attr("name","maxsel").attr("class","_dialogMaxsel").addClass("_dialogBtnText");
				} else {
					$(maxBtn).attr("name","maxsel").attr("class","_dialogMaxsel");
				}
			}
			
			//触发打开调用的事件
			objProperty.openFunc();
		}
		
	};
	
	//先去加载,在窗体open及isMaxSize的时候再加特效
	if (objProperty.isMaxSize) {
		_originalWidth = objProperty.width;
		_originalHeight = objProperty.height;
		objProperty.position = { my: "center", at: "center center+5", of: window };
		objProperty.draggable = false;
		objProperty.resizable = false;
		objProperty.width = $(window).width();
		objProperty.height = $(window).height();
		//触发最大化按钮调用的事件
		objProperty.maxFunc();
	}
	
	confirmdiv.dialog(objProperty);
	
	//点击按钮时是否触发关闭事件
	if (this.IsButtonClose) {
		$(confirmdiv).parent().find(".ui-dialog-buttonset").find("button").each(function(){
			$(this).click(function () {
				try {
					confirmdiv.dialog("close");
				} catch (e) {}
			});
		});
	}
	
	//加载容器是否相对定位,跟随滚动条移动定位,配合jquery.mousewheel.js鼠标滚动脚本
	if (document.attachEvent) {
		document.attachEvent("onmousewheel", function(e) {
			loadDialogTop();//加载dialog定位Top
		});
	} else if (document.addEventListener) {
		if(isBrowser().firefox) {
			document.addEventListener("DOMMouseScroll", function(e) {
				loadDialogTop();//加载dialog定位Top
			}, false);
		} else {
			document.addEventListener("mousewheel", function(e) {
				loadDialogTop();//加载dialog定位Top
			}, false);
		}
	}
	//加载鼠标移动事件
	$(document).mousemove(function(e){
		loadDialogTop();//加载dialog定位Top
    });
	
	var loadDialogTopTimer = null;
	function loadDialogTop() {
		if(loadDialogTopTimer) clearTimeout(loadDialogTopTimer);//清除定时器
		loadDialogTopTimer = setTimeout(function () {
			var nowDialog = jqueryDialog.prototype.getNowDialog();
			if ($(nowDialog).length > 0 && nowDialog.dialog('option', 'isRelativePosition')) {
				var nowDialogWindow = nowDialog.dialog('option', '_window');
				var parentDomScrollTop = $($(nowDialogWindow)[0].document).scrollTop();
				var _nowDialogTop = ($(nowDialogWindow).height() - $(nowDialog.parent()).height()) / 2;
				if (_isMinimize) {
					$(nowDialog.parent()).css({"top": (parentDomScrollTop - 3) + "px"});
				} else {
					$(nowDialog.parent()).css({"top": (_nowDialogTop + parentDomScrollTop - 3) + "px"});
				}
			}
			clearTimeout(loadDialogTopTimer);//清除定时器
			loadDialogTopTimer = null;//定时器变量置null
		}, 500);
	}
};

jqueryDialog.prototype.show = function() {
	this.create();
	var confirmdiv = $("." + this.timestamp);
	//设置内部区域不显示滚动条
	if (!this.IsShowScroll) {
		$(confirmdiv).css({'overflow-y':'hidden'});
	}
	$(confirmdiv).show();
	
	//定时关闭Dialog窗口
	clearTimeout(timer);//清除定时器
	if(this.AutoClose != null && this.AutoClose != "" && this.AutoClose != 0) {
		timer = setTimeout(function () {
			//自动关闭
			confirmdiv.dialog("close");
        }, this.AutoClose);
	}
};

/** Dialog打开show()之后触发的延期执行事件 **/
jqueryDialog.prototype.delayEvent = function(timer,func) {
	clearTimeout(delayTimer);//清除定时器
	delayTimer = setTimeout(function () {
		func();
	}, timer);
};

/** Dialog打开show()之后触发的持续执行事件,注意清除掉定时器 **/
jqueryDialog.prototype.continueEvent = function(timer,func) {
	clearTimeout(continueTimer);//清除定时器
	continueTimer = setInterval(function () {
		func();
	}, timer);
};

/** Dialog打开show()之后动态加载按钮 **/
jqueryDialog.prototype.addButton = function(isReload,text,func) {
	var confirmdiv = $("." + this.timestamp);
	var buttons = $(confirmdiv).dialog('option', 'buttons');
	if (isReload) { buttons = {}; }
	buttons[text] = func;
	
	$(confirmdiv).dialog('option', 'buttons', buttons);
};

/** 根据下标查找Dialog对象 **/
jqueryDialog.prototype.getItemDialog = function(val) {
	if (!isNaN(val)){
		if (dialogItems[val]) {
			return $("." + dialogItems[val]);
		}
	}
	return null;
};

/** 查找上一个Dialog对象 **/
jqueryDialog.prototype.getBeforeDialog = function() {
	if (dialogItems.length > 1) {
		var length = dialogItems.length;
		return $("." + dialogItems[length - 2]);
	}
	return null;
};

/** 查找当前Dialog对象 **/
jqueryDialog.prototype.getNowDialog = function() {
	if (dialogItems.length > 0) {
		var length = dialogItems.length;
		return $("." + dialogItems[length - 1]);
	}
	return null;
};

/** 根据下标查找Dialog对象(全局调用方式) **/
var getItemDialog = function(val) {
	if (!isNaN(val)){
		if (dialogItems[val]) {
			return $("." + dialogItems[val]);
		}
	}
	return null;
};

/** 查找上一个Dialog对象(全局调用方式) **/
var getBeforeDialog = function() {
	if (dialogItems.length > 1) {
		var length = dialogItems.length;
		return $("." + dialogItems[length - 2]);
	}
	return null;
};

/** 查找当前Dialog对象(全局调用方式) **/
var getNowDialog = function() {
	if (dialogItems.length > 0) {
		var length = dialogItems.length;
		return $("." + dialogItems[length - 1]);
	}
	return null;
};

function removeElement(_element){
	var _parentElement = _element.parentNode;
	if(_parentElement) {
		_parentElement.removeChild(_element);
	}
}

//查寻最顶级窗口
function grandParent() {
	var win = window.top;
	while (win.opener) {
		win = win.opener.top;
	}
	return win;
}

/** 获取JqueryDialog **/
function getJqueryDialog(){
	var dialog = new jqueryDialog();
	var skinName = typeof($.cookie('skinName')) != "undefined" ?  $.cookie('skinName').split(',') : ["skin-blue base_skin-aqua","base_box-area-aqua","modal-aqua"];
	var dialogSkin = skinName[2];
	dialog.DialogClass = typeof(dialogSkin) != "undefined" ? dialogSkin : "modal-aqua";
	dialog.ResizeStop = function(){selectUpdated(".chosen-select-deselect");};//更新chosen下拉框长度
	dialog.Max = function(){selectUpdated(".chosen-select-deselect")};//更新chosen下拉框长度
	dialog.MinWidth = "500";
	dialog.MinHeight = "80";
	dialog.IsRelativePosition = false;
	dialog.IsTitlebarDblclick = false;
	dialog.IsButtonClose = false;
	dialog.ShowMinButton = false;
	return dialog;
}

function isBrowser() {
    var Sys = {};
    var ua=navigator.userAgent.toLowerCase();
    var s;
    (s=ua.match(/msie\s([\d.]+)/))?Sys.ie=s[1]:
    (s=ua.match(/trident.*rv:([\d.]+)/))?Sys.ie=s[1]:
    (s=ua.match(/firefox\/([\d.]+)/))?Sys.firefox=s[1]:
    (s=ua.match(/chrome\/([\d.]+)/))?Sys.chrome=s[1]:
    (s=ua.match(/opera.([\d.]+)/))?Sys.opera=s[1]:
    (s=ua.match(/version\/([\d.]+).*safari/))?Sys.safari=s[1]:0;
    //alert(JSON.stringify(Sys));
    return Sys;
/*	if(isBrowser().ie){//Js判断为IE浏览器
		alert(isBrowser().ie + " IE_have");
		if(isBrowser().ie=='9.0'){//Js判断为IE 9
			alert(isBrowser().ie + " IE_9.0");
		}else if(isBrowser().ie=='8.0'){//Js判断为IE 8
			alert(isBrowser().ie + " IE_8.0");
		}else{
			alert(isBrowser().ie + " IE_other");
		}
	}
	if(isBrowser().firefox){//Js判断为火狐(firefox)浏览器
		alert(isBrowser().firefox + " firefox");
	}
	if(isBrowser().chrome){//Js判断为谷歌chrome浏览器
		alert(isBrowser().chrome + " chrome");
	}
	if(isBrowser().opera){//Js判断为opera浏览器
		alert(isBrowser().opera + " opera");
	}
	if(isBrowser().safari){//Js判断为苹果safari浏览器
		alert(isBrowser().safari + " safari");
	}*/
}