package com.fline.request;

import com.fline.yztb.util.HttpRequest;

public class TestRsjInteface {
	
	private static String url = "http://10.23.65.246:18080/insiis/nettest";

	// 市民卡  http://10.71.172.156:8080/insiis/nettest?type=01&code01=110224196910144228
	
	// 个人参保证明（人员基本参保信息） http://10.71.172.156:8080/insiis/nettest?type=0201&code01=110224196910144228
		
	// 个人参保证明（单位信息）http://10.71.172.156:8080/insiis/nettest?type=0202&code01=110224196910144228
		
	// 个人参保证明  http://10.71.172.156:8080/insiis/nettest?type=0203&code01=110224196910144228&page=1
		
	// 养老退休证明基本信息  http://10.71.172.156:8080/insiis/nettest?type=0301&code01=330625195004078198
		
	// 养老退休月发放金额记录 http://10.71.172.156:8080/insiis/nettest?type=0202&code01=110224196910144228
		
	// 伤残数据   http://10.71.172.156:8080/insiis/nettest?type=04&code01=330625196312303873
	
	public static void main(String[] args) throws Exception { 
		
		String result = HttpRequest.sendGetGBK(url, "type=0201&code01=330681199008192077"); 
		System.out.println(result);
		
		result = HttpRequest.sendGetGBK("http://10.23.65.246:18080/insiis/nettest", 
							"type=0203&code01=330681199008192077&page=1");
		System.out.println(result); 
	}
	
}
