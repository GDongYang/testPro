package com.fline.form.mgmt.service;

import java.util.List;
import java.util.Map;

public interface DataFormatMgmtService {

	/*
	 *查看市场主体所在地
	 */
	String findAddress(String dataStr);

	/*
	 *查看行业
	 */
	Map<String, String> findIndustry();

	/*
	 *查看登记机关
	 */
	String findBureau();

	/*
	 *分页
	 */
	String findPagenation(String pageNum, String pageSize);

	/*
	 *查看业务属性
	 */
	String findCategoryCode(String param);

	/*
	 *查看数据
	 */
	String findById(String dataId);

	/*
	 *查看数据格式
	 */
	String search(String dataStr);

	/*
	 *查看数据具体值
	 */
	Map<String, String> findValues(String dataId);

	/*
	 *根据parent查看数据具体值
	 */
	String findByParent(String formateId, String parentId);

	String findAllByParent(String formatId, String parentId);

	String findEntType(String parentId);

	String getFormateValue(String dataId, String key);

    void setDataFormatText(Object item);

	List<?> setDataFormatText(List<?> item);
}
