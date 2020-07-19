/* 
 * ZJCA Key object Script Interface for HTML
 * Version: v1.0
 * Date: 2016-03-10
 * 2015 - 2016 ZJCA. All rights reserved.
 * 
 *
*/

function zjca_Cert(index, sn, alg, usage, dn, issuer, from, until, key_index) {
	var _index = index;
	var _sn = sn;
	var _alg = alg;
	var _usage = usage;
	var _dn = dn;
	var _issuer = issuer;
	var _from = from;
	var _until = until;
	var _key_index = key_index;
	
	this.getIndex = function() {
		return _index;
	}
	this.getSN = function() {
		return _sn;
	}
	this.getAlg = function() {
		return _alg;
	}
	this.getUsage = function() {
		return _usage;
	}
	this.getDN = function() {
		return _dn;
	}
	this.getSubjectCN = function() {
		var cn = "";
		var start = _dn.indexOf('CN=');
		var end = _dn.indexOf(',', start);
		if (-1 == end) {
			end = _dn.length;
		}
		if (start > 0 && end > start) {
			cn = _dn.substring(start + 3, end);			
		}
		return cn;
	}
	this.getIssuer = function() {
		return _issuer;
	}
	this.getIssuerCN = function() {
		var cn = "";
		var start = _issuer.indexOf('CN=');
		var end = _issuer.indexOf(',', start);
		if (-1 == end) {
			end = _issuer.length;
		}
		if (start > 0 && end > start) {
			cn = _issuer.substring(start + 3, end);			
		}
		return cn;		
	}
	this.getFrom = function() {
		return _from;
	}
	this.getUntil = function() {
		return _until;
	}
	this.getKeyIndex = function() {
		return _key_index;
	}
}