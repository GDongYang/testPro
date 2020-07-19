// 适配方法
function remMatchApi() {
	var equipmentWidth = document.documentElement.clientWidth;
	var bodyHtml = document.querySelector('html');
	bodyHtml.style.fontSize = 25 * (equipmentWidth / 375) + 'px';
	// 重新调用适配方法
	window.onresize = function() {
		remMatchApi()
	}
}
remMatchApi();
