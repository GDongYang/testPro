package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSON;
import com.feixian.tp.cache.Cache;
import com.fline.aic.db.vo.redis.DataElementVo;
import com.fline.aic.db.vo.redis.DataFormatVo;
import com.fline.aic.db.vo.redis.PackageCategory;
import com.fline.aic.db.vo.redis.PackageVo;
import com.fline.aic.resourcesharing.vo.CatalogVo;
import com.fline.aic.resourcesharing.vo.ResourceVo;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.model.Secret;
import com.fline.form.access.model.ServiceAccount;
import com.fline.form.condition.ConditionalOnMissRedis;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.vo.*;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.ItemVo;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Conditional(ConditionalOnMissRedis.class)
@Service("dataCacheService")
public class DataCacheServiceSimpleImpl implements DataCacheService {
    @Resource
    private Cache cache;
    @Override
    public List<String> getSubCatalogIds(String catalogId) {
        return null;
    }

    @Override
    public CatalogVo getCatalog(String catalogId) {
        return null;
    }

    @Override
    public List<String> getSubResourceCodes(String resourceCode) {
        return null;
    }

    @Override
    public ResourceVo getResource(String resourceCode) {
        return null;
    }

    @Override
    public List<PackageCategory> getPackageCategoryList() {
        String json = "[{\"typeId\":\"GAJ\",\"typeName\":\"公安局\"},{\"typeId\":\"common\",\"typeName\":\"通用\"},{\"typeId\":\"HYQZJJ\",\"typeName\":\"黄岩区住建局\"}]";
        return JSON.parseArray(json, PackageCategory.class);
    }

    @Override
    public List<PackageVo> getPackageList(String categoryCode) {
        String json = "[{\"name\":\"提交《身体条件证明》\",\"packageId\":\"PZGAJSGAJ_TJSTTJZM002\",\"memo\":\"提交《身体条件证明》\",\"dataElementVos\":null},{\"name\":\"市公安局_申请注销驾驶资格+公共部分\",\"packageId\":\"PZGAJSGAJ_SQZXJSZG002\",\"memo\":\"\",\"dataElementVos\":null},{\"name\":\"自愿降低准驾车型换领机动车驾驶证接入登记表\",\"packageId\":\"PZGAJSGAJ_ZYJDZJCXHLJDC002\",\"memo\":\"自愿降低准驾车型换领机动车驾驶证接入登记表\",\"dataElementVos\":null},{\"name\":\"申请延期提交《身体条件证明》\",\"packageId\":\"PZGAJSGAJ_SQYQTJSTTJZM002\",\"memo\":\"申请延期提交《身体条件证明》\",\"dataElementVos\":null},{\"name\":\"身体条件变化降低准驾车型换领机动车驾驶证\",\"packageId\":\"PZGAJSGAJ_STTJBHJDZJCX002\",\"memo\":\"身体条件变化降低准驾车型换领机动车驾驶证\",\"dataElementVos\":null},{\"name\":\"SGAJ_JSZYQSY\",\"packageId\":\"PZGAJSGAJ_JSZYQSY002\",\"memo\":\"\",\"dataElementVos\":null},{\"name\":\"延期换领机动车驾驶证\",\"packageId\":\"PZGAJSGAJ_YQHLJDCJSZ002\",\"memo\":\"延期换领机动车驾驶证\",\"dataElementVos\":null}]";
        return JSON.parseArray(json, PackageVo.class);
    }

    @Override
    public PackageVo getPackage(String packageId) {
        String json = "[{\"dataElementID\":\"DGAJSGAJ_STTJBHJDZJCX002\",\"code\":\"username\",\"name\":\"驾驶人姓名\",\"dataType\":\"C0..10\",\"dataFormatID\":\"\",\"dataFormatVo\":null,\"packageId\":\"PZGAJSGAJ_STTJBHJDZJCX002\",\"check\":0,\"parentId\":0,\"headId\":0},{\"dataElementID\":\"DGAJSGAJ_STTJBHJDZJCX003\",\"code\":\"fzjg\",\"name\":\"发证机关\",\"dataType\":\"C0..2\",\"dataFormatID\":\"\",\"dataFormatVo\":null,\"packageId\":\"PZGAJSGAJ_STTJBHJDZJCX002\",\"check\":0,\"parentId\":0,\"headId\":0},{\"dataElementID\":\"DGAJSGAJ_STTJBHJDZJCX004\",\"code\":\"zt\",\"name\":\"驾驶证状态\",\"dataType\":\"C0..10\",\"dataFormatID\":\"\",\"dataFormatVo\":null,\"packageId\":\"PZGAJSGAJ_STTJBHJDZJCX002\",\"check\":0,\"parentId\":0,\"headId\":0},{\"dataElementID\":\"DGAJSGAJ_STTJBHJDZJCX005\",\"code\":\"zjcx\",\"name\":\"准驾车型\",\"dataType\":\"C0..10\",\"dataFormatID\":\"\",\"dataFormatVo\":null,\"packageId\":\"PZGAJSGAJ_STTJBHJDZJCX002\",\"check\":0,\"parentId\":0,\"headId\":0},{\"dataElementID\":\"DGAJSGAJ_STTJBHJDZJCX006\",\"code\":\"date\",\"name\":\"有效期限\",\"dataType\":\"C0..10\",\"dataFormatID\":\"\",\"dataFormatVo\":null,\"packageId\":\"PZGAJSGAJ_STTJBHJDZJCX002\",\"check\":0,\"parentId\":0,\"headId\":0}]";
        List<DataElementVo> dataElementVos = JSON.parseArray(json, DataElementVo.class);
        PackageVo packageVo = new PackageVo();
        packageVo.setDataElementVos(dataElementVos);
        return packageVo;
    }

