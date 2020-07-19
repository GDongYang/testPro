package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.CertTemp;
import com.fline.form.vo.CertTempVo;

public interface CertTempAccessService extends AbstractNamespaceAccessService<CertTemp> {

	CertTemp findByCode(String code);

	List<CertTemp> findCropCertTempList();

	List<CertTemp> findByInnerCode(String innerCode);

	void updateActive(long id, int active);

	List<CertTemp> findByIds(String[] ids);
	
	void createCertSeal(Map<String,Object> params);
	
//	List<String> findRSeal(Map<String,Object> params);
	
	List<Map<String,Object>> findRSeal(Map<String,Object> params);
	
	void insertHistory(CertTemp certTemp);
	
	void deleteCertSeal(Map<String,Object> params);

	CertTemp loadFormPageDef(long id);

	void saveFormPageDef(long id, String content, String htmlContent, byte[] image);

    List<CertTempVo> findAllVo();
    
    long createHighVersion(CertTemp highVersionCert);
    
    List<CertTemp> findRelateVersion(Map<String,Object> params);
    
    void copyToRelations(Map<String,Object> params);
    
    List<CertTemp> findHistotyVersion(Map<String,Object> params);
    
    /**
      * @Description : 首次材料和模板关联
      * @author : shaowei
      * @return params
      */
    void createRmaterialTempByKey(Map<String,Object> params);
    
    /**
      * @Description : 更新关联的模板id
      * @author : shaowei
      * @return
      */
    void updateRmaterialTempByNewId(Map<String,Object> params);
    
    /**
      * @Description : 查找已经发布的模板数据
      * @author : shaowei
      * @return
      */
    List<CertTemp> findAllActive();
    
    /**
     * @Description: 根据材料的Id获取证明信息
     * @param params
     * @return List<CertTemp>
     */
    List<CertTemp> findByMaterial(Map<String, Object> params);
    
    /**
     * @Description: 根据证明ID删除 材料证明的关联关系
     * @param params     
     * @return void
     */
    void removeRelationByTempId(Map<String, Object> params);
    
    /**
     * @Description: 根据类型获取证明
     * @param params type
     * @return     
     * @return List<CertTemp>
     */
    List<CertTemp> findAllByType (Map<String, Object> params);
    
    /**
     * @Description: 查询证件列表
     * @param params
     * @return List<CertTemp>
     */
    List<CertTemp> findTempList (Map<String, Object> params);
    /**
     * @Description: 执行复制证件功能的实现
     * @param params     
     * @return void
     */
    void createTempList(Map<String, Object> params);

    List<String> findByForm(String formCode);
}
