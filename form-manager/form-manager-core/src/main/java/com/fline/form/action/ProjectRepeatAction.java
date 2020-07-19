package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.fline.form.access.model.ProjectRepeat;
import com.fline.form.mgmt.service.ProjectRepeatMgmtService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ProjectRepeatAction extends AbstractAction{
	
	@Autowired
	private ProjectRepeatMgmtService projectRepeatMgmtService;
	
	private int pageNum;
	
	private int pageSize;
	
	private String startDate;
	
	private String endDate;
	
	private String projectId;
	
	private String sysStatus;
	
	private String ztStatus;
	
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	public void setZtStatus(String ztStatus) {
		this.ztStatus = ztStatus;
	}

	private ProjectRepeat projectRepeat;
	
	private long id;
	
	private Map<String, Object> dataMap = new HashMap<String, Object>();

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setProjectRepeat(ProjectRepeat projectRepeat) {
		this.projectRepeat = projectRepeat;
	}
	
	public ProjectRepeat getProjectRepeat() {
		return projectRepeat;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String findPage() {
		Map<String, Object> params = new HashMap<>();
//		params.put("projectId", projectRepeat.getProjectId());
//		params.put("sysStatus", projectRepeat.getSysStatus());
//		params.put("ztStatus", projectRepeat.getZtStatus());
		params.put("pageSize", pageSize);
		params.put("pageNum", pageNum);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("ztStatus", ztStatus);
		params.put("sysStatus", sysStatus);
		params.put("projectId", projectId);
		dataMap = projectRepeatMgmtService.findPage(params);
		return SUCCESS;
	}
	
	public String findProjectReateInfo() {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		Map<String, Object> result = projectRepeatMgmtService.findProjectReateInfo(params);
		dataMap.put("rows", result);
		dataMap.put("total", result.size());
		return SUCCESS;
	}
	
	public String repushOne() {
		String result  = projectRepeatMgmtService.pushByProjectId(projectId);
		dataMap.put("msg", result);	
		return SUCCESS;
	}
	
	
}
