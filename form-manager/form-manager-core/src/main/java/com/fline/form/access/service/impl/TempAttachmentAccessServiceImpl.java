package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.access.service.TempAttachmentAccessService;
import com.fline.form.vo.TempAttachmentVo;

public class TempAttachmentAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<TempAttachment> implements
        TempAttachmentAccessService {

	@Override
	public void removeByCode(String code) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", code);
		remove("removeByCode", params);
	}
	
	@Override
	public TempAttachment findByCertCode(String certCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("certCode", certCode);
		List<TempAttachment> list = find("findByCertCode", params);
		return Detect.notEmpty(list) ? list.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.access.service.TempAttachmentAccessService#removeByTempId(long)
	 */
	@Override
	public void removeByTempId(long tempId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tempId", tempId);
		remove("removeByTempId", params);
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.access.service.TempAttachmentAccessService#removeByTempIdAndType(long, int)
	 */
	@Override
	public void removeByTempIdAndType(long tempId, int type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tempId", tempId);
		params.put("type", type);
		remove("removeByTempIdAndType", params);
		
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.access.service.TempAttachmentAccessService#findByTempId(long)
	 */
	@Override
	public List<TempAttachment> findByTempId(long tempId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tempId", tempId);
		return find("findByTempId",params);
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.access.service.TempAttachmentAccessService#findAllVo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TempAttachmentVo> findAllVo() {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList(namespace + ".findAllVo");
	}

	/* (non-Javadoc)
	 * @see com.fline.yztb.access.service.TempAttachmentAccessService#findVoByMap(java.util.Map)
	 */
	@Override
	public List<TempAttachmentVo> findVoByMap(Map<String, Object> map) {
		return this.getIbatisDataAccessObject().find("TempAttachment.findVoByMap", map);
	}

	/* (non-Javadoc)
	 */
	@Override
	public int updateImage(TempAttachment tempAttachment) {
		int count = getIbatisDataAccessObject().update(namespace, "updateImage", tempAttachment);
		return count;
	}

	/* (non-Javadoc)
	 */
	@Override
	public TempAttachment createImage(TempAttachment tempAttachment) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tempId", tempAttachment.getTempId());
		params.put("type", tempAttachment.getType());
		params.put("content", tempAttachment.getContent());
		getIbatisDataAccessObject().getSqlMapClientTemplate().insert(namespace+".createImage", tempAttachment);
		return tempAttachment;
	}

	/* (non-Javadoc)
	 */
	@Override
	public void removeImageByTempId(long tempId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tempId", tempId);
		remove("removeImageByTempId", params);
	}

	/* (non-Javadoc)
	 */
	@Override
	public void removeImageById(long id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		remove("removeImage", params);
		
	}

	@Override
	public List<TempAttachment> findByCode(String code) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", code);
		return find("findByCode",params);
	}

}