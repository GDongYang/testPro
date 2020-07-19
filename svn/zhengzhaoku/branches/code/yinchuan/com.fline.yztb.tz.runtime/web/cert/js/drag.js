"use strict";
var box = document.getElementById('owidow'),
	double = document.getElementById('double');

//添加事件的跨游览器解决方法

var eventUtil = {
	addHandle: function(element, type , fun){
		try {
			if ( element.addEventListener ) {
				element.addEventListener(type, fun);
			}else if (element.attachEvent) {
				element.attachEvent('on' + type, fun);
			}else{
				element['on' + type] = fun;
			}
		} catch (e) {
			// TODO: handle exception
		}
	},
	removeHandle: function(element, type , fun){
		if ( element.removeEventListener ) {
			element.removeEventListener(type, fun);
		}else if (element.detachEvent) {
			element.detachEvent('on' + type, fun);
		}else{
			element['on' + type] = null;
		}
	},
	getEvent:function(event){
        return event?event:window.event;
    },
	getType:function(event){
		return event.type;
	},
	getElement:function(event){
		return event.target || event.srcElement;
	},
	preventDefault:function(event){
		if(event.preventDefault){
		  	event.preventDefault();
		}else{
		  	event.returnValue=false;
		}
	},
	stopPropagation:function(event){
		if(event.stopPropagation){
	 		event.stopPropagation();
		}else{
	 		event.cancelBubble=true;
		}
	}
};

var towage = function(ele, ele_parent){
	eventUtil.addHandle(ele, 'mousedown', function(e){
		var event = e || window.event;
		var x = event.clientX - box.offsetLeft ,
			y = event.clientY - box.offsetTop ;

		move(x, y, ele_parent);  //移动

		document.onmouseup = function(){  //移除鼠标事件
			document.onmousemove = null;
			document.onmouseup = null;
		};
	});
}(double,box);

function move(x, y, ele_parent){
	document.onmousemove = function(e){
		var e = e || window.event,
		    left = e.clientX - x,
			top = e.clientY - y;	
			
		//获取网页可见区域宽高
		var winHeight = document.body.clientHeight || document.documentElement.clientHeight,
		    winWidth = document.body.clientWidth || document.documentElement.clientWidth;

		//判断是否超出网页可见区域
		if (left < 0) {  
			left = 0;
		}else if (left > winWidth - ele_parent.clientWidth) {
			left = winWidth - ele_parent.clientWidth;
		}
		if (top < 0) {
			top = 0;
		}else if (top > winHeight - ele_parent.clientHeight) {
			top = winHeight - ele_parent.clientHeight;
		}
		// console.log(ele_parent.clientWidth, ele_parent.clientHeight, left, top);
		box.style.left = left + 162 + 'px';
		box.style.top = top + 220 + 'px';
	};
}