package com.fline.form.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.common.action.AbstractAction;

public class SeniorCertAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916746433101845390L;
	private Map<String, Object> dataMap = new HashMap<>();

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}


	public String example() {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		map2.put("1", 1);
		map1.put("code", "1");
		map1.put("message", "11");
		map1.put("ip", "127.0.0.1");
		map1.put("success", true);
		map1.put("data", map2);
		map1.put("zsbh", "123");
		map1.put("xm", "zhangsan");
		map1.put("zjhm", "123456789987654321");
		map1.put("zymc", "111");
		map1.put("zgmc", "dfa");
		map1.put("qdzgsj", "2019-6-18 09:44:48");
		map1.put("pwhmc", "111");
		map1.put("url",
				"http://10.23.0.193/promise/PDFViewer.jsp?fileCode=%E9%99%88-201905-00003_913306815972489482_20190526104030_122.pdf&fileName=%E9%A1%B9%E7%9B%AE%E4%BF%A1%E6%81%AF%E7%99%BB%E8%AE%B0%E8%A1%A8");

		map3.put("code", "1");
		map3.put("message", "11");
		map3.put("ip", "127.0.0.1");
		map3.put("success", true);
		map3.put("data", map2);
		map3.put("zsbh", "123");
		map3.put("xm", "zhangsan");
		map3.put("zjhm", "123456789987654321");
		map3.put("zymc", "111");
		map3.put("zgmc", "dfa");
		map3.put("qdzgsj", "2019-6-18 09:44:48");
		map3.put("pwhmc", "111");
		map3.put("url",
				"http://10.23.0.193/promise/PDFViewer.jsp?fileCode=%E9%99%88-201905-00003_913306815972489482_20190526104030_122.pdf&fileName=%E9%A1%B9%E7%9B%AE%E4%BF%A1%E6%81%AF%E7%99%BB%E8%AE%B0%E8%A1%A8");

		list.add(map1);
		list.add(map3);
		dataMap.put("result", list);
		return SUCCESS;
	}

}
