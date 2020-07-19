package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.TempAttachment;

public interface TempAttachmentMgmtService {
	
	Pagination<TempAttachment> findPagination(Map<String, Object> param,
			Ordering order, Pagination<TempAttachment> page);

	void update(TempAttachment tempDetail);

	void remove(TempAttachment tempDetail);

	TempAttachment create(TempAttachment tempDetail);

	TempAttachment findById(long id);
	
	TempAttachment findByCertCode(String tempCode);

	/**
	 * @param i
	 * @return
	 */
	List<TempAttachment> findByTempId(int tempId);
	
	int updateImage(TempAttachment tempAttachment);
	
	TempAttachment createImage(TempAttachment tempAttachment);
	
	void removeImageByTempId(long tempId);
	
	void removeImageById(long id);
	
}
