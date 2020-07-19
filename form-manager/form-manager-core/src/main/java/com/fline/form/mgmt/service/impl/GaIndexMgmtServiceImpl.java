package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.GaIndexMgmtService;
import com.fline.yztb.vo.ItemVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("gaIndexMgmtService")
public class GaIndexMgmtServiceImpl implements GaIndexMgmtService, Cacheable {

    @Resource
    private ItemAccessService itemAccessService;
    @Resource
    private DataCacheService dataCacheService;

    @Override
    public void refreshCache() {
        //获取所有事项
        List<ItemVo> itemVos = itemAccessService.findAllVo();
        //放入hash表
        Map<Long, ItemVo> itemVoMap = new HashMap<>();//key事项id
        Map<String, List<String>> themeItemMap = new HashMap<>();//key 部门code前12位+theme
        Map<String, List<String>> baseItemMap = new HashMap<>();
        Set<String> baseItemSet = new HashSet<>();
        for (ItemVo itemVo : itemVos) {
            String departmentCode = itemVo.getDepartmentCode();
            departmentCode = departmentCode.length() > 12 ? departmentCode.substring(0,12) : departmentCode;
            itemVo.setDepartmentCode(departmentCode);
            itemVoMap.put(itemVo.getId(), itemVo);

            String theme = itemVo.getTheme();
            if(Detect.notEmpty(theme) && Detect.notEmpty(itemVo.getFormCode()) && "2".equals(itemVo.getActive())) {
                //根据（departmentCode+theme）分组：001008001001_33
                String deptThemeCode = departmentCode + "_" + theme;
                List<String> themeItems = themeItemMap.get(deptThemeCode);
                if(themeItems == null) {
                    themeItems = new ArrayList<>();
                    themeItemMap.put(deptThemeCode, themeItems);
                }
                String alias = itemVo.getAlias();
                String name = itemVo.getName();
                themeItems.add(name + "|" + alias + ":" + itemVo.getInnerCode() + ":" + itemVo.getStarLevel());

                //根据部门code+事项基本码分组
                String itemCode = itemVo.getCode();
                String[] deptNameArray = itemVo.getDeptName().split("/");
                String areaName = deptNameArray[1].trim();
                if(itemCode.startsWith("许可-00751") || itemCode.startsWith("许可-00750")) {
                    itemCode = "许可-00750";
                }
                String code = deptThemeCode.substring(0, 9) + "_" + itemCode;
                if(!baseItemSet.add(code + areaName)) {
                    continue;
                }
                List<String> baseItemList = baseItemMap.get(code);
                if(baseItemList == null) {
                    baseItemList = new ArrayList<>();
                    baseItemMap.put(code, baseItemList);
                }
                baseItemList.add(areaName + ":" + itemVo.getInnerCode());
            }
        }
        //事项基本码
        dataCacheService.clearBaseItem();
        for (String code: baseItemMap.keySet()) {
            dataCacheService.setBaseItems(code, baseItemMap.get(code));
        }

        //公安专区事项
        dataCacheService.clearItemTheme();
        for (String deptThemeCode: themeItemMap.keySet()) {
            dataCacheService.setItemThemes(deptThemeCode, themeItemMap.get(deptThemeCode));
        }
        dataCacheService.clearThemeKeys();
        dataCacheService.setItemThemeKeys(themeItemMap.keySet().toArray(new String[themeItemMap.size()]));
    }
}
