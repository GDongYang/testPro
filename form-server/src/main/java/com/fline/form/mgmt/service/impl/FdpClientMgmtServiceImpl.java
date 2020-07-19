package com.fline.form.mgmt.service.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fline.fdp.client.FileClient;
import com.fline.fdp.client.SearchEngineClient;
import com.fline.fdp.common.FDPResultSet;
import com.fline.fdp.common.exception.FDPException;
import com.fline.form.mgmt.service.FdpClientMgmtService;
import com.fline.form.constant.KeyConstant;

@Service
@Lazy(false)
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
			fileClient.writeData(key, content, labels, true);
			logger.info("Write file into bigdata success(" + key + ") : " );
			return true;
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
			Map<String, Object> json = (Map<String, Object>) JSONObject.toJSON(data);
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
	public boolean createFormInfo(Map<String, Object> data) {
		try {
			logger.info("form_solr_db:"+KeyConstant.FORM_SOLR_DB);
			client.indexData(KeyConstant.FORM_SOLR_DB, KeyConstant.FORM_INFO, data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public JSONObject loadFormInfo(String formBusiCode) {
		try {
			String sql = "select * from " + KeyConstant.FORM_INFO + " where FORM_BUSINESS_CODE='" + formBusiCode + "'";
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			if (rs.next()) {
				return rs.getCurrentObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveFormData(Map<String, Object> data) {
		try {
			client.indexAsyncData(username, KeyConstant.FORM_SOLR_DB, KeyConstant.FORM_DATA, data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public JSONObject loadFormData(String formBusiCode) {
		try {
			String sql = "select * from " + KeyConstant.FORM_DATA + " where formBusiCode='" + formBusiCode + "' order by dataCreated desc limit 0,1" ;
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			if (rs.next()) {
				return rs.getCurrentObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean savePayment(Object data) {
		try {
			JSONObject json = (JSONObject) JSONObject.toJSON(data);
			client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.PAYMENT, json);
			return true;
		} catch (FDPException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public JSONArray queryPayment(String sql) {
		JSONArray arr = new JSONArray();
		try {
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
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
	public boolean saveCallbackInfo(Object data) {
		try {
			JSONObject json = (JSONObject) JSONObject.toJSON(data);
			client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.ALI_CALLBACK_INFO, json);
			return true;
		} catch (FDPException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String queryCallbackInfo(String generatedUnicode) {

		JSONObject currentObj = null;
		String sql = "select * from " + KeyConstant.ALI_CALLBACK_INFO + " where projectId ='" + generatedUnicode + "' and methodName = 'supplementCallback'";
		FDPResultSet rs;
		try {
			rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				currentObj = rs.getCurrentObj();
			}
		} catch (FDPException | SQLException e) {
			logger.error("请求查询办件状态失败："+e.getMessage());
			e.printStackTrace();
		}
		String content = (currentObj!=null)?currentObj.getString("content"):"";
		return content;
	}
	
	@Override
	public String finishCallbackInfo(String generatedUnicode) {

		JSONObject currentObj = null;
		String sql = "select * from " + KeyConstant.ALI_CALLBACK_INFO + " where projectId ='" + generatedUnicode + "' and methodName = 'finishCallback'";
		FDPResultSet rs;
		try {
			rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				currentObj = rs.getCurrentObj();
			}
		} catch (FDPException | SQLException e) {
			logger.error("请求查询办件状态失败："+e.getMessage());
			e.printStackTrace();
		}
		String content = (currentObj!=null)?currentObj.getString("content"):"";
		return content;
	}

	

    @Override
    public boolean saveProjectInfo(Object data) {
        try {
            JSONObject json = (JSONObject) JSONObject.toJSON(data);
            client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.PROJECT_INFO, json);
            return true;
        } catch (FDPException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public String queryProjectInfo(String projectId) {
    	JSONObject currentObj = null;
    	String sql = "select * from " + KeyConstant.PROJECT_INFO + " where projectId ='" + projectId + "'";
    	FDPResultSet rs;
		try {
			rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				currentObj = rs.getCurrentObj();
			}
		} catch (FDPException | SQLException e) {
			logger.error("请求查询办件状态失败："+e.getMessage());
			e.printStackTrace();
		}
		String content = (currentObj!=null)?JSON.toJSONString(currentObj):"";
		return content;
    }

    @Override
    public boolean saveProjectLifecycle(Object data) {
        try {
            JSONObject json = (JSONObject) JSONObject.toJSON(data);
            client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.PROJECT_LIFECYCLE, json);
            return true;
        } catch (FDPException e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public String queryProjectInfoByfCode(String formBusiCode) {
		JSONObject currentObj = null;
    	String sql = "select * from " + KeyConstant.PROJECT_INFO + " where formBusiCode ='" + formBusiCode + "'";
    	FDPResultSet rs;
		try {
			rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				currentObj = rs.getCurrentObj();
			}
		} catch (FDPException | SQLException e) {
			logger.error("请求查询办件状态失败："+e.getMessage());
			e.printStackTrace();
		}
		String content = (currentObj!=null)?JSON.toJSONString(currentObj):"";
		return content;
	}

	@Override
	public boolean insertSmsIntoSolr(String tableName, Map<String, Object> data) {
		try {
			// JSONObject json = (JSONObject) JSONObject.toJSON(data);
			// client.indexAsyncData(username, KeyConstant.FORM_SOLR_DB,
			// tableName, json);
			client.indexData(KeyConstant.YZTB_SOLR_DB, tableName, data);
			return true;
		} catch (FDPException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String queryPayInfo(String noticeno, String ywcode) {
		JSONObject currentObj = null;
    	String sql = "select * from " + KeyConstant.PAY_INFO + " where payFormType = '1' and noticeno ='" + noticeno + "' and ywbh = '" + ywcode +"'";
    	FDPResultSet rs;
		try {
			rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				currentObj = rs.getCurrentObj();
			}
		} catch (FDPException | SQLException e) {
			logger.error("查询缴款单信息失败："+e.getMessage());
			e.printStackTrace();
		}
		String content = (currentObj!=null)?JSON.toJSONString(currentObj):"";
		return content;
	}

	@Override
	public boolean savePayResult(Object data) {
		try {
            JSONObject json = (JSONObject) JSONObject.toJSON(data);
            client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.PAY_RESULT_INFO, json);
            return true;
        } catch (FDPException e) {
            e.printStackTrace();
        }
		return false;
	}

	@Override
	public boolean savePayInfo(Object data) {
		try {
            JSONObject json = (JSONObject) JSONObject.toJSON(data);
            client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.PAY_INFO, json);
            return true;
        } catch (FDPException e) {
            e.printStackTrace();
        }
		return false;
	}

	@Override
	public String queryRefundInfo(String noticeno, String ywcode) {
		JSONObject currentObj = null;
    	String sql = "select * from " + KeyConstant.PAY_INFO + " where payFormType = '2' and noticeno ='" + noticeno + "' and ywbh = '" + ywcode +"'";
    	FDPResultSet rs;
		try {
			rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			while (rs.next()) {
				currentObj = rs.getCurrentObj();
			}
		} catch (FDPException | SQLException e) {
			logger.error("请求退款信息失败："+e.getMessage());
			e.printStackTrace();
		}
		String content = (currentObj!=null)?JSON.toJSONString(currentObj):"";
		return content;
	}

	@Override
	public boolean saveQuestionFeedbackVo(Object data) {
		try {
            JSONObject json = (JSONObject) JSONObject.toJSON(data);
            client.indexAsyncData(username,KeyConstant.FORM_SOLR_DB, KeyConstant.QUESTION_FEEDBACK, json);
            return true;
        } catch (FDPException e) {
            e.printStackTrace();
        }
		return false;
	}

    @Override
    public boolean saveRequestLog(Object data) {
        try {
            JSONObject json = (JSONObject) JSONObject.toJSON(data);
            client.indexAsyncData(username, KeyConstant.FORM_SOLR_DB, KeyConstant.REQUEST_LOG, json);
            return true;
        } catch (FDPException e) {
            e.printStackTrace();
        }
        return false;
    }

	@Override
	public JSONObject loadTempFormData(String projId) {
		try {
			String sql = "select * from " + KeyConstant.TEMP_FORM_DATA + " where FORM_BUSINESS_CODE='" + projId + "' order by update_time desc limit 0,1" ;
			FDPResultSet rs = (FDPResultSet) client.executeSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			if (rs.next()) {
				return rs.getCurrentObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean tempSaveFormData(Map<String, Object> formData) {
		try {
			String sql = "delete from "+ KeyConstant.TEMP_FORM_DATA +" where FORM_BUSINESS_CODE = '"+ formData.get("FORM_BUSINESS_CODE") +"';";
			client.executeDeleteSQL(username, KeyConstant.FORM_SOLR_DB, sql);
			client.indexAsyncData(username, KeyConstant.FORM_SOLR_DB, KeyConstant.TEMP_FORM_DATA, formData);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