    @Override
    public DataFormatVo getDataFormat(String dataFormatId) {
        return null;
    }

    @Override
    public void setItem(ItemVo itemVo) {

    }

    @Override
    public void setItems(List<ItemVo> items) {

    }

    @Override
    public void clearItem() {

    }

    @Override
    public void setPosition(PositionVo positionVo) {

    }

    @Override
    public void setPositions(List<PositionVo> positions) {

    }

    @Override
    public void clearPosition() {

    }

    @Override
    public void setSealInfo(String key, SealInfo sealInfo) {

    }

    @Override
    public void setSealInfos(List<SealInfoVo> sealInfos) {

    }

    @Override
    public void clearSealInfo() {

    }

    @Override
    public void setServiceAccount(String key, ServiceAccount serviceAccount) {

    }

    @Override
    public void setServiceAccounts(List<ServiceAccountVo> serviceAccounts) {

    }

    @Override
    public void clearServiceAccount() {

    }

    @Override
    public void setDepartment(DepartmentVo departmentVo) {

    }

    @Override
    public void setDepartments(List<DepartmentVo> departments) {

    }

    @Override
    public void clearDepartment() {

    }

    @Override
    public void setCertTemp(CertTempVo certTempVo) {

    }

    @Override
    public void setCertTemps(List<CertTempVo> certTemps) {

    }

    @Override
    public void clearCertTemp() {

    }

    @Override
    public void removeRedis(String key, String childKey) {

    }

    @Override
    public void setCertResource(CertResourceVo certResource) {

    }

    @Override
    public void setCertResources(List<CertResourceVo> certResources) {

    }

    @Override
    public void clearCertResource() {

    }

    @Override
    public void setSecret(Secret secret) {

    }

    @Override
    public void clearSecret() {

    }

    @Override
    public void setSecrets(List<SecretVo> findAllVo) {

    }

    @Override
    public void setVichelDistricts(Map<String, Object> map) {

    }

    @Override
    public void clearVichelDistrict() {

    }

    @Override
    public void setPcsDistricts(Map<String, Object> map) {

    }

    @Override
    public void clearPcsDistrict() {

    }

    @Override
    public void setItemThemes(String theme, List<String> itemCodes) {

    }

    @Override
    public void clearItemTheme() {

    }

    @Override
    public void setItemThemeKeys(String[] themeKeys) {

    }

    @Override
    public void clearThemeKeys() {

    }

    @Override
    public Set<String> getThemeKeys() {
        return null;
    }

    @Override
    public void setItemTheme(String deptThemeCode, String itemTheme) {

    }

    @Override
    public void setItemThemeKey(String themeKey) {

    }

    @Override
    public void clearDictionaries() {

    }

    @Override
    public void setDictionaries(Map<String, Object> map) {

    }

    @Override
    public void clearDictionary() {

    }

    @Override
    public void setDictionary(Map<String, Object> map) {

    }

    @Override
    public void setDeptItemCountByDay(Map<String, Object> map) {

    }

    @Override
    public Map<String, Object> getDeptItemCountByDay() {
        return null;
    }

    @Override
    public void setDayItemCountChange(Map<String, Object> map, int days) {

    }

    @Override
    public Map<String, Object> getDayItemCountChange(int days) {
        return null;
    }

    @Override
    public void setDeptItemCount(Map<String, Object> map, int type) {

    }

    @Override
    public Map<String, Object> getDeptItemCount(int type) {
        return null;
    }

    @Override
    public void setCityItemCount(Map<String, Object> map, int type) {

    }

    @Override
    public Map<String, Object> getCityItemCount(int type) {
        return null;
    }

    @Override
    public void setServiceItemCount(Map<String, Object> map, int type) {

    }

    @Override
    public Map<String, Object> getServiceItemCount(int type) {
        return null;
    }

    @Override
    public String getStatisticState() {
        return null;
    }

    @Override
    public void setStatisticState(String state, int expireTime) {

    }

    @Override
    public boolean setStatisticLock(String requestId) {
        return false;
    }

    @Override
    public String getStatisticLock() {
        return null;
    }

    @Override
    public void delStatisticLock() {

    }

    @Override
    public void setFormPage(FormPageVo formPageVo) {

    }

    @Override
    public void setFormPages(List<FormPageVo> formPages) {

    }

    @Override
    public void clearFormPage() {

    }

    @Override
    public void setBaseItems(String code, List<String> baseItems) {

    }

    @Override
    public void clearBaseItem() {

    }

    @Override
    public void setAreaOrgs(List<AreaOrgVo> areaOrgs) {

    }

    @Override
    public void clearAreaOrg() {

    }

    @Override
    public Map<Object, Object> getDepartment() {
        return null;
    }

    @Override
    public Map<Object, Object> getDictionary() {
        return null;
    }

    @Override
    public DepartmentVo getDepartment(long depId) {
        return null;
    }

    @Override
    public ItemVo getItem(String code) {
        return null;
    }

    @Override
    public void setSecurityCode(String uuid, String securityCode, int expireTime) {
        cache.put(KeyConstant.SECURITY_CODE + uuid, expireTime, securityCode);
    }

    @Override
    public String getSecurityCode(String uuid) {
        Object obj = cache.get(KeyConstant.SECURITY_CODE + uuid);
        return obj == null ? null : obj.toString();
    }

    @Override
    public void delSecurityCode(String uuid) {
        cache.remove(KeyConstant.SECURITY_CODE + uuid);
    }
}
