package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.ProjectInfoMgmtService;
import com.fline.form.vo.ProjectInfoVo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class ProjectInfoAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9168376085906167001L;
	private ProjectInfoVo projectInfoVo;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	@Resource
	private ProjectInfoMgmtService projectInfoMgmtService;
	private int pageNum;
	private int pageSize;
	private String queryDate;
	private String certcode;
	private String certNum;
	private String itemNum;
	private String startDate;
	private String endDate;
	private String projectId;
	private String pId;
	private String itemCode;
	private String projectNode;
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String findPage() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		if (Detect.notEmpty(certcode)) {
			param.put("certTempCode", certcode);
		}
		if (Detect.notEmpty(itemNum)) {
			param.put("itemCode", itemNum);
		}
		if (Detect.notEmpty(certNum)) {
			param.put("certNum", certNum);
		}
		if (Detect.notEmpty(projectId)) {
			param.put("projectId", projectId);
		}
		if (Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if (Detect.notEmpty(endDate)) {
			param.put("stopDate", endDate);
		}
		if (Detect.notEmpty(itemCode)) {
			param.put("qlCode", itemCode);
		}
		if (Detect.notEmpty(projectNode)) {
			param.put("projectNode", projectNode);
		}
		dataMap = projectInfoMgmtService.findPagination(param);
		return SUCCESS;
	}

	public String findByProjectId() {
		Map<String, Object> param = new HashMap<>();
		if (Detect.notEmpty(projectId)) {
			param.put("projectId", projectId);
		}
		param.put("pageNum", 1);
		param.put("pageSize", 10);

		dataMap = projectInfoMgmtService.findByProjectId(param);
		return SUCCESS;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String getCertcode() {
		return certcode;
	}

	public void setCertcode(String certcode) {
		this.certcode = certcode;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public ProjectInfoVo getProjectInfoVo() {
		return projectInfoVo;
	}

	public void setProjectInfoVo(ProjectInfoVo projectInfoVo) {
		this.projectInfoVo = projectInfoVo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getProjectNode() {
		return projectNode;
	}

	public void setProjectNode(String projectNode) {
		this.projectNode = projectNode;
	}

}
