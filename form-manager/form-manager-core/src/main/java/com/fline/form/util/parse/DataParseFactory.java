package com.fline.form.util.parse;

import com.fline.form.constant.Contants;


/**
 * 数据解析工厂类
 * @author smj
 * @date 2017-07-12
 */
public class DataParseFactory {
	
	public static IDataParse factory(String dataType){
		if("xml".equalsIgnoreCase(dataType) || Contants.XML.equals(dataType)){
			return new XmlDataParse();
		}else{
			return new JsonDataParse();
		}
	}
}
