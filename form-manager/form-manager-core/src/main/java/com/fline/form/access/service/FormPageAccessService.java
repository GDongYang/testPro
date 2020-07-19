package com.fline.form.access.service;

import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.yztb.access.model.FormPage;
import com.fline.yztb.vo.FormPageVo;

import java.util.List;

public interface FormPageAccessService extends AbstractNamespaceAccessService<FormPage> {
	
	FormPage findByCode(String code);
	
	FormPage findImagesById(long id);

    void saveFormPageDef(long id, String appContent, byte[] appImage, String onlineContent, byte[] onlineImage,
                         String offlineContent, byte[] offlineImage,String terminalContent,byte[] terminalImage, int postType, int payType);

    List<FormPageVo> findAllVo();
	/**
	 * @Description: 发布表单,置active = 1 并且版本号加1
	 * @param params formPageId : 表单的ID号
	 * @return void
	 */
	void updateActive(Map<String, Object> params);
	
	/**
	 * @Description: 获取需要缓存的字段
	 * @param params : formPageId: 表单的id号
	 * @return List<FormPage>
	 */
	FormPageVo findForCache(Map<String, Object> params);
	
	List<FormPage> findList(Map<String, Object> params);
	
	void copyFormsToOtherDepts(Map<String, Object> params);
	
	FormPage findAllById(long id);
	
	void updateVersion(long id);

    void removeFormTemp(String formCode);

    void saveFormTemp(String formCode, String[] tempCodes);
}
