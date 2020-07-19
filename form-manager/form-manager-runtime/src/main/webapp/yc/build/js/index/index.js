//获得slider插件对象
var gallery = mui('.mui-slider');
gallery.slider({
  interval:5000//自动轮播周期，若为0则不自动播放，默认为0；
});
var idnum = getQueryString('idnum');
//获取url的地址参数
function getQueryString(key) {
	const regExp = new RegExp(`(^|&)${key}=([^&]*)(&|$)`, 'i');
	const r = window.location.search.substr(1).match(regExp);
	if (r != null) return decodeURIComponent(r[2]);
	return '';
}
function openPage (page,id,name) {
	if (id == 10) {
		window.open(page+'?idnum='+idnum);
	} else if (id == 16) {
		window.open(page+idnum);
	} else {
		window.open(page+'?id='+id+'&name='+name);
	}
}
function openPageZLB (page,id,name) {
	dd.biz.util.openLink({
        url:page,
        onFail: function(error) {mui.alert('打开异常！');}
    });
}
function openPageHotItem(params) {
	window.open(params)
}