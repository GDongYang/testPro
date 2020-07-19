package com.fline.form.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Item;
import com.fline.form.access.service.CertTempAccessService;
import com.fline.form.mgmt.service.CertTempMgmtService;
import com.fline.form.mgmt.service.ItemMgmtService;

/**
 * 该Controller 作为一表通享调取一证通办服务使用
 * @author hi
 *
 */
@Controller
@RequestMapping(value = "/yztbProvider")
public class YztbProviderController {
	
	@Autowired
	private ItemMgmtService itemMgmtService;
	
	@Autowired
	private CertTempMgmtService certTempMgmtService;
	
	@Autowired
	private CertTempAccessService certTempAccessService;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	/**
	 * 获取分页的Item列表接口
	 * @param itemName
	 * @param pageSize
	 * @param pageNum
	 */
	@RequestMapping(value = "/getItemList")
	public @ResponseBody String getItemList(@RequestParam(value = "itemName",required = false)String itemName,
											@RequestParam(value = "pageSize",required = false,defaultValue = "100")Integer pageSize,
											@RequestParam(value = "pageNum",required = false,defaultValue = "0")Integer pageNum) {
	try {
			Pagination<Item> page = new Pagination<Item>();
			page.setCounted(true);
			page.setIndex(pageNum);
			page.setSize(pageSize);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("name", itemName);
			Ordering order = new Ordering();
			Pagination<Item> pageData = itemMgmtService.findPaginationTable(param, order, page);
			dataMap.put("code", 600);
			if(pageData.getCount() > 0) {
				dataMap.put("message", "查询成功");
			}else {
				dataMap.put("message", "查询结果为空");
			}
			dataMap.put("data", pageData);
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}catch (Exception e) {
			dataMap.put("code", 800);
			dataMap.put("message", "服务器异常");
			dataMap.put("data","");
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}
	}
	
	/**
	 * 获取指定事项下模板
	 * @param itemCode
	 * @return
	 */
	@RequestMapping(value = "/getTermsByItemCode")
	public @ResponseBody String getTermsByItemCode(@RequestParam(value = "innerCode",required = true)String innerCode,
												   @RequestParam(value = "isMust",required = false,defaultValue = "")String isMust) {
		try {
			Item item = itemMgmtService.findByInnerCode(innerCode);	//根据itemCode获取item
			List<String> listStr = itemMgmtService.findRTemp(String.valueOf(item.getId()), isMust);
			String ids[] = listStr.toArray(new String[listStr.size()]);
			List<CertTemp> listCert = certTempMgmtService.findByIds(ids);
			dataMap.put("code", 600);
			if(listCert.size() > 0) {
				dataMap.put("message", "查询成功");
			}else {
				dataMap.put("message", "查询结果为空");
			}
			dataMap.put("data", listCert);
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}catch(Exception e) {
			dataMap.put("code", 800);
			dataMap.put("message", "服务器异常");
			dataMap.put("data","");
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}
	}
	
	/**
	 * 根据名字进行模糊查询申请表
	 * @param name
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getApplyTableList")
	public @ResponseBody String getApplyTableList(@RequestParam(value = "name",required= false) String name,
												  @RequestParam(value = "pageSize",required = false,defaultValue = "100")Integer pageSize,
												  @RequestParam(value = "pageNum",required = false,defaultValue = "0")Integer pageNum){
		try {
			Pagination<CertTemp> page = new Pagination<>();
			page.setCounted(true);
			page.setIndex(pageNum);
			page.setSize(pageSize);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", 3);
			param.put("nameLike", name);
			Ordering order = new Ordering();
			Pagination<CertTemp> pageData = certTempMgmtService.findPagination(param, order, page);
			dataMap.put("code", 600);
			if(pageData.getCount() > 0) {
				dataMap.put("message", "查询成功");
			}else {
				dataMap.put("message", "查询结果为空");
			}
			dataMap.put("data", pageData);
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}catch(Exception e) {
			dataMap.put("code", 800);
			dataMap.put("message", "服务器异常");
			dataMap.put("data","");
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}
	}
	/**
	 * 根据传递的ItemCode获取对应的详细数据
	 * @param innerCodesStr
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getItemsByItemCodeList")
	public @ResponseBody String getItemsByItemCodeList(@RequestParam(value = "innerCodesStr",required = false)String innerCodesStr,
													   @RequestParam(value = "pageSize",required = false,defaultValue = "100")Integer pageSize,
													   @RequestParam(value = "pageNum",required = false,defaultValue = "0")Integer pageNum){
		try {
			Pagination<Item> page = new Pagination<Item>();
			page.setCounted(true);
			page.setIndex(pageNum);
			page.setSize(pageSize);
			List<String>innerCodes = (List<String>) JSON.parse(innerCodesStr);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("innerCodes", innerCodes);
			//param.put("codes",codes);
			Ordering order = new Ordering();
			Pagination<Item> pageData = itemMgmtService.findPaginationTable(param, order, page);
			dataMap.put("code", 600);
			if(pageData.getCount() > 0) {
				dataMap.put("message", "查询成功");
			}else {
				dataMap.put("message", "查询结果为空");
			}
			dataMap.put("data", pageData);
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}catch(Exception e) {
			dataMap.put("code", 800);
			dataMap.put("message", "服务器异常");
			dataMap.put("data","");
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}
	}
	/**
	 * 通过证件/申请表的code获取到对应详细信息
	 * @param codesStr
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getCertTempByCodeList")
	public @ResponseBody String getCertTempByCodeList(@RequestParam(value = "codesStr",required = false)String codesStr,
													  @RequestParam(value = "pageSize",required = false,defaultValue = "100")Integer pageSize,
													  @RequestParam(value = "pageNum",required = false,defaultValue = "0")Integer pageNum) {
		try {
			Pagination<CertTemp> page = new Pagination<CertTemp>();
			page.setCounted(true);
			page.setIndex(pageNum);
			page.setSize(pageSize);
			List<String>codes = (List<String>) JSON.parse(codesStr);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("codes", codes);
			Ordering order = new Ordering();
			Pagination<CertTemp>pageData = certTempMgmtService.findPagination(param, order, page);
			dataMap.put("code", 600);
			if(pageData.getCount() > 0) {
				dataMap.put("message", "查询成功");
			}else {
				dataMap.put("message", "查询结果为空");
			}
			dataMap.put("data", pageData);
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}catch(Exception e) {
			dataMap.put("code", 800);
			dataMap.put("message", "服务器异常");
			dataMap.put("data","");
			String dataJson = JSON.toJSONString(dataMap);
			return dataJson;
		}
	}
}
