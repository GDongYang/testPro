package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.util.JsonUtil;
import com.fline.form.mgmt.service.ProjectRepeatMgmtService;
import com.fline.form.vo.ResponseResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("projectRepeatMgmtService")
public class ProjectRepeatMgmtServiceImpl implements ProjectRepeatMgmtService {
	
	@Value(value = "${dataShare.projectRepeatUrl}")
	private String projectRepeatUrl;
	
	/**
	 * 调用Share提供过的接口进行推送
	 */
	@Override
	public String pushByProjectId(String projectId) {
		String result = "";
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String pushUrl = projectRepeatUrl;
		pushUrl += "/push/" + projectId;
		HttpPost httpPost = new HttpPost(pushUrl);
		try {
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				String resultString = EntityUtils.toString(entity, "UTF-8");
				ResponseResult<Integer> responseResult = JsonUtil.unmarshal(resultString, new TypeReference<ResponseResult<Integer>>(){});
				Integer resultData = responseResult.getData();
				result = (resultData != null && resultData == 1)? "重推请求成功" :"重推请求失败"; 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> findPage(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int pageNum = (int) params.get("pageNum");
		int pageSize = (int) params.get("pageSize");
		String sysStatus = (String) params.get("sysStatus");
		String ztStatus = (String) params.get("ztStatus");
		String startDate = (String) params.get("startDate");
		String endDate = (String) params.get("endDate");
		String projectId = (String) params.get("projectId");
		int startNum = (pageNum - 1) * pageSize;
		
		String sql = "select * from c_project_repeat where 1=1";
		String countSql = "select count(1) as total from c_project_repeat where 1=1";
		StringBuilder sb = new StringBuilder();
		if(Detect.notEmpty(startDate)) {
			sb.append(" and createTime > " + "'" + startDate + "'");
		}
		if(Detect.notEmpty(endDate)) {
			sb.append(" and createTime < " + "'" + endDate + "'");
		}
		if(Detect.notEmpty(sysStatus)) {
			sb.append(" and sysStatus = " + sysStatus);
		}
		if(Detect.notEmpty(ztStatus)) {
			sb.append(" and ztStatus = " + ztStatus);
		}
		if(Detect.notEmpty(projectId)) {
			sb.append(" and projectId = " + projectId);
		}
		BasicDataSource basicDs = new BasicDataSource();
		basicDs.setDriverClassName("com.mysql.jdbc.Driver");
		basicDs.setUrl("jdbc:mysql://172.18.19.36:3306/yztb?useUnicode=true&characterEncoding=utf-8");
		basicDs.setUsername("yztb");
		basicDs.setPassword("yztb_2019");
//		basicDs.setDriverClassName("com.mysql.jdbc.Driver");
//		basicDs.setUrl("jdbc:mysql://59.202.39.73:3306/yztb_test?useUnicode=true&characterEncoding=utf-8");
//		basicDs.setUsername("fdp");
//		basicDs.setPassword("fdp!@#");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDs);
		Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql + sb.toString());
		sb.append(" limit " + startNum + "," + pageSize);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql + sb.toString());
		resultMap.put("rows", results);
		resultMap.put("total", countMap.get("total"));
		return resultMap;
	}

	@Override
	public Map<String, Object> findProjectReateInfo(Map<String, Object> params) {
		Long id = (Long) params.get("id");
		String sql = "select * from c_project_repeat where id = " + id ;
		BasicDataSource basicDs = new BasicDataSource();
		basicDs.setDriverClassName("com.mysql.jdbc.Driver");
		basicDs.setUrl("jdbc:mysql://172.18.19.36:3306/yztb?useUnicode=true&characterEncoding=utf-8");
		basicDs.setUsername("yztb");
		basicDs.setPassword("yztb_2019");
//		basicDs.setDriverClassName("com.mysql.jdbc.Driver");
//		basicDs.setUrl("jdbc:mysql://59.202.39.73:3306/yztb_test?useUnicode=true&characterEncoding=utf-8");
//		basicDs.setUsername("fdp");
//		basicDs.setPassword("fdp!@#");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(basicDs);
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql);
		return resultMap;
	}

	

	
    

}
