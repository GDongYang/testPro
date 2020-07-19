package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Item;
import com.fline.form.access.service.*;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.*;
import com.fline.form.util.SFTPUtils;
import com.fline.yztb.access.model.FormPage;
import com.fline.yztb.access.model.FormProperty;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.FormPropertyVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("formPageMgmtService")
public class FormPageMgmtServiceImpl implements FormPageMgmtService, Cacheable {
	
	@Resource
	private FormPageAccessService formPageAccessService;
	
	@Resource
	private FormPropertyAccessService formPropertyAccessService;
	
	@Resource
	private CertResourceAccessService certResourceAccessService;
	
	@Resource
	private DepartmentMgmtService departmentMgmtService;
	
	@Resource
	private ItemAccessService itemAccessService;
	
	@Resource
	private ItemMgmtService itemMgmtService;
	
	@Resource
	private DataCacheService dataCacheService;
	@Resource
	private CertTempAccessService certTempAccessService;
	@Resource
	private CertResourceMgmtService certResourceMgmtService;

	@Value("${form.path}")
	private String formPath;		//form文件保存地址
	
	@Value("${app.path}")
	private String appPath; 		//存储浙里办Html页面地址
	
	@Value("${online.path}")
	private String onlinePath;		//存储浙江政务服务网Html页面地址
	
	@Value("${offline.path}")
	private String offlinePath;		//存储线下一窗办理Html页面地址

    @Value("${terminal.path}")		//存储自助终端设备Html页面的路径地址
    private String terminalPath;
	
	@Value("${html.host}")
	private String htmlHost;
	
	@Value("${html.port}")
	private String htmlPort;
	
	@Value("${html.username}")
	private String htmlUserName;
	
	@Value("${html.password}")
	private String htmlPassword;
	
	
	@Override
	public Pagination<FormPage> findPagination(Map<String, Object> param,
			Ordering order, Pagination<FormPage> page) {
		Pagination<FormPage> pt =  formPageAccessService.findPagination(param, order, page);
		return pt;
	}

	@Override
	public void update(FormPage formPage) {
		formPageAccessService.update(formPage);
		Map<String, Object> params = new HashMap<>();
		params.put("formPageId", formPage.getId());
		this.saveToCache(params);
	}

	@Override
	public void remove(FormPage formPage) {
		FormPage findFormPage = formPageAccessService.findById(formPage.getId());
		formPageAccessService.remove(formPage);
		certResourceAccessService.removeByCert(findFormPage.getCode());//删除关联关系
		
		dataCacheService.removeRedis(KeyConstant.YZTB_FORM_PAGE, findFormPage.getCode());//删除缓存
		dataCacheService.removeRedis(KeyConstant.YZTB_CERT_RESOURCE, findFormPage.getPackageId());//删除证件数据资源的缓存
	}

	@Override
	public FormPage create(FormPage formPage) {
		Department department = departmentMgmtService.findById(formPage.getDepartmentId());
		formPage.setCode(department.getCode());
		return formPageAccessService.create(formPage);
	}

	@Override
	public FormPage findById(long id) {
		return formPageAccessService.findById(id);
	}
	
	@Override
	public List<FormPage> findAll() {
		return formPageAccessService.findAll();
	}

	@Override
	public FormPage findByCode(String code) {
		return formPageAccessService.findByCode(code);
	}

	@Override
	public FormPage findImagesById(long id) {
		return formPageAccessService.findImagesById(id);
	}
	
