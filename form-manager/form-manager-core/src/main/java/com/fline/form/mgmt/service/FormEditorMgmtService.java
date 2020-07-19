package com.fline.form.mgmt.service;

import com.fline.aic.db.vo.redis.DataElementVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Situation;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;
import com.fline.yztb.vo.ItemVo;

import java.util.List;
import java.util.Map;

public interface FormEditorMgmtService {

    List<Item> findItemList(Map<String, Object> params);

    List<Situation> findSituationByItemId(long itemId);

    List<PackageCategory> getPackageCategoryList();

    List<PackageVo> getPackageList(String parentId);

    List<DataElementVo> getDataElementList(String packageId);

    Map<String, Object> loadFormProperty(long formId);

    void saveFormPageDef(FormPageParamVo formParam, boolean onlyContent);
}
