package com.fline.form.mgmt.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.model.TempAttachment;

public interface SealInfoMgmtService {
	
	
	Pagination<SealInfo> findPagination(Map<String, Object> param, Ordering order,
			Pagination<SealInfo> page);

	void update(SealInfo sealInfo,String[] certTempIdS,String[] area,String keyword);
	
	void updateActive(SealInfo sealInfo);
	
	Map<String,Object> findByidkeyword(Long id);
	
	void remove(SealInfo sealInfo);

	void create(SealInfo sealInfo,String[] certTempIdS,String[] area,String keyword);

	SealInfo findById(long id);
	
	List<SealInfo> findAll();
	
	List<SealInfo> findByDepartmentId(long deptId);
	
	/**
	 * 根据印章名称查询
	 * @param code
	 * @return
	 */
	SealInfo findByName(String name);
	
	public void analysisExcel(File file) throws Exception;
	
	//List<Map<String,Object>> findCertSeal(String code);
	List<TempAttachment> findCertSeal(String code);
	//更新图片
	void updateImage(long id,byte[] image);
}
