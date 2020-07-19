package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.yztb.access.model.CertResource;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.access.service.*;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.*;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;
import com.fline.yztb.mgmt.service.impl.vo.ImportingFileParam;
import com.fline.form.util.WordToHtml;
import com.fline.form.vo.CertTempVo;
import com.fline.form.vo.TempAttachmentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
@Service("certTempMgmtService")
public class CertTempMgmtServiceImpl implements CertTempMgmtService, Cacheable {
	@Resource
	private CertTempAccessService certTempAccessService;
	@Resource
	private DepartmentMgmtService departmentMgmtService;
	@Resource
	private CertResourceMgmtService certResourceMgmtService;
	@Resource
	private TempAttachmentAccessService tempAttachmentAccessService;
	@Resource
	private DataCacheService dataCacheService;
	@Resource
	private SealInfoAccessService sealInfoAccessService;
	@Resource
	private CertResourceAccessService certResourceAccessService;
	@Resource
	private ItemAccessService itemAccessService;
	
	
	@Override
	public Pagination<CertTemp> findPagination(Map<String, Object> param,
			Ordering order, Pagination<CertTemp> page) {
		Pagination<CertTemp> pt =  certTempAccessService.findPagination(param, order, page);
		return pt;
	}

	@Override
	public void update(CertTemp certTemp) {
		certTempAccessService.update(certTemp);
		CertTempVo certTempVo = certTemp.toVo();
		dataCacheService.setCertTemp(certTempVo);
	}

