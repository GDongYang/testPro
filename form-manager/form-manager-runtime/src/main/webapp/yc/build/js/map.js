//地图查询方法，还需删减无用代码
// 百度地图API功能
var map = new BMap.Map("l-map");
var myValue;
var to = null;
var geolocation = new BMap.Geolocation();
to = new BMap.Autocomplete({
    "input": "newAddress",
    "location": map,
});
to.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
    var _value = e.item.value;
    myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
    provinces.forEach((item,index) => {
        item.city.forEach((itt, idx) => {
              if (itt.name == _value.city) {
            	  if(item.name == _value.city){
            		  myValue = _value.city + _value.district + _value.street + _value.business;
            	  }else{
            		  myValue = item.name + _value.city + _value.district + _value.street + _value.business;
            	  }
              }
        })
    });
    $("#newAddress").val(myValue);
});

