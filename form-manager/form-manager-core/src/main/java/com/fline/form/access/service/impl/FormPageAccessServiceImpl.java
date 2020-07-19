package com.fline.form.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.feixian.tp.common.util.Detect;
import com.fline.yztb.access.model.FormPage;
import com.fline.form.access.service.FormPageAccessService;
import com.fline.yztb.vo.FormPageVo;

public class FormPageAccessServiceImpl extends
		AbstractNamespaceAccessServiceImpl<FormPage> implements
		FormPageAccessService {

	@Override
	public FormPage findByCode(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", code);
        List<FormPage> list = find(param);
        if(Detect.notEmpty(list) && list.size() > 0){
        	return list.get(0);
        }
		return null;
	}
	
	@Override
	public FormPage findImagesById(long id) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
		return findOne("findImagesById", param);
	}

	@Override
    public void saveFormPageDef(long id, String appContent, byte[] appImage, String onlineContent, byte[] onlineImage,
                                String offlineContent, byte[] offlineImage,String terminalContent,byte[] terminalImage, int postType, int payType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("appContent", appContent);
        params.put("appImage", appImage);
        params.put("onlineContent", onlineContent);
        params.put("onlineImage", onlineImage);
        params.put("offlineContent", offlineContent);
        params.put("offlineImage", offlineImage);
        params.put("terminalContent", terminalContent);
        params.put("terminalImage", terminalImage);
        params.put("postType", postType);
        params.put("payType",payType);
        update("updateContent", params);
    }

	@Override
	public void updateActive(Map<String, Object> params) {
		update("updateActive",params);
	}

	@Override
	public List<FormPageVo> findAllVo() {
	    return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList(namespace + ".findAllVo");
    }

	@Override
	public FormPageVo findForCache(Map<String, Object> params) {
		return (FormPageVo) this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForObject(namespace + ".findForCache", params);
	}

	@Override
	public List<FormPage> findList(Map<String, Object> params) {
		return find("findList",params);
	}

	@Override
	public void copyFormsToOtherDepts(Map<String, Object> params) {
		getIbatisDataAccessObject().getSqlMapClientTemplate().insert("FormPage.createFormList", params);
	}

	@Override
	public FormPage findAllById(long id) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
		return findOne("findAllById", param);
	}

	@Override
	public void updateVersion(long id) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
		update("updateVersion",param);
	}

	@Override
	public void removeFormTemp(String formCode) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("formCode", formCode);
	    remove("removeFormTemp", param);
    }

    @Override
    public void saveFormTemp(String formCode, String[] tempCodes) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("formCode", formCode);
	    param.put("tempCodes", tempCodes);
        getIbatisDataAccessObject().getSqlMapClientTemplate().insert("FormPage.saveFormTemp", param);
    }

}