	@Override
	public void remove(CertTemp certTemp) {
		certTempAccessService.remove(certTemp);			//删除模板
		tempAttachmentAccessService.removeByTempId(certTemp.getId());//删除关联的印章数据
		certResourceAccessService.removeByCert(certTemp.getCode());//certResource删除关联
		dataCacheService.removeRedis(KeyConstant.YZTB_CERT_TEMP, certTemp.getCode());
	}
	@Override
	public void removeTemplate(CertTemp certTemp) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tempId", certTemp.getId());
		//将旧版本放入history表
		CertTemp oldCertTemp = certTempAccessService.findById(certTemp.getId());
		certTempAccessService.insertHistory(oldCertTemp);
		//获取旧版本的证明
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("certId", certTemp.getId());
		params.put("formCode", "");
		params.put("formCodeDel", oldCertTemp.getCode());
		//itemAccessService.bindFormTemp(params);		//设置事项的formCode为空
		certTempAccessService.remove(certTemp);			//删除模板
		tempAttachmentAccessService.removeByTempId(certTemp.getId());//删除关联模板的所有数据，印章，二维码等
		certResourceAccessService.removeByCert(certTemp.getCode());//certResource删除关联
		certTempAccessService.removeRelationByTempId(params);//删除关联里的数据
		dataCacheService.removeRedis(KeyConstant.YZTB_CERT_TEMP, certTemp.getCode());
	}
	@Override
	public CertTemp create(CertTemp certTemp) {
		Department department = departmentMgmtService.findById(certTemp.getDepartmentId());
		certTemp.setCode(department.getCode());
		certTemp.setDeptCode(department.getCode());
		CertTemp certTempNew =  certTempAccessService.create(certTemp);
		return certTempNew;
	}
	
	@Override
	public CertTemp findById(long id) {
		return certTempAccessService.findById(id);
	}
	
	@Override
	public CertTemp findByCode(String code) {
		return certTempAccessService.findByCode(code);
	}

	@Override
	public List<CertTemp> findByInnerCode(String itemCode) {
		return certTempAccessService.findByInnerCode(itemCode);
	}
	
	@Override
	public List<CertTemp> findAll() {
		return certTempAccessService.findAll();
	}

	@Override
	public List<CertTemp> findCropCertTempList(){
		return certTempAccessService.findCropCertTempList();
	}

	@Override
	public String updateActive(long id, int active) {
		String resultMsg = "";
		boolean canUpdate = false;
		CertTemp findCertTemp = this.findById(id);
		if(findCertTemp.getActive() != 1) {
			if((findCertTemp.getType() == 2	 || findCertTemp.getType() == 6 )|| (Detect.notEmpty(findCertTemp.getContent()) && !"null".equals(findCertTemp.getContent()))) {//当类型为表单或者其他或者content不为空时才能进行发布操作
				certTempAccessService.updateActive(id, active);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("version", findCertTemp.getVersion());
				params.put("code", findCertTemp.getCode());
				params.put("active", findCertTemp.getActive());
				Map<String, Object> map = new HashMap<>();
				List<CertTemp> lowVersionCerts = this.findRelateVersion(params);//获取旧版本
				if(Detect.notEmpty(lowVersionCerts)) { // 不为空 为版本已发布过。
					for(CertTemp lowVersionCert : lowVersionCerts) {//将旧版本插入到history表中
						this.insertHistory(lowVersionCert);
						this.remove(lowVersionCert);	//删除旧版本
						// 更新材料关联模板ID
						map.put("oldID", lowVersionCert.getId());
						map.put("newID", findCertTemp.getId());
						this.updateRmaterialTempByNewId(map);
					}
				}else { // 为首次发布
					if(Detect.notEmpty(findCertTemp.getKeywords()) && !"".equals(findCertTemp.getKeywords().replace(" ", ""))) {
						map.put("id", findCertTemp.getId());
						map.put("name", findCertTemp.getKeywords());
						this.createRmaterialTempByKey(map);
					}
				}
				findCertTemp.setActive(active);
				this.creatToCache(findCertTemp);
			} else if (findCertTemp.getType() == 3) {//申请表
                certTempAccessService.updateActive(id, active);
          	}else {
				resultMsg = "模板不存在,发布失败！请编辑模板";
			}
		}else{
			resultMsg = "请勿重新发布!";
		}

		/*刷入证件数据资源缓存*/
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tempId", id);
		List<CertResource> certResources = certResourceAccessService.find(param);
		certResourceMgmtService.createToCache(certResources);
		return resultMsg;
	}

	@Override
	public List<CertTemp> findByIds(String[] ids) {
		return certTempAccessService.findByIds(ids);
	}

	@Override
	//创建关联表的数据
	public void createCertSeal(CertTemp certTemp, String[] sealCodes, String keyword, String signx, String signy) {
		if(sealCodes != null) { //章发布了才能配置
			int length = sealCodes.length;
			for(int i = 0;i < length;i++) {
				String code = sealCodes[i];
				if(!Detect.notEmpty(code)) {
						continue;
				}
				TempAttachment ta = new TempAttachment();
				ta.setCode(code);
				ta.setCoordinatex(signx);
				ta.setCoordinatey(signy);
				ta.setKeyWord(keyword);
				ta.setType(1);
				ta.setTempId(certTemp.getId());
				tempAttachmentAccessService.create(ta);
			}
		}
	}

	@Override
	public CertTemp loadFormPageDef(long id) {
		return certTempAccessService.loadFormPageDef(id);
	}

	@Override
	public void saveFormPageDef(FormPageParamVo formParam) {
		
		//以下为原本代码
		/*certTempAccessService.saveFormPageDef(id, content); 
		certResourceAccessService.removeByCert(id);
		if (Detect.notEmpty(certResources)) {
			certResourceAccessService.saveList(certResources);
		}*/
		CertTemp sourceCertTemp = certTempAccessService.findById(formParam.getId());
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("version", sourceCertTemp.getVersion());
		params.put("code", sourceCertTemp.getCode());
		params.put("active", sourceCertTemp.getActive());
		List<CertTemp> relateVersions = certTempAccessService.findRelateVersion(params);//查看是否已存在待发布版本
		if(sourceCertTemp.getActive() == 1) {	//已发布且无待发布版本则生成新的版本
			if(Detect.notEmpty(relateVersions)) {
				CertTemp firstRelateVersion = relateVersions.get(0);
				certTempAccessService.saveFormPageDef(firstRelateVersion.getId(), formParam.getContent(), 
						formParam.getHtmlContent(), formParam.getImage());
			}else {
				CertTemp highVersionCert = sourceCertTemp;
				highVersionCert.setActive(2);	//2:草稿
				highVersionCert.setVersion(sourceCertTemp.getVersion() + 1);
				highVersionCert.setContent(formParam.getContent());
				highVersionCert.setHtmlContent(formParam.getHtmlContent());
				highVersionCert.setImage(formParam.getImage());
				long id_new = certTempAccessService.createHighVersion(highVersionCert);
				highVersionCert.setId(id_new);
				Map<String, Object> copyParams = new HashMap<String, Object>();
				copyParams.put("sourceId", formParam.getId());
				copyParams.put("destId", id_new);
				certTempAccessService.copyToRelations(copyParams);//复制原版本的关联数据 生成草稿的关联数据
			}
		}else {//未发布则正常编辑
			certTempAccessService.saveFormPageDef(formParam.getId(), formParam.getContent(), 
					formParam.getHtmlContent(), formParam.getImage());
		}
		certResourceAccessService.removeByCert(sourceCertTemp.getCode());
		if (Detect.notEmpty(formParam.getCertResources())) {
			certResourceAccessService.saveList(formParam.getCertResources());
		}
//		if (formParam.getItemId() > 0) {
//			Map<String, Object> itemParam = new HashMap<>();
//			itemParam.put("itemId", formParam.getItemId());
//			itemParam.put("formCode", sourceCertTemp.getCode());
//			itemAccessService.bindFormTemp(itemParam);
//		}
	}

	@Override
	public String importFormTemplate(ImportingFileParam param) {
		try {
            String html = WordToHtml.convert(param.getFileContent());
            System.out.println(html);
            return html;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<TempAttachment> findRSeal(String tempId) {
		Map<String,Object> params = new HashMap<>();
		params.put("tempId",tempId);
		return tempAttachmentAccessService.findByTempId(Long.valueOf(tempId));
		//return certTempAccessService.findRSeal(params);
	}

	@Override
	public void insertHistory(CertTemp certTemp) {
		certTempAccessService.insertHistory(certTemp);
	}

	@Override
	public void update(CertTemp certTemp, String[] sealCodes, String keyword, String signx, String signy) {
		CertTemp oldCertTemp = certTempAccessService.findById(certTemp.getId());//获取旧版本的证明
		//如有待发布的版本则更新待发布的版本
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("version", oldCertTemp.getVersion());
		params.put("code", oldCertTemp.getCode());
		params.put("active", oldCertTemp.getActive()); //active = 1 查看低版本
		List<CertTemp> relateVersions= certTempAccessService.findRelateVersion(params);
		if(oldCertTemp.getActive() == 1) {
			if(Detect.notEmpty(relateVersions)) {//非空则表示已存在草稿,则修改到草稿上
				CertTemp draft = relateVersions.get(0);
				certTemp.setId(draft.getId());
			}else {//新建草稿
				CertTemp highVersionCert = oldCertTemp;
				highVersionCert.setActive(2);	//2:草稿
				highVersionCert.setVersion(oldCertTemp.getVersion() + 1);
				highVersionCert = certTempAccessService.create(highVersionCert);
				certTemp.setId(highVersionCert.getId());
			}
		}
		certTempAccessService.update(certTemp); 		//更新数据
		params.put("tempId", certTemp.getId());
//		certTempAccessService.deleteCertSeal(params);	//刪除所有关联印章后重新插入
		tempAttachmentAccessService.removeByTempId(certTemp.getId());//删除模板和印章的关联信息
		if(Detect.notEmpty(sealCodes)) {				//如果印章列不为空则循环插入数据
			params = new HashMap<String,Object>();
			int length = sealCodes.length;
			for(int i = 0;i < length;i++) {
				if(!Detect.notEmpty(sealCodes[i])) {
						continue;
				}
				String code = sealCodes[i];
				TempAttachment ta = new TempAttachment();
				ta.setCode(code);
				ta.setCoordinatex(signx);
				ta.setCoordinatey(signy);
				ta.setKeyWord(keyword);
				ta.setType(1);
				ta.setTempId(certTemp.getId());
				tempAttachmentAccessService.create(ta);
			}
		}/*else if(keyword != "" || signx != "" ||signy != "" ){
			params = new HashMap<String,Object>();
			params.put("certId", certTemp.getId());
			params.put("keyword", keyword);
			params.put("signx", signx);
			params.put("signy", signy);
			certTempAccessService.createCertSeal(params);
		}*/
	}

	@Override
	public String produceTemplateFile(CertTemp certTemp) {
//		String result = "failed";
//		try {
//			File templatePath = new File(ftlPath);	//模板保存路径
//			if(!templatePath.exists()||!templatePath.isDirectory()){ //生成文件夹
//			    templatePath.mkdirs();
//			}
//			String templateName = certTemp.getCode() + ".ftl";
//			File templateFile = new File(templatePath,templateName);
//			if(!templateFile.exists()) {
//				templateFile.createNewFile();
//			}
//			FileWriter fw = new FileWriter(templateFile);
//			fw.write(certTemp.getContent());
//			fw.flush();
//			fw.close();
//			result = "success";
//		} catch (IOException e) {
//			e.printStackTrace();
//			return result;
//		}
//		return result;
        return "success";
	}

    /**
     * 刷新缓存
     */
	@Override
	public void refreshCache() {
        //获取所有附加信息
        List<TempAttachmentVo> attachmentVoList = tempAttachmentAccessService.findAllVo();
        //根据证件id分组
        Map<Long, Map<Integer, List<TempAttachmentVo>>> attachmentVoListMap = new HashMap<>();
        for (TempAttachmentVo attachmentVo : attachmentVoList) {
            Map<Integer, List<TempAttachmentVo>> map = attachmentVoListMap.get(attachmentVo.getTempId());
            if(map == null) {
                map = new HashMap<>();
                attachmentVoListMap.put(attachmentVo.getTempId(), map);
            }
            List<TempAttachmentVo> attachmentVos = map.get(attachmentVo.getType());
            if(attachmentVos == null) {
                attachmentVos = new ArrayList<>();
                map.put(attachmentVo.getType(), attachmentVos);
            }
            attachmentVos.add(attachmentVo);
        }
        //获取 资源
        List<Map<String, Object>> certResources = certResourceAccessService.findByGroup();
        //根据证件id分组
        Map<Long, Set<String[]>> certResourceMap = new HashMap<>();
        for(Map<String, Object> certResource : certResources) {
            long tempId = Long.parseLong(certResource.get("tempId") + "") ;
            Set<String[]> resources = certResourceMap.get(tempId);
            if(resources == null) {
                resources = new HashSet<>();
                certResourceMap.put(tempId, resources);
            }
            String[] resource = {certResource.get("resourceCode") + "", certResource.get("resourceType") + ""};
            resources.add(resource);
        }
        List<CertTempVo> certTempVos = certTempAccessService.findAllVo();
        for (CertTempVo vo : certTempVos) {
            vo.setAttachmentMap(attachmentVoListMap.get(vo.getId()));
            vo.setResources(certResourceMap.get(vo.getId()));
        }
        dataCacheService.clearCertTemp();
        dataCacheService.setCertTemps(certTempVos);
    }
	/**
	 * 添加模板进缓存
	 */
	@Override
	public void creatToCache(CertTemp certTemp) {
		CertTempVo certTempVo = certTemp.toVo();
		/*
		 * 获取关联的sealInfo表进行保存
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tempId", certTemp.getId());
		//根据类型分组
        Map<Integer, List<TempAttachmentVo>> attachmentMap = new HashMap<>();
		List<TempAttachmentVo> attachmentVoList = tempAttachmentAccessService.findVoByMap(map);
		if(Detect.notEmpty(attachmentVoList)) {
            for (TempAttachmentVo attachmentVo : attachmentVoList) {
                List<TempAttachmentVo> attachmentVos = attachmentMap.get(attachmentVo.getType());
                if(attachmentVos == null) {
                    attachmentVos = new ArrayList<>();
                    attachmentMap.put(attachmentVo.getType(), attachmentVos);
                }
                attachmentVos.add(attachmentVo);
            }
            certTempVo.setAttachmentMap(attachmentMap);
        }
		/*
		 * 获取关联的certResource数据进行保存
		 */
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("tempId", certTemp.getId());
		List<Map<String,Object>> tempResourceList = certResourceAccessService.findByMap(param);
		Set<String[]> resources = new HashSet<String[]>();
		for(Map<String,Object> certResource :tempResourceList) {
			String[] resource = {certResource.get("resourceCode") + "", certResource.get("resourceType") + ""};
			resources.add(resource);
		}
		certTempVo.setResources(resources);
		dataCacheService.setCertTemp(certTempVo);
	}

	@Override
	public List<CertTemp> findRelateVersion(Map<String, Object> params) {
		return certTempAccessService.findRelateVersion(params);
	}

	@Override
	public List<CertTemp> findHistoryVersion(Map<String, Object> params) {
		return certTempAccessService.findHistotyVersion(params);
	}

	@Override
	public void createRmaterialTempByKey(Map<String, Object> params) {
		this.certTempAccessService.createRmaterialTempByKey(params);
	}

	@Override
	public void updateRmaterialTempByNewId(Map<String, Object> params) {
		this.certTempAccessService.updateRmaterialTempByNewId(params);
	}

	@Override
	public CertTemp create(CertTemp certTemp, String[] sealCodes, String keyword, String signx, String signy) {
		Department department = departmentMgmtService.findById(certTemp.getDepartmentId());
		certTemp.setCode(department.getCode());
		certTemp.setDeptCode(department.getCode());
		CertTemp createdCertTemp =  certTempAccessService.create(certTemp);
		this.createCertSeal(createdCertTemp, sealCodes, keyword, signx, signy);
		return createdCertTemp;
	}

	@Override
	public List<CertTemp> findAllByType(Map<String, Object> params) {
		return certTempAccessService.findAllByType(params);
	}

	@Override
	public void saveToCache(long id) {
		CertTemp certTemp = certTempAccessService.findById(id);
		System.out.println(certTemp.getCode());
		this.creatToCache(certTemp);
		
		/*刷入证件数据资源缓存*/
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tempId", id);
		List<CertResource> certResources = certResourceAccessService.find(param);
		certResourceMgmtService.createToCache(certResources);
	}

	@Override
	public List<CertTemp> findAllActive() {
		return certTempAccessService.findAllActive();
	}

	@Override
	public List<CertTemp> findTempList(Map<String, Object> params) {
		return certTempAccessService.findTempList(params);
	}

	@Override
    public List<String> findByForm(String formCode) {
        return certTempAccessService.findByForm(formCode);
    }


	@Override
	public String copyTempsToOtherDepts(Map<String, Object> params) {
		String result = "";
		try {
			long[] deptIds = (long[]) params.get("deptIds");//目的部门ID
			long[] tempIds = (long[]) params.get("tempIds");//数源证件ID
			int deptIdsLen = deptIds.length;
			int tempIdsLen = tempIds.length;
			for(int i = 0; i < tempIdsLen;i++) {//逐个证件配置
				long tempId = tempIds[i];
				List<CertTemp> certTemps = new LinkedList();
				CertTemp sourceTemp = certTempAccessService.findById(tempId);
				params.put("certTemp", sourceTemp);
				certTempAccessService.createTempList(params);
			}
			result = "复制成功!";
		}catch (Exception e) {
			e.printStackTrace();
			result = "复制失败";
		}
		return result;
	}
}
