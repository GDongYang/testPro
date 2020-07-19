package com.fline.form.action;

import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.mgmt.service.CertTempMgmtService;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CertTempAction extends AbstractAction implements
		ModelDriven<CertTemp> {

	private static final long serialVersionUID = 960312586160086237L;

	private CertTempMgmtService certTempMgmtService;
	
	@Autowired
	private DepartmentMgmtService departmentMgmtService;
	
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	
	private CertTemp certTemp;

	private int pageNum;

	private int pageSize;
	
	private String sort;
	
	private String order;
	
	private String departId = "1";
	
	private String fileName;
	
	private String innerCode;
	
	private String[] sealCodes;
	
	private String keyword;				//关键字
	
	private String signx;				//x轴坐标
	
	private String signy;				//y轴坐标
	
	private String findByActive;		//是否需要根据发布进行查询  1为需要使用Active查询  ""则不需要
	
	private String contentType;
	
	private String nameLike;
	
	private File imgFile;
	
	private long[] deptIds;				//证件复制时传入的目的部门ID号
	
	private long[] tempIds;				//证件复制时传入的证件ID号

    private String formCode;

    public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private InputStream fileStream;

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	
	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setCertTempMgmtService(CertTempMgmtService certTempMgmtService) {
		this.certTempMgmtService = certTempMgmtService;
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

	public CertTemp getCertTemp() {
		return certTemp;
	}

	public void setCertTemp(CertTemp certTemp) {
		this.certTemp = certTemp;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSignx() {
		return signx;
	}

	public void setSignx(String signx) {
		this.signx = signx;
	}

	public String getSigny() {
		return signy;
	}

	public void setSigny(String signy) {
		this.signy = signy;
	}
	
	public String getFindByActive() {
		return findByActive;
	}

	public void setFindByActive(String findByActive) {
		this.findByActive = findByActive;
	}
	public String[] getSealCodes() {
		return sealCodes;
	}

	public void setSealCodes(String[] sealCodes) {
		this.sealCodes = sealCodes;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getNameLike() {
		return nameLike;
	}

	public long[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(long[] deptIds) {
		this.deptIds = deptIds;
	}

	public long[] getTempIds() {
		return tempIds;
	}

	public void setTempIds(long[] tempIds) {
		this.tempIds = tempIds;
	}

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String save() {
		//根据前台数据active自动填充
		certTemp.setVersion(1);
		CertTemp certTempNew = certTempMgmtService.create(certTemp,sealCodes, keyword, signx, signy);//新增certTemp
		certTempNew = certTempMgmtService.findById(certTempNew.getId());
		certTempMgmtService.creatToCache(certTempNew);
		dataMap.put("msg", "新增成功!");
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
		certTemp.setImage(image);
		certTempMgmtService.update(certTemp,sealCodes,keyword,signx,signy);
		dataMap.put("msg", "修改成功!");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis != null) {
				fis.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
		return SUCCESS;
	}

	public String updateActive() {
		try {
			String resulMsg = certTempMgmtService.updateActive(certTemp.getId(), certTemp.getActive());//更新active状态为已发布
			dataMap.put("resultMsg", resulMsg);
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("resultMsg", "发布失败,请重试!");
		}
		return SUCCESS;
	}

	public String remove() {
		certTempMgmtService.remove(certTemp);//刪除模板数据、印章的关联关系
		return SUCCESS;
	}
	
	public String removeTemplate() {
		//待优化
//		CertTemp oldCertTemp = certTempMgmtService.findById(certTemp.getId());
//		certTempMgmtService.insertHistory(oldCertTemp);		//将删除前的数据插入到history表中
		certTempMgmtService.removeTemplate(certTemp);
		return SUCCESS;
	}
	
	public String findAll(){
		List<CertTemp> listTemp=certTempMgmtService.findAll();
		dataMap.put("result", listTemp);
		return "findAll";
	}
	
	public String findById() {
		CertTemp certTemp1 = certTempMgmtService.findById(certTemp.getId());
		dataMap.put("certTemp", certTemp1);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<CertTemp> page = new Pagination<CertTemp>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("active",certTemp.getActive());				//查询的发布状态
		param.put("findByActive", findByActive);				//是否需要查询
		if(certTemp.getType() != 0) {
			param.put("type", certTemp.getType());
		}
		param.put("nameLike",nameLike);
		param.put("nameLike", certTemp.getName());
		Ordering order = new Ordering();
		order.addDesc(sort);

        if(Detect.notEmpty(departId) && !departId.equals("1")) {//如果不是顶级节点则进入
            param.put("deptIdLike", departId);
        }
		Pagination<CertTemp> pageData = certTempMgmtService.findPagination(param, order, page);
		dataMap.put("rows", pageData.getItems());
        dataMap.put("total", pageData.getCount());
		return SUCCESS;
	}
	
	public String getDownload() {
		certTemp = certTempMgmtService.findByCode(certTemp.getCode());
		String realpath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String filepath = realpath + File.separator + "templates" + File.separator 
				+ certTemp.getCatalogCode() + File.separator + certTemp.getCode() + ".pdf";
		this.fileName = UUID.randomUUID() + ".pdf";
		try {
			fileStream = new FileInputStream(new File(filepath));
            contentType = "application/pdf";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			dataMap.put("resultMsg", "模板不存在!");
			return SUCCESS;
		}
		return "stream";
	}
	
	public String getImage() {
        certTemp = certTempMgmtService.findByCode(certTemp.getCode());
        byte[] image = certTemp.getImage();
        fileStream = new ByteArrayInputStream(image);
        contentType = "image/png";
        return "stream";
    }
	
	public String findByInnerCode() {
		List<CertTemp> list = certTempMgmtService.findByInnerCode(innerCode);
		dataMap.put("result", list);
		return SUCCESS;
	}
	/**
	 * 查询与当前模板相关联的所有印章id号
	 * @return
	 */
	public String findRSeal() {
//		List<String> list = certTempMgmtService.findRSeal(String.valueOf(certTemp.getId()));
		List<TempAttachment> list = certTempMgmtService.findRSeal(String.valueOf(certTemp.getId()));
		dataMap.put("result", list);
		return SUCCESS;
	}
	public String haveHighVersion() {
		CertTemp findCertTemp = certTempMgmtService.findById(certTemp.getId());
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("version", findCertTemp.getVersion());
		params.put("code", findCertTemp.getCode());
		params.put("active", findCertTemp.getActive());
		List<CertTemp> highVersionCerts = certTempMgmtService.findRelateVersion(params);
		dataMap.put("data", highVersionCerts);
		return SUCCESS;
	}
	
	@Override
	public CertTemp getModel() {
		if (certTemp == null) {
			certTemp = new CertTemp();
		}
		return certTemp;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
	/**
	 * 查看当前证明的历史版本
	 * @return
	 */
	public String findHistoryVersion() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", certTemp.getCode());
		List<CertTemp> result = certTempMgmtService.findHistoryVersion(params);
		dataMap.put("data", result);
		return SUCCESS;
	}
	/**
	 * 查看相关的版本 如传入已发布则查询草稿 如传入草稿则查询已发布
	 * @return
	 */
	public String findRelateVersion() {
		CertTemp oldCertTemp = certTempMgmtService.findById(certTemp.getId());
		List<CertTemp> relateVersions = null;
		//如有待发布的版本则更新待发布的版本
		if(oldCertTemp != null && oldCertTemp.getActive() == 1) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("version", oldCertTemp.getVersion());
			params.put("code", oldCertTemp.getCode());
			params.put("active", oldCertTemp.getActive()); //active = 1 查看低版本
			relateVersions = certTempMgmtService.findRelateVersion(params);
		}
		dataMap.put("data", relateVersions);
		return SUCCESS;
	}
	
	public String findFormTemp() {
		//获取所有表单数据
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("type", certTemp.getType());
		List<CertTemp> certTemps = certTempMgmtService.findAllByType(params);
		dataMap.put("result", certTemps);
		return SUCCESS;
	}
	/**
	 * @Description: 将单个证明刷入缓存
	 * @return     
	 * @return String
	 */
	public String saveToCache() {
		try {
			certTempMgmtService.saveToCache(certTemp.getId());
			dataMap.put("msg", "更新缓存成功!");
		}catch (Exception e) {
			dataMap.put("msg", "更新缓存失败，请重试");
		}
		return SUCCESS;
	}
	/**
	 * @Description: 获取所有已发布的证件 
	 * @return String
	 */
	public String findAllActive() {
		List<CertTemp> certTemps = certTempMgmtService.findAllActive();
		dataMap.put("result", certTemps);
		return SUCCESS;
	}
	/**
	 * @Description: 获取证明列表
	 * @return String
	 */
	public String findTempList() {
		Map<String, Object> params = new HashMap<>();
		if(Detect.notEmpty(departId) && !departId.equals("1")) {//如果不是顶级节点则进入
            params.put("deptIdLike", departId);
        }
		List<CertTemp> tempList = certTempMgmtService.findTempList(params);
		dataMap.put("result", tempList);
		return SUCCESS;
	}
	/**
	 * @Description: 复制证件到其他的部门
	 * @return String
	 */
	public String copyTempsToOtherDept() {
		Map<String, Object> params = new HashMap<>();
		params.put("tempIds", tempIds);
		params.put("deptIds", deptIds);
		String result = certTempMgmtService.copyTempsToOtherDepts(params);
		dataMap.put("msg", result);
		return SUCCESS; 
	}

    public String findList() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", certTemp.getType());
        List<CertTemp> certTemps = certTempMgmtService.findTempList(params);
        dataMap.put("result", certTemps);
        return SUCCESS;
    }

    public String findByForm() {
        List<String> tempCodes = certTempMgmtService.findByForm(formCode);
        dataMap.put("tempCodes", tempCodes);
        return SUCCESS;
    }
}
