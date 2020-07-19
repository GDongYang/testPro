package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.SealInfoMgmtService;
import com.fline.form.util.RelationUtil;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;

public class SealInfoAction extends AbstractAction implements ModelDriven<SealInfo>{
	
	private static final long serialVersionUID = 1L;
	
	private SealInfoMgmtService sealInfoMgmtService;
	
	private SealInfo sealInfo;
	
	private Map<String,Object> responseData = new HashMap<String,Object>();
	
	private int pageNum;

	private int pageSize; 
	
	private File fileExcel;
	
	private String[] certTempIdS;
	
	private String[] area;
	
	private String fileName;
	
	private InputStream fileStream;
	
	private File imgFile;
	
	private String departId;
	
	private String sort;
	
	private String keyword;
	
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	
	@Autowired
	private DepartmentMgmtService departmentMgmtService;
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	
	public void setSealInfoMgmtService(SealInfoMgmtService sealInfoMgmtService) {
		this.sealInfoMgmtService = sealInfoMgmtService;
	}

	public void setSealInfo(SealInfo sealInfo) {
		this.sealInfo = sealInfo;
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

	public File getFileExcel() {
		return fileExcel;
	}

	public void setFileExcel(File fileExcel) {
		this.fileExcel = fileExcel;
	}

	public String[] getCertTempIdS() {
		return certTempIdS;
	}

	public void setCertTempIdS(String[] certTempIdS) {
		this.certTempIdS = certTempIdS;
	}

	public String[] getArea() {
		return area;
	}

	public void setArea(String[] area) {
		this.area = area;
	}
	
	
	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	
	@Override
	public SealInfo getModel() {
		if(sealInfo == null){
			sealInfo = new SealInfo();
		}
		return sealInfo;
	}
	
	
	public String findPagination() throws Exception{
		Pagination<SealInfo> page = new Pagination<SealInfo>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);
		
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("departmentId", departId);
		param.put("visible", sealInfo.getVisible());
		param.put("name", sealInfo.getName());
		Ordering order = new Ordering();
		order.addDesc("id");
		// order.addDesc("CREATED");
		if(Detect.notEmpty(departId) && !departId.equals("1")) {//如果不是顶级节点则进入
			//获取指定地区的部门
			Map<String, Object> deptParam = new HashMap<String, Object>();
			deptParam.put("deptId",departId);
			List<Department> allDepartment = departmentMgmtService.find(deptParam);
			//获取所有子节点
			List<String> list = new LinkedList();
			list.add(departId);									//防止传递空数据
			if( Detect.notEmpty(allDepartment)) {				//如果存在子部门
				long startTime = System.currentTimeMillis();
				list = RelationUtil.getDepartmentAllChildrenPoint(allDepartment,list,departId);//获取所有子孙节点的id
				long endTime = System.currentTimeMillis();
				System.out.println("花费时间为" + (endTime - startTime) + "ms");
			}
			param.put("deptIds",list);
		}
		Pagination<SealInfo> pageData = sealInfoMgmtService.findPagination(
				param, order, page);
		
		responseData.put("total", pageData.getCount());
		responseData.put("rows",pageData.getItems());
		return SUCCESS;
		
		
		
	}
	
	
	public String  findByidkeyword() throws Exception{
		responseData = sealInfoMgmtService.findByidkeyword(sealInfo.getId());
		return SUCCESS;
	}
	
	

	public String update() throws Exception{
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			byte[] image = null;
			if(imgFile != null) {
				fis = new FileInputStream(imgFile);
				bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n;
				while ((n = fis.read(buf)) != -1) {
					bos.write(buf, 0, n);
				}
				image = bos.toByteArray();
			}
			sealInfo.setImage(image);
			sealInfoMgmtService.update(sealInfo,certTempIdS,area,keyword);
			responseData.put("msg", "修改成功!");
		}catch(Exception e) {
			e.printStackTrace();
			responseData.put("msg", "修改失败!");
		}finally {
			if(fis != null) {
				fis.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
		return SUCCESS;
	}
	
	
	public String updateActive() throws Exception{
		sealInfoMgmtService.updateActive(sealInfo);
		responseData.put("result", "成功");
		return SUCCESS;
	}
	

	public String remove() throws Exception{
		sealInfoMgmtService.remove(sealInfo);
		responseData.put("result", "成功");
		return SUCCESS;
	}

	public String create() throws Exception{
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			byte[] image = null;
			if(imgFile != null) {
				fis = new FileInputStream(imgFile);
				bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n;
				while ((n = fis.read(buf)) != -1) {
					bos.write(buf, 0, n);
				}
				image = bos.toByteArray();
			}
			sealInfo.setVisible("0");
			sealInfo.setVersion(1);
			sealInfo.setImage(image);
			sealInfoMgmtService.create(sealInfo,certTempIdS,area,keyword);
			responseData.put("active",sealInfo.getVisible() );
			responseData.put("msg", "新增成功!");
		}catch(Exception e) {
			e.printStackTrace();
			responseData.put("msg", "新增失败!");
		}finally {
			if(fis != null) {
				fis.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
		return SUCCESS;
	}

	public String findById() throws Exception{
		SealInfo sif = sealInfoMgmtService.findById(sealInfo.getId());
		responseData.put("result", sif);
		return SUCCESS;
		
	}
	
	public String findAll() throws Exception{
		List<SealInfo> listSealInfo = sealInfoMgmtService.findAll();
		responseData.put("result", listSealInfo);
		return SUCCESS;
	}
	
	/**
	 * 根据印章名称查询
	 * @param
	 * @return
	 */
	public String findByName(){
		SealInfo sif = sealInfoMgmtService.findByName(sealInfo.getName());
		responseData.put("result", sif);
		return SUCCESS;
	}
	
	public String analysisExcel() throws Exception{
		sealInfoMgmtService.analysisExcel(fileExcel);
		responseData.put("result", "成功");
		return SUCCESS;
	}
	
	public String findCertSeal() {
		List<TempAttachment> list = sealInfoMgmtService.findCertSeal(sealInfo.getCode());
		Set<String> certs = new HashSet<>();
		if(Detect.notEmpty(list)) {
			for(TempAttachment tempAttachment : list) {
				certs.add(tempAttachment.getTempId() + "");
			}
		}
		responseData.put("result", "成功");
		responseData.put("certs", certs);
		//area_code字段数据库已删除
		return SUCCESS;
	}
	
	/**
	 * 根据id获取图片
	 * @return
	 */
	public String getImageById() {
		try {
			SealInfo findSeal = sealInfoMgmtService.findById(sealInfo.getId());
			if(findSeal!=null && Detect.notEmpty(findSeal.getImage())) {
				fileStream = new ByteArrayInputStream(findSeal.getImage());
				return "imageStream";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		responseData.put("msg", "图片不存在!");
		return SUCCESS;
	}
}
