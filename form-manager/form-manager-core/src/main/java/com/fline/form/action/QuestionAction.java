package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Question;
import com.fline.form.mgmt.service.CertTempMgmtService;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.QuestionMgmtService;
import com.fline.form.util.RelationUtil;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangn
 * 2019-04-28
 * c_question表action类  
 */
public class QuestionAction extends AbstractAction implements ModelDriven<Question>{
	
	private static final long serialVersionUID = 1L;
	
	private QuestionMgmtService questionMgmtService;
	
	private Question question;
	
	@Autowired
	private DepartmentMgmtService departmentMgmtService;
	
	private Map<String,Object> responseData = new HashMap<String,Object>();
	
	private int pageNum;

	private int pageSize; 
	
	
	private String sort;
	
	private String quid;
	
	private CertTempMgmtService certTempMgmtService;
	
	public void setCertTempMgmtService(CertTempMgmtService certTempMgmtService) {
		this.certTempMgmtService = certTempMgmtService;
	}
	
	
	public String getQuid() {
		return quid;
	}

	public void setQuid(String quid) {
		this.quid = quid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public void setQuestionMgmtService(QuestionMgmtService questionMgmtService) {
		this.questionMgmtService = questionMgmtService;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}

	public Map<String, Object> getResponseData() {
		return responseData;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	@Override
	public Question getModel() {
		if(question == null){
			question = new Question();
		}
		return question;
	}
	/**
	 * 根据区域id查找所有模板
	 * @return
	 */
	public String findCerta(){
		
		Map<String, Object> param = new HashMap<String, Object>();
		//获取指定地区的部门
		Map<String, Object> deptParam = new HashMap<String, Object>();
		deptParam.put("deptId",quid);
		List<Department> allDepartment = departmentMgmtService.find(deptParam);
		//获取所有子节点
		List<String> list = new LinkedList();
		list.add(quid);									//防止传递空数据
		if( Detect.notEmpty(allDepartment)) {				//如果存在子部门
			long startTime = System.currentTimeMillis();
			list = RelationUtil.getDepartmentAllChildrenPoint(allDepartment,list,quid);//获取所有子孙节点的id
			long endTime = System.currentTimeMillis();
			System.out.println("花费时间为" + (endTime - startTime) + "ms");
		}
		param.put("deptIds",list);
		
		Pagination<CertTemp> page = new Pagination<CertTemp>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		
		Ordering order = new Ordering();
		order.addDesc(sort);
		// order.addDesc("CREATED");
		Pagination<CertTemp> pageData = certTempMgmtService.findPagination(
				param, order, page);
		responseData.put("total", pageData.getCount());
		responseData.put("rows",pageData.getItems());
		return SUCCESS;
	}
	
	/**
	 * find方法，查找数据
	 * @return
	 * @throws Exception
	 */
	public String findPagination() throws Exception{
		
		
		Pagination<Question> page = new Pagination<Question>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		
		

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name",question.getName());
		Ordering order = new Ordering();
		order.addDesc(sort);
		 //order.addDesc("CREATED");
		Pagination<Question> pageData = questionMgmtService.findPagination(
				param, order, page);
		
		responseData.put("total", pageData.getCount());
		responseData.put("rows",pageData.getItems());
		return SUCCESS;
		
	}
	
	/**
	 * 修改数据
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception{
		questionMgmtService.update(question);
		responseData.put("result", "成功");
		return SUCCESS;
	}
	
	/**
	 * 删除数据
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception{
		questionMgmtService.remove(question);
		responseData.put("result", "成功");
		return SUCCESS;
	}
	/**
	 * 增加数据
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception{
		
		questionMgmtService.create(question);
		responseData.put("result", "成功");
		
		return SUCCESS;
	}

	

}
