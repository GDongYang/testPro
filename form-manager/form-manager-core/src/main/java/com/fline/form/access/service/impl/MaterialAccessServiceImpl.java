package com.fline.form.access.service.impl;

import java.util.List;
import java.util.Map;

import com.feixian.aip.platform.access.common.service.impl.AbstractNamespaceAccessServiceImpl;
import com.fline.form.access.model.Material;
import com.fline.form.access.service.MaterialAccessService;
import com.fline.form.mgmt.service.impl.vo.MaterialTempVo;

public class MaterialAccessServiceImpl extends
AbstractNamespaceAccessServiceImpl<Material> implements
MaterialAccessService {

    @SuppressWarnings("unchecked")
    @Override
    public List<Material> findMaterialBySId(Map<String, Object> map) {
        return (List<Material>)this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Material.findMaterialBySId", map);
    }

	@Override
	public List<String> findTempCodeByMap(Map<String,Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findTempCodeByMap",params);
	}

    @Override
    public List<MaterialTempVo> findMaterialTempVo() {
        return this.getIbatisDataAccessObject().getSqlMapClientTemplate()
                .queryForList(namespace + ".findMaterialTempVo");
    }

	@Override
	public List<String> findCertMaterial(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Material.findCertMaterial", params);
	}

	@Override
	public void createMaterialTemp(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Material.createMaterialTemp", params);
	}

	@Override
	public void deleteMaterialTemp(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.deleteMaterialTemp", params);
	}
	@Override
	public void removeBySituationId(long situationId) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.removeBySituationId", situationId);
	}

	@Override
	public void removeByMap(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.removeByMap", params);
	}

	@Override
	public void removeMaterialTempByMap(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.removeMaterialTempByMap", params);	
	}

	@Override
	public List<Material> findDefaultSituationMaterials(Map<String, Object> params) {
		 return (List<Material>)this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Material.findDefaultSituationMaterials", params);
	}

	@Override
	public void deleteMaterialTempByInnerCode(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.deleteMaterialTempByInnerCode", params);
	}

	@Override
	public void deleteMaterialByInnerCode(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.deleteMaterialByInnerCode", params);
	}

	@Override
	public void createRmaterialTemp(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Material.createRmaterialTemp", params);		
	}

	@Override
	public void deleteOtherCard(Map<String,Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().delete("Material.deleteOtherCard", params);
	}

	@Override
	public List<Material> findMaterialList(Map<String, Object> params) {
		return find("findMaterialList",params);
	}

	@Override
	public List<Long> findIdsByNames(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Material.findIdsByNames", params);
	}

	@Override
	public void insertRMaterialsTemps(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().insert("Material.insertRMaterialsTemps", params);
	}

	@Override
	public List<Long> findMaterialIds(Map<String, Object> params) {
		return this.getIbatisDataAccessObject().getSqlMapClientTemplate().queryForList("Material.findMaterialIds", params);
	}

	@Override
	public void updateRMaterialsTemps(Map<String, Object> params) {
		this.getIbatisDataAccessObject().getSqlMapClientTemplate().update("Material.updateRMaterialsTemps", params);
	}
}
