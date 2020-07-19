package com.fline.form.mgmt.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;

/**
 * 大数据操作接口
 * 
 * @author Administrator
 *
 */
public interface FdpClientMgmtService {
	boolean writeFile(String key, Map<String, String> labels, byte[] content);

	byte[] readFile(String key);

	//将object对象存入solr
	boolean insertDataIntoSolr(String tableName, Object data);
	
	/**
	 * @param sql
	 * @return
	 * @Description 从solr的一证通办log库获取表单的相关信息
	 */
	JSONArray queryDataFromSolr(String sql);

	/**
	 * @param sql
	 * @return
	 * @Description 从solr的表单中心库获取表单的相关信息
	 */
	JSONArray queryDataFromFormSolrDb(String sql);

	/**
	 * @param sql
	 * @return
	 * @Description 从solr的表单中心库获取表单的相关信息
	 */
	Map<String, Object> queryFormFromSolr(String sql);

	/**
	 * @param sql
	 * @return
	 * @Description 从solr的一证通办log库获取表单的相关信息
	 */
	Map<String, Object> queryLogDbFromSolr(String sql);

	Map<String, Object> insertSmsIntoSolr(String sql, Map<String, Object> data);

	Map<String, Object> querySmsFromSolr(String sql);
	
	/**
	 * queryDataGroupByCount:获取分类数据. 
	 *
	 * @author 邵炜
	 * @param sql
	 * @return
	 */
	JSONArray queryItemDataGroupByCount(String sql);
	
	/**
	 * queryDataCount:获取数据的数量. 
	 *
	 * @author 邵炜
	 * @param sql
	 * @return
	 */
	Integer queryDataCount(String sql);
	/**
	 * @Description: 从表单中心获取数据分类的数量 
	 * @param sql
	 * @return JSONArray
	 */
	JSONArray queryCountFromFormCenter(String sql);

}
