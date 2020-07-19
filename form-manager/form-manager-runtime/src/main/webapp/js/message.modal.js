/** 重写弹出框 **/
//四个选项都是可选参数,Modal.alert附带一 个操作按键,Modal.confirm附带两个操作按键
//点击“确定” e: true
//点击“取消” e: false
/**
Modal.alert({ msg:'内容', title:'标题' });
Modal.alert({ msg:'内容', title:'标题', btnok:'确定' });
Modal.alert({ msg:'内容', title:'标题', btnok:'确定' }).on(function(e){ alert("返回结果：" + e); });

Modal.confirm({ msg:"是否删除数据？", title:'标题' });
Modal.confirm({ msg:"是否删除数据？", title:'标题', btnok:'确定' });
Modal.confirm({ msg:"是否删除数据？", title:'标题', btnok:'确定', btncl:'取消' });
Modal.confirm({ msg:"是否删除数据？", title:'标题', btnok:'确定', btncl:'取消' }).on(function(e){ alert("返回结果：" + e); });
**/
$(function(){
	window.Modal = function () {
		if ($('body').find("#message-alert").length <= 0) {
			var messageModal = 
			'<div id="message-alert" class="modal">' +
				'<div class="modal-dialog modal-sm">' +
					'<div class="modal-content">' +
						'<div class="modal-header">' +
							'<button type="button" class="close" data-dismiss="modal" id="messageModalCloseButton"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
							'<h5 class="modal-title"><i class="fa fa-exclamation-circle"></i> [Title]</h5>' +
						'</div>' +
						'<div class="modal-body small">' +
							'<p>[Message]</p>' +
						'</div>' +
						'<div class="modal-footer" >' +
							'<button type="button" class="btn btn-info ok" data-dismiss="modal">[BtnOk]</button>' +
							'<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>';
			$('body').append(messageModal);
		}
		
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var alr = $("#message-alert");
		var ahtml = alr.html();
		
		//关闭时恢复 modal html 原样，供下次调用时 replace 用
		//var _init = function () {
		//    alr.on("hidden.bs.modal", function (e) {
		//        $(this).html(ahtml);
		//    });
		//}();
		
		/* html 复原不在 _init() 里面做了，重复调用时会有问题，直接在 _alert/_confirm 里面做 */
		
		var _alert = function (options) {
		    alr.html(ahtml);    // 复原
		    alr.find('.ok').removeClass('btn-success').addClass('btn-info');
		    alr.find('.cancel').hide();
		    $("#messageModalCloseButton").hide();
		    _dialog(options);
		
		    return {
		        on: function (callback) {
		            if (callback && callback instanceof Function) {
		                alr.find('.ok').click(function () { callback(true); });
		            }
		        }
		    };
		};
		
		var _confirm = function (options) {
		    alr.html(ahtml); // 复原
		    alr.find('.ok').removeClass('btn-info').addClass('btn-success');
		    alr.find('.cancel').show();
		    _dialog(options);
		
		    return {
		        on: function (callback) {
		            if (callback && callback instanceof Function) {
		                alr.find('.ok').click(function () { callback(true); });
		                alr.find('.cancel').click(function () { callback(false); });
		            }
		        }
		    };
		};
		
		var _dialog = function (options) {
		    var ops = {
		        msg: "提示内容",
		        title: "操作提示",
		        btnok: "确定",
		        btncl: "取消"
		    };
		
		    $.extend(ops, options);
		
		    //console.log(alr);
		
		    var html = alr.html().replace(reg, function (node, key) {
		        return {
		            Title: ops.title,
		            Message: ops.msg,
		            BtnOk: ops.btnok,
		            BtnCancel: ops.btncl
		        }[key];
		    });
		    
		    alr.html(html);
		    alr.modal({
		        width: 500,
		        backdrop: 'static'
		    });
		};
		
		return {
		    alert: _alert,
		    confirm: _confirm
		};
	}();
});