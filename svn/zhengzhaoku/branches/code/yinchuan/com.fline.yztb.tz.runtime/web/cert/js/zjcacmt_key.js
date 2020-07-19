/* 
 * ZJCA Key object Script Interface for HTML
 * Version: v1.0
 * Date: 2016-03-10
 * 2015 - 2016 ZJCA. All rights reserved.
 * 
 *
*/

function zjca_Key(index, sn, label, manufacturer) {
	var _index = index;
	var _sn = sn;
	var _label = label;
	var _manufacturer = manufacturer;
	
	this.getIndex = function() {
		return _index;
	}
	this.getSN = function() {
		return _sn;
	}
	this.getLabel = function() {
		return _label;
	}
	this.getManufacturer = function() {
		return _manufacturer;
	}
}