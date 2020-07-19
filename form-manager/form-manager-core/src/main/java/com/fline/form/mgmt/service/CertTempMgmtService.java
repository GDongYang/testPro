package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.TempAttachment;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;
import com.fline.yztb.mgmt.service.impl.vo.ImportingFileParam;

public interface CertTempMgmtService {
	Pagination<CertTemp> findPagination(Map<String, Object> param,
			Ordering order, Pagination<CertTemp> page);

	void update(CertTemp certTemp);
	
	void update(CertTemp certTemp,String[] sealIds,String keyword, String signx,String signy);// 更新模板及关联的印章

	void remove(CertTemp certTemp);

	void removeTemplate(CertTemp certTemp);
	
	CertTemp create(CertTemp certTemp);
	
	CertTemp create(CertTemp certTemp,String[] sealIds,String keyword, String signx,String signy);
	
	void createCertSeal(CertTemp certTemp,String[] sealIds,String keyword, String signx,String signy);	//创建r_cert_seal关联数据

	CertTemp findById(long id);
	
	CertTemp findByCode(String code);
	
	List<CertTemp> findAll();
	
	List<CertTemp> findCropCertTempList();

	List<CertTemp> findByInnerCode(String itemCode);

	String updateActive(long id,int i);
	
	List<CertTemp> findByIds(String[] ids);
	
//	List<String> findRSeal(String certId);			//查找与当前模板相关联的所有印章的id号
	
//	List<Map<String,Object>> findRSeal(String certId);
	
	List<TempAttachment> findRSeal(String certId);
	
	void insertHistory(CertTemp certTemp);			//把数据插入到history表中
	
	CertTemp loadFormPageDef(long id);
	
	void saveFormPageDef(FormPageParamVo formParam);
	
	String importFormTemplate(ImportingFileParam param);
	
	String produceTemplateFile(CertTemp certTemp);	//指定位置生成模板文件
	
	void creatToCache(CertTemp certTemp);			//创建单个缓存数据
	
	List<CertTemp> findRelateVersion(Map<String,Object> params);//查看低/高版本的数据
	
	List<CertTemp> findHistoryVersion(Map<String,Object> params);//查看历史版本数据
	
	/**
	  * @Description : 材料和模板关联
	  * @author : shaowei
	  * @return
	  */
	void createRmaterialTempByKey(Map<String,Object> params);
	
	/**
	  * @Description : 更新关联的模板id
	  * @author : shaowei
	  * @return
	  */
	void updateRmaterialTempByNewId(Map<String,Object> params);
	
	/**
     * @Description: 根据类型获取证明
     * @param params type
     * @return     
     * @return List<CertTemp>
     */
    List<CertTemp> findAllByType (Map<String, Object> params);
    
    /**
     * @Description: 刷新指定证明的缓存
     * @param id  模板id 
     * @return void
     */
    void saveToCache(long id);
    
    /**
     * @Description : 查找已经发布的模板数据
     * @author : shaowei
     */
   List<CertTemp> findAllActive();
   
   List<CertTemp> findTempList(Map<String, Object> params);

    List<String> findByForm(String formCode);

    String copyTempsToOtherDepts(Map<String, Object> params);
}
