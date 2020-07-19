package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.fline.aic.db.vo.redis.DataElementVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Situation;
import com.fline.form.access.service.*;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.FormEditorMgmtService;
import com.fline.form.mgmt.service.ItemMgmtService;
import com.fline.yztb.access.model.FormPage;
import com.fline.yztb.access.model.FormProperty;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;
import com.fline.yztb.vo.FormPropertyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("formEditorMgmtService")
public class FormEditorMgmtServiceImpl implements FormEditorMgmtService {

    @Resource
    private ItemAccessService itemAccessService;
    @Resource
    private SituationAccessService situationAccessService;
    @Resource
    private DataCacheService dataCacheService;
    @Resource
    private FormPageAccessService formPageAccessService;
    @Resource
    private FormPropertyAccessService formPropertyAccessService;
    @Resource
    private CertResourceAccessService certResourceAccessService;
    @Resource
    private ItemMgmtService itemMgmtService;

    @Override
    public List<Item> findItemList(Map<String, Object> params) {
        return itemAccessService.find(params);
    }

    @Override
    public List<Situation> findSituationByItemId(long itemId) {
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        return situationAccessService.findSituationByItemId(params);
    }

    @Override
    public List<PackageCategory> getPackageCategoryList() {
        List<PackageCategory> result = dataCacheService.getPackageCategoryList();
        return result;
    }

    @Override
    public List<PackageVo> getPackageList(String parentId) {
        List<PackageVo> result = dataCacheService.getPackageList(parentId);
        return result;
    }

    @Override
    public List<DataElementVo> getDataElementList(String packageId) {
        PackageVo result = dataCacheService.getPackage(packageId);
        return result.getDataElementVos();
    }

    @Override
    public Map<String, Object> loadFormProperty(long formId) {
        //获取表单基本信息
        FormPage form = formPageAccessService.findById(formId);
        String formCode = form.getCode();
        Map<String, Object> result = new HashMap<String, Object>();

        result.put(FormProperty.DEPT_ID, form.getDepartmentId());
        result.put(FormProperty.POST_TYPE, form.getPostType());
        result.put(FormProperty.PAY_TYPE, form.getPayType());
        List<PackageVo> packages = new ArrayList<PackageVo>();
        //获取表单属性
        List<FormProperty> properties = formPropertyAccessService.findByForm(formCode, form.getVersion());
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
        //事项
        List<Item> itemList = itemAccessService.findItemByFormCode(formCode);
        List<Long> itemIds = itemList.stream().map(Item::getId).collect(Collectors.toList());
        result.put(FormProperty.ITEM, itemIds);
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
}