	@Override
	public Map<String, Object> loadFormProperty(String formCode, long formVersion) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PackageVo> packages = new ArrayList<PackageVo>();
		List<FormProperty> properties = formPropertyAccessService.findByForm(formCode, formVersion);
		if (Detect.notEmpty(properties)) {
			for (FormProperty property : properties) {
				if (FormProperty.PACKAGE.equals(property.getName())) {
					packages.add(dataCacheService.getPackage(property.getValue()));
				}
				else if (FormProperty.INTERFACE_VERIFY.equals(property.getName())
						|| FormProperty.INTERFACE_SUBMIT.equals(property.getName())) {
					result.put(property.getName(), dataCacheService.getResource(property.getValue()));
				}
                else {
                    result.put(property.getName(), property.getValue());
                }
			}
		}
		result.put(FormProperty.PACKAGE, packages);
		return result;
	}
	
	@Override
    public void saveFormPageDef(FormPageParamVo formParam, boolean onlyContent) {
        formPageAccessService.updateVersion(formParam.getId());//form版本加1
        FormPage form = formPageAccessService.findById(formParam.getId());
        if (form == null) {
            throw new RuntimeException("保存表单数据失败，未找到该表单：" + formParam.getId());
        }
        if (onlyContent) {
            formPageAccessService.saveFormPageDef(formParam.getId(),
                    formParam.getAppContent(), formParam.getAppImage(),
                    formParam.getOnlineContent(), formParam.getOnlineImage(),
                    formParam.getOfflineContent(), formParam.getOfflineImage(),
                    formParam.getTerminalContent(),formParam.getTerminalImage(),
                    formParam.getPostType(), formParam.getPayType());
        }
        else {
            formPageAccessService.saveFormPageDef(formParam.getId(),
                    formParam.getAppContent(), formParam.getAppImage(),
                    formParam.getOnlineContent(), formParam.getOnlineImage(),
                    formParam.getOfflineContent(), formParam.getOfflineImage(),
                    formParam.getTerminalContent(),formParam.getTerminalImage(),
                    formParam.getPostType(), formParam.getPayType());

            certResourceAccessService.removeByCert(form.getCode());
            if (Detect.notEmpty(formParam.getCertResources())) {
                certResourceAccessService.saveList(formParam.getCertResources());
            }

            if (Detect.notEmpty(formParam.getProperties())) {
                formPropertyAccessService.removeByForm(form.getCode(), form.getVersion());
                for (FormPropertyVo propVo : formParam.getProperties()) {
                    FormProperty prop = new FormProperty(propVo);
                    prop.setFormCode(form.getCode());
                    prop.setFormName(form.getName());
                    prop.setFormVersion(form.getVersion());
                    formPropertyAccessService.save(prop);
                }
            }

            if (Detect.notEmpty(formParam.getItems())) {
                for (Integer itemId : formParam.getItems()) {
                    Map<String, Object> itemParam = new HashMap<>();
                    itemParam.put("itemId", itemId);
                    itemParam.put("formCode", form.getCode());
                    itemAccessService.bindFormTemp(itemParam);
                    //刷新事项的缓存
                    Item item = itemAccessService.findById(itemId);
                    itemMgmtService.createToCache(item);
                }
            }
        }
    }

    @Override
    public void refreshCache() {
        List<FormPageVo> formPageVos = formPageAccessService.findAllVo();
        Map<String, FormPageVo> formPageVoMap = new HashMap<>();
        for (FormPageVo formPageVo : formPageVos) {
            formPageVo.setProperties(new ArrayList<>());
            formPageVoMap.put(formPageVo.getCode(), formPageVo);
            List<String> tempCodes = certTempAccessService.findByForm(formPageVo.getCode());
            formPageVo.setTempCodes(tempCodes);
        }
        List<FormPropertyVo> propertyVos = formPropertyAccessService.findByMaxVersion();
        for (FormPropertyVo propertyVo : propertyVos) {
            String formCode = propertyVo.getFormCode();
            FormPageVo formPageVo = formPageVoMap.get(formCode);
            if(formPageVo == null) {
                continue;
            }
            long version = formPageVo.getVersion();
            if(version == propertyVo.getFormVersion()) {
                formPageVo.getProperties().add(propertyVo);
            } else {
                FormProperty formProperty = formPropertyAccessService.findByFormAndName(formCode, version, propertyVo.getName());
                if(formProperty != null) {
                    formPageVo.getProperties().add(formProperty.toVo());
                }
            }
        }
        dataCacheService.clearFormPage();
        dataCacheService.setFormPages(formPageVos);
    }
	@Override
	public String updateActive(long id) {
		String resultMsg = "";
		try {
			FormPage findFormPage = formPageAccessService.findById(id);//查询指定的表单
			/*发布表单*/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("formPageId", id);
			formPageAccessService.updateActive(params);
			long version = findFormPage.getVersion();
			/*发布时将对应的content内容存储到html服务器*/
			String fileName = findFormPage.getCode() + "_" + version + ".html";
			String appContent = findFormPage.getAppContent();
			String onlineContent = findFormPage.getOnlineContent();
			String offlineContent = findFormPage.getOfflineContent();
            String terminalContent = findFormPage.getTerminalContent();
			if(Detect.notEmpty(appContent) && !"".equals(appContent.replace(" ", ""))) {
				 SFTPUtils.uploadFile(htmlHost, Integer.parseInt(htmlPort), htmlUserName, htmlPassword, fileName, appContent, appPath);
			}
			if(Detect.notEmpty(onlineContent) && !"".equals(onlineContent.replace(" ", ""))) {
				 SFTPUtils.uploadFile(htmlHost, Integer.parseInt(htmlPort), htmlUserName, htmlPassword, fileName, onlineContent, onlinePath);
			}
			if(Detect.notEmpty(offlineContent) && !"".equals(offlineContent.replace(" ", ""))) {
				 SFTPUtils.uploadFile(htmlHost, Integer.parseInt(htmlPort), htmlUserName, htmlPassword, fileName, offlineContent, offlinePath);
			}
            if(Detect.notEmpty(terminalContent) && !"".equals(terminalContent.replace(" ", ""))) {
                SFTPUtils.uploadFile(htmlHost, Integer.parseInt(htmlPort), htmlUserName, htmlPassword, fileName, terminalContent, terminalPath);
            }
			this.saveToCache(params);
            certResourceMgmtService.cacheByForm(id);
			resultMsg = "发布成功!";
		}catch (Exception e) {
			e.printStackTrace();
			resultMsg = "发布失败!请稍后重试";
		}
		return resultMsg;
	}

	@Override
	public String saveToCache(Map<String, Object> params) {
		String msg = "";
		try {
			FormPageVo formPageVo = formPageAccessService.findForCache(params);
			List<FormPropertyVo> formProperties = formPropertyAccessService.findVoListByFormInfo(formPageVo.getCode(), formPageVo.getVersion());
			if(Detect.notEmpty(formProperties)) {
				formPageVo.setProperties(formProperties);
			}
            List<String> tempCodes = certTempAccessService.findByForm(formPageVo.getCode());
			if(Detect.notEmpty(tempCodes)) {
                formPageVo.setTempCodes(tempCodes);
            }
			dataCacheService.setFormPage(formPageVo);
			msg = "更新缓存成功!"; 
		}catch (Exception e) {
			e.printStackTrace();
			msg = "更新缓存失败!请重试";
		}
		return msg;
	}

	@Override
	public List<FormPage> findList(Map<String, Object> params) {
		return formPageAccessService.findList(params);
	}

	@Override
	public String copyFormsToOtherDepts(Map<String, Object> params) {
		String result = "";
		try {
			List<Long> deptIds = (List<Long>) params.get("deptIds");
			List<Long> formPageIds = (List<Long>) params.get("formPageIds");
			for(Long formPageId : formPageIds) {
				FormPage sourceFormPage = formPageAccessService.findAllById(formPageId);
				params.put("formPage", sourceFormPage);
				formPageAccessService.copyFormsToOtherDepts(params);
			}
			result = "复制成功!";
		}catch (Exception e) {
			e.printStackTrace();
			result = "复制失败,请稍后重试!";
		}
		return result;
	}

	@Override
	public void saveFormTemp(String formCode, String[] tempCodes) {
        formPageAccessService.removeFormTemp(formCode);
        if(Detect.notEmpty(tempCodes)) {
            List<String> codes = Arrays.stream(tempCodes).filter(Detect::notEmpty).collect(Collectors.toList());
            if(Detect.notEmpty(codes)) {
                formPageAccessService.saveFormTemp(formCode, codes.toArray(new String[codes.size()]));
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("formCode", formCode);
        this.saveToCache(params);
    }
}
