package com.fline.form.access.service;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.vo.TempAttachmentVo;

public interface TempAttachmentAccessService extends AbstractNamespaceAccessService<TempAttachment>{
	void removeByCode(String code);
	
	TempAttachment findByCertCode(String certCode);
	
	void removeByTempId(long tempId);
	
	void removeByTempIdAndType(long tempId,int type);
	
	List<TempAttachment> findByTempId(long tempId);
	
	List<TempAttachment> findByCode(String code);

	/**
	 * @return
	 */
	List<TempAttachmentVo> findAllVo();

	/**
	 * @param map
	 * @return
	 */
	List<TempAttachmentVo> findVoByMap(Map<String, Object> map);
	
	int updateImage(TempAttachment tempAttachment);
	
	TempAttachment createImage(TempAttachment tempAttachment);
	
	void removeImageByTempId(long tempId);
	
	void removeImageById(long id);
}
