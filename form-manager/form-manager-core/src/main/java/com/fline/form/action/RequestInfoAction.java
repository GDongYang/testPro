package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.fline.form.mgmt.service.RequestInfoMgmtService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author panym
 * @date 2019年9月26日上午10:47:32
 * @Description:
 */
public class RequestInfoAction extends AbstractAction {
	
	private static final long serialVersionUID = 6726636404835216673L;
	
	private Log logger = LogFactory.getLog(RequestInfoAction.class);

	private String requestName;			//接口的名称
	
	private String startDate;			//起始日期
	
	private String endDate;				//结束日期
	
	private Integer status;				//接口调用的结果状态 1:成功 -1:失败
	
	private String source;				//接口的来源 治安、交管、出入境、网安、监管、有数
	
	private Integer pageNum;			//当前的页数
	
	private Integer pageSize;			//当前分页大小
	
	private Integer type;				//获取指定时间段的 接口调用数量 1:今日 0:总数
	
	private String fileName;			//导出的文件名
	
	private InputStream excelStream;	//作为流输出
	
	private String month;				//月份
	
	private String year;				//年份
	
	@Resource
	private RequestInfoMgmtService requestInfoMgmtService;
	
	private Map<String, Object> dataMap = new HashMap<String, Object>();	//作为结果数据返回

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
	public String getFileName() {
		return fileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @Description: 分页 条件 查询 获取数据
	 * @return String
	 */
	public String getInfos() {
		Map<String, Object> param = new HashMap<String,Object>();
		if(Detect.notEmpty(requestName)) {
			param.put("requestName", requestName);
		}
		if(Detect.notEmpty(startDate)) {
			param.put("startDate", startDate);
		}
		if(Detect.notEmpty(endDate)) {
			param.put("endDate", endDate);
		}
		if(Detect.notEmpty(source)) {
			param.put("source", source);
		}
		if(status != null) {
			param.put("status", status);
		}
		dataMap = requestInfoMgmtService.getInfos(param,pageNum, pageSize);
		return SUCCESS;
	}
	
	/*根据类型获取统计数据*/
	public String getCounts() {
		dataMap = requestInfoMgmtService.getCounts(type);
		return SUCCESS;
	}
	
}
	