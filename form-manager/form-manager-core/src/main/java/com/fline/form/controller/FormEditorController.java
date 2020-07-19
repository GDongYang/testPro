package com.fline.form.controller;

import com.feixian.tp.common.util.Detect;
import com.fline.aic.db.vo.redis.DataElementVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.form.access.model.Item;
import com.fline.form.access.model.Situation;
import com.fline.form.mgmt.service.FormEditorMgmtService;
import com.fline.form.vo.ResponseResult;
import com.fline.yztb.mgmt.service.impl.vo.FormPageParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formEditor")
@Api(tags = "表单编辑器接口")
public class FormEditorController {

    @Resource
    private FormEditorMgmtService formEditorMgmtService;

    @ApiOperation("获取事项列表")
    @GetMapping("/itemList")
    public ResponseResult<List<Item>> findItemList(@ApiParam("部门id")Long deptId, @ApiParam("搜索内容")String searchValue) {
        Map<String, Object> params = new HashMap<>();
        if(deptId != null) {
            params.put("departmentId", deptId);
        }
        if(Detect.notEmpty(searchValue)) {
            params.put("nameLike", searchValue);
        }
        List<Item> itemList = formEditorMgmtService.findItemList(params);
        return ResponseResult.success(itemList);
    }

    @ApiOperation("获取情形列表")
    @GetMapping("/situationList")
    public ResponseResult<List<Situation>> findSituations(@ApiParam("事项id") long itemId) {
        List<Situation> situations = formEditorMgmtService.findSituationByItemId(itemId);
        return ResponseResult.success(situations);
    }

    @ApiOperation("获取业务分类列表")
    @GetMapping("/categoryList")
    public ResponseResult<List<PackageCategory>> getPackageCategoryList() {
        List<PackageCategory> result = formEditorMgmtService.getPackageCategoryList();
        return ResponseResult.success(result);
    }

    @ApiOperation("获取业务对象列表")
    @GetMapping("/packageList")
    public ResponseResult<List<PackageVo>> getPackageList(@ApiParam("业务分类id")String categoryId) {
        List<PackageVo> packageList = formEditorMgmtService.getPackageList(categoryId);
        return ResponseResult.success(packageList);
    }

    @ApiOperation("获取数据元列表")
    @GetMapping("/dataElementList")
    public ResponseResult<List<DataElementVo>> getDataElementList(@ApiParam("业务分类id")String packageId) {
        List<DataElementVo> dataElementList = formEditorMgmtService.getDataElementList(packageId);
        return ResponseResult.success(dataElementList);
    }

    @ApiOperation("获取表单属性")
    @GetMapping("/formProperty")
    public ResponseResult<Map<String, Object>> loadFormProperty(@ApiParam("表单id")long formId) {
        Map<String, Object> formProperty = formEditorMgmtService.loadFormProperty(formId);
        return ResponseResult.success(formProperty);
    }

    @ApiOperation("保存表单属性")
    @PostMapping("/formProperty")
    public ResponseResult<?> saveFormPageDef(FormPageParamVo formParam, boolean onlyContent) {
        formEditorMgmtService.saveFormPageDef(formParam, onlyContent);
        return ResponseResult.success("");
    }

}
