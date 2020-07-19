package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.fdp.client.FileClient;
import com.fline.fdp.client.SearchEngineClient;
import com.fline.fdp.common.FDPResultSet;
import com.fline.fdp.common.exception.FDPException;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
@Service("fdpClientMgmtService")@Lazy(false)
public class FdpClientMgmtServiceImpl implements FdpClientMgmtService {

	private static Log logger = LogFactory.getLog(FdpClientMgmtServiceImpl.class);
	
	@Value("${basedata.url}")
	private String serverUri;
	@Value("${basedata.username}")
	private String username;
	@Value("${basedata.password}")
	private String password;

	//写结构化数据
	private SearchEngineClient client;
	//写非结构化数据
	private static FileClient fileClient;
	

	@PostConstruct
	public void init() {
		client = new SearchEngineClient(serverUri, username, password);
		fileClient = new FileClient(serverUri, username, password);
	}

	@Override
	public boolean writeFile(String key, Map<String, String> labels, byte[] content) {
		try {
			fileClient.writeData(key, content, labels, false);
			logger.info("Write file into bigdata success(" + key + ") : " );
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Write file into bigdata failed (" + key + ") : " + e.getMessage());
		}
		return false;
	}

	@Override
	public byte[] readFile(String key) {
		try {
			return fileClient.readData(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Read file from bigdata failed (" + key + ") : " + e.getMessage());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see 将结构化数据写入solr
	 */
	@Override
	public boolean insertDataIntoSolr(String tableName, Object data) {
		try {
			JSONObject json = (JSONObject) JSONObject.toJSON(data);
			client.indexAsyncData(username, KeyConstant.YZTB_SOLR_DB, tableName, json);
		} catch (FDPException e) {
			e.printStackTrace();
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see 从solr查询结构化数据
	 */
	@Override
	public JSONArray queryDataFromSolr(String sql) {
		JSONArray arr = new JSONArray();
		try {
//			JSONObject json = (JSONObject) JSONObject.toJSON(data);
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.YZTB_SOLR_DB, sql);
			while (rs.next()) {
			    //强制转成FDPResultSet类型后，可以将一条记录转成JSONObject。也可以使用rs.getInt， rs.getLong, rs.getString等方法获取字段对应的值
				arr.add(rs.getCurrentObj());
			}

		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	@Override
	public Map<String, Object> queryFormFromSolr(String sql) {
		Map<String, Object> resultMap = new HashMap<>();
		JSONArray arr = new JSONArray();
		try {
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);

			while (rs.next()) {
				arr.add(rs.getCurrentObj());
			}
			resultMap.put("JSONArray", arr);
			resultMap.put("total", rs.getCount());
		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> queryLogDbFromSolr(String sql) {
		JSONArray arr = new JSONArray();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.YZTB_SOLR_DB, sql);
			while (rs.next()) {
				arr.add(rs.getCurrentObj());
			}
			resultMap.put("JSONArray", arr);
			resultMap.put("total", rs.getCount());
		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	// @Override
	// public Map<String, Object> deleteFormFromSolr(String sql) {
	// Map<String, Object> resultMap = new HashMap<>();
	// JSONArray arr = new JSONArray();
	// try {
	// // FDPResultSet rs = (FDPResultSet) client.executeSQL(username,
	// // KeyConstant.FORM_SOLR_DB, sql);
	// // client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
	// client.executeDeleteSQL("", KeyConstant.FORM_SOLR_DB, sql);
	//
	// // while (rs.next()) {
	// // arr.add(rs.getCurrentObj());
	// // }
	// resultMap.put("JSONArray", arr);
	// // resultMap.put("total", rs.getCount());
	// } catch (FDPException e) {
	// e.printStackTrace();
	// }
	// return resultMap;
	// }

	@Override
	public Map<String, Object> insertSmsIntoSolr(String tableName, Map<String, Object> data) {
		try {
			// JSONObject json = (JSONObject) JSONObject.toJSON(data);
			// client.indexAsyncData(username, KeyConstant.FORM_SOLR_DB,
			// tableName, json);
			client.indexData(KeyConstant.YZTB_SOLR_DB, tableName, data);
		} catch (FDPException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> querySmsFromSolr(String sql) {
		JSONArray arr = new JSONArray();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.YZTB_SOLR_DB, sql);
			while (rs.next()) {
				arr.add(rs.getCurrentObj());
			}
			resultMap.put("JSONArray", arr);
			resultMap.put("total", rs.getCount());
		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public JSONArray queryDataFromFormSolrDb(String sql) {
		JSONArray arr = new JSONArray();
		try {
			// JSONObject json = (JSONObject) JSONObject.toJSON(data);
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				// 强制转成FDPResultSet类型后，可以将一条记录转成JSONObject。也可以使用rs.getInt，
				// rs.getLong, rs.getString等方法获取字段对应的值
				arr.add(rs.getCurrentObj());
			}

		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	@Override
	public JSONArray queryItemDataGroupByCount(String sql) {
		JSONArray arr = new JSONArray();
		try {
			JSONObject ob = null;
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.YZTB_SOLR_DB, sql);
			while (rs.next()) {
				ob = rs.getCurrentObj();
				ob.put("count", ob.getInteger("_fse_count_"));
				arr.add(ob);
			}
		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	

	@Override
	public Integer queryDataCount(String sql) {
		Integer arr = 0;
		try {
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.YZTB_SOLR_DB, sql);
			arr = rs.getCount();
		} catch (FDPException e) {
			e.printStackTrace();
		}
		return arr;
	}

	@Override
	public JSONArray queryCountFromFormCenter(String sql) {
		JSONArray arr = new JSONArray();
		try {
			JSONObject ob = null;
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				ob = rs.getCurrentObj();
				ob.put("count", ob.getInteger("_fse_count_"));
				arr.add(ob);
			}
		} catch (FDPException | SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
}
