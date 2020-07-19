package com.fline.form.mgmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.access.service.TempAttachmentAccessService;
import com.fline.form.mgmt.service.TempAttachmentMgmtService;
@Service("tempAttachmentMgmtService")
public class TempAttachmentMgmtServiceImpl implements TempAttachmentMgmtService {
	@Resource
	private TempAttachmentAccessService tempAttachmentAccessService;


	@Override
	public Pagination<TempAttachment> findPagination(Map<String, Object> param,
			Ordering order, Pagination<TempAttachment> page) {
		return tempAttachmentAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(TempAttachment tempAttachment) {
		tempAttachmentAccessService.update(tempAttachment);
	}

	@Override
	public void remove(TempAttachment tempAttachment) {
		tempAttachmentAccessService.remove(tempAttachment);
	}

	@Override
	public TempAttachment create(TempAttachment tempAttachment) {

		tempAttachment = tempAttachmentAccessService.create(tempAttachment);

		return tempAttachment;
	}

	@Override
	public TempAttachment findById(long id) {
		return tempAttachmentAccessService.findById(id);
	}

	@Override
	public TempAttachment findByCertCode(String tempCode) {
		return tempAttachmentAccessService.findByCertCode(tempCode);
	}

	/* (non-Javadoc)
	 */
	@Override
	public List<TempAttachment> findByTempId(int tempId) {
		return tempAttachmentAccessService.findByTempId(tempId);
	}

	/* (non-Javadoc)
	 */
	@Override
	public int updateImage(TempAttachment tempAttachment) {
		return tempAttachmentAccessService.updateImage(tempAttachment);
	}

	/* (non-Javadoc)
	 */
	@Override
	public TempAttachment createImage(TempAttachment tempAttachment) {
		return tempAttachmentAccessService.createImage(tempAttachment);
	}

	/* (non-Javadoc)
	 */
	@Override
	public void removeImageByTempId(long tempId) {
		tempAttachmentAccessService.removeImageByTempId(tempId);
		
	}

	/* (non-Javadoc)
	 */
	@Override
	public void removeImageById(long id) {
		tempAttachmentAccessService.removeImageById(id);
	}

}
